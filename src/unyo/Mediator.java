package unyo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;

import debug.Debug;

import runtime.Atom;
import runtime.Dumper;
import runtime.Env;
import runtime.Functor;
import runtime.InterpretedRuleset;
import runtime.Membrane;
import runtime.Rule;
import runtime.Ruleset;
import runtime.SymbolFunctor;

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
	public boolean unyoWait_ = false;
	
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
		
		removedMembrane_ = new LinkedHashMap<String, String>();
		addedMembrane_ = new LinkedList<Membrane>();
		modifiedMembrane_ = new LinkedList<Membrane>();
		removedAtom_ = new LinkedHashMap<String, String>();
		addedAtom_ = new LinkedList<Atom>();
		modifiedAtom_ = new LinkedList<Atom>();
		currentRule_ = new Rule();
		rules_ = new HashMap<Rule, String>();
		
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
	public HashSet addLink(Object source,
			Object sourcem,
			String sourceName,
			int sl,
			Object target,
			Object targetm,
			String targetName,
			int tl){
		HashSet<Object> newLinkSet = new HashSet<Object>();

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
		printRoot();
		if(rules_.isEmpty()){
			collectAllRules(root);
		}

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
			while((Boolean)sync_.invoke(unyoObj_)&&!releasing){
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
		return true;

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
		if(removeAtom.getFunctor().equals(Functor.STAR) ||
				removeAtom.getFunctor().equals(Functor.INSIDE_PROXY) ||
				removeAtom.getFunctor().equals(Functor.OUTSIDE_PROXY)||
				removeAtom.getFunctor().getName().startsWith("/*inline*/")){
			return;
		}
//		System.out.println("addRemAtom/" + removeAtom.getid() + removeAtom.getName());
		String id = ((Integer)removeAtom.getid()).toString();
		removedAtom_.put(id, parentMemID);
//		addedAtom_.remove(removeAtom);
	}
	
	static
	public void addAddedAtom(Atom atom){
		if(atom.getFunctor().equals(Functor.STAR) ||
				atom.getFunctor().equals(Functor.INSIDE_PROXY) ||
				atom.getFunctor().equals(Functor.OUTSIDE_PROXY) ||
				atom.getFunctor().getName().startsWith("/*inline*/")){
			return;
		}
//		System.out.println("addAddAtom/" + atom.getid()+atom.getName());
		addedAtom_.add(atom);
	}
	
	static
	public void addModifiedAtom(Atom atom){
		if(atom.getFunctor().equals(Functor.STAR) ||
				atom.getFunctor().equals(Functor.INSIDE_PROXY) ||
				atom.getFunctor().equals(Functor.OUTSIDE_PROXY) ||
				atom.getFunctor().getName().startsWith("/*inline*/")){
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
