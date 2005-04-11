/*
 * 作成日: 2004/11/10
 */
package compile;

import java.util.*;
import runtime.Instruction;
import runtime.InstructionList;
/**
 * @author sakurai
 *
 * ヘッド命令列をグループごとに分ける
 * group[ findatom
 *        deref
 *        func
 *        insint ]
 * group [･･･]
 * のような形になる
 */
public class Grouping {
	HashMap var2DefInst;         //変数番号→変数番号を定義した命令
	HashMap Inst2GroupId;        //命令→グループ識別番号
		
	public Grouping(List head){
		var2DefInst = new HashMap();
		Inst2GroupId = new HashMap();
		
		if(((Instruction)head.get(0)).getKind() != Instruction.SPEC) return;
		if(((Instruction)head.get(head.size()-1)).getKind() != Instruction.JUMP) return;

		//グループ番号を割り振る
		//spec, jump以外の全ての命令に行番号をグループ番号として割り振る
		for(int hid=1; hid<head.size()-1; hid++){
			Inst2GroupId.put(head.get(hid), new Integer(hid));
		}
		
		//変数番号→命令番号にマップを張る
		for(int hid=1; hid<head.size()-1; hid++){
			Instruction insth = (Instruction)head.get(hid);
			if (insth.getOutputType() != -1) {
				var2DefInst.put(insth.getArg1(), insth);
			}
		}
		createGroup(head);
	}
	
	//グループ分け
	//命令番号→グループ番号とし, 同じグループに入る命令は
	//同じグループ番号へマップが張られる
	private void createGroup(List head){
		for(int hid=1; hid<head.size()-1; hid++){
			Instruction insth = (Instruction)head.get(hid);
			Object group = null;
			Object changegroup = null;
			ArrayList list = insth.getVarArgs();
			
			//否定条件があった場合, 全ての命令を同じグループにする.
			//暫定的措置
			if(insth.getKind() == Instruction.NOT){
				allInstsToSameGroup();
				break;
			}
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals(new Integer(0))) continue;
				group = Inst2GroupId.get(var2DefInst.get(list.get(i)));
				changegroup = Inst2GroupId.get(insth);
				changeMap(changegroup, group);
			}
			if(insth.getKind() == Instruction.ANYMEM
			  || insth.getKind() == Instruction.LOCKMEM){
				//膜を取得する命令は全て同じグループに属すことにする
				//これも暫定的措置
				//{a}, {b, $p}, {c} ･･･これらは実際は違うグループ
				//{a(X)}, {a(Y)} ･･･これらは同じグループ
				//どう区別する?
				for(int i = 1; i < head.size()-1; i++){
					Instruction inst = (Instruction)head.get(i);
					if(inst.getKind() == Instruction.ANYMEM
						|| inst.getKind() == Instruction.LOCKMEM){
							group = Inst2GroupId.get(insth);
							changegroup = Inst2GroupId.get(inst);
							changeMap(changegroup, group);
						}
				}
			}
			  
		}
		//マップ生成終了
		
		//GROUP生成
		for(int hid=1; hid<head.size()-1; hid++){
			Instruction insth = (Instruction)head.get(hid);
			Object group = Inst2GroupId.get(insth);
			InstructionList subinsts = new InstructionList();
			subinsts.add(new Instruction(Instruction.SPEC,0,0));
			for(int hid2=hid; hid2<head.size()-1; hid2++){
				Instruction insth2 = (Instruction)head.get(hid2);
				if(group.equals(Inst2GroupId.get(insth2))){
					subinsts.add(insth2);
					head.remove(hid2);
					hid2 -= 1;
				}
			}
			subinsts.add(new Instruction(Instruction.PROCEED));
			head.add(hid, new Instruction(Instruction.GROUP, subinsts));
		}
		
		guardMoveOptimize(head);
		//mapView();
	}
	
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
	
	//全ての命令を同じグループにする.
	//否定条件用
	private void allInstsToSameGroup(){
		Iterator it = Inst2GroupId.keySet().iterator();
		while(it.hasNext()){
			Object key = it.next();
			Inst2GroupId.put(key, new Integer(1));
		}
	}
	
	//GROUP内の並び替えによる最適化
	private void guardMoveOptimize(List list){
		//ガード命令の移動
		for(int i=0; i<list.size(); i++){
			Instruction inst = (Instruction)list.get(i);
			if(inst.getKind() == Instruction.GROUP){
				InstructionList subinsts = (InstructionList)inst.getArg1();
				GuardOptimizer.guardMove((List)subinsts.insts);
			}
		}
	}
	
	//生成されたマップの確認 デバッグ用
	public void mapView(){
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
