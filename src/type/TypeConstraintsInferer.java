package type;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import runtime.Env;
import runtime.FloatingFunctor;
import runtime.Functor;
import runtime.IntegerFunctor;
import runtime.ObjectFunctor;
import runtime.StringFunctor;

import compile.structure.Atom;
import compile.structure.LinkOccurrence;
import compile.structure.Membrane;
import compile.structure.RuleStructure;

/**
 * This class infers type constrains from COMPILE STRUCTURE
 * 
 * @author kudo
 * @since 2006/06/03 (Sat.)
 */
public class TypeConstraintsInferer {

	/** membrane contains all processes */
	private Membrane root;

	private ConstraintSet constraints = new ConstraintSet();

	/**
	 * @param root
	 */
	public TypeConstraintsInferer(Membrane root) {
		this.root = root;
	}

	/**
	 * distinguish active atom from passive atom
	 * 
	 * @param atom
	 * @return
	 */
	private static final int ACTIVE = -1;

	private static final int CONNECTOR = -2;

	private int outOfPassiveAtom(Atom atom) {
		Functor f = atom.functor;
		/* Connector */
		if (f.equals(new Functor("=", 2)))
			return CONNECTOR;
		if (f.equals(Functor.INSIDE_PROXY))
			return CONNECTOR;
		if (f.equals(Functor.OUTSIDE_PROXY))
			return CONNECTOR;
		/* Passive atom */
		/* Special atom */
		if (f instanceof IntegerFunctor)
			return 0;
		if (f instanceof FloatingFunctor)
			return 0;
		if (f instanceof StringFunctor)
			return 0;
		if (f instanceof ObjectFunctor)
			return 0;
		/* List atom */
		if (f.equals(new Functor(".", 3)))
			return 2;
		if (f.equals(new Functor("[]", 1)))
			return 0;
		/* Boolean atom */
		if (f.equals(new Functor("true", 1)))
			return 0;
		if (f.equals(new Functor("false", 1)))
			return 0;
		return ACTIVE;
	}

	public void infer() throws TypeConstraintException {
		Set freelinks = new HashSet();
		inferMembrane(root, freelinks);
		solvePathes();
		constraints.solveUnifyConstraints();
	}

	/**
	 * 
	 * @param mem
	 * @param freelinks
	 *            free links already checked
	 * @return free links in the process at mem
	 */
	private Set inferMembrane(Membrane mem, Set freelinks) {
		Iterator it = mem.atoms.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom) it.next();
			int out = outOfPassiveAtom(atom);
			if (out == ACTIVE) {
				add(new HasActiveAtomConstraint(mem.name, atom.functor));
				freelinks = inferAtom(atom, freelinks);
			} else if (out == CONNECTOR)
				continue;
			else
				freelinks = inferAtom(atom, freelinks);
		}
		it = mem.mems.iterator();
		while (it.hasNext()) {
			Membrane child = (Membrane) it.next();
			add(new HasMembraneConstraint(mem.name, child.name));
			freelinks = inferMembrane(child, freelinks);
		}
		it = mem.rules.iterator();
		while (it.hasNext()) {
			RuleStructure rs = (RuleStructure) it.next();
			inferRule(rs);
		}
		return freelinks;
	}

	private Set lhsmems = new HashSet();

	/**
	 * inferrence and add lhsmem into $lhsmems
	 * 
	 * @param rule
	 */
	private void inferRule(RuleStructure rule) {
		lhsmems.add(rule.leftMem);
		Set freelinksLeft = inferMembrane(rule.leftMem, new HashSet());
		Set freelinksRight = inferMembrane(rule.rightMem, new HashSet());
		Iterator it = freelinksLeft.iterator();
		while (it.hasNext()) {
			LinkOccurrence lo = (LinkOccurrence) it.next();
			LinkOccurrence b = getRealBuddy(lo);
			if (freelinksRight.contains(b))
				addConstraintAboutLinks(1, lo, b);
		}
	}

	private Set inferAtom(Atom atom, Set freelinks) {
		for (int i = 0; i < atom.args.length; i++) {
			updateFreeLinks(atom.args[i], freelinks);
		}
		return freelinks;
	}

	/**
	 * if $lo's buddy is in $freelinks, add an inversion constraint. Else, add
	 * $lo into $freelinks.
	 * 
	 * @param lo
	 * @param freelinks
	 *            Set of free links already checked
	 * @return updated Set
	 */
	private Set updateFreeLinks(LinkOccurrence lo, Set freelinks) {
		LinkOccurrence b = getRealBuddy(lo);
		if (freelinks.contains(b)) {
			addConstraintAboutLinks(-1, lo, b);
			freelinks.remove(b);
		} else
			freelinks.add(lo);
		return freelinks;
	}

	/**
	 * If $lo or $b is output-argument, add receive-passive-constraint. Else,
	 * add unify-constraint.
	 * 
	 * @param sign
	 * @param lo
	 * @param b
	 */
	private void addConstraintAboutLinks(int sign, LinkOccurrence lo,
			LinkOccurrence b) {
		if (lo.atom instanceof Atom) {
			int out = outOfPassiveAtom(((Atom) lo.atom));
			if (out == lo.pos) {
				addReceiveConstraint(-sign, getRealBuddy(lo),
						((Atom) lo.atom).functor);
				return;
			}
		}
		if (b.atom instanceof Atom) {
			int out = outOfPassiveAtom(((Atom) b.atom));
			if (out == b.pos) {
				addReceiveConstraint(-sign, getRealBuddy(b),
						((Atom) b.atom).functor);
				return;
			}
		}
		addUnifyConstraint(sign, lo, b);
	}

	private void addUnifyConstraint(int sign, LinkOccurrence lo,
			LinkOccurrence b) {
		add(new UnifyConstraint(new PolarizedPath(1, new RootPath(lo)),
				new PolarizedPath(sign, new RootPath(b))));
	}

	private void addReceiveConstraint(int sign, LinkOccurrence b, Functor f) {
		add(new ReceiveConstraint(new PolarizedPath(sign, new RootPath(b)), f));
	}

	/**
	 * get real buddy through =/2, $out, $in
	 * 
	 * @param lo
	 * @return
	 */
	private LinkOccurrence getRealBuddy(LinkOccurrence lo) {
		if (lo.buddy.atom instanceof Atom) {
			Atom a = (Atom) lo.buddy.atom;
			int o = outOfPassiveAtom(a);
			if (o == CONNECTOR)
				return getRealBuddy(a.args[1 - lo.buddy.pos]);
			else
				return lo.buddy;
		} else
			return lo.buddy;
	}

	private void add(Constraint c) {
		constraints.add(c);
	}

	/**
	 * change RootPath into ActiveAtomPath or TracingPath.
	 * 
	 */
	private void solvePathes() {
		Set unSolvedRPCs = constraints.getReceivePassiveConstraints();
		Iterator it = unSolvedRPCs.iterator();
		while (it.hasNext()) {
			ReceiveConstraint rpc = (ReceiveConstraint) it.next();
			rpc.setPPath(solvePolarizedPath(rpc.getPPath()));
		}
		constraints.refreshReceivePassiveConstraints(unSolvedRPCs);
		Set unSolvedUCs = constraints.getUnifyConstraints();
		it = unSolvedUCs.iterator();
		while (it.hasNext()) {
			UnifyConstraint uc = (UnifyConstraint) it.next();
			uc.setPPathes(solvePolarizedPath(uc.getPPath1()),
					solvePolarizedPath(uc.getPPath2()));
		}
	}

	private PolarizedPath solvePolarizedPath(PolarizedPath pp) {
		Path p = pp.getPath();
		if (!(p instanceof RootPath)) {
			System.out.println("fatal error in solving path.");
			return pp;
		}
		LinkOccurrence lo = ((RootPath) p).getTarget();
		if (!(lo.atom instanceof Atom))
			return pp;
		PolarizedPath tp = getPolarizedPath(lo);
		return new PolarizedPath(pp.getSign() * tp.getSign(), tp.getPath());
	}

	/**
	 * get the path to the active head atom.
	 * 
	 * @param lo :
	 *            argument of Atom (not Atomic)
	 * @return
	 */
	private PolarizedPath getPolarizedPath(LinkOccurrence lo) {
		Atom atom = (Atom) lo.atom;
		int out = outOfPassiveAtom(atom);
		if (out == ACTIVE) {
			return new PolarizedPath(1, new ActiveAtomPath(atom.mem.name,
					atom.functor, lo.pos));
		} else if (out == CONNECTOR) {
			System.out.println("fatal error in getting path.");
			return null;
		} else {
			LinkOccurrence tl = getRealBuddy(atom.args[out]);
			if (!(tl.atom instanceof Atom))
				return new PolarizedPath(1, new RootPath(tl));
			Atom ta = (Atom) tl.atom;
			PolarizedPath pp = getPolarizedPath(tl);
			if (isLHSAtom(atom) == isLHSAtom(ta))
				return new PolarizedPath(pp.getSign(), new TracingPath(pp
						.getPath(), atom.functor, lo.pos));
			else
				return new PolarizedPath(-pp.getSign(), new TracingPath(pp
						.getPath(), atom.functor, lo.pos));
		}
	}

	private boolean isLHSAtom(Atom atom) {
		return isLHSMem(atom.mem);
	}

	private boolean isLHSMem(Membrane mem) {
		return lhsmems.contains(mem);
	}

	public void printAllConstraints() {
		constraints.printAllConstraints();
	}
}