/*
 * 作成日: 2004/01/26
 *
 */
package test;


import java.io.*;
import java.util.*;
import java.net.*;

import runtime.Env;

/**
 * わかったこと→ path.separator = (linux => : windows => ;)
 * 
 * @author hara
 *
 */
public class Test {

	public static void main(String[] args) {
//		a();
//		b();
//		c();
//		d();
		e();
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
