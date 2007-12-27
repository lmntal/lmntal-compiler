package test;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import runtime.Functor;
import runtime.Instruction;
import runtime.InterpretedRuleset;
import runtime.Rule;
import runtime.SymbolFunctor;
import util.Util;

import compile.Optimizer;
import compile.RulesetCompiler;
import compile.parser.LMNParser;

/**
 * @author Mizuno
 *
 */
public class OptimizerTest {

	public static void main(String[] args) throws Exception {	
		LMNParser lp = new LMNParser(new BufferedReader(new InputStreamReader(System.in)));
					
		compile.structure.Membrane m = lp.parse();
		InterpretedRuleset initIr = (InterpretedRuleset)RulesetCompiler.compileMembrane(m);
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
		Functor append = new SymbolFunctor("append", 3);
		Functor cons = new SymbolFunctor("cons", 3);
		
		ArrayList list = new ArrayList();
		Util.println("( append(X0, Y, Z), cons(A, X, X0) :- cons(A, X1, Z), append(X, Y, X1) )");
		
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
		Util.println("( append(X0, Y, Z), cons(A, X, X0) :- cons(A, X1, Z), append(X, Y, X1) )");
		Util.println("use relink instruction");

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

		Functor a = new SymbolFunctor("a", 0);
		Functor b = new SymbolFunctor("b", 0);
		list = new ArrayList();

		Util.println("( {a, $p}, {b, $q} :- {a, $q}, {a, b, $p} )");

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

		Util.println("( {$p}, {$q} :- {$q, $p} )");

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

		Util.println("( {$p, {$q}} :- {$q, {$p}} )");
		
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
		Util.println("( { change, {a,$p} } :- { {b,$p} } )");

		Functor change = new SymbolFunctor("change", 0);
		
		list.add(Instruction.spec(5,8));
		list.add(Instruction.dequeueatom(3));
		list.add(Instruction.dequeueatom(4));
		list.add(Instruction.removeatom(3, 1, change));
		list.add(Instruction.removeatom(4, 2, a));
		list.add(new Instruction(Instruction.REMOVEMEM, 2, 1));
	}
	
	
	private static void doTest(ArrayList list) { 
		
		Util.println("Before Optimization:");
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Util.println(it.next());
		}
		
		//optimize
		Optimizer.optimize(null, list);
		
		Util.println("After Optimization:");
		it = list.iterator();
		while (it.hasNext()) {
			Util.println(it.next());
		}
		Util.println("");
	}

}
