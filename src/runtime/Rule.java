package runtime;

import java.util.*;

public final class Rule {
	// 中身は Instruction
	public List memMatch;
	public List atomMatches; //?
	public List body;
	private String text;
	
	/**
	 * ふつうのコンストラクタ。
	 *
	 */
	public Rule() {
		memMatch    = new ArrayList();
		atomMatches = new ArrayList();
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
		l = atomMatches.listIterator();
		Env.p("--atommatches :");
		while(l.hasNext()) {
			Iterator ll = ((List)l.next()).iterator();
			Env.p((Instruction)ll.next());
		} 
		
		l = memMatch.listIterator();
		Env.p("--memmatch :");
		while(l.hasNext()) Env.p((Instruction)l.next());
		
		l = body.listIterator();
		Env.p("--body :");
		while(l.hasNext()) Env.p((Instruction)l.next());
	}
	
	public String toString() {
		return text;
	}
}
