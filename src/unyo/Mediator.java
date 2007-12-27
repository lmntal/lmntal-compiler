package unyo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;

import runtime.Atom;
import runtime.Env;
import runtime.Membrane;
import runtime.SymbolFunctor;

public class Mediator {

	// <削除された膜のID, 親膜のID>
	static
	private HashMap<String, String> removedMembrane_;
	
	static
	private HashSet<Membrane> addedMembrane_;
	
	static
	private HashSet<Membrane> modifiedMembrane_;
	
	// <削除されたアトムのID, 親膜のID>
	static
	private HashMap<String, String> removedAtom_;
	
	static
	private HashSet<Atom> addedAtom_;
	
	static
	private HashSet<Atom> modifiedAtom_;
	
	static
	private Object unyoObj_;

	static
	private Class unyoClass_;
	
	static
	private Method setState_;
	
	static
	private Method print_;
	
	static
	private Method errPrint_;
	
	static
	private Method sync_;
	
	static
	public void release(){
//		if(Env.theRuntime != null){
//			Env.theRuntime.terminate();
//		}
//		Env.fUNYO = false;
		Env.srcs.clear();
//		doNext_ = false;
//		removedMembrane_.clear();
//		addedMembrane_.clear();
//		modifiedMembrane_.clear();
//		removedAtom_.clear();
//		addedAtom_.clear();
//		modifiedAtom_.clear();
	}
	
	static
	public void init(){

		removedMembrane_ = new HashMap<String, String>();
		addedMembrane_ = new HashSet<Membrane>();
		modifiedMembrane_ = new HashSet<Membrane>();
		removedAtom_ = new HashMap<String, String>();
		addedAtom_ = new HashSet<Atom>();
		modifiedAtom_ = new HashSet<Atom>();
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
					HashMap.class,
					HashSet.class,
					HashSet.class,
					HashMap.class,
					HashSet.class,
					HashSet.class);
			
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
	public void sync(Membrane root) {
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
			
			while((Boolean)sync_.invoke(unyoObj_)){
				try {
					Thread.sleep(50);
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
}
