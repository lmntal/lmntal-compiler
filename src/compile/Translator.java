package compile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import runtime.Env;
import runtime.Functor;
import runtime.Inline;
import runtime.Instruction;
import runtime.InstructionList;
import runtime.IntegerFunctor;
import runtime.InterpretedRuleset;
import runtime.ObjectFunctor;
import runtime.Rule;
import runtime.Ruleset;
import runtime.StringFunctor;

/**
 * 中間命令列からJavaへの変換を行うクラス。
 * 1 つのルールセットを 1 つのクラスに変換する。
 * TODO 特殊ファンクタの処理
 * @author mizuno
 */
public class Translator {
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

	/**
	 * 指定された InterpretedRuleset を Java に変換するためのインスタンスを生成する。
	 * @param ruleset 変換するルールセット
	 * @throws IOException Java ソースの出力に失敗した場合
	 */
	public Translator(InterpretedRuleset ruleset) throws IOException{
		className = "Ruleset" + ruleset.getId();
//暫定対応
		if (translated.contains(className)) {
			return;
		}
		outputFile = new File(className + ".java");
		writer = new BufferedWriter(new FileWriter(outputFile));
		this.ruleset = ruleset;
	}
	private static HashSet translated = new HashSet();
	/**
	 * Javaソースを出力する。
	 * @param genMain main 関数を生成する場合は true。初期データ生成ルールに対して利用する。
	 * @throws IOException Java ソースの出力に失敗した場合
	 */
	public void translate(boolean genMain) throws IOException {
//暫定対応
		if (translated.contains(className)) {
			return;
		}
		translated.add(className);
		
		writer.write("import runtime.*;\n");
		writer.write("import java.util.*;\n");
		writer.write("import java.io.*;\n");
		writer.write("import daemon.IDConverter;\n");
		writer.write("\n");
		{
			Iterator il0 = Inline.inlineSet.values().iterator();
			while(il0.hasNext()) {
				runtime.InlineUnit iu = (runtime.InlineUnit)il0.next();
				Iterator il1 = iu.defs.iterator();
				while(il1.hasNext()) {
					writer.write((String)il1.next());
					writer.write("\n");
				}
			}
		}
		writer.write("\n");
		writer.write("public class " + className + " extends Ruleset {\n");
		writer.write("	private static final " + className + " theInstance = new " + className + "();\n");
		writer.write("	public static " + className + " getInstance() {\n");
		writer.write("		return theInstance;\n");
		writer.write("	}\n");
		writer.write("	private int id = " + ruleset.getId() + ";\n");
		writer.write("	private String globalRulesetID;\n");
		writer.write("	public String getGlobalRulesetID() {\n");
		writer.write("		if (globalRulesetID == null) {\n");
		writer.write("			globalRulesetID = Env.theRuntime.getRuntimeID() + \":\" + id;\n");
		writer.write("			IDConverter.registerRuleset(globalRulesetID, this);\n");
		writer.write("		}\n");
		writer.write("		return globalRulesetID;\n");
		writer.write("	}\n");
		writer.write("	public String toString() {\n");
		writer.write("		return \"@\" + id;\n");
		writer.write("	}\n");

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

		it = funcVarMap.keySet().iterator();
		while (it.hasNext()) {
			Functor func = (Functor)it.next();
			writer.write("	private static final Functor " + funcVarMap.get(func));
			if (func instanceof StringFunctor) {
				writer.write(" = new StringFunctor(\"" + escapeString((String)func.getValue()) + "\");\n");
			} else if (func instanceof ObjectFunctor) {
				throw new RuntimeException("ObjectFunctor is not supported");
			} else if (func instanceof IntegerFunctor) {
				writer.write(" = new IntegerFunctor(" + ((IntegerFunctor)func).getValue() + ");\n");
			} else {
				writer.write(" = new Functor(\"" + escapeString(func.getName()) + "\", " + func.getArity() + ");\n");
			}
		}
		
		if (genMain) {
			writer.write("	public static void main(String[] args) {\n");
			writer.write("		runtime.FrontEnd.run(" + className + ".getInstance());\n"); //todo 引数の処理
			writer.write("	}\n");
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
	 * 文字列リテラル用にエスケープ処理をする。
	 * @param data 処理する文字列
	 * @return エスケープした文字列
	 */
	private String escapeString(String data) {
		data = data.replaceAll("\r", "\\\\r");
		data = data.replaceAll("\n", "\\\\n");
		data = data.replaceAll("\\\\", "\\\\\\\\"); // \ -> \\
		data = data.replaceAll("\"", "\\\\\"");		// " -> \"
		return data;
	}

	/**
	 * 指定された InstructionList をJavaコードに変換する。
	 * @param instList 変換するInstructionList
	 * @throws IOException Java ソースの出力に失敗した場合
	 */
	private void translate(InstructionList instList) throws IOException {
		writer.write("	public boolean exec" + instList.label + "(");
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
			writer.write("		Object var" + i + ";\n");
		}
		
		writer.write("		Atom atom;\n");
		writer.write("		Functor func;\n");
		writer.write("		Link link;\n");
		writer.write("		AbstractMembrane mem;\n");
		writer.write("		int x, y;\n");
		writer.write("		double u, v;\n");

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
			Functor func;
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
			writer.write("// " + a + "\n");

			switch (inst.getKind()) {
				//メモ：LOCALHOGEはHOGEと同じコードでいい。
				//nakajima: 2003-12-12
				//メモ：コメントは引数
				//nakajima: 2003-12-12
				//====その他====ここから====
				case Instruction.DUMMY :
					writer.write(tabs + "System.out.println(\n");
					writer.write(tabs + "	\"SYSTEM ERROR: dummy instruction remains: \" + inst);\n");
					break;
					//case Instruction.UNDEF:
					//	break; //n-kato
					//====その他====ここまで====
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
					writer.write(tabs + "	mem.unlock();\n");
					writer.write(tabs + "}\n");
					break;
				case Instruction.ANYMEM :
				case Instruction.LOCALANYMEM : // anymem [-dstmem, srcmem] 
					writer.write(tabs + "Iterator it" + iteratorNo + " = ((AbstractMembrane)var" + inst.getIntArg2() + ").memIterator();\n");
					writer.write(tabs + "while (it" + iteratorNo + ".hasNext()) {\n");
					writer.write(tabs + "	AbstractMembrane submem = (AbstractMembrane) it" + iteratorNo + ".next();\n");
					writer.write(tabs + "	if (submem.lock()) {\n");
					writer.write(tabs + "		var" + inst.getIntArg1() + " = submem;\n");
					translate(it, tabs + "		", iteratorNo + 1, varnum, breakLabel);
					writer.write(tabs + "		submem.unlock();\n");
					writer.write(tabs + "	}\n");
					writer.write(tabs + "}\n");
					break;
				case Instruction.LOCK :
				case Instruction.LOCALLOCK : //[srcmem] 
					writer.write(tabs + "mem = ((AbstractMembrane)var" + inst.getIntArg1() + ");\n");
					writer.write(tabs + "if (mem.lock()) {\n");
					translate(it, tabs + "\t", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "	mem.unlock();						\n");
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
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").setName(\"" + escapeString((String)inst.getArg2()) + "\");\n");
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
				case Instruction.LOCALUNIFY:	//[atom1, pos1, atom2, pos2 (,mem)]
					// mem = mems[0]; // 昔のコード
					// mem = (AbstractMembrane)inst.getArg5(); // 正規のコード
					writer.write(tabs + "mem = ((Atom)var" + inst.getIntArg1() + ").getArg(" + inst.getIntArg2() + ")\n");
					writer.write(tabs + "		.getAtom().getMem(); // 代用コード\n");
					writer.write(tabs + "if (mem != null) {\n");
					writer.write(tabs + "	mem.unifyAtomArgs(\n");
					writer.write(tabs + "		((Atom)var" + inst.getIntArg1() + "), " + inst.getIntArg2() + ",\n");
					writer.write(tabs + "		((Atom)var" + inst.getIntArg3() + "), " + inst.getIntArg4() + " );\n");
					writer.write(tabs + "}\n");
					break; //n-kato
				case Instruction.INHERITLINK:		 //[atom1, pos1, link2, mem]
				case Instruction.LOCALINHERITLINK:	 //[atom1, pos1, link2 (,mem)]
					writer.write(tabs + "((Atom)var" + inst.getIntArg1() + ").getMem().inheritLink(\n");
					writer.write(tabs + "	((Atom)var" + inst.getIntArg1() + "), " + inst.getIntArg2() + ",\n");
					writer.write(tabs + "	(Link)var" + inst.getIntArg3() + " );\n");
					break; //n-kato
				case Instruction.UNIFYLINKS:		//[link1, link2, mem]
				case Instruction.LOCALUNIFYLINKS:	//[link1, link2 (,mem)]
					// mem = (AbstractMembrane)inst.getArg3(); // 正規のコード
					writer.write(tabs + "mem = ((Link)var" + inst.getIntArg1() + ").getAtom().getMem(); // 代用コード\n");
					writer.write(tabs + "if (mem != null) {\n");
					writer.write(tabs + "	mem.unifyLinkBuddies(\n");
					writer.write(tabs + "		((Link)var" + inst.getIntArg1() + "),\n");
					writer.write(tabs + "		((Link)var" + inst.getIntArg2() + "));\n");
					writer.write(tabs + "}\n");
					break; //n-kato
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
//					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").loadRuleset((Ruleset)inst.getArg2() );\n");
//					break; //n-kato
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
//					// モジュール膜直属のルールセットを全部読み込む
//					writer.write(tabs + "compile.structure.Membrane m = (compile.structure.Membrane)compile.Module.memNameTable.get(\"" + escapeString((String)inst.getArg2()) + "\");\n");
//					writer.write(tabs + "if(m==null) {\n");
//					writer.write(tabs + "	Env.e(\"Undefined module " + escapeString((String)inst.getArg2()) + "\");\n");
//					writer.write(tabs + "} else {\n");
//					writer.write(tabs + "	Iterator i = m.rulesets.iterator();\n");
//					writer.write(tabs + "	while (i.hasNext()) {\n");
//					writer.write(tabs + "		((AbstractMembrane)var" + inst.getIntArg1() + ").loadRuleset((Ruleset)i.next() );\n");
//					writer.write(tabs + "	}\n");
//					writer.write(tabs + "}\n");
//					break;
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
					writer.write(tabs + "var" + inst.getIntArg1() + " =  ((AbstractMembrane)var" + inst.getIntArg2() + ").copyFrom(((AbstractMembrane)var" + inst.getIntArg3() + "));\n");
					break; //kudo 2004-09-29
				case Instruction.DROPMEM : //[srcmem]
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").drop();\n");
					break; //kudo 2004-09-29
				case Instruction.LOOKUPLINK : //[-dstlink, srcmap, srclink]
					writer.write(tabs + "HashMap srcmap = (HashMap)var" + inst.getIntArg2() + ";\n");
					writer.write(tabs + "Link srclink = (Link)var" + inst.getIntArg3() + ";\n");
					writer.write(tabs + "Atom la = (Atom) srcmap.get(new Integer(srclink.getAtom().id)); // hashCode()をidに変更 (2004-10-12) n-kato\n");
					writer.write(tabs + "var" + inst.getIntArg1() + " = new Link(la, srclink.getPos());\n");
					break; //kudo 2004-10-10
					//====型付きでないプロセス文脈をコピーまたは廃棄するための命令====ここまで====
					//====制御命令====ここから====
				case Instruction.COMMIT :
					// トレーサをよぶ
					break;//
//一部は未実装、一部は下の方で手動生成
//				case Instruction.REACT :
//					writer.write(tabs + "Rule rule = (Rule) inst.getArg1();\n");
//					writer.write(tabs + "List bodyInsts = (List) rule.body;\n");
//					writer.write(tabs + "Instruction spec = (Instruction) bodyInsts.get(0);\n");
//					writer.write(tabs + "int formals = spec.getIntArg1();\n");
//					writer.write(tabs + "int locals  = spec.getIntArg2();\n");
//// // ArrayIndexOutOfBoundsException がでたので一時的に変更
//// if (locals < 10) locals = 10;
//					writer.write(tabs + "InterpretiveReactor ir = new InterpretiveReactor(locals);\n");
//					writer.write(tabs + "ir.reloadVars(this, locals, (List)inst.getArg2(),\n");
//					writer.write(tabs + "	(List)inst.getArg3(), (List)inst.getArg4());\n");
//					writer.write(tabs + "if (ir.interpret(bodyInsts, 0)) return true;\n");
//					writer.write(tabs + "if (Env.debug == 9) Env.p(\"info: body execution failed\");\n");
//				case Instruction.JUMP: {
//					writer.write(tabs + "InstructionList label = (InstructionList) inst.getArg1();\n");
//					writer.write(tabs + "List bodyInsts = (List) label.insts;\n");
//					writer.write(tabs + "Instruction spec = (Instruction) bodyInsts.get(0);\n");
//					writer.write(tabs + "int formals = spec.getIntArg1();\n");
//					writer.write(tabs + "int locals  = spec.getIntArg2();					\n");
//					writer.write(tabs + "InterpretiveReactor ir = new InterpretiveReactor(locals);\n");
//					writer.write(tabs + "ir.reloadVars(this, locals, (List)inst.getArg2(),\n");
//					writer.write(tabs + "	(List)inst.getArg3(), (List)inst.getArg4());\n");
//					writer.write(tabs + "if (ir.interpret(bodyInsts, 0)) return true;\n");
//					writer.write(tabs + "}\n");
//				case Instruction.RESETVARS :
//					writer.write(tabs + "reloadVars(this, vars.size(), (List)inst.getArg1(),\n");
//					writer.write(tabs + "		(List)inst.getArg2(), (List)inst.getArg3());\n");
//					break;
//				case Instruction.CHANGEVARS :
//					writer.write(tabs + "changeVars(this, (List)inst.getArg1(),\n");
//					writer.write(tabs + "		(List)inst.getArg2(), (List)inst.getArg3());\n");
//					break; //n-kato
				case Instruction.PROCEED:
//					writer.write(tabs + "return true; //n-kato\n");
					writer.write(tabs + "ret = true;\n");
					writer.write(tabs + "break " + breakLabel + ";\n");
					return;// true;
//				case Instruction.SPEC://[formals,locals]
//					writer.write(tabs + "extendVector(" + inst.getIntArg2() + ");\n");
//					break;//n-kato
//				case Instruction.BRANCH :
//					writer.write(tabs + "List subinsts;\n");
//					writer.write(tabs + "subinsts = ((InstructionList)inst.getArg1()).insts;\n");
//**					if (interpret(subinsts, 0))
//**						return true;
//					break; //nakajima, n-kato
//				case Instruction.LOOP :
//					writer.write(tabs + "subinsts = (List) ((List) inst.getArg1()).get(0); // reverted by n-kato: remove \".get(0)\" by mizuno\n");
//					writer.write(tabs + "while (interpret(subinsts, 0)) {\n");
//					writer.write(tabs + "}\n");
//					break; //nakajima, n-kato
//				case Instruction.RUN :
//					writer.write(tabs + "subinsts = (List) ((List) inst.getArg1()).get(0);\n");
//					writer.write(tabs + "interpret(subinsts, 0);\n");
//					break; //nakajima
//				case Instruction.NOT :
//					writer.write(tabs + "subinsts = ((InstructionList)inst.getArg1()).insts;\n");
//					writer.write(tabs + "if (!(interpret(subinsts, 0))) {\n");
//					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
//					writer.write(tabs + "}\n");
//					break; //n-kato
					//====制御命令====ここまで====
					//====型付きプロセス文脈を扱うための追加命令====ここから====
				case Instruction.EQGROUND : //[link1,link2]
					writer.write(tabs + "boolean eqground_ret = ((Link)var" + inst.getIntArg1() + ").eqGround(((Link)var" + inst.getIntArg2() + "),new HashMap());\n");
					writer.write(tabs + "if (!(!eqground_ret)) {\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //kudo 2004-12-03
				case Instruction.COPYGROUND : //[-dstlink, srclink, dstmem]
					writer.write(tabs + "var" + inst.getIntArg1() + " = ((AbstractMembrane)var" + inst.getIntArg3() + ").copyGroundFrom(((Link)var" + inst.getIntArg2() + "),new HashMap());\n");
					break; //kudo 2004-12-03
				case Instruction.REMOVEGROUND : //[srclink,srcmem]
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg2() + ").removeGround(((Link)var" + inst.getIntArg1() + "),new HashSet());\n");
					break; //kudo 2004-12-08
				case Instruction.FREEGROUND : //[srclink]
					break; //kudo 2004-12-08
					//====型付きプロセス文脈を扱うための追加命令====ここまで====
					//====型検査のためのガード命令====ここから====
				case Instruction.ISGROUND : //[-natomsfunc,srclink,srcset]
					writer.write(tabs + "int isground_ret = ((Link)var" + inst.getIntArg2() + ").isGround(new HashSet(),((Set)var" + inst.getIntArg3() + "));\n");
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
					writer.write(tabs + "		Object obj = ((ObjectFunctor)((Atom)var" + inst.getIntArg2() + ").getFunctor()).getObject();\n");
					writer.write(tabs + "		var" + inst.getIntArg1() + " = new Atom(null, new StringFunctor( obj.getClass().toString().substring(6) ));\n");
					writer.write(tabs + "	}\n");
					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
					writer.write(tabs + "}\n");
					break; //n-kato
					//====型検査のためのガード命令====ここまで====
					//====組み込み機能に関する命令====ここから====
//未実装
				case Instruction.INLINE : //[atom, inlineref]
					writer.write(tabs + "do{ Atom me = (Atom)var" + inst.getIntArg1() + ";\n");
					writer.write(tabs + "  mem = (AbstractMembrane)var0;\n");
					writer.write(tabs + Inline.getCode(inst.getIntArg1(), (String)inst.getArg2(), inst.getIntArg3()));
					writer.write(tabs + "}while(false);\n"); // インラインコードは switch の中にある前提で書かれている。
					
					break;
//					writer.write(tabs + "Inline.callInline( ((Atom)var" + inst.getIntArg1() + "), \"" + escapeString((String)inst.getArg2()) + "\", " + inst.getIntArg3() + " );\n");
//					break; //hara
					//====組み込み機能に関する命令====ここまで====
//分散機能は未実装
//					//====分散拡張用の命令====ここから====
//				case Instruction.CONNECTRUNTIME: //[srcatom] // todo StringFunctorに変える（ISSTRINGも）
//					writer.write(tabs + "func = ((Atom)var" + inst.getIntArg1() + ").getFunctor();\n");
//					writer.write(tabs + "if (!(!(func instanceof ObjectFunctor))) {\n");
//					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
//					writer.write(tabs + "}\n");
//					writer.write(tabs + "if (!(!(((ObjectFunctor)func).getObject() instanceof String))) {\n");
//					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
//					writer.write(tabs + "}\n");
//					writer.write(tabs + "if (func.getName().equals(\"\")) break; // 空文字列の場合はつねに成功とする\n");
//					writer.write(tabs + "if (!(LMNtalRuntimeManager.connectRuntime(func.getName()) == null)) {\n");
//					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
//					writer.write(tabs + "}\n");
//					break; //n-kato
//				case Instruction.GETRUNTIME: //[-dstatom,srcmem] // todo StringFunctorに変える（ISSTRINGも）
//					writer.write(tabs + "String hostname = \"\";\n");
//**					if (mems[inst.getIntArg2()].isRoot())
//**						hostname = mems[inst.getIntArg2()].getTask().getMachine().hostname;
//					writer.write(tabs + "var" + inst.getIntArg1() + " = new Atom(null, new StringFunctor(hostname));\n");
//					break; //n-kato
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
//					writer.write(tabs + "subinsts = ((InstructionList)inst.getArg1()).insts;\n");
//					writer.write(tabs + "if(!interpret(subinsts, 0)){\n");
//						//現状では必ずここに入る
//						//GROUP内の命令が成功することはない
//						//System.out.println("failed");
//					writer.write(tabs + "}\n");
//					break;
//					//現状ではまともに動かない。
//					//sakurai
//					writer.write(tabs + "				default :\n");
//					writer.write(tabs + "System.out.println(\n");
//					writer.write(tabs + "	\"SYSTEM ERROR: Invalid instruction: \" + inst);\n");
//					break;
//					writer.write(tabs + "			}\n");
//					writer.write(tabs + "		}\n");
//					writer.write(tabs + "	}\n");
//					writer.write(tabs + "}\n");

//以下は手動生成コード
				case Instruction.LOADMODULE:
					// モジュール膜直属のルールセットを全部読み込む
					compile.structure.Membrane m = (compile.structure.Membrane)compile.Module.memNameTable.get(inst.getArg2());
					if(m==null) {
						Env.e("Undefined module "+inst.getArg2());
					} else {
						Iterator i = m.rulesets.iterator();
						while (i.hasNext()) {
							//TODO 重複防止
							Translator t = new Translator((InterpretedRuleset)i.next());
							t.translate(false);
							writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").loadRuleset(" + t.className + ".getInstance());\n");
						}
					}
					break;
				case Instruction.JUMP:
					label = (InstructionList)inst.getArg1();
					add(label);
					writer.write(tabs + "if (exec" + label.label + "(");
					genArgList((List)inst.getArg2(), (List)inst.getArg3(), (List)inst.getArg4());
					writer.write(")) {\n");
					writer.write(tabs + "	ret = true;\n");
					writer.write(tabs + "	break " + breakLabel + ";\n");
					writer.write(tabs + "}\n");
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
					label = (InstructionList)inst.getArg1();
					writer.write(tabs + "while (true) {\n");
					writer.write(label.label + ":\n");
					writer.write(tabs + "	{\n");
					translate(label.insts.iterator(), tabs + "\t\t", iteratorNo, varnum, label.label);
					writer.write(tabs + "		break;\n");
					writer.write(tabs + "	}\n");
					writer.write(tabs + "	ret = false;\n");
					writer.write(tabs + "}\n");
					break; //nakajima, n-kato
				case Instruction.NOT :
//					label = (InstructionList)inst.getArg1();
//					add(label);
//					writer.write(tabs + "if (!exec" + label.label + "(var0");
//					for (int i = 1; i < varnum; i++) {
//						writer.write(", var" + i);
//					}
//					writer.write(")) {\n");
//					translate(it, tabs + "	", iteratorNo, varnum, breakLabel);
//					writer.write(tabs + "}\n");
//					break; //n-kato
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
					Translator t = new Translator(rs);
					t.translate(false);
					writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").loadRuleset(" + t.className + ".getInstance());\n"); 
					break;
				default:
					throw new RuntimeException("Unsupported Instruction : " + inst);
			}
		}
//		return false;
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
