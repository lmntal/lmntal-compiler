/*
 * 作成日: 2003/11/30
 */
package compile;

import java.util.*;
import runtime.Instruction;
import runtime.Functor;

/**
 * @author Mizuno
 *
 */
public class Optimizer {
	/**	
	 * 渡された命令列を最適化する。
	 * @param list 最適化したい命令列。今のところボディ命令列が渡されることを仮定している。
	 */
	public static void optimize(List list) {
		//TODO relink命令を、getlink/inheritlink命令に変換する？
		reuseAtom(list);
		removeUnnecessaryRelink(list);
	}
	
	/**	
	 * アトム再利用を行う。
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
		// getlink命令を命令列の先頭に移動する。
		// TODO 適切な移動場所を見つける
		it = getlinkInsts.values().iterator();
		while (it.hasNext()) {
			list.add(0, it.next());
		}

		//不要になったremoveatom/newatom命令を除去
		ListIterator lit = list.listIterator(getlinkInsts.size());
		while (lit.hasNext()) {
			Instruction inst = (Instruction)lit.next();
			switch (inst.getKind()) {
				case Instruction.REMOVEATOM: {
					Integer atomId = (Integer)inst.getArg1();
					if (reuseAtoms.contains(atomId)) {
						lit.remove();
					}
					break;
				}
				case Instruction.NEWATOM: {
					Integer atomId = (Integer)inst.getArg1();
					if (reuseMap.containsKey(atomId)) {
						lit.remove();
					}
					break;
				}
				case Instruction.GETLINK: {
					//先頭に移動したので除去
					lit.remove();
					break;
				}
			}
		}

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