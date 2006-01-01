package translated.module_java;
import runtime.*;
import java.util.*;
import java.io.*;
import daemon.IDConverter;
import module.*;

public class Ruleset610 extends Ruleset {
	private static final Ruleset610 theInstance = new Ruleset610();
	private Ruleset610() {}
	public static Ruleset610 getInstance() {
		return theInstance;
	}
	private int id = 610;
	private String globalRulesetID;
	public String getGlobalRulesetID() {
		if (globalRulesetID == null) {
			globalRulesetID = Env.theRuntime.getRuntimeID() + ":java" + id;
			IDConverter.registerRuleset(globalRulesetID, this);
		}
		return globalRulesetID;
	}
	public String toString() {
		return "@java" + id;
	}
	public boolean react(Membrane mem, Atom atom) {
		boolean result = false;
		if (execL937(mem, atom, false)) {
			if (Env.fTrace)
				Task.trace("-->", "@610", "null");
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		return react(mem, false);
	}
	public boolean react(Membrane mem, boolean nondeterministic) {
		boolean result = false;
		if (execL938(mem, nondeterministic)) {
			if (Env.fTrace)
				Task.trace("==>", "@610", "null");
			return true;
		}
		return result;
	}
	public boolean execL938(Object var0, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
L938:
		{
			if (execL935(var0,nondeterministic)) {
				ret = true;
				break L938;
			}
		}
		return ret;
	}
	public boolean execL935(Object var0, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
L935:
		{
			if (nondeterministic) {
				Task.states.add(new Object[] {theInstance, "null", "L936",var0});
			} else if (execL936(var0,nondeterministic)) {
				ret = true;
				break L935;
			}
		}
		return ret;
	}
	public boolean execL936(Object var0, boolean nondeterministic) {
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
			mem = ((AbstractMembrane)var0).newMem();
			var1 = mem;
			((AbstractMembrane)var1).loadRuleset(Ruleset609.getInstance());
			func = f0;
			var2 = ((AbstractMembrane)var1).newAtom(func);
			func = f1;
			var3 = ((AbstractMembrane)var1).newAtom(func);
			func = f2;
			var4 = ((AbstractMembrane)var1).newAtom(func);
			link = new Link(((Atom)var2), 0);
			var5 = link;
			link = new Link(((Atom)var3), 0);
			var6 = link;
			mem = ((AbstractMembrane)var1);
			mem.unifyLinkBuddies(
				((Link)var5),
				((Link)var6));
			atom = ((Atom)var4);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var3);
			atom.getMem().enqueueAtom(atom);
			atom = ((Atom)var2);
			atom.getMem().enqueueAtom(atom);
			ret = true;
			break L936;
		}
		return ret;
	}
	public boolean execL937(Object var0, Object var1, boolean nondeterministic) {
		Atom atom;
		Functor func;
		Link link;
		AbstractMembrane mem;
		int x, y;
		double u, v;
		int isground_ret;
		boolean eqground_ret;
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
		}
		return ret;
	}
	private static final Functor f1 = new Functor("module", 1, null);
	private static final Functor f2 = new Functor("/*inline_define*/\r\nimport java.lang.reflect.Constructor;\r\nimport java.lang.reflect.Method;\r\nclass Java {\r\n\tstatic Object doInvoke(Object obj, String methodName) {\r\n\t\treturn doInvoke(obj, methodName, null);\r\n\t}\r\n\tstatic Object doInvoke(Object obj, String methodName, Object argv[]) {\r\n\t\tObject r = null;\r\n\t\tif(argv==null) argv=new Object[0];\r\n\t\tClass cl[] = new Class[argv.length];\r\n\t\t\r\n\t\tif(false) { // 速いけどだめな方法。add(Button) が add(Component) にマッチしない\r\n\t\t\ttry {\r\n\t\t\t\t// 必要に応じて追加する\r\n\t\t\t\tClass IntegerClass = Class.forName(\"java.lang.Integer\");\r\n\t\t\t\t// メソッドを識別するためにクラスの配列をつくる\r\n\t\t\t\tfor(int i=0;i<cl.length;i++) {\r\n\t\t\t\t\tcl[i] = argv[i].getClass();\r\n\t\t\t\t\tif(cl[i].equals(IntegerClass)) cl[i] = Integer.TYPE;\r\n//\t\t\t\t\tSystem.out.println(argv[i].getClass());\r\n\t\t\t\t}\r\n\t\t\t} catch (Exception e) {\r\n\t\t\t}\r\n\t\t\r\n\t\t\ttry {\r\n//\t\t\t\tMethod mm[] = obj.getClass().getMethods();\r\n//\t\t\t\tfor(int j=0;j<mm.length;j++) System.out.println(mm[j].getName());\r\n\t\t\t\tMethod m = obj.getClass().getMethod(methodName, cl);\r\n\t\t\t\tr = m.invoke(obj, argv);\r\n\t\t\t} catch (Exception e) {\r\n\t\t\t\te.printStackTrace();\r\n\t\t\t}\r\n\t\t} else { // 遅いけどよさそうな方法\r\n\t\t\tMethod mm[] = obj.getClass().getMethods();\r\n\t\t\tfor(int j=0;j<mm.length;j++) {\r\n\t\t\t\ttry {\r\n\t\t\t\t\tif(!mm[j].getName().equals(methodName)) continue;\r\n\t\t\t\t\tif(mm[j].getParameterTypes().length != argv.length) continue;\r\n\t\t\t\t\tSystem.out.println(\"----------\"+ mm[j]);\r\n\t\t\t\t\tr = mm[j].invoke(obj, argv);\r\n\t\t\t\t\tbreak;\r\n\t\t\t\t} catch (Exception e) {\r\n//\t\t\t\t\te.printStackTrace();\r\n\t\t\t\t}\r\n\t\t\t}\r\n\t\t}\r\n\t\treturn r;\r\n\t}\r\n\tstatic Object doNew(String className) {\r\n\t\treturn doNew(className, null);\r\n\t}\r\n\tstatic Object doNew(String className, Object argv[]) {\r\n\t\tObject r = null;\r\n\t\tif(argv==null) argv=new Object[0];\r\n\t\ttry {\r\n\t\t\tClass cl = Class.forName(className);\r\n\t\t\tConstructor cn[] = cl.getConstructors();\r\n\t\t\tfor(int i=0;i<cn.length;i++) {\r\n\t\t\t\ttry {\r\n//\t\t\t\t\tSystem.out.println(\"-----------------------\\n\"+cn[i]);\r\n\t\t\t\t\tr = cn[i].newInstance(argv);\r\n\t\t\t\t\tbreak;\r\n\t\t\t\t} catch (Exception e) {\r\n//\t\t\t\t\te.printStackTrace();\r\n\t\t\t\t}\r\n\t\t\t}\r\n\t\t} catch (Exception e) {\r\n\t\t\te.printStackTrace();\r\n\t\t}\r\n\t\treturn r;\r\n\t}\r\n}\r\n", 0, null);
	private static final Functor f0 = new Functor("java", 1, null);
}
