import runtime.*;
import java.util.*;
public class SomeInlineCodeio implements InlineCode {
	public void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 8: {
			/*inline*/
	try {
		Atom pw = mem.newAtom(new ObjectFunctor(new java.io.PrintWriter(new java.io.FileWriter(me.nth(0)))));
//		System.out.println("FILE="+me.nth(0));
		mem.relink(pw, 0, me, 1);
		me.nthAtom(0).remove();
		me.remove();
	} catch(Exception e) {}
	
			break; }
		case 9: {
			/*inline*/
	try {
		Object obj = ((ObjectFunctor)(me.nthAtom(0).getFunctor())).getValue();
		if(!(obj instanceof Process)) break;
		Atom r = mem.newAtom(new ObjectFunctor(
		  new java.io.BufferedReader( new java.io.InputStreamReader(
		    ((Process)obj).getInputStream()
		  ))
		));
//		System.out.println(r);
		mem.relink(r, 0, me, 1);
		me.nthAtom(0).remove();
		me.remove();
	} catch(Exception e) {e.printStackTrace();}
	
			break; }
		case 10: {
			/*inline*/
		try {
			java.io.BufferedReader br = (java.io.BufferedReader) ((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();
			String s = br.readLine();
			Atom result = mem.newAtom(new Functor(s==null?"":s, 1));
			mem.relink(result, 0, me, 1);
			Atom res = mem.newAtom(new Functor(s==null ? "nil" : "done", 1));
			mem.relink(res, 0, me, 2);
			me.nthAtom(0).remove();
			me.remove();
		} catch(Exception e) {Env.e(e);}
	
			break; }
		case 11: {
			/*inline*/
		try {
			java.io.PrintWriter pw = (java.io.PrintWriter) ((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();
			Atom done = mem.newAtom(new Functor("done", 1));
			if(pw!=null) {
				pw.println(me.nth(1));
			}
			mem.relink(done, 0, me, 2);
			me.nthAtom(1).remove();
			me.remove();
		} catch(Exception e) {e.printStackTrace();}
	
			break; }
		case 7: {
			/*inline*/
	try {
		Atom br = mem.newAtom(new ObjectFunctor(new java.io.BufferedReader(new java.io.FileReader(me.nth(0)))));
//		System.out.println("FILE="+me.nth(0));
		mem.relink(br, 0, me, 1);
		me.nthAtom(0).remove();
		me.remove();
	} catch(Exception e) {}
	
			break; }
		case 5: {
			/*inline*/
	String s = javax.swing.JOptionPane.showInputDialog(null, me.nth(0));
	Atom atom = mem.newAtom(new IntegerFunctor(Integer.parseInt(s)));
	mem.relink(atom,0,me,1);
	mem.removeAtom(me.nthAtom(0));
	mem.removeAtom(me);
	
			break; }
		case 3: {
			/*inline*/
	String s = javax.swing.JOptionPane.showInputDialog(null, me.nth(0));
	me.setName("done");
	me.nthAtom(0).setName(s);
	
			break; }
		case 6: {
			/*inline*/
	javax.swing.JOptionPane.showMessageDialog(null, me.nth(0));
	mem.newAtom(new Functor("done",0));
	mem.removeAtom(me.nthAtom(0));
	mem.removeAtom(me);
	
			break; }
		case 0: {
			/*inline*/
		Atom stdin = mem.newAtom(new ObjectFunctor(new java.io.BufferedReader(new java.io.InputStreamReader(System.in))));
		mem.relink(stdin, 0, me, 0);
		me.remove();
		
			break; }
		case 2: {
			/*inline*/
	String s = javax.swing.JOptionPane.showInputDialog(null, me.nth(0));
	me.setName(s);
	me.nthAtom(0).setName("done");
	
			break; }
		case 1: {
			/*inline*/
		Atom stdout = mem.newAtom(new ObjectFunctor(new java.io.PrintWriter(System.out, true)));
		mem.relink(stdout, 0, me, 0);
		me.remove();
		
			break; }
		case 12: {
			/*inline*/
		try {
			java.io.PrintWriter pw = (java.io.PrintWriter) ((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();
			pw.close();
			mem.relink(me.nthAtom(0), 0, me, 1);
			me.remove();
		} catch(Exception e) {Env.e(e);}
	
			break; }
		case 4: {
			/*inline*/
	String s = javax.swing.JOptionPane.showInputDialog(null, "Input text.");
	me.setName(s);
	
			break; }
		}
	}
}
