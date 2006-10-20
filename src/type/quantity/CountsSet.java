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
public class CountsSet {
	/** ソース上の膜 -> 量解析結果(生成膜) */
	Map<Membrane,StaticCounts> memToGenCounts;
	/** ソース上の膜 -> 量解析結果(継続膜) */
	Map<Membrane,Set<DynamicCounts>> memToInhCountss;
	/** 膜名 -> 量解析結果(継続膜) */
	Map<String,Set<DynamicCounts>> memnameToAllInhCountss;
	/** ソース上の膜 -> 量解析結果(生成膜/評価済み) */
	Map<Membrane,FixedCounts> memToFixedCounts;
//	/** ソース上の膜 -> 量解析結果(継続膜/評価済み) */
//	Map<Membrane,FixedCounts> memToInhFixedCounts;
	Map<String, FixedCounts> memnameToMergedFixedCounts;
	/** 膜名 -> 量解析結果(継続膜) */
	Map<String,Set<FixedDynamicCounts>> memnameToFixedCountss;
	public CountsSet(){
		memToGenCounts = new HashMap<Membrane,StaticCounts>();
		memToInhCountss = new HashMap<Membrane,Set<DynamicCounts>>();
		memnameToAllInhCountss = new HashMap<String,Set<DynamicCounts>>();
		memnameToMergedFixedCounts = new HashMap<String, FixedCounts>();
		memnameToFixedCountss = new HashMap<String, Set<FixedDynamicCounts>>();
	}
	/**
	 * 膜の解析結果を加えていく。
	 * この段階では、膜は同名でもソース上の別の膜なら区別される。
	 * ただし、ルールの本膜は、ルールの所属する膜と同じとされる。
	 * したがい、解析結果は同一膜については加算される。
	 * TODO addCountsOfMemはこちらに統合できる(たぶん)
	 * @param counts
	 */
	public void add(StaticCounts counts){
			if(!memToGenCounts.containsKey(counts.mem))
				memToGenCounts.put(counts.mem,counts);
			else{
				StaticCounts oldcounts = memToGenCounts.get(counts.mem);
				oldcounts.addAllCounts(counts);
			}
	}
	public void add(DynamicCounts counts){
		if(!memToInhCountss.containsKey(counts.mem)){
			Set<DynamicCounts> doms = new HashSet<DynamicCounts>();
			doms.add(counts);
			memToInhCountss.put(counts.mem,doms);
		}
		else{
			Set<DynamicCounts> oldcountss = memToInhCountss.get(counts.mem);
			oldcountss.add(counts);
		}
		String memname = counts.mem.name;
		if(!memnameToAllInhCountss.containsKey(memname)){
			Set<DynamicCounts> doms = new HashSet<DynamicCounts>();
			doms.add(counts);
			memnameToAllInhCountss.put(memname, doms);
		}
		else{
			Set<DynamicCounts> oldcountss = memnameToAllInhCountss.get(memname);
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

	Map<String, Boolean> memnameToAlreadyApplyed = new HashMap<String, Boolean>();
	
	/** 個々の具体膜ごとに効果を適用する
	 * ただし、プロセスの独立性が崩れている膜については何もしない
	 *  */
	public void applyIndividual(){
		for(Membrane mem : memToGenCounts.keySet()){
			String memname = TypeEnv.getMemName(mem);
			/** プロセスの独立性が崩れている場合、何もしない */
			if(memnameToCPIFlg.get(memname)){
//				if(!memnameToMergedCounts.containsKey(memname))
//					memnameToMergedCounts.put(memname,memToGenCounts.get(mem));
//				else{
//					StaticCounts oldsom = memnameToMergedCounts.get(memname);
//					oldsom.merge(memToGenCounts.get(mem));
//				}
			}
			/** ルールの独立性が崩れている場合, 他の膜の効果を適用 */
			else if(memToCRIFlg.get(mem) || memnameToCRIFlg.get(memname)){
				for(DynamicCounts dom : memnameToAllInhCountss.get(memname)){
					// DynamicCountsをコピーして適用する。
					memToGenCounts.get(mem).apply(dom.clone());
				}
				// 膜名について、適用済みとする
				memnameToAlreadyApplyed.put(memname, true);
			}
			else{
				/** その具体膜への効果を適用 */
				for(DynamicCounts dom : memToInhCountss.get(mem)){
					memToGenCounts.get(mem).apply(dom);
				}
				// 膜名について、適用済みとする
				memnameToAlreadyApplyed.put(memname, true);
			}
		}
	}
	
	public void solveIndividuals(){
		for(Membrane mem : memToGenCounts.keySet()){
			memToFixedCounts.put(mem, memToGenCounts.get(mem).solve());
		}
	}
	public void solveDynamics(){
		for(String memname : memnameToAllInhCountss.keySet()){
			Set<FixedDynamicCounts> fdoms = new HashSet<FixedDynamicCounts>();
			for(DynamicCounts dom : memnameToAllInhCountss.get(memname)){
				fdoms.add(dom.solve());
			}
			memnameToFixedCountss.put(memname, fdoms);
		}
	}
	
	// マージされた膜に対し、適用済みのフラグが立っていない場合にのみ全ルール適用
	public void applyCollapseds(){
		for(String memname : memnameToMergedFixedCounts.keySet()){
			Boolean already = memnameToAlreadyApplyed.get(memname);
			if(already != null && already)continue;
			for(FixedDynamicCounts dom : memnameToFixedCountss.get(memname)){
				memnameToMergedFixedCounts.get(memname).apply(dom);
			}
		}
	}
	
	public void mergeFixeds(){
		for(Membrane mem : memToFixedCounts.keySet()){
			String memname = TypeEnv.getMemName(mem);
			if(!memnameToMergedFixedCounts.containsKey(memname))
				memnameToMergedFixedCounts.put(memname, memToFixedCounts.get(mem));
			else{
				FixedCounts oldfc = memnameToMergedFixedCounts.get(memname);
				oldfc.merge(memToFixedCounts.get(mem));
			}
		}
	}
	
//	/**
//	 * 膜名ごとにマージして効果を適用する
//	 *
//	 */
//	public void applyAllInOne(){
//		for(Membrane mem : memToGenCounts.keySet()){
//			String memname = TypeEnv.getMemName(mem);
//			if(!memnameToMergedCounts.containsKey(memname))
//				memnameToMergedCounts.put(memname,memToGenCounts.get(mem));
//			else{
//				StaticCounts oldsom = memnameToMergedCounts.get(memname);
//				oldsom.addAllCounts(memToGenCounts.get(mem));
//			}
//		}
//	}
	
//	/**
//	 * ルール変数が束縛されたものとし、生成膜の各個数について実際の値を求める
//	 */
//	public void solve(){
//		memToFixedCounts = new HashMap<Membrane, FixedCounts>();
//		for(Membrane m : memToGenCounts.keySet())
//			memToFixedCounts.put(m,memToGenCounts.get(m).solve());
//		fixed = true;
//	}

	private boolean fixed = false;
	/**
	 * 効果におけるルール変数を無限と束縛し、解く
	 *
	 */
	public void assignInfinityToVar(){
		for(Set<DynamicCounts> doms : memToInhCountss.values())
			for(DynamicCounts dom : doms)
				if(!dom.applyCount.isBound())dom.applyCount.bind(Count.INFINITY.or0());
	}
	
	/**
	 * アトム数、子膜数の下限を0として制約問題としてルール変数を解く
	 */
	public void solveByCounts(){
		for(Membrane mem : memToGenCounts.keySet()){
			// プロセスの独立性が保たれていなければ無視
			if(memnameToCPIFlg.get(TypeEnv.getMemName(mem)))
				continue;
			memToGenCounts.get(mem).solveByCounts();
		}
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
			for(StaticCounts com : memToGenCounts.values())
				com.print();
			Env.p("---mem effect on source counts:");
			for(Set<DynamicCounts> doms : memToInhCountss.values())
				for(DynamicCounts dom : doms)
					dom.print();
			Env.p("");
		}
	}

}
