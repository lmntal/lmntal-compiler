package runtime;

import java.util.*;
import java.io.*;

/**
 * １つのインラインコードのまとまりに対応するクラス。
 * まとまりとは、通常 .lmn のライブラリファイルのこと。
 * 
 * @author hara
 *
 */
public class InlineUnit {
	/** 識別名（ファイルならファイル名、出所不明の時は "-"） */
	String name;
	public static final String DEFAULT_UNITNAME = "-";
	
	/** インラインクラスが使用可能の時、そのオブジェクトが入る。*/
	public static InlineCode inlineCode;
	
	/** Hash { インラインコード文字列 => 一意な連番 } */
	public static Map codes = new HashMap(); 
	
	/** List インライン宣言コード文字列 */
	public static List defs = new ArrayList(); 
	
	/** 一意な連番。インラインコード文字列と1対1 */
	static int codeCount = 0;
	
	/** インライン実行アトム */
	static final int EXEC   = 0;
	
	/** インライン宣言行アトム */
	static final int DEFINE = 1;
	
	/****** コンパイル時に使う ******/
	
	/**
	 * class ファイルが最新かどうかを返す
	 */
	public boolean isCached() {
		// ?.lmn
		File src = new File(name);
		// ?.class
		File dst = classFile(name);
//		System.out.println(src.lastModified()+" "+src);
//		System.out.println(dst.lastModified()+" "+dst);
		
		// src が無いのは、"-" のとき
		if(!dst.exists() || !src.exists()) return false;
		return src.lastModified() <= dst.lastModified();
	}
	
	InlineUnit(String name) {
		this.name = name;
	}
	
	/**
	 * コードに対応する ID を返す。
	 * @param codeStr
	 * @return
	 */
	public int getCodeID(String codeStr) {
		return codes.containsKey(codeStr) ? ((Integer)codes.get(codeStr)).intValue() : -1;
	}
	
	/**
	 * インラインアトムを登録しる。
	 * @param code アトム名
	 * @param type インライン実行アトム => EXEC ,  インライン宣言アトム => DEFINE
	 */
	public void register(String code, int type) {
		switch(type) {
		case EXEC:
			if(Env.debug>=Env.DEBUG_TRACE) Env.d("Register inlineCode : "+code);
			codes.put(code, new Integer(codeCount++));
			break;
		case DEFINE:
			if(Env.debug>=Env.DEBUG_TRACE) Env.d("Register inlineDefineCode : "+code);
			defs.add(code);
			break;
		}
	}
	
	/**
	 * コードを生成する。
	 */
	public void makeCode() {
		if(isCached()) return;
		try {
			if(codes.isEmpty() && defs.isEmpty()) return;
			Iterator i;
			
			String className = className(name);
			File outputFile = srcFile(name);
			if(!outputFile.getParentFile().exists()) {
				outputFile.getParentFile().mkdirs();
			}
			PrintWriter p = new PrintWriter(new FileOutputStream(outputFile));
//			Env.d("make inline code "+name);
			
			//p.println("package runtime;");
			p.println("import runtime.*;");
			p.println("import java.util.*;");
			
			i = defs.iterator();
			while(i.hasNext()) {
				String s = (String)i.next();
				p.println(s);
			}
			p.println("public class "+className+" implements InlineCode {");
			p.println("\tpublic void run(Atom me, int codeID) {");
			p.println("\t\tAbstractMembrane mem = me.getMem();");
			//p.println("\t\tEnv.p(\"-------------------------- \");");
			//p.println("\t\tEnv.d(\"Exec Inline \"+me+codeID);");
			p.println("\t\tswitch(codeID) {");
			i = codes.keySet().iterator();
			while(i.hasNext()) {
				String s = (String)i.next();
				int codeID = ((Integer)(codes.get(s))).intValue();
				p.println("\t\tcase "+codeID+": {");
				//p.println("\t\t\t/*"+s.replaceAll("\\*\\/", "* /").replaceAll("\\/\\*", "/ *")+"*/");
				p.println("\t\t\t"+s);
				p.println("\t\t\tbreak; }");
			}
			p.println("\t\t}");
			p.println("\t}");
			p.println("}");
			p.close();
			
			Env.d("Class "+className+" written to "+outputFile);
		} catch (Exception e) {
			Env.d(e);
		}
	}
	
	/****** 実行時に使う ******/
	
	/**
	 * 自分に対応するインラインコードクラスを読み込む。
	 */
	public void attach() {
		// jar で処理系を起動すると、勝手なファイルからクラスをロードすることができないみたい。
		String cname = className(name);
		FileClassLoader cl = new FileClassLoader();
		Env.d("Try loading "+classFile(name));
		try {
			Object o = cl.loadClass(name).newInstance();
			if (o instanceof InlineCode) {
				inlineCode = (InlineCode)o;
			}
		} catch (Exception e) {
			//Env.e("!! catch !! "+e.getMessage()+"\n"+Env.parray(Arrays.asList(e.getStackTrace()), "\n"));
		}
		if (inlineCode == null) {
			Env.d("Failed in loading "+cname);
		} else {
			Env.d(cname+" Loaded");
		}
	}
	
	/**
	 * インライン命令を実行する。
	 * @param atom 実行すべきアトム名を持つアトム
	 */
	public void callInline(Atom atom, int codeID) {
		//Env.d(atom+" "+codeID);
		Env.d(" => call Inline "+atom.getName()+" "+codeID);
		if(inlineCode==null) return;
		inlineCode.run(atom, codeID);
	}

	/**
	 * インラインコードのソースファイルのパスを返す。最後の / は含まない。
	 * @param unitName
	 * @return PATH/
	 */
	public static File srcPath(String unitName) {
		File path;
		if(unitName.equals(DEFAULT_UNITNAME)) {
			path = new File(".");
		} else {
			path = new File(unitName).getParentFile();
			if(path==null) path = new File(".");
		}
		path = new File(path + "/.lmntal_inline");
		return path;
	}

	/**
	 * クラス名を返す
	 * @param unitName
	 * @return SomeClass
	 */
	public static String className(String unitName) {
		// あやしい
		String o = new File(unitName).getName();
		if(unitName.endsWith(".lmn") || unitName.equals(InlineUnit.DEFAULT_UNITNAME)) {
			o = o.replaceAll("\\.lmn$", "");
			// クラス名に使えない文字を削除
			o = o.replaceAll("\\-", "");
			o = "SomeInlineCode"+o;
		}
		return o;
	}
	
	/**
	 * インラインコードのソースファイル名を返す。パス付。
	 * @param unitName
	 * @return PATH/SomeClass.java
	 */
	public static File srcFile(String unitName) {
		return new File(srcPath(unitName)+"/"+className(unitName)+".java");
	}

	/**
	 * インラインコードのクラスファイル名を返す。パス付。
	 * @param unitName
	 * @return
	 */
	public static File classFile(String unitName) {
		return new File(srcPath(unitName) + "/" + className(unitName) + ".class");
	}
}
