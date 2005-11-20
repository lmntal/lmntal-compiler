package translated.module_java;
import runtime.*;
import java.util.*;
/*inline_define*/
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
class Java {
	static Object doInvoke(Object obj, String methodName) {
		return doInvoke(obj, methodName, null);
	}
	static Object doInvoke(Object obj, String methodName, Object argv[]) {
		Object r = null;
		if(argv==null) argv=new Object[0];
		Class cl[] = new Class[argv.length];
		
		if(false) { // 速いけどだめな方法。add(Button) が add(Component) にマッチしない
			try {
				// 必要に応じて追加する
				Class IntegerClass = Class.forName("java.lang.Integer");
				// メソッドを識別するためにクラスの配列をつくる
				for(int i=0;i<cl.length;i++) {
					cl[i] = argv[i].getClass();
					if(cl[i].equals(IntegerClass)) cl[i] = Integer.TYPE;
//					System.out.println(argv[i].getClass());
				}
			} catch (Exception e) {
			}
		
			try {
//				Method mm[] = obj.getClass().getMethods();
//				for(int j=0;j<mm.length;j++) System.out.println(mm[j].getName());
				Method m = obj.getClass().getMethod(methodName, cl);
				r = m.invoke(obj, argv);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else { // 遅いけどよさそうな方法
			Method mm[] = obj.getClass().getMethods();
			for(int j=0;j<mm.length;j++) {
				try {
					if(!mm[j].getName().equals(methodName)) continue;
					if(mm[j].getParameterTypes().length != argv.length) continue;
					System.out.println("----------"+ mm[j]);
					r = mm[j].invoke(obj, argv);
					break;
				} catch (Exception e) {
//					e.printStackTrace();
				}
			}
		}
		return r;
	}
	static Object doNew(String className) {
		return doNew(className, null);
	}
	static Object doNew(String className, Object argv[]) {
		Object r = null;
		if(argv==null) argv=new Object[0];
		try {
			Class cl = Class.forName(className);
			Constructor cn[] = cl.getConstructors();
			for(int i=0;i<cn.length;i++) {
				try {
//					System.out.println("-----------------------\n"+cn[i]);
					r = cn[i].newInstance(argv);
					break;
				} catch (Exception e) {
//					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
}

public class SomeInlineCodejava {
	public static void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 0: {
			/*inline*/
//	System.out.println(me.nth(0));
	Object obj = Java.doNew(me.nth(0), util.Util.arrayOfList(me.getArg(1)));
//	System.out.println(obj);
	Atom n = mem.newAtom(new ObjectFunctor(obj));
	Atom nil = mem.newAtom(new Functor("nil", 1));
	mem.relink(nil, 0, me, 1);
	mem.relink(n, 0, me, 2);
	me.nthAtom(0).remove();
	me.remove();
	
			break; }
		case 1: {
			/*inline*/
//		System.out.println(me.nth(0));
	Object obj = me.nthAtom(0).getFunctor().getValue();
	Object res = Java.doInvoke(obj, me.nth(1), util.Util.arrayOfList(me.getArg(2)));
	if(res==null) res = "nil";
	Atom n = mem.newAtom(new ObjectFunctor(res));
	Atom nil = mem.newAtom(new Functor("nil", 1));
	mem.relink(nil, 0, me, 2);
	mem.relink(n, 0, me, 3);
	me.nthAtom(0).remove();
	me.nthAtom(1).remove();
	me.remove();
	
			break; }
		}
	}
}
