package test;

import java.util.*;
import java.io.*;
//import java.lang.reflect.*;

import compile.*;
import compile.parser.*;
import runtime.*;

/**
 * @author Mizuno
 *
 */
public class OptimizerTest {

	public static void main(String[] args) throws Exception {	
		LMNParser lp = new LMNParser(new BufferedReader(new InputStreamReader(System.in)));
					
		compile.structure.Membrane m = lp.parse();
		compile.structure.Membrane root = RulesetCompiler.runStartWithNull(m);
		InterpretedRuleset initIr = (InterpretedRuleset)root.rulesets.get(0);
		Rule initRule = (Rule)initIr.rules.get(0);
		Instruction loadrulesetInst = (Instruction)initRule.body.get(1);
		InterpretedRuleset ir = (InterpretedRuleset)loadrulesetInst.getArg2();
		List rules = ir.rules;

		ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(System.out));
		out.writeObject(rules);
		out.close();
	}
	
	public static void main2(String[] args) throws Exception {
		/////////////////////////////////////////////////////////
		// append 1
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

		list.add(new Instruction(Instruction.ENQUEUEATOM, 3));
		list.add(new Instruction(Instruction.ENQUEUEATOM, 4));
		list.add(new Instruction(Instruction.FREEATOM, 1));
		list.add(new Instruction(Instruction.FREEATOM, 2));
		list.add(new Instruction(Instruction.PROCEED));

				
		doTest(list);

		/////////////////////////////////////////////////////////
		// append 2
		
		list = new ArrayList();
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

		list.add(new Instruction(Instruction.ENQUEUEATOM, 3));
		list.add(new Instruction(Instruction.ENQUEUEATOM, 4));
		list.add(new Instruction(Instruction.FREEATOM, 1));
		list.add(new Instruction(Instruction.FREEATOM, 2));
		list.add(new Instruction(Instruction.PROCEED));
		
		doTest(list);

		/////////////////////////////////////////////////////////
		// mem 1

		Functor a = new Functor("a", 0);
		Functor b = new Functor("b", 0);
		list = new ArrayList();

		System.out.println("( {a, $p}, {b, $q} :- {a, $q}, {a, b, $p} )");

		list.add(Instruction.spec(5, 10));
		list.add(Instruction.removeatom(3, 1, a));
		list.add(Instruction.removeatom(4, 2, b));
		list.add(new Instruction(Instruction.REMOVEMEM, 1, 0));
		list.add(new Instruction(Instruction.REMOVEPROXIES, 1));
		list.add(new Instruction(Instruction.REMOVEMEM, 2, 0));
		list.add(new Instruction(Instruction.REMOVEPROXIES, 2));
		list.add(new Instruction(Instruction.REMOVETOPLEVELPROXIES, 0));
		list.add(Instruction.newmem(5, 0));
		list.add(new Instruction(Instruction.MOVECELLS, 5, 2));
		list.add(new Instruction(Instruction.INSERTPROXIES, 0, 5));
		list.add(Instruction.newmem(6, 0));
		list.add(new Instruction(Instruction.MOVECELLS, 6, 1));
		list.add(new Instruction(Instruction.INSERTPROXIES, 0, 6));
		list.add(Instruction.newatom(7, 5, a));
		list.add(Instruction.newatom(8, 6, a));
		list.add(Instruction.newatom(9, 6, b));

		list.add(new Instruction(Instruction.ENQUEUEATOM, 7));
		list.add(new Instruction(Instruction.ENQUEUEATOM, 8));
		list.add(new Instruction(Instruction.ENQUEUEATOM, 9));
		list.add(new Instruction(Instruction.FREEMEM, 1));
		list.add(new Instruction(Instruction.FREEMEM, 2));
		list.add(new Instruction(Instruction.FREEATOM, 3));
		list.add(new Instruction(Instruction.FREEATOM, 4));
		list.add(new Instruction(Instruction.PROCEED));
		
		doTest(list);


		/////////////////////////////////////////////////////////
		// mem 2

		list = new ArrayList();

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

		list.add(new Instruction(Instruction.FREEMEM, 1));
		list.add(new Instruction(Instruction.FREEMEM, 2));
		list.add(new Instruction(Instruction.PROCEED));
		
		doTest(list);


		/////////////////////////////////////////////////////////
		// mem 3

		list = new ArrayList();

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
		list.add(new Instruction(Instruction.INSERTPROXIES, 3, 4));
		list.add(new Instruction(Instruction.MOVECELLS, 3, 2));
		list.add(new Instruction(Instruction.INSERTPROXIES, 0, 3));
		
		list.add(new Instruction(Instruction.FREEMEM, 2));
		list.add(new Instruction(Instruction.FREEMEM, 1));
		list.add(new Instruction(Instruction.PROCEED));
		
		doTest(list);
		
		///////////////////////////////////////////////////////////
		// mem4
		
		list = new ArrayList();
		System.out.println("( { change, {a,$p} } :- { {b,$p} } )");

		Functor change = new Functor("change", 0);
		
		list.add(Instruction.spec(5,8));
		list.add(Instruction.dequeueatom(3));
		list.add(Instruction.dequeueatom(4));
		list.add(Instruction.removeatom(3, 1, change));
		list.add(Instruction.removeatom(4, 2, a));
		list.add(new Instruction(Instruction.REMOVEMEM, 2, 1));
	}
	
	
	private static void doTest(ArrayList list) { 
		
		System.out.println("Before Optimization:");
		Iterator it = list.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		
		//optimize
		Optimizer.optimize(null, list);
		
		System.out.println("After Optimization:");
		it = list.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println();
	}

}
