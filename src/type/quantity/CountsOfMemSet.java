package type.quantity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import runtime.Env;

import compile.structure.Membrane;

/**
 * 膜名ごとに量的解析結果を保持する
 * @author kudo
 *
 */
public class CountsOfMemSet {
	/** ソース上の膜 -> 量解析結果 */
	Map<Membrane,CountsOfMem> memToCounts;
//	/** 膜名 -> 変化プロセス */
//	Map<Membrane,CountsOfMem> memToInhCounts;
	public CountsOfMemSet(){
//		memToGenCounts = new HashMap<Membrane,CountsOfMem>();
//		memToInhCounts = new HashMap<Membrane,CountsOfMem>();
		memToCounts = new HashMap<Membrane, CountsOfMem>();
	}
	/**
	 * 膜についての解析結果をマージしていく
	 * TODO addCountsOfMemはこちらに統合できる(たぶん)
	 * @param counts
	 */
	public void add(CountsOfMem counts){
		switch(counts.multiple){
		case 0:// 生成
		case 1:// 派生
			addCountsOfMem(counts);
			break;
		default://マージ
			addMerCounts(counts);
		}
	}
	/**
	 * 膜の解析結果を加えていく。
	 * この段階では、膜は同名でもソース上の別の膜なら区別される。
	 * ただし、ルールの本膜は、ルールの所属する膜と同じとされる。
	 * したがい、解析結果は同一膜については加算される。
	 * @param counts
	 */
	public void addCountsOfMem(CountsOfMem counts){
//		String memname = TypeEnv.getMemName(counts.mem);
		if(!memToCounts.containsKey(counts.mem))
			memToCounts.put(counts.mem,counts);
		else{
			CountsOfMem oldcounts = memToCounts.get(counts.mem);
			oldcounts.addAllCounts(counts);
		}
	}
	/**
	 * 
	 * @param counts
	 */
	public void addMerCounts(CountsOfMem counts){
		
	}
	
	/**
	 * 膜名ごとにマージする
	 * TODO 実装
	 */
	public void mergeForName(){
		
	}
	
	/**
	 * 各解析結果を整理する
	 * (-RV1+RV1 -> 0 など)
	 * TODO 実装
	 *
	 */
	public void reflesh(){
		
	}
	
	public void printAll(){
		Env.p("--QUANTITY ANALYSIS");
		Env.p("---mem on source counts:");
		Iterator<CountsOfMem> itmgs = memToCounts.values().iterator();
		while(itmgs.hasNext()){
			itmgs.next().print();
		}
//		Env.p("---inh counts:");
//		Iterator<CountsOfMem> itmis = memToInhCounts.values().iterator();
//		while(itmis.hasNext()){
//			itmis.next().print();
//		}
		Env.p("");
	}
	
}
