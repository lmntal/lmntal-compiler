package runtime.systemRuleset;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;
public class GlobalSystemRuleset_13 {
	static public void exec(Object[] var, Functor[] f) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
		boolean guard_inline_ret;
		ArrayList guard_inline_gvar2;
		Iterator it_guard_inline;
		Set insset;
		Set delset;
		Map srcmap;
		Map delmap;
		Atom orig;
		Atom copy;
		Link a;
		Link b;
		Iterator it_deleteconnectors;
			atom = ((Atom)var[1]);
			atom.dequeue();
			atom = ((Atom)var[1]);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var[2]);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var[3]);
			atom.getMem().removeAtom(atom);
	}
}
