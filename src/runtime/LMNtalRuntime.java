package runtime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.Stack;

/** 抽象物理マシンクラス */
abstract class AbstractMachine {
	protected String runtimeid;
	/** この物理マシンに親膜を持たないロックされていないルート膜を作成し、仮でない実行膜スタックに積む。*/
	abstract AbstractTask newTask();
	/** この物理マシンに指定の親膜を持つロックされたルート膜を作成し、仮の実行膜スタックに積む。*/
	abstract AbstractTask newTask(AbstractMembrane parent);
}

/** 抽象タスククラス */
abstract class AbstractTask {
	/** 物理マシン */
	protected AbstractMachine runtime;
	/** ルート膜 */
	protected AbstractMembrane root;
	
	/** 物理マシンの取得 */
	AbstractMachine getMachine() {
		return runtime;
	}
	/** ルート膜の取得 */
	AbstractMembrane getRoot() {
		return root;
	}
}

/**
 * TODO システムルールセットのマッチテスト・ボディ実行
 * TODO タスク間の上下関係の実装をする？
 */
final class Task extends AbstractTask {
	/** 実行膜スタック */
	Stack memStack = new Stack();
	Stack bufferedStack = new Stack();
	boolean idle = false;
	static final int maxLoop = 10;
	
	Task() {
		root = new Membrane(this);
		memStack.push(root);
	}
	Task(AbstractMembrane parent) {
		root = new Membrane(this);
		root.lock(root);
		root.activate(); // 仮の実行膜スタックに積む
		parent.addMem(root);
	}
	
	boolean isIdle(){
		return idle;
	}
	
	void exec() {
		Membrane mem = (Membrane)memStack.peek();
		if(mem == null || !mem.lock(mem)) {
			// 本膜が無いかまたは本膜のロックを取得できないとき
			if (mem != null) { System.out.println(mem);}			idle = true;
			return;
		}
		
		for(int i=0; i < maxLoop && mem == memStack.peek(); i++){
			// 本膜が変わらない間 & ループ回数を越えない間
//			System.out.println("mems  = " + memStack);
//			System.out.println("atoms = " + mem.getReadyStackStatus());
			Atom a = mem.popReadyAtom();
			Iterator it = mem.rulesetIterator();
			boolean flag;
			if(a != null){ // 実行膜スタックが空でないとき
				flag = false;
				while(it.hasNext()){ // 本膜のもつルールをaに適用
					if(((Ruleset)it.next()).react(mem, a)) flag = true;
				}
				if(flag == false){ // ルールが適用できなかった時
					if(!mem.isRoot()) {mem.getParent().enqueueAtom(a);} 
				}
				else {}// システムコールアトムなら親膜につみ、親膜を活性化
			}else{ // 実行膜スタックが空の時
				flag = false;
				while(it.hasNext()){ // 膜主導テストを行う
					if(((Ruleset)it.next()).react(mem)) {
						flag = true;
						if (memStack.peek() != mem) break;
					}
				}

				if(flag == false){ // ルールが適用できなかった時
					memStack.pop(); // 本膜をpop
					// 本膜がroot膜かつ親膜を持つなら、親膜を活性化
					if(mem.isRoot() && mem.getParent() != null) {
						mem.getParent().activate();
					}
					// 子膜が全てstableなら、この膜をstableにする。
					it = mem.memIterator();
					flag = false;
					while(it.hasNext()){
						if(((AbstractMembrane)it.next()).isStable() == false)
							flag = true;
					}
					if(flag == false) mem.toStable();
				}
			}
		}
		// 本膜が変わったor指定回数繰り返したら、ロックを解放して終了
		mem.unlock();
	}
}

public final class LMNtalRuntime extends Machine {
	Membrane globalRoot;
	
	public LMNtalRuntime(){
		AbstractTask t = newTask();
		globalRoot = (Membrane)t.getRoot();
		// Inline
		Inline.initInline();
	}

	/**
	 * １回だけ適用するルールをglobalRoot膜に適用する。
	 * 初期化ルール、およびREPLが１行入力毎に生成するルールを適用するために使用する。
	 * @deprecated
	 */
	public void applyRulesetOnce(Ruleset r){
		r.react(globalRoot);
	}
	
	public Membrane getGlobalRoot(){
		return globalRoot;
	}
	/**@deprecated*/
	public Membrane getRoot(){
		return globalRoot;
	}
}

/** 物理マシン */
class Machine extends AbstractMachine {
	List tasks = new ArrayList();
	
	AbstractTask newTask() {
		Task t = new Task();
		tasks.add(t);
		return t;
	}
	AbstractTask newTask(AbstractMembrane parent) {
		Task t = new Task(parent);
		tasks.add(t);
		return t;
	}

	/** 物理マシンが持つタスク全てがidleになるまで実行。<br>
	 *  Tasksに積まれた順に実行する。親タスク優先にするためには
	 *  タスクが木構造になっていないと出来ない。優先度はしばらく未実装。
	 */
	public void exec() {
		boolean allIdle;
		do {
			allIdle = true; // idleでないタスクが見つかったらfalseになる。
			Iterator it = tasks.iterator();
			while (it.hasNext()) {
				Task task = (Task)it.next();
				if (!task.isIdle()) { // idleでないタスクがあったら
					task.exec(); // ひとしきり実行
					allIdle = false; // idleでないタスクがある
					break;
				}
			}
		} while(!allIdle);
	}
}


