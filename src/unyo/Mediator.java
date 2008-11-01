package unyo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import runtime.Atom;
import runtime.Env;
import runtime.Functor;
import runtime.Membrane;
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
		try {
			unyoClass_ = Class.forName("jp.ac.waseda.info.ueda.unyo.mediator.Synchronizer");
			unyoObj_ = unyoClass_.newInstance();
			Env.fUNYO = true;
			
			sync_ 
			= unyoClass_.getMethod("sync");
			
			print_ 
			= unyoClass_.getMethod("print", String.class);
			
			errPrint_ 
			= unyoClass_.getMethod("errPrint", String.class);
			
			setState_ 
			= unyoClass_.getMethod("setState",
					Object.class,
					LinkedHashMap.class,
					LinkedList.class,
					LinkedList.class,
					LinkedHashMap.class,
					LinkedList.class,
					LinkedList.class);
			
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
	
	public static void println(String msg){
		print(msg  + System.getProperty("line.separator"));
	}
	
	public static void println(Object msg){
		println(msg.toString());
	}
	
	public static void print(String msg){
		try {
			print_.invoke(unyoObj_, msg);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static void print(Object msg){
		print(msg.toString());
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
	
	static 
	public boolean sync(Membrane root) {
		
		if(releasing){
			return false;
		}
		
		try {
			setState_.invoke(unyoObj_,
					root,
					removedMembrane_,
					addedMembrane_,
					modifiedMembrane_,
					removedAtom_,
					addedAtom_,
					modifiedAtom_);

			removedMembrane_.clear();
			addedMembrane_.clear();
			modifiedMembrane_.clear();
			removedAtom_.clear();
			addedAtom_.clear();
			modifiedAtom_.clear();
			
			while((Boolean)sync_.invoke(unyoObj_)&&!releasing){
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
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
	public void addRemovedMembrane(String removeMemID, String parentMemID){
		removedMembrane_.put(removeMemID, parentMemID);
	}
	
	static
	public void addAddedMembrane(Membrane mem){
		addedMembrane_.add(mem);
	}
	
	static
	public void addModifiedMembrane(Membrane mem){
		modifiedMembrane_.add(mem);
	}
	
	static
	public void addRemovedAtom(Atom removeAtom, String parentMemID){
		String id = ((Integer)removeAtom.getid()).toString();
		removedAtom_.put(id, parentMemID);
		addedAtom_.remove(removeAtom);
	}
	
	static
	public void addAddedAtom(Atom atom){
		addedAtom_.add(atom);
	}
	
	static
	public void addModifiedAtom(Atom atom){
		modifiedAtom_.add(atom);
	}
	
	static
	public boolean getWait(){
		return unyoWait_;
	}
}
