/**
 * LMNtal Parser メインクラス
 * １つのソースコードはMembraneとして表現されます。
 */

package compile.parser;

import java_cup.runtime.Scanner;
import java.io.Reader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;

import runtime.Functor;
import runtime.Inline;
import runtime.Env;
import compile.Module;
import compile.structure.*;

public class LMNParser {

	private static final String LINK_NAME_PREFIX = "";
	private static final String PROXY_LINK_NAME_PREFIX = "^";
	private static final String PROCESS_CONTEXT_NAME_PREFIX = "_";
	static final LinkOccurrence CLOSED_LINK = new LinkOccurrence("",null,0);

	private int nLinkNumber = 0;
	private Scanner lex = null;
	
	private int nErrors = 0;
	private int nWarnings = 0;
	
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
		解析の結果を LinkedList とする解析木として返します。
		@return 解析されたソースコードのリスト
		@throws ParseException 
	*/
	protected LinkedList parseSrc() throws ParseException {
		parser p = new parser(lex);
		LinkedList result = null;
		try {
			result = (LinkedList)p.parse().value;
		} catch (Exception e) {
			throw new ParseException(e.getMessage()+" "+Env.parray(Arrays.asList(e.getStackTrace()), "\n"));	
		}
		return result;
	}
	
	/**
	 * ソースファイルを解析します
	 * 解析後はリンクの貼り付け、プロキシーの作成が行われています
	 * @return ソースファイル全体を生成するルールが１個だけ含まれる膜
	 * @throws ParseException
	 */
	public Membrane parse() throws ParseException {
		LinkedList srcProcess = parseSrc();
		Membrane mem = new Membrane(null);
		expandAtoms(srcProcess);
		correctWorld(srcProcess);
		addProcessToMem(srcProcess, mem);
		HashMap freeLinks = addProxies(mem);
		if (!freeLinks.isEmpty()) {
			closeFreeLinks(mem, "WARNING: Global singleton link: ");
		}
		Inline.makeCode();
		return mem;
	}
	
	////////////////////////////////////////////////////////////////
	
	/**
	 * 膜にアトム、子膜、ルールなどを膜に登録する
	 * @param list 登録したいプロセスのリスト
	 * @throws ParseException
	 */
	void addProcessToMem(LinkedList list, Membrane mem) throws ParseException {
		for (int i = 0; i < list.size(); i++) addObjectToMem(list.get(i), mem);
	}
	/**
	 * 膜にアトム、子膜、ルールなどの構文オブジェクトを追加
	 * @param obj 追加する構文オブジェクト
	 * @param mem 追加先の膜
	 * @throws ParseException objが未知なオブジェクトの場合など
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
//		// リンク単一化
//		else if (obj instanceof SrcLinkUnify) {
//			addSrcLinkUnifyToMem((SrcLinkUnify)obj, mem);
//			System.out.println("foo");
//		}
		// その他 
		else {
			throw new ParseException("Illegal Object to add to a membrane: "+obj);
		}
	}

	/**
	 * 膜構文を膜に追加
	 * @param sMem 追加する膜構文
	 * @param mem 追加先の膜
	 * @throws ParseException
	 */
	private void addSrcMemToMem(SrcMembrane sMem, Membrane mem) throws ParseException {
		Membrane submem = new Membrane(mem);
		// hara
		Module.regMemName(sMem.name, submem);
		submem.name = sMem.name;
		addProcessToMem(sMem.getProcess(), submem);
		mem.mems.add(submem);
	}
	/**
	 * アトム構文を膜に追加
	 * @param sAtom 追加したいアトム構文
	 * @param mem 追加先の膜
	 * @throws ParseException
	 */
	private void addSrcAtomToMem(SrcAtom sAtom, Membrane mem) throws ParseException {
		boolean alllinks   = true;
		boolean allbundles = true;
		LinkedList p = sAtom.getProcess();
		int arity = p.size();
		
		// [1] ファンクタを生成する
		// GUIからの動的な生成に対応する場合にそなえて FunctorFactory のようなものがあった方がよい。
		// runtime.*Functor の多さが、現状の不自然さを物語る。

		SrcName srcname = sAtom.getName();
		String name = srcname.getName();
		String path = null;
		if (srcname.getType() == SrcName.PATHED) {
			int pos = name.indexOf('.');
			path = name.substring(0, pos);
			name = name.substring(pos + 1);
		}
		Functor func = new runtime.Functor(name, arity, path);
		if (arity == 1 && path == null) {
			if (srcname.getType() == SrcName.PLAIN || srcname.getType() == SrcName.SYMBOL) {
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
			// プロセス文脈
			else if (obj instanceof SrcProcessContext) {
				throw new ParseException("Untyped process context in an atom argument: " + obj);
			}
			// その他
			else {
				throw new ParseException("Illegal object in an atom argument: " + obj);
			}
		}
		
		// [4] アトムとアトム集団を識別する
		if (arity > 0 && allbundles) 
			mem.aggregates.add(atom);
		else if (arity == 0 || alllinks )
			mem.atoms.add(atom);
		else {
			System.out.println("SYNTAX ERROR: arguments of an atom contain both links and bundles");
		}
	}

	////////////////////////////////////////////////////////////////
	//
	// リンクとプロキシ
	//
	
	/** 子膜に対して再帰的にプロキシを追加する。
	 * @return この膜の更新された自由リンクマップ mem.freeLinks */
	private HashMap addProxies(Membrane mem) throws ParseException {
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
	 * @throws ParseException
	 * @return リンク名から自由リンク出現へのHashMap
	 */
	private static HashMap coupleLinks(Membrane mem) throws ParseException {
		HashMap links = new HashMap();
		// 同じ膜レベルのリンク結合を行う
		for (int i = 0; i < mem.atoms.size(); i++) {
			Atom a = (Atom)mem.atoms.get(i);
			// リンクの取り出し
			for (int j = 0; j < a.args.length; j++) {
				if (a.args[j].buddy == null) addLinkOccurrence(links, a.args[j]);
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
	 * @throws ParseException 2回より多くリンク名が出現した場合
	 */
	private static void addLinkOccurrence(HashMap links, LinkOccurrence lnk) throws ParseException {
		// 3回以上の出現
		if (links.get(lnk.name) == CLOSED_LINK) {
			throw new ParseException("Link " + lnk.name + " appears more than twice.");
		}
		// 1回目の出現
		else if (links.get(lnk.name) == null) {
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
	public void closeFreeLinks(Membrane mem, String prefix) throws ParseException {
		Iterator it = mem.freeLinks.keySet().iterator();
		while (it.hasNext()) {
			LinkOccurrence link = (LinkOccurrence)mem.freeLinks.get(it.next());
			System.out.println(prefix  + link.name);
			LinkedList process = new LinkedList();
			process.add(new SrcLink(link.name));
			SrcAtom sAtom = new SrcAtom(link.name, process);
			addSrcAtomToMem(sAtom, mem);
		}
		coupleLinks(mem);
	}


	////////////////////////////////////////////////////////////////
	//
	// プロセス文脈とルール文脈
	//
	
	/** ヘッドのプロセス文脈、ルール文脈のマップを作成 */
	private void enumHeadNames(Membrane mem, HashMap names) throws ParseException {
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			enumHeadNames(submem, names);
		}
		//
		if (mem.processContexts.size() > 1) {
			System.out.println("SYNTAX ERROR: Head membrane cannot contain more than one untyped process context");
		}
		it = mem.processContexts.iterator();
		while (it.hasNext()) {
			ProcessContext pc = (ProcessContext)it.next();
			names.put(pc.getQualifiedName(), pc);
			pc.src = pc;
			if (pc.bundle != null) addLinkOccurrence(names, pc.bundle);
		}
		//
		if (mem.ruleContexts.size() > 1) {
			System.out.println("SYNTAX ERROR: Head membrane cannot contain more than one rule context");
		}
		it = mem.ruleContexts.iterator();
		while (it.hasNext()) {
			RuleContext rc = (RuleContext)it.next();
			names.put(rc.getQualifiedName(), rc);
			rc.src = rc;
		}
		//
		it = mem.aggregates.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();
			for (int i = 0; i < atom.args.length; i++) {
				addLinkOccurrence(names, atom.args[i]);
			}
		}
	}

	/** ボディのプロセス文脈、ルール文脈のリストを作成 */
	private void enumBodyNames(Membrane mem, HashMap names) throws ParseException {
		Iterator it = mem.mems.iterator();
		while (it.hasNext()) {
			Membrane submem = (Membrane)it.next();
			enumBodyNames(submem, names);
		}
		it = mem.processContexts.iterator();
		while (it.hasNext()) {
			ProcessContext pc = (ProcessContext)it.next();
			if (names.containsKey(pc.getQualifiedName())) {
				ProcessContext pcsrc = (ProcessContext)names.get(pc.getQualifiedName());
				pc.src = pcsrc;
				if (pc.args.length != pcsrc.args.length
				 || ((pc.bundle == null) ^ (pcsrc.bundle == null)) ) {
					System.out.println("Unmatched length of free link list of process context");
					it.remove();
				}
			}
			else {
				System.out.println("process context not appeared in head: " + pc.getQualifiedName());
			}
		}
		it = mem.ruleContexts.iterator();
		while (it.hasNext()) {
			RuleContext rc = (RuleContext)it.next();
			if (names.containsKey(rc.getQualifiedName())) {
				rc.src = (Context)names.get(rc.getQualifiedName());
			}
			else {
				System.out.println("rule context not appeared in head: " + rc.getQualifiedName());
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
	////////////////////////////////////////////////////////////////
		
	/**
	 * ルール構文を膜に追加する
	 * @param sRule 追加したいルール構文
	 * @param mem 追加先の膜
	 * @throws ParseException
	 */
	private void addSrcRuleToMem(SrcRule sRule, Membrane mem) throws ParseException {
		RuleStructure rule = new RuleStructure(mem);
		
		// todo ここでガードを型制約と否定条件に分類する（現在は全て型制約として扱っている）
		LinkedList typeConstraints = sRule.getGuard();

		// === 略記法の展開ここから ===

		// - アトム展開（アトム引数の再帰的な展開）
		expandAtoms(sRule.getHead());
		expandAtoms(typeConstraints);
		expandAtoms(sRule.getBody());

		// - 型制約の構文エラーを訂正し、アトム引数にリンクかプロセス文脈のみが存在するようにする
		correctTypeConstraints(typeConstraints);

		// - 型制約に出現するリンク名Xに対して、ルール内の全てのXを$_Xに置換する
		HashMap typedNames = new HashMap();
		enumNames(typeConstraints, typedNames);

		HashMap typedLinkNames = new HashMap();
		Iterator it = typedNames.keySet().iterator();
		while (it.hasNext()) {
			String name = (String)it.next();
			if (((LinkedList)typedNames.get(name)).getFirst() instanceof SrcLink) {
				typedLinkNames.put(name, new SrcProcessContext("_" + name, true));
			}
		}
		unabbreviateTypedLinks(sRule.getHead(), typedLinkNames);
		unabbreviateTypedLinks(typeConstraints, typedLinkNames);
		unabbreviateTypedLinks(sRule.getBody(), typedLinkNames);

		// - 構造代入
		// 左辺に2回以上$pが出現した場合に、新しい名前$qにして $p=$qを型制約に追加する
		// todo 実装する

		// - 構造比較
		// 型制約の同じアトムに2回以上$pが出現した場合に、新しい名前$qにして $p==$qを型制約に追加する
		// todo 実装する

		// - 基底項
		// 型制約に出現せず、Bodyでの出現が1回でない$pに対してガードにground($p)を追加する
		// todo 実装する
		

		// - 型付きプロセス文脈構文の展開
		typedNames = new HashMap();
		enumNames(typeConstraints, typedNames);
		expandTypedProcessContexts(sRule.getHead(), typedNames);
		expandTypedProcessContexts(sRule.getBody(), typedNames);

		// === 略記法の展開ここまで ===

		// 構造の生成
		addProcessToMem(sRule.getHead(), rule.leftMem);		
		addProcessToMem(typeConstraints, rule.guardMem);
		addProcessToMem(sRule.getBody(), rule.rightMem);

		// プロキシアトムを生成し、リンクをつなぎ、膜の自由リンクリストを決定する
		addProxies(rule.leftMem);
		addProxies(rule.rightMem);
		
		// 右辺と左辺の自由リンクを接続する
		coupleInheritedLinks(rule);

		// 何もしない
		correctHead(rule.leftMem);
		correctBody(rule.rightMem);
		
		// プロセス文脈およびルール文脈を接続する
		HashMap names = new HashMap();
		enumHeadNames(rule.leftMem, names);
		enumBodyNames(rule.rightMem, names);
		
		// todo rule.processContexts を生成する
		// todo rule.ruleContexts を生成する
		// todo rule.typedProcessContexts を生成する
		// todo bundle を接続する
		
		mem.rules.add(rule);
	}
	
	/** 左辺と右辺の自由リンクをつなぐ */
	void coupleInheritedLinks(RuleStructure rule) throws ParseException {
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
			closeFreeLinks(rule.leftMem, "SYNTAX ERROR: rule head contains free variable: ");
			closeFreeLinks(rule.rightMem,"SYNTAX ERROR: rule body contains free variable: ");
		}
	}

	/**
	 * プロセス文脈構文を膜に追加
	 * @param sProc 追加したいプロセス文脈構文
	 * @param mem 追加先の膜
	 */
	private void addSrcProcessContextToMem(SrcProcessContext sProc, Membrane mem) {
		ProcessContext pc;
		if (sProc.args == null) {
			pc = new ProcessContext(mem, sProc.getQualifiedName(), 0);
			pc.setBundleName("*" + sProc.getName());
		}
		else {
			int length = sProc.args.size();
			pc = new ProcessContext(mem, sProc.getQualifiedName(), length);
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
	
//	private void addSrcLinkUnifyToMem(SrcLinkUnify sUnify, Membrane mem) throws ParseException {
//		Atom unify = new Atom(mem,"=",2);
//		setLinkToAtomArg((SrcLink)sUnify.getProcess().get(0), unify, 0);
//		setLinkToAtomArg((SrcLink)sUnify.getProcess().get(1), unify, 1);
//	}
	
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
	 * @throws ParseException セット先の場所がアトムに存在しない場合
	 */
	private void setLinkToAtomArg(SrcLink link, Atom atom, int pos) throws ParseException {
		if (pos >= atom.args.length) throw new ParseException("Out of Atom args length:"+pos);
		atom.args[pos] = new LinkOccurrence(link.getName(), atom, pos);
	}
	
	
	////////////////////////////////////////////////////////////////
	//
	// 略記法の展開
	//
	
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

	/** アトム展開後のプロセス構造（子ルール外）に出現する全てのtypedLinkNames内のリンク名を
	 * プロセス文脈構文に置換する。
	 * @param typedLinkNames 型付きリンク名 X (String) から、
	 * 対応する型付きプロセス文脈構文 $p_X (SrcProcessContext) への写像
	 * <pre> p(s1,X,sn) → p(s1,$p_X,sn)
	 * </pre>*/
	private void unabbreviateTypedLinks(LinkedList process, HashMap typedLinkNames) {
		Iterator it = process.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof SrcAtom) {
				SrcAtom sAtom = (SrcAtom)obj;
				for (int i = 0; i < sAtom.getProcess().size(); i++) {
					Object subobj = sAtom.getProcess().get(i);
					if (subobj instanceof SrcLink) {
						SrcLink srcLink = (SrcLink)subobj;
						String name = srcLink.getName();
						if (typedLinkNames.containsKey(name)) {
							sAtom.getProcess().set(i, typedLinkNames.get(name));
						}
					}
				}
			}
			else if (obj instanceof SrcMembrane) {
				unabbreviateTypedLinks(((SrcMembrane)obj).getProcess(), typedLinkNames);
			}
		}
	}
	
	/** アトム展開後のプロセス構造（子ルール外）のアトム引数に出現する型付きプロセス文脈を展開する。
	 * <pre> p(s1,$p,sn) → p(s1,X,sn), $p[X]
	 * </pre> */
	private void expandTypedProcessContexts(LinkedList process, HashMap typedNames) {
		Iterator it = process.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof SrcAtom) {
				SrcAtom sAtom = (SrcAtom)obj;
				for (int i = 0; i < sAtom.getProcess().size(); i++) {
					Object subobj = sAtom.getProcess().get(i);
					if (subobj instanceof SrcProcessContext) {
						SrcProcessContext srcProcessContext = (SrcProcessContext)subobj;
						String name = srcProcessContext.getName();
						if (typedNames.containsKey(name)) {							
							String newlinkname = generateNewLinkName();
							sAtom.getProcess().set(i, new SrcLink(newlinkname));
							((SrcAtom)obj).process.add(new SrcLink(newlinkname));
							process.add(srcProcessContext);
							srcProcessContext.args.add(new SrcLink(newlinkname));
							sAtom.getProcess().set(i, typedNames.get(name));
						}
					}
				}
			}
			else if (obj instanceof SrcMembrane) {
				expandTypedProcessContexts(((SrcMembrane)obj).getProcess(), typedNames);
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
						System.out.println("Illegal object in guard atom argument: " + obj);
					}
				}
			}
			else {
				System.out.println("Illegal object in guard: " + obj);
				it.remove();
			}
		}
	}
	
	private void correctHead(Membrane mem) {}
	private void correctBody(Membrane mem) {}

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
						System.out.println("Illegal object in an atom argument: " + obj);
					}
				}
			}
			else if (obj instanceof SrcMembrane) {
				correctWorld(((SrcMembrane)obj).getProcess());
			}
			else if (obj instanceof SrcRule) {}
			else {
				System.out.println("Illegal object outside a rule: " + obj);
				it.remove();
			}
		}
	}
}

// TODO ( {p($t)} :- ground($t) | end ) をコンパイルするための内部命令が足りない
