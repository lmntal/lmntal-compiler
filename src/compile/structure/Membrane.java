package compile.structure;

import java.util.*;
import runtime.InterpretedRuleset;

import runtime.Env;

/** 
 * ソースコード中の膜の構造を表すクラス
 * memo:全て1つの配列に入れる方法もある。
 * 各要素はListとして保持される。
 */
public final class Membrane {
	/** 
	 * 親膜 
	 */
	public Membrane mem = null;
	
	/**
	 * アトム(compile.structure.Atom)のリスト
	 */
	public List atoms = new ArrayList();

	/** 
	 * 子膜(compile.structure.Membrane)のリスト
	 */
	public List mems = new ArrayList();
	
	/**
	 * ルール(compile.structure.RuleStructure)のリスト
	 */
	public List rules = new ArrayList();
	
	/**
	 * プロセス変数(compile.structure.ProcessContext)のリスト
	 */
	public List processContexts = new ArrayList();
	
	/**
	 * ルール変数(compile.struct.RuleContext)のリスト
	 */
	public List ruleContexts = new ArrayList();
	
	/**
	 * 型付プロセス文脈(compile.struct.TypedProcessContext)のリスト
	 */
	public List typedProcessContexts = new ArrayList();
	
	/**
	 * 膜内の自由リンク(compile.struct.LinkOccurrence)へのポインタ
	 * TODO O(1)で自由リンクであるかどうかわかる形式にする Hashset?
	 */
	public List freeLinks = new ArrayList();
	
	/**
	 * ルールセット。生成されたルールオブジェクトは逐次ここに追加されていく。
	 */
	public runtime.Ruleset ruleset = new InterpretedRuleset();
	
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
	 *
	 */
	public void showAllRuleset() {
		Env.p( ((InterpretedRuleset)ruleset) );
		
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
		((InterpretedRuleset)ruleset).showDetail();
		
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
