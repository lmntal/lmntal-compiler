/*
 * 作成日: 2003/11/30
 */
package compile;

import java.util.*;
import runtime.Instruction;
import runtime.Functor;
import runtime.Env;

/**
 * 最適化を行うクラスメソッドを持つクラス。
 * @author Mizuno
 */
public class Optimizer {
	/**	
	 * 渡された命令列を、現在の最適化レベルに応じて最適化する。<br>
	 * 命令列中には、1引数のremoveatom/removemem命令が現れていてはいけない。
	 * 現在の引数仕様は暫定的なもので、将来変更される予定。
	 * @param head 膜主導マッチング命令列
	 * @param body ボディ命令列
	 */
	public static void optimize(List head, List body) {
		if (Env.optimize > 0) {
			if (Env.optimize >= 4) {
				reuseMem(body);
			}
			if (Env.optimize >= 2) {
				if (changeOrder(body)) {
					reuseAtom(body);
					removeUnnecessaryRelink(body);
				}
			}
			if (Env.optimize >= 7) {
				makeLoop(head, body); //まだ問題だらけ
			}
		}
	}

	///////////////////////////////////////////////////////
	// 膜最適化関連
		
	/**
	 * 膜の再利用を行うコードを生成する。<br>
	 * 命令列中には、1引数のremovemem命令が現れていてはいけない。
	 * 命令列の最後はproceed命令でなければならない。
	 * @param list ボディ命令列
	 */
	private static void reuseMem(List list) {
		Instruction last = (Instruction)list.get(list.size() - 1);
		if (last.getKind() != Instruction.PROCEED) {
			return;
		}
		
		HashMap reuseMap = new HashMap();
		HashSet reuseMems = new HashSet(); // 再利用される膜のIDの集合
		HashMap parent = new HashMap();
		HashMap removedChildren = new HashMap(); // map -> list of children
		HashMap createdChildren = new HashMap(); // map -> list of children
		HashMap pourMap = new HashMap();
		HashSet pourMems = new HashSet(); // pour命令の第２引数に含まれる膜
		
		//再利用する膜の組み合わせを決定する
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Instruction inst = (Instruction)it.next();
			switch (inst.getKind()) {
				case Instruction.REMOVEMEM:
					parent.put(inst.getArg1(), inst.getArg2());
					addToMap(removedChildren, inst.getArg2(), inst.getArg1());
					break;
				case Instruction.NEWMEM:
					parent.put(inst.getArg1(), inst.getArg2());
					addToMap(createdChildren, inst.getArg2(), inst.getArg1());
					break;
				case Instruction.MOVECELLS:
//					//すでに再利用で生成する事が決まっている膜でなければ、再利用で生成する
//					if (!reuseMap.containsKey(inst.getArg1())) {
//						reuseMap.put(inst.getArg1(), inst.getArg2());
//						reuseMems.add(inst.getArg2());
//						break;
//					}
					addToMap(pourMap, inst.getArg1(), inst.getArg2());
					pourMems.add(inst.getArg2());
			}
		}

		createReuseMap(reuseMap, reuseMems, parent, removedChildren, createdChildren,
					   pourMap, pourMems, new Integer(0));
		
//		//再利用方法の表示（デバッグ用）
//		System.out.println("result of reusing mem");
//		it = reuseMap.keySet().iterator();
//		while (it.hasNext()) {
//			Object key = it.next();
//			System.out.println(key + " " + reuseMap.get(key));
//		}

		//命令列を書き換える
		//その際、冗長なremovemem/addmem命令を除去する
		HashSet set = new HashSet(); //removemem/addmem命令の不要な膜再利用に関わる膜
		it = reuseMap.keySet().iterator();
		while (it.hasNext()) {
			Integer i1 = (Integer)it.next();
			Integer i2 = (Integer)reuseMap.get(i1);
			Integer p1 = (Integer)parent.get(i1);
			Integer p2 = (Integer)parent.get(i2);
			if (reuseMap.containsKey(p1)) {
				p1 = (Integer)reuseMap.get(p1);
			}
			if (p1.equals(p2)) { //親が同じだったら
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
					break;
			}
		}
		
		lit.previous(); //最後のproceed命令の手前に追加
		addUnlockInst(lit, reuseMap, new Integer(0), createdChildren);

		Instruction.changeVar(list, reuseMap);
	}
	private static void addUnlockInst(ListIterator lit, HashMap reuseMap, Integer mem, HashMap children) {
		//子膜を先に処理
		ArrayList c = (ArrayList)children.get(mem);
		if (c != null) {
			Iterator it = c.iterator();
			while (it.hasNext()) {
				addUnlockInst(lit, reuseMap, (Integer)it.next(), children);
			}
		}
		
		if (reuseMap.containsKey(mem)) {
			lit.add(new Instruction(Instruction.UNLOCKMEM, mem));
		}
	}
	
	/**
	 * Listを値とするようなマップに値を追加する。
	 * 指摘されたキーがすでに存在する場合は値のリストにvalueを追加する。
	 * 存在しない場合は新しくリストを登録し、マップに追加する。
	 * @param map マップ
	 * @param key キー
	 * @param value 値
	 */
	private static void addToMap(HashMap map, Object key, Object value) {
		ArrayList list = (ArrayList)map.get(key);
		if (list == null) {
			list = new ArrayList();
			map.put(key, list);
		}
		list.add(value);
	}

	/**
	 * 再利用する膜を決定します。
	 * startで指定された膜内にある膜について、再帰呼び出しを行います。
	 * @param reuseMap 再利用方法を入れるためのマップ
	 * @param reuseMems 再利用される膜の集合を入れるためのセット
	 * @param parent 親膜へのマップ
	 * @param children 子膜集合へのマップ
	 * @param pourMap pour命令の対応を入れたマップ
	 * @param pourMems pour命令の第２引数に含まれる膜のセット
	 * @param start この膜内の膜の再利用を決定する。
	 */
	private static void createReuseMap(HashMap reuseMap, HashSet reuseMems, HashMap parent,
										 HashMap removedChildren, HashMap createdChildren,
										 HashMap pourMap, HashSet pourMems, Integer start) {

		ArrayList list = (ArrayList)createdChildren.get(start);
		if (list == null || list.size() == 0) {
			return;
		}

		Integer start2; //startの、再利用後の変数番号
		if (reuseMap.containsKey(start)) {
			start2 = (Integer)reuseMap.get(start);
		} else {
			start2 = start;
		}
				
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Integer mem = (Integer)it.next();
			//memの再利用元を決める
			 
			Integer candidate = null; //pour命令による再利用候補を１つ保持しておく
			Integer result = null; //決定した再利用先を入れる
			ArrayList list2 = (ArrayList)pourMap.get(mem);
			if (list2 != null) {
				Iterator it2 = list2.iterator();
				while (it2.hasNext()) {
					Integer mem2 = (Integer)it2.next();
					//すでに再利用することが決まっている場合は無視
					if (reuseMems.contains(mem2)) {
						continue;
					}
					
					//候補に登録
					candidate = mem2;
					
					if (parent.get(mem2).equals(start2)) {
						//親膜が同じ膜からのpour命令がある場合は、それを優先
						result = mem2;
						break;
					}
				}
			}
			if (result == null) {
				//上の方法で決まらなかった場合
				if (candidate == null) {
					//共通の親膜を持つ、プロセス文脈のない膜の中から適当に決定。
					//該当する膜がなければ再利用しない
					ArrayList list3 = (ArrayList)removedChildren.get(start2);
					if (list3 != null) {
						Iterator it3 = list3.iterator();
						while (it3.hasNext()) {
							Integer m = (Integer)it3.next();
							if (!pourMems.contains(m) && !reuseMems.contains(m)) {
								result = m;
								break;
							}
						}
					}
				} else {
					//pour命令がある中から適当に決定
					result = candidate;
				}
			}
			if (result != null) {
				reuseMap.put(mem, result);
				reuseMems.add(result);
			}
			//再帰呼び出し
			createReuseMap(reuseMap, reuseMems, parent, removedChildren, createdChildren,
						   pourMap, pourMems, mem);
		}
	}

	//////////////////////////////////////////////////////////////////////
	// アトム再利用関連

	/**
	 * relink命令をgetlink/inheritlink命令に変換し、
	 * getlink/unify命令をボディ命令列の先頭に移動する。
	 * 先頭の命令はspecでなければならない。
	 * @param list 変換する命令列
	 * @return 変換に成功した場合はtrue
	 */
	public static boolean changeOrder(List list) {
		Instruction spec = (Instruction)list.get(0);
		if (spec.getKind() != Instruction.SPEC) {
			return false;
		}
		int nextId = spec.getIntArg2();
		
		ArrayList moveInsts = new ArrayList();
		
		ListIterator it = list.listIterator(1);
		while (it.hasNext()) {
			Instruction inst = (Instruction)it.next();
			switch (inst.getKind()) {
				case Instruction.UNIFY:
					moveInsts.add(inst);
					it.remove();
					break;
				case Instruction.RELINK:
					moveInsts.add(new Instruction(Instruction.GETLINK,  nextId, inst.getIntArg3(), inst.getIntArg4()));
					it.set(new Instruction(Instruction.INHERITLINK,  inst.getIntArg1(), inst.getIntArg2(), nextId, inst.getIntArg5()));
					nextId++;
					break;
				case Instruction.LOCALRELINK:
					moveInsts.add(new Instruction(Instruction.GETLINK,  nextId, inst.getIntArg3(), inst.getIntArg4()));
					it.set(new Instruction(Instruction.LOCALINHERITLINK,  inst.getIntArg1(), inst.getIntArg2(), nextId, inst.getIntArg5()));
					nextId++;
					break;
			}
		}
		list.set(0, Instruction.spec(spec.getIntArg1(), nextId));
		list.addAll(1, moveInsts);
//		spec.data.set(1, new Integer(nextId)); //ローカル変数の数を変更
		return true;
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
//				case Instruction.GETLINK:
//					//後で場所を移動する
//					getlinkInsts.put(inst.getArg1(), inst);
//					break;
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

//		//再利用方法の表示（デバッグ用）
//		
//		it = reuseMap.keySet().iterator();
//		while (it.hasNext()) {
//			Object key = it.next();
//			System.out.println(key + " " + reuseMap.get(key));
//		}
		
		//////////////////////////////////////////////////
		//
		// アトムを再利用するような命令列を生成する
		//

										
//		// アトム再利用をするとgetlink命令の前にinherit命令が来る事があるので、
//		// getlink命令をspec命令の後に移動する。
//		// TO DO 適切な移動場所を見つける
//		it = getlinkInsts.values().iterator();
//		while (it.hasNext()) {
//			list.add(1, it.next());
//		}

		//不要になったremoveatom/freeatom/newatom命令を除去
		ListIterator lit = list.listIterator(/*getlinkInsts.size() + */1);
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
//				case Instruction.GETLINK:
//					//先頭に移動したので除去
//					lit.remove();
//					break;
			}
		}
		//TO DO enqueueatom命令を生成 → 元の命令列にあるものが使えるので不要

		Instruction.changeVar(list, reuseMap);

//		it = list.iterator();
//		while (it.hasNext()) {
//			System.out.println(it.next());
//		}
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

	//////////////////////////////////////////////////////////////
	// ループ化関連

	/**
	 * アトムと引数番号の組を保持するクラス。
	 * @author Ken
	 */	
	private static class Link {
		int atom;
		int pos;
		Link(int atom, int pos) {
			this.atom = atom;
			this.pos = pos;
		}
		public boolean equals(Object o) {
			Link l = (Link)o;
			return this.atom == l.atom && this.pos == l.pos;
		}
		public int hashCode() {
			return atom + pos;
		}
		public String toString() {
			return "(" + atom + ", " + pos + ")";
		}
	}
	/**
	 * 同一ルールの複数回同時適用<br>
	 * とりあえず、次の条件を満たしている場合にのみ処理を行う。
	 * <ul>
	 *  <li>ボディ実行仮引数と実引数が同じである。
	 *  <li>spec命令以外の最初の命令がfindatomで、その第二引数は0である。
	 *  <li>はじめのfindatom命令によって取得されたアトムが再利用されている。
	 * </ul>
	 * 一つ目の条件が満たされない場合の動作は定義されない。（TODO これは何とかする。）
	 * それ以外の条件が満たされない場合は何もしない。
	 * @param head 膜主導マッチング命令列
	 * @param body ボディ命令列
	 */	
	private static void makeLoop(List head, List body) {
		Instruction inst = (Instruction)head.get(0);
		if (inst.getKind() != Instruction.SPEC) {
			return;
		}
		inst = (Instruction)head.get(1);
		if (inst.getKind() != Instruction.FINDATOM || inst.getIntArg2() != 0) {
			return;
		}
		Integer firstAtom = (Integer)inst.getArg1();

		//条件に合致するか検査＋情報収集
		HashMap links = new HashMap(); //newlink命令で生成したリンクの情報
		HashMap functor = new HashMap(); // atom -> functor
		ListIterator lit = body.listIterator();
		while (lit.hasNext()) {
			inst = (Instruction)lit.next();
			switch (inst.getKind()) {
				case Instruction.REMOVEATOM:
//				case Instruction.FREEATOM:
					if (inst.getArg1().equals(firstAtom)) {
						return;
					}
					break;
				case Instruction.NEWLINK:
				case Instruction.LOCALNEWLINK:
					Link l1 = new Link(inst.getIntArg1(), inst.getIntArg2());
					Link l2 = new Link(inst.getIntArg3(), inst.getIntArg4());
					links.put(l1, l2);
					links.put(l2, l1);
					break;
				case Instruction.NEWATOM:
					functor.put(inst.getArg1(), inst.getArg3());
					break;
				case Instruction.FINDATOM:
					functor.put(inst.getArg1(), inst.getArg3());
					break;
				case Instruction.FUNC:
					functor.put(inst.getArg1(), inst.getArg2());
					break;
			}
		}
		HashMap changeMap = new HashMap();

		ArrayList loop = new ArrayList(); //ループ内の命令列
		
		//ループ内命令列の生成
		
		//まずはコピーして変数番号付け替え
		Instruction spec = (Instruction)body.get(0);
		//ループ内命令列で使用する変数の開始値
		int base = spec.getIntArg2(); 
		//もともとの変数→ループ内での変数
		HashMap varMap = new HashMap();
		varMap.put(new Integer(0), new Integer(0)); //本膜
		for (int i = 1; i < base; i++) {
			varMap.put(new Integer(i), new Integer(base + i));
			System.out.println(i + " -> " + (base + i));
		}
		lit = head.subList(2, head.size() - 1).listIterator(); //spec,findatom,reactを除去
		while (lit.hasNext()) {
			loop.add(((Instruction)lit.next()).clone());
		}
		lit = body.listIterator(1);
		while (lit.hasNext()) {
			loop.add(((Instruction)lit.next()).clone());
		}
		Instruction.changeVar(loop, varMap); //specを除去

		//命令列の変更を行う
		varMap = new HashMap();
		varMap.put(firstAtom, firstAtom);
		
		ArrayList moveInsts = new ArrayList();
		ListIterator baseIterator = head.subList(2, head.size() - 1).listIterator(); //１回目用命令列
		ListIterator loopIterator = loop.listIterator(); //ループ内命令列
		while (lit.hasNext()) {
			baseIterator.next();
			inst = (Instruction)loopIterator.next();
			switch (inst.getKind()) {
				case Instruction.DEREF:
					Link l = (Link)links.get(new Link(inst.getIntArg2(), inst.getIntArg3()));
					if (l != null && l.pos == inst.getIntArg4()) {
						varMap.put(inst.getArg1(), new Integer(l.atom));
						loopIterator.remove();
					}
					break;
				case Instruction.FUNC:
					if (varMap.containsKey(inst.getArg1())) {
						Integer atom = (Integer)varMap.get(inst.getArg1());
						if (functor.containsKey(atom)) {
							if (!functor.get(atom).equals(inst.getArg2())) {
								//絶対失敗するので複数回同時適用は行わない
								return;
							}
							//絶対成功するので除去	
							loopIterator.remove();
						}
					}
					break;					
			}
		}

		HashMap changeToNewlink = new HashMap(); //newlinkに変更するリンク -> リンク先
		baseIterator = body.listIterator(1); //１回目用命令列
		loopIterator = loop.listIterator(); // ループ内命令列
		while (lit.hasNext()) {
			Instruction baseInst = (Instruction)baseIterator.next();
			inst = (Instruction)loopIterator.next();
			switch (inst.getKind()) {
				case Instruction.GETLINK:
					int atom = inst.getIntArg2();
					Link l = (Link)links.get(new Link(atom, inst.getIntArg3()));
					if (l != null) { //前回のループのnewlinkによってリンク先が特定できる場合
						int atom2 = l.atom;
						if (varMap.get(new Integer(atom)).equals(new Integer(atom- base)) ||
							varMap.get(new Integer(atom2)).equals(new Integer(atom2 - base))) { //前回のループのnewlink命令が削除されるものの場合
							//getlinkを削除し、inheritlinkをnewlinkに変更
							changeToNewlink.put(inst.getArg1(), l);
							loopIterator.remove();
						}
					}
					break;
				case Instruction.INHERITLINK:
					Integer linkVar = (Integer)inst.getArg3();
					if (changeToNewlink.containsKey(linkVar)) {
						l = (Link)changeToNewlink.get(linkVar);
						//newlinkに変更
						loopIterator.set(Instruction.newlink(inst.getIntArg1(),
															 inst.getIntArg2(),
															 l.atom,
															 l.pos));
															 //inst.getIntArg5()));
						
					}
					break;
				case Instruction.NEWLINK:
					int atomVar = inst.getIntArg1();
					int atomVar2 = inst.getIntArg3();
					if (varMap.get(new Integer(atomVar)).equals(new Integer(atomVar - base)) ||
						varMap.get(new Integer(atomVar2)).equals(new Integer(atomVar2 - base))) { //もともとの変数番号と同じになっている場合
						//最後に移動
						moveInsts.add(baseInst);
						baseIterator.remove();
						loopIterator.remove();
					}
					break;
			}
		}
		
		Instruction.changeVar(loop, varMap);
		
		//proceed命令の前に挿入
		ArrayList looparg = new ArrayList();
		looparg.add(loop);
		body.add(body.size() - 1, new Instruction(Instruction.LOOP, looparg));
		//最後に1回実行する命令を挿入
		body.addAll(body.size() - 1, moveInsts);
	}

}