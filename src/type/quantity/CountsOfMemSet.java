package type.quantity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import runtime.Env;
import type.TypeEnv;

import compile.structure.Membrane;

/**
 * 膜名ごとに量的解析結果を保持する
 * @author kudo
 *
 */
public class CountsOfMemSet {
	/** ソース上の膜 -> 量解析結果(生成膜) */
	Map<Membrane,StaticCountsOfMem> memToGenCounts;
	/** ソース上の膜 -> 量解析結果(継続膜) */
	Map<Membrane,Set<DynamicCountsOfMem>> memToInhCountss;
	/** 膜名 -> 量解析結果(継続膜) */
	Map<String,Set<DynamicCountsOfMem>> memnameToAllInhCountss;
	/** ソース上の膜 -> 量解析結果(生成膜/評価済み) */
	Map<Membrane,FixedCounts> memToFixedCounts;
//	/** ソース上の膜 -> 量解析結果(継続膜/評価済み) */
//	Map<Membrane,FixedCounts> memToInhFixedCounts;
	Map<String, StaticCountsOfMem> memnameToMergedCounts;
	public CountsOfMemSet(){
		memToGenCounts = new HashMap<Membrane,StaticCountsOfMem>();
		memToInhCountss = new HashMap<Membrane,Set<DynamicCountsOfMem>>();
		memnameToAllInhCountss = new HashMap<String,Set<DynamicCountsOfMem>>();
		memnameToMergedCounts = new HashMap<String, StaticCountsOfMem>();
	}
	/**
	 * 膜の解析結果を加えていく。
	 * この段階では、膜は同名でもソース上の別の膜なら区別される。
	 * ただし、ルールの本膜は、ルールの所属する膜と同じとされる。
	 * したがい、解析結果は同一膜については加算される。
	 * TODO addCountsOfMemはこちらに統合できる(たぶん)
	 * @param counts
	 */
	public void add(StaticCountsOfMem counts){
			if(!memToGenCounts.containsKey(counts.mem))
				memToGenCounts.put(counts.mem,counts);
			else{
				StaticCountsOfMem oldcounts = memToGenCounts.get(counts.mem);
				oldcounts.addAllCounts(counts);
			}
	}
	public void add(DynamicCountsOfMem counts){
		if(!memToInhCountss.containsKey(counts.mem)){
			Set<DynamicCountsOfMem> doms = new HashSet<DynamicCountsOfMem>();
			doms.add(counts);
			memToInhCountss.put(counts.mem,doms);
		}
		else{
			Set<DynamicCountsOfMem> oldcountss = memToInhCountss.get(counts.mem);
			oldcountss.add(counts);
		}
		String memname = counts.mem.name;
		if(!memnameToAllInhCountss.containsKey(memname)){
			Set<DynamicCountsOfMem> doms = new HashSet<DynamicCountsOfMem>();
			doms.add(counts);
			memnameToAllInhCountss.put(memname, doms);
		}
		else{
			Set<DynamicCountsOfMem> oldcountss = memnameToAllInhCountss.get(memname);
			oldcountss.add(counts);
		}
	}
	
	Map<String, Boolean> memnameToCRIFlg = new HashMap<String, Boolean>();
	/**
	 * 指定した膜名については、全ての膜のルールは全ての膜に影響する
	 * @param memname
	 */
	public void collapseRulesIndependency(String memname){
		memnameToCRIFlg.put(memname, true);
	}
	Map<Membrane, Boolean> memToCRIFlg = new HashMap<Membrane, Boolean>();
	/**
	 * 指定した膜名については、全ての膜のルールは全ての膜に影響する
	 * @param memname
	 */
	public void collapseRuleIndependency(Membrane mem){
		memToCRIFlg.put(mem, true);
	}
	Map<String, Boolean> memnameToCPUBFlg = new HashMap<String, Boolean>();
	/**
	 * 指定した膜名については、プロセスの下限を無くす
	 * @param memname
	 */
	public void collapseProcessUnderBounds(String memname){
		memnameToCPUBFlg.put(memname, true);
		collapseProcessIndependency(memname);
	}
	Map<String, Boolean> memnameToCPIFlg = new HashMap<String, Boolean>();
	/**
	 * 指定した膜名については、具体膜を区別しない
	 * @param memname
	 */
	public void collapseProcessIndependency(String memname){
		memnameToCPIFlg.put(memname, true);
	}

	/** 個々の具体膜ごとに効果を適用する
	 * ただし、プロセスの独立性が崩れている膜名についてはmergeする
	 *  */
	public void applyIndividual(){
		for(Membrane mem : memToGenCounts.keySet()){
			String memname = TypeEnv.getMemName(mem);
			if(memnameToCPIFlg.get(memname)){
				if(!memnameToMergedCounts.containsKey(memname))
					memnameToMergedCounts.put(memname,memToGenCounts.get(mem));
				else{
					StaticCountsOfMem oldsom = memnameToMergedCounts.get(memname);
					oldsom.addAllCounts(memToGenCounts.get(mem));
				}
			}
			/** その具体膜への効果を適用 */
			for(DynamicCountsOfMem dom : memToInhCountss.get(mem)){
				memToGenCounts.get(mem).apply(dom);
			}
			/** 他の膜の効果を適用 */
			if(memToCRIFlg.get(mem) || memnameToCRIFlg.get(memname)){
				for(DynamicCountsOfMem dom : memnameToAllInhCountss.get(memname)){
					memToGenCounts.get(mem).apply(dom);
				}
			}
		}
	}
	
	/**
	 * 膜名ごとにマージして効果を適用する
	 *
	 */
	public void applyAllInOne(){
		for(Membrane mem : memToGenCounts.keySet()){
			String memname = TypeEnv.getMemName(mem);
			if(!memnameToMergedCounts.containsKey(memname))
				memnameToMergedCounts.put(memname,memToGenCounts.get(mem));
			else{
				StaticCountsOfMem oldsom = memnameToMergedCounts.get(memname);
				oldsom.addAllCounts(memToGenCounts.get(mem));
			}
		}
	}
	
	/**
	 * ルール変数が束縛されたものとし、生成膜の各個数について実際の値を求める
	 */
	public boolean solve(){
		memToFixedCounts = new HashMap<Membrane, FixedCounts>();
		for(Membrane m : memToGenCounts.keySet())
			memToFixedCounts.put(m,memToGenCounts.get(m).solve());
		return true;
	}

	private boolean fixed = false;
	/**
	 * 効果におけるルール変数を無限と束縛し、解く
	 *
	 */
	public void solveRVAsInfinity(){
		for(Set<DynamicCountsOfMem> doms : memToInhCountss.values())
			for(DynamicCountsOfMem dom : doms)
				if(!dom.applyCount.isBound())dom.applyCount.bind(Count.INFINITY.or0());
		fixed = solve();
	}
	
	/**
	 * アトム数、子膜数の下限を0として制約問題としてルール変数を解く
	 * @return
	 */
	public void solveByCounts(){
		for(Membrane mem : memToGenCounts.keySet()){
			// プロセスの独立性が保たれていなければ無視
			if(memnameToCPIFlg.get(TypeEnv.getMemName(mem)))
				continue;
			memToGenCounts.get(mem).solveByCounts();
		}
		// 解ききったかどうかわからないので残りは[0,#inf]とする
		solveRVAsInfinity();
	}
	
	public void printAll(){
		if(fixed){
			Env.p("--QUANTITY ANALYSIS");
			Env.p("---mem on source counts:");
			for(FixedCounts fc : memToFixedCounts.values())
				fc.print();
			Env.p("");
		}
		else{
			Env.p("--QUANTITY ANALYSIS");
			Env.p("---mem on source counts:");
			for(StaticCountsOfMem com : memToGenCounts.values())
				com.print();
			Env.p("---mem effect on source counts:");
			for(Set<DynamicCountsOfMem> doms : memToInhCountss.values())
				for(DynamicCountsOfMem dom : doms)
					dom.print();
			Env.p("");
		}
	}

}
