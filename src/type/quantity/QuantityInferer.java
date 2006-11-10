package type.quantity;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import type.TypeEnv;

import compile.structure.Atom;
import compile.structure.Membrane;
import compile.structure.ProcessContext;
import compile.structure.RuleContext;
import compile.structure.RuleStructure;

/**
 * 量的解析(?)
 * @author kudo
 *
 */
public class QuantityInferer {
	
	private final CountsSet countsset;
	
	public Map<String, FixedCounts> getMemNameToFixedCountsSet(){
		return countsset.getMemNameToFixedCountsSet();
	}
	
	private Membrane root;
	
	public QuantityInferer(Membrane root){
		this.countsset = new CountsSet();
		this.root = root;
	}
	
	/**
	 * 量的解析を行う
	 * @param root
	 */
	public void infer(){
		inferRHSMembrane(root);
		
		if(TypeEnv.countLevel >= TypeEnv.COUNT_APPLY){
			// 個々の具体膜についてそれぞれに効果を適用する
			countsset.applyIndividual();
		}
		// 適用回数変数に[0,無限]を割り当てる
		countsset.assignInfinityToVar();
		if(TypeEnv.countLevel >= TypeEnv.COUNT_APPLY){
			// アトム個数の下限を0として適用回数を解く
			countsset.solveByCounts();
		}
		// 具体値を計算させる
		countsset.solveIndividuals();
		countsset.solveDynamics();
		// 具体膜をマージする
		countsset.mergeFixeds();
		//プロセスの独立性の崩れた膜に効果を適用する
		countsset.applyCollapseds();
		
		// 全下限を0に直し、[0,0]を0にする
		countsset.assignZeroToMinimum();
	}
	
	public void printAll(){
		countsset.printAll();
	}
	
	/**
	 * ルールについて量的解析を行う
	 * @param rule
	 */
	private void inferRule(RuleStructure rule){
		// 左辺膜と右辺膜を、同じ膜として扱う
		inferRuleRootMembrane(rule);
		// 右辺子膜の走査
		for(Membrane rhsmem : ((List<Membrane>)rule.rightMem.mems))
			inferRHSMembrane(rhsmem);
		// 右辺出現ルールの検査
		for(RuleStructure rhsrule : ((List<RuleStructure>)rule.rightMem.rules))
			inferRule(rhsrule);
	}

	/**
	 * 右辺の膜を再帰的に走査し、
	 * 1. プロセス文脈が出現しない膜
	 * 2. プロセス文脈が1個出現する膜
	 * 3. プロセス文脈が2個以上出現する膜
	 * にわける
	 * @param rhs
	 */
	private void inferRHSMembrane(Membrane rhs){
		
		Set<Membrane> lhss = new HashSet<Membrane>();
		/* プロセス文脈、型付きプロセス文脈について左辺出現膜の集合を得る */
		for(ProcessContext rhsOcc : ((List<ProcessContext>)rhs.processContexts)){
			ProcessContext lhsOcc = (ProcessContext)rhsOcc.def.lhsOcc;
			if(!lhss.contains(lhsOcc.mem))lhss.add(lhsOcc.mem);
		}
		for(ProcessContext rhsOcc : ((List<ProcessContext>)rhs.typedProcessContexts)){
			ProcessContext lhsOcc = (ProcessContext)rhsOcc.def.lhsOcc;
			if(!lhss.contains(lhsOcc.mem))lhss.add(lhsOcc.mem);
		}
		
		// プロセス文脈が複数の膜から来ている場合、プロセスの独立性は絶たれる
		if(lhss.size() > 1)countsset.collapseProcessIndependency(TypeEnv.getMemName(rhs));

		switch(lhss.size()){
		case 0 : // プロセス文脈が出現しない膜
			inferGeneratedMembrane(rhs);
			break;
		default:
			inferMultiInheritedMembrane(lhss,rhs);
//		case 1 : // プロセス文脈が1個出現する膜
//			countsset.add(inferInheritedMembrane(((ProcessContext)rhs.processContexts.get(0)).def.lhsOcc.mem,rhs));
//			break;
//		default: // プロセス文脈が2個以上出現する膜
//			Set<Membrane> lhss = new HashSet<Membrane>();
//			for(ProcessContext rhsOcc : ((List<ProcessContext>)rhs.processContexts)){
//				ProcessContext lhsOcc = (ProcessContext)rhsOcc.def.lhsOcc;
//				if(!lhss.contains(lhsOcc.mem))lhss.add(lhsOcc.mem);
//			}
//			countsset.add(inferMultiInheritedMembrane(lhss,rhs));
		}

		/** プロセス文脈の分散が無いことを確認する */
		if(!checkIndependency((List<ProcessContext>)rhs.processContexts, rhs) ||
			!checkIndependency((List<ProcessContext>)rhs.typedProcessContexts, rhs) )
			countsset.collapseProcessUnderBounds(TypeEnv.getMemName(rhs));
		
		/** ルール文脈の移動の有無を確認する */
		for(RuleContext rc : ((List<RuleContext>)rhs.ruleContexts)){
			if(lhss.contains(rc.mem))continue;
			else{
				if(lhss.size() == 0)
					countsset.collapseRuleIndependency(rhs);
				else
					countsset.collapseRulesIndependency(TypeEnv.getMemName(rhs));
			}
		}
		
		// 子膜の検査
		for(Membrane child : ((List<Membrane>)rhs.mems))
			inferRHSMembrane(child);
		// ルールの検査
		for(RuleStructure rule : ((List<RuleStructure>)rhs.rules))
			inferRule(rule);
	}

	/**
	 * わたされた文脈列のいずれかがこの膜に出現することを確かめる
	 * いずれも出現していない場合、falseを返す
	 */
	public boolean checkOccurrence(List<ProcessContext> rhsOccs, Membrane rhs){
		boolean okflg = false;
		for(ProcessContext pcRhsOcc : rhsOccs){
			if(pcRhsOcc.mem == rhs){
				okflg = true;
				break;
			}
		}
		return okflg;
	}
	/**プロセス文脈の分散をチェックする
	 * 渡された文脈列のそれぞれの左辺出現膜のプロセスが全て輸送されていることを確認し、
	 * 確認できたらtrueを、確認できなければfalseを返す */
	public boolean checkIndependency(List<ProcessContext> rhsOccs, Membrane rhs){
		for(ProcessContext rhsOcc : rhsOccs){
			Membrane lhsmem = ((ProcessContext)rhsOcc.def.lhsOcc).mem;
			if(lhsmem.processContexts.size() > 0){
				boolean ok = checkOccurrence((List<ProcessContext>)((ProcessContext)lhsmem.processContexts.get(0)).def.rhsOccs, rhs);
				if(!ok)return false;
			}
			boolean okflg = true;
			for(ProcessContext lhsOcc : ((List<ProcessContext>)lhsmem.typedProcessContexts)){
				boolean ok = checkOccurrence((List<ProcessContext>)lhsOcc.def.rhsOccs, rhs);
				if(!ok){
					okflg = false;
					break;
				}
			}
			if(!okflg)return false;
		}
		return true;
	}

//	/**
//	 * 左辺から右辺に受け継がれた「同じ膜」である、として解析する。
//	 * @param lhs
//	 * @param rhs
//	 */
//	private DynamicCounts inferInheritedMembrane(Membrane lhs, Membrane rhs){
//		VarCount vc = new VarCount();
//		Count count = new Count(vc);
//		//右辺から左辺を減算(解析結果を加算)
//		StaticCounts rhsCounts = getCountsOfMem(1,rhs,count);
//		StaticCounts lhsCounts = getCountsOfMem(-1,lhs,count);
//		return new DynamicCounts(lhsCounts, 1, rhsCounts,vc);
//	}
	
	/**
	 * ルール右辺と左辺の本膜について受け継がれたものとして解析する。
	 * @param rule
	 * @param count
	 * @return
	 */
	private void inferRuleRootMembrane(RuleStructure rule){
		//右辺から左辺を減算(解析結果を加算)
		VarCount vc = new VarCount();
		Count count = new Count(vc);
		StaticCounts rhsCounts = getCountsOfMem(1,rule.rightMem,count);
		StaticCounts lhsCounts = getCountsOfMem(-1,rule.leftMem,count);
		
		rhsCounts.mem = rule.parent;
		lhsCounts.mem = rule.parent;
		
		countsset.add(new DynamicCounts(lhsCounts, 1, rhsCounts, vc), false);
	}

	/**
	 * 左辺複数の膜から右辺に受け継がれた「マージされた膜」として解析する。
	 * @param lhss
	 * @param rhs
	 */
	private void inferMultiInheritedMembrane(Set<Membrane> lhss, Membrane rhs){
		VarCount vc = new VarCount();
		Count count = new Count(vc);
		StaticCounts rhsCounts = getCountsOfMem(1,rhs,count);
		StaticCounts lhsCounts = new StaticCounts(rhs);
		for(Membrane lhs : lhss)
			lhsCounts.addAllCounts(getCountsOfMem(-1,lhs,count));
		countsset.add(new DynamicCounts(lhsCounts, lhss.size(), rhsCounts, vc), true);
	}
	
	/**
	 * 単独で生成された膜として解析する。
	 * @param mem
	 */
	private void inferGeneratedMembrane(Membrane mem){
		VarCount vc = new VarCount();
		vc.bind(new NumCount(1));
		Count count = new Count(vc);
		countsset.add(getCountsOfMem(1,mem,count));
	}
	
	private StaticCounts getCountsOfMem(int sign, Membrane mem, Count count){
		StaticCounts quantities = new StaticCounts(mem);
		//アトムの解析結果
		for(Atom atom : ((List<Atom>)mem.atoms))
			quantities.addAtomCount(atom,(Count.mul(sign, count)));
		//子膜の解析結果
		for(Membrane child : ((List<Membrane>)mem.mems))
			quantities.addMemCount(child,(Count.mul(sign,count)));
		return quantities;
	}
	
//	/**
//	 * ルール適用回数を全て無限として解析結果を解く
//	 * 
//	 */
//	private boolean solveRVAsInfinity(){
//		return true;
//	}
}
