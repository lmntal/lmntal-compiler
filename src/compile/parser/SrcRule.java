/**
 * ソース中のルールを表します
 */

package compile.parser;
import java.util.LinkedList;

class SrcRule {
	
	private LinkedList head = null;
	private LinkedList body = null;
	
	/**
	 * 指定されたヘッドルールとボディルールでルールを初期化します
	 * @param head ヘッドのリスト
	 * @param body ボディのリスト
	 */
	public SrcRule(LinkedList head, LinkedList body) {
		this.head = head;
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
	 * ルールのボディを取得します
	 * @return ボディのリスト
	 */
	public LinkedList getBody() {
		return this.head;
	}
}