package runtime;

import java.util.HashMap;

import daemon.IDConverter;

/**
 * リモートタスククラス
 * <p>命令ブロックを管理する。
 * @author n-kato
 */
public final class RemoteTask extends AbstractTask {
	// 送信用
	String cmdbuffer;	// 命令ブロック送信用バッファ
	int nextid;		// 次のNEW_変数番号
	
//	// 受信用
//	HashMap memTable;

	/** 通常のコンストラクタ。
	 * <p>指定した親膜を持つ新しいロックされたリモートのルート膜および対応するリモートタスクを作成する
	 * @param runtime 作成したタスクを実行するランタイムに対応するリモートランタイム
	 * @param parent 親膜 */
	RemoteTask(RemoteLMNtalRuntime runtime, AbstractMembrane parent){
		super(runtime);
		root = new RemoteMembrane(this, parent);
		root.locked = true;
		root.remote = parent.remote;
		parent.addMem(root);	// タスクは膜の作成時に設定した
	}
	/** 擬似タスク作成用のコンストラクタ（擬似タスクはroot=null）
	 * @see RemoteLMNtalRuntime#createPseudoMembrane() */
	RemoteTask(RemoteLMNtalRuntime runtime) {
		super(runtime);
	}

	//
	
	/** 親膜の無い膜を作成し、このタスクが管理する膜にする。 */
	public RemoteMembrane createFreeMembrane() {
		return new RemoteMembrane(this);
	}


	///////////////////////////////
	// 送信用

	String generateNewID() {
		return "NEW_" + nextid++;
	}

	/** 命令ブロック送信用バッファおよびNEW_変換表を初期化する */
	public void init() {
		cmdbuffer = "";
		nextid = 0;
		memTable.clear();
		atomTable.clear();
	}

	/**	命令ブロック送信用バッファにボディ命令を追加する */
	public void send(String cmd) {
		cmdbuffer += cmd + "\n";
	}
	
	void send(String cmd, AbstractMembrane mem) {
		cmdbuffer += cmd + " " + mem.getGlobalMemID() + "\n"; 
	}
	void send(String cmd, AbstractMembrane mem, String args) {
		cmdbuffer += cmd + " " + mem.getGlobalMemID() + " " + args + "\n"; 
	}
	void send(String cmd, AbstractMembrane mem,
			String arg1, int arg2, String arg3, int arg4) {
		cmdbuffer += cmd + " " + mem.getGlobalMemID() + " "
			+ arg1 + " " + arg2 + " " + arg3 + " " + arg4 + "\n"; 
	}
	void send(String cmd, String outarg, AbstractMembrane mem) {
		cmdbuffer += cmd + " " + mem.getGlobalMemID() + " " + outarg + "\n"; 
	}
	void send(String cmd, String outarg, AbstractMembrane mem, String args) {
		cmdbuffer += cmd + " " + mem.getGlobalMemID() + " " + outarg + " " + args + "\n"; 
	}

	/**
	 * 命令ブロック送信用バッファの内容をリモートに送信し、バッファを初期化する
	 * <p>
	 * @throws RuntimeException 通信失敗（fatal）
	 */
	public void flush() {
		if (cmdbuffer.equals("")) return;
		String cmd = "BEGIN\n" + cmdbuffer + "END";
		String result = LMNtalRuntimeManager.daemon.sendWaitText(runtime.hostname, cmd);

		if (result.length() >= 4 && result.substring(0, 4).equalsIgnoreCase("FAIL")) {
			throw new RuntimeException("RemoteTask.flush: failure");
		}
		// BEGINメッセージに対する返答を解釈する
		String[] binds = result.split(";");
		for (int i = 0; i < binds.length; i++) {
			String[] args = binds[i].split("=",2);
			String tmpid = args[0];
			String newid = args[1];
			// todo もう少し拡張性の高い識別方法を考える。おそらくNEW_を分化させればよいはず。
			if (newid.indexOf(':') >= 0) {
				RemoteMembrane mem = (RemoteMembrane)memTable.get(tmpid);
				if (mem != null) IDConverter.registerGlobalMembrane(newid, mem);
			}
			else {
				Atom atom = (Atom)atomTable.get(tmpid);
				if (atom != null) atom.remoteid = newid;
			}
		}
		//
		cmdbuffer = "";	// nextidは初期化しない
	}

	///////////////////////////////
	// NEW_ （説明はいずれ）
	
	/** NEW_memid (String) -> AbstractMembrane */
	HashMap memTable = new HashMap();
	/** NEW_atomid (String) -> Atom */
	HashMap atomTable = new HashMap();

	void registerMem(String memid, RemoteMembrane mem) {
		memTable.put(memid, mem);
	}
	void registerAtom(String atomid, Atom atom) {
		atomTable.put(atomid, atom);
	}
	
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