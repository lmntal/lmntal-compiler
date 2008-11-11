package compile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
			makeGroupAtomic(atomLink);
		}
		for(AtomicLink<Membrane> memLink : memLinks){
			Membrane m = memLink.atomic;
			if(visitedMembranes.contains(m)) continue;
			visitedMembranes.add(m);
			addGroupList();
			makeGroupAtomic(memLink);
		}
		for(AtomicLink<ProcessContext> tpcLink : typedProcessContextLinks){
			ProcessContext tpc = tpcLink.atomic;
			if(visitedTypedProcessContexts.contains(tpc)) continue;
			visitedTypedProcessContexts.contains(tpc);
			addGroupList();
			makeGroupAtomic(tpcLink);
		}
		for(AtomicLink<ProcessContext> pcLink : processContextLinks){
			ProcessContext pc = pcLink.atomic;
			if(visitedProcessContexts.contains(pc)) continue;
			visitedProcessContexts.add(pc);
			addGroupList();
			makeGroupAtomic(pcLink);
		}
	}
	
	
	
	
	private <E> void makeGroupAtomic(AtomicLink<E> atomicLink){
		for(Atom a : atomicLink.atomList){
			atomGroupList.get(atomGroupList.size()-1).add(a);
			makeGroupAtomic(a, visitedAtoms, atomLinks);
		}
		for(Membrane m : atomicLink.memList){
			memGroupList.get(memGroupList.size()-1).add(m);
			makeGroupAtomic(m, visitedMembranes, memLinks);
		}
		for(ProcessContext tpc : atomicLink.typedProcessContextList){
			typedProcessContextGroupList.get(typedProcessContextGroupList.size()-1).add(tpc);
			makeGroupAtomic(tpc, visitedTypedProcessContexts, typedProcessContextLinks);
		}
		for(ProcessContext pc : atomicLink.processContextList){
			processContextGroupList.get(processContextGroupList.size()-1).add(pc);
			makeGroupAtomic(pc, visitedProcessContexts, processContextLinks);
		}

	}
	
	private <E> void makeGroupAtomic(E atomic, Set<E> visited, List<AtomicLink<E>> atomicLinks){
		if(visited.contains(atomic)) return;
		visited.add(atomic);
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
		throw new RuntimeException();
	}
	private void makeLinkGraphForAtom(){
		for(AtomicLink<Atom> atomLink : atomLinks){
			Atom atom = atomLink.atomic;

			if(has(memLinks,  atom.mem)){
				atomLink.memList.add(atom.mem);
			}
			for(LinkOccurrence otherSide : atom.args){
				Atomic a = otherSide.buddy.atom;
				if(a == null) continue;
				if(a instanceof Atom) atomLink.atomList.add((Atom)a);
				else if(a instanceof ProcessContext){
					if(rs.leftMem.typedProcessContexts.contains(a)){
						atomLink.typedProcessContextList.add((ProcessContext)a);
					}else if(rs.leftMem.processContexts.contains(a)){
						atomLink.processContextList.add((ProcessContext)a);
					}else{
						throw new RuntimeException();
					}
				}
			}
		}
	}

	private void makeLinkGraphForMem(){
		for(AtomicLink<Membrane> memLink : memLinks){
			Membrane mem = memLink.atomic;
			for(Atom a : mem.atoms){
				memLink.atomList.add(a);
			}

			if(has(memLinks, mem.parent)) memLink.memList.add(mem.parent);
			for(Membrane subMem : mem.mems){
				memLink.memList.add(subMem);
			}

			for(ProcessContext tpc : mem.typedProcessContexts){
				memLink.typedProcessContextList.add(tpc);
			}

			for(ProcessContext pc : mem.processContexts){
				memLink.processContextList.add(pc);
			}
		}
	}

	private void makeLinkGraphForTypedProcessContext(){
		for(AtomicLink<ProcessContext> tpcLink : typedProcessContextLinks){
			ProcessContext tpc = tpcLink.atomic;

			if(has(memLinks,  tpc.mem))
				tpcLink.memList.add(tpc.mem);

			for(LinkOccurrence otherSide : tpc.args){
				Atomic a = otherSide.buddy.atom;
				if(a == null) continue;
				if(a instanceof Atom) tpcLink.atomList.add((Atom)a);
				else if(a instanceof ProcessContext){
					if(rs.leftMem.typedProcessContexts.contains(a)){
						tpcLink.typedProcessContextList.add((ProcessContext)a);
					}else if(rs.leftMem.processContexts.contains(a)){
						tpcLink.processContextList.add((ProcessContext)a);
					}else{
						throw new RuntimeException();
					}
				}
			}

			for(AtomicLink<ProcessContext> tpcLink2 : typedProcessContextLinks){
				ProcessContext tpc2 = tpcLink2.atomic;
				if(tpc == tpc2) continue;
				tpcLink.typedProcessContextList.add(tpc2);
			}

			for(AtomicLink<ProcessContext> pcLink : processContextLinks){
				ProcessContext pc = pcLink.atomic;
				tpcLink.processContextList.add(pc);
			}
		}
	}

	private void makeLinkGraphForProcessContext(){
		for(AtomicLink<ProcessContext> pcLink : processContextLinks){
			ProcessContext pc = pcLink.atomic;

			if(has(memLinks, pc.mem))
				pcLink.memList.add(pc.mem);

			for(LinkOccurrence otherSide : pc.args){
				Atomic a = otherSide.buddy.atom;
				if(a == null) continue;
				if(a instanceof Atom) pcLink.atomList.add((Atom)a);
				else if(a instanceof ProcessContext){
					if(rs.leftMem.typedProcessContexts.contains(a)){
						pcLink.typedProcessContextList.add((ProcessContext)a);
					}else if(rs.leftMem.processContexts.contains(a)){
						pcLink.processContextList.add((ProcessContext)a);
					}else{
						throw new RuntimeException();
					}
				}
			}

			for(AtomicLink<ProcessContext> tpcLink : typedProcessContextLinks){
				ProcessContext tpc = tpcLink.atomic;
				pcLink.typedProcessContextList.add(tpc);
			}

			for(AtomicLink<ProcessContext> pcLink2 : processContextLinks){
				ProcessContext pc2 = pcLink2.atomic;
				if(pc == pc2) continue;
				pcLink.processContextList.add(pc2);
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

		public String toString(){
			StringBuffer sb = new StringBuffer();
			sb.append(atomic);
			sb.append(atomList);
			sb.append(memList);
			sb.append(typedProcessContextList);
			sb.append(processContextList);
			return sb.toString();

		}
	}

}
