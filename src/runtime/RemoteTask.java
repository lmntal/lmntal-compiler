package runtime;

import java.util.HashMap;

import daemon.LMNtalDaemon;
import daemon.LMNtalNode;

/**
 * リモートタスククラス
 * 済20040707 nakajima コネクションの管理はRemoteMachineにまかせる。
 *       しかしnextatom(mem)idはsynchronizedにしなければならなくなる。
 * @author n-kato
 */
final class RemoteTask extends AbstractTask {
	String cmdbuffer;
	int nextatomid;
	int nextmemid;
	LMNtalNode remoteNode;

	//
	HashMap memTable;

	/*
	 * コンストラクタ。
	 */
	RemoteTask(AbstractLMNtalRuntime runtime) {
		super(runtime);

		//runtimeはRemoteLMNtalRuntimeのはず
		remoteNode = ((RemoteLMNtalRuntime) runtime).lmnNode;
	}

	//	String getNextAtomID() {
	//		return "NEW_" + nextatomid++;
	//	}

	synchronized String getNextAtomID() {
		return "NEW_" + nextatomid++;
	}

	//	String getNextMemID() {
	//		return "NEW_" + nextmemid++;
	//	}

	synchronized String getNextMemID() {
		//LMNtalDaemon.getGlobalMembraneID(mem);

		return "NEW_" + nextmemid++;
	}

	/*	
	 * コマンドをリモート側へ送信する。実態はString cmdbufferにcmdと改行(\n)を加えているだけ。
	 * 
	 * @param cmd 送りたいコマンド
	 */
	void send(String cmd) {
		cmdbuffer += cmd + "\n";
	}

	synchronized void registerMem(String id, String mem) {
		memTable.put(id, mem);
	}
	
	/*  
	 * "NEW_1"のようなIDを渡すと、グローバルな膜IDを返す。
	 * 
	 * @param id "NEW_1"のようなString
	 * @return 膜ID。なかったらnull。
	 */
	String getRealMemName(String id) {
		return (String) memTable.get(id);
	}

	/*
	 * cmdbufferにたまった命令をリモート側へ送り、cmdbufferを空にする。
	 * 実際にはLMNtalDaemon.sendMessage()を呼んでいるだけ。
	 * 
	 * @throw RuntimeException LMntalDaemon.sendMessage()の返り値がfalseの時（つまり送信失敗時）
	 */
	synchronized void flush() {
		//TODO BEGINとENDをつける（ここでやるべきかLMNtalDaemonなど他の場所でやるべきか

		boolean result = LMNtalDaemon.sendMessage(remoteNode, cmdbuffer);

		if (result == true) {
			cmdbuffer = ""; //バッファを初期化
			nextatomid = 0;
			nextmemid = 0;
		} else {
			throw new RuntimeException("error in flush()");
		}
	}

	// ロック
	public void lock() {
		//TODO 実装
		throw new RuntimeException("not implemented");
	}
	public boolean unlock() {
		//TODO 実装

		//リモートのルート膜にunlock命令を送る
		//もう送られているのかどうか調べる

		//cmdbufferをflush()する
		flush();

		throw new RuntimeException("not implemented");
	}
}