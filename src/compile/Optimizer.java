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
	 * 最適化する。
	 * TODO 再利用した膜内のアトムの再利用（現状は本膜のみ）
	 * TODO 命令列の操作（移動・削除）の効率的な実装
	 * @param list 最適化したい命令列
	 */
	public static void optimize(List list) {
		
		/////////////////////////////////////////////////
		//
		// 再利用するアトムの組み合わせを決定する
		//
		
		// removeatom/newatom/getlink/inherit命令の情報を調べる
		HashMap removedAtoms = new HashMap(); // functor -> set of atomId
		HashMap createdAtoms = new HashMap(); // functor -> set of atomId
		HashMap getlinkInsts = new HashMap(); // linkId -> getlink instruction
		HashMap inheritlinkInsts = new HashMap(); // linkId -> inheritlink instruction
		
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
					getlinkInsts.put(inst.getArg1(), inst);
					break;
				case Instruction.INHERITLINK:
					inheritlinkInsts.put(inst.getArg3(), inst);
					break;
			}
		}
		
		//再利用するアトムの組み合わせを決定

		//再利用前のアトムID -> 再利用後のアトムID
		HashMap reuseMap = new HashMap();
		//再利用されるアトムのID（reuseMapの値に設定されているIDの集合）
		HashSet reuseAtoms = new HashSet(); 

		//本膜中にある、同じ名前のアトムを再利用する	
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

		// 冗長なgetlink/inheritlink命令を探してremovelinkInsts/inheritlinkInstsから除去する
		it = getlinkInsts.keySet().iterator();
		while (it.hasNext()) {
			Integer linkId = (Integer)it.next();
			Instruction getlink = (Instruction)getlinkInsts.get(linkId);
			Instruction inheritlink = (Instruction)inheritlinkInsts.get(linkId);
			//inheritlink第1引数のアトムに対応する再利用後のアトムID
			Integer atomId = (Integer)reuseMap.get(inheritlink.getArg1());
			if (inheritlink != null && 
				atomId != null && atomId.equals(getlink.getArg2()) &&
				inheritlink.getArg2().equals(getlink.getArg3())) {

				it.remove();
				inheritlinkInsts.remove(linkId);
			}
		}
										
		// アトム再利用をするとgetlink命令の前にinherit命令が来る事があるので、
		// getlink命令を命令列の先頭に移動する。
		it = getlinkInsts.values().iterator();
		while (it.hasNext()) {
			list.add(0, it.next());
		}

		//不要になった命令を除去し、アトムIDを書き換える
		it = list.subList(getlinkInsts.size(), list.size()).iterator();
		while (it.hasNext()) {
			Instruction inst = (Instruction)it.next();
			switch (inst.getKind()) {
				case Instruction.REMOVEATOM: {
					Integer atomId = (Integer)inst.getArg1();
					if (reuseAtoms.contains(atomId)) {
						it.remove();
					}
					break;
				}
				case Instruction.NEWATOM: {
					Integer atomId = (Integer)inst.getArg1();
					if (reuseMap.containsKey(atomId)) {
						it.remove();
					}
					break;
				}
				case Instruction.NEWLINK: {
					changeAtomArg1(inst, reuseMap);
					changeAtomArg3(inst, reuseMap);
					break;
				}
				case Instruction.GETLINK: {
					//先頭に移動したので除去
					it.remove();
					break;
				}
				case Instruction.INHERITLINK: {
					//inheritlinkInstsにない場合は冗長と判断されたものなので削除
					if (!inheritlinkInsts.containsKey(inst.getArg3())) {
						it.remove();
					} else {
						changeAtomArg1(inst, reuseMap);
					}
					break;
				}
			}
		}

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
	 * 第１引数を書き換える。
	 * @param inst 書き換える命令
	 * @param reuseMap アトム再利用マップ
	 */
	private static void changeAtomArg1(Instruction inst, Map reuseMap) {
		Integer before = (Integer)inst.getArg1();
		Integer after = (Integer)reuseMap.get(before);
		if (after != null) {
			inst.setArg1(after);
		}
	}
	/**
	 * 第３引数を書き換える。
	 * @param inst 書き換える命令
	 * @param reuseMap アトム再利用マップ
	 */
	private static void changeAtomArg3(Instruction inst, Map reuseMap) {
		Integer before = (Integer)inst.getArg3();
		Integer after = (Integer)reuseMap.get(before);
		if (after != null) {
			inst.setArg3(after);
		}
	}
}