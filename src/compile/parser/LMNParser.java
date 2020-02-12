/**
 * LMNtal Parser メインクラス
 * １つのソースコードはMembraneとして表現されます。
 */

package compile.parser;

import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.AbstractMap.SimpleEntry;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Scanner;
import runtime.Env;
import runtime.functor.Functor;
import runtime.functor.SpecialFunctor;

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


public class LMNParser {

	private static final String            LINK_NAME_PREFIX = "~"; //         [A-Za-z0-9_]* 以外
	private static final String      PROXY_LINK_NAME_PREFIX = "^"; //   [A-Z_][A-Za-z0-9_]* 以外
	private static final String PROCESS_CONTEXT_NAME_PREFIX = "_"; // [a-z0-9][A-Za-z0-9_]* 以外
	static final LinkOccurrence CLOSED_LINK = new LinkOccurrence("",null,0);

	private int nLinkNumber = 0;
	private Scanner lex = null;
	
	private SyntaxExpander expander = new SyntaxExpander(this);
	
	public void error(String text) {
		Env.error(text);
	}
	public void warning(String text) {
		Env.warning(text);
	}
	
	
	/**
	   字句解析器と入力を指定して初期化
	   @param lex 利用する字句解析器
	*/
	protected LMNParser(Scanner lex) {
		this.lex = lex;
	}
	
	/**
		デフォルトの字句解析器と指定されたストリームで初期化
	*/
	public LMNParser(Reader in) {
		this(new Lexer(in));
	}

	/**
	 * メインメソッド。ソースファイルを解析し、プロセス構造が入った膜構造を生成する。
	 * 解析後は構文エラーが修正され、リンクやコンテキスト名の解決、およびプロキシの作成が行われている。
	 * 
	 * 詳しくはaddSrcRuleToMem を参照。
	 * 
	 * @return ソースファイル全体が表すプロセス構造が入った膜構造
	 * @throws ParseException
	 */
	public Membrane parse() throws ParseException {
		LinkedList<SrcAbstract> srcProcess = parseSrc();
		setRuleText(srcProcess);
		Membrane mem = new Membrane(null);
		expander.incorporateSignSymbols(srcProcess);
//		expander.incorporateModuleNames(srcProcess);
		expander.expandAtoms(srcProcess);
//		expander.correctPragma(new LinkedList(), srcProcess, "connectRuntime"); // TODO ガードが無いので書けない ( やらなくてよくなった )
		expander.correctWorld(srcProcess);
		addProcessToMem(srcProcess, mem);
		HashMap<String,LinkOccurrence> freeLinks = addProxies(mem);
		if (!freeLinks.isEmpty()) closeFreeLinks(mem);
		return mem;
	}
	
	/**	
		解析の結果を LinkedList とする解析木として返します。
		@return 解析されたソースコードのリスト
		@throws ParseException 
	*/
	protected LinkedList<SrcAbstract> parseSrc() throws ParseException {
		parser p = new parser(lex, new ComplexSymbolFactory());
		LinkedList<SrcAbstract> result = null;
		try {
			result = (LinkedList<SrcAbstract>)p.parse().value;
		} catch (Error e) {
			error("ERROR: " + e.getMessage());
			result = new LinkedList<>();
		} catch (Throwable e) {
			throw new ParseException(e.getMessage(), e);	
		}
		return result;
	}

	////////////////////////////////////////////////////////////////

	/** ルールのテキスト表現を決定する */
	private void setRuleText(LinkedList<SrcAbstract> process) {
		ListIterator<SrcAbstract> it = process.listIterator();
		while (it.hasNext()) {
			SrcAbstract obj = it.next();
			if (obj instanceof SrcAtom) {
				setRuleText(((SrcAtom)obj).getProcess());
			} else if (obj instanceof SrcMembrane) {
				setRuleText(((SrcMembrane)obj).getProcess());
			} else if (obj instanceof SrcProcessList) {
				setRuleText(((SrcProcessList)obj).list);
			} else if (obj instanceof SrcRule) {
				SrcRule rule = (SrcRule)obj;
				rule.setText();
				setRuleText(rule.body);
			}
		}
	}

	/** ユニークな新しいリンク名を生成する */
	String generateNewLinkName() {
		nLinkNumber++;
		return LINK_NAME_PREFIX + nLinkNumber;	
	}
	/** ユニークな新しいプロセス文脈名を生成する */
	String generateNewProcessContextName() {
		nLinkNumber++;
		return PROCESS_CONTEXT_NAME_PREFIX + nLinkNumber;	
	}
	
	/**
	 * アトムの引数にリンクをセットする
	 * @param link セットしたいリンク
	 * @param atom セット先のアトム
	 * @param pos セット先のアトムでの場所
	 */
	private void setLinkToAtomArg(SrcLink link, Atomic atom, int pos) {
		//if (pos >= atom.args.length) error("SYSTEM ERROR: out of Atom arg length:"+pos);
		atom.args[pos] = new LinkOccurrence(link.getName(), atom, pos);
	}
	
	////////////////////////////////////////////////////////////////
	//
	// 構文オブジェクトを膜構造オブジェクトに追加するメソッド群
	//
	
	/**
	 * 膜にリスト内の構文オブジェクトを追加する
	 * @param list 登録する構文オブジェクトのリスト
	 * @param mem 追加先の膜
	 */
	void addProcessToMem(LinkedList<SrcAbstract> list, Membrane mem) throws ParseException {
		Iterator<SrcAbstract> it = list.iterator();
		while (it.hasNext()) {
			addObjectToMem(it.next(), mem);
		}
	}
	/**
	 * 膜にアトム、子膜、ルールなどの構文オブジェクトを追加
	 * @param obj 追加する構文オブジェクト
	 * @param mem 追加先の膜
	 */
	private void addObjectToMem(SrcAbstract obj, Membrane mem) throws ParseException {
		// リスト
		if (obj instanceof SrcProcessList) {
			Iterator<SrcAbstract> it = ((SrcProcessList)obj).list.iterator();
			while (it.hasNext()) {
				addObjectToMem(it.next(), mem);
			}
		}
		// アトム
		else if (obj instanceof SrcAtom) {
			addSrcAtomToMem((SrcAtom)obj, mem);
		}
		// 膜
		else if (obj instanceof SrcMembrane) {
			addSrcMemToMem((SrcMembrane)obj, mem);
		}
		// ルール
		else if (obj instanceof SrcRule) {
			addSrcRuleToMem((SrcRule)obj, mem);
		}
		// プロセスコンテキスト
		else if (obj instanceof SrcProcessContext) {
			addSrcProcessContextToMem((SrcProcessContext)obj, mem);
		}
		// ルールコンテキスト
		else if (obj instanceof SrcRuleContext) {
			addSrcRuleContextToMem((SrcRuleContext)obj, mem);
		}
		// リンク
		else if (obj instanceof SrcLink) {
			SrcLink link = (SrcLink)obj;
			error("SYNTAX ERROR: top-level variable occurrence: " + link.getName()+", at line "+link.lineno);
		}
		// その他 
		else {
			throw new ParseException("SYSTEM ERROR: Illegal Object to add to a membrane: "+obj);
		}
	}

	/**
	 * 膜構文を膜に追加
	 * @param sMem 追加する膜構文
	 * @param mem 追加先の膜
	 */
	private void addSrcMemToMem(SrcMembrane sMem, Membrane mem) throws ParseException {
		Membrane submem = new Membrane(mem);
		submem.stable = sMem.stable;
		submem.kind = sMem.kind;
		submem.name = sMem.name;
		if (sMem.pragma instanceof SrcProcessContext) {
			SrcProcessContext sProc = (SrcProcessContext)sMem.pragma;
			String name = sProc.getQualifiedName();
			ProcessContext pc = new ProcessContext(mem, name, 0);
			submem.pragmaAtHost = pc;
			// todo 【コード整理】直接ContextDefを代入できるようにする(1)
		}
		if (sMem.pragma != null && submem.pragmaAtHost == null) {
			warning("WARNING: unrecognized pragma, ignored: " + sMem.pragma );
		}
		addProcessToMem(sMem.getProcess(), submem);
		mem.mems.add(submem);
	}

	/**
	 * アトム構文を膜に追加
	 * @param sAtom 追加したいアトム構文
	 * @param mem 追加先の膜
	 */
	private void addSrcAtomToMem(SrcAtom sAtom, Membrane mem) {
		boolean alllinks   = true;
		boolean allbundles = true;
		LinkedList<SrcAbstract> p = sAtom.getProcess();
		int arity = p.size();
		
		// [1] ファンクタを生成する
		Functor func = Functor.build(sAtom.getName(), arity, sAtom.getNameType());
		
		// [2] アトム構造を生成する
		Atom atom = new Atom(mem, func);
		atom.setSourceLocation(sAtom.line, sAtom.column);
		if(sAtom.getNameType() == SrcName.SYMBOL){
			atom.isSelfEvaluated = true;
		}
		// [3] 引数の構造を生成する		
		for (int i = 0; i < arity; i++) {
			Object obj = p.get(i);
			// リンク
			if (obj instanceof SrcLink) {
				setLinkToAtomArg((SrcLink)obj, atom, i);
				if (obj instanceof SrcLinkBundle) { alllinks = false; }
				else { allbundles = false; }
			}
			else if (obj instanceof SrcHyperLink) {
				setLinkToAtomArg((SrcLink)obj, atom, i);
			}

//			// アトム
//			else if (obj instanceof SrcAtom) {
//				String newlinkname = generateNewLinkName();
//				((SrcAtom)obj).process.add(new SrcLink(newlinkname));
//				addSrcAtomToMem((SrcAtom)obj, mem);
//				setLinkToAtomArg(new SrcLink(newlinkname), atom, i);
//			}

//			// プロセス文脈
//			else if (obj instanceof SrcProcessContext) {
//				error("SYNTAX ERROR: untyped process context in an atom argument: " + obj);
//				setLinkToAtomArg(new SrcLink(generateNewLinkName()), atom, i);
//				allbundles = false;
//			}

			// その他
			else {
				error("SYNTAX ERROR: illegal object in an atom argument: " + obj);
				setLinkToAtomArg(new SrcLink(generateNewLinkName()), atom, i);
				allbundles = false;
			}
		}
		
		// [4] アトムとアトム集団を識別する
		if (arity > 0 && allbundles) 
			mem.aggregates.add(atom);
		else if (arity == 0 || alllinks )
			mem.atoms.add(atom);
		else {
			error("SYNTAX ERROR: arguments of an atom contain both of links and bundles");
		}
	}

	/**
	 * プロセス文脈構文を膜に追加
	 * <p>引数なしの$pは$p[|*p]という内部名*pを使った構造に自動的に置換される
	 * @param sProc 追加したいプロセス文脈構文
	 * @param mem 追加先の膜
	 */
	private void addSrcProcessContextToMem(SrcProcessContext sProc, Membrane mem) {
		ProcessContext pc;
		String name = sProc.getQualifiedName();
		if (sProc.args == null) {
			pc = new ProcessContext(mem, name, 0);
			pc.setBundleName(SrcLinkBundle.PREFIX_TAG + sProc.getName());
		} else {
			int length = sProc.args.size();
			pc = new ProcessContext(mem, name, length);
			for (int i = 0; i < length; i++) {
				String linkname = ((SrcLink)sProc.args.get(i)).getName();
				pc.args[i] = new LinkOccurrence(linkname,pc,i);
			}
			if (sProc.bundle != null) pc.setBundleName(sProc.bundle.getQualifiedName());
			if (Env.hyperLinkOpt) {/*seiji--*/
  			pc.linkName = sProc.getLinkName();
  			if (sProc.hasSameNameList()) {
  				if (pc.getSameNameList() == null) 
  					pc.sameNameList = new LinkedList<>();		
  				for (int i = 0; i < sProc.getSameNameList().size(); i++)
  					pc.getSameNameList().add(sProc.getSameNameList().get(i));
  				// ListIterator<String> itt = pc.sameNameList.listIterator();
  			}
			}/*--seiji*/
		}
		mem.processContexts.add(pc);
	}
	
	/**
	 * ルール文脈構文を膜に追加
	 * @param sRule 追加したいルール文脈構文
	 * @param mem 追加先の膜
	 */
	private void addSrcRuleContextToMem(SrcRuleContext sRule, Membrane mem) {
		RuleContext p = new RuleContext(mem, sRule.getQualifiedName());
		mem.ruleContexts.add(p);
	}
	
	/**
	 * ルール構文を膜に追加する
	 * @param sRule 追加したいルール構文
	 * @param mem 追加先の膜
	 */
	private void addSrcRuleToMem(SrcRule sRule, Membrane mem) throws ParseException {
		//2006.1.22 linenoを追加 by inui
		RuleStructure rule = new RuleStructure(mem, sRule.getText(), sRule.lineno);
		rule.name = sRule.name;
		// 略記法の展開		
		expander.expandRuleAbbreviations(sRule);
		//  左辺のルールを構文エラーとして除去する
		assertLHSRules(sRule.getHead());
		
		// 左辺およびガード型制約に対して、構造を生成し、リンク以外の名前を解決する
		addProcessToMem(sRule.getHead(), rule.leftMem);		
		addProcessToMem(sRule.getGuard(), rule.guardMem);
		HashMap<String,ContextDef> names = resolveHeadContextNames(rule);
		// ガード否定条件および右辺に対して、構造を生成し、リンク以外の名前を解決する
		addGuardNegatives(sRule.getGuardNegatives(), rule, names);
		addProcessToMem(sRule.getBody(), rule.rightMem);
		resolveContextNames(rule, names);
		
		//略記法が展開されて構造が生成され，
		//リンク以外の名前が解決されている ( $p,@pのContext.defがセットされている，*Vは双方向リンクがはられている )
		
		// プロキシアトムを生成し、リンクをつなぎ、膜の自由リンクリストを決定する
		// この時点ではアトムのリンク引数には自分自身のLinkOccurreceが格納されている
		// これらが終わると，アトムのリンク引数のLinkOccurrenceのbuddyがセットされる
		// リンクを繋ぐ作業はaddLinkOccurrenceで行われる
		
		addProxies(rule.leftMem); // addProxiesAndCoupleLinksであるべき?
		coupleLinks(rule.guardMem);
		addProxies(rule.rightMem);
		addProxiesToGuardNegatives(rule);
		coupleGuardNegativeLinks(rule);		// ガード否定条件のリンクを接続する
		coupleInheritedLinks(rule);			// 右辺と左辺の自由リンクを接続する
		
		mem.rules.add(rule);
	
	}
	
	/**
	 * アトム展開されたソース膜に対してルールが無いことを確認する
	 * 
	 */
	private void assertLHSRules(LinkedList<SrcAbstract> procs)throws ParseException{
		Iterator<SrcAbstract> it = procs.iterator();
		while (it.hasNext()) {
			SrcAbstract obj = it.next();
			if (obj instanceof SrcRule) {
				SrcRule sr = (SrcRule)obj;
				throw new ParseException("SYNTAX ERROR: rule head has some rules at line " + sr.lineno);
			}
			else if (obj instanceof SrcMembrane) {
				assertLHSRules(((SrcMembrane)obj).process);
			}
		}
	}

	/** ガード否定条件の中間形式に対応する構造を生成する
	 *  @param sNegatives ガード否定条件の中間形式[$p,[Q]]のリスト[in]
	 *  @param rule ルール構造[in,out]
	 *  @param names 左辺およびガード型制約に出現した$p（と*X）からその定義（と出現）へのマップ[in] */
	private void addGuardNegatives(LinkedList<LinkedList<LinkedList<SrcAbstract>>> sNegatives, RuleStructure rule, HashMap<String,ContextDef> names) throws ParseException {
		for(LinkedList<LinkedList<SrcAbstract>> list1 : sNegatives){
			LinkedList<ProcessContextEquation> neg = new LinkedList<>();
			ListIterator<LinkedList<SrcAbstract>> it2 = list1.listIterator();
			while(it2.hasNext()){
				LinkedList<SrcAbstract> sPair = it2.next();
				String cxtname = ((SrcProcessContext)sPair.getFirst()).getQualifiedName();
				if (!names.containsKey(cxtname)) {
					error("SYNTAX ERROR: fresh process context constrained in a negative condition: " + cxtname + " in a rule at line " + rule.lineno);
				}
				else {
					ContextDef def = (ContextDef)names.get(cxtname);
					if (def.typed) {
						error("SYNTAX ERROR: typed process context constrained in a negative condition: " + cxtname + " in a rule at line " + rule.lineno);
					}
					else if (def.lhsOcc != null) {
						Membrane mem = new Membrane(null);
						addProcessToMem(((SrcProcessList)sPair.getLast()).list,mem);
						neg.add(new ProcessContextEquation(def,mem));
					}
				}
			}
			rule.guardNegatives.add(neg);
		}
	}

	////////////////////////////////////////////////////////////////
	//
	// リンクとプロキシ
	//
	
	/** 子膜に対して再帰的にプロキシを追加する。
	 * @return この膜の更新された自由リンクマップ mem.freeLinks */
	private HashMap<String,LinkOccurrence> addProxies(Membrane mem) {
		HashSet<String> proxyLinkNames = new HashSet<>();	// memとその子膜の間に作成した膜間リンク名の集合
		for(Membrane submem : mem.mems){
			HashMap<String,LinkOccurrence> freeLinks = addProxies(submem);
			// 子膜の自由リンクに対してプロキシを追加する
			HashMap<String,LinkOccurrence> newFreeLinks = new HashMap<>();
			Iterator<String> it2 = freeLinks.keySet().iterator();
			while (it2.hasNext()) {
				LinkOccurrence freeLink = (LinkOccurrence)freeLinks.get(it2.next());
				// 子膜の自由リンク名 freeLink.name に対して、膜間リンク名 proxyLinkName を決定する。
				// 通常はXに対して、1^Xとする。
				// Xがmemの局所リンクであり、1^Xをmem内ですでに使用した場合は、1^^Xとする。
				// Xがsubmemの子膜への直通リンクであり、そこでの膜間リンク名が1^Xの場合は、2^Xとする。
				String index = "1";
				if (freeLink.atom instanceof Atom
				 && ((Atom)freeLink.atom).functor.isOutsideProxy()
				 && freeLink.atom.args[0].name.startsWith("1") ) {
				 	index = "2";
				}
				String proxyLinkName = index + PROXY_LINK_NAME_PREFIX + freeLink.name;
				if (proxyLinkNames.contains(proxyLinkName)) {
					proxyLinkName = index + PROXY_LINK_NAME_PREFIX
						+ PROXY_LINK_NAME_PREFIX + freeLink.name;
				}
				proxyLinkNames.add(proxyLinkName);
				// 子膜にinside_proxyを追加
//				ProxyAtom inside = new ProxyAtom(submem, ProxyAtom.INSIDE_PROXY);
				Atom inside = new Atom(submem,Functor.INSIDE_PROXY);
				inside.args[0] = new LinkOccurrence(proxyLinkName, inside, 0); // 外側
				inside.args[1] = new LinkOccurrence(freeLink.name, inside, 1); // 内側
				inside.args[1].buddy = freeLink;
				freeLink.buddy = inside.args[1];
				submem.atoms.add(inside);
				// 新しい自由リンク名を新しい自由リンク一覧に追加する
				newFreeLinks.put(proxyLinkName, inside.args[0]);			
				// この膜にoutside_proxyを追加
//				ProxyAtom outside = new ProxyAtom(mem, new SpecialFunctor("$out", 2, submem.kind));
				Atom outside = new Atom(mem, new SpecialFunctor("$out", 2, submem.kind));//Functor.OUTSIDE_PROXY);
				outside.args[0] = new LinkOccurrence(proxyLinkName, outside, 0); // 内側
				outside.args[1] = new LinkOccurrence(freeLink.name, outside, 1); // 外側
				outside.args[0].buddy = inside.args[0];
				inside.args[0].buddy = outside.args[0];
				mem.atoms.add(outside);
			}
			submem.freeLinks = newFreeLinks;
		}
		
		// memの子膜の自由リンクはプロキシを挟まれてmemまで上がってきている
		
		return coupleLinks(mem);
	}
	/** ガード否定条件に対してaddProxiesを呼ぶ */
	private void addProxiesToGuardNegatives(RuleStructure rule) {
		Iterator<LinkedList<ProcessContextEquation>> it = rule.guardNegatives.iterator();
		while (it.hasNext()) {
			Iterator<ProcessContextEquation> it2 = it.next().iterator();
			while (it2.hasNext()) {
				ProcessContextEquation eq = (ProcessContextEquation)it2.next();
				addProxies(eq.mem);
			}
		}
	}
	/**
	 * 指定された膜にあるアトムの引数に対して、リンクの結合を行い、自由リンクのHashMapを返す。
	 * <p>子膜に対してリンクの結合およびプロキシの作成が行われた後で呼び出される。
	 * <p>副作用として、メソッドの戻り値を mem.freeLinks にセットする。
	 * @return リンク名から自由リンク出現へのHashMap
	 */
	private HashMap<String,LinkOccurrence> coupleLinks(Membrane mem) {
		// 同じ膜レベルのリンク結合を行う
		HashMap<String,LinkOccurrence> links = new HashMap<>();
		List[] lists = {mem.atoms, mem.processContexts, mem.typedProcessContexts};
		for (int i = 0; i < lists.length; i++) {
			Iterator it = lists[i].iterator();
			while (it.hasNext()) {
				Atomic a = (Atomic)it.next();
				for (int j = 0; j < a.args.length; j++) {
					if (a.args[j].buddy == null) { // outside_proxyの第1引数はすでに非nullになっている
						addLinkOccurrence(links, a.args[j]);
					}
				}
			}
		}
		removeClosedLinks(links);
		mem.freeLinks = links;
		return links;
	}
	
	/** 閉じたリンクをlinksから除去する */
	private static void removeClosedLinks(HashMap links) {
		Iterator it = links.keySet().iterator();
		while (it.hasNext()) {
			String linkName = (String)it.next();
			if (links.get(linkName) == CLOSED_LINK) it.remove();
		}
	}
	
	/**
	 * 指定されたリンク出現を記録する。同じ名前で2回目の出現ならばリンクの結合を行う。
	 * @param lnk 記録するリンク出現
	 */
	private void addLinkOccurrence(HashMap<String, LinkOccurrence> links, LinkOccurrence lnk) {
		// 3回以上の出現
		if (links.get(lnk.name) == CLOSED_LINK) {
			error("SYNTAX ERROR: link " + lnk.name + " appears more than twice at line " + lnk.atom.line);
			String linkname = lnk.name + generateNewLinkName();
			if (lnk.name.startsWith(SrcLinkBundle.PREFIX_TAG))
				linkname = SrcLinkBundle.PREFIX_TAG + linkname;
			lnk.name = linkname;
			links.put(lnk.name, lnk);
		}
		// 1回目の出現
		if (links.get(lnk.name) == null) {
			links.put(lnk.name, lnk);
		}
		// 2回目の出現
		else {
			LinkOccurrence buddy = links.get(lnk.name);
			lnk.buddy = buddy;
			buddy.buddy = lnk;
			links.put(lnk.name, CLOSED_LINK);
		}
	}
	
	/** 膜memの自由リンクを膜内で閉じる（構文エラーからの復帰用） */
	public void closeFreeLinks(Membrane mem) {
		Iterator<String> it = mem.freeLinks.keySet().iterator();
		while (it.hasNext()) {
			LinkOccurrence link = mem.freeLinks.get(it.next());
			warning("WARNING: global singleton link: " + link.name + " at line " + link.atom.line);
			LinkedList<SrcAbstract> process = new LinkedList<>();
			process.add(new SrcLink(link.name));
			SrcAtom sAtom = new SrcAtom(link.name, process);
			addSrcAtomToMem(sAtom, mem);
		}
		coupleLinks(mem);
	}
	/** ルールのガード否定条件のトップレベルのリンクをつなぐ。
	 * <p>各膜のfreeLinksの計算後、coupleInheritedLinks(rule)の前に呼ぶこと。
	 * <p>本メソッド終了後、否定条件中の等式内のリンク出現は次のいずれかになる：
	 * <ol>
	 * <li>同じ等式右辺への局所リンク（通常の双方向リンク）- 本メソッド呼び出し時にすでに閉じている
	 * <li>同じ否定条件内の他の等式右辺への「等式間リンク」（双方向直接リンク）
	 * <li>その等式左辺$pがルール左辺で出現する膜にあるアトム/型付き$pへの「上書きリンク」（片方向リンク）
	 *     （ただし現状では型付きアトムが1引数のみであるため、パッシブ型制限にかかるため型付きへのリンクは無い）
	 * <li>nullを指すガード「匿名リンク」（正確には$ppの明示的なリンク引数との通常の双方向リンク）
	 * </ul>
	 * <p>ガードコンパイルで実際に使うときには、等式間リンクに対して、自由リンク管理アトムの鎖を適宜補うこと。*/
	void coupleGuardNegativeLinks(RuleStructure rule) {
		Iterator<LinkedList<ProcessContextEquation>> it = rule.guardNegatives.iterator();
		while (it.hasNext()) {
			HashMap<String,LinkOccurrence> interlinks = new HashMap<>();	// 等式間リンクおよびガード匿名リンクの一覧
			Iterator<ProcessContextEquation> it2 = it.next().iterator();
			while (it2.hasNext()) {
				ProcessContextEquation eq = (ProcessContextEquation)it2.next();
				// 等式右辺の自由リンク出現の一覧を取得する
				Membrane mem = eq.mem;
				HashMap<String,LinkOccurrence> rhsfreelinks = mem.freeLinks;
				// 等式左辺の自由リンク出現の一覧を取得し、右辺の一覧と対応を取る
				ProcessContext a = (ProcessContext)eq.def.lhsOcc;
				HashMap<String,LinkOccurrence> rhscxtfreelinks = new HashMap<>();	// この等式右辺トップレベル$ppの自由リンク集合
				for (int i = 0; i < a.args.length; i++) {
					LinkOccurrence lhslnk = a.args[i];
					// String linkname = lhslnk.name; // unused
					if (rhsfreelinks.containsKey(lhslnk.name)) {
						// 両辺に出現する場合: ( {$p[X]} :- \+($p=(a(X),$pp)) | ... )
						LinkOccurrence rhslnk = (LinkOccurrence)rhsfreelinks.get(lhslnk.name);
						rhslnk.buddy = lhslnk.buddy;	// 一方向のみのbuddy設定を行う
						rhsfreelinks.put(lhslnk.name, CLOSED_LINK);
					}
					else {
						// 左辺にのみ出現する場合: ( {$p[X]} :- \+($p=(a,$pp)) | ... )
						rhscxtfreelinks.put(lhslnk.name, lhslnk);
					}
				}
				removeClosedLinks(rhsfreelinks);
				Iterator<String> it3 = rhsfreelinks.keySet().iterator();
				while (it3.hasNext()) {
					String linkname = (String)it3.next();
					LinkOccurrence lnk = (LinkOccurrence)rhsfreelinks.get(linkname);
					// 右辺にのみ出現する場合:
					// ( ... :- \+($p=a(X),$q=b(X)) | ... ) => 等式間リンクは、2回目の出現のとき閉じられる
					// ( ... :- \+($p=(a(X),$pp)  ) | ... ) => ガード匿名リンク（トップレベル$ppの自由リンク）
					addLinkOccurrence(interlinks, lnk);
					// todo lnkが3回目以降の出現のとき、リンク名が変わったため、rhsfreelinksの修正が必要
				}
			}
			removeClosedLinks(interlinks);

			// ガード匿名リンクを処理する（$ppの剰余項が[]でない限り重要ではない）
			
			// {$p[A|*V]} :- \+($p=(f(A)            )) | ... // *V={}       片方向リンク
			// {$p[A|*V]} :- \+($p=(e,   $pp[ |*W]  )) | ... // A∈*W
			// {$p[A|*V]} :- \+($p=(e,   $pp[A|*W]  )) | ... // *V=*W       片方向リンク
			// {$p[A|*V]} :- \+($p=(e,   $pp[B|*W]  )) | ... // A∈*W,B∈*V 
			// {$p[ |*V]} :- \+($p=(f(B)            )) | ... // *V={B}
			// {$p[A]   } :- \+($p=(f(B)            )) | ... // {A}≠{B}
			// {$p[A|*V]} :- \+($p=(f(B)            )) | ... // not A∈{B} よりマッチしない
			// {$p[A]   } :- \+($p=(f(B),$pp[A,B|*W])) | ... //
			// {$p[A|*V]} :- \+($p=(f(B),$pp[A,B|*W])) | ... //
			
			// {$p[A|*V]} :- \+($p=(f(B),$pp[A,B|*W])) | ... //
						
			Iterator<String> it3 = interlinks.keySet().iterator();
			anonymouslink:
			while (it3.hasNext()) {
				String linkname = (String)it3.next();
				LinkOccurrence lnk = (LinkOccurrence)interlinks.get(linkname);
				if (lnk.atom.mem.processContexts.isEmpty()) {
					warning("WARNING: unsatisfiable negative condition because of the free link: " + lnk.name);
				}
				else {
					ProcessContext pc = (ProcessContext)lnk.atom.mem.processContexts.get(0);
					LinkOccurrence[] newargs = new LinkOccurrence[pc.args.length + 1];
					for (int i = 0; i < pc.args.length; i++) {
						if (pc.args[i].name.equals(lnk.name)) continue anonymouslink;
						newargs[i] = pc.args[i];
					}
					newargs[pc.args.length] = new LinkOccurrence(lnk.name, pc, pc.args.length);
					pc.args = newargs;
				}
			}
		}
	}
	/** 左辺と右辺の自由リンクをつなぐ */
	void coupleInheritedLinks(RuleStructure rule) {
		HashMap<String,LinkOccurrence> lhsFreeLinks = rule.leftMem.freeLinks;
		HashMap<String,LinkOccurrence> rhsFreeLinks = rule.rightMem.freeLinks;
		HashMap<String,LinkOccurrence> links = new HashMap<>();
		Iterator<String> it = lhsFreeLinks.keySet().iterator();
		while (it.hasNext()) {
			String linkname = it.next();
			LinkOccurrence lhsocc = (LinkOccurrence)lhsFreeLinks.get(linkname);
			addLinkOccurrence(links, lhsocc);
		}
		it = rhsFreeLinks.keySet().iterator();
		while (it.hasNext()) {
			String linkname = it.next();
			LinkOccurrence rhsocc = (LinkOccurrence)rhsFreeLinks.get(linkname);
			addLinkOccurrence(links, rhsocc);
		}
		removeClosedLinks(links);
		if (!links.isEmpty()) {
			it = links.keySet().iterator();
			while (it.hasNext()) {
				LinkOccurrence link = (LinkOccurrence)links.get(it.next());
//				error("SYNTAX ERROR: rule with free variable: "+ link.name + "\n    in " + rule);
				error("SYNTAX ERROR: rule with free variable: "+ link.name + ", at line " + rule.lineno);
				LinkedList<SrcAbstract> process = new LinkedList<>();
				process.add(new SrcLink(link.name));
				SrcAtom sAtom = new SrcAtom(link.name, process);
				addSrcAtomToMem(sAtom, link.atom.mem);
			}
			coupleLinks(rule.leftMem);
			coupleLinks(rule.rightMem);
		}
	}

	
	////////////////////////////////////////////////////////////////
	//
	// プロセス文脈、型付きプロセス文脈、ルール文脈、リンク束
	//

	/** ガード型制約の型付きプロセス文脈のリストを作成する。
	 * @param names コンテキストの限定名 (String) から ContextDef への写像 [in,out] */
	private void enumTypedNames(Membrane mem, HashMap<String,ContextDef> names) {
		Iterator<ProcessContext> it = mem.processContexts.iterator();
		while (it.hasNext()) {
			ProcessContext pc = it.next();
			String name = pc.getQualifiedName();
			if (!names.containsKey(name)) {
				pc.def = new ContextDef(pc.getQualifiedName());
				//TODO ガードで出現するからと言って型付きとは限らない
				pc.def.typed = true;
				names.put(name, pc.def);
			}
			else pc.def = names.get(name);
			it.remove();
			mem.typedProcessContexts.add(pc);
			// TODO ここは実質何もしていないし、型があわない
			// if (pc.bundle != null) addLinkOccurrence(names, pc.bundle);
		}
	}
	
	/** ヘッドのプロセス文脈、型付きプロセス文脈、ルール文脈、リンク束のリストを作成する。
	 * <strike>型なし</strike>(2006/09/13 kudo) プロセス文脈の明示的な引数が互いに異なることを確認する。 
	 * TODO 二つ目の仕事は別メソッドにすべき
	 * @param mem 左辺膜、またはガード否定条件内等式制約右辺の構造を保持する膜
	 * @param names コンテキストの限定名 (String) から ContextDef への写像 [in,out]
	 * @param isLHS 左辺かどうか（def.lhsOccに追加するかどうかの判定に使用される）*/
	private void enumHeadNames(Membrane mem, HashMap names, boolean isLHS) throws ParseException {
		// 子膜
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			enumHeadNames(submem, names, isLHS);
		}
		//
		it = mem.processContexts.iterator();
		while (it.hasNext()) {
			ProcessContext pc = (ProcessContext)it.next();
			String name = pc.getQualifiedName();
			if (!names.containsKey(name)) {
				pc.def = new ContextDef(name);
				names.put(name, pc.def);
			}
			else {
				it.remove(); // 少なくとも型なしプロセス文脈ではない（型付きまたはエラーとなる）ため取り除く
				pc.def = (ContextDef)names.get(name);
				if (pc.def.isTyped()) {
					if (pc.def.lhsOcc != null) { // 既に左辺に出現している
						// 展開を実装すれば不要になる（ガード否定条件のときはどうしても書けないが放置）
						// TODO 構文エラーでは？ (2006/12/01 kudo)
						error("FEATURE NOT IMPLEMENTED: head contains more than one occurrence of a typed process context name: " + name + " at line " + pc.line);
						continue;
					}
					// 2引数以上の左辺出現型付きプロセス文脈を許す ( 2006/09/13 by kudo )
					if (pc.args.length == 0){//!= 1) {
//						error("SYNTAX ERROR: typed process context occurring in head must have exactly one explicit free link argument: " + pc);
						error("SYNTAX ERROR: typed process context occurring in head must have some explicit free link arguments: " + pc + " at line " + pc.line);
						continue;
					}
					mem.typedProcessContexts.add(pc);
				}
				else {
					// 構造比較への変換を実装すれば不要になる（ガード否定条件のときはどうしても書けないが放置）
					// TODO 構文エラーでは？ (2006/12/01 kudo)
					error("FEATURE NOT IMPLEMENTED: untyped process context name appeared more than once in a head: " + name + " at line " + pc.line);
					continue;
				}
			}
			if (isLHS)  pc.def.lhsOcc = pc;	// 左辺での出現を登録
			if (pc.bundle != null) addLinkOccurrence(names, pc.bundle);
			//
			if (!pc.def.isTyped()) {
				HashSet<String> explicitfreelinks = new HashSet<>();
				for (int i = 0; i < pc.args.length; i++) {
					LinkOccurrence lnk = pc.args[i];
					if (explicitfreelinks.contains(lnk.name)) {
						error("SYNTAX ERROR: explicit arguments of a process context in head must be pairwise disjoint: " + pc.def + " at line " + pc.line);
						lnk.name = lnk.name + generateNewLinkName();
					}
					else {
						explicitfreelinks.add(lnk.name);
					}
				}
			}
		}
		it = mem.ruleContexts.iterator();
		while (it.hasNext()) {
			RuleContext rc = (RuleContext)it.next();
			String name = rc.getQualifiedName();
			if (!names.containsKey(name)) {
				rc.def = new ContextDef(name);
				if (isLHS)  rc.def.lhsOcc = rc;
				names.put(name, rc.def);
			}
			else {
				error("SYNTAX ERROR: head contains more than one occurrence of a rule context: " + name + " at line " + rc.line);
				it.remove();
			}
		}
		it = mem.aggregates.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();
			for (int i = 0; i < atom.args.length; i++) {
				addLinkOccurrence(names, atom.args[i]);
			}
		}
		//
		if (mem.processContexts.size() > 1) {
			it = mem.processContexts.iterator();
			while (it.hasNext()) {
				ProcessContext pc = (ProcessContext)it.next();
				if (pc.def.lhsOcc == pc)  pc.def.lhsOcc = null; // 左辺での出現の登録を取り消す
				it.remove(); // namesには残る
				error("SYNTAX ERROR: head membrane cannot contain more than one untyped process context: " + pc.def.getName() + " at line " + pc.line);
			}
		}
		if (mem.ruleContexts.size() > 1) {
			it = mem.ruleContexts.iterator();
			while (it.hasNext()) {
				RuleContext rc = (RuleContext)it.next();
				if (rc.def.lhsOcc == rc)  rc.def.lhsOcc = null; // 左辺での出現の登録を取り消す
				it.remove(); // namesには残る
				error("SYNTAX ERROR: head membrane cannot contain more than one rule context: " + rc.def.getName() + " at line " + rc.line);
			}
		}
		//
		if (mem.pragmaAtHost != null) { // ＠指定は型付きプロセス文脈（仮）
			ProcessContext pc = mem.pragmaAtHost;
			String name = pc.getQualifiedName();
			pc.def = (ContextDef)names.get(name);
			if (pc.def == null) {
				error("SYSTEM ERROR: contextdef not set for pragma " + name + " at line " + pc.line);
			}			
			// todo 【コード整理】直接ContextDefを代入できるようにする(2)
			if (pc.def.lhsMem == null) {
				pc.def.lhsMem = mem;
			}
			else {
				// 展開を実装すれば不要になる？
				error("FEATURE NOT IMPLEMENTED: head contains more than one occurrence of a typed process context name for pragma: " + name);
			}
		}
	}
	/** ボディのプロセス文脈、型付きプロセス文脈、ルール文脈、リンク束のリストを作成する。
	 * @param names コンテキストの限定名 (String) から ContextDef への写像 [in] */
	private void enumBodyNames(Membrane mem, HashMap names) throws ParseException {
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			enumBodyNames(submem, names);
		}
		//
		it = mem.processContexts.iterator();
		while (it.hasNext()) {
			ProcessContext pc = (ProcessContext)it.next();
			String name = pc.getQualifiedName();
			if (!names.containsKey(name)) {
				error("SYNTAX ERROR: untyped process context not appeared in head: " + pc.getQualifiedName() + " at line " + pc.line);
				it.remove();
				continue;
			}
			else {
				pc.def = (ContextDef)names.get(name);
				if (pc.def.lhsOcc != null) {
					if (pc.args.length != pc.def.lhsOcc.args.length
					 || ((pc.bundle == null) != (((ProcessContext)pc.def.lhsOcc).bundle == null)) ) {
						error("SYNTAX ERROR: unmatched length of free link list of process context: " + pc + " at line " + pc.line);
						it.remove();
						continue;
					}
				}
				if (pc.def.isTyped()) {
					it.remove();
//					if (pc.args.length >= 1) {
//					error("SYNTAX ERROR: typed process context occurring in body must have exactly one explicit free link argument: " + pc + " at line " + pc.line);
				if(pc.args.length == 0){
					error("SYNTAX ERROR: typed process context occurring in body must have some explicit free link argument: " + pc + " at line " + pc.line);
						continue;
					}
					mem.typedProcessContexts.add(pc);
				}
				else {
					if (pc.def.lhsOcc == null) {
						// 構文エラーによりヘッド出現が取り消された型なし$pは、ボディ出現が無言で取り除かれる。
						it.remove();
						continue;
					}
				}
				pc.def.rhsOccs.add(pc);
			}
		}
		it = mem.ruleContexts.iterator();
		while (it.hasNext()) {
			RuleContext rc = (RuleContext)it.next();
			String name = (String)rc.getQualifiedName();
			if (names.containsKey(name)) {
				rc.def = (ContextDef)names.get(name);
				rc.def.rhsOccs.add(rc);
			}
			else {
				error("SYNTAX ERROR: rule context not appeared in head: " + rc + " at line " + rc.line);
				it.remove();
			}
		}
		it = mem.aggregates.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();
			for (int i = 0; i < atom.args.length; i++) {
				addLinkOccurrence(names, atom.args[i]);
			}
		}
		//
		if (mem.pragmaAtHost != null) { // ＠指定は型付きプロセス文脈（仮）
			ProcessContext pc = mem.pragmaAtHost;
			String name = pc.getQualifiedName();
			pc.def = (ContextDef)names.get(name);
			if (pc.def == null) {
				error("SYSTEM ERROR: contextdef not set for pragma " + name + " at line " + pc.line);
			}	
			// todo 【コード整理】直接ContextDefを代入できるようにする(2)
		}
	}

	/** 左辺およびガード型制約に対して、プロセス文脈およびルール文脈の名前解決を行う。
	 *  名前解決により発見された構文エラーを訂正する。
	 *  @return 左辺およびガードに出現する限定名(String) -> ContextDef / LinkOccurrence(Bundles) */
	private HashMap<String,ContextDef> resolveHeadContextNames(RuleStructure rule) throws ParseException {
		HashMap<String,ContextDef> names = new HashMap<>();
		//次のメソッド後には型付きプロセス文脈の pc.def.typed が true になる
		enumTypedNames(rule.guardMem, names); // この時点では型付きプロセス文脈のみ
		enumHeadNames(rule.leftMem, names, true); // この時点で型なしプロセス文脈およびルール文脈およびリンク束が登録される
		// todo リンク束が左辺で閉じていないことを確認する
		// ---リンク束が2回出現したかどうかを調べればよいだけ。
		// ( ここではやらなくてよいかもしれない )
		
		// 左辺トップレベルのプロセス文脈を削除する
		Iterator<ProcessContext> it = rule.leftMem.processContexts.iterator();
		while (it.hasNext()) {
			ProcessContext pc = (ProcessContext)it.next();
			error("SYNTAX ERROR: untyped head process context requires an enclosing membrane: " + pc + " at line " + pc.line);
			names.remove(pc.def.getName());
			pc.def.lhsOcc = null;	// 左辺での出現の登録を取り消す
			it.remove();
		}
		return names;
	}

	/** ガード否定条件および右辺に対して、プロセス文脈およびルール文脈の名前解決を行う。
	 *  名前解決により発見された構文エラーを訂正する。*/
	private void resolveContextNames(RuleStructure rule, HashMap names) throws ParseException {

		// 同じ名前のプロセス文脈の引数パターンを同じにする。
		// 型付きは明示的な自由リンクの個数を1にする。

		Iterator it;
		
		// ガード否定条件
		it = rule.guardNegatives.iterator();
		while (it.hasNext()) {
			Iterator it2 = ((LinkedList)it.next()).iterator();
			HashMap tmpnames = (HashMap)names.clone();	// 他の条件やボディとは関係ないため
			HashSet cxtnames = new HashSet();
			while (it2.hasNext()) {
				ProcessContextEquation eq = (ProcessContextEquation)it2.next();
				String cxtname = eq.def.getName();
				if (cxtnames.contains(cxtname)) {
					error("SYNTAX ERROR: process context constrained more than once in a negative condition: " + cxtname);
					it2.remove();
				}
				else {
					cxtnames.add(cxtname);
					enumHeadNames(eq.mem, tmpnames, false);
				}
			}
		}
				
		// 右辺
		enumBodyNames(rule.rightMem, names);
		
		// todo リンク束を閉じる
		
		// todo プロセス文脈間で継承されたリンク束が同じ名前であることを確認する
		// todo 右辺のアトム集団のリンク先が全て同じプロセス文脈名を持つことを確認する
		
		// rule.processContexts/ruleContexts/typedProcessContexts を生成する
		it = names.keySet().iterator();
		while (it.hasNext()) {
			String name = (String)it.next();
			Object obj = names.get(name);
			if (obj instanceof LinkOccurrence) continue;	// リンク束のときは無視
			ContextDef def = (ContextDef)obj;
			if (def.isTyped()) {
				rule.typedProcessContexts.put(name, def);
			}
			else { // 型付きでない場合、lhsOcc!=nullとなっている
				if (def.lhsOcc instanceof ProcessContext) {
					rule.processContexts.put(name, def);
				}
				else if (def.lhsOcc instanceof RuleContext) {
					rule.ruleContexts.put(name, def);
				}
			}
//			if (def.rhsOccs.size() == 1) {
//				if (def.lhsOcc != null) {	// ガードでないとき
//					Context rhsocc = ((Context)def.rhsOccs.get(0));
//					rhsocc.buddy = def.lhsOcc;
//					def.lhsOcc.buddy = rhsocc;
//				}
//			}
			if( def.lhsOcc != null) {	// ガードでないとき
				for(int i=0;i<def.rhsOccs.size();i++){
					Context rhsocc = ((Context)def.rhsOccs.get(i));
					rhsocc.def.lhsOcc = def.lhsOcc;
				}
				//if (def.rhsOccs.size() > 0 )def.lhsOcc.buddy = ((Context)def.rhsOccs.get(0));
			}
		}
		
		// （非線型プロセス文脈が実装されるまでの仮措置として）線型でなく剰余引数が[]でない型なし$pを取り除く
		it = rule.processContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			if (def.rhsOccs.size() != 1 && ((ProcessContext)def.lhsOcc).bundle != null) {
				error( "FEATURE NOT IMPLEMENTED: untyped process context must be linear unless its bundle is null: "
					+ def.getName() +"\nHINT: Try " + def.getName() + "[] instead." );
				throw new ParseException("");
//				int len = def.lhsOcc.args.length;
//				Iterator it2 = def.rhsOccs.iterator();
//				while (it2.hasNext()) {
//					ProcessContext pc = (ProcessContext)it2.next();
//					pc.mem.processContexts.remove(pc);
//					Atom atom = new Atom(pc.mem, def.getName(), len);
//					for (int i = 0; i < len; i++) {
//						atom.args[i] = new LinkOccurrence(pc.args[i].name, atom, i);
//					}
//					// if (pc.bundle != null) { remove from names; }
//					pc.mem.atoms.add(atom);
//					it2.remove();
//				}
//				ProcessContext rhsocc = new ProcessContext(rule.rightMem, def.getName(), len);
//				rule.rightMem.processContexts.add(rhsocc);
//				for (int i = 0; i < len; i++) {
//					String linkname = generateNewLinkName();
//					rhsocc.args[i] = new LinkOccurrence(linkname, rhsocc, i);
//					Atom atom = new Atom(rule.rightMem, linkname, 1);
//					atom.args[0] = new LinkOccurrence(linkname, atom, 0);
//					rule.rightMem.atoms.add(atom);
//				}
//				if (((ProcessContext)def.lhsOcc).bundle != null) {
//					rhsocc.setBundleName(SrcLinkBundle.PREFIX_TAG + generateNewLinkName());
//					// add to names;
//				}
//				rhsocc.def = def;
//				def.rhsOccs.add(rhsocc);
			}
		}
	}
}

////////////////////////////////////////////////////////////////
//
// 構文的な書き換えを行うメソッドを保持するクラス
//

class SyntaxExpander {
	private LMNParser parser;
	private HashSet<String> TopAtomNameSet = new HashSet<>();
	SyntaxExpander(LMNParser parser) {
		this.parser = parser;
	}	
	
	////////////////////////////////////////////////////////////////
	//
	// 略記法の展開
	//

	/** ルール構文に対して略記法の展開を行う */
	void expandRuleAbbreviations(SrcRule sRule) throws ParseException {

		// ガードを型制約と否定条件に分類する
		flatten(sRule.getGuard());
		ListIterator lit = sRule.getGuard().listIterator();
		while (lit.hasNext()) {
			Object obj = lit.next();
			if (obj instanceof SrcAtom) {
				SrcAtom sAtom = (SrcAtom)obj;
				if (sAtom.getName().equals("\\+") && sAtom.getProcess().size() == 1) {
					lit.remove();
					sRule.getGuardNegatives().add(sAtom.getProcess().getFirst());
				}
			}
		}
		LinkedList typeConstraints = sRule.getGuard();
		LinkedList guardNegatives  = sRule.getGuardNegatives();

		// - ガード否定条件の根本的な構文エラーを訂正し、各否定条件を[$p,[Q]]のリストという中間表現に変換する
		correctGuardNegatives(guardNegatives);
		
		// - 数値の正負号の取り込み
		incorporateSignSymbols(sRule.getHead());
		incorporateSignSymbols(typeConstraints);
		incorporateSignSymbols(guardNegatives);
		incorporateSignSymbols(sRule.getBody());
		
		// - モジュール名のアトムファンクタへの取り込み
//		incorporateModuleNames(sRule.getHead());
//		incorporateModuleNames(typeConstraints);
//		incorporateModuleNames(guardNegatives);
//		incorporateModuleNames(sRule.getBody());
		
		// - 型制約の冗長な = を除去する
		shrinkUnificationConstraints(typeConstraints);
		
		// - アトム展開（アトム引数の再帰的な展開）
		expandAtoms(sRule.getHead());
		expandAtoms(guardNegatives);
		expandAtoms(sRule.getBody());

		// - 左辺と右辺の＠指定を処理する
		correctPragma(typeConstraints, sRule.getHead(), "string");
		correctPragma(typeConstraints, sRule.getBody(), "connectRuntime");

		// - アトム展開（ガード型制約）
		expandAtoms(typeConstraints);
		
		// - 型制約の構文エラーを訂正し、アトム引数にリンクかプロセス文脈のみが存在するようにする
		correctTypeConstraints(typeConstraints);
		
		// - 型制約に出現するリンク名Xに対して、ルール内の全てのXを$Xに置換する
		HashMap typedLinkNameMap = computeTypedLinkNameMap(typeConstraints);//" X"->"X"
		unabbreviateTypedLinks(sRule.getHead(), typedLinkNameMap);
		unabbreviateTypedLinks(typeConstraints, typedLinkNameMap);
		unabbreviateTypedLinks(guardNegatives,  typedLinkNameMap);
		unabbreviateTypedLinks(sRule.getBody(), typedLinkNameMap);

		// - 構造代入
		// 左辺に2回以上$pが出現した場合に、新しい名前$qにして $p=$qを型制約に追加する
		// todo 実装する
		// // 実装しました。便宜上、"同名型付きプロセス文脈の分離"と呼んでいます
		// // 現状では、-hl系を指定した場合のみ使用可能 (10/09/29 seiji)
		HashMap ruleProcNameMap = new HashMap();
		if (Env.hyperLink) 
  		sameTypedProcessContext(sRule.getHead(), typeConstraints, ruleProcNameMap);

		// - 構造比較
		// 型制約の同じアトムに2回以上$pが出現した場合に、新しい名前$qにして $p==$qを型制約に追加する
		// これは廃止。

		// - 基底項
		// 型制約に出現せず、Bodyでの出現が1回でない$pに対してガードにground($p)を追加する
		// todo 実装する
		
		// - アトム引数にプロセス文脈が書ける構文（いわゆる「型付きプロセス文脈構文」）の展開
		// todo $pを強制的に$p[X]に展開すると$p[X|*V]に展開できる可能性を制限しているのを何とかする
		expandTypedProcessContexts(sRule.getHead());
		expandTypedProcessContexts(typeConstraints);
		expandTypedProcessContexts(guardNegatives);
		expandTypedProcessContexts(sRule.getBody());
		
		// - 中間命令findproccxt用の処理(--hl, --hl-opt限定) //seiji
		if (Env.hyperLink && Env.hyperLinkOpt)
		  procCxtNameToLinkName(sRule.getHead(), typeConstraints, ruleProcNameMap);
		
		// 終わると：
		// - ガード否定条件は[$p,[Q]]のリストという中間表現に変換されている
		// - 数値の正負号の取り込まれている ( -(3) -> -3 )
		// - ガード型制約のアトム展開
		// -- 型制約の冗長な = を除去する
		// - アトム展開（アトム引数の再帰的な展開）
		// - 左辺と右辺の＠指定を処理する ( pragmaAtHostがnullかSrcProcessContextになり、必要に応じて  getruntime と connectruntime が追加される )
		// - ガードのアトム引数にプロセス文脈へのリンクのみが存在するようになっている
		// - リンク文字を使って表されていた型付きプロセス文脈が$pに置換されている
		// - アトムの引数は全てリンクになっている ( ? ) -> なっていない ( @p, ルール等 )
		// - 同名型付きプロセス文脈の分離 ( a($p), a($p) :- ... → a($p), a($q) :- $p = $q | ... )
		
	}

	/** ガード否定条件の根本的な構文エラーを訂正し、各否定条件を[$p,[Q]]のリストという中間形式に変換する。
	 *  この中間形式は、アトム展開などを透過的に行うために採用された。*/
	private void correctGuardNegatives(LinkedList guardNegatives) {
		ListIterator lit = guardNegatives.listIterator();
		while (lit.hasNext()) {
			Object obj = lit.next();
			LinkedList eqlist;
			// \+の引数をリストに再構成する
			if (obj instanceof LinkedList) {
				eqlist = (LinkedList)obj;
				flatten(eqlist);
			}
			else {
				eqlist = new LinkedList();
				eqlist.add(obj);
			}
			lit.remove();
			lit.add(eqlist);
			// リストの要素のうち、$p=Q のみを[$p,[Q]]として残す。
			ListIterator lit2 = eqlist.listIterator();
			while (lit2.hasNext()) {
				Object obj2 = lit2.next();
				lit2.remove();
				if (obj2 instanceof SrcAtom) {
					SrcAtom sAtom = (SrcAtom)obj2;
					if (sAtom.getName().equals("=") && sAtom.getProcess().size() == 2) {
						Object lhs = sAtom.getProcess().getFirst();
						if (lhs instanceof SrcProcessContext) {
							if (((SrcProcessContext)lhs).args != null) {
								warning("WARNING: argument of constrained process context is ignored: "
									+ SrcDumper.dump(lhs).replaceAll("\n",""));
								((SrcProcessContext)lhs).args = null;
							}
							Object rhs = sAtom.getProcess().get(1);
							LinkedList list = new LinkedList();
							LinkedList rhslist = new LinkedList();
							list.add(lhs);
							list.add(rhslist);
							rhslist.add(rhs);
							lit2.add(list);
							continue;
						}
					}
				}
				error("SYNTAX ERROR: process context equation expected rather than: "
					+ SrcDumper.dump(obj2).replaceAll("\n",""));
			}
		}
	}

	/** プロセス構造（子ルール外）に出現する正負号を数値アトムに取り込む。
	 * <pre>
	 * '+'(x) → '+x'
	 * '-'(x) → '-x'
	 * </pre>
	 */
	void incorporateSignSymbols(LinkedList process) {
		ListIterator it = process.listIterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof SrcAtom) {
				SrcAtom atom = (SrcAtom)obj;
				if (atom.getProcess().size() == 1
				 && (atom.getName().equals("+") || atom.getName().equals("-"))
				 && atom.getProcess().get(0) instanceof SrcAtom) {
					SrcAtom inneratom = (SrcAtom)atom.getProcess().get(0);
					if (inneratom.getProcess().size() == 0
					 && inneratom.getName().matches("([0-9]+|[0-9]*\\.[0-9]*)([Ee][+-]?[0-9]+)?")) {
						it.remove();
						it.add(new SrcAtom( atom.getName()
							+ ((SrcAtom)atom.getProcess().get(0)).getName() ));
					}
				}
				incorporateSignSymbols(atom.getProcess());
			}
			else if (obj instanceof SrcMembrane) {
				incorporateSignSymbols(((SrcMembrane)obj).getProcess());
			}
			else if (obj instanceof LinkedList) {
				incorporateSignSymbols((LinkedList)obj);
			}
		}
	}
//	/** プロセス構造（子ルール外）に出現するモジュール名をファンクタに取り込む。
//	 * <pre>
//	 * ':'(m,p(t1..tn)) → 'm.p'(t1..tn)
//	 * </pre>
//	 */
//	void incorporateModuleNames(LinkedList process) {
//		ListIterator it = process.listIterator();
//		while (it.hasNext()) {
//			Object obj = it.next();
//			if (obj instanceof SrcAtom) {
//				SrcAtom atom = (SrcAtom)obj;
//				if (atom.getProcess().size() == 2
//				 && atom.getName().equals(":")
//				 && atom.getProcess().get(0) instanceof SrcAtom
//				 && atom.getProcess().get(1) instanceof SrcAtom ) {
//					SrcAtom pathatom = (SrcAtom)atom.getProcess().get(0);
//					SrcAtom bodyatom = (SrcAtom)atom.getProcess().get(1);
//					if (pathatom.getProcess().size() == 0
//					 && pathatom.getNameType() == SrcName.PLAIN) {
//						it.remove();
//						it.add(bodyatom);
//						bodyatom.srcname = new SrcName(pathatom.getName() + "." + bodyatom.getName(), SrcName.PATHED);
//						incorporateModuleNames(bodyatom.getProcess());
//						continue;
//					}
//				}
//				incorporateModuleNames(atom.getProcess());
//			}
//			else if (obj instanceof SrcMembrane) {
//				incorporateModuleNames(((SrcMembrane)obj).getProcess());
//			}
//			else if (obj instanceof LinkedList) {
//				incorporateModuleNames((LinkedList)obj);
//			}
//		}
//	}
	/** （ガード型制約の）プロセス構造のトップレベルに出現する冗長な = を除去する。
	 * <pre>
	 * $p = f(t1..tn) → f(t1..tn,$p)
	 * f(t1..tn) = $p → f(t1..tn,$p)
	 * </pre>
	 */
	private void shrinkUnificationConstraints(LinkedList process) {
		ListIterator it = process.listIterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof SrcAtom) {
				SrcAtom atom = (SrcAtom)obj;
				if (!atom.getName().equals("=")) continue;
				if (atom.getProcess().size() != 2) continue;
				for (int atomarg = 0; atomarg < 2; atomarg++) {
					if (!(atom.getProcess().get(1 - atomarg) instanceof SrcProcessContext)) continue;
					if (!(atom.getProcess().get(atomarg) instanceof SrcAtom)) continue;
					SrcAtom subatom = (SrcAtom)atom.getProcess().get(atomarg);
					it.remove();
					it.add(subatom);
					subatom.getProcess().add(atom.getProcess().get(1 - atomarg));
					break;
				}
			}
		}
	}
	/** プロセス構造のルートからたどれる範囲のリストを再帰的に展開する。
	 * <pre>
	 * (t1,,tn) → t1,,tn
	 * </pre>
	 */
	private void flatten(LinkedList process) {
		LinkedList srcprocess = (LinkedList)process.clone();
		process.clear();
		ListIterator it = srcprocess.listIterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof LinkedList) {
				LinkedList list = (LinkedList)obj;
				flatten(list);
				process.addAll(list);
			}
			else process.add(obj);
		}
	}
	/** プロセス構造（子ルール外）をアトム展開する。
	 * すなわち、アトム引数に出現する全てのアトム構造と膜構造を再帰的に展開する。
	 * <pre>
	 * f(s1,g(t1,,tn),sm) → f(s1,X,sm), g(t1,,tn,X)
	 * f(s1, {t1,,tn},sm) → f(s1,X,sm), {+X,t1,,tm}
	 * f(s1, (t1,,tn),sm) → f(s1,X,sm), ','(t1,(t2,,tn),X)
	 * </pre>
	 */
	void expandAtoms(LinkedList process) {
		LinkedList srcprocess = (LinkedList)process.clone();
		process.clear();
		ListIterator it = srcprocess.listIterator();
		while (it.hasNext()) {
			Object obj = it.next();
			process.add(obj);
			if (obj instanceof SrcAtom) {
				expandAtom((SrcAtom)obj, process);
			}
			else if (obj instanceof SrcMembrane) {
				expandAtoms(((SrcMembrane)obj).getProcess());
			}
			else if (obj instanceof LinkedList) {
				expandAtoms((LinkedList)obj);
			}
		}
	}
	/** アトムの各引数に対してアトム展開を行う。
	 * @param sAtom アトム展開するアトム。戻るときには破壊される。
	 * @param result アトム展開結果のオブジェクト列を追加するリストオブジェクト（プロセス構造）
	 */
	private void expandAtom(SrcAtom sAtom, LinkedList result) {
		LinkedList<SrcAbstract> process = sAtom.getProcess();
		for (int i = 0; i < process.size(); i++) {
			SrcAbstract obj = process.get(i);
			// アトム
			if (obj instanceof SrcAtom) {
				SrcAtom subatom = (SrcAtom)obj;
				//
				String newlinkname = generateNewLinkName();
				process.set(i, new SrcLink(newlinkname));
				subatom.getProcess().add(new SrcLink(newlinkname));
				//
				result.add(subatom);
				expandAtom(subatom, result);
			}
			// 膜
			else if (obj instanceof SrcMembrane) {
				SrcMembrane submem = (SrcMembrane)obj;
				SrcAtom subatom = new SrcAtom("+");
				//
				String newlinkname = generateNewLinkName();
				process.set(i, new SrcLink(newlinkname));
				subatom.getProcess().add(new SrcLink(newlinkname));
				//
				result.add(submem);
				submem.getProcess().add(subatom);
				expandAtoms(submem.getProcess());
			}
			// 項組（仮）
			else if (obj instanceof SrcProcessList) {
				 LinkedList<SrcAbstract> list = ((SrcProcessList)obj).list;
				 if (list.isEmpty()) {				
					 SrcAtom subatom = new SrcAtom("()");
					 //
					 String newlinkname = generateNewLinkName();
					 process.set(i, new SrcLink(newlinkname));
					 subatom.getProcess().add(new SrcLink(newlinkname));
					 //
					 expandAtom(subatom, result);
					 result.add(subatom);
				 }
				 else {
					 SrcAtom subatom = new SrcAtom(",");
					 //
					 String newlinkname = generateNewLinkName();
					 process.set(i, new SrcLink(newlinkname));
					 subatom.getProcess().add(list.removeFirst());
					 if (list.size() == 1) {
						 subatom.getProcess().add(list.getFirst());
					 }
					 else {
						 subatom.getProcess().add((SrcProcessList)obj);
					 }
					 subatom.getProcess().add(new SrcLink(newlinkname));
					 //
					 result.add(subatom);
					 expandAtom(subatom, result);
				 }
			 }
		}
	}
	/** アトム展開後のプロセス構造（子ルール外）に出現するリンク名およびコンテキスト名を枚挙する。
	 * @param names 限定名 (String) からコンテキスト出現のLinkedListへの写像 [in,out] */
	private void enumNames(LinkedList process, HashMap names) {
		Iterator it = process.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			// アトム
			if (obj instanceof SrcAtom) {
				SrcAtom sAtom = (SrcAtom)obj;
				for (int i = 0; i < sAtom.getProcess().size(); i++) {
					Object subobj = sAtom.getProcess().get(i);
					if (subobj instanceof SrcContext) {
						addNameOccurrence((SrcContext)subobj, names);
					}
				}
			}
			// 膜
			else if (obj instanceof SrcMembrane) {
				enumNames(((SrcMembrane)obj).getProcess(), names);
			}
			// プロセス文脈、ルール文脈
			else if (obj instanceof SrcProcessContext || obj instanceof SrcRuleContext) {
				addNameOccurrence((SrcContext)obj, names);
			}
		}
	}
	private void addNameOccurrence(SrcContext sContext, HashMap names) {
		String name = sContext.getQualifiedName();
		if (!names.containsKey(name)) {
			names.put(name, new LinkedList());
		}
		((LinkedList)names.get(name)).add(sContext);
	}
		
	/** unabbreviateTypedLinksで使うための写像を生成する。
	 * @return 型付きリンクの限定名 " X" (String) から、
	 * 対応する型付きプロセス文脈名テキスト "X" (String) への写像
	 * <p>todo もはや不要。単にリンク名テキスト "X" から生成するように修正すべきである。
	 */
	HashMap computeTypedLinkNameMap(LinkedList typeConstraints) {	
		HashMap typedLinkNameMap = new HashMap();
		HashMap typedNames = new HashMap();
		enumNames(typeConstraints, typedNames);
		Iterator it = typedNames.keySet().iterator();
		while (it.hasNext()) {
			String name = (String)it.next();
			Object obj = ((LinkedList)typedNames.get(name)).getFirst();
			if (obj instanceof SrcLink) {
				typedLinkNameMap.put(name, ((SrcLink)obj).getName());
			}
		}
		return typedLinkNameMap;
	}

	/** アトム展開後のプロセス構造（子ルール外）に出現する全てのtypedLinkNameMap内のリンク名を
	 * プロセス文脈構文に置換する。
	 * @param typedLinkNameMap 型付きリンクの限定名 " X" (String) から、
	 * 対応する型付きプロセス文脈名テキスト "X" (String) への写像
	 * <pre> p(s1,X,sn) → p(s1,$X,sn)
	 * </pre>*/
	private void unabbreviateTypedLinks(LinkedList<SrcAbstract> process, HashMap<String,String> typedLinkNameMap) {
		Iterator<SrcAbstract> it = process.iterator();
		while (it.hasNext()) {
			SrcAbstract obj = it.next();
			if (obj instanceof SrcAtom) {
				SrcAtom sAtom = (SrcAtom)obj;
				for (int i = 0; i < sAtom.getProcess().size(); i++) {
					SrcAbstract subobj = sAtom.getProcess().get(i);
					if (subobj instanceof SrcLink) {
						SrcLink srcLink = (SrcLink)subobj;
						String name = srcLink.getQualifiedName();
						if (typedLinkNameMap.containsKey(name)) {
							sAtom.getProcess().set(i,
								new SrcProcessContext((String)typedLinkNameMap.get(name),true));
						}
					}
				}
			}
			else if (obj instanceof SrcMembrane) {
				// {..}@H → {..}@($h) //（仮）
				SrcMembrane sMem = (SrcMembrane)obj;
				if (sMem.pragma instanceof SrcLink) {
					SrcLink srcLink = (SrcLink)sMem.pragma;
					String name = srcLink.getQualifiedName();
					if (typedLinkNameMap.containsKey(name)) {
						sMem.pragma = new SrcProcessContext((String)typedLinkNameMap.get(name),true);
					}
				}
				unabbreviateTypedLinks(sMem.getProcess(), typedLinkNameMap);
			}
			else if (obj instanceof SrcProcessList){
				unabbreviateTypedLinks(((SrcProcessList)obj).list, typedLinkNameMap);
			}
		}
	}
	
	/** 同名型付きプロセス文脈の分離：
	 *  　左辺に2回以上$pが出現した場合に、新しい名前$qにして $p=$qを型制約に追加する
	 * <pre> a($p), a($p) :- ... → a($p), a($q) :- $p = $q | ...
	 * </pre>
	 */
	private void sameTypedProcessContext(LinkedList<SrcAbstract> head, LinkedList<SrcAbstract> cons, HashMap<String,SrcProcessContext> ruleProcNameMap) {//seiji

		HashMap usedProcNameMap = new HashMap(); // 既に出現しているプロセス文脈名
		int j = 0;
		
		/* ガード制約で出現するプロセス文脈の名前表を作成 */
		processContextNameMap(head, ruleProcNameMap);
		processContextNameMap(cons, ruleProcNameMap);

		/* ヘッドに同名のプロセス文脈が出現する場合には、ユニークな名前に変更する */
		separateProcessContext(head, cons, ruleProcNameMap, usedProcNameMap);

	}
	/** 型付きプロセス文脈の名前表を作成する */ //seiji
	private void processContextNameMap(LinkedList<SrcAbstract> list, HashMap<String, SrcProcessContext> ruleProcNameMap) {
		ListIterator<SrcAbstract> it = list.listIterator();
		while (it.hasNext()) {
			SrcAbstract obj = it.next();
			if (obj instanceof SrcAtom) {
				SrcAtom sAtom = (SrcAtom)obj;
				for (int i = 0; i < sAtom.getProcess().size(); i++) {
					SrcAbstract subobj = sAtom.getProcess().get(i);
					if (subobj instanceof SrcProcessContext) {
						SrcProcessContext srcProcessContext = (SrcProcessContext)subobj;
						String name = srcProcessContext.getName();
						if (!ruleProcNameMap.containsKey(name))
							ruleProcNameMap.put(name, srcProcessContext);
					}
				}
			} else if (obj instanceof SrcMembrane) {
				SrcMembrane sMem = (SrcMembrane)obj;
				processContextNameMap(sMem.getProcess(), ruleProcNameMap);
			}
		}
	}
	/** ヘッドに同名のプロセス文脈が出現する場合には、ユニークな名前に変更する */
	private void separateProcessContext(LinkedList head, LinkedList cons, 
			HashMap ruleProcNameMap, HashMap usedProcNameMap) {//seiji
		ListIterator it = head.listIterator();
		int j = 0;
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof SrcAtom) {
				SrcAtom sAtom = (SrcAtom)obj;
				for (int i = 0; i < sAtom.getProcess().size(); i++) {
					Object subobj = sAtom.getProcess().get(i);
					if (subobj instanceof SrcProcessContext) {
						SrcProcessContext srcProcessContext = (SrcProcessContext)subobj;
						String name = srcProcessContext.getName();
						if (usedProcNameMap.containsKey(name)) {
							
							/* name の後ろに数字をつけることで、新しい名前とする */
							String newName = name + j;
							while (ruleProcNameMap.containsKey((newName))) {
								j++;
								newName = name + j;
							}
							srcProcessContext.name = newName;
							usedProcNameMap.put(newName, srcProcessContext);
							ruleProcNameMap.put(newName, srcProcessContext);
							
							LinkedList<SrcAbstract> procList = new LinkedList();
							procList.add(new SrcProcessContext(name));
							procList.add(new SrcProcessContext(newName));
							SrcAtom sa = new SrcAtom("==", procList);
							cons.add(sa);
	            
				            /* --hl-optではガードにhlink型チェックを追加して、構造比較にかかる時間を短縮している */
				            if (Env.hyperLinkOpt) {
	  							LinkedList<SrcAbstract> procList2 = new LinkedList<>();
	  							procList2.add(new SrcProcessContext(name));
	  							SrcAtom sa2 = new SrcAtom("unary", procList2);
	  							cons.add(sa2);
	  							LinkedList<SrcAbstract> procList3 = new LinkedList<>();
	  							procList3.add(new SrcProcessContext(newName));
	  							SrcAtom sa3 = new SrcAtom("unary", procList3);
	  							cons.add(sa3);
				            }

							/* オリジナルの名前を持つ型付きプロセス文脈に、新しい名前を記憶させる */
							SrcProcessContext oriProcessContext = (SrcProcessContext)ruleProcNameMap.get(name);
							if (oriProcessContext.getSameNameList() == null) {
								oriProcessContext.sameNameList = new LinkedList();
							}
							oriProcessContext.getSameNameList().add(newName);
							
						}else{
							usedProcNameMap.put(name, srcProcessContext);
						}
					}
				}
			} else if (obj instanceof SrcMembrane) {
				SrcMembrane sMem = (SrcMembrane)obj;
				separateProcessContext(sMem.getProcess(), cons, ruleProcNameMap, usedProcNameMap);
			}
		}
	}
	
	/** アトム展開後のプロセス構造（子ルール外）のアトム引数に出現するプロセス文脈を展開する。
	 * <pre> p(s1,$p,sn) → p(s1,X,sn), $p[X]
	 * </pre>
	 * <p>メソッドの名前とは異なり、型付きでないプロセス文脈も展開する仕様になっている。
	 * <p>todo $p[X|*p] に展開すべき場合もあるはず
	 */
	private void expandTypedProcessContexts(LinkedList process) {
		ListIterator it = process.listIterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof SrcAtom) {
				SrcAtom sAtom = (SrcAtom)obj;
				for (int i = 0; i < sAtom.getProcess().size(); i++) {
					Object subobj = sAtom.getProcess().get(i);
					if (subobj instanceof SrcProcessContext) {
						SrcProcessContext srcProcessContext = (SrcProcessContext)subobj;
						String name = srcProcessContext.getQualifiedName();
						String newlinkname = generateNewLinkName();
						sAtom.getProcess().set(i, new SrcLink(newlinkname));
						srcProcessContext.linkName = newlinkname;//seiji
						it.add(srcProcessContext);
						// アトム引数に$p[...]を許すように構文拡張された場合のみ args!=null となる
						if (srcProcessContext.args == null)
							srcProcessContext.args = new LinkedList();
						srcProcessContext.args.add(new SrcLink(newlinkname));
					}
				}
			}
			else if (obj instanceof SrcMembrane) {
				expandTypedProcessContexts(((SrcMembrane)obj).getProcess());
			}
		}
	}
	/** 中間命令findproccxt用の処理(--hl, --hl-opt限定)
	 * プロセス文脈名($p, $q, ...)から
	 * 内部的なプロセス文脈名(~5, ~6, ...)に変換し、
	 * SrcProcessContextに保持させる
	 * */
	private void procCxtNameToLinkName(LinkedList head, LinkedList cons, HashMap ruleProcNameMap) {//seiji
		ListIterator it = head.listIterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof SrcProcessContext) {
				SrcProcessContext spc = (SrcProcessContext)obj;
				if (spc.hasSameNameList()) {
					LinkedList<String> temp = new LinkedList<>();
					SrcProcessContext subspc;
					for (int j = 0; j < spc.getSameNameList().size(); j++) {
						subspc = (SrcProcessContext)ruleProcNameMap.get(spc.getSameNameList().get(j));
						temp.add(subspc.linkName);
					}
					spc.getSameNameList().clear();
					for (int j = 0; j < temp.size(); j++)
						spc.getSameNameList().add(temp.get(j));

				}
			} else if (obj instanceof SrcMembrane) {
				SrcMembrane sMem = (SrcMembrane)obj;
				procCxtNameToLinkName(sMem.getProcess(), cons, ruleProcNameMap);
			}
		}
	}
	
	/* アトム展開後のプロセス構造（子ルール外）に出現する型付きプロセス文脈にtypedマークを行う。
	 * @param typedNames 型付きプロセス文脈の限定名 "$p" (String) をキーとする写像
	 * <pre> $p[X] → $p[X]
	 * </pre> *
	private void markAsTyped(LinkedList process, HashMap typedNames) {
		ListIterator it = process.listIterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof SrcProcessContext) {
				SrcProcessContext sProc = (SrcProcessContext)obj;
				if (typedNames.containsKey(sProc.getQualifiedName())) {
					sProc.typed = true;
				}
			}
			else if (obj instanceof SrcMembrane) {
				markAsTyped(((SrcMembrane)obj).getProcess(), typedNames);
			}
		}
	}*/

	////////////////////////////////////////////////////////////////
	//
	// ＠指定を処理するメソッド
	//
	
	/** アトム展開後のプロセス構造に対して、
	 * (1) {..}@"hostname"を{..}@Hに置換し、ガード型制約 H="hostname" を追加する。
	 * (2) {..}@Hに対して、ガード型制約 cmd(H) を追加する。
	 * <p>左辺の場合のpragmaフィールドへの登録は、addProcessToMemで行う。
	 * @param cmd 右辺ならば"connectRuntime"を、左辺ならば"string"を渡すこと。(2)で使用される。*/
	void correctPragma(LinkedList typeConstraints, LinkedList process, String cmd) {
		Iterator it = process.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof SrcMembrane) {
				SrcMembrane sMem = (SrcMembrane)obj;
				correctPragma(typeConstraints, sMem.process, cmd);
				if (sMem.pragma instanceof SrcAtom) {
					SrcAtom sAtom = (SrcAtom)sMem.pragma;
					// (1) | {..}@"hostname" → "hostname"($h) | {..}@($h)
					if (sAtom.getProcess().size() == 0 && sAtom.getNameType() == SrcName.STRING) {
						String cxtname = generateNewProcessContextName();
						sMem.pragma = new SrcProcessContext(cxtname);
						SrcAtom eq = new SrcAtom(sAtom.srcname);
						eq.getProcess().add(new SrcProcessContext(cxtname));
						typeConstraints.add(eq);						
					}
				}
				if (sMem.pragma instanceof SrcLink) {
					// (2) | {..}@H → cmd(H) | {..}@H
					LinkedList args = new LinkedList();
					args.add(new SrcLink(((SrcLink)sMem.pragma).getName()));
					typeConstraints.add(new SrcAtom(cmd,args));
					continue;
				}
				if (sMem.pragma instanceof SrcProcessContext) {
					SrcProcessContext sProcCxt = (SrcProcessContext)sMem.pragma;
					if (sProcCxt.args == null && sProcCxt.bundle == null) {
						// (2) | {..}@($h) → cmd($h) | {..}@($h)
						LinkedList args = new LinkedList();
						args.add(new SrcProcessContext(sProcCxt.getName()));
						typeConstraints.add(new SrcAtom(cmd,args));
						continue;
					}
				}
				if (sMem.pragma == null) continue;
				error("SYNTAX ERROR: illegal pragma, ignored: " + sMem.pragma);
				sMem.pragma = null;
			}
		}
	}

	////////////////////////////////////////////////////////////////
	//
	// 構文エラー検出および復帰を行うメソッド
	//
	
	/** アトム展開後のプロセス構造（ガードの型制約）に対して、
	 * 膜やルール文脈やルールやリンク束やトップレベルのプロセス文脈が存在したら
	 * コンパイルエラーとする。アトム引数での出現は無名のプロセス変数で置換する。*/
	private void correctTypeConstraints(LinkedList process) {
		Iterator it = process.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof SrcAtom) {
				SrcAtom sAtom = (SrcAtom)obj;
				for (int i = 0; i < sAtom.getProcess().size(); i++) {
					Object subobj = sAtom.getProcess().get(i);
					if (subobj instanceof SrcLink) {}
					else if (subobj instanceof SrcProcessContext) {}
					else {
						String proccxtname = generateNewProcessContextName();
						sAtom.getProcess().set(i, new SrcProcessContext(proccxtname, true));
						error("SYNTAX ERROR: illegal object in guard atom argument: " + subobj + " at line " + sAtom.line);
					}
				}
			}
			else {
				error("SYNTAX ERROR: illegal object in guard: " + obj);
				it.remove();
			}
		}
	}

	/** アトム展開後のプロセス構造（ソースファイル）（ルール外）に対して、
	 * プロセス文脈やルール文脈やリンク束が出現したらコンパイルエラーとする。
	 * アトム引数での出現は無名のリンクで置換する。*/
	void correctWorld(LinkedList process) {
		Iterator it = process.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof LinkedList) {
				LinkedList list = (LinkedList)obj;
				correctWorld(list);				
			}
			else if (obj instanceof SrcAtom) {
				SrcAtom sAtom = (SrcAtom)obj;
				for (int i = 0; i < sAtom.getProcess().size(); i++) {
					Object subobj = sAtom.getProcess().get(i);
					if (subobj instanceof SrcLinkBundle) {
						String linkname = generateNewLinkName();
						sAtom.getProcess().set(i, new SrcLink(linkname));
						error("SYNTAX ERROR: illegal link bundle: " + subobj);
					}
					else if (subobj instanceof SrcLink) {}
					else {
						String linkname = generateNewLinkName();
						sAtom.getProcess().set(i, new SrcLink(linkname));
						error("SYNTAX ERROR: illegal object in an atom argument: " + subobj);
					}
				}
			}
			else if (obj instanceof SrcMembrane) {
				SrcMembrane sMem = (SrcMembrane)obj;
				if (sMem.pragma != null) {
					warning("FEATURE NOT IMPLEMENTED: pragma outside rule, ignored: " + sMem.pragma);
					sMem.pragma = null;
				}
				correctWorld(sMem.getProcess());
			}
			else if (obj instanceof SrcRule) {}
			else if (obj instanceof SrcLink) {
				SrcLink link = (SrcLink)obj;
				error("SYNTAX ERROR: top-level variable occurrence: " + link.getName()+", at line "+link.lineno);
				it.remove();
			}
			else if (obj instanceof SrcContext) {
				error("SYNTAX ERROR: process/rule context must occur in a rule: " + obj);
				it.remove();
			}
			else {
				error("SYNTAX ERROR: illegal object outside a rule: " + obj);
				it.remove();
			}
		}
	}
	private void error(String text) {
		parser.error(text);
	}
	private void warning(String text) {
		parser.warning(text);
	}
	/** ユニークな新しいリンク名を生成する */
	private String generateNewLinkName() {
		return parser.generateNewLinkName();
	}
	/** ユニークな新しいプロセス文脈名を生成する */
	private String generateNewProcessContextName() {
		return parser.generateNewProcessContextName();
	}
	
	HashSet<String> getTopAtomNameSet(){
		return TopAtomNameSet;
	}
	
	private void addToTopAtomNameSet(String name){
		if(
				name.matches("[\\w]*") 
				 && !name.matches("^[0-9]")
				 && !name.equals("int")
				 && !name.equals("unary")
				 && !name.equals("ground")
				 && !name.equals("uniq")
				)
		TopAtomNameSet.add(name);
	}
}
