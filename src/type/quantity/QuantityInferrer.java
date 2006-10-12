package type.quantity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import type.ConstraintSet;

import compile.structure.Atom;
import compile.structure.Membrane;
import compile.structure.ProcessContext;
import compile.structure.RuleStructure;

/**
 * 量的解析(?)
 * @author kudo
 *
 */
public class QuantityInferrer {
	
	private ConstraintSet constraints;
	
	private final CountsOfMemSet countsset;
	
	public QuantityInferrer(ConstraintSet constraints){
		this.constraints = constraints;
		countsset = new CountsOfMemSet();
	}
	
	/**
	 * 量的解析を行う
	 * @param root
	 */
	public void infer(Membrane root){
		// 1回適用されたルールの右辺として検査する
		inferRHSMembrane(root, new NumCount(1));
		
		countsset.printAll();
	}
	
	/**
	 * ルールについて量的解析を行う
	 * @param rule
	 */
	private void inferRule(RuleStructure rule){
		Count count = new VarCount();
		// 左辺膜と右辺膜を、同じ膜として扱う
		countsset.add(inferInheritedMembrane(rule.leftMem, rule.rightMem, count));
		Iterator<Membrane> itm = rule.rightMem.mems.iterator();
		// 右辺子膜の走査
		while(itm.hasNext()){
			inferRHSMembrane(itm.next(),count);
		}
		// 右辺出現ルールの検査
		Iterator<RuleStructure> itr = rule.rightMem.rules.iterator();
		while(itr.hasNext()){
			inferRule(itr.next());
		}
	}

	/**
	 * 右辺の膜を再帰的に走査し、
	 * 1. プロセス文脈が出現しない膜
	 * 2. プロセス文脈が1個出現する膜
	 * 3. プロセス文脈が2個以上出現する膜
	 * にわける
	 * @param rhs
	 */
	private void inferRHSMembrane(Membrane rhs, Count count){
		int pcsize = rhs.processContexts.size();
		switch(pcsize){
		case 0 : // プロセス文脈が出現しない膜
			countsset.add(inferGeneratedMembrane(rhs, count));
			break;
		case 1 : // プロセス文脈が1個出現する膜
			countsset.add(inferInheritedMembrane(((ProcessContext)rhs.processContexts.get(0)).def.lhsOcc.mem,rhs, count));
			break;
		default: // プロセス文脈が2個以上出現する膜
			Set<Membrane> lhss = new HashSet<Membrane>();
			Iterator<ProcessContext> itp = rhs.processContexts.iterator();
			while(itp.hasNext()){
				ProcessContext lhsOcc = (ProcessContext)itp.next().def.lhsOcc;
				if(!lhss.contains(lhsOcc.mem))lhss.add(lhsOcc.mem);
			}
			countsset.add(inferMultiInheritedMembrane(lhss,rhs, count));
		}
		// 子膜の検査
		Iterator<Membrane> itm = rhs.mems.iterator();
		while(itm.hasNext()){
			inferRHSMembrane(itm.next(), count);
		}
		// ルールの検査
		Iterator<RuleStructure> itr = rhs.rules.iterator();
		while(itr.hasNext()){
			inferRule(itr.next());
		}
	}

	/**
	 * 左辺から右辺に受け継がれた「同じ膜」である、として解析する。
	 * @param lhs
	 * @param rhs
	 */
	private CountsOfMem inferInheritedMembrane(Membrane lhs, Membrane rhs, Count count){
		//右辺から左辺を減算(解析結果を加算)
		CountsOfMem rhsCounts = getCountsOfMem(1,rhs,count,1);
		rhsCounts.addAllCounts(getCountsOfMem(-1,lhs,count,1));
		return rhsCounts;
	}

	/**
	 * 左辺複数の膜から右辺に受け継がれた「マージされた膜」として解析する。
	 * TODO inferInheritedMembraneはこちらに統合可能
	 * @param lhss
	 * @param rhs
	 */
	private CountsOfMem inferMultiInheritedMembrane(Set<Membrane> lhss, Membrane rhs, Count count){
		Iterator<Membrane> itl = lhss.iterator();
		CountsOfMem rhsCounts = getCountsOfMem(1,rhs,count,lhss.size());
		while(itl.hasNext()){
			Membrane lhs = itl.next();
			rhsCounts.addAllCounts(getCountsOfMem(-1,lhs,count,lhss.size()));
		}
		return rhsCounts;
	}
	
	/**
	 * 単独で生成された膜として解析する。
	 * @param mem
	 */
	private CountsOfMem inferGeneratedMembrane(Membrane mem, Count count){
		return getCountsOfMem(1,mem,count, 0);
	}

	
	private CountsOfMem getCountsOfMem(int sign, Membrane mem, Count count, int multiple){
		CountsOfMem quantities = new CountsOfMem(mem, multiple);
		//アトムの解析結果
		Iterator<Atom> ita = mem.atoms.iterator();
		while(ita.hasNext()){
			// <-R, p/n>
			quantities.addAtomCount(ita.next(),(sign==1?count:new MinCount(count)));
		}
		//子膜の解析結果
		Iterator<Membrane> itm = mem.mems.iterator();
		while(itm.hasNext()){
			// <-R, m>
			quantities.addMemCount(itm.next(),(sign==1?count:new MinCount(count)));
		}
		return quantities;
	}
	
}
