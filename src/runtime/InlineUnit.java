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
	/** 識別名（ファイル名） */
	String name;
	
	/** インラインクラスが使用可能の時、そのオブジェクトが入る。*/
	public static InlineCode inlineCode;
	
	Date classDate;
	Date srcDate;
	
	/** Hash { インラインコード文字列 -> 一意な連番 } */
	public static Map codes = new HashMap(); 
	
	/** List インライン宣言コード文字列 */
	public static List defs = new ArrayList(); 
	
	/** 一意な連番 */
	static int codeCount = 0;
	
	static final int EXEC   = 0;
	static final int DEFINE = 1;
	
	/****** コンパイル時に使う ******/
	
	public boolean isCached() {
		return false;
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
	
	public void register(String code, int type) {
		switch(type) {		case EXEC:
			Env.d("Register inlineCode : "+code);
			codes.put(code, new Integer(codeCount++));
			break;
		case DEFINE:
			Env.d("Register inlineDefineCode : "+code);
			defs.add(code);
			break;
		}
	}
	
	/**
	 * コードを生成する。
	 */
	public void makeCode() {
		try {
			if(codes.isEmpty() && defs.isEmpty()) return;
			Iterator i;
			
			String className = Inline.className_of_lmntalFilename(name);
			PrintWriter p = new PrintWriter(new FileOutputStream(className+".java"));
			Env.d("make inline code "+codes);
			
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
			
		} catch (Exception e) {
			Env.d("!!! "+e+Arrays.asList(e.getStackTrace()));
		}
	}
	
	/****** 実行時に使う ******/
	
	/**
	 * インライン命令を実行する。
	 * @param atom 実行すべきアトム名を持つアトム
	 */
	public void callInline(Atom atom, int codeID) {
		//Env.d(atom+" "+codeID);
		if(inlineCode==null) return;
		//Env.d("=> call Inline "+atom.getName());
		inlineCode.run(atom, codeID);
	}
	
}
