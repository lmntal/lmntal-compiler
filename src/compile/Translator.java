package compile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import runtime.Functor;
import runtime.Instruction;
import runtime.InstructionList;
import runtime.InterpretedRuleset;
import runtime.Rule;
import runtime.Ruleset;

/**
 * 中間命令列からJavaへの変換を行うクラス。
 * 1 つのルールセットを 1 つのクラスに変換する。
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
	/**この Ruleset 内で利用している Functor の集合*/
	private HashSet functors = new HashSet();

	/**
	 * 指定された InterpretedRuleset を Java に変換するためのインスタンスを生成する。
	 * @param ruleset 変換するルールセット
	 * @throws IOException Java ソースの出力に失敗した場合
	 */
	public Translator(InterpretedRuleset ruleset) throws IOException{
		className = "Ruleset" + ruleset.getId();
		outputFile = new File(className + ".java");
		writer = new BufferedWriter(new FileWriter(outputFile));
		this.ruleset = ruleset;
	}
	/**
	 * Javaソースを出力する。
	 * @param genMain main 関数を生成する場合は true。初期データ生成ルールに対して利用する。
	 * @throws IOException Java ソースの出力に失敗した場合
	 */
	public void translate(boolean genMain) throws IOException {
		writer.write("import runtime.*;\n");
		writer.write("import java.util.*;\n");
		writer.write("import daemon.IDConverter;\n");
		writer.write("\n");
		writer.write("public class " + className + " extends Ruleset {\n");
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
			writer.write("			result = true;\n");
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
			writer.write("			result = true;\n");
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

		it = functors.iterator();
		while (it.hasNext()) {
			Functor func = (Functor)it.next();
			writer.write("	private static final Functor func_" + func
					+ " = new Functor(\"" + func.getName() + "\", " + func.getArity() + ");\n");
		}
		
		if (genMain) {
			writer.write("	public static void main(String[] args) {\n");
			writer.write("		runtime.FrontEnd.run(new " + className + "());\n"); //todo 引数の処理
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
	 * 指定された InstructionList をJavaコードに変換する。
	 * @param instList 変換するInstructionList
	 * @throws IOException Java ソースの出力に失敗した場合
	 */
	private void translate(InstructionList instList) throws IOException {
		writer.write("	public boolean exec" + instList.label + "(");
		Instruction spec = (Instruction)instList.insts.get(0);
		int formals = spec.getIntArg1();
		int locals = spec.getIntArg2();
		if (formals > 0) {
			writer.write("Object var0");
			for (int i = 1; i < formals; i++) {
				writer.write(", Object var" + i);
			}
		}
		writer.write(") {\n");
		
		if (locals > formals) {
			writer.write("		Object var" + formals);
			for (int i = formals + 1; i < locals; i++) {
				writer.write(", var" + i);
			}
			writer.write(";\n");
		}
		
		writer.write("		Atom atom;\n");
		
		Iterator it = instList.insts.iterator();
		if (!translate(it, "		", 1)) {
			writer.write("		return false;\n");
		}
		
		writer.write("	}\n");
	}
	/**
	 * 指定された Iterator によって得られる命令列を Java コードに変換する。
	 * @param it 変換する命令列の Iterator
	 * @param tabs 出力時に利用するインデント。通常は N 個のタブ文字を指定する。
	 * @param iteratorNo 出力するコード内で次に利用する Iterator の番号。ローカル変数の重複を防ぐために必要。
	 * @return return 文を出力して終了した場合には true。コンパイルエラーを防ぐため、true を返した場合は直後に"}"以外のコードを出力してはならない。
	 * @throws IOException Java ソースの出力に失敗した場合
	 */
	private boolean translate(Iterator it, String tabs, int iteratorNo) throws IOException {
		while (it.hasNext()) {
			Functor func;
			Instruction inst = (Instruction)it.next();
			switch (inst.getKind()) {
			case Instruction.SPEC:
				break;
			case Instruction.FINDATOM:
				func = (Functor)inst.getArg3();
				functors.add(func);
				writer.write(tabs + "Iterator it" + iteratorNo + 
						" = ((AbstractMembrane)var" + inst.getIntArg2() + ").atomIteratorOfFunctor(func_" + func + ");\n");
				writer.write(tabs + "while (it" + iteratorNo + ".hasNext()) {\n");
				writer.write(tabs + "	var" + inst.getIntArg1() + " = (Atom)it" + iteratorNo + ".next();\n");
				translate(it, tabs + "	", iteratorNo + 1);
				writer.write(tabs + "}\n");
				break;
			case Instruction.JUMP:
				InstructionList label = (InstructionList)inst.getArg1();
				add(label);
				writer.write(tabs + "return exec" + label.label + "(");
				boolean fFirst = true;
				Iterator it2 = ((List)inst.getArg2()).iterator();
				while (it2.hasNext()) {
					if (!fFirst) {
						writer.write(",");
					}
					fFirst = false;
					writer.write("var" + it2.next());
				}
				it2 = ((List)inst.getArg3()).iterator();
				while (it2.hasNext()) {
					if (!fFirst) {
						writer.write(",");
					}
					fFirst = false;
					writer.write("var" + it2.next());
				}
				it2 = ((List)inst.getArg4()).iterator();
				while (it2.hasNext()) {
					if (!fFirst) {
						writer.write(",");
					}
					fFirst = false;
					writer.write("var" + it2.next());
				}
				writer.write(");\n");
				return true;
			case Instruction.COMMIT:
				break;
			case Instruction.DEQUEUEATOM:
				writer.write(tabs + "atom = (Atom)var" + inst.getIntArg1() + ";\n");
				writer.write(tabs + "atom.dequeue();\n");
				break;
			case Instruction.REMOVEATOM:
				writer.write(tabs + "atom = (Atom)var" + inst.getIntArg1() + ";\n");
				writer.write(tabs + "atom.getMem().removeAtom(atom);\n");
				break;
			case Instruction.NEWATOM:
				func = (Functor)inst.getArg3();
				functors.add(func);
				writer.write(tabs + "var" + inst.getIntArg1()
						+ " = ((AbstractMembrane)var" + inst.getIntArg2() + ").newAtom(func_" + func + ");\n");
				break;
			case Instruction.ENQUEUEATOM:
				writer.write(tabs + "atom = (Atom)var" + inst.getIntArg1() + ";\n");
				writer.write(tabs + "atom.getMem().enqueueAtom(atom);\n");
				break;
			case Instruction.FREEATOM:
				break;
			case Instruction.PROCEED:
				writer.write(tabs + "return true;\n");
				return true;
			case Instruction.LOADRULESET:
				InterpretedRuleset rs = (InterpretedRuleset)inst.getArg2();
				Translator t = new Translator(rs);
				t.translate(false);
				writer.write(tabs + "((AbstractMembrane)var" + inst.getIntArg1() + ").loadRuleset(new " + t.className + "());\n"); 
				break;
			default:
				throw new RuntimeException("Unsupported Instruction : " + inst);
			}
		}
		return false;
	}
}
