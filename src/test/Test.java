/*
 * 作成日: 2004/01/26
 *
 */
package test;


import java.awt.Button;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JFrame;

import runtime.Env;
import util.Util;

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
//		g();
//		h();
//		i();
//		j();
//		k();
		l();
	}
	
	public static void l() throws Exception {
		BufferedReader br0 = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br1 = new BufferedReader(new FileReader("demo.sh"));
		Util.println(br0.getClass());
		Util.println(br1.getClass());
	}
	public static void k()  {
		byte b;
		b = (byte)255;
		Util.println(b&0xff);
	}
	public static void j() {
		Util.println("aHIJc___123".replaceAll("a(.*?)c", ":$1:$1:$1:"));
	}
	public static void i() {
		JFrame fr = new JFrame("test");
		fr.setSize(200,300);
		fr.getContentPane().add(new Button("hello"));
		fr.setVisible(true);
	}
	public static void h() {
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		pw.println("hello");
		pw.flush();
	}
	public static void g() throws Exception {
		if(false) {
			Frame fr = new Frame("test");
			fr.setSize(200,300);
			fr.setVisible(true);
		}
		if(true) {
			Class cl = Class.forName("java.awt.Frame");
			Method[] m = cl.getMethods();
			Class[] cls = new Class[2];
			cls[0] = cls[1] = Integer.TYPE;
			Method tm = cl.getMethod("setSize", cls);
			Util.println(tm);
			for(int i=0;i<m.length;i++) {
				Util.println(m[i]);
				Class[] pt = m[i].getParameterTypes();
				for(int j=0;j<pt.length;j++) {
					Util.print(" "+pt[j]);
				}
			}
		}
		
		if(false) {
			Object arg[] = new Object[1];
			arg[0] = "Hello";
			Object o = Java.doNew("java.awt.Frame", arg);
			Util.println("GENERATED : "+o);
			Java.doInvoke(o, "show", null);
		}
	}
	public static void f() {
		Util.println((int)1.3);
	}
	public static void e() {
		Util.println(get("http://yahoo.co.jp"));
	}
	public static void d() {
		try {
			File f = new File("abc/build.xml/");
			Util.println(f.getPath());
		} catch (Exception e) {
		}
	}
	public static void c() {
		String s = "abc/def/g.txt";
		s = s.replaceAll(".*?[\\/]([^\\/]+)$", "$1");
		Util.println(s);
	}
	public static void b() {
		String s = "1 2 .. 3.. 4. 5 6 7";
		String r[] = s.split("[\\s.]+");
		Util.println(Arrays.asList(r));
		
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
