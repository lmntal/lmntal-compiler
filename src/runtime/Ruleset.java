package runtime;

import java.io.*;

/**
 * ルールの集合。
 * 現在はルールの配列として表現しているが、将来的には複数のルールのマッチングを
 * １つのマッチングテストで行うようにする。
 */
abstract public class Ruleset {
	/** new束縛された名前の具体値を格納する配列 */
	protected Functor[] holes;
	abstract public String toString();
	/**
	 * アトム主導テストを行い、マッチすれば適用する
	 * @return ルールを適用した場合はtrue
	 */
	abstract public boolean react(Membrane mem, Atom atom);
	/**
	 * 膜主導テストを行い、マッチすれば適用する
	 * @return ルールを適用した場合はtrue
	 */
	abstract public boolean react(Membrane mem);
	/** new束縛された名前の具体値を指定して新しいRulesetを作成する。
	 * @return 新しいRuleset */
	//abstract
	public Ruleset fillHoles(Functor[] holes) { return null; }
	
	/**
	 * グローバルルールセットIDを取得する
	 * @author nakajima */
	abstract public String getGlobalRulesetID();
	
	////////////////////////////////////////////////////////////////
	
	/** (n-kato)このメソッドを使わないように書き換えてもよい（仮）*/
	public byte[] serialize() {
		return new byte[0]; // TODO 実装（水野君） - ただしバイナリ転送問題の解決が先
	}
	/** (n-kato)このメソッドを使わないように書き換えてもよい（仮）*/
	public static Ruleset deserialize(byte[] data) {
		return null; // todo 実装（水野君） - ただしバイナリ転送問題の解決が先
	}
	
	/**
	 * このインスタンスの内容をストリームに書き込む。
	 * 子クラスでオーバーライドする場合は、最初にこのメソッドの返り値をバイト列の先頭に追加する必要がある。
	 * @return バイト列
	 */
	public void serialize(ObjectOutputStream out) throws IOException {
		out.writeObject(getClass());
		out.writeObject(holes);
	}
	/**
	 * バイト列からRulesetを復元する。
	 * @param out バイト列
	 * @return 復元したオブジェクト
	 */
	public static Ruleset deserialize(ObjectInputStream in) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class c = (Class)in.readObject();
		Ruleset ret = (Ruleset)c.newInstance();
		ret.deserializeInstance(in);
		return ret;
	}
	/**
	 * ストリームから、このインスタンスの内容を復元する。子クラスでオーバーライドする場合は、最初にこのメソッドを呼び出す必要がある。
	 * @param out 読み込むストリーム
	 */
	protected void deserializeInstance(ObjectInputStream in) throws IOException, ClassNotFoundException {
		holes = (Functor[])in.readObject();
	}
}
