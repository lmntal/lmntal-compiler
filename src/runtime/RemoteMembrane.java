package runtime;

import java.util.HashMap;
import java.util.Iterator;

import daemon.LMNtalDaemon;
import daemon.LMNtalNode;

/**
 * リモート計算ノード
 * 
 * 手元にあって、リモート側（ネットワークの向こう側）の代理人として存在する。
 * やってる事は命令をリモートへ転送する役目。
 * 
 * @author n-kato
 * 
 */
final class RemoteLMNtalRuntime extends AbstractLMNtalRuntime{
	boolean result;
	
	/*
	 * リモート側のホスト名。Fully Qualified Domain Nameである必要がある。
	 */
	protected String hostname;
	/*
	 * hostnameに対応するLMNtalNode。実際はLMNtalDaemon.getLMNtalNodeFromFQDN()でとってきてるだけ。
	 */
	protected LMNtalNode lmnNode; 
	
/*
 * コンストラクタ
 * 
 * @param hostname つなげたいホストのホスト名。Fully Qualified Domain Nameである必要がある。
 */	
	protected RemoteLMNtalRuntime(String hostname) {
		//runtimeidの中にはfqdnが入っている（とみなす）

		this.hostname = hostname;
	}

	public AbstractTask newTask() {
		// todo 下と同じ
		return (AbstractTask)null;
	}
	
	/*
	 * タスクを作る命令を発行する。
	 * 実際にタスクが作られるのはリモート側。
	 * 
	 * @param AbstractMembrane 親膜
	 * @return AbstractTask
	 */
	public AbstractTask newTask(AbstractMembrane parent) {
		// TODO コネクションの管理をRemoteTaskからこのクラスに移した後でsendを発行するコードを書く

		return (AbstractTask)null;
	}
	
	/*
	 * TERMINATEを発行。
	 */
	public void terminate() {
		//TODO 実装@LMNtalDaemon(or MessageProcessor
		//send("TERMINATE");
	}
	
	/*
	 * AWAKEを発行
	 */
	public void awake() {
		//TODO 実装
		//send("AWAKE");
	}

/*
 * リモート側に接続する。
 * 実際はLMNtalDaemon.connet(hostname)を呼出しているだけ。
 * 
 * @return 接続成功したらtrue、失敗したらfalse。接続の成功判定はLMNtalDaemon.connect()が返すbooleanと、
 */	
	public boolean connect(){
		//TODO 単体テスト
		result = LMNtalDaemon.connect(hostname);
		lmnNode = LMNtalDaemon.getLMNtalNodeFromFQDN(hostname);
		if(lmnNode != null && result == true){
			return true;
		} else {
			return false;
		}
	}
	

}

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

	HashMap memIDTable;

	/*
	 * コンストラクタ。
	 */
	RemoteTask(AbstractLMNtalRuntime runtime) {
		 super(runtime);
		 
		 //runtimeはRemoteLMNtalRuntimeのはず
		 remoteNode = ((RemoteLMNtalRuntime)runtime).lmnNode;
	}

//	String getNextAtomID() {
//		return "NEW_" + nextatomid++;
//	}
	
	synchronized String getNextAtomID(){
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


	synchronized void registerMem(String id, String mem){
		memIDTable.put(id, mem);
	}
	/*  
	 * "NEW_1"のようなIDを渡すと、グローバルな膜IDを返す。
	 * 
	 * @param id "NEW_1"のようなString
	 * @return 膜ID。なかったらnull。
	 */
	String getRealMemName(String id){
		return (String)memIDTable.get(id);
	}

	/*
	 * cmdbufferにたまった命令をリモート側へ送り、cmdbufferを空にする。
	 * 実際にはLMNtalDaemon.sendMessage()を呼んでいるだけ。
	 * 
	 * @throw RuntimeException LMntalDaemon.sendMessage()の返り値がfalseの時（つまり送信失敗時）
	 */
	synchronized void flush() {
		//TODO BEGINとENDをつける（ここでやるべきかLMNtalDaemonなど他の場所でやるべきか
		
		boolean result = LMNtalDaemon.sendMessage(remoteNode,cmdbuffer);

		if(result == true){
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

/**
 * リモート膜クラス
 * @author n-kato
 */
final class RemoteMembrane extends AbstractMembrane {
	/** この膜を管理する計算ノードにおけるこの膜のID */
	protected String remoteid;
	/** この膜のアトムのローカルIDからリモートIDへの写像 */
	protected HashMap atomids = new HashMap();
	/** この膜の子膜のローカルIDからリモートIDへの写像 */
	protected HashMap memids = new HashMap();
	
	/** 仮ロック状態かどうか。
	 * （リモートではロックされていると見なし、ローカルではロック解放されているとみなす状態のこと）*/
	protected boolean fUnlockDeferred = false;
	
	/*
	 * コンストラクタ。remoteidはLMNtalDaemon.getGlobalMembraneIDによって生成される。
	 */
	public RemoteMembrane(RemoteTask task, RemoteMembrane parent){
		super(task,parent);
		this.remoteid = LMNtalDaemon.getGlobalMembraneID(this);
	}
	
	/*
	 * コンストラクタ。remoteidは明示的に渡す。
	 */
	public RemoteMembrane(RemoteTask task, RemoteMembrane parent, String remoteid) {
		super(task,parent);
		this.remoteid = remoteid;
	}
	
	void send(String cmd) {
		((RemoteTask)task).send(cmd + " " + remoteid);
	}
	void send(String cmd, String args) {
		((RemoteTask)task).send(cmd + " " + remoteid + " " + args);
	}
	void send(String cmd, String arg1, String arg2) {
		((RemoteTask)task).send(cmd + " " + remoteid + " " + arg1 + " " + arg2);
	}
	void send(String cmd, String arg1, String arg2, String arg3, String arg4) {
		((RemoteTask)task).send(cmd + " " + remoteid + " " + arg1 + " " + arg2
													 + " " + arg3 + " " + arg4);
	}
	///////////////////////////////
	// 情報の取得
	
	String getMemID() { return remoteid; }
	String getAtomID(Atom atom) { return (String)atomids.get(atom); }

	///////////////////////////////
	// 操作

	// 操作1 - ルールの操作

	public void clearRules() {
		send("CLEARRULES");
		super.clearRules();
	}
	
	public void copyRulesFrom(AbstractMembrane srcMem) {
		Iterator it = srcMem.rulesetIterator();
		while (it.hasNext()) {
			Ruleset rs = (Ruleset)it.next();
			send("LOADRULESET",rs.getGlobalRulesetID());
		}
		super.copyRulesFrom(srcMem);
	}
	/** ルールセットを追加 */
	public void loadRuleset(Ruleset srcRuleset) {
		send("LOADRULESET",srcRuleset.getGlobalRulesetID());
		super.loadRuleset(srcRuleset);
	}
	
	// 操作2 - アトムの操作

	/** 新しいアトムを作成し、この膜に追加し、この膜の実行アトムスタックに入れる。 */
	public Atom newAtom(Functor functor) {
		Atom a = super.newAtom(functor);
		String atomid = ((RemoteTask)task).getNextAtomID(); //NEW_1とかが入ってしまう
		atomids.put(a,atomid);
		send("NEWATOM",atomid);
		return a;
	}
	public void alterAtomFunctor(Atom atom, Functor func) {
		send("ALTERATOMFUNCTOR",getAtomID(atom),func.getName()); // getNameでは、正確な転送は期待できない
		super.alterAtomFunctor(atom,func);
	}
	public void removeAtom(Atom atom) {
		send("REMOVEATOM",getAtomID(atom));
		super.removeAtom(atom);
	}
	/** 指定されたアトムをこの膜の実行アトムスタックに積む */
	public void enqueueAtom(Atom atom) {
		// TODO リモートのアトムを積む場合があるが、実装可能かどうか調べる
		//String atomid = getAtomID(atom);
		//if (atomid != null) { // AbstractMembrane#addAtomからの呼び出しは無視する
		//	send("ENQUEUEATOM",atomid);
		//}
	}
	/** リモートのmoveCellsFromで行われるため何もしなくてよい */
	public void enqueueAllAtoms() {}

	// 操作3 - 子膜の操作
	
	/** 新しい子膜を作成する */
	public AbstractMembrane newMem() {
		//String newremoteid = ((RemoteTask)task).getNextMemID();
		//RemoteMembrane m = new RemoteMembrane((RemoteTask)task, this, newremoteid);
		//m.remoteid = newremoteid;
		//mems.add(m);
		//send("NEWMEM",newremoteid);
		//return m;
		
		String newremoteid = ((RemoteTask)task).getNextMemID();
		RemoteMembrane m = new RemoteMembrane((RemoteTask)task, this);
		memids.put(m.remoteid, newremoteid);
		mems.add(m);
		send("NEWMEM", newremoteid);
		((RemoteTask)task).registerMem(newremoteid, m.remoteid);
				
		return m;
	}
	
	public AbstractMembrane newRoot(AbstractLMNtalRuntime runtime) {
		// TODO 実装する
		
		//RemoteTaskを作る
		RemoteTask newtask = new RemoteTask(runtime);
						
		//リモートで膜を作る命令を発行する
		send("NEWROOT",""+ remoteid ); //引数はこれでいいのかな
		
		//RemoteTask.rootを返す		
		return newtask.getRoot();
	}

	public void removeMem(AbstractMembrane mem) {
		send("REMOVEMEM",mem.getMemID());
		super.removeMem(mem);
	}

	// 操作4 - リンクの操作

	public void newLink(Atom atom1, int pos1, Atom atom2, int pos2) {
		send("NEWLINK",""+getAtomID(atom1)+" "+pos1+" "+getAtomID(atom2)+" "+pos2);
		super.newLink(atom1,pos1,atom2,pos2);
	}
	public void relinkAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		send("RELINKATOMARGS",""+getAtomID(atom1)+" "+pos1+" "+getAtomID(atom2)+" "+pos2);
		super.relinkAtomArgs(atom1,pos1,atom2,pos2);
	}
	public void inheritLink(Atom atom1, int pos1, Link link2) {
		send("RELINKATOMARGS",""+getAtomID(atom1)+" "+pos1+" "
			+getAtomID(link2.getAtom())+" "+link2.getPos());
		super.inheritLink(atom1,pos1,link2);
	}
	public void unifyAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		send("UNIFYATOMARGS",""+getAtomID(atom1)+" "+pos1+" "+getAtomID(atom2)+" "+pos2);
		super.unifyAtomArgs(atom1,pos1,atom2,pos2);
	}
	public void unifyLinkBuddies(Link link1, Link link2) {
		send("NEWLINK",""+getAtomID(link1.getAtom())+" "+link1.getPos()+" "
						+getAtomID(link2.getAtom())+" "+link2.getPos());
		super.unifyLinkBuddies(link1,link2);
	}

	// 操作5 - 膜自身や移動に関する操作
	
	public void activate() {
		send("ACTIVATE");
	}
//	public void remove() {
//		send("REMOVE");
//		super.remove();
//	}

	public void moveCellsFrom(AbstractMembrane srcMem) {
		if (srcMem.task.getMachine() != task.getMachine()) {
			throw new RuntimeException("cross-site remote process fusion not implemented");
		}
		send("POUR",srcMem.getMemID());
	}
	
	/** dstMemに移動 */
	public void moveTo(AbstractMembrane dstMem) {
		if (dstMem.task.getMachine() != task.getMachine()) {
			throw new RuntimeException("cross-site remote process migration not implemented");
		}
		// remote call of a local process migration
		send("MOVETO",dstMem.getMemID());
		super.moveTo(dstMem);
	}
	
	// 操作6 - ロックに関する操作
	
	synchronized public boolean lock() {
		if (locked) {
			return false;
		} else {
			if (fUnlockDeferred) {
				locked = true;
				fUnlockDeferred = false;	// ?
			}
			else {
				send("LOCK");
				//wait();
				if (true) { // ロック取得成功
					//todo:キャッシュの更新
				}
				else { // ロック取得失敗
					return false;
				}
			}
			locked = true;
			return true;
		}
	}
	public void blockingLock() {
		//todo:locked==trueのとき、この計算ノードの誰がロックしたか分からないのを何とかする
		// [ロック解放要求の処理方法]
		// * 仮ロックされた本膜のロックを解放してもらうときに必要。
		// ルールスレッドがロックしていた場合、タスクのIDをリモートに渡す？【TODO 本当に必要か？】
		// 非ルールスレッドがロックしていた場合、タスクは存在しないが、
		// 優先度無限大と見なすのでロックは解放できないことになっているので大丈夫。
		send("BLOCKINGLOCK");
		//wait;
		// todo:キャッシュの更新
	}
	public void asyncLock() {
		send("ASYNCLOCK");
	}
	public void unlock(boolean signal) {
		fUnlockDeferred = true;
		//send("");
	}
	public void forceUnlock() {
		send("UNLOCK",""+false); // あとでよく考えること
	}
	public void asyncUnlock() {
		send("ASYNCUNLOCK");
	}
	public void recursiveLock() {
		send("RECURSIVELOCK");
	}
	public void recursiveUnlock() {
		send("RECURSIVEUNLOCK");
	}
}