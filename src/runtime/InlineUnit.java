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
	public InlineCode inlineCode;
	
	/** カスタムガードクラスが使用可能の時、そのオブジェクトが入る。*/
	public CustomGuard customGuard;
	
	/** Hash { インラインコード文字列 => 一意な連番 } */
	public Map<String, Integer> codes = new HashMap<String, Integer>(); 
	/** codes の逆 */
	public List<String> code_of_id = new ArrayList<String>(); 
	
	/** List インライン宣言コード文字列 */
	public List<String> defs = new ArrayList<String>(); 
	
	/** 一意な連番。インラインコード文字列と1対1 */
	int codeCount = 0;
	
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
	
	public InlineUnit(String name) {
		this.name = name;
	}
	
	/**
	 * コードに対応する ID を返す。
	 * @param codeStr
	 * @return
	 */
	public int getCodeID(String codeStr) {
		return codes.containsKey(codeStr) ? (Integer) codes.get(codeStr) : -1;
	}
	
	/**
	 * ID に対応するコードを返す。
	 */
	public String getCode(int id) {
		if(id<0 || code_of_id.size() <= id) return null;
		return (String)code_of_id.get(id);
	}
	
	/**
	 * インラインアトムを登録しる。
	 * @param code アトム名
	 * @param type インライン実行アトム => EXEC ,  インライン宣言アトム => DEFINE
	 */
	public void register(String code, int type) {
		switch(type) {
		case EXEC:
			if(Env.debug>=Env.DEBUG_TRACE) Env.d("Register inlineCode to "+name+" : "+code);
			codes.put(code, codeCount);
			code_of_id.add(code);
			codeCount++;
			break;
		case DEFINE:
			if(Env.debug>=Env.DEBUG_TRACE) Env.d("Register inlineDefineCode to "+name+" : "+code);
			defs.add(code);
			break;
		}
	}

	/**
	 * コードを生成する。解釈実行時に利用する。
	 */
	public void makeCode() {
		if(isCached()) return;
		try {
			String className = className(name);
			File outputFile = srcFile(name);
			if(!outputFile.getParentFile().exists()) {
				outputFile.getParentFile().mkdirs();
			}
	//		Env.d("make inline code "+name);
	
			makeCode(null, className, outputFile, true);
		} catch (Exception e) {
			Env.d(e);
		}
	}
	
	/**
	 * コードを生成する。
	 */
	public void makeCode(String packageName, String className, File outputFile, boolean interpret) throws IOException {
		if(codes.isEmpty() && defs.isEmpty()) return;
		Iterator<String> i;
		PrintWriter p = new PrintWriter(new FileOutputStream(outputFile));

		//p.println("package runtime;");
		if (packageName != null) {
			p.println("package " + packageName + ";");
		}
		p.println("import runtime.*;");
		p.println("import java.util.*;");
		
		i = defs.iterator();
		PrintWriter defaultPW = p;
		while(i.hasNext()) {
			String s = i.next();
			s = s.replaceAll("\\/\\*__UNITNAME__\\*\\/", className(name));
			if (packageName != null) {
				s = s.replaceAll("\\/\\*__PACKAGE__\\*\\/", "package " + packageName + ";");
			}
//			System.out.println(s);
//			p.println(s);
			BufferedReader obr = new BufferedReader(new StringReader(s));
			String ss;
			while((ss=obr.readLine())!=null) {
				// //# で始まる行は、その行のそれ以降の内容をファイル名とみなし、その行以降を指定されたファイルに出力される。
				// その際、__UNITNAME__, __PACKAGE__ はそれぞれの内容に置換される。
				if(ss.startsWith("//#")) {
					if(p!=defaultPW) p.close();
					String fname = ss.substring(3);
					if(fname.equals("")) {
						p = defaultPW;
					} else {
						File ofile = new File(outputFile.getParentFile()+"/"+fname);
						Inline.othersToCompile.add(ofile.toString());
//						System.out.println(ofile);
						p = new PrintWriter(new FileOutputStream(ofile));
					}
				}
				p.println(ss);
			}
		}
		p = defaultPW;
		
		if (interpret) {
			p.println("public class "+className+" implements InlineCode {");
		} else {
			p.println("public class "+className+" {");
		}
		p.println("\tpublic boolean runGuard(String guardID, Membrane mem, Object obj) throws GuardNotFoundException {");
		p.println("\t\ttry {");
		p.println("\t\tString name = \""+className(name)+"CustomGuardImpl\";\n");
//		p.println("\t\tSystem.out.println(\"Load \"+name);\n");
		p.println("\t\t	CustomGuard cg=(CustomGuard)Class.forName(name).newInstance();\n");
//		p.println("\t\t	System.out.println(\"CG\"+cg);");
		p.println("\t\t	if(cg==null) throw new GuardNotFoundException();\n");
		p.println("\t\t	return cg.run(guardID, mem, obj);\n");
		p.println("\t\t} catch(GuardNotFoundException e) {");
		p.println("\t\t	throw new GuardNotFoundException();\n");
		p.println("\t\t} catch(ClassNotFoundException e) {");
		p.println("\t\t} catch(InstantiationException e) {");
		p.println("\t\t} catch(IllegalAccessException e) {");
		p.println("\t\t} catch(Exception e) {\n");
		p.println("\t\t	e.printStackTrace();\n");
		p.println("\t\t}\n");
		p.println("\t\tthrow new GuardNotFoundException();\n");
		p.println("\t}");
		
		if (interpret) {
			//InlineCode クラスのインスタンスとして使う
			p.println("\tpublic void run(Atom me, int codeID) {");
		} else {
			//直接呼び出すので、static でよい
			p.println("\tpublic static void run(Atom me, int codeID) {");
		}
		p.println("\t\tMembrane mem = me.getMem();");
		//p.println("\t\tEnv.p(\"-------------------------- \");");
		//p.println("\t\tEnv.d(\"Exec Inline \"+me+codeID);");
		p.println("\t\tswitch(codeID) {");
		i = codes.keySet().iterator();
		while(i.hasNext()) {
			String s = (String)i.next();
			int codeID = (Integer) (codes.get(s));
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
	}
	
	/****** 実行時に使う ******/
	
	/**
	 * 自分に対応するインラインコードクラスを読み込む。
	 */
	public void attach() {
		// jar で処理系を起動すると、勝手なファイルからクラスをロードすることができないみたい。
		String cname = className(name);
		FileClassLoader.addPath(srcPath(name));
		FileClassLoader cl = new FileClassLoader();
		Env.d("Try loading "+className(name));
		Object o;
		o = newInstance(cl, className(name));
		if (o instanceof InlineCode) inlineCode = (InlineCode)o;
		o = newInstance(cl, className(name)+"CustomGuardImpl");
		if (o instanceof CustomGuard) customGuard = (CustomGuard)o;
		o = newInstance(cl, "translated."+className(name)+"CustomGuardImpl");
		if (o instanceof CustomGuard) customGuard = (CustomGuard)o;
		o = newInstance(cl, "translated.module_"+FileNameWithoutExt(name)+"."+className(name)+"CustomGuardImpl");
		if (o instanceof CustomGuard) customGuard = (CustomGuard)o;
		
		if (inlineCode == null) {
			Env.d("Failed in loading "+cname);
		} else {
			Env.d(cname+" Loaded");
		}
	}
	private Object newInstance(FileClassLoader cl, String name) {
		try {
//			System.out.print(name+"   ");
			Object o = cl.loadClass(name).newInstance();
//			System.out.println("OK");
			return o;
		} catch (Exception e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//		} catch (IllegalAccessException e) {
//		} catch (InstantiationException e) {
//		} catch (NullPointerException e) {
		}
//		System.out.println("Fail");
		return null;
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
	
	public static String FileNameWithoutExt(String unitName) {
		// あやしい
		String o = new File(unitName).getName();
		if(unitName.endsWith(".lmn") || unitName.equals(InlineUnit.DEFAULT_UNITNAME)) {
			o = o.replaceAll("\\.lmn$", "");
			// クラス名に使えない文字を削除
			o = o.replaceAll("\\-", "");
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
