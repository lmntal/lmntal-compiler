package compile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import runtime.Env;
import runtime.Instruction;
import runtime.InstructionList;
import runtime.MergedBranchMap;
import runtime.Rule;
import runtime.functor.Functor;
import runtime.functor.SymbolFunctor;
import util.Util;

/**
 * 編み上げを行うクラス
 * @author sakurai
 */
public class Merger {
	//アトム主導テスト部の命令列を編み上げるf
	//todo?　膜主導テスト部の編み上げ
	/** 編み上げ後のルールの実引数 */
	/** ファンクタ⇒命令列のマップ */
	HashMap<Functor, List<Instruction>> instsMap;
	/** 変数番号⇒ファンクタのマップ*/
	HashMap var2funcMap;
	int maxLocals;
	
	public Merger(){
		maxLocals = 0;
		instsMap = new HashMap<Functor, List<Instruction>>();
		var2funcMap = new HashMap();
	}
	
	public void clear(){
		maxLocals = 0;
		instsMap.clear();
		var2funcMap.clear();
	}
	/**
	 * 各ルール中のアトム主導テスト部に出現するbranch命令を編み上げ、
	 * ファンクタ⇒命令列のマップを生成する
	 * @param rules ルールセット内のルール群
	 * @return ファンクタ⇒命令列のマップ
	 */
	public MergedBranchMap Merging(List<Rule> rules, boolean system)
	{
		for (Rule rule : rules)
		{
			List<Instruction> atomMatch = rule.atomMatch;
			List<Instruction> guard = rule.guard;
			//if(Env.fTrace || Env.debugOption)(rule.body).add(1, new Instruction(Instruction.GETCURRENTRULE, rule));
			if(guard != null){
				//uniq命令がある場合は編み上げ中止。todo なんとかする
				for(int i=0; i<guard.size(); i++) {
					Instruction inst = guard.get(i);
					if(inst.getKind() == Instruction.UNIQ
						|| inst.getKind() == Instruction.NOT_UNIQ) return null;
				}
			}
			for(int i=0; i<atomMatch.size(); i++){
				Instruction inst = atomMatch.get(i);
				switch(inst.getKind()){
				case Instruction.SPEC:
					if(inst.getIntArg2() > maxLocals) maxLocals = inst.getIntArg2();
					break;
				case Instruction.BRANCH:
					InstructionList label = (InstructionList)inst.getArg1();
					List<Instruction> branchInsts = label.insts;
					maxLocals = 0;
					//uniq関係の応急処置
					for(int u=0; u<branchInsts.size(); u++) {
						Instruction uniq = branchInsts.get(u);
						if(uniq.getKind() == Instruction.UNIQ
							|| uniq.getKind() == Instruction.NOT_UNIQ) return null;
					}
					Functor func = null;
					for(int j=1; j<branchInsts.size(); j++){
						Instruction funcInst = branchInsts.get(j);
						if(funcInst.getKind() == Instruction.FUNC){
							func = (Functor)funcInst.getArg2();
							break;
						}
					}
					if(instsMap.containsKey(func)){ //先頭のfunc命令が同じbranch命令を探す
						List<Instruction> existInsts = instsMap.get(func);
						List<Instruction> mergedInsts = new ArrayList<Instruction>();
						mergedInsts = mergeInsts(branchInsts, existInsts);
						Instruction spec = Instruction.spec(2, maxLocals);
						mergedInsts.add(0, spec);
						instsMap.put(func, mergedInsts);
					}
					else {
						instsMap.put(func, branchInsts);
					}
					break;
				case Instruction.FUNC:
					//List insts2 = new ArrayList();
					//for(int j=i+1; j<atomMatch.size(); j++) insts2.add((Instruction)atomMatch.get(j));
					func = (Functor)inst.getArg2();
					if(instsMap.containsKey(func)){
						List<Instruction> existInsts = instsMap.get(func);
						List<Instruction> mergedInsts = new ArrayList<Instruction>();
						mergedInsts = mergeInsts(atomMatch, existInsts);
						Instruction spec = Instruction.spec(2, maxLocals);
						mergedInsts.add(0, spec);
						instsMap.put(func, mergedInsts);
					}
					else {
						instsMap.put(func, atomMatch);
					}
					break;
				default: break;
				}
			}
		}
		Set<Entry<Functor, List<Instruction>>> set = instsMap.entrySet();
		Iterator<Entry<Functor, List<Instruction>>> it2 = set.iterator();
		HashMap<Functor, List<Instruction>> optimizedmap= new HashMap<Functor, List<Instruction>>();
		while(it2.hasNext()){
			Entry<Functor, List<Instruction>> mapentry = it2.next();
			List<Instruction> insts = mapentry.getValue();
			//if(Optimizer.fGuardMove) Optimizer.guardMove(insts);
			//stackOrderChange(insts);
			optimizedmap.put(mapentry.getKey(), insts);
		}
		if(Env.debug >= 1 && !system) 	viewMap(optimizedmap);
		//viewMap(optimizedmap);
		return new MergedBranchMap(optimizedmap);
	}
	
	/**
	 * 生成したマップの表示　デバッグ用
	 * param map マップ
	 */
	private static void viewMap(HashMap<Functor, List<Instruction>> map){
		Set<Entry<Functor, List<Instruction>>> set = map.entrySet();
		Iterator<Entry<Functor, List<Instruction>>> it1 = set.iterator();
		while(it1.hasNext()){
			Entry<Functor, List<Instruction>> mapentry = it1.next();
			List<Instruction> branchinststest = mapentry.getValue();

			Util.println(mapentry.getKey() + " ⇒ ");
			viewInsts(branchinststest, 1);
			Util.println("");
		}
	}
	
	/**
	 * マップのvalueである命令列の表示 デバッグ用
	 * @param insts 命令列
	 * @param tabs 表示する際のタブ
	 */
	private static void viewInsts(List<Instruction> insts, int tabs){
		for(int i=0; i<insts.size(); i++){
			Instruction inst = insts.get(i);
			for(int j=0; j<tabs; j++) System.out.print("    ");
			//引数に命令列を持つ命令
			if(inst.getKind() == Instruction.BRANCH){
				Util.println("branch\t[");
				InstructionList label = (InstructionList) inst.getArg1();
				viewInsts(label.insts, tabs+1);
				for(int j=0; j<tabs; j++) System.out.print("    ");
				Util.println("]");
			}
			else Util.println(inst);
		}
	}
	
	/**
	 * 2つの命令列を比較し、共通部分をマージする
	 * @param insts1 1つ目の命令列
	 * @param insts2 2つ目の命令列
	 * @return 2つの命令列の共通部分
	 */
	private List<Instruction> mergeInsts(List<Instruction> insts1, List<Instruction> insts2){
		List<Instruction> mergedInsts = new ArrayList<Instruction>();
		int differenceIndex = insts1.size()+insts2.size();
		List<Instruction> branchInsts1 = new ArrayList<Instruction>();
		List<Instruction> branchInsts2 = new ArrayList<Instruction>();
		List<Instruction> branchInsts3 = new ArrayList<Instruction>();
		int formal = 0;
		int local = 0;
		for(int i=0, j=0; i<insts1.size() &&  j<insts2.size(); i++, j++){
			Instruction inst1 = insts1.get(i);
			Instruction inst2 = insts2.get(j);
			if(i==0 || j==0){
				if(inst1.getIntArg1() > inst2.getIntArg1())formal = inst1.getIntArg1();
				else formal = inst2.getIntArg1();
				if(inst1.getIntArg2() > inst2.getIntArg2())local = inst1.getIntArg2();
				else local = inst2.getIntArg2();
				if(inst1.getKind() == Instruction.SPEC && inst1.getIntArg2() > maxLocals){
					maxLocals = inst1.getIntArg2();
					continue;
				}
				if(inst2.getKind() == Instruction.SPEC && inst2.getIntArg2() > maxLocals){
					maxLocals = inst2.getIntArg2();
					continue;
				}
			}
			else if (inst1.equalsInst(inst2)){
				mergedInsts.add(inst1);
				continue;
			}
			else {
				if(inst2.getKind() == Instruction.BRANCH){
					//insts2のj番目以降はBRANNCH命令
					for(int k=j; k<insts2.size(); k++){
						Instruction instb = insts2.get(k);
						InstructionList label = (InstructionList)instb.getArg1();
						List<Instruction> instsb = label.insts;
						List<Instruction> tmpinsts = mergeInBranchInsts(insts1, i, instsb);
						if (tmpinsts != null){
							mergedInsts.addAll(branchInsts3);
							if(tmpinsts.size()>0)mergedInsts.add(new Instruction(Instruction.BRANCH, new InstructionList(tmpinsts)));
							//mergedInsts.addAll((ArrayList)tmpinsts);
							return mergedInsts;
						}
						else {branchInsts3.add(instb); continue;}
					}
					for(int k=j; k<insts2.size(); k++)
						mergedInsts.add(insts2.get(k));
					for(int k=i; k<insts1.size(); k++)
						branchInsts1.add(insts1.get(k));
					if (branchInsts1.size() > 0){
						Instruction spec = branchInsts1.get(0);
						if (spec.getKind() != Instruction.SPEC) branchInsts1.add(0, new Instruction(Instruction.SPEC, formal, local));
					}
					if(branchInsts1.size()>0)mergedInsts.add(new Instruction(Instruction.BRANCH, new InstructionList(branchInsts1)));
					return mergedInsts;
				}
				else {
					differenceIndex = i;
					break;
				}
			}
		}
		
		for(int i=differenceIndex; i<insts1.size(); i++)
			branchInsts1.add(insts1.get(i));
		for(int i=differenceIndex; i<insts2.size(); i++)
			branchInsts2.add(insts2.get(i));
		if (branchInsts1.size() > 0){
			Instruction inst1 = branchInsts1.get(0);
			if (inst1.getKind() != Instruction.SPEC) branchInsts1.add(0, new Instruction(Instruction.SPEC,formal, local));
		}
		if (branchInsts2.size() > 0){
			Instruction inst2 = branchInsts2.get(0);
			if (inst2.getKind() != Instruction.SPEC) branchInsts2.add(0, new Instruction(Instruction.SPEC,formal,local));
		}
		if(branchInsts2.size()>0)mergedInsts.add(new Instruction(Instruction.BRANCH, new InstructionList(branchInsts2)));
		if(branchInsts1.size()>0)mergedInsts.add(new Instruction(Instruction.BRANCH, new InstructionList(branchInsts1)));
		return mergedInsts;
	}
	
	/**
	 * BRANCH命令内の命令列とのマージ
	 * @param insts1 1つ目の命令列
	 * @param insts2 2つ目の命令列
	 * @return 2つの命令列の共通部分
	 */
	private List<Instruction> mergeInBranchInsts(List<Instruction> insts1, int index, List<Instruction> insts2){
		List<Instruction> mergedInsts = new ArrayList<Instruction>();
		int differenceIndex1 = insts1.size()+insts2.size();
		int differenceIndex2 = insts1.size()+insts2.size();
		List<Instruction> branchInsts1 = new ArrayList<Instruction>();
		List<Instruction> branchInsts2 = new ArrayList<Instruction>();
		List<Instruction> branchInsts3 = new ArrayList<Instruction>();
		Instruction spec = insts2.get(0);
		int formal = 0;
		int local = 0;
		if(spec.getKind() != Instruction.SPEC) return null;
		else {
			formal = spec.getIntArg1();
			local = spec.getIntArg2();
			if (spec.getIntArg2() > maxLocals) 	maxLocals = spec.getIntArg2();
		}
		
		int startsize = mergedInsts.size();	
		for(int i=index, j=1; i<insts1.size() && j<insts2.size(); i++, j++){
			Instruction inst1 = insts1.get(i);
			Instruction inst2 = insts2.get(j);
			if (inst1.equalsInst(inst2)){
				mergedInsts.add(inst1);
				continue;
			}
			else {
				if(inst2.getKind() == Instruction.BRANCH){
					//insts2のj番目以降はBRANNCH命令
					for(int k=j; k<insts2.size(); k++){
						Instruction instb = insts2.get(k);
						InstructionList label = (InstructionList)instb.getArg1();
						List<Instruction> instsb = label.insts;
						List<Instruction> tmpinsts = mergeInBranchInsts(insts1, i, instsb);
						if (tmpinsts != null){
							mergedInsts.addAll(branchInsts3);
							if(tmpinsts.size()>0)mergedInsts.add(new Instruction(Instruction.BRANCH, new InstructionList(tmpinsts)));
							//mergedInsts.addAll((ArrayList)tmpinsts);
							return mergedInsts;
						}
						else {branchInsts3.add(instb); continue;}
					}
					differenceIndex1 = i;
					differenceIndex2 = j;
					break;
				}
				else {
					differenceIndex1 = i;
					differenceIndex2 = j;
					break;
				}
			}
		}
		int endsize = mergedInsts.size();
		if (startsize == endsize) {
			return null;
		}
		for(int i=differenceIndex1; i<insts1.size(); i++)
			branchInsts1.add(insts1.get(i));
		for(int i=differenceIndex2; i<insts2.size(); i++)
			branchInsts2.add(insts2.get(i));
		if (branchInsts1.size() > 0){
			Instruction inst1 = branchInsts1.get(0);
			if (inst1.getKind() != Instruction.SPEC) branchInsts1.add(0, new Instruction(Instruction.SPEC, formal, local));
		}
		if (branchInsts2.size() > 0){
			Instruction inst2 = branchInsts2.get(0);
			if (inst2.getKind() != Instruction.SPEC) branchInsts2.add(0, new Instruction(Instruction.SPEC, formal, local));
		}
		if(branchInsts2.size()>0)mergedInsts.add(new Instruction(Instruction.BRANCH, new InstructionList(branchInsts2)));
		if(branchInsts1.size()>0)mergedInsts.add(new Instruction(Instruction.BRANCH, new InstructionList(branchInsts1)));
		
		return mergedInsts;
	}
	
	//システムルールセットのマップを生成する
	public MergedBranchMap createSystemRulesetsMap(){
		MergedBranchMap systemmbm;
		ArrayList<Rule> systemrules = new ArrayList<Rule>();
		
//		自由リンク管理アトム関連はとりあえず保留		
//		ArrayList insts = new ArrayList();
//		Rule rule = new Rule("proxy");
//		insts.add(new Instruction(Instruction.SPEC,        2,6));
//		insts.add(new Instruction(Instruction.FUNC,  1,Functor.OUTSIDE_PROXY));
//		insts.add(new Instruction(Instruction.TESTMEM, 0, 1));
//		insts.add(new Instruction(Instruction.DEREFATOM, 2,1,0));
//		insts.add(new Instruction(Instruction.LOCKMEM,   3,2));
//		insts.add(new Instruction(Instruction.DEREFATOM, 4,2,1));
//		insts.add(new Instruction(Instruction.FUNC,        4,Functor.INSIDE_PROXY));
//		insts.add(new Instruction(Instruction.DEREFATOM, 5,4,0));
//		insts.add(new Instruction(Instruction.COMMIT, rule.name, rule.lineno));
//		insts.add(new Instruction(Instruction.REMOVEATOM,  1,0,Functor.OUTSIDE_PROXY));
//		insts.add(new Instruction(Instruction.REMOVEATOM,  2,3,Functor.INSIDE_PROXY));
//		insts.add(new Instruction(Instruction.REMOVEATOM,  4,3,Functor.INSIDE_PROXY));
//		insts.add(new Instruction(Instruction.REMOVEATOM,  5,0,Functor.OUTSIDE_PROXY));
//		insts.add(new Instruction(Instruction.UNIFY,       1,1,5,1,0));
//		insts.add(new Instruction(Instruction.UNLOCKMEM,   3));
//		insts.add(new Instruction(Instruction.PROCEED));
//		rule.atomMatch = insts;
//		systemrules.add(rule);
//		
//		ArrayList insts2 = new ArrayList();
//		Rule rule2 = new Rule("proxy");
//		insts2.add(new Instruction(Instruction.SPEC,        2,6));
//		insts2.add(new Instruction(Instruction.FUNC,  1,Functor.OUTSIDE_PROXY));
//		insts2.add(new Instruction(Instruction.TESTMEM, 0, 1));
//		insts2.add(new Instruction(Instruction.DEREFATOM, 2,1,1));
//		insts2.add(new Instruction(Instruction.FUNC,        2,Functor.OUTSIDE_PROXY));
//		insts2.add(new Instruction(Instruction.DEREFATOM, 3,2,0));
//		insts2.add(new Instruction(Instruction.LOCKMEM,   4,3));
//		insts2.add(new Instruction(Instruction.DEREFATOM, 5,1,0));
//		insts2.add(new Instruction(Instruction.TESTMEM,     4,5));
//		insts2.add(new Instruction(Instruction.COMMIT, rule.name, rule.lineno));
//		insts2.add(new Instruction(Instruction.REMOVEATOM,  1,0,Functor.OUTSIDE_PROXY));
//		insts2.add(new Instruction(Instruction.REMOVEATOM,  2,0,Functor.OUTSIDE_PROXY));
//		insts2.add(new Instruction(Instruction.REMOVEATOM,  3,4,Functor.INSIDE_PROXY));
//		insts2.add(new Instruction(Instruction.REMOVEATOM,  5,4,Functor.INSIDE_PROXY));
//		insts2.add(new Instruction(Instruction.UNIFY,       3,1,5,1,4));
//		insts2.add(new Instruction(Instruction.ENQUEUEMEM,  4));
//		insts2.add(new Instruction(Instruction.UNLOCKMEM,   4));
//		insts2.add(new Instruction(Instruction.PROCEED));
//		rule2.atomMatch = insts2;
//		systemrules.add(rule2);
		
		systemrules.add(buildUnaryPlusOpRule("+", Instruction.ISINT));
		systemrules.add(buildUnaryPlusOpRule("+.", Instruction.ISFLOAT));
		systemrules.add(buildUnaryOpRule("-", Instruction.ISINT, Instruction.INEG));
		systemrules.add(buildUnaryOpRule("-.", Instruction.ISFLOAT, Instruction.FNEG));
		systemrules.add(buildUnaryOpRule("int", Instruction.ISFLOAT, Instruction.FLOAT2INT));
		systemrules.add(buildUnaryOpRule("float", Instruction.ISINT, Instruction.INT2FLOAT));
		
		systemrules.add(buildBinOpRule("+", Instruction.ISINT, Instruction.IADD));
		systemrules.add(buildBinOpRule("-", Instruction.ISINT, Instruction.ISUB));
		systemrules.add(buildBinOpRule("*", Instruction.ISINT, Instruction.IMUL));
		systemrules.add(buildBinOpRule("/", Instruction.ISINT, Instruction.IDIV));
		systemrules.add(buildBinOpRule("mod", Instruction.ISINT, Instruction.IMOD));
		systemrules.add(buildBinOpRule("+.", Instruction.ISFLOAT, Instruction.FADD));
		systemrules.add(buildBinOpRule("-.", Instruction.ISFLOAT, Instruction.FSUB));
		systemrules.add(buildBinOpRule("*.", Instruction.ISFLOAT, Instruction.FMUL));
		systemrules.add(buildBinOpRule("/.", Instruction.ISFLOAT, Instruction.FDIV));
		
//		Iterator it = (Iterator)systemrules.iterator();
//		while(it.hasNext()) System.out.println(it.next());
		systemmbm = Merging(systemrules, true);
//		viewMap(systemmbm.branchMap);
		return systemmbm;
		//return Merging(systemrules);
	}
	
	//システムルール生成用メソッド
	private Rule buildUnaryPlusOpRule(String name, int typechecker){
		Rule rule = new Rule(name);
		ArrayList<Instruction> insts =  new ArrayList<Instruction>();
		ArrayList<Instruction> insts2 = new ArrayList<Instruction>();

		insts.add(new Instruction(Instruction.SPEC,        2,5));
		insts.add(new Instruction(Instruction.FUNC,  1,new SymbolFunctor(name,2)));
		insts.add(new Instruction(Instruction.TESTMEM, 0, 1));
		insts.add(new Instruction(Instruction.DEREFATOM, 2,1,0));
		insts.add(new Instruction(typechecker,             2));
		insts.add(new Instruction(Instruction.GETFUNC,   4,2));
		insts.add(new Instruction(Instruction.ALLOCATOMINDIRECT, 3,4));

		ArrayList<Integer> mems = new ArrayList<Integer>();
		mems.add(new Integer(0));
		ArrayList<Integer> atoms = new ArrayList<Integer>();
		atoms.add(new Integer(1));
		atoms.add(new Integer(2));
		atoms.add(new Integer(3));

		insts2.add(new Instruction(Instruction.SPEC,        4,4));
		insts2.add(new Instruction(Instruction.COMMIT, rule.name, rule.lineno));
		insts2.add(new Instruction(Instruction.DEQUEUEATOM, 1));
		insts2.add(new Instruction(Instruction.REMOVEATOM,  1,0));
		insts2.add(new Instruction(Instruction.REMOVEATOM,  2,0));
		insts2.add(new Instruction(Instruction.ADDATOM,  0,3));
		insts2.add(new Instruction(Instruction.RELINK,        3,0,1,1,0));
		insts2.add(new Instruction(Instruction.FREEATOM,      1));
		insts2.add(new Instruction(Instruction.FREEATOM,      2));
		insts2.add(new Instruction(Instruction.PROCEED));
		
		rule.bodyLabel = new InstructionList(insts2); rule.body = rule.bodyLabel.insts;
		insts.add(Instruction.jump(rule.bodyLabel, mems, atoms, new ArrayList()));
		rule.atomMatch = insts;
		return rule;
	}
	
	private Rule buildUnaryOpRule(String name, int typechecker, int op){
		Rule rule = new Rule(name);
		ArrayList<Instruction> insts = new ArrayList<Instruction>();
		ArrayList<Instruction> insts2 = new ArrayList<Instruction>();

		insts.add(new Instruction(Instruction.SPEC,        2,5));
		insts.add(new Instruction(Instruction.FUNC,  1,new SymbolFunctor(name,2)));
		insts.add(new Instruction(Instruction.TESTMEM, 0, 1));
		insts.add(new Instruction(Instruction.DEREFATOM, 2,1,0));
		insts.add(new Instruction(typechecker,             2));
		insts.add(new Instruction(Instruction.GETFUNC,   4,2));
		insts.add(new Instruction(Instruction.ALLOCATOMINDIRECT, 3,4));

		ArrayList<Integer> mems = new ArrayList<Integer>();
		mems.add(new Integer(0));
		ArrayList<Integer> atoms = new ArrayList<Integer>();
		atoms.add(new Integer(1));
		atoms.add(new Integer(2));
		atoms.add(new Integer(3));
		
		insts2.add(new Instruction(Instruction.SPEC,        4,4));
		insts2.add(new Instruction(Instruction.COMMIT, rule.name, rule.lineno));
		insts2.add(new Instruction(Instruction.DEQUEUEATOM, 1));
		insts2.add(new Instruction(Instruction.REMOVEATOM,  1,0));
		insts2.add(new Instruction(Instruction.REMOVEATOM,  2,0));
		insts2.add(new Instruction(Instruction.ADDATOM,  0,3));
		insts2.add(new Instruction(Instruction.RELINK,        3,0,1,1,0));
		insts2.add(new Instruction(Instruction.FREEATOM,      1));
		insts2.add(new Instruction(Instruction.FREEATOM,      2));
		insts2.add(new Instruction(Instruction.PROCEED));
		
		rule.bodyLabel = new InstructionList(insts2); rule.body = rule.bodyLabel.insts;
		insts.add(Instruction.jump(rule.bodyLabel, mems, atoms, new ArrayList()));
		rule.atomMatch = insts;
		return rule;
	}
	
	private Rule buildBinOpRule(String name, int typechecker, int op){
		Rule rule = new Rule(name);
		ArrayList<Instruction> insts = new ArrayList<Instruction>();
		ArrayList<Instruction> insts2 = new ArrayList<Instruction>();

		insts.add(new Instruction(Instruction.SPEC,        2,5));
		insts.add(new Instruction(Instruction.FUNC,  1,new SymbolFunctor(name,3)));
		insts.add(new Instruction(Instruction.TESTMEM, 0, 1));
		insts.add(new Instruction(Instruction.DEREFATOM, 2,1,0));
		insts.add(new Instruction(typechecker,             2));
		insts.add(new Instruction(Instruction.DEREFATOM, 3,1,1));
		insts.add(new Instruction(typechecker,             3));
		insts.add(new Instruction(op,                    4,2,3));
		ArrayList<Integer> mems = new ArrayList<Integer>();
		mems.add(new Integer(0));
		ArrayList<Integer> atoms = new ArrayList<Integer>();
		atoms.add(new Integer(1));
		atoms.add(new Integer(2));
		atoms.add(new Integer(3));
		atoms.add(new Integer(4));
		
		insts2.add(new Instruction(Instruction.SPEC,        5,5));
		insts2.add(new Instruction(Instruction.COMMIT, rule.name, rule.lineno));
		insts2.add(new Instruction(Instruction.DEQUEUEATOM, 1));
		insts2.add(new Instruction(Instruction.REMOVEATOM,  1,0));
		insts2.add(new Instruction(Instruction.REMOVEATOM,  2,0));
		insts2.add(new Instruction(Instruction.REMOVEATOM,  3,0));
		insts2.add(new Instruction(Instruction.ADDATOM,  0,4));
		insts2.add(new Instruction(Instruction.RELINK,        4,0,1,2,0));
		insts2.add(new Instruction(Instruction.FREEATOM,      1));
		insts2.add(new Instruction(Instruction.FREEATOM,      2));
		insts2.add(new Instruction(Instruction.FREEATOM,      3));
		insts2.add(new Instruction(Instruction.PROCEED));
		
		rule.bodyLabel = new InstructionList(insts2); rule.body = rule.bodyLabel.insts;
		insts.add(Instruction.jump(rule.bodyLabel, mems, atoms, new ArrayList()));
		rule.atomMatch = insts;
		return rule;
	}	
}
