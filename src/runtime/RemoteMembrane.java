package runtime;

//import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.net.Socket;

/**
 * リモート計算ノード
 * @author n-kato
 */
final class RemoteLMNtalRuntime extends AbstractMachine {
	protected String runtimeid;
	protected Socket socket;
	protected RemoteLMNtalRuntime(String runtimeid) {
		this.runtimeid = runtimeid;
	}
	//
	static HashMap runtimeids = new HashMap();
	public static AbstractMachine connectRuntime(String node) {
		node = node.intern();
		AbstractMachine ret = (AbstractMachine)runtimeids.get(node);
		if (ret == null) {
			ret = new RemoteLMNtalRuntime(node);
			runtimeids.put(node,ret);
		}
		return ret;
	}
	public AbstractTask newTask() {
		// TODO コネクションの管理をRemoteTaskからこのクラスに移した後でsendを発行するコードを書く
		return (AbstractTask)null;
	}
}


/**
 * リモートタスククラス
 * TODO コネクションの管理はRemoteLMNtalRuntimeにまかせる。
 *       しかしnextatom(mem)idはsynchronizedにしなければならなくなる。
 * @author n-kato
 */
final class RemoteTask extends AbstractTask {
	String cmdbuffer;
	int nextatomid;
	int nextmemid;
	RemoteTask() {}
	String getNextAtomID() {
		return "NEW_" + nextatomid++;
	}
	String getNextMemID() {
		return "NEW_" + nextmemid++;
	}
	void send(String cmd) {
		cmdbuffer += cmd + "\n";
	}
	void flush() {
		System.out.println("SYSTEM ERROR: remote call not implemented");
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

	void clearRules() {
		send("CLEARRULES");
		super.clearRules();
	}
	/** TODO Ruleset#getGlobalRulesetID()のようなメソッドを作る。 */
	void inheritRules(AbstractMembrane srcMem) {
		Iterator it = srcMem.rulesetIterator();
		while (it.hasNext()) {
			Ruleset rs = (Ruleset)it.next();
			send("LOADRULESET"/*,rs.getGlobalRulesetID()*/);
		}
		super.inheritRules(srcMem);
	}
	/** ルールセットを追加 */
	void loadRuleset(Ruleset srcRuleset) {
		send("LOADRULESET"/*,srcRuleset.getGlobalRulesetID()*/);
		super.loadRuleset(srcRuleset);
	}
	
	// 操作2 - アトムの操作

	/** 新しいアトムを作成し、この膜に追加し、この膜の実行スタックに入れる。 */
	Atom newAtom(Functor functor) {
		Atom a = super.newAtom(functor);
		String atomid = ((RemoteTask)task).getNextAtomID();
		atomids.put(a,atomid);
		send("NEWATOM",atomid);
		return a;
	}
	/** 指定された子膜に新しいinside_proxyアトムを追加する */
	Atom newFreeLink(AbstractMembrane submem) {
		Atom a = submem.newAtom(Functor.INSIDE_PROXY);
		if (submem.task.getRuntime() != task.getRuntime()) {
			send("ADDFREELINK",submem.getAtomID(a));
		}
		return a;
	}
	void alterAtomFunctor(Atom atom, Functor func) {
		send("ALTERATOMFUNCTOR",getAtomID(atom),func.toString());
		super.alterAtomFunctor(atom,func);
	}
	void removeAtom(Atom atom) {
		send("REMOVEATOM",getAtomID(atom));
		super.removeAtom(atom);
	}
	/** 指定されたアトムをこの膜の実行スタックに積む */
	protected void enqueueAtom(Atom atom) {
		String atomid = getAtomID(atom);
		if (atomid != null) { // AbstractMembrane#addAtomからの呼び出しは無視する
			send("ENQUEUEATOM",atomid);
		}
	}
	// abstractメソッドではなくなった
	//protected void dequeueAtom(Atom atom) {
	//	send("DEQUEUEATOM",getAtomID(atom));
	//}
	/** リモートのpourで行われるため何もしなくてよい */
	protected void enqueueAllAtoms() {}

	// 操作3 - 子膜の操作
	
	/** 新しい子膜を作成する */
	AbstractMembrane newMem() {
		String newremoteid = ((RemoteTask)task).getNextMemID();
		RemoteMembrane m = new RemoteMembrane((RemoteTask)task, this, newremoteid);
		m.remoteid = newremoteid;
		addMem(m);
		send("NEWMEM",newremoteid);
		return m;
	}
	AbstractMembrane newRoot(AbstractMachine runtime) {
		// TODO 実装する
		return null;
	}

	void removeMem(AbstractMembrane mem) {
		send("REMOVEMEM",mem.getMemID());
		super.removeMem(mem);
	}

	// 操作4 - リンクの操作

	void newLink(Atom atom1, int pos1, Atom atom2, int pos2) {
		send("NEWLINK",""+getAtomID(atom1)+pos1+getAtomID(atom2)+pos2);
		super.newLink(atom1,pos1,atom2,pos2);
	}
	void relinkAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		send("RELINKATOMARGS",""+getAtomID(atom1)+pos1+getAtomID(atom2)+pos2);
		super.relinkAtomArgs(atom1,pos1,atom2,pos2);
	}
	void unifyAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		send("UNIFYATOMARGS",""+getAtomID(atom1)+pos1+getAtomID(atom2)+pos2);
		super.unifyAtomArgs(atom1,pos1,atom2,pos2);
	}

	// 操作5 - 膜自身や移動に関する操作
	
	/** ロックが解放されたときに再活性化されるため、何もしなくてよい */
	void activate() {}
	void remove() {
		send("REMOVE");
		super.remove();
	}

	void pour(AbstractMembrane srcMem) {
		if (srcMem.task.getRuntime() != task.getRuntime()) {
			throw new RuntimeException("cross-site remote process fusion not implemented");
		}
		send("POUR",srcMem.getMemID());
	}
	
	/** dstMemに移動 */
	void moveTo(AbstractMembrane dstMem) {
		if (dstMem.task.getRuntime() != task.getRuntime()) {
			throw new RuntimeException("cross-site remote process migration not implemented");
		}
		// remote call of a local process migration
		send("MOVETO",dstMem.getMemID());
		super.moveTo(dstMem);
	}
	
	// 操作6 - ロックに関する操作
	
	synchronized boolean lock(AbstractMembrane mem) {
		if (locked) {
			//todo:キューに記録
			//todo:locked==trueのとき、この計算ノードの誰がロックしたか分からないのを何とかする
			return false;
		} else {
			//todo:計算ノードの記録、キャッシュの更新
			locked = true;
			return true;
		}
	}
	boolean recursiveLock(AbstractMembrane mem) {
		//send("RECURSIVELOCK");
		return false;
	}
	void unlock() {
		send("UNLOCK");
	}
	void recursiveUnlock() {
		//send("RECURSIVEUNLOCK");
	}

}