/*
 * 作成日: 2004/01/26
 *
 */
package test;


import java.util.Arrays;

import runtime.Env;

/**
 * わかったこと→ path.separator = (linux => : windows => ;)
 * 
 * @author hara
 *
 */
public class Test {

	public static void main(String[] args) {
		a();
//		b();
	}
	
	public static void b() {
		String s = "1 2 .. 3.. 4. 5 6 7";
		String r[] = s.split("[\\s.]+");
		System.out.println(Arrays.asList(r));
		
	}
	
	public static void a() {
		java.util.Enumeration e = System.getProperties().keys();
		while(e.hasMoreElements()) {
			String o = (String)e.nextElement(); 
			Env.p(o+"  =>  "+System.getProperty(o));
		}
	}
}
