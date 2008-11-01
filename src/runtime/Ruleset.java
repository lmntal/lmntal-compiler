package runtime;
import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * ルールの集合。
 * 現在はルールの配列として表現しているが、将来的には複数のルールのマッチングを
 * １つのマッチングテストで行うようにする。
 */
abstract public class Ruleset {
	/** new束縛された名前の具体値を格納する配列 */
	protected Functor[] holes;
	public List<Rule> compiledRules = new ArrayList<Rule>();
	public boolean isRulesSetted = false;
	public boolean isSystemRuleset = false;
	abstract public String toString();
	abstract public String encode();
	public String[] encodeRulesIndividually(){ return null; }
	/**
	 * アトム主導テストを行い、マッチすれば適用する
	 * @param mem 本膜
	 * @param atom 主導するアトム
	 * @return ルールを適用した場合はtrue
	 */
	abstract public boolean react(Membrane mem, Atom atom);
	/**
	 * 膜主導テストを行い、マッチすれば適用する
	 * @param mem 本膜
	 * @return ルールを適用した場合はtrue
	 */
	abstract public boolean react(Membrane mem);
	/**
	 * 膜主導テストを行い、マッチすれば適用する
	 * @param mem 本膜
	 * @param nondeterministic 非決定的実行の適用検査を行う場合はtrue
	 * @return ルールを適用した場合はtrue
	 */
	abstract public boolean react(Membrane mem, boolean nondeterministic);
	/** new束縛された名前の具体値を指定して新しいRulesetを作成する。
	 * @return 新しいRuleset */
	//abstract
	public Ruleset fillHoles(Functor[] holes) { return null; }
	
	// 061129 okabe runtimeid 廃止による
//	/**
//	 * グローバルルールセットIDを取得する
//	 * @author nakajima */
//	abstract public String getGlobalRulesetID();
	
	////////////////////////////////////////////////////////////////
	
	/** (n-kato)このメソッドを使わないように書き換えてもよい（仮）*/
	public byte[] serialize() {
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bout);
			serialize(out);
			out.close();
			return bout.toByteArray();
		} catch (IOException e) {
			//ByteArrayOutputStreamなので絶対に発生しない。
			throw new RuntimeException("Unexpected Exception", e);
		}
	}
	/** (n-kato)このメソッドを使わないように書き換えてもよい（仮）*/
	public static Ruleset deserialize(byte[] data) {
		try {
			ByteArrayInputStream bin = new ByteArrayInputStream(data);
			ObjectInputStream in = new ObjectInputStream(bin);
			Ruleset ret = Ruleset.deserialize(in);
			in.close();
			return ret;
		} catch (IOException e) {
			//ByteArrayInputStreamなので絶対に発生しない。
			throw new RuntimeException("Unexpected Exception", e);
		}
	}
	
	/**
	 * このインスタンスの内容をストリームに書き込む。
	 * 子クラスでオーバーライドする場合は、最初にこのメソッドを呼び出す必要がある。
	 * @param out 書き込むストリーム
	 */
	public void serialize(ObjectOutputStream out) throws IOException {
		out.writeObject(getClass());
		out.writeObject(holes);
	}
	/**
	 * バイト列からRulesetを復元する。
	 * @param in 読み込むストリーム
	 * @return 復元したオブジェクト
	 */
	public static Ruleset deserialize(ObjectInputStream in) throws IOException {
		Class c;
		Ruleset ret;
		try {
			c = (Class)in.readObject();
			ret = (Ruleset)c.newInstance();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unexpected Error in deserialization");
		} catch (InstantiationException e) {
			throw new RuntimeException("Unexpected Error in deserialization");
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Unexpected Error in deserialization");
		}
		ret.deserializeInstance(in);
		return ret;
	}
	/**
	 * ストリームから、このインスタンスの内容を復元する。子クラスでオーバーライドする場合は、最初にこのメソッドを呼び出す必要がある。
	 * @param in 読み込むストリーム
	 */
	protected void deserializeInstance(ObjectInputStream in) throws IOException {
		try {
			holes = (Functor[])in.readObject();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unexpected Error in deserialization");
		}
	}
}
