package compile.structure;

import java.util.*;
import runtime.InterpretedRuleset;

import runtime.Env;

/** 
 * ソースコード中の膜の構造を表すクラス<br>
 * memo:全て1つの配列に入れる方法もある。<br>
 * 各要素はListとして保持される。
 */
public final class Membrane {
	/** 親膜 <p> todo いずれmemはparentに名称変更する */
	public Membrane mem = null;
	
	/** アトム(compile.structure.Atom)のリスト */
	public List atoms = new ArrayList();

	/** 子膜(compile.structure.Membrane)のリスト */
	public List mems = new ArrayList();
	
	/** ルール(compile.structure.RuleStructure)のリスト */
	public List rules = new ArrayList();

	////////////////////////////////////////////////////////////////

	/** アトム集団(compile.structure.Atom)のリスト */
	public List aggregates = new ArrayList();

	/** プロセス文脈出現(compile.structure.ProcessContext)のリスト */
	public List processContexts = new ArrayList();
	
	/** ルール文脈出現(compile.struct.RuleContext)のリスト */
	public List ruleContexts = new ArrayList();
	
	/** 型付きプロセス文脈出現(compile.struct.ProcessContext)のリスト */
	public List typedProcessContexts = new ArrayList();
	
	////////////////////////////////////////////////////////////////
	
	/** 膜の自由リンク名(String)からそのリンク出現(compile.struct.LinkOccurrence)への写像 */
	public HashMap freeLinks = new HashMap();
	
	/** ルールセット。生成されたルールオブジェクトは逐次ここに追加されていく。*/
//	public runtime.Ruleset ruleset = new InterpretedRuleset();
	public List rulesets = new ArrayList();
	 	
	public String name;
	
	////////////////////////////////////////////////////////////////

	/**
	 * コンストラクタ
	 * @param mem 親膜
	 */
	public Membrane(Membrane mem) {
		this.mem = mem;
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
		return 
		(atoms.isEmpty() ? "" : ""+Env.parray(atoms))+
		(mems.isEmpty() ? "" : " "+Env.parray(mems))+
		(rules.isEmpty() ? "" : " "+Env.parray(rules))+
		(processContexts.isEmpty() ? "" : " "+Env.parray(processContexts))+
		(ruleContexts.isEmpty() ? "" : " "+Env.parray(ruleContexts))+
		(typedProcessContexts.isEmpty() ? "" : " "+Env.parray(typedProcessContexts))+
		"";
		
	}
	public String toString() {
		return "{ " + toStringWithoutBrace() + " }";
	}
	
	/**
	 * この膜に含まれる全てのルールセットを表示する。
	 */
	public void showAllRuleset() {
		Iterator it = rulesets.iterator();
		while (it.hasNext()) {
			Env.d( ((InterpretedRuleset)it.next()) );
		}
		
		Iterator l;
		
		// 直属のルールそれぞれについて、その左辺膜と右辺膜のルールセットを表示
		l = rules.listIterator();
		while(l.hasNext()) {
			RuleStructure rs = (RuleStructure)l.next();
			rs.leftMem.showAllRuleset();
			rs.rightMem.showAllRuleset();
		} 
		// 子膜それぞれ
		l = mems.listIterator();
		while(l.hasNext()) ((Membrane)l.next()).showAllRuleset();
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
	public void showAllRule() {
		Env.c("Membrane.showAllRule mem="+this);
		Iterator it = rulesets.iterator();
		while (it.hasNext()) {
			((InterpretedRuleset)it.next()).showDetail();
		}
		
		Iterator l;
		
		// 直属のルールそれぞれについて、その左辺膜と右辺膜のルールセットを表示
		l = rules.listIterator();
		while(l.hasNext()) {
			RuleStructure rs = (RuleStructure)l.next();
			//Env.p("");
			//Env.p("About rule structure (LEFT): "+rs.leftMem+" of "+rs);
			rs.leftMem.showAllRule();
			//Env.p("About rule structure (LEFT): "+rs.rightMem+" of "+rs);
			rs.rightMem.showAllRule();
		}
		// 子膜それぞれ
		l = mems.listIterator();
		while(l.hasNext()) ((Membrane)l.next()).showAllRule();
	}
}
