package daemon;

import java.util.HashMap;

import runtime.Atom;
import runtime.Functor;

/*
 * キャッシュ機構。
 * 
 * @author nakajima
 *
 */
class Cache{
	//アトム表
	HashMap atomTable = new HashMap();
	//ファンクタ表
	HashMap functorTable = new HashMap();
	
	
	/*
	 * コンストラクタ。
	 * 
	 * 各ルート膜がキャッシュオブジェクトを持つ？
	 * それとも各LocalLMNtalRuntimeが持つ？
	 */
	Cache(){
	}
	
	/*
	 * キャッシュ文字列をくみたてる
	 * @author nakajima
	 */
	 void encode(){
	 }

	 /*
	  * キャッシュ文字列を解読する
	  * @author nakajima
	  */
	 void decode(){
	 }
	 
	 /*
	  * キャッシュを更新する
	  * @author nakajima
	  *
	  */
	 void update(){
		//キャッシュ更新の時にinsideproxy以外はremove（膜の構成要素から剥がす）する
	 }
	 
	 /*
	  * atom id -> atom object
	  */
	 Atom getAtom(String atomid){
	 	Atom a = (Atom)atomTable.get(atomid);
	 	if(a == null){
	 		return null;
	 	} else {
	 		return a;
	 	}
	 }
	 
	/*
	 * fucntor id -> functor object
	 */
	Functor getFunctor(String functorid){
	   Functor f = (Functor)functorTable.get(functorid);
	   if(f == null){
		   return null;
	   } else {
		   return f;
	   }
	}
	 
}