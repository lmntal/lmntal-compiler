/**
 * LMNtal Parser メインクラス
 * １つのソースコードはMembraneとして表現されます。
 */

package compile.parser;

import java_cup.runtime.Scanner;
import java.io.Reader;
//import java.util.Arrays;
import java.util.LinkedList;
//import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

import runtime.Functor;
import runtime.Inline;
//import runtime.Env;
import compile.Module;
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
		incorporateSignSymbols(srcProcess);
		expandAtoms(srcProcess);
		correctWorld(srcProcess);
		addProcessToMem(srcProcess, mem);
		HashMap freeLinks = addProxies(mem);
		if (!freeLinks.isEmpty()) closeFreeLinks(mem);
		Inline.makeCode();
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
		}
		return result;
	}

	////////////////////////////////////////////////////////////////
	
	/**
	 * ユニークな名前の新しいリンク構文を作成する
	 * @return 作成したリンク構文
	 * @deprecated
	 */
	private SrcLink createNewSrcLink() {
		return new SrcLink(generateNewLinkName());
	}
	
	/** ユニークな新しいリンク名を生成する */
	private String generateNewLinkName() {
		nLinkNumber++;
		return LINK_NAME_PREFIX + nLinkNumber;	
	}
	/** ユニークな新しいプロセス文脈名を生成する */
	private String generateNewProcessContextName() {
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
		//if (pos >= atom.args.length) error("SYSTEM ERROR: Out of Atom args length:"+pos);
		atom.args[pos] = new LinkOccurrence(link.getName(), atom, pos);
	}
	
	////////////////////////////////////////////////////////////////
	
	/**
	 * 膜にアトム、子膜、ルールなどを膜に登録する
	 * @param list 登録したいプロセスのリスト
	 */
	void addProcessToMem(LinkedList list, Membrane mem) throws ParseException {
		for (int i = 0; i < list.size(); i++) addObjectToMem(list.get(i), mem);
	}
	/**
	 * 膜にアトム、子膜、ルールなどの構文オブジェクトを追加
	 * @param obj 追加する構文オブジェクト
	 * @param mem 追加先の膜
	 */
	private void addObjectToMem(Object obj, Membrane mem) throws ParseException {
		// アトム
		if (obj instanceof SrcAtom) {
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
		// hara
		Module.regMemName(sMem.name, submem);
		submem.name = sMem.name;
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
//				error("SYNTAX ERROR: Untyped process context in an atom argument: " + obj);
//				setLinkToAtomArg(new SrcLink(generateNewLinkName()), atom, i);
//				allbundles = false;
//			}

			// その他
			else {
				error("SYNTAX ERROR: Illegal object in an atom argument: " + obj);
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
		expandRuleAbbreviations(sRule);
		// 構造の生成
		LinkedList typeConstraints = sRule.getGuard();
		addProcessToMem(sRule.getHead(), rule.leftMem);		
		addProcessToMem(typeConstraints, rule.guardMem);
		addProcessToMem(sRule.getBody(), rule.rightMem);
		// リンク以外の名前の接続
		resolveContextNames(rule);
		// プロキシアトムを生成し、リンクをつなぎ、膜の自由リンクリストを決定する
		addProxies(rule.leftMem);
		coupleLinks(rule.guardMem);
		addProxies(rule.rightMem);
		// 右辺と左辺の自由リンクを接続する
		coupleInheritedLinks(rule);
		//
		mem.rules.add(rule);
	}
	
	////////////////////////////////////////////////////////////////
	//
	// リンクとプロキシ
	//
	
	/** 子膜に対して再帰的にプロキシを追加する。
	 * @return この膜の更新された自由リンクマップ mem.freeLinks */
	private HashMap addProxies(Membrane mem) {
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			HashMap freeLinks = addProxies(submem);
			// 子膜の自由リンクに対してプロキシを追加する
			HashMap newFreeLinks = new HashMap();
			Iterator it2 = freeLinks.keySet().iterator();
			while (it2.hasNext()) {
				LinkOccurrence freeLink = (LinkOccurrence)freeLinks.get(it2.next());
				String proxyLinkName = PROXY_LINK_NAME_PREFIX + freeLink.name;
				// 子膜にinside_proxyを追加
				ProxyAtom inside = new ProxyAtom(submem, ProxyAtom.INSIDE_PROXY_NAME);
				inside.args[0] = new LinkOccurrence(proxyLinkName, inside, 0); // 外側
				inside.args[1] = new LinkOccurrence(freeLink.name, inside, 1); // 内側
				inside.args[1].buddy = freeLink;
				freeLink.buddy = inside.args[1];
				submem.atoms.add(inside);
				// 新しい自由リンク名を新しい自由リンク一覧に追加する
				newFreeLinks.put(proxyLinkName, inside.args[0]);			
				// この膜にoutside_proxyを追加
				ProxyAtom outside = new ProxyAtom(mem, ProxyAtom.OUTSIDE_PROXY_NAME);
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
					if (a.args[j].buddy == null) addLinkOccurrence(links, a.args[j]);
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
	private void addLinkOccurrence(HashMap links, LinkOccurrence lnk) {
		// 3回以上の出現
		if (links.get(lnk.name) == CLOSED_LINK) {
			error("SYNTAX ERROR: Link " + lnk.name + " appears more than twice.");
			String linkname = lnk.name + generateNewLinkName();
			if (lnk.name.startsWith(SrcLinkBundle.PREFIX_TAG))
				linkname = SrcLinkBundle.PREFIX_TAG + linkname;
			lnk.name = linkname;
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
			warning("WARNING: Global singleton link: " + link.name);
			LinkedList process = new LinkedList();
			process.add(new SrcLink(link.name));
			SrcAtom sAtom = new SrcAtom(link.name, process);
			addSrcAtomToMem(sAtom, mem);
		}
		coupleLinks(mem);
	}

	/** 左辺と右辺の自由リンクをつなぐ */
	void coupleInheritedLinks(RuleStructure rule) {
		HashMap lhsFreeLinks = rule.leftMem.freeLinks;
		HashMap rhsFreeLinks = rule.rightMem.freeLinks;
		HashMap links = new HashMap();
		Iterator it = lhsFreeLinks.keySet().iterator();
		while (it.hasNext()) {
			String linkname = (String)it.next();
			if (lhsFreeLinks.get(linkname) == CLOSED_LINK) continue;
			LinkOccurrence lhsocc = (LinkOccurrence)lhsFreeLinks.get(linkname);
			addLinkOccurrence(links, lhsocc);
		}
		it = rhsFreeLinks.keySet().iterator();
		while (it.hasNext()) {
			String linkname = (String)it.next();
			if (rhsFreeLinks.get(linkname) == CLOSED_LINK) continue;
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
	 * @param names コンテキストの限定名 (String) から ContextDef への写像 [in,out] */
	private void enumHeadNames(Membrane mem, HashMap names) throws ParseException {
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			enumHeadNames(submem, names);
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
				it.remove(); // 少なくとも型なしプロセス文脈ではないため取り除く
				pc.def = (ContextDef)names.get(name);
				if (pc.def.isTyped()) {
					if (pc.def.src != null) {
						// 展開を実装すれば不要になる
						error("FEATURE NOT IMPLEMENTED: head contains more than one occurrence of a typed process context name: " + name);
						corrupted();
					}
					if (pc.args.length != 1) {
						error("SYNTAX ERROR: Typed process context occurring in head must have exactly one explicit free link argument: " + pc);
						continue;
					}
					mem.typedProcessContexts.add(pc);
				}
				else {
					// 構造比較への変換を実装すれば不要になる
					error("FEATURE NOT IMPLEMENTED: untyped process context name appeared more than once in a head: " + name);
					corrupted();
				}
			}
			pc.def.src = pc;	// ソース出現を登録
			if (pc.bundle != null) addLinkOccurrence(names, pc.bundle);
		}
		it = mem.ruleContexts.iterator();
		while (it.hasNext()) {
			RuleContext rc = (RuleContext)it.next();
			rc.def = new ContextDef(rc.getQualifiedName());
			rc.def.src = rc;
			names.put(rc.getQualifiedName(), rc.def);
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
			error("SYNTAX ERROR: Head membrane cannot contain more than one untyped process context");
			it = mem.processContexts.iterator();
			while (it.hasNext()) {
				((ProcessContext)it.next()).def.src = null; // ソース出現の登録を取り消す
				it.remove(); // namesには残る
			}
		}
		if (mem.ruleContexts.size() > 1) {
			error("SYNTAX ERROR: Head membrane cannot contain more than one rule context");
			while (it.hasNext()) {
				((RuleContext)it.next()).def.src = null; // ソース出現の登録を取り消す
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
				if (pc.def.src != null) {
					if (pc.args.length != pc.def.src.args.length
					 || ((pc.bundle == null) != (((ProcessContext)pc.def.src).bundle == null)) ) {
						error("SYNTAX ERROR: unmatched length of free link list of process context: " + pc);
						it.remove();
						continue;
					}
				}
				if (pc.def.isTyped()) {
					it.remove();
					if (pc.args.length != 1) {
						error("SYNTAX ERROR: Typed process context occurring in body must have exactly one explicit free link argument: " + pc);
						continue;
					}
					mem.typedProcessContexts.add(pc);
				}
				else {
					if (pc.def.src == null) {
						// 構文エラーによりヘッド出現が取り消された型なし$pは、ボディ出現が無言で取り除かれる
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

	/** ルール構造に対して、プロセス文脈およびルール文脈の接続を行う */
	private void resolveContextNames(RuleStructure rule) throws ParseException {

		// 同じ名前のプロセス文脈の引数パターンを同じにする。
		// 型付きは明示的な自由リンクの個数を1にする。

		Iterator it;
		HashMap names = new HashMap();
		enumTypedNames(rule.guardMem, names);
		enumHeadNames(rule.leftMem, names);
		// todo リンク束が左辺で閉じていないことを確認する
		
		// - 左辺トップレベルのプロセス文脈を削除する
		it = rule.leftMem.processContexts.iterator();
		while (it.hasNext()) {
			ProcessContext pc = (ProcessContext)it.next();
			error("SYNTAX ERROR: untyped head process context requires an enclosing membrane: " + pc);
			names.remove(pc.def.getName());
			pc.def.src = null;	// ソース出現の登録を取り消す
			it.remove();
		}
		
		//
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
			else { // 型付きでない場合、src!=nullとなっている
				if (def.src instanceof ProcessContext) {
					rule.processContexts.put(name, def);
				}
				else if (def.src instanceof RuleContext) {
					rule.ruleContexts.put(name, def);
				}
			}
			if (def.rhsOccs.size() == 1) {
				if (def.src != null) {	// ガードのとき。意味が無いのでdef.srcは仕様変更か廃止したい
					Context rhsocc = ((Context)def.rhsOccs.get(0));
					rhsocc.buddy = def.src;
					def.src.buddy = rhsocc;
				}
			}			
		}
		
		// （非線型プロセス文脈が実装されるまでの仮措置として）線型でないものを取り除く
		it = rule.processContexts.values().iterator();
		while (it.hasNext()) {
			ContextDef def = (ContextDef)it.next();
			if (def.rhsOccs.size() != 1) {
				error("FEATURE NOT IMPLEMENTED: untyped process context must be linear: " + def.getName());
				corrupted();
			}
		}
	}
	
	////////////////////////////////////////////////////////////////
	//
	// 略記法の展開
	//

	/** ルール構文に対して略記法の展開を行う */
	private void expandRuleAbbreviations(SrcRule sRule) throws ParseException {

		// todo ここでガードを型制約と否定条件に分類する（現在は全て型制約として扱っている）
		LinkedList typeConstraints = sRule.getGuard();

		// - 数値の正負号の取り込み
		incorporateSignSymbols(sRule.getHead());
		incorporateSignSymbols(typeConstraints);
		incorporateSignSymbols(sRule.getBody());

		// - 型制約の = を除去する
		// todo
		
		// - アトム展開（アトム引数の再帰的な展開）
		expandAtoms(sRule.getHead());
		expandAtoms(typeConstraints);
		expandAtoms(sRule.getBody());

		// - 型制約の構文エラーを訂正し、アトム引数にリンクかプロセス文脈のみが存在するようにする
		correctTypeConstraints(typeConstraints);

		// - 型制約に出現するリンク名Xに対して、ルール内の全てのXを$Xに置換する
		HashMap typedLinkNameMap = computeTypedLinkNameMap(typeConstraints);
		unabbreviateTypedLinks(sRule.getHead(), typedLinkNameMap);
		unabbreviateTypedLinks(typeConstraints, typedLinkNameMap);
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
		
		// - 型付きプロセス文脈構文の展開
		// todo $pを強制的に$p[X]に展開すると$p[X|*V]に展開できる可能性を制限しているのを何とかする
		expandTypedProcessContexts(sRule.getHead());
		expandTypedProcessContexts(typeConstraints);
		expandTypedProcessContexts(sRule.getBody());
	}

	/** プロセス構造（子ルール外）に出現する正負号を数値アトムに取り込む。
	 * <pre>
	 * '+'(x) → '+x'
	 * '-'(x) → '-x'
	 * </pre>
	 */
	private void incorporateSignSymbols(LinkedList process) {
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
		}
	}
	
	/** プロセス構造（子ルール外）をアトム展開する。
	 * すなわち、アトム引数に出現する全てのアトム構造と膜構造を再帰的に展開する。
	 * <pre>
	 * f(s1,g(t1,tn),sm) → f(s1,X,sm), g(t1,tn,X)
	 * f(s1, {t1,tn},sm) → f(s1,X,sm), {+X,t1,tm}
	 * </pre>
	 */
	private void expandAtoms(LinkedList process) {
		LinkedList srcprocess = (LinkedList)process.clone();
		process.clear();
		Iterator it = srcprocess.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof SrcAtom) {
				expandAtom((SrcAtom)obj, process);
			}
			else if (obj instanceof SrcMembrane) {
				expandAtoms(((SrcMembrane)obj).getProcess());
			}
			process.add(obj);
		}
	}
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
			// 膜（廃止してもよい。実際、現在、構文解析器生成器の都合上、構文的に廃止している）
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
		}
	}
	
	/** アトム展開後のプロセス構造（子ルール外）のアトム引数に出現するプロセス文脈を展開する。
	 * <pre> p(s1,$p,sn) → p(s1,X,sn), $p[X]
	 * </pre>
	 * todo $p[X|*p] に展開すべき場合もあるはず
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
						error("SYNTAX ERROR: Illegal object in guard atom argument: " + subobj);
					}
				}
			}
			else {
				error("SYNTAX ERROR: Illegal object in guard: " + obj);
				it.remove();
			}
		}
	}

	/** アトム展開後のプロセス構造（ソースファイル）（ルール外）に対して、
	 * プロセス文脈やルール文脈やリンク束が出現したらコンパイルエラーとする。
	 * アトム引数での出現は無名のリンクで置換する。*/
	private void correctWorld(LinkedList process) {
		Iterator it = process.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof SrcAtom) {
				SrcAtom sAtom = (SrcAtom)obj;
				for (int i = 0; i < sAtom.getProcess().size(); i++) {
					Object subobj = sAtom.getProcess().get(i);
					if (subobj instanceof SrcLink) {}
					else {
						String linkname = generateNewLinkName();
						sAtom.getProcess().set(i, new SrcLink(linkname));
						error("SYNTAX ERROR: Illegal object in an atom argument: " + subobj);
					}
				}
			}
			else if (obj instanceof SrcMembrane) {
				correctWorld(((SrcMembrane)obj).getProcess());
			}
			else if (obj instanceof SrcRule) {}
			else {
				error("SYNTAX ERROR: Illegal object outside a rule: " + obj);
				it.remove();
			}
		}
	}
}

// TODO ( {p($t)} :- ground($t) | end ) をコンパイルするための内部命令が足りない
