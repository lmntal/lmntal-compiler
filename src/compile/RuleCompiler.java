package compile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import runtime.Env;
import runtime.Functor;
import runtime.Inline;
import runtime.InlineUnit;
import runtime.Instruction;
import runtime.InstructionList;
import runtime.Rule;
import runtime.SymbolFunctor;

import compile.structure.Atom;
import compile.structure.Atomic;
import compile.structure.Context;
import compile.structure.ContextDef;
import compile.structure.LinkOccurrence;
import compile.structure.Membrane;
import compile.structure.ProcessContext;
import compile.structure.RuleContext;
import compile.structure.RuleStructure;

/**
 * コンパイル時ルール構造（compile.structure.RuleStructure）を
 * （インタプリタ動作可能な）ルールオブジェクト（runtime.Rule）に変換する。
 * <p>
 * 子ルール構造は無視される代わりに、同じ膜の持つルールセット（runtime.Ruleset）が参照される。
 * したがってこのクラスを呼び出す RulesetCompiler は、
 * 子ルール構造を事前にルールセットにコンパイルしておく必要がある。
 * 
 * @author n-kato, hara
 */
public class RuleCompiler {
	/** コンパイルされるルール構造 */
	public RuleStructure rs;
	
	/** コンパイルされるルールに対応するルールオブジェクト */
	public Rule theRule;
	
	public List<Instruction> atomMatch;
	public List<Instruction> memMatch;
	public List<Instruction> guard;
	public List<Instruction> body;
	int varcount;			// 次の変数番号
	
	List rhsatoms;
	Map  rhsatompath;		// 右辺のアトム (Atomic) -> 変数番号 (Integer)
	Map  rhsmempath;		// 右辺の膜 (Membrane) -> 変数番号 (Integer)	
	Map  rhslinkpath;		// 右辺のリンク出現(LinkOccurence) -> 変数番号(Integer)
	//List rhslinks;		// 右辺のリンク出現(LinkOccurence)のリスト（片方のみ） -> computeRHSLinksの返り血にした
	List lhsatoms;
	List lhsmems;
	Map  lhsatompath;		// 左辺のアトム (Atomic) -> 変数番号 (Integer)
	Map  lhsmempath;		// 左辺の膜 (Membrane) -> 変数番号 (Integer)
	Map  lhslinkpath = new HashMap();		// 左辺のアトムのリンク出現 (LinkOccurrence) -> 変数番号(Integer)
		// ＜左辺のアトムの変数番号 (Integer) -> リンクの変数番号の配列 (int[])　＞から変更
	
	HeadCompiler hc;
	
	final int lhsmemToPath(Membrane mem) { return ((Integer)lhsmempath.get(mem)).intValue(); }
	final int rhsmemToPath(Membrane mem) { return ((Integer)rhsmempath.get(mem)).intValue(); }
	final int lhsatomToPath(Atomic atom) { return ((Integer)lhsatompath.get(atom)).intValue(); } 
	final int rhsatomToPath(Atomic atom) { return ((Integer)rhsatompath.get(atom)).intValue(); } 
	final int lhslinkToPath(Atomic atom, int pos) {
		return lhslinkToPath(atom.args[pos]);
	}
	final int lhslinkToPath(LinkOccurrence link) {
		return ((Integer)lhslinkpath.get(link)).intValue();
	}
	
	public String unitName;
	/** ヘッドのマッチング終了後の継続命令列のラベル */
	private InstructionList contLabel;

	/**
	 * 指定された RuleStructure 用のルールをつくる
	 */
	RuleCompiler(RuleStructure rs) {
		this(rs, InlineUnit.DEFAULT_UNITNAME);
	}
	RuleCompiler(RuleStructure rs, String unitName) {
		//Env.n("RuleCompiler");
		//Env.d(rs);
		this.unitName = unitName;
		this.rs = rs;
	}
	/**
	 * 初期化時に指定されたルール構造をルールオブジェクトにコンパイルする
	 */
	public Rule compile() throws CompileException {
		Env.c("compile");
		liftupActiveAtoms(rs.leftMem);
		simplify();
//		theRule = new Rule(rs.toString());
		theRule = new Rule(rs.leftMem.getFirstAtomName(),rs.toString());
		theRule.name = rs.name;
		
		hc = new HeadCompiler();//rs.leftMem;
		hc.enumFormals(rs.leftMem);	// 左辺に対する仮引数リストを作る
		
		//とりあえず常にガードコンパイラを呼ぶ事にしてしまう by mizuno
//		if (!rs.typedProcessContexts.isEmpty() || !rs.guardNegatives.isEmpty()) {
		if (true) {
			theRule.guardLabel = new InstructionList();
			guard = theRule.guardLabel.insts;
		}
		else guard = null;
		theRule.bodyLabel = new InstructionList();
		body = theRule.bodyLabel.insts;
		contLabel = (guard != null ? theRule.guardLabel : theRule.bodyLabel);		
		
		compile_l();
		compile_g();
		compile_r();
		
		theRule.memMatch  = memMatch;
		theRule.atomMatch = atomMatch;
		theRule.guard     = guard;
		theRule.body      = body;
		optimize();
		return theRule;
	}
	
	/** 左辺膜をコンパイルする */
	private void compile_l() {
		Env.c("compile_l");
		
		theRule.atomMatchLabel = new InstructionList();
		atomMatch = theRule.atomMatchLabel.insts;
		
		int maxvarcount = 2;	// アトム主導用（仮）
		for (int firstid = 0; firstid <= hc.atoms.size(); firstid++) {
			hc.prepare(); // 変数番号を初期化			
			if (firstid < hc.atoms.size()) {			
				if (Env.shuffle >= Env.SHUFFLE_DONTUSEATOMSTACKS) continue;
				// Env.SHUFFLE_DEFAULT ならば、ルールの反応確率を優先するためアトム主導テストは行わない
				
				Atom atom = (Atom)hc.atoms.get(firstid);
				if (!atom.functor.isActive()) continue;
				
				// アトム主導
				InstructionList tmplabel = new InstructionList();
				tmplabel.insts = hc.match;
				atomMatch.add(new Instruction(Instruction.BRANCH, tmplabel));
				
				hc.mempaths.put(rs.leftMem, new Integer(0));	// 本膜の変数番号は 0
				hc.atompaths.put(atom, new Integer(1));		// 主導するアトムの変数番号は 1
				hc.varcount = 2;
				hc.match.add(new Instruction(Instruction.FUNC, 1, atom.functor));
				Membrane mem = atom.mem;
				if (mem == rs.leftMem) {
					hc.match.add(new Instruction(Instruction.TESTMEM, 0, 1));
				}
				else {
					hc.match.add(new Instruction(Instruction.GETMEM, hc.varcount, 1, mem.kind, mem.name));
					hc.match.add(new Instruction(Instruction.LOCK,   hc.varcount));
					hc.mempaths.put(mem, new Integer(hc.varcount++));
					mem = mem.parent;
					while (mem != rs.leftMem) {
						hc.match.add(new Instruction(Instruction.GETPARENT,hc.varcount,hc.varcount-1));
						hc.match.add(new Instruction(Instruction.LOCK,     hc.varcount));
						hc.mempaths.put(mem, new Integer(hc.varcount++));
						mem = mem.parent;
					}
					hc.match.add(new Instruction(Instruction.GETPARENT,hc.varcount,hc.varcount-1));
					hc.match.add(new Instruction(Instruction.EQMEM, 0, hc.varcount++));
				}
				hc.getLinks(1, atom.functor.getArity()); //リンクの一括取得(RISC化) by mizuno
				Atom firstatom = (Atom)hc.atoms.get(firstid);
				hc.compileLinkedGroup(firstatom);
				hc.compileMembrane(firstatom.mem);
			} else {
				// 膜主導
				theRule.memMatchLabel = hc.matchLabel;
				memMatch = hc.match;
				hc.mempaths.put(rs.leftMem, new Integer(0));	// 本膜の変数番号は 0
			}
			hc.compileMembrane(rs.leftMem);
			// 自由出現したデータアトムがないか検査する
			if (!hc.fFindDataAtoms) {
				if (Env.debug >= 1) {
					Iterator it = hc.atoms.iterator();
					while (it.hasNext()) {
						Atom atom = (Atom)it.next();
						if (!hc.isAtomLoaded(atom)) {
							Env.warning("TYPE WARNING: Rule head contains free data atom: " + atom);
						}
					}
				}
				hc.switchToUntypedCompilation();
				hc.compileMembrane(rs.leftMem);
			}
			hc.checkFreeLinkCount(rs.leftMem); // 言語仕様変更により呼ばなくてよくなった→やはり呼ぶ必要あり
			if (hc.match == memMatch) {
				hc.match.add(0, Instruction.spec(1, hc.varcount));
			}
			else {
				hc.match.add(0, Instruction.spec(2, hc.varcount));
			}
			// jump命令群の生成
			List memActuals  = hc.getMemActuals();
			List atomActuals = hc.getAtomActuals();
			List varActuals  = hc.getVarActuals();
			// - コード#1
			hc.match.add( Instruction.jump(contLabel, memActuals, atomActuals, varActuals) );
			// - コード#2
//			hc.match.add( Instruction.inlinereact(theRule, memActuals, atomActuals, varActuals) );
//			int formals = memActuals.size() + atomActuals.size() + varActuals.size();
//			hc.match.add( Instruction.spec(formals, formals) );
//			hc.match.add( hc.getResetVarsInstruction() );
//			List brancharg = new ArrayList();
//			brancharg.add(body);
//			hc.match.add( new Instruction(Instruction.BRANCH, brancharg) );
			
			// jump命令群の生成終わり
			if (maxvarcount < hc.varcount) maxvarcount = hc.varcount;
		}
		atomMatch.add(0, Instruction.spec(2,maxvarcount));
	}
	
	// todo spec命令を外側に持ち上げる最適化器を実装する
	
	/**
	 * 左辺のアトムのリンクに対してgetlinkを行い、変数番号を登録する。(RISC化)
	 * 将来的にはリンクオブジェクトをボディ命令列の引数に渡すようにするかもしれない。
	 */
//	private void getLHSLinks() {
//		lhslinkpath = new HashMap();
//		for (int i = 0; i < lhsatoms.size(); i++) {
//			Atom atom = (Atom)lhsatoms.get(i);
//			int atompath = lhsatomToPath(atom);
//			int arity = atom.functor.getArity();
//			for (int j = 0; j < arity; j++) {
//				int linkpath;
//				// リンク先がgroundの場合、既にGETLINKは発行されている(getGroundLinkPaths)
//				if(!(atom.args[j].buddy.atom instanceof Context &&
//					groundsrcs.containsKey(((Context)atom.args[j].buddy.atom).def))){
//					linkpath = varcount++;
//					body.add(new Instruction(Instruction.GETLINK, linkpath, atompath, j));
//				}
//				else{
//					linkpath = ((Integer)groundsrcs.get(((Context)atom.args[j].buddy.atom).def)).intValue();
//				}
//				lhslinkpath.put(atom.args[j],new Integer(linkpath));
//			}
//		}
//	}

	/** 右辺のリンクを取得または生成する */
	private List computeRHSLinks() {
		List rhslinks = new ArrayList();
		rhslinkpath = new HashMap();
		int rhslinkindex = 0;
		// アトムの引数のリンク出現
		Iterator it = rhsatoms.iterator();
		while(it.hasNext()){
			Atom atom = (Atom)it.next();
			for (int pos = 0; pos < atom.functor.getArity(); pos++) {
				body.add(new Instruction(Instruction.ALLOCLINK,varcount,rhsatomToPath(atom),pos));
				rhslinkpath.put(atom.args[pos],new Integer(varcount));
				if(!rhslinks.contains(atom.args[pos].buddy) &&
				!(atom.functor.equals(Functor.INSIDE_PROXY) && pos == 0))
					rhslinks.add(rhslinkindex++,atom.args[pos]);
				varcount++;
			}
		}

		// unary型付プロセス文脈のリンク出現
		it = rhstypedcxtpaths.keySet().iterator();
		while(it.hasNext()){
			ProcessContext atom = (ProcessContext)it.next();
			body.add(new Instruction(Instruction.ALLOCLINK,varcount,rhstypedcxtToPath(atom),0));
			rhslinkpath.put(atom.args[0],new Integer(varcount));
			if(!rhslinks.contains(atom.args[0].buddy))rhslinks.add(rhslinkindex++,atom.args[0]);
			varcount++;
		}
		
		// ground型付プロセス文脈のリンク出現
		it = rhsgroundpaths.keySet().iterator();
		while(it.hasNext()){
			ProcessContext ground = (ProcessContext)it.next();
			int linklistpath = rhsgroundToPath(ground);
			for(int i=0;i<ground.def.lhsOcc.args.length;i++){
				int linkpath = varcount++;
				body.add(new Instruction(Instruction.GETFROMLIST,linkpath, linklistpath, i));
//				int linkpath = rhsgroundToPath(atom);
				rhslinkpath.put(ground.args[i],new Integer(linkpath));
				if(!rhslinks.contains(ground.args[i].buddy))rhslinks.add(rhslinkindex++,ground.args[i]);
			}
		}

		// 型なし
		it = rs.processContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			Iterator it2 = def.rhsOccs.iterator();
			while (it2.hasNext()) {
				ProcessContext atom = (ProcessContext)it2.next();
				for (int pos = 0; pos < atom.getArity(); pos++) {
//					LinkOccurrence srclink = atom.def.lhsOcc.args[pos].buddy;
//					int srclinkid;
//					if(!lhslinkpath.containsKey(srclink)){
//						srclinkid = varcount++;
//						body.add( new Instruction(Instruction.GETLINK,srclinkid, 
//													lhsatomToPath(srclink.atom), srclink.pos));
//						lhslinkpath.put(srclink,new Integer(srclinkid));
//					}
//					srclinkid = lhslinkToPath(srclink);
//					if (!(fUseMoveCells && atom.def.rhsOccs.size() == 1)) {							
//						int copiedlink = varcount++;
//						body.add( new Instruction(Instruction.LOOKUPLINK,
//										copiedlink, rhspcToMapPath(atom), srclinkid));
//						srclinkid = copiedlink;
//					}
//					rhslinkpath.put(atom.args[pos],new Integer(srclinkid));
					if(!rhslinks.contains(atom.args[pos].buddy))rhslinks.add(rhslinkindex++,atom.args[pos]);
				}
			}
		}
		return rhslinks;
	}
	
	private int getLinkPath(LinkOccurrence link){
		if(rhslinkpath.containsKey(link)){
			return ((Integer)rhslinkpath.get(link)).intValue();
		}
		else if (link.atom instanceof ProcessContext && !((ProcessContext)link.atom).def.typed){
			LinkOccurrence srclink = ((ProcessContext)link.atom).def.lhsOcc.args[link.pos].buddy;
			int linkpath = varcount++;
			body.add(new Instruction(Instruction.GETLINK,linkpath,lhsatomToPath(srclink.atom),srclink.pos));
			if(!(fUseMoveCells && ((ProcessContext)link.atom).def.rhsOccs.size() == 1)) {
				int copiedlink = varcount++;
				body.add( new Instruction(Instruction.LOOKUPLINK,
								copiedlink, rhspcToMapPath(((ProcessContext)link.atom)), linkpath));
				return copiedlink;
			}
			return linkpath;
		}
		else{
			if(!lhslinkpath.containsKey(link)){
				int linkpath = varcount++;
				body.add(new Instruction(Instruction.GETLINK,linkpath,lhsatomToPath(link.atom),link.pos));
				lhslinkpath.put(link,new Integer(linkpath));
			}
			return lhslinkToPath(link);
		}
	}

	/** 右辺膜をコンパイルする 
	 * 
	 * LMNParserが，左辺に出現するリンクに対して必要に応じて自由リンク管理アトムを挿入している
	 * 
	 * 左辺の膜のロックはマッチングの時点で行う
	 * 
	 * */
	private void compile_r() throws CompileException {
		Env.c("compile_r");
		int formals = varcount;
		body.add( Instruction.commit(theRule) );
		inc_guard();
			
		rhsatoms    = new ArrayList();
		rhsatompath = new HashMap();
		rhsmempath  = new HashMap();
		int toplevelmemid = lhsmemToPath(rs.leftMem);
		rhsmempath.put(rs.rightMem, new Integer(toplevelmemid));
		
		//Env.d("rs.leftMem -> "+rs.leftMem);
		//Env.d("lhsmempaths.get(rs.leftMem) -> "+lhsmempaths.get(rs.leftMem));
		//Env.d("rhsmempaths -> "+rhsmempaths);

		/*
		 * 左辺の明示的なプロセスを除去(所属膜との関係を絶つ)する
		 * 非線形$pの全ての子膜を再帰的にlockする
		 * 非線形$pの自由リンクにコネクタを挿入する
		 * 
		 * 終わると：
		 * 左辺のアトム(明示的な自由リンク管理アトムを含む)/unaryは変数番号にバインドされ，所属膜からは除去され，実行アトムスタックからも除去されている
		 * 左辺の膜は変数番号にバインドされ，親膜および実行膜スタックから除去され，ロックされている
		 * 左辺のgroundは根が変数番号にバインドされ，所属膜からは除去されている
		 * 型なし$pはマッチしたプロセスの全ての明示的でない自由リンクはstarの第1引数に出現するようになっている
		 * 非線形型なし$pの場合更に明示的な自由リンクに=/2が挿入され，明示的な自由リンクのリストへのマップが生成されている
		 * 非線形$pの子膜は再帰的にロックされている
		 */
		dequeueLHSAtoms();
		removeLHSAtoms();
		removeLHSTypedProcesses();
		if (removeLHSMem(rs.leftMem) >= 2) {
			body.add(new Instruction(Instruction.REMOVETOPLEVELPROXIES, toplevelmemid));
		}

		recursiveLockLHSNonlinearProcessContextMems();
		insertconnectors();

		// insertconnectorsの後でなければうまくいかないので再発行 ( 2006/09/15 kudo)
		getGroundLinkPaths();


		// 右辺の構造と$pの内容，を再帰的に生成する
		// $pの明示的でないリンクをはる

		buildRHSMem(rs.rightMem);
		/* 右辺の$pが配置された直後。このタイミングでなければならない筈 */
		if (!rs.rightMem.processContexts.isEmpty()) {
			body.add(new Instruction(Instruction.REMOVETEMPORARYPROXIES, toplevelmemid));
		}
		copyRules(rs.rightMem);
		loadRulesets(rs.rightMem);		
		buildRHSTypedProcesses();
		buildRHSAtoms(rs.rightMem);
		// ここでvarcountの最終値が確定することになっている。変更時は適切に下に移動すること。


		//右辺の明示的なリンクを貼る
		//getLHSLinks();
		updateLinks();
		deleteconnectors();
		
		//右辺のアトムを実行アトムスタックに積む
		enqueueRHSAtoms();

		//次の2つは右辺の構造の生成以降ならいつでもよい
		addInline();
		addRegAndLoadModules();

		// 左辺の残ったプロセスを解放する
		freeLHSNonlinearProcessContexts();
		freeLHSMem(rs.leftMem);
		freeLHSAtoms();
		freeLHSTypedProcesses();
//		freeLHSSingletonProcessContexts(); // freememに先行させるため3行上に移動した n-kato 2005.1.13

		// 膜をunlockする
		
		recursiveUnlockLHSNonlinearProcessContextMems();
		unlockReusedOrNewRootMem(rs.rightMem);
		//
		body.add(0, Instruction.spec(formals, varcount));
		
//		if (rs.rightMem.mems.isEmpty() && rs.rightMem.ruleContexts.isEmpty()
//		 && rs.rightMem.processContexts.isEmpty() && rs.rightMem.rulesets.isEmpty()) {
//			body.add(new Instruction(Instruction.CONTINUE));
//		} else 
		body.add(new Instruction(Instruction.PROCEED));
	}
		
	////////////////////////////////////////////////////////////////
	//
	// ガード関係
	//

	/** ヘッドの膜とアトムに対して、仮引数番号を登録する */
	private void genLHSPaths() {
		lhsatompath = new HashMap();
		lhsmempath  = new HashMap();
		varcount = 0;
		for (int i = 0; i < lhsmems.size(); i++) {
			lhsmempath.put(lhsmems.get(i), new Integer(varcount++));
		}
		for (int i = 0; i < lhsatoms.size(); i++) {
			lhsatompath.put(lhsatoms.get(i), new Integer(varcount++));
		}
	}
	
	/** ガードの取り込み */
	private void inc_guard() {
		varcount = lhsatoms.size() + lhsmems.size();
		genTypedProcessContextPaths();
		// typedcxtdefs = gc.typedcxtdefs;
		// varcount = lhsatoms.size() + lhsmems.size() + rs.typedProcessContexts.size();
		//getLHSLinks();
		getGroundLinkPaths();
	}

//	private void inc_head(HeadCompiler hc) {
//		// ヘッドの取り込み
//		lhsatoms = hc.atoms;
//		lhsmems  = hc.mems;
//		genLHSPaths();
//		varcount = lhsatoms.size() + lhsmems.size();
//	}

	/** ガードをコンパイルする */
	private void compile_g() throws CompileException {
		lhsmems  = hc.mems;
		lhsatoms = hc.atoms;
		genLHSPaths();
		gc = new GuardCompiler(this, hc);		/* 変数番号の正規化 */
		if (guard == null) return;
		int formals = gc.varcount;
		gc.getLHSLinks();								/* 左辺の全てのアトムのリンクについてgetlink命令を発行する */
		gc.fixTypedProcesses();						/* 型付きプロセス文脈を一意に決定する */
		gc.checkMembraneStatus();					/* プロセス文脈のない膜やstableな膜の検査をする */
		varcount = gc.varcount;
		compileNegatives();							/* 否定条件のコンパイル */
		fixUniqOrder();									/* uniq命令を最後に移動 */
		guard.add( 0, Instruction.spec(formals,varcount) );
		guard.add( Instruction.jump(theRule.bodyLabel, gc.getMemActuals(),
			gc.getAtomActuals(), gc.getVarActuals()) );
		//RISC化で、暫定処置としてガードでgetlinkした物をボディに渡さない事にしたので、
		//ガード命令列の局所変数の数とボディ命令列の引数の数が一致しなくなった。by mizuno
		varcount = gc.getMemActuals().size() + gc.getAtomActuals().size() 
					+ gc.getVarActuals().size();
	}
	/**
	 * uniq 命令を一つにまとめてガード命令列の最後に移動する。
	 * uniq 命令は、全ての失敗しうるガード命令のうち最後尾にないといけない。
	 * hara
	 */
	void fixUniqOrder() {
		boolean found = false;
		List vars = new ArrayList();
		Iterator it = guard.iterator();
		while(it.hasNext()) {
			Instruction inst = (Instruction)it.next();
			if(inst.getKind() == Instruction.UNIQ) {
				found = true;
				vars.addAll((ArrayList)inst.getArg(0));
				it.remove();
			}
		}
		if(found) guard.add(new Instruction(Instruction.UNIQ, vars));
	}

	/** 否定条件をコンパイルする */
	void compileNegatives() throws CompileException{
		Iterator it = rs.guardNegatives.iterator();
		while (it.hasNext()) {
			LinkedList eqs = (LinkedList)it.next();
			HeadCompiler negcmp = hc.getNormalizedHeadCompiler();
			negcmp.varcount = varcount;
			negcmp.compileNegativeCondition(eqs);
			guard.add(new Instruction(Instruction.NOT, negcmp.matchLabel));
			if (varcount < negcmp.varcount)  varcount = negcmp.varcount;
		}
	}
	
	// 型付きプロセス文脈関係
	
	GuardCompiler gc;
	/** 型付きプロセス文脈の右辺での出現 (Context) -> 変数番号 */
	HashMap rhstypedcxtpaths = new HashMap();
	/** ground型付きプロセス文脈の右辺での出現(Context) -> (Linkのリストを指す)変数番号 */
	HashMap rhsgroundpaths = new HashMap();
	/** ground型付きプロセス文脈の右辺での出現(Context) -> (Linkを指す)変数番号のリスト */
	HashMap rhsgroundlinkpaths = new HashMap();
	/** 型付きプロセス文脈定義 (ContextDef) -> ソース出現（コピー元とする出現）の変数番号（Body実行時） */	
	HashMap typedcxtsrcs  = new HashMap();
	/** ground型付きプロセス文脈定義(ContextDef) -> ソース出現（コピー元とする出現）の変数番号（Body実行時）のリストの変数番号 */
	HashMap groundsrcs = new HashMap();
	/** Body実行時なので、UNBOUNDにはならない */
	int typedcxtToSrcPath(ContextDef def) {
		return ((Integer)typedcxtsrcs.get(def)).intValue();
	}
	/** Body実行時なので、UNBOUNDにはならない */
	int groundToSrcPath(ContextDef def) {
		return ((Integer)groundsrcs.get(def)).intValue();
	}
	/**　*/
	int rhstypedcxtToPath(Context cxt) {
		return ((Integer)rhstypedcxtpaths.get(cxt)).intValue();
	}
	/**　*/
	int rhsgroundToPath(Context cxt) {
		return ((Integer)rhsgroundpaths.get(cxt)).intValue();
	}

	/** unary型プロセス文脈について、変数番号をガードコンパイラから取得する */
	private void genTypedProcessContextPaths() {
		Iterator it = gc.typedcxtdefs.iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			if (gc.typedcxttypes.get(def) == GuardCompiler.UNARY_ATOM_TYPE) {
				typedcxtsrcs.put( def, new Integer(varcount++) );
			}
		}
	}
	
	/** ground型付きプロセス文脈定義について、根となるリンクのリストを取得する */
	private void getGroundLinkPaths() {
		groundsrcs = new HashMap();
		Iterator it = gc.groundsrcs.keySet().iterator();
		while(it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			if(gc.typedcxttypes.get(def) == GuardCompiler.GROUND_LINK_TYPE) {
//				ProcessContext lhsOcc = def.lhsOcc
				int linklistpath = varcount++;
				body.add(new Instruction(Instruction.NEWLIST,linklistpath));
				// 全ての引数に対して発行する
				for(int i=0;i<def.lhsOcc.args.length;i++){
					int linkpath = varcount++;
					body.add(new Instruction(Instruction.GETLINK,linkpath,lhsatomToPath(def.lhsOcc.args[i].buddy.atom),def.lhsOcc.args[i].buddy.pos));
					body.add(new Instruction(Instruction.ADDTOLIST,linklistpath,linkpath));
					groundsrcs.put(def,new Integer(linklistpath));
				}
			}
		}
	}
//	public void enumTypedContextDefs() {
//		Iterator it = rs.typedProcessContexts.values().iterator();
//		while (it.hasNext()) {
//			ContextDef def = (ContextDef)it.next();
//			typedcxtdefs.add(def);
//		}
//	}
	/** 左辺の型付きプロセス文脈を除去する */
	private void removeLHSTypedProcesses() {
		Iterator it = rs.typedProcessContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			Context pc = def.lhsOcc;
			if (pc != null) { // ヘッドのときのみ除去する
				if (gc.typedcxttypes.get(def) == GuardCompiler.UNARY_ATOM_TYPE) {
					//dequeueされていなかったので追加(2005/08/27) by mizuno
					body.add(new Instruction( Instruction.DEQUEUEATOM, typedcxtToSrcPath(def) ));
					body.add(new Instruction( Instruction.REMOVEATOM,
						typedcxtToSrcPath(def), lhsmemToPath(pc.mem) ));
				}
				else if (gc.typedcxttypes.get(def) == GuardCompiler.GROUND_LINK_TYPE) {
					body.add(new Instruction( Instruction.REMOVEGROUND,
						groundToSrcPath(def), lhsmemToPath(pc.mem) ));
				}
			}
		}
	}
	/** 左辺の型付きプロセス文脈を解放する */
	private void freeLHSTypedProcesses() {
		Iterator it = rs.typedProcessContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			if (gc.typedcxttypes.get(def) == GuardCompiler.UNARY_ATOM_TYPE) {
				body.add(new Instruction( Instruction.FREEATOM,
					typedcxtToSrcPath(def) ));
			}
			else if (gc.typedcxttypes.get(def) == GuardCompiler.GROUND_LINK_TYPE) {
				body.add(new Instruction( Instruction.FREEGROUND,groundToSrcPath(def)));
			}
		}
	}	

	/** 非線形プロセス文脈の左辺出現を解放する */
	private void freeLHSNonlinearProcessContexts(){
		Iterator it = rs.processContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			if (def.rhsOccs.size() != 1) { // 非線型のとき1つだけ再利用するようにしたら size == 0 に直せる -> 再利用は最適化に任せることにしたので不要
				body.add(new Instruction( Instruction.DROPMEM,
					lhsmemToPath(def.lhsOcc.mem) ));
			}
		}
	}

	/** 非線形プロセス文脈の左辺出現膜を再帰的にロックする */
	private void recursiveLockLHSNonlinearProcessContextMems(){
		Iterator it = rs.processContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			if (def.rhsOccs.size() != 1) {
				body.add(new Instruction( Instruction.RECURSIVELOCK,
					lhsmemToPath(def.lhsOcc.mem) ));
			}
		}
	}

	/** 非線形プロセス文脈の左辺出現膜を再帰的にロック解放する */
	private void recursiveUnlockLHSNonlinearProcessContextMems(){
		Iterator it = rs.processContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			if (def.rhsOccs.size() != 1) {
				if (false) { // 再利用したときのみ recursiveunlock する
					body.add(new Instruction( Instruction.RECURSIVEUNLOCK,
						lhsmemToPath(def.lhsOcc.mem) ));
				}
			}
		}
	}

	/** 右辺の型付きプロセス文脈を構築する */
	private void buildRHSTypedProcesses() {
		Iterator it = rs.typedProcessContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			Iterator it2 = def.rhsOccs.iterator();
			while (it2.hasNext()) {
				ProcessContext pc = (ProcessContext)it2.next();
				if (gc.typedcxttypes.get(def) == GuardCompiler.UNARY_ATOM_TYPE) {
					int atompath = varcount++;
					body.add(new Instruction( Instruction.COPYATOM, atompath,
						rhsmemToPath(pc.mem),
						typedcxtToSrcPath(pc.def) ));
					rhstypedcxtpaths.put(pc, new Integer(atompath));
				}
				else if(gc.typedcxttypes.get(def) == GuardCompiler.GROUND_LINK_TYPE) {
					int retlistpath = varcount++;
//					System.out.println("cp");
//					int mappath = varcount++;
					body.add(new Instruction( Instruction.COPYGROUND, retlistpath,
						groundToSrcPath(pc.def), // groundの場合はリンクの変数番号のリストを指す変数番号
						rhsmemToPath(pc.mem) ));
					int groundpath = varcount++;
					body.add(new Instruction( Instruction.GETFROMLIST,groundpath,retlistpath,0));
					int mappath = varcount++;
					body.add(new Instruction(Instruction.GETFROMLIST,mappath,retlistpath,1));
					rhsgroundpaths.put(pc, new Integer(groundpath));
					rhsmappaths.put(pc,new Integer(mappath));
				}
			}
		}
	}	

	////////////////////////////////////////////////////////////////

	/** 膜階層下にあるアクティブアトムを各膜内で先頭方向にスライド移動する。*/
	private static void liftupActiveAtoms(Membrane mem) {
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			liftupActiveAtoms((Membrane)it.next());
		}
		LinkedList atomlist = new LinkedList();
		it = mem.atoms.iterator();
		while (it.hasNext()) {
			atomlist.add(it.next());
		}
		mem.atoms.clear();
		it = atomlist.iterator();
		while (it.hasNext()) {
			Atom a = (Atom)it.next();
			if (a.functor.isActive()) {
				mem.atoms.add(a);
				it.remove();
			}
		}
		mem.atoms.addAll(atomlist);	
	}
	/** ルールの左辺と右辺に対してstaticUnifyを呼ぶ */
	public void simplify() throws CompileException {
		staticUnify(rs.leftMem);
		checkExplicitFreeLinks(rs.leftMem);
		staticUnify(rs.rightMem);
		if (rs.leftMem.atoms.isEmpty() && rs.leftMem.mems.isEmpty() && !rs.fSuppressEmptyHeadWarning) {
			Env.warning("WARNING: rule with empty head: " + rs);
		}
		// その他 unary =/== ground の順番に並べ替える
		List typeConstraints = rs.guardMem.atoms;
		LinkedList lists[] = {new LinkedList(),new LinkedList(),new LinkedList()};
		Iterator it = typeConstraints.iterator();
		while (it.hasNext()) {
			Atom cstr = (Atom)it.next();
			Functor func = cstr.functor;
			if (func.equals(new SymbolFunctor("unary",1)))  { lists[0].add(cstr); it.remove(); }
			if (func.equals(Functor.UNIFY))                 { lists[1].add(cstr); it.remove(); }
			if (func.equals(new SymbolFunctor("==",2)))     { lists[1].add(cstr); it.remove(); }
			if (func.equals(new SymbolFunctor("ground",1))) { lists[2].add(cstr); it.remove(); }
		}
		typeConstraints.addAll(lists[0]);
		typeConstraints.addAll(lists[1]);
		typeConstraints.addAll(lists[2]);

	}
	
	/** 指定された膜とその子孫に存在する冗長な =（todo および自由リンク管理アトム）を除去する */
	public void staticUnify(Membrane mem) throws CompileException {
		Env.c("RuleCompiler::staticUnify");
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			staticUnify((Membrane)it.next());
		}
		ArrayList removedAtoms = new ArrayList();
		it = mem.atoms.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();
			if (atom.functor.equals(Functor.UNIFY)) {
				LinkOccurrence link1 = atom.args[0].buddy;
				LinkOccurrence link2 = atom.args[1].buddy;
				if (link1.atom.mem != mem && link2.atom.mem != mem) {
					// 単一化アトムのリンク先が両方とも他の膜につながっている場合
					if (mem == rs.leftMem) {
							// // <strike> ( X=Y :- p(X,Y) ) は意味解析エラー
							// //（=は通常のヘッドアトムと見なして放置される）</strike>
							// error("COMPILE ERROR: head contains body unification");
						// ( X=Y :- p(X,Y) ) は ( :- p(X,X) ) になる
					}
					else {
						// ( p(X,Y) :- X=Y ) はUNIFYボディ命令を出力するのでここでは何もしない
						continue;
					}
				}
				link1.buddy = link2;
				link2.buddy = link1;
				link2.name = link1.name;
				removedAtoms.add(atom);
			}
		}
		it = removedAtoms.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();
			atom.mem.atoms.remove(atom);
		}
	}
	
	/**
	 * 型なしプロセス文脈の明示的な引数を再帰的に検査する．
	 * @param mem
	 * @throws CompileException
	 */
	private void checkExplicitFreeLinks(Membrane mem)throws CompileException {
		Env.c("RuleCompiler::checkExplicitFreeLinks");
		Iterator it = mem.mems.iterator();
		while(it.hasNext()) {
			checkExplicitFreeLinks((Membrane)it.next());
		}
		it = mem.processContexts.iterator();
		while(it.hasNext()){
			ProcessContext pc = (ProcessContext)it.next();
			if(pc.def.isTyped())continue;
			HashSet explicitfreelinks = new HashSet();
			for (int i = 0; i < pc.args.length; i++) {
				LinkOccurrence lnk = pc.args[i];
				if (explicitfreelinks.contains(lnk.name)) {
					systemError("SYNTAX ERROR: explicit arguments of a process context in head must be pairwise disjoint: " + pc.def);
				}
				else {
					explicitfreelinks.add(lnk.name);
				}
			}
		}
	}
	
	/** 命令列を最適化する */
	private void optimize() {
		Env.c("optimize");
//		Optimizer.optimize(memMatch, body);
		if (!rs.fSuppressEmptyHeadWarning) {
			//このフラグがtrue <=> theRuleは初期データ生成用ルール
			Optimizer.optimizeRule(theRule);
		}
	}

	////////////////////////////////////////////////////////////////
	//
	// ボディ実行
	//
	
	/** 左辺のアトムを所属膜から除去する。*/
	private void removeLHSAtoms() {
		//Env.c("RuleCompiler::removeLHSAtoms");
		for (int i = 0; i < lhsatoms.size(); i++) {
			Atom atom = (Atom)lhsatoms.get(i);
			body.add( Instruction.removeatom(
				lhsatomToPath(atom), // ← lhsmems.size() + i に一致する
				lhsmemToPath(atom.mem), atom.functor ));
		}
	}
	/** 左辺のアトムを実行アトムスタックから除去する。*/
	private void dequeueLHSAtoms() {
		for (int i = 0; i < lhsatoms.size(); i++) {
			Atom atom = (Atom)lhsatoms.get(i);
			if (atom.functor.isSymbol() ) {
				body.add( Instruction.dequeueatom(
					lhsatomToPath(atom) // ← lhsmems.size() + i に一致する
					));
			}
		}
	}
	/** 左辺の膜を子膜側から再帰的に除去する。
	 * @return 膜memの内部に出現したプロセス文脈の個数 */
	private int removeLHSMem(Membrane mem) {
		//Env.c("RuleCompiler::removeLHSMem");
		int procvarcount = mem.processContexts.size();
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			int subcount = removeLHSMem(submem);
			body.add(new Instruction(Instruction.REMOVEMEM, lhsmemToPath(submem), lhsmemToPath(mem))); //第2引数追加 by mizuno
			if (subcount > 0) {
				body.add(new Instruction(Instruction.REMOVEPROXIES, lhsmemToPath(submem)));
			}
			procvarcount += subcount;
		}
		return procvarcount;
	}	

	//
	
	HashMap rhsmappaths = new HashMap();	// 右辺の非線型$p出現(ProcessContext) -> mapの変数番号(Integer)
	static final int NOTCOPIED = -1;		// rhsmappaths未登録時の値
	private int rhspcToMapPath(ProcessContext pc) {
		if (!rhsmappaths.containsKey(pc)) return NOTCOPIED;
		return ((Integer)rhsmappaths.get(pc)).intValue();
	}
	
	//
	
	private boolean fUseMoveCells = true;	// 線型$pに対してMOVECELLSを使い最適化するか（開発時向け変数）

	/** 膜の階層構造およびプロセス文脈の内容を親膜側から再帰的に生成する。
	 * @return 膜memの内部に出現したプロセス文脈の個数 */
	private int buildRHSMem(Membrane mem) throws CompileException {
		Env.c("RuleCompiler::buildRHSMem" + mem);
		int procvarcount = mem.processContexts.size();
		Iterator it = mem.processContexts.iterator();
		while (it.hasNext()) {
			ProcessContext pc = (ProcessContext)it.next();
			if (pc.def.lhsOcc.mem == null) {
				systemError("SYSTEM ERROR: ProcessContext.def.lhsOcc.mem is not set");
			}
			if (rhsmemToPath(mem) != lhsmemToPath(pc.def.lhsOcc.mem)) {
				if (fUseMoveCells && /*pc.def.rhsOccs.get(0) == pc*/ pc.def.rhsOccs.size() == 1) {
					body.add(new Instruction(Instruction.MOVECELLS,
						rhsmemToPath(mem), lhsmemToPath(pc.def.lhsOcc.mem) ));
				} 
				else {
					int rethashmap = varcount++;
					body.add(new Instruction(Instruction.COPYCELLS,
						rethashmap, rhsmemToPath(mem), lhsmemToPath(pc.def.lhsOcc.mem) ));
					rhsmappaths.put(pc,new Integer(rethashmap));
					//else {
					//	systemError("FEATURE NOT IMPLEMENTED: untyped process context must be linear: " + pc);
					//}
				}
			}
		}
		it = mem.mems.iterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			int submempath = varcount++;
			rhsmempath.put(submem, new Integer(submempath));
			if (submem.pragmaAtHost != null) { // 右辺で＠指定されている場合
				if (submem.pragmaAtHost.def == null) {
					systemError("SYSTEM ERROR: pragmaAtHost.def is not set: " + submem.pragmaAtHost.getQualifiedName());
				}
				int nodedescatomid = typedcxtToSrcPath(submem.pragmaAtHost.def);
				body.add( new Instruction(Instruction.NEWROOT, submempath, rhsmemToPath(mem),
					nodedescatomid, submem.kind) );
			}
			else { // 通常の右辺膜の場合
				body.add( Instruction.newmem(submempath, rhsmemToPath(mem), submem.kind ) );
			}
			if (submem.name != null)
				body.add(new Instruction( Instruction.SETMEMNAME, submempath, submem.name.intern() ));
			int subcount = buildRHSMem(submem);
			if (subcount > 0) {
				body.add(new Instruction(Instruction.INSERTPROXIES,
					rhsmemToPath(mem), rhsmemToPath(submem)));
			}
			procvarcount += subcount;
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
			if (rhsmemToPath(mem) == lhsmemToPath(rc.def.lhsOcc.mem)) continue;
			body.add(new Instruction( Instruction.COPYRULES, rhsmemToPath(mem), lhsmemToPath(rc.def.lhsOcc.mem) ));
		}
	}
	/** 右辺の膜内のルールの内容を生成する */	
	private void loadRulesets(Membrane mem) {
		Env.c("RuleCompiler::loadRulesets");
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			loadRulesets((Membrane)it.next());
		}
		it = mem.rulesets.iterator();
		while (it.hasNext()) {
//			if (!mem.rules.isEmpty()) {
				body.add(Instruction.loadruleset(rhsmemToPath(mem), (runtime.Ruleset)it.next()));
//			} 
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
			if (atom.functor.equals(Functor.UNIFY)) {
				LinkOccurrence link1 = atom.args[0].buddy;
				LinkOccurrence link2 = atom.args[1].buddy;
				body.add(new Instruction( Instruction.UNIFY,
					lhsatomToPath(link1.atom), link1.pos,
					lhsatomToPath(link2.atom), link2.pos, rhsmemToPath(mem) ));
			} else {
				int atomid = varcount++;
				rhsatompath.put(atom, new Integer(atomid));
				rhsatoms.add(atom);
				body.add( Instruction.newatom(atomid, rhsmemToPath(mem), atom.functor));
			}
		}
	}
	
	/** プロセス文脈定義->setの変数番号 */
	HashMap cxtlinksetpaths = new HashMap();
	
	/** コピーする$pについて、そのリンクオブジェクトへの参照を取得し、
	 * そのリストを引数にinsertconnectors命令を発行する。
	 * 得たsetオブジェクトへの参照が代入された変数を覚えておき、
	 * プロセス文脈定義->setの変数番号
	 * というマップに登録する。
	 * 
	 * プロセス文脈の自由リンクが実は局所リンクである場合に必要であるらしい
	 */
	private void insertconnectors(){
		Iterator it = rs.processContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			if (def.rhsOccs.size() > 1) {
				List linklist = new ArrayList();
				int setpath=varcount++;
				for(int i=0;i<def.lhsOcc.args.length;i++){
					if(!lhslinkpath.containsKey(def.lhsOcc.args[i])){
						int linkpath = varcount++;
						body.add(new Instruction(Instruction.GETLINK,linkpath,
							lhsatomToPath(def.lhsOcc.args[i].buddy.atom),def.lhsOcc.args[i].buddy.pos));
						lhslinkpath.put(def.lhsOcc.args[i],new Integer(linkpath));
					}
					int srclink = lhslinkToPath(def.lhsOcc.args[i]);
					linklist.add(new Integer(srclink));
				}
				body.add(new Instruction( Instruction.INSERTCONNECTORS,
					setpath,linklist,lhsmemToPath(def.lhsOcc.mem) ));
				cxtlinksetpaths.put(def,new Integer(setpath));
			}
		}
		it = rs.typedProcessContexts.values().iterator();
		while(it.hasNext()){
			ContextDef def = (ContextDef)it.next();
			if(gc.typedcxttypes.get(def) == GuardCompiler.GROUND_LINK_TYPE) {
				List linklist = new ArrayList();
				int setpath = varcount++;
				for(int i=0;i<def.lhsOcc.args.length;i++){
					if(!lhslinkpath.containsKey(def.lhsOcc.args[i])){
						int linkpath = varcount++;
						body.add(new Instruction(Instruction.GETLINK,linkpath,
								lhsatomToPath(def.lhsOcc.args[i].buddy.atom),def.lhsOcc.args[i].buddy.pos));	
						lhslinkpath.put(def.lhsOcc.args[i],new Integer(linkpath));
					}
					int srclink = lhslinkToPath(def.lhsOcc.args[i]);
					linklist.add(new Integer(srclink));
				}
				body.add(new Instruction(Instruction.INSERTCONNECTORSINNULL,
						setpath,linklist));//,lhsmemToPath(def.lhsOcc.mem)));
				cxtlinksetpaths.put(def,new Integer(setpath));
			}
		}
	}
	
	/** 上で作られたマップから引いてきたsetと、あとコピー時に作ったマップを
	 * 引数にして、deleteconnectors命令を発行する。
	 *
	 */
	private void deleteconnectors(){
		Iterator it = rs.processContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			Iterator it2 = def.rhsOccs.iterator();
			if(def.rhsOccs.size() <2)continue;
			while (it2.hasNext()) {
				ProcessContext pc = (ProcessContext)it2.next();
				body.add(new Instruction(Instruction.DELETECONNECTORS,
				((Integer)cxtlinksetpaths.get(def)).intValue(),
				rhspcToMapPath(pc)));
//				rhsmemToPath(pc.mem)));
			}
		}
		it = rs.typedProcessContexts.values().iterator();
		while(it.hasNext()){
			ContextDef def = (ContextDef)it.next();
			if(gc.typedcxttypes.get(def) == GuardCompiler.GROUND_LINK_TYPE){
				Iterator it2 = def.rhsOccs.iterator();
				while(it2.hasNext()){
					ProcessContext pc = (ProcessContext)it2.next();
					body.add(new Instruction(Instruction.DELETECONNECTORS,
						((Integer)cxtlinksetpaths.get(def)).intValue(),
						rhspcToMapPath(pc)));
//						rhsmemToPath(pc.mem)));
				}
			}
		}
	}
	
	/** リンクの張り替えと生成を行う */
	private void updateLinks() throws CompileException {
		Env.c("RuleCompiler::updateLinks");
		if(true) {
			Iterator it = computeRHSLinks().iterator();
			while(it.hasNext()){
				LinkOccurrence link = (LinkOccurrence)it.next();
				int linkpath = getLinkPath(link);
				int buddypath = getLinkPath(link.buddy);
				Membrane mem = link.atom.mem;
				int mempath = rhsmemToPath(mem);
				body.add(new Instruction(Instruction.UNIFYLINKS,linkpath,buddypath,mempath));
			}
		}
	}
	/** 右辺のアトムを実行アトムスタックに積む */
	private void enqueueRHSAtoms() {
		int index = body.size(); // 末尾再帰最適化の効果を最大化するため、逆順に積む（コードがセコい）
		Iterator it = rhsatoms.iterator();
		while(it.hasNext()) {
			Atom atom = (Atom)it.next();
			if (atom.functor.isSymbol() && atom.functor.isActive() ) {
				body.add(index, new Instruction(Instruction.ENQUEUEATOM, rhsatomToPath(atom)));
			}
		}
	}
	/** インラインコードを実行する命令を生成する */
	private void addInline() {
		Iterator it = rhsatoms.iterator();
		while(it.hasNext()) {
			Atom atom = (Atom)it.next();
			int atomID = rhsatomToPath(atom);
			Inline.register(unitName, atom.functor.getName());
			int codeID = Inline.getCodeID(unitName, atom.functor.getName());
			if(codeID == -1) continue;
			body.add( new Instruction(Instruction.INLINE, atomID, unitName, codeID));
		}
	}
	static final Functor FUNC_USE = new SymbolFunctor("use",1);
	/** モジュールを読み込む */
	private void addRegAndLoadModules() {
		Iterator it = rhsatoms.iterator();
		while(it.hasNext()) {
			Atom atom = (Atom)it.next();
			//REG
			if(atom.functor.getArity()==1 && atom.functor.getName().equals("module")) {
				Module.regMemName(atom.args[0].buddy.atom.getName(), atom.mem);
			}
			
			//LOAD
			if (atom.functor.equals(FUNC_USE)) {
				body.add( new Instruction(Instruction.LOADMODULE, rhsmemToPath(atom.mem),
					atom.args[0].buddy.atom.getName()) );
			}
			String path = atom.getPath(); // .functor.path;
			if(path!=null && !path.equals(atom.mem.name)) {
				// この時点では解決できないモジュールがあるので名前にしておく
				body.add( new Instruction(Instruction.LOADMODULE, rhsmemToPath(atom.mem), path));
			}
		}
	}
	/**（再利用された膜または）新しいルート膜に対して、子孫膜から順番にUNLOCKMEMを発行する。
	 * ただし現在の実装では、この時点ではまだ膜は再利用されていない。*/
	private void unlockReusedOrNewRootMem(Membrane mem) {
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) unlockReusedOrNewRootMem((Membrane)it.next());
		if (mem.pragmaAtHost != null) { // 右辺で＠指定されている場合
			body.add(new Instruction(Instruction.UNLOCKMEM, rhsmemToPath(mem)));
		}
	}
	/** 左辺の膜を廃棄する */
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
	/** 左辺のアトムを廃棄する */
	private void freeLHSAtoms() {
		for (int i = 0; i < lhsatoms.size(); i++) {
			body.add( new Instruction(Instruction.FREEATOM, lhsmems.size() + i ));
		}
	}
	
	/** デバッグ用表示 */
	private void showInstructions() {
		Iterator it;
		it = atomMatch.listIterator();
		Env.d("--atomMatches:");
		while(it.hasNext()) Env.d((Instruction)it.next());
		
		it = memMatch.listIterator();
		Env.d("--memMatch:");
		while(it.hasNext()) Env.d((Instruction)it.next());
		
		it = body.listIterator();
		Env.d("--body:");
		while(it.hasNext()) Env.d((Instruction)it.next());
	}

	////////////////////////////////////////////////////////////////

	public void systemError(String text) throws CompileException {
		Env.error(text);
		throw new CompileException("SYSTEM ERROR");
	}
}

