package type.quantity;

import runtime.Env;

import compile.structure.Membrane;

/**
 * それぞれの膜について作られる。
 * 動的な量情報を表すクラス
 * @author kudo
 *
 */
public class DynamicCountsOfMem {
	public final Membrane mem;
	
	/** 適用回数を表す変数 */
	public final VarCount applyCount;
	
	/**
	 * この膜の所属プロセスが何倍されるかを表す。
	 * 1 : 移動時(あるいはルールの本膜等)
	 * >1 : 複製、マージ
	 */
	public final int multiple;
	
	/** 削除するプロセス */
	public final StaticCountsOfMem removeCounts;
	/** 生成するプロセス */
	public final StaticCountsOfMem generateCounts;

	public DynamicCountsOfMem(StaticCountsOfMem removeCounts, int multiple, StaticCountsOfMem generateCounts, VarCount applyCount){
		this.mem = generateCounts.mem;
		this.multiple = multiple;
		this.applyCount = applyCount;
		this.removeCounts = removeCounts;
		this.generateCounts = generateCounts;
	}

//	public void addAllCounts(DynamicCountsOfMem dom){
//		removeCounts.addAllCounts(dom.removeCounts);
//		generateCounts.addAllCounts(dom.generateCounts);
//	}
	
//	/**
//	 * 具体値にする
//	 * @return
//	 */
//	public FixedCounts solve(){
////		return new FixedCounts(this);
//	}
	
	public void print(){
		Env.p("---dynamic count in " + mem.name + " :");
		Env.p("----remove:");
		removeCounts.print();
		Env.p("----generate:");
		generateCounts.print();
//		Env.p("----atoms of " + TypeEnv.getMemName(mem) + ":");
//		Iterator<Functor> itf = functorToCount.keySet().iterator();
//		while(itf.hasNext()){
//			Functor f = itf.next();
//			Env.p(f + ":" + functorToCount.get(f));
//		}
//		Env.p("----mems of " + TypeEnv.getMemName(mem) + ":");
//		Iterator<String> itm = memnameToCount.keySet().iterator();
//		while(itm.hasNext()){
//			String m = itm.next();
//			Env.p(m + ":" + memnameToCount.get(m));
//		}
	}
	
}

