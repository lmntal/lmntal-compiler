package runtime;

import java.util.*;

class InterpreterReactor {
	AbstractMembrane[] mems;
	Atom[] atoms;
	List vars;
	//List insts;
	InterpreterReactor(
		AbstractMembrane[] mems,
		Atom[] atoms,
		List vars /*, List insts*/
	) {
		Env.n("InterpreterReactor");
		this.mems = mems;
		this.atoms = atoms;
		this.vars = vars;
		//this.insts = insts;
	}

	/** 命令列を解釈する。
	 * @param mems  膜変数のベクタ
	 * @param atoms アトム変数のベクタ
	 * @param vars  その他の変数のベクタ（memsやatomsは廃止してvarsに統合する？）
	 * @param insts 命令列
	 * @param pc    命令列中のプログラムカウンタ
	 * @return 命令列の実行が成功したかどうかを返す
	 */
	boolean interpret(List insts, int pc) {
		Env.p("interpret : " + insts);
		Iterator it;
		Functor func;
		while (pc < insts.size()) {
			Instruction inst = (Instruction) insts.get(pc++);
			Env.p("Do " + inst);
			switch (inst.getKind()) {

				//メモ：LOCALHOGEはHOGEと同じコードでいい。
				//nakajima: 2003-12-12
				//メモ：コメントは引数
				//nakajima: 2003-12-12

				//====その他====ここから====
				case Instruction.DUMMY :
					System.out.println(
						"SYSTEM ERROR: dummy instruction remains: " + inst);
					break;
					//case Instruction.UNDEF:
					//	break; //n-kato
					//====その他====ここまで====

					//====アトムに関係する出力する基本ガード命令====ここから====
				case Instruction.DEREF : //[-dstatom, srcatom, srcpos, dstpos]
					break;

				case Instruction.DEREFATOM : //[-dstatom, srcatom, srcpos]
					break;

				case Instruction.FINDATOM :
					// findatom [-dstatom, srcmem, funcref]
					func = (Functor) inst.getArg3();
					it = mems[inst.getIntArg2()].atoms.iteratorOfFunctor(func);
					while (it.hasNext()) {
						Atom a = (Atom) it.next();
						atoms[inst.getIntArg1()] = a;
						if (interpret(insts, pc))
							return true;
					}
					return false; //n-kato

				case Instruction.GETLINK : //[-link, atom, pos]
					break;
					//====アトムに関係する出力する基本ガード命令====ここまで====

					//====膜に関係する出力する基本ガード命令 ====ここから====
				case Instruction.LOCKMEM :
				case Instruction.LOCALLOCKMEM :
					// lockmem [-dstmem, freelinkatom]
					AbstractMembrane mem = atoms[inst.getIntArg2()].mem;
					if (mem.lock(mems[0])) {
						mems[inst.getIntArg1()] = mem;
						if (interpret(insts, pc))
							return true;
						mem.unlock();
					}
					return false; //n-kato

				case Instruction.ANYMEM :
				case Instruction.LOCALANYMEM : // anymem [-dstmem, srcmem] 
					it = mems[inst.getIntArg2()].mems.iterator();
					while (it.hasNext()) {
						AbstractMembrane submem = (AbstractMembrane) it.next();
						if (submem.lock(mems[0])) {
							mems[inst.getIntArg1()] = submem;
							if (interpret(insts, pc))
								return true;
							submem.unlock();
						}
					}
					return false; //n-kato

				case Instruction.GETMEM : //[-dstmem, srcatom]
					break;
				case Instruction.GETPARENT : //[-dstmem, srcmem]
					break;

					//====膜に関係する出力する基本ガード命令====ここまで====

					//====膜に関係する出力しない基本ガード命令====ここから====
				case Instruction.TESTMEM : //[dstmem, srcatom]
					if (mems[inst.getIntArg1()]
						!= atoms[inst.getIntArg2()].mem)
						return false;
					break; //n-kato
				case Instruction.NORULES : //[srcmem] 
					break;
				case Instruction.NATOMS : //[srcmem, count]
					break;
				case Instruction.NFREELINKS : //[srcmem, count]
					break;
				case Instruction.NMEMS : //[srcmem, count]
					break;
				case Instruction.EQMEM : //[mem1, mem2]
					break;
				case Instruction.NEQMEM : //[mem1, mem2]
					break;
				case Instruction.STABLE : //[srcmem] 
					break;
				case Instruction.LOCK :
				case Instruction.LOCALLOCK : //[srcmem] 
					break;
					//====膜に関係する出力しない基本ガード命令====ここまで====

					//====アトムに関係する出力しない基本ガード命令====ここから====
				case Instruction.FUNC : //[srcatom, funcref]
					break;
				case Instruction.EQATOM : //[atom1, atom2]
					break;
				case Instruction.NEQATOM : //[atom1, atom2]
					break;
					//====アトムに関係する出力しない基本ガード命令====ここまで====

					//====ファンクタに関係する命令====ここから====
				case Instruction.DEREFFUNC : //[-dstfunc, srcatom, srcpos]
					break;
				case Instruction.GETFUNC : //[-func, atom]
					break;
				case Instruction.LOADFUNC : //[-func, funcref]
					break;
				case Instruction.EQFUNC : //[func1, func2]
					break;
				case Instruction.NEQFUNC : //[func1, func2]
					break;
					//====ファンクタに関係する命令====ここまで====

					//====アトムを操作する基本ボディ命令====ここから====
				case Instruction.REMOVEATOM :
				case Instruction.LOCALREMOVEATOM : //[srcatom]
					Atom atom;
					Atom a;
					atom = atoms[inst.getIntArg1()];
					atom.mem.removeAtom(atom);
					break;
				case Instruction.NEWATOM :
				case Instruction.LOCALNEWATOM : //[-dstatom, srcmem, funcref]
					func = (Functor) inst.getArg3();
					atoms[inst.getIntArg1()] = mems[inst.getIntArg2()].newAtom(func);

					break;

				case Instruction.NEWATOMINDIRECT :
				case Instruction.LOCALNEWATOMINDIRECT :
					//[-dstatom, srcmem, func]
					break;
				case Instruction.ENQUEUEATOM :
				case Instruction.LOCALENQUEUEATOM : //[srcatom]
					break;
				case Instruction.DEQUEUEATOM : //[srcatom]
					break;
				case Instruction.FREEATOM : //[srcatom]
					break; //n-kato
				case Instruction.ALTERFUNC :
				case Instruction.LOCALALTERFUNC : //[atom, funcref]
					break;
				case Instruction.ALTERFUNCINDIRECT :
				case Instruction.LOCALALTERFUNCINDIRECT : //[atom, func]
					break;
					//====アトムを操作する基本ボディ命令====ここまで====

					//====アトムを操作する型付き拡張用命令====ここから====
				case Instruction.ALLOCATOM : //[-dstatom, funcref]
					break;

				case Instruction.ALLOCATOMINDIRECT : //[-dstatom, func]
					break;

				case Instruction.COPYATOM :
				case Instruction.LOCALCOPYATOM : //[-dstatom, mem, srcatom]
					break;

					//case Instruction.ADDATOM:
				case Instruction.LOCALADDATOM : //[dstmem, atom]
					break;
					//====アトムを操作する型付き拡張用命令====ここまで====

					//====膜を操作する基本ボディ命令====ここから====
				case Instruction.REMOVEMEM :
				case Instruction.LOCALREMOVEMEM : //[srcmem]
					break;

				case Instruction.NEWMEM :
				case Instruction.LOCALNEWMEM : //[-dstmem, srcmem]
					break;

				case Instruction.NEWROOT : //[-dstmem, srcmem, node]
					break;
				case Instruction.MOVECELLS : //[dstmem, srcmem]
					break;
				case Instruction.ENQUEUEALLATOMS : //[srcmem]
					break;
				case Instruction.FREEMEM : //[srcmem]
					break; //n-kato

				case Instruction.ADDMEM :
				case Instruction.LOCALADDMEM : //[dstmem, srcmem]
					break;

				case Instruction.UNLOCKMEM :
				case Instruction.LOCALUNLOCKMEM : //[srcmem]
					break;
					//====膜を操作する基本ボディ命令====ここまで====

					//====リンクを操作するボディ命令====ここから====
				case Instruction.NEWLINK :
				case Instruction.LOCALNEWLINK: //[atom1, pos1, atom2, pos2]
					atoms[inst.getIntArg1()].mem.newLink(
						(Atom)atoms[inst.getIntArg1()], inst.getIntArg2(),
						(Atom)atoms[inst.getIntArg3()], inst.getIntArg4() );
					break;
				case Instruction.RELINK :
				case Instruction.LOCALRELINK : //[atom1, pos1, atom2, pos2]
					atoms[inst.getIntArg1()].mem.relinkAtomArgs(
						(Atom)atoms[inst.getIntArg1()], inst.getIntArg2(),
						(Atom)atoms[inst.getIntArg3()], inst.getIntArg4() );
					break;
				case Instruction.UNIFY :
				case Instruction.LOCALUNIFY : //[atom1, pos1, atom2, pos2]
					atoms[inst.getIntArg1()].mem.unifyAtomArgs(
						(Atom)atoms[inst.getIntArg1()], inst.getIntArg2(),
						(Atom)atoms[inst.getIntArg3()], inst.getIntArg4() );
					break;

				case Instruction.INHERITLINK :
				case Instruction.LOCALINHERITLINK : //[atom1, pos1, link2]
					atoms[inst.getIntArg1()].mem.inheritLink(
						(Atom)atoms[inst.getIntArg1()], inst.getIntArg2(),
						(Link)vars.get(inst.getIntArg3()) );
					break;
					//====リンクを操作するボディ命令====ここまで====

					//====自由リンク管理アトム自動処理のためのボディ命令====ここから====
				case Instruction.REMOVEPROXIES : //[srcmem]
					break;
				case Instruction.REMOVETOPLEVELPROXIES : //[srcmem]
					break;
				case Instruction.INSERTPROXIES : //[parentmem,childmem]
					break;
				case Instruction.REMOVETEMPORARYPROXIES : //[srcmem]
					break;
					//====自由リンク管理アトム自動処理のためのボディ命令====ここまで====

					//====ルールを操作するボディ命令====ここから====
				case Instruction.LOADRULESET: //[dstmem, ruleset]
					((AbstractMembrane)mems[inst.getIntArg1()]).loadRuleset(
						(Ruleset)inst.getArg2() );
					break;
				case Instruction.COPYRULES:   //[dstmem, srcmem]
					break;
				case Instruction.CLEARRULES:  //[dstmem, srcmem]
					break;
					//====ルールを操作するボディ命令====ここまで====

					//====型付きでないプロセス文脈をコピーまたは廃棄するための命令====ここから====
				case Instruction.RECURSIVELOCK : //[srcmem]
					break;
				case Instruction.RECURSIVEUNLOCK : //[srcmem]
					break;
				case Instruction.COPYMEM : //[-dstmem, srcmem]
					break;
				case Instruction.DROPMEM : //[srcmem]
					break;
					//====型付きでないプロセス文脈をコピーまたは廃棄するための命令====ここまで====

					//====制御命令====ここから====
				case Instruction.REACT :
					Rule rule = (Rule) inst.getArg1();
					List bodyInsts = (List) rule.body;
					Instruction spec = (Instruction) bodyInsts.get(0);
					int formals = spec.getIntArg1();
					int locals = spec.getIntArg2();
					
// // ArrayIndexOutOfBoundsException がでたので一時的に変更
// if (locals < 10) locals = 10;
					
					AbstractMembrane[] bodymems = new AbstractMembrane[locals];
					Atom[] bodyatoms = new Atom[locals];
					List memformals = (List) inst.getArg2();
					List atomformals = (List) inst.getArg3();
					for (int i = 0; i < memformals.size(); i++) {
						bodymems[i] =
							mems[((Integer) memformals.get(i)).intValue()];
					}
					for (int i = 0; i < atomformals.size(); i++) {
						bodyatoms[i] =
							atoms[((Integer) atomformals.get(i)).intValue()];
					}
					InterpreterReactor ir =
						new InterpreterReactor(
							bodymems,
							bodyatoms,
							new ArrayList());
					ir.interpret(bodyInsts, 0);
					return true; //n-kato

				case Instruction.PROCEED :
					return true;

				case Instruction.SPEC:
					break;

				case Instruction.BRANCH :
					List subinsts;
					subinsts = (List) ((List) inst.getArg1()).get(0);
					if (interpret(subinsts, 0))
						return true;
					break; //nakajima

				case Instruction.LOOP :
					subinsts = (List) ((List) inst.getArg1()).get(0);
					while (interpret(subinsts, 0)) {
					}
					break; //nakajima, n-kato

				case Instruction.RUN :
					subinsts = (List) ((List) inst.getArg1()).get(0);
					interpret(subinsts, 0);
					break; //nakajima

				case Instruction.NOT :
					subinsts = (List) ((List) inst.getArg1()).get(0);
					if (interpret(subinsts, 0))
						return false;
					break; //n-kato

					//====制御命令====ここまで====

					//====型検査のためのガード命令====ここから====
				case Instruction.ISGROUND : //[link]
					break;

				case Instruction.ISINT : //[atom]
					break;
				case Instruction.ISFLOAT : //[atom]
					break;
				case Instruction.ISSTRING : //[atom]
					break;
				case Instruction.ISINTFUNC : //[func]
					break;
				case Instruction.ISFLOATFUNC : //[func]
					break;
				case Instruction.ISSTRINGFUNC : //[func]
					break;
					//====型検査のためのガード命令====ここまで====

					//====組み込み機能に関する命令====ここから====
				case Instruction.INLINE : //[atom, inlineref]
					Inline.callInline( atoms[inst.getIntArg1()], inst.getIntArg2() );
					break;
					//====組み込み機能に関する命令====ここまで====
					
					//====整数用の組み込みボディ命令====ここから====
				case Instruction.IADD : //[-dstintatom, intatom1, intatom2]
					break;
				case Instruction.ISUB : //[-dstintatom, intatom1, intatom2]
					break;
				case Instruction.IMUL : //[-dstintatom, intatom1, intatom2]
					break;
				case Instruction.IDIV : //[-dstintatom, intatom1, intatom2]
					break;
				case Instruction.INEG : //[-dstintatom, intatom1, intatom2]
					break;
				case Instruction.IMOD : //[-dstintatom, intatom1, intatom2]
					break;
				case Instruction.INOT : //[-dstintatom, intatom1, intatom2]
					break;
				case Instruction.IAND : //[-dstintatom, intatom1, intatom2]
					break;
				case Instruction.IOR : //[-dstintatom, intatom1, intatom2]
					break;
				case Instruction.IXOR : //[-dstintatom, intatom1, intatom2]
					break;
				case Instruction.ISHL : //[-dstintatom, intatom1, intatom2]
					break;
				case Instruction.ISHR : //[-dstintatom, intatom1, intatom2]
					break;
				case Instruction.ISAR : //[-dstintatom, intatom1, intatom2]
					break;
				case Instruction.IADDFUNC : //[-dstintfunc, intfunc1, intfunc2]
					break;
				case Instruction.ISUBFUNC : //[-dstintfunc, intfunc1, intfunc2]
					break;
				case Instruction.IMULFUNC : //[-dstintfunc, intfunc1, intfunc2]
					break;
				case Instruction.IDIVFUNC : //[-dstintfunc, intfunc1, intfunc2]
					break;
				case Instruction.INEGFUNC : //[-dstintfunc, intfunc1, intfunc2]
					break;
				case Instruction.IMODFUNC : //[-dstintfunc, intfunc1, intfunc2]
					break;
				case Instruction.INOTFUNC : //[-dstintfunc, intfunc1, intfunc2]
					break;
				case Instruction.IANDFUNC : //[-dstintfunc, intfunc1, intfunc2]
					break;
				case Instruction.IORFUNC : //[-dstintfunc, intfunc1, intfunc2]
					break;
				case Instruction.IXORFUNC : //[-dstintfunc, intfunc1, intfunc2]
					break;
				case Instruction.ISHLFUNC : //[-dstintfunc, intfunc1, intfunc2]
					break;
				case Instruction.ISHRFUNC : //[-dstintfunc, intfunc1, intfunc2]
					break;
				case Instruction.ISARFUNC : //[-dstintfunc, intfunc1, intfunc2]
					break;
					//====整数用の組み込みボディ命令====ここまで====

					//====整数用の組み込みガード命令====ここから====
				case Instruction.ILT : //[intatom1, intatom2]
					break;
				case Instruction.ILE : //[intatom1, intatom2]
					break;
				case Instruction.IGT : //[intatom1, intatom2]
					break;
				case Instruction.IGE : //[intatom1, intatom2]
					break;
				case Instruction.ILTFUNC : //[intfunc1, intfunc2]
					break;
				case Instruction.ILEFUNC : //[intfunc1, intfunc2]
					break;
				case Instruction.IGTFUNC : //[intfunc1, intfunc2]
					break;
				case Instruction.IGEFUNC : //[intfunc1, intfunc2]
					break;
					//====整数用の組み込みガード命令====ここまで====

				default :
					System.out.println(
						"SYSTEM ERROR: Invalid instruction: " + inst);
					break;
			}
		}
		return false;
	}
}

// TODO 【重要】マッチング検査の途中で取得したロックを全て解放する必要がある
// memo：ロックを開放する単位がリストになるように修正

/**
 * compile.RuleCompiler によって生成される。
 * @author n-kato, nakajima
 */
public final class InterpretedRuleset extends Ruleset {
	/** ルールセット番号 */
	private int id;
	private static int lastId = 600;

	/** とりあえずルールの配列として実装 */
	public List rules;

	/**
	 * RuleCompiler では、まず生成してからデータを入れ込む。
	 * ので、特になにもしない
	 */
	public InterpretedRuleset() {
		rules = new ArrayList();
		id = ++lastId;
	}

	/**
	 * あるルールについてアトム主導テストを行い、マッチすれば適用する
	 * @return ルールを適用した場合はtrue
	 */
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		Iterator it = rules.iterator();
		while (it.hasNext()) {
			Rule r = (Rule) it.next();
			result
				|= matchTest(mem, atom, r.atomMatch, (Instruction) r.body.get(0));
		}
		return result;
	}

	/**
	 * あるルールについて膜主導テストを行い、マッチすれば適用する
	 * @return ルールを適用した場合はtrue
	 */
	public boolean react(Membrane mem) {
		boolean result = false;
		Iterator it = rules.iterator();
		while (it.hasNext()) {
			Rule r = (Rule) it.next();
			result
				|= matchTest(mem, null, r.memMatch, (Instruction) r.body.get(0));
		}
		return result;
	}

	/**
	 * 膜主導かアトム主導テストを行い、マッチすれば適用する
	 * @return ルールを適用した場合はtrue
	 */
	private boolean matchTest(
		Membrane mem,
		Atom atom,
		List matchInsts,
		Instruction spec) {
		int formals = spec.getIntArg1();
		int locals = spec.getIntArg2();
		
// ArrayIndexOutOfBoundsException がでたので一時的に変更
if (formals < 10) formals = 10;
		
		AbstractMembrane[] mems = new AbstractMembrane[formals];
		Atom[] atoms = new Atom[formals];
		mems[0] = mem;
		atoms[1] = atom;
		InterpreterReactor ir =
			new InterpreterReactor(mems, atoms, new ArrayList());
		return ir.interpret(matchInsts, 0);
	}
	
	/**
	 * ルールを適用する。<br>
	 * 引数の膜と、引数のアトムの所属膜はすでにロックされているものとする。
	 * @param ruleid 適用するルール
	 * @param memArgs 実引数のうち、膜であるもの
	 * @param atomArgs 実引数のうち、アトムであるもの
	 * @author nakajima
	 * @deprecated
	 * 
	 */
	private void body(List rulebody, AbstractMembrane[] mems, Atom[] atoms) {
		/*	Iterator it = rulebody.iterator();
			while(it.hasNext()){
				Instruction hoge = (Instruction)it.next();
		
		
				//ここ以下のswitchは移動予定 bodyとguardを両方扱うメソッドを定義する予定
			switch (hoge.getID()){
			case Instruction.DEREF:
				//deref [-dstatom, +srcatom, +srcpos, +dstpos]
				//if (atomArgs[1].args[srcpos] == atomArgs[1].args[dstpos]) {
					//リンク先のアトムをdstatomに代入する。
				//            }
				break;
		
			case Instruction.GETMEM:
				//getmem [-dstmem, srcatom]
				//TODO 回答：聞く:「膜の所属膜」とは？→[1]が膜のときはGETPARENTを使う。「親膜」の意味。
				//ruby版だとgetparentと同じ実装になってるのでとりあえずそうする
				//コメント：Java版では命令ごとに引数の型を固定するために、名前を分けることにしました
		        
					memArgs[0] = memArgs[1].mem;
					//TODO ルール実行中の変数ベクタを参照渡しして、引数で間接参照する設計にする方がよい。
					// そうしないと、出力引数がうまく反映しない。
					break;
		
			case Instruction.GETPARENT:
				//getparent [-dstmem, srcmem] 
				memArgs[0] = memArgs[1].mem;
				break;
		
			case Instruction.ANYMEM:
				//anymem [??dstmem, srcmem]
				for (int i = 0; i <  memArgs[1].mems.size(); i++ ){
					//ノンブロッキングでのロック取得を試みる。
					//if(成功){各子膜をdstmemに代入する}
				}
				break;
		
			case Instruction.FINDATOM:
				// findatom [dstatom, srcmem, func]
				ListIterator i = memArgs[1].atom.iterator();
				while (i.hasNext()){
					Atom a;
					a = (Atom)i.next();
					if ( a.functor == atomArgs[1]){
						atomArgs[0] = a;
					}
				}
				break;
		
			case Instruction.FUNC:
				//func [srcatom, func]
				if (atomArgs[0].functor == func){
					//同じだった
				} else {
					//違ってた
				}
				break;
		
			case Instruction.NORULES:
				//norules [srcmem]
				if(memArgs[0].rules.isEmpty()){
					//ルールが存在しないことを確認
				} else {
					//ルールが存在していることを確認
				}
				break;
		
			case Instruction.NATOMS:
				// natoms [srcmem, count]
				//if (memArgs[0].atoms.size() == count) { //確認した  }
				break;
		
			case Instruction.NFREELINKS:
				//nfreelinks [srcmem, count]
				//if (memArgs[0].freeLinks.size() == count) { //確認した  }
				break;
		
			case Instruction.NMEMS:
				//nmems [srcmem, count]
				//if (memArgs[0].mems.size() == count) { //確認した  }
				break;
		
			case Instruction.EQ:
				//eq [atom1, atom2]
				//eq [mem1, mem2]
				if(memArgs.length == 0){
					if (atomArgs[0] == atomArgs[1]){
						//同一のアトムを参照
					}
				} else {
					if (memArgs[0] == memArgs[1]){
						//同一の膜を参照
					}
				} 
				break;
		
			case Instruction.NEQ:
				//neq [atom1, atom2]
				//neq [mem1, mem2]
				if(memArgs.length == 0){
					if (atomArgs[0] != atomArgs[1]){
						//同一のアトムを参照
					}
				} else {
					if (memArgs[0] != memArgs[1]){
						//同一の膜を参照
					}
				} 
				break;
		
			case Instruction.REMOVEATOM:
				//removeatom [srcatom]
		        
				break;
		
			case Instruction.REMOVEMEM:
				//removemem [srcmem]
		
				break;
		
			case Instruction.INSERTPROXIES:
				//insertproxies [parentmem M], [srcmem N]
		        
				break;
		
			case Instruction.REMOVEPROXIES:
		
				break;
		
			case Instruction.NEWATOM:
				//newatom [dstatom, srcmem, func] 
				memArgs[1].atoms.add(atomArgs[1]);
				atomArgs[0] = atomArgs[1];
				break;
		
			case Instruction.NEWMEM:
				//newmem [dstmem, srcmem] 
				memArgs[1] = new Membrane(memArgs[0]);
				memArgs[0].mems.add(memArgs[1]);
				break;
		
			case Instruction.NEWLINK:
				//newlink [atom1, pos1, atom2, pos2]
				//atomArgs[0].args[pos1]    atomArgs[1].args[pos2]
				break;
		
			case Instruction.RELINK:
				//relink [atom1, pos1, atom2, pos2]
		        
				break;
			case Instruction.UNIFY:
				break;
			case Instruction.DEQUEUEATOM:
				break;
			case Instruction.DEQUEUEMEM:
				break;
			case Instruction.MOVEMEM:
				break;
			case Instruction.RECURSIVELOCK:
				break;
			case Instruction.RECURSIVEUNLOCK:
				break;
			case Instruction.COPY:
				break;
			case Instruction.NOT:
				break;
			case Instruction.STOP:
				break;
			case Instruction.REACT:
				break;
		
			 default:
				System.out.println("Invalid rule");
				break;
			}
		}*/

	}
	public String toString() {
		StringBuffer s = new StringBuffer("");
		Iterator l;
		l = rules.iterator();
		while (l.hasNext())
			s.append(((Rule) l.next()).toString() + " ");
		return "@" + id + "  " + s;
	}

	public void showDetail() {
		Env.p("InterpretedRuleset.showDetail " + this);
		Iterator l;
		l = rules.listIterator();
		while (l.hasNext()) {
			Rule r = ((Rule) l.next());
			r.showDetail();
		}
	}
}
