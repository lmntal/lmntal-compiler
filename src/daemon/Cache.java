package daemon;

/*
 * キャッシュ機構。
 * 
 * @author nakajima
 *
 */
class Cache{
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
	 void refresh(){
		//キャッシュ更新の時にinsideproxy以外はremove（膜の構成要素から剥がす）する
	 }
}