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

//import java_cup.runtime.Symbol;
import runtime.Inline;
import runtime.Env;
import compile.structure.*;

public class LMNParser {

	private static final String PREFIX_LINK_NAME = "L::";
	private static final String PREFIX_PROXY_LINK_NAME = "P::";
	static final LinkOccurrence CLOSED_LINK = new LinkOccurrence("",null,0);

	private /*static*/ int nLinkNumber = 0;
	private Scanner lex = null;
	
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
		addProcessToMem(srcProcess, mem);
		HashMap freeLinks = coupleLinks(mem);
		if (!freeLinks.isEmpty()) {
			Iterator it = freeLinks.keySet().iterator();
			while (it.hasNext()) {
				LinkOccurrence link = (LinkOccurrence)freeLinks.get(it.next());
				System.out.println("WARNING: Global singleton link: " + link.name);
				LinkedList process = new LinkedList();
				process.add(new SrcLink(link.name));
				SrcAtom sAtom = new SrcAtom(link.name, process);
				addSrcAtomToMem(sAtom, mem);
			}
			coupleLinks(mem);
		}
		Inline.makeCode();
		return mem;
	}

	////////////////////////////////////////////////////////////////
	
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
				addLinkOccurrence(links, a.args[j]);
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
		// リンク単一化
		else if (obj instanceof SrcLinkUnify) {
			addSrcLinkUnifyToMem((SrcLinkUnify)obj, mem);
		}
		// その他 
		else {
			throw new ParseException("Unknown Object to add membrane:"+obj);
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
		addProcessToMem(sMem.getProcess(), submem);
		HashMap freeLinks = coupleLinks(submem);
		
		// 子膜の自由リンクに対してプロキシを追加する
		HashMap newFreeLinks = new HashMap();
		Iterator it = freeLinks.keySet().iterator();
		while (it.hasNext()) {
			LinkOccurrence freeLink = (LinkOccurrence)freeLinks.get(it.next());
			String proxyLinkName = PREFIX_PROXY_LINK_NAME + freeLink.name;
			// 子膜にinside_proxyを追加
			ProxyAtom inside = new ProxyAtom(ProxyAtom.INSIDE_PROXY, submem);
			inside.args[0] = new LinkOccurrence(proxyLinkName, inside, 0); // 外側
			inside.args[1] = new LinkOccurrence(freeLink.name, inside, 1); // 内側
			inside.args[1].buddy = freeLink;
			freeLink.buddy = inside.args[1];
			submem.atoms.add(inside);
			// 新しい自由リンク名を新しい自由リンク一覧に追加する
			newFreeLinks.put(proxyLinkName, inside.args[0]);			
			// この膜にoutside_proxyを追加
			ProxyAtom outside = new ProxyAtom(ProxyAtom.OUTSIDE_PROXY, mem);
			outside.args[0] = new LinkOccurrence(proxyLinkName, outside, 0); // 内側
			outside.args[1] = new LinkOccurrence(freeLink.name, outside, 1); // 外側
			outside.args[0].buddy = inside.args[0];
			inside.args[0].buddy = outside.args[0];
			mem.atoms.add(outside);
		}
		submem.freeLinks = newFreeLinks;
		mem.mems.add(submem);
	}
	
	/**
	 * アトム構文を膜に追加
	 * @param sAtom 追加したいアトム構文
	 * @param mem 追加先の膜
	 * @throws ParseException アトムのリンクに未知なオブジェクトがある場合など
	 */
	private void addSrcAtomToMem(SrcAtom sAtom, Membrane mem) throws ParseException {
		LinkedList p = sAtom.getProcess();
		Atom atom = new Atom(mem, sAtom.getName(), p.size(), sAtom.line, sAtom.column);
		for (int i = 0; i < p.size(); i++) {
			Object obj = p.get(i);
			// 通常リンク
			if (obj instanceof SrcLink) {
				setLinkToAtomArg((SrcLink)obj, atom, i);
			}
			// アトム
			else if (obj instanceof SrcAtom) {
				SrcLink link = createNewSrcLink();
				((SrcAtom)obj).process.add(new SrcLink(link.getName()));
				addSrcAtomToMem((SrcAtom)obj, mem);
				setLinkToAtomArg(link, atom, i);
			}
			// その他
			else {
				throw new ParseException("Unknown object in an atom argument: "+obj);
			}
		}
		mem.atoms.add(atom);
	}

	/**
	 * ルール構文を膜に追加する
	 * @param sRule 追加したいルール構文
	 * @param mem 追加先の膜
	 * @throws ParseException
	 */
	private void addSrcRuleToMem(SrcRule sRule, Membrane mem) throws ParseException {
		RuleStructure rule = new RuleStructure(mem);
		HashMap ruleLinks = new HashMap();

		// TODO 簡略記法の展開 sRuleの中身を置き換え
		
		// ヘッド
		addProcessToMem(sRule.getHead(), rule.leftMem);
		HashMap lhsFreeLinks = coupleLinks(rule.leftMem);
		
		// ガード
		addProcessToMem(sRule.getGuard(), rule.guardMem);

		// ボディ
		addProcessToMem(sRule.getBody(), rule.rightMem);
		HashMap rhsFreeLinks = coupleLinks(rule.rightMem);
		
		// 右辺と左辺の自由リンクを接続する
		coupleInheritedLinks(ruleLinks, lhsFreeLinks, rhsFreeLinks);
		
		// todo プロセス文脈を接続する
		
		mem.rules.add(rule);
	}
	
	/** 左辺と右辺の自由リンクをつなぐ */
	static void coupleInheritedLinks(HashMap links, HashMap lhsfreelinks, HashMap rhsfreelinks) throws ParseException {
		HashMap linkNameTable = new HashMap();
		Iterator it = lhsfreelinks.keySet().iterator();
		while (it.hasNext()) {
			String linkname = (String)it.next();
			if (lhsfreelinks.get(linkname) == CLOSED_LINK) continue;
			LinkOccurrence lhsocc = (LinkOccurrence)lhsfreelinks.get(linkname);
			addLinkOccurrence(links, lhsocc);
		}
		it = rhsfreelinks.keySet().iterator();
		while (it.hasNext()) {
			String linkname = (String)it.next();
			if (rhsfreelinks.get(linkname) == CLOSED_LINK) continue;
			LinkOccurrence rhsocc = (LinkOccurrence)rhsfreelinks.get(linkname);
			addLinkOccurrence(links, rhsocc);
		}
		// TODO 片方にしか出現しない自由リンクをエラー報告とする
	}
	

	/**
	 * プロセス文脈構文を膜に追加
	 * @param sProc 追加したいプロセス文脈構文
	 * @param mem 追加先の膜
	 */
	private void addSrcProcessContextToMem(SrcProcessContext sProc, Membrane mem) {
		ProcessContext p = new ProcessContext(sProc.getName());
		mem.processContexts.add(p);
	}
	
	/**
	 * ルール文脈構文を膜に追加
	 * @param sRule 追加したいルール文脈構文
	 * @param mem 追加先の膜
	 */
	private void addSrcRuleContextToMem(SrcRuleContext sRule, Membrane mem) {
		RuleContext p = new RuleContext(sRule.getName());
		mem.ruleContexts.add(p);
	}
	
	/**
	 * リンク単一化を膜に追加
	 * @param sUnify 追加したいルール単一化
	 * @param mem 追加先の膜
	 * @throws ParseException
	 */
	private void addSrcLinkUnifyToMem(SrcLinkUnify sUnify, Membrane mem) throws ParseException {
		LinkUnify unify = new LinkUnify(mem);
		setLinkToAtomArg((SrcLink)sUnify.getProcess().get(0), unify, 0);
		setLinkToAtomArg((SrcLink)sUnify.getProcess().get(1), unify, 1);
	}
	
	////////////////////////////////////////////////////////////////
	
	/**
	 * ユニークな名前の新しいリンク構文を作成する
	 * @return 作成したリンク構文
	 */
	private SrcLink createNewSrcLink() {
		nLinkNumber++;
		return new SrcLink(PREFIX_LINK_NAME + nLinkNumber);
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
}