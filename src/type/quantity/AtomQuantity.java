package type.quantity;

/** アトムについての「量」を管理するクラス */
public class AtomQuantity {
	int min = 0;
	int max = 0;
	AtomQuantity(){}
	public static AtomQuantity merge(AtomQuantity aq1, AtomQuantity aq2){
		AtomQuantity ret = new AtomQuantity();
		ret.min = (aq1.min<aq2.min)?aq1.min:aq2.min;
		ret.max = (aq1.max>aq2.max)?aq1.max:aq2.max;
		return ret;
	}
}
