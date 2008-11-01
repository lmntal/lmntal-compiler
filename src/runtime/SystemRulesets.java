package runtime;

import java.util.ArrayList;
import java.util.Iterator;

import runtime.systemRuleset.GlobalSystemRuleset;

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
		all.add(GlobalSystemRuleset.getInstance());
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

	/**
	 * 膜主導テストによるシステムルールの適用を試みる。
	 * @return 適用した場合はtrue
	 */
	public static boolean react(Membrane mem, boolean nondeterministic) {
		boolean flag = false;
		int debugvalue = Env.debug; // todo spy機能を実装する
		if (Env.debug < Env.DEBUG_SYSTEMRULESET) Env.debug = 0;
		Iterator<Ruleset> itsys = SystemRulesets.iterator();
		while (itsys.hasNext()) {
			if (itsys.next().react(mem, nondeterministic)) {
				flag = true;
				break;
			}
		}
		Env.debug = debugvalue;
		return flag;
	}
}
