/*
 * 作成日: 2004/01/26
 *
 */
package test;


import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;

import runtime.Env;

/**
 * わかったこと→ path.separator = (linux => : windows => ;)
 * 
 * @author hara
 *
 */
public class Test {

	public static void main(String[] args) throws Exception {
//		a();
//		b();
//		c();
//		d();
//		e();
//		f();
		g();
	}
	
	public static void g() throws Exception {
		if(false) {
			Frame fr = new Frame("test");
			fr.setSize(200,300);
			fr.show();
		}
		if(true) {
			Class cl = Class.forName("java.awt.Frame");
			Method[] m = cl.getMethods();
			Class[] cls = new Class[2];
			cls[0] = cls[1] = Integer.TYPE;
			Method tm = cl.getMethod("setSize", cls);
			System.out.println(tm);
			for(int i=0;i<m.length;i++) {
				System.out.println(m[i]);
				Class[] pt = m[i].getParameterTypes();
				for(int j=0;j<pt.length;j++) {
					System.out.print(" "+pt[j]);
				}
			}
		}
		
		if(false) {
			Object arg[] = new Object[1];
			arg[0] = "Hello";
			Object o = Java.doNew("java.awt.Frame", arg);
			System.out.println("GENERATED : "+o);
			Java.doInvoke(o, "show", null);
		}
	}
	public static void f() {
		System.out.println((int)1.3);
	}
	public static void e() {
		System.out.println(get("http://yahoo.co.jp"));
	}
	public static void d() {
		try {
			File f = new File("abc/build.xml/");
			System.out.println(f.getPath());
		} catch (Exception e) {
		}
	}
	public static void c() {
		String s = "abc/def/g.txt";
		s = s.replaceAll(".*?[\\/]([^\\/]+)$", "$1");
		System.out.println(s);
	}
	public static void b() {
		String s = "1 2 .. 3.. 4. 5 6 7";
		String r[] = s.split("[\\s.]+");
		System.out.println(Arrays.asList(r));
		
	}
	public static void a() {
		Iterator it = System.getProperties().keySet().iterator();
		while(it.hasNext()) {
			String o = (String)it.next();
			Env.p(o+"  =>  "+System.getProperty(o));
		}
	}
	public static String get(String u) {
		try {
			URL url = new URL(u);
			HttpURLConnection hc = (HttpURLConnection)url.openConnection();
			hc.setRequestProperty("USER-AGENT", "DoCoMo/1.0/D505i/c10");
			hc.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream(), "JISAutoDetect"));
			String s;
			StringBuffer b=new StringBuffer();
			while((s=br.readLine())!=null) {
				b.append(s);
			}
			System.out.println(b);
			return b.toString()
			.replaceAll("<script>.*?</script>", "")
			.replaceAll("<br>", "\n")
			.replaceAll("<.*?>", "")
			.replaceAll("&#\\d{5}", "")
			.replaceAll("&.*;"," ");
		} catch (Exception e) {
			return null;
		}
	}
}

class Java {
	static Object doInvoke(Object obj, String methodName) {
		return doInvoke(obj, methodName, null);
	}
	static Object doInvoke(Object obj, String methodName, Object argv[]) {
		Object r = null;
		if(argv==null) argv=new Object[0];
		Class cl[] = new Class[argv.length];
		for(int i=0;i<cl.length;i++) cl[i] = argv.getClass();
		try {
			Method m = obj.getClass().getMethod(methodName, cl);
			r = m.invoke(obj, argv);
		} catch (Exception e) {
			e.printStackTrace();
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
//			e.printStackTrace();
		}
		return r;
	}
}
