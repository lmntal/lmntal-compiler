package runtime;
import java.util.List;

/** システムルールセット
 * <p>todo インスタンスを誰が生成するのか決める
 */
final class SystemRuleset extends Ruleset {
	static InterpretedRuleset ruleset;
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
		//    1:$outside(A,B), 2:$outside(C,B), 4:{3:$inside(C,D), 5:$inside(A,E), $p,@p}, 
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
		insts.add(new Instruction(Instruction.UNIFY,       3,1,5,1,4));
		insts.add(new Instruction(Instruction.UNLOCKMEM,   4)); // ここでは子膜の活性化が必要
		insts.add(new Instruction(Instruction.PROCEED));
		ruleset.rules.add(rule);
		//ruleset.compile();
	}
}
