/**
 * 作成日: 2004/4/8
 * @author n-kato
 */
package runtime;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * ラベル付き命令列を表すクラス。
 * <p>
 * 当分の間先頭の命令はspec命令であることにしておく。
 * いずれspecの引数値はこのクラスのメンバ変数にすべきである。
 * その際、InterpretedRuleset.javaの「[0]はspecなのでスキップする」を廃止すること。
 * 
 * @author n-kato
 */
public class InstructionList implements Cloneable {
	/** ラベル生成用整数値 */
	private static int nextId = 100;
	/** ラベル */
	public String label;
	/** 命令列 (InstructionのList) */
	public List insts = new ArrayList();

	public InstructionList() {
		label = "L" + nextId++;
	}
	public InstructionList(String label) {
		this.label = label;
	}
	public Object clone() {
		InstructionList c = new InstructionList();
		c.insts = cloneInstructions(insts);
		return c;
	}
	/** 指定された命令列（InstructionのList）のクローンを作成する。
	 * @param insts クローンを作成する命令列
	 * @return 作成したクローン */
	public static List cloneInstructions(List insts) {
		List ret = new ArrayList();
		Iterator it = insts.iterator();
		while (it.hasNext()) {
			Instruction inst = (Instruction)it.next();
			ret.add(inst.clone());
		}
		return ret;
	}
	public String toString() {
		return label;
	}
	public String dump() {
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < insts.size(); i++){
			buffer.append(insts.get(i));
			buffer.append("\n");
		}
		return buffer.toString();
	}
}
