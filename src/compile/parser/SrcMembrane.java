package compile.parser;
import java.util.LinkedList;

/** ソースファイル中の膜表現 */

class SrcMembrane {
	/** 膜の内容プロセスの表現 */
	LinkedList process = null;
	/** 終了フラグの有無 */
	boolean stable = false;
	/** 名前 */
	public String name;
	
	/**
	 * 空の膜を作成します 
	 */
	public SrcMembrane() {
		this(null, new LinkedList());
	}
	
	/**
	 * 空の名前つき膜を作成します 
	 */
	public SrcMembrane(String name) {
		this(name, new LinkedList());
	}
	
	/**
	 * 指定された子プロセスを持つ膜を作成します
	 * @param process 膜に含まれる子プロセス
	 */
	public SrcMembrane(LinkedList process) {
		this(null, process);
	}
	
	/**
	 * 指定された子プロセスを持つ名前つき膜を作成します
	 * @param process 膜に含まれる子プロセス
	 */
	public SrcMembrane(String name, LinkedList process) {
		this.name = name;
		this.process = process;
		//runtime.Env.p("Membran name = "+name);
	}
	
	/**
	 * 子プロセスを取得します
	 * @return 子プロセスのリスト
	 */
	public LinkedList getProcess() { return process; }
}