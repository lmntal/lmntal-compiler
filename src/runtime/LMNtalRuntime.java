package runtime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.Stack;

/** 抽象物理マシンクラス */
abstract class AbstractMachine {
	protected String runtimeid;
	/** この物理マシンに（親膜を持たない）ルート膜を作成する。
	 * <p>
	 * ルート膜の親膜またはnullを引数に取るようにした方がよいかも知れない。
	 * 詳細は、タスクを生成する構文を決定する頃に決める */
	abstract AbstractTask newTask();
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
	boolean idle;
	static final int maxLoop = 10;
	
	Task() {
		root = new Membrane(this);
		memStack.push(root);
		idle = false;
	}
	
	boolean isIdle(){
		return idle;
	}
	
	void exec() {
		if(memStack.isEmpty()){ // 空ならidleにする。
			idle = true;
			return;
		}
		// 実行膜スタックが空でない
		Membrane mem = (Membrane)memStack.peek();
		if(!mem.lock(mem)) return; // ロック失敗
		
		Atom a;
		for(int i=0; i < maxLoop && mem == memStack.peek(); i++){
			// 本膜が変わらない間 & ループ回数を越えない間
			
			a = mem.popReadyAtom();
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
					if(((Ruleset)it.next()).react(mem)) flag = true;
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
						if(((Membrane)it.next()).isStable() == false)
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
	
	/** 物理マシンが持つタスク全てがidleになるまで実行。<br>
	 *  Tasksに積まれた順に実行する。親タスク優先にするためには
	 *  タスクが木構造になっていないと出来ない。優先度はしばらく未実装。
	 */

	/**
	 * 	このマシンにタスクを作る。これを呼んだ後、ルート膜のmembrane.setParent()が呼ばれる？
	 * これをやめて、Taskのコンストラクタに親膜を渡すようにしたほうがいい？
	 */
	AbstractTask newTask() {
		Task t = new Task();
		tasks.add(t);
		return t;
	}

/* TODO　消去。Master以外では、タスクを最初に作る必要はない。
	public Machine(){
		newTask();
	}
*/	
//	AbstractMembrane getRootMem(){
//		return rootMem;
//	}
	
	public void exec() {
		boolean allIdle;
		Iterator it;
		Task m;
		do{
			allIdle = true; // idleでないタスクが見つかったらfalseになる。
			it = tasks.iterator();
			while(it.hasNext()){
				m = (Task)it.next();
				if(!m.isIdle()){ // idleでないタスクがあったら
					m.exec(); // ひとしきり実行
					allIdle = false; // idleでないタスクがある
					break;
				}
			}
		}while(!allIdle);
	}
}


