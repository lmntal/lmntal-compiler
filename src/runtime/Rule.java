/*
 * 作成日: 2003/10/24
 *
 */
package runtime;

/**
 * 試行錯誤ちゅう
 * @author hara
 */

public final class Rule {
	public HeadInstruction[] memMatch;
	public HeadInstruction[][] atomMatches; //?
	public BodyInstruction[] body;
	public String text;
	
	/**
	 * 膜主導テストを行い、マッチすれば適用する
	 * @return ルールを適用した場合はtrue
	 */
	public boolean react(Membrane mem) {
		Env.c("Rule.react "+mem);
		return true;
	}
	public String toString() {
		return text;
	}
}
