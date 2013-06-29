package runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import util.Util;

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
 *   INLINE  [1, <unitName>, 0]
 * </PRE>
 * 
 * @author hara
 */
public class Inline {
	// InlineUnit.name -> InlineUnit
	public static Map<String, InlineUnit> inlineSet = new HashMap<String, InlineUnit>();

	/** コンパイルプロセス。 */
	static Process cp;

	/** LMntal ライブラリを探すパス */
	static List<File> classPath = new ArrayList<File>();
	static {
		classPath.add(new File(System.getProperty("java.class.path")));
//		classPath.add(new File("."));
//		classPath.add(new File("lmntal.jar"));
	}

	/**
	 * ほかにコンパイルすべきファイル。
	 * //# 行により別ファイルに出力されたjavaファイルがこれに入る。
	 */
	public static ArrayList<String> othersToCompile = new ArrayList<String>();

	/****** コンパイル時に使う ******/

	/**
	 * InlineUnit の名前とクラスファイルの組を出力する。
	 */
	public static void showInlineList() {
		Util.println("Inline");
		Iterator<String> it = inlineSet.keySet().iterator();
		while (it.hasNext()) {
			Util.println(Util.quoteString(it.next(), '"'));
		}
	}

	/**
	 * コンパイルされたインラインコードを読み込んで InlineUnit に関連付ける
	 */
	public static void initInline() {
		try {
			if(cp!=null) {
				// コンパイルしてるプロセスのエラー出力を取得。
				// これをしないと、エラーがたくさんあるときデッドロックになって止まる！
				BufferedReader br = new BufferedReader(new InputStreamReader(cp.getErrorStream()));
				String el;
				while( (el=br.readLine())!=null ) Env.p(el);
				cp.waitFor();
				Env.d("Compile result :  "+cp.exitValue());
				if(cp.exitValue()==1) {
					Util.errPrintln("Failed in compiling.");
					//出力が冗長になるのでコメントアウトしました．2006.07.05 inui
//					System.err.println("Failed in compiling. Commandline was :");
//					System.err.println(compileCommand);
				} 
				cp = null;
			}
		} catch (Exception e) {
			Env.d(e);
		}
		for(Iterator<InlineUnit> ui = inlineSet.values().iterator();ui.hasNext();) {
			InlineUnit u = ui.next();
			u.attach();
		}
	}

	/**
	 * 指定したファンクタ名を持つコードID を返す。
	 * @param codeStr
	 * @return codeID
	 */
	public static int getCodeID(String unitName, String codeStr) {
		if(!inlineSet.containsKey(unitName)) return -1;
		return inlineSet.get(unitName).getCodeID(codeStr);
	}

	/**
	 * ここで必要に応じてインライン命令を登録する。
	 * すべてのアトムに対してこれを呼ぶべき。
	 * @param unitName
	 * @param funcName
	 */
	public static void register(String unitName, String funcName) {
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
		if(!inlineSet.containsKey(unitName)) inlineSet.put(unitName, new InlineUnit(unitName));
		inlineSet.get(unitName).register(funcName, type);
	}

	public static void terminate() {
		if(cp!=null) cp.destroy();
	}

	public static String getCode(int atomID, String unitName, int codeID) {
		if(!Inline.inlineSet.containsKey(unitName)) return null;
		InlineUnit iu = Inline.inlineSet.get(unitName);
		return iu.getCode(codeID);
	}

	static List<String> compileCommand = new ArrayList<String>();
	/**
	 * 必要に応じてコードの生成とコンパイルを行う。
	 */
	public static void makeCode() {
		Iterator<InlineUnit> it = inlineSet.values().iterator();
		while(it.hasNext()) {
			InlineUnit u = it.next();
			u.makeCode();
		}

		try {

			// 非同期。別プロセスでコンパイルしながら、現在のプロセスでほかの事をやる。
			// OS とかによってクラスパスの区切り文字が ; だったり : だったりするので動的に取得
			StringBuffer path = new StringBuffer("");
			String sep = System.getProperty("path.separator");
			for(Iterator<File> ci=classPath.iterator();ci.hasNext();) {
				path.append(ci.next());
				path.append(sep);
			}
			compileCommand.clear();			// n-kato
//			compileCommand.add("javaca");	// hara
			compileCommand.add("javac");	// n-kato
			compileCommand.add("-classpath");
			compileCommand.add(path.toString());

			boolean do_compile = false;
			for(Iterator<InlineUnit> iu = inlineSet.values().iterator();iu.hasNext();) {
				InlineUnit u = iu.next();
				if(!u.isCached()) {
					compileCommand.add(InlineUnit.srcFile(u.name).toString());
					InlineUnit.classFile(u.name).delete();
					do_compile = true;
				}
			}
			compileCommand.addAll(othersToCompile);
			if(do_compile) {
				Env.d("Compile command line: "+compileCommand);
				String cmd[] = new String[compileCommand.size()];
				for(int i=0;i<compileCommand.size();i++) {
					cmd[i] = compileCommand.get(i);
				}
				cp = Runtime.getRuntime().exec(cmd);
			}
		} catch (java.io.IOException e) {
			Util.errPrintln("\n*** Compile failed. javac not found on PATH. javac is necessary for compiling inline code. ***\n");
			Env.d(e);
		}
	}

	/****** 実行時に使う ******/

	/**
	 * インライン命令を実行する。
	 * @param atom 実行すべきアトム名を持つアトム
	 */
	public static void callInline(Atom atom, String unitName, int codeID) {
		Env.d("=> call Inline "+unitName);
//		System.out.println(inlineSet);
		if(inlineSet.containsKey(unitName)) {
			inlineSet.get(unitName).callInline(atom, codeID);
		}
	}
	/**
	 * ガードインライン命令を実行する。
	 * @param obj
	 * @return
	 */
	public static boolean callGuardInline(String guardID, Membrane mem, Object obj) {
//		System.out.println(Inline.inlineSet);
		Iterator<InlineUnit> it = Inline.inlineSet.values().iterator();
		boolean res = false;
		while(it.hasNext()) {
			InlineUnit iu = it.next();
//			System.out.println("iu.name "+iu.name);
			try {
//				res = iu.inlineCode.runGuard(guardID, mem, obj);
				if(iu.customGuard!=null) {
					res = iu.customGuard.run(guardID, mem, obj);
					return res;
				}
//				System.out.println("GUARD result = "+res);
			} catch(GuardNotFoundException e) {
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
