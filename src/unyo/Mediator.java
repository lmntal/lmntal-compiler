package unyo;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.sun.net.httpserver.Authenticator.Success;

import runtime.Atom;
import runtime.Dumper;
import runtime.Env;
import runtime.Functor;
import runtime.InterpretedRuleset;
import runtime.LMNtalRuntime;
import runtime.Membrane;
import runtime.Rule;
import runtime.Ruleset;
import runtime.SymbolFunctor;
import runtime.Task;

public class Mediator {

	final static
	private long SLEEP_TIME = 50;

	// <削除された膜のID, 親膜のID>
	static
	private LinkedHashMap<String, String> removedMembrane_;

	static
	private LinkedList<Membrane> addedMembrane_;

	static
	private LinkedList<Membrane> modifiedMembrane_;

	// <削除されたアトムのID, 親膜のID>
	static
	private LinkedHashMap<String, String> removedAtom_;

	static
	private LinkedList<Atom> addedAtom_;

	static
	private LinkedList<Atom> modifiedAtom_;

	static
	private Rule currentRule_;

	static
	private HashMap<Rule, String> rules_;

	static
	private Object unyoObj_;

	static
	private Class unyoClass_;

	static
	private Method setState_;

	static
	private Method end_;

	static
	private Method print_;

	static
	private Method errPrint_;

	static
	private Method sync_;

	static
	public boolean releasing = false;
	
	static
	private boolean taskEnd = false;

	static
	public boolean unyoWait_ = false;
	
	static
	private Membrane root_ = null;
	
	static
	private HashSet<InterpretedRuleset> breakpointRuleset_ = null;
	
	static
	private boolean error_ = false;

	static
	public void release(){
		//if(Env.theRuntime != null){
		//	Env.theRuntime.terminate();
		//}
		//System.out.println("release : terminate end");
		releasing = true;
//		Env.fUNYO = false;
		Env.srcs.clear();
//		doNext_ = false;
		removedMembrane_.clear();
		addedMembrane_.clear();
		modifiedMembrane_.clear();
		removedAtom_.clear();
		addedAtom_.clear();
		modifiedAtom_.clear();
		rules_.clear();
	}

	static
	public void init(){

		releasing = false;
		taskEnd = false;

		removedMembrane_ = new LinkedHashMap<String, String>();
		addedMembrane_ = new LinkedList<Membrane>();
		modifiedMembrane_ = new LinkedList<Membrane>();
		removedAtom_ = new LinkedHashMap<String, String>();
		addedAtom_ = new LinkedList<Atom>();
		modifiedAtom_ = new LinkedList<Atom>();
		currentRule_ = new Rule();
		rules_ = new HashMap<Rule, String>();
//		setBreakPoint("{{a}},{{a}},{{a}},{{a}},{{a}},{{a}} :- .");

		try {
			unyoClass_ = Class.forName("jp.ac.waseda.info.ueda.unyo.mediator.Synchronizer");
			unyoObj_ = unyoClass_.newInstance();
			Env.fUNYO = true;

			sync_
			= unyoClass_.getMethod("sync");

			print_
			= unyoClass_.getMethod("print", String.class, int.class);

			errPrint_
			= unyoClass_.getMethod("errPrint", String.class);

			setState_
			= unyoClass_.getMethod("setState",
					Object.class,
					HashMap.class,
					LinkedHashMap.class,
					LinkedList.class,
					LinkedList.class,
					LinkedHashMap.class,
					LinkedList.class,
					LinkedList.class,
					Object.class,
					String.class);

			end_ = unyoClass_.getMethod("end");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	static
	public void updateAtom(Object atom, String name){
		Atom targetAtom = (Atom)atom;
		targetAtom.setName(name);
	}

	static
	public void updateMembrane(Object mem, String name){
		Membrane targetMem = (Membrane)mem;
		targetMem.setName(name);
	}

	static
	public Atom addAtom(Object mem, String name){
		Membrane targetMem = (Membrane)mem;
		SymbolFunctor func = new SymbolFunctor(name, 0);
		Atom atom = new Atom(targetMem, func);
		targetMem.addAtom(atom);
		return atom;
	}

	static
	public Membrane addMembrane(Object mem, String name){
		Membrane targetMem = (Membrane)mem;
		Membrane newMem = new Membrane();
		newMem.setName(name);
		targetMem.addMem(newMem);
		return newMem;
	}

	static
	public LinkedList addLink(Object source,
			Object sourcem,
			String sourceName,
			int sl,
			Object target,
			Object targetm,
			String targetName,
			int tl){
		LinkedList<Object> newLinkSet = new LinkedList<Object>();

		Atom sourceAtom = (Atom) source;
		Atom targetAtom = (Atom) target;
		Membrane sourceMem = (Membrane) sourcem;
		Membrane targetMem = (Membrane) targetm;

		Functor newSourceFunctor;
		Functor newTargetFunctor;

		newSourceFunctor =
			new SymbolFunctor(sourceName, sl + 1);
		newTargetFunctor =
			new SymbolFunctor(targetName, tl + 1);

		Atom newSourceAtom = new Atom(sourceMem, newSourceFunctor);
		Atom newTargetAtom = new Atom(targetMem, newTargetFunctor);
		sourceAtom.getMem().addAtom(newSourceAtom);
		targetAtom.getMem().addAtom(newTargetAtom);

		for(int i = 0; i < sl; i++){
			sourceMem.relink(newSourceAtom, i, sourceAtom, i);
		}
		sourceAtom.remove();

		for(int i = 0; i < tl; i++){
			targetMem.relink(newTargetAtom, i, targetAtom, i);
		}
		targetAtom.remove();

		newSourceAtom.getMem().newLink(newSourceAtom,
				newSourceAtom.getEdgeCount() - 1,
				newTargetAtom,
				newTargetAtom.getEdgeCount() - 1);
		newLinkSet.add(newSourceAtom);
		newLinkSet.add(newTargetAtom);

		return newLinkSet;
	}

	public static void errPrintln(String msg){
		errPrint(msg + System.getProperty("line.separator"));
	}

	public static void errPrint(String msg){
		error_ = true;
		try {
			errPrint_.invoke(unyoObj_, msg);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static void println(String msg, int mode){
		print(msg  + System.getProperty("line.separator"), mode);
	}

	public static void println(Object msg, int mode){
		println(msg.toString(), mode);
	}

	public static void print(String msg,int mode){
		try {
			print_.invoke(unyoObj_, msg, mode);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	/**
	 * mode 0-->LogPanelに出力
	 * 1-->ProcessPanelにルール出力
	 * 2-->ProcessPanelに結果出力
	 * @param msg
	 * @param mode
	 */
	public static void print(Object msg, int mode){
		print(msg.toString(), mode);
	}

	public static void printRoot(){
		Membrane root = Env.theRuntime.getGlobalRoot();
		print(Dumper.dump(root) + System.getProperty("line.separator"), 2);
	}
	/* ルールもfulltext出力する */
	public static void printRootAll(){
		Membrane root = Env.theRuntime.getGlobalRoot();
		Env.fUNYO_d = true;
		print(Dumper.dump(root), 2);
		Env.fUNYO_d = false;
	}

	static
	public void end(){
		if(!taskEnd) return;
		try {
			end_.invoke(unyoObj_);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	static
	public void endTask(boolean b){
		taskEnd = b;
	}

	/**
	 * 全膜のルールを再帰的に収集する
	 * @param mem ルート膜
	 */
	private static void collectAllRules(Membrane mem) {
		Iterator<Ruleset> itr = mem.rulesetIterator();
		while (itr.hasNext()) {
			Object o = itr.next();
			if (!(o instanceof InterpretedRuleset)) continue;
			InterpretedRuleset ruleset = (InterpretedRuleset)o;
			for(Rule rule : ruleset.rules){
				rules_.put(rule, rule.getFullText());
			}
		}

		Iterator<Membrane> memIterator = mem.memIterator();
		while (memIterator.hasNext()) {
			collectAllRules(memIterator.next());
		}
	}

	static
	public boolean sync(Membrane root) {
		if(releasing){
			return false;
		}
		root_ = root;
		if(rules_.isEmpty()){
			collectAllRules(root);
		}
		boolean breakpoint = false;
		if(breakpointRuleset_ != null){
			for(InterpretedRuleset r : breakpointRuleset_){
				breakpoint = isBreakPoint(r);
				if(breakpoint){
					System.out.println("break");
					break;
				}
			}
		}
		printRoot();
		try {
			setState_.invoke(unyoObj_,
					root,
					rules_,
					removedMembrane_,
					addedMembrane_,
					modifiedMembrane_,
					removedAtom_,
					addedAtom_,
					modifiedAtom_,
					(Object)currentRule_,
					currentRule_.fullText);
			
			removedMembrane_.clear();
			addedMembrane_.clear();
			modifiedMembrane_.clear();
			removedAtom_.clear();
			addedAtom_.clear();
			modifiedAtom_.clear();

			while(((Boolean)sync_.invoke(unyoObj_)&&!releasing)){
				setWait(true);
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			setWait(false);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		if(releasing){
			return false;
		}
		taskEnd = false;
		return true;

	}
	
	/**
	 * parentMem以下の全てのアトムと膜を得る
	 * @param parentMem
	 * @param allNodes
	 */
	static
	private void getAllNodes(
			Membrane parentMem,
			HashSet<Object> allNodes){
		Iterator<Atom> it_a = parentMem.atomIterator();
		while (it_a.hasNext()) {
			Atom a = it_a.next();
			allNodes.add(a);
		}
		Iterator<Membrane> it_m = parentMem.memIterator();
		while (it_m.hasNext()) {
			Membrane m = it_m.next();
			allNodes.add(m);
			getAllNodes(m, allNodes);
		}
	}
	
	/**
	 * parentMem以下の全てのアトムと膜をclear
	 * @param parentMem
	 * @param allNodes
	 */
	static
	private void clearAllNodes(Membrane parentMem, ArrayList<Object> clearNodeList){
		Iterator<Atom> it_a = parentMem.atomIterator();
		while (it_a.hasNext()) {
			Atom a = it_a.next();
			clearNodeList.add(a);
			a.remove();
		}
		Iterator<Membrane> it_m = parentMem.memIterator();
		while (it_m.hasNext()) {
			Membrane m = it_m.next();
			clearNodeList.add(m);
			clearAllNodes(m, clearNodeList);
		}
	}
	
	static
	public boolean isBreakPoint(InterpretedRuleset r){
//		Env.fUNYO = false;
//		LMNtalRuntime srcRuntime = Env.theRuntime;
//		try {
//			LMNtalRuntime runtime = new LMNtalRuntime();
//			Membrane root = runtime.getGlobalRoot();
//			HashMap<Membrane, Membrane> memMap = new HashMap<Membrane, Membrane>();
//			HashMap<Atom, Atom> atomMap = new HashMap<Atom, Atom>();
//			root.copyCellsFrom3(root_, atomMap, memMap);
//			 r.react(root);
//			 Env.fUNYO_b = true;
//			 ((Task)root.getTask()).execAsMasterTask();
//			 System.out.println(root);
//			 Env.theRuntime = srcRuntime;
//			 Env.fUNYO = true;
//			 return Env.fUNYO_b;
//		}catch (Exception e) {
//		}
//		Env.theRuntime = srcRuntime;
//		Env.fUNYO = true;
		if(getMachedNodes(r).isEmpty()) return false;
		return false;
	}
	
	static
	private InterpretedRuleset compileRuleset(String text){
		Reader src = new StringReader(text);
		compile.parser.LMNParser lp = new compile.parser.LMNParser(src);
		compile.structure.Membrane tempMem = null;
		InterpretedRuleset r = null;
		try {
			tempMem = lp.parse();
		} catch(Exception e){}
		if(error_){
			error_ = false;
			return null;
		}
		try {
			r = (InterpretedRuleset)compile.RulesetCompiler.compileMembrane(tempMem);
		} catch (Exception e) {
			return null;
		}
		if(error_){
			error_ = false;
			return null;
		}
		return r;
	}
	
	static
	public void setBreakPoint(String text){
		InterpretedRuleset r = compileRuleset(text);
		if(r == null) return;
		if(breakpointRuleset_ == null){
			breakpointRuleset_ = new HashSet<InterpretedRuleset>();
		}
		breakpointRuleset_.add(r);
	}
	
	static
	private HashSet<String> getMachedNodes(InterpretedRuleset r){
		Env.fUNYO = false;
		HashSet<String> matchNodes = new HashSet<String>();
		LMNtalRuntime srcRuntime = Env.theRuntime;
		try {
			LMNtalRuntime runtime = new LMNtalRuntime();
			Membrane root = runtime.getGlobalRoot();
			HashMap<Membrane, Membrane> memMap = new HashMap<Membrane, Membrane>();
			HashMap<Atom, Atom> atomMap = new HashMap<Atom, Atom>();
			root.copyCellsFrom3(root_, atomMap, memMap);
			r.react(root);
			((Task)root.getTask()).execAsMasterTask();
			HashSet<Object> allNodes = new HashSet<Object>();
			getAllNodes(root, allNodes);
			
			for(Atom srcAtom : atomMap.keySet()){
				Atom copyAtom = atomMap.get(srcAtom);
//				System.out.println("srcAtom : " + srcAtom.getid() + "/ copyAtom : " + copyAtom.getid());
				if(allNodes.contains(copyAtom)) continue;
				if(!srcAtom.isVisible()|| srcAtom.getFunctor().getName().startsWith("/*inline*/")){
					continue;
				}
				matchNodes.add("atom_" + srcAtom.getid());
			}
			for(Membrane srcMem : memMap.keySet()){
				Membrane copyMem = memMap.get(srcMem);
//				System.out.println("srcMem : " + srcMem.getMemID() + "/ copyMem : " + copyMem.getMemID());
				if(allNodes.contains(copyMem)) continue;
				matchNodes.add("membrane_" + srcMem.getMemID());
			}
//			System.out.println(matchNodes);
//			System.out.println(root);
//			ArrayList<Object> clearNodeList = new ArrayList<Object>();
//			clearAllNodes(root, clearNodeList);
//			java.util.ListIterator<Object> it = clearNodeList.listIterator(clearNodeList.size());
//			while(it.hasPrevious()){
//				Object o = it.previous();
//				if(o instanceof Atom){
//					Atom a = (Atom)o;
//					if(a.getMem() != null){
//						a.remove();
//					}
//				}else{
//					Membrane m = (Membrane)o;
//					if((m.getParent()) !=null){
//						(m.getParent()).removeMem(m);
//					}
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Env.theRuntime = srcRuntime;
		Env.fUNYO = true;
		return matchNodes;
	}
	
	static
	public HashSet<String> searchGraphs(String text){
		InterpretedRuleset r = compileRuleset(text);
		if(r == null) return null;
		return getMachedNodes(r);
	}


	static
	public void addRemovedMembrane(Membrane removeMem, String parentMemID){
//		System.out.println("removeMem/" + removeMemID);
		removeChildrenOfMembrane(removeMem);
		removedMembrane_.put(removeMem.getMemID(), parentMemID);
	}
	static
	private void removeChildrenOfMembrane(Membrane mem){
		Iterator<Atom> it_a = mem.atomIterator();
		/* 膜の中身も追加 */
		while (it_a.hasNext()) {
			Atom a = it_a.next();
			unyo.Mediator.addRemovedAtom(a, mem.getMemID());
		}
		Iterator<Membrane> it_m = mem.memIterator();
		while (it_m.hasNext()) {
			Membrane m = it_m.next();
			unyo.Mediator.addRemovedMembrane(m, mem.getMemID());
		}
	}

	static
	public void addAddedMembrane(Membrane mem){
//		System.out.println("addMem/" + mem.getMemID());
		addedMembrane_.add(mem);
		addChildrenOfMembrane(mem);
	}

	static
	private void addChildrenOfMembrane(Membrane mem){
		Iterator<Atom> it_a = mem.atomIterator();
		/* 膜の中身も追加 */
		while (it_a.hasNext()) {
			Atom a = it_a.next();
			unyo.Mediator.addAddedAtom(a);
		}
		Iterator<Membrane> it_m = mem.memIterator();
		while (it_m.hasNext()) {
			Membrane m = it_m.next();
			unyo.Mediator.addAddedMembrane(m);
//			addChildrenOfMembrane(m);
		}
	}

	static
	public void addModifiedMembrane(Membrane mem){
//		System.out.println("addModMem/" + mem.getMemID());
		modifiedMembrane_.add(mem);
	}

	static
	public void addRemovedAtom(Atom removeAtom, String parentMemID){
		if(!removeAtom.isVisible() || removeAtom.getFunctor().getName().startsWith("/*inline*/")){
			return;
		}
//		System.out.println("addRemAtom/" + removeAtom.getid() + ":" + parentMemID);
		String id = ((Integer)removeAtom.getid()).toString();
		removedAtom_.put(id, parentMemID);
//		addedAtom_.remove(removeAtom);
	}

	static
	public void addAddedAtom(Atom atom){
		if(!atom.isVisible() || atom.getFunctor().getName().startsWith("/*inline*/")){
			return;
		}
//		System.out.println("addAddAtom/" + atom.getid()+"/"+atom.getName()+ ":" + (atom.getMem()).getMemID());
		addedAtom_.add(atom);
	}

	static
	public void addModifiedAtom(Atom atom){
		if(!atom.isVisible() || atom.getFunctor().getName().startsWith("/*inline*/")){
			return;
		}
		if(addedAtom_.contains(atom)){
			return;
		}
//		System.out.println("addModeAtom/" + atom.getid() + atom.getName());
		modifiedAtom_.add(atom);
	}

	static
	private void setWait(boolean wait){
		unyoWait_ = wait;
	}
	static
	public boolean isWait(){
		return unyoWait_;
	}

	static
	public void setCurrentRule(Rule rule){
		currentRule_ = rule;
	}
}
