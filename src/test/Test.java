/*
 * 作成日: 2004/01/26
 *
 */
package test;


import java.io.*;
import java.util.*;

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
		d();
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
	
}
