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
 *   INLINE  [1, <unitName>, 0]
 * </PRE>
 * 
 * @author hara
 */
public class Inline {
	// InlineUnit.name -> InlineUnit
	public static Map inlineSet = new HashMap();
	
	/** コンパイルプロセス。 */
	static Process cp;
	
	/** LMntal ライブラリを探すパス */
	static List classPath = new ArrayList();
	static {
		classPath.add(new File(System.getProperty("java.class.path")));
//		classPath.add(new File("."));
//		classPath.add(new File("lmntal.jar"));
	}
	
	/****** コンパイル時に使う ******/
	
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
					System.out.println("Failed in compiling. Commandline was :");
					System.out.println(compileCommand);
				} 
				cp = null;
			}
		} catch (Exception e) {
			Env.d(e);
		}
		for(Iterator ui = inlineSet.values().iterator();ui.hasNext();) {
			InlineUnit u = (InlineUnit)ui.next();
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
		return ((InlineUnit)inlineSet.get(unitName)).getCodeID(codeStr);
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
		((InlineUnit)inlineSet.get(unitName)).register(funcName, type);
	}
	
	public static void terminate() {
		if(cp!=null) cp.destroy();
	}
	
	static List compileCommand = new ArrayList();
	/**
	 * 必要に応じてコードの生成とコンパイルを行う。
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
			for(Iterator ci=classPath.iterator();ci.hasNext();) {
				path.append(ci.next());
				path.append(sep);
			}
			compileCommand.add("javac");
			compileCommand.add("-classpath");
			compileCommand.add(path);
			
			StringBuffer srcs = new StringBuffer("");
			boolean do_compile = false;
			for(Iterator iu = inlineSet.values().iterator();iu.hasNext();) {
				InlineUnit u = (InlineUnit)iu.next();
				if(!u.isCached()) {
					compileCommand.add(InlineUnit.srcFile(u.name));
					InlineUnit.classFile(u.name).delete();
					do_compile = true;
				}
			}
			if(do_compile) {
				Env.d("Compile command line: "+compileCommand);
				String cmd[] = new String[compileCommand.size()];
				for(int i=0;i<compileCommand.size();i++) {
					cmd[i] = compileCommand.get(i).toString();
				}
				cp = Runtime.getRuntime().exec(cmd);
			}
		} catch (Exception e) {
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
			((InlineUnit)inlineSet.get(unitName)).callInline(atom, codeID);
		}
	}
}
