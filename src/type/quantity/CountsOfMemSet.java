package type.quantity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import runtime.Env;
import type.TypeEnv;

/**
 * 膜名ごとに量的解析結果を保持する
 * @author kudo
 *
 */
public class CountsOfMemSet {
	/** 膜名 -> 生成プロセス */
	Map<String,CountsOfMem> memnameToGenCounts;
	/** 膜名 -> 変化プロセス */
	Map<String,CountsOfMem> memnameToInhCounts;
	public CountsOfMemSet(){
		memnameToGenCounts = new HashMap<String,CountsOfMem>();
		memnameToInhCounts = new HashMap<String,CountsOfMem>();
	}
	/**
	 * 膜についての解析結果をマージしていく
	 * @param counts
	 */
	public void add(CountsOfMem counts){
		// 生成
		if(counts.multiple == 0)
			addGenCounts(counts);
		// 移動
		else if(counts.multiple == 1)
			addInhCounts(counts);
		// マージ
		else
			addMerCounts(counts);
	}
	/**
	 * 
	 * @param counts
	 */
	public void addGenCounts(CountsOfMem counts){
		String memname = TypeEnv.getMemName(counts.mem);
		if(!memnameToGenCounts.containsKey(memname))
			memnameToGenCounts.put(memname,counts);
		else{
			CountsOfMem oldcounts = memnameToGenCounts.get(memname);
			oldcounts.merge(counts);
		}
	}
	/**
	 * 
	 * @param counts
	 */
	public void addInhCounts(CountsOfMem counts){
		String memname = TypeEnv.getMemName(counts.mem);
		if(!memnameToInhCounts.containsKey(memname))
			memnameToInhCounts.put(memname,counts);
		else{
			CountsOfMem oldcounts = memnameToInhCounts.get(memname);
			oldcounts.addAllCounts(counts);
		}
	}
	/**
	 * 
	 * @param counts
	 */
	public void addMerCounts(CountsOfMem counts){
		
	}
	
	public void printAll(){
		Env.p("--gen counts:");
		Iterator<CountsOfMem> itmgs = memnameToGenCounts.values().iterator();
		while(itmgs.hasNext()){
			itmgs.next().print();
		}
		Env.p("--inh counts:");
		Iterator<CountsOfMem> itmis = memnameToInhCounts.values().iterator();
		while(itmis.hasNext()){
			itmis.next().print();
		}
	}
	
}
