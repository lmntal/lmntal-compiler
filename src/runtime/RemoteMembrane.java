package runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

// このファイルは編集中です。

/**
 * リモートマシンクラス
 * @author n-kato
 */
final class RemoteMachine extends AbstractMachine {
	String cmdbuffer;
	int nextatomid;
	int nextmemid;
	RemoteMachine() {}
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
	protected String memid;
	protected HashMap atomids;
	public RemoteMembrane(AbstractMachine machine, RemoteMembrane parent, String memid) {
		super(machine,parent);
		this.memid = memid;
		atomids = new HashMap();
	}
	String getMemID() { return memid; }
	void send(String cmd) {
		((RemoteMachine)machine).send(cmd + " " + memid);
	}
	void send(String cmd, String args) {
		((RemoteMachine)machine).send(cmd + " " + memid + " " + args);
	}

	///////////////////////////////
	// 操作

	/** ロックが解放されたときに再活性化されるため、何もしなくてよい */
	void activate() {}
	void clearRules() {
		send("CLEARRULES");
		super.clearRules();
	}
	/** TODO Ruleset#getGlobalRulesetID()のようなメソッドを作る */
	void inheritRules(AbstractMembrane srcMem) {
		Iterator it = srcMem.rulesetIterator();
		while (it.hasNext()) {
			Ruleset rs = (Ruleset)it.next();
			send("LOADRULESET"/*,rs.getGlobalRulesetID()*/);
		}
		super.inheritRules(srcMem);
	}
	/** アトムの追加 */
	Atom newAtom(Functor functor) {
		Atom a = super.newAtom(functor);
		String atomid = ((RemoteMachine)machine).getNextAtomID();
		atomids.put(a,atomid);
		send("NEWATOM",atomid);
		return a;
	}
	/** 指定されたアトムを実行スタックに積む */
	protected void enqueueAtom(Atom atom) {
		send("ENQUEUEATOM",(String)atomids.get(atom));
	}
	/** 膜の追加 */
	AbstractMembrane newMem() {
		String newmemid = ((RemoteMachine)machine).getNextMemID();
		RemoteMembrane m = new RemoteMembrane(machine, this, newmemid);
		m.memid = newmemid;
		mems.add(m);
		send("NEWMEM",newmemid);
		return m;
	}
	
	/** dstMemに移動 
	 * TODO ルート膜とそれ以外で分ける */
	void moveTo(AbstractMembrane dstMem) {
		/*
		send("MOVETO");
		parent.removeMem(this);
		dstMem.addMem(this);
		parent = dstMem;
		enqueueAllAtoms();
		*/
	}
	void alterAtomFunctor(Atom atom, Functor func) {
		send("ALTERATONFUNCTOR",atomids.get(atom) + " " + func.toString());
		super.alterAtomFunctor(atom,func);
	}
	protected void enqueueAllAtoms() {
		send("ENQUEUEALLATOMS");
	}
	void removeAtom(Atom atom) {
		send("REMOVEATOM",(String)atomids.get(atom));		super.removeAtom(atom);
	}
	void removeAtoms(ArrayList atomlist) {
		Iterator it = atomlist.iterator();
		while (it.hasNext()) {
			Atom atom = (Atom)it.next();
			send("REMOVEATOM",(String)atomids.get(atom));
		}
		super.removeAtoms(atomlist);
	}

	void remove() {
		send("REMOVE");
		super.remove();
	}
	void pour(AbstractMembrane srcMem) {
		//send("POUR");
	}
	
	////////////////////////////////
	// ロック
	synchronized boolean lock(AbstractMembrane mem) {
		if (locked) {
			//todo:キューに記録
			// TODO locked==trueのとき、この計算ノードの誰がロックしたか分からないのを何とかする
			return false;
		} else {
			//todo:計算ノードの記録、キャッシュの更新
			locked = true;
			return false;
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
		send("RECURSIVEUNLOCK");
	}
	// リンクの操作	
	void newLink(Atom atom1, int pos1, Atom atom2, int pos2) {
		send("NEWLINK",""+atomids.get(atom1)+pos1+atomids.get(atom2)+pos2);
		super.newLink(atom1,pos1,atom2,pos2);
	}
	void relinkAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		send("RELINKATOMARGS",""+atomids.get(atom1)+pos1+atomids.get(atom2)+pos2);
		super.relinkAtomArgs(atom1,pos1,atom2,pos2);
	}
	void unifyAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		send("UNIFYATOMARGS",""+atomids.get(atom1)+pos1+atomids.get(atom2)+pos2);
		super.unifyAtomArgs(atom1,pos1,atom2,pos2);
	}
}