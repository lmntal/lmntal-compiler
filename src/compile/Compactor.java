/*
 * 作成日: 2004/10/25
 */
package compile;

import java.util.*;
import runtime.Instruction;
//import runtime.InstructionList;
//import runtime.Functor;
//import runtime.Env;

import runtime.Rule;

/**
 * 命令列の長さに関する最適化を行うクラスメソッドを持つクラス。開発時用。
 * RISC化された命令列の可読性を高めるために使うかもしれない。
 * RISC化テスト用のexpandメソッドも持つ。
 * 命令セットのRISC化に一役買う予定。
 * @author n-kato
 */
public class Compactor {
	/** ルールオブジェクトを最適化する（予定） */
	public static void compactRule(Rule rule) {
		Instruction spec = (Instruction)rule.bodyLabel.insts.get(0);
		int formals = spec.getIntArg1();
		int varcount = spec.getIntArg2();
		//varcount = expandBody(rule.body, varcount);	// 展開（RISC化）
		varcount = compactBody(rule.body, varcount);	// 圧縮 (CISC化）
		//genTest(rule);
		//varcount = renumberLocals(rule.body, varcount);	// 局所変数を振りなおす

		// todo: make use of InstructionList.updateLocals()
		spec.updateSpec(formals, varcount);
	}
	
	//（テスト命令列生成用） for f(X,Y):-X=Y
	static void genTest(Rule rule) {		
		 if (rule.body.size() > 6) {
			 HashMap map = new HashMap();
			 map.put(new Integer(2), new Integer(4));
			 Instruction.applyVarRewriteMap(rule.body, map);
			 map.clear();
			 map.put(new Integer(3), new Integer(2));
			 Instruction.applyVarRewriteMap(rule.body, map);
			 map.clear();
			 map.put(new Integer(4), new Integer(3));
			 Instruction.applyVarRewriteMap(rule.body, map);
		 }
	}
	
	/** ボディ命令列の命令をRISC化する */
	public static int expandBody(List body, int varcount) {
		int size = body.size();
		for (int i = 0; i < size; i++) {
			Instruction inst = (Instruction)body.get(i);
			int modify = (inst.getKind() >= Instruction.LOCAL ? Instruction.LOCAL : 0);
			switch (inst.getKind()) {
			// inheritlink [atom1,pos1,link2,mem1]
			// ==> alloclink[link1,atom1,pos1];unifylinks[link1,link2,mem1]
			case Instruction.LOCALINHERITLINK:
			case Instruction.INHERITLINK:
				body.remove(i);
				body.add(i,     new Instruction(Instruction.ALLOCLINK,
								varcount, inst.getIntArg1(), inst.getIntArg2() ));
				body.add(i + 1, new Instruction(Instruction.UNIFYLINKS + modify,
								varcount, inst.getIntArg3() ));
				if (inst.data.size() == 4) ((Instruction)body.get(i + 1)).data.add(inst.getArg4());
				varcount += 1;
				size += 1;
				i += 1;
				continue;

			// unify[atom1,pos1,atom2,pos2,mem1]
			// ==> getlink[link1,atom1,pos1];getlink[link2,atom2,pos2];unifylinks[link1,link2,mem1]
			case Instruction.LOCALUNIFY:
			case Instruction.UNIFY:
				body.remove(i);
				body.add(i,     new Instruction(Instruction.GETLINK,
								varcount, inst.getIntArg1(), inst.getIntArg2() ));
				body.add(i + 1, new Instruction(Instruction.GETLINK,
								varcount + 1, inst.getIntArg3(), inst.getIntArg4() ));
				body.add(i + 2, new Instruction(Instruction.UNIFYLINKS + modify,
								varcount, varcount + 1 ));
				if (inst.data.size() == 5) ((Instruction)body.get(i + 2)).data.add(inst.getArg5());
				varcount += 2;
				size += 2;
				i += 2;
				continue;

			// newlink[atom1,pos1,atom2,pos2,mem1]
			// ==> alloclink[link1,atom1,pos1];alloclink[link2,atom2,pos2];alloclinks[link1,link2,mem1]
			case Instruction.LOCALNEWLINK:
			case Instruction.NEWLINK:
				body.remove(i);
				body.add(i,     new Instruction(Instruction.ALLOCLINK,
								varcount, inst.getIntArg1(), inst.getIntArg2() ));
				body.add(i + 1, new Instruction(Instruction.ALLOCLINK,
								varcount + 1, inst.getIntArg3(), inst.getIntArg4() ));
				body.add(i + 2, new Instruction(Instruction.UNIFYLINKS + modify,
								varcount, varcount + 1 ));
				if (inst.data.size() == 5) ((Instruction)body.get(i + 2)).data.add(inst.getArg5());
				varcount += 2;
				size += 2;
				i += 2;
				continue;

			// relink[atom1,pos1,atom2,pos2,mem1]
			// ==> alloclink[link1,atom1,pos1];getlink[link2,atom2,pos2];unifylinks[link1,link2,mem1]
			case Instruction.LOCALRELINK:
			case Instruction.RELINK:
				body.remove(i);
				body.add(i,     new Instruction(Instruction.ALLOCLINK,
								varcount, inst.getIntArg1(), inst.getIntArg2() ));
				body.add(i + 1, new Instruction(Instruction.GETLINK,
								varcount + 1, inst.getIntArg3(), inst.getIntArg4() ));
				body.add(i + 2, new Instruction(Instruction.UNIFYLINKS + modify,
								varcount, varcount + 1 ));
				if (inst.data.size() == 5) ((Instruction)body.get(i + 2)).data.add(inst.getArg5());
				varcount += 2;
				size += 2;
				i += 2;
				continue;
			}
		}
		return varcount;
	}
	/** ボディ命令列の命令をCISC化する */
	public static int compactBody(List body, int varcount) {
		int size = body.size() - 2;
		for (int i = 0; i < size; i++) {
			Instruction inst;
			Instruction inst0 = (Instruction)body.get(i);
			Instruction inst1 = (Instruction)body.get(i + 1);
			Instruction inst2 = (Instruction)body.get(i + 2);

			// getlink[link1,atom1,pos1];getlink[link2,atom2,pos2];unifylinks[link1,link2,mem1]
			// ==> unify[atom1,pos1,atom2,pos2,mem1]
			if (inst0.getKind() == Instruction.GETLINK
			 && inst1.getKind() == Instruction.GETLINK
			 && inst2.getKind() == Instruction.UNIFYLINKS
			 && Instruction.getVarUseCountFrom(body, (Integer)inst0.getArg1(), i + 3) == 0
			 && Instruction.getVarUseCountFrom(body, (Integer)inst1.getArg1(), i + 3) == 0) {
				body.remove(i); body.remove(i); body.remove(i);
				inst = new Instruction(Instruction.UNIFY,
								inst0.getIntArg2(), inst0.getIntArg3(),
								inst1.getIntArg2(), inst1.getIntArg3() );
				body.add(i,inst);
				if (inst2.data.size() == 3) inst.add(inst2.getArg3());
				size -= 2;
				continue;
			}

			// alloclink[link1,atom1,pos1];alloclink[link2,atom2,pos2];unifylinks[link1,link2,mem1]
			// ==> newlink[atom1,pos1,atom2,pos2,mem1]
			if (inst0.getKind() == Instruction.ALLOCLINK
			 && inst1.getKind() == Instruction.ALLOCLINK
			 && inst2.getKind() == Instruction.UNIFYLINKS
			 && Instruction.getVarUseCountFrom(body, (Integer)inst0.getArg1(), i + 3) == 0
			 && Instruction.getVarUseCountFrom(body, (Integer)inst1.getArg1(), i + 3) == 0) {
				body.remove(i); body.remove(i); body.remove(i);
				inst = new Instruction(Instruction.NEWLINK,
								inst0.getIntArg2(), inst0.getIntArg3(),
								inst1.getIntArg2(), inst1.getIntArg3() );
				body.add(i,inst);
				if (inst2.data.size() == 3) inst.add(inst2.getArg3());
				size -= 2;
				continue;
			}

			// alloclink[link1,atom1,pos1];getlink[link2,atom2,pos2];unifylinks[link1,link2,mem1]
			// ==> relink[atom1,pos1,atom2,pos2,mem1]
			if (inst0.getKind() == Instruction.ALLOCLINK
			 && inst1.getKind() == Instruction.GETLINK
			 && inst2.getKind() == Instruction.UNIFYLINKS
			 && Instruction.getVarUseCountFrom(body, (Integer)inst0.getArg1(), i + 3) == 0
			 && Instruction.getVarUseCountFrom(body, (Integer)inst1.getArg1(), i + 3) == 0) {
				body.remove(i); body.remove(i); body.remove(i);
				inst = new Instruction(Instruction.RELINK,
								inst0.getIntArg2(), inst0.getIntArg3(),
								inst1.getIntArg2(), inst1.getIntArg3() );
				body.add(i,inst);
				if (inst2.data.size() == 3) inst.add(inst2.getArg3());
				size -= 2;
				continue;
			}

		}
		return varcount;
	}
	/** （ボディ命令列の）変数番号を振りなおす */
	public static int renumberLocals(List body, int varcount) {
		int size = body.size();
		Instruction spec = (Instruction)body.get(0);
		int locals = spec.getIntArg1();	// 最初の局所変数の番号は、仮引数の個数にする
		for (int i = 1; i < size; i++) {
			Instruction inst = (Instruction)body.get(i);
			if (inst.getOutputType() == -1) continue;
			if (inst.getIntArg1() != locals) {
				Integer src = (Integer)inst.getArg1();
				Integer dst = new Integer(locals);
				Integer tmp = new Integer(varcount);
				HashMap map1 = new HashMap();
				HashMap map2 = new HashMap();
				HashMap map3 = new HashMap();
				map1.put(src, tmp);
				map2.put(dst, src);
				map3.put(tmp, dst);
				Instruction.applyVarRewriteMapFrom(body,map1,i);
				Instruction.applyVarRewriteMapFrom(body,map2,i);
				Instruction.applyVarRewriteMapFrom(body,map3,i);
			}
			locals++;
		}
		return locals;
	}
}