/*
 * 作成日: 2004/11/04
 */
package compile;

import java.util.*;
import runtime.Instruction;
import runtime.InstructionList;


/**
 * @author sakurai
 *
 * 最適化を行うメソッドを持つクラスその2。 
 *
 */
public class Optimizer2 {
	
	/** 
	 * ガード命令を可能な限り前に移動させる.
	 * とりあえず元々ヘッド命令列にあった命令の移動は考えない.
	 */
	public static void guardMove(List head){
		for(int hid=0; hid<head.size()-1; hid++){
			Instruction insth = (Instruction)head.get(hid);
			ArrayList list = insth.getVarArgs();
			boolean moveOk = true;
			if(insth.getKind() == Instruction.NOT)
				continue;
			else if(insth.getKind() == Instruction.GROUP){
				InstructionList subinsts = (InstructionList)insth.getArg1();
				Optimizer2.guardMove((List)subinsts.insts);
			}
			else 
				for(int hid2=hid-1; hid2>0; hid2--){
					Instruction insth2 = (Instruction)head.get(hid2);
					ArrayList list2 = insth2.getVarArgs();
					for(int i=0; i<list.size(); i++){
						if((insth2.getOutputType() != -1
						   && list.get(i).equals(insth2.getArg1()))
						   || list2.contains(list.get(i))){
							moveOk = false;
							break;
						   }
					}
					if(moveOk){
						head.remove(hid2+1); //移動対象の命令insthの位置はhid2+1
						head.add(hid2, insth); //hid2に移動
					}
					else break;
				}
		}
		/*
		//LOADMAP命令はSPEC命令の直後に移動
		for(int hid=head.size()-1; hid>0; hid--){
			Instruction insth = (Instruction)head.get(hid);
			if(insth.getKind() == Instruction.LOADMAP){
				head.remove(hid);
				head.add(1, insth);
				break;
			}
		}*/
	}
	    
		//グループ化
		public static void grouping(List head){
			Group group = new Group(head);	
		}
		
		//マップ生成
		public static void mapping(List head){
			//apMaker mapmaker = new MapMaker(head);
			//mapmaker.viewMap();
		}
 }
 
	/**
	 * ヘッド命令列をグループごとに分ける
	 * group[ findatom
	 *        deref
	 *        func
	 *        insint ]
	 * group [･･･]
	 * のような形になる
	 */
	class Group {
		HashMap var2DefInst;         //変数番号→変数番号を定義した命令
		HashMap Inst2GroupId;        //命令→グループ識別番号
		
		Group(List head){
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
				//else if(insth.getKind() == Instruction.LOADMAP) continue;
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
				//if(insth.getKind() == Instruction.LOADMAP) continue;
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
			
			//viewMap();
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
		//現在は否定条件用
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
					Optimizer2.guardMove((List)subinsts.insts);
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
	
	/*
		 //変数番号の定義元(?)へのマップを生成するクラス
		 //findatom	[1, 0, a_0]
		 //deref		[2, 1, 0, 0]
		 //この場合1->0, 2->1にマップを張る
		 //つまり全ての変数番号に, その番号を定義するきっかけとなった
		 //findatomかanymemで定義された番号にマップを張る
	class MapMaker{
		HashMap map;
		List list;
		public MapMaker(List insts){
			map = new HashMap();
			list = insts;
			makeMap();
			//LOADMAP命令をSPEC命令の次に挿入
			insts.add(1, new Instruction(Instruction.LOADMAP, map));
		}
		 	
		public void makeMap(){
		  for(int i=0; i<list.size(); i++){
			  Instruction inst = (Instruction)list.get(i);
			  List defvars = new ArrayList(); //定義元の変数番号のリスト
			  List usevars = new ArrayList(); //各命令で使用する変数番号のリスト
			  if(inst.getOutputType() != -1){
				if(inst.getKind() == Instruction.FINDATOM 
					|| inst.getKind() == Instruction.ANYMEM){
					defvars.add(inst.getArg2());
					map.put(inst.getArg1(), defvars);
				}
				else {
					usevars = inst.getVarArgs();
					if(usevars.isEmpty()) {
						//allocatomなど出力引数のみの命令にはとりあえず[0]へマッピング
						defvars.add(new Integer(0));
						map.put(inst.getArg1(), defvars);
					} 
					else map.put(inst.getArg1(), getDefVars(usevars, i));
				} 
			  }
			  else continue;
		  }
		}
	
		//usevars内の変数番号を定義する元となったfindatom, anymem
		//で定義した変数番号のリストを返す
		//探すfindatom, anymem命令は命令列のpc番目以前にあるはず
		public List getDefVars(List usevars, int pc){
			List defvars = new ArrayList();
			List vars = new ArrayList();
			for(int i=0; i<usevars.size(); i++){
				Object usevar = usevars.get(i);
				for(int j=pc-1; j>0; j--){
					Instruction inst = (Instruction)list.get(j);
					if(inst.getOutputType() != -1){
						if(inst.getKind() == Instruction.FINDATOM || inst.getKind() == Instruction.ANYMEM) {
							if(inst.getArg1().equals(usevar)) {
								defvars.add(usevar);
								break;
							}
							else continue;
						}
						else if(inst.getArg1().equals(usevar)){
							vars = inst.getVarArgs();
							if(vars.isEmpty()) defvars.add(new Integer(0));
							else defvars.addAll(getDefVars(vars, j));
							break;
						}
					}
					else continue;
				}
			}
			return defvars;
		}
	 	
		public void viewMap(){
			Set set = map.entrySet();
			Iterator it = set.iterator();

			System.out.println("Map :- ");
			while(it.hasNext()){
				Map.Entry mapentry = (Map.Entry)it.next();
				System.out.println(mapentry.getKey() + "/" + mapentry.getValue());
			}	
		}
		
	 }
	 */