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
	
	public static void instsRearrangement(List atom, List mem){
		//instsRearrangementForAtomMatch(atom);
		instsRearrangement(atom);
		instsRearrangement(mem);
	}
	
	/** 
	 * ガード命令を可能な限り前に移動させる.
	 * ボディ命令は並び替えない
	 */
	public static void instsRearrangement(List insts){
		for(int i=1; i<insts.size(); i++){
			boolean moveok = true; //移動可能判定フラグ
			Instruction inst = (Instruction)insts.get(i);
			ArrayList list = inst.getVarArgs();
			
			//ボディ命令列は並び替えない -> ボディの先頭はcommit
			if(inst.getKind() == Instruction.COMMIT) break;
			
			//否定条件は放置(位置を変えない)
			else if(inst.getKind() == Instruction.NOT) continue;
			
			//その他位置を変えたくない命令
			else if(inst.getKind() == Instruction.FINDATOM
				|| inst.getKind() == Instruction.ANYMEM
				|| inst.getKind() == Instruction.PROCEED) continue;
				
			else if(inst.getKind() == Instruction.GROUP
					|| inst.getKind() == Instruction.BRANCH){
				InstructionList subinsts = (InstructionList)inst.getArg1();
				instsRearrangement(subinsts.insts);
			}
			else
				for(int i2=i-1; i2>0; i2--){
					Instruction inst2 = (Instruction)insts.get(i2);
					ArrayList list2 = inst2.getVarArgs();
					for(int j=0; j<list.size(); j++){
						if((inst2.getOutputType() != -1
							&& list.get(j).equals(inst2.getArg1()))
							|| list2.contains(list.get(j))){
								moveok = false;
								break;
							}
					}
					if(moveok){
						insts.remove(i2+1);
						insts.add(i2, inst);
					}
					else break; 
				}
		}			
			/*
			//LOADMAP命令はSPEC命令の直後に移動
			for(int i=insts.size()-bodysize; i>1; i--){
				Instruction inst = (Instruction)insts.get(i);
				if(inst.getKind() == Instruction.LOADMAP){
					insts.remove(i);
					insts.add(1, inst);
					break;
				}
			}*/
	}
	    
	//グループ化
	public static void grouping(List atom, List mem){
		Group g = new Group();
		g.groupingForAtomMatch(atom);
		g.grouping(mem, false);
		g = null;
	}
		
	//マップ生成
	public static void mapping(List head){
		//MapMaker mapmaker = new MapMaker(head);
		//Mapmaker.viewMap();
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
	
	Group(){
		var2DefInst = new HashMap();
		Inst2GroupId = new HashMap();
	}
	
	private void initMap(){
		var2DefInst.clear();
		Inst2GroupId.clear();
	}
	
	public void grouping(List insts, boolean isAtomMatch){
		if(((Instruction)insts.get(0)).getKind() != Instruction.SPEC) return;
		for(int i=1; i<insts.size(); i++){
			Instruction inst = (Instruction)insts.get(i);
			//否定条件がある場合はグループ化は無効 暫定的処置
			if(inst.getKind() == Instruction.NOT) return;
			//ボディ命令列はグループ化しない
			if(inst.getKind() == Instruction.COMMIT) break;
			//グループ番号を割り振る	  
			Inst2GroupId.put(inst, new Integer(i));
			//変数番号→命令にマップを張る
			if(inst.getOutputType() != -1)
				var2DefInst.put(inst.getArg1(), inst);			
		}
		//viewMap();
		createGroup(insts, isAtomMatch);
		initMap();
	}
		
	public void groupingForAtomMatch(List insts){
		if(((Instruction)insts.get(0)).getKind() != Instruction.SPEC) return;
		for(int i=1; i<insts.size(); i++){
			Instruction branch = (Instruction)insts.get(i);
			if(branch.getKind() == Instruction.COMMIT) break;
			if(branch.getKind() == Instruction.BRANCH){
				InstructionList subinsts = (InstructionList)branch.getArg1();
				grouping(subinsts.insts, true);
			}
		}	
	}	

	//グループ分け
	//命令番号→グループ番号とし, 同じグループに入る命令は
	//同じグループ番号へマップが張られる
	private void createGroup(List insts, boolean isAtomMatch){
		for(int i=1; i<insts.size(); i++){
			Instruction inst = (Instruction)insts.get(i);
			if(inst.getKind() == Instruction.COMMIT) break;
			Object group = null;
			Object changegroup = null;
			ArrayList list = inst.getVarArgs();
			if(list.isEmpty()) continue;
		
			//if(insth.getKind() == Instruction.LOADMAP) continue;
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
				//どう区別する?
				for(int i2 = 1; i2 < insts.size(); i2++){
					Instruction inst2 = (Instruction)insts.get(i2);
					if(inst2.getKind() == Instruction.COMMIT) break;
					if(inst2.getKind() == Instruction.ANYMEM
						|| inst.getKind() == Instruction.LOCKMEM){
							group = Inst2GroupId.get(inst);
							changegroup = Inst2GroupId.get(inst);
							changeMap(changegroup, group);
						}
				}
			}
		  
		}
		//マップ生成終了
		
		//GROUP生成
		for(int i=1; i<insts.size(); i++){
			Instruction inst = (Instruction)insts.get(i);
			if(inst.getKind() == Instruction.COMMIT) break;
			//if(inst.getKind() == Instruction.LOADMAP) continue;
			Object group = Inst2GroupId.get(inst);
			InstructionList subinsts = new InstructionList();
			subinsts.add(new Instruction(Instruction.SPEC, 0, 0));
			for(int i2=i; i2<insts.size(); i2++){
				Instruction inst2 = (Instruction)insts.get(i2);
				if(inst2.getKind() == Instruction.COMMIT) break;
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