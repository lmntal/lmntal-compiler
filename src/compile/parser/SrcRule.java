/**
 * ソース中のルールを表します
 */

package compile.parser;
import java.util.LinkedList;

class SrcRule {
	
	public LinkedList head;			// ヘッドプロセス
	public LinkedList body;			// ボディプロセス
	public LinkedList guard;			// ガードプロセス
	public LinkedList guardNegatives;	// ガード否定条件構文のリスト
	/**
	 * 指定されたヘッドルールとボディルールと空のガードでルールを初期化します
	 * @param head ヘッドのリスト
	 * @param body ボディのリスト
	 */
	public SrcRule(LinkedList head, LinkedList body) {
		this(head, new LinkedList(), body);
	}
	
	/**
	 * 指定されたヘッドルールとボディルールとガードでルールを初期化します
	 * @param head ヘッドのリスト
	 * @param gurad ガードのリスト
	 * @param body ボディのリスト
	 */
	public SrcRule(LinkedList head, LinkedList guard, LinkedList body) {
		this.head = head;
		this.guard = guard;
		this.guardNegatives = new LinkedList();
		this.body = body;
	}

	/**
	 * ヘッドを設定する
	 */
	public void setHead(LinkedList head) {
		this.head = head;
	}
	
	/**
	 * ルールのヘッドを取得します
	 * @return ヘッドのリスト
	 */
	public LinkedList getHead() {
		return this.head;
	}
	
	/**
	 * ルールのガードを得ます
	 * @return ガードのリスト
	 */
	public LinkedList getGuard() {
		return this.guard;
	}
	/**
	 * ガード否定条件を取得する
	 */
	public LinkedList getGuardNegatives() {
		return this.guardNegatives;
	}
	
	/**
	 * ルールのボディを取得します
	 * @return ボディのリスト
	 */
	public LinkedList getBody() {
		return this.body;
	}
	
	public String toString() {
		return "(rule)";
	}
}