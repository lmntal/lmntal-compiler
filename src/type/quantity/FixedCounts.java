package type.quantity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import runtime.Env;
import runtime.Functor;
import type.TypeEnv;

import compile.structure.Membrane;

/**
 * マージする為のオブジェクト
 * @author kudo
 *
 */
public class FixedCounts {
	
	/** ファンクタ -> 量 */
	final Map<Functor,FixedCount> functorToCount;
	/** 膜名 -> 量 */
	final Map<String,FixedCount> memnameToCount;
	
	final Membrane mem;

	/**
	 * 受け取った解析結果を解く(この段階でルール適用回数変数が束縛されていること)
	 *
	 */
	public FixedCounts(StaticCountsOfMem com){
		this.mem = com.mem;
		functorToCount = new HashMap<Functor, FixedCount>();
		memnameToCount = new HashMap<String, FixedCount>();
		Iterator<Functor> itf = com.functorToCount.keySet().iterator();
		while(itf.hasNext()){
			Functor f = itf.next();
			Count c = com.functorToCount.get(f);
			functorToCount.put(f,c.evaluate());
		}
		Iterator<String> itn = com.memnameToCount.keySet().iterator();
		while(itn.hasNext()){
			String name = itn.next();
			Count c = com.memnameToCount.get(name);
			memnameToCount.put(name,c.evaluate());
		}
	}
	
	/**
	 * TODO 倍数により分ける
	 * @param com
	 */
	public void merge(FixedCounts fcs){
		Set<Functor> mergedFunctors = new HashSet<Functor>();
		mergedFunctors.addAll(functorToCount.keySet());
		mergedFunctors.addAll(fcs.functorToCount.keySet());
		Iterator<Functor> itf = mergedFunctors.iterator();
		while(itf.hasNext()){
			Functor f = itf.next();
			FixedCount fc1 = functorToCount.get(f);
			FixedCount fc2 = fcs.functorToCount.get(f);
			if(fc1==null && fc2!=null)
				functorToCount.put(f,fc2.or0());
			else if(fc1!=null && fc2==null)
				functorToCount.put(f,fc1.or0());
			else if(fc1!=null && fc2!=null)
				functorToCount.put(f, fc1.or(fc2));
		}
		Set<String> mergedNames = new HashSet<String>();
		mergedNames.addAll(memnameToCount.keySet());
		mergedNames.addAll(fcs.memnameToCount.keySet());
		Iterator<String> itn = mergedNames.iterator();
		while(itn.hasNext()){
			String name = itn.next();
			FixedCount fc1 = memnameToCount.get(name);
			FixedCount fc2 = fcs.memnameToCount.get(name);
			if(fc1==null && fc2!=null)
				memnameToCount.put(name, fc2.or0());
			else if(fc1!=null && fc2==null)
				memnameToCount.put(name, fc1.or0());
			else if(fc1!=null && fc2!=null)
				memnameToCount.put(name, fc1.or(fc2));
		}
	}
	public void print(){
		Env.p("----atoms of " + TypeEnv.getMemName(mem) /* + "(" + multiple + ") :"*/);
		Iterator<Functor> itf = functorToCount.keySet().iterator();
		while(itf.hasNext()){
			Functor f = itf.next();
			Env.p(f + ":" + functorToCount.get(f));
		}
		Env.p("----mems of " + TypeEnv.getMemName(mem) /*+ "(" + multiple + ") :"*/);
		Iterator<String> itm = memnameToCount.keySet().iterator();
		while(itm.hasNext()){
			String m = itm.next();
			Env.p(m + ":" + memnameToCount.get(m));
		}
	}
}
