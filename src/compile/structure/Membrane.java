package compile.structure;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import runtime.Functor;
import runtime.InterpretedRuleset;
import runtime.Ruleset;

import runtime.Env;

/** 
 * ソースコード中の膜の構造を表すクラス<br>
 * memo:全て1つの配列に入れる方法もある。<br>
 * 各要素はListとして保持される。
 */
public final class Membrane {
	/** 親膜 */
	public Membrane parent = null;
	/** 終了フラグがセットされているかどうかを表す */
	public boolean stable = false;
	/** 膜のタイプ */
	public int kind = 0;
	/** ＠指定またはnull
	 * <p><b>仮仕様</b>
	 * ホスト指定を表す文字列が入る型付きプロセス文脈名を持った
	 * 所属膜を持たないプロセスコンテキストが代入される。
	 * <br>[要注意]例外的に、引数の長さおよびbundleは0にセットされる。
	 * @see ContextDef.lhsMem */
	public ProcessContext pragmaAtHost = null;
//	/** システムルールセットとして使うなら真 */
//	public boolean is_system_ruleset = false;

	/** 膜名 */
	public String name;

	/** アトム(compile.structure.Atom)のリスト */
	public List<Atom> atoms = new LinkedList<Atom>();

	/** 子膜(compile.structure.Membrane)のリスト */
	public List<Membrane> mems = new LinkedList<Membrane>();

	/** ルール(compile.structure.RuleStructure)のリスト */
	public List<RuleStructure> rules = new LinkedList<RuleStructure>();

	////////////////////////////////////////////////////////////////

	/** アトム集団(compile.structure.Atom)のリスト */
	public List<Atom> aggregates = new LinkedList<Atom>();

	/** プロセス文脈出現(compile.structure.ProcessContext)のリスト */
	public List<ProcessContext> processContexts = new LinkedList<ProcessContext>();

	/** ルール文脈出現(compile.structure.RuleContext)のリスト */
	public List<RuleContext> ruleContexts = new LinkedList<RuleContext>();

	/** 型付きプロセス文脈出現(compile.structure.ProcessContext)のリスト */
	public List<ProcessContext> typedProcessContexts = new LinkedList<ProcessContext>();

	////////////////////////////////////////////////////////////////

	/** 膜の自由リンク名(String)からそのリンク出現(compile.structure.LinkOccurrence)への写像 */
	public HashMap<String, LinkOccurrence> freeLinks = new HashMap<String, LinkOccurrence>();

	/** ルールセット。生成されたルールオブジェクトは逐次ここに追加されていく。*/
//	public runtime.Ruleset ruleset = new InterpretedRuleset();
	public List<Ruleset> rulesets = new LinkedList<Ruleset>();

	////////////////////////////////////////////////////////////////

	/** コンパイラ用データ構造。atomやmemをGROUP化されるべきものについて近くに配置する。 **/
	private RependenceGraph rg = new RependenceGraph();
	////////////////////////////////////////////////////////////////

	/**
	 * コンストラクタ
	 * @param mem 親膜
	 */
	public Membrane(Membrane mem) {
		this.parent = mem;
	}

	public int getNormalAtomCount() {
		int c=0;
		for(Atom a : atoms){
			if(!a.functor.isInsideProxy() && !a.functor.isOutsideProxy()) c++;
		}
		return c;
	}
	/** この膜にあるinside_proxyアトムの個数を取得する */
	public int getFreeLinkAtomCount() {
		int c=0;
		for(Atom a : atoms){
			if(a.functor.equals(Functor.INSIDE_PROXY)) c++;
		}
		return c;
	}

	/**
	 * {} なしで出力する。
	 * 
	 * ルールの出力の際、{} アリだと
	 * (a:-b) が ({a}:-{b}) になっちゃうから。
	 *  
	 * @return String 
	 */
	public String toStringWithoutBrace() {
		LinkedList list = new LinkedList();
		list.addAll(atoms);
		list.addAll(mems);
		list.addAll(rules);
		list.addAll(processContexts);
		list.addAll(ruleContexts);
		list.addAll(typedProcessContexts);

		//return list.toString().replaceAll("^.|.$","");
		return Env.parray(list, ", ").toString();
	}

	public String toString() {
		String ret = "{ " + toStringWithoutBrace() + " }" + (kind==1 ? "_" : "") + (stable ? "/" : "");
		if (pragmaAtHost != null) {
			ret += "@" + ((ProcessContext)pragmaAtHost).getQualifiedName();
		}
		return ret;
	}
	public String toStringAsGuardTypeConstraints() {
		if (atoms.isEmpty()) return "";
		String text = "";
		for(Atom atom : atoms){
			text += " "+ atom.toStringAsTypeConstraint();
		}
		return text.substring(1);
	}
	/**
	 * この膜に含まれる全てのルールセットを表示する。
	 */
	public void showAllRulesets() {
		Iterator it = rulesets.iterator();
		while (it.hasNext()) {
			Env.d( ((InterpretedRuleset)it.next()) );
		}

		// 直属のルールそれぞれについて、その左辺膜と右辺膜のルールセットを表示
		for(RuleStructure rs : rules){
			rs.leftMem.showAllRulesets();
			rs.rightMem.showAllRulesets();
		}
		// 子膜それぞれ
		for(Membrane mem : mems){
			mem.showAllRulesets();
		}
	}

	/**
	 * この膜の中にあるルールを全て表示する。
	 * 
	 * <pre>
	 * 「この膜の中にあるルール」とは、以下の３種類。
	 * 　[1]この膜のルールセットに含まれる全てのルール
	 * 　[2] [1]の左辺膜の中にあるルール
	 * 　[3] [1]の右辺膜の中にあるルール
	 * </pre>
	 */
	public void showAllRules() {
		Env.c("Membrane.showAllRules " + this);
		Iterator it = rulesets.iterator();
		while (it.hasNext()) {
			((InterpretedRuleset)it.next()).showDetail();
		}

		// 直属のルールそれぞれについて、その左辺膜と右辺膜のルールセットを表示
		it = rules.iterator();
		while (it.hasNext()) {
			RuleStructure rs = (RuleStructure)it.next();
			//Env.p("");
			//Env.p("About rule structure (LEFT): "+rs.leftMem+" of "+rs);
			rs.leftMem.showAllRules();
			//Env.p("About rule structure (RIGHT): "+rs.rightMem+" of "+rs);
			rs.rightMem.showAllRules();
		}
		// 子膜それぞれ
		it = mems.iterator();
		while(it.hasNext()) ((Membrane)it.next()).showAllRules();
	}

	/**
	 * この膜の中にあるルールを LMNtal syntax で全て表示する。
	 * 
	 * <pre>
	 * 「この膜の中にあるルール」とは、以下の３種類。
	 * 　[1]この膜のルールセットに含まれる全てのルール
	 * 　[2] [1]の左辺膜の中にあるルール
	 * 　[3] [1]の右辺膜の中にあるルール
	 * </pre>
	 */
	public int showAllRulesLMNtalSyntax(int rulesetIndex) {
		Env.c("Membrane.showAllRules " + this);
		Iterator it = rulesets.iterator();
		while (it.hasNext()) {
			Env.p("ruleset{");
			((InterpretedRuleset)it.next()).showDetailLMNtalSyntax(rulesetIndex);
			rulesetIndex++;
			Env.p("}.");
		}

		// 直属のルールそれぞれについて、その左辺膜と右辺膜のルールセットを表示
		it = rules.iterator();
		while (it.hasNext()) {
			RuleStructure rs = (RuleStructure)it.next();
			//Env.p("");
			//Env.p("About rule structure (LEFT): "+rs.leftMem+" of "+rs);
			rs.leftMem.showAllRulesLMNtalSyntax(rulesetIndex);
			//Env.p("About rule structure (RIGHT): "+rs.rightMem+" of "+rs);
			rs.rightMem.showAllRulesLMNtalSyntax(rulesetIndex);
		}
		// 子膜それぞれ
		it = mems.iterator();
		while(it.hasNext()){
			((Membrane)it.next()).showAllRulesLMNtalSyntax(rulesetIndex);
		}
		return rulesetIndex;
	}
	/*
	 * okabe
	 * この膜に含まれるアトムのうち一番最初のアトムのアトム名を返す
	 * ルールの左辺が膜の場合は再帰呼び出し
	 * トレースモードで使用
	 */
	public String getFirstAtomName(){
		Iterator<Atom> atomIt = atoms.iterator();
		Iterator<Membrane> memIt= mems.iterator();
		if (atomIt.hasNext()) {
			return atomIt.next().getName();
		} else if (memIt.hasNext()) {
			return memIt.next().getFirstAtomName();
		} else {
			// プロキシとかプロセス文脈のときはとりあえず放置
			return "null";
		}
	}

	/**
	 * 与えられた膜の中身をこの膜に追加する。
	 * @param m
	 */
	public void add(Membrane m) {
		atoms.addAll(m.atoms);
		mems.addAll(m.mems);
		rules.addAll(m.rules);
		aggregates.addAll(m.aggregates);
		processContexts.addAll(m.processContexts);
		ruleContexts.addAll(m.ruleContexts);
		typedProcessContexts.addAll(m.typedProcessContexts);
		freeLinks.putAll(m.freeLinks);
		rulesets.addAll(m.rulesets);
	}

	public void connect(Object x, Object y){
//		System.out.println("connect "+x + "   " + y);
		rg.connect(x, y);
	}

	public void printfRG(){
		System.out.println(rg.toString());
	}
	public void createRG() {
		rg.addAll(atoms);
		rg.addAll(mems);
	}

	public Collection<LinkedList> allKnownElements() {
		return rg.allKnownElements();
	}
}

class RependenceGraph {
	private List<Membrane> mems;
	private List<Atomic> atoms;
	private List atomandmems;
	private UnionFind uf;

	RependenceGraph(){
		atoms = new LinkedList<Atomic>();
		mems = new LinkedList<Membrane>();
		uf = new UnionFind();
	}
	RependenceGraph(List<Atomic> atoms, List<Membrane> mems){
		this.atoms = atoms;
		this.mems = mems;
		uf = new UnionFind();
		uf.addAll(atoms);
		uf.addAll(mems);
	}
	void addAll(List x){
		uf.addAll(x);
	}
	void connect(Object x, Object y){
		uf.union(x,y);
	}

	private void reachable(Object x, Object y){
		uf.areUnified(x, y);
	}

	public String toString(){
		return uf.allKnownElements().toString();
	}

	Collection<LinkedList> allKnownElements() {
		return uf.allKnownElements();
	}

	private class UnionFind {
		private HashMap<Object, Object> lnk = new HashMap();
		private HashMap<Object, Integer> lnkSiz = new HashMap();
		private HashMap<Object, LinkedList> lists = new HashMap();

		private void union( Object x, Object y ){
			if(!lnkSiz.containsKey(x))
				add(x);
			if(!lnkSiz.containsKey(y))
				add(y);
			Object tx = find(x);
			Object ty = find(y);
			Object temp = link_repr(tx, ty);
			LinkedList listx = lists.get(tx);
			LinkedList listy = lists.get(ty);
			if(temp == tx){
				listx.addAll(listy);
				lists.remove(ty);
			} else if(temp == ty) {
				listy.addAll(listx);
				lists.remove(tx);
			}
		}

		public String toString(){
			return lists.toString();
		}

		private void add(Object x)
		{
			if(!lnkSiz.containsKey(x))
			{
				lnkSiz.put(x, 1);
				LinkedList<Object> list = new LinkedList();
				list.add(x);
				lists.put(x, list);
			}
		}
		private void addAll(Collection c){
			Iterator it = c.iterator();
			while(it.hasNext())
				add(it.next());
		}

		private boolean areUnified(Object x, Object y){
			return find(x) == find(y);
		}

		Collection<LinkedList> allKnownElements(){
			return lists.values();
		}

		private	Object find( Object x )
		{
			// ここでPath圧縮すると計算量が nlog(n) から n ack^-1(n) に
			while(lnk.containsKey(x))
				x = lnk.get(x);
			return x;
		}

		private	Object link_repr( Object x, Object y )
		{
			if( x == y )
				return -1;

			// グループ化
			if( lnkSiz.get(x) < lnkSiz.get(y) ) {
				lnk.put(x, y);
				lnkSiz.put(y, lnkSiz.get(y)+lnkSiz.get(x));
				return y;
			} else {
				lnk.put(y,x);
				lnkSiz.put(x, lnkSiz.get(x)+lnkSiz.get(y));
				return x;
			}
		}
	}
}