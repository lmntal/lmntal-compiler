/**
 * ソース中での膜表現
 */

package compile.parser;
import java.util.LinkedList;

class SrcMembrane {
	
	private LinkedList process = null;
	
	/**
	 * 空の膜を作成します 
	 */
	public SrcMembrane() {
		this(null);	
	}
	
	/**
	 * 指定された子プロセスを持つ膜を作成します
	 * @param process 膜に含まれる子プロセス
	 */
	public SrcMembrane(LinkedList process) {
		this.process = process;	
	}
	
	/**
	 * 子プロセスを取得します
	 * @return 子プロセスのリスト
	 */
	public LinkedList getProcess() { return process; }
}