package type;

import runtime.Env;
import type.argument.ArgumentInferer;
import type.quantity.QuantityInferer;

import compile.structure.Membrane;

/**
 * This class infers type constraints from COMPILE STRUCTURE
 * 
 * @author kudo
 * @since 2006/06/03 (Sat.)
 */
public class TypeInferer {

	/** membrane contains all processes */
	private Membrane root;

	/**
	 * @param root
	 */
	public TypeInferer(Membrane root) {
		this.root = root;
	}

	public void infer() throws TypeException {
		TypeEnv.initialize(root);
		
		// ユーザ定義情報を取得する
		boolean typeDefined = false;
		Membrane typedefmem = null;
		for(Membrane topmem : root.mems)
			if(topmem.name.equals("typedef")){
				typedefmem = topmem;
				break; // TODO 型定義膜が2つあったらどうする
			}
		
		TypeChecker tc = new TypeChecker();
		if(typedefmem != null){
			typeDefined = tc.parseTypeDefinition(typedefmem);
			root.mems.remove(typedefmem); // 型定義膜は検査、コンパイルから外す
		}
		
		// 出現制約を推論する
		// TODO 個数が推論できるなら不要(?)
//		if(Env.flgOccurrenceInference){
//			OccurrenceInferrer oi = new OccurrenceInferrer(root);
//			oi.infer();
//			if(Env.flgShowConstraints)
//				oi.printAll();
//		}

		QuantityInferer qi = new QuantityInferer(root);
		// 個数制約を推論する
		if(Env.flgQuantityInference){
			qi.infer();
//			if(Env.flgShowConstraints)
//				qi.printAll();
		}
		
		ArgumentInferer ai = new ArgumentInferer(root);
		// 引数制約を推論する
		if(Env.flgArgumentInference){
			ai.infer();
//			if(Env.flgShowConstraints)
//				ai.printAll();
		}
		
		// 型定義が与えられていたら整合性をチェックする
		if(typeDefined){
			tc.check(ai, qi);
		}

		//推論結果を出力する
		if(Env.flgShowConstraints){
			TypePrinter tp;
			if(Env.flgArgumentInference && Env.flgQuantityInference){
				tp = new TypePrinter(ai,qi);
				tp.printAll();
			}
		}
		
		
	}

}
