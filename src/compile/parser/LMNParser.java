/**
 * LMNtal Parser メインクラス
 * １つのソースコードはMembraneとして表現されます。
 */

package compile.parser;

import java_cup.runtime.Scanner;
import java.io.Reader;
//import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

import runtime.Functor;
//import runtime.Env;
import compile.structure.*;

public class LMNParser {

	private static final String            LINK_NAME_PREFIX = "~"; //         [A-Za-z0-9_]* 以外
	private static final String      PROXY_LINK_NAME_PREFIX = "^"; //   [A-Z_][A-Za-z0-9_]* 以外
	private static final String PROCESS_CONTEXT_NAME_PREFIX = "_"; // [a-z0-9][A-Za-z0-9_]* 以外
	static final LinkOccurrence CLOSED_LINK = new LinkOccurrence("",null,0);

	private int nLinkNumber = 0;
	private Scanner lex = null;
	
	private int nErrors = 0;
	private int nWarnings = 0;
	
	private SyntaxExpander expander = new SyntaxExpander(this);
	
	public void corrupted() throws ParseException {
		error("SYSTEM ERROR: error recovery for the previous error is not implemented");
		throw new ParseException("Rule compilation aborted");
	}
	public void error(String text) {
		System.out.println(text);
		nErrors++;
	}
	public void warning(String text) {
		System.out.println(text);
		nWarnings++;
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
	 * @return ソースファイル全体が表すプロセス構造が入った膜構造
	 * @throws ParseException
	 */
	public Membrane parse() throws ParseException {
		LinkedList srcProcess = parseSrc();
		Membrane mem = new Membrane(null);
		expander.incorporateSignSymbols(srcProcess);
		expander.incorporateModuleNames(srcProcess);
		expander.expandAtoms(srcProcess);
		expander.correctWorld(srcProcess);
		addProcessToMem(srcProcess, mem);
		HashMap freeLinks = addProxies(mem);
		if (!freeLinks.isEmpty()) closeFreeLinks(mem);
		return mem;
	}
	
	/**	
		解析の結果を LinkedList とする解析木として返します。
		@return 解析されたソースコードのリスト
		@throws ParseException 
	*/
	protected LinkedList parseSrc() { // throws ParseException {
		parser p = new parser(lex);
		LinkedList result = null;
		try {
			result = (LinkedList)p.parse().value;
		} catch (Exception e) {
//			throw new ParseException(e.getMessage()+" "+runtime.Env.parray(java.util.Arrays.asList(e.getStackTrace()), "\n"));	
//			error("PARSE ERROR: " + p.error_sym());
			result = new LinkedList();
		} catch (Error e) {
			error("ERROR: " + e.getMessage());
			result = new LinkedList();
		}
		return result;
	}

	////////////////////////////////////////////////////////////////

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
	private void setLinkToAtomArg(SrcLink link, Atom atom, int pos) {
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
	void addProcessToMem(LinkedList list, Membrane mem) throws ParseException {
		Iterator it = list.iterator();
		while (it.hasNext()) {
			addObjectToMem(it.next(), mem);
		}
	}
	/**
	 * 膜にアトム、子膜、ルールなどの構文オブジェクトを追加
	 * @param obj 追加する構文オブジェクト
	 * @param mem 追加先の膜
	 */
	private void addObjectToMem(Object obj, Membrane mem) throws ParseException {
		// リスト
		if (obj instanceof LinkedList) {
			Iterator it = ((LinkedList)obj).iterator();
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
		LinkedList p = sAtom.getProcess();
		int arity = p.size();
		
		// [1] ファンクタを生成する
		// GUIからの動的な生成に対応する場合にそなえて FunctorFactory のようなものがあった方がよい。
		// runtime.*Functor の多さが、現状の不自然さを物語る。

		int nametype = sAtom.getNameType();
		String name = sAtom.getName();
		String path = null;
		if (nametype == SrcName.PATHED) {
			int pos = name.indexOf('.');
			path = name.substring(0, pos);
			name = name.substring(pos + 1);
		}
		Functor func = new runtime.Functor(name, arity, path);
		if (arity == 1 && path == null) {
			if (nametype == SrcName.PLAIN || nametype == SrcName.SYMBOL) {
				try {
					func = new runtime.IntegerFunctor(Integer.parseInt(name));
				}
				catch (NumberFormatException e) {
					try {
						func = new runtime.FloatingFunctor(Double.parseDouble(name));
					}
					catch (NumberFormatException e2) {
						//
					}
				}
			}
			else if (nametype == SrcName.STRING || nametype == SrcName.QUOTED) {
				func = new runtime.StringFunctor(name); // new runtime.ObjectFunctor(name);
			}
		}
		
		// [2] アトム構造を生成する
		Atom atom = new Atom(mem, func);
		atom.setSourceLocation(sAtom.line, sAtom.column);
		
		// [3] 引数の構造を生成する		
		for (int i = 0; i < arity; i++) {
			Object obj = p.get(i);
			// リンク
			if (obj instanceof SrcLink) {
				setLinkToAtomArg((SrcLink)obj, atom, i);
				if (obj instanceof SrcLinkBundle) { alllinks = false; }
				else { allbundles = false; }
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
			error("SYNTAX ERROR: arguments of an atom contain both links and bundles");
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
		RuleStructure rule = new RuleStructure(mem);
		// 略記法の展開		
		expander.expandRuleAbbreviations(sRule);
		// todo 左辺のルールを構文エラーとして除去する
		
		// 左辺およびガード型制約に対して、構造を生成し、リンク以外の名前を解決する
		addProcessToMem(sRule.getHead(), rule.leftMem);		
		addProcessToMem(sRule.getGuard(), rule.guardMem);
		HashMap names = resolveHeadContextNames(rule);
		// ガード否定条件および右辺に対して、構造を生成し、リンク以外の名前を解決する
		addGuardNegatives(sRule.getGuardNegatives(), rule, names);
		addProcessToMem(sRule.getBody(), rule.rightMem);
		resolveContextNames(rule, names);
		// プロキシアトムを生成し、リンクをつなぎ、膜の自由リンクリストを決定する
		addProxies(rule.leftMem);
		coupleLinks(rule.guardMem);
		addProxies(rule.rightMem);
		addProxiesToGuardNegatives(rule);
		coupleGuardNegativeLinks(rule);		// ガード否定条件のリンクを接続する
		coupleInheritedLinks(rule);			// 右辺と左辺の自由リンクを接続する
		//
		mem.rules.add(rule);
	}

	/** ガード否定条件の中間形式に対応する構造を生成する
	 *  @param sNegatives ガード否定条件の中間形式[$p,[Q]]のリスト[in]
	 *  @param rule ルール構造[in,out]
	 *  @param names 左辺およびガード型制約に出現した$p（と*X）からその定義（と出現）へのマップ[in] */
	private void addGuardNegatives(LinkedList sNegatives, RuleStructure rule, HashMap names) throws ParseException {
		Iterator it = sNegatives.iterator();
		while (it.hasNext()) {
			LinkedList neg = new LinkedList();
			ListIterator it2 = ((LinkedList)it.next()).listIterator();
			while (it2.hasNext()) {
				LinkedList sPair = (LinkedList)it2.next();
				String cxtname = ((SrcProcessContext)sPair.getFirst()).getQualifiedName();
				if (!names.containsKey(cxtname)) {
					error("SYNTAX ERROR: fresh process context constrained in a negative condition: " + cxtname);
				}
				else {
					ContextDef def = (ContextDef)names.get(cxtname);
					if (def.typed) {
						error("SYNTAX ERROR: typed process context constrained in a negative condition: " + cxtname);
					}
					else if (def.lhsOcc != null) {
						Membrane mem = new Membrane(null);
						addProcessToMem((LinkedList)sPair.getLast(),mem);
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
	private HashMap addProxies(Membrane mem) {
		HashSet proxyLinkNames = new HashSet();	// memとその子膜の間に作成した膜間リンク名の集合
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			HashMap freeLinks = addProxies(submem);
			// 子膜の自由リンクに対してプロキシを追加する
			HashMap newFreeLinks = new HashMap();
			Iterator it2 = freeLinks.keySet().iterator();
			while (it2.hasNext()) {
				LinkOccurrence freeLink = (LinkOccurrence)freeLinks.get(it2.next());
				// 子膜の自由リンク名 freeLink.name に対して、膜間リンク名 proxyLinkName を決定する。
				// 通常はXに対して、1^Xとする。
				// Xがmemの局所リンクであり、1^Xをmem内ですでに使用した場合は、1^^Xとする。
				// Xがsubmemの子膜への直通リンクであり、そこでの膜間リンク名が1^Xの場合は、2^Xとする。
				String index = "1";
				if (freeLink.atom.functor.equals(ProxyAtom.OUTSIDE_PROXY)
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
				ProxyAtom inside = new ProxyAtom(submem, ProxyAtom.INSIDE_PROXY);
				inside.args[0] = new LinkOccurrence(proxyLinkName, inside, 0); // 外側
				inside.args[1] = new LinkOccurrence(freeLink.name, inside, 1); // 内側
				inside.args[1].buddy = freeLink;
				freeLink.buddy = inside.args[1];
				submem.atoms.add(inside);
				// 新しい自由リンク名を新しい自由リンク一覧に追加する
				newFreeLinks.put(proxyLinkName, inside.args[0]);			
				// この膜にoutside_proxyを追加
				ProxyAtom outside = new ProxyAtom(mem, ProxyAtom.OUTSIDE_PROXY);
				outside.args[0] = new LinkOccurrence(proxyLinkName, outside, 0); // 内側
				outside.args[1] = new LinkOccurrence(freeLink.name, outside, 1); // 外側
				outside.args[0].buddy = inside.args[0];
				inside.args[0].buddy = outside.args[0];
				mem.atoms.add(outside);
			}
			submem.freeLinks = newFreeLinks;
		}
		return coupleLinks(mem);
	}
	/** ガード否定条件に対してaddProxiesを呼ぶ */
	private void addProxiesToGuardNegatives(RuleStructure rule) {
		Iterator it = rule.guardNegatives.iterator();
		while (it.hasNext()) {
			Iterator it2 = ((LinkedList)it.next()).iterator();
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
	private HashMap coupleLinks(Membrane mem) {
		// 同じ膜レベルのリンク結合を行う
		HashMap links = new HashMap();
		List[] lists = {mem.atoms, mem.processContexts, mem.typedProcessContexts};
		for (int i = 0; i < lists.length; i++) {
			Iterator it = lists[i].iterator();
			while (it.hasNext()) {
				Atom a = (Atom)it.next();
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

	private HashMap enumGuardNegativeLinks(RuleStructure rule ) {
//		
//		Iterator it = rule.guardNegatives.iterator();
//		while (it.hasNext()) {
//			Iterator it2 = ((LinkedList)it.next()).iterator();
//			while (it2.hasNext()) {
//				
//				eq.mem
//			}
//			removeClosedLinks(links);
//			//mem.freeLinks = links;
//		}
//		return links;
		return null;
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
	private void addLinkOccurrence(HashMap links, LinkOccurrence lnk) {
		// 3回以上の出現
		if (links.get(lnk.name) == CLOSED_LINK) {
			error("SYNTAX ERROR: link " + lnk.name + " appears more than twice.");
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
			LinkOccurrence buddy = (LinkOccurrence)links.get(lnk.name);
			lnk.buddy = buddy;
			buddy.buddy = lnk;
			links.put(lnk.name, CLOSED_LINK);
		}
	}
	
	/** 膜memの自由リンクを膜内で閉じる（構文エラーからの復帰用） */
	public void closeFreeLinks(Membrane mem) {
		Iterator it = mem.freeLinks.keySet().iterator();
		while (it.hasNext()) {
			LinkOccurrence link = (LinkOccurrence)mem.freeLinks.get(it.next());
			warning("WARNING: global singleton link: " + link.name);
			LinkedList process = new LinkedList();
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
		Iterator it = rule.guardNegatives.iterator();
		while (it.hasNext()) {
			HashMap interlinks = new HashMap();	// 等式間リンクおよびガード匿名リンクの一覧
			Iterator it2 = ((LinkedList)it.next()).iterator();
			while (it2.hasNext()) {
				ProcessContextEquation eq = (ProcessContextEquation)it2.next();
				// 等式右辺の自由リンク出現の一覧を取得する
				Membrane mem = eq.mem;
				HashMap rhsfreelinks = mem.freeLinks;
				// 等式左辺の自由リンク出現の一覧を取得し、右辺の一覧と対応を取る
				ProcessContext a = (ProcessContext)eq.def.lhsOcc;
				HashMap rhscxtfreelinks = new HashMap();	// この等式右辺トップレベル$ppの自由リンク集合
				for (int i = 0; i < a.args.length; i++) {
					LinkOccurrence lhslnk = a.args[i];
					String linkname = lhslnk.name;
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
				Iterator it3 = rhsfreelinks.keySet().iterator();
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
			Iterator it3 = interlinks.keySet().iterator();
			anonymouslink:
			while (it3.hasNext()) {
				String linkname = (String)it3.next();
				LinkOccurrence lnk = (LinkOccurrence)interlinks.get(linkname);
				if (lnk.atom.mem.processContexts.isEmpty()) {
					warning("WARNING: unsatisfiable negative condition because of free link: " + lnk.name);
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
		HashMap lhsFreeLinks = rule.leftMem.freeLinks;
		HashMap rhsFreeLinks = rule.rightMem.freeLinks;
		HashMap links = new HashMap();
		Iterator it = lhsFreeLinks.keySet().iterator();
		while (it.hasNext()) {
			String linkname = (String)it.next();
			LinkOccurrence lhsocc = (LinkOccurrence)lhsFreeLinks.get(linkname);
			addLinkOccurrence(links, lhsocc);
		}
		it = rhsFreeLinks.keySet().iterator();
		while (it.hasNext()) {
			String linkname = (String)it.next();
			LinkOccurrence rhsocc = (LinkOccurrence)rhsFreeLinks.get(linkname);
			addLinkOccurrence(links, rhsocc);
		}
		removeClosedLinks(links);
		if (!links.isEmpty()) {
			it = links.keySet().iterator();
			while (it.hasNext()) {
				LinkOccurrence link = (LinkOccurrence)links.get(it.next());
				error("SYNTAX ERROR: rule with free variable: "+ link.name);
				LinkedList process = new LinkedList();
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
	private void enumTypedNames(Membrane mem, HashMap names) {
		Iterator it = mem.processContexts.iterator();
		while (it.hasNext()) {
			ProcessContext pc = (ProcessContext)it.next();
			String name = pc.getQualifiedName();
			if (!names.containsKey(name)) {
				pc.def = new ContextDef(pc.getQualifiedName());
				pc.def.typed = true;
				names.put(name, pc.def);
			}
			else pc.def = (ContextDef)names.get(name);
			it.remove();
			mem.typedProcessContexts.add(pc);
			if (pc.bundle != null) addLinkOccurrence(names, pc.bundle);
		}
	}
	
	/** ヘッドのプロセス文脈、型付きプロセス文脈、ルール文脈、リンク束のリストを作成する。
	 * 型なしプロセス文脈の明示的な引数が互いに異なることを確認する。
	 * @param mem 左辺膜、またはガード否定条件内等式制約右辺の構造を保持する膜
	 * @param names コンテキストの限定名 (String) から ContextDef への写像 [in,out]
	 * @param isLHS 左辺かどうか（def.lhsOccに追加するかどうかの判定に使用される）*/
	private void enumHeadNames(Membrane mem, HashMap names, boolean isLHS) throws ParseException {
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
					if (pc.def.lhsOcc != null) {
						// 展開を実装すれば不要になる（ガード否定条件のときはどうしても書けないが放置）
						error("FEATURE NOT IMPLEMENTED: head contains more than one occurrence of a typed process context name: " + name);
						continue;
					}
					if (pc.args.length != 1) {
						error("SYNTAX ERROR: typed process context occurring in head must have exactly one explicit free link argument: " + pc);
						continue;
					}
					mem.typedProcessContexts.add(pc);
				}
				else {
					// 構造比較への変換を実装すれば不要になる（ガード否定条件のときはどうしても書けないが放置）
					error("FEATURE NOT IMPLEMENTED: untyped process context name appeared more than once in a head: " + name);
					continue;
				}
			}
			if (isLHS)  pc.def.lhsOcc = pc;	// 左辺での出現を登録
			if (pc.bundle != null) addLinkOccurrence(names, pc.bundle);
			//
			if (!pc.def.isTyped()) {
				HashSet explicitfreelinks = new HashSet();
				for (int i = 0; i < pc.args.length; i++) {
					LinkOccurrence lnk = pc.args[i];
					if (explicitfreelinks.contains(lnk.name)) {
						error("SYNTAX ERROR: explicit arguments of a process context in head must be pairwise disjoint: " + pc.def);
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
				error("SYNTAX ERROR: head contains more than one occurrence of a rule context: " + name);
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
			error("SYNTAX ERROR: head membrane cannot contain more than one untyped process context");
			it = mem.processContexts.iterator();
			while (it.hasNext()) {
				ProcessContext pc = (ProcessContext)it.next();
				if (pc.def.lhsOcc == pc)  pc.def.lhsOcc = null; // 左辺での出現の登録を取り消す
				it.remove(); // namesには残る
			}
		}
		if (mem.ruleContexts.size() > 1) {
			error("SYNTAX ERROR: head membrane cannot contain more than one rule context");
			while (it.hasNext()) {
				RuleContext rc = (RuleContext)it.next();
				if (rc.def.lhsOcc == rc)  rc.def.lhsOcc = null; // 左辺での出現の登録を取り消す
				it.remove(); // namesには残る
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
				error("SYNTAX ERROR: untyped process context not appeared in head: " + pc.getQualifiedName());
				it.remove();
				continue;
			}
			else {
				pc.def = (ContextDef)names.get(name);
				if (pc.def.lhsOcc != null) {
					if (pc.args.length != pc.def.lhsOcc.args.length
					 || ((pc.bundle == null) != (((ProcessContext)pc.def.lhsOcc).bundle == null)) ) {
						error("SYNTAX ERROR: unmatched length of free link list of process context: " + pc);
						it.remove();
						continue;
					}
				}
				if (pc.def.isTyped()) {
					it.remove();
					if (pc.args.length != 1) {
						error("SYNTAX ERROR: typed process context occurring in body must have exactly one explicit free link argument: " + pc);
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
				error("SYNTAX ERROR: rule context not appeared in head: " + rc);
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
	}

	/** 左辺およびガード型制約に対して、プロセス文脈およびルール文脈の名前解決を行う。
	 *  名前解決により発見された構文エラーを訂正する。
	 *  @return 左辺およびガードに出現する限定名(String) -> ContextDef / LinkOccurrence(Bundles) */
	private HashMap resolveHeadContextNames(RuleStructure rule) throws ParseException {
		HashMap names = new HashMap();
		enumTypedNames(rule.guardMem, names);
		enumHeadNames(rule.leftMem, names, true);
		// todo リンク束が左辺で閉じていないことを確認する
		// ---リンク束が2回出現したかどうかを調べればよいだけ。
		
		// 左辺トップレベルのプロセス文脈を削除する
		Iterator it = rule.leftMem.processContexts.iterator();
		while (it.hasNext()) {
			ProcessContext pc = (ProcessContext)it.next();
			error("SYNTAX ERROR: untyped head process context requires an enclosing membrane: " + pc);
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
			if (def.rhsOccs.size() == 1) {
				if (def.lhsOcc != null) {	// ガードでないとき
					Context rhsocc = ((Context)def.rhsOccs.get(0));
					rhsocc.buddy = def.lhsOcc;
					def.lhsOcc.buddy = rhsocc;
				}
			}
		}
		
		// （非線型プロセス文脈が実装されるまでの仮措置として）線型でない型なし$pを取り除く
		it = rule.processContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			if (def.rhsOccs.size() != 1) {
				error("FEATURE NOT IMPLEMENTED: untyped process context must be linear: " + def.getName());
				int len = def.lhsOcc.args.length;
				Iterator it2 = def.rhsOccs.iterator();
				while (it2.hasNext()) {
					ProcessContext pc = (ProcessContext)it2.next();
					pc.mem.processContexts.remove(pc);
					Atom atom = new Atom(pc.mem, def.getName(), len);
					for (int i = 0; i < len; i++) {
						atom.args[i] = new LinkOccurrence(pc.args[i].name, atom, i);
					}
					// if (pc.bundle != null) { remove from names; }
					pc.mem.atoms.add(atom);
					it2.remove();
				}
				ProcessContext rhsocc = new ProcessContext(rule.rightMem, def.getName(), len);
				rule.rightMem.processContexts.add(rhsocc);
				for (int i = 0; i < len; i++) {
					String linkname = generateNewLinkName();
					rhsocc.args[i] = new LinkOccurrence(linkname, rhsocc, i);
					Atom atom = new Atom(rule.rightMem, linkname, 1);
					atom.args[0] = new LinkOccurrence(linkname, atom, 0);
					rule.rightMem.atoms.add(atom);
				}
				if (((ProcessContext)def.lhsOcc).bundle != null) {
					rhsocc.setBundleName(SrcLinkBundle.PREFIX_TAG + generateNewLinkName());
					// add to names;
				}
				rhsocc.buddy = def.lhsOcc;
				def.lhsOcc.buddy = rhsocc;
				rhsocc.def = def;
				def.rhsOccs.add(rhsocc);
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
		incorporateModuleNames(sRule.getHead());
		incorporateModuleNames(typeConstraints);
		incorporateModuleNames(guardNegatives);
		incorporateModuleNames(sRule.getBody());
		
		// - 型制約の冗長な = を除去する
		shrinkUnificationConstraints(typeConstraints);
		
		// - アトム展開（アトム引数の再帰的な展開）
		expandAtoms(sRule.getHead());
		expandAtoms(typeConstraints);
		expandAtoms(guardNegatives);
		expandAtoms(sRule.getBody());

		// - 型制約の構文エラーを訂正し、アトム引数にリンクかプロセス文脈のみが存在するようにする
		correctTypeConstraints(typeConstraints);

		// - 型制約に出現するリンク名Xに対して、ルール内の全てのXを$Xに置換する
		HashMap typedLinkNameMap = computeTypedLinkNameMap(typeConstraints);
		unabbreviateTypedLinks(sRule.getHead(), typedLinkNameMap);
		unabbreviateTypedLinks(typeConstraints, typedLinkNameMap);
		unabbreviateTypedLinks(guardNegatives,  typedLinkNameMap);
		unabbreviateTypedLinks(sRule.getBody(), typedLinkNameMap);

		// - 構造代入
		// 左辺に2回以上$pが出現した場合に、新しい名前$qにして $p=$qを型制約に追加する
		// todo 実装する

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
									+ SrcDumper.dump(lhs,0).replaceAll("\n",""));
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
					+ SrcDumper.dump(obj2,0).replaceAll("\n",""));
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
	/** プロセス構造（子ルール外）に出現するモジュール名をファンクタに取り込む。
	 * <pre>
	 * ':'(m,p(t1..tn)) → 'm.p'(t1..tn)
	 * </pre>
	 */
	void incorporateModuleNames(LinkedList process) {
		ListIterator it = process.listIterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof SrcAtom) {
				SrcAtom atom = (SrcAtom)obj;
				if (atom.getProcess().size() == 2
				 && atom.getName().equals(":")
				 && atom.getProcess().get(0) instanceof SrcAtom
				 && atom.getProcess().get(1) instanceof SrcAtom ) {
					SrcAtom pathatom = (SrcAtom)atom.getProcess().get(0);
					SrcAtom bodyatom = (SrcAtom)atom.getProcess().get(1);
					if (pathatom.getProcess().size() == 0
					 && pathatom.getNameType() == SrcName.PLAIN) {
						it.remove();
						it.add(bodyatom);
						bodyatom.srcname = new SrcName(pathatom.getName() + "." + bodyatom.getName(), SrcName.PATHED);
						incorporateModuleNames(bodyatom.getProcess());
						continue;
					}
				}
				incorporateModuleNames(atom.getProcess());
			}
			else if (obj instanceof SrcMembrane) {
				incorporateModuleNames(((SrcMembrane)obj).getProcess());
			}
			else if (obj instanceof LinkedList) {
				incorporateModuleNames((LinkedList)obj);
			}
		}
	}
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
			if (obj instanceof SrcAtom) {
				expandAtom((SrcAtom)obj, process);
			}
			else if (obj instanceof SrcMembrane) {
				expandAtoms(((SrcMembrane)obj).getProcess());
			}
			else if (obj instanceof LinkedList) {
				expandAtoms((LinkedList)obj);
			}
			process.add(obj);
		}
	}
	/** アトムの各引数に対してアトム展開を行う。
	 * @param sAtom アトム展開するアトム。戻るときには破壊される。
	 * @param result アトム展開結果のオブジェクト列を追加するリストオブジェクト（プロセス構造）
	 */
	private void expandAtom(SrcAtom sAtom, LinkedList result) {
		LinkedList process = sAtom.getProcess();
		for (int i = 0; i < process.size(); i++) {
			Object obj = process.get(i);
			// アトム
			if (obj instanceof SrcAtom) {
				SrcAtom subatom = (SrcAtom)obj;
				//
				String newlinkname = generateNewLinkName();
				process.set(i, new SrcLink(newlinkname));
				subatom.getProcess().add(new SrcLink(newlinkname));
				//
				expandAtom(subatom, result);
				result.add(subatom);
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
				submem.getProcess().add(subatom);
				expandAtoms(submem.getProcess());
				result.add(submem);
			}
			// 項組（仮）
			else if (obj instanceof LinkedList) {
				 LinkedList list = (LinkedList)obj;
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
						 subatom.getProcess().add(list);
					 }
					 subatom.getProcess().add(new SrcLink(newlinkname));
					 //
					 expandAtom(subatom, result);
					 result.add(subatom);
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
	private void unabbreviateTypedLinks(LinkedList process, HashMap typedLinkNameMap) {
		Iterator it = process.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof SrcAtom) {
				SrcAtom sAtom = (SrcAtom)obj;
				for (int i = 0; i < sAtom.getProcess().size(); i++) {
					Object subobj = sAtom.getProcess().get(i);
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
				unabbreviateTypedLinks(((SrcMembrane)obj).getProcess(), typedLinkNameMap);
			}
			else if (obj instanceof LinkedList) {
				unabbreviateTypedLinks((LinkedList)obj, typedLinkNameMap);
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
						error("SYNTAX ERROR: illegal object in guard atom argument: " + subobj);
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
					if (subobj instanceof SrcLink) {}
					else {
						String linkname = generateNewLinkName();
						sAtom.getProcess().set(i, new SrcLink(linkname));
						error("SYNTAX ERROR: illegal object in an atom argument: " + subobj);
					}
				}
			}
			else if (obj instanceof SrcMembrane) {
				correctWorld(((SrcMembrane)obj).getProcess());
			}
			else if (obj instanceof SrcRule) {}
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
}

// TODO ( {p($t)} :- ground($t) | end ) をコンパイルするための内部命令が足りない
