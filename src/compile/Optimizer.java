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
					it.add(new Instruction(Instruction.INHERITLINK,  inst.getIntArg1(), inst.getIntArg2(), nextId, inst.getIntArg5()));
					nextId++;
					break;
				case Instruction.LOCALRELINK:
					if (nextId < 0) {
						throw new RuntimeException("SYSTEM ERROR: relink before spec instruction");
					}
					it.remove();
					it.add(new Instruction(Instruction.GETLINK,  nextId, inst.getIntArg3(), inst.getIntArg4()));
					it.add(new Instruction(Instruction.LOCALINHERITLINK,  inst.getIntArg1(), inst.getIntArg2(), nextId, inst.getIntArg5()));
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
		HashMap parent = new HashMap();
		
		//再利用する膜の組み合わせを決定する
		//TODO プロセス文脈を持たない膜の再利用
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Instruction inst = (Instruction)it.next();
			switch (inst.getKind()) {
				case Instruction.REMOVEMEM:
					parent.put(inst.getArg1(), inst.getArg2());
					break;
				case Instruction.NEWMEM:
					parent.put(inst.getArg1(), inst.getArg2());
					break;
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
		//その際、冗長なremovemem/addmem命令を除去する
		HashSet set = new HashSet(); //removemem/addmem命令の不要な膜再利用に関わる膜
		it = reuseMap.keySet().iterator();
		while (it.hasNext()) {
			Integer i1 = (Integer)it.next();
			Integer i2 = (Integer)reuseMap.get(i1);
			if (parent.get(i1).equals(parent.get(i2))) { //親が同じだったら
				set.add(i1);
				set.add(i2);
			}
		}
		
		//TODO removeproxies/insertproxies命令を適切に変更する
		ListIterator lit = list.listIterator();
		while (lit.hasNext()) {
			Instruction inst = (Instruction)lit.next();
			switch (inst.getKind()) {
				case Instruction.REMOVEMEM:
					if (set.contains(inst.getArg1())) {
						lit.remove();
					}
					break;
				case Instruction.REMOVEPROXIES:
					if (set.contains(inst.getArg1())) {
						lit.remove();
					}
					break;
				case Instruction.NEWMEM:
					Integer arg1 = (Integer)inst.getArg1();
					if (reuseMap.containsKey(arg1)) {
						lit.remove();
						if (!set.contains(arg1)) {
							//addmem命令に変更
							int m = ((Integer)reuseMap.get(arg1)).intValue();
							lit.add(new Instruction(Instruction.ADDMEM, inst.getIntArg2(), m)); 
						}
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
	 * 膜・ファンクタ毎にアトムの集合を管理するためのクラス。
	 * アトム再利用コードを生成する際にアトムを管理するために使用する。
	 * @author Ken
	 */
	private static class AtomSet {
		HashMap map = new HashMap(); // mem -> (functor -> atoms)
		/**
		 * アトムを追加する
		 * @param mem アトムが所属する膜
		 * @param functor アトムのファンクタ
		 * @param atom 追加するアトム
		 */
		void add(Integer mem, Functor functor, Integer atom) {
			HashMap map2 = (HashMap)map.get(mem);
			if (map2 == null) {
				map2 = new HashMap();
				map.put(mem, map2);
			}
			HashSet atoms = (HashSet)map2.get(functor);
			if (atoms == null) {
				atoms = new HashSet();
				map2.put(functor, atoms);
			}
			atoms.add(atom);
		}
		/**
		 * 指定された膜に所属する、指定されたファンクタを持つアトムの反復子を返す
		 * @param mem アトムが所属するアトム
		 * @param functor アトムのファンクター
		 * @return 反復子
		 */
		Iterator iterator(Integer mem, Functor functor) {
			HashMap map2 = (HashMap)map.get(mem);
			if (map2 == null) {
				return util.Util.NULL_ITERATOR;
			}
			HashSet atoms = (HashSet)map2.get(functor);
			if (atoms == null) {
				return util.Util.NULL_ITERATOR;
			}
			return atoms.iterator();
		}
		/**
		 * 膜の反復子を返す
		 * @return 反復子
		 */
		Iterator memIterator() {
			return map.keySet().iterator();
		}
		/**
		 * 指定された膜内にある、このインスタンスが管理するアトムのファンクタの反復子を返す
		 * @param mem 膜
		 * @return 反復子
		 */
		Iterator functorIterator(Integer mem) {
			HashMap map2 = (HashMap)map.get(mem);
			if (map2 == null) {
				return util.Util.NULL_ITERATOR;
			}
			return map2.keySet().iterator();
		}
	}
	/**	
	 * アトム再利用を行うコードを生成する。<br>
	 * 引数に渡される命令列は、次の条件を満たしている必要がある。
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
		AtomSet removedAtoms = new AtomSet();
		AtomSet createdAtoms = new AtomSet();
		HashMap getlinkInsts = new HashMap(); // linkId -> getlink instruction
		
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Instruction inst = (Instruction)it.next();
			switch (inst.getKind()) {
				case Instruction.REMOVEATOM:
					Integer atom = (Integer)inst.getArg1();
					Functor functor = (Functor)inst.getArg3();
					Integer mem = (Integer)inst.getArg2();
					removedAtoms.add(mem, functor, atom);
					break;
				case Instruction.NEWATOM:
					atom = (Integer)inst.getArg1();
					functor = (Functor)inst.getArg3();
					mem = (Integer)inst.getArg2();
					createdAtoms.add(mem, functor, atom);
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

		//同じ膜にある、同じ名前のアトムを再利用する
		Iterator memIterator = removedAtoms.memIterator();
		while (memIterator.hasNext()) {
			Integer mem = (Integer)memIterator.next();
			Iterator functorIterator = removedAtoms.functorIterator(mem);
			while (functorIterator.hasNext()) {
				Functor functor = (Functor)functorIterator.next();
				Iterator removedAtomIterator = removedAtoms.iterator(mem, functor);
				Iterator createdAtomIterator = createdAtoms.iterator(mem, functor);
				while (removedAtomIterator.hasNext() && createdAtomIterator.hasNext()) {
					Integer removeAtom = (Integer)removedAtomIterator.next();
					reuseMap.put(createdAtomIterator.next(), removeAtom);
					reuseAtoms.add(removeAtom);
					//再利用の組み合わせが決まったものは削除する
					//（この後に続く、アトム名が異なる場合などの再利用の組み合わせを決定する処理のため。）
					removedAtomIterator.remove();
					createdAtomIterator.remove();
				}
			}
		}
		
		//TODO 膜・アトム名が異なるものの再利用の組み合わせを決定するコードをここに書く

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