package translated.module_io;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset611 extends Ruleset {
	private static final Ruleset611 theInstance = new Ruleset611();
	private Ruleset611() {}
	public static Ruleset611 getInstance() {
		return theInstance;
	}
	private int id = 611;
	private String globalRulesetID;
	public String getGlobalRulesetID() {
		if (globalRulesetID == null) {
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":io" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@io" + id;
	}
	private String encodedRuleset = 
"(io.use :- '='(io.stdin, [:/*inline*/\t\tAtom stdin = mem.newAtom(new ObjectFunctor(new java.io.BufferedReader(new java.io.InputStreamReader(System.in))));\t\tmem.relink(stdin, 0, me, 0);\t\tme.remove();\t\t:]), '='(io.stdout, [:/*inline*/\t\tAtom stdout = mem.newAtom(new ObjectFunctor(new java.io.PrintWriter(System.out, true)));\t\tmem.relink(stdout, 0, me, 0);\t\tme.remove();\t\t:])), (io.input(Message) :- [:/*inline*/\tString s = javax.swing.JOptionPane.showInputDialog(null, me.nth(0));\tme.setName(s);\tme.nthAtom(0).setName(\"done\");\t:](Message)), (io.input(Message, X) :- [:/*inline*/\tString s = javax.swing.JOptionPane.showInputDialog(null, me.nth(0));\tme.setName(\"done\");\tme.nthAtom(0).setName(s);\t:](Message, X)), (io.input :- [:/*inline*/\tString s = javax.swing.JOptionPane.showInputDialog(null, \"Input text.\");\tme.setName(s);\t:]), ('='(R, io.inputInteger(Message)) :- '='(R, [:/*inline*/\tString s = javax.swing.JOptionPane.showInputDialog(null, me.nth(0));\tAtom atom = mem.newAtom(new IntegerFunctor(Integer.parseInt(s)));\tmem.relink(atom,0,me,1);\tmem.removeAtom(me.nthAtom(0));\tmem.removeAtom(me);\t:](Message))), (io.popup(String) :- string(String) | [:/*inline*/\tjavax.swing.JOptionPane.showMessageDialog(null, me.nth(0));\tmem.newAtom(new Functor(\"done\",0));\tmem.removeAtom(me.nthAtom(0));\tmem.removeAtom(me);\t:](String)), ('='(H, io.fileReader(Filename)) :- unary(Filename) | '='(H, [:/*inline*/\ttry {\t\tAtom br = mem.newAtom(new ObjectFunctor(new java.io.BufferedReader(new java.io.FileReader(me.nth(0)))));//\t\tSystem.out.println(\"FILE=\"+me.nth(0));\t\tmem.relink(br, 0, me, 1);\t\tme.nthAtom(0).remove();\t\tme.remove();\t} catch(Exception e) {}\t:](Filename))), ('='(H, io.fileWriter(Filename)) :- unary(Filename) | '='(H, [:/*inline*/\ttry {\t\tAtom pw = mem.newAtom(new ObjectFunctor(new java.io.PrintWriter(new java.io.FileWriter(me.nth(0)))));//\t\tSystem.out.println(\"FILE=\"+me.nth(0));\t\tmem.relink(pw, 0, me, 1);\t\tme.nthAtom(0).remove();\t\tme.remove();\t} catch(Exception e) {}\t:](Filename))), ('='(H, io.reader(Process)) :- unary(Process) | '='(H, [:/*inline*/\ttry {\t\tObject obj = ((ObjectFunctor)(me.nthAtom(0).getFunctor())).getValue();\t\tif(!(obj instanceof Process)) break;\t\tAtom r = mem.newAtom(new ObjectFunctor(\t\t  new java.io.BufferedReader( new java.io.InputStreamReader(\t\t    ((Process)obj).getInputStream()\t\t  ))\t\t));//\t\tSystem.out.println(r);\t\tmem.relink(r, 0, me, 1);\t\tme.nthAtom(0).remove();\t\tme.remove();\t} catch(Exception e) {e.printStackTrace();}\t:](Process))), ('='(H, io.readline(Result)), io.stdin(STDIN) :- class(STDIN, \"java.io.BufferedReader\") | '='(H, io.readline(STDIN, Result, 0)), io.stdin(STDIN)), (Readline @@ '='(H, io.readline(Object, ReadString)) :- '='(H, io.readline(Object, ReadString, 0))), (Readline @@ '='(H, io.readline(Object, ReadString, ASync)) :- int(ASync), class(Object, \"java.io.BufferedReader\") | '='(H, [:/*inline*/\t\ttry {\t\t\tjava.io.BufferedReader br = (java.io.BufferedReader) ((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\t\t\tint async = ((IntegerFunctor)me.nthAtom(2).getFunctor()).intValue();\t\t\t// ¤³¤Î¥Á¥§¥Ã¥¯¤Ï¥¬¡¼¥É¤Ç¤ä¤?¤Ù¤­\t\t\tif((async!=0 && br.ready()) || async==0) {\t\t\t\tString s = br.readLine();\t\t\t\tAtom result = mem.newAtom(new StringFunctor(s==null?\"\":s));\t\t\t\tmem.relink(result, 0, me, 1);\t\t\t\tAtom res = mem.newAtom(new Functor(s==null ? \"nil\" : \"done\", 1));\t\t\t\tmem.relink(res, 0, me, 3);\t\t\t\tme.nthAtom(0).remove();\t\t\t\tme.nthAtom(2).remove();\t\t\t\tme.remove();\t\t\t} else {\t\t\t\tmem.alterAtomFunctor(me, new Functor(\"readline\", 4, \"io\"));\t\t\t}\t\t} catch(Exception e) {Env.e(e);}\t:](Object, ReadString, ASync))), ('='(H, io.print(String)), io.stdout(STDOUT) :- class(STDOUT, \"java.io.PrintWriter\") | '='(H, io.print(STDOUT, String)), io.stdout(STDOUT)), ('='(H, io.print('.'(String, Rest))), io.stdout(STDOUT) :- string(String), class(STDOUT, \"java.io.PrintWriter\") | '='(H, io.print(STDOUT, '.'(String, Rest))), io.stdout(STDOUT)), ('='(H, io.print(Object, String)) :- class(Object, \"java.io.PrintWriter\"), string(String) | '='(H, [:/*inline*/\t\ttry {\t\t\tjava.io.PrintWriter pw = (java.io.PrintWriter) ((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\t\t\tAtom done = mem.newAtom(new Functor(\"done\", 1));\t\t\tif(pw!=null) {\t\t\t\tpw.println(me.nth(1));\t\t\t}\t\t\tmem.relink(done, 0, me, 2);\t\t\tme.nthAtom(0).remove();\t\t\tme.nthAtom(1).remove();\t\t\tme.remove();\t\t} catch(Exception e) {e.printStackTrace();}\t:](Object, String))), ('='(H, io.print(Object, '.'(String, Rest))) :- string(String), class(Object, \"java.io.PrintWriter\"), string(String) | '='(H, io.print(Object, Rest)), [:/*inline*/\t\ttry {\t\t\tjava.io.PrintWriter pw = (java.io.PrintWriter) ((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\t\t\tif(pw!=null) {\t\t\t\tpw.println(me.nth(1));\t\t\t}\t\t\tme.nthAtom(0).remove();\t\t\tme.nthAtom(1).remove();\t\t\tme.remove();\t\t} catch(Exception e) {e.printStackTrace();}\t:](Object, String)), ('='(H, close(Object)) :- class(Object, \"java.io.PrintWriter\") | '='(H, [:/*inline*/\t\ttry {\t\t\tjava.io.PrintWriter pw = (java.io.PrintWriter) ((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\t\t\tpw.close();\t\t\tmem.relink(me.nthAtom(0), 0, me, 1);\t\t\tme.remove();\t\t} catch(Exception e) {Env.e(e);}\t:](Object))), ('='(H, io.list_of_stdin), io.stdin(STDIN) :- unary(STDIN) | '='(H, list_of_dev_s0(STDIN, '[]', done, 0)), io.stdin(STDIN)), ('='(H, io.list_of_stdin_async), io.stdin(STDIN) :- unary(STDIN) | '='(H, list_of_dev_s0(STDIN, '[]', done, 1)), io.stdin(STDIN)), ('='(H, io.list_of_file(Filename)) :- '='(H, list_of_dev_s0(io.fileReader(Filename), '[]', done, 0))), ('='(H, list_of_dev_s0(Obj, List, done, ASync)) :- int(ASync), class(Obj, \"java.io.BufferedReader\") | '='(H, '.'(String, list_of_dev_s0(Obj, List, io.readline(Obj, String, ASync), ASync)))), ('='(H, list_of_dev_s0(Obj, List, nil, ASync)) :- int(ASync), class(Obj, \"java.io.BufferedReader\") | '='(H, List)), ('='(H, io.toFile(Filename, List)) :- unary(Filename) | '='(H, toFile_s0(io.fileWriter(Filename), List))), ('='(H, toFile_s0(Obj, '.'(CAR, CDR))) :- unary(CAR) | '='(H, toFile_s0(io.print(Obj, CAR), CDR))), ('='(H, toFile_s0(Obj, '[]')) :- '='(H, close(Obj))), ('='(H, io.eager(In)) :- '='(H, io.eager(In, '[]', done))), ('='(H, io.eager(In, List, done)) :- unary(In) | '='(H, io.eager(In, '.'(Res, List), io.readline(In, Res))))";
	public String encode() {
		return encodedRuleset;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL856(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "use");
			return true;
		}
		if (execL865(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "input");
			return true;
		}
		if (execL874(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "input");
			return true;
		}
		if (execL883(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "input");
			return true;
		}
		if (execL892(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "inputInteger");
			return true;
		}
		if (execL901(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "popup");
			return true;
		}
		if (execL910(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "fileReader");
			return true;
		}
		if (execL919(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "fileWriter");
			return true;
		}
		if (execL928(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "reader");
			return true;
		}
		if (execL937(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "readline");
			return true;
		}
		if (execL948(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "Readline");
			return true;
		}
		if (execL957(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "Readline");
			return true;
		}
		if (execL966(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "print");
			return true;
		}
		if (execL977(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "print");
			return true;
		}
		if (execL989(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "print");
			return true;
		}
		if (execL998(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "print");
			return true;
		}
		if (execL1008(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "close");
			return true;
		}
		if (execL1017(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "list_of_stdin");
			return true;
		}
		if (execL1028(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "list_of_stdin_async");
			return true;
		}
		if (execL1039(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "list_of_file");
			return true;
		}
		if (execL1048(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "done");
			return true;
		}
		if (execL1059(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "nil");
			return true;
		}
		if (execL1070(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "toFile");
			return true;
		}
		if (execL1079(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "toFile_s0");
			return true;
		}
		if (execL1089(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "toFile_s0");
			return true;
		}
		if (execL1099(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "eager");
			return true;
		}
		if (execL1108(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@611", "done");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL859(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "use");
			return true;
		}
		if (execL868(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "input");
			return true;
		}
		if (execL877(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "input");
			return true;
		}
		if (execL886(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "input");
			return true;
		}
		if (execL895(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "inputInteger");
			return true;
		}
		if (execL904(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "popup");
			return true;
		}
		if (execL913(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "fileReader");
			return true;
		}
		if (execL922(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "fileWriter");
			return true;
		}
		if (execL931(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "reader");
			return true;
		}
		if (execL942(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "readline");
			return true;
		}
		if (execL951(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "Readline");
			return true;
		}
		if (execL960(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "Readline");
			return true;
		}
		if (execL971(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "print");
			return true;
		}
		if (execL983(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "print");
			return true;
		}
		if (execL992(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "print");
			return true;
		}
		if (execL1002(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "print");
			return true;
		}
		if (execL1011(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "close");
			return true;
		}
		if (execL1022(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "list_of_stdin");
			return true;
		}
		if (execL1033(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "list_of_stdin_async");
			return true;
		}
		if (execL1042(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "list_of_file");
			return true;
		}
		if (execL1053(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "done");
			return true;
		}
		if (execL1064(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "nil");
			return true;
		}
		if (execL1073(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "toFile");
			return true;
		}
		if (execL1083(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "toFile_s0");
			return true;
		}
		if (execL1093(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "toFile_s0");
			return true;
		}
		if (execL1102(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "eager");
			return true;
		}
		if (execL1113(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@611", "done");
			return true;
		}
		return result;
	}
	public boolean execL1113(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
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
		boolean ret = false;
L1113:
		{
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f1).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						var3 = link.getAtom();
						func = ((Atom)var3).getFunctor();
						if (!(func.getArity() != 1)) {
							if (execL1107(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L1113;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1107(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
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
		boolean ret = false;
L1107:
		{
			link = ((Atom)var2).getArg(1);
			var9 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f2;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f3;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var7), 1 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 1,
				(Link)var9 );
			((Atom)var6).getMem().newLink(
				((Atom)var6), 2,
				((Atom)var2), 1 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var4), 0 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 2,
				((Atom)var2), 2 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var5), 0 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L1107;
		}
		return ret;
	}
	public boolean execL1108(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
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
		boolean ret = false;
L1108:
		{
			if (execL1110(var0, var1, nondeterministic)) {
				ret = true;
				break L1108;
			}
			if (execL1112(var0, var1, nondeterministic)) {
				ret = true;
				break L1108;
			}
		}
		return ret;
	}
	public boolean execL1112(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
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
		boolean ret = false;
L1112:
		{
			if (!(!(f1).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(2);
					if (!(link.getPos() != 0)) {
						var6 = link.getAtom();
						link = ((Atom)var1).getArg(0);
						var8 = link.getAtom();
						func = ((Atom)var8).getFunctor();
						if (!(func.getArity() != 1)) {
							if (!(!(f0).equals(((Atom)var6).getFunctor()))) {
								link = ((Atom)var6).getArg(0);
								var7 = link;
								if (execL1107(var0,var6,var1,var8,nondeterministic)) {
									ret = true;
									break L1112;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1110(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
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
		boolean ret = false;
L1110:
		{
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var3 = link.getAtom();
						if (!(!(f1).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(3);
							var7 = link;
							link = ((Atom)var3).getArg(0);
							var8 = link.getAtom();
							func = ((Atom)var8).getFunctor();
							if (!(func.getArity() != 1)) {
								if (execL1107(var0,var1,var3,var8,nondeterministic)) {
									ret = true;
									break L1110;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1102(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
		boolean ret = false;
L1102:
		{
			func = f4;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL1098(var0,var1,nondeterministic)) {
					ret = true;
					break L1102;
				}
			}
		}
		return ret;
	}
	public boolean execL1098(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
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
		boolean ret = false;
L1098:
		{
			link = ((Atom)var1).getArg(0);
			var5 = link;
			link = ((Atom)var1).getArg(1);
			var6 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f5;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			func = f0;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			func = f1;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var4), 1 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 0,
				((Atom)var4), 2 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 0,
				(Link)var5 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 3,
				(Link)var6 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L1098;
		}
		return ret;
	}
	public boolean execL1099(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
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
		boolean ret = false;
L1099:
		{
			if (execL1101(var0, var1, nondeterministic)) {
				ret = true;
				break L1099;
			}
		}
		return ret;
	}
	public boolean execL1101(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
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
		boolean ret = false;
L1101:
		{
			if (!(!(f4).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL1098(var0,var1,nondeterministic)) {
						ret = true;
						break L1101;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1093(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
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
		boolean ret = false;
L1093:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 0)) {
					var2 = link.getAtom();
					if (!(!(f5).equals(((Atom)var2).getFunctor()))) {
						if (execL1088(var0,var1,var2,nondeterministic)) {
							ret = true;
							break L1093;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1088(Object var0, Object var1, Object var2, boolean nondeterministic) {
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
		boolean ret = false;
L1088:
		{
			link = ((Atom)var1).getArg(0);
			var4 = link;
			link = ((Atom)var1).getArg(2);
			var5 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			func = f7;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 0,
				(Link)var4 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 1,
				(Link)var5 );
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L1088;
		}
		return ret;
	}
	public boolean execL1089(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
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
		boolean ret = false;
L1089:
		{
			if (execL1091(var0, var1, nondeterministic)) {
				ret = true;
				break L1089;
			}
		}
		return ret;
	}
	public boolean execL1091(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
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
		boolean ret = false;
L1091:
		{
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 0)) {
						var5 = link.getAtom();
						if (!(!(f5).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							if (execL1088(var0,var1,var5,nondeterministic)) {
								ret = true;
								break L1091;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1083(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
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
		boolean ret = false;
L1083:
		{
			func = f6;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						var3 = link.getAtom();
						func = ((Atom)var3).getFunctor();
						if (!(func.getArity() != 1)) {
							if (execL1078(var0,var1,var2,var3,nondeterministic)) {
								ret = true;
								break L1083;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1078(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
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
		boolean ret = false;
L1078:
		{
			link = ((Atom)var1).getArg(0);
			var7 = link;
			link = ((Atom)var2).getArg(1);
			var8 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f8;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 0,
				(Link)var7 );
			((Atom)var5).getMem().newLink(
				((Atom)var5), 1,
				((Atom)var4), 0 );
			((Atom)var5).getMem().newLink(
				((Atom)var5), 2,
				((Atom)var1), 0 );
			((Atom)var1).getMem().inheritLink(
				((Atom)var1), 1,
				(Link)var8 );
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L1078;
		}
		return ret;
	}
	public boolean execL1079(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
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
		boolean ret = false;
L1079:
		{
			if (execL1081(var0, var1, nondeterministic)) {
				ret = true;
				break L1079;
			}
		}
		return ret;
	}
	public boolean execL1081(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
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
		boolean ret = false;
L1081:
		{
			if (!(!(f6).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 2)) {
						var5 = link.getAtom();
						if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
							link = ((Atom)var5).getArg(0);
							var6 = link;
							link = ((Atom)var5).getArg(1);
							var7 = link;
							link = ((Atom)var5).getArg(2);
							var8 = link;
							link = ((Atom)var5).getArg(0);
							var9 = link.getAtom();
							func = ((Atom)var9).getFunctor();
							if (!(func.getArity() != 1)) {
								if (execL1078(var0,var1,var5,var9,nondeterministic)) {
									ret = true;
									break L1081;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1073(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
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
		boolean ret = false;
L1073:
		{
			func = f9;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				func = ((Atom)var2).getFunctor();
				if (!(func.getArity() != 1)) {
					if (execL1069(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L1073;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1069(Object var0, Object var1, Object var2, boolean nondeterministic) {
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
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
		boolean ret = false;
L1069:
		{
			link = ((Atom)var1).getArg(1);
			var6 = link;
			link = ((Atom)var1).getArg(2);
			var7 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var3 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f10;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f6;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 1,
				((Atom)var5), 0 );
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 1,
				(Link)var6 );
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 2,
				(Link)var7 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L1069;
		}
		return ret;
	}
	public boolean execL1070(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
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
		boolean ret = false;
L1070:
		{
			if (execL1072(var0, var1, nondeterministic)) {
				ret = true;
				break L1070;
			}
		}
		return ret;
	}
	public boolean execL1072(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
		boolean ret = false;
L1072:
		{
			if (!(!(f9).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					func = ((Atom)var5).getFunctor();
					if (!(func.getArity() != 1)) {
						if (execL1069(var0,var1,var5,nondeterministic)) {
							ret = true;
							break L1072;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1064(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
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
		boolean ret = false;
L1064:
		{
			var4 = new Atom(null, f11);
			func = f12;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f13).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(3);
						var3 = link.getAtom();
						link = ((Atom)var2).getArg(0);
						var5 = link.getAtom();
						if (!(!(((Atom)var5).getFunctor() instanceof ObjectFunctor))) {
							{
								Object obj = ((ObjectFunctor)((Atom)var5).getFunctor()).getObject();
								String className = obj.getClass().getName();
								className = className.replaceAll("translated.module_io.", "");
								var6 = new Atom(null, new StringFunctor( className ));
							}
							if (!(!((Atom)var6).getFunctor().equals(((Atom)var4).getFunctor()))) {
								if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
									if (execL1058(var0,var1,var2,var3,var4,var5,nondeterministic)) {
										ret = true;
										break L1064;
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1058(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
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
		boolean ret = false;
L1058:
		{
			mem = ((AbstractMembrane)var0);
			mem.unifyAtomArgs(
				((Atom)var2), 4,
				((Atom)var2), 1 );
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			ret = true;
			break L1058;
		}
		return ret;
	}
	public boolean execL1059(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
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
		boolean ret = false;
L1059:
		{
			if (execL1061(var0, var1, nondeterministic)) {
				ret = true;
				break L1059;
			}
			if (execL1063(var0, var1, nondeterministic)) {
				ret = true;
				break L1059;
			}
		}
		return ret;
	}
	public boolean execL1063(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
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
		boolean ret = false;
L1063:
		{
			var10 = new Atom(null, f11);
			if (!(!(f13).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(4);
					var6 = link;
					link = ((Atom)var1).getArg(2);
					if (!(link.getPos() != 0)) {
						var7 = link.getAtom();
						link = ((Atom)var1).getArg(3);
						var9 = link.getAtom();
						link = ((Atom)var1).getArg(0);
						var11 = link.getAtom();
						if (!(!(((Atom)var11).getFunctor() instanceof ObjectFunctor))) {
							{
								Object obj = ((ObjectFunctor)((Atom)var11).getFunctor()).getObject();
								String className = obj.getClass().getName();
								className = className.replaceAll("translated.module_io.", "");
								var12 = new Atom(null, new StringFunctor( className ));
							}
							if (!(!((Atom)var12).getFunctor().equals(((Atom)var10).getFunctor()))) {
								if (!(!(((Atom)var9).getFunctor() instanceof IntegerFunctor))) {
									if (!(!(f12).equals(((Atom)var7).getFunctor()))) {
										link = ((Atom)var7).getArg(0);
										var8 = link;
										if (execL1058(var0,var7,var1,var9,var10,var11,nondeterministic)) {
											ret = true;
											break L1063;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1061(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
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
		boolean ret = false;
L1061:
		{
			var10 = new Atom(null, f11);
			if (!(!(f12).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var3 = link.getAtom();
						if (!(!(f13).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(3);
							var7 = link;
							link = ((Atom)var3).getArg(4);
							var8 = link;
							link = ((Atom)var3).getArg(3);
							var9 = link.getAtom();
							link = ((Atom)var3).getArg(0);
							var11 = link.getAtom();
							if (!(!(((Atom)var11).getFunctor() instanceof ObjectFunctor))) {
								{
									Object obj = ((ObjectFunctor)((Atom)var11).getFunctor()).getObject();
									String className = obj.getClass().getName();
									className = className.replaceAll("translated.module_io.", "");
									var12 = new Atom(null, new StringFunctor( className ));
								}
								if (!(!((Atom)var12).getFunctor().equals(((Atom)var10).getFunctor()))) {
									if (!(!(((Atom)var9).getFunctor() instanceof IntegerFunctor))) {
										if (execL1058(var0,var1,var3,var9,var10,var11,nondeterministic)) {
											ret = true;
											break L1061;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1053(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
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
		boolean ret = false;
L1053:
		{
			var4 = new Atom(null, f11);
			func = f0;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f13).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(3);
						var3 = link.getAtom();
						link = ((Atom)var2).getArg(0);
						var5 = link.getAtom();
						if (!(!(((Atom)var5).getFunctor() instanceof ObjectFunctor))) {
							{
								Object obj = ((ObjectFunctor)((Atom)var5).getFunctor()).getObject();
								String className = obj.getClass().getName();
								className = className.replaceAll("translated.module_io.", "");
								var6 = new Atom(null, new StringFunctor( className ));
							}
							if (!(!((Atom)var6).getFunctor().equals(((Atom)var4).getFunctor()))) {
								if (!(!(((Atom)var3).getFunctor() instanceof IntegerFunctor))) {
									if (execL1047(var0,var1,var2,var3,var4,var5,nondeterministic)) {
										ret = true;
										break L1053;
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1047(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
		Object var14 = null;
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
		boolean ret = false;
L1047:
		{
			link = ((Atom)var2).getArg(4);
			var14 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var7 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var8 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			var9 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			func = f14;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			func = f2;
			var12 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var8), 0 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 1,
				((Atom)var12), 0 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 2,
				((Atom)var6), 0 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 3,
				((Atom)var2), 2 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var9), 0 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 3,
				((Atom)var7), 0 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 4,
				((Atom)var12), 1 );
			((Atom)var12).getMem().inheritLink(
				((Atom)var12), 2,
				(Link)var14 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var10);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L1047;
		}
		return ret;
	}
	public boolean execL1048(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
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
		boolean ret = false;
L1048:
		{
			if (execL1050(var0, var1, nondeterministic)) {
				ret = true;
				break L1048;
			}
			if (execL1052(var0, var1, nondeterministic)) {
				ret = true;
				break L1048;
			}
		}
		return ret;
	}
	public boolean execL1052(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
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
		boolean ret = false;
L1052:
		{
			var10 = new Atom(null, f11);
			if (!(!(f13).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(4);
					var6 = link;
					link = ((Atom)var1).getArg(2);
					if (!(link.getPos() != 0)) {
						var7 = link.getAtom();
						link = ((Atom)var1).getArg(3);
						var9 = link.getAtom();
						link = ((Atom)var1).getArg(0);
						var11 = link.getAtom();
						if (!(!(((Atom)var11).getFunctor() instanceof ObjectFunctor))) {
							{
								Object obj = ((ObjectFunctor)((Atom)var11).getFunctor()).getObject();
								String className = obj.getClass().getName();
								className = className.replaceAll("translated.module_io.", "");
								var12 = new Atom(null, new StringFunctor( className ));
							}
							if (!(!((Atom)var12).getFunctor().equals(((Atom)var10).getFunctor()))) {
								if (!(!(((Atom)var9).getFunctor() instanceof IntegerFunctor))) {
									if (!(!(f0).equals(((Atom)var7).getFunctor()))) {
										link = ((Atom)var7).getArg(0);
										var8 = link;
										if (execL1047(var0,var7,var1,var9,var10,var11,nondeterministic)) {
											ret = true;
											break L1052;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1050(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
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
		boolean ret = false;
L1050:
		{
			var10 = new Atom(null, f11);
			if (!(!(f0).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var3 = link.getAtom();
						if (!(!(f13).equals(((Atom)var3).getFunctor()))) {
							link = ((Atom)var3).getArg(0);
							var4 = link;
							link = ((Atom)var3).getArg(1);
							var5 = link;
							link = ((Atom)var3).getArg(2);
							var6 = link;
							link = ((Atom)var3).getArg(3);
							var7 = link;
							link = ((Atom)var3).getArg(4);
							var8 = link;
							link = ((Atom)var3).getArg(3);
							var9 = link.getAtom();
							link = ((Atom)var3).getArg(0);
							var11 = link.getAtom();
							if (!(!(((Atom)var11).getFunctor() instanceof ObjectFunctor))) {
								{
									Object obj = ((ObjectFunctor)((Atom)var11).getFunctor()).getObject();
									String className = obj.getClass().getName();
									className = className.replaceAll("translated.module_io.", "");
									var12 = new Atom(null, new StringFunctor( className ));
								}
								if (!(!((Atom)var12).getFunctor().equals(((Atom)var10).getFunctor()))) {
									if (!(!(((Atom)var9).getFunctor() instanceof IntegerFunctor))) {
										if (execL1047(var0,var1,var3,var9,var10,var11,nondeterministic)) {
											ret = true;
											break L1050;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1042(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
		boolean ret = false;
L1042:
		{
			func = f15;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL1038(var0,var1,nondeterministic)) {
					ret = true;
					break L1042;
				}
			}
		}
		return ret;
	}
	public boolean execL1038(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
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
		boolean ret = false;
L1038:
		{
			link = ((Atom)var1).getArg(0);
			var7 = link;
			link = ((Atom)var1).getArg(1);
			var8 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f16;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			func = f5;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			func = f0;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f17;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			func = f13;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var7 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 1,
				((Atom)var6), 0 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 0,
				((Atom)var6), 1 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var6), 2 );
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var6), 3 );
			((Atom)var6).getMem().inheritLink(
				((Atom)var6), 4,
				(Link)var8 );
			atom = ((Atom)var6);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L1038;
		}
		return ret;
	}
	public boolean execL1039(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
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
		boolean ret = false;
L1039:
		{
			if (execL1041(var0, var1, nondeterministic)) {
				ret = true;
				break L1039;
			}
		}
		return ret;
	}
	public boolean execL1041(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
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
		boolean ret = false;
L1041:
		{
			if (!(!(f15).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL1038(var0,var1,nondeterministic)) {
						ret = true;
						break L1041;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1033(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
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
		boolean ret = false;
L1033:
		{
			func = f18;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				func = f19;
				Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
				while (it2.hasNext()) {
					atom = (Atom) it2.next();
					var2 = atom;
					link = ((Atom)var2).getArg(0);
					var3 = link.getAtom();
					func = ((Atom)var3).getFunctor();
					if (!(func.getArity() != 1)) {
						if (execL1027(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L1033;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1027(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
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
		boolean ret = false;
L1027:
		{
			link = ((Atom)var1).getArg(0);
			var11 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f5;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f0;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f20;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			func = f13;
			var9 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var9), 1 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var9), 2 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var9), 3 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 0,
				((Atom)var4), 0 );
			((Atom)var9).getMem().inheritLink(
				((Atom)var9), 4,
				(Link)var11 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var5), 0 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var9);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L1027;
		}
		return ret;
	}
	public boolean execL1028(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
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
		boolean ret = false;
L1028:
		{
			if (execL1030(var0, var1, nondeterministic)) {
				ret = true;
				break L1028;
			}
			if (execL1032(var0, var1, nondeterministic)) {
				ret = true;
				break L1028;
			}
		}
		return ret;
	}
	public boolean execL1032(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
		boolean ret = false;
L1032:
		{
			if (!(!(f19).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					func = ((Atom)var5).getFunctor();
					if (!(func.getArity() != 1)) {
						func = f18;
						Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
						while (it1.hasNext()) {
							atom = (Atom) it1.next();
							var3 = atom;
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (execL1027(var0,var3,var1,var5,nondeterministic)) {
								ret = true;
								break L1032;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1030(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
		boolean ret = false;
L1030:
		{
			if (!(!(f18).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					func = f19;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var3 = atom;
						link = ((Atom)var3).getArg(0);
						var4 = link;
						link = ((Atom)var3).getArg(0);
						var5 = link.getAtom();
						func = ((Atom)var5).getFunctor();
						if (!(func.getArity() != 1)) {
							if (execL1027(var0,var1,var3,var5,nondeterministic)) {
								ret = true;
								break L1030;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1022(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
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
		boolean ret = false;
L1022:
		{
			func = f21;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				func = f19;
				Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
				while (it2.hasNext()) {
					atom = (Atom) it2.next();
					var2 = atom;
					link = ((Atom)var2).getArg(0);
					var3 = link.getAtom();
					func = ((Atom)var3).getFunctor();
					if (!(func.getArity() != 1)) {
						if (execL1016(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L1022;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1016(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
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
		boolean ret = false;
L1016:
		{
			link = ((Atom)var1).getArg(0);
			var11 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f5;
			var6 = ((AbstractMembrane)var0).newAtom(func);
			func = f0;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f17;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			func = f13;
			var9 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var6).getMem().newLink(
				((Atom)var6), 0,
				((Atom)var9), 1 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var9), 2 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var9), 3 );
			((Atom)var9).getMem().newLink(
				((Atom)var9), 0,
				((Atom)var4), 0 );
			((Atom)var9).getMem().inheritLink(
				((Atom)var9), 4,
				(Link)var11 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var5), 0 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var9);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L1016;
		}
		return ret;
	}
	public boolean execL1017(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
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
		boolean ret = false;
L1017:
		{
			if (execL1019(var0, var1, nondeterministic)) {
				ret = true;
				break L1017;
			}
			if (execL1021(var0, var1, nondeterministic)) {
				ret = true;
				break L1017;
			}
		}
		return ret;
	}
	public boolean execL1021(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
		boolean ret = false;
L1021:
		{
			if (!(!(f19).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					func = ((Atom)var5).getFunctor();
					if (!(func.getArity() != 1)) {
						func = f21;
						Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
						while (it1.hasNext()) {
							atom = (Atom) it1.next();
							var3 = atom;
							link = ((Atom)var3).getArg(0);
							var4 = link;
							if (execL1016(var0,var3,var1,var5,nondeterministic)) {
								ret = true;
								break L1021;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1019(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
		boolean ret = false;
L1019:
		{
			if (!(!(f21).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					func = f19;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var3 = atom;
						link = ((Atom)var3).getArg(0);
						var4 = link;
						link = ((Atom)var3).getArg(0);
						var5 = link.getAtom();
						func = ((Atom)var5).getFunctor();
						if (!(func.getArity() != 1)) {
							if (execL1016(var0,var1,var3,var5,nondeterministic)) {
								ret = true;
								break L1019;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1011(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
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
		boolean ret = false;
L1011:
		{
			var2 = new Atom(null, f22);
			func = f7;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var3 = link.getAtom();
				if (!(!(((Atom)var3).getFunctor() instanceof ObjectFunctor))) {
					{
						Object obj = ((ObjectFunctor)((Atom)var3).getFunctor()).getObject();
						String className = obj.getClass().getName();
						className = className.replaceAll("translated.module_io.", "");
						var4 = new Atom(null, new StringFunctor( className ));
					}
					if (!(!((Atom)var4).getFunctor().equals(((Atom)var2).getFunctor()))) {
						if (execL1007(var0,var1,var2,var3,nondeterministic)) {
							ret = true;
							break L1011;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1007(Object var0, Object var1, Object var2, Object var3, boolean nondeterministic) {
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
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
		boolean ret = false;
L1007:
		{
			link = ((Atom)var1).getArg(1);
			var6 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var4 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f23;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var5).getMem().newLink(
				((Atom)var5), 0,
				((Atom)var4), 0 );
			((Atom)var5).getMem().inheritLink(
				((Atom)var5), 1,
				(Link)var6 );
			atom = ((Atom)var5);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var5, 13);
			ret = true;
			break L1007;
		}
		return ret;
	}
	public boolean execL1008(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
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
		boolean ret = false;
L1008:
		{
			if (execL1010(var0, var1, nondeterministic)) {
				ret = true;
				break L1008;
			}
		}
		return ret;
	}
	public boolean execL1010(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
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
		boolean ret = false;
L1010:
		{
			var4 = new Atom(null, f22);
			if (!(!(f7).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					if (!(!(((Atom)var5).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var5).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_io.", "");
							var6 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var6).getFunctor().equals(((Atom)var4).getFunctor()))) {
							if (execL1007(var0,var1,var4,var5,nondeterministic)) {
								ret = true;
								break L1010;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL1002(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
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
		boolean ret = false;
L1002:
		{
			var4 = new Atom(null, f22);
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(1);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					link = ((Atom)var1).getArg(0);
					var5 = link.getAtom();
					if (!(!(((Atom)var5).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var5).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_io.", "");
							var6 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var6).getFunctor().equals(((Atom)var4).getFunctor()))) {
							if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
								link = ((Atom)var2).getArg(0);
								var3 = link.getAtom();
								if (((Atom)var3).getFunctor() instanceof ObjectFunctor &&
								    ((ObjectFunctor)((Atom)var3).getFunctor()).getObject() instanceof String) {
									if (execL997(var0,var1,var2,var3,var4,var5,nondeterministic)) {
										ret = true;
										break L1002;
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL997(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, boolean nondeterministic) {
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
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
		boolean ret = false;
L997:
		{
			link = ((Atom)var2).getArg(1);
			var11 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var5);
			atom.dequeue();
			atom = ((Atom)var5);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			var7 = ((AbstractMembrane)var0).newAtom(((Atom)var5).getFunctor());
			var8 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			func = f24;
			var10 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var1).getMem().newLink(
				((Atom)var1), 0,
				((Atom)var6), 0 );
			((Atom)var1).getMem().inheritLink(
				((Atom)var1), 1,
				(Link)var11 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 0,
				((Atom)var7), 0 );
			((Atom)var10).getMem().newLink(
				((Atom)var10), 1,
				((Atom)var8), 0 );
			atom = ((Atom)var10);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var1);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var10, 12);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L997;
		}
		return ret;
	}
	public boolean execL998(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
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
		boolean ret = false;
L998:
		{
			if (execL1000(var0, var1, nondeterministic)) {
				ret = true;
				break L998;
			}
		}
		return ret;
	}
	public boolean execL1000(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
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
		boolean ret = false;
L1000:
		{
			var10 = new Atom(null, f22);
			if (!(!(f8).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(1);
					if (!(link.getPos() != 2)) {
						var5 = link.getAtom();
						link = ((Atom)var1).getArg(0);
						var11 = link.getAtom();
						if (!(!(((Atom)var11).getFunctor() instanceof ObjectFunctor))) {
							{
								Object obj = ((ObjectFunctor)((Atom)var11).getFunctor()).getObject();
								String className = obj.getClass().getName();
								className = className.replaceAll("translated.module_io.", "");
								var12 = new Atom(null, new StringFunctor( className ));
							}
							if (!(!((Atom)var12).getFunctor().equals(((Atom)var10).getFunctor()))) {
								if (!(!(f2).equals(((Atom)var5).getFunctor()))) {
									link = ((Atom)var5).getArg(0);
									var6 = link;
									link = ((Atom)var5).getArg(1);
									var7 = link;
									link = ((Atom)var5).getArg(2);
									var8 = link;
									link = ((Atom)var5).getArg(0);
									var9 = link.getAtom();
									if (((Atom)var9).getFunctor() instanceof ObjectFunctor &&
									    ((ObjectFunctor)((Atom)var9).getFunctor()).getObject() instanceof String) {
										if (execL997(var0,var1,var5,var9,var10,var11,nondeterministic)) {
											ret = true;
											break L1000;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL992(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
		boolean ret = false;
L992:
		{
			var2 = new Atom(null, f22);
			func = f8;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var3 = link.getAtom();
				link = ((Atom)var1).getArg(1);
				var5 = link.getAtom();
				if (((Atom)var5).getFunctor() instanceof ObjectFunctor &&
				    ((ObjectFunctor)((Atom)var5).getFunctor()).getObject() instanceof String) {
					if (!(!(((Atom)var3).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var3).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_io.", "");
							var4 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var4).getFunctor().equals(((Atom)var2).getFunctor()))) {
							if (execL988(var0,var1,var2,var3,var5,nondeterministic)) {
								ret = true;
								break L992;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL988(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
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
		boolean ret = false;
L988:
		{
			link = ((Atom)var1).getArg(2);
			var8 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var3).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			func = f25;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var5), 0 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 1,
				((Atom)var6), 0 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 2,
				(Link)var8 );
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var7, 11);
			ret = true;
			break L988;
		}
		return ret;
	}
	public boolean execL989(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
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
		boolean ret = false;
L989:
		{
			if (execL991(var0, var1, nondeterministic)) {
				ret = true;
				break L989;
			}
		}
		return ret;
	}
	public boolean execL991(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
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
		boolean ret = false;
L991:
		{
			var5 = new Atom(null, f22);
			if (!(!(f8).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(0);
					var6 = link.getAtom();
					link = ((Atom)var1).getArg(1);
					var8 = link.getAtom();
					if (((Atom)var8).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var8).getFunctor()).getObject() instanceof String) {
						if (!(!(((Atom)var6).getFunctor() instanceof ObjectFunctor))) {
							{
								Object obj = ((ObjectFunctor)((Atom)var6).getFunctor()).getObject();
								String className = obj.getClass().getName();
								className = className.replaceAll("translated.module_io.", "");
								var7 = new Atom(null, new StringFunctor( className ));
							}
							if (!(!((Atom)var7).getFunctor().equals(((Atom)var5).getFunctor()))) {
								if (execL988(var0,var1,var5,var6,var8,nondeterministic)) {
									ret = true;
									break L991;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL983(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
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
		boolean ret = false;
L983:
		{
			var5 = new Atom(null, f22);
			func = f26;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				if (!(link.getPos() != 2)) {
					var2 = link.getAtom();
					if (!(!(f2).equals(((Atom)var2).getFunctor()))) {
						link = ((Atom)var2).getArg(0);
						var4 = link.getAtom();
						if (((Atom)var4).getFunctor() instanceof ObjectFunctor &&
						    ((ObjectFunctor)((Atom)var4).getFunctor()).getObject() instanceof String) {
							func = f27;
							Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
							while (it2.hasNext()) {
								atom = (Atom) it2.next();
								var3 = atom;
								link = ((Atom)var3).getArg(0);
								var6 = link.getAtom();
								if (!(!(((Atom)var6).getFunctor() instanceof ObjectFunctor))) {
									{
										Object obj = ((ObjectFunctor)((Atom)var6).getFunctor()).getObject();
										String className = obj.getClass().getName();
										className = className.replaceAll("translated.module_io.", "");
										var7 = new Atom(null, new StringFunctor( className ));
									}
									if (!(!((Atom)var7).getFunctor().equals(((Atom)var5).getFunctor()))) {
										if (execL976(var0,var1,var3,var2,var4,var5,var6,nondeterministic)) {
											ret = true;
											break L983;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL976(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, boolean nondeterministic) {
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
		Object var14 = null;
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
		boolean ret = false;
L976:
		{
			link = ((Atom)var1).getArg(1);
			var14 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var3);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var6);
			atom.dequeue();
			atom = ((Atom)var6);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			var7 = ((AbstractMembrane)var0).newAtom(((Atom)var6).getFunctor());
			var8 = ((AbstractMembrane)var0).newAtom(((Atom)var6).getFunctor());
			var9 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			func = f8;
			var11 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var3).getMem().newLink(
				((Atom)var3), 0,
				((Atom)var9), 0 );
			((Atom)var3).getMem().newLink(
				((Atom)var3), 2,
				((Atom)var11), 1 );
			((Atom)var11).getMem().newLink(
				((Atom)var11), 0,
				((Atom)var7), 0 );
			((Atom)var11).getMem().inheritLink(
				((Atom)var11), 2,
				(Link)var14 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var8), 0 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var11);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L976;
		}
		return ret;
	}
	public boolean execL977(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
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
		boolean ret = false;
L977:
		{
			if (execL979(var0, var1, nondeterministic)) {
				ret = true;
				break L977;
			}
			if (execL981(var0, var1, nondeterministic)) {
				ret = true;
				break L977;
			}
		}
		return ret;
	}
	public boolean execL981(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
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
		boolean ret = false;
L981:
		{
			var11 = new Atom(null, f22);
			if (!(!(f27).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var12 = link.getAtom();
					if (!(!(((Atom)var12).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var12).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_io.", "");
							var13 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var13).getFunctor().equals(((Atom)var11).getFunctor()))) {
							func = f26;
							Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
							while (it1.hasNext()) {
								atom = (Atom) it1.next();
								var3 = atom;
								link = ((Atom)var3).getArg(0);
								var4 = link;
								link = ((Atom)var3).getArg(1);
								var5 = link;
								link = ((Atom)var3).getArg(0);
								if (!(link.getPos() != 2)) {
									var6 = link.getAtom();
									if (!(!(f2).equals(((Atom)var6).getFunctor()))) {
										link = ((Atom)var6).getArg(0);
										var7 = link;
										link = ((Atom)var6).getArg(1);
										var8 = link;
										link = ((Atom)var6).getArg(2);
										var9 = link;
										link = ((Atom)var6).getArg(0);
										var10 = link.getAtom();
										if (((Atom)var10).getFunctor() instanceof ObjectFunctor &&
										    ((ObjectFunctor)((Atom)var10).getFunctor()).getObject() instanceof String) {
											if (execL976(var0,var3,var1,var6,var10,var11,var12,nondeterministic)) {
												ret = true;
												break L981;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL979(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
		Object var12 = null;
		Object var13 = null;
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
		boolean ret = false;
L979:
		{
			var11 = new Atom(null, f22);
			if (!(!(f26).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					if (!(link.getPos() != 2)) {
						var4 = link.getAtom();
						if (!(!(f2).equals(((Atom)var4).getFunctor()))) {
							link = ((Atom)var4).getArg(0);
							var5 = link;
							link = ((Atom)var4).getArg(1);
							var6 = link;
							link = ((Atom)var4).getArg(2);
							var7 = link;
							link = ((Atom)var4).getArg(0);
							var10 = link.getAtom();
							if (((Atom)var10).getFunctor() instanceof ObjectFunctor &&
							    ((ObjectFunctor)((Atom)var10).getFunctor()).getObject() instanceof String) {
								func = f27;
								Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
								while (it1.hasNext()) {
									atom = (Atom) it1.next();
									var8 = atom;
									link = ((Atom)var8).getArg(0);
									var9 = link;
									link = ((Atom)var8).getArg(0);
									var12 = link.getAtom();
									if (!(!(((Atom)var12).getFunctor() instanceof ObjectFunctor))) {
										{
											Object obj = ((ObjectFunctor)((Atom)var12).getFunctor()).getObject();
											String className = obj.getClass().getName();
											className = className.replaceAll("translated.module_io.", "");
											var13 = new Atom(null, new StringFunctor( className ));
										}
										if (!(!((Atom)var13).getFunctor().equals(((Atom)var11).getFunctor()))) {
											if (execL976(var0,var1,var8,var4,var10,var11,var12,nondeterministic)) {
												ret = true;
												break L979;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL971(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
		boolean ret = false;
L971:
		{
			var3 = new Atom(null, f22);
			func = f26;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				func = f27;
				Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
				while (it2.hasNext()) {
					atom = (Atom) it2.next();
					var2 = atom;
					link = ((Atom)var2).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var4).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_io.", "");
							var5 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var5).getFunctor().equals(((Atom)var3).getFunctor()))) {
							if (execL965(var0,var1,var2,var3,var4,nondeterministic)) {
								ret = true;
								break L971;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL965(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
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
		boolean ret = false;
L965:
		{
			link = ((Atom)var1).getArg(0);
			var9 = link;
			link = ((Atom)var1).getArg(1);
			var10 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			func = f8;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var5), 0 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 1,
				(Link)var9 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 2,
				(Link)var10 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var6), 0 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L965;
		}
		return ret;
	}
	public boolean execL966(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
		boolean ret = false;
L966:
		{
			if (execL968(var0, var1, nondeterministic)) {
				ret = true;
				break L966;
			}
			if (execL970(var0, var1, nondeterministic)) {
				ret = true;
				break L966;
			}
		}
		return ret;
	}
	public boolean execL970(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
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
		boolean ret = false;
L970:
		{
			var6 = new Atom(null, f22);
			if (!(!(f27).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var7 = link.getAtom();
					if (!(!(((Atom)var7).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var7).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_io.", "");
							var8 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var8).getFunctor().equals(((Atom)var6).getFunctor()))) {
							func = f26;
							Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
							while (it1.hasNext()) {
								atom = (Atom) it1.next();
								var3 = atom;
								link = ((Atom)var3).getArg(0);
								var4 = link;
								link = ((Atom)var3).getArg(1);
								var5 = link;
								if (execL965(var0,var3,var1,var6,var7,nondeterministic)) {
									ret = true;
									break L970;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL968(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
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
		boolean ret = false;
L968:
		{
			var6 = new Atom(null, f22);
			if (!(!(f26).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					func = f27;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var4 = atom;
						link = ((Atom)var4).getArg(0);
						var5 = link;
						link = ((Atom)var4).getArg(0);
						var7 = link.getAtom();
						if (!(!(((Atom)var7).getFunctor() instanceof ObjectFunctor))) {
							{
								Object obj = ((ObjectFunctor)((Atom)var7).getFunctor()).getObject();
								String className = obj.getClass().getName();
								className = className.replaceAll("translated.module_io.", "");
								var8 = new Atom(null, new StringFunctor( className ));
							}
							if (!(!((Atom)var8).getFunctor().equals(((Atom)var6).getFunctor()))) {
								if (execL965(var0,var1,var4,var6,var7,nondeterministic)) {
									ret = true;
									break L968;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL960(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
		boolean ret = false;
L960:
		{
			var3 = new Atom(null, f11);
			func = f14;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(2);
				var2 = link.getAtom();
				link = ((Atom)var1).getArg(0);
				var4 = link.getAtom();
				if (!(!(((Atom)var4).getFunctor() instanceof ObjectFunctor))) {
					{
						Object obj = ((ObjectFunctor)((Atom)var4).getFunctor()).getObject();
						String className = obj.getClass().getName();
						className = className.replaceAll("translated.module_io.", "");
						var5 = new Atom(null, new StringFunctor( className ));
					}
					if (!(!((Atom)var5).getFunctor().equals(((Atom)var3).getFunctor()))) {
						if (!(!(((Atom)var2).getFunctor() instanceof IntegerFunctor))) {
							if (execL956(var0,var1,var2,var3,var4,nondeterministic)) {
								ret = true;
								break L960;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL956(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
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
		boolean ret = false;
L956:
		{
			link = ((Atom)var1).getArg(1);
			var8 = link;
			link = ((Atom)var1).getArg(3);
			var9 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f28;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var5), 0 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 1,
				(Link)var8 );
			((Atom)var7).getMem().newLink(
				((Atom)var7), 2,
				((Atom)var6), 0 );
			((Atom)var7).getMem().inheritLink(
				((Atom)var7), 3,
				(Link)var9 );
			atom = ((Atom)var7);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var7, 10);
			ret = true;
			break L956;
		}
		return ret;
	}
	public boolean execL957(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
		boolean ret = false;
L957:
		{
			if (execL959(var0, var1, nondeterministic)) {
				ret = true;
				break L957;
			}
		}
		return ret;
	}
	public boolean execL959(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
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
		boolean ret = false;
L959:
		{
			var7 = new Atom(null, f11);
			if (!(!(f14).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					link = ((Atom)var1).getArg(3);
					var5 = link;
					link = ((Atom)var1).getArg(2);
					var6 = link.getAtom();
					link = ((Atom)var1).getArg(0);
					var8 = link.getAtom();
					if (!(!(((Atom)var8).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var8).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_io.", "");
							var9 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var9).getFunctor().equals(((Atom)var7).getFunctor()))) {
							if (!(!(((Atom)var6).getFunctor() instanceof IntegerFunctor))) {
								if (execL956(var0,var1,var6,var7,var8,nondeterministic)) {
									ret = true;
									break L959;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL951(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
		boolean ret = false;
L951:
		{
			func = f3;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL947(var0,var1,nondeterministic)) {
					ret = true;
					break L951;
				}
			}
		}
		return ret;
	}
	public boolean execL947(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
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
		boolean ret = false;
L947:
		{
			link = ((Atom)var1).getArg(0);
			var4 = link;
			link = ((Atom)var1).getArg(1);
			var5 = link;
			link = ((Atom)var1).getArg(2);
			var6 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f17;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			func = f14;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var3), 2 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 0,
				(Link)var4 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 1,
				(Link)var5 );
			((Atom)var3).getMem().inheritLink(
				((Atom)var3), 3,
				(Link)var6 );
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L947;
		}
		return ret;
	}
	public boolean execL948(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
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
		boolean ret = false;
L948:
		{
			if (execL950(var0, var1, nondeterministic)) {
				ret = true;
				break L948;
			}
		}
		return ret;
	}
	public boolean execL950(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
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
		boolean ret = false;
L950:
		{
			if (!(!(f3).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(2);
					var4 = link;
					if (execL947(var0,var1,nondeterministic)) {
						ret = true;
						break L950;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL942(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
		boolean ret = false;
L942:
		{
			var3 = new Atom(null, f11);
			func = f29;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				func = f19;
				Iterator it2 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
				while (it2.hasNext()) {
					atom = (Atom) it2.next();
					var2 = atom;
					link = ((Atom)var2).getArg(0);
					var4 = link.getAtom();
					if (!(!(((Atom)var4).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var4).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_io.", "");
							var5 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var5).getFunctor().equals(((Atom)var3).getFunctor()))) {
							if (execL936(var0,var1,var2,var3,var4,nondeterministic)) {
								ret = true;
								break L942;
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL936(Object var0, Object var1, Object var2, Object var3, Object var4, boolean nondeterministic) {
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
		Object var9 = null;
		Object var10 = null;
		Object var11 = null;
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
		boolean ret = false;
L936:
		{
			link = ((Atom)var1).getArg(0);
			var10 = link;
			link = ((Atom)var1).getArg(1);
			var11 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var4);
			atom.dequeue();
			atom = ((Atom)var4);
			atom.getMem().removeAtom(atom);
			var5 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			var6 = ((AbstractMembrane)var0).newAtom(((Atom)var4).getFunctor());
			func = f17;
			var7 = ((AbstractMembrane)var0).newAtom(func);
			func = f14;
			var8 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var7).getMem().newLink(
				((Atom)var7), 0,
				((Atom)var8), 2 );
			((Atom)var8).getMem().newLink(
				((Atom)var8), 0,
				((Atom)var5), 0 );
			((Atom)var8).getMem().inheritLink(
				((Atom)var8), 1,
				(Link)var10 );
			((Atom)var8).getMem().inheritLink(
				((Atom)var8), 3,
				(Link)var11 );
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var6), 0 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var8);
			atom.getMem().enqueueAtom(atom);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L936;
		}
		return ret;
	}
	public boolean execL937(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
		boolean ret = false;
L937:
		{
			if (execL939(var0, var1, nondeterministic)) {
				ret = true;
				break L937;
			}
			if (execL941(var0, var1, nondeterministic)) {
				ret = true;
				break L937;
			}
		}
		return ret;
	}
	public boolean execL941(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
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
		boolean ret = false;
L941:
		{
			var6 = new Atom(null, f11);
			if (!(!(f19).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var7 = link.getAtom();
					if (!(!(((Atom)var7).getFunctor() instanceof ObjectFunctor))) {
						{
							Object obj = ((ObjectFunctor)((Atom)var7).getFunctor()).getObject();
							String className = obj.getClass().getName();
							className = className.replaceAll("translated.module_io.", "");
							var8 = new Atom(null, new StringFunctor( className ));
						}
						if (!(!((Atom)var8).getFunctor().equals(((Atom)var6).getFunctor()))) {
							func = f29;
							Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
							while (it1.hasNext()) {
								atom = (Atom) it1.next();
								var3 = atom;
								link = ((Atom)var3).getArg(0);
								var4 = link;
								link = ((Atom)var3).getArg(1);
								var5 = link;
								if (execL936(var0,var3,var1,var6,var7,nondeterministic)) {
									ret = true;
									break L941;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL939(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
		Object var6 = null;
		Object var7 = null;
		Object var8 = null;
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
		boolean ret = false;
L939:
		{
			var6 = new Atom(null, f11);
			if (!(!(f29).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					func = f19;
					Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
					while (it1.hasNext()) {
						atom = (Atom) it1.next();
						var4 = atom;
						link = ((Atom)var4).getArg(0);
						var5 = link;
						link = ((Atom)var4).getArg(0);
						var7 = link.getAtom();
						if (!(!(((Atom)var7).getFunctor() instanceof ObjectFunctor))) {
							{
								Object obj = ((ObjectFunctor)((Atom)var7).getFunctor()).getObject();
								String className = obj.getClass().getName();
								className = className.replaceAll("translated.module_io.", "");
								var8 = new Atom(null, new StringFunctor( className ));
							}
							if (!(!((Atom)var8).getFunctor().equals(((Atom)var6).getFunctor()))) {
								if (execL936(var0,var1,var4,var6,var7,nondeterministic)) {
									ret = true;
									break L939;
								}
							}
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL931(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
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
		boolean ret = false;
L931:
		{
			func = f30;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				func = ((Atom)var2).getFunctor();
				if (!(func.getArity() != 1)) {
					if (execL927(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L931;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL927(Object var0, Object var1, Object var2, boolean nondeterministic) {
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
		boolean ret = false;
L927:
		{
			link = ((Atom)var1).getArg(1);
			var5 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var3 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f31;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var4, 9);
			ret = true;
			break L927;
		}
		return ret;
	}
	public boolean execL928(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
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
		boolean ret = false;
L928:
		{
			if (execL930(var0, var1, nondeterministic)) {
				ret = true;
				break L928;
			}
		}
		return ret;
	}
	public boolean execL930(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
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
		boolean ret = false;
L930:
		{
			if (!(!(f30).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					func = ((Atom)var4).getFunctor();
					if (!(func.getArity() != 1)) {
						if (execL927(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L930;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL922(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
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
		boolean ret = false;
L922:
		{
			func = f10;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				func = ((Atom)var2).getFunctor();
				if (!(func.getArity() != 1)) {
					if (execL918(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L922;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL918(Object var0, Object var1, Object var2, boolean nondeterministic) {
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
		boolean ret = false;
L918:
		{
			link = ((Atom)var1).getArg(1);
			var5 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var3 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f32;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var4, 8);
			ret = true;
			break L918;
		}
		return ret;
	}
	public boolean execL919(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
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
		boolean ret = false;
L919:
		{
			if (execL921(var0, var1, nondeterministic)) {
				ret = true;
				break L919;
			}
		}
		return ret;
	}
	public boolean execL921(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
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
		boolean ret = false;
L921:
		{
			if (!(!(f10).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					func = ((Atom)var4).getFunctor();
					if (!(func.getArity() != 1)) {
						if (execL918(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L921;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL913(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
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
		boolean ret = false;
L913:
		{
			func = f16;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				func = ((Atom)var2).getFunctor();
				if (!(func.getArity() != 1)) {
					if (execL909(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L913;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL909(Object var0, Object var1, Object var2, boolean nondeterministic) {
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
		boolean ret = false;
L909:
		{
			link = ((Atom)var1).getArg(1);
			var5 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var3 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f33;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().inheritLink(
				((Atom)var4), 1,
				(Link)var5 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var4, 7);
			ret = true;
			break L909;
		}
		return ret;
	}
	public boolean execL910(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
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
		boolean ret = false;
L910:
		{
			if (execL912(var0, var1, nondeterministic)) {
				ret = true;
				break L910;
			}
		}
		return ret;
	}
	public boolean execL912(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
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
		boolean ret = false;
L912:
		{
			if (!(!(f16).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					link = ((Atom)var1).getArg(0);
					var4 = link.getAtom();
					func = ((Atom)var4).getFunctor();
					if (!(func.getArity() != 1)) {
						if (execL909(var0,var1,var4,nondeterministic)) {
							ret = true;
							break L912;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL904(Object var0, boolean nondeterministic) {
		Object var1 = null;
		Object var2 = null;
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
		boolean ret = false;
L904:
		{
			func = f34;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				link = ((Atom)var1).getArg(0);
				var2 = link.getAtom();
				if (((Atom)var2).getFunctor() instanceof ObjectFunctor &&
				    ((ObjectFunctor)((Atom)var2).getFunctor()).getObject() instanceof String) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "popup", "L900",var0,var1,var2});
					} else if (execL900(var0,var1,var2,nondeterministic)) {
						ret = true;
						break L904;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL900(Object var0, Object var1, Object var2, boolean nondeterministic) {
		Object var3 = null;
		Object var4 = null;
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
		boolean ret = false;
L900:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			atom = ((Atom)var2);
			atom.dequeue();
			atom = ((Atom)var2);
			atom.getMem().removeAtom(atom);
			var3 = ((AbstractMembrane)var0).newAtom(((Atom)var2).getFunctor());
			func = f35;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var3), 0 );
			SomeInlineCodeio.run((Atom)var4, 6);
			ret = true;
			break L900;
		}
		return ret;
	}
	public boolean execL901(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
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
		boolean ret = false;
L901:
		{
			if (execL903(var0, var1, nondeterministic)) {
				ret = true;
				break L901;
			}
		}
		return ret;
	}
	public boolean execL903(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
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
		boolean ret = false;
L903:
		{
			if (!(!(f34).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(0);
					var3 = link.getAtom();
					if (((Atom)var3).getFunctor() instanceof ObjectFunctor &&
					    ((ObjectFunctor)((Atom)var3).getFunctor()).getObject() instanceof String) {
						if (nondeterministic) {
							Task.states.add(new Object[] {theInstance, "popup", "L900",var0,var1,var3});
						} else if (execL900(var0,var1,var3,nondeterministic)) {
							ret = true;
							break L903;
						}
					}
				}
			}
		}
		return ret;
	}
	public boolean execL895(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
		boolean ret = false;
L895:
		{
			func = f36;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL891(var0,var1,nondeterministic)) {
					ret = true;
					break L895;
				}
			}
		}
		return ret;
	}
	public boolean execL891(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
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
		boolean ret = false;
L891:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			link = ((Atom)var1).getArg(1);
			var4 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f37;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 1,
				(Link)var4 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var2, 5);
			ret = true;
			break L891;
		}
		return ret;
	}
	public boolean execL892(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
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
		boolean ret = false;
L892:
		{
			if (execL894(var0, var1, nondeterministic)) {
				ret = true;
				break L892;
			}
		}
		return ret;
	}
	public boolean execL894(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
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
		boolean ret = false;
L894:
		{
			if (!(!(f36).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL891(var0,var1,nondeterministic)) {
						ret = true;
						break L894;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL886(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
		boolean ret = false;
L886:
		{
			func = f38;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "input", "L882",var0,var1});
				} else if (execL882(var0,var1,nondeterministic)) {
					ret = true;
					break L886;
				}
			}
		}
		return ret;
	}
	public boolean execL882(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
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
		boolean ret = false;
L882:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f39;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var2, 4);
			ret = true;
			break L882;
		}
		return ret;
	}
	public boolean execL883(Object var0, Object var1, boolean nondeterministic) {
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
		boolean ret = false;
L883:
		{
			if (execL885(var0, var1, nondeterministic)) {
				ret = true;
				break L883;
			}
		}
		return ret;
	}
	public boolean execL885(Object var0, Object var1, boolean nondeterministic) {
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
		boolean ret = false;
L885:
		{
			if (!(!(f38).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "input", "L882",var0,var1});
					} else if (execL882(var0,var1,nondeterministic)) {
						ret = true;
						break L885;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL877(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
		boolean ret = false;
L877:
		{
			func = f40;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL873(var0,var1,nondeterministic)) {
					ret = true;
					break L877;
				}
			}
		}
		return ret;
	}
	public boolean execL873(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
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
		boolean ret = false;
L873:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			link = ((Atom)var1).getArg(1);
			var4 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f41;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 1,
				(Link)var4 );
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var2, 3);
			ret = true;
			break L873;
		}
		return ret;
	}
	public boolean execL874(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
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
		boolean ret = false;
L874:
		{
			if (execL876(var0, var1, nondeterministic)) {
				ret = true;
				break L874;
			}
		}
		return ret;
	}
	public boolean execL876(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
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
		boolean ret = false;
L876:
		{
			if (!(!(f40).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					link = ((Atom)var1).getArg(1);
					var3 = link;
					if (execL873(var0,var1,nondeterministic)) {
						ret = true;
						break L876;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL868(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
		boolean ret = false;
L868:
		{
			func = f42;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (execL864(var0,var1,nondeterministic)) {
					ret = true;
					break L868;
				}
			}
		}
		return ret;
	}
	public boolean execL864(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
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
		boolean ret = false;
L864:
		{
			link = ((Atom)var1).getArg(0);
			var3 = link;
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f43;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().inheritLink(
				((Atom)var2), 0,
				(Link)var3 );
			SomeInlineCodeio.run((Atom)var2, 2);
			ret = true;
			break L864;
		}
		return ret;
	}
	public boolean execL865(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
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
		boolean ret = false;
L865:
		{
			if (execL867(var0, var1, nondeterministic)) {
				ret = true;
				break L865;
			}
		}
		return ret;
	}
	public boolean execL867(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
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
		boolean ret = false;
L867:
		{
			if (!(!(f42).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					link = ((Atom)var1).getArg(0);
					var2 = link;
					if (execL864(var0,var1,nondeterministic)) {
						ret = true;
						break L867;
					}
				}
			}
		}
		return ret;
	}
	public boolean execL859(Object var0, boolean nondeterministic) {
		Object var1 = null;
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
		boolean ret = false;
L859:
		{
			func = f44;
			Iterator it1 = ((AbstractMembrane)var0).atomIteratorOfFunctor(func);
			while (it1.hasNext()) {
				atom = (Atom) it1.next();
				var1 = atom;
				if (nondeterministic) {
					Task.states.add(new Object[] {theInstance, "use", "L855",var0,var1});
				} else if (execL855(var0,var1,nondeterministic)) {
					ret = true;
					break L859;
				}
			}
		}
		return ret;
	}
	public boolean execL855(Object var0, Object var1, boolean nondeterministic) {
		Object var2 = null;
		Object var3 = null;
		Object var4 = null;
		Object var5 = null;
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
		boolean ret = false;
L855:
		{
			atom = ((Atom)var1);
			atom.dequeue();
			atom = ((Atom)var1);
			atom.getMem().removeAtom(atom);
			func = f19;
			var2 = ((AbstractMembrane)var0).newAtom(func);
			func = f45;
			var3 = ((AbstractMembrane)var0).newAtom(func);
			func = f27;
			var4 = ((AbstractMembrane)var0).newAtom(func);
			func = f46;
			var5 = ((AbstractMembrane)var0).newAtom(func);
			((Atom)var2).getMem().newLink(
				((Atom)var2), 0,
				((Atom)var3), 0 );
			((Atom)var4).getMem().newLink(
				((Atom)var4), 0,
				((Atom)var5), 0 );
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			SomeInlineCodeio.run((Atom)var3, 0);
			SomeInlineCodeio.run((Atom)var5, 1);
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
				try {
					Class c = Class.forName("translated.Module_io");
					java.lang.reflect.Method method = c.getMethod("getRulesets", null);
					Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);
					for (int i = 0; i < rulesets.length; i++) {
						((AbstractMembrane)var0).loadRuleset(rulesets[i]);
					}
				} catch (ClassNotFoundException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (NoSuchMethodException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (IllegalAccessException e) {
					Env.d(e);
					Env.e("Undefined module io");
				} catch (java.lang.reflect.InvocationTargetException e) {
					Env.d(e);
					Env.e("Undefined module io");
				}
			ret = true;
			break L855;
		}
		return ret;
	}
	public boolean execL856(Object var0, Object var1, boolean nondeterministic) {
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
		boolean ret = false;
L856:
		{
			if (execL858(var0, var1, nondeterministic)) {
				ret = true;
				break L856;
			}
		}
		return ret;
	}
	public boolean execL858(Object var0, Object var1, boolean nondeterministic) {
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
		boolean ret = false;
L858:
		{
			if (!(!(f44).equals(((Atom)var1).getFunctor()))) {
				if (!(((AbstractMembrane)var0) != ((Atom)var1).getMem())) {
					if (nondeterministic) {
						Task.states.add(new Object[] {theInstance, "use", "L855",var0,var1});
					} else if (execL855(var0,var1,nondeterministic)) {
						ret = true;
						break L858;
					}
				}
			}
		}
		return ret;
	}
	private static final Functor f27 = new Functor("stdout", 1, "io");
	private static final Functor f25 = new Functor("/*inline*/\r\n\t\ttry {\r\n\t\t\tjava.io.PrintWriter pw = (java.io.PrintWriter) ((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\r\n\t\t\tAtom done = mem.newAtom(new Functor(\"done\", 1));\r\n\t\t\tif(pw!=null) {\r\n\t\t\t\tpw.println(me.nth(1));\r\n\t\t\t}\r\n\t\t\tmem.relink(done, 0, me, 2);\r\n\t\t\tme.nthAtom(0).remove();\r\n\t\t\tme.nthAtom(1).remove();\r\n\t\t\tme.remove();\r\n\t\t} catch(Exception e) {e.printStackTrace();}\r\n\t", 3, null);
	private static final Functor f21 = new Functor("list_of_stdin", 1, "io");
	private static final Functor f3 = new Functor("readline", 3, "io");
	private static final Functor f12 = new Functor("nil", 1, null);
	private static final Functor f24 = new Functor("/*inline*/\r\n\t\ttry {\r\n\t\t\tjava.io.PrintWriter pw = (java.io.PrintWriter) ((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\r\n\t\t\tif(pw!=null) {\r\n\t\t\t\tpw.println(me.nth(1));\r\n\t\t\t}\r\n\t\t\tme.nthAtom(0).remove();\r\n\t\t\tme.nthAtom(1).remove();\r\n\t\t\tme.remove();\r\n\t\t} catch(Exception e) {e.printStackTrace();}\r\n\t", 2, null);
	private static final Functor f35 = new StringFunctor("/*inline*/\r\n\tjavax.swing.JOptionPane.showMessageDialog(null, me.nth(0));\r\n\tmem.newAtom(new Functor(\"done\",0));\r\n\tmem.removeAtom(me.nthAtom(0));\r\n\tmem.removeAtom(me);\r\n\t");
	private static final Functor f18 = new Functor("list_of_stdin_async", 1, "io");
	private static final Functor f40 = new Functor("input", 2, "io");
	private static final Functor f22 = new StringFunctor("java.io.PrintWriter");
	private static final Functor f33 = new Functor("/*inline*/\r\n\ttry {\r\n\t\tAtom br = mem.newAtom(new ObjectFunctor(new java.io.BufferedReader(new java.io.FileReader(me.nth(0)))));\r\n//\t\tSystem.out.println(\"FILE=\"+me.nth(0));\r\n\t\tmem.relink(br, 0, me, 1);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.remove();\r\n\t} catch(Exception e) {}\r\n\t", 2, null);
	private static final Functor f2 = new Functor(".", 3, null);
	private static final Functor f44 = new Functor("use", 0, "io");
	private static final Functor f5 = new Functor("[]", 1, null);
	private static final Functor f1 = new Functor("eager", 4, "io");
	private static final Functor f20 = new IntegerFunctor(1);
	private static final Functor f32 = new Functor("/*inline*/\r\n\ttry {\r\n\t\tAtom pw = mem.newAtom(new ObjectFunctor(new java.io.PrintWriter(new java.io.FileWriter(me.nth(0)))));\r\n//\t\tSystem.out.println(\"FILE=\"+me.nth(0));\r\n\t\tmem.relink(pw, 0, me, 1);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.remove();\r\n\t} catch(Exception e) {}\r\n\t", 2, null);
	private static final Functor f41 = new Functor("/*inline*/\r\n\tString s = javax.swing.JOptionPane.showInputDialog(null, me.nth(0));\r\n\tme.setName(\"done\");\r\n\tme.nthAtom(0).setName(s);\r\n\t", 2, null);
	private static final Functor f43 = new StringFunctor("/*inline*/\r\n\tString s = javax.swing.JOptionPane.showInputDialog(null, me.nth(0));\r\n\tme.setName(s);\r\n\tme.nthAtom(0).setName(\"done\");\r\n\t");
	private static final Functor f29 = new Functor("readline", 2, "io");
	private static final Functor f28 = new Functor("/*inline*/\r\n\t\ttry {\r\n\t\t\tjava.io.BufferedReader br = (java.io.BufferedReader) ((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\r\n\t\t\tint async = ((IntegerFunctor)me.nthAtom(2).getFunctor()).intValue();\r\n\t\t\t// ¤³¤Î¥Á¥§¥Ã¥¯¤Ï¥¬¡¼¥É¤Ç¤ä¤?¤Ù¤­\r\n\t\t\tif((async!=0 && br.ready()) || async==0) {\r\n\t\t\t\tString s = br.readLine();\r\n\t\t\t\tAtom result = mem.newAtom(new StringFunctor(s==null?\"\":s));\r\n\t\t\t\tmem.relink(result, 0, me, 1);\r\n\t\t\t\tAtom res = mem.newAtom(new Functor(s==null ? \"nil\" : \"done\", 1));\r\n\t\t\t\tmem.relink(res, 0, me, 3);\r\n\t\t\t\tme.nthAtom(0).remove();\r\n\t\t\t\tme.nthAtom(2).remove();\r\n\t\t\t\tme.remove();\r\n\t\t\t} else {\r\n\t\t\t\tmem.alterAtomFunctor(me, new Functor(\"readline\", 4, \"io\"));\r\n\t\t\t}\r\n\t\t} catch(Exception e) {Env.e(e);}\r\n\t", 4, null);
	private static final Functor f6 = new Functor("toFile_s0", 3, null);
	private static final Functor f45 = new StringFunctor("/*inline*/\r\n\t\tAtom stdin = mem.newAtom(new ObjectFunctor(new java.io.BufferedReader(new java.io.InputStreamReader(System.in))));\r\n\t\tmem.relink(stdin, 0, me, 0);\r\n\t\tme.remove();\r\n\t\t");
	private static final Functor f9 = new Functor("toFile", 3, "io");
	private static final Functor f8 = new Functor("print", 3, "io");
	private static final Functor f14 = new Functor("readline", 4, "io");
	private static final Functor f19 = new Functor("stdin", 1, "io");
	private static final Functor f36 = new Functor("inputInteger", 2, "io");
	private static final Functor f34 = new Functor("popup", 1, "io");
	private static final Functor f4 = new Functor("eager", 2, "io");
	private static final Functor f16 = new Functor("fileReader", 2, "io");
	private static final Functor f31 = new Functor("/*inline*/\r\n\ttry {\r\n\t\tObject obj = ((ObjectFunctor)(me.nthAtom(0).getFunctor())).getValue();\r\n\t\tif(!(obj instanceof Process)) break;\r\n\t\tAtom r = mem.newAtom(new ObjectFunctor(\r\n\t\t  new java.io.BufferedReader( new java.io.InputStreamReader(\r\n\t\t    ((Process)obj).getInputStream()\r\n\t\t  ))\r\n\t\t));\r\n//\t\tSystem.out.println(r);\r\n\t\tmem.relink(r, 0, me, 1);\r\n\t\tme.nthAtom(0).remove();\r\n\t\tme.remove();\r\n\t} catch(Exception e) {e.printStackTrace();}\r\n\t", 2, null);
	private static final Functor f26 = new Functor("print", 2, "io");
	private static final Functor f46 = new StringFunctor("/*inline*/\r\n\t\tAtom stdout = mem.newAtom(new ObjectFunctor(new java.io.PrintWriter(System.out, true)));\r\n\t\tmem.relink(stdout, 0, me, 0);\r\n\t\tme.remove();\r\n\t\t");
	private static final Functor f11 = new StringFunctor("java.io.BufferedReader");
	private static final Functor f13 = new Functor("list_of_dev_s0", 5, null);
	private static final Functor f38 = new Functor("input", 0, "io");
	private static final Functor f10 = new Functor("fileWriter", 2, "io");
	private static final Functor f0 = new Functor("done", 1, null);
	private static final Functor f37 = new Functor("/*inline*/\r\n\tString s = javax.swing.JOptionPane.showInputDialog(null, me.nth(0));\r\n\tAtom atom = mem.newAtom(new IntegerFunctor(Integer.parseInt(s)));\r\n\tmem.relink(atom,0,me,1);\r\n\tmem.removeAtom(me.nthAtom(0));\r\n\tmem.removeAtom(me);\r\n\t", 2, null);
	private static final Functor f30 = new Functor("reader", 2, "io");
	private static final Functor f15 = new Functor("list_of_file", 2, "io");
	private static final Functor f42 = new Functor("input", 1, "io");
	private static final Functor f39 = new Functor("/*inline*/\r\n\tString s = javax.swing.JOptionPane.showInputDialog(null, \"Input text.\");\r\n\tme.setName(s);\r\n\t", 0, null);
	private static final Functor f7 = new Functor("close", 2, null);
	private static final Functor f17 = new IntegerFunctor(0);
	private static final Functor f23 = new Functor("/*inline*/\r\n\t\ttry {\r\n\t\t\tjava.io.PrintWriter pw = (java.io.PrintWriter) ((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\r\n\t\t\tpw.close();\r\n\t\t\tmem.relink(me.nthAtom(0), 0, me, 1);\r\n\t\t\tme.remove();\r\n\t\t} catch(Exception e) {Env.e(e);}\r\n\t", 2, null);
}
