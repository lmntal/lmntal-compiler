package runtime;

/** ランタイムグループおよびグローバルルート膜を生成し、管理するランタイムのクラス。
 * @author n-kato */

public final class MasterLMNtalRuntime extends LocalLMNtalRuntime {
	private Membrane globalRoot;

	public MasterLMNtalRuntime(){
		Task masterTask = new Task(this);
		tasks.add(masterTask);
		globalRoot = (Membrane)masterTask.getRoot();
		// Inline
		Inline.initInline();
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

