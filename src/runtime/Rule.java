package runtime;

import java.util.*;

public final class Rule {
	// 中身は Instruction
	public List memMatch;
	public List atomMatches; //?
	public List body;
	
	public Rule() {
		memMatch    = new ArrayList();
		atomMatches = new ArrayList();
		body        = new ArrayList();
	}
	
	/**
	 * 命令列の詳細を出力する
	 *
	 */
	public void showDetail() {
		Iterator l;
		l = atomMatches.listIterator();
		Env.p("--atommatches :");
		while(l.hasNext()) Env.p((Instruction)l.next());
		
		l = memMatch.listIterator();
		Env.p("--memmatch :");
		while(l.hasNext()) Env.p((Instruction)l.next());
		
		l = body.listIterator();
		Env.p("--body :");
		while(l.hasNext()) Env.p((Instruction)l.next());
	}
}
