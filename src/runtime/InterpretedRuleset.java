/*
 * ※重要※
 * このファイルの内容を修正した場合、Transrator.java にも同様の修正を加えること。
 * 大きな変更を加えた場合、TranslatorGenerator を利用すると良いかもしれない。
 */

package runtime;

import gui.BreakPoint;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.Util;
import debug.Debug;

// doneTODO LOCAL関係のコメントを完全に削除する (2) -> 0912 done
// TODO 引数mem がいらない箇所がある（デーモン削除の影響）

/**
 * compile.RulesetCompiler によって生成される。
 * @author hara, nakajima, n-kato
 */
public final class InterpretedRuleset extends Ruleset implements Serializable {
	/** このルールセットのローカルID */
	private int id;
	private static int lastId = 600;

	/** とりあえずルールの配列として実装 */
	public List<Rule> rules;
	
	/** 編み上げ後の命令列 */
	public MergedBranchMap branchmap;
	public MergedBranchMap systemrulemap;
	
	/** 現在実行中のルール */
	public Rule currentRule;
	
	private int backtracks, lockfailure;
	/**
	 * RuleCompiler では、まず生成してからデータを入れ込む。
	 * ので、特になにもしない
	 */
	public InterpretedRuleset() {
		rules = new ArrayList<Rule>();
		id = ++lastId;
		branchmap = null;
		systemrulemap = null;
	}
	
	/** 中間命令列をパーズして生成するときに利用するコンストラクタ */
	public InterpretedRuleset(int id, List<Rule> rules) {
		this.id = id;
		this.rules = rules;
		if (lastId < id)
			lastId = id;
	}
	
	////////////////////////////////////////////////////////////////

	/** グローバルルールセットID（未定義の場合はnull）*/
	private String globalRulesetID;

	/**このルールセットのローカルIDを取得する。*/
	public int getId() {
		return id;
	}
	/**（仮）*/
	// 061129 okabe runtimeid 廃止による
//	public String getGlobalRulesetID() {
//		// todo ランタイムIDの有効期間を見直す
//		if (globalRulesetID == null) {
//			globalRulesetID = Env.theRuntime.getRuntimeID() + ":" + id;
////			IDConverter.registerRuleset(globalRulesetID, this);
//		}
//		return globalRulesetID;
//	}
	
	////////////////////////////////////////////////////////////////

	public boolean react(Membrane mem, boolean nondeterministic) {
		if (nondeterministic) {
			Env.e("Nondeterministic execution is not supported by interpreter.");
			System.exit(-1);
			return false;
		} else {
			return react(mem);
		}
	}
	
	/**
	 * あるルールについてアトム主導テストを行い、マッチすれば適用する
	 * @return ルールを適用した場合はtrue
	 */
	public boolean react(Membrane mem, Atom atom) {
		// 060804 safe mode
		if(Env.safe && (Env.counter > Env.maxStep)) return false;
		Env.counter++;
    	Thread thread = Thread.currentThread();
		boolean result = false;
		Iterator<Rule> it = rules.iterator();
		if(branchmap != null){
			Functor func = atom.getFunctor();
			if(systemrulemap.containsKey(func)){
				List insts = systemrulemap.getInsts(func);
				Instruction spec = (Instruction)insts.get(0);
				int formals = spec.getIntArg1();
				int locals = spec.getIntArg2();
				boolean success;
				if(locals == 0){
					System.err.println("SYSTEM DEBUG REPORT: an old version of spec instruction was detected");
					locals = formals;
				}
				InterpretiveReactor ir = new InterpretiveReactor(locals, this);
				ir.mems[0] = mem;
				ir.atoms[1] = atom;
				success = ir.interpret(insts, 0);
				if(success) {
					return true;
				}
			} else
			if(branchmap.containsKey(func)){
				List insts = branchmap.getInsts(func);
				Instruction spec = (Instruction)insts.get(0);
				int formals = spec.getIntArg1();
				int locals = spec.getIntArg2();
				boolean success;
				if(locals == 0){
					System.err.println("SYSTEM DEBUG REPORT: an old version of spec instruction was detected");
					locals = formals;
				}
				InterpretiveReactor ir = new InterpretiveReactor(locals, this);
				ir.mems[0] = mem;
				ir.atoms[1] = atom;
				success = ir.interpret(insts, 0);
				/*if(success){
					//todo トレースモード
				}*/
				return success;
			} else return false;
		} else
		while (it.hasNext()) {
			Rule r = currentRule = it.next();
			if (r.atomMatch.size() == 1) continue; // debug表示抑制用
			boolean success;
			if(Env.profile >= Env.PROFILE_BYRULE){
				long start,stop;
		        start = Util.getTime();
				success = matchTest(mem, atom, r.atomMatch);
		        stop = Util.getTime();
		        synchronized(r){
					if(Env.profile == Env.PROFILE_ALL){
			        	r.setAtomTime((stop>start)?(stop-start):0, thread);
			        	r.incAtomApply(thread);
						if(success)r.incAtomSucceed(thread);
						r.setBackTracks(backtracks, thread);
						r.setLockFailure(lockfailure, thread);
					} else {
						r.atomtime += (stop>start)?(stop-start):0;
						r.atomapply++;
						if (success)r.atomsucceed ++;;
						r.backtracks += backtracks;
						r.lockfailure += lockfailure;
					}
		        }
			} else {
				success = matchTest(mem, atom, r.atomMatch);
			}
			if (success) {
				result = true;
//				if (Env.fTrace) Task.trace("-->", "@" + id, r.toString());
//				if (Env.debugOption) {//2006.1.26 by inui
//					Debug.breakPoint(r.lineno, Debug.ATOM);
//					//if (Debug.isBreakPoint()) Task.trace("-->", "@" + id, r.toString());
//				}
				//if (!mem.isCurrent()) return true;
				if(Env.LMNtool != null && !mem.isRoot())
					Env.LMNtool.removeMem();
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
		// 060804 safe mode
		if(Env.safe && (Env.counter > Env.maxStep)) return false;
		Env.counter++;
    	Thread thread = Thread.currentThread();
		boolean result = false;
		Iterator it = rules.iterator();
		while (it.hasNext()) {
			Rule r = currentRule = (Rule) it.next();
			boolean success;
			if(Env.profile >= Env.PROFILE_BYRULE){
				long start,stop;
				backtracks = lockfailure = 0;
		        start = Util.getTime();
				success = matchTest(mem, null, r.memMatch);
		        stop = Util.getTime();
		        synchronized(r){
					if(Env.profile == Env.PROFILE_ALL){
			        	r.setMemTime((stop>start)?(stop-start):0, thread);
			        	r.incMemApply(thread);
						if(success)r.incMemSucceed(thread);
						r.setBackTracks(backtracks, thread);
						r.setLockFailure(lockfailure, thread);
					} else {
						r.memtime += (stop>start)?(stop-start):0;
						r.memapply++;
						if (success)r.memsucceed ++;;
						r.backtracks += backtracks;
						r.lockfailure += lockfailure;
					}
		        }
			} else {
				success = matchTest(mem, null, r.memMatch);
			}
			if (success) {
//				result = true;
//				if(Env.fTrace) Task.trace("==>", "@" + id, r.toString());
//				if (Env.debugOption) {//2006.1.26 by inui
//					Debug.breakPoint(r.lineno, Debug.MEMBRANE);
//					//if (Debug.isBreakPoint()) Task.trace("==>", "@" + id, r.toString());
//				}
				if(Env.LMNtool != null && !mem.isRoot())
					Env.LMNtool.removeMem();
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
		boolean success;
		if (locals == 0) {
			System.err.println("SYSTEM DEBUG REPORT: an old version of spec instruction was detected");
			locals = formals;
		}
// ArrayIndexOutOfBoundsException がでたので一時的に変更
//if (formals < 10) formals = 10;
		
		InterpretiveReactor ir = new InterpretiveReactor(locals, this);
		ir.mems[0] = mem;
		if (atom != null) { ir.atoms[1] = atom; }
		success = ir.interpret(matchInsts, 0);
		if(Env.profile >= Env.PROFILE_BYRULE) {
			backtracks = ir.backtracks;
			lockfailure = ir.lockfailure;
		}
		return success;
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

	public String encode() {
		StringBuffer s = new StringBuffer("");
		Iterator it = rules.iterator();
		while(it.hasNext()) {
			s.append(((Rule)it.next()).getFullText().
					replaceAll("\\n","").replaceAll("\\r",""));
			if(it.hasNext())
				s.append(", ");
		}
		return s.toString();
	}
	
	public String[] encodeRulesIndividually(){
		String[] result = new String[rules.size()];
		Iterator it = rules.iterator();
		int i = 0;
		while(it.hasNext()) {
			result[i] = ((Rule)it.next()).getFullText().
					replaceAll("\\n","").replaceAll("\\r","");
			if(2 < result[i].length()){
				result[i] = result[i].substring(1, result[i].length() - 1);
			}
			i++;
		}
		return result;
	}
	
	public void showDetail() {
		if (Env.verbose >= Env.VERBOSE_SHOWRULES || Env.compileonly)
			if(isSystemRuleset)
				Env.p("Compiled SystemRuleset @" + id + dumpRules());
			else
				Env.p("Compiled Ruleset @" + id + dumpRules());
		Iterator l;
		l = rules.listIterator();
		while (l.hasNext()) {
			Rule r = ((Rule) l.next());
			r.showDetail();
		}
		if(Env.slimcode)
			Env.p("");
	}
	
	public void serialize(ObjectOutputStream out) throws IOException {
		super.serialize(out);
		out.writeObject(rules);
	}
	protected void deserializeInstance(ObjectInputStream in) throws IOException {
		super.deserializeInstance(in);
		// todo idの振り方を考える。→このidは捨てられてglobalidのみで管理される
		id = ++lastId;
		try {
			rules = (List)in.readObject();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unexpected Error in deserialization");
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
	Membrane[] mems;
	/** アトム変数のベクタ */
	Atom[] atoms;
	/** その他の変数のベクタ（memsやatomsは廃止してvarsに統合する？）*/
	List vars;
	/** ロックした膜のリスト　グループ化時に使用**/
	List lockedMemList = new ArrayList();
	
	int backtracks, lockfailure;
	
	InterpretedRuleset currentInterpretedRuleset;
	
	InterpretiveReactor(int size, InterpretedRuleset ir) {
		this.currentInterpretedRuleset = ir;
		initVector(size);
		backtracks = lockfailure = 0;
	}
	private void initVector(int size) {
		this.mems  = new Membrane[size];
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
		Membrane[] oldmems = mems;
		Atom[] oldatoms = atoms;
		
		this.mems  = new Membrane[size];
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
		Membrane[] srcmems  = irSrc.mems;
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
		Membrane[] srcmems  = irSrc.mems;
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
		Membrane mem;
		Link link;
		Functor func;
		while (pc < insts.size()) {
			Instruction inst = (Instruction) insts.get(pc++);
			if (Env.debug >= Env.DEBUG_TRACE) Env.d("Do " + inst);
			switch (inst.getKind()) {

				//メモ：コメントは引数
				//nakajima: 2003-12-12

				//====その他====ここから====
				case Instruction.DUMMY :
					System.err.println(
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
						backtracks++;
					}
					return false; //n-kato
					//====アトムに関係する出力する基本ガード命令====ここまで====

					//====膜に関係する出力する基本ガード命令 ====ここから====
				case Instruction.LOCKMEM :
					// lockmem [-dstmem, freelinkatom, memname]
					mem = atoms[inst.getIntArg2()].mem;
					if(!mem.equalName((String)inst.getArg3()))
						return false;
					if (mem.lock()) {
						lockedMemList.add(mem);
						mems[inst.getIntArg1()] = mem;
						if (interpret(insts, pc))
							return true;
						mem.unlock(true);
					}
					lockfailure++;
					return false; //n-kato

				case Instruction.ANYMEM :
					// anymem [-dstmem, srcmem, memname] 
					it = mems[inst.getIntArg2()].mems.iterator();
					while (it.hasNext()) {
						Membrane submem = (Membrane) it.next();
						if ((submem.kind != inst.getIntArg3()) ||
								(!submem.equalName((String)inst.getArg4()))) { 
							backtracks++;
							continue;
						}
						if (submem.lock()) {
							mems[inst.getIntArg1()] = submem;
							lockedMemList.add(submem);
							if (interpret(insts, pc))
								return true;
							submem.unlock();
						}
						lockfailure++;
					}
					return false; //n-kato
				case Instruction.LOCK :
					//[srcmem] 
					mem = mems[inst.getIntArg1()];
					if (mem.lock()) {
						lockedMemList.add(mem);
						if (interpret(insts, pc))
							return true;
						mem.unlock();						
					}
					lockfailure++;
					return false; //n-kato

				case Instruction.GETMEM : //[-dstmem, srcatom, memtype, memname]
					if(atoms[inst.getIntArg2()].mem.kind != inst.getIntArg3() ||
							(!atoms[inst.getIntArg2()].mem.equalName((String)inst.getArg4())))
						return false;
					mems[inst.getIntArg1()] = atoms[inst.getIntArg2()].mem;
					break;
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
				case Instruction.NATOMSINDIRECT : //[srcmem, countfunc]
					if (mems[inst.getIntArg1()].atoms.getNormalAtomCount() != ((IntegerFunctor)vars.get(inst.getIntArg2())).intValue()) return false;
					break; //kudo 2004-12-08
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
				case Instruction.SUBCLASS: //[atom1, atom2]
					try {
						Class c1 = ((ObjectFunctor)atoms[inst.getIntArg1()].getFunctor()).getObject().getClass();
						Class c2 = Class.forName(((StringFunctor)atoms[inst.getIntArg2()].getFunctor()).stringValue());
						if (!c2.isAssignableFrom(c1)) return false;
					} catch (ClassNotFoundException e1) {
						return false;
					}
                    break; //inui 2006-07-01
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
					//[srcatom, srcmem, funcref]
					if(Env.fUNYO){
						unyo.Mediator.addRemovedAtom(atoms[inst.getIntArg1()], atoms[inst.getIntArg1()].mem.getMemID());
					}
					atom = atoms[inst.getIntArg1()];
					atom.mem.removeAtom(atom);
					break; //n-kato
				case Instruction.NEWATOM :
					//[-dstatom, srcmem, funcref]
					func = (Functor) inst.getArg3();
					atoms[inst.getIntArg1()] = mems[inst.getIntArg2()].newAtom(func);
					if(Env.fUNYO){
						unyo.Mediator.addAddedAtom(atoms[inst.getIntArg1()]);
					}
					break; //n-kato
				case Instruction.NEWATOMINDIRECT :
					//[-dstatom, srcmem, func]
					atoms[inst.getIntArg1()] = mems[inst.getIntArg2()].newAtom((Functor)(vars.get(inst.getIntArg3())));

					if(Env.fUNYO){
						unyo.Mediator.addAddedAtom(atoms[inst.getIntArg1()]);
					}
					break; //nakajima 2003-12-27, 2004-01-03, n-kato
				case Instruction.ENQUEUEATOM :
					//[srcatom]
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
					//[atom, funcref]
					atom = atoms[inst.getIntArg1()];
					atom.mem.alterAtomFunctor(atom,(Functor)inst.getArg2());
					break; //n-kato
				case Instruction.ALTERFUNCINDIRECT :
					//[atom, func]
					atom = atoms[inst.getIntArg1()];
					atom.mem.alterAtomFunctor(atom,(Functor)(vars.get(inst.getIntArg2())));
					break; //nakajima 2003-12-27, 2004-01-03, n-kato
					//====アトムを操作する基本ボディ命令====ここまで====

					//====アトムを操作する型付き拡張用命令====ここから====
				case Instruction.ALLOCATOM : //[-dstatom, funcref]
					atoms[inst.getIntArg1()] = new Atom(null, (Functor)inst.getArg2());

					if(Env.fUNYO){
						unyo.Mediator.addAddedAtom(atoms[inst.getIntArg1()]);
					}
					break; //nakajima 2003-12-27, n-kato

				case Instruction.ALLOCATOMINDIRECT : //[-dstatom, func]
					atoms[inst.getIntArg1()] = new Atom(null, (Functor)(vars.get(inst.getIntArg2())));

					if(Env.fUNYO){
						unyo.Mediator.addModifiedAtom(atoms[inst.getIntArg1()]);
					}
					break; //nakajima 2003-12-27, 2004-01-03, n-kato

				case Instruction.COPYATOM :
					//[-dstatom, mem, srcatom]
					atoms[inst.getIntArg1()] = mems[inst.getIntArg2()].newAtom(atoms[inst.getIntArg3()].getFunctor());

					if(Env.fUNYO){
						unyo.Mediator.addAddedAtom(atoms[inst.getIntArg1()]);
					}
					break; //nakajima, n-kato

				case Instruction.ADDATOM:
					//[dstmem, atom]
					mems[inst.getIntArg1()].addAtom(atoms[inst.getIntArg2()]);
					if(Env.fUNYO){
						unyo.Mediator.addAddedAtom(atoms[inst.getIntArg2()]);
					}
					break; //nakajima 2003-12-27, n-kato
					//====アトムを操作する型付き拡張用命令====ここまで====

					//====膜を操作する基本ボディ命令====ここから====
				case Instruction.REMOVEMEM :
					//[srcmem, parentmem]
					if(Env.fUNYO){
						unyo.Mediator.addRemovedMembrane(mems[inst.getIntArg1()].getMemID(), mems[inst.getIntArg1()].getParent().getMemID());
					}
					mem = mems[inst.getIntArg1()];
					mem.parent.removeMem(mem);
					break; //n-kato
				case Instruction.NEWMEM: //[-dstmem, srcmem, memtype]
					mem = mems[inst.getIntArg2()].newMem(inst.getIntArg3());
					mems[inst.getIntArg1()] = mem;
					if(Env.fUNYO){
						unyo.Mediator.addAddedMembrane(mem);
					}
					break; //n-kato
				case Instruction.ALLOCMEM: //[-dstmem]
					mem = ((Task)mems[0].getTask()).createFreeMembrane();
					mems[inst.getIntArg1()] = mem;
					break; //n-kato

				case Instruction.NEWROOT : //[-dstmem, srcmem, nodeatom]
					String nodedesc = atoms[inst.getIntArg3()].getFunctor().getName();
					mems[inst.getIntArg1()] = mems[inst.getIntArg2()].newRoot(nodedesc, inst.getIntArg4());
					break; //n-kato 2004-09-17
				case Instruction.MOVECELLS : //[dstmem, srcmem]
					mems[inst.getIntArg1()].moveCellsFrom(mems[inst.getIntArg2()]);
					break; //nakajima 2004-01-04, n-kato
				case Instruction.ENQUEUEALLATOMS : //[srcmem]
					break;
				case Instruction.FREEMEM : //[srcmem]
					mems[inst.getIntArg1()].free();
					break; //mizuno 2004-10-12, n-kato

				case Instruction.ADDMEM :
					//[dstmem, srcmem]
					mems[inst.getIntArg2()] = mems[inst.getIntArg2()].moveTo(mems[inst.getIntArg1()]);
					if(Env.fUNYO){
						unyo.Mediator.addAddedMembrane(mems[inst.getIntArg2()]);
					}
					break; //nakajima 2004-01-04, n-kato, n-kato 2004-11-10
				case Instruction.ENQUEUEMEM:
					mems[inst.getIntArg1()].activate();
					break;
				case Instruction.UNLOCKMEM :
					//[srcmem]
					mems[inst.getIntArg1()].forceUnlock();
					break; //n-kato

				case Instruction.SETMEMNAME: //[dstmem, name]
					mems[inst.getIntArg1()].setName((String)inst.getArg2());
					if(Env.fUNYO){
						unyo.Mediator.addModifiedMembrane(mems[inst.getIntArg1()]);
					}
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
					atoms[inst.getIntArg1()].mem.newLink(
						atoms[inst.getIntArg1()], inst.getIntArg2(),
						atoms[inst.getIntArg3()], inst.getIntArg4() );
					if(Env.fUNYO){
						unyo.Mediator.addModifiedAtom(atoms[inst.getIntArg1()]);
						unyo.Mediator.addModifiedAtom(atoms[inst.getIntArg3()]);
					}
					break; //n-kato
				case Instruction.RELINK:		 //[atom1, pos1, atom2, pos2, mem]
					
					atoms[inst.getIntArg1()].mem.relinkAtomArgs(
						atoms[inst.getIntArg1()], inst.getIntArg2(),
						atoms[inst.getIntArg3()], inst.getIntArg4() );

					if(Env.fUNYO){
						unyo.Mediator.addModifiedAtom(atoms[inst.getIntArg1()]);
						unyo.Mediator.addModifiedAtom(atoms[inst.getIntArg3()]);
					}
					break; //n-kato
				case Instruction.UNIFY:		//[atom1, pos1, atom2, pos2, mem]
					
					//2005/10/11 mizuno ローカルなので、本膜を使えば問題ないはず
					mems[0].unifyAtomArgs(
						atoms[inst.getIntArg1()], inst.getIntArg2(),
						atoms[inst.getIntArg3()], inst.getIntArg4() );
					if(Env.fUNYO){
						unyo.Mediator.addModifiedAtom(atoms[inst.getIntArg1()]);
						unyo.Mediator.addModifiedAtom(atoms[inst.getIntArg3()]);
						unyo.Mediator.addModifiedAtom(atoms[inst.getIntArg1()].args[inst.getIntArg2()].getAtom());
						unyo.Mediator.addModifiedAtom(atoms[inst.getIntArg3()].args[inst.getIntArg4()].getAtom());
					}
					break; //mizuno

				case Instruction.INHERITLINK:		 //[atom1, pos1, link2, mem]
					atoms[inst.getIntArg1()].mem.inheritLink(
						atoms[inst.getIntArg1()], inst.getIntArg2(),
						(Link)vars.get(inst.getIntArg3()) );
					
					if(Env.fUNYO){
						unyo.Mediator.addModifiedAtom(atoms[inst.getIntArg1()]);
						unyo.Mediator.addModifiedAtom(((Link)vars.get(inst.getIntArg3())).getAtom());
					}
					
					break; //n-kato

				case Instruction.UNIFYLINKS:		//[link1, link2, mem]
					
					//2005/10/11 mizuno ローカルなので、本膜を使えば問題ないはず
					mems[0].unifyLinkBuddies(
						((Link)vars.get(inst.getIntArg1())),
						((Link)vars.get(inst.getIntArg2())));
					if(Env.fUNYO){
						unyo.Mediator.addModifiedAtom(((Link)vars.get(inst.getIntArg1())).getAtom());
						unyo.Mediator.addModifiedAtom(((Link)vars.get(inst.getIntArg2())).getAtom());
					}
					break; //mizuno

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
					//[dstmem, ruleset]
					mems[inst.getIntArg1()].loadRuleset((Ruleset)inst.getArg2() );
					break; //n-kato
				case Instruction.COPYRULES:
					//[dstmem, srcmem]
					mems[inst.getIntArg1()].copyRulesFrom(mems[inst.getIntArg2()]);
					break; //n-kato
				case Instruction.CLEARRULES:
					//[dstmem]
					mems[inst.getIntArg1()].clearRules();
					break; //n-kato
				case Instruction.LOADMODULE: //[dstmem, module_name]
					// モジュール膜直属のルールセットを全部読み込む
					compile.structure.Membrane m = (compile.structure.Membrane)compile.Module.memNameTable.get(inst.getArg2());
					if(m==null) {
						if (!Env.fUseSourceLibrary) { 
							//ライブラリモジュールの読み込み
							try {
								Class c = Class.forName("translated.Module_" + inst.getArg2());
								Method method = c.getMethod("getRulesets", null);
								Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
								for (int i = 0; i < rulesets.length; i++) {
									mems[inst.getIntArg1()].loadRuleset(rulesets[i]);
								}
								break;
							} catch (ClassNotFoundException e) {
							} catch (NoSuchMethodException e) {
							} catch (IllegalAccessException e) {
							} catch (InvocationTargetException e) {	}
							//例外発生時は読み込み失敗
						}
						Env.e("Undefined module "+inst.getArg2());
					} else {
						//同一ソース内のモジュール or ソースライブラリの場合
						for (Ruleset rs : m.rulesets) {
							mems[inst.getIntArg1()].loadRuleset(rs);
						}
					}
					break;
					//====ルールを操作するボディ命令====ここまで====

					//====型付きでないプロセス文脈をコピーまたは廃棄するための命令====ここから====
				case Instruction.RECURSIVELOCK : //[srcmem]
					mems[inst.getIntArg1()].recursiveLock();
				    lockedMemList.add(mems[inst.getIntArg1()]);
					break; //n-kato
				case Instruction.RECURSIVEUNLOCK : //[srcmem]
					mems[inst.getIntArg1()].recursiveUnlock();
				    lockedMemList.add(mems[inst.getIntArg1()]);
					break;//nakajima 2004-01-04, n-kato

				case Instruction.COPYCELLS : //[-dstmap, dstmem, srcmem]
					// <strike>自由リンクを持たない膜（その子膜とのリンクはOK）のみ</strike>
					vars.set(inst.getIntArg1(), mems[inst.getIntArg2()].copyCellsFrom(mems[inst.getIntArg3()]));
					break; //kudo 2004-09-29
				case Instruction.DROPMEM : //[srcmem]
					mems[inst.getIntArg1()].drop();
					break; //kudo 2004-09-29
				case Instruction.LOOKUPLINK : //[-dstlink, srcmap, srclink]
					HashMap srcmap = (HashMap)vars.get(inst.getIntArg2());
					Link srclink = (Link)vars.get(inst.getIntArg3());
					Atom la = (Atom) srcmap.get(srclink.getAtom());//new Integer(srclink.getAtom().id)); // hashCode()をidに変更 (2004-10-12) n-kato
					vars.set(inst.getIntArg1(),new Link(la, srclink.getPos()));
					break; //kudo 2004-10-10
				case Instruction.INSERTCONNECTORS : //[-dstset,linklist,mem]
					List linklist=(List)inst.getArg2();
					Set insset=new HashSet();
					Membrane srcmem=mems[inst.getIntArg3()];
					for(int i=0;i<linklist.size();i++)
						for(int j=i+1;j<linklist.size();j++){
							Link a=(Link)vars.get(((Integer)linklist.get(i)).intValue());
							Link b=(Link)vars.get(((Integer)linklist.get(j)).intValue());
							if(a==b.getBuddy()){
								Atom eq = srcmem.newAtom(Functor.UNIFY);
//								Link a2 = new Link(eq,0);
//								Link b2 = new Link(eq,1);
//								a.getAtom().args[a.getPos()] = a2;
//								a2.getAtom().args[a2.getPos()] = a;
//								b.getAtom().args[b.getPos()] = b2;
//								b2.getAtom().args[b2.getPos()] = b;
								srcmem.unifyLinkBuddies(a,new Link(eq,0));
								srcmem.unifyLinkBuddies(b,new Link(eq,1));
								insset.add(eq);
							}
						}
					vars.set(inst.getIntArg1(),insset);
					break; //kudo 2004-12-29
				case Instruction.INSERTCONNECTORSINNULL : //[-dstset, linklist]
					linklist=(List)inst.getArg2();
					insset=new HashSet();
//					srcmem=mems[inst.getIntArg3()];
					for(int i=0;i<linklist.size();i++)
						for(int j=i+1;j<linklist.size();j++){
							Link a=(Link)vars.get(((Integer)linklist.get(i)).intValue());
							Link b=(Link)vars.get(((Integer)linklist.get(j)).intValue());
							if(a==b.getBuddy()){
								Atom eq = new Atom(null, Functor.UNIFY);
//								Link a2 = new Link(eq,0);
//								Link b2 = new Link(eq,1);
								mems[0].unifyLinkBuddies(a,new Link(eq,0));
								mems[0].unifyLinkBuddies(b,new Link(eq,1));
//								a.getAtom().args[a.getPos()] = a2;
//								a2.getAtom().args[a2.getPos()] = a;
//								b.getAtom().args[b.getPos()] = b2;
//								b2.getAtom().args[b2.getPos()] = b;
//								srcmem.unifyLinkBuddies(a,new Link(eq,0));
//								srcmem.unifyLinkBuddies(b,new Link(eq,1));
								insset.add(eq);
							}
						}
					vars.set(inst.getIntArg1(),insset);
					break; //kudo 2006-09-24
				case Instruction.DELETECONNECTORS : //[srcset, srcmap]
					// 2006/09/24 膜引数を使わないように修正 kudo
					Set delset = (Set)vars.get(inst.getIntArg1());
					Map delmap = (Map)vars.get(inst.getIntArg2());
//					srcmem = mems[inst.getIntArg3()];
					it = delset.iterator();
					while(it.hasNext()){
						Atom orig=(Atom)it.next();
						Atom copy=(Atom)delmap.get(orig);//new Integer(orig.id));
						//ローカルなので本膜を使えば問題無いらしい
//						copy.mem.unifyLinkBuddies(copy.args[0], copy.args[1]);
						mems[0].unifyLinkBuddies(copy.args[0], copy.args[1]);
//						Link link1 = copy.args[0];
//						Link link2 = copy.args[1];
//						link1.getAtom().args[link1.getPos()] = link2;
//						link2.getAtom().args[link2.getPos()] = link1;
//						copy.args[0].getAtom().args[copy.args[0].getPos()]=copy.args[1];
//						copy.args[1].getAtom().args[copy.args[1].getPos()]=copy.args[0];
						if(copy.mem != null)
							copy.mem.removeAtom(copy);
					}
					break; //kudo 2004-12-29
					//====型付きでないプロセス文脈をコピーまたは廃棄するための命令====ここまで====

					//====制御命令====ここから====
				case Instruction.COMMIT :
					if (Env.fTrace) Task.trace("-->", "@" + currentInterpretedRuleset.getId(), (String)inst.getArg1());
					if (Env.debugOption) {//2006.1.26 by inui
						Debug.breakPoint(inst.getIntArg2(), Debug.ATOM);
						//if (Debug.isBreakPoint()) Task.trace("-->", "@" + id, r.toString());
					}
					if(BreakPoint.breakPointFlag_){
						BreakPoint.setCurrentRuleName((String)inst.getArg1());
					}
					// トレーサをよぶ
					break;//
				case Instruction.REACT : {
					Rule rule = (Rule) inst.getArg1();
					List bodyInsts = (List) rule.body;
					Instruction spec = (Instruction) bodyInsts.get(0);
					int formals = spec.getIntArg1();
					int locals  = spec.getIntArg2();

// // ArrayIndexOutOfBoundsException がでたので一時的に変更
// if (locals < 10) locals = 1 ;
					
					InterpretiveReactor ir = new InterpretiveReactor(locals, this.currentInterpretedRuleset);
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
					InterpretiveReactor ir = new InterpretiveReactor(locals, this.currentInterpretedRuleset);
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
					subinsts = ((InstructionList)inst.getArg1()).insts;
					if (interpret(subinsts, 0))
						return false;
					break; //n-kato

					//====制御命令====ここまで====

					//====重複適用をカットする命令====ここから====
				case Instruction.UNIQ : //[ [link1,link2...] ]
				case Instruction.NOT_UNIQ : //[ [link1,link2...] ]
					Uniq uniq = currentInterpretedRuleset.currentRule.uniq;
					if(uniq==null) {
						uniq = currentInterpretedRuleset.currentRule.uniq = new Uniq();
					}
					ArrayList uniqVars = (ArrayList)inst.getArg(0);
					List[] hEntry = new List[uniqVars.size()];
					for(int i=0;i<uniqVars.size();i++) {
						int v = ((Integer)uniqVars.get(i)).intValue();
//						hEntry[i] = (Link)((List)vars.get(v)).get(0); // とりあえず1引数のみ
						hEntry[i] = (List)vars.get(v); // groundの2引数以上化に伴いlistに
					}
					if(inst.getKind()==Instruction.UNIQ) {
						if(!uniq.check(hEntry)) return false;
					} else {
						if(uniq.check(hEntry)) return false;
					}
					break; //hara 2005-12-02
					//====重複適用をカットする命令====ここまで====
					
					//====型付きプロセス文脈を扱うための追加命令====ここから====
				case Instruction.EQGROUND : //[link1,link2]
					boolean eqground_ret = Membrane.eqGround((List)vars.get(inst.getIntArg1()),(List)vars.get(inst.getIntArg2()));
					if(!eqground_ret)return false;
					break; //kudo 2004-12-03
				case Instruction.NEQGROUND : //[link1,link2]
					boolean neqground_ret = !Membrane.eqGround((List)vars.get(inst.getIntArg1()),(List)vars.get(inst.getIntArg2()));
					if(!neqground_ret)return false;
					break; //kudo 2006-02-18
				case Instruction.COPYGROUND : //[-dstlist, srclinklist, dstmem]
					vars.set(inst.getIntArg1(),mems[inst.getIntArg3()].copyGroundFrom((List)vars.get(inst.getIntArg2())));
//					vars.set(inst.getIntArg1(),mems[inst.getIntArg3()].copyGroundFrom((List)vars.get(inst.getIntArg2())));
//					vars.set(inst.getIntArg1(),copy_ret.get(0));
//					vars.set(inst.getIntArg2(),copy_ret.get(1));
					break; //kudo 2004-12-03
				case Instruction.REMOVEGROUND : //[srclinklist,srcmem]
//					List rlinks = new ArrayList();
//					it = ((List)vars.get(inst.getIntArg1())).iterator();
//					while(it.hasNext()){
//						rlinks.add(vars.get(((Integer)it.next()).intValue()));
//					}
					mems[inst.getIntArg2()].removeGround((List)vars.get(inst.getIntArg1()));
					break; //kudo 2004-12-08
				case Instruction.FREEGROUND : //[srclinklist]
					break; //kudo 2004-12-08
					//====型付きプロセス文脈を扱うための追加命令====ここまで====

					//====型検査のためのガード命令====ここから====
				case Instruction.ISGROUND : //[-natomsfunc,srclinklist,avolist, mem]
//					it = ((List)vars.get(inst.getIntArg2())).iterator();
//					List links = new ArrayList();
//					while(it.hasNext()){
//						links.add(vars.get(((Integer)it.next()).intValue()));
//					}
					List avos = (List)vars.get(inst.getIntArg3());
					Set avoSet = new HashSet();
					avoSet.addAll(avos);
					int isground_ret = Membrane.isGround((List)vars.get(inst.getIntArg2()),avoSet);
//					int isground_ret = ((Link)vars.get(inst.getIntArg2())).isGround((Set)vars.get(inst.getIntArg3()));
					if(isground_ret == -1)return false;
					vars.set(inst.getIntArg1(),new IntegerFunctor(isground_ret));
					break; //kudo 2004-12-03
					
				case Instruction.ISUNARY: // [atom]
					Functor f = atoms[inst.getIntArg1()].getFunctor();
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
				case Instruction.ISSTRING : //[atom] // todo StringFunctorに変える（CONNECTRUNTIMEも）
					if (!(atoms[inst.getIntArg1()].getFunctor() instanceof StringFunctor)) return false;
//					if (!(atoms[inst.getIntArg1()].getFunctor() instanceof ObjectFunctor)) return false;
//					if (!(((ObjectFunctor)atoms[inst.getIntArg1()].getFunctor()).getObject() instanceof String)) return false;
					break; //n-kato
				case Instruction.ISINTFUNC : //[func]
					if (!(vars.get(inst.getIntArg1()) instanceof IntegerFunctor)) return false;
					break; //n-kato
//				case Instruction.ISFLOATFUNC : //[func]
//					break;
//				case Instruction.ISSTRINGFUNC : //[func]
//					break;

				case Instruction.GETCLASS: //[-objectatom, atom]
					if (!(atoms[inst.getIntArg2()].getFunctor() instanceof ObjectFunctor)) return false;
					atoms[inst.getIntArg1()] = atoms[inst.getIntArg2()];
					break; //n-kato
					
					//====型検査のためのガード命令====ここまで====

					//====組み込み機能に関する命令====ここから====
				case Instruction.INLINE : //[atom, inlineref]
					Inline.callInline( atoms[inst.getIntArg1()], (String)inst.getArg2(), inst.getIntArg3() );
					/// // このコードを使ってください。＞TranslatorGeneratorさん
					///writer.write(tabs + "do{ Atom me = (Atom)var" + inst.getIntArg1() + ";\n");
					///writer.write(tabs + "  mem = (AbstractMembrane)var0;\n");
					///writer.write(tabs + Inline.getCode(inst.getIntArg1(), (String)inst.getArg2(), inst.getIntArg3()));
					///writer.write(tabs + "}while(false);\n"); // インラインコードは switch の中にある前提で書かれている。
					break; //hara
//				case Instruction.BUILTIN: //[class, atom]
//					add(A,B,C) :- int(A),int(B),$builtin:iadd(A,B,C), 
//					Inline.callInline( atoms[inst.getIntArg1()], inst.getIntArg2() );
//					break;
				case Instruction.GUARD_INLINE : //[string, [Links...], [OutLinks...]]
					ArrayList gvars = (ArrayList)inst.getArg2();
					ArrayList gOutVars = (ArrayList)inst.getArg3();
					ArrayList gvars2 = new ArrayList();
					for(int i=0;i<gvars.size();i++) {
						int idx = ((Integer)gvars.get(i)).intValue();
						if(vars.size()>idx && vars.get(idx)!=null) {
							gvars2.add(vars.get(idx));
						} else {
							gvars2.add(atoms[idx]);
						}
					}
					if(! Inline.callGuardInline( (String)inst.getArg1(), (Membrane)mems[0], gvars2 ) ) return false;
					// ガードで値が変わったかもしれないので戻す
					for(int i=0;i<gvars2.size();i++) {
						int v = ((Integer)gvars.get(i)).intValue();
						if(gOutVars.contains(gvars.get(i))) {
							atoms[v] = (Atom)gvars2.get(i);
						} else {
							vars.set(v, gvars2.get(i));
						}
					}
					break;
					//====組み込み機能に関する命令====ここまで====

					//====分散拡張用の命令====ここから====

					// delete 060829 okabe
					
//				case Instruction.CONNECTRUNTIME: //[srcatom] // todo StringFunctorに変える（ISSTRINGも）
					// 060829 delete okabe
					/*
					func = atoms[inst.getIntArg1()].getFunctor();
					if (!(func instanceof ObjectFunctor)) return false;
					if (!(((ObjectFunctor)func).getObject() instanceof String)) return false;
					if (func.getName().equals("")) break; // 空文字列の場合はつねに成功とする
//					if (LMNtalRuntimeManager.connectRuntime(func.getName()) == null) return false;
					*/
//					break; //n-kato
					
				case Instruction.GETRUNTIME: //[-dstatom,srcmem] // todo StringFunctorに変える（ISSTRINGも）
					String hostname = "";
// n-kato 2006-09-07
//					if (mems[inst.getIntArg2()].isRoot())
//						hostname = mems[inst.getIntArg2()].getTask().getMachine().hostname;
					atoms[inst.getIntArg1()] = new Atom(null, new StringFunctor(hostname));
					break; //n-kato
					//====分散拡張用の命令====ここまで====
					
					//====アトムセットを操作するための命令====ここから====
				case Instruction.NEWSET : //[-dstset]
					vars.set(inst.getIntArg1(),new HashSet());
					break; //kudo 2004-12-08
				case Instruction.ADDATOMTOSET : //[srcset,atom]
					((Set)vars.get(inst.getIntArg1())).add(atoms[inst.getIntArg2()]);
					break; //kudo 2004-12-08
					
				case Instruction.NEWLIST: //[-dstlist]
					vars.set(inst.getIntArg1(),new ArrayList());
					break; //kudo 2006-09-15
				case Instruction.ADDTOLIST: // [dstlist, src]
					((List)vars.get(inst.getIntArg1())).add(vars.get(inst.getIntArg2()));
					break;
				case Instruction.GETFROMLIST: // [-dst, list, pos]
					vars.set(inst.getIntArg1(),((List)vars.get(inst.getIntArg2())).get(inst.getIntArg3()));
					break;
					
					//====アトムセットを操作するための命令====ここまで====
					
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

				case Instruction.GROUP:
					subinsts = ((InstructionList)inst.getArg1()).insts;
					if(!interpret(subinsts, 0)){
						//GROUP内の命令で失敗したらルール適用失敗
						//その前のGROUP内で取得した膜のロックを開放する
						Iterator it2 = lockedMemList.iterator();
						while(it2.hasNext()){
							((Membrane)it2.next()).unlock();
						}
						return false;
					}
					break;
				case Instruction.SYSTEMRULESETS:
					boolean srsflag = true;
					subinsts = ((InstructionList)inst.getArg1()).insts;
					List subinsts2 = ((InstructionList)inst.getArg2()).insts;
					Iterator flagit = ((ArrayList)inst.getArg3()).iterator();
					while(flagit.hasNext()){
						int flagvar = ((Integer)flagit.next()).intValue();
						if(flagvar >= atoms.length
								|| atoms[flagvar] == null){
							srsflag = false;
							break;
						}
					}
					if(srsflag){
						if(!interpret(subinsts, 0)){
							srsflag = false;
							interpret(subinsts2, 0);
						}
					}
					else interpret(subinsts2, 0);
					break;
					//sakurai
				
				// 2006/07/09 by kudo
				case Instruction.ISBUDDY: // isbuddy [ link1, link2 ]
					Link l1 = (Link)vars.get(inst.getIntArg1());
					Link l2 = (Link)vars.get(inst.getIntArg2());
					if(l1.getBuddy() != l2)return false;
					break;

				default :
					System.err.println(
						"SYSTEM ERROR: Invalid instruction: " + inst);
					break;
			}
		}
		return false;
	}
}
