/**
 * ソース中のルールを表します
 */

package compile.parser;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class SrcRule {
	
	public String name; // ルール名
	public int lineno;	//行番号 2006.1.22 by inui
	public LinkedList head;			// ヘッドプロセス
	public LinkedList body;			// ボディプロセス
	public LinkedList guard;			// ガードプロセス
	public LinkedList guardNegatives;	// ガード否定条件構文のリスト
	private String text; //ルールのテキスト表現
	/**
	 * 指定されたヘッドルールとボディルールと空のガードでルールを初期化します
	 * @param head ヘッドのリスト
	 * @param body ボディのリスト
	 */
	public SrcRule(String name, LinkedList head, LinkedList body) {
		this(name, head, new LinkedList(), body);
	}
	
	//2006.1.22 by inui
	/**
	 * 指定されたヘッドルールとボディルールと空のガードでルールを初期化します
	 * @param head ヘッドのリスト
	 * @param body ボディのリスト
	 * @param lineno 行番号
	 */
	public SrcRule(String name, LinkedList head, LinkedList body, int lineno) {
		this(name, head, new LinkedList(), body);
		this.lineno = lineno;
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
		addHyperLinkConstraint(head, body);
	}
	
	//2006.1.22 by inui
	/**
	 * 指定されたヘッドルールとボディルールとガードでルールを初期化します
	 * @param head ヘッドのリスト
	 * @param gurad ガードのリスト
	 * @param body ボディのリスト
	 * @param lineno 行番号
	 */
	public SrcRule(String name, LinkedList head, LinkedList guard, LinkedList body, int lineno) {
		this(name, head, guard, body);
		this.lineno = lineno;
	}
	
	//2006/07/07 by kudo
	/**
	 * 指定されたヘッドルールとボディルールとガードルールで初期化する。
	 * simpagation ruleの構文に対して使われるコンストラクタ。
	 * @param head ヘッドのリスト
	 * @param head2 ヘッドのリスト ( \ の後ろ )
	 * @param guard ガードのリスト
	 * @param body ボディのリスト
	 * @param lineno 行番号
	 */
	public SrcRule(String name, LinkedList head, List head2, LinkedList guard, LinkedList body, int lineno){
		this.name = name;
		this.head = head;
		this.guard = (guard==null? new LinkedList() : guard);
		this.guardNegatives = new LinkedList();
		this.body = body;
		if(head2 != null)unSimpagationize(head2);
		LinkedList head3 = this.head;
		LinkedList body2 = this.body;
		addTypeConstraint(head3);
		addHyperLinkConstraint(head3, body2);
	}

	/**
	 *	 Head部, Body部に!Xが出現した場合，Guard部にhlink(X)もしくはnew(X)を追加する.
	 *	 meguro
	 */	
	public void addHyperLinkConstraint(LinkedList head, LinkedList body) {
		LinkedList headhl = new LinkedList();
		addHyperLinkConstraintSub(head, body, headhl);	
	}

	private LinkedList addHyperLinkConstraintSub(LinkedList head, LinkedList body, LinkedList headhl) {
		if (head!=null)	{
			for (int i = 0; i < head.size(); i++) {
				Object o = head.get(i);
				if (o instanceof SrcHyperLink) {
					SrcHyperLink shl = (SrcHyperLink)o;
					LinkedList newl = new LinkedList();
					SrcLink name = new SrcLink(shl.name);
					newl.add(name);
					headhl.add(name.toString());	
					SrcAtom newg = new SrcAtom("hlink", newl);
					this.guard.add(newg);
				}
				else if (o instanceof SrcAtom) {
					SrcAtom sa = (	SrcAtom)o;
					headhl = addHyperLinkConstraintSub(	sa.process, null, headhl);
				} 
				else if (o instanceof SrcMembrane)	 {
					SrcMembrane sm = (	SrcMembrane)o;
					headhl = addHyperLinkConstraintSub(sm.process, null, headhl);
				}
			}
		}
 
		if (body!=null) {
			for (int i = 0; i < body.size(); i++) {
				Object o = body.get(i);
				if(o instanceof SrcHyperLink) {
					SrcHyperLink shl = (SrcHyperLink)o;
					SrcLink name = new SrcLink(shl.name);
					if (headhl.contains(name.toString())==false) {
						LinkedList newl = new LinkedList();
						newl.add(name);
						headhl.add(name.toString());
						SrcAtom newg = new SrcAtom("new", newl);
						this.guard.add(newg);
					}
				}
				else if (o instanceof SrcAtom) {
					SrcAtom sa = (	SrcAtom)o;
					headhl = addHyperLinkConstraintSub(	null, sa.process, headhl);
				}
				else if (o instanceof SrcMembrane) {
					SrcMembrane sm = (	SrcMembrane)o;
					headhl = addHyperLinkConstraintSub(	null, sm.process, headhl);
				}
			}
		}
		return headhl;
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
	
	// by kudo (2006/07/07)
	/**
	 * simpagation rule を、通常のルールの形に直す。コンストラクタから呼ばれる。
	 * @param head2 ヘッドの'\'の後ろ部分のリスト
	 */
	private void unSimpagationize(List head2){
		// head を全てbodyへコピー (前に追加のほうが再利用の上でも都合がいい？)
		body.addAll(copySrcs(head));
		// head2をheadの後ろに連結
		head.addAll(head2);
	}
	
	/**
	 * ソースオブジェクトのリストをコピーする。
	 * @param l
	 * @return
	 */
	private LinkedList copySrcs(List l){
		LinkedList ret = new LinkedList(); // List 型だと各所で使っているgetFirstが無い
		if(l == null)return null;
		Iterator it = l.iterator();
		while(it.hasNext()){
			Object o = it.next();
			if(o instanceof SrcAtom){
				SrcAtom sa = (SrcAtom)o;
				ret.add(new SrcAtom(sa.getName(),copySrcs(sa.getProcess())));
			}
			else if(o instanceof SrcMembrane){
				SrcMembrane sm = (SrcMembrane)o;
				SrcMembrane cpm = new SrcMembrane(copySrcs(sm.getProcess()));
				cpm.name = sm.name;
				cpm.kind = sm.kind;
				cpm.stable = sm.stable;
				cpm.pragma = sm.pragma;
				ret.add(cpm);
			}
			else if(o instanceof SrcProcessContext){
				SrcProcessContext spc = (SrcProcessContext)o;
				SrcProcessContext cppc = new SrcProcessContext(spc.getName());
				cppc.args = copySrcs(spc.args);
				if(spc.bundle != null)cppc.bundle = new SrcLinkBundle(spc.bundle.getName());
				ret.add(cppc);
			}
			else if(o instanceof SrcContext){ // SrcLink, SrcLinkBundle, SrcRuleContext
				SrcLink sl = (SrcLink)o;
				ret.add(new SrcLink(sl.getName()));
			}
			else if(o instanceof SrcRule){ // ※左辺にルールは出現しない筈
			}
		}
		return ret;
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
	
	/**LMNtalソース形式のテキスト表現を取得する。*/
	public String getText() {
		return text;
	}
	void setText() {
		text = SrcDumper.dump(this);
	}
}
