package runtime;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;

public class Tester extends TestCase {
	public Tester(String arg0) {
		super(arg0);
	}
	public static void main(String[] args) {
		junit.textui.TestRunner.run(Tester.class);
	}
	public void testAppend() {
		Machine machine = new Machine();
		Membrane root = (Membrane)machine.getRoot();
		
		Atom append = root.newAtom("append", 3);
		Atom cons = root.newAtom("cons", 3);
		Atom nil1 = root.newAtom("nil", 1);
		Atom nil2 = root.newAtom("nil", 1);
		Atom one = root.newAtom("1", 1);
		Atom result = root.newAtom("result", 1);
		
		root.newLink(result, 0, append, 2);
		root.newLink(append, 0, nil1, 0);
		root.newLink(append, 1, cons, 2);
		root.newLink(cons, 0, one, 0);
		root.newLink(cons, 1, nil2, 0);
		
		String ret = Dumper.dump(root);
		System.out.println("\nret = " + ret);
		assertTrue(ret.equals("append(nil,cons(1,nil),result), ") ||
				   ret.equals("result(append(nil,cons(1,nil)), "));
	}
	public void testAtomSet() {
		AtomSet atomSet = new AtomSet();
		HashSet hashSet = new HashSet();
		Atom[] atom = {
			new Atom(null, new Functor("a", 1)), 
			new Atom(null, new Functor("a", 1)), 
			new Atom(null, new Functor("a", 2)), 
			new Atom(null, new Functor("b", 1))
		};

		//add/containsのテスト		
		for (int i = 0; i < 4; i++) {
			hashSet.add(atom[i]);
		}

		assertEquals(0, atomSet.size());
		assertTrue(atomSet.add(atom[0]));
		assertTrue(!atomSet.add(atom[0]));
		assertEquals(1, atomSet.size());
		assertTrue(atomSet.addAll(hashSet));
		assertEquals(4, atomSet.size());
		assertTrue(!atomSet.addAll(hashSet));
		assertTrue(areSameSet(hashSet, atomSet));
		
		//removeのテスト
		assertTrue(atomSet.remove(atom[2]));
		assertTrue(!atomSet.remove(atom[2]));
		assertEquals(atomSet.size(), 3);

		//整合性検査
		assertTrue(atomSet.verify());
				
		//iteratorのテスト		
		Iterator it = atomSet.functorIterator();
		int count = 0;
		while (it.hasNext()) {
			count++;
			Functor f = (Functor)it.next();
			assertTrue(
				f.equals(new Functor("a", 1)) ||
				f.equals(new Functor("a", 2)) ||
				f.equals(new Functor("b", 1)));
		}
		//削除されたはずのものが残っていてもかまわない（要素数０になる）
		assertTrue(count == 3 || count == 2);

		it = atomSet.iterator();
		count = 0;
		while (it.hasNext()) {
			count++;
			assertTrue(hashSet.contains(it.next()));
		}
		assertEquals(3, count);

		it = atomSet.iteratorOfFunctor(new Functor("a", 1));
		count = 0;
		while (it.hasNext()) {
			count++;
			Atom a = (Atom)it.next();
			assertTrue(a.getFunctor().equals(new Functor("a", 1)));
			assertTrue(hashSet.contains(a));
		}
		assertEquals(2, count);

		//toString
		Object[] array = atomSet.toArray();
		assertEquals(3, array.length);
		for (int i = 0; i < 3; i++) {
			assertTrue(hashSet.contains(array[i]));
		}
		Atom[] atomArray = (Atom[])atomSet.toArray(new Atom[0]);
		for (int i = 0; i < 3; i++) {
			assertTrue(hashSet.contains(atomArray[i]));
		}
		
		//isEmpty/clear
		assertTrue(!atomSet.isEmpty());
		atomSet.clear();
		assertTrue(atomSet.isEmpty());
		assertEquals(0, atomSet.size());
		
		//整合性検査
		assertTrue(atomSet.verify());
	}
	private boolean areSameSet(Set set1, Set set2) {
		HashSet tmpSet1 = new HashSet();
		HashSet tmpSet2 = new HashSet();
		tmpSet1.addAll(set1);
		tmpSet2.addAll(set2);
		return tmpSet1.equals(tmpSet2);
	}
}
