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

	private Map hasActiveAtomConstraintsMap = new HashMap();

	private Map hasMembraneConstraintsMap = new HashMap();

	// private Set receivePassiveConstraints = new HashSet();
	private Map receivePassiveConstraintsMap = new HashMap();

	private Set unifyConstraints = new HashSet();

	private Set typeVarConstraints = new HashSet();

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

	private Set all() {
		Set all = new HashSet();
		Iterator it = hasActiveAtomConstraintsMap.keySet().iterator();
		while (it.hasNext()) {
			String memname = (String) it.next();
			Set haacs = (Set) hasActiveAtomConstraintsMap.get(memname);
			all.addAll(haacs);
		}
		it = hasMembraneConstraintsMap.keySet().iterator();
		while (it.hasNext()) {
			String memname = (String) it.next();
			Set hmcs = (Set) hasMembraneConstraintsMap.get(memname);
			all.addAll(hmcs);
		}
		it = receivePassiveConstraintsMap.values().iterator();
		while (it.hasNext()) {
			Set rpcs = (Set) it.next();
			all.addAll(rpcs);
		}
		// all.addAll(receivePassiveConstraints);
		all.addAll(unifyConstraints);
		return all;
	}

	public Set getUnifyConstraints() {
		return unifyConstraints;
	}

	public Set getReceivePassiveConstraints() {
		Set rpcall = new HashSet();
		Iterator it = receivePassiveConstraintsMap.values().iterator();
		while (it.hasNext()) {
			Set rpcs = (Set) it.next();
			rpcall.addAll(rpcs);
		}
		// return receivePassiveConstraints;
		return rpcall;
	}

	public void refreshReceivePassiveConstraints(Set rpcs) {
		receivePassiveConstraintsMap = new HashMap();
		addAll(rpcs);
	}

	public void solveUnifyConstraints() throws TypeConstraintException {
		Iterator it = unifyConstraints.iterator();
		UnifySolver us = new UnifySolver();
		while (it.hasNext()) {
			UnifyConstraint uc = (UnifyConstraint) it.next();
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
			if (!hasActiveAtomConstraintsMap.containsKey(haac.getMemname())) {
				hasActiveAtomConstraintsMap.put(haac.getMemname(),
						new HashSet());
			}
			((Set) hasActiveAtomConstraintsMap.get(haac.getMemname()))
					.add(haac);
		} else if (arg0 instanceof MembraneOccurrenceConstraint) {
			MembraneOccurrenceConstraint hmc = (MembraneOccurrenceConstraint) arg0;
			if (!hasMembraneConstraintsMap.containsKey(hmc.getParentName())) {
				hasMembraneConstraintsMap.put(hmc.getParentName(),
						new HashSet());
			}
			((Set) hasMembraneConstraintsMap.get(hmc.getParentName())).add(hmc);
		} else if (arg0 instanceof ReceiveConstraint) {
			// ReceivePassiveConstraint rpc = (ReceivePassiveConstraint) arg0;
			// receivePassiveConstraints.add(rpc);
			ReceiveConstraint rpc = (ReceiveConstraint) arg0;
			if (!receivePassiveConstraintsMap.containsKey(rpc.getPPath())) {
				receivePassiveConstraintsMap.put(rpc.getPPath(), new HashSet());
			}
			((Set) receivePassiveConstraintsMap.get(rpc.getPPath())).add(rpc);
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
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// public boolean removeAll(Collection arg0) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// public void clear() {
	// // TODO Auto-generated method stub
	//
	// }

	public void printAllConstraints() {
		if (!Env.flgShowConstraints)
			return;
		Env.p("---Inferred Constraints : ");
		Env.p("-----HasActiveAtomConstraints :");
		Iterator it = hasActiveAtomConstraintsMap.values().iterator();
		while (it.hasNext()) {
			Iterator it2 = ((Set) it.next()).iterator();
			while (it2.hasNext()) {
				Env.p(it2.next());
			}
		}
		Env.p("-----HasMembraneConstarints : ");
		it = hasMembraneConstraintsMap.values().iterator();
		while (it.hasNext()) {
			Iterator it2 = ((Set) it.next()).iterator();
			while (it2.hasNext()) {
				Env.p(it2.next());
			}
		}
		if (Env.flgShowAllConstraints)
			printReceiveConstraints();
		if (Env.flgShowAllConstraints)
			printUnifyConstraints();
		Env.p("-----TypeVarConstraints : ");
		TreeSet tvcs = new TreeSet(new TypeVarConstraintComparator());
		tvcs.addAll(typeVarConstraints);
		it = tvcs.iterator();
		while (it.hasNext()) {
			Env.p(it.next());
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
