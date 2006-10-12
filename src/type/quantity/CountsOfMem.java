package type.quantity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import runtime.Env;
import runtime.Functor;
import type.TypeEnv;

import compile.structure.Atom;
import compile.structure.Membrane;

/**
 * それぞれの膜について作られる
 * @author kudo
 *
 */
public class CountsOfMem{
	
	Membrane mem;
	
	/**
	 * この膜の所属プロセスが何倍されるかを表す。
	 * 0 : 生成時
	 * 1 : 移動時(あるいはルールの本膜等)
	 * >1 : 複製、マージ
	 */
	int multiple;
	
	/** ファンクタ -> 量 */
	Map<Functor,Count> functorToCount;
	/** 膜名 -> 量 */
	Map<String,Count> memnameToCount;

	public CountsOfMem(Membrane mem, int multiple){
		this.mem = mem;
		this.multiple = multiple;

		functorToCount = new HashMap<Functor, Count>();
		memnameToCount = new HashMap<String, Count>();
	}
	
	/**
	 * アトムに関する量を加算
	 * @param atom
	 * @param count
	 */
	public void addAtomCount(Atom atom, Count count){
		addAtomCount(atom.functor, count);
	}
	public void addAtomCount(Functor functor, Count count){
		if(!functorToCount.containsKey(functor))
			functorToCount.put(functor,count);
		else{
			Count atomcount = functorToCount.get(functor);
			functorToCount.put(functor, atomcount.add(count));
		}
	}
	/**
	 * 膜に関する量を加算
	 * @param m
	 * @param count
	 */
	public void addMemCount(Membrane m, Count count){
		addMemCount(TypeEnv.getMemName(m), count);
	}
	public void addMemCount(String memname, Count count){
		if(!memnameToCount.containsKey(memname))
			memnameToCount.put(memname,count);
		else{
			Count memcount = memnameToCount.get(memname);
			memnameToCount.put(memname, memcount.add(count));
		}
	}
	/**
	 * 別の量セットから全て加算
	 * @param com2
	 */
	public void addAllCounts(CountsOfMem com2){
		Iterator<Functor> itf = com2.functorToCount.keySet().iterator();
		while(itf.hasNext()){
			Functor f = itf.next();
			addAtomCount(f,com2.functorToCount.get(f));
		}
		Iterator<String> itn = com2.memnameToCount.keySet().iterator();
		while(itf.hasNext()){
			String name = itn.next();
			addMemCount(name,com2.memnameToCount.get(name));
		}
	}
	/**
	 * 別の量セットをor結合
	 */
	public void merge(CountsOfMem com2){
		Set<Functor> mergedFunctors = new HashSet<Functor>();
		mergedFunctors.addAll(functorToCount.keySet());
		mergedFunctors.addAll(com2.functorToCount.keySet());
		Iterator<Functor> itf = mergedFunctors.iterator();
		while(itf.hasNext()){
			Functor f = itf.next();
			Count c1 = functorToCount.get(f);
			Count c2 = com2.functorToCount.get(f);
			if(c1==null && c2!=null)
				functorToCount.put(f,new OrCount(c2));
			else if(c1!=null && c2==null)
				functorToCount.put(f,new OrCount(c1));
			else if(c1!=null && c2!=null)
				functorToCount.put(f, c1.merge(c2));
		}
		Set<String> mergedNames = new HashSet<String>();
		mergedNames.addAll(memnameToCount.keySet());
		mergedNames.addAll(com2.memnameToCount.keySet());
		Iterator<String> itn = mergedNames.iterator();
		while(itn.hasNext()){
			String name = itn.next();
			Count c1 = memnameToCount.get(name);
			Count c2 = com2.memnameToCount.get(name);
			if(c1==null && c2!=null)
				memnameToCount.put(name,new OrCount(c2));
			else if(c1!=null && c2==null)
				memnameToCount.put(name, new OrCount(c1));
			else if(c1!=null && c2!=null)
				memnameToCount.put(name, c1.merge(c2));
		}
	}
	
	public void print(){
		Env.p("---atoms of " + TypeEnv.getMemName(mem) + "(" + multiple + ") :");
		Iterator<Functor> itf = functorToCount.keySet().iterator();
		while(itf.hasNext()){
			Functor f = itf.next();
			Env.p(f + ":" + functorToCount.get(f));
		}
		Env.p("---mems of " + TypeEnv.getMemName(mem) + "(" + multiple + ") :");
		Iterator<String> itm = memnameToCount.keySet().iterator();
		while(itm.hasNext()){
			String m = itm.next();
			Env.p(m + ":" + memnameToCount.get(m));
		}
	}
	
}
