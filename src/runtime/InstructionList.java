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
 * 当分の間、先頭の命令はspec命令であることにするが、いずれメンバ変数にすべきである。
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
		Iterator it = insts.iterator();
		while (it.hasNext()) {
			c.insts.add(it.next());
		}
		return c;
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
