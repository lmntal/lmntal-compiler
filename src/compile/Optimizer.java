/*
 * 作成日: 2003/11/30
 */
package compile;

import java.util.*;
import runtime.Instruction;
import runtime.Functor;

/**
 * 最適化を行うクラスメソッドを持つクラス。
 * @author Mizuno
 * TODO 命令列の仕様を確認
 */
public class Optimizer {
	/**	
	 * 渡された命令列を最適化する。<br>
	 * 命令列中には、1引数のremoveatom/removemem命令が現れていてはいけない。
	 * @param list 最適化したい命令列。今のところボディ命令列が渡されることを仮定している。
	 */
	public static void optimize(List list) {
		normalize(list);
		reuseMem(list);
		reuseAtom(list);
		removeUnnecessaryRelink(list);
	}
	
	/**
	 * relink命令を、getlink/inheritlink命令に変換する。
	 * @param list 変換する命令列
	 */
	private static void normalize(List list) {
		int nextId = -1;
		
		ListIterator it = list.listIterator();
		while (it.hasNext()) {
			Instruction inst = (Instruction)it.next();
			switch (inst.getKind()) {
				case Instruction.SPEC:
					if (nextId >= 0) {
						throw new RuntimeException("SYSTEM ERROR: more than one spec instruction");
					}
					nextId = inst.getIntArg2();
					break;
				case Instruction.RELINK:
					if (nextId < 0) {
						throw new RuntimeException("SYSTEM ERROR: relink before spec instruction");
					}
					it.remove();
					it.add(new Instruction(Instruction.GETLINK,  nextId, inst.getIntArg3(), inst.getIntArg4()));
					it.add(new Instruction(Instruction.INHERITLINK,  inst.getIntArg1(), inst.getIntArg2(), nextId));
					nextId++;
					break;
				case Instruction.LOCALRELINK:
					if (nextId < 0) {
						throw new RuntimeException("SYSTEM ERROR: relink before spec instruction");
					}
					it.remove();
					it.add(new Instruction(Instruction.GETLINK,  nextId, inst.getIntArg3(), inst.getIntArg4()));
					it.add(new Instruction(Instruction.LOCALINHERITLINK,  inst.getIntArg1(), inst.getIntArg2(), nextId));
					nextId++;
					break;
			}
		}
	}

	/**
	 * 膜の再利用を行うコードを生成する。<br>
	 * 命令列中には、1引数のremovemem命令が現れていてはいけない。
	 * 
	 * @param list
	 */
	private static void reuseMem(List list) {
		HashMap reuseMap = new HashMap();
		HashSet reuseMems = new HashSet(); // 再利用される膜のIDの集合
		
		//再利用する膜の組み合わせを決定する
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Instruction inst = (Instruction)it.next();
			switch (inst.getKind()) {
//TODO プロセス文脈を持たない膜の再利用
//				case Instruction.REMOVEMEM:
//					break;
//				case Instruction.NEWMEM:
//					break;
				case Instruction.MOVECELLS:
					//すでに再利用で生成する事が決まっている膜でなければ、再利用で生成する
					if (!reuseMap.containsKey(inst.getArg1())) {
						reuseMap.put(inst.getArg1(), inst.getArg2());
						reuseMems.add(inst.getArg2());
						break;
					}
			}
		}
		
		//命令列を書き換える
		//TODO insertproxies命令の順番・回数を適切に変更する
		ListIterator lit = list.listIterator();
		while (lit.hasNext()) {
			Instruction inst = (Instruction)lit.next();
			switch (inst.getKind()) {
				case Instruction.NEWMEM:
					if (reuseMap.containsKey(inst.getArg1())) {
						//addmem命令に変更
						lit.remove();
						int m = ((Integer)reuseMap.get(inst.getArg1())).intValue();
						lit.add(new Instruction(Instruction.ADDMEM, inst.getIntArg2(), m)); 
					}
					break;
				case Instruction.MOVECELLS:
					if (reuseMems.contains(inst.getArg2())) {
						//addmem命令で移動が完了しているため除去
						//※ある膜が、2つ以上の膜の再利用の根拠となることはない
						lit.remove();
					}
					break;
				case Instruction.FREEMEM:
					if (reuseMems.contains(inst.getArg1())) {
						lit.remove();
					}
			}
		}
		Instruction.changeMemId(list, reuseMap);
	}
		
	/**	
	 * アトム再利用を行うコードを生成する。<br>
	 * 分散環境の事は考えていない。
	 * また、引数に渡される命令列は、次の条件を満たしている必要がある。
	 * <ul>
	 *  <li>1引数のremoveatom命令を使用していない
	 *  <li>getlink命令を使用していない
	 * </ul>
	 * @param list 最適化したい命令列。今のところボディ命令列が渡されることを仮定している。
	 */
	private static void reuseAtom(List list) {
		/////////////////////////////////////////////////
		//
		// 再利用するアトムの組み合わせを決定する
		//
		
		// removeatom/newatom/getlink命令の情報を調べる
		HashMap removedAtoms = new HashMap(); // functor -> set of atomId
		HashMap createdAtoms = new HashMap(); // functor -> set of atomId
		HashMap getlinkInsts = new HashMap(); // linkId -> getlink instruction
		
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Instruction inst = (Instruction)it.next();
			switch (inst.getKind()) {
				case Instruction.REMOVEATOM:
					//とりあえず本膜だけ
					if (inst.getIntArg2() == 0) {
						Integer atomId = (Integer)inst.getArg1();
						Functor functor = (Functor)inst.getArg3();
						addToMap(removedAtoms, functor, atomId);
					}
					break;
				case Instruction.NEWATOM:
					//とりあえず本膜だけ
					if (inst.getIntArg2() == 0) {
						Integer atomId = (Integer)inst.getArg1();
						Functor functor = (Functor)inst.getArg3();
						addToMap(createdAtoms, functor, atomId);
					}
					break;
				case Instruction.GETLINK:
					//後で場所を移動する
					getlinkInsts.put(inst.getArg1(), inst);
					break;
			}
		}
		
		//再利用するアトムの組み合わせを決定

		//再利用前のアトムID -> 再利用後のアトムID
		HashMap reuseMap = new HashMap();
		//再利用されるアトムのID（reuseMapの値に設定されているIDの集合）
		HashSet reuseAtoms = new HashSet(); 

		//本膜中にある、同じ名前のアトムを再利用する
		//とりあえずでてきた順に対応させる	
		Iterator functorIterator = removedAtoms.keySet().iterator();
		while (functorIterator.hasNext()) {
			Functor functor = (Functor)functorIterator.next();
			if (createdAtoms.containsKey(functor)) {
				Iterator removedAtomIterator = ((HashSet)removedAtoms.get(functor)).iterator();
				Iterator createdAtomIterator = ((HashSet)createdAtoms.get(functor)).iterator();
				while (removedAtomIterator.hasNext() && createdAtomIterator.hasNext()) {
					Integer removeAtomId = (Integer)removedAtomIterator.next();
					reuseMap.put(createdAtomIterator.next(), removeAtomId);
					reuseAtoms.add(removeAtomId);
					//再利用の組み合わせが決まったものは削除する
					//（この後に続く、アトム名が異なる場合などの再利用の組み合わせを決定する処理のため。）
					removedAtomIterator.remove();
					createdAtomIterator.remove();
				}
			}
		}
		
		//TODO アトム名が異なるものの再利用の組み合わせを決定するコードをここに書く

		//再利用方法の表示（デバッグ用）
		it = reuseMap.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			System.out.println(key + " " + reuseMap.get(key));
		}
		
		//////////////////////////////////////////////////
		//
		// アトムを再利用するような命令列を生成する
		//

										
		// アトム再利用をするとgetlink命令の前にinherit命令が来る事があるので、
		// getlink命令をspec命令の後に移動する。
		// TODO 適切な移動場所を見つける
		it = getlinkInsts.values().iterator();
		while (it.hasNext()) {
			list.add(1, it.next());
		}

		//不要になったremoveatom/freeatom/newatom命令を除去
		ListIterator lit = list.listIterator(getlinkInsts.size() + 1);
		while (lit.hasNext()) {
			Instruction inst = (Instruction)lit.next();
			switch (inst.getKind()) {
				case Instruction.REMOVEATOM:
				case Instruction.FREEATOM:
					Integer atomId = (Integer)inst.getArg1();
					if (reuseAtoms.contains(atomId)) {
						lit.remove();
					}
					break;
				case Instruction.NEWATOM:
					atomId = (Integer)inst.getArg1();
					if (reuseMap.containsKey(atomId)) {
						lit.remove();
					}
					break;
				case Instruction.GETLINK:
					//先頭に移動したので除去
					lit.remove();
					break;
			}
		}
		//TODO enqueueatom命令を生成

		//アトムIDの付け替え
		Instruction.changeAtomId(list, reuseMap);
	}

	/**
	 * HashMapの値が、複数の値の集合を表すHashSetであるものに対して値を追加する
	 * @param map 値を追加するHashMap
	 * @param key キー
	 * @param value 追加したい値
	 */
	private static void addToMap(HashMap map, Object key, Object value) {
		HashSet set = (HashSet)map.get(key);
		if (set == null) {
			set = new HashSet();
			set.add(value);
			map.put(key, set);
		} else {
			set.add(value);
		}
	}			

	/**
	 * 冗長なrelink/inheritlink命令を除去します。
	 * @param list
	 */
	private static void removeUnnecessaryRelink(List list) {
		HashMap getlinkInsts = new HashMap(); // linkId -> getlink instruction
		ArrayList remove = new ArrayList();
		
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Instruction inst = (Instruction)it.next();
			switch (inst.getKind()) {
				case Instruction.GETLINK:
					getlinkInsts.put(inst.getArg1(), inst);
					break;
				case Instruction.INHERITLINK:
					Instruction getlink = (Instruction)getlinkInsts.get(inst.getArg3());
					if (getlink.getArg2().equals(inst.getArg1()) &&  // <- atomID
						 getlink.getArg3().equals(inst.getArg2())) { // <- pos
						//冗長なので除去
						remove.add(getlink);
						remove.add(inst);
					}
					break;
			}
		}
		list.removeAll(remove);
	}
}