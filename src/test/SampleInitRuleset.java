package test;

import runtime.*;
import java.util.Iterator;
import util.Util;

public final class SampleInitRuleset extends Ruleset {
	public String toString() {
		return "Ruleset Sample";
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
		AbstractMembrane m1;
		
		a = mem.newAtom(new Functor("a", 1));
		b = mem.newAtom(new Functor("b", 2));
		m1 = mem.newMem();
		c = m1.newAtom(new Functor("c", 1));
		
		mem.newLink(a, 0, b, 0);
		mem.newLink(b, 0, a, 0);
		mem.newLink(b, 1, c, 0);
		mem.newLink(c, 0, b, 1);
		
		mem.newAtom(new Functor("x", 0));
		mem.newAtom(new Functor("x", 0));
		mem.newAtom(new Functor("x", 0));

		mem.loadRuleset(new SampleRuleset());
		
		System.out.println("Sample init ruleset generated:");
		System.out.println("a(X), b(X,Y), {c(Y)}, x, x, x, (x :- y)\n");		

		return true;
	}
	public int getId() { return 0; }
	public String getGlobalRulesetID() { return ""; }	
	
}

final class SampleRuleset extends Ruleset {
	public String toString() {
		return "(x :- y)";
	}
	/**
	 * アトム主導テストを行い、マッチすれば適用する
	 * @return ルールを適用した場合はtrue
	 */
	public boolean react(Membrane mem, Atom atom) {
		return false;
	}
	/**
	 * x() :- y()
	 * @return ルールを適用した場合はtrue
	 */
	public boolean react(Membrane mem) {
		Iterator it = mem.atomIteratorOfFunctor(new Functor("x", 0));
		if(it == Util.NULL_ITERATOR) return false;
		
		Atom a = null;
					
		while(it.hasNext()){
			a = (Atom)it.next();
			break;
		}
		if(a == null) return false;
		
		// x()を消してy()を追加
		mem.removeAtom(a);
		mem.newAtom(new Functor("y", 0));
		return true;
	}
	public int getId() { return 1; }
	public String getGlobalRulesetID() { return ""; }	
}
