package compile;

import java.util.*;
import runtime.Env;
import runtime.Rule;
//import runtime.InterpretedRuleset;
import runtime.Instruction;
import runtime.Functor;
import runtime.Inline;
import compile.structure.*;

// TODO 【確認】左辺膜の自由リンクに対応する自由リンク管理アトムは生成されないようになっているかどうか

/**
 * <pre>
 * コンパイル時データ構造（膜）にルールセットを付加する。
 * 
 * 具体的には、Membrane -> RuleSet が付加された Membrane
 * 
 * 外部からは、{ ( :- WORLD ) } の形式で呼ばれることになる。
 * WORLD にはルールが含まれる場合もあるので、
 * 見つかったルールからルールオブジェクトを生成してその膜のルールセットに追加する
 * という作業を再帰的にやることになる。
 * 
 * </pre>
 * 
 * - ルール内ルールの処理方法の改善案については下のtodoを参照のこと
 * 
 * @author n-kato, hara
 */
public class RuleCompiler {
	/** 単一化を意味する（ものとしてRuleCompilerが考える）ファンクタ。=/2 */
	public static final Functor FUNC_UNIFY = new Functor("=", 2);

	/** コンパイルされるルール構造 */
	public RuleStructure rs;
	
	/** コンパイルされるルールに対応するルールオブジェクト */
	public Rule theRule;
	
	public List atomMatch;
	public List memMatch;
	public List body;
	
	public int varcount;
	
	public List rhsatoms;
	public Map  rhsatompath;
	public Map  rhsmempath;
	
	public List lhsatoms;
	public List lhsfreemems;
	public Map  lhsatompath;
	public Map  lhsmempath;
	
//	private List newatoms = new ArrayList();	// rhsatomsと同じなので統合
	
	HeadCompiler hc;
	
	final int lhsmemToPath(Membrane mem) { return ((Integer)lhsmempath.get(mem)).intValue(); }
	final int rhsmemToPath(Membrane mem) { return ((Integer)rhsmempath.get(mem)).intValue(); }
	//final int lhsatomToID(Atom atom) { return lhsatomToPath(atom) - 1; }
	final int lhsatomToPath(Atom atom) { return ((Integer)lhsatompath.get(atom)).intValue(); } 
	final int rhsatomToPath(Atom atom) { return ((Integer)rhsatompath.get(atom)).intValue(); } 
	
	/**
	 * 指定された RuleStructure 用のルールをつくる
	 */
	RuleCompiler(RuleStructure rs) {
		Env.n("RuleCompiler");
		Env.p(rs);
		this.rs = rs;
	}
	
	/**
	 * 初期化時に指定されたルール構造をルールオブジェクトにコンパイルし、
	 * 所属膜のルールセットに追加する。
	 */
	public void compile() {
		Env.c("compile");
		simplify();
		theRule = new Rule(rs.toString());
		//@ruleid = rule.ruleid		
		
		hc = new HeadCompiler(rs.leftMem);
		hc.enumFormals(rs.leftMem);	// ヘッドに対する仮引数リストを作る
		
		compile_l();
		compile_r();
		
		optimize();	// optimize if $optlevel > 0
		
		//showInstructions();
		
		//rule.register(@atomMatches,@memMatch,@body)
		theRule.memMatch  = memMatch;
		theRule.atomMatch = atomMatch;
		theRule.body      = body;
		
		theRule.showDetail();
		
		// TODO 以下の3行は、RulesetCompilerが行うべきである。また、整合性検査の都合上、ルール内ルールの方から先にコンパイルすべきである。
		// compiler.structure.Membrane::rulesetの参照を外側ルールのコンパイルで使用するのは構わない。
		
		((runtime.InterpretedRuleset)rs.parent.ruleset).rules.add(theRule);

		// ルールの右辺膜以下にルールがあるかもしれないので再帰的に走査
		RuleSetGenerator.processMembrane(rs.leftMem); // 一応左辺も
		RuleSetGenerator.processMembrane(rs.rightMem);
	}
	
	/** 左辺膜をコンパイルする */
	private void compile_l() {
		Env.c("compile_l");
		
		atomMatch = new ArrayList();
		for (int firstid = 0; firstid <= hc.atoms.size(); firstid++) {
			hc.prepare(); // 変数番号を初期化			
			if (firstid < hc.atoms.size()) {			
				if (true) continue; // 臨時
				// アトム主導
				List singletonListArgToBranch = new ArrayList();
				singletonListArgToBranch.add(hc.match);
				atomMatch.add(new Instruction(Instruction.BRANCH, singletonListArgToBranch));
				hc.mempath.put(rs.leftMem, new Integer(0));	// 本膜の変数番号は 0
				hc.atomidpath.set(firstid, new Integer(1));	// 主導するアトムの変数番号は 1
				hc.varcount = 2;
				Atom atom = (Atom)hc.atoms.get(firstid);
				hc.match.add(new Instruction(Instruction.FUNC, 1, atom.functor));
				Membrane mem = atom.mem;
				if (mem == rs.leftMem) {
					hc.match.add(new Instruction(Instruction.TESTMEM, 0, 1));
				}
				else {
					hc.match.add(new Instruction(Instruction.GETMEM, varcount, 1));
					hc.mempath.put(mem, new Integer(varcount++));
					do {
						hc.match.add(new Instruction(Instruction.GETPARENT,varcount,varcount-1));
						hc.mempath.put(mem, new Integer(varcount++));
						mem = mem.mem;
					}	
					while (mem != rs.leftMem);
					hc.match.add(new Instruction(Instruction.EQMEM, 0, varcount-1));
					for (int i = varcount-1; --i >= 2; ) {
						hc.match.add(new Instruction(Instruction.LOCK,i));
					}
				}
				hc.compileLinkedGroup(firstid);
			} else {
				// 膜主導
				memMatch = hc.match;
				hc.mempath.put(rs.leftMem, new Integer(0));	// 本膜の変数番号は 0
			}
			hc.compileMembrane(rs.leftMem);
			hc.match.add( Instruction.react(theRule, hc.getMemActuals(), hc.getAtomActuals()) );
		}
	}
	
	/** 右辺膜をコンパイルする */
	private void compile_r() {
		Env.c("compile_r");
		
		// ヘッドの取り込み
		lhsatoms = hc.atoms;
		lhsfreemems = hc.freemems;
		genLHSMemPaths();
		varcount = lhsatoms.size() + lhsfreemems.size();
		int formals = varcount;
		
		//
		body = new ArrayList();
		rhsatoms    = new ArrayList();
		rhsatompath = new HashMap();
		rhsmempath  = new HashMap();
		int toplevelmemid = lhsmemToPath(rs.leftMem);
		rhsmempath.put(rs.rightMem, new Integer(toplevelmemid));
		
		//Env.p("rs.leftMem -> "+rs.leftMem);
		//Env.p("lhsmempaths.get(rs.leftMem) -> "+lhsmempaths.get(rs.leftMem));
		//Env.p("rhsmempaths -> "+rhsmempaths);
		
		removeLHSAtoms();
		if (removeLHSMem(rs.leftMem) >= 2) {
			body.add(new Instruction(Instruction.REMOVETOPLEVELPROXIES, toplevelmemid));
		}
		buildRHSMem(rs.rightMem);
		if (!rs.rightMem.processContexts.isEmpty()) {
			body.add(new Instruction(Instruction.REMOVETEMPORARYPROXIES, toplevelmemid));
		}
		copyRules(rs.rightMem);
		loadRulesets(rs.rightMem);		
		buildRHSAtoms(rs.rightMem);
		body.add(0, Instruction.spec(formals, varcount));
		updateLinks();
		addInline();
		freeLHSMem(rs.leftMem);
		freeLHSAtoms();
		body.add(new Instruction(Instruction.PROCEED));
	}
	
	/** ルールの左辺と右辺に対してstaticUnifyを呼ぶ */
	public void simplify() {
		staticUnify(rs.leftMem);
		staticUnify(rs.rightMem);
	}
	
	/** 指定された膜とその子孫に存在する冗長な =（todo および自由リンク管理アトム）を除去する */
	public void staticUnify(Membrane mem) {
		Env.c("RuleCompiler::staticUnify");
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			staticUnify((Membrane)it.next());
		}
		ArrayList removedAtoms = new ArrayList();
		it = mem.atoms.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();
			if (atom.functor.equals(FUNC_UNIFY)) {
				LinkOccurrence link1 = atom.args[0].buddy;
				LinkOccurrence link2 = atom.args[1].buddy;
				if (link1.atom.mem != mem && link2.atom.mem != mem) {
					// 単一化アトムのリンク先が両方とも他の膜につながっている場合
					if (mem == rs.leftMem) {
						// ( X=Y :- p(X,Y) ) は意味解析エラー
						//$nerrors += 1;
						System.out.println("Compile error: head contains body unification");
					}
					else {
						// ( p(X,Y) :- X=Y ) はUNIFYボディ命令を出力するのでここでは何もしない
					}
				} else {
					link1.atom.args[link1.pos] = link2;
					link2.atom.args[link2.pos] = link1;
					link1.buddy = link2;
					link2.buddy = link1;
					removedAtoms.add(atom);
				}
			}
		}
		it = removedAtoms.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();
			atom.mem.atoms.remove(atom);
		}
	}
	
	/** ヘッドの膜とアトムに対して、仮引数番号を登録する */
	private void genLHSMemPaths() {
		Env.c("RuleCompiler::genLHSMemPaths");
		lhsatompath = new HashMap();
		lhsmempath  = new HashMap();
		for (int i = 0; i < lhsfreemems.size(); i++) {
			lhsmempath.put(lhsfreemems.get(i), new Integer(i));
		}
		for (int i = 0; i < lhsatoms.size(); i++) {
			lhsatompath.put(lhsatoms.get(i), new Integer( lhsfreemems.size() + i ));
		}
		//Env.p("lhsmempaths"+lhsmempaths);
	}
	
	private void optimize() {
		Env.c("optimize");
	}	
	private void removeLHSAtoms() {
		Env.c("RuleCompiler::removeLHSAtoms");
		for (int i = 0; i < lhsatoms.size(); i++) {
			body.add( new Instruction(Instruction.REMOVEATOM, i+1) );
		}
	}
	/** 左辺の膜を子膜側から再帰的に除去する。
	 * @return 膜memの内部に出現したプロセス文脈の個数 */
	private int removeLHSMem(Membrane mem) {
		Env.c("RuleCompiler::removeLHSMem");
		int procvarcount = mem.processContexts.size();
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			int subcount = removeLHSMem(submem);
			body.add(new Instruction(Instruction.REMOVEMEM, lhsmemToPath(submem)));
			if (subcount > 0) {
				body.add(new Instruction(Instruction.REMOVEPROXIES, lhsmemToPath(submem)));
			}
			procvarcount += subcount;
		}
		return procvarcount;
	}	

	/** 膜の階層構造およびプロセス文脈の内容を親膜側から再帰的に生成する。
	 * @return */
	private int buildRHSMem(Membrane mem) {
		Env.p("RuleCompiler::buildRHSMem" + mem);
		int procvarcount = mem.processContexts.size();
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			int submempath = varcount++;
			rhsmempath.put(submem, new Integer(submempath));
			body.add( Instruction.newmem(submempath, rhsmemToPath(mem)) );
			int subcount = buildRHSMem(submem);
			if (subcount > 0) {
				body.add(new Instruction(Instruction.INSERTPROXIES,
					rhsmemToPath(mem), rhsmemToPath(submem)));
			}
			procvarcount += subcount;
		}
		it = mem.processContexts.iterator();
		while (it.hasNext()) {
			ProcessContext pc = (ProcessContext)it.next();
			if (rhsmemToPath(mem) != lhsmemToPath(pc.lhsMem)) {
				body.add(new Instruction(Instruction.MOVECELLS,
					rhsmemToPath(mem), lhsmemToPath(pc.lhsMem) ));
			}
		}
		return procvarcount;
	}
	
	/** 右辺の膜内のルール文脈の内容を生成する */
	private void copyRules(Membrane mem) {
		Env.c("RuleCompiler::copyRules");
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			copyRules((Membrane)it.next());
		}
		it = mem.ruleContexts.iterator();
		while (it.hasNext()) {
			RuleContext rc = (RuleContext)it.next();			
			if (rhsmemToPath(mem) == lhsmemToPath(rc.lhsMem)) continue;
			body.add(new Instruction( Instruction.COPYRULES, rhsmemToPath(mem), lhsmemToPath(rc.lhsMem) ));
		}
	}
	/** 右辺の膜内のルールの内容を生成する */	
	private void loadRulesets(Membrane mem) {
		Env.c("RuleCompiler::loadRulesets");
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			loadRulesets((Membrane)it.next());
		}
		if (!mem.rules.isEmpty()) {
			body.add(Instruction.loadruleset(rhsmemToPath(mem), mem.ruleset));
		} 
	}
	/** 右辺の膜内のアトムを生成する。
	 * 単一化アトムならばUNIFY命令を生成し、
	 * それ以外ならば右辺のアトムのリストrhsatomsに追加する。 */
	private void buildRHSAtoms(Membrane mem) {
		Env.c("RuleCompiler::buildRHSAtoms");
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			buildRHSAtoms((Membrane)it.next());
		}
		it = mem.atoms.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();			
			if (atom.functor.equals(FUNC_UNIFY)) {
				LinkOccurrence link1 = atom.args[0].buddy;
				LinkOccurrence link2 = atom.args[1].buddy;
				body.add(new Instruction( Instruction.UNIFY,
					lhsatomToPath(link1.atom), link1.pos,
					lhsatomToPath(link2.atom), link2.pos ));
			} else {
				int atomid = varcount++;
				rhsatompath.put(atom, new Integer(atomid));
				rhsatoms.add(atom);
//				// NEWATOM した分を覚えておく
//				newatoms.add(atom);
				body.add( Instruction.newatom(atomid, rhsmemToPath(mem), atom.functor));
			}
		}
	}
	/** リンクの張り替えと生成を行う */
	private void updateLinks() {
		Env.c("RuleCompiler::updateLinks");
		Iterator it = rhsatoms.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();			
			for (int pos = 0; pos < atom.functor.getArity(); pos++) {
				LinkOccurrence link = atom.args[pos].buddy;
				//Env.p(atom+"("+pos+")"+" buddy -> "+link.buddy.atom+" link.atom="+link.atom);
				if (link.atom.mem == rs.leftMem) {
					body.add( new Instruction(Instruction.RELINK,
						rhsatomToPath(atom), pos,
						lhsatomToPath(link.atom), link.pos));
				} else {
					if (rhsatomToPath(atom) < rhsatomToPath(link.atom)
					|| (rhsatomToPath(atom) == rhsatomToPath(link.atom) && pos < link.pos)) {
						body.add( new Instruction(Instruction.NEWLINK,
							rhsatomToPath(atom), pos,
							rhsatomToPath(link.atom), link.pos));
					}
				}
			}
		}
	}
	/**
	 * インライン命令を生成する。
	 */
	private void addInline() {
		Iterator it = rhsatoms.iterator();
		while(it.hasNext()) {
			Atom atom = (Atom)it.next();
			int atomID = rhsatomToPath(atom);
			int codeID = Inline.getCodeID(atom.functor.getName());
			if(codeID==-1) continue;
			body.add( new Instruction(Instruction.INLINE, atomID, codeID));
		}
	}
	/** 左辺の膜を除去する */
	private void freeLHSMem(Membrane mem) {
		Env.c("RuleCompiler::freeLHSMem");
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			freeLHSMem(submem);
			// 再利用された場合freeしてはいけない
			body.add(new Instruction(Instruction.FREEMEM, lhsmemToPath(submem)));
		}
	}
	private void freeLHSAtoms() {
		for (int i = 0; i < lhsatoms.size(); i++) {
			body.add( new Instruction(Instruction.FREEATOM, i+1) );
		}
	}

	/**
	 * デバッグ用表示
	 */
	private void showInstructions() {
		Iterator it;
		it = atomMatch.listIterator();
		Env.p("--atomMatches :");
		while(it.hasNext()) Env.p((Instruction)it.next());
		
		it = memMatch.listIterator();
		Env.p("--memMatch :");
		while(it.hasNext()) Env.p((Instruction)it.next());
		
		it = body.listIterator();
		Env.p("--body :");
		while(it.hasNext()) Env.p((Instruction)it.next());
	}
}

