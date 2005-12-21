/*
 * ※重要※このファイルの内容を修正した場合、InterpretedRuleset.java にも同様の修正を加えること。
 */

package compile;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import runtime.Env;
import runtime.FloatingFunctor;
import runtime.FrontEnd;
import runtime.Functor;
import runtime.Inline;
import runtime.InlineUnit;
import runtime.Instruction;
import runtime.InstructionList;
import runtime.IntegerFunctor;
import runtime.InterpretedRuleset;
import runtime.ObjectFunctor;
import runtime.Rule;
import runtime.StringFunctor;
import runtime.SystemRulesets;
import util.Util;

/**
 * 中間命令列からJavaへの変換を行うクラス。
 * 1 つのルールセットを 1 つのクラスに変換する。
 * TODO 特殊ファンクタの処理
 * @author mizuno
 */
public class Translator {
	private static boolean fStandardLibrary;
	private static int success, count;
	private static boolean gen_all_lib;
	
	/**
	 * std_lib.jar を作るための main 関数
	 * @param args FrontEnd に渡すオプション
	 */
	public static void main(String[] args) throws Exception {
		gen_all_lib = true;
		FrontEnd.processOptions(args);
		Env.fInterpret = false;
		Env.fLibrary = true;

		// public/*.lmn から std_lib.jar を作成
		fStandardLibrary = true;
		fKeepSource = true;
		baseDirName = "public/tmp";

		File publicDir = new File("public");
		if (!publicDir.exists() || !publicDir.isDirectory()) {
			System.err.println("public directory does not exists. (" + publicDir.getCanonicalPath() + ")");
			System.exit(1);
		}
		File outDir = new File(baseDirName);
		if (!outDir.exists()) outDir.mkdir();
		File transDir = new File(outDir, "translated");
		if (!transDir.exists()) transDir.mkdir();
		
		ArrayList l = new ArrayList();
		l.add(null);

		String[] files = publicDir.list();
		for (int i = 0; i < files.length; i++) {
			if (!files[i].endsWith(".lmn")) continue;
			File f = new File(publicDir, files[i]);
			if (f.isDirectory()) continue;
			long modified = f.lastModified();
			
			File moduleClass = new File(transDir, "Module_" + files[i].substring(0, files[i].length() - 4) + ".class");
			//修正されていなければ何もしない
			if (moduleClass.exists() && moduleClass.lastModified() >= modified) continue; 

			Inline.inlineSet.clear();
			SystemRulesets.clear();
			l.set(0, "public/" + files[i]);
			System.out.println("processing " + l.get(0));
			count++;
			FrontEnd.run(l);
		}
		baseDir = outDir;
		genJAR("std_lib.jar");
		
		// src/*.lmn から通常のライブラリの作成
		fStandardLibrary = false;
		fKeepSource = false;
		baseDirName = null;
		baseDir = null;
		
		File srcDir = new File("src");
		files = srcDir.list();
		for (int i = 0; i < files.length; i++) {
			if (!files[i].endsWith(".lmn")) continue;
			File f = new File(srcDir, files[i]);
			if (f.isDirectory()) continue;
			long modified = f.lastModified();
			
			File jar = new File(files[i].substring(0, files[i].length() - 4) + ".jar");
			//修正されていなければ何もしない
			if (jar.exists() && jar.lastModified() >= modified) continue;
			
			Inline.inlineSet.clear();
			SystemRulesets.clear();
			l.set(0, "src/" + files[i]);
			System.out.println("processing " + l.get(0));
			count++;
			FrontEnd.run(l);
		}
		System.out.println("success : " + success + ", failure : " + (count - success));
	}
	
	private String className;
	private File outputFile;
	private BufferedWriter writer;
	private InterpretedRuleset ruleset;
	/**出力する InstructionList の集合。重複を防ぐために利用する。*/
	private HashSet instLists = new HashSet();
	/**処理すべき InstructionList*/
	private ArrayList instListsToTranslate = new ArrayList();
	/**この Ruleset 内で利用している Functor についての、Functor -> 変数名*/
	private HashMap funcVarMap = new HashMap();

	/** 作業用一時ディレクトリ*/
	private static File baseDir;
	/** モジュールクラスを生成するディレクトリ */
	private static File moduleDir;
	/** 変換したファイルをおくディレクトリ */
	private static File dir;
	/** 変換したファイルのパッケージ名 */
	private static String packageName;
	/** LMNtalソースファイル名 */
	private static String sourceName;
	/** 変換後の Java ソースを削除するかどうか */
	public static boolean fKeepSource = false;
	/** 一時ディレクトリとして利用するディレクトリ名 */
	public static String baseDirName;

	//////////////////////////////////////////////////////////////////
	// static メソッド
	
	/**
	 * 指定されたルールセットに対応するクラス名を取得する。
	 * @param ruleset ルールセット
	 * @return 変換後のクラス名
	 */
	public static String getClassName(InterpretedRuleset ruleset) {
		return "Ruleset" + ruleset.getId();
	}
	
	/**
	 * Translator を初期化する。
	 * 同一のソースに対する一連の Translator の前に呼び出す必要がある。
	 * @param unitName LMNtalソースファイル名
	 * @return 初期化に成功した場合はtrue
	 */
	public static boolean init(String unitName) throws IOException {
		if (unitName.equals(InlineUnit.DEFAULT_UNITNAME)) {
			sourceName = "a";
		} else {
			sourceName = new File(unitName).getName();
			if (sourceName.endsWith(".lmn")) {
				sourceName = sourceName.substring(0, sourceName.length() - 4);
			}
		}
		//作業用ディレクトリ作成
		if (baseDirName != null) {
			//ユーザー指定
			baseDir = new File(baseDirName);
			if (!baseDir.exists() && !baseDir.mkdir()) {
				Env.e("Failed to create temporary directory");
				return false;
			}
		} else {
			//ユーザー指定がない場合は、システムの一時ディレクトリに作成
			String s = System.getProperty("java.io.tmpdir");
			int i = 1;
			while (true) {
				File f = new File(s, "lmn_translate" + i);
	//			Env.d("trying to create temporary directory : " + f);
				if (f.mkdir()) {
					Env.d("Using temporary directory : " + f);
					baseDir = f.getCanonicalFile(); //Windows て8.3形式の名前になっているのがなんか嫌なので正規化している
					break;
				}
				i++;
			}
		}
		moduleDir = new File(baseDir, "translated");
		moduleDir.mkdir();
		if (Env.fLibrary) {
			//パッケージ名として使えない文字はライブラリ名として使うな、ということで。
			dir = new File(moduleDir, "module_" + sourceName);
			dir.mkdir();
			packageName = "translated." + "module_" + sourceName;
		} else {
			dir = moduleDir;
			packageName = "translated";
		}
		return true;
	}
	
	private static void genLoadModuleFunc(Writer writer, compile.structure.Membrane m) throws IOException {
		writer.write("	public static void loadUserDefinedSystemRuleset() {\n");
		Iterator it = SystemRulesets.userDefinedSystemRulesetIterator();
		while (it.hasNext()) {
			writer.write("		runtime.SystemRulesets.addUserDefinedSystemRuleset(" + packageName + "." + getClassName((InterpretedRuleset)it.next()) + ".getInstance());\n");
		}

		//利用しているモジュールに対して再帰呼び出し
		ArrayList modules = new ArrayList();
		Module.getNeedModules(m, modules);
		for (int i = 0; i < modules.size(); i++) {
			writer.write("		loadSystemRulesetFromModule(\"" + modules.get(i) + "\");\n");
		}
		writer.write("	}\n");

		//TODO このメソッドは動的にな要素がないので、SystemRulesets クラスに作るべきだった。
		writer.write("	private static void loadSystemRulesetFromModule(String moduleName) {\n");
		writer.write("		try {\n");
		writer.write("			Class c = Class.forName(\"translated.Module_\" + moduleName);\n");
		writer.write("			java.lang.reflect.Method method = c.getMethod(\"loadUserDefinedSystemRuleset\", null);\n");
		writer.write("			method.invoke(null, null);\n");
		writer.write("		} catch (ClassNotFoundException e) {\n");
		writer.write("		} catch (NoSuchMethodException e) {\n");
		writer.write("		} catch (IllegalAccessException e) {\n");
		writer.write("		} catch (java.lang.reflect.InvocationTargetException e) {\n");
		writer.write("		}\n");
		writer.write("	}\n");
	}

	/**
	 * メイン関数を生成する。
	 * @param initialRuleset 初期データ生成ルールセット
	 * @param m 全世界が入った膜構造
	 * @throws IOException IOエラーが発生した場合
	 */
	public static void genMain(InterpretedRuleset initialRuleset, compile.structure.Membrane m) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(baseDir, "Main.java")));
		writer.write("public class Main {\n");
		genLoadModuleFunc(writer, m);
		writer.write("	public static void main(String[] args) {\n");
		writer.write("		runtime.FrontEnd.processOptions(args);\n");
		writer.write("		loadUserDefinedSystemRuleset();\n");
		writer.write("		runtime.FrontEnd.run(translated." + getClassName(initialRuleset) + ".getInstance());\n"); //todo 引数の処理
		writer.write("	}\n");
		writer.write("}\n");
		writer.close();
	}
	/**
	 * モジュールクラスを生成する。
	 * @param m 全世界が入った膜構造
	 * @throws IOException IOエラーが発生した場合
	 */
	public static void genModules(compile.structure.Membrane m) throws IOException {
		Iterator moduleIterator = Module.memNameTable.keySet().iterator();
		while (moduleIterator.hasNext()) {
			String moduleName = (String)moduleIterator.next();
			if (Env.fLibrary && !moduleName.equals(sourceName)) {
				continue;
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(moduleDir, "Module_" + moduleName + ".java")));
			writer.write("package translated;\n");
			if (Env.fLibrary) {
				writer.write("import translated.module_" + sourceName + ".*;\n");
				writer.write("import runtime.SystemRulesets;\n");
			}
			writer.write("import runtime.Ruleset;\n");
			writer.write("public class Module_" + moduleName + "{\n");
			writer.write("	private static Ruleset[] rulesets = {");
			boolean first = true;
			Iterator rulesetIterator = ((compile.structure.Membrane)Module.memNameTable.get(moduleName)).rulesets.iterator();
			while (rulesetIterator.hasNext()) {
				if (!first) writer.write(", ");
				writer.write(getClassName((InterpretedRuleset)rulesetIterator.next()) + ".getInstance()");
			}
			writer.write("};\n");
			writer.write("	public static Ruleset[] getRulesets() {\n");
			writer.write("		return rulesets;\n");
			writer.write("	}\n");
			if (Env.fLibrary) {
				genLoadModuleFunc(writer, m);
			}
			writer.write("}\n");
			writer.close();
		}
	}
	/**
	 * インラインコードを生成する。
	 * @throws IOException IOエラーが発生した場合
	 * @return コンパイルに成功したらtrue
	 */
	public static boolean genInlineCode() throws IOException {
		if (Inline.inlineSet.size() > 1) {
			Env.e("Translator supports only one InlineUnit.");
			return false;
		}
		Iterator it = Inline.inlineSet.values().iterator();
		if (it.hasNext()) {
			InlineUnit iu = (InlineUnit)it.next();
			File f = new File(dir, InlineUnit.className(sourceName + ".lmn") + ".java");
			iu.makeCode(packageName, InlineUnit.className(sourceName + ".lmn"), f, false);
			//エラーメッセージを出力するため、インラインコードは先にコンパイルする。
			return compile(f, !gen_all_lib);
		}
		return true;
	}

	/**
	 * 変換した Java ソースファイルをコンパイルし、JARファイルを生成する。
	 * @throws IOException IOエラーが発生した場合
	 */
	public static void genJAR() throws IOException {
		//コンパイル
		if (!Env.fLibrary) {
			if (!compile(new File(baseDir, "Main.java"), Env.debug > 0)) {
				return;
			}
		}
		Iterator moduleIterator = Module.memNameTable.keySet().iterator();
		while (moduleIterator.hasNext()) {
			String moduleName = (String)moduleIterator.next();
			if (Env.fLibrary && !moduleName.equals(sourceName)) {
				continue;
			}
			if (!compile(new File(moduleDir, "Module_" + moduleName + ".java"), Env.debug > 0)) {
				return;
			}
		}

		success++;
		//JARの生成
		if (!fStandardLibrary)
			genJAR(sourceName + ".jar");
	}
	private static void genJAR(String outName) throws IOException {
		Manifest mf = new Manifest();
		Attributes att = mf.getMainAttributes();
		att.put(Attributes.Name.MANIFEST_VERSION, "1.0");
		JarOutputStream out = new JarOutputStream(new BufferedOutputStream(new FileOutputStream(outName)), mf);
		putToJar(out, "", baseDir);
		out.close();
	}

	/**
	 * 一時ファイルの削除。
	 * init メソッド内で作成した一時ディレクトリを再帰的に削除します。
	 */
	public static void deleteTemporaryFiles() {
		if (fKeepSource) return;
		if (baseDir != null && !delete(baseDir)) {
			Env.warning("failed to delete temprary files");
		}
	}
	
	/**
	 * 変換したファイルをコンパイルする。
	 * @param file コンパイルするファイル
	 * @param outputErrorMessage コンパイルエラー発生時にエラーメッセージを出力するかどうか
	 * @throws IOException IOエラーが発生した場合
	 * @return コンパイルに成功した場合true
	 */
	private static boolean compile(File file, boolean outputErrorMessage) throws IOException {
		String classpath = System.getProperty("java.class.path");
		String[] command = {"javac", "-classpath", classpath, "-sourcepath", baseDir.getPath(), file.getPath(), "-source", "1.4", "-target", "1.4"}; //1.5 の機能を使う場合は最後の 4 つをコメントアウトしてください。
		Process javac = Runtime.getRuntime().exec(command);
		javac.getInputStream().close();
		if (outputErrorMessage) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(javac.getErrorStream()));
			while (true) {
				String line = reader.readLine();
				if (line == null) break;
				Env.e(line);
			}
			reader.close();
		} else {
			javac.getErrorStream().close();
		}
		try {
			if (javac.waitFor() != 0) {
				Env.e("Failed to compile the translated files."); 
				return false;
			}
			return true;
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * ディレクトリ内の class ファイルを Jar ファイルに出力する。ディレクトリは再帰的に処理される。
	 * @param out 出力する JarOutputStream
	 * @param relativeDir "/"で区切られた、相対パス。Jar への出力に利用する。
	 * @param directory 処理するディレクトリを表す File インスタンス。
	 * @throws IOException IOエラーが発生した場合
	 */
	private static void putToJar(JarOutputStream out, String relativeDir, File directory) throws IOException {
		String[] files = directory.list();
		byte[] buf = new byte[8192];
		for (int i = 0; i < files.length; i++) {
			File f = new File(directory, files[i]);
			if (f.isDirectory()) {
				out.putNextEntry(new JarEntry(relativeDir + files[i] + "/"));
				putToJar(out, relativeDir + files[i] + "/", f);
			} else if (files[i].endsWith(".class")) {
				out.putNextEntry(new JarEntry(relativeDir + files[i]));
				FileInputStream in = new FileInputStream(f);
				int size;
				while ((size = in.read(buf)) != -1) {
					out.write(buf, 0, size);
				}
				in.close();
			}
		}
	}
	/**
	 * 指定したディレクトリを削除する。
	 * 子ディレクトリ・ファイルをすべて削除してから、このディレクトリを削除する。
	 * @param directory 削除するディレクトリ
	 * @return 削除に成功した場合は true
	 */
	private static boolean delete(File directory) {
		String[] files = directory.list();
		for (int i = 0; i < files.length; i++) {
			File f = new File(directory, files[i]);
			if (f.isDirectory()) {
				delete(f);
			} else {
				f.delete();
			}
		}
		return directory.delete();
	}
	
	////////////////////////////////////////////////////////////////////////
	// インスタンスメソッド

	private boolean globalSystemRuleset = false;
	
	/**
	 * 指定された InterpretedRuleset を Java に変換するためのインスタンスを生成する。
	 * @param ruleset 変換するルールセット
	 * @throws IOException Java ソースの出力に失敗した場合
	 */
	public Translator(InterpretedRuleset ruleset) throws IOException{
		className = getClassName(ruleset);
		outputFile = new File(dir, className + ".java");
		writer = new BufferedWriter(new FileWriter(outputFile));
		this.ruleset = ruleset;
	}

	/**
	 * 標準のシステムルールセットを生成するためのインスタンスを生成する。
	 * @param ruleset システムルールセット
	 * @param system コンストラクタを分けるための引数。必ず true を指定すること
	 * @throws IOException Java ソースの出力に失敗した場合
	 */
	public Translator(InterpretedRuleset ruleset, boolean system) throws IOException {
		if (!system)
			throw new RuntimeException();

		packageName = "runtime";
		className = "GlobalSystemRuleset";
		writer = new BufferedWriter(new OutputStreamWriter(System.out, Charset.forName("EUC_JP")));
		writer.write("//GlobalSystemRulesetGeneratorによって自動生成されたファイル\n\n");
		this.ruleset = ruleset;
		globalSystemRuleset = true;
	}
	
	/**
	 * Javaソースを出力する。
	 * @throws IOException Java ソースの出力に失敗した場合
	 */
	public void translate() throws IOException {
		writer.write("package " + packageName + ";\n");
		writer.write("import runtime.*;\n");
		writer.write("import java.util.*;\n");
		writer.write("import java.io.*;\n");
		writer.write("import daemon.IDConverter;\n");
		writer.write("import module.*;\n");
//		writer.write("\n");
//		{
//			Iterator il0 = Inline.inlineSet.values().iterator();
//			while(il0.hasNext()) {
//				runtime.InlineUnit iu = (runtime.InlineUnit)il0.next();
//				Iterator il1 = iu.defs.iterator();
//				while(il1.hasNext()) {
//					writer.write((String)il1.next());
//					writer.write("\n");
//				}
//			}
//		}
		writer.write("\n");
		if (globalSystemRuleset) {
			writer.write("/**\n");
			writer.write(" * コンパイル済みシステムルールセット。GlobalSystemRulesetGenerator によって生成される。\n");
			writer.write(" */\n");
		}
		writer.write("public class " + className + " extends Ruleset {\n");
		writer.write("	private static final " + className + " theInstance = new " + className + "();\n");
		writer.write("	private " + className + "() {}\n");
		writer.write("	public static " + className + " getInstance() {\n");
		writer.write("		return theInstance;\n");
		writer.write("	}\n");
		writer.write("	private int id = " + ruleset.getId() + ";\n");
		if (globalSystemRuleset) {
			writer.write("	public String getGlobalRulesetID() {\n");
			writer.write("		return \"$systemruleset\";\n");
			writer.write("	}\n");
			writer.write("	public String toString() {\n");
			writer.write("		return \"System Ruleset Object\";\n");
			writer.write("	}\n");
		} else {
			writer.write("	private String globalRulesetID;\n");
			writer.write("	public String getGlobalRulesetID() {\n");
			writer.write("		if (globalRulesetID == null) {\n");
			String libname = (Env.fLibrary ? sourceName : "");
			writer.write("			globalRulesetID = Env.theRuntime.getRuntimeID() + \":" + libname + "\" + id;\n");
			writer.write("			IDConverter.registerRuleset(globalRulesetID, this);\n");
			writer.write("		}\n");
			writer.write("		return globalRulesetID;\n");
			writer.write("	}\n");
			writer.write("	public String toString() {\n");
			writer.write("		return \"@" + libname + "\" + id;\n");
			writer.write("	}\n");
		}

		//膜手動テスト
		writer.write("	public boolean react(Membrane mem, Atom atom) {\n");
		writer.write("		boolean result = false;\n");
		Iterator it = ruleset.rules.iterator();
		while (it.hasNext()) {
			Rule rule = (Rule) it.next();
			writer.write("		if (exec" + rule.atomMatchLabel.label + "(mem, atom)) {\n");
			//writer.write("			result = true;\n");
			writer.write("			return true;\n");
			//writer.write("			if (!mem.isCurrent()) return true;\n");
			writer.write("		}\n");
		}
		writer.write("		return result;\n");
		writer.write("	}\n");
		//アトム手動テスト
		writer.write("	public boolean react(Membrane mem) {\n");
		writer.write("		boolean result = false;\n");
		it = ruleset.rules.iterator();
		while (it.hasNext()) {
			Rule rule = (Rule) it.next();
			writer.write("		if (exec" + rule.memMatchLabel.label + "(mem)) {\n");
			//writer.write("			result = true;\n");
			writer.write("			return true;\n");
			//writer.write("			if (!mem.isCurrent()) return true;\n");
			writer.write("		}\n");
		}
		writer.write("		return result;\n");
		writer.write("	}\n");

		//InstructionList をメソッドに変換
		it = ruleset.rules.iterator();
		while (it.hasNext()) {
			Rule rule = (Rule)it.next();
			add(rule.atomMatchLabel);
			add(rule.memMatchLabel);
		}
		while (instListsToTranslate.size() > 0) {
			InstructionList instList = (InstructionList)instListsToTranslate.remove(instListsToTranslate.size() - 1);
			translate(instList);
		}

		//Functor の生成。毎回 new するのを防ぐため、クラス変数にする。
		//この方法だと Ruleset 毎に作られるので、Functors クラスみたいな物を作った方がよいかもしれない。
		it = funcVarMap.keySet().iterator();
		while (it.hasNext()) {
			Functor func = (Functor)it.next();
			writer.write("	private static final Functor " + funcVarMap.get(func));
			if (func instanceof StringFunctor) {
				writer.write(" = new StringFunctor(" + Util.quoteString((String)func.getValue(), '"') + ");\n");
			} else if (func instanceof IntegerFunctor) {
				writer.write(" = new IntegerFunctor(" + ((IntegerFunctor)func).intValue() + ");\n");
			} else if (func instanceof FloatingFunctor) {
				writer.write(" = new FloatingFunctor(" + ((FloatingFunctor)func).floatValue() + ");\n");
			} else if (func instanceof ObjectFunctor) {
				throw new RuntimeException("Static ObjectFunctor is not supported");
			} else {
				String path = "null";
				if (func.getPath() != null) {
					path = Util.quoteString(func.getPath(), '"');
				}
				writer.write(" = new Functor(" + Util.quoteString(func.getName(), '"') + ", " + func.getArity() + ", " + path + ");\n");
			}
		}
		
		writer.write("}\n");
		writer.close();
	}

	/**
	 * 変換すべき InstructionList を追加する。
	 * 同じインスタンスに対して複数回呼び出した場合は、２回目以降は何もしない。
	 * @param instList 追加する InstructionList
	 */
	private void add(InstructionList instList) {
		if (instLists.contains(instList)) {
			return;
		}
		instLists.add(instList);
		instListsToTranslate.add(instList);
	}

	/**
	 * 指定された InstructionList をJavaコードに変換する。
	 * @param instList 変換するInstructionList
	 * @throws IOException Java ソースの出力に失敗した場合
	 */
	private void translate(InstructionList instList) throws IOException {
		writer.write("	public boolean exec" + instList.label + "(");
		if (globalSystemRuleset && instList.insts.size() == 0) {
			//GlobalSystemRuleset の生成では、アトム主導テストが空のことがある
			writer.write("Object var0, Object var1) {\n");
			writer.write("		return false;\n");
			writer.write("	}\n");
			return;
		}
		Instruction spec = (Instruction)instList.insts.get(0);
		if (spec.getKind() != Instruction.SPEC) {
			throw new RuntimeException("first instructio is not SPEC but " + spec);
		}
		int formals = spec.getIntArg1();
		int locals = spec.getIntArg2();
		if (formals > 0) {
			writer.write("Object var0");
			for (int i = 1; i < formals; i++) {
				writer.write(", Object var" + i);
			}
		}
		writer.write(") {\n");

		for (int i = formals; i < locals; i++) {
			writer.write("		Object var" + i + " = null;\n");
		}

		//以下の変数は、変換したソース内で自由に利用できる。
		//ローカル変数名の衝突を避けるため、最初に1回だけ定義して利用することにしている。
		//利用する場合は、必ず値を代入してから使うこと。
		//また、translateメソッドの再起呼び出しをすると、変数の値が書き換えられていることがあるので
		//再起呼び出しの後には（再度代入せずに）利用してはいけない。
		writer.write("		Atom atom;\n");
		writer.write("		Functor func;\n");
		writer.write("		Link link;\n");
		writer.write("		AbstractMembrane mem;\n");
		writer.write("		int x, y;\n");
		writer.write("		double u, v;\n");
		writer.write("		int isground_ret;\n");
		writer.write("		boolean eqground_ret;\n");
		
		//2005-10-21 by kudo (INSERTCONNECTORS,DELETECONNECTORS,LOOKUPLINKで使う)
		writer.write("		Set insset;\n");
		writer.write("		Set delset;\n");
		writer.write("		Map srcmap;\n");
		writer.write("		Map delmap;\n");
		writer.write("		Atom orig;\n");
		writer.write("		Atom copy;\n");
		writer.write("		Link a;\n");
		writer.write("		Link b;\n");
		writer.write("		Iterator it_deleteconnectors;\n");

		writer.write("		boolean ret = false;\n");
		writer.write(instList.label + ":\n");
		writer.write("		{\n");
		Iterator it = instList.insts.iterator();
		translate(it, "			", 1, locals, instList.label);
		writer.write("		}\n");
		writer.write("		return ret;\n");
//		if (!translate(it, "			", 1, locals)) {
//			writer.write("		return false;\n");
//		}
		
		writer.write("	}\n");
	}
	/**
	 * 指定された Iterator によって得られる命令列を Java コードに変換する。
	 * @param it 変換する命令列の Iterator
	 * @param tabs 出力時に利用するインデント。通常は N 個のタブ文字を指定する。
	 * @param iteratorNo 出力するコード内で次に利用する Iterator の番号。ローカル変数の重複を防ぐために必要。
	 * @param breakLabel 成功時に break するブロックのラベル
	 * @return return 文を出力して終了した場合には true。コンパイルエラーを防ぐため、true を返した場合は直後に"}"以外のコードを出力してはならない。
	 * @throws IOException Java ソースの出力に失敗した場合
	 */
	private void translate(Iterator it, String tabs, int iteratorNo, int varnum, String breakLabel) throws IOException {
		while (it.hasNext()) {
//			Functor func;
			InstructionList label; 
			Instruction inst = (Instruction)it.next();

			String a = inst.toString();
			int pos_nl = a.indexOf('\r');
			int pos2 = a.indexOf('\n');
			if (pos_nl == -1 || (pos2 >= 0 && pos2 < pos_nl)) {
				pos_nl = pos2;
			}
			if (pos_nl >= 0) {
				int pos_b = a.indexOf('[');
				if (pos_b > pos_nl) {
					a = a.substring(0, pos_nl) + "...";
				} else {
					a = a.substring(0, pos_b) + "[ ... ]";
				}
			}
			if (Env.debug > 0)
				writer.write("// " + a + "\n");

			switch (inst.getKind()) {
				//====アトムに関係する出力する基本ガード命令====ここから====
				case Instruction.DEREF : //[-dstatom, srcatom, srcpos, dstpos]
					writer.write(tabs + "link = ((Atom)var" + inst.getIntArg2() + ").getArg(" + inst.getIntArg3() + ");\n");
					writer.write(tabs + "if (!(link.getPos() != " + inst.getIntArg4() + ")) {\n");
					writer.write(tabs + "	var" + inst.getIntArg1() + " = link.getAtom();\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
				case Instruction.DEREFATOM : // [-dstatom, srcatom, srcpos]
					writer.write(tabs + "link = ((Atom)var" + inst.getIntArg2() + ").getArg(" + inst.getIntArg3() + ");\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = link.getAtom();\n");
					break; //n-kato
				case Instruction.DEREFLINK : //[-dstatom, srclink, dstpos]
					writer.write(tabs + "link = (Link)var" + inst.getIntArg2() + ";\n");
					writer.write(tabs + "if (!(link.getPos() != " + inst.getIntArg3() + ")) {\n");
					writer.write(tabs + "	var" + inst.getIntArg1() + " = link.getAtom();\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //mizuno
				case Instruction.FINDATOM : // [-dstatom, srcmem, funcref]
					writer.write(tabs + "func = " + getFuncVarName((Functor)inst.getArg3()) + ";\n");
					writer.write(tabs + "Iterator it" + iteratorNo + " = ((AbstractMembrane)var" + inst.getIntArg2() + ").atomIteratorOfFunctor(func);\n");
					writer.write(tabs + "while (it" + iteratorNo + ".hasNext()) {\n");
					writer.write(tabs + "	atom = (Atom) it" + iteratorNo + ".next();\n");
					writer.write(tabs + "	var" + inst.getIntArg1() + " = atom;\n");
					translate(it, tabs + "\t", iteratorNo + 1, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break;
					//====アトムに関係する出力する基本ガード命令====ここまで====
					//====膜に関係する出力する基本ガード命令 ====ここから====
				case Instruction.LOCKMEM :
				case Instruction.LOCALLOCKMEM :
					// lockmem [-dstmem, freelinkatom]
					writer.write(tabs + "mem = ((Atom)var" + inst.getIntArg2() + ").getMem();\n");
					writer.write(tabs + "if (mem.lock()) {\n");
					writer.write(tabs + "	var" + inst.getIntArg1() + " = mem;\n");
					translate(it, tabs + "\t", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "	((AbstractMembrane)var" + inst.getIntArg1() + ").unlock();\n"); //失敗する場合は、resetvars命令を実行する事はない
					writer.write(tabs + "}\n");
					break;
				case Instruction.ANYMEM :
				case Instruction.LOCALANYMEM : // anymem [-dstmem, srcmem] 
					writer.write(tabs + "Iterator it" + iteratorNo + " = ((AbstractMembrane)var" + inst.getIntArg2() + ").memIterator();\n");
					writer.write(tabs + "while (it" + iteratorNo + ".hasNext()) {\n");
					writer.write(tabs + "	mem = (AbstractMembrane) it" + iteratorNo + ".next();\n");
					writer.write(tabs + "	if (mem.lock()) {\n");
					writer.write(tabs + "		var" + inst.getIntArg1() + " = mem;\n");
					translate(it, tabs + "		", iteratorNo + 1, varnum, breakLabel);
					writer.write(tabs + "		((AbstractMembrane)var" + inst.getIntArg1() + ").unlock();\n");
					writer.write(tabs + "	}\n");
					writer.write(tabs + "}\n");
					break;
				case Instruction.LOCK :
				case Instruction.LOCALLOCK : //[srcmem] 
					writer.write(tabs + "mem = ((AbstractMembrane)var" + inst.getIntArg1() + ");\n");
					writer.write(tabs + "if (mem.lock()) {\n");
					translate(it, tabs + "\t", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "	((AbstractMembrane)var" + inst.getIntArg1() + ").unlock();\n");
					writer.write(tabs + "}\n");
					break;
				case Instruction.GETMEM : //[-dstmem, srcatom]
					writer.write(tabs + "var" + inst.getIntArg1() + " = ((Atom)var" + inst.getIntArg2() + ").getMem();\n");
					break; //n-kato
				case Instruction.GETPARENT : //[-dstmem, srcmem]
					writer.write(tabs + "mem = ((AbstractMembrane)var" + inst.getIntArg2() + ").getParent();\n");
					writer.write(tabs + "if (!(mem == null)) {\n");
					writer.write(tabs + "	var" + inst.getIntArg1() + " = mem;\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
					//====膜に関係する出力する基本ガード命令====ここまで====
					//====膜に関係する出力しない基本ガード命令====ここから====
				case Instruction.TESTMEM : //[dstmem, srcatom]
					writer.write(tabs + "if (!(((AbstractMembrane)var" + inst.getIntArg1() + ") != ((Atom)var" + inst.getIntArg2() + ").getMem())) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
				case Instruction.NORULES : //[srcmem] 
					writer.write(tabs + "if (!(((AbstractMembrane)var" + inst.getIntArg1() + ").hasRules())) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
				case Instruction.NFREELINKS : //[srcmem, count]
					writer.write(tabs + "mem = ((AbstractMembrane)var" + inst.getIntArg1() + ");\n");
					writer.write(tabs + "if (!(mem.getAtomCountOfFunctor(Functor.INSIDE_PROXY) != " + inst.getIntArg2() + ")) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break;
				case Instruction.NATOMS : //[srcmem, count]
					writer.write(tabs + "if (!(((AbstractMembrane)var" + inst.getIntArg1() + ").getAtomCount() != " + inst.getIntArg2() + ")) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
				case Instruction.NATOMSINDIRECT : //[srcmem, countfunc]
					writer.write(tabs + "if (!(((AbstractMembrane)var" + inst.getIntArg1() + ").getAtomCount() != ((IntegerFunctor)var" + inst.getIntArg2() + ").intValue())) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //kudo 2004-12-08
				case Instruction.NMEMS : //[srcmem, count]
					writer.write(tabs + "if (!(((AbstractMembrane)var" + inst.getIntArg1() + ").getMemCount() != " + inst.getIntArg2() + ")) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
				case Instruction.EQMEM : //[mem1, mem2]
					writer.write(tabs + "if (!(((AbstractMembrane)var" + inst.getIntArg1() + ") != ((AbstractMembrane)var" + inst.getIntArg2() + "))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
				case Instruction.NEQMEM : //[mem1, mem2]
					writer.write(tabs + "if (!(((AbstractMembrane)var" + inst.getIntArg1() + ") == ((AbstractMembrane)var" + inst.getIntArg2() + "))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
				case Instruction.STABLE : //[srcmem] 
					writer.write(tabs + "if (!(!((AbstractMembrane)var" + inst.getIntArg1() + ").isStable())) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
					//====膜に関係する出力しない基本ガード命令====ここまで====
					//====アトムに関係する出力しない基本ガード命令====ここから====
				case Instruction.FUNC : //[srcatom, funcref]
					writer.write(tabs + "if (!(!(" + getFuncVarName((Functor)inst.getArg2()) + ").equals(((Atom)var" + inst.getIntArg1() + ").getFunctor()))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
				case Instruction.NOTFUNC : //[srcatom, funcref]
					writer.write(tabs + "if (!((" + getFuncVarName((Functor)inst.getArg2()) + ").equals(((Atom)var" + inst.getIntArg1() + ").getFunctor()))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
				case Instruction.EQATOM : //[atom1, atom2]
					writer.write(tabs + "if (!(((Atom)var" + inst.getIntArg1() + ") != ((Atom)var" + inst.getIntArg2() + "))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
				case Instruction.NEQATOM : //[atom1, atom2]
					writer.write(tabs + "if (!(((Atom)var" + inst.getIntArg1() + ") == ((Atom)var" + inst.getIntArg2() + "))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
				case Instruction.SAMEFUNC: //[atom1, atom2]
					writer.write(tabs + "if (!(!((Atom)var" + inst.getIntArg1() + ").getFunctor().equals(((Atom)var" + inst.getIntArg2() + ").getFunctor()))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
					//====アトムに関係する出力しない基本ガード命令====ここまで====
					//====ファンクタに関係する命令====ここから====
				case Instruction.DEREFFUNC : //[-dstfunc, srcatom, srcpos]
					writer.write(tabs + "var" + inst.getIntArg1() + " =  ((Atom)var" + inst.getIntArg2() + ").getArg(" + inst.getIntArg3() + ").getAtom().getFunctor();\n");
					break; //nakajima 2003-12-21, n-kato
				case Instruction.GETFUNC : //[-func, atom]
					writer.write(tabs + "var" + inst.getIntArg1() + " =  ((Atom)var" + inst.getIntArg2() + ").getFunctor();\n");
					break; //nakajima 2003-12-21, n-kato
				case Instruction.LOADFUNC : //[-func, funcref]
					writer.write(tabs + "var" + inst.getIntArg1() + " =  " + getFuncVarName((Functor)inst.getArg2()) + ";\n");
					break;//nakajima 2003-12-21, n-kato
				case Instruction.EQFUNC : //[func1, func2]
					writer.write(tabs + "if (!(!var" + inst.getIntArg1() + ".equals(var" + inst.getIntArg2() + "))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //nakajima, n-kato
				case Instruction.NEQFUNC : //[func1, func2]
					writer.write(tabs + "if (!(var" + inst.getIntArg1() + ".equals(var" + inst.getIntArg2() + "))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //nakajima, n-kato
					//====ファンクタに関係する命令====ここまで====
					//====アトムを操作する基本ボディ命令====ここから====
				case Instruction.REMOVEATOM :
				case Instruction.LOCALREMOVEATOM : //[srcatom, srcmem, funcref]
					writer.write(tabs + "atom = ((Atom)var" + inst.getIntArg1() + ");\n");
					writer.write(tabs + "atom.getMem().removeAtom(atom);\n");
					break; //n-kato
				case Instruction.NEWATOM :
				case Instruction.LOCALNEWATOM : //[-dstatom, srcmem, funcref]
					writer.write(tabs + "func = " + getFuncVarName((Functor)inst.getArg3()) + ";\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = ((AbstractMembrane)var" + inst.getIntArg2() + ").newAtom(func);\n");
					break; //n-kato
				case Instruction.NEWATOMINDIRECT :
				case Instruction.LOCALNEWATOMINDIRECT : //[-dstatom, srcmem, func]
					writer.write(tabs + "var" + inst.getIntArg1() + " = ((AbstractMembrane)var" + inst.getIntArg2() + ").newAtom((Functor)(var" + inst.getIntArg3() + "));\n");
					break; //nakajima 2003-12-27, 2004-01-03, n-kato
				case Instruction.ENQUEUEATOM :
				case Instruction.LOCALENQUEUEATOM : //[srcatom]
					writer.write(tabs + "atom = ((Atom)var" + inst.getIntArg1() + ");\n");
					writer.write(tabs + "atom.getMem().enqueueAtom(atom);\n");
					break; //n-kato
				case Instruction.DEQUEUEATOM : //[srcatom]
					writer.write(tabs + "atom = ((Atom)var" + inst.getIntArg1() + ");\n");
					writer.write(tabs + "atom.dequeue();\n");
					break; //n-kato
				case Instruction.FREEATOM : //[srcatom]
					break; //n-kato
				case Instruction.ALTERFUNC :
				case Instruction.LOCALALTERFUNC : //[atom, funcref]
					writer.write(tabs + "atom = ((Atom)var" + inst.getIntArg1() + ");\n");
					writer.write(tabs + "atom.getMem().alterAtomFunctor(atom," + getFuncVarName((Functor)inst.getArg2()) + ");\n");
					break; //n-kato
				case Instruction.ALTERFUNCINDIRECT :
				case Instruction.LOCALALTERFUNCINDIRECT : //[atom, func]
					writer.write(tabs + "atom = ((Atom)var" + inst.getIntArg1() + ");\n");
					writer.write(tabs + "atom.getMem().alterAtomFunctor(atom,(Functor)(var" + inst.getIntArg2() + "));\n");
					break; //nakajima 2003-12-27, 2004-01-03, n-kato
					//====アトムを操作する基本ボディ命令====ここまで====
					//====アトムを操作する型付き拡張用命令====ここから====
				case Instruction.ALLOCATOM : //[-dstatom, funcref]
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, " + getFuncVarName((Functor)inst.getArg2()) + ");\n");
					break; //nakajima 2003-12-27, n-kato
				case Instruction.ALLOCATOMINDIRECT : //[-dstatom, func]
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, (Functor)(var" + inst.getIntArg2() + "));\n");
					break; //nakajima 2003-12-27, 2004-01-03, n-kato
				case Instruction.COPYATOM :
				case Instruction.LOCALCOPYATOM : //[-dstatom, mem, srcatom]
					writer.write(tabs + "var" + inst.getIntArg1() + " = ((AbstractMembrane)var" + inst.getIntArg2() + ").newAtom(((Atom)var" + inst.getIntArg3() + ").getFunctor());\n");
					break; //nakajima, n-kato
					//case Instruction.ADDATOM:
				case Instruction.LOCALADDATOM : //[dstmem, atom]
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").addAtom(((Atom)var" + inst.getIntArg2() + "));\n");
					break; //nakajima 2003-12-27, n-kato
					//====アトムを操作する型付き拡張用命令====ここまで====
					//====膜を操作する基本ボディ命令====ここから====
				case Instruction.REMOVEMEM :
				case Instruction.LOCALREMOVEMEM : //[srcmem, parentmem]
					writer.write(tabs + "mem = ((AbstractMembrane)var" + inst.getIntArg1() + ");\n");
					writer.write(tabs + "mem.getParent().removeMem(mem);\n");
					break; //n-kato
				case Instruction.NEWMEM: //[-dstmem, srcmem]
					writer.write(tabs + "mem = ((AbstractMembrane)var" + inst.getIntArg2() + ").newMem();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = mem;\n");
					break; //n-kato
				case Instruction.LOCALNEWMEM : //[-dstmem, srcmem]
					writer.write(tabs + "mem = ((Membrane)((AbstractMembrane)var" + inst.getIntArg2() + ")).newLocalMembrane();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = mem;\n");
					break; //n-kato
				case Instruction.ALLOCMEM: //[-dstmem]
					writer.write(tabs + "mem = ((Task)((AbstractMembrane)var0).getTask()).createFreeMembrane();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = mem;\n");
					break; //n-kato
				case Instruction.NEWROOT : //[-dstmem, srcmem, nodeatom]
					writer.write(tabs + "String nodedesc = ((Atom)var" + inst.getIntArg3() + ").getFunctor().getName();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = ((AbstractMembrane)var" + inst.getIntArg2() + ").newRoot(nodedesc);\n");
					break; //n-kato 2004-09-17
				case Instruction.MOVECELLS : //[dstmem, srcmem]
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").moveCellsFrom(((AbstractMembrane)var" + inst.getIntArg2() + "));\n");
					break; //nakajima 2004-01-04, n-kato
				case Instruction.ENQUEUEALLATOMS : //[srcmem]
					break;
				case Instruction.FREEMEM : //[srcmem]
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").free();\n");
					break; //mizuno 2004-10-12, n-kato
				case Instruction.ADDMEM :
				case Instruction.LOCALADDMEM : //[dstmem, srcmem]
					writer.write(tabs + "var" + inst.getIntArg2() + " = ((AbstractMembrane)var" + inst.getIntArg2() + ").moveTo(((AbstractMembrane)var" + inst.getIntArg1() + "));\n");
					break; //nakajima 2004-01-04, n-kato, n-kato 2004-11-10
				case Instruction.ENQUEUEMEM:
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").activate();\n");
					//mems[inst.getIntArg1()].enqueueAllAtoms();
					break;
				case Instruction.UNLOCKMEM :
				case Instruction.LOCALUNLOCKMEM : //[srcmem]
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").forceUnlock();\n");
					break; //n-kato
				case Instruction.LOCALSETMEMNAME: //[dstmem, name]
				case Instruction.SETMEMNAME: //[dstmem, name]
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").setName(" + Util.quoteString((String)inst.getArg2(), '"') + ");\n");
					break; //n-kato
					//====膜を操作する基本ボディ命令====ここまで====
					//====リンクに関係する出力するガード命令====ここから====
				case Instruction.GETLINK : //[-link, atom, pos]
					writer.write(tabs + "link = ((Atom)var" + inst.getIntArg2() + ").getArg(" + inst.getIntArg3() + ");\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = link;\n");
					break; //n-kato
				case Instruction.ALLOCLINK : //[-link, atom, pos]
					writer.write(tabs + "link = new Link(((Atom)var" + inst.getIntArg2() + "), " + inst.getIntArg3() + ");\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = link;\n");
					break; //n-kato
					//====リンクに関係する出力するガード命令====ここまで====
					//====リンクを操作するボディ命令====ここから====
				case Instruction.NEWLINK:		 //[atom1, pos1, atom2, pos2, mem1]
				case Instruction.LOCALNEWLINK:	 //[atom1, pos1, atom2, pos2 (,mem1)]
					writer.write(tabs + "((Atom)var" + inst.getIntArg1() + ").getMem().newLink(\n");
					writer.write(tabs + "	((Atom)var" + inst.getIntArg1() + "), " + inst.getIntArg2() + ",\n");
					writer.write(tabs + "	((Atom)var" + inst.getIntArg3() + "), " + inst.getIntArg4() + " );\n");
					break; //n-kato
				case Instruction.RELINK:		 //[atom1, pos1, atom2, pos2, mem]
				case Instruction.LOCALRELINK:	 //[atom1, pos1, atom2, pos2 (,mem)]
					writer.write(tabs + "((Atom)var" + inst.getIntArg1() + ").getMem().relinkAtomArgs(\n");
					writer.write(tabs + "	((Atom)var" + inst.getIntArg1() + "), " + inst.getIntArg2() + ",\n");
					writer.write(tabs + "	((Atom)var" + inst.getIntArg3() + "), " + inst.getIntArg4() + " );\n");
					break; //n-kato
				case Instruction.UNIFY:		//[atom1, pos1, atom2, pos2, mem]
					writer.write(tabs + "mem = ((AbstractMembrane)var" + inst.getIntArg5() + ");\n");
					writer.write(tabs + "mem.unifyAtomArgs(\n");
					writer.write(tabs + "	((Atom)var" + inst.getIntArg1() + "), " + inst.getIntArg2() + ",\n");
					writer.write(tabs + "	((Atom)var" + inst.getIntArg3() + "), " + inst.getIntArg4() + " );\n");
					break; //n-kato
				case Instruction.LOCALUNIFY:	//[atom1, pos1, atom2, pos2 (,mem)]
					//2005/10/11 mizuno ローカルなので、本膜を使えば問題ないはず
					writer.write(tabs + "((AbstractMembrane)var0).unifyAtomArgs(\n");
					writer.write(tabs + "	((Atom)var" + inst.getIntArg1() + "), " + inst.getIntArg2() + ",\n");
					writer.write(tabs + "	((Atom)var" + inst.getIntArg3() + "), " + inst.getIntArg4() + " );\n");
					break; //mizuno
				case Instruction.INHERITLINK:		 //[atom1, pos1, link2, mem]
				case Instruction.LOCALINHERITLINK:	 //[atom1, pos1, link2 (,mem)]
					writer.write(tabs + "((Atom)var" + inst.getIntArg1() + ").getMem().inheritLink(\n");
					writer.write(tabs + "	((Atom)var" + inst.getIntArg1() + "), " + inst.getIntArg2() + ",\n");
					writer.write(tabs + "	(Link)var" + inst.getIntArg3() + " );\n");
					break; //n-kato
				case Instruction.UNIFYLINKS:		//[link1, link2, mem]
					//2005/10/11 mizuno
					//必ず第五引数を利用するようにコンパイラを修正し、正規のコードに変更
					writer.write(tabs + "mem = ((AbstractMembrane)var" + inst.getIntArg3() + ");\n");
					writer.write(tabs + "mem.unifyLinkBuddies(\n");
					writer.write(tabs + "	((Link)var" + inst.getIntArg1() + "),\n");
					writer.write(tabs + "	((Link)var" + inst.getIntArg2() + "));\n");
					break; //n-kato
				case Instruction.LOCALUNIFYLINKS:	//[link1, link2 (,mem)]
					//2005/10/11 mizuno ローカルなので、本膜を使えば問題ないはず
					writer.write(tabs + "((AbstractMembrane)var0).unifyLinkBuddies(\n");
					writer.write(tabs + "	((Link)var" + inst.getIntArg1() + "),\n");
					writer.write(tabs + "	((Link)var" + inst.getIntArg2() + "));\n");
					break; //mizuno
					//====リンクを操作するボディ命令====ここまで====
					//====自由リンク管理アトム自動処理のためのボディ命令====ここから====
				case Instruction.REMOVEPROXIES : //[srcmem]
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").removeProxies();\n");
					break; //nakajima 2004-01-04, n-kato
				case Instruction.REMOVETOPLEVELPROXIES : //[srcmem]
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").removeToplevelProxies();\n");
					break; //nakajima 2004-01-04, n-kato
				case Instruction.INSERTPROXIES : //[parentmem,childmem]
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").insertProxies(((AbstractMembrane)var" + inst.getIntArg2() + "));\n");
					break;  //nakajima 2004-01-04, n-kato
				case Instruction.REMOVETEMPORARYPROXIES : //[srcmem]
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").removeTemporaryProxies();\n");
					break; //nakajima 2004-01-04, n-kato
					//====自由リンク管理アトム自動処理のためのボディ命令====ここまで====
					//====ルールを操作するボディ命令====ここから====
//下で手動生成
//				case Instruction.LOADRULESET:
//				case Instruction.LOCALLOADRULESET: //[dstmem, ruleset]
				case Instruction.COPYRULES:
				case Instruction.LOCALCOPYRULES:   //[dstmem, srcmem]
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").copyRulesFrom(((AbstractMembrane)var" + inst.getIntArg2() + "));\n");
					break; //n-kato
				case Instruction.CLEARRULES:
				case Instruction.LOCALCLEARRULES:  //[dstmem]
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").clearRules();\n");
					break; //n-kato
//下で手動生成
//				case Instruction.LOADMODULE: //[dstmem, module_name]
					//====ルールを操作するボディ命令====ここまで====
					//====型付きでないプロセス文脈をコピーまたは廃棄するための命令====ここから====
				case Instruction.RECURSIVELOCK : //[srcmem]
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").recursiveLock();\n");
					break; //n-kato
				case Instruction.RECURSIVEUNLOCK : //[srcmem]
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").recursiveUnlock();\n");
					break;//nakajima 2004-01-04, n-kato
				case Instruction.COPYCELLS : //[-dstmap, -dstmem, srcmem]
					// <strike>自由リンクを持たない膜（その子膜とのリンクはOK）のみ</strike>
					writer.write(tabs + "var" + inst.getIntArg1() + " =  ((AbstractMembrane)var" + inst.getIntArg2() + ").copyCellsFrom(((AbstractMembrane)var" + inst.getIntArg3() + "));\n");
					break; //kudo 2004-09-29
				case Instruction.DROPMEM : //[srcmem]
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").drop();\n");
					break; //kudo 2004-09-29
				case Instruction.LOOKUPLINK : //[-dstlink, srcmap, srclink]
					writer.write(tabs + "srcmap = (HashMap)var" + inst.getIntArg2() + ";\n");
					writer.write(tabs + "link = (Link)var" + inst.getIntArg3() + ";\n");
					writer.write(tabs + "atom = (Atom) srcmap.get(link.getAtom());\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Link(atom, link.getPos());\n");
					break; //kudo 2004-10-10
//未対応。変数は、配列に持たなければならなかったらしい。
//対応。二番目の引数は実行時ではなくコンパイル時に参照するみたい。 って自分で作った命令なんだけど。 by kudo
				case Instruction.INSERTCONNECTORS : //[-dstset,linklist,mem]
					writer.write(tabs + "func = "+ getFuncVarName(new Functor("=",2))+";\n");
					List linklist = (List)inst.getArg2();
					writer.write(tabs + "insset = new HashSet();\n");
					writer.write(tabs + "mem = ((AbstractMembrane)var" + inst.getIntArg3() + ");\n");
					for(int i=0;i<linklist.size();i++) {
						for(int j=i+1;j<linklist.size();j++) {
							writer.write(tabs + "		a = (Link)var"+((Integer)linklist.get(i)).intValue()+";\n");
							writer.write(tabs + "		b = (Link)var"+((Integer)linklist.get(j)).intValue()+";\n");
							writer.write(tabs + "		if(a == b.getAtom().getArg(b.getPos())){\n");
							writer.write(tabs + "			atom = mem.newAtom(func);\n");//"+getFuncVarName(new Functor("=",2))+");\n");
							writer.write(tabs + "			mem.unifyLinkBuddies(a,new Link(atom,0));\n");
							writer.write(tabs + "			mem.unifyLinkBuddies(b,new Link(atom,1));\n");
							writer.write(tabs + "			insset.add(atom);\n");
							writer.write(tabs + "		}\n");
						}
					}
					writer.write(tabs + "var" + inst.getIntArg1() + " = insset;\n");
					break; //kudo 2004-12-29
				case Instruction.DELETECONNECTORS : //[srcset,srcmap,srcmem]
					writer.write(tabs + "delset = (Set)var" + inst.getIntArg1() + ";\n");
					writer.write(tabs + "delmap = (Map)var" + inst.getIntArg2() + ";\n");
					writer.write(tabs + "mem = ((AbstractMembrane)var" + inst.getIntArg3() + ");\n");
					writer.write(tabs + "it_deleteconnectors = delset.iterator();\n");
					writer.write(tabs + "while(it_deleteconnectors.hasNext()){\n");
					writer.write(tabs + "	orig = (Atom)it_deleteconnectors.next();\n");
					writer.write(tabs + "	copy = (Atom)delmap.get(orig);\n");
					writer.write(tabs + "	mem.unifyLinkBuddies(copy.getArg(0), copy.getArg(1));\n");
					writer.write(tabs + "	mem.removeAtom(copy);\n");
					writer.write(tabs + "}\n");
					break; //kudo 2004-12-29
					//====型付きでないプロセス文脈をコピーまたは廃棄するための命令====ここまで====
					//====制御命令====ここから====
				case Instruction.COMMIT :
					// TODO トレーサをよぶ
					break;//
//一部は未実装、一部は下の方で手動生成
//				case Instruction.REACT :
//				case Instruction.JUMP: {
//				case Instruction.RESETVARS :
//				case Instruction.CHANGEVARS :
				case Instruction.PROCEED:
//					writer.write(tabs + "return true; //n-kato\n");
					writer.write(tabs + "ret = true;\n");
					writer.write(tabs + "break " + breakLabel + ";\n");
					return;// true;
//				case Instruction.SPEC://[formals,locals]
//				case Instruction.BRANCH :
//				case Instruction.LOOP :
//				case Instruction.RUN :
//				case Instruction.NOT :
					//====制御命令====ここまで====
					//====型付きプロセス文脈を扱うための追加命令====ここから====
				case Instruction.EQGROUND : //[link1,link2]
					writer.write(tabs + "eqground_ret = ((Link)var" + inst.getIntArg1() + ").eqGround(((Link)var" + inst.getIntArg2() + "));\n");
					writer.write(tabs + "if (!(!eqground_ret)) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //kudo 2004-12-03
				case Instruction.COPYGROUND : //[-dstlink, srclink, dstmem]
					writer.write(tabs + "var" + inst.getIntArg1() + " = ((AbstractMembrane)var" + inst.getIntArg3() + ").copyGroundFrom(((Link)var" + inst.getIntArg2() + "));\n");
					break; //kudo 2004-12-03
				case Instruction.REMOVEGROUND : //[srclink,srcmem]
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg2() + ").removeGround(((Link)var" + inst.getIntArg1() + "));\n");
					break; //kudo 2004-12-08
				case Instruction.FREEGROUND : //[srclink]
					break; //kudo 2004-12-08
					//====型付きプロセス文脈を扱うための追加命令====ここまで====
					//====型検査のためのガード命令====ここから====
				case Instruction.ISGROUND : //[-natomsfunc,srclink,srcset]
					writer.write(tabs + "isground_ret = ((Link)var" + inst.getIntArg2() + ").isGround(((Set)var" + inst.getIntArg3() + "));\n");
					writer.write(tabs + "if (!(isground_ret == -1)) {\n");
					writer.write(tabs + "	var" + inst.getIntArg1() + " = new IntegerFunctor(isground_ret);\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //kudo 2004-12-03
				case Instruction.ISUNARY: // [atom]
					writer.write(tabs + "func = ((Atom)var" + inst.getIntArg1() + ").getFunctor();\n");
					// まくを超えたリンクが unary かどうかが判断できない。OUTSIDE_PROXY を見てる
					// DEREF も？
					// (n-kato)
					// すべて仕様です。というか、リンク先は親膜にあるかもしれないわけですし、
					// 本膜の親膜にあるアトムを調べることは許されていません。
					// (hara) じゃそういうときは「失敗」ということでいいですかねぇ
					// (n-kato) はい。失敗して下さい。ちなみに$in,$outのarityは2なので次の2行は省略しました。
					//if(f.equals(Functor.OUTSIDE_PROXY)) return false;
					//if(f.equals(Functor.INSIDE_PROXY)) return false;
					writer.write(tabs + "if (!(func.getArity() != 1)) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; // n-kato
//				case Instruction.ISUNARYFUNC: // [func]
//					break;
				case Instruction.ISINT : //[atom]
					writer.write(tabs + "if (!(!(((Atom)var" + inst.getIntArg1() + ").getFunctor() instanceof IntegerFunctor))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
				case Instruction.ISFLOAT : //[atom]
					writer.write(tabs + "if (!(!(((Atom)var" + inst.getIntArg1() + ").getFunctor() instanceof FloatingFunctor))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
				case Instruction.ISSTRING : //[atom] // todo StringFunctorに変える（CONNECTRUNTIMEも）
					writer.write(tabs + "if (((Atom)var" + inst.getIntArg1() + ").getFunctor() instanceof ObjectFunctor &&\n");
					writer.write(tabs + "    ((ObjectFunctor)((Atom)var" + inst.getIntArg1() + ").getFunctor()).getObject() instanceof String) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
				case Instruction.ISINTFUNC : //[func]
					writer.write(tabs + "if (!(!(var" + inst.getIntArg1() + " instanceof IntegerFunctor))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
//				case Instruction.ISFLOATFUNC : //[func]
//					break;
//				case Instruction.ISSTRINGFUNC : //[func]
//					break;
				case Instruction.GETCLASS: //[-stringatom, atom]
					writer.write(tabs + "if (!(!(((Atom)var" + inst.getIntArg2() + ").getFunctor() instanceof ObjectFunctor))) {\n");
					writer.write(tabs + "	{\n");
					//再帰呼び出ししていないので、ブロック内で変数宣言しても大丈夫
					writer.write(tabs + "		Object obj = ((ObjectFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).getObject();\n");
					writer.write(tabs + "		String className = obj.getClass().getName();\n");
					if (packageName != null) { //GlobalSystemRuleset のコンパイル時には null
						writer.write(tabs + "		className = className.replaceAll(\"" + packageName + ".\", \"\");\n");
					}
					writer.write(tabs + "		var" + inst.getIntArg1() + " = new Atom(null, new StringFunctor( className ));\n");
					writer.write(tabs + "	}\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
					//====型検査のためのガード命令====ここまで====
					//====組み込み機能に関する命令====ここから====
				case Instruction.INLINE : //[atom, inlineref]
					writer.write(tabs + InlineUnit.className((String)inst.getArg2()) + ".run((Atom)var" + inst.getArg1() + ", " + inst.getArg3() + ");\n");
					break;
					//====組み込み機能に関する命令====ここまで====
//分散機能は未実装
//					//====分散拡張用の命令====ここから====
//				case Instruction.CONNECTRUNTIME: //[srcatom] // todo StringFunctorに変える（ISSTRINGも）
//				case Instruction.GETRUNTIME: //[-dstatom,srcmem] // todo StringFunctorに変える（ISSTRINGも）
//					//====分散拡張用の命令====ここまで====
					//====アトムセットを操作するための命令====ここから====
				case Instruction.NEWSET : //[-dstset]
					writer.write(tabs + "var" + inst.getIntArg1() + " = new HashSet();\n");
					break; //kudo 2004-12-08
				case Instruction.ADDATOMTOSET : //[srcset,atom]
					writer.write(tabs + "((Set)var" + inst.getIntArg1() + ").add(((Atom)var" + inst.getIntArg2() + "));\n");
					break; //kudo 2004-12-08
					//====アトムセットを操作するための命令====ここまで====
					//====整数用の組み込みボディ命令====ここから====
				case Instruction.IADD : //[-dstintatom, intatom1, intatom2]
					writer.write(tabs + "x = ((IntegerFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)((Atom)var" + inst.getIntArg3() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, new IntegerFunctor(x+y));\n");
					break; //n-kato
				case Instruction.ISUB : //[-dstintatom, intatom1, intatom2]
					writer.write(tabs + "x = ((IntegerFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)((Atom)var" + inst.getIntArg3() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, new IntegerFunctor(x-y));	\n");
					break; //nakajima 2004-01-05
				case Instruction.IMUL : //[-dstintatom, intatom1, intatom2]
					writer.write(tabs + "x = ((IntegerFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)((Atom)var" + inst.getIntArg3() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, new IntegerFunctor(x * y));	\n");
					break; //nakajima 2004-01-05
				case Instruction.IDIV : //[-dstintatom, intatom1, intatom2]
					writer.write(tabs + "x = ((IntegerFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)((Atom)var" + inst.getIntArg3() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "if (!(y == 0)) {\n");
					writer.write(tabs + "	func = new IntegerFunctor(x / y);\n");
					writer.write(tabs + "	var" + inst.getIntArg1() + " = new Atom(null, func);				\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					//if (y == 0) func = new Functor("NaN",1);
					break; //nakajima 2004-01-05, n-kato
				case Instruction.INEG : //[-dstintatom, intatom]
					writer.write(tabs + "x = ((IntegerFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, new IntegerFunctor(-x));				\n");
					break;
				case Instruction.IMOD : //[-dstintatom, intatom1, intatom2]
					writer.write(tabs + "x = ((IntegerFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)((Atom)var" + inst.getIntArg3() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "if (!(y == 0)) {\n");
					writer.write(tabs + "	func = new IntegerFunctor(x % y);\n");
					writer.write(tabs + "	var" + inst.getIntArg1() + " = new Atom(null, func);						\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					//if (y == 0) func = new Functor("NaN",1);
					break; //nakajima 2004-01-05
				case Instruction.INOT : //[-dstintatom, intatom]
					writer.write(tabs + "x = ((IntegerFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, new IntegerFunctor(~x));	\n");
					break; //nakajima 2004-01-21
				case Instruction.IAND : //[-dstintatom, intatom1, intatom2]
					writer.write(tabs + "x = ((IntegerFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)((Atom)var" + inst.getIntArg3() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, new IntegerFunctor(x & y));	\n");
					break; //nakajima 2004-01-21
				case Instruction.IOR : //[-dstintatom, intatom1, intatom2]
					writer.write(tabs + "x = ((IntegerFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)((Atom)var" + inst.getIntArg3() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, new IntegerFunctor(x | y));	\n");
					break; //nakajima 2004-01-21
				case Instruction.IXOR : //[-dstintatom, intatom1, intatom2]
					writer.write(tabs + "x = ((IntegerFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)((Atom)var" + inst.getIntArg3() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, new IntegerFunctor(x ^ y));	\n");
					break; //nakajima 2004-01-21
				case Instruction.ISAL : //[-dstintatom, intatom1, intatom2]
					writer.write(tabs + "x = ((IntegerFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)((Atom)var" + inst.getIntArg3() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, new IntegerFunctor(x << y));	\n");
					break; //nakajima 2004-01-21
				case Instruction.ISAR : //[-dstintatom, intatom1, intatom2] 
					writer.write(tabs + "x = ((IntegerFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)((Atom)var" + inst.getIntArg3() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, new IntegerFunctor(x >> y));	\n");
					break; //nakajima 2004-01-21					
				case Instruction.ISHR : //[-dstintatom, intatom1, intatom2] 
					writer.write(tabs + "x = ((IntegerFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)((Atom)var" + inst.getIntArg3() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, new IntegerFunctor(x >>> y));	\n");
					break; //nakajima 2004-01-21	
				case Instruction.IADDFUNC : //[-dstintfunc, intfunc1, intfunc2]
					writer.write(tabs + "x = ((IntegerFunctor)var" + inst.getIntArg2() + ").intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)var" + inst.getIntArg3() + ").intValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " =  new IntegerFunctor(x+y);\n");
					break; //n-kato
				case Instruction.ISUBFUNC : //[-dstintfunc, intfunc1, intfunc2]
					writer.write(tabs + "x = ((IntegerFunctor)var" + inst.getIntArg2() + ").intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)var" + inst.getIntArg3() + ").intValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " =  new IntegerFunctor(x-y);\n");
					break; //nakajima 2003-01-05
				case Instruction.IMULFUNC : //[-dstintfunc, intfunc1, intfunc2]
					writer.write(tabs + "x = ((IntegerFunctor)var" + inst.getIntArg2() + ").intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)var" + inst.getIntArg3() + ").intValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " =  new IntegerFunctor(x*y);\n");
					break; //nakajima 2003-01-05
				case Instruction.IDIVFUNC : //[-dstintfunc, intfunc1, intfunc2]
					writer.write(tabs + "x = ((IntegerFunctor)var" + inst.getIntArg2() + ").intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)var" + inst.getIntArg3() + ").intValue();\n");
					writer.write(tabs + "if (!(y == 0)) {\n");
					writer.write(tabs + "	func = new IntegerFunctor(x / y);\n");
					writer.write(tabs + "	var" + inst.getIntArg1() + " =  func;\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					//if (y == 0) func = new Functor("NaN",1);
					break; //nakajima 2003-01-05
				case Instruction.INEGFUNC : //[-dstintfunc, intfunc]
					writer.write(tabs + "x = ((IntegerFunctor)var" + inst.getIntArg2() + ").intValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " =  new IntegerFunctor(-x);\n");
					break;
				case Instruction.IMODFUNC : //[-dstintfunc, intfunc1, intfunc2]
					writer.write(tabs + "x = ((IntegerFunctor)var" + inst.getIntArg2() + ").intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)var" + inst.getIntArg3() + ").intValue();\n");
					writer.write(tabs + "if (!(y == 0)) {\n");
					writer.write(tabs + "	func = new IntegerFunctor(x % y);\n");
					writer.write(tabs + "	var" + inst.getIntArg1() + " =  func;\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					//if (y == 0) func = new Functor("NaN",1);
					break; //nakajima 2003-01-05
				case Instruction.INOTFUNC : //[-dstintfunc, intfunc]
					writer.write(tabs + "x = ((IntegerFunctor)var" + inst.getIntArg2() + ").intValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " =  new IntegerFunctor(~x);\n");
					break; //nakajima 2003-01-21
				case Instruction.IANDFUNC : //[-dstintfunc, intfunc1, intfunc2]
					writer.write(tabs + "x = ((IntegerFunctor)var" + inst.getIntArg2() + ").intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)var" + inst.getIntArg3() + ").intValue();	\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " =  new IntegerFunctor(x & y);\n");
					break; //nakajima 2003-01-21
				case Instruction.IORFUNC : //[-dstintfunc, intfunc1, intfunc2]
					writer.write(tabs + "x = ((IntegerFunctor)var" + inst.getIntArg2() + ").intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)var" + inst.getIntArg3() + ").intValue();	\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " =  new IntegerFunctor(x | y);\n");
					break; //nakajima 2003-01-21
				case Instruction.IXORFUNC : //[-dstintfunc, intfunc1, intfunc2]
					writer.write(tabs + "x = ((IntegerFunctor)var" + inst.getIntArg2() + ").intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)var" + inst.getIntArg3() + ").intValue();	\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " =  new IntegerFunctor(x ^ y);\n");
					break; //nakajima 2003-01-21
				case Instruction.ISALFUNC : //[-dstintfunc, intfunc1, intfunc2]
					writer.write(tabs + "x = ((IntegerFunctor)var" + inst.getIntArg2() + ").intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)var" + inst.getIntArg3() + ").intValue();	\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " =  new IntegerFunctor(x << y);\n");
					break; //nakajima 2003-01-21
				case Instruction.ISARFUNC : //[-dstintfunc, intfunc1, intfunc2]
					writer.write(tabs + "x = ((IntegerFunctor)var" + inst.getIntArg2() + ").intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)var" + inst.getIntArg3() + ").intValue();	\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " =  new IntegerFunctor(x >> y);\n");
					break; //nakajima 2003-01-21
				case Instruction.ISHRFUNC : //[-dstintfunc, intfunc1, intfunc2]
					writer.write(tabs + "x = ((IntegerFunctor)var" + inst.getIntArg2() + ").intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)var" + inst.getIntArg3() + ").intValue();	\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " =  new IntegerFunctor(x >>> y);\n");
					break; //nakajima 2003-01-21
					//====整数用の組み込みボディ命令====ここまで====
					//====整数用の組み込みガード命令====ここから====
				case Instruction.ILT : //[intatom1, intatom2]
					writer.write(tabs + "x = ((IntegerFunctor)((Atom)var" + inst.getIntArg1() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).intValue();	\n");
					writer.write(tabs + "if (!(!(x < y))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; // n-kato
				case Instruction.ILE : //[intatom1, intatom2]
					writer.write(tabs + "x = ((IntegerFunctor)((Atom)var" + inst.getIntArg1() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).intValue();	\n");
					writer.write(tabs + "if (!(!(x <= y))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; // n-kato
				case Instruction.IGT : //[intatom1, intatom2]
					writer.write(tabs + "x = ((IntegerFunctor)((Atom)var" + inst.getIntArg1() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).intValue();	\n");
					writer.write(tabs + "if (!(!(x > y))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; // n-kato
				case Instruction.IGE : //[intatom1, intatom2]
					writer.write(tabs + "x = ((IntegerFunctor)((Atom)var" + inst.getIntArg1() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).intValue();	\n");
					writer.write(tabs + "if (!(!(x >= y))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; // n-kato
				case Instruction.IEQ : //[intatom1, intatom2]
					writer.write(tabs + "x = ((IntegerFunctor)((Atom)var" + inst.getIntArg1() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).intValue();	\n");
					writer.write(tabs + "if (!(!(x == y))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; // n-kato
				case Instruction.INE : //[intatom1, intatom2]
					writer.write(tabs + "x = ((IntegerFunctor)((Atom)var" + inst.getIntArg1() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).intValue();	\n");
					writer.write(tabs + "if (!(!(x != y))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; // n-kato
				case Instruction.ILTFUNC : //[intfunc1, intfunc2]
					writer.write(tabs + "x = ((IntegerFunctor)var" + inst.getIntArg1() + ").intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)var" + inst.getIntArg2() + ").intValue();\n");
					writer.write(tabs + "if (!(!(x < y))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; // n-kato
				case Instruction.ILEFUNC : //[intfunc1, intfunc2]
					writer.write(tabs + "x = ((IntegerFunctor)var" + inst.getIntArg1() + ").intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)var" + inst.getIntArg2() + ").intValue();\n");
					writer.write(tabs + "if (!(!(x <= y))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; // n-kato
				case Instruction.IGTFUNC : //[intfunc1, intfunc2]
					writer.write(tabs + "x = ((IntegerFunctor)var" + inst.getIntArg1() + ").intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)var" + inst.getIntArg2() + ").intValue();\n");
					writer.write(tabs + "if (!(!(x > y))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; // n-kato
				case Instruction.IGEFUNC : //[intfunc1, intfunc2]
					writer.write(tabs + "x = ((IntegerFunctor)var" + inst.getIntArg1() + ").intValue();\n");
					writer.write(tabs + "y = ((IntegerFunctor)var" + inst.getIntArg2() + ").intValue();\n");
					writer.write(tabs + "if (!(!(x >= y))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; // n-kato
				// IEQFUNC INEFUNC FEQFUNC FNEFUNC FNEFUNC... INT2FLOATFUNC...
					//====整数用の組み込みガード命令====ここまで====
					//====浮動小数点数用の組み込みボディ命令====ここから====
				case Instruction.FADD : //[-dstfloatatom, floatatom1, floatatom2]
					writer.write(tabs + "u = ((FloatingFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).floatValue();\n");
					writer.write(tabs + "v = ((FloatingFunctor)((Atom)var" + inst.getIntArg3() + ").getFunctor()).floatValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, new FloatingFunctor(u+v));\n");
					break; //n-kato
				case Instruction.FSUB : //[-dstfloatatom, floatatom1, floatatom2]
					writer.write(tabs + "u = ((FloatingFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).floatValue();\n");
					writer.write(tabs + "v = ((FloatingFunctor)((Atom)var" + inst.getIntArg3() + ").getFunctor()).floatValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, new FloatingFunctor(u-v));	\n");
					break; // n-kato
				case Instruction.FMUL : //[-dstfloatatom, floatatom1, floatatom2]
					writer.write(tabs + "u = ((FloatingFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).floatValue();\n");
					writer.write(tabs + "v = ((FloatingFunctor)((Atom)var" + inst.getIntArg3() + ").getFunctor()).floatValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, new FloatingFunctor(u * v));	\n");
					break; // n-kato
				case Instruction.FDIV : //[-dstfloatatom, floatatom1, floatatom2]
					writer.write(tabs + "u = ((FloatingFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).floatValue();\n");
					writer.write(tabs + "v = ((FloatingFunctor)((Atom)var" + inst.getIntArg3() + ").getFunctor()).floatValue();\n");
					//if (v == 0.0) func = new Functor("NaN",1);
					//else
					writer.write(tabs + "func = new FloatingFunctor(u / v);\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, func);				\n");
					break; // n-kato
				case Instruction.FNEG : //[-dstfloatatom, floatatom]
					writer.write(tabs + "u = ((FloatingFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).floatValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, new FloatingFunctor(-u));\n");
					break; //nakajima 2004-01-23
				case Instruction.FADDFUNC : //[-dstfloatfunc, floatfunc1, floatfunc2]
					writer.write(tabs + "u = ((FloatingFunctor)var" + inst.getIntArg2() + ").floatValue();\n");
					writer.write(tabs + "v = ((FloatingFunctor)var" + inst.getIntArg3() + ").floatValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " =  new FloatingFunctor(u + v);\n");
					break; //nakajima 2004-01-23			
				case Instruction.FSUBFUNC : //[-dstfloatfunc, floatfunc1, floatfunc2]
					writer.write(tabs + "u = ((FloatingFunctor)var" + inst.getIntArg2() + ").floatValue();\n");
					writer.write(tabs + "v = ((FloatingFunctor)var" + inst.getIntArg3() + ").floatValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " =  new FloatingFunctor(u - v);\n");
					break; //nakajima 2004-01-23
				case Instruction.FMULFUNC : //[-dstfloatfunc, floatfunc1, floatfunc2]
					writer.write(tabs + "u = ((FloatingFunctor)var" + inst.getIntArg2() + ").floatValue();\n");
					writer.write(tabs + "v = ((FloatingFunctor)var" + inst.getIntArg3() + ").floatValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " =  new FloatingFunctor(u * v);\n");
					break; //nakajima 2004-01-23
				case Instruction.FDIVFUNC : //[-dstfloatfunc, floatfunc1, floatfunc2]
					writer.write(tabs + "u = ((FloatingFunctor)var" + inst.getIntArg2() + ").floatValue();\n");
					writer.write(tabs + "v = ((FloatingFunctor)var" + inst.getIntArg3() + ").floatValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " =  new FloatingFunctor(u / v);\n");
					break; //nakajima 2004-01-23
				case Instruction.FNEGFUNC : //[-dstfloatfunc, floatfunc]
					writer.write(tabs + "u = ((FloatingFunctor)var" + inst.getIntArg2() + ").floatValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " =  new FloatingFunctor(-u);\n");
					break; //nakajima 2004-01-23
					//====浮動小数点数用の組み込みボディ命令====ここまで====
					//====浮動小数点数用の組み込みガード命令====ここから====	
				case Instruction.FLT : //[floatatom1, floatatom2]
					writer.write(tabs + "u = ((FloatingFunctor)((Atom)var" + inst.getIntArg1() + ").getFunctor()).floatValue();\n");
					writer.write(tabs + "v = ((FloatingFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).floatValue();	\n");
					writer.write(tabs + "if (!(!(u < v))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; // n-kato
				case Instruction.FLE : //[floatatom1, floatatom2]
					writer.write(tabs + "u = ((FloatingFunctor)((Atom)var" + inst.getIntArg1() + ").getFunctor()).floatValue();\n");
					writer.write(tabs + "v = ((FloatingFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).floatValue();	\n");
					writer.write(tabs + "if (!(!(u <= v))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; // n-kato
				case Instruction.FGT : //[floatatom1, floatatom2]
					writer.write(tabs + "u = ((FloatingFunctor)((Atom)var" + inst.getIntArg1() + ").getFunctor()).floatValue();\n");
					writer.write(tabs + "v = ((FloatingFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).floatValue();	\n");
					writer.write(tabs + "if (!(!(u > v))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; // n-kato
				case Instruction.FGE : //[floatatom1, floatatom2]
					writer.write(tabs + "u = ((FloatingFunctor)((Atom)var" + inst.getIntArg1() + ").getFunctor()).floatValue();\n");
					writer.write(tabs + "v = ((FloatingFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).floatValue();	\n");
					writer.write(tabs + "if (!(!(u >= v))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; // n-kato
				case Instruction.FEQ : //[floatatom1, floatatom2]
					writer.write(tabs + "u = ((FloatingFunctor)((Atom)var" + inst.getIntArg1() + ").getFunctor()).floatValue();\n");
					writer.write(tabs + "v = ((FloatingFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).floatValue();	\n");
					writer.write(tabs + "if (!(!(u == v))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; // n-kato
				case Instruction.FNE : //[floatatom1, floatatom2]
					writer.write(tabs + "u = ((FloatingFunctor)((Atom)var" + inst.getIntArg1() + ").getFunctor()).floatValue();\n");
					writer.write(tabs + "v = ((FloatingFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).floatValue();	\n");
					writer.write(tabs + "if (!(!(u != v))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; // n-kato
				case Instruction.FLTFUNC : //[floatfunc1, floatfunc2]
					writer.write(tabs + "u = ((FloatingFunctor)var" + inst.getIntArg1() + ").floatValue();\n");
					writer.write(tabs + "v = ((FloatingFunctor)var" + inst.getIntArg2() + ").floatValue();\n");
					writer.write(tabs + "if (!(!(u < v))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //nakajima 2003-01-23
				case Instruction.FLEFUNC : //[floatfunc1, floatfunc2]	
					writer.write(tabs + "u = ((FloatingFunctor)var" + inst.getIntArg1() + ").floatValue();\n");
					writer.write(tabs + "v = ((FloatingFunctor)var" + inst.getIntArg2() + ").floatValue();\n");
					writer.write(tabs + "if (!(u <= v)) return false;		\n");
					break; //nakajima 2003-01-23
				case Instruction.FGTFUNC : //[floatfunc1, floatfunc2]
					writer.write(tabs + "u = ((FloatingFunctor)var" + inst.getIntArg1() + ").floatValue();\n");
					writer.write(tabs + "v = ((FloatingFunctor)var" + inst.getIntArg2() + ").floatValue();\n");
					writer.write(tabs + "if (!(u > v)) return false;		\n");
					break; //nakajima 2003-01-23
				case Instruction.FGEFUNC : //[floatfunc1, floatfunc2]
					writer.write(tabs + "u = ((FloatingFunctor)var" + inst.getIntArg1() + ").floatValue();\n");
					writer.write(tabs + "v = ((FloatingFunctor)var" + inst.getIntArg2() + ").floatValue();\n");
					writer.write(tabs + "if (!(!(u >= v))) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //nakajima 2003-01-23
					//====浮動小数点数用の組み込みガード命令====ここまで====
				case Instruction.FLOAT2INT: //[-intatom, floatatom]
					writer.write(tabs + "u = ((FloatingFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).floatValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, new IntegerFunctor((int)u));\n");
					break; // n-kato
				case Instruction.INT2FLOAT: //[-floatatom, intatom]
					writer.write(tabs + "x = ((IntegerFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).intValue();\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, new FloatingFunctor((double)x));\n");
					break; // n-kato
//未実装
//				case Instruction.GROUP:

//以下は手動生成コード
				case Instruction.RESETVARS: {
					writer.write(tabs + "{\n");
					int i = 0;
					List l = (List)inst.getArg1();
					for (int j = 0; j < l.size(); j++) {
						writer.write(tabs + "	Object t" + (i++) + " = var" + l.get(j) + ";\n");
					}
					l = (List)inst.getArg2();
					for (int j = 0; j < l.size(); j++) {
						writer.write(tabs + "	Object t" + (i++) + " = var" + l.get(j) + ";\n");
					}
					l = (List)inst.getArg3();
					for (int j = 0; j < l.size(); j++) {
						writer.write(tabs + "	Object t" + (i++) + " = var" + l.get(j) + ";\n");
					}
					for (int j = 0; j < i; j++) {
						writer.write(tabs + "	var" + j + " = t" + j + ";\n");
					}
					writer.write(tabs + "}\n");
					break;
				}
				case Instruction.LOADMODULE:
					writer.write(tabs + "	try {\n");
					writer.write(tabs + "		Class c = Class.forName(\"translated.Module_" + inst.getArg2() + "\");\n");
					writer.write(tabs + "		java.lang.reflect.Method method = c.getMethod(\"getRulesets\", null);\n");
					writer.write(tabs + "		Ruleset[] rulesets = (Ruleset[])method.invoke(null, null);\n");
					writer.write(tabs + "		for (int i = 0; i < rulesets.length; i++) {\n");
					writer.write(tabs + "			((AbstractMembrane)var" + inst.getIntArg1() + ").loadRuleset(rulesets[i]);\n");
					writer.write(tabs + "		}\n");
					writer.write(tabs + "	} catch (ClassNotFoundException e) {\n");
					writer.write(tabs + "		Env.d(e);\n");
					writer.write(tabs + "		Env.e(\"Undefined module " + inst.getArg2() + "\");\n");
					writer.write(tabs + "	} catch (NoSuchMethodException e) {\n");
					writer.write(tabs + "		Env.d(e);\n");
					writer.write(tabs + "		Env.e(\"Undefined module " + inst.getArg2() + "\");\n");
					writer.write(tabs + "	} catch (IllegalAccessException e) {\n");
					writer.write(tabs + "		Env.d(e);\n");
					writer.write(tabs + "		Env.e(\"Undefined module " + inst.getArg2() + "\");\n");
					writer.write(tabs + "	} catch (java.lang.reflect.InvocationTargetException e) {\n");
					writer.write(tabs + "		Env.d(e);\n");
					writer.write(tabs + "		Env.e(\"Undefined module " + inst.getArg2() + "\");\n");
					writer.write(tabs + "	}\n");
					break;
				case Instruction.JUMP:
					label = (InstructionList)inst.getArg1();
					add(label);
					if (Env.fNonDeterministic && ((Instruction)label.insts.get(1)).getKind() == Instruction.COMMIT) {
						writer.write(tabs + "Task.states.add(new Object[] {theInstance, \"" + label.label + "\",");
						genArgList((List)inst.getArg2(), (List)inst.getArg3(), (List)inst.getArg4());
						writer.write("});\n");
					} else {
						writer.write(tabs + "if (exec" + label.label + "(");
						genArgList((List)inst.getArg2(), (List)inst.getArg3(), (List)inst.getArg4());
						writer.write(")) {\n");
						writer.write(tabs + "	ret = true;\n");
						writer.write(tabs + "	break " + breakLabel + ";\n");
						writer.write(tabs + "}\n");
					}
					return;// false;
				case Instruction.SPEC://[formals,locals]
					break;//n-kato
				case Instruction.BRANCH :
					label = (InstructionList)inst.getArg1();
					add(label);
					Instruction in_spec = (Instruction)label.insts.get(0);
					if (in_spec.getKind() != Instruction.SPEC) {
						throw new RuntimeException("the first instruction is not spec but " + in_spec);
					}
					writer.write(tabs + "if (exec" + label.label + "(var0");
					for (int i = 1; i < in_spec.getIntArg1(); i++) {
						writer.write(", var" + i);
					}
					writer.write(")) {\n");
					writer.write(tabs + "	ret = true;\n");
					writer.write(tabs + "	break " + breakLabel + ";\n");
					writer.write(tabs + "}\n");
					break; //nakajima, n-kato
				case Instruction.LOOP :
//					label = (InstructionList)inst.getArg1();
					List list = (List)((List)inst.getArg1()).get(0);
					writer.write(tabs + "while (true) {\n");
//					writer.write(label.label + ":\n");
					writer.write("LL:\n");
					writer.write(tabs + "	{\n");
					translate(list.iterator(), tabs + "\t\t", iteratorNo, varnum, "LL");
					writer.write(tabs + "		break;\n");
					writer.write(tabs + "	}\n");
					writer.write(tabs + "	ret = false;\n");
					writer.write(tabs + "}\n");
					break; //nakajima, n-kato
				case Instruction.NOT :
					label = (InstructionList)inst.getArg1();
					writer.write(label.label + ":\n");
					writer.write(tabs + "{\n");
					translate(label.insts.iterator(), tabs + "	", iteratorNo, varnum, label.label);
					writer.write(tabs + "}\n");
					writer.write(tabs + "if (ret) {\n");
					writer.write(tabs + "	ret = false;\n");
					writer.write(tabs + "} else {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
				case Instruction.LOADRULESET:
				case Instruction.LOCALLOADRULESET:
					InterpretedRuleset rs = (InterpretedRuleset)inst.getArg2();
					String name = getClassName(rs);
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").loadRuleset(" + name + ".getInstance());\n"); 
					break;
				default:
					Env.e("Unsupported Instruction : " + inst);
			}
		}
	}
	private int nextFuncVarNum = 0;
	private String getFuncVarName(Functor func) {
		if (func.equals(Functor.INSIDE_PROXY)) {
			return "Functor.INSIDE_PROXY";
		} else if (func.equals(Functor.OUTSIDE_PROXY)) {
			return "Functor.OUTSIDE_PROXY";
		} else if (func.equals(Functor.STAR)) {
			return "Functor.STAR";
		}
		
		if (funcVarMap.containsKey(func)) {
			return (String)funcVarMap.get(func);
		} else {
			String varname = "f" + nextFuncVarNum++;
			funcVarMap.put(func, varname);
			return varname;
		}
	}
	
	private void genArgList(List l1, List l2, List l3) throws IOException {
		boolean fFirst = true;
		Iterator it = l1.iterator();
		while (it.hasNext()) {
			if (!fFirst) {
				writer.write(",");
			}
			fFirst = false;
			writer.write("var" + it.next());
		}
		it = l2.iterator();
		while (it.hasNext()) {
			if (!fFirst) {
				writer.write(",");
			}
			fFirst = false;
			writer.write("var" + it.next());
		}
		it = l3.iterator();
		while (it.hasNext()) {
			if (!fFirst) {
				writer.write(",");
			}
			fFirst = false;
			writer.write("var" + it.next());
		}
	}
}
