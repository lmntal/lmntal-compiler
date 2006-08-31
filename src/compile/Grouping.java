package compile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import runtime.Instruction;
import runtime.InstructionList;

/**
 * 命令列のグループ化をを行うクラス。
 * @author sakurai
 */
public class Grouping {
	/** 変数番号⇒変数番号を定義した命令のマップ */
	private HashMap var2DefInst;
	/** 命令⇒グループ識別番号のマップ */
	private HashMap Inst2GroupId;
	
	Grouping(){
		var2DefInst = new HashMap();
		Inst2GroupId = new HashMap();
	}
	
	/*
	 * マップの初期化
	 */
	private void initMap(){
		var2DefInst.clear();
		Inst2GroupId.clear();
	}
	
	/** 命令列のグループ化
	 *  アトム主導テスト部と膜主導テスト部で操作を分ける。
	 *  @atom アトム主導テスト部の命令列
	 *  @param 膜主導テスト部の命令列
	 * */
	public void grouping(List atom, List mem){
		//アトム主導テスト部
		groupingInstsForAtomMatch(atom);
		//膜主導テスト部
		groupingInsts(mem, false);
	}
	
	/** 命令列のグループ化 
	 * @param insts グループ化する命令列
	 * @param isAtomMatch instsがアトム主導テスト部かどうかのフラグ
	 * */
	public void groupingInsts(List insts, boolean isAtomMatch){
		if(((Instruction)insts.get(0)).getKind() != Instruction.SPEC) return;
		for(int i=1; i<insts.size(); i++){
			Instruction inst = (Instruction)insts.get(i);
			//否定条件がある場合はグループ化は無効 暫定的処置
			if(inst.getKind() == Instruction.NOT) return;
			//if (inst.getKind() == Instruction.COMMIT) break;
			if(inst.getKind() == Instruction.COMMIT) continue;
			if (inst.getKind() == Instruction.JUMP) continue;
			//グループ番号を割り振る	  
			Inst2GroupId.put(inst, new Integer(i));
			//変数番号→命令にマップを張る
			//System.out.println("instruction = "+inst);
			if(inst.getOutputType() != -1)
				var2DefInst.put(inst.getArg1(), inst);
		}
		//viewMap();
		createGroup(insts, isAtomMatch);
		initMap();
	}
	
	/** 命令列のグループ化 アトム主導テスト用 
	 * @param insts グループ化するアトム主導テスト部の命令列
	 * */
	public void groupingInstsForAtomMatch(List insts){
		if(((Instruction)insts.get(0)).getKind() != Instruction.SPEC) return;
		for(int i=1; i<insts.size(); i++){
			Instruction branch = (Instruction)insts.get(i);
			//if(branch.getKind() == Instruction.COMMIT) break;
			if(branch.getKind() == Instruction.BRANCH){
				InstructionList subinsts = (InstructionList)branch.getArg1();
				groupingInsts(subinsts.insts, true);
			}
		}	
	}	

	/**
	 * マップに基づいてグループを生成する。
	 * var2DefInstを参照し、グループ識別番号が同じ命令を同じグループとする。 
	 * @param insts 命令列
	 * @param isAtomMatch instsがアトム主導テスト部かどうかのフラグ
	 * */
	private void createGroup(List insts, boolean isAtomMatch){
		//マップの書き換え
		for(int i=1; i<insts.size(); i++){
			Instruction inst = (Instruction)insts.get(i);
			if(inst.getKind() == Instruction.COMMIT
				|| inst.getKind() == Instruction.JUMP) break;
			Object group = null;
			Object changegroup = null;
			ArrayList list = inst.getVarArgs();
			if(list.isEmpty()) continue;
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j).equals(new Integer(0))) continue;
				if (list.get(j).equals(new Integer(1)) && isAtomMatch) continue;
				group = Inst2GroupId.get(var2DefInst.get(list.get(j)));
				changegroup = Inst2GroupId.get(inst);
				changeMap(changegroup, group);
			}
			if(inst.getKind() == Instruction.ANYMEM
			  || inst.getKind() == Instruction.LOCKMEM){
				//膜を取得する命令は全て同じグループに属すことにする
				//これも暫定的措置
				//{a}, {b, $p}, {c} ･･･これらは実際は違うグループ
				//{a(X)}, {a(Y)} ･･･これらは同じグループ
				//todo どう区別するか考える
				for(int i2 = 1; i2 < insts.size(); i2++){
					Instruction inst2 = (Instruction)insts.get(i2);
					switch(inst2.getKind()){
					case Instruction.COMMIT:
					case Instruction.JUMP:
						break;
					case Instruction.ANYMEM:
					case Instruction.LOCKMEM:
						group = Inst2GroupId.get(inst);
						changegroup = Inst2GroupId.get(inst);
						changeMap(changegroup, group);
						break;
					default : break;
					}
				}
			}
		}
		
		//マップ書き換え終了
		//GROUP生成
		for(int i=1; i<insts.size(); i++){
			Instruction inst = (Instruction)insts.get(i);
			if(inst.getKind() == Instruction.COMMIT
				|| inst.getKind() == Instruction.JUMP) break;
			Object group = Inst2GroupId.get(inst);
			InstructionList subinsts = new InstructionList();
			subinsts.add(new Instruction(Instruction.SPEC, 0, 0));
			for(int i2=i; i2<insts.size(); i2++){
				Instruction inst2 = (Instruction)insts.get(i2);
				if(inst2.getKind() == Instruction.COMMIT
					|| inst.getKind() == Instruction.JUMP) break;
				if(group.equals(Inst2GroupId.get(inst2))){
					subinsts.add(inst2);
					insts.remove(i2);
					i2 -= 1;
				}
			}
			subinsts.add(new Instruction(Instruction.PROCEED));
			insts.add(i, new Instruction(Instruction.GROUP, subinsts));
		}
		//viewMap();
	}

	/**
	 * マップInst2GroupIdの書き換え
	 * マップの内、値 group1 を持つ全ての要素を、値 group2 に書き換える
	 * @param group1 書き換え前の値
	 * @param group2 書き換え後の値
	 * */
	//Inst2GroupIdの内, 値group1をもつ全てのキーに対し, 値group2へマップを張り替える.
	public void changeMap(Object group1, Object group2){
		Iterator it = Inst2GroupId.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			if (group1.equals(Inst2GroupId.get(key))) {
				Inst2GroupId.put(key, group2);
			}
		}
	}
		
	//生成されたマップの確認 デバッグ用
	public void viewMap(){
		Set set1 = var2DefInst.entrySet();
		Set set2 = Inst2GroupId.entrySet();

		Iterator it1 = set1.iterator();
		Iterator it2 = set2.iterator();
	
		System.out.println("var2DefInst :- ");
		while(it1.hasNext()){
			Map.Entry mapentry = (Map.Entry)it1.next();
			System.out.println(mapentry.getKey() + "/" + mapentry.getValue());
		}
			System.out.println("Inst2GroupId :- ");
		while(it2.hasNext()){
			Map.Entry mapentry = (Map.Entry)it2.next();
			System.out.println(mapentry.getKey() + "/" + mapentry.getValue());
		}	
	}
}