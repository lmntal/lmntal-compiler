package runtime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.Stack;

/** 抽象マシンクラス */
abstract class AbstractMachine {
	/** ルート膜 */
	protected AbstractMembrane root;
	
	/** ルート膜の取得 */
	AbstractMembrane getRoot() {
		return root;
	}
}

/**
 * TODO 「タスク」に名前変更
 * TODO システムルールセットのマッチテスト・ボディ実行
 * TODO マシン間の上下関係の実装、newMachineをマシンが持つようにする
 *
 */
final class Machine extends AbstractMachine {
	/** 実行膜スタック */
	Stack memStack = new Stack();
	boolean idle;
	static final int maxLoop = 10;
	
	Machine() {
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

/** 計算ノード */
final class LMNtalRuntime {
	List machines = new ArrayList();
	AbstractMembrane rootMem;
	
	/** 計算ノードが持つマシン全てがidleになるまで実行。<br>
	 *  machinesに積まれた順に実行する。親マシン優先にするためには
	 *  マシンが木構造になっていないと出来ない。優先度はしばらく未実装。
	 */
	LMNtalRuntime(Ruleset init){
		Machine root = newMachine();
		rootMem = root.getRoot();
		init.react((Membrane)rootMem);
	}
	
	AbstractMembrane getRootMem(){
		return rootMem;
	}
	
	void exec() {
		boolean allIdle;
		Iterator it;
		Machine m;
		do{
			allIdle = true; // idleでないマシンが見つかったらfalseになる。
			it = machines.iterator();
			while(it.hasNext()){
				m = (Machine)it.next();
				if(!m.isIdle()){ // idleでないマシンがあったら
					m.exec(); // ひとしきり実行
					allIdle = false; // idleでないマシンがある
					break;
				}
			}
		}while(!allIdle);
	}
	
	Machine newMachine() {
		Machine m = new Machine();
		machines.add(m);
		return m;
	}
}