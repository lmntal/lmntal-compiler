/**
 * ソース中のルールを表します
 */

package compile.parser;
import java.util.LinkedList;

class SrcRule {
	
	public String name; // ルール名
	public LinkedList head;			// ヘッドプロセス
	public LinkedList body;			// ボディプロセス
	public LinkedList guard;			// ガードプロセス
	public LinkedList guardNegatives;	// ガード否定条件構文のリスト
	/**
	 * 指定されたヘッドルールとボディルールと空のガードでルールを初期化します
	 * @param head ヘッドのリスト
	 * @param body ボディのリスト
	 */
	public SrcRule(String name, LinkedList head, LinkedList body) {
		this(name, head, new LinkedList(), body);
	}
	
	/**
	 * 指定されたヘッドルールとボディルールとガードでルールを初期化します
	 * @param head ヘッドのリスト
	 * @param gurad ガードのリスト
	 * @param body ボディのリスト
	 */
	public SrcRule(String name, LinkedList head, LinkedList guard, LinkedList body) {
		
		this.name = name;
		this.head = head;
		this.guard = guard;
		this.guardNegatives = new LinkedList();
		this.body = body;
		addTypeConstraint(head);
		
	}
	
	/**
	 * リンク名が_IXなど、頭に_Iがつくと自動でガードにint(_IX)を加える.
	 * hara. nakano.
	 * */
	public void addTypeConstraint(LinkedList l){
		if(l==null)return;
		for(int i = 0; i < l.size(); i++){
			Object o = l.get(i);
			if(o instanceof SrcLink){
				SrcLink sl = (SrcLink)o;
				if(sl.name.matches("^_I.*")){
					/*int発見*/
					LinkedList newl = new LinkedList();
					newl.add(new SrcLink(sl.name));
					SrcAtom newg = new SrcAtom("int",newl);
					this.guard.add(newg);
				}
				else if(sl.name.matches("^_G.*")){
					/*ground発見*/
					LinkedList newl = new LinkedList();
					newl.add(new SrcLink(sl.name));
					SrcAtom newg = new SrcAtom("ground",newl);
					this.guard.add(newg);
				}
				else if(sl.name.matches("^_S.*")){
					/*string発見*/
					LinkedList newl = new LinkedList();
					newl.add(new SrcLink(sl.name));
					SrcAtom newg = new SrcAtom("string",newl);
					this.guard.add(newg);
				}
				else if(sl.name.matches("^_U.*")){
					/*unary発見*/
					LinkedList newl = new LinkedList();
					newl.add(new SrcLink(sl.name));
					SrcAtom newg = new SrcAtom("unary",newl);
					this.guard.add(newg);
				}
			}
			else if(o instanceof SrcAtom){
				SrcAtom sa = (SrcAtom)o;
					addTypeConstraint(sa.process);
			}else if(o instanceof SrcMembrane){
				SrcMembrane sm = (SrcMembrane)o;
					addTypeConstraint(sm.process);
			}
		}
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
		return "(rule:"+name+")";
	}
}