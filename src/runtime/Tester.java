package runtime;

/**
 * @author user
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Tester {
	public static void main(String[] args) {
		Machine machine = new Machine();
		Membrane root = (Membrane)machine.getRoot();
		
		Atom append = new Atom(root, "append", 3);
		root.addAtom(append);
		Atom cons = new Atom(root, "cons", 3);
		root.addAtom(cons);
		Atom nil1 = new Atom(root, "nil", 1);
		root.addAtom(nil1);
		Atom nil2 = new Atom(root, "nil", 1);
		root.addAtom(nil2);
		Atom nil3 = new Atom(root, "nil", 1);
		root.addAtom(nil3);
		Atom result = new Atom(root, "result", 1);
		root.addAtom(result);
		root.newLink(result, 0, append, 2);
		root.newLink(append, 0, nil1, 0);
		root.newLink(append, 1, cons, 2);
		root.newLink(cons, 0, nil2, 0);
		root.newLink(cons, 1, nil3, 0);
		
		Dumper.resetId();
		System.out.println(Dumper.dump(root));
	}
}
