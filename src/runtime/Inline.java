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
	// InlineUnit.name -> InlineUnit
	public static Map inlineSet = new HashMap();
	
	/** インラインクラスが使用可能の時、そのオブジェクトが入る。*/
	public static InlineCode inlineCode;
	
	/** コンパイルプロセス。 */
	static Process cp;
	
	
	static List classPath = new ArrayList();
	static {
		classPath.add(".");
		classPath.add("lmntal.jar");
	}
	
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
			//Env.e("!! catch !! "+e.getMessage()+"\n"+Env.parray(Arrays.asList(e.getStackTrace()), "\n"));
		}
		if (inlineCode != null) { Env.d("MyInlineCode Loaded"); }
		else if (inlineCode == null) { Env.d("Failed in loading MyInlineCode"); }
	}
	
	/**
	 * LMNtal ソースファイル名に対応するクラス名を返す
	 * @param lmn LMNtal ソースファイル。パスを含んでもよい。
	 * @return
	 */
	public static String className_of_lmntalFilename(String lmn) {
		// パスを取得
		String path = lmn.replaceFirst("([\\/])[^\\/]+$", "$1");
		
		String o = lmn.replaceAll("^.*?[\\/]([^\\/]+)$", "$1");
		o = o.replaceAll("\\.lmn$", "");
		// クラス名に使えない文字を削除
		o = o.replaceAll("\\-", "");
		o = "Inline"+o;
		return o;
	}
	/**
	 * 指定したファンクタ名を持つコードID を返す。
	 * @param codeStr
	 * @return codeID
	 */
	public static int getCodeID(String unitName, String codeStr) {
		return getUnit(unitName).getCodeID(codeStr);
	}
	
	/**
	 * パース中にアトムが出てくると呼ばれる。
	 * ここで必要に応じてインライン命令を登録する。
	 * @param unitName
	 * @param funcName
	 */
	public static void add(String unitName, String funcName) {
		if(funcName==null) return;
		
		int type=0;
		if(funcName.startsWith("/*inline*/")) {
			type = InlineUnit.EXEC;
		} else if(funcName.startsWith("/*inline_define*/")) {
			type = InlineUnit.DEFINE;
		} else {
			return;
		}
		
		//登録
		getUnit(unitName).register(funcName, type);
	}
	
	static InlineUnit getUnit(String unitName) {
		if(!inlineSet.containsKey(unitName)) {
			inlineSet.put(unitName, new InlineUnit(unitName));
		}
		return (InlineUnit)inlineSet.get(unitName);
	}
	
	public static void compile() {
	}
	
	/**
	 * コードを生成する。
	 * TODO java ファイルの名前を、コンパイルするファイル名と同じにする。oneLiner や REPL の時は "-"
	 * TODO 更新されてたときだけコンパイルする。
	 */
	public static void makeCode() {
		Iterator it = inlineSet.values().iterator();
		while(it.hasNext()) {
			InlineUnit u = (InlineUnit)it.next();
			u.makeCode();
		}
		
		try {
			
			// 非同期。別プロセスでコンパイルしながら、現在のプロセスでほかの事をやる。
			// OS とかによってクラスパスの区切り文字が ; だったり : だったりするので動的に取得
			StringBuffer path = new StringBuffer("");
			String sep = System.getProperty("path.separator");
			Iterator ci=classPath.iterator();
			while(ci.hasNext()) {
				path.append(ci.next());
				path.append(sep);
			}
			StringBuffer srcs = new StringBuffer("");
			Iterator ik = inlineSet.keySet().iterator();
			while(ik.hasNext()) {
				InlineUnit u = getUnit((String)ik.next());
				if(!u.isCached()) srcs.append(className_of_lmntalFilename(u.name)+".java ");
			}
			String cmd = "javac -classpath "+path+" "+srcs;
			Env.d("Compile command line: "+cmd);
			cp = Runtime.getRuntime().exec(cmd);
		} catch (Exception e) {
			Env.d("!!! "+e+Arrays.asList(e.getStackTrace()));
		}
		
	}
	
	/**
	 * インライン命令を実行する。
	 * @param atom 実行すべきアトム名を持つアトム
	 */
	public static void callInline(Atom atom, String unitName, int codeID) {
		getUnit(unitName).callInline(atom, codeID);
	}
}
