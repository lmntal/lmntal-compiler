package type.quantity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import runtime.Env;
import runtime.functor.Functor;
import type.TypeEnv;

import compile.structure.Membrane;

/**
 * 膜名ごとに量的解析結果を保持する
 * @author kudo
 *
 */
public class CountsSet {
	/** 生成膜 -> 固定多重度 */
	private final Map<Membrane,StaticCounts> memToGenCounts = new HashMap<Membrane,StaticCounts>();
	/** 継続膜・混在膜・最外膜 -> その膜の変動多重度 */
	private final Map<Membrane,Set<DynamicCounts>> memToInhCountss = new HashMap<Membrane,Set<DynamicCounts>>();
	/** 膜名 -> 全ての変動多重度 */
	private final Map<String,Set<DynamicCounts>> memnameToAllInhCountss = new HashMap<String,Set<DynamicCounts>>();
	/** 膜名 -> 共通の変動多重度 */
	private final Map<String,Set<DynamicCounts>> memnameToCommonInhCountss = new HashMap<String, Set<DynamicCounts>>();
	/** ソース上の膜 -> 量解析結果(生成膜/評価済み) */
	private final Map<Membrane,FixedCounts> memToFixedCounts = new HashMap<Membrane, FixedCounts>();
//	/** ソース上の膜 -> 量解析結果(継続膜/評価済み) */
//	Map<Membrane,FixedCounts> memToInhFixedCounts;
	private final Map<String, FixedCounts> memnameToMergedFixedCounts = new HashMap<String, FixedCounts>();
	/** 膜名 -> 量解析結果(継続膜) */
	// private final Map<String,Set<FixedDynamicCounts>> memnameToFixedDynamicCountss = new HashMap<String, Set<FixedDynamicCounts>>();

	public CountsSet(){
	}
	
	/** 効果対象 (生成膜 : 自身, 継続膜/過剰膜/混在膜 : null, 最外部 : ルール所属膜の効果対象*/
	public final Map<Membrane, Membrane> effectTarget = new HashMap<Membrane, Membrane>();
	
	/**
	 * 膜の解析結果を加えていく。
	 * この段階では、膜は同名でもソース上の別の膜なら区別される。
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
	/**
	 * 
	 * @param counts
	 */
	public void add(DynamicCounts counts, boolean common){
		String memname = TypeEnv.getMemName(counts.mem);
		if(common){// 共通
			Set<DynamicCounts> doms = memnameToCommonInhCountss.get(memname);
			if(doms == null){
				doms = new HashSet<DynamicCounts>();
				memnameToCommonInhCountss.put(memname, doms);
			}
			doms.add(counts);
		}// 生成膜ルール
		else{
			if(!memToInhCountss.containsKey(counts.mem)){
				Set<DynamicCounts> doms = new HashSet<DynamicCounts>();
				doms.add(counts);
				memToInhCountss.put(counts.mem,doms);
			}
			else{
				Set<DynamicCounts> oldcountss = memToInhCountss.get(counts.mem);
				oldcountss.add(counts);
			}
		}
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
	
//	Set<DynamicCounts> clonedDynamicCounts = new HashSet<DynamicCounts>();
	
	/** 個々の具体膜ごとに変数をふり、効果を適用し、制約を解く
	 * プロセスの独立性が崩れている膜については何もしない
	 *  */
	// 個々に解ける生成膜については解く
	public void solveIndividual(){
		for(Membrane mem : memToGenCounts.keySet()){
			String memname = TypeEnv.getMemName(mem);
			/** プロセスの独立性が崩れている場合、何もしない */
			Boolean cpiflg = memnameToCPIFlg.get(memname);
			if(cpiflg != null && cpiflg &&
					Env.quantityInferenceLevel >= Env.COUNT_APPLYANDMERGE){
//				if(!memnameToMergedCounts.containsKey(memname))
//					memnameToMergedCounts.put(memname,memToGenCounts.get(mem));
//				else{
//					StaticCounts oldsom = memnameToMergedCounts.get(memname);
//					oldsom.merge(memToGenCounts.get(mem));
//				}
			}
			else{
				/** ルールの独立性が崩れている場合, 他の膜の効果を適用 */
				Boolean criflg = memToCRIFlg.get(mem);
				if(criflg == null || !criflg)criflg = memnameToCRIFlg.get(memname);
				if(criflg != null && criflg
						){ // ルールセット独立性が崩れている
					for(DynamicCounts dom : memnameToAllInhCountss.get(memname)){
						// DynamicCountsをコピーして適用する。(この時変数も複製され、別オブジェクトになる)
						DynamicCounts domclone = dom.clone();
//						clonedDynamicCounts.add(domclone);
						domclone.assignToVar(new IntervalCount(new NumCount(0),Count.INFINITY));
						memToGenCounts.get(mem).apply(domclone);
					}
					// 解く
					if(Env.quantityInferenceLevel >= Env.COUNT_APPLYANDMERGEDETAIL)
						memToGenCounts.get(mem).solveByCounts();
					memToFixedCounts.put(mem, memToGenCounts.get(mem).solve());
				}
				else{// ルールセット独立性が崩れてない
					Set<DynamicCounts> doms = memToInhCountss.get(mem);
					/** その具体膜への効果を適用 */
					if(doms != null){
						for(DynamicCounts dom : doms){
							DynamicCounts domclone = dom.clone();
							domclone.assignToVar(new IntervalCount(new NumCount(0),Count.INFINITY));
							memToGenCounts.get(mem).apply(domclone);
						}
					}
					/** その膜名への共通効果を適用 */
					doms = memnameToCommonInhCountss.get(memname);
					if(doms != null){
						for(DynamicCounts dom : doms){
							DynamicCounts domclone = dom.clone();
//							clonedDynamicCounts.add(domclone);
							domclone.assignToVar(new IntervalCount(new NumCount(0),Count.INFINITY));
							memToGenCounts.get(mem).apply(domclone);
						}
					}
					//解く
					if(Env.quantityInferenceLevel >= Env.COUNT_APPLYANDMERGEDETAIL)
						memToGenCounts.get(mem).solveByCounts();
					memToFixedCounts.put(mem, memToGenCounts.get(mem).solve());
//					memToFixedCounts.put(mem, memToGenCounts.get(mem).solveByCounts());
				}
				// 膜名について、適用済みとする
				memnameToAlreadyApplyed.put(memname, true);
			}
		}
		mergeFixeds();
	}
	
	public void solveAll(){
//		Map <String, StaticCounts> memnameToGenCount = new HashMap<String, StaticCounts>();
		for(Membrane mem : memToGenCounts.keySet()){
			String memname = TypeEnv.getMemName(mem);
			/** プロセスの独立性が崩れている場合 */
			Boolean cpiflg = memnameToCPIFlg.get(memname);
			if(cpiflg != null && cpiflg &&
					Env.quantityInferenceLevel >= Env.COUNT_APPLYANDMERGE){
				if(!(memnameToMergedFixedCounts.containsKey(memname))){
					for(DynamicCounts dom : memnameToAllInhCountss.get(memname)){
						DynamicCounts domclone = dom.clone();
//						clonedDynamicCounts.add(domclone);
						domclone.assignToVar(new IntervalCount(new NumCount(0),Count.INFINITY));
						memToGenCounts.get(mem).apply(domclone);
					}
					memnameToMergedFixedCounts.put(memname, memToGenCounts.get(mem).solve());
//					memnameToGenCount.put(memname, memToGenCounts.get(mem));
				}
				else{
					FixedCounts oldsc = memnameToMergedFixedCounts.get(memname);
					oldsc.addAllCounts(memToGenCounts.get(mem).solve());
				}
//				if(!memnameToMergedCounts.containsKey(memname))
//					memnameToMergedCounts.put(memname,memToGenCounts.get(mem));
//				else{
//					StaticCounts oldsom = memnameToMergedCounts.get(memname);
//					oldsom.merge(memToGenCounts.get(mem));
//				}
			}
		}
		
	}
	
//	public void solveIndividuals(){
//		for(Membrane mem : memToGenCounts.keySet()){
//			if()
//			
//			
//			memToFixedCounts.put(mem, memToGenCounts.get(mem).solve());
//		}
//	}
	
//	public void solveDynamics(){
//		for(String memname : memnameToAllInhCountss.keySet()){
//			Set<FixedDynamicCounts> fdoms = new HashSet<FixedDynamicCounts>();
//			for(DynamicCounts dom : memnameToAllInhCountss.get(memname)){
//				fdoms.add(dom.solve());
//			}
//			memnameToFixedDynamicCountss.put(memname, fdoms);
//		}
//	}
//	
//	// マージされた膜に対し、適用済みのフラグが立っていない場合にのみ全ルール適用
//	public void applyCollapseds(){
//		for(String memname : memnameToMergedFixedCounts.keySet()){
//			Boolean already = memnameToAlreadyApplyed.get(memname);
//			if(already != null && already)continue;
//			Set<FixedDynamicCounts> doms = memnameToFixedDynamicCountss.get(memname);
//			if(doms != null){
//				for(FixedDynamicCounts dom : doms){
//					memnameToMergedFixedCounts.get(memname).apply(dom);
//				}
//			}
//		}
//	}
	
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
	
//	private boolean fixed = false;
	/**
	 * 効果におけるルール変数を無限と束縛し、解く
	 *
	 */
//	public void assignInfinityToVar(){
//		for(Set<DynamicCounts> doms : memToInhCountss.values())
//			for(DynamicCounts dom : doms)
//				if(!dom.applyCount.isBound())dom.applyCount.bind(new IntervalCount(new NumCount(0),Count.INFINITY));
//		for(DynamicCounts dom : clonedDynamicCounts)
//			if(!dom.applyCount.isBound())dom.applyCount.bind(new IntervalCount(new NumCount(0),Count.INFINITY));
//		for(Set<DynamicCounts> doms : memnameToCommonInhCountss.values())
//			for(DynamicCounts dom : doms)
//				if(!dom.applyCount.isBound())dom.applyCount.bind(new IntervalCount(new NumCount(0),Count.INFINITY));
//	}
	
	/** 下限が0以下なのを0に補正 */
	public void assignZeroToMinimum(){
		for(FixedCounts fc : memnameToMergedFixedCounts.values()){
			for(Functor f : fc.functorToCount.keySet()){
				IntervalCount ic = fc.functorToCount.get(f);
				if(ic.min.compare(new NumCount(0))< 0){
					fc.functorToCount.put(f, new IntervalCount(new NumCount(0),ic.max));
				}
			}
			for(String m : fc.memnameToCount.keySet()){
				IntervalCount ic = fc.memnameToCount.get(m);
				if(ic.min.compare(new NumCount(0))< 0){
					fc.memnameToCount.put(m, new IntervalCount(new NumCount(0),ic.max));
				}
			}
		}
	}
	
	/**
	 * アトム数、子膜数の下限を0として制約問題としてルール変数を解く
	 */
	public void solveByCounts(){
		for(Membrane mem : memToGenCounts.keySet()){
			// プロセスの独立性が保たれていなければ無視
			Boolean cpiflg = memnameToCPIFlg.get(TypeEnv.getMemName(mem));
			if(cpiflg != null && cpiflg)
//			if(memnameToCPIFlg.get(TypeEnv.getMemName(mem)))
				continue;
			memToGenCounts.get(mem).solveByCounts();
		}
	}
	
	public Map<String, FixedCounts> getMemNameToFixedCountsSet(){
		return memnameToMergedFixedCounts;
	}
	
	public void printAll(){
//		if(fixed){
			Env.p("--QUANTITY ANALYSIS");
//			Env.p("---mem on source counts:");
//			for(FixedCounts fc : memToFixedCounts.values())
//				fc.print();
//			Env.p("");
			Env.p("---mem on source counts:");
			for(FixedCounts fc : memnameToMergedFixedCounts.values())
				fc.print();
			Env.p("");
//		}
//		else{
//			Env.p("--QUANTITY ANALYSIS");
//			Env.p("---mem on source counts:");
//			for(StaticCounts com : memToGenCounts.values())
//				com.print();
//			Env.p("---mem effect on source counts:");
//			for(Set<DynamicCounts> doms : memToInhCountss.values())
//				for(DynamicCounts dom : doms)
//					dom.print();
//			Env.p("");
//		}
	}

}
