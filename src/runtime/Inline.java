/*
 * 作成日: 2003/12/16
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
package runtime;

import java.io.*;
import java.util.*;

/**
 * @author pa
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
public class Inline {
	/** インラインクラスが使用可能の時、そのオブジェクトが入る。*/
	public static InlineCode inlineCode;
	
	static Process cp;
	
	static List code = new ArrayList(); 
	
	/**
	 * インラインを使うための初期化。実行時に呼ぶ。
	 *
	 */
	public static void initInline() {
		try {
			inlineCode = (InlineCode)Class.forName("MyInlineCode").newInstance();
		} catch (Exception e) {
			Env.p(e);
		}
		Env.p("inline = "+inlineCode);
	}
	
	/**
	 * パース中にアトムが出てくると呼ばれる。
	 * ここで必要に応じてインライン命令を登録する。
	 * @param atom
	 */
	public static void add(String src) {
		//if(src.startsWith("/*inline*/")) {
		if(src.startsWith("a")) {
			//登録
			Env.p("Register inlineCode : "+src);
			code.add(src);
		}
	}
	
	/**
	 * コードを生成する。
	 *
	 */
	public static void makeCode() {
		try {
			PrintWriter p = new PrintWriter(new FileOutputStream("MyInlineCode.java"));
			Env.p("make inline code "+code);
			
			//p.println("package runtime;");
			p.println("import runtime.*;");
			p.println("public class MyInlineCode implements InlineCode {");
			p.println("\tpublic void run(Atom a) {");
			p.println("\t\tEnv.p(a);");
			p.println("\t\tswitch(a.getName().hashCode()) {");
			Iterator i = code.iterator();
			while(i.hasNext()) {
				String s = (String)i.next();
				p.println("\t\tcase "+s.hashCode()+": ");
				p.println("\t\t\t/*"+s+"*/");
				p.println("\t\t\tSystem.out.println(\"=> call Inline "+s+" \");");
				p.println("\t\t\tbreak;");
			}
			p.println("\t\t}");
			p.println("\t}");
			p.println("}");
			p.close();
			
			cp = Runtime.getRuntime().exec("javac MyInlineCode.java");
			BufferedReader br = new BufferedReader(new InputStreamReader(cp.getErrorStream()));
			String el;
			while( (el=br.readLine())!=null ) {
				System.err.println(el);
			}
			cp.waitFor();
			Env.p("Compile result :  "+cp.exitValue());
			
		} catch (Exception e) {
			Env.p("!!! "+e.getMessage()+e.getStackTrace());
		}
		
	}
	
	/**
	 * インライン命令を実行する。
	 * @param atom 実行すべきアトム名を持つアトム
	 */
	public static void callInline(Atom atom) {
		if(inlineCode==null) return;
		//Env.p("=> call Inline "+atom.getName());
		inlineCode.run(atom);
	}
}
