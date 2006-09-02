package test;

import runtime.*;
import java.util.Iterator;
import util.Util;

public final class SampleInitRuleset extends Ruleset {
	public String toString() {
		return "Ruleset Sample";
	}
	public String encode() {
		return "";
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		return false;
	}
	/**
	 * アトム主導テストを行い、マッチすれば適用する
	 * @return ルールを適用した場合はtrue
	 */
	public boolean react(Membrane mem, Atom atom) {
		return false;
	}
	/**
	 * 無からすべてを作るルールはこんな感じ？
	 * @return ルールを適用した場合はtrue
	 */
	public boolean react(Membrane mem) {
		Atom a, b, c;
		Membrane m1;
		
		a = mem.newAtom(new SymbolFunctor("a", 1));
		b = mem.newAtom(new SymbolFunctor("b", 2));
		m1 = mem.newMem();
		c = m1.newAtom(new SymbolFunctor("c", 1));
		
		mem.newLink(a, 0, b, 0);
		mem.newLink(b, 0, a, 0);
		mem.newLink(b, 1, c, 0);
		mem.newLink(c, 0, b, 1);
		
		mem.newAtom(new SymbolFunctor("x", 0));
		mem.newAtom(new SymbolFunctor("x", 0));
		mem.newAtom(new SymbolFunctor("x", 0));

		mem.loadRuleset(new SampleRuleset());
		
		System.out.println("Sample init ruleset generated:");
		System.out.println("a(X), b(X,Y), {c(Y)}, x, x, x, (x :- y)\n");		

		return true;
	}
	public String getGlobalRulesetID() { return ""; }	
	
}

final class SampleRuleset extends Ruleset {
	public String toString() {
		return "(x :- y)";
	}
	// 2006.01.02 okabe
	public String encode() {
		return "";
	}
	/**
	 * アトム主導テストを行い、マッチすれば適用する
	 * @return ルールを適用した場合はtrue
	 */
	public boolean react(Membrane mem, Atom atom) {
		return false;
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		return false;
	}
	/**
	 * x() :- y()
	 * @return ルールを適用した場合はtrue
	 */
	public boolean react(Membrane mem) {
		Iterator it = mem.atomIteratorOfFunctor(new SymbolFunctor("x", 0));
		if(it == Util.NULL_ITERATOR) return false;
		
		Atom a = null;
					
		while(it.hasNext()){
			a = (Atom)it.next();
			break;
		}
		if(a == null) return false;
		
		// x()を消してy()を追加
		mem.removeAtom(a);
		mem.newAtom(new SymbolFunctor("y", 0));
		return true;
	}
	public String getGlobalRulesetID() { return ""; }	
}