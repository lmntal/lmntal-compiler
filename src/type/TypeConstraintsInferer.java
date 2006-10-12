package type;

import runtime.Env;
import type.argument.ArgumentInferrer;
import type.occurrence.OccurrenceInferrer;
import type.quantity.QuantityInferrer;

import compile.structure.Membrane;

/**
 * This class infers type constrains from COMPILE STRUCTURE
 * 
 * @author kudo
 * @since 2006/06/03 (Sat.)
 */
public class TypeConstraintsInferer {

	/** membrane contains all processes */
	private Membrane root;

//	private ConstraintSet constraints = new ConstraintSet();

	/**
	 * @param root
	 */
	public TypeConstraintsInferer(Membrane root) {
		this.root = root;
	}

	public void infer() throws TypeConstraintException {
		// 全ての膜について、ルールの左辺最外部出現かどうかの情報を得る
		TypeEnv.collectLHSMems(root.rules);
		
		// 出現制約を推論する
		// TODO 個数が推論できるなら不要(?)
		if(Env.flgOccurrenceInference){
			OccurrenceInferrer oi = new OccurrenceInferrer(root);
			oi.infer();
			if(Env.flgShowConstraints)
				oi.printAll();
		}

		// 個数制約を推論する
		if(Env.flgQuantityInference){
			QuantityInferrer qi = new QuantityInferrer(root);
			qi.infer();
			if(Env.flgShowConstraints)
				qi.printAll();
		}
		
		// 引数制約を推論する
		if(Env.flgArgumentInference){
			ArgumentInferrer ai = new ArgumentInferrer(root);
			ai.infer();
			if(Env.flgShowConstraints)
				ai.printAll();
		}
	}

}
