/**
 * ソース中のルールを表します
 */

package compile.parser;
import java.util.LinkedList;

class SrcRule {
	
	public LinkedList head = null; // ヘッドプロセス
	public LinkedList body = null; // ボディプロセス
	public LinkedList guard = null; // ガードプロセス
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
		this.body = body;
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
	 * ルールのボディを取得します
	 * @return ボディのリスト
	 */
	public LinkedList getBody() {
		return this.body;
	}
}