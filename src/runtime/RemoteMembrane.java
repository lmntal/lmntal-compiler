package runtime;

//import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.net.Socket;

/**
 * リモート計算ノード
 * @author n-kato
 */
final class RemoteMachine extends AbstractMachine {
	protected String runtimeid;
	protected Socket socket;
	protected RemoteMachine(String runtimeid) {
		this.runtimeid = runtimeid;
	}
	//
	static HashMap runtimeids = new HashMap();
	public static AbstractMachine connectRuntime(String node) {
		node = node.intern();
		AbstractMachine ret = (AbstractMachine)runtimeids.get(node);
		if (ret == null) {
			ret = new RemoteMachine(node);
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
 * TODO コネクションの管理はRemoteMachineにまかせる。
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

	public void clearRules() {
		send("CLEARRULES");
		super.clearRules();
	}
	/** TODO Ruleset#getGlobalRulesetID()のようなメソッドを作る。 */
	public void copyRulesFrom(AbstractMembrane srcMem) {
		Iterator it = srcMem.rulesetIterator();
		while (it.hasNext()) {
			Ruleset rs = (Ruleset)it.next();
			send("LOADRULESET"/*,rs.getGlobalRulesetID()*/);
		}
		super.copyRulesFrom(srcMem);
	}
	/** ルールセットを追加 */
	public void loadRuleset(Ruleset srcRuleset) {
		send("LOADRULESET"/*,srcRuleset.getGlobalRulesetID()*/);
		super.loadRuleset(srcRuleset);
	}
	
	// 操作2 - アトムの操作

	/** 新しいアトムを作成し、この膜に追加し、この膜の実行スタックに入れる。 */
	public Atom newAtom(Functor functor) {
		Atom a = super.newAtom(functor);
		String atomid = ((RemoteTask)task).getNextAtomID();
		atomids.put(a,atomid);
		send("NEWATOM",atomid);
		return a;
	}
	public void alterAtomFunctor(Atom atom, Functor func) {
		send("ALTERATOMFUNCTOR",getAtomID(atom),func.toString());
		super.alterAtomFunctor(atom,func);
	}
	public void removeAtom(Atom atom) {
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
	/** リモートのmoveCellsFromで行われるため何もしなくてよい */
	protected void enqueueAllAtoms() {}

	// 操作3 - 子膜の操作
	
	/** 新しい子膜を作成する */
	public AbstractMembrane newMem() {
		String newremoteid = ((RemoteTask)task).getNextMemID();
		RemoteMembrane m = new RemoteMembrane((RemoteTask)task, this, newremoteid);
		m.remoteid = newremoteid;
		mems.add(m);
		send("NEWMEM",newremoteid);
		return m;
	}
	public AbstractMembrane newRoot(AbstractMachine runtime) {
		// TODO 実装する
		return null;
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
//	public void unifyAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
//		send("UNIFYATOMARGS",""+getAtomID(atom1)+pos1+getAtomID(atom2)+pos2);
//		super.unifyAtomArgs(atom1,pos1,atom2,pos2);
//	}

	// 操作5 - 膜自身や移動に関する操作
	
	public void activate() {
		send("ACTIVATE");
	}
	public void remove() {
		send("REMOVE");
		super.remove();
	}

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
	
	synchronized public boolean lock(AbstractMembrane mem) {
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
	public boolean recursiveLock(AbstractMembrane mem) {
		//send("RECURSIVELOCK");
		return false;
	}
	public void unlock() {
		send("UNLOCK");
	}
	public void recursiveUnlock() {
		//send("RECURSIVEUNLOCK");
	}

}