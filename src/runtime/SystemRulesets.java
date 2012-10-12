package runtime;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * システムルールセットを管理する static クラス
 * @author Mizuno
 */
public final class SystemRulesets {
	private static ArrayList<Ruleset> all = new ArrayList<Ruleset>();
	private static ArrayList<Ruleset> userDefined = new ArrayList<Ruleset>();
	static {
		clear();
	}
	/** 初期化。登録されたユーザー定義システムルールセットを除去する。 */
	public static void clear() {
		all.clear();
		userDefined.clear();
		//all.add(GlobalSystemRuleset.getInstance());
	}
	/**
	 * ユーザー定義システムルールセットを登録する。
	 * @param rs 登録するルールセット
	 */
	public static void addUserDefinedSystemRuleset(Ruleset rs) {
		userDefined.add(rs);
		all.add(rs);
	}
	/**
	 * ユーザー定義システムルールセットのイテレータを取得する
	 * @return ユーザー定義システムルールセットのイテレータ
	 */
	public static Iterator<Ruleset> userDefinedSystemRulesetIterator() {
		return userDefined.iterator();
	}
	
	/**
	 * システムルールセットのイテレータを取得する
	 * @return システムルールセットのイテレータ
	 */
	public static Iterator<Ruleset> iterator() {
		return all.iterator();
	}
}
