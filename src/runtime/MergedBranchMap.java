package runtime;

import java.util.ArrayList;
import java.util.HashMap;

import runtime.functor.Functor;

/**
 * 編み上げ後の命令列を扱うクラス
 * ルールセット1つあたりに1つ作成される
 * アクティブアトムのファンクタをキーに、適用可能なめいれ入れtうを引いてくることが可能
 * @author sakurai
 *
 */

public class MergedBranchMap {
	/**
	 * アクティブアトムのファンクタ⇒それに続く命令列のマップ
	 */
	public HashMap branchMap;
	
	public MergedBranchMap(HashMap bm){
		branchMap = bm;
	}
	
	/**
	 * ファンクタに対応する命令列を返す
	 * @param func アクティブアトムのファンクタ
	 * @return　命令列
	 */
	public ArrayList getInsts(Functor func){
		return (ArrayList)branchMap.get(func);
	}
	
	/**
	 * マップにファンクタが含まれるかを確認
	 * @param func　アクティブアトムのファンクタ
	 * @return ファンクタが含まれるかどうか(boolean)
	 */
	public boolean containsKey(Functor func){
		return branchMap.containsKey(func);
	}
}
