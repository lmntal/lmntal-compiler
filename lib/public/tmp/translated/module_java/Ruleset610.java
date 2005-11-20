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
		if (execL710(mem, atom)) {
			return true;
		}
		return result;
	}
	public boolean react(Membrane mem) {
		boolean result = false;
		if (execL711(mem)) {
			return true;
		}
		return result;
	}
	public boolean execL711(Object var0) {
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
L711:
		{
			if (execL708(var0)) {
				ret = true;
				break L711;
			}
		}
		return ret;
	}
	public boolean execL708(Object var0) {
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
L708:
		{
			if (execL709(var0)) {
				ret = true;
				break L708;
			}
		}
		return ret;
	}
	public boolean execL709(Object var0) {
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
L709:
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
			break L709;
		}
		return ret;
	}
	public boolean execL710(Object var0, Object var1) {
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
L710:
		{
		}
		return ret;
	}
	private static final Functor f1 = new Functor("module", 1, null);
	private static final Functor f2 = new Functor("/*inline_define*/\\r\\nimport java.lang.reflect.Constructor;\\r\\nimport java.lang.reflect.Method;\\r\\nclass Java {\\r\\n	static Object doInvoke(Object obj, String methodName) {\\r\\n		return doInvoke(obj, methodName, null);\\r\\n	}\\r\\n	static Object doInvoke(Object obj, String methodName, Object argv[]) {\\r\\n		Object r = null;\\r\\n		if(argv==null) argv=new Object[0];\\r\\n		Class cl[] = new Class[argv.length];\\r\\n		\\r\\n		if(false) { // 速いけどだめな方法。add(Button) が add(Component) にマッチしない\\r\\n			try {\\r\\n				// 必要に応じて追加する\\r\\n				Class IntegerClass = Class.forName(\"java.lang.Integer\");\\r\\n				// メソッドを識別するためにクラスの配列をつくる\\r\\n				for(int i=0;i<cl.length;i++) {\\r\\n					cl[i] = argv[i].getClass();\\r\\n					if(cl[i].equals(IntegerClass)) cl[i] = Integer.TYPE;\\r\\n//					System.out.println(argv[i].getClass());\\r\\n				}\\r\\n			} catch (Exception e) {\\r\\n			}\\r\\n		\\r\\n			try {\\r\\n//				Method mm[] = obj.getClass().getMethods();\\r\\n//				for(int j=0;j<mm.length;j++) System.out.println(mm[j].getName());\\r\\n				Method m = obj.getClass().getMethod(methodName, cl);\\r\\n				r = m.invoke(obj, argv);\\r\\n			} catch (Exception e) {\\r\\n				e.printStackTrace();\\r\\n			}\\r\\n		} else { // 遅いけどよさそうな方法\\r\\n			Method mm[] = obj.getClass().getMethods();\\r\\n			for(int j=0;j<mm.length;j++) {\\r\\n				try {\\r\\n					if(!mm[j].getName().equals(methodName)) continue;\\r\\n					if(mm[j].getParameterTypes().length != argv.length) continue;\\r\\n					System.out.println(\"----------\"+ mm[j]);\\r\\n					r = mm[j].invoke(obj, argv);\\r\\n					break;\\r\\n				} catch (Exception e) {\\r\\n//					e.printStackTrace();\\r\\n				}\\r\\n			}\\r\\n		}\\r\\n		return r;\\r\\n	}\\r\\n	static Object doNew(String className) {\\r\\n		return doNew(className, null);\\r\\n	}\\r\\n	static Object doNew(String className, Object argv[]) {\\r\\n		Object r = null;\\r\\n		if(argv==null) argv=new Object[0];\\r\\n		try {\\r\\n			Class cl = Class.forName(className);\\r\\n			Constructor cn[] = cl.getConstructors();\\r\\n			for(int i=0;i<cn.length;i++) {\\r\\n				try {\\r\\n//					System.out.println(\"-----------------------\\n\"+cn[i]);\\r\\n					r = cn[i].newInstance(argv);\\r\\n					break;\\r\\n				} catch (Exception e) {\\r\\n//					e.printStackTrace();\\r\\n				}\\r\\n			}\\r\\n		} catch (Exception e) {\\r\\n			e.printStackTrace();\\r\\n		}\\r\\n		return r;\\r\\n	}\\r\\n}\\r\\n", 0, null);
	private static final Functor f0 = new Functor("java", 1, null);
}
