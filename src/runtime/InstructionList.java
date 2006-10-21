/**
 * 作成日: 2004/4/8
 * @author n-kato
 */
package runtime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ラベル付き命令列を表すクラス。
 * 先頭の命令として必ずspecを持つ。
 * 
 * @author n-kato
 */
public class InstructionList implements Cloneable, Serializable {
	/** ラベル生成用整数値 */
	private static int nextId = 100;
	/** ラベル */
	public String label;
	/** 仮引数の個数 */
	private int formals;
	/** 局所変数の個数（仮引数の個数を含む） */
	private int locals;
	/** 命令列 (InstructionのList) */
	public List<Instruction> insts = new ArrayList<Instruction>();
//	/** 親命令列またはnull */
//	public InstructionList parent;
	/** 未使用メソッド。*/
	public void setFormals(int formals) {
	}
	/** 未使用メソッド。局所変数の個数を更新する。*/
	public void updateLocals(int locals) {
		if (this.locals < locals) this.locals = locals;
//		if (parent != null) parent.updateLocals(locals);
	}
//	/** 親命令列が無いかどうか返す */
//	public boolean isRoot() {
//		return parent == null;
//	}
	/** 通常のコンストラクタ */
	public InstructionList() {
		label = "L" + nextId++;
//		this.parent = parent;
	}

	/** パーザーで利用するコンストラクタ */
	public InstructionList(ArrayList insts) {
		this();
		this.insts = insts;
	}
	/** パーザーで利用するコンストラクタ */
	public InstructionList(int id, ArrayList insts) {
		this(insts);
		setLabel(id);
	}
	public void setLabel(int id) {
		this.label = "L" + id;
		//もっと賢い方法はないものだろうか。
		if (nextId <= id)
			nextId = id+1;
	}
	
	public Object clone() {
		InstructionList c = new InstructionList();
		c.formals = formals;
		c.locals = locals;
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
	/** 命令列の末尾に命令を追加する。*/
	public void add(Instruction inst) {
		insts.add(inst);
	}
	/** 命令列の指定の場所に命令を追加する。*/
	public void add(int index, Instruction inst) {
		insts.add(index, inst);
	}
	public List getInstructions() {
		return insts;
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
