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
