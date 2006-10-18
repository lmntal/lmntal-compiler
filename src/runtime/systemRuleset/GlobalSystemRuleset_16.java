package runtime.systemRuleset;
import runtime.*;
import java.util.*;
import java.io.*;
import module.*;
public class GlobalSystemRuleset_16 {
	static public void exec(Object[] var, Functor[] f) {
		Atom atom;
		Functor func;
		Link link;
		Membrane mem;
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
			((Atom)var[4]).getMem().relinkAtomArgs(
				((Atom)var[4]), 0,
				((Atom)var[1]), 2 );
	}
}
