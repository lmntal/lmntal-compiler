package compile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import runtime.Instruction;
import runtime.InstructionList;
import util.Util;

/**
 * 命令列のグループ化をを行うクラス。
 * @author sakurai
 */
public class Grouping {
	/** 変数番号⇒変数番号を定義した命令のマップ */
	private HashMap var2DefInst;
	/** 命令⇒グループ識別番号のマップ */
	private HashMap<Instruction,Integer> Inst2GroupId;
	/** グループ命令内の計算コスト*/
	private HashMap<Instruction,Cost> group2Cost;
	
	Instruction spec;
	Grouping(){
		var2DefInst = new HashMap();
		Inst2GroupId = new HashMap<>();
		group2Cost = new HashMap<>();
		spec = null;
	}
	
	/*
	 * マップの初期化
	 */
	private void initMap(){
		var2DefInst.clear();
		Inst2GroupId.clear();
	}
	
	/** 命令列のグループ化
	 *  @param mem 膜主導テスト部の命令列
	 * */
	public void grouping(List<Instruction> mem){
		//膜主導テスト部
		groupingInsts(mem);
	}
	
	/** 命令列のグループ化 
	 * @param insts グループ化する命令列
	 * */
	public void groupingInsts(List<Instruction> insts){
		if(insts.get(0).getKind() != Instruction.SPEC) return;
		spec = insts.get(0);
		int last = -1;
		for(int i=1; i<insts.size(); i++){
			Instruction inst = insts.get(i);
			//否定条件がある場合はグループ化は無効 暫定的処置
			if(inst.getKind() == Instruction.NOT) return;
			//if (inst.getKind() == Instruction.COMMIT) break;
			if(inst.getKind() == Instruction.COMMIT) continue;
			if (inst.getKind() == Instruction.JUMP) continue;
			//グループ番号を割り振る	  
			Inst2GroupId.put(inst, i);
			//変数番号→命令にマップを張る
			//System.out.println("instruction = "+inst);
			if(inst.getOutputType() != -1)
				var2DefInst.put(inst.getArg1(), inst);
			last = i+1;
		}
		//viewMap();
		createGroup(insts);
		initMap();
	}


	/**
	 * マップに基づいてグループを生成する。
	 * var2DefInstを参照し、グループ識別番号が同じ命令を同じグループとする。 
	 * @param insts 命令列
	 * */
	private void createGroup(List<Instruction> insts){
		//マップの書き換え
		for(int i=1; i<insts.size(); i++){
			Instruction inst = insts.get(i);
			if(inst.getKind() == Instruction.COMMIT
				|| inst.getKind() == Instruction.JUMP) break;
			Integer group = null;
			Integer changegroup = null;
			ArrayList list = inst.getVarArgs(new HashMap());
			if(list.isEmpty()) continue;
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j).equals(0)) continue;
				//if (list.get(j).equals(new Integer(1)) && isAtomMatch) continue;
				group = Inst2GroupId.get(var2DefInst.get(list.get(j)));
				changegroup = Inst2GroupId.get(inst);
				changeMap(changegroup, group);
			}
		}
		for(int i=1; i<insts.size(); i++){
			Instruction inst = insts.get(i);
			if(inst.getKind() == Instruction.COMMIT
				|| inst.getKind() == Instruction.JUMP) break;
			Integer group = null;
			Integer changegroup = null;
			boolean norules = false;
			boolean meminsttype = false; //anymem -> true  lockmem -> false
			int natoms = -1;
			int nmems = -1;
			if(inst.getKind() == Instruction.ANYMEM
					  || inst.getKind() == Instruction.LOCKMEM){
						//膜をグループ分け
						//ルールの有無による区別 {a, $p, @p} と {a, $p}は違うグループ
						//アトム, 子膜の数による区別 {a, b} と {a, a, b, b} は違うグループ
						//今のところ次の場合は区別できないので同じグループとする
						//{a(X), b(X)}, {a(Y)}, b(Y) ・・・これらは違うグループ
						//$in, $out の数で区別可能?
						//todo どう区別するか考える
				if(inst.getKind() == Instruction.ANYMEM) meminsttype = true;
				else meminsttype = false;
						for(int i2 = i+1; i2 < insts.size(); i2++){
							Instruction inst2 = insts.get(i2);
							switch(inst2.getKind()){
							case Instruction.COMMIT:
							case Instruction.JUMP:
								break;
							case Instruction.NORULES:
								if((inst2.getArg1()).equals(inst.getArg1())) norules = true;
								break;
							case Instruction.NATOMS:
								if((inst2.getArg1()).equals(inst.getArg1())) natoms = inst2.getIntArg2();
								break;
							case Instruction.NMEMS:
								if((inst2.getArg1()).equals(inst.getArg1())) nmems = inst2.getIntArg2();
								break;
							default : break;
							}
						}
						for(int j = i+1; j < insts.size(); j++){
							Instruction inst2 = insts.get(j);
							switch(inst2.getKind()){
							case Instruction.COMMIT:
							case Instruction.JUMP:
								break;
							case Instruction.ANYMEM:
							case Instruction.LOCKMEM:
								boolean eqgroup = true;
								boolean norules2 = false;
								boolean meminsttype2;
								if(inst2.getKind() == Instruction.ANYMEM) meminsttype2 = true;
								else meminsttype2 = false;
								int natoms2 = -1;
								int nmems2 = -1;
								for(int j2 = j+1; j2 < insts.size(); j2++){
									Instruction inst3 = insts.get(j2);
									switch(inst3.getKind()){
									case Instruction.COMMIT:
									case Instruction.JUMP:
										break;
									case Instruction.NORULES:
										if((inst3.getArg1()).equals(inst2.getArg1())) norules2 = true;
										break;
									case Instruction.NATOMS:
										if((inst3.getArg1()).equals(inst2.getArg1())) natoms2 = inst3.getIntArg2();
										break;
									case Instruction.NMEMS:
										if((inst3.getArg1()).equals(inst2.getArg1())) nmems2 = inst3.getIntArg2();
										break;
									default : break;
									}
								}
								if(meminsttype != meminsttype2) eqgroup = false;
								if(natoms != natoms2) eqgroup = false;
								if(nmems != nmems2) eqgroup = false;
								if(natoms == -1 || nmems == -1 ||
									natoms2 == -1 || nmems2 == -1) eqgroup = true;
								if(norules != norules2) eqgroup = false;
								if(eqgroup){
									group = Inst2GroupId.get(inst);
									changegroup = Inst2GroupId.get(inst2);
									changeMap(changegroup, group);
								}
								break;
							default : break;
							}
						}
					}
		}
		
		//マップ書き換え終了
		//GROUP生成
		for(int i=1; i<insts.size(); i++){
			Instruction inst = insts.get(i);
			if(inst.getKind() == Instruction.COMMIT
				|| inst.getKind() == Instruction.JUMP) break;
			Object group = Inst2GroupId.get(inst);
			InstructionList subinsts = new InstructionList();
			//SPECは不要?
			subinsts.add(spec);
			for(int i2=i; i2<insts.size(); i2++){
				Instruction inst2 = insts.get(i2);
				if(inst2.getKind() == Instruction.COMMIT
					|| inst.getKind() == Instruction.JUMP) break;
				if(group.equals(Inst2GroupId.get(inst2))){
					subinsts.add(inst2);
					insts.remove(i2);
					i2 -= 1;
				}
			}
			subinsts.add(new Instruction(Instruction.PROCEED));
			Instruction groupinst = new Instruction(Instruction.GROUP, subinsts);
			insts.add(i, groupinst);
			Cost cost = new Cost();
			cost.evaluateCost(subinsts.insts);
//          デバッグ用コスト表示
//			System.out.print(groupinst + "\n cost = ");
//			for(int s = 0; s<cost.costvalueN.size(); s++){
//				if(s>0){
//					if(((Integer)cost.costvalueN.get(s)).intValue() > 1)
//						System.out.print(((Integer)cost.costvalueN.get(s)).intValue());
//				} else System.out.print(((Integer)cost.costvalueN.get(s)).intValue());
//				if(s<cost.costvalueM.size()){
//					if(((Integer)cost.costvalueM.get(s)).intValue() == 1)
//						System.out.print("m");
//					else if(((Integer)cost.costvalueM.get(s)).intValue() > 1)
//						System.out.print("m^"+((Integer)cost.costvalueM.get(s)).intValue());
//				}
//				if(s==1)System.out.print("n");
//				else if(s>1)System.out.print("n^"+s);
//				
//				if(s != cost.costvalueN.size()-1) System.out.print(" + ");
//			}
//			System.out.println("");
			
			group2Cost.put(groupinst, cost);
		}
//		Group命令の並び替え
		//アトム主導テストでは最初のfunc命令を含むグループの位置は先頭のままとする
		int groupstart = 0;
		for(int i=groupstart; i<insts.size(); i++){
			Instruction inst = insts.get(i);
			if(inst.getKind() == Instruction.GROUP){
				Cost cost1 = null;
				if(group2Cost.containsKey(inst)) cost1 = group2Cost.get(inst);
				else break;
				for(int j=i-1; j>0; j--){
					Instruction inst2 = insts.get(j);
					if (inst2.getKind() == Instruction.GROUP){
						Cost cost2 = null;
						if(group2Cost.containsKey(inst2)) cost2 = group2Cost.get(inst2);
						else break;
						if(cost2.igtCost(cost1)){
							insts.add(j--, inst);
							insts.remove(i+1);
							continue;
						}
					}
					else break;
				}
			}
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
	public void changeMap(Integer group1, Integer group2){
		Iterator<Instruction> it = Inst2GroupId.keySet().iterator();
		while (it.hasNext()) {
			Instruction key = it.next();
			if (group1.equals(Inst2GroupId.get(key))) {
				Inst2GroupId.put(key, group2);
			}
		}
	}
		
	//生成されたマップの確認 デバッグ用
	public void viewMap(){
		Set set1 = var2DefInst.entrySet();
		Set<Map.Entry<Instruction, Integer>> set2 = Inst2GroupId.entrySet();

		Iterator it1 = set1.iterator();
		Iterator<Map.Entry<Instruction, Integer>> it2 = set2.iterator();
	
		Util.println("var2DefInst :- ");
		while(it1.hasNext()){
			Map.Entry mapentry = (Map.Entry)it1.next();
			Util.println(mapentry.getKey() + "/" + mapentry.getValue());
		}
			Util.println("Inst2GroupId :- ");
		while(it2.hasNext()){
			Map.Entry<Instruction, Integer> mapentry = it2.next();
			Util.println(mapentry.getKey() + "/" + mapentry.getValue());
		}	
	}
}

class Cost {
	List<Integer> costvalueN;
	List<Integer> costvalueM;
	HashMap memend;
	int n;
	
	Cost(){
		costvalueN = new ArrayList<>();
		costvalueM = new ArrayList<>();
		memend = new HashMap();
		n = 0;
	}
	
	public void evaluateCost(List<Instruction> insts){
		int vn = 0;
		int vm = 0;
		for(int i=0; i<insts.size(); i++){
			Instruction inst = insts.get(i);
			switch(inst.getKind()){
			case Instruction.FINDATOM:
				costvalueN.add(n++, vn);
				costvalueM.add(vm);
				vn = 1;
				break;
			case Instruction.ANYMEM:
				if(costvalueN.size() <= n) costvalueN.add(++vn);
				else costvalueN.set(n, ++vn);
				for(int j=insts.size()-1; j>i; j--) {
					Instruction inst2 = insts.get(j);
					switch(inst2.getKind()){
					case Instruction.PROCEED:
						memend.put(inst.getArg1(), inst2);
						break;
					case Instruction.NATOMS:
					case Instruction.NMEMS:
					case Instruction.NORULES:
						if(!Optimizer.fGuardMove && inst2.getIntArg1() == inst.getIntArg1()){
							if(memend.containsKey(inst2.getArg1())){
								memend.put(inst2.getArg1(), inst2);
								j = -1;
								break;
							}
						}
						break;
					}
				}
				if(costvalueM.size() <= n) costvalueM.add(++vm);
				else costvalueM.set(n, ++vm);
				break;
			case Instruction.NATOMS:
			case Instruction.NMEMS:
			case Instruction.NORULES:
				if(Optimizer.fGuardMove) {vn++; break;}
				if(costvalueN.size() <= n) costvalueN.add(++vn);
				else costvalueN.set(n, ++vn);
				if(memend.containsValue(inst)){
//					while(costvalueM.size() <=n) costvalueM.add(new Integer(vm));
//						costvalueM.set(n, new Integer(vm--));
					vm--;
				}
				break;
			case Instruction.PROCEED:
				if(memend.containsValue(inst)){
//					while(costvalueM.size() <=n) costvalueM.add(new Integer(vm));
//						costvalueM.set(n, new Integer(vm--));
					vm--;
				}
				break;
			default:
				if(costvalueN.size() <= n) costvalueN.add(++vn);
				else costvalueN.set(n, ++vn);
				
				break;
			}
		}
		if(costvalueN.size() == 0) costvalueN.add(vn);
	}
	
	public boolean igtCost(Cost c){
		List<Integer> costsn = c.costvalueN;
		List<Integer> costsm = c.costvalueM;
		if(costvalueN.size() > costsn.size()) return true;
		else if(costvalueN.size() < costsn.size()) return false;
		else {
			for(int i=costvalueN.size()-1, j=costvalueM.size()-1; i>=0 && j>=0; i--, j--){
				if(costvalueM.size() > j && costsm.size() > j){
					if(costvalueM.get(j) > costsm.get(j)) return true;
					else if(costvalueM.get(j) < costsm.get(j))return false;
				}
				if(costvalueN.get(i) > costsn.get(i)) return true;
				else if(costvalueN.get(i) < costsn.get(i)) return false;
				else continue;
			}
			return false;
		}
	}
}
