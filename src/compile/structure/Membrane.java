package compile.structure;

import java.util.ArrayList;
import java.util.List;

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
	Membrane mem;
	
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
}
