package runtime;
import java.util.List;

/** システムルールセット
 * <p>todo インスタンスを誰が生成するのか決める</p>
 * 
 * システムルールセットのモジュール化：
 * system_ruleset アトムがある膜の直属のルールセットはシステムルールセットであることにする。
 * 
 * {system_ruleset, (a:-b)}, {{{{{{a}}}}}}    ==> {{{{{{b}}}}}} 
 * 
 * @author n-kato, hara
 */
public final class SystemRuleset extends Ruleset {
	public static InterpretedRuleset ruleset;
	public String toString() {
		return "System Ruleset Object";
	}
	/**
	 * アトム主導テストを行い、マッチすれば適用する
	 * @return ルールを適用した場合はtrue
	 */
	public boolean react(Membrane mem, Atom atom) {
		return false;
	}
	/**
	 * 膜主導テストを行い、マッチすれば適用する
	 * @return ルールを適用した場合はtrue
	 */
	public boolean react(Membrane mem) {
		return ruleset.react(mem);
	}
	/** システムルールセット（なぜか今はSystemRulesetオブジェクトではない）を返す */
	public static Ruleset getInstance() {
		return ruleset;
	}
	static {
		ruleset = new InterpretedRuleset();
		// === System Rule #1 ===
		//    1:$outside(B,A), 3:{2:$inside(B,C), 4:$inside(D,C), $p,@p}, 5:$outside(D,E)
		// :-                  3:{                                $p,@p}, A=E.
		Rule rule = new Rule();
		List insts = rule.memMatch;
		// match
		insts.add(new Instruction(Instruction.SPEC,        6,0));
		insts.add(new Instruction(Instruction.FINDATOM,  1,0,Functor.OUTSIDE_PROXY));
		insts.add(new Instruction(Instruction.DEREFATOM, 2,1,0));
		insts.add(new Instruction(Instruction.LOCKMEM,   3,2));
		insts.add(new Instruction(Instruction.DEREFATOM, 4,2,1));
		insts.add(new Instruction(Instruction.FUNC,        4,Functor.INSIDE_PROXY));
		insts.add(new Instruction(Instruction.DEREFATOM, 5,4,0));
		// react
		insts.add(new Instruction(Instruction.REMOVEATOM,  1,0,Functor.OUTSIDE_PROXY));
		insts.add(new Instruction(Instruction.REMOVEATOM,  2,3,Functor.INSIDE_PROXY));
		insts.add(new Instruction(Instruction.REMOVEATOM,  4,3,Functor.INSIDE_PROXY));
		insts.add(new Instruction(Instruction.REMOVEATOM,  5,0,Functor.OUTSIDE_PROXY));
		insts.add(new Instruction(Instruction.LOCALUNIFY,  1,1,5,1,0));
		insts.add(new Instruction(Instruction.UNLOCKMEM,   3)); // TODO 子膜を活性化しないようにする
		insts.add(new Instruction(Instruction.PROCEED));
		ruleset.rules.add(rule);
		//
		// === System Rule #2 ===
		//    1:$outside(A,B), 2:$outside(C,B), 4:{3:$inside(C,D), 5:$inside(A,E), $p,@p}
		// :-                                   4:{                          D=E,  $p,@p}.
		rule = new Rule();
		insts = rule.memMatch;
		// match		
		insts.add(new Instruction(Instruction.SPEC,        6,0));
		insts.add(new Instruction(Instruction.FINDATOM,  1,0,Functor.OUTSIDE_PROXY));
		insts.add(new Instruction(Instruction.DEREFATOM, 2,1,1));
		insts.add(new Instruction(Instruction.FUNC,        2,Functor.OUTSIDE_PROXY));
		insts.add(new Instruction(Instruction.DEREFATOM, 3,2,0));
		insts.add(new Instruction(Instruction.LOCKMEM,   4,3));
		insts.add(new Instruction(Instruction.DEREFATOM, 5,1,0));
		insts.add(new Instruction(Instruction.TESTMEM,     4,5));
		// react
		insts.add(new Instruction(Instruction.REMOVEATOM,  1,0,Functor.OUTSIDE_PROXY));
		insts.add(new Instruction(Instruction.REMOVEATOM,  2,0,Functor.OUTSIDE_PROXY));
		insts.add(new Instruction(Instruction.REMOVEATOM,  3,4,Functor.OUTSIDE_PROXY));
		insts.add(new Instruction(Instruction.REMOVEATOM,  5,4,Functor.OUTSIDE_PROXY));
		insts.add(new Instruction(Instruction.UNIFY,       3,1,5,1,4)); // memo: 本質的にリモートのUNIFY
		insts.add(new Instruction(Instruction.UNLOCKMEM,   4)); // ここでは子膜の活性化が必要
		insts.add(new Instruction(Instruction.PROCEED));
		ruleset.rules.add(rule);

		////////////////////////////////////////////////////////////
		//
		// ガードがコンパイルできるようになるまでの当分の間、
		// このタイミングで組み込みモジュールをロードさせてもらう。
		
		if (true) {
			loadBuiltInRules(ruleset);
		}			
		//ruleset.compile();
	}
	/**
	 * 仮メソッド。
	 * 指定されたInterpretedRulesetに対して、integerモジュール相当の内容を追加するために使用される。
	 * 【注意】整数演算を無効にするオプションがあってもよい。		
	 */
	static Rule buildBinOpRule(String name, int typechecker, int op) {
		// 1:'+'(X,Y,Res), 2:$x[X], 3:$y[Y] :- int($x),int($y), (4:$z)=$x+$y | 5:$z[Res].
		Rule rule = new Rule();
		List insts = rule.memMatch;
		// match		
		insts.add(new Instruction(Instruction.SPEC,        5,0));
		insts.add(new Instruction(Instruction.FINDATOM,  1,0,new Functor(name,3)));
		insts.add(new Instruction(Instruction.DEREFATOM, 2,1,0));
		insts.add(new Instruction(typechecker,             2));
		insts.add(new Instruction(Instruction.DEREFATOM, 3,1,1));
		insts.add(new Instruction(typechecker,             3));
		insts.add(new Instruction(op,                    4,2,3));
		// react
		insts.add(new Instruction(Instruction.DEQUEUEATOM, 1));
		insts.add(new Instruction(Instruction.REMOVEATOM,  1,0));
		insts.add(new Instruction(Instruction.REMOVEATOM,  2,0));
		insts.add(new Instruction(Instruction.REMOVEATOM,  3,0));
		insts.add(new Instruction(Instruction.LOCALADDATOM,  0,4));
		insts.add(new Instruction(Instruction.RELINK,        4,0,1,2));
		insts.add(new Instruction(Instruction.FREEATOM,      1));
		insts.add(new Instruction(Instruction.FREEATOM,      2));
		insts.add(new Instruction(Instruction.FREEATOM,      3));
		insts.add(new Instruction(Instruction.PROCEED));
		return rule;
	}
	/**
	 * 仮メソッド。
	 */
	static Rule buildUnaryOpRule(String name, int typechecker, int op) {
		// 1:float(X,Res), 2:$x[X] :- int($x), (3:$y)=float($x) | 4:$y[Res].
		Rule rule = new Rule();
		List insts = rule.memMatch;
		// match		
		insts.add(new Instruction(Instruction.SPEC,        4,0));
		insts.add(new Instruction(Instruction.FINDATOM,  1,0,new Functor(name,2)));
		insts.add(new Instruction(Instruction.DEREFATOM, 2,1,0));
		insts.add(new Instruction(typechecker,             2));
		insts.add(new Instruction(op,                    3,2));
		// react
		insts.add(new Instruction(Instruction.DEQUEUEATOM, 1));
		insts.add(new Instruction(Instruction.REMOVEATOM,  1,0));
		insts.add(new Instruction(Instruction.REMOVEATOM,  2,0));
		insts.add(new Instruction(Instruction.LOCALADDATOM,  0,3));
		insts.add(new Instruction(Instruction.RELINK,        3,0,1,1));
		insts.add(new Instruction(Instruction.FREEATOM,      1));
		insts.add(new Instruction(Instruction.FREEATOM,      2));
		insts.add(new Instruction(Instruction.PROCEED));
		return rule;
	}
	
	
	static void loadBuiltInRules(InterpretedRuleset ruleset) {
		Rule rule;
		List insts;		
		
		ruleset.rules.add(buildBinOpRule("+",	Instruction.ISINT,Instruction.IADD));
		ruleset.rules.add(buildBinOpRule("-",	Instruction.ISINT,Instruction.ISUB));
		ruleset.rules.add(buildBinOpRule("*",	Instruction.ISINT,Instruction.IMUL));
		ruleset.rules.add(buildBinOpRule("/",	Instruction.ISINT,Instruction.IDIV));
		ruleset.rules.add(buildBinOpRule("mod",	Instruction.ISINT,Instruction.IMOD));

		ruleset.rules.add(buildBinOpRule("+.",	Instruction.ISFLOAT,Instruction.FADD));
		ruleset.rules.add(buildBinOpRule("-.",	Instruction.ISFLOAT,Instruction.FSUB));
		ruleset.rules.add(buildBinOpRule("*.",	Instruction.ISFLOAT,Instruction.FMUL));
		ruleset.rules.add(buildBinOpRule("/.",	Instruction.ISFLOAT,Instruction.FDIV));

		ruleset.rules.add(buildUnaryOpRule("int",  Instruction.ISFLOAT,Instruction.FLOAT2INT));
		ruleset.rules.add(buildUnaryOpRule("float",Instruction.ISINT,  Instruction.INT2FLOAT));
		
		// 1:cp(A,B,C), 2:$n[A] :- unary($n) | $3:$n[B], $4:$n[C].
		// reuse { 2->4 }
		rule = new Rule();
		insts = rule.memMatch;
		// match		
		insts.add(new Instruction(Instruction.SPEC,        4,0));
		insts.add(new Instruction(Instruction.FINDATOM,  1,0,new Functor("cp",3)));
		insts.add(new Instruction(Instruction.DEREFATOM, 2,1,0));
		insts.add(new Instruction(Instruction.ISUNARY,     2));
		// react
		insts.add(new Instruction(Instruction.DEQUEUEATOM, 1));
		insts.add(new Instruction(Instruction.REMOVEATOM,  1,0));
		insts.add(new Instruction(Instruction.COPYATOM,  3,0,2));
		insts.add(new Instruction(Instruction.RELINK,    2,0,1,1));
		insts.add(new Instruction(Instruction.RELINK,    3,0,1,2));
		insts.add(new Instruction(Instruction.FREEATOM,    1));
		insts.add(new Instruction(Instruction.PROCEED));
		ruleset.rules.add(rule);
	}
}
