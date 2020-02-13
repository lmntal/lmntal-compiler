package compile.parser;

import java.util.LinkedList;
import java.util.AbstractMap.SimpleEntry;

/**
 * ソース中のルールを表します
 */
class SrcRule extends SrcAbstract
{
	public String name; // ルール名
	public int lineno;	//行番号 2006.1.22 by inui
	public LinkedList<SrcAbstract> head;			// ヘッドプロセス
	public LinkedList<SrcAbstract> body;			// ボディプロセス
	public LinkedList<SrcAbstract> guard;			// ガードプロセス
	public LinkedList guardNegatives;	// ガード否定条件構文のリスト

	private String text; //ルールのテキスト表現

	/**
	 * 指定されたヘッドルールとボディルールと空のガードでルールを初期化します
	 * @param head ヘッドのリスト
	 * @param body ボディのリスト
	 */
	public SrcRule(String name, LinkedList<SrcAbstract> head, LinkedList<SrcAbstract> body)
	{
		this(name, head, new LinkedList<>(), body);
	}

	//2006.1.22 by inui
	/**
	 * 指定されたヘッドルールとボディルールと空のガードでルールを初期化します
	 * @param head ヘッドのリスト
	 * @param body ボディのリスト
	 * @param lineno 行番号
	 */
	public SrcRule(String name, LinkedList<SrcAbstract> head, LinkedList<SrcAbstract> body, int lineno)
	{
		this(name, head, new LinkedList<>(), body);
		this.lineno = lineno;
	}

	/**
	 * 指定されたヘッドルールとボディルールとガードでルールを初期化します
	 * @param head ヘッドのリスト
	 * @param gurad ガードのリスト
	 * @param body ボディのリスト
	 */
	public SrcRule(String name, LinkedList<SrcAbstract> head, LinkedList<SrcAbstract> guard, LinkedList<SrcAbstract> body)
	{
		this.name = name;
		this.head = head;
		this.guard = guard;
		this.guardNegatives = new LinkedList<>();
		this.body = body;
		addHyperLinkConstraint(head, body);
	}

	//2006.1.22 by inui
	/**
	 * 指定されたヘッドルールとボディルールとガードでルールを初期化します
	 * @param head ヘッドのリスト
	 * @param gurad ガードのリスト
	 * @param body ボディのリスト
	 * @param lineno 行番号
	 */
	public SrcRule(String name, LinkedList<SrcAbstract> head, LinkedList<SrcAbstract> guard, LinkedList<SrcAbstract> body, int lineno)
	{
		this(name, head, guard, body);
		this.lineno = lineno;
	}

	//2006/07/07 by kudo
	/**
	 * 指定されたヘッドルールとボディルールとガードルールで初期化する。
	 * simpagation ruleの構文に対して使われるコンストラクタ。
	 * @param head ヘッドのリスト
	 * @param head2 ヘッドのリスト ( \ の後ろ )
	 * @param guard ガードのリスト
	 * @param body ボディのリスト
	 * @param lineno 行番号
	 */
	public SrcRule(String name, LinkedList<SrcAbstract> head, LinkedList<SrcAbstract> head2, LinkedList<SrcAbstract> guard, LinkedList<SrcAbstract> body, int lineno)
	{
		this.name = name;
		this.head = head;
		this.guard = (guard == null ? new LinkedList<>() : guard);
		this.guardNegatives = new LinkedList<>();
		this.body = body;
		if (head2 != null)
		{
			unSimpagationize(head2);
		}
		LinkedList<SrcAbstract> head3 = this.head;
		LinkedList<SrcAbstract> body2 = this.body;
		addTypeConstraint(head3);
		addHyperLinkConstraint(head3, body2);
	}

	/**
	 *	 Head部, Body部に!Xが出現した場合，Guard部にhlink(X)もしくはnew(X)を追加する.
	 *	 meguro
	 */	
	public void addHyperLinkConstraint(LinkedList<SrcAbstract> head, LinkedList<SrcAbstract> body)
	{
		addHyperLinkConstraintSub(head, body, new LinkedList<>());	
	}

	private LinkedList<String> addHyperLinkConstraintSub(LinkedList<SrcAbstract> head, LinkedList<SrcAbstract> body, LinkedList<String> headhl)
	{
		if (head != null)
		{
			for (SrcAbstract o : head)
			{
				if (o instanceof SrcHyperLink)
				{
					SrcHyperLink shl = (SrcHyperLink)o;
					LinkedList<SrcAbstract> newl = new LinkedList<>();
					SrcLink name = new SrcLink(shl.name);
					newl.add(name);
					headhl.add(name.toString());	
					SrcAtom newg = new SrcAtom("hlink", newl);
					guard.add(newg);
				}
				else if (o instanceof SrcAtom)
				{
					SrcAtom sa = (SrcAtom)o;
					headhl = addHyperLinkConstraintSub(sa.process, null, headhl);
				} 
				else if (o instanceof SrcMembrane)
				{
					SrcMembrane sm = (SrcMembrane)o;
					headhl = addHyperLinkConstraintSub(sm.process, null, headhl);
				}
			}
		}
 
		if (body != null)
		{
			for (SrcAbstract o : body)
			{
				if (o instanceof SrcHyperLink)
				{
					SrcHyperLink shl = (SrcHyperLink)o;
					SrcLink name = new SrcLink(shl.name);
					if (!headhl.contains(name.toString()))
					{
						LinkedList<SrcAbstract> newl = new LinkedList<>();
						newl.add(name);
						headhl.add(name.toString());
						SrcAtom newg = new SrcAtom("new", newl);
						guard.add(newg);
					}
				}
				else if (o instanceof SrcAtom)
				{
					SrcAtom sa = (SrcAtom)o;
					headhl = addHyperLinkConstraintSub(null, sa.process, headhl);
				}
				else if (o instanceof SrcMembrane)
				{
					SrcMembrane sm = (SrcMembrane)o;
					headhl = addHyperLinkConstraintSub(null, sm.process, headhl);
				}
			}
		}
		return headhl;
	}

	/**
	 * リンク名が_IXなど、頭に_Iがつくと自動でガードにint(_IX)を加える.
	 * hara. nakano.
	 * */
	public void addTypeConstraint(LinkedList<SrcAbstract> l)
	{
		if (l == null)
		{
			return;
		}

		for (SrcAbstract o : l)
		{
			if (o instanceof SrcLink)
			{
				SrcLink sl = (SrcLink)o;
				if (sl.name.matches("^_I.*")) // int
				{
					LinkedList<SrcAbstract> newl = new LinkedList<>();
					newl.add(new SrcLink(sl.name));
					SrcAtom newg = new SrcAtom("int", newl);
					guard.add(newg);
				}
				else if (sl.name.matches("^_G.*")) // ground
				{
					LinkedList<SrcAbstract> newl = new LinkedList<>();
					newl.add(new SrcLink(sl.name));
					SrcAtom newg = new SrcAtom("ground", newl);
					guard.add(newg);
				}
				else if (sl.name.matches("^_S.*")) // string
				{
					LinkedList<SrcAbstract> newl = new LinkedList<>();
					newl.add(new SrcLink(sl.name));
					SrcAtom newg = new SrcAtom("string", newl);
					guard.add(newg);
				}
				else if (sl.name.matches("^_U.*")) // unary
				{
					LinkedList<SrcAbstract> newl = new LinkedList<>();
					newl.add(new SrcLink(sl.name));
					SrcAtom newg = new SrcAtom("unary", newl);
					guard.add(newg);
				}
			}
			else if (o instanceof SrcAtom)
			{
				SrcAtom sa = (SrcAtom)o;
				addTypeConstraint(sa.process);
			}
			else if (o instanceof SrcMembrane)
			{
				SrcMembrane sm = (SrcMembrane)o;
				addTypeConstraint(sm.process);
			}
		}
	}

	// by kudo (2006/07/07)
	/**
	 * simpagation rule を、通常のルールの形に直す。コンストラクタから呼ばれる。
	 * @param head2 ヘッドの'\'の後ろ部分のリスト
	 */
	private void unSimpagationize(LinkedList<SrcAbstract> head2)
	{
		// head を全てbodyへコピー (前に追加のほうが再利用の上でも都合がいい？)
		body.addAll(copySrcs(head));
		// head2をheadの後ろに連結
		head.addAll(head2);
	}

	/**
	 * ソースオブジェクトのリストをコピーする。
	 * @param l
	 * @return
	 */
	private LinkedList<SrcAbstract> copySrcs(LinkedList<SrcAbstract> l)
	{
		if (l == null)
		{
			return null;
		}

		LinkedList<SrcAbstract> ret = new LinkedList<>(); // List 型だと各所で使っているgetFirstが無い
		for (SrcAbstract o : l)
		{
			if (o instanceof SrcAtom)
			{
				SrcAtom sa = (SrcAtom)o;
				ret.add(new SrcAtom(sa.getName(), copySrcs(sa.getProcess())));
			}
			else if (o instanceof SrcMembrane)
			{
				SrcMembrane sm = (SrcMembrane)o;
				SrcMembrane cpm = new SrcMembrane(copySrcs(sm.getProcess()));
				cpm.name = sm.name;
				cpm.kind = sm.kind;
				cpm.stable = sm.stable;
				cpm.pragma = sm.pragma;
				ret.add(cpm);
			}
			else if (o instanceof SrcProcessContext)
			{
				SrcProcessContext spc = (SrcProcessContext)o;
				SrcProcessContext cppc = new SrcProcessContext(spc.getName());
				cppc.args = copySrcs(spc.args);
				if (spc.bundle != null)
				{
					cppc.bundle = new SrcLinkBundle(spc.bundle.getName());
				}
				ret.add(cppc);
			}
			else if (o instanceof SrcContext) // SrcLink, SrcLinkBundle, SrcRuleContext
			{
				SrcLink sl = (SrcLink)o;
				ret.add(new SrcLink(sl.getName()));
			}
			else if (o instanceof SrcRule) // ※左辺にルールは出現しない筈
			{
			}
		}
		return ret;
	}

	/**
	 * ヘッドを設定する
	 */
	public void setHead(LinkedList<SrcAbstract> head)
	{
		this.head = head;
	}

	/**
	 * ルールのヘッドを取得します
	 * @return ヘッドのリスト
	 */
	public LinkedList<SrcAbstract> getHead()
	{
		return head;
	}

	/**
	 * ルールのガードを得ます
	 * @return ガードのリスト
	 */
	public LinkedList<SrcAbstract> getGuard()
	{
		return guard;
	}

	/**
	 * ガード否定条件を取得する
	 */
	public LinkedList getGuardNegatives()
	{
		return guardNegatives;
	}

	/**
	 * ルールのボディを取得します
	 * @return ボディのリスト
	 */
	public LinkedList<SrcAbstract> getBody()
	{
		return body;
	}

	public String toString()
	{
		return "(rule:" + name + ")";
	}

	/**
	 * LMNtalソース形式のテキスト表現を取得する。
	 */
	public String getText()
	{
		return text;
	}

	void setText()
	{
		text = SrcDumper.dump(this);
	}
}
