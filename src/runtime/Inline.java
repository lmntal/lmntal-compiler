/*
 * 作成日: 2003/12/16
 *
 */
package runtime;

import java.io.*;
import java.util.*;

/**
 * インラインの方針<BR>
 * 
 * <UL>
 * <LI>"/＊inline＊/" で始まるファンクタ名を持つアトムをインラインコードとして扱う。
 * 
 * <LI>"/＊inline_define＊/" で始まるファンクタ名を持つアトムをインライン宣言コードとして扱う。
 * 
 * <LI>インライン宣言コードは、まとめて大域的に宣言される。クラス宣言など、宣言的なものはここに書ける。
 * 
 * <LI>あるインラインコードの実行は、
 * そのコードが右辺に含まれるルールを適用した直後のタイミングで実行される。
 * 
 * <LI>対応する中間命令は INLINE である。
 * 
 * <LI>リンク先のアトムをいじれるように、すべての NEWATOM, LINK などの操作が終わってから
 * INLINE 命令を発行する。
 * 
 * <LI>中間命令の例
 * </UL>
 * <PRE>
 *   NEWATOM [1, 0, abc_0]
 *   ...いろいろ
 *   INLINE  [1, 0]
 * </PRE>
 * 
 * @author hara
 */
public class Inline {
	/** インラインクラスが使用可能の時、そのオブジェクトが入る。*/
	public static InlineCode inlineCode;
	
	/** コンパイルプロセス。 */
	static Process cp;
	
	/** Hash { インラインコード文字列 -> 一意な連番 } */
	public static Map code = new HashMap(); 
	
	/** List インライン宣言コード文字列 */
	public static List defs = new ArrayList(); 
	
	/** 一意な連番 */
	static int codeCount = 0;
	
	/**
	 * インラインを使うための初期化。実行時に呼ぶ。
	 *
	 */
	public static void initInline() {
		try {
			if(cp!=null) {
				// コンパイルしてるプロセスのエラー出力を取得。
				// これをしないと、エラーがたくさんあるときデッドロックになって止まる！
				BufferedReader br = new BufferedReader(new InputStreamReader(cp.getErrorStream()));
				String el;
				while( (el=br.readLine())!=null ) {
					System.err.println(el);
				}
				cp.waitFor();
				Env.d("Compile result :  "+cp.exitValue());
				cp = null;
			}
			// jar で処理系を起動すると、勝手なファイルからクラスをロードすることができないみたい。
			ClassLoader cl = new FileClassLoader();
			Object o = cl.loadClass("MyInlineCode").newInstance();
			if (o instanceof InlineCode) {
				inlineCode = (InlineCode)o;
			}
			//inlineCode = (InlineCode)Class.forName("MyInlineCode").newInstance();
			//Env.d(Class.forName("MyInlineCode").getField("version"));
		} catch (Exception e) {
			Env.d(e);
		}
		Env.d("inline = "+inlineCode);
	}
	
	/**
	 * 指定したファンクタ名を持つコードID を返す。
	 * @param name
	 * @return codeID
	 */
	public static int getCodeID(String name) {
		try {
			return ((Integer)code.get(name)).intValue();
		} catch (Exception e) {
			return -1;
		}
	}
	/**
	 * パース中にアトムが出てくると呼ばれる。
	 * ここで必要に応じてインライン命令を登録する。
	 * @param atom
	 */
	public static void add(String src) {
		if(src.startsWith("/*inline*/")) {
		//if(src.startsWith("a")) {
			//登録
			Env.d("Register inlineCode : "+src);
			code.put(src, new Integer(codeCount++));
		} else if(src.startsWith("/*inline_define*/")) {
			//登録
			Env.d("Register inlineDefineCode : "+src);
			defs.add(src);
		}
	}
	
	/**
	 * コードを生成する。
	 *
	 */
	public static void makeCode() {
		try {
			if(code.isEmpty() && defs.isEmpty()) return;
			Iterator i;
			
			PrintWriter p = new PrintWriter(new FileOutputStream("MyInlineCode.java"));
			Env.d("make inline code "+code);
			
			//p.println("package runtime;");
			p.println("import runtime.*;");
			
			i = defs.iterator();
			while(i.hasNext()) {
				String s = (String)i.next();
				p.println(s);
			}
			p.println("public class MyInlineCode implements InlineCode {");
			p.println("\tpublic static String version=\"static string.\";");
			p.println("\tpublic void run(Atom me, int codeID) {");
			//p.println("\t\tEnv.p(\"-------------------------- \");");
			//p.println("\t\tEnv.d(\"Exec Inline \"+me+codeID);");
			p.println("\t\tswitch(codeID) {");
			i = code.keySet().iterator();
			while(i.hasNext()) {
				String s = (String)i.next();
				int codeID = ((Integer)(code.get(s))).intValue();
				p.println("\t\tcase "+codeID+": ");
				//p.println("\t\t\t/*"+s.replaceAll("\\*\\/", "* /").replaceAll("\\/\\*", "/ *")+"*/");
				p.println("\t\t\t"+s);
				p.println("\t\t\tbreak;");
			}
			p.println("\t\t}");
			p.println("\t}");
			p.println("}");
			p.close();
			
			// 非同期。別プロセスでコンパイルしながら、現在のプロセスでほかの事をやる。
			cp = Runtime.getRuntime().exec("javac -classpath .;lmntal.jar MyInlineCode.java");
		} catch (Exception e) {
			Env.d("!!! "+e.getMessage()+Arrays.asList(e.getStackTrace()));
		}
		
	}
	
	/**
	 * インライン命令を実行する。
	 * @param atom 実行すべきアトム名を持つアトム
	 */
	public static void callInline(Atom atom, int codeID) {
		//Env.d(atom+" "+codeID);
		if(inlineCode==null) return;
		//Env.d("=> call Inline "+atom.getName());
		inlineCode.run(atom, codeID);
	}
}
