package runtime;


/** 抽象ランタイム（旧：抽象物理マシン、旧々：抽象計算ノード）クラス。*/

abstract class AbstractLMNtalRuntime {
	protected String runtimeid;
	/** このランタイムに親膜を持たないロックされていないルート膜を作成し、仮でない実行膜スタックに積む。*/
	abstract AbstractTask newTask(AbstractMembrane parent);
	/** このランタイムの実行を終了する */
	abstract public void terminate();
	
//	/** ランタイムのルールスレッドに対して再実行を要求する。*/
//	abstract public void awake();
}



/** ランタイムグループおよびグローバルルート膜を生成し、管理するランタイムのクラス */

public final class MasterLMNtalRuntime extends LocalLMNtalRuntime {
	private Membrane globalRoot;	// masterTaskへの参照を持つ方が分かりやすいかもしれない

	public MasterLMNtalRuntime(){
		Task masterTask = new Task(this);
		tasks.add(masterTask);
		globalRoot = (Membrane)masterTask.getRoot();
		// Inline
		Inline.initInline();
		Env.theRuntime = this;
	}

//	/**
//	 * １回だけ適用するルールをglobalRoot膜に適用する。
//	 * 初期化ルール、およびREPLが１行入力毎に生成するルールを適用するために使用する。
//	 * @deprecated
//	 */
//	public void applyRulesetOnce(Ruleset r){
//		r.react(globalRoot);
//	}
	
	public final Membrane getGlobalRoot(){
		return globalRoot;
	}
	public final Task getMasterTask(){
		return (Task)globalRoot.getTask();
	}
//	/** マスタランタイムとして実行する */
//	public void run() {
//		RemoteLMNtalRuntime.init();
//		while (true) {
//			if (Env.fTrace) {
//				Env.p( Dumper.dump(getGlobalRoot()) );
//			}
//			localExec();
//			if (globalRoot.isStable()) break;
//			synchronized(this) {
//				try {
//					if (terminated) break;
//					wait();
//				}
//				catch (InterruptedException e) {}
//			}
//		}
//		RemoteLMNtalRuntime.terminateAll();
//	}
}

