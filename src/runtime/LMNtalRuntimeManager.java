package runtime;

import java.util.HashMap;
import java.util.Iterator;

import daemon.LMNtalDaemonMessageProcessor;


/**
 * 計算ノード管理クラス
 * @author n-kato, nakajima
 */

public final class LMNtalRuntimeManager {

	/** 計算ノード表（String -> AbstractLMNtalRuntime）*/
	static HashMap runtimeids = new HashMap();
	/** 計算ノード表を利用開始する */
	public static void init() {}
	
	/** 指定されたホストに接続し、計算ノード表に登録する
	 *  すでに登録されている場合は生存を確認する。生存を確認できない場合はnullを返す。*/
	public static AbstractLMNtalRuntime connectRuntime(String node) {
		node = node.intern();

		//あて先はどこ？
		if(LMNtalDaemonMessageProcessor.isMyself(node)){
			//localhostなら  自分自身を返す
			return Env.theRuntime;
		}
		
		//remote
		RemoteLMNtalRuntime ret = (RemoteLMNtalRuntime)runtimeids.get(node);			

		if (ret == null) {
			ret = new RemoteLMNtalRuntime(node);
			runtimeids.put(node,ret);
		}
		
		if (ret.connect()){
			//生きている
			return ret;			
		} else {
			//失敗したらnull
			return null;
		}
	}

	/** 登録されている全てのLMNtalRuntimeを終了し、計算ノード表の登録を削除する。
	 * TODO Env.theRuntime は terminate しなくてよいのかどうかを明らかにする */
	public static void terminateAll() {
		Iterator it = runtimeids.keySet().iterator();
		while (it.hasNext()) {
			AbstractLMNtalRuntime machine = (AbstractLMNtalRuntime)runtimeids.get(it.next());
			machine.terminate();
		}
		runtimeids.clear();
	}
}
