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
	
	/** コンパイルプロセス。 */
	static Process cp;
	
	
	static List classPath = new ArrayList();
	static {
		classPath.add(new File("."));
		classPath.add(new File("lmntal.jar"));
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
		} catch (Exception e) {
			Env.e("!! catch !! "+e.getMessage()+"\n"+Env.parray(Arrays.asList(e.getStackTrace()), "\n"));
		}
		Iterator ui = inlineSet.values().iterator();
		while(ui.hasNext()) {
			InlineUnit u = (InlineUnit)ui.next();
			u.attach();
		}
	}
	
	/**
	 * インラインコードのソースファイルのパスを返す。最後の / も含む。
	 * @param unitName
	 * @return
	 */
	public static File path_of_unitName(String unitName) {
		if(unitName.equals(InlineUnit.DEFAULT_UNITNAME)) return new File("");
		File path = new File(unitName).getParentFile();
		path = new File(path + "/.lmntal_inline/");
		return path;
	}
	
	/**
	 * クラス名を返す
	 * @param unitName
	 * @return
	 */
	public static String className_of_unitName(String unitName) {
		String o = new File(unitName).getName();
		o = o.replaceAll("\\.lmn$", "");
		// クラス名に使えない文字を削除
		o = o.replaceAll("\\-", "");
		o = "SomeInlineCode"+o;
		return o;
	}
	/**
	 * インラインコードのソースファイル名を返す。パス付。
	 * @param unitName
	 * @return
	 */
	public static File fileName_of_unitName(String unitName) {
		return new File(Inline.path_of_unitName(unitName)+"/"+className_of_unitName(unitName)+".java");
	}
	
	/**
	 * 指定したファンクタ名を持つコードID を返す。
	 * @param codeStr
	 * @return codeID
	 */
	public static int getCodeID(String unitName, String codeStr) {
		if(!inlineSet.containsKey(unitName)) return -1;
		return ((InlineUnit)inlineSet.get(unitName)).getCodeID(codeStr);
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
			Iterator iu = inlineSet.values().iterator();
			boolean do_compile = false;
			while(iu.hasNext()) {
				InlineUnit u = (InlineUnit)iu.next();
				if(!u.isCached()) {
					srcs.append(fileName_of_unitName(u.name));
					srcs.append(" ");
					do_compile = true;
				}
			}
			if(do_compile) {
				String cmd = "javac -classpath "+path+" "+srcs;
				Env.d("Compile command line: "+cmd);
				cp = Runtime.getRuntime().exec(cmd);
			}
		} catch (Exception e) {
			Env.d("!!! "+e+Arrays.asList(e.getStackTrace()));
		}
	}
	
	/**
	 * インライン命令を実行する。
	 * @param atom 実行すべきアトム名を持つアトム
	 */
	public static void callInline(Atom atom, String unitName, int codeID) {
		Env.d("=> call Inline "+unitName);
//		System.out.println(inlineSet);
		if(inlineSet.containsKey(unitName)) {
			((InlineUnit)inlineSet.get(unitName)).callInline(atom, codeID);
		}
	}
}
