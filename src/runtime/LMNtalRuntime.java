package runtime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.Stack;

/** 抽象物理マシンクラス */
abstract class AbstractLMNtalRuntime {
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
	protected AbstractLMNtalRuntime runtime;
	/** ルート膜 */
	protected AbstractMembrane root;
	
	/** 物理マシンの取得 */
	AbstractLMNtalRuntime getRuntime() {
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

/** 物理マシン */
final class LMNtalRuntime extends AbstractLMNtalRuntime {
	List tasks = new ArrayList();
	AbstractMembrane rootMem;
	
	/** 物理マシンが持つタスク全てがidleになるまで実行。<br>
	 *  Tasksに積まれた順に実行する。親タスク優先にするためには
	 *  タスクが木構造になっていないと出来ない。優先度はしばらく未実装。
	 */
	LMNtalRuntime(Ruleset init){
		Task root = (Task)newTask();
		rootMem = root.getRoot();
		init.react((Membrane)rootMem);
	}
	
	AbstractMembrane getRootMem(){
		return rootMem;
	}
	
	void exec() {
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
	
	AbstractTask newTask() {
		Task m = new Task();
		tasks.add(m);
		return m;
	}
}