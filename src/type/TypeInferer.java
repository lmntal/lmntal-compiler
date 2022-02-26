package type;

import compile.structure.Membrane;
import java.util.ArrayList;
import java.util.List;
import runtime.Env;
import type.argument.ArgumentInferer;
import type.connect.ConnectInferer;
import type.quantity.QuantityInferer;

/**
 * This class infers type constraints from COMPILE STRUCTURE
 *
 * @author kudo
 * @since 2006/06/03 (Sat.)
 */
public class TypeInferer {

  /** membrane contains all processes */
  private Membrane root;

  /** @param root */
  public TypeInferer(Membrane root) {
    // 世界的ルート膜にはrootという名前をつける
    root.name = "root";
    this.root = root;
  }

  public void infer() throws TypeException {
    // 初期化処理(左辺出現を収集したり)
    TypeEnv.initialize(root);

    // ユーザ定義情報を取得する
    boolean typeDefined = false;
    List<Membrane> typedefmems = new ArrayList<>();
    for (Membrane topmem : root.mems)
      if (TypeEnv.getMemName(topmem).equals("typedef")) {
        typedefmems.add(topmem);
        break; // TODO 型定義膜が2つあったらどうする => マージ
      }

    TypeChecker tc = new TypeChecker();
    if (typedefmems.size() > 0) {
      typeDefined = tc.parseTypeDefinition(typedefmems);
      root.mems.removeAll(typedefmems); // 型定義膜は検査、コンパイルから外す
    }

    // 出現制約を推論する
    //		if(Env.flgOccurrenceInference){
    //			OccurrenceInferrer oi = new OccurrenceInferrer(root);
    //			oi.infer();
    //			if(Env.flgShowConstraints)
    //				oi.printAll();
    //		}

    ConnectInferer ci = new ConnectInferer(root);
    if (true) {
      ci.infer();
    }

    QuantityInferer qi = new QuantityInferer(root);
    // 個数制約を推論する
    if (Env.flgQuantityInference) {
      qi.infer();
      //			if(Env.flgShowConstraints)
      //				qi.printAll();
    }

    ArgumentInferer ai = new ArgumentInferer(root);
    // 引数制約を推論する
    if (Env.flgArgumentInference) {
      ai.infer();
      // if(false)
      // ai.printAll();
    }

    // 型定義が与えられていたら整合性をチェックする
    if (typeDefined) {
      tc.check(ai, qi);
    }

    // 推論結果を出力する
    if (Env.flgShowConstraints) {
      TypePrinter tp;
      //			if(Env.flgArgumentInference && Env.flgQuantityInference){
      tp = new TypePrinter(ai, qi, ci);
      // printAll が使われていたが LMNtal Syntax で表示する printAllLMNSyntax メソッドに切り替え
      // tp.printAll();
      tp.printAllLMNSyntax();
      //			}
    }
  }
}
