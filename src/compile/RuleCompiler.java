package compile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import runtime.Env;
import runtime.Inline;
import runtime.InlineUnit;
import runtime.Instruction;
import runtime.InstructionList;
import runtime.Rule;
import runtime.Ruleset;
import runtime.functor.Functor;
import runtime.functor.SymbolFunctor;

import compile.structure.Atom;
import compile.structure.Atomic;
import compile.structure.Context;
import compile.structure.ContextDef;
import compile.structure.LinkOccurrence;
import compile.structure.Membrane;
import compile.structure.ProcessContext;
import compile.structure.ProcessContextEquation;
import compile.structure.RuleContext;
import compile.structure.RuleStructure;
import compile.util.Collector;

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
public class RuleCompiler
{
	/** コンパイルされるルール構造 */
	RuleStructure rs;

	/**
	 * コンパイルされるルールに対応するルールオブジェクト
	 * 中間命令列
	 */
	Rule theRule;

	private List<Instruction> atomMatch;
	private List<Instruction> memMatch;
	private List<Instruction> tempMatch;// Slimcode時にのみ使用される(中身はmemMatch)

	List<Instruction> guard;

	private List<Instruction> body;

	private int varcount;			// 次の変数番号

	boolean hasISGROUND = true;

	private List<Atom> rhsatoms;
	//private List<Atomic> rhsAtomics;				// プロセス文脈拡張用
	private Map<Atom, Integer>  rhsatompath;		// 右辺のアトム (Atomic) -> 変数番号 (Integer)
	//private Map<Atomic, Integer> rhsAtomicPath;		// プロセス文脈拡張用
	private Map<Membrane, Integer>  rhsmempath;		// 右辺の膜 (Membrane) -> 変数番号 (Integer)
	private Map<LinkOccurrence, Integer>  rhslinkpath;		// 右辺のリンク出現(LinkOccurence) -> 変数番号(Integer)
	//private List rhslinks;		// 右辺のリンク出現(LinkOccurence)のリスト（片方のみ） -> computeRHSLinksの返り血にした
	private List<Atomic> lhsatoms;
	private List<Membrane> lhsmems;
	private Map<Atomic, Integer>  lhsatompath;		// 左辺のアトム (Atomic) -> 変数番号 (Integer)
	private Map<Membrane, Integer>  lhsmempath;		// 左辺の膜 (Membrane) -> 変数番号 (Integer)
	private Map<LinkOccurrence, Integer>  lhslinkpath = new HashMap<LinkOccurrence, Integer>();		// 左辺のアトムのリンク出現 (LinkOccurrence) -> 変数番号(Integer)
	// ＜左辺のアトムの変数番号 (Integer) -> リンクの変数番号の配列 (int[])　＞から変更

	private HeadCompiler hc, hc2;

	private int lhsmemToPath(Membrane mem) { return lhsmempath.get(mem); }
	private int rhsmemToPath(Membrane mem) { return rhsmempath.get(mem); }
	private int lhsatomToPath(Atomic atom) { return lhsatompath.get(atom); }
	private int rhsatomToPath(Atomic atom) { return rhsatompath.get(atom); }
	private int lhslinkToPath(Atomic atom, int pos) { return lhslinkToPath(atom.args[pos]); }
	private int lhslinkToPath(LinkOccurrence link) { return lhslinkpath.get(link); }

	private String unitName;

	/** ヘッドのマッチング終了後の継続命令列のラベル */
	private InstructionList contLabel;

	/**
	 * 指定された RuleStructure 用のルールをつくる
	 */
	public RuleCompiler(RuleStructure rs)
	{
		this(rs, InlineUnit.DEFAULT_UNITNAME);
	}

	public RuleCompiler(RuleStructure rs, String unitName)
	{
		this.unitName = unitName;
		this.rs = rs;
	}

	/**
	 * 初期化時に指定されたルール構造をルールオブジェクトにコンパイルする
	 */
	public Rule compile() throws CompileException
	{
		liftupActiveAtoms(rs.leftMem);
		simplify();
		//theRule = new Rule(rs.toString());
		theRule = new Rule(rs.leftMem.getFirstAtomName(),rs.toString());
		theRule.name = rs.name;

		hc = new HeadCompiler();//rs.leftMem;
		hc.enumFormals(rs.leftMem);	// 左辺に対する仮引数リストを作る
		hc2 = new HeadCompiler();
		hc2.enumFormals(rs.leftMem);
		//とりあえず常にガードコンパイラを呼ぶ事にしてしまう by mizuno
		//if (!rs.typedProcessContexts.isEmpty() || !rs.guardNegatives.isEmpty())
		if (true)
		{
			theRule.guardLabel = new InstructionList();
			guard = theRule.guardLabel.insts;
		}
		else
		{
			guard = null;
		}
		theRule.bodyLabel = new InstructionList();
		body = theRule.bodyLabel.insts;
		contLabel = (guard != null ? theRule.guardLabel : theRule.bodyLabel);

		// 左辺膜のコンパイル
		compile_l();

		// ガードのコンパイル
		compile_g();

		hc = new HeadCompiler();//rs.leftMem;
		hc.enumFormals(rs.leftMem);	// 左辺に対する仮引数リストを作る
		hc.firsttime = false;
		theRule.guardLabel = new InstructionList();
		guard = theRule.guardLabel.insts;
		contLabel = (guard != null ? theRule.guardLabel : theRule.bodyLabel);

		compile_l();

		compile_g();

		// 右辺膜のコンパイル
		boolean compileWithSwaplink = false;
		if (Env.useSwapLink || Env.useCycleLinks)
		{
			if (isSwapLinkUsable())
			{
				compileWithSwaplink = true;
			}
			else
			{
				if (Env.verboseLinkExt)
				{
					System.err.println("WARNING: swaplink/cyclelinks was suppressed. - " + rs);
				}
			}
		}
		if (compileWithSwaplink)
		{
			compile_r_swaplink();
		}
		else
		{
			compile_r();
		}

		theRule.memMatch  = memMatch;
		theRule.tempMatch = tempMatch;
		theRule.atomMatch = atomMatch;
		theRule.guard     = guard;
		theRule.body      = body;

		String ruleName = theRule.name;
		if (theRule.name == null)
		{
			ruleName = makeRuleName(rs.toString(), Env.showlongrulename, 4);
		}
		theRule.body.add(1, Instruction.commit(ruleName, theRule.lineno));

		optimize();
		return theRule;
	}
	
	/**
	 * <p>
	 * swaplink/cyclelinks が使用可能か判定します。
	 * 現状の実装で未対応だと分かっているケースについて、通常のパスでコード生成をするための判断に必要です。
	 * ただし、この判定を通過しても上手くコード生成ができないケースがあるかも知れません。
	 * </p>
	 */
	private boolean isSwapLinkUsable()
	{
		// 1. 型無しプロセス文脈が存在しない
		// 2. ground 型プロセス文脈が存在しない
		return rs.processContexts.isEmpty() && !containsGround();
	}

	private boolean containsGround()
	{
		for (ProcessContext pc : Collector.collectTypedProcessContexts(rs.rightMem))
		{
			if (gc.typedCxtTypes.get(pc.def) == GuardCompiler.GROUND_LINK_TYPE || gc.typedCxtTypes.get(pc.def) == GuardCompiler.HLGROUND_LINK_TYPE)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 左辺膜をコンパイルする
	 */
	private void compile_l()
	{
		theRule.atomMatchLabel = new InstructionList();
		atomMatch = theRule.atomMatchLabel.insts;

		int maxvarcount = 2;	// アトム主導用（仮）
		for (int firstid = 0; firstid <= hc.atoms.size(); firstid++)
		{
			hc.prepare(); // 変数番号を初期化
			hc2.prepare();
			if (firstid < hc.atoms.size())
			{
				if (Env.slimcode || Env.memtestonly) continue;
				// Env.SHUFFLE_DEFAULT ならば、ルールの反応確率を優先するためアトム主導テストは行わない

				Atom atom = (Atom)hc.atoms.get(firstid);
				if (!atom.functor.isActive()) continue;

				// アトム主導
				InstructionList tmplabel = new InstructionList();
				tmplabel.insts = hc.match;
				atomMatch.add(new Instruction(Instruction.BRANCH, tmplabel));

				hc.memPaths.put(rs.leftMem, 0);	// 本膜の変数番号は 0
				hc.atomPaths.put(atom, 1);		// 主導するアトムの変数番号は 1
				hc.varCount = 2;
				hc.match.add(new Instruction(Instruction.FUNC, 1, atom.functor));
				Membrane mem = atom.mem;
				if (mem == rs.leftMem)
				{
					hc.match.add(new Instruction(Instruction.TESTMEM, 0, 1));
				}
				else
				{
					hc.match.add(new Instruction(Instruction.GETMEM, hc.varCount, 1, mem.kind, mem.name));
					hc.match.add(new Instruction(Instruction.LOCK,   hc.varCount));
					hc.memPaths.put(mem, hc.varCount++);
					mem = mem.parent;
					while (mem != rs.leftMem)
					{
						hc.match.add(new Instruction(Instruction.GETPARENT,hc.varCount,hc.varCount-1));
						hc.match.add(new Instruction(Instruction.LOCK,     hc.varCount));
						hc.memPaths.put(mem, hc.varCount++);
						mem = mem.parent;
					}
					hc.match.add(new Instruction(Instruction.GETPARENT,hc.varCount,hc.varCount-1));
					hc.match.add(new Instruction(Instruction.EQMEM, 0, hc.varCount++));
				}
				hc.getLinks(1, atom.functor.getArity(), hc.match); //リンクの一括取得(RISC化) by mizuno
				Atom firstatom = (Atom)hc.atoms.get(firstid);
				hc.compileLinkedGroup(firstatom, hc.matchLabel);
				hc.compileMembrane(firstatom.mem, hc.matchLabel);
			}
			else
			{
				// 膜主導
				theRule.memMatchLabel = hc.matchLabel;
				if (Env.findatom2)
				{
					tempMatch = hc.tempMatch;
					memMatch = hc.match;
				}
				else
				{
					memMatch = hc.match;
				}
				hc.memPaths.put(rs.leftMem, 0);	// 本膜の変数番号は 0
				hc2.memPaths.put(rs.leftMem, 0);	// 本膜の変数番号は 0
			}
			if (Env.findatom2)
			{
				hc.compileMembraneForSlimcode(rs.leftMem, hc.matchLabel, hasISGROUND);
				hc2.compileMembrane(rs.leftMem, hc.tempLabel);
			}
			else
			{
				hc.compileMembrane(rs.leftMem, hc.matchLabel);
			}

			// 自由出現したデータアトムがないか検査する
			if (!hc.fFindDataAtoms)
			{
				if (Env.debug >= 1)
				{
					for (Atomic a : hc.atoms)
					{
						Atom atom = (Atom)a;
						if (!hc.isAtomLoaded(atom))
						{
							Env.warning("TYPE WARNING: Rule head contains free data atom: " + atom);
						}
					}
				}
				hc.switchToUntypedCompilation();
				hc.setContLabel(contLabel);
				if (Env.findatom2)
				{
					hc.compileMembraneForSlimcode(rs.leftMem, hc.matchLabel, hasISGROUND);
					hc2.compileMembrane(rs.leftMem, hc.tempLabel);
				}
				else
				{
					hc.compileMembrane(rs.leftMem, hc.matchLabel);
				}
			}
			hc.checkFreeLinkCount(rs.leftMem, hc.match); // 言語仕様変更により呼ばなくてよくなった→やはり呼ぶ必要あり

			if (Env.hyperLinkOpt)
			{
				hc.compileSameProcessContext(rs.leftMem, hc.matchLabel);//seiji
			}

			if (hc.match == memMatch)
			{
				hc.match.add(0, Instruction.spec(1, hc.maxVarCount));
				hc.tempMatch.add(0, Instruction.spec(1, hc.maxVarCount));
			}
			else
			{
				hc.match.add(0, Instruction.spec(2, hc.maxVarCount));
				hc.tempMatch.add(0, Instruction.spec(1, hc.maxVarCount));
			}
			// jump命令群の生成
			List<Integer> memActuals  = hc.getMemActuals();
			List<Integer> atomActuals = hc.getAtomActuals();
			List varActuals  = hc.getVarActuals();
			// - コード#1
			hc.match.add( Instruction.jump(contLabel, memActuals, atomActuals, varActuals) );
			hc.tempMatch.add( Instruction.jump(contLabel, memActuals, atomActuals, varActuals) );
			// - コード#2
			//hc.match.add( Instruction.inlinereact(theRule, memActuals, atomActuals, varActuals) );
			//int formals = memActuals.size() + atomActuals.size() + varActuals.size();
			//hc.match.add( Instruction.spec(formals, formals) );
			//hc.match.add( hc.getResetVarsInstruction() );
			//List brancharg = new ArrayList();
			//brancharg.add(body);
			//hc.match.add( new Instruction(Instruction.BRANCH, brancharg) );

			// jump命令群の生成終わり
			if (maxvarcount < hc.varCount) maxvarcount = hc.maxVarCount;
		}
		atomMatch.add(0, Instruction.spec(2,maxvarcount));
	}

	/**
	 * 右辺のリンクを取得または生成する
	 */
	private List<LinkOccurrence> computeRHSLinks()
	{
		List<LinkOccurrence> rhslinks = new ArrayList<LinkOccurrence>();
		rhslinkpath = new HashMap<LinkOccurrence, Integer>();
		int rhslinkindex = 0;
		// アトムの引数のリンク出現
		for (Atom atom : rhsatoms)
		{
			for (int pos = 0; pos < atom.functor.getArity(); pos++)
			{
				body.add(new Instruction(Instruction.ALLOCLINK,varcount,rhsatomToPath(atom),pos));
				rhslinkpath.put(atom.args[pos], varcount);
				if (!rhslinks.contains(atom.args[pos].buddy) &&
						!(atom.functor.equals(Functor.INSIDE_PROXY) && pos == 0))
				{
					rhslinks.add(rhslinkindex++,atom.args[pos]);
				}
				varcount++;
			}
		}

		// unary型付プロセス文脈のリンク出現
		for (ProcessContext atom : rhstypedcxtpaths.keySet())
		{
			body.add(new Instruction(Instruction.ALLOCLINK,varcount,rhstypedcxtToPath(atom),0));
			rhslinkpath.put(atom.args[0], varcount);
			if (!rhslinks.contains(atom.args[0].buddy))
			{
				rhslinks.add(rhslinkindex++,atom.args[0]);
			}
			varcount++;
		}

		// ground型付プロセス文脈のリンク出現
		for (ProcessContext ground : rhsgroundpaths.keySet())
		{
			int linklistpath = rhsgroundToPath(ground);
			for (int i = 0; i < ground.def.lhsOcc.args.length; i++)
			{
				int linkpath = varcount++;
				body.add(new Instruction(Instruction.GETFROMLIST,linkpath, linklistpath, i));
//				int linkpath = rhsgroundToPath(atom);
				rhslinkpath.put(ground.args[i], linkpath);
				if (!rhslinks.contains(ground.args[i].buddy))
				{
					rhslinks.add(rhslinkindex++,ground.args[i]);
				}
			}
		}

		// 型なし

		for (ContextDef def : rs.processContexts.values())
		{
			Iterator it2 = def.rhsOccs.iterator();
			while (it2.hasNext())
			{
				ProcessContext atom = (ProcessContext)it2.next();
				for (int pos = 0; pos < atom.getArity(); pos++)
				{
					//LinkOccurrence srclink = atom.def.lhsOcc.args[pos].buddy;
					//int srclinkid;
					//if (!lhslinkpath.containsKey(srclink))
					//{
					//	srclinkid = varcount++;
					//	body.add(new Instruction(Instruction.GETLINK,srclinkid,
					//	lhsatomToPath(srclink.atom), srclink.pos));
					//	lhslinkpath.put(srclink, srclinkid);
					//}
					//srclinkid = lhslinkToPath(srclink);
					//if (!(fUseMoveCells && atom.def.rhsOccs.size() == 1))
					//{
					//	int copiedlink = varcount++;
					//	body.add( new Instruction(Instruction.LOOKUPLINK,
					//	copiedlink, rhspcToMapPath(atom), srclinkid));
					//	srclinkid = copiedlink;
					//}
					//rhslinkpath.put(atom.args[pos], srclinkid);
					if (!rhslinks.contains(atom.args[pos].buddy))
					{
						rhslinks.add(rhslinkindex++,atom.args[pos]);
					}
				}
			}
		}
		return rhslinks;
	}

	private int getLinkPath(LinkOccurrence link)
	{
		if (rhslinkpath.containsKey(link))
		{
			return rhslinkpath.get(link);
		}
		else if (link.atom instanceof ProcessContext && !((ProcessContext)link.atom).def.typed)
		{
			LinkOccurrence srclink = ((ProcessContext)link.atom).def.lhsOcc.args[link.pos].buddy;
			int linkpath = varcount++;
			body.add(new Instruction(Instruction.GETLINK,linkpath,lhsatomToPath(srclink.atom),srclink.pos));
			if (!(fUseMoveCells && ((ProcessContext)link.atom).def.rhsOccs.size() == 1))
			{
				int copiedlink = varcount++;
				body.add( new Instruction(Instruction.LOOKUPLINK,
						copiedlink, rhspcToMapPath(((ProcessContext)link.atom)), linkpath));
				return copiedlink;
			}
			return linkpath;
		}
		else
		{
			if (!lhslinkpath.containsKey(link))
			{
				int linkpath = varcount++;
				body.add(new Instruction(Instruction.GETLINK,linkpath,lhsatomToPath(link.atom),link.pos));
				lhslinkpath.put(link, linkpath);
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
	private void compile_r() throws CompileException
	{
		int formals = varcount;
		//body.add( Instruction.commit(theRule) );
		inc_guard();

		rhsatoms    = new ArrayList<Atom>();
		rhsatompath = new HashMap<Atom, Integer>();
		rhsmempath  = new HashMap<Membrane, Integer>();
		int toplevelmemid = lhsmemToPath(rs.leftMem);
		rhsmempath.put(rs.rightMem, toplevelmemid);

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
		if (removeLHSMem(rs.leftMem) >= 2)
		{
			//2011/01/23 slimでは必要なくなったので挿入しないように修正 by meguro
			if (!Env.slimcode)
			{
				body.add(new Instruction(Instruction.REMOVETOPLEVELPROXIES, toplevelmemid));
			}
		}

		recursiveLockLHSNonlinearProcessContextMems();
		insertconnectors();

		// insertconnectorsの後でなければうまくいかないので再発行 ( 2006/09/15 kudo)
		getGroundLinkPaths();

		// 右辺の構造と$pの内容，を再帰的に生成する
		// $pの明示的でないリンクをはる

		buildRHSMem(rs.rightMem);
		/* 右辺の$pが配置された直後。このタイミングでなければならない筈 */
		if (!rs.rightMem.processContexts.isEmpty())
		{
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
		if (Env.slimcode)
		{
			if (Env.hyperLink)
			{
				addHyperlink();//seiji
			}
			addCallback();
		}
		addRegAndLoadModules();

		// 左辺の残ったプロセスを解放する
		freeLHSNonlinearProcessContexts();
		freeLHSMem(rs.leftMem);
		freeLHSAtoms();
		freeLHSTypedProcesses();
		//freeLHSSingletonProcessContexts(); // freememに先行させるため3行上に移動した n-kato 2005.1.13

		// 膜をunlockする

		recursiveUnlockLHSNonlinearProcessContextMems();
		unlockReusedOrNewRootMem(rs.rightMem);
		//
		body.add(0, Instruction.spec(formals, varcount));

		//if (rs.rightMem.mems.isEmpty() && rs.rightMem.ruleContexts.isEmpty()
		//&& rs.rightMem.processContexts.isEmpty() && rs.rightMem.rulesets.isEmpty()) {
		//body.add(new Instruction(Instruction.CONTINUE));
		//} else
		body.add(new Instruction(Instruction.PROCEED));
	}

	/**
	 * <p>右辺膜コンパイル処理の {@code swaplink/cyclelinks} 拡張版。</p>
	 */
	private void compile_r_swaplink() throws CompileException
	{
		int formals = varcount;
		inc_guard();

		if (rhsatoms == null)
			rhsatoms = new ArrayList<Atom>();
		else
			rhsatoms.clear();

		if (rhsatompath == null)
			rhsatompath = new HashMap<Atom, Integer>();
		else
			rhsatompath.clear();

		/*
		if (rhsAtomics == null)
			rhsAtomics = new ArrayList<Atomic>();
		else
			rhsAtomics.clear();

		if (rhsAtomicPath == null)
			rhsAtomicPath = new HashMap<Atomic, Integer>();
		else
			rhsAtomicPath.clear();
		*/

		if (rhsmempath == null)
			rhsmempath = new HashMap<Membrane, Integer>();
		else
			rhsmempath.clear();

		int toplevelmemid = lhsmemToPath(rs.leftMem);
		rhsmempath.put(rs.rightMem, toplevelmemid);

		Set<Atom> rhsAtomSet = Collector.collectAtomsExceptUnify(rs.rightMem);
		rhsatoms.addAll(rhsAtomSet);
		//rhsAtomics.addAll(rhsatoms);

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
		removeLHSTypedProcesses();
		if (removeLHSMem(rs.leftMem) >= 2)
		{
			//2011/01/23 slimでは必要なくなったので挿入しないように修正 by meguro
			if (!Env.slimcode)
			{
				body.add(new Instruction(Instruction.REMOVETOPLEVELPROXIES, toplevelmemid));
			}
		}

		recursiveLockLHSNonlinearProcessContextMems();
		insertconnectors();

		// insertconnectorsの後でなければうまくいかないので再発行 ( 2006/09/15 kudo)
		getGroundLinkPaths();

		// 右辺の構造と$pの内容，を再帰的に生成する
		// $pの明示的でないリンクをはる

		buildRHSMem(rs.rightMem); // 右辺にある膜の変数番号確定
		/* 右辺の$pが配置された直後。このタイミングでなければならない筈 */
		if (!rs.rightMem.processContexts.isEmpty()) {
			body.add(new Instruction(Instruction.REMOVETEMPORARYPROXIES, toplevelmemid));
		}
		copyRules(rs.rightMem);
		loadRulesets(rs.rightMem);
		buildRHSTypedProcesses();
		
		Set<Atomic> noModified = getInvariantAtomics();
		Map<Atom, Atom> reusable = getReusableAtomics(noModified);
		Set<Atomic> removed = getRemovedAtomics(noModified, reusable);
		Set<Atomic> created = getCreatedAtomics(noModified, reusable);

		removeLHSAtoms_swaplink(removed);
		
		buildRHSAtoms_swaplink(rs.rightMem, created, reusable);
		// ここでvarcountの最終値が確定することになっている。変更時は適切に下に移動すること。

		// 右辺の内部リンクを取得する
		Set<LinkOccurrence> newLinks = getInternalLinks(rs.rightMem);

		if (Env.verboseLinkExt && !rs.isInitialRule())
		{
			System.err.println("===============================");
			String name = rs.name != null ? rs.name + " @@ " : "";
			String kind = Env.useSwapLink ? "swap" : "cycle";
			System.err.println("Compiling[" + kind + "]: " + name + rs.leftMem.toStringWithoutBrace() + " :- " + rs.rightMem.toStringWithoutBrace());
		}

		compileUnify();

		//右辺の明示的なリンクを貼る
		//getLHSLinks();
		if (Env.useCycleLinks)
		{
			compileCycleLinks(removed, created, reusable);
		}
		else
		{
			compileLinkOperations(removed, created, reusable);
		}

		if (Env.verboseLinkExt && !rs.isInitialRule())
		{
			System.err.print("New links: ");
			boolean first = true;
			for (LinkOccurrence l : newLinks)
			{
				if (!first)
				{
					System.err.print(", ");
				}
				first = false;
				System.err.print(l.getInformativeText());
			}
			System.err.println();
		}
		// 右辺で生成されるリンクを処理
		compileNewlinks(newLinks);

		deleteconnectors();

		//右辺のアトムを実行アトムスタックに積む
		enqueueRHSAtoms_swaplink(created, reusable.keySet());

		//次の2つは右辺の構造の生成以降ならいつでもよい
		addInline();
		if (Env.slimcode) {
			if (Env.hyperLink) addHyperlink();//seiji
			addCallback();
		}
		addRegAndLoadModules();

		// 左辺の残ったプロセスを解放する
		freeLHSNonlinearProcessContexts();
		freeLHSMem(rs.leftMem);
		freeLHSAtoms_swaplink(removed);
		freeLHSTypedProcesses();

		// 膜をunlockする

		recursiveUnlockLHSNonlinearProcessContextMems();
		unlockReusedOrNewRootMem(rs.rightMem);
		body.add(0, Instruction.spec(formals, varcount));

		body.add(new Instruction(Instruction.PROCEED));
	}

	/**
	 * <p>膜 {@code mem} 内に含まれる内部リンクの端の集合を取得する。</p>
	 * <p>端とは、1つのリンクを構成する2つのリンク出現のそれぞれを指す。
	 * このメソッドが返す集合には、ある内部リンクをなす端が両方が含まれることはなく、必ずどちらか一方のみが含まれる。</p>
	 * @param mem 対象膜
	 * @return 内部リンクの端の集合
	 */
	private static Set<LinkOccurrence> getInternalLinks(Membrane mem)
	{
		Set<LinkOccurrence> occurredLinks = new HashSet<LinkOccurrence>();
		Set<LinkOccurrence> internalLinks = new HashSet<LinkOccurrence>();
		for (Atomic a : Collector.collectAllAtomsAndTypedPCs(mem))
		{
			for (LinkOccurrence l : a.args)
			{
				if (occurredLinks.contains(l.buddy))
				{
					internalLinks.add(l);
				}
				occurredLinks.add(l);
			}
		}
		return internalLinks;
	}


	/**
	 * <p>2つのアトムが同型アトムであるかを調べます。</p>
	 * <p>2つのアトムが同型であるとは、当該アトム対について以下の条件
	 * <ol>
	 * <li>価数が等しい</li>
	 * <li>名前が等しい</li>
	 * </ol>
	 * が成立することを表します。同型であるアトム対はルール中で再利用されます。</p>
	 * @param a1 アトム1
	 * @param a2 アトム2
	 * @return アトム{@code a1}とアトム{@code a2}が同型である場合に{@code true}、そうでない場合に{@code false}を返します。
	 */
	private boolean isIsomorphic(Atom a1, Atom a2)
	{
		return a1.getArity() == a2.getArity() && a1.getName().equals(a2.getName());
	}

	/**
	 * <p>アトミックの集合{@code atomics}が持つリンクの本数を数えます。</p>
	 * TODO: 無理矢理な実装なので、後でもっとまともな実装を考える。
	 */
	private int countLinkOccurrence(Collection<? extends Atomic> atomics)
	{
		int count = 0;
		for (Atomic a : atomics) count += a.getArity();
		return count;
	}

	////////////////////////////////////////////////////////////////
	//
	// ガード関係
	//

	/** ヘッドの膜とアトムに対して、仮引数番号を登録する */
	private void genLHSPaths()
	{
		lhsatompath = new HashMap<Atomic, Integer>();
		lhsmempath  = new HashMap<Membrane, Integer>();
		varcount = 0;
		for (int i = 0; i < lhsmems.size(); i++)
		{
			lhsmempath.put(lhsmems.get(i), varcount++);
		}
		for (Atomic atomic : lhsatoms)
		{
			lhsatompath.put(atomic, varcount++);
		}
	}

	/**
	 * ガードの取り込み
	 */
	private void inc_guard()
	{
		varcount = lhsatoms.size() + lhsmems.size();
		genTypedProcessContextPaths();
		// typedcxtdefs = gc.typedcxtdefs;
		// varcount = lhsatoms.size() + lhsmems.size() + rs.typedProcessContexts.size();
		//getLHSLinks();
		getGroundLinkPaths();
	}

//	private void inc_head(HeadCompiler hc) {
//	// ヘッドの取り込み
//	lhsatoms = hc.atoms;
//	lhsmems  = hc.mems;
//	genLHSPaths();
//	varcount = lhsatoms.size() + lhsmems.size();
//	}

	/** ガードをコンパイルする */
	private void compile_g() throws CompileException
	{
		lhsmems  = hc.mems;
		lhsatoms = hc.atoms;
		genLHSPaths();
		gc = new GuardCompiler(this, hc);		/* 変数番号の正規化 */
		if (guard == null) return;
		int formals = gc.varCount;
		gc.getLHSLinks();								/* 左辺の全てのアトムのリンクについてgetlink命令を発行する */
		gc.fixTypedProcesses();						/* 型付きプロセス文脈を一意に決定する */
		gc.checkMembraneStatus();					/* プロセス文脈のない膜やstableな膜の検査をする */
		varcount = gc.varCount;
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
	 *   
	 *   newhlinkなど, シンボルアトムを生成するガード命令を追加したため、
	 *   以下のように、"全ての失敗しうるガード命令"の最後尾であり、
	 *   ”シンボルアトム生成命令”よりも前に挿入される 2011/01/10 seiji
	 *        ....
	 *     [全ての失敗しうるガード命令列]
	 *      [uniq] <-- ここに挿入する
	 *     [newhlinkなどのシンボルアトム生成命令列]
	 *        ....
	 */
	private void fixUniqOrder()
	{
		boolean found = guard.contains(Instruction.UNIQ);
		List<Integer> vars = new ArrayList<Integer>();
		for (Iterator<Instruction> it = guard.iterator(); it.hasNext(); )
		{
			Instruction inst = it.next();
			if (inst.getKind() == Instruction.UNIQ)
			{
				found = true;
				vars.addAll((List<Integer>)inst.getArg(0));
				it.remove();
			}
		}

//		if(found) guard.add(new Instruction(Instruction.UNIQ, vars));
		if (found)
		{
			boolean guardallocs = false;
			int i = 0;
			for (Instruction inst : guard)
			{
				// シンボルアトムを生成する命令に出会うまでループ
				if (inst.getKind() == Instruction.NEWHLINK || inst.getKind() == Instruction.MAKEHLINK)
				{
					guardallocs = true;
					break;
				}
				i++;
			}
			if (guardallocs)
			{
				guard.add(i, new Instruction(Instruction.UNIQ, vars));
			}
			else
			{
				guard.add(new Instruction(Instruction.UNIQ, vars));
			}
		}
	}

	/** 否定条件をコンパイルする */
	void compileNegatives() throws CompileException
	{
		Iterator<List<ProcessContextEquation>> it = rs.guardNegatives.iterator();
		while (it.hasNext())
		{
			List<ProcessContextEquation> eqs = it.next();
			HeadCompiler negcmp = hc.getNormalizedHeadCompiler();
			negcmp.varCount = varcount;
			negcmp.compileNegativeCondition(eqs, negcmp.matchLabel);
			guard.add(new Instruction(Instruction.NOT, negcmp.matchLabel));
			if (varcount < negcmp.varCount)  varcount = negcmp.varCount;
		}
	}

	// 型付きプロセス文脈関係

	private GuardCompiler gc;
	/** 型付きプロセス文脈の右辺での出現 (Context) -> 変数番号 */
	private HashMap<ProcessContext, Integer> rhstypedcxtpaths = new HashMap<ProcessContext, Integer>();
	/** ground型付きプロセス文脈の右辺での出現(Context) -> (Linkのリストを指す)変数番号 */
	private HashMap<ProcessContext, Integer> rhsgroundpaths = new HashMap<ProcessContext, Integer>();
	/** ground型付きプロセス文脈の右辺での出現(Context) -> (Linkを指す)変数番号のリスト */
	private HashMap rhsgroundlinkpaths = new HashMap();
	/** 型付きプロセス文脈定義 (ContextDef) -> ソース出現（コピー元とする出現）の変数番号（Body実行時） */
	private HashMap<ContextDef, Integer> typedcxtsrcs  = new HashMap<ContextDef, Integer>();
	/** ground型付きプロセス文脈定義(ContextDef) -> ソース出現（コピー元とする出現）の変数番号（Body実行時）のリストの変数番号 */
	private HashMap<ContextDef, Integer> groundsrcs = new HashMap<ContextDef, Integer>();
	/** Body実行時なので、UNBOUNDにはならない */
	private int typedcxtToSrcPath(ContextDef def) {
		return typedcxtsrcs.get(def);
	}
	/** Body実行時なので、UNBOUNDにはならない */
	private int groundToSrcPath(ContextDef def) {
		return groundsrcs.get(def);
	}
	/**　*/
	private int rhstypedcxtToPath(Context cxt) {
		return rhstypedcxtpaths.get(cxt);
	}
	/**　*/
	private int rhsgroundToPath(Context cxt) {
		return rhsgroundpaths.get(cxt);
	}

	/** unary型プロセス文脈について、変数番号をガードコンパイラから取得する */
	private void genTypedProcessContextPaths()
	{
		for (ContextDef def : gc.typedCxtDefs)
		{
			if (gc.typedCxtTypes.get(def) == GuardCompiler.UNARY_ATOM_TYPE)
			{
				typedcxtsrcs.put( def, varcount++);
			}
		}
	}

	/** ground型付きプロセス文脈定義について、根となるリンクのリストを取得する */
	private void getGroundLinkPaths()
	{
		groundsrcs = new HashMap<ContextDef, Integer>();
		for (ContextDef def : gc.groundSrcs.keySet())
		{
			if (gc.typedCxtTypes.get(def) == GuardCompiler.GROUND_LINK_TYPE || gc.typedCxtTypes.get(def) == GuardCompiler.HLGROUND_LINK_TYPE)
			{
				//ProcessContext lhsOcc = def.lhsOcc
				int linklistpath = varcount++;
				body.add(new Instruction(Instruction.NEWLIST,linklistpath));
				// 全ての引数に対して発行する
				for (int i = 0; i < def.lhsOcc.args.length; i++)
				{
					int linkpath = varcount++;
					if (!def.lhsOcc.args[i].buddy.name.startsWith("!")) {
						body.add(new Instruction(Instruction.GETLINK,linkpath,lhsatomToPath(def.lhsOcc.args[i].buddy.atom),def.lhsOcc.args[i].buddy.pos));						
					} else {
						body.add(new Instruction(Instruction.HYPERGETLINK,linkpath,lhsatomToPath(def.lhsOcc.args[i].buddy.atom),def.lhsOcc.args[i].buddy.pos));
					}
					body.add(new Instruction(Instruction.ADDTOLIST,linklistpath,linkpath));
					groundsrcs.put(def, linklistpath);
				}
			}
		}
	}

	/*
	public void enumTypedContextDefs()
	{
		Iterator it = rs.typedProcessContexts.values().iterator();
		while (it.hasNext())
		{
			ContextDef def = (ContextDef)it.next();
			typedcxtdefs.add(def);
		}
	}
	*/

	/** 左辺の型付きプロセス文脈を除去する */
	private void removeLHSTypedProcesses()
	{
		for (ContextDef def : rs.typedProcessContexts.values())
		{
			Context pc = def.lhsOcc;
			if (pc != null) { // ヘッドのときのみ除去する
				if (gc.typedCxtTypes.get(def) == GuardCompiler.UNARY_ATOM_TYPE)
				{
					//dequeueされていなかったので追加(2005/08/27) by mizuno
					if(!Env.slimcode)body.add(new Instruction( Instruction.DEQUEUEATOM, typedcxtToSrcPath(def) ));
					body.add(new Instruction( Instruction.REMOVEATOM,
							typedcxtToSrcPath(def), lhsmemToPath(pc.mem) ));
				}
				else if (gc.typedCxtTypes.get(def) == GuardCompiler.GROUND_LINK_TYPE)
				{
					body.add(new Instruction( Instruction.REMOVEGROUND,
							groundToSrcPath(def), lhsmemToPath(pc.mem) ));
				}
				else if (gc.typedCxtTypes.get(def) == GuardCompiler.HLGROUND_LINK_TYPE)
				{
					Atom[] atoms = this.gc.hlgroundAttrs.get(def); // hlgroundの属性
					List<Functor> attrs = this.gc.getHlgroundAttrs(atoms);
					body.add(new Instruction( Instruction.REMOVEHLGROUND,
							groundToSrcPath(def), lhsmemToPath(pc.mem), attrs ));
				}				
			}
		}
	}
	/** 左辺の型付きプロセス文脈を解放する */
	private void freeLHSTypedProcesses()
	{
		for (ContextDef def : rs.typedProcessContexts.values())
		{
			if (gc.typedCxtTypes.get(def) == GuardCompiler.UNARY_ATOM_TYPE)
			{
				body.add(new Instruction( Instruction.FREEATOM,
						typedcxtToSrcPath(def) ));
			}
			else if (gc.typedCxtTypes.get(def) == GuardCompiler.GROUND_LINK_TYPE)
			{
				body.add(new Instruction( Instruction.FREEGROUND,groundToSrcPath(def)));
			}
			else if (gc.typedCxtTypes.get(def) == GuardCompiler.HLGROUND_LINK_TYPE)
			{
				Atom[] atoms = this.gc.hlgroundAttrs.get(def); // hlgroundの属性
				List<Functor> attrs = this.gc.getHlgroundAttrs(atoms);
				body.add(new Instruction( Instruction.FREEHLGROUND,groundToSrcPath(def), attrs));
			}
		}
	}

	/** 非線形プロセス文脈の左辺出現を解放する */
	private void freeLHSNonlinearProcessContexts()
	{
		for (ContextDef def : rs.processContexts.values())
		{
			if (def.rhsOccs.size() != 1) { // 非線型のとき1つだけ再利用するようにしたら size == 0 に直せる -> 再利用は最適化に任せることにしたので不要
				body.add(new Instruction( Instruction.DROPMEM,
						lhsmemToPath(def.lhsOcc.mem) ));
			}
		}
	}

	/** 非線形プロセス文脈の左辺出現膜を再帰的にロックする */
	private void recursiveLockLHSNonlinearProcessContextMems()
	{
		for (ContextDef def : rs.processContexts.values())
		{
			if (def.rhsOccs.size() != 1)
			{
				body.add(new Instruction( Instruction.RECURSIVELOCK,
						lhsmemToPath(def.lhsOcc.mem) ));
			}
		}
	}

	/** 非線形プロセス文脈の左辺出現膜を再帰的にロック解放する */
	private void recursiveUnlockLHSNonlinearProcessContextMems()
	{
		for (ContextDef def : rs.processContexts.values())
		{
			if (def.rhsOccs.size() != 1)
			{
				if (false) { // 再利用したときのみ recursiveunlock する
					body.add(new Instruction( Instruction.RECURSIVEUNLOCK,
							lhsmemToPath(def.lhsOcc.mem) ));
				}
			}
		}
	}

	/** 右辺の型付きプロセス文脈を構築する */
	private void buildRHSTypedProcesses()
	{
		for (ContextDef def : rs.typedProcessContexts.values())
		{
			Iterator it2 = def.rhsOccs.iterator();
			while (it2.hasNext())
			{
				ProcessContext pc = (ProcessContext)it2.next();
				if (gc.typedCxtTypes.get(def) == GuardCompiler.UNARY_ATOM_TYPE)
				{
					int atompath = varcount++;
					body.add(new Instruction( Instruction.COPYATOM, atompath,
							rhsmemToPath(pc.mem),
							typedcxtToSrcPath(pc.def) ));
					rhstypedcxtpaths.put(pc, atompath);
					rhsmappaths.put(pc, atompath);
				}
				else if (gc.typedCxtTypes.get(def) == GuardCompiler.GROUND_LINK_TYPE)
				{
					int retlistpath = varcount++;
					//System.out.println("cp");
					//int mappath = varcount++;
					body.add(new Instruction( Instruction.COPYGROUND, retlistpath,
							groundToSrcPath(pc.def), // groundの場合はリンクの変数番号のリストを指す変数番号
							rhsmemToPath(pc.mem) ));
					int groundpath = varcount++;
					body.add(new Instruction( Instruction.GETFROMLIST,groundpath,retlistpath,0));
					int mappath = varcount++;
					body.add(new Instruction(Instruction.GETFROMLIST,mappath,retlistpath,1));
					rhsgroundpaths.put(pc, groundpath);
					rhsmappaths.put(pc, mappath);
				}
				else if (gc.typedCxtTypes.get(def) == GuardCompiler.HLGROUND_LINK_TYPE)
				{
					int retlistpath = varcount++;
					//System.out.println("cp");
					//int mappath = varcount++;
					Atom[] atoms = this.gc.hlgroundAttrs.get(def); // hlgroundの属性
					List<Functor> attrs = this.gc.getHlgroundAttrs(atoms);
					body.add(new Instruction( Instruction.COPYHLGROUND, retlistpath,
							groundToSrcPath(pc.def), // groundの場合はリンクの変数番号のリストを指す変数番号
							rhsmemToPath(pc.mem), attrs));
					int groundpath = varcount++;
					body.add(new Instruction( Instruction.GETFROMLIST,groundpath,retlistpath,0));
					int mappath = varcount++;
					body.add(new Instruction(Instruction.GETFROMLIST,mappath,retlistpath,1));
					rhsgroundpaths.put(pc, groundpath);
					rhsmappaths.put(pc, mappath);
				}
			}
		}
	}

	////////////////////////////////////////////////////////////////

	/**
	 * 膜階層下にあるアクティブアトムを各膜内で先頭方向にスライド移動する。
	 * slimのためにアクティブアトム、データアトム(数値アトム以外)、
	 * 数値アトムの順に変更。数値アトムとは IntegerFunctorとFloatingFunctorがファンクタであるようなアトムのこと。
	 */
	private static void liftupActiveAtoms(Membrane mem)
	{
		for (Membrane submem : mem.mems)
		{
			liftupActiveAtoms(submem);
		}
		List<Atom> atomlist = new LinkedList<Atom>();
		for (Atom atom : mem.atoms)
		{
			atomlist.add(atom);
		}
		mem.atoms.clear();

		for (Iterator<Atom> it = atomlist.iterator(); it.hasNext();)
		{
			Atom a = it.next();
			if (a.functor.isActive())
			{
				mem.atoms.add(a);
				it.remove();
			}
		}
		for (Iterator<Atom> it = atomlist.iterator(); it.hasNext();)
		{
			Atom a = it.next();
			if (!a.functor.isNumber())
			{
				mem.atoms.add(a);
				it.remove();
			}
		}
		mem.atoms.addAll(atomlist);
	}

	/**
	 * ルールの左辺と右辺に対してstaticUnifyを呼ぶ
	 */
	private void simplify() throws CompileException
	{
		staticUnify(rs.leftMem);
		checkExplicitFreeLinks(rs.leftMem);
		staticUnify(rs.rightMem);
		if (Env.warnEmptyHead && rs.leftMem.atoms.isEmpty() && rs.leftMem.mems.isEmpty() && !rs.isInitialRule())
		{
			Env.warning("Warning: rule with empty head: " + rs);
		}

		// ガード膜に関する操作（ここでいいのか？）
		// その他 unary =/== ground の順番に並べ替える
		List<Atom> typeConstraints = rs.guardMem.atoms;
		List<Atom> unaryList = new ArrayList<Atom>();
		List<Atom> unifyList = new ArrayList<Atom>();
		List<Atom> groundList = new ArrayList<Atom>();
		for (Iterator<Atom> it = typeConstraints.iterator(); it.hasNext(); )
		{
			Atom cstr = it.next();
			Functor func = cstr.functor;
			if (func.equals("unary", 1))
			{
				unaryList.add(cstr);
			}
			else if (func.equals(Functor.UNIFY) || func.equals("==", 2) || func.equals("\\==", 2))
			{
				unifyList.add(cstr);
			}
			else if (func.equals("ground", 1))
			{
				groundList.add(cstr);
			}
			else
			{
				continue;
			}
			it.remove();
		}
		typeConstraints.addAll(unaryList);
		typeConstraints.addAll(unifyList);
		typeConstraints.addAll(groundList);
	}

	/**
	 * <p>指定された膜とその子孫に存在する冗長な {@code '='/2} を除去する。</p>
	 * <p><code>{ a(X), b(Y), X=Y } <==> { a(X), b(X) }</code></p>
	 * TODO: 冗長な自由リンク管理アトムを除去する
	 */
	private void staticUnify(Membrane mem) throws CompileException
	{
		for (Membrane submem : mem.mems)
		{
			staticUnify(submem);
		}

		for (Iterator<Atom> it = mem.atoms.iterator(); it.hasNext(); )
		{
			Atom atom = it.next();
			if (atom.functor.equals(Functor.UNIFY))
			{
				LinkOccurrence link1 = atom.args[0].buddy;
				LinkOccurrence link2 = atom.args[1].buddy;

				// 単一化アトムのリンク先が両方とも他の膜につながっている場合
				if (link1.atom.mem != mem && link2.atom.mem != mem)
				{
					if (mem == rs.leftMem)
					{
						// ( X=Y :- p(X,Y) ) は ( :- p(X,X) ) になる
					}
					else
					{
						// ( p(X,Y) :- X=Y ) はUNIFYボディ命令を出力するのでここでは何もしない
						continue;
					}
				}

				link1.buddy = link2;
				link2.buddy = link1;
				link2.name = link1.name;
				it.remove();
			}
		}
	}

	/**
	 * 型なしプロセス文脈の明示的な引数を再帰的に検査する．
	 * @param mem
	 * @throws CompileException
	 */
	private static void checkExplicitFreeLinks(Membrane mem) throws CompileException
	{
		for (Membrane submem : mem.mems)
		{
			checkExplicitFreeLinks(submem);
		}

		// $p[X,X] などを検出
		// 右辺では許される（単一化）。
		// 左辺では「内部リンクの存在」という制約を示すと考えられるが、これは意味論エラー。
		for (ProcessContext pc : mem.processContexts)
		{
			if (pc.def.isTyped()) continue;

			Set<String> occurredNames = new HashSet<String>();
			for (LinkOccurrence link : pc.args)
			{
				if (occurredNames.contains(link.name))
				{
					systemError("Syntax Error: explicit arguments of a process context in head must be pairwise disjoint: " + pc.def);
				}
				else
				{
					occurredNames.add(link.name);
				}
			}
		}
	}

	/**
	 * 命令列を最適化する
	 */
	private void optimize()
	{
		Env.c("optimize");
		//Optimizer.optimize(memMatch, body);
		if (!rs.isInitialRule())
		{
			//このフラグがtrue <=> theRuleは初期データ生成用ルール
			Optimizer.optimizeRule(theRule);
		}
	}

	////////////////////////////////////////////////////////////////
	//
	// ボディ実行
	//

	/** 左辺のアトムを所属膜から除去する。*/
	private void removeLHSAtoms()
	{
		for (int i = 0; i < lhsatoms.size(); i++)
		{
			Atom atom = (Atom)lhsatoms.get(i);
			body.add( Instruction.removeatom(
					lhsatomToPath(atom), // ← lhsmems.size() + i に一致する
					lhsmemToPath(atom.mem), atom.functor ));
		}
	}

	private void removeLHSAtoms_swaplink(Set<Atomic> removedAtoms)
	{
		for (Atomic a : removedAtoms)
		{
			if (!(a instanceof Atom)) continue;
			body.add(Instruction.removeatom(
				lhsatomToPath(a),
				lhsmemToPath(a.mem), ((Atom)a).functor));
		}
	}

	/** 左辺のアトムを実行アトムスタックから除去する。*/
	private void dequeueLHSAtoms()
	{
		for (int i = 0; i < lhsatoms.size(); i++)
		{
			Atom atom = (Atom)lhsatoms.get(i);
			if (atom.functor.isSymbol() && !Env.slimcode)
			{
				body.add(Instruction.dequeueatom(
						lhsatomToPath(atom) // ← lhsmems.size() + i に一致する
				));
			}
		}
	}
	/** 左辺の膜を子膜側から再帰的に除去する。
	 * @return 膜memの内部に出現したプロセス文脈の個数 */
	private int removeLHSMem(Membrane mem)
	{
		int procvarcount = mem.processContexts.size();
		for (Membrane submem : mem.mems)
		{
			int subcount = removeLHSMem(submem);
			body.add(new Instruction(Instruction.REMOVEMEM, lhsmemToPath(submem), lhsmemToPath(mem))); //第2引数追加 by mizuno
			if (subcount > 0)
			{
				body.add(new Instruction(Instruction.REMOVEPROXIES, lhsmemToPath(submem)));
			}
			procvarcount += subcount;
		}
		return procvarcount;
	}

	//

	private Map<ProcessContext, Integer> rhsmappaths = new HashMap<ProcessContext, Integer>();	// 右辺の非線型$p出現(ProcessContext) -> mapの変数番号(Integer)
	private static final int NOTCOPIED = -1;		// rhsmappaths未登録時の値
	private int rhspcToMapPath(ProcessContext pc)
	{
		if (!rhsmappaths.containsKey(pc)) return NOTCOPIED;
		return rhsmappaths.get(pc).intValue();
	}

	//

	private boolean fUseMoveCells = true;	// 線型$pに対してMOVECELLSを使い最適化するか（開発時向け変数）

	/** 膜の階層構造およびプロセス文脈の内容を親膜側から再帰的に生成する。
	 * @return 膜memの内部に出現したプロセス文脈の個数 */
	private int buildRHSMem(Membrane mem) throws CompileException
	{
		int procvarcount = mem.processContexts.size();
		for (ProcessContext pc : mem.processContexts)
		{
			if (pc.def.lhsOcc.mem == null)
			{
				systemError("SYSTEM ERROR: ProcessContext.def.lhsOcc.mem is not set");
			}
			if (rhsmemToPath(mem) != lhsmemToPath(pc.def.lhsOcc.mem))
			{
				if (fUseMoveCells && /*pc.def.rhsOccs.get(0) == pc*/ pc.def.rhsOccs.size() == 1)
				{
					body.add(new Instruction(Instruction.MOVECELLS,
							rhsmemToPath(mem), lhsmemToPath(pc.def.lhsOcc.mem) ));
				}
				else
				{
					int rethashmap = varcount++;
					body.add(new Instruction(Instruction.COPYCELLS,
							rethashmap, rhsmemToPath(mem), lhsmemToPath(pc.def.lhsOcc.mem) ));
					rhsmappaths.put(pc, rethashmap);
					//else {
					//	systemError("FEATURE NOT IMPLEMENTED: untyped process context must be linear: " + pc);
					//}
				}
			}
		}
		for (Membrane submem : mem.mems)
		{
			int submempath = varcount++;
			rhsmempath.put(submem, submempath);
			if (submem.pragmaAtHost != null) { // 右辺で＠指定されている場合
				if (submem.pragmaAtHost.def == null)
				{
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

	/**
	 * プロセス文脈をアトミックとして扱う。
	 */
	/*
	private int buildRHSMem_AtomicPC(Membrane mem) throws CompileException
	{
		Env.c("RuleCompiler::buildRHSMem" + mem);
		int procvarcount = mem.processContexts.size();
		for (ProcessContext pc : mem.processContexts)
		{
			if (pc.def.lhsOcc.mem == null)
			{
				systemError("SYSTEM ERROR: ProcessContext.def.lhsOcc.mem is not set");
			}
			if (rhsmemToPath(mem) != lhsmemToPath(pc.def.lhsOcc.mem))
			{
				System.err.println("ProcessContext: " + pc + " (copied from " + pc.def.lhsOcc + " in mem " + lhsmemToPath(pc.def.lhsOcc.mem) + ")");
				if (!lhsatoms.contains(pc.def.lhsOcc))
				{
					lhsatoms.add(pc.def.lhsOcc);
					lhsatompath.put(pc.def.lhsOcc, procvarcount++);
				}
				rhsAtomics.add(pc);
				rhsAtomicPath.put(pc, procvarcount++);
				body.add(new Instruction(Instruction.COPYATOM, rhsAtomicPath.get(pc), lhsatompath.get(pc.def.lhsOcc)));
			}
		}
		for (Membrane submem : mem.mems)
		{
			int submempath = varcount++;
			rhsmempath.put(submem, submempath);
			if (submem.pragmaAtHost != null) // 右辺で＠指定されている場合
			{
				if (submem.pragmaAtHost.def == null) {
					systemError("SYSTEM ERROR: pragmaAtHost.def is not set: " + submem.pragmaAtHost.getQualifiedName());
				}
				int nodedescatomid = typedcxtToSrcPath(submem.pragmaAtHost.def);
				body.add( new Instruction(Instruction.NEWROOT, submempath, rhsmemToPath(mem),
						nodedescatomid, submem.kind) );
			}
			else // 通常の右辺膜の場合
			{
				body.add( Instruction.newmem(submempath, rhsmemToPath(mem), submem.kind ) );
			}
			if (submem.name != null)
			{
				body.add(new Instruction( Instruction.SETMEMNAME, submempath, submem.name.intern() ));
			}
			int subcount = buildRHSMem_AtomicPC(submem);
			// 子膜内のプロセスが空でない場合、insertproxies命令を発行
			if (subcount > 0)
			{
				body.add(new Instruction(Instruction.INSERTPROXIES,
						rhsmemToPath(mem), rhsmemToPath(submem)));
			}
			procvarcount += subcount;
		}
		return procvarcount;
	}
	*/

	/** 右辺の膜内のルール文脈の内容を生成する */
	private void copyRules(Membrane mem)
	{
		for (Membrane submem : mem.mems)
		{
			copyRules(submem);
		}
		for (RuleContext rc : mem.ruleContexts)
		{
			if (rhsmemToPath(mem) == lhsmemToPath(rc.def.lhsOcc.mem)) continue;
			body.add(new Instruction( Instruction.COPYRULES, rhsmemToPath(mem), lhsmemToPath(rc.def.lhsOcc.mem) ));
		}
	}

	/** 右辺の膜内のルールの内容を生成する */
	private void loadRulesets(Membrane mem)
	{
		for (Membrane submem : mem.mems)
		{
			loadRulesets(submem);
		}
		for (Ruleset ruleset : mem.rulesets)
		{
			//if (!mem.rules.isEmpty())
			//{
			body.add(Instruction.loadruleset(rhsmemToPath(mem), ruleset));
			//}
		}
	}

	/** 右辺の膜内のアトムを生成する。
	 * 単一化アトムならばUNIFY命令を生成し、
	 * それ以外ならば右辺のアトムのリストrhsatomsに追加する。 */
	private void buildRHSAtoms(Membrane mem)
	{
		for (Membrane submem : mem.mems)
		{
			buildRHSAtoms(submem);
		}
		for (Atom atom : mem.atoms)
		{
			if (atom.functor.equals(Functor.UNIFY))
			{
				LinkOccurrence link1 = atom.args[0].buddy;
				LinkOccurrence link2 = atom.args[1].buddy;
				body.add(new Instruction( Instruction.UNIFY,
						lhsatomToPath(link1.atom), link1.pos,
						lhsatomToPath(link2.atom), link2.pos, rhsmemToPath(mem) ));
			}
			else
			{
				int atomid = varcount++;
				rhsatompath.put(atom, atomid);
				rhsatoms.add(atom);
				body.add(Instruction.newatom(atomid, rhsmemToPath(mem), atom.functor));
			}
		}
	}

	private void buildRHSAtoms_swaplink(Membrane mem, Set<Atomic> created, Map<Atom, Atom> reused)
	{
		for (Membrane submem : mem.mems)
		{
			buildRHSAtoms_swaplink(submem, created, reused);
		}
		for (Atom atom : mem.atoms)
		{
			if (atom.functor.equals(Functor.UNIFY))
			{
				LinkOccurrence link1 = atom.args[0].buddy;
				LinkOccurrence link2 = atom.args[1].buddy;
				body.add(new Instruction(Instruction.UNIFY,
					lhsatomToPath(link1.atom), link1.pos,
					lhsatomToPath(link2.atom), link2.pos, rhsmemToPath(mem)));
			}
			else
			{
				int atomid;
				if (reused.containsKey(atom))
				{
					atomid = lhsatomToPath(reused.get(atom));
				}
				else
				{
					atomid = varcount++;
				}
				rhsatompath.put(atom, atomid);
				//rhsAtomicPath.put(atom, atomid);
				if (created.contains(atom))
				{
					body.add(Instruction.newatom(rhsatomToPath(atom), rhsmemToPath(mem), atom.functor));
				}
			}
		}
	}

	/**
	 * <p>ルールの左辺と右辺で組になっているリンク出現の集合を得る。</p>
	 */
	private Set<LinkOccurrence> getFreeLinkOccurrence()
	{
		Set<LinkOccurrence> freeLinks = new HashSet<LinkOccurrence>();
		for (Atomic a1 : lhsatoms)
		{
			for (Atomic a2 : rhsatoms)
			{
				for (int i = 0; i < a1.getArity(); i++)
				{
					for (int j = 0; j < a2.getArity(); j++)
					{
						if (a1.args[i].buddy == a2.args[j])
						{
							freeLinks.add(a1.args[i]);
							freeLinks.add(a2.args[j]);
							break;
						}
					}
				}
			}
		}
		return freeLinks;
	}

	/** プロセス文脈定義->setの変数番号 */
	private Map<ContextDef, Integer> cxtlinksetpaths = new HashMap<ContextDef, Integer>();

	/** コピーする$pについて、そのリンクオブジェクトへの参照を取得し、
	 * そのリストを引数にinsertconnectors命令を発行する。
	 * 得たsetオブジェクトへの参照が代入された変数を覚えておき、
	 * プロセス文脈定義->setの変数番号
	 * というマップに登録する。
	 *
	 * プロセス文脈の自由リンクが実は局所リンクである場合に必要であるらしい
	 */
	private void insertconnectors()
	{
		for (ContextDef def : rs.processContexts.values())
		{
			if (def.rhsOccs.size() < 2) continue;
			List<Integer> linklist = new ArrayList<Integer>();
			int setpath = varcount++;
			for (int i = 0; i < def.lhsOcc.args.length; i++)
			{
				if (!lhslinkpath.containsKey(def.lhsOcc.args[i]))
				{
					int linkpath = varcount++;
					body.add(new Instruction(Instruction.GETLINK,linkpath,
							lhsatomToPath(def.lhsOcc.args[i].buddy.atom),def.lhsOcc.args[i].buddy.pos));
					lhslinkpath.put(def.lhsOcc.args[i], linkpath);
				}
				int srclink = lhslinkToPath(def.lhsOcc.args[i]);
				linklist.add(srclink);
			}
			body.add(new Instruction( Instruction.INSERTCONNECTORS,
					setpath,linklist,lhsmemToPath(def.lhsOcc.mem) ));
			cxtlinksetpaths.put(def, setpath);

		}
		for (ContextDef def : rs.typedProcessContexts.values())
		{
			if (gc.typedCxtTypes.get(def) != GuardCompiler.GROUND_LINK_TYPE) continue;
			List<Integer> linklist = new ArrayList<Integer>();
			int setpath = varcount++;
			for (int i = 0; i < def.lhsOcc.args.length; i++)
			{
				if (!lhslinkpath.containsKey(def.lhsOcc.args[i]))
				{
					int linkpath = varcount++;
					body.add(new Instruction(Instruction.GETLINK,linkpath,
							lhsatomToPath(def.lhsOcc.args[i].buddy.atom),def.lhsOcc.args[i].buddy.pos));
					lhslinkpath.put(def.lhsOcc.args[i], linkpath);
				}
				int srclink = lhslinkToPath(def.lhsOcc.args[i]);
				linklist.add(srclink);
			}
			body.add(new Instruction(Instruction.INSERTCONNECTORSINNULL,
					setpath,linklist));//,lhsmemToPath(def.lhsOcc.mem)));
			cxtlinksetpaths.put(def, setpath);
		}
	}

	/** 上で作られたマップから引いてきたsetと、あとコピー時に作ったマップを
	 * 引数にして、deleteconnectors命令を発行する。
	 *
	 */
	private void deleteconnectors()
	{
		/*
		Iterator it = rs.processContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
		 */
		for (ContextDef def : rs.processContexts.values())
		{
			Iterator it2 = def.rhsOccs.iterator();
			if (def.rhsOccs.size() < 2)continue;
			while (it2.hasNext())
			{
				ProcessContext pc = (ProcessContext)it2.next();
				body.add(new Instruction(Instruction.DELETECONNECTORS,
						cxtlinksetpaths.get(def).intValue(),
						rhspcToMapPath(pc)));
//				rhsmemToPath(pc.mem)));
			}
		}
		/*
		it = rs.typedProcessContexts.values().iterator();
		while(it.hasNext()){
			ContextDef def = (ContextDef)it.next();
		 */
		for (ContextDef def : rs.typedProcessContexts.values())
		{
			if (gc.typedCxtTypes.get(def) == GuardCompiler.GROUND_LINK_TYPE)
			{
				Iterator it2 = def.rhsOccs.iterator();
				while (it2.hasNext())
				{
					ProcessContext pc = (ProcessContext)it2.next();
					body.add(new Instruction(Instruction.DELETECONNECTORS,
							cxtlinksetpaths.get(def).intValue(),
							rhspcToMapPath(pc)));
//					rhsmemToPath(pc.mem)));
				}
			}
		}
	}

	/**
	 * リンクの張り替えと生成を行う
	 */
	private void updateLinks() throws CompileException
	{
		if (true)
		{
			for (LinkOccurrence link : computeRHSLinks())
			{
				int linkpath = getLinkPath(link);
				int buddypath = getLinkPath(link.buddy);
				Membrane mem = link.atom.mem;
				int mempath = rhsmemToPath(mem);
				body.add(new Instruction(Instruction.UNIFYLINKS,linkpath,buddypath,mempath));
			}
		}
	}

	/**
	 * 単一化アトムによって右辺で unify されるものをコンパイルする。
	 */
	private void compileUnify()
	{
		for (Atom atom : Collector.collectUnifyAtoms(rs.rightMem))
		{
			LinkOccurrence l1 = atom.args[0];
			LinkOccurrence l2 = atom.args[1];
			l1.atom.args[l1.pos] = l2;
			l2.atom.args[l2.pos] = l1;
		}
	}

	/**
	 * <p>リンク互換命令{@code swaplink}を使用してリンク操作をコンパイルします。</p>
	 */
	private void compileLinkOperations(Set<Atomic> removed, Set<Atomic> created, Map<Atom, Atom> reusable)
	{
		Env.c("RuleCompiler::compileLinkOperations");

		Set<LinkOccurrence> freeLinks = getFreeLinkOccurrence();

		// ファンクタが同じものには最低限同じ順序数を割り当てる
		int linkCount = countLinkOccurrence(reusable.keySet()) / 2
			+ countLinkOccurrence(removed)
			+ countLinkOccurrence(created);

		// リンク名 -> 順序数
		Map<LinkOccurrence, Integer> order = new HashMap<LinkOccurrence, Integer>();
		LinkOccurrence[] links1 = new LinkOccurrence[linkCount];
		LinkOccurrence[] links2 = new LinkOccurrence[linkCount];
		int id = 0;
		for (Map.Entry<Atom, Atom> entry : reusable.entrySet())
		{
			Atom al = entry.getKey(), ar = entry.getValue();
			if (!lhsatoms.contains(al)) continue;
			for (int i = 0; i < al.getArity(); i++)
			{
				LinkOccurrence l1 = al.args[i];
				LinkOccurrence l2 = ar.args[i];
				if (freeLinks.contains(l1))
				{
					links1[id + i] = l1;
					order.put(l1, id + i);
				}
				else
				{
					links1[id + i] = new LinkOccurrence(".", l1.atom, l1.pos);
				}
				if (freeLinks.contains(l2))
				{
					links2[id + i] = l2;
				}
				else
				{
					links2[id + i] = new LinkOccurrence(".", l2.atom, l2.pos);
				}
			}
			id += al.getArity();
		}
		for (Atomic a : removed)
		{
			for (LinkOccurrence link : a.args)
			{
				if (freeLinks.contains(link))
				{
					links1[id] = link;
					order.put(link, id);
				}
				else
				{
					links1[id] = new LinkOccurrence(".", link.atom, link.pos);
				}
				links2[id] = new LinkOccurrence(".", link.atom, link.pos);
				id++;
			}
		}
		for (Atomic a : created)
		{
			for (LinkOccurrence link : a.args)
			{
				links1[id] = new LinkOccurrence(".", link.atom, link.pos);
				order.put(link, id);
				if (freeLinks.contains(link))
				{
					links2[id] = link;
				}
				else
				{
					links2[id] = new LinkOccurrence(".", link.atom, link.pos);
				}
				id++;
			}
		}

		if (Env.verboseLinkExt && !rs.isInitialRule())
		{
			System.err.println(Arrays.toString(links1));
		}

		// 置換
		for (int i = 0; i < links2.length; i++)
		{
			LinkOccurrence link = links2[i].buddy;
			Integer j = order.get(link);
			if (freeLinks.contains(link) && i != j)
			{
				Atomic a1 = links1[i].atom;
				Atomic a2 = links1[j].atom;

				if (created.contains(a1))
				{
					if(Env.slimcode)
						body.add(new Instruction(
								Instruction.CLEARLINK, rhsatompath.get(a1),links1[i].pos));
					body.add(swaplink(
						rhsatompath.get(a1), links1[i].pos,
						lhsatompath.get(a2), links1[j].pos));
				}
				else if (created.contains(a2))
				{
					if(Env.slimcode)
						body.add(new Instruction(
								Instruction.CLEARLINK, rhsatompath.get(a2),links2[i].pos));
					body.add(swaplink(
						lhsatompath.get(a1), links1[i].pos,
						rhsatompath.get(a2), links1[j].pos));
				}
				else
				{
					body.add(swaplink(
						lhsatompath.get(a1), links1[i].pos,
						lhsatompath.get(a2), links1[j].pos));
				}
				LinkOccurrence buddy1 = links1[i].buddy;
				LinkOccurrence buddy2 = links1[j].buddy;

				links1[i].buddy = buddy2;
				if (buddy2 != null) buddy2.buddy = links1[i];

				links1[j].buddy = buddy1;
				if (buddy1 != null) buddy1.buddy = links1[j];

				String tmpName = links1[i].name;
				links1[i].name = links1[j].name;
				links1[j].name = tmpName;

				if (Env.verboseLinkExt && !rs.isInitialRule())
				{
					System.err.println(Arrays.toString(links1));
				}
			}
		}
	}

	/**
	 * <p>リンク巡回置換命令{@code cyclelinks}を使用してリンク操作をコンパイルします。</p>
	 */
	private void compileCycleLinks(Set<Atomic> removed, Set<Atomic> created, Map<Atom, Atom> reusable)
	{
		Env.c("RuleCompiler::compileCycleLinks");

		Set<LinkOccurrence> freeLinks = getFreeLinkOccurrence();

		// ルール両辺のリンク出現の和集合の要素数
		int linkCount = countLinkOccurrence(reusable.keySet()) / 2
			+ countLinkOccurrence(removed)
			+ countLinkOccurrence(created);

		// リンク名 -> 順序数
		Map<LinkOccurrence, Integer> order = new HashMap<LinkOccurrence, Integer>();
		LinkOccurrence[] links1 = new LinkOccurrence[linkCount];
		LinkOccurrence[] links2 = new LinkOccurrence[linkCount];
		int id = 0;
		for (Map.Entry<Atom, Atom> entry : reusable.entrySet())
		{
			Atom al = entry.getKey(), ar = entry.getValue();
			if (!lhsatoms.contains(al)) continue;
			for (int i = 0; i < al.getArity(); i++)
			{
				LinkOccurrence l1 = al.args[i];
				if (freeLinks.contains(l1))
				{
					order.put(l1, id + i);
					links1[id + i] = l1;
				}
				else
				{
					links1[id + i] = new LinkOccurrence(".", l1.atom, l1.pos);
				}
				LinkOccurrence l2 = ar.args[i];
				if (freeLinks.contains(l2))
				{
					links2[id + i] = l2;
				}
				else
				{
					links2[id + i] = new LinkOccurrence(".", l2.atom, l2.pos);
				}
			}
			id += al.getArity();
		}
		for (Atomic a : removed)
		{
			for (LinkOccurrence link : a.args)
			{
				if (freeLinks.contains(link))
				{
					order.put(link, id);
					links1[id] = link;
				}
				else
				{
					links1[id] = new LinkOccurrence(".", link.atom, link.pos);
				}
				links2[id] = new LinkOccurrence(".", link.atom, link.pos);
				id++;
			}
		}
		for (Atomic a : created)
		{
			for (LinkOccurrence link : a.args)
			{
				links1[id] = new LinkOccurrence(".", link.atom, link.pos);
				if (freeLinks.contains(link))
				{
					links2[id] = link;
				}
				else
				{
					links2[id] = new LinkOccurrence(".", link.atom, link.pos);
				}
				id++;
			}
		}

		// 巡回置換
		boolean[] checked = new boolean[links1.length];
		for (int i = 0; i < links1.length; i++)
		{
			if (links1[i].name.equals(".") || links1[i].buddy == links2[i] || checked[i]) continue;

			List<Integer> alist = new ArrayList<Integer>();
			List<Integer> plist = new ArrayList<Integer>();

			int j = i;
			do
			{
				checked[j] = true;
				if (links1[j].name.equals("."))
				{
					alist.add(rhsatompath.get(links1[j].atom));
				}
				else
				{
					alist.add(lhsatompath.get(links1[j].atom));
				}

				plist.add(links1[j].pos);

				if (links2[j].name.equals("."))
				{
					for (j = 0; j < links1.length; j++)
					{
						if (!checked[j] && links1[j].name.equals(".") && !links2[j].name.equals("."))
						{
							break;
						}
					}
				}
				else
				{
					j = order.get(links2[j].buddy);
				}
			}
			while (j != i && j < links1.length);
			body.add(new Instruction(Instruction.CYCLELINKS, alist, plist));
		}
	}

	/**
	 * 右辺で生成されるリンクに対して {@code newlink} 命令を生成する。
	 */
	private void compileNewlinks(Set<LinkOccurrence> newlinks)
	{
		for (LinkOccurrence l1 : newlinks)
		{
			LinkOccurrence l2 = l1.buddy;
			int atom1 = getRegisterIndexOf(l1.atom);
			int atom2 = getRegisterIndexOf(l2.atom);
			int pos1 = l1.pos;
			int pos2 = l2.pos;
			int memi = rhsmemToPath(l1.atom.mem);
			body.add(newlink(atom1, pos1, atom2, pos2, memi));
		}
	}

	private int getRegisterIndexOf(Atomic atomic)
	{
		if (atomic instanceof ProcessContext)
		{
			ProcessContext pc = (ProcessContext)atomic;
			return rhspcToMapPath(pc);
		}
		else if (atomic instanceof Atom)
		{
			Atom atom = (Atom)atomic;
			return rhsatomToPath(atom);
		}
		throw new RuntimeException("method unimplemented");
	}

	/**
	 * ルール中で変化しないアトムを求める。
	 * このアルゴリズムは初等的で、ほぼ自明なものしか検出できない。
	 * つまり、リンク先がルール膜内のプロセスに接続している場合は考慮しない。
	 * 考慮する場合、接続先の一連のプロセスについて同型性判定を行う必要がある。
	 */
	private Set<Atomic> getInvariantAtomics()
	{
		Set<Atomic> nomodified = new HashSet<Atomic>();
		for (Atomic al : lhsatoms)
		{
			if (!(al instanceof Atom) || !((Atom)al).functor.isSymbol()) continue;
			
			for (Atom ar : rhsatoms)
			{
				if (!ar.functor.isSymbol()) continue;
				
				if (al.getName().equals(ar.getName()) && al.getArity() == ar.getArity())
				{
					boolean eq = true;
					for (int i = 0; i < al.getArity(); i++)
					{
						if (al.args[i].buddy != ar.args[i])
						{
							eq = false;
							break;
						}
					}
					if (eq)
					{
						int m1 = lhsmemToPath(al.mem);
						int m2 = rhsmemToPath(ar.mem);
						if (m1 != m2)
						{
							body.add(Instruction.removeatom(lhsatomToPath(al), m1, ((Atom)al).functor));
							body.add(new Instruction(Instruction.ADDATOM, m2, lhsatomToPath(al)));
						}
						nomodified.add(al);
						nomodified.add(ar);
						break;
					}
				}
			}
		}
		return nomodified;
	}

	/**
	 * <p>再利用可能なアトム（同型アトム対）を求めます。</p>
	 */
	private Map<Atom, Atom> getReusableAtomics(Set<Atomic> noModified)
	{
		Map<Atom, Atom> reusable = new HashMap<Atom, Atom>();
		for (Atomic al : lhsatoms)
		{
			//if (!(al instanceof Atom) || !((Atom)al).functor.isSymbol() || noModified.contains(al)) continue;
			if (!(al instanceof Atom) || noModified.contains(al)) continue;
			
			for (Atom ar : rhsatoms)
			{
				//if (!ar.functor.isSymbol() || noModified.contains(ar)) continue;
				if (noModified.contains(ar)) continue;
				
				if (!reusable.containsValue(ar) && isIsomorphic((Atom)al, ar))
				{
					int m1 = lhsmemToPath(al.mem);
					int m2 = rhsmemToPath(ar.mem);
					if (m1 != m2)
					{
						// TODO: moveatom命令を実装し、ここで生成
						body.add(new Instruction(Instruction.REMOVEATOM, lhsatomToPath(al), m1, ((Atom)al).functor));
						body.add(new Instruction(Instruction.ADDATOM, m2, lhsatomToPath(al)));
					}
					reusable.put((Atom)al, ar);
					reusable.put(ar, (Atom)al);
					break;
				}
			}
		}
		return reusable;
	}

	private Set<Atomic> getRemovedAtomics(Set<Atomic> noModified, Map<Atom, Atom> reused)
	{
		return getChangedAtomics(lhsatoms, noModified, reused);
	}

	private Set<Atomic> getCreatedAtomics(Set<Atomic> noModified, Map<Atom, Atom> reused)
	{
		return getChangedAtomics(rhsatoms, noModified, reused);
	}

	private Set<Atomic> getChangedAtomics(List<? extends Atomic> atoms, Set<Atomic> noModified, Map<Atom, Atom> reused)
	{
		Set<Atomic> set = new HashSet<Atomic>();
		for (Atomic a : atoms)
		{
			if (!noModified.contains(a) && !reused.containsKey(a))
			{
				set.add(a);
			}
		}
		return set;
	}

	private static Instruction swaplink(int a1, int pos1, int a2, int pos2)
	{
		return new Instruction(Instruction.SWAPLINK, a1, pos1, a2, pos2);
	}

	private static Instruction newlink(int a1, int pos1, int a2, int pos2, int memi)
	{
		return new Instruction(Instruction.NEWLINK, a1, pos1, a2, pos2, memi);
	}

	/**
	 * 右辺のアトムを実行アトムスタックに積む
	 */
	private void enqueueRHSAtoms()
	{
		int index = body.size(); // 末尾再帰最適化の効果を最大化するため、逆順に積む（コードがセコい）
		for (Atom atom : rhsatoms)
		{
			if (atom.functor.isSymbol() && atom.functor.isActive() && !Env.slimcode)
			{
				body.add(index, new Instruction(Instruction.ENQUEUEATOM, rhsatomToPath(atom)));
			}
		}
	}

	/**
	 * 右辺のアトムを実行アトムスタックに積む(swaplink版)
	 */
	private void enqueueRHSAtoms_swaplink(Set<Atomic> created, Set<Atom> reused)
	{
		int index = body.size();

		// 生成されたアトム
		for(Atomic a : created)
		{
			Atom atom = (Atom)a;
			if (atom.functor.isSymbol() && atom.functor.isActive() && !Env.slimcode)
			{
				body.add(index, new Instruction(Instruction.ENQUEUEATOM, rhsatomToPath(a)));
			}
		}

		// 再利用されたアトム
		for(Atom atom : reused)
		{
			if (!lhsatoms.contains(atom)) continue;
			if (atom.functor.isSymbol() && atom.functor.isActive() && !Env.slimcode)
			{
				body.add(index, new Instruction(Instruction.ENQUEUEATOM, lhsatomToPath(atom)));
			}
		}
	}

	/**
	 * hyperlink関連の命令列を生成する
	 * @author Seiji Ogawa
	 */
	private void addHyperlink()
	{
		for (Atom atom : rhsatoms)
		{
			int atomID = rhsatomToPath(atom);
			Functor f = atom.functor;
			Membrane mem = atom.mem;
			if (f.equals(new SymbolFunctor("-", 2)))
			{
				//body.add( new Instruction(Instruction.REVERSEHLINK, rhsmemToPath(mem), atomID));
			}
			else if (f.equals(new SymbolFunctor("><", 2)))
			{
				body.add( new Instruction(Instruction.UNIFYHLINKS, rhsmemToPath(mem), atomID));
			}
			else if (f.equals(new SymbolFunctor(">*<", 2)))
			{
				body.add( new Instruction(Instruction.UNIFYHLINKS, rhsmemToPath(mem), atomID));
			}
			else if (f.equals(new SymbolFunctor(">+<", 2)))
			{
				body.add( new Instruction(Instruction.UNIFYHLINKS, rhsmemToPath(mem), atomID));
			}
			else if (f.equals(new SymbolFunctor(">>", 2)))
			{
				//body.add( new Instruction(Instruction.UNIFYNAMECONAME, rhsmemToPath(mem), atomID));
			}
			else if (f.equals(new SymbolFunctor("<<", 2)))
			{
			}
		}
	}

	/**
	 * Cコールバックを実行する命令を生成する
	 */
	private void addCallback()
	{
		for (Atom atom : rhsatoms)
		{
			if (atom.getName() == "$callback")
			{
				int atomID = rhsatomToPath(atom);
				body.add( new Instruction(Instruction.CALLBACK, rhsmemToPath(atom.mem), atomID));
			}
		}
	}

	/**
	 * インラインコードを実行する命令を生成する
	 */
	private void addInline()
	{
		for (Atom atom : rhsatoms)
		{
			int atomID = rhsatomToPath(atom);
			Inline.register(unitName, atom.functor.getName());
			int codeID = Inline.getCodeID(unitName, atom.functor.getName());
			if (codeID == -1) continue;
			body.add(new Instruction(Instruction.INLINE, atomID, unitName, codeID));
		}
	}

	private static final Functor FUNCTOR_USE = new SymbolFunctor("use",1);

	/**
	 * モジュールを読み込む
	 */
	private void addRegAndLoadModules()
	{
		for (Atom atom : rhsatoms)
		{
			//REG
			if (atom.functor.getArity()==1 && atom.functor.getName().equals("module"))
			{
				Module.regMemName(atom.args[0].buddy.atom.getName(), atom.mem);
			}

			//LOAD
			if (atom.functor.equals(FUNCTOR_USE))
			{
				body.add(new Instruction(Instruction.LOADMODULE, rhsmemToPath(atom.mem),
						atom.args[0].buddy.atom.getName()));
			}
			String path = atom.getPath(); // .functor.path;
			if (path != null && !path.equals(atom.mem.name))
			{
				// この時点では解決できないモジュールがあるので名前にしておく
				body.add(new Instruction(Instruction.LOADMODULE, rhsmemToPath(atom.mem), path));
			}
		}
	}

	/**
	 * （再利用された膜または）新しいルート膜に対して、子孫膜から順番にUNLOCKMEMを発行する。
	 * ただし現在の実装では、この時点ではまだ膜は再利用されていない。
	 */
	private void unlockReusedOrNewRootMem(Membrane mem)
	{
		for (Membrane submem : mem.mems)
		{
			unlockReusedOrNewRootMem(submem);
		}
		if (mem.pragmaAtHost != null) // 右辺で＠指定されている場合
		{
			body.add(new Instruction(Instruction.UNLOCKMEM, rhsmemToPath(mem)));
		}
	}

	/**
	 * 左辺の膜を廃棄する
	 */
	private void freeLHSMem(Membrane mem)
	{
		for (Membrane submem : mem.mems)
		{
			freeLHSMem(submem);
			// 再利用された場合freeしてはいけない
			body.add(new Instruction(Instruction.FREEMEM, lhsmemToPath(submem)));
		}
	}

	/**
	 * 左辺のアトムを廃棄する
	 */
	private void freeLHSAtoms()
	{
		for (int i = 0; i < lhsatoms.size(); i++)
		{
			body.add( new Instruction(Instruction.FREEATOM, lhsmems.size() + i ));
		}
	}

	/**
	 * 左辺のアトムを破棄する(swaplink版)
	 */
	private void freeLHSAtoms_swaplink(Set<Atomic> removed)
	{
		for (Atomic a : removed)
		{
			int i = lhsatompath.get(a);
			body.add(new Instruction(Instruction.FREEATOM, i));
		}
	}

	/** デバッグ用表示 */
	private void showInstructions()
	{
		Env.d("--atomMatches:");
		for (Instruction inst : atomMatch)
		{
			Env.d(inst);
		}

		Env.d("--memMatch:");
		for (Instruction inst : memMatch)
		{
			Env.d(inst);
		}

		Env.d("--body:");
		for (Instruction inst : body)
		{
			Env.d(inst);
		}
	}

	////////////////////////////////////////////////////////////////

	/**
	 * エラー出力とともに例外を発する。
	 */
	private static void systemError(String text) throws CompileException
	{
		Env.error(text);
		throw new CompileException("SYSTEM ERROR");
	}

	/**
	 * <p>文字{@code c}が英数字であるか調べます。</p>
	 * @param c 調べる文字
	 * @return 文字{@code c}が英数字の場合{@code true}、そうでない場合{@code false}を返します。
	 * TODO: このメソッドはここにあるべきではない
	 */
	private static boolean isAlphabetOrDigit(char c)
	{
		return '0' <= c && c <= '9'
			|| 'A' <= c && c <= 'Z'
			|| 'a' <= c && c <= 'z';
	}

	private static String makeRuleName(String s, boolean fullName, int limit)
	{
		StringBuilder buf = new StringBuilder("_");
		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if (isAlphabetOrDigit(c) || c == '_')
			{
				buf.append(c);
			}
			if (!fullName && buf.length() > limit)
			{
				break;
			}
		}
		return buf.toString();
	}
}
