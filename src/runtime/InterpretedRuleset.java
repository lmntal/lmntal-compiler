package runtime;

import java.util.*;

/**
 * compile.RulesetCompiler によって生成される。
 * @author hara, nakajima, n-kato
 */
public final class InterpretedRuleset extends Ruleset {
	/** このルールセットのローカルID */
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
			if (r.atomMatch.size() == 1) continue; // debug表示抑制用
			if (matchTest(mem, atom, r.atomMatch)) {
				result = true;
				//if (!mem.isCurrent()) return true;
				return true;
			}
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
			if (matchTest(mem, null, r.memMatch)) {
				result = true;
				return true;
				//if (!mem.isCurrent()) return true;
			}
		}
		return result;
	}

	/**
	 * 膜主導かアトム主導テストを行い、マッチすれば適用する
	 * @return ルールを適用した場合はtrue
	 */
	private boolean matchTest(Membrane mem, Atom atom, List matchInsts) {
		Instruction spec = (Instruction)matchInsts.get(0);
		int formals = spec.getIntArg1();
		int locals  = spec.getIntArg2();
		if (locals == 0) {
			System.out.println("SYSTEM DEBUG REPORT: an old version of spec instruction was detected");
			locals = formals;
		}
// ArrayIndexOutOfBoundsException がでたので一時的に変更
//if (formals < 10) formals = 10;
		
		InterpretiveReactor ir = new InterpretiveReactor(locals);
		ir.mems[0] = mem;
		if (atom != null) { ir.atoms[1] = atom; }
  		return ir.interpret(matchInsts, 0);
  	}
	public String toString() {
		String ret = "@" + id;
		if (Env.verbose >= Env.VERBOSE_EXPANDRULES) {
			ret += dumpRules();
		}
		return ret;
	}
	public String dumpRules() {
		StringBuffer s = new StringBuffer("");
		Iterator it = rules.iterator();
		while (it.hasNext()) {
			s.append(" ");
			s.append((Rule)it.next());
		}
		return s.toString();
	}

	public void showDetail() {
		if (Env.verbose >= Env.VERBOSE_SHOWRULES)
			Env.p("Compiled Ruleset @" + id + dumpRules());
		Iterator l;
		l = rules.listIterator();
		while (l.hasNext()) {
			Rule r = ((Rule) l.next());
			r.showDetail();
		}
	}
}

////////////////////////////////////////////////////////////////

/**
 * インタプリタが使う変数ベクタを表すクラス。
 * <p>
 * 現在は、2つの配列と1つのリストを生成し、互いに変数番号が重複しないように使用している。
 * @author hara,nakajima,n-kato
 */

class InterpretiveReactor {
	/** 膜変数のベクタ */
	AbstractMembrane[] mems;
	/** アトム変数のベクタ */
	Atom[] atoms;
	/** その他の変数のベクタ（memsやatomsは廃止してvarsに統合する？）*/
	List vars;
	
	InterpretiveReactor(int size) {
		initVector(size);
	}
	private void initVector(int size) {
		this.mems  = new AbstractMembrane[size];
		this.atoms = new Atom[size];
		this.vars  = new ArrayList();
		//ArrayList newvars = new ArrayList(size);
		for (int i = 0; i < size; i++) {
			this.vars.add(null);
		}
	}
	/** 変数ベクタを拡張する */
	private void extendVector(int size) {
		int oldsize = mems.length;
		if (oldsize >= size) return;
		AbstractMembrane[] oldmems = mems;
		Atom[] oldatoms = atoms;
		
		this.mems  = new AbstractMembrane[size];
		this.atoms = new Atom[size];
		
		for (int i = 0; i < oldsize; i++) {
			mems[i] = oldmems[i];
			atoms[i] = oldatoms[i];
		}
		for (int i = oldsize; i < size; i++) {
			this.vars.add(null);
		}
	}
	
	private void reloadVars(InterpretiveReactor irSrc,
			int size, List memargs, List atomargs, List varargs) {
		AbstractMembrane[] srcmems  = irSrc.mems;
		Atom[]             srcatoms = irSrc.atoms;
		List               srcvars  = irSrc.vars;
		initVector(size);
				
		int memcount  = memargs.size();
		int atomcount = atomargs.size();
		for (int i = 0; i < memcount; i++) {
			mems[i] =
			   srcmems[((Integer) memargs.get(i)).intValue()];
		}
		for (int i = 0; i < atomcount; i++) {
			atoms[i + memcount] =
			   srcatoms[((Integer) atomargs.get(i)).intValue()];
		}
		for (int i = 0; i < varargs.size(); i++) {
			vars.set(i + memcount + atomcount,
				srcvars.get(((Integer) varargs.get(i)).intValue()));
		}
	}

	private void changeVars(InterpretiveReactor irSrc,
			List memargs, List atomargs, List varargs) {
		AbstractMembrane[] srcmems  = irSrc.mems;
		Atom[]             srcatoms = irSrc.atoms;
		List               srcvars  = irSrc.vars;
		int size = memargs.size();
		if (size < atomargs.size()) size = atomargs.size();
		if (size < varargs.size())  size = varargs.size();
		initVector(size);
		for (int i = 0; i < size; i++) {
			if (memargs.get(i) != null)
				mems[i]  = srcmems[((Integer) memargs.get(i)).intValue()];
			else if (atomargs.get(i) != null)
				atoms[i] = srcatoms[((Integer) atomargs.get(i)).intValue()];
			else
				vars.set(i, srcvars.get(((Integer) varargs.get(i)).intValue()));
		}
	}
	


	/** 引数に与えられた命令列を解釈する。
	 * @param insts 命令列
	 * @param pc    命令列中のプログラムカウンタ
	 * @return 命令列の実行が成功したかどうかを返す
	 */
	boolean interpret(List insts, int pc) {
		//Env.p("interpret : " + insts);
		Iterator it;
		Atom atom;
		AbstractMembrane mem;
		Link link;
		Functor func;
		while (pc < insts.size()) {
			Instruction inst = (Instruction) insts.get(pc++);
			if (Env.debug >= Env.DEBUG_TRACE) Env.d("Do " + inst);
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
					link = atoms[inst.getIntArg2()].args[inst.getIntArg3()];
					if (link.getPos() != inst.getIntArg4()) return false;
					atoms[inst.getIntArg1()] = link.getAtom();
					break; //n-kato
				case Instruction.DEREFATOM : // [-dstatom, srcatom, srcpos]
					link = atoms[inst.getIntArg2()].args[inst.getIntArg3()];
					atoms[inst.getIntArg1()] = link.getAtom();
					break; //n-kato
				case Instruction.DEREFLINK : //[-dstatom, srclink, dstpos]
					link = (Link)vars.get(inst.getIntArg2());
					if (link.getPos() != inst.getIntArg3()) return false;
					atoms[inst.getIntArg1()] = link.getAtom();
					break; //mizuno
				case Instruction.FINDATOM : // [-dstatom, srcmem, funcref]
					func = (Functor) inst.getArg3();
					it = mems[inst.getIntArg2()].atoms.iteratorOfFunctor(func);
					while (it.hasNext()) {
						Atom a = (Atom) it.next();
						atoms[inst.getIntArg1()] = a;
						if (interpret(insts, pc))
							return true;
					}
					return false; //n-kato
					//====アトムに関係する出力する基本ガード命令====ここまで====

					//====膜に関係する出力する基本ガード命令 ====ここから====
				case Instruction.LOCKMEM :
				case Instruction.LOCALLOCKMEM :
					// lockmem [-dstmem, freelinkatom]
					mem = atoms[inst.getIntArg2()].mem;
					if (mem.lock()) {
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
						if (submem.lock()) {
							mems[inst.getIntArg1()] = submem;
							if (interpret(insts, pc))
								return true;
							submem.unlock();
						}
					}
					return false; //n-kato
				case Instruction.LOCK :
				case Instruction.LOCALLOCK : //[srcmem] 
					mem = mems[inst.getIntArg1()];
					if (mem.lock()) {
						if (interpret(insts, pc))
							return true;
						mem.unlock();						
					}
					return false; //n-kato

				case Instruction.GETMEM : //[-dstmem, srcatom]
					mems[inst.getIntArg1()] = atoms[inst.getIntArg2()].mem;
					break; //n-kato
				case Instruction.GETPARENT : //[-dstmem, srcmem]
					mem = mems[inst.getIntArg2()].parent;
					if (mem == null) return false;
					mems[inst.getIntArg1()] = mem;
					break; //n-kato

					//====膜に関係する出力する基本ガード命令====ここまで====

					//====膜に関係する出力しない基本ガード命令====ここから====
				case Instruction.TESTMEM : //[dstmem, srcatom]
					if (mems[inst.getIntArg1()] != atoms[inst.getIntArg2()].mem) return false;
					break; //n-kato
				case Instruction.NORULES : //[srcmem] 
					if (mems[inst.getIntArg1()].hasRules()) return false;
					break; //n-kato
				case Instruction.NFREELINKS : //[srcmem, count]
					mem = mems[inst.getIntArg1()];
					if (mem.atoms.getAtomCountOfFunctor(Functor.INSIDE_PROXY) != inst.getIntArg2())
						return false;
					break;
				case Instruction.NATOMS : //[srcmem, count]
					if (mems[inst.getIntArg1()].atoms.getNormalAtomCount() != inst.getIntArg2()) return false;
					break; //n-kato
				case Instruction.NMEMS : //[srcmem, count]
					if (mems[inst.getIntArg1()].mems.size() != inst.getIntArg2()) return false;
					break; //n-kato
				case Instruction.EQMEM : //[mem1, mem2]
					if (mems[inst.getIntArg1()] != mems[inst.getIntArg2()]) return false;
					break; //n-kato
				case Instruction.NEQMEM : //[mem1, mem2]
					if (mems[inst.getIntArg1()] == mems[inst.getIntArg2()]) return false;
					break; //n-kato
				case Instruction.STABLE : //[srcmem] 
					if (!mems[inst.getIntArg1()].isStable()) return false;
					break; //n-kato
					//====膜に関係する出力しない基本ガード命令====ここまで====

					//====アトムに関係する出力しない基本ガード命令====ここから====
				case Instruction.FUNC : //[srcatom, funcref]
					if (!((Functor)inst.getArg2()).equals(atoms[inst.getIntArg1()].getFunctor())) 
						return false;
					break; //n-kato
				case Instruction.NOTFUNC : //[srcatom, funcref]
					if (((Functor)inst.getArg2()).equals(atoms[inst.getIntArg1()].getFunctor())) 
						return false;
					break; //n-kato
				case Instruction.EQATOM : //[atom1, atom2]
					if (atoms[inst.getIntArg1()] != atoms[inst.getIntArg2()]) return false;
					break; //n-kato
				case Instruction.NEQATOM : //[atom1, atom2]
					if (atoms[inst.getIntArg1()] == atoms[inst.getIntArg2()]) return false;
					break; //n-kato
				case Instruction.SAMEFUNC: //[atom1, atom2]
					if (!atoms[inst.getIntArg1()].getFunctor().equals(atoms[inst.getIntArg2()].getFunctor())) return false;
					break; //n-kato
					//====アトムに関係する出力しない基本ガード命令====ここまで====

					//====ファンクタに関係する命令====ここから====
				case Instruction.DEREFFUNC : //[-dstfunc, srcatom, srcpos]
					vars.set(inst.getIntArg1(), atoms[inst.getIntArg2()].args[inst.getIntArg3()].getAtom().getFunctor());
					break; //nakajima 2003-12-21, n-kato
				case Instruction.GETFUNC : //[-func, atom]
					vars.set(inst.getIntArg1(), atoms[inst.getIntArg2()].getFunctor());
					break; //nakajima 2003-12-21, n-kato
				case Instruction.LOADFUNC : //[-func, funcref]
					vars.set(inst.getIntArg1(), (Functor)inst.getArg2());
					break;//nakajima 2003-12-21, n-kato
				case Instruction.EQFUNC : //[func1, func2]
					if (!vars.get(inst.getIntArg1()).equals(vars.get(inst.getIntArg2()))) return false;
					break; //nakajima, n-kato
				case Instruction.NEQFUNC : //[func1, func2]
					if (vars.get(inst.getIntArg1()).equals(vars.get(inst.getIntArg2()))) return false;
					break; //nakajima, n-kato
					//====ファンクタに関係する命令====ここまで====

					//====アトムを操作する基本ボディ命令====ここから====
				case Instruction.REMOVEATOM :
				case Instruction.LOCALREMOVEATOM : //[srcatom, srcmem, funcref]
					atom = atoms[inst.getIntArg1()];
					atom.mem.removeAtom(atom);
					break; //n-kato
				case Instruction.NEWATOM :
				case Instruction.LOCALNEWATOM : //[-dstatom, srcmem, funcref]
					func = (Functor) inst.getArg3();
					atoms[inst.getIntArg1()] = mems[inst.getIntArg2()].newAtom(func);
					break; //n-kato
				case Instruction.NEWATOMINDIRECT :
				case Instruction.LOCALNEWATOMINDIRECT : //[-dstatom, srcmem, func]
					atoms[inst.getIntArg1()] = mems[inst.getIntArg2()].newAtom((Functor)(vars.get(inst.getIntArg3())));
					break; //nakajima 2003-12-27, 2004-01-03, n-kato
				case Instruction.ENQUEUEATOM :
				case Instruction.LOCALENQUEUEATOM : //[srcatom]
					atom = atoms[inst.getIntArg1()];
					atom.mem.enqueueAtom(atom);
					break; //n-kato
				case Instruction.DEQUEUEATOM : //[srcatom]
					atom = atoms[inst.getIntArg1()];
					atom.dequeue();
					break; //n-kato
				case Instruction.FREEATOM : //[srcatom]
					break; //n-kato
				case Instruction.ALTERFUNC :
				case Instruction.LOCALALTERFUNC : //[atom, funcref]
					atom = atoms[inst.getIntArg1()];
					atom.mem.alterAtomFunctor(atom,(Functor)inst.getArg2());
					break; //n-kato
				case Instruction.ALTERFUNCINDIRECT :
				case Instruction.LOCALALTERFUNCINDIRECT : //[atom, func]
					atom = atoms[inst.getIntArg1()];
					atom.mem.alterAtomFunctor(atom,(Functor)(vars.get(inst.getIntArg2())));
					break; //nakajima 2003-12-27, 2004-01-03, n-kato
					//====アトムを操作する基本ボディ命令====ここまで====

					//====アトムを操作する型付き拡張用命令====ここから====
				case Instruction.ALLOCATOM : //[-dstatom, funcref]
					atoms[inst.getIntArg1()] = new Atom(null, (Functor)inst.getArg2());
					break; //nakajima 2003-12-27, n-kato

				case Instruction.ALLOCATOMINDIRECT : //[-dstatom, func]
					atoms[inst.getIntArg1()] = new Atom(null, (Functor)(vars.get(inst.getIntArg2())));
					break; //nakajima 2003-12-27, 2004-01-03, n-kato

				case Instruction.COPYATOM :
				case Instruction.LOCALCOPYATOM : //[-dstatom, mem, srcatom]
					atoms[inst.getIntArg1()] = mems[inst.getIntArg2()].newAtom(atoms[inst.getIntArg3()].getFunctor());
					break; //nakajima, n-kato

					//case Instruction.ADDATOM:
				case Instruction.LOCALADDATOM : //[dstmem, atom]
					mems[inst.getIntArg1()].addAtom(atoms[inst.getIntArg2()]);
					break; //nakajima 2003-12-27, n-kato
					//====アトムを操作する型付き拡張用命令====ここまで====

					//====膜を操作する基本ボディ命令====ここから====
				case Instruction.REMOVEMEM :
				case Instruction.LOCALREMOVEMEM : //[srcmem, parentmem]
					mem = mems[inst.getIntArg1()];
					mem.parent.removeMem(mem);
					break; //n-kato
				case Instruction.NEWMEM: //[-dstmem, srcmem]
					mem = mems[inst.getIntArg2()].newMem();
					mems[inst.getIntArg1()] = mem;
					break; //n-kato
				case Instruction.LOCALNEWMEM : //[-dstmem, srcmem]
					mem = ((Membrane)mems[inst.getIntArg2()]).newLocalMembrane();
					mems[inst.getIntArg1()] = mem;
					break; //n-kato
				case Instruction.ALLOCMEM: //[-dstmem]
					mem = ((Task)mems[0].getTask()).createFreeMembrane();
					mems[inst.getIntArg1()] = mem;
					break; //n-kato

				case Instruction.NEWROOT : //[-dstmem, srcmem, node]
					// 仕様検討中
					AbstractMachine machine = RemoteMachine.connectRuntime((String)inst.getArg3());
					mems[inst.getIntArg1()] = machine.newTask(mems[inst.getIntArg2()]).getRoot();
					break;
				case Instruction.MOVECELLS : //[dstmem, srcmem]
					mems[inst.getIntArg1()].moveCellsFrom(mems[inst.getIntArg2()]);
					break; //nakajima 2004-01-04, n-kato
				case Instruction.ENQUEUEALLATOMS : //[srcmem]
					break;
				case Instruction.FREEMEM : //[srcmem]
					break; //n-kato

				case Instruction.ADDMEM :
				case Instruction.LOCALADDMEM : //[dstmem, srcmem]
					mems[inst.getIntArg2()].moveTo(mems[inst.getIntArg1()]);
					break;//nakajima 2004-01-04, n-kato

				case Instruction.UNLOCKMEM :
				case Instruction.LOCALUNLOCKMEM : //[srcmem]
					mems[inst.getIntArg1()].unlock();
					break; //n-kato

				case Instruction.LOCALSETMEMNAME: //[dstmem, name]
				case Instruction.SETMEMNAME: //[dstmem, name]
					mems[inst.getIntArg1()].setName((String)inst.getArg2());
					break; //n-kato


					//====膜を操作する基本ボディ命令====ここまで====

					//====リンクに関係する出力するガード命令====ここから====
				case Instruction.GETLINK : //[-link, atom, pos]
					link = atoms[inst.getIntArg2()].args[inst.getIntArg3()];
					vars.set(inst.getIntArg1(),link); //3->1 by mizuno
					break; //n-kato
				case Instruction.ALLOCLINK : //[-link, atom, pos]
					link = new Link(atoms[inst.getIntArg2()], inst.getIntArg3());
					vars.set(inst.getIntArg1(),link);
					break; //n-kato
					//====リンクに関係する出力するガード命令====ここまで====

					//====リンクを操作するボディ命令====ここから====
				case Instruction.NEWLINK:		 //[atom1, pos1, atom2, pos2, mem1]
				case Instruction.LOCALNEWLINK:	 //[atom1, pos1, atom2, pos2 (,mem1)]
					atoms[inst.getIntArg1()].mem.newLink(
						atoms[inst.getIntArg1()], inst.getIntArg2(),
						atoms[inst.getIntArg3()], inst.getIntArg4() );
					break; //n-kato
				case Instruction.RELINK:		 //[atom1, pos1, atom2, pos2, mem]
				case Instruction.LOCALRELINK:	 //[atom1, pos1, atom2, pos2 (,mem)]
					atoms[inst.getIntArg1()].mem.relinkAtomArgs(
						atoms[inst.getIntArg1()], inst.getIntArg2(),
						atoms[inst.getIntArg3()], inst.getIntArg4() );
					break; //n-kato
				case Instruction.UNIFY:		//[atom1, pos1, atom2, pos2, mem]
				case Instruction.LOCALUNIFY:	//[atom1, pos1, atom2, pos2 (,mem)]
					mems[0].unifyAtomArgs(
						atoms[inst.getIntArg1()], inst.getIntArg2(),
						atoms[inst.getIntArg3()], inst.getIntArg4() );
					break; //n-kato

				case Instruction.INHERITLINK:		 //[atom1, pos1, link2, mem]
				case Instruction.LOCALINHERITLINK:	 //[atom1, pos1, link2 (,mem)]
					atoms[inst.getIntArg1()].mem.inheritLink(
						atoms[inst.getIntArg1()], inst.getIntArg2(),
						(Link)vars.get(inst.getIntArg3()) );
					break; //n-kato
					//====リンクを操作するボディ命令====ここまで====

					//====自由リンク管理アトム自動処理のためのボディ命令====ここから====
				case Instruction.REMOVEPROXIES : //[srcmem]
					mems[inst.getIntArg1()].removeProxies();
					break; //nakajima 2004-01-04, n-kato
				case Instruction.REMOVETOPLEVELPROXIES : //[srcmem]
					mems[inst.getIntArg1()].removeToplevelProxies();
					break; //nakajima 2004-01-04, n-kato
				case Instruction.INSERTPROXIES : //[parentmem,childmem]
					mems[inst.getIntArg1()].insertProxies(mems[inst.getIntArg2()]);
					break;  //nakajima 2004-01-04, n-kato
				case Instruction.REMOVETEMPORARYPROXIES : //[srcmem]
					mems[inst.getIntArg1()].removeTemporaryProxies();
					break; //nakajima 2004-01-04, n-kato
					//====自由リンク管理アトム自動処理のためのボディ命令====ここまで====

					//====ルールを操作するボディ命令====ここから====
				case Instruction.LOADRULESET:
				case Instruction.LOCALLOADRULESET: //[dstmem, ruleset]
					mems[inst.getIntArg1()].loadRuleset((Ruleset)inst.getArg2() );
					break; //n-kato
				case Instruction.COPYRULES:
				case Instruction.LOCALCOPYRULES:   //[dstmem, srcmem]
					mems[inst.getIntArg1()].copyRulesFrom(mems[inst.getIntArg2()]);
					break; //n-kato
				case Instruction.CLEARRULES:
				case Instruction.LOCALCLEARRULES:  //[dstmem]
					mems[inst.getIntArg1()].clearRules();
					break; //n-kato
				case Instruction.LOADMODULE: //[dstmem, module_name]
					// モジュール膜直属のルールセットを全部読み込む
					compile.structure.Membrane m = (compile.structure.Membrane)compile.Module.memNameTable.get(inst.getArg2());
					if(m==null) {
						Env.e("Undefined module "+inst.getArg2());
					} else {
						Iterator i = m.rulesets.iterator();
						while (i.hasNext()) {
							mems[inst.getIntArg1()].loadRuleset((Ruleset)i.next() );
						}
					}
					break;
					//====ルールを操作するボディ命令====ここまで====

					//====型付きでないプロセス文脈をコピーまたは廃棄するための命令====ここから====
				case Instruction.RECURSIVELOCK : //[srcmem]
					mems[inst.getIntArg1()].recursiveLock();
					break; //n-kato
				case Instruction.RECURSIVEUNLOCK : //[srcmem]
					mems[inst.getIntArg1()].recursiveUnlock();
					break;//nakajima 2004-01-04, n-kato
//				case Instruction.COPYMEM : //[-dstmem, srcmem]
					// //todo proxyに関する処理をまだ書いてない→それ以前にcopymemはまだ実装できません
					//「内容」とは子膜・アトム・ルール（ルールは違う。それと、addAllで追加すると親膜はどうなってしまいますか？）
					//mems[inst.getIntArg1()].copyRulesFrom(mems[inst.getIntArg2()]);
					//mems[inst.getIntArg1()].atoms.addAll(mems[inst.getIntArg2()].atoms);
					//mems[inst.getIntArg1()].mems.addAll(mems[inst.getIntArg2()].mems);
					//break;
				case Instruction.DROPMEM : //[srcmem]
					//mems[inst.getIntArg1()] = null;まだ実装できません
					break; //nakajima 2004-01-05
					//====型付きでないプロセス文脈をコピーまたは廃棄するための命令====ここまで====

					//====制御命令====ここから====
				case Instruction.COMMIT :
					break;//
				case Instruction.REACT : {
					Rule rule = (Rule) inst.getArg1();
					List bodyInsts = (List) rule.body;
					Instruction spec = (Instruction) bodyInsts.get(0);
					int formals = spec.getIntArg1();
					int locals  = spec.getIntArg2();

// // ArrayIndexOutOfBoundsException がでたので一時的に変更
// if (locals < 10) locals = 10;
					
					InterpretiveReactor ir = new InterpretiveReactor(locals);
					ir.reloadVars(this, locals, (List)inst.getArg2(),
						(List)inst.getArg3(), (List)inst.getArg4());
					if (ir.interpret(bodyInsts, 0)) return true;
					if (Env.debug == 9) Env.p("info: body execution failed");
					return false; //n-kato
					}
				case Instruction.JUMP: {
					InstructionList label = (InstructionList) inst.getArg1();
					List bodyInsts = (List) label.insts;
					Instruction spec = (Instruction) bodyInsts.get(0);
					int formals = spec.getIntArg1();
					int locals  = spec.getIntArg2();					
					InterpretiveReactor ir = new InterpretiveReactor(locals);
					ir.reloadVars(this, locals, (List)inst.getArg2(),
						(List)inst.getArg3(), (List)inst.getArg4());
					if (ir.interpret(bodyInsts, 0)) return true;
					return false; //n-kato
					}
				case Instruction.RESETVARS :
					reloadVars(this, vars.size(), (List)inst.getArg1(),
							(List)inst.getArg2(), (List)inst.getArg3());
					break;

				case Instruction.CHANGEVARS :
					changeVars(this, (List)inst.getArg1(),
							(List)inst.getArg2(), (List)inst.getArg3());
					break; //n-kato

				case Instruction.PROCEED:
					return true; //n-kato

				case Instruction.SPEC://[formals,locals]
					extendVector(inst.getIntArg2());
					break;//n-kato

				case Instruction.BRANCH :
					List subinsts;
					subinsts = ((InstructionList)inst.getArg1()).insts;
					if (interpret(subinsts, 0))
						return true;
					break; //nakajima, n-kato

				case Instruction.LOOP :
					subinsts = (List) ((List) inst.getArg1()).get(0); // reverted by n-kato: remove ".get(0)" by mizuno
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

					//====型付きプロセス文脈を扱うための追加命令====ここから====
				case Instruction.EQGROUND : //[groundlink1,groundlink2]
					//if(vars.get(inst.getIntArg1()).equals(vars.get(inst.getIntArg2()))){
					//	return true;
					//}
					break; //nakajima 2004-01-05
					//====型付きプロセス文脈を扱うための追加命令====ここまで====

					//====型検査のためのガード命令====ここから====
				case Instruction.ISGROUND : //[link]
					//atom = ((Link)vars.get(inst.getIntArg1())).getAtom();
					//if (atom.getMem().equals(mems[0])){
					//	return true;
					//}
					break; //nakajima 2004-01-05
					
				case Instruction.ISUNARY: // [atom]
					Functor f = atoms[inst.getIntArg1()].getFunctor();
					// まくを超えたリンクが unary かどうかが判断できない。OUTSIDE_PROXY を見てる
					// DEREF も？
					
					// (n-kato)
					// すべて仕様です。というか、リンク先は親膜にあるかもしれないわけですし、
					// 本膜の親膜にあるアトムを調べることは許されていません。
					// (hara) じゃそういうときは「失敗」ということでいいですかねぇ
					// (n-kato) はい。失敗して下さい。ちなみに$in,$outのarityは2なので次の2行は省略しました。
					//if(f.equals(Functor.OUTSIDE_PROXY)) return false;
					//if(f.equals(Functor.INSIDE_PROXY)) return false;
					if (f.getArity() != 1) return false;
					break; // n-kato
//				case Instruction.ISUNARYFUNC: // [func]
//					break;
					
				case Instruction.ISINT : //[atom]
					if (!(atoms[inst.getIntArg1()].getFunctor() instanceof IntegerFunctor)) return false;
					break; //n-kato
				case Instruction.ISFLOAT : //[atom]
					if (!(atoms[inst.getIntArg1()].getFunctor() instanceof FloatingFunctor)) return false;
					break; //n-kato
				case Instruction.ISSTRING : //[atom] // todo StringFunctorに変える
					if (!(atoms[inst.getIntArg1()].getFunctor() instanceof ObjectFunctor)) return false;
					if (!(((ObjectFunctor)atoms[inst.getIntArg1()].getFunctor()).getObject() instanceof String)) return false;
					break; //n-kato
				case Instruction.ISINTFUNC : //[func]
					if (!(vars.get(inst.getIntArg1()) instanceof IntegerFunctor)) return false;
					break; //n-kato
//				case Instruction.ISFLOATFUNC : //[func]
//					break;
//				case Instruction.ISSTRINGFUNC : //[func]
//					break;

				case Instruction.GETCLASS: //[-stringatom, atom]
					if (!(atoms[inst.getIntArg2()].getFunctor() instanceof ObjectFunctor)) return false;
					Object obj = ((ObjectFunctor)atoms[inst.getIntArg2()].getFunctor()).getObject();
					atoms[inst.getIntArg1()] = new Atom(null, new StringFunctor( obj.getClass().toString().substring(6) ));
					break; //n-kato
					
					//====型検査のためのガード命令====ここまで====

					//====組み込み機能に関する命令====ここから====
				case Instruction.INLINE : //[atom, inlineref]
					Inline.callInline( atoms[inst.getIntArg1()], inst.getIntArg2() );
					break; //hara
					//====組み込み機能に関する命令====ここまで====
//				case Instruction.BUILTIN: //[class, atom]
//					add(A,B,C) :- int(A),int(B),$builtin:iadd(A,B,C), 
//					Inline.callInline( atoms[inst.getIntArg1()], inst.getIntArg2() );
//					break;
					//====組み込み機能に関する命令====ここまで====
					
					//====整数用の組み込みボディ命令====ここから====
				case Instruction.IADD : //[-dstintatom, intatom1, intatom2]
					int x,y;
					x = ((IntegerFunctor)atoms[inst.getIntArg2()].getFunctor()).intValue();
					y = ((IntegerFunctor)atoms[inst.getIntArg3()].getFunctor()).intValue();
					atoms[inst.getIntArg1()] = new Atom(null, new IntegerFunctor(x+y));
					break; //n-kato
				case Instruction.ISUB : //[-dstintatom, intatom1, intatom2]
					x = ((IntegerFunctor)atoms[inst.getIntArg2()].getFunctor()).intValue();
					y = ((IntegerFunctor)atoms[inst.getIntArg3()].getFunctor()).intValue();
					atoms[inst.getIntArg1()] = new Atom(null, new IntegerFunctor(x-y));	
					break; //nakajima 2004-01-05
				case Instruction.IMUL : //[-dstintatom, intatom1, intatom2]
					x = ((IntegerFunctor)atoms[inst.getIntArg2()].getFunctor()).intValue();
					y = ((IntegerFunctor)atoms[inst.getIntArg3()].getFunctor()).intValue();
					atoms[inst.getIntArg1()] = new Atom(null, new IntegerFunctor(x * y));	
					break; //nakajima 2004-01-05
				case Instruction.IDIV : //[-dstintatom, intatom1, intatom2]
					x = ((IntegerFunctor)atoms[inst.getIntArg2()].getFunctor()).intValue();
					y = ((IntegerFunctor)atoms[inst.getIntArg3()].getFunctor()).intValue();
					if (y == 0) return false;
					//if (y == 0) func = new Functor("NaN",1);
					else func = new IntegerFunctor(x / y);
					atoms[inst.getIntArg1()] = new Atom(null, func);				
					break; //nakajima 2004-01-05, n-kato
				case Instruction.INEG : //[-dstintatom, intatom]
					x = ((IntegerFunctor)atoms[inst.getIntArg2()].getFunctor()).intValue();
					atoms[inst.getIntArg1()] = new Atom(null, new IntegerFunctor(-x));				
					break;
				case Instruction.IMOD : //[-dstintatom, intatom1, intatom2]
					x = ((IntegerFunctor)atoms[inst.getIntArg2()].getFunctor()).intValue();
					y = ((IntegerFunctor)atoms[inst.getIntArg3()].getFunctor()).intValue();
					if (y == 0) return false;
					//if (y == 0) func = new Functor("NaN",1);
					else func = new IntegerFunctor(x % y);
					atoms[inst.getIntArg1()] = new Atom(null, func);						
					break; //nakajima 2004-01-05
				case Instruction.INOT : //[-dstintatom, intatom]
					x = ((IntegerFunctor)atoms[inst.getIntArg2()].getFunctor()).intValue();
					atoms[inst.getIntArg1()] = new Atom(null, new IntegerFunctor(~x));	
					break; //nakajima 2004-01-21
				case Instruction.IAND : //[-dstintatom, intatom1, intatom2]
					x = ((IntegerFunctor)atoms[inst.getIntArg2()].getFunctor()).intValue();
					y = ((IntegerFunctor)atoms[inst.getIntArg3()].getFunctor()).intValue();
					atoms[inst.getIntArg1()] = new Atom(null, new IntegerFunctor(x & y));	
					break; //nakajima 2004-01-21
				case Instruction.IOR : //[-dstintatom, intatom1, intatom2]
					x = ((IntegerFunctor)atoms[inst.getIntArg2()].getFunctor()).intValue();
					y = ((IntegerFunctor)atoms[inst.getIntArg3()].getFunctor()).intValue();
					atoms[inst.getIntArg1()] = new Atom(null, new IntegerFunctor(x | y));	
					break; //nakajima 2004-01-21
				case Instruction.IXOR : //[-dstintatom, intatom1, intatom2]
					x = ((IntegerFunctor)atoms[inst.getIntArg2()].getFunctor()).intValue();
					y = ((IntegerFunctor)atoms[inst.getIntArg3()].getFunctor()).intValue();
					atoms[inst.getIntArg1()] = new Atom(null, new IntegerFunctor(x ^ y));	
					break; //nakajima 2004-01-21
				case Instruction.ISAL : //[-dstintatom, intatom1, intatom2]
					x = ((IntegerFunctor)atoms[inst.getIntArg2()].getFunctor()).intValue();
					y = ((IntegerFunctor)atoms[inst.getIntArg3()].getFunctor()).intValue();
					atoms[inst.getIntArg1()] = new Atom(null, new IntegerFunctor(x << y));	
					break; //nakajima 2004-01-21
				case Instruction.ISAR : //[-dstintatom, intatom1, intatom2] 
					x = ((IntegerFunctor)atoms[inst.getIntArg2()].getFunctor()).intValue();
					y = ((IntegerFunctor)atoms[inst.getIntArg3()].getFunctor()).intValue();
					atoms[inst.getIntArg1()] = new Atom(null, new IntegerFunctor(x >> y));	
					break; //nakajima 2004-01-21					
				case Instruction.ISHR : //[-dstintatom, intatom1, intatom2] 
					x = ((IntegerFunctor)atoms[inst.getIntArg2()].getFunctor()).intValue();
					y = ((IntegerFunctor)atoms[inst.getIntArg3()].getFunctor()).intValue();
					atoms[inst.getIntArg1()] = new Atom(null, new IntegerFunctor(x >>> y));	
					break; //nakajima 2004-01-21	
				case Instruction.IADDFUNC : //[-dstintfunc, intfunc1, intfunc2]
					x = ((IntegerFunctor)vars.get(inst.getIntArg2())).intValue();
					y = ((IntegerFunctor)vars.get(inst.getIntArg3())).intValue();
					vars.set(inst.getIntArg1(), new IntegerFunctor(x+y));
					break; //n-kato
				case Instruction.ISUBFUNC : //[-dstintfunc, intfunc1, intfunc2]
					x = ((IntegerFunctor)vars.get(inst.getIntArg2())).intValue();
					y = ((IntegerFunctor)vars.get(inst.getIntArg3())).intValue();
					vars.set(inst.getIntArg1(), new IntegerFunctor(x-y));
					break; //nakajima 2003-01-05
				case Instruction.IMULFUNC : //[-dstintfunc, intfunc1, intfunc2]
					x = ((IntegerFunctor)vars.get(inst.getIntArg2())).intValue();
					y = ((IntegerFunctor)vars.get(inst.getIntArg3())).intValue();
					vars.set(inst.getIntArg1(), new IntegerFunctor(x*y));
					break; //nakajima 2003-01-05
				case Instruction.IDIVFUNC : //[-dstintfunc, intfunc1, intfunc2]
					x = ((IntegerFunctor)vars.get(inst.getIntArg2())).intValue();
					y = ((IntegerFunctor)vars.get(inst.getIntArg3())).intValue();
					if (y == 0) return false;
					//if (y == 0) func = new Functor("NaN",1);
					else func = new IntegerFunctor(x / y);
					vars.set(inst.getIntArg1(), func);
					break; //nakajima 2003-01-05
				case Instruction.INEGFUNC : //[-dstintfunc, intfunc]
					x = ((IntegerFunctor)vars.get(inst.getIntArg2())).intValue();
					vars.set(inst.getIntArg1(), new IntegerFunctor(-x));
					break;
				case Instruction.IMODFUNC : //[-dstintfunc, intfunc1, intfunc2]
					x = ((IntegerFunctor)vars.get(inst.getIntArg2())).intValue();
					y = ((IntegerFunctor)vars.get(inst.getIntArg3())).intValue();
					if (y == 0) return false;
					//if (y == 0) func = new Functor("NaN",1);
					else func = new IntegerFunctor(x % y);
					vars.set(inst.getIntArg1(), func);
					break; //nakajima 2003-01-05
				case Instruction.INOTFUNC : //[-dstintfunc, intfunc]
					x = ((IntegerFunctor)vars.get(inst.getIntArg2())).intValue();
					vars.set(inst.getIntArg1(), new IntegerFunctor(~x));
					break; //nakajima 2003-01-21
				case Instruction.IANDFUNC : //[-dstintfunc, intfunc1, intfunc2]
					x = ((IntegerFunctor)vars.get(inst.getIntArg2())).intValue();
					y = ((IntegerFunctor)vars.get(inst.getIntArg3())).intValue();	
					vars.set(inst.getIntArg1(), new IntegerFunctor(x & y));				
					break; //nakajima 2003-01-21
				case Instruction.IORFUNC : //[-dstintfunc, intfunc1, intfunc2]
					x = ((IntegerFunctor)vars.get(inst.getIntArg2())).intValue();
					y = ((IntegerFunctor)vars.get(inst.getIntArg3())).intValue();	
					vars.set(inst.getIntArg1(), new IntegerFunctor(x | y));				
					break; //nakajima 2003-01-21
				case Instruction.IXORFUNC : //[-dstintfunc, intfunc1, intfunc2]
					x = ((IntegerFunctor)vars.get(inst.getIntArg2())).intValue();
					y = ((IntegerFunctor)vars.get(inst.getIntArg3())).intValue();	
					vars.set(inst.getIntArg1(), new IntegerFunctor(x ^ y));				
					break; //nakajima 2003-01-21
				case Instruction.ISALFUNC : //[-dstintfunc, intfunc1, intfunc2]
					x = ((IntegerFunctor)vars.get(inst.getIntArg2())).intValue();
					y = ((IntegerFunctor)vars.get(inst.getIntArg3())).intValue();	
					vars.set(inst.getIntArg1(), new IntegerFunctor(x << y));				
					break; //nakajima 2003-01-21
				case Instruction.ISARFUNC : //[-dstintfunc, intfunc1, intfunc2]
					x = ((IntegerFunctor)vars.get(inst.getIntArg2())).intValue();
					y = ((IntegerFunctor)vars.get(inst.getIntArg3())).intValue();	
					vars.set(inst.getIntArg1(), new IntegerFunctor(x >> y));				
					break; //nakajima 2003-01-21
				case Instruction.ISHRFUNC : //[-dstintfunc, intfunc1, intfunc2]
					x = ((IntegerFunctor)vars.get(inst.getIntArg2())).intValue();
					y = ((IntegerFunctor)vars.get(inst.getIntArg3())).intValue();	
					vars.set(inst.getIntArg1(), new IntegerFunctor(x >>> y));				
					break; //nakajima 2003-01-21
					//====整数用の組み込みボディ命令====ここまで====

					//====整数用の組み込みガード命令====ここから====
				case Instruction.ILT : //[intatom1, intatom2]
					x = ((IntegerFunctor)atoms[inst.getIntArg1()].getFunctor()).intValue();
					y = ((IntegerFunctor)atoms[inst.getIntArg2()].getFunctor()).intValue();	
					if (!(x < y)) return false;
					break; // n-kato
				case Instruction.ILE : //[intatom1, intatom2]
					x = ((IntegerFunctor)atoms[inst.getIntArg1()].getFunctor()).intValue();
					y = ((IntegerFunctor)atoms[inst.getIntArg2()].getFunctor()).intValue();	
					if (!(x <= y)) return false;
					break; // n-kato
				case Instruction.IGT : //[intatom1, intatom2]
					x = ((IntegerFunctor)atoms[inst.getIntArg1()].getFunctor()).intValue();
					y = ((IntegerFunctor)atoms[inst.getIntArg2()].getFunctor()).intValue();	
					if (!(x > y)) return false;
					break; // n-kato
				case Instruction.IGE : //[intatom1, intatom2]
					x = ((IntegerFunctor)atoms[inst.getIntArg1()].getFunctor()).intValue();
					y = ((IntegerFunctor)atoms[inst.getIntArg2()].getFunctor()).intValue();	
					if (!(x >= y)) return false;
					break; // n-kato
				case Instruction.IEQ : //[intatom1, intatom2]
					x = ((IntegerFunctor)atoms[inst.getIntArg1()].getFunctor()).intValue();
					y = ((IntegerFunctor)atoms[inst.getIntArg2()].getFunctor()).intValue();	
					if (!(x == y)) return false;
					break; // n-kato
				case Instruction.INE : //[intatom1, intatom2]
					x = ((IntegerFunctor)atoms[inst.getIntArg1()].getFunctor()).intValue();
					y = ((IntegerFunctor)atoms[inst.getIntArg2()].getFunctor()).intValue();	
					if (!(x != y)) return false;
					break; // n-kato

				case Instruction.ILTFUNC : //[intfunc1, intfunc2]
					x = ((IntegerFunctor)vars.get(inst.getIntArg1())).intValue();
					y = ((IntegerFunctor)vars.get(inst.getIntArg2())).intValue();
					if (!(x < y)) return false;
					break; // n-kato
				case Instruction.ILEFUNC : //[intfunc1, intfunc2]
					x = ((IntegerFunctor)vars.get(inst.getIntArg1())).intValue();
					y = ((IntegerFunctor)vars.get(inst.getIntArg2())).intValue();
					if (!(x <= y)) return false;
					break; // n-kato
				case Instruction.IGTFUNC : //[intfunc1, intfunc2]
					x = ((IntegerFunctor)vars.get(inst.getIntArg1())).intValue();
					y = ((IntegerFunctor)vars.get(inst.getIntArg2())).intValue();
					if (!(x > y)) return false;
					break; // n-kato
				case Instruction.IGEFUNC : //[intfunc1, intfunc2]
					x = ((IntegerFunctor)vars.get(inst.getIntArg1())).intValue();
					y = ((IntegerFunctor)vars.get(inst.getIntArg2())).intValue();
					if (!(x >= y)) return false;
					break; // n-kato
				// IEQFUNC INEFUNC FEQFUNC FNEFUNC FNEFUNC... INT2FLOATFUNC...
					//====整数用の組み込みガード命令====ここまで====


					//====浮動小数点数用の組み込みボディ命令====ここから====
				case Instruction.FADD : //[-dstfloatatom, floatatom1, floatatom2]
					double u,v;
					u = ((FloatingFunctor)atoms[inst.getIntArg2()].getFunctor()).floatValue();
					v = ((FloatingFunctor)atoms[inst.getIntArg3()].getFunctor()).floatValue();
					atoms[inst.getIntArg1()] = new Atom(null, new FloatingFunctor(u+v));
					break; //n-kato
				case Instruction.FSUB : //[-dstfloatatom, floatatom1, floatatom2]
					u = ((FloatingFunctor)atoms[inst.getIntArg2()].getFunctor()).floatValue();
					v = ((FloatingFunctor)atoms[inst.getIntArg3()].getFunctor()).floatValue();
					atoms[inst.getIntArg1()] = new Atom(null, new FloatingFunctor(u-v));	
					break; // n-kato
				case Instruction.FMUL : //[-dstfloatatom, floatatom1, floatatom2]
					u = ((FloatingFunctor)atoms[inst.getIntArg2()].getFunctor()).floatValue();
					v = ((FloatingFunctor)atoms[inst.getIntArg3()].getFunctor()).floatValue();
					atoms[inst.getIntArg1()] = new Atom(null, new FloatingFunctor(u * v));	
					break; // n-kato
				case Instruction.FDIV : //[-dstfloatatom, floatatom1, floatatom2]
					u = ((FloatingFunctor)atoms[inst.getIntArg2()].getFunctor()).floatValue();
					v = ((FloatingFunctor)atoms[inst.getIntArg3()].getFunctor()).floatValue();
					//if (v == 0.0) func = new Functor("NaN",1);
					//else
					func = new FloatingFunctor(u / v);
					atoms[inst.getIntArg1()] = new Atom(null, func);				
					break; // n-kato
				case Instruction.FNEG : //[-dstfloatatom, floatatom]
					u = ((FloatingFunctor)atoms[inst.getIntArg2()].getFunctor()).floatValue();
					atoms[inst.getIntArg1()] = new Atom(null, new FloatingFunctor(-u));
					break; //nakajima 2004-01-23
				case Instruction.FADDFUNC : //[-dstfloatfunc, floatfunc1, floatfunc2]
					u = ((FloatingFunctor)vars.get(inst.getIntArg2())).floatValue();
					v = ((FloatingFunctor)vars.get(inst.getIntArg3())).floatValue();
					vars.set(inst.getIntArg1(), new FloatingFunctor(u + v));	
					break; //nakajima 2004-01-23			
				case Instruction.FSUBFUNC : //[-dstfloatfunc, floatfunc1, floatfunc2]
					u = ((FloatingFunctor)vars.get(inst.getIntArg2())).floatValue();
					v = ((FloatingFunctor)vars.get(inst.getIntArg3())).floatValue();
					vars.set(inst.getIntArg1(), new FloatingFunctor(u - v));		
					break; //nakajima 2004-01-23
				case Instruction.FMULFUNC : //[-dstfloatfunc, floatfunc1, floatfunc2]
					u = ((FloatingFunctor)vars.get(inst.getIntArg2())).floatValue();
					v = ((FloatingFunctor)vars.get(inst.getIntArg3())).floatValue();
					vars.set(inst.getIntArg1(), new FloatingFunctor(u * v));	
					break; //nakajima 2004-01-23
				case Instruction.FDIVFUNC : //[-dstfloatfunc, floatfunc1, floatfunc2]
					u = ((FloatingFunctor)vars.get(inst.getIntArg2())).floatValue();
					v = ((FloatingFunctor)vars.get(inst.getIntArg3())).floatValue();
					vars.set(inst.getIntArg1(), new FloatingFunctor(u / v));		
					break; //nakajima 2004-01-23
				case Instruction.FNEGFUNC : //[-dstfloatfunc, floatfunc]
					u = ((FloatingFunctor)vars.get(inst.getIntArg2())).floatValue();
					vars.set(inst.getIntArg1(), new FloatingFunctor(-u));	
					break; //nakajima 2004-01-23
					//====浮動小数点数用の組み込みボディ命令====ここまで====
					
					//====浮動小数点数用の組み込みガード命令====ここから====	
				case Instruction.FLT : //[floatatom1, floatatom2]
					u = ((FloatingFunctor)atoms[inst.getIntArg1()].getFunctor()).floatValue();
					v = ((FloatingFunctor)atoms[inst.getIntArg2()].getFunctor()).floatValue();	
					if (!(u < v)) return false;
					break; // n-kato
				case Instruction.FLE : //[floatatom1, floatatom2]
					u = ((FloatingFunctor)atoms[inst.getIntArg1()].getFunctor()).floatValue();
					v = ((FloatingFunctor)atoms[inst.getIntArg2()].getFunctor()).floatValue();	
					if (!(u <= v)) return false;
					break; // n-kato
				case Instruction.FGT : //[floatatom1, floatatom2]
					u = ((FloatingFunctor)atoms[inst.getIntArg1()].getFunctor()).floatValue();
					v = ((FloatingFunctor)atoms[inst.getIntArg2()].getFunctor()).floatValue();	
					if (!(u > v)) return false;
					break; // n-kato
				case Instruction.FGE : //[floatatom1, floatatom2]
					u = ((FloatingFunctor)atoms[inst.getIntArg1()].getFunctor()).floatValue();
					v = ((FloatingFunctor)atoms[inst.getIntArg2()].getFunctor()).floatValue();	
					if (!(u >= v)) return false;
					break; // n-kato
				case Instruction.FEQ : //[floatatom1, floatatom2]
					u = ((FloatingFunctor)atoms[inst.getIntArg1()].getFunctor()).floatValue();
					v = ((FloatingFunctor)atoms[inst.getIntArg2()].getFunctor()).floatValue();	
					if (!(u == v)) return false;
					break; // n-kato
				case Instruction.FNE : //[floatatom1, floatatom2]
					u = ((FloatingFunctor)atoms[inst.getIntArg1()].getFunctor()).floatValue();
					v = ((FloatingFunctor)atoms[inst.getIntArg2()].getFunctor()).floatValue();	
					if (!(u != v)) return false;
					break; // n-kato
				case Instruction.FLTFUNC : //[floatfunc1, floatfunc2]
					u = ((FloatingFunctor)vars.get(inst.getIntArg1())).floatValue();
					v = ((FloatingFunctor)vars.get(inst.getIntArg2())).floatValue();
					if (!(u < v)) return false;
					break; //nakajima 2003-01-23
				case Instruction.FLEFUNC : //[floatfunc1, floatfunc2]	
					u = ((FloatingFunctor)vars.get(inst.getIntArg1())).floatValue();
					v = ((FloatingFunctor)vars.get(inst.getIntArg2())).floatValue();
					if (!(u <= v)) return false;		
					break; //nakajima 2003-01-23
				case Instruction.FGTFUNC : //[floatfunc1, floatfunc2]
					u = ((FloatingFunctor)vars.get(inst.getIntArg1())).floatValue();
					v = ((FloatingFunctor)vars.get(inst.getIntArg2())).floatValue();
					if (!(u > v)) return false;		
					break; //nakajima 2003-01-23
				case Instruction.FGEFUNC : //[floatfunc1, floatfunc2]
					u = ((FloatingFunctor)vars.get(inst.getIntArg1())).floatValue();
					v = ((FloatingFunctor)vars.get(inst.getIntArg2())).floatValue();
					if (!(u >= v)) return false;
					break; //nakajima 2003-01-23
					//====浮動小数点数用の組み込みガード命令====ここまで====

				case Instruction.FLOAT2INT: //[-intatom, floatatom]
					u = ((FloatingFunctor)atoms[inst.getIntArg2()].getFunctor()).floatValue();
					atoms[inst.getIntArg1()] = new Atom(null, new IntegerFunctor((int)u));
					break; // n-kato
				case Instruction.INT2FLOAT: //[-floatatom, intatom]
					x = ((IntegerFunctor)atoms[inst.getIntArg2()].getFunctor()).intValue();
					atoms[inst.getIntArg1()] = new Atom(null, new FloatingFunctor((double)x));
					break; // n-kato












				default :
					System.out.println(
						"SYSTEM ERROR: Invalid instruction: " + inst);
					break;
			}
		}
		return false;
	}
}

