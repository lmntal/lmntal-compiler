package runtime;

import java.util.HashMap;
import java.util.Iterator;


/*
 * 「計算ノード管理クラス」
 */

final class LMNtalRuntimeManager{

	/** 計算ノード表（String -> AbstractLMNtalRuntime）*/
	static HashMap runtimeids = new HashMap();
	/** 計算ノード表を利用開始する */
	public static void init() {}
	/** 指定された物理マシンに接続し、計算ノード表に登録する */
	public static AbstractLMNtalRuntime connectRuntime(String node) {
		node = node.intern();
		AbstractLMNtalRuntime ret = (AbstractLMNtalRuntime)runtimeids.get(node);
		if (ret == null) {
			ret = new RemoteLMNtalRuntime(node);
			runtimeids.put(node,ret);
		}
		return ret;
	}
	/** 登録されている全ての物理マシンを終了し、計算ノード表の登録を削除する */
	public static void terminateAll() {
		Iterator it = runtimeids.keySet().iterator();
		while (it.hasNext()) {
			AbstractLMNtalRuntime machine = (AbstractLMNtalRuntime)runtimeids.get(it.next());
			machine.terminate();
		}
		runtimeids.clear();
	}
}
