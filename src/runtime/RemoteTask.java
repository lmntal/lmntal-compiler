package runtime;

//import java.util.HashMap;

/**
 * リモートタスククラス
 * <p>命令ブロックを管理する。
 * <p>
 * 済20040707 nakajima コネクションの管理はRemoteMachineにまかせる。
 * @author n-kato
 */
final class RemoteTask extends AbstractTask {
	// 送信用
	String cmdbuffer;	// 命令ブロック送信用バッファ
	int nextid;		// 次のNEW_変数番号
	
//	// 受信用
//	HashMap memTable;

	/** 通常のコンストラクタ */
	RemoteTask(RemoteLMNtalRuntime runtime, AbstractMembrane parent){
		super(runtime);
		root = new RemoteMembrane(this, parent);
		parent.addMem(root);	// タスクは膜の作成時に設定した
	}

	///////////////////////////////
	// 送信用

	String getNextAtomID() {
		return "NEW_" + nextid++;
	}
	String getNextMemID() {
		return "NEW_" + nextid++;
	}

	/** 命令ブロック送信用バッファを初期化する */
	void init() {
		cmdbuffer = "";
		nextid = 0;
	}

	/**	命令ブロック送信用バッファにボディ命令を追加する */
	void send(String cmd) {
		cmdbuffer += cmd + "\n";
	}
	
	void send(String cmd, AbstractMembrane mem) {
		cmdbuffer += cmd + " " + mem.getGlobalMemID();
	}
	void send(String cmd, AbstractMembrane mem, String args) {
		cmdbuffer += cmd + " " + mem.getGlobalMemID() + " " + args;
	}
	void send(String cmd, AbstractMembrane mem,
			String arg1, int arg2, String arg3, int arg4) {
		cmdbuffer += cmd + " " + mem.getGlobalMemID() + " "
			+ arg1 + " " + arg2 + " " + arg3 + " " + arg4;
	}
	void send(String cmd, String outarg, AbstractMembrane mem) {
		cmdbuffer += cmd + " " + outarg + " " + mem.getGlobalMemID();
	}
	void send(String cmd, String outarg, AbstractMembrane mem, String args) {
		cmdbuffer += cmd + " " + outarg + " " + mem.getGlobalMemID() + " " + args;
	}

	/**
	 * 命令ブロック送信用バッファの内容をリモートに送信する
	 * <p>
	 * (n-kato) synchronizedはなぜ付いているのか？
	 * @throws RuntimeException 通信失敗（fatal）
	 */
	synchronized void flush() {
		String cmd = "BEGIN\n" + cmdbuffer + "END";
		boolean result = LMNtalRuntimeManager.daemon.sendWait(runtime.hostname, cmd);
		if (!result) {
			throw new RuntimeException("error in flush()");
		}
	}

	// ロック
	// nakajima20040719: RemoteMembraneにあるのでいらないような気がする
	// n-kato  20040815: ルート膜のロックを取得/解放するときのフックを考えていたのだと思う

	///////////////////////////////
	// 受信用

//	synchronized void registerMem(String id, String mem) {
//		memTable.put(id, mem);
//	}
	
//	/*  
//	 * "NEW_1"のようなIDを渡すと、グローバルな膜IDを返す。
//	 * 
//	 * @param id "NEW_1"のようなString
//	 * @return 膜ID。なかったらnull。
//	 */
//	String getRealMemName(String id) {
//		return (String) memTable.get(id);
//	}
}