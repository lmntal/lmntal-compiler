package util;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author mizuno
 * 汎用ユーティリティメソッド・定数を集めたクラス
 */
abstract public class Util {
	public static final Iterator NULL_ITERATOR = (new ArrayList()).iterator();
	public static void systemError(String msg) {
		System.err.println(msg);
		System.exit(-1);
	}
}
