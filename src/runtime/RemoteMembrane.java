package runtime;

//import java.util.HashMap;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;

import compile.RulesetCompiler;
import compile.parser.LMNParser;

import daemon.IDConverter;

/**
 * リモート膜クラス
 * @author n-kato
 */
public final class RemoteMembrane extends AbstractMembrane {
	/** この膜のグローバルIDまたはNEW_n
	 * （ルート膜や擬似膜でない場合、親膜の連続するロック取得期間中のみ有効）
	 * <p>Atomのremoteid に対応している */
	protected String globalid;
	
//	/** この膜のアトムからリモートIDまたはNEW_nへの写像 (Atom -> String) */
//	protected HashMap atomids = new HashMap();	// Atom.remoteid に移管
//	/** この膜の子膜からリモートIDへの写像 (AbstractMembrane -> String) */
//	protected HashMap memids = new HashMap();	// Membrane.globalid に移管

	/** 仮ロック状態かどうか。簡単のため、ルート膜のみが仮ロック状態になれるものとする。
	 * （リモートではロックされていると見なし、ローカルではロック解放されているとみなす状態のこと）*/
	private boolean fUnlockDeferred = false;
	
	////////////////////////////////////////////////////////////////
	
	/**
	 * コンストラクタ。globalidは初期化しない（困った話）
	 */
	public RemoteMembrane(RemoteTask task, AbstractMembrane parent) {
		super(task, parent);
	}
	/**
	 * コンストラクタ。remoteidは明示的に渡す。
	 */
	public RemoteMembrane(RemoteTask task, RemoteMembrane parent, String remoteid) {
		super(task, parent);
		this.globalid = remoteid;
	}
	/**
	 * 親膜を持たない膜を作成する。RemoteLMNtalRuntime.createPseudoMembrane から呼ばれる。
	 * @see RemoteLMNtalRuntime#createPseudoMembrane() */
	protected RemoteMembrane(RemoteTask task) {
		super(task, null);
	}
	
	///////////////////////////////
	// 情報の取得

	public String getGlobalMemID() {
		return globalid;
	}
	public String getAtomID(Atom atom) {
		//return (String) atomids.get(atom);
		return atom.remoteid;
	}

	///////////////////////////////
	// ボディ操作

	// ボディ操作1 - ルールの操作

	public void clearRules() {
		remote.send("CLEARRULES",this);
		super.clearRules();
	}

	public void copyRulesFrom(AbstractMembrane srcMem) {
		Iterator it = srcMem.rulesetIterator();
		while (it.hasNext()) {
			Ruleset rs = (Ruleset) it.next();
			remote.send("LOADRULESET", this, rs.getGlobalRulesetID());
		}
		super.copyRulesFrom(srcMem);
	}
	/** ルールセットを追加 */
	public void loadRuleset(Ruleset srcRuleset) {
		remote.send("LOADRULESET", this, srcRuleset.getGlobalRulesetID());
		super.loadRuleset(srcRuleset);
	}

	// ボディ操作2 - アトムの操作

	/** 新しいアトムを作成し、この膜に追加し、この膜の実行アトムスタックに入れる。 */
	public Atom newAtom(Functor func) {
		Atom atom = super.newAtom(func);
		String atomid = remote.generateNewID();
		//atomids.put(atom,atomid);
		atom.remoteid = atomid;
		remote.send("NEWATOM", atomid, this, func.serialize());
		if (func.equals(Functor.INSIDE_PROXY)) remote.registerAtom(atomid, atom);
		return atom;
	}
	public void alterAtomFunctor(Atom atom, Functor func) {
		remote.send("ALTERATOMFUNCTOR", this, getAtomID(atom) + " " + func.serialize());
		super.alterAtomFunctor(atom, func);
	}
	public void removeAtom(Atom atom) {
		remote.send("REMOVEATOM", this, getAtomID(atom));
		super.removeAtom(atom);
	}
	/** 指定されたアトムをこの膜の実行アトムスタックに積む */
	public void enqueueAtom(Atom atom) {
		String atomid = getAtomID(atom);
		if (atomid != null) { // AbstractMembrane#addAtomからの呼び出しは無視する
			remote.send("ENQUEUEATOM", this, atomid);
		}
	}
	/** リモートのmoveCellsFromで行われるため何もしなくてよい */
	public void enqueueAllAtoms() {
	}

	// ボディ操作3 - 子膜の操作

	/** 新しい子膜を作成する */
	public AbstractMembrane newMem() {
		String newglobalid = remote.generateNewID();
		RemoteMembrane submem = new RemoteMembrane((RemoteTask)task, this, newglobalid);
		//memids.put(submem.globalid, newglobalid);
		mems.add(submem);
		remote.send("NEWMEM", newglobalid, this);
		remote.registerMem(newglobalid, submem);
		return submem;
	}

	public void removeMem(AbstractMembrane mem) {
		remote.send("REMOVEMEM", this, mem.getGlobalMemID());
		super.removeMem(mem);
	}

	// ボディ操作4 - リンクの操作

	public void newLink(Atom atom1, int pos1, Atom atom2, int pos2) {
		remote.send("NEWLINK", this,
			getAtomID(atom1), pos1,
			getAtomID(atom2), pos2);
		super.newLink(atom1, pos1, atom2, pos2);
	}
	public void relinkAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		remote.send("RELINKATOMARGS", this,
			getAtomID(atom1), pos1,
			getAtomID(atom2), pos2);
		super.relinkAtomArgs(atom1, pos1, atom2, pos2);
	}
	public void inheritLink(Atom atom1, int pos1, Link link2) {
		remote.send("RELINKATOMARGS", this,
			getAtomID(atom1), pos1,
			getAtomID(link2.getAtom()), link2.getPos());
		super.inheritLink(atom1, pos1, link2);
	}
	public void unifyAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		remote.send("UNIFYATOMARGS", this,
			getAtomID(atom1), pos1,
			getAtomID(atom2), pos2);
		super.unifyAtomArgs(atom1, pos1, atom2, pos2);
	}
	public void unifyLinkBuddies(Link link1, Link link2) {
		remote.send("UNIFYATOMARGS", this,
			getAtomID(link1.getAtom()), link1.getPos(),
			getAtomID(link2.getAtom()), link2.getPos());
		super.unifyLinkBuddies(link1, link2);
	}

	// ボディ操作5 - 膜自身や移動に関する操作

	public void activate() {
		remote.send("ACTIVATE",this);
	}

	public void moveCellsFrom(AbstractMembrane srcMem) {
		//todo 実装
		
		if (srcMem.task.getMachine() != task.getMachine()) {
			throw new RuntimeException("cross-site remote process fusion not implemented");
		}
		remote.send("MOVECELLSFROM", this, srcMem.getGlobalMemID());
		remote.flush();
	}

	/** dstMemに移動 */
	public void moveTo(AbstractMembrane dstMem) {
		//todo 実装
		
		if (dstMem.task.getMachine() != task.getMachine()) {
			throw new RuntimeException("cross-site remote process migration not implemented");
		}
		// remote call of a local process migration
		remote.send("MOVETO", this, dstMem.getGlobalMemID());
		super.moveTo(dstMem);
	}

	// ロックに関する操作 - ガード命令は管理するtaskに直接転送される
	
	// - ガード命令
	
	synchronized public boolean lock() {
		if (locked) return false;
		if (fUnlockDeferred) {
			fUnlockDeferred = false;
		} else {
			if (!doLock("LOCK")) return false;
		}
		onLock(false);
		return true;
	}
	public boolean blockingLock() {
		//todo:locked==trueのとき、この計算ノードの誰がロックしたか分からないのを何とかする
		// [ロック解放要求の処理方法]
		// * 仮ロックされた本膜のロックを解放してもらうときに必要。
		// ルールスレッドがロックしていた場合、タスクのIDをリモートに渡す？【todo 本当に必要か？】
		// 非ルールスレッドがロックしていた場合、タスクは存在しないが、
		// 優先度無限大と見なすのでロックは解放できないことになっているので大丈夫。
		if (!doLock("BLOCKINGLOCK")) return false;
		onLock(false);
		return true;
	}
	/** 非同期的にロックする。ルート膜の親膜を活性化するときなどに使用される。
	 * 先祖膜のキャッシュは更新しない。*/
	public boolean asyncLock() {
		if (!doLock("ASYNCLOCK")) return false;
		onLock(true);
		return true;
	}
	public boolean recursiveLock() {
		return sendWait("RECURSIVELOCK");
	}
	
	// - ボディ命令
	
	public void unlock() {
		if (false && isRoot() && remote.cmdbuffer.length() == 0) {
			fUnlockDeferred = true;
		}
		else {
			remote.send("UNLOCK",this);
			onUnlock(false);
		}
	}
	public void forceUnlock() {
		remote.send("UNLOCK",this);
		onUnlock(false);
	}
	public void asyncUnlock() {
		remote.send("ASYNCUNLOCK",this);
		onUnlock(true);
	}
	public void recursiveUnlock() {
		remote.send("RECURSIVEUNLOCK",this);
	}

	///////////////////////////////	
	// RemoteMembrane で定義されるメソッド
	
	private boolean doLock(String cmd) {
		Object obj = sendWaitObject(cmd);
		if (obj instanceof byte[]) { // ロック取得成功
			updateCache((byte[])obj);
		}
		else if (obj instanceof String) {
			String response = (String)obj;
			if (response.equalsIgnoreCase("UNCHANGED")) {
				// ロック取得成功
			} else { // ロック取得失敗
				return false;
			}
		}
		return true;
	}

	private void onLock(boolean signal) {
		locked = true;
		if (signal || isRoot()) {
			if (signal || parent == null || parent.remote == null) {
				remote = (RemoteTask)task;
				remote.init();							// 命令ブロックの積み上げを開始する
			}
		} else {
			remote = parent.remote;
		}
	}
	private void onUnlock(boolean signal) {
		if (signal || isRoot()) {
			if (remote == task) remote.flush();	// 命令ブロックの積み上げを終了する
		}
		remote = null;
		locked = false;
	}
	/** キャッシュを更新する
	 * @see Membrane#cache()
	 * author mizuno */
	protected void updateCache(byte[] data) {
		//
		//前処理
		//

		//前のキャッシュをクリア
		//全部削除しなくても良いのかもしれないが、とりあえずは全部削除する。
		mems.clear();
		rulesets.clear();
		//INSIDE_PROXYだけとっておく
		HashMap proxyMap = new HashMap();
		Iterator it = atomIteratorOfFunctor(Functor.INSIDE_PROXY);
		while (it.hasNext()) {
			Atom a = (Atom)it.next();
			proxyMap.put(a.remoteid, a);
		}
		atoms.clear();

		//
		try {
			ByteArrayInputStream bin = new ByteArrayInputStream(data);
			ObjectInputStream in = new ObjectInputStream(bin);
			
			//子膜
			int n = in.readInt();
			for (int i = 0; i < n; i++) {
				String hostname = (String)in.readObject();
				String memid = (String)in.readObject();
				boolean isRoot = ((Boolean)in.readObject()).booleanValue();
				//
				RemoteLMNtalRuntime r;
				r = (RemoteLMNtalRuntime)LMNtalRuntimeManager.runtimeids.get(hostname);
				if (r == null) {
					AbstractLMNtalRuntime ar;
					ar = (AbstractLMNtalRuntime)LMNtalRuntimeManager.connectRuntime(hostname);
					if (ar instanceof LocalLMNtalRuntime) {
						AbstractMembrane am = IDConverter.lookupGlobalMembrane(hostname + ":" + memid);
						if (am == null) { // 起こらないと思うけどどうだろう
							throw new RuntimeException("SYSTEM ERROR: unknown local membrane id");
						}
						else {
							addMem(am);
						}							
						continue;
					}
					r = (RemoteLMNtalRuntime)ar;
				}
				//
				RemoteTask t;
				RemoteMembrane m;
				if (isRoot) {
					t = new RemoteTask(r, this);
					m = (RemoteMembrane)t.getRoot();
					m.globalid = memid;
				}
				else {
					t = (RemoteTask)getTask();
					m = new RemoteMembrane(t, this, memid);
					addMem(m);
				}
				// GlobalMemIDの正しい作成方法は？→さっさとメソッドにした方がいいですね
				String globalid = hostname + ":" + memid;
				IDConverter.registerGlobalMembrane(globalid, m);
			}

			//アトム
			n = in.readInt();
			for (int i = 0; i < n; i++) {
				Atom a = (Atom)in.readObject();
				Functor f = a.getFunctor();
				if (f.equals(Functor.INSIDE_PROXY)) {
					//すでにある物と置き換える
					Atom a2 = (Atom)proxyMap.get(a.remoteid);
					//relinkAtomArgsを使うとメッセージが送信されてしまうので、直接書き換える。
					a2.args[1] = a.args[1];
					a2.args[1].getBuddy().set(a2, 1);
					//同上
					a2.mem = this;
					atoms.add(a2);
				} else {
					a.mem = this;
					atoms.add(a);
				}
			}
			
			//ルールセット
			n = in.readInt();
			for (int i = 0; i < n; i++) {
				String id = (String)in.readObject();
				Ruleset r = IDConverter.lookupRuleset(id);
				//todo これはIDConverterの中でやる事？→たぶん外でやった方がよいと思うけど根拠はない
				if (r == null) {
					r = (Ruleset)sendWaitObject("REQUIRERULESET " + id);					
				}
				rulesets.add(r);
			}
			
			//
			stable = ((Boolean)in.readObject()).booleanValue();

		} catch (IOException e) {
			//ByteArrayOutputStreamなので、発生するはずがない
			throw new RuntimeException("Unwxpected Exception", e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unwxpected Exception", e);
		}
	}

	///////////////////////////////
	// 送信用
	
	/** メッセージを直接送信し、返答を待ってブロックする。（仮）*/
	boolean sendWait(String cmd) {
		String host = task.runtime.hostname;
		String msg = cmd + " " + getGlobalMemID();
		return LMNtalRuntimeManager.daemon.sendWait(host,msg);
	}
	/** メッセージを直接送信し、返答を待ってブロックする。（仮）*/
	String sendWaitText(String cmd) {
		String host = task.runtime.hostname;
		String msg = cmd + " " + getGlobalMemID();
		return LMNtalRuntimeManager.daemon.sendWaitText(host,msg);
	}
	/** メッセージを直接送信し、返答を待ってブロックする。（仮）*/
	Object sendWaitObject(String cmd) {
		String host = task.runtime.hostname;
		String msg = cmd + " " + getGlobalMemID();
		return LMNtalRuntimeManager.daemon.sendWaitObject(host,msg);
	}
	
	/**
	 * キャッシュテスト
	 */
	public static void main(String[] args) throws Exception {
		LMNParser lp = new LMNParser(new InputStreamReader(System.in));
		compile.structure.Membrane m = lp.parse();
		Ruleset rs = RulesetCompiler.compileMembrane(m, "Test");
//		Inline.makeCode();
//		((InterpretedRuleset)rs).showDetail();
//		m.showAllRules();
		
		// 実行
		MasterLMNtalRuntime rt = new MasterLMNtalRuntime();
		LMNtalRuntimeManager.init();

		Membrane rootMem = rt.getGlobalRoot();
		rs.react(rootMem);
		System.out.println("src:");
		System.out.println(rootMem);
		
		RemoteMembrane remote = new RemoteMembrane(null);

		byte[] data = rootMem.cache();
		remote.updateCache(data);
		
		Iterator it = rootMem.memIterator();
		if (it.hasNext()) {
			Membrane src = (Membrane)it.next();
			data = src.cache();
			Iterator it2 = remote.memIterator();
			
			RemoteMembrane dst = (RemoteMembrane)it2.next();
			dst.updateCache(data);
		}
		
		System.out.println("dst : ");
		System.out.println(remote);
	}
	
}