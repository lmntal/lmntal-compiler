package runtime;

import java.util.*;

public final class Rule {
	// 中身は Instruction
	public List memMatch;
	public List atomMatch; //?
	public List body;
	private String text;
	
	/**
	 * ふつうのコンストラクタ。
	 *
	 */
	public Rule() {
		memMatch    = new ArrayList();
		atomMatch = new ArrayList();
		body        = new ArrayList();
	}
	/**
	 * ルール文字列つきコンストラクタ
	 * @param text ルールの文字列表現
	 */
	public Rule(String text) {
		this.text = text;
	}
	
	/**
	 * 命令列の詳細を出力する
	 *
	 */
	public void showDetail() {
		Iterator l;
		l = atomMatch.listIterator();
		Env.p("Rule.showDetail  this = "+this);
		
		/*
		Env.p("--atommatches :", 1);
		while(l.hasNext()) {
			Iterator ll = ((List)l.next()).iterator();
			while(ll.hasNext()) Env.p(indent+(Instruction)ll.next());
		}
		*/
		
		l = memMatch.listIterator();
		Env.p("--memmatch :", 1);
		while(l.hasNext()) Env.p((Instruction)l.next(), 2);
		
		l = body.listIterator();
		Env.p("--body :", 1);
		while(l.hasNext()) Env.p((Instruction)l.next(), 2);
		
		Env.p("");
	}
	
	public String toString() {
		return text;
	}
}
