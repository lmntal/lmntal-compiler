/**
 * LMNtal Parser メインクラス
 * １つのソースコードはMembraneとして表現されます。
 */

package compile.parser;

import java_cup.runtime.Scanner;
import java.io.Reader;
import java.util.LinkedList;
import java.util.Hashtable;
import java.util.Enumeration;

import compile.structure.*;

public class LMNParser {
	
	private Scanner lex = null;
	
	private static int nLinkNumber = 0;
	
	private static final String PREFIX_LINK_NAME = "L::";
	private static final String PREFIX_PROXY_LINK_NAME = "P::";
	
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
			throw new ParseException(e.getMessage());	
		}
		return result;
	}
	
	/**
	 * ソースファイルを解析します
	 * 解析後はリンクの貼り付け、プロキシーの作成が行われています
	 * @return ソースファイル全体（を生成するルールが１個だけ含まれる膜）
	 * @throws ParseException
	 */
	public Membrane parse() throws ParseException {
		LinkedList src = parseSrc();
		Membrane mem = new Membrane(null);
		addProcessToMem(src, mem);
		createProxy(mem);
		return mem;
	}
	
	/**
	 * 膜にアトム、子膜、ルールなどを膜に登録する
	 * @param list 登録したいプロセスのリスト
	 * @param mem 登録先の膜
	 * @throws ParseException
	 */
	private void addProcessToMem(LinkedList list, Membrane mem) throws ParseException {
		for (int i=0;i<list.size();i++) addObjectToMem(list.get(i), mem);
	}
	
	/**
	 * プロキシーの作成とリンクの結合
	 * 子膜は全てリンクの結合、プロキシの作成は行われているとする
	 * @param mem リンク処理を行いたい膜
	 * @throws ParseException
	 */
	private void createProxy(Membrane mem) throws ParseException {
		Hashtable linkNameTable = new Hashtable();
		// 同じ膜レベルのリンク結合を行う
		for (int i=0;i<mem.atoms.size();i++) {
			Atom a = (Atom)mem.atoms.get(i);
			// リンクの取り出し
			for (int j=0;j<a.args.length;j++) {
				connectLink(a.args[j], linkNameTable);
			}
		}
		// 子膜から自由リンクの取り出し＆結合
		for (int i=0;i<mem.mems.size();i++) {
			Membrane childMem = (Membrane)mem.mems.get(i);
			// 自由リンクの取り出し
			for (int j=0;j<childMem.freeLinks.size();j++) {
				connectLink((LinkOccurrence)childMem.freeLinks.get(i), linkNameTable, true, mem);
			}
		}

		Enumeration enumLinkName = linkNameTable.keys();
		// 親膜があり、対応がないものは自由リンクとして登録
		if (mem.mem != null) {
			while (enumLinkName.hasMoreElements()) {
				// プロキシを通した先のリンクを取得
				LinkOccurrence freeLink = 
					addProxyToMem((LinkOccurrence)linkNameTable.get(enumLinkName.nextElement()), mem);
				mem.freeLinks.add(freeLink);
			}
		}
		// 親膜がなくて自由リンクがある場合
		else {
			
		}
	}
	
	/**
	 * アトムにプロキシーを追加
	 * @param freeLink プロキシーを通して外に出る自由リンク
	 * @param mem 追加先の膜
	 * @return プロキシーの先のリンクオブジェクト
	 */
	private LinkOccurrence addProxyToMem(LinkOccurrence freeLink, Membrane mem) {
		ProxyAtom proxy = new ProxyAtom(mem);
		proxy.args[0] = new LinkOccurrence(freeLink.name, proxy, 0); // 外側
		proxy.args[1] = new LinkOccurrence(PREFIX_PROXY_LINK_NAME+freeLink.name, proxy, 1); // 内側
		// 内側の結合
		proxy.args[1].buddy = freeLink;
		freeLink.buddy = proxy.args[1];
		freeLink.name = proxy.args[1].name;
		// プロキシの追加
		mem.atoms.add(proxy);
		
		return proxy.args[0];
	}
	
	
	/**
	 * リンクの結合を行う(同じ膜に存在する場合)
	 * @param lnk 結合を行いたい
	 * @param linkNameTable リンク名にリンクオブジェクトを対応づけたテーブル
	 * @throws ParseException 2回より多くリンク名が出現した場合
	 */
	private void connectLink(LinkOccurrence lnk, Hashtable linkNameTable) throws ParseException {
		connectLink(lnk, linkNameTable, false, null);
	}
	
	/**
	 * リンクの結合を行う
	 * @param lnk 結合を行いたい
	 * @param linkNameTable リンク名にリンクオブジェクトを対応づけたテーブル
	 * @param isOverMembrane 膜を通過するリンクか
	 * @param mem 追加先の膜
	 * @throws ParseException 2回より多くリンク名が出現した場合
	 */
	private void connectLink(LinkOccurrence lnk, Hashtable linkNameTable, boolean isOverMembrane, Membrane mem) throws ParseException {
		// 3回以上の出現
		if (linkNameTable.get(lnk.name) == Boolean.TRUE) {
			throw new ParseException("Link Name '" + lnk.name + "' appear more than 3.");
		}
		// 1回目の出現
		else if (linkNameTable.get(lnk.name) == null) {
			linkNameTable.put(lnk.name, lnk);
		}
		// 2回目の出現
		else {
			LinkOccurrence buddy = (LinkOccurrence)linkNameTable.get(lnk.name);
			if (isOverMembrane) buddy = addProxyToMem(buddy, mem);
			lnk.buddy = buddy;
			buddy.buddy = lnk;
			linkNameTable.put(lnk.name, Boolean.TRUE);
		}
	}
	
	/**
	 * 膜にアトム、子膜、ルールなどを追加
	 * @param obj アトム、子膜、ルールなどのオブジェクト
	 * @param mem 追加したい膜
	 * @throws ParseException objが未知なオブジェクトの場合
	 */
	private void addObjectToMem(Object obj, Membrane mem) throws ParseException {
		// アトム
		if (obj instanceof SrcAtom) {
			addAtomToMem((SrcAtom)obj, mem);
		}
		// 膜
		else if (obj instanceof SrcMembrane) {
			SrcMembrane sMem = (SrcMembrane)obj;
			Membrane p = new Membrane(mem);
			addProcessToMem(sMem.getProcess(), p);
			// リンクの貼り付け プロキシーの生成
			createProxy(p);
			mem.mems.add(p); 
		}
		// ルール
		else if (obj instanceof SrcRule) {
			addRuleToMem((SrcRule)obj, mem);
		}
		// プロセスコンテキスト
		else if (obj instanceof SrcProcessContext) {
			addProcessContextToMem((SrcProcessContext)obj, mem);
		}
		// ルールコンテキスト
		else if (obj instanceof SrcRuleContext) {
			addRuleContextToMem((SrcRuleContext)obj, mem);
		}
		// リンク単一化
		else if (obj instanceof SrcLinkUnify) {
			addLinkUnifyToMem((SrcLinkUnify)obj, mem);
		}
		// その他 
		else {
			throw new ParseException("Unknown Object to add membrane:"+obj);
		}
	}
	
	/**
	 * アトムを膜に追加
	 * @param sAtom 追加したいアトム
	 * @param mem 追加先の膜
	 * @throws ParseException
	 */
	private void addAtomToMem(SrcAtom sAtom, Membrane mem) throws ParseException {
		addAtomToMem(sAtom, mem, null);
	}
	
	/**
	 * アトムを膜に追加
	 * @param sAtom 追加したいアトム
	 * @param mem 追加先の膜
	 * @param lastLink 簡略化されているリンク
	 * @throws ParseException アトムのリンクに未知なオブジェクトがある場合
	 */
	private void addAtomToMem(SrcAtom sAtom, Membrane mem, SrcLink lastLink) throws ParseException {
		if (lastLink != null) sAtom.process.add(lastLink);
		LinkedList p = sAtom.getProcess();
		Atom atom = new Atom(mem, sAtom.getName(), p.size());
		// リンクの編集
		for (int i=0;i<p.size();i++) {
			Object obj = p.get(i);
			// 通常リンク
			if (obj instanceof SrcLink) {
				addLinkToAtom((SrcLink)obj, atom, i);
			}
			// アトム
			else if (obj instanceof SrcAtom) {
				SrcLink link = createNewLink();
				addAtomToMem((SrcAtom)obj, mem, new SrcLink(link.getName()));
				addLinkToAtom(link, atom, i);
			}
			// その他
			else {
				throw new ParseException("Unknown Object to add Link:"+obj);
			}
		}
		mem.atoms.add(atom);
	}
	
	/**
	 * 簡略化されているリンクに名前をつけて作成
	 * @return ユニークなリンク名
	 */
	private SrcLink createNewLink() {
		nLinkNumber++;
		return new SrcLink(PREFIX_LINK_NAME+nLinkNumber);
	}
	
	/**
	 * アトムにリンクを追加する
	 * @param link 追加したいリンク
	 * @param atom 追加先のアトム
	 * @param pos 追加先のアトムでの場所
	 * @throws ParseException 追加先の場所がアトムに存在しない場合
	 */
	private void addLinkToAtom(SrcLink link, Atom atom, int pos) throws ParseException {
		if (pos >= atom.args.length) throw new ParseException("Out of Atom args length:"+pos);
		atom.args[pos] = new LinkOccurrence(link.getName(), atom, pos);
	}

	/**
	 * ルールを膜に追加する
	 * @param sRule 追加したいルール
	 * @param mem 追加先の膜
	 * @throws ParseException
	 */
	private void addRuleToMem(SrcRule sRule, Membrane mem) throws ParseException {
		RuleStructure rule = new RuleStructure();
		// TODO 簡略記法の展開 sRuleの中身を置き換え
		
		addProcessToMem(sRule.getHead(), rule.leftMem);
		createProxy(rule.leftMem);
		addProcessToMem(sRule.getBody(), rule.rightMem);
		createProxy(rule.rightMem);
		
		mem.rules.add(rule);
		rule.parent = mem;       // RuleStructure.parent を追加したので追加 by Hara
	}

	/**
	 * プロセス変数を膜に追加
	 * @param sProc 追加したいプロセス変数
	 * @param mem 追加先の膜
	 */
	private void addProcessContextToMem(SrcProcessContext sProc, Membrane mem) {
		ProcessContext p = new ProcessContext(sProc.getName());
		mem.processContexts.add(p);
	}
	
	/**
	 * ルール変数を膜に追加
	 * @param sRule 追加したいルール変数
	 * @param mem 追加先の膜
	 */
	private void addRuleContextToMem(SrcRuleContext sRule, Membrane mem) {
		RuleContext p = new RuleContext(sRule.getName());
		mem.ruleContexts.add(p);
	}
	
	/**
	 * リンク単一化を膜に追加
	 * @param sUnify 追加したいルール単一化
	 * @param mem 追加先の膜
	 * @throws ParseException
	 */
	private void addLinkUnifyToMem(SrcLinkUnify sUnify, Membrane mem) throws ParseException {
		LinkUnify unify = new LinkUnify(mem);
		addLinkToAtom((SrcLink)sUnify.getProcess().get(0), unify, 0);
		addLinkToAtom((SrcLink)sUnify.getProcess().get(1), unify, 1);
	}
}