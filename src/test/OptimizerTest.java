/*
 * 作成日: 2003/11/30
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
package test;

import compile.Optimizer;
import runtime.Instruction;
import runtime.Functor;

import java.util.*;

import junit.framework.TestCase;

/**
 * @author Mizuno
 *
 */
public class OptimizerTest extends TestCase {
	public OptimizerTest(String arg0) {
		super(arg0);
	}

	public static void main2(String[] args) {
		junit.textui.TestRunner.run(OptimizerTest.class);
	}

	public static void main(String[] args) {
		Functor append = new Functor("append", 3);
		Functor cons = new Functor("cons", 3);
		
		List list = new ArrayList();
		System.out.println("( append(X0, Y, Z), cons(A, X, X0) :- cons(A, X1, Z), append(X, Y, X1) )");
		
//		//head
//		list.add(Instruction.findatom(1, 0, append));
//		list.add(new Instruction(Instruction.DEREF, 2, 1, 0, 2));
//		list.add(new Instruction(Instruction.FUNC, 2, cons));
//		
//		list.add(Instruction.react(null, null));
//
		//body
//		list.add(Instruction.removeatom(1)); //append
		list.add(new Instruction(Instruction.REMOVEATOM, 1, 0, append));
//		list.add(Instruction.removeatom(2)); //cons
		list.add(new Instruction(Instruction.REMOVEATOM, 2, 0, cons));
		list.add(Instruction.newatom(3, 0, cons));
		list.add(Instruction.newatom(4, 0, append));
//		list.add(new Instruction(Instruction.RELINK, 3, 0, 2, 0));
		list.add(new Instruction(Instruction.GETLINK, 5, 2, 0));
		list.add(new Instruction(Instruction.INHERITLINK, 3, 0, 5));
		list.add(Instruction.newlink(3, 1, 4, 2));
//		list.add(new Instruction(Instruction.RELINK, 3, 2, 1, 2));
		list.add(new Instruction(Instruction.GETLINK, 6, 1, 2));
		list.add(new Instruction(Instruction.INHERITLINK, 3, 2, 6));
//		list.add(new Instruction(Instruction.RELINK, 4, 0, 2, 1));
		list.add(new Instruction(Instruction.GETLINK, 7, 2, 1));
		list.add(new Instruction(Instruction.INHERITLINK, 4, 0, 7));
//		list.add(new Instruction(Instruction.RELINK, 4, 1, 1, 1));
		list.add(new Instruction(Instruction.GETLINK, 8, 1, 1));
		list.add(new Instruction(Instruction.INHERITLINK, 4, 1, 8));
		
		System.out.println("Before Optimization:");
		Iterator it = list.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println();
		
		//optimize
		Optimizer.optimize(list);
		
		System.out.println("After Optimization:");
		it = list.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
}
