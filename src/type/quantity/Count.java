package type.quantity;

public abstract class Count {
	
	public final static InfinityCount INFINITY = new InfinityCount(false);
	public final static InfinityCount M_INFINITY = new InfinityCount(true);	
	
	public abstract String toString();
	public Count self(){
		return this;
	}
	/**
	 * 量の加算
	 * @param c
	 * @return
	 */
	public abstract Count add(Count c);
//		return new SumCount(this,c);
//	}
	/**
	 * 量のor結合
	 * @param c
	 * @return
	 */
//	public Count merge(Count c){
//		return new OrCount(this,c);
//	}

	/**
	 * ルール変数について整理したものを返す
	 * @return
	 */
	public abstract Count reflesh();
	
	/**
	 * 符号をひっくり返したものを返す
	 * 
	 */
	public abstract Count inverse();
	
	/**
	 * 評価値を返す
	 * @return
	 */
	public abstract FixedCount evaluate();
}
