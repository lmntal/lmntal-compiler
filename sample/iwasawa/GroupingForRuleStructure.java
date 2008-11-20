package compile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import runtime.Functor;

import util.Util;

import compile.structure.Atom;
import compile.structure.Atomic;
import compile.structure.LinkOccurrence;
import compile.structure.Membrane;
import compile.structure.ProcessContext;
import compile.structure.RuleStructure;

/**
 * 
 * @author iwasawa
 *
 */
class GroupingForRuleStructure {
	private List<AtomicLink<Atom>> atomLinks;
	private List<AtomicLink<Membrane>> memLinks;
	private List<AtomicLink<ProcessContext>> typedProcessContextLinks;
	private List<AtomicLink<ProcessContext>> processContextLinks;
	private RuleStructure rs;
	
	private List<List<Atom>> atomGroupList;
	private List<List<Membrane>> memGroupList;
	private List<List<ProcessContext>> typedProcessContextGroupList;
	private	List<List<ProcessContext>> processContextGroupList;

	private Set<Atom> visitedAtoms;
	private Set<Membrane> visitedMembranes;
	private Set<ProcessContext> visitedTypedProcessContexts;
	private Set<ProcessContext> visitedProcessContexts;

	GroupingForRuleStructure(RuleStructure rs){
		atomLinks = new LinkedList<AtomicLink<Atom>>();
		memLinks = new LinkedList<AtomicLink<Membrane>>();
		processContextLinks = new LinkedList<AtomicLink<ProcessContext>>();
		typedProcessContextLinks = new LinkedList<AtomicLink<ProcessContext>>();
		this.rs = rs;

		makeLinkGraph();
	}
	
	void showGraph(){
		Util.println("show Graph : " + rs);
		Util.println(atomLinks);
		Util.println(memLinks);
		Util.println(typedProcessContextLinks);
		Util.println(processContextLinks);
	}
	
	void showGroup(){
		Util.println("show Group : " + rs);
		Util.println(atomGroupList);
		Util.println(memGroupList);
		Util.println(typedProcessContextGroupList);
		Util.println(processContextGroupList);
	}
	
	private void addGroupList(){
		atomGroupList.add(new LinkedList<Atom>());
		memGroupList.add(new LinkedList<Membrane>());
		typedProcessContextGroupList.add(new LinkedList<ProcessContext>());
		processContextGroupList.add(new LinkedList<ProcessContext>());
	}

	List<List> getatommemsList(){
		List<List> groupList = new LinkedList<List>();
		for(int i=0; i<atomGroupList.size(); i++){
			List group = new LinkedList();
			for(Atom atom : atomGroupList.get(i)){
				if(isInGlobalRootMem(atom) && ! (atom.functor.getName().equals(Functor.UNIFY.getName())))
					group.add(atom);
			}
			for(Object mem : memGroupList.get(i)){
				if(isInGlobalRootMem(mem)){
					group.add(mem);
					
				}
			}
			groupList.add(group);
		}
		return groupList;
	}
	void makeGroup(){
		atomGroupList = new ArrayList<List<Atom>>();
		memGroupList = new ArrayList<List<Membrane>>();
		typedProcessContextGroupList = new ArrayList<List<ProcessContext>>();
		processContextGroupList = new ArrayList<List<ProcessContext>>();

		visitedAtoms = new HashSet<Atom>();
		visitedMembranes = new HashSet<Membrane>();
		visitedTypedProcessContexts = new HashSet<ProcessContext>();
		visitedProcessContexts = new HashSet<ProcessContext>();
		
		for(AtomicLink<Atom> atomLink : atomLinks){
			Atom atom = atomLink.atomic;
			if(visitedAtoms.contains(atom)) continue;
			visitedAtoms.add(atom);
			addGroupList();
			atomGroupList.get(atomGroupList.size()-1).add(atom);
			makeGroupAtomic(atomLink);
		}
		for(AtomicLink<Membrane> memLink : memLinks){
			Membrane m = memLink.atomic;
			if(visitedMembranes.contains(m)) continue;
			visitedMembranes.add(m);
			addGroupList();
			memGroupList.get(memGroupList.size()-1).add(m);
			makeGroupAtomic(memLink);
		}
		for(AtomicLink<ProcessContext> tpcLink : typedProcessContextLinks){
			ProcessContext tpc = tpcLink.atomic;
			if(visitedTypedProcessContexts.contains(tpc)) continue;
			visitedTypedProcessContexts.contains(tpc);
			addGroupList();
			typedProcessContextGroupList.get(typedProcessContextGroupList.size()-1).add(tpc);
			makeGroupAtomic(tpcLink);
		}
		for(AtomicLink<ProcessContext> pcLink : processContextLinks){
			ProcessContext pc = pcLink.atomic;
			if(visitedProcessContexts.contains(pc)) continue;
			visitedProcessContexts.add(pc);
			addGroupList();
			processContextGroupList.get(processContextGroupList.size()-1).add(pc);
			makeGroupAtomic(pcLink);
		}
	}
	
	
	
	
	private <E> void makeGroupAtomic(AtomicLink<E> atomicLink){
		for(Atom a : atomicLink.atomList){
			if(visitedAtoms.contains(a)) continue;
			visitedAtoms.add(a);
			atomGroupList.get(atomGroupList.size()-1).add(a);
			makeGroupAtomic(a, atomLinks);
		}
		for(Membrane m : atomicLink.memList){
			if(visitedMembranes.contains(m)) continue;
			visitedMembranes.add(m);
			memGroupList.get(memGroupList.size()-1).add(m);
			makeGroupAtomic(m, memLinks);
		}
		for(ProcessContext tpc : atomicLink.typedProcessContextList){
			if(visitedTypedProcessContexts.contains(tpc)) continue;
			visitedTypedProcessContexts.add(tpc);
			typedProcessContextGroupList.get(typedProcessContextGroupList.size()-1).add(tpc);
			makeGroupAtomic(tpc, typedProcessContextLinks);
		}
		for(ProcessContext pc : atomicLink.processContextList){
			if(visitedProcessContexts.contains(pc)) continue;
			visitedProcessContexts.add(pc);
			processContextGroupList.get(processContextGroupList.size()-1).add(pc);
			makeGroupAtomic(pc, processContextLinks);
		}

	}
	
	private <E> void makeGroupAtomic(E atomic, List<AtomicLink<E>> atomicLinks ){
		makeGroupAtomic(get(atomicLinks, atomic));
	}

	private void makeLinkGraph(){
		makeNodeForHeadMem();

		makeLinkGraphForAtom();
		makeLinkGraphForMem();
		makeLinkGraphForTypedProcessContext();
		makeLinkGraphForProcessContext();

		return ;
	}

	private <E> boolean has(List<AtomicLink<E>> atomicLinkList, E e){
		for(AtomicLink<E> atomicLink : atomicLinkList){
			if(atomicLink.atomic == e) return true;
		}
		return false;
	}
	private <E> AtomicLink<E> get(List<AtomicLink<E>> atomicLinkList, E e){
		for(AtomicLink<E> atomicLink : atomicLinkList){
			if(atomicLink.atomic == e) return atomicLink;
		}
		Util.println("\n\n");
		Util.println(atomicLinkList);
		Util.println(e);
		throw new RuntimeException();
	}
	
	private boolean isFreeLink(LinkOccurrence lo){
		return rs.leftMem.freeLinks.containsValue(lo);
	}
	private boolean isInGlobalRootMem(Object atomic){
		if(atomic instanceof Atom){
			return ((Atom)atomic).mem.parent == null;
		}else if(atomic instanceof Membrane){
			return ((Membrane)atomic).parent == rs.leftMem;
		}else if(atomic instanceof ProcessContext){
			return ((ProcessContext)atomic).mem.parent == null;
		}else{
			throw new RuntimeException();
		}
	}
	private void makeLinkGraphForAtom(){
		for(AtomicLink<Atom> atomLink : atomLinks){
			Atom atom = atomLink.atomic;

			if(has(memLinks,  atom.mem)){
				atomLink.addMembraneListUniq(atom.mem);
			}
			for(LinkOccurrence otherSide : atom.args){
				if(isFreeLink(otherSide)) continue;
				Atomic a = otherSide.buddy.atom;
				
				if(a instanceof Atom) atomLink.addAtomListUniq((Atom)a);
				else if(a instanceof ProcessContext){
					ProcessContext pc = ((ProcessContext)a);
					if(pc.def.typed) atomLink.addTypedProcessContextListUniq(pc);
					else atomLink.addProcessContextListUniq(pc);
				}
			}
		}
	}

	private void makeLinkGraphForMem(){
		for(AtomicLink<Membrane> memLink : memLinks){
			Membrane mem = memLink.atomic;
			for(Atom a : mem.atoms){
				memLink.addAtomListUniq(a);
			}

			if(has(memLinks, mem.parent)) memLink.addMembraneListUniq(mem.parent);
			for(Membrane subMem : mem.mems){
				memLink.addMembraneListUniq(subMem);
			}

			for(ProcessContext tpc : mem.typedProcessContexts){
				memLink.addTypedProcessContextListUniq(tpc);
			}

			for(ProcessContext pc : mem.processContexts){
				memLink.addProcessContextListUniq(pc);
			}
		}
	}

	private void makeLinkGraphForTypedProcessContext(){
		for(AtomicLink<ProcessContext> tpcLink : typedProcessContextLinks){
			ProcessContext tpc = tpcLink.atomic;

			/* globalルート膜に所属している型付プロセス文脈は所属膜とリンクしない。 */
			if(has(memLinks,  tpc.mem))
				tpcLink.addMembraneListUniq(tpc.mem);

			for(LinkOccurrence otherSide : tpc.args){
				if(isFreeLink(otherSide)) continue;
				Atomic a = otherSide.buddy.atom;
	
				if(a instanceof Atom) tpcLink.addAtomListUniq((Atom)a);
				else if(a instanceof ProcessContext){
					ProcessContext pc = (ProcessContext)a;
					if(pc.def.typed) tpcLink.addTypedProcessContextListUniq(pc);
					else tpcLink.addProcessContextListUniq(pc);
				}
			}

			for(AtomicLink<ProcessContext> tpcLink2 : typedProcessContextLinks){
				ProcessContext tpc2 = tpcLink2.atomic;
				if(tpc == tpc2) continue;
				tpcLink.addTypedProcessContextListUniq(tpc2);
			}

			for(AtomicLink<ProcessContext> pcLink : processContextLinks){
				ProcessContext pc = pcLink.atomic;
				tpcLink.addProcessContextListUniq(pc);
			}
		}
	}

	private void makeLinkGraphForProcessContext(){
		for(AtomicLink<ProcessContext> pcLink : processContextLinks){
			ProcessContext pc = pcLink.atomic;

			if(has(memLinks, pc.mem))
				pcLink.addMembraneListUniq(pc.mem);

			for(LinkOccurrence otherSide : pc.args){
				if(isFreeLink(otherSide)) continue;
				Atomic a = otherSide.buddy.atom;

				if(a instanceof Atom) pcLink.addAtomListUniq((Atom)a);
				else if(a instanceof ProcessContext){
					ProcessContext pc2 = (ProcessContext)a;
					if(pc2.def.typed) pcLink.addTypedProcessContextListUniq(pc2);
					else pcLink.addProcessContextListUniq(pc2);
				}
			}

			for(AtomicLink<ProcessContext> tpcLink : typedProcessContextLinks){
				ProcessContext tpc = tpcLink.atomic;
				pcLink.addTypedProcessContextListUniq(tpc);
			}

			for(AtomicLink<ProcessContext> pcLink2 : processContextLinks){
				ProcessContext pc2 = pcLink2.atomic;
				if(pc == pc2) continue;
				pcLink.addProcessContextListUniq(pc2);
			}
		}
	}

	private void makeNodeMemForHeadMem(Membrane mem){
		for(Atom atom : mem.atoms)
			atomLinks.add(new AtomicLink<Atom>(atom));

		for(ProcessContext tpc : mem.typedProcessContexts) 
			typedProcessContextLinks.add(new AtomicLink<ProcessContext>(tpc));

		for(ProcessContext pc : mem.processContexts)
			processContextLinks.add(new AtomicLink<ProcessContext>(pc));

		for(Membrane submem : mem.mems){
			memLinks.add(new AtomicLink<Membrane>(submem));
			makeNodeMemForHeadMem(submem);
		}
	}

	private void makeNodeForHeadMem(){
		makeNodeMemForHeadMem(rs.leftMem);
	}

	/**
	 * 
	 * @author iwasawa
	 *
	 * @param <E>
	 */

	class AtomicLink <E>{
		E atomic;
		List<Atom> atomList;
		List<Membrane> memList;
		List<ProcessContext> processContextList;
		List<ProcessContext> typedProcessContextList;

		AtomicLink(E a){
			atomic = a;

			atomList = new LinkedList<Atom>();
			memList = new LinkedList<Membrane>();
			processContextList = new LinkedList<ProcessContext>();
			typedProcessContextList = new LinkedList<ProcessContext>();
		}
		

		
		void addAtomListUniq(Atom atom){
			if(atomList.contains(atom)) return;
			atomList.add(atom);
		}
		void addMembraneListUniq(Membrane mem){
			if(memList.contains(mem)) return;
			memList.add(mem);
		}
		void addTypedProcessContextListUniq(ProcessContext tpc){
			if(typedProcessContextList.contains(tpc)) return;
			typedProcessContextList.add(tpc);
		}
		void addProcessContextListUniq(ProcessContext pc){
			if(processContextList.contains(pc)) return;
			processContextList.add(pc);
		}
		public String toString(){
			StringBuffer sb = new StringBuffer();
			sb.append("\n");
			sb.append(atomic);
			sb.append(atomList);
			sb.append(memList);
			sb.append(typedProcessContextList);
			sb.append(processContextList);
			return sb.toString();
		}
	}

}
