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

	public static void main(String[] args) {
		junit.textui.TestRunner.run(OptimizerTest.class);
	}

	public static void testAppend() {
		Functor append = new Functor("append", 3);
		Functor cons = new Functor("cons", 3);
		
		ArrayList list = new ArrayList();
		System.out.println("( append(X0, Y, Z), cons(A, X, X0) :- cons(A, X1, Z), append(X, Y, X1) )");
		
//		//head
//		list.add(Instruction.findatom(1, 0, append));
//		list.add(new Instruction(Instruction.DEREF, 2, 1, 0, 2));
//		list.add(new Instruction(Instruction.FUNC, 2, cons));
//		
//		list.add(Instruction.react(null, null));
//
		//body
		list.add(Instruction.spec(3, 9));
		list.add(Instruction.removeatom(1, 0, append)); //append
		list.add(Instruction.removeatom(2, 0, cons)); //cons
		list.add(Instruction.newatom(3, 0, cons));
		list.add(Instruction.newatom(4, 0, append));
		list.add(new Instruction(Instruction.GETLINK, 5, 2, 0));
		list.add(new Instruction(Instruction.INHERITLINK, 3, 0, 5, 0));
		list.add(Instruction.newlink(3, 1, 4, 2, 0));
		list.add(new Instruction(Instruction.GETLINK, 6, 1, 2));
		list.add(new Instruction(Instruction.INHERITLINK, 3, 2, 6, 0));
		list.add(new Instruction(Instruction.GETLINK, 7, 2, 1));
		list.add(new Instruction(Instruction.INHERITLINK, 4, 0, 7, 0));
		list.add(new Instruction(Instruction.GETLINK, 8, 1, 1));
		list.add(new Instruction(Instruction.INHERITLINK, 4, 1, 8, 0));

		list.add(new Instruction(Instruction.FREEATOM, 1));
		list.add(new Instruction(Instruction.FREEATOM, 2));
		list.add(new Instruction(Instruction.PROCEED));
		
		doTest(list);
	}

	public static void testAppendWithRelink() {
		Functor append = new Functor("append", 3);
		Functor cons = new Functor("cons", 3);
		
		ArrayList list = new ArrayList();
		System.out.println("( append(X0, Y, Z), cons(A, X, X0) :- cons(A, X1, Z), append(X, Y, X1) )");
		System.out.println("use relink instruction");

		list.add(new Instruction(Instruction.SPEC, 3, 5));
		list.add(Instruction.removeatom(1, 0, append)); //append
		list.add(Instruction.removeatom(2, 0, cons)); //cons
		list.add(Instruction.newatom(3, 0, cons));
		list.add(Instruction.newatom(4, 0, append));
		list.add(new Instruction(Instruction.RELINK, 3, 0, 2, 0, 0));
		list.add(Instruction.newlink(3, 1, 4, 2, 0));
		list.add(new Instruction(Instruction.RELINK, 3, 2, 1, 2, 0));
		list.add(new Instruction(Instruction.RELINK, 4, 0, 2, 1, 0));
		list.add(new Instruction(Instruction.RELINK, 4, 1, 1, 1, 0));

		list.add(new Instruction(Instruction.FREEATOM, 1));
		list.add(new Instruction(Instruction.FREEATOM, 2));
		list.add(new Instruction(Instruction.PROCEED));
		
		doTest(list);
	}
	
	public static void testReuseMem() {
		Functor a = new Functor("a", 0);
		Functor b = new Functor("b", 0);
		ArrayList list = new ArrayList();

		System.out.println("( {a, $p}, {b, $q} :- {a, $q}, {b, $p} )");

		list.add(Instruction.spec(5, 9));
		list.add(Instruction.removeatom(3, 1, a));
		list.add(Instruction.removeatom(4, 2, a));
		list.add(new Instruction(Instruction.REMOVEMEM, 1, 0));
		list.add(new Instruction(Instruction.REMOVEPROXIES, 1));
		list.add(new Instruction(Instruction.REMOVEMEM, 2, 0));
		list.add(new Instruction(Instruction.REMOVEPROXIES, 2));
		list.add(new Instruction(Instruction.REMOVETOPLEVELPROXIES, 0));
		list.add(Instruction.newmem(5, 0));
		list.add(Instruction.newmem(6, 0));
		list.add(new Instruction(Instruction.MOVECELLS, 6, 1));
		list.add(new Instruction(Instruction.MOVECELLS, 5, 2));
		list.add(new Instruction(Instruction.INSERTPROXIES, 0, 6));
		list.add(new Instruction(Instruction.INSERTPROXIES, 0, 5));
		list.add(Instruction.newatom(7, 5, a));
		list.add(Instruction.newatom(8, 6, b));

		list.add(new Instruction(Instruction.FREEMEM, 1));
		list.add(new Instruction(Instruction.FREEMEM, 2));
		list.add(new Instruction(Instruction.FREEATOM, 3));
		list.add(new Instruction(Instruction.FREEATOM, 4));
		list.add(new Instruction(Instruction.PROCEED));
		
		doTest(list);
	}

	public static void testReuseMem2() {
		ArrayList list = new ArrayList();

		System.out.println("( {$p}, {$q} :- {$q, $p} )");

		list.add(Instruction.spec(3, 4));
		list.add(new Instruction(Instruction.REMOVEMEM, 1, 0));
		list.add(new Instruction(Instruction.REMOVEPROXIES, 1));
		list.add(new Instruction(Instruction.REMOVEMEM, 2, 0));
		list.add(new Instruction(Instruction.REMOVEPROXIES, 2));
		list.add(new Instruction(Instruction.REMOVETOPLEVELPROXIES, 0));
		list.add(Instruction.newmem(3, 0));
		list.add(new Instruction(Instruction.MOVECELLS, 3, 1));
		list.add(new Instruction(Instruction.MOVECELLS, 3, 2));
		list.add(new Instruction(Instruction.INSERTPROXIES, 0, 3));
		list.add(new Instruction(Instruction.INSERTPROXIES, 0, 3));

		list.add(new Instruction(Instruction.FREEMEM, 1));
		list.add(new Instruction(Instruction.FREEMEM, 2));
		list.add(new Instruction(Instruction.PROCEED));
		
		doTest(list);
	}
	
	public static void testReuseMem3() {
		ArrayList list = new ArrayList();

		System.out.println("( {$p, {$q}} :- {$q, {$p}} )");
		
		list.add(Instruction.spec(3, 5));
		list.add(new Instruction(Instruction.REMOVEMEM, 2, 1));
		list.add(new Instruction(Instruction.REMOVEPROXIES, 2));
		list.add(new Instruction(Instruction.REMOVEMEM, 1, 0));
		list.add(new Instruction(Instruction.REMOVEPROXIES, 1));
		list.add(new Instruction(Instruction.REMOVETOPLEVELPROXIES, 0));
		list.add(Instruction.newmem(3, 0));
		list.add(Instruction.newmem(4, 3));
		list.add(new Instruction(Instruction.MOVECELLS, 4, 1));
		list.add(new Instruction(Instruction.MOVECELLS, 3, 2));
		list.add(new Instruction(Instruction.INSERTPROXIES, 3, 4));
		list.add(new Instruction(Instruction.INSERTPROXIES, 0, 3));
		
		list.add(new Instruction(Instruction.FREEMEM, 2));
		list.add(new Instruction(Instruction.FREEMEM, 1));
		list.add(new Instruction(Instruction.PROCEED));
		
		doTest(list);
	}
	private static void doTest(ArrayList list) { 
		
		System.out.println("Before Optimization:");
		Iterator it = list.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		
		//optimize
		Optimizer.optimize(list);
		
		System.out.println("After Optimization:");
		it = list.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println();
	}

}
