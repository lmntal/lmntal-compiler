package type;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import runtime.Env;

public class ConstraintSet /* implements Set */{

	private Map<String, Set<AtomOccurrenceConstraint>> atomOccurrenceConstraintsMap = new HashMap<String, Set<AtomOccurrenceConstraint>>();

	private Map<String, Set<MembraneOccurrenceConstraint>> membraneOccurrenceConstraintsMap = new HashMap<String, Set<MembraneOccurrenceConstraint>>();

	// private Set receivePassiveConstraints = new HashSet();
	private Map<PolarizedPath,Set<ReceiveConstraint>> receivePassiveConstraintsMap = new HashMap<PolarizedPath,Set<ReceiveConstraint>>();

	private Set<UnifyConstraint> unifyConstraints = new HashSet<UnifyConstraint>();

	private Set<TypeVarConstraint> typeVarConstraints = new HashSet<TypeVarConstraint>();

	// public int size() {
	// return all().size();
	// }
	//
	// public boolean isEmpty() {
	// return all().isEmpty();
	// }
	//
	// public boolean contains(Object arg0) {
	// return all().contains(arg0);
	// }

	private Set<Constraint> all() {
		Set<Constraint> all = new HashSet<Constraint>();
		Iterator<String> itmn = atomOccurrenceConstraintsMap.keySet().iterator();
		while (itmn.hasNext()) {
			String memname = itmn.next();
			Set<AtomOccurrenceConstraint> haacs = atomOccurrenceConstraintsMap.get(memname);
			all.addAll(haacs);
		}
		itmn = membraneOccurrenceConstraintsMap.keySet().iterator();
		while (itmn.hasNext()) {
			String memname = itmn.next();
			Set<MembraneOccurrenceConstraint> hmcs = membraneOccurrenceConstraintsMap.get(memname);
			all.addAll(hmcs);
		}
		Iterator<Set<ReceiveConstraint>> itrc = receivePassiveConstraintsMap.values().iterator();
		while (itrc.hasNext()) {
			Set<ReceiveConstraint> rpcs = itrc.next();
			all.addAll(rpcs);
		}
		// all.addAll(receivePassiveConstraints);
		all.addAll(unifyConstraints);
		return all;
	}

	public Set getUnifyConstraints() {
		return unifyConstraints;
	}

	public Set<ReceiveConstraint> getReceivePassiveConstraints() {
		Set<ReceiveConstraint> rpcall = new HashSet<ReceiveConstraint>();
		Iterator<Set<ReceiveConstraint>> it = receivePassiveConstraintsMap.values().iterator();
		while (it.hasNext()) {
			Set<ReceiveConstraint> rpcs = it.next();
			rpcall.addAll(rpcs);
		}
		// return receivePassiveConstraints;
		return rpcall;
	}

	public void refreshReceivePassiveConstraints(Set rpcs) {
		receivePassiveConstraintsMap = new HashMap<PolarizedPath,Set<ReceiveConstraint>>();
		addAll(rpcs);
	}

	public void solveUnifyConstraints() throws TypeConstraintException {
		Iterator<UnifyConstraint> it = unifyConstraints.iterator();
		UnifySolver us = new UnifySolver();
		while (it.hasNext()) {
			UnifyConstraint uc = it.next();
			uc.regularize();
			us.add(uc);
		}
		us.solveTypeAndMode(receivePassiveConstraintsMap.values());
		typeVarConstraints = us.getTypeVarConstraints();
	}

	public Iterator iterator() {
		return all().iterator();
	}

	// public Object[] toArray() {
	// return all().toArray();
	// }
	//
	// public Object[] toArray(Object[] arg0) {
	// return all().toArray(arg0);
	// }

	public boolean add(Object arg0) {
		if (arg0 instanceof AtomOccurrenceConstraint) {
			AtomOccurrenceConstraint haac = (AtomOccurrenceConstraint) arg0;
			if (!atomOccurrenceConstraintsMap.containsKey(haac.getMemname())) {
				atomOccurrenceConstraintsMap.put(haac.getMemname(),
						new HashSet<AtomOccurrenceConstraint>());
			}
			atomOccurrenceConstraintsMap.get(haac.getMemname())
					.add(haac);
		} else if (arg0 instanceof MembraneOccurrenceConstraint) {
			MembraneOccurrenceConstraint hmc = (MembraneOccurrenceConstraint) arg0;
			if (!membraneOccurrenceConstraintsMap.containsKey(hmc.getParentName())) {
				membraneOccurrenceConstraintsMap.put(hmc.getParentName(),
						new HashSet<MembraneOccurrenceConstraint>());
			}
			membraneOccurrenceConstraintsMap.get(hmc.getParentName()).add(hmc);
		} else if (arg0 instanceof ReceiveConstraint) {
			// ReceivePassiveConstraint rpc = (ReceivePassiveConstraint) arg0;
			// receivePassiveConstraints.add(rpc);
			ReceiveConstraint rpc = (ReceiveConstraint) arg0;
			if (!receivePassiveConstraintsMap.containsKey(rpc.getPPath())) {
				receivePassiveConstraintsMap.put(rpc.getPPath(), new HashSet<ReceiveConstraint>());
			}
			receivePassiveConstraintsMap.get(rpc.getPPath()).add(rpc);
		} else if (arg0 instanceof UnifyConstraint) {
			UnifyConstraint uc = (UnifyConstraint) arg0;
			unifyConstraints.add(uc);
		} else
			System.err
					.println("fatal error. ConstraintSet is required to add Object is'nt constraint.");
		return false;
	}

	// public boolean remove(Object arg0) {
	// return false;
	// }
	//
	// public boolean containsAll(Collection arg0) {
	// return all().containsAll(arg0);
	// }
	//
	public boolean addAll(Collection arg0) {
		Iterator it = arg0.iterator();
		while (it.hasNext()) {
			add(it.next());
		}
		return false;
	}

	//
	// public boolean retainAll(Collection arg0) {
	// return false;
	// }
	//
	// public boolean removeAll(Collection arg0) {
	// return false;
	// }
	//
	// public void clear() {
	//
	// }

	/** 制約を出力する */
	public void printAllConstraints() {
		if (!Env.flgShowConstraints)
			return;
		Env.p("---Inferred Constraints : ");
		Env.p("-----AtomOccurrenceConstraints :");
		Iterator<Set<AtomOccurrenceConstraint>> itas = atomOccurrenceConstraintsMap.values().iterator();
		while (itas.hasNext()) {
			Iterator<AtomOccurrenceConstraint> itac = itas.next().iterator();
			while (itac.hasNext()) {
				Env.p(itac.next());
			}
		}
		Env.p("-----MembraneOccurrenceConstarints : ");
		Iterator<Set<MembraneOccurrenceConstraint>> itms = membraneOccurrenceConstraintsMap.values().iterator();
		while (itms.hasNext()) {
			Iterator<MembraneOccurrenceConstraint> itmc = itms.next().iterator();
			while (itmc.hasNext()) {
				Env.p(itmc.next());
			}
		}
		if (Env.flgShowAllConstraints)
			printReceiveConstraints();
		if (Env.flgShowAllConstraints)
			printUnifyConstraints();
		Env.p("-----TypeVarConstraints : ");
		TreeSet<TypeVarConstraint> tvcs = new TreeSet<TypeVarConstraint>(new TypeVarConstraintComparator());
		tvcs.addAll(typeVarConstraints);
		Iterator<TypeVarConstraint> ittvc = tvcs.iterator();
		while (ittvc.hasNext()) {
			Env.p(ittvc.next());
		}
		Env.p("---");
	}

	public void printReceiveConstraints() {
		Env.p("-----ReceiveConstarints : ");
		Iterator it = receivePassiveConstraintsMap.values().iterator();
		while (it.hasNext()) {
			Iterator it2 = ((Set) it.next()).iterator();
			while (it2.hasNext()) {
				Env.p(it2.next());
			}
		}
	}

	public void printUnifyConstraints() {
		Env.p("-----UnifyConstraints : ");
		Iterator it = unifyConstraints.iterator();
		while (it.hasNext())
			Env.p(it.next());
	}

}
