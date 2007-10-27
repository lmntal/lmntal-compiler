package unyo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;

import runtime.Atom;
import runtime.Env;
import runtime.Membrane;

public class Mediator {

	static
	private HashSet<Membrane> deletedMembrane_;
	
	static
	private HashSet<Membrane> addedMembrane_;
	
	static
	private HashSet<Membrane> modifiedMembrane_;
	
	static
	private HashSet<Atom> deletedAtom_;
	
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
	private Method sync_;
	
	static
	public void init(){

		deletedMembrane_ = new HashSet<Membrane>();
		addedMembrane_ = new HashSet<Membrane>();
		modifiedMembrane_ = new HashSet<Membrane>();
		deletedAtom_ = new HashSet<Atom>();
		addedAtom_ = new HashSet<Atom>();
		modifiedAtom_ = new HashSet<Atom>();
		
		try {
			unyoClass_ = Class.forName("jp.ac.waseda.info.ueda.unyo.mediator.StepSync");
			unyoObj_ = unyoClass_.newInstance();
			Env.fUNYO = true;
			
			sync_ 
			= unyoClass_.getMethod("sync");
			
			setState_ 
			= unyoClass_.getMethod("setState",
					Object.class,
					HashSet.class,
					HashSet.class,
					HashSet.class,
					HashSet.class,
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
	public void sync(Membrane root) {
		try {
			setState_.invoke(unyoObj_,
					root,
					deletedMembrane_,
					addedMembrane_,
					modifiedMembrane_,
					deletedAtom_,
					addedAtom_,
					modifiedAtom_);
			
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
	public void addDeletedMembrane(Membrane mem){
		deletedMembrane_.add(mem);
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
	public void addDeletedAtom(Atom atom){
		deletedAtom_.add(atom);
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
