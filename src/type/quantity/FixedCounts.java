package type.quantity;

import java.util.HashMap;
import java.util.HashSet;
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
	public FixedCounts(StaticCounts com){
		this.mem = com.mem;
		functorToCount = new HashMap<Functor, FixedCount>();
		memnameToCount = new HashMap<String, FixedCount>();
		for(Functor f : com.functorToCount.keySet()){
			Count c = com.functorToCount.get(f);
			functorToCount.put(f,c.evaluate());
		}
		for(String name : com.memnameToCount.keySet()){
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
		for(Functor f : mergedFunctors){
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
		for(String name : mergedNames){
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
	
	
	public void apply(FixedDynamicCounts fdc){
		addAllCounts(fdc.removeCounts);
		addAllCounts(fdc.generateCounts);
	}
	public void addAllCounts(FixedCounts fc){
		addCounts(functorToCount, fc.functorToCount);
		addCounts(memnameToCount, fc.memnameToCount);
	}

	public <Key> void addCounts(Map<Key, FixedCount> countmap1, Map<Key, FixedCount> countmap2){
		for(Key key : countmap2.keySet()){
			if(countmap1.containsKey(key)){
				FixedCount oldfc = countmap1.get(key);
				oldfc.add(countmap2.get(key));
			}
			else{
				countmap1.put(key, countmap2.get(key));
			}
		}
	}
	
	public void print(){
		Env.p("----atoms of " + TypeEnv.getMemName(mem) /* + "(" + multiple + ") :"*/);
		for(Functor f : functorToCount.keySet()){
			Env.p(f + ":" + functorToCount.get(f));
		}
		Env.p("----mems of " + TypeEnv.getMemName(mem) /*+ "(" + multiple + ") :"*/);
		for(String m : memnameToCount.keySet()){
			Env.p(m + ":" + memnameToCount.get(m));
		}
	}
}
