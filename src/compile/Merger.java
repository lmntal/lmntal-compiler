package compile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import runtime.Env;
import runtime.Functor;
import runtime.Instruction;
import runtime.InstructionList;
import runtime.MergedBranchMap;
import runtime.Rule;

/**
 * 編み上げを行うクラス
 * @author sakurai
 */
public class Merger {
	//アトム主導テスト部の命令列を編み上げる
	//todo?　膜主導テスト部の編み上げ
	/** 編み上げ後のルールの実引数 */
	int maxLocals;
	/** ファンクタ⇒命令列のマップ */
	HashMap instsMap;
	
	public Merger(){
		maxLocals = 0;
		instsMap = new HashMap();
	}
	
	/**
	 * 各ルール中のアトム主導テスト部に出現するbranch命令を編み上げ、
	 * ファンクタ⇒命令列のマップを生成する
	 * @param rules ルールセット内のルール群
	 * @return ファンクタ⇒命令列のマップ
	 */
	public MergedBranchMap Merging(ArrayList rules){
		Iterator it = rules.iterator();
		while(it.hasNext()){
			Rule rule = (Rule)it.next();
			List atomMatch = (ArrayList)rule.atomMatch;
			for(int i=0; i<atomMatch.size(); i++){
				Instruction inst = (Instruction)atomMatch.get(i);
				switch(inst.getKind()){
				case Instruction.SPEC:
					if(inst.getIntArg2() > maxLocals) maxLocals = inst.getIntArg2();
					break;
				case Instruction.BRANCH:
					InstructionList label = (InstructionList)inst.getArg1();
					List branchInsts = label.insts;
					Functor func = null;
					for(int j=1; j<branchInsts.size(); j++){
						Instruction funcInst = (Instruction)branchInsts.get(j);
						if(funcInst.getKind() == Instruction.FUNC){
							func = (Functor)funcInst.getArg2();
							break;
						}
					}
					if(instsMap.containsKey(func)){ //先頭のfunc命令が同じbranch命令を探す
						List existInsts = (ArrayList)instsMap.get(func);
						List mergedInsts = new ArrayList();
						mergedInsts = mergeInsts(branchInsts, existInsts);
						Instruction spec = Instruction.spec(2, maxLocals);
						mergedInsts.add(0, spec);
						instsMap.put(func, mergedInsts);
					}
					else {
						instsMap.put(func, branchInsts);
					}
					break;
				default: break;
				}
			}
		}
		if(Env.debug >= 1) viewMap(instsMap);
		return new MergedBranchMap(instsMap);
	}
	
	/**
	 * 生成したマップの表示　デバッグ用
	 * param map マップ
	 */
	private void viewMap(HashMap map){
		Set set = map.entrySet();
		Iterator it1 = set.iterator();
		while(it1.hasNext()){
			Map.Entry mapentry = (Map.Entry)it1.next();
			ArrayList branchinststest = (ArrayList)mapentry.getValue();

			System.out.println(mapentry.getKey() + " ⇒ ");
			viewInsts(branchinststest, 1);
			System.out.println("");
		}
	}
	
	/**
	 * マップのvalueである命令列の表示 デバッグ用
	 * @param insts 命令列
	 * @param tabs 表示する際のタブ
	 */
	private void viewInsts(ArrayList insts, int tabs){
		for(int i=0; i<insts.size(); i++){
			Instruction inst = (Instruction)insts.get(i);
			for(int j=0; j<tabs; j++) System.out.print("    ");
			//引数に命令列を持つ命令
			if(inst.getKind() == Instruction.BRANCH){
				System.out.println("branch\t[");
				InstructionList label = (InstructionList) inst.getArg1();
				viewInsts((ArrayList)label.insts, tabs+1);
				for(int j=0; j<tabs; j++) System.out.print("    ");
				System.out.println("]");
			}
			else System.out.println(inst);
		}
	}
	
	/**
	 * 2つの命令列を比較し、共通部分をマージする
	 * @param insts1 1つ目の命令列
	 * @param insts2 2つ目の命令列
	 * @return 2つの命令列の共通部分
	 */
	private ArrayList mergeInsts(List insts1, List insts2){
		List mergedInsts = new ArrayList();
		int differenceIndex = insts1.size()+insts2.size();
		List branchInsts1 = new ArrayList();
		List branchInsts2 = new ArrayList();
		for(int i=0, j=0; i<insts1.size() &&  j<insts2.size(); i++, j++){
			Instruction inst1 = (Instruction)insts1.get(i);
			Instruction inst2 = (Instruction)insts2.get(j);
			if(i==0 || j==0){
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
						Instruction instb = (Instruction)insts2.get(k);
						InstructionList label = (InstructionList)instb.getArg1();
						List instsb = label.insts;
						List tmpinsts = mergeInBranchInsts(insts1, i, instsb);
						if (tmpinsts != null){
							mergedInsts.addAll((ArrayList)tmpinsts);
							return (ArrayList)mergedInsts;
						}
						else continue;
					}
					for(int k=j; k<insts2.size(); k++)
						mergedInsts.add(insts2.get(k));
					for(int k=i; k<insts1.size(); k++)
						branchInsts1.add((Instruction)insts1.get(k));
					if (branchInsts1.size() > 0){
						Instruction spec = (Instruction)branchInsts1.get(0);
						if (spec.getKind() != Instruction.SPEC) branchInsts1.add(0, new Instruction(Instruction.SPEC,0,0));
					}
					mergedInsts.add(new Instruction(Instruction.BRANCH, new InstructionList((ArrayList)branchInsts1)));
					return (ArrayList)mergedInsts;
				}
				else {
					differenceIndex = i;
					break;
				}
			}
		}
		
		for(int i=differenceIndex; i<insts1.size(); i++)
			branchInsts1.add((Instruction)insts1.get(i));
		for(int i=differenceIndex; i<insts2.size(); i++)
			branchInsts2.add((Instruction)insts2.get(i));
		if (branchInsts1.size() > 0){
			Instruction inst1 = (Instruction)branchInsts1.get(0);
			if (inst1.getKind() != Instruction.SPEC) branchInsts1.add(0, new Instruction(Instruction.SPEC,0,0));
		}
		if (branchInsts2.size() > 0){
			Instruction inst2 = (Instruction)branchInsts2.get(0);
			if (inst2.getKind() != Instruction.SPEC) branchInsts2.add(0, new Instruction(Instruction.SPEC,0,0));
		}
		mergedInsts.add(new Instruction(Instruction.BRANCH, new InstructionList((ArrayList)branchInsts2)));
		mergedInsts.add(new Instruction(Instruction.BRANCH, new InstructionList((ArrayList)branchInsts1)));
		return (ArrayList)mergedInsts;
	}
	
	/**
	 * BRANCH命令内の命令列とのマージ
	 * @param insts1 1つ目の命令列
	 * @param insts2 2つ目の命令列
	 * @return 2つの命令列の共通部分
	 */
	private ArrayList mergeInBranchInsts(List insts1, int index, List insts2){
		List mergedInsts = new ArrayList();
		int differenceIndex1 = insts1.size()+insts2.size();
		int differenceIndex2 = insts1.size()+insts2.size();
		List branchInsts1 = new ArrayList();
		List branchInsts2 = new ArrayList();
		Instruction spec = (Instruction)insts2.get(0);
		if(spec.getKind() != Instruction.SPEC) return null;
		else if (spec.getIntArg2() > maxLocals) maxLocals = spec.getIntArg2();
		
		int startsize = mergedInsts.size();	
		for(int i=index, j=1; i<insts1.size() && j<insts2.size(); i++, j++){
			Instruction inst1 = (Instruction)insts1.get(i);
			Instruction inst2 = (Instruction)insts2.get(j);
			if (inst1.equalsInst(inst2)){
				mergedInsts.add(inst1);
				continue;
			}
			else {
				if(inst2.getKind() == Instruction.BRANCH){
					//insts2のj番目以降はBRANNCH命令
					for(int k=j; k<insts2.size(); k++){
						Instruction instb = (Instruction)insts2.get(k);
						InstructionList label = (InstructionList)instb.getArg1();
						List instsb = label.insts;
						List tmpinsts = mergeInBranchInsts(insts1, i, instsb);
						if (tmpinsts != null){
							mergedInsts.addAll((ArrayList)tmpinsts);
							return (ArrayList)mergedInsts;
						}
						else continue;
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
			branchInsts1.add((Instruction)insts1.get(i));
		for(int i=differenceIndex2; i<insts2.size(); i++)
			branchInsts2.add((Instruction)insts2.get(i));
		if (branchInsts1.size() > 0){
			Instruction inst1 = (Instruction)branchInsts1.get(0);
			if (inst1.getKind() != Instruction.SPEC) branchInsts1.add(0, new Instruction(Instruction.SPEC,0,0));
		}
		if (branchInsts2.size() > 0){
			Instruction inst2 = (Instruction)branchInsts2.get(0);
			if (inst2.getKind() != Instruction.SPEC) branchInsts2.add(0, new Instruction(Instruction.SPEC,0,0));
		}
		mergedInsts.add(new Instruction(Instruction.BRANCH, new InstructionList((ArrayList)branchInsts2)));
		mergedInsts.add(new Instruction(Instruction.BRANCH, new InstructionList((ArrayList)branchInsts1)));
		
		return (ArrayList)mergedInsts;
	}
}
