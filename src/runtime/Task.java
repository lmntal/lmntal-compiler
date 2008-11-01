package runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import debug.Debug;

//import unyo.Mediator;
import util.Stack;
import util.Util;

/** タスク
 * todo タスクに優先度を設定する。これはルールスレッドによるロックがブロッキングになるかどうかの決定に影響させる。
 * 非同期実行はルールスレッドよりも高い優先度を与える。
 * todo ルート膜が先祖タスクまたは自タスクのルールスレッドによってロックされたかどうかを記憶する。
 * これにより、先祖タスクのルールスレッドを停止して動作する非同期実行が可能となる。
 * <p>
 * <font size=+1><b>方法7</b></font><br>
 * 
 * <p>
 * <b>不変条件</b>
 * (常に成立されてなければならない制約)
 * <p>
 * ・実行膜スタックの不変条件
 * 　ある膜M(ルート膜でない膜)の親膜は以下の条件が満たされている。
 * 　１．ある膜Mが実行膜スタックに積まれているとき、
 * 　　(1)．実行膜スタックの、Mより底の方に積まれている
 * 　　(2)．仮の実行膜スタックに積まれている
 * 　　(3)．ロックされている
 * 　２．ある膜が仮の実行膜スタックに積まれているとき
 * 　　(1)．仮の実行膜スタックの、Mより底の方に積まれている
 * 　この不変条件は、子膜を親膜より優先して実行させるためにある。
 * <p>
 * ・膜のロックに関する不変条件
 * 　膜がデッドロックしないために、以下の条件が満たされている。
 * 　１．膜のロックを取得しようとするスレッドは、どの膜のロックも取得していないか、
 * 　　　またはロックを取得しようとする膜の親膜のロックを取得していなければならない。
 * 　２．ルールスレッドが最初にロックを取得する膜は，自タスクが管理する膜でなければならない．
 * 　３. 非ルールスレッドが最初にロックを取得する膜は，ルート膜でなければならない．
 * 　膜のロック要求が短時間で受け入れさせるために、以下の条件が満たされている。
 * 　４．ルールスレッドは，ロック取得要求を受け取ったら速やかに全てのロックを解放しなければならない．
 * 　５．非ルールスレッドは，取得したロックを短期間で解放しなければならない．
 * <p>
 *
 * <p>
 * <b>ルールスレッド</b>
 * <p>
 * ルール適用を行うためのスレッドをルールスレッドと呼ぶ。
 * タスクはちょうど1つのルールスレッドによって実行される。
 * 物理マシンごとに複数のタスクを生成することができる。
 * 各タスクは固有の論理スレッドを持つようになっている。
 * <p>
 * 各スレッドは、ルールスレッドに対して停止要求を発行し解除することができる。
 * ルールスレッドは、停止要求中のスレッドが存在するときはルール適用を行わない。
 * ルールスレッドは、停止要求を検知すると直ちにルール適用を中止し、自分自身にシグナルを発行する。
 * <p>
 * 現状では、ルールスレッドは、膜のロック取得をノンブロッキングで行う。
 * このため、膜がすでにロックされていた場合、その膜に対するルール適用を行わない。
 * これは、タスクに優先度を設けることによりブロッキングでの取得を可能にすることにより解決すべきである。
 * 
 * <p>
 * <b>ルールスレッドでないスレッド</b>
 * <p>
 * ルールスレッドでないスレッドは、膜のロック取得をブロッキングで行う。
 * 膜がロックされている場合、タスクのルールスレッドに対して停止要求を発行した後、
 * このルールスレッドがシグナルを発行するのを待って再度膜のロック取得を試みつづける。
 * <p>
 * ルールスレッドでないスレッドは、短時間でロックを解放すべきである。
 * ルールスレッドは、そのタスクに対してロック要求があった場合には直ちに実行を停止すべきである。
 * ルールスレッドは、本膜を管理するタスク以外のタスクのロック（非線形プロセス文脈のときのみ必要）
 * は短時間で解放すべきである。
 * 
 * <p>
 * <b>親タスクの膜の活性化</b>
 * <p>
 * 非同期で膜のロックを取得することにより実行膜スタックを更新することにより実現する。実装済み。テスト済。
 * 
 * <p>
 * <b>システムコール</b>
 * <p>
 * 非同期で膜のロックを取得することにより実現可能。
 * ただし子膜の取得がブロッキングになるようにタスクに優先度を設ける必要があるはずである。
 * システムコールは現在はまだ実装されていない。
 * 
 * <p>
 * <b>排他制御</b>
 * <p>
 * タスクの停止・再開の待ち合わせ処理のために、このクラスのインスタンスに関する synchronized 節を利用する。
 */

public class Task implements Runnable {
	/** このタスクを管理するランタイム */
	protected LMNtalRuntime runtime;
	/** このタスクのルート膜 */
	protected Membrane root;
	/** asyncUnlockされたときにtrueになる
	 * （trueならばシグナルで復帰時にトレースdumpする）*/
	protected boolean asyncFlag = false;
	
	/** ランタイムの取得 */
	public LMNtalRuntime getMachine() {
		return runtime;
	}
	/** ルート膜の取得 */
	public Membrane getRoot() {
		return root;
	}
	
	/** このタスクのルールスレッド */
	protected Thread thread = new Thread(this, "Task");
	/** 実行膜スタック*/
	Stack memStack = new Stack();
	/** 仮の実行膜スタック */
	Stack bufferedStack = new Stack();
	static final private int MAX_LOOP = 100;
	/** タスクの優先度（正確には、このタスクのルールスレッドの優先度）
	 * <p>ロックの制御に使用する予定。将来的にはタスクのスケジューリングにも使用される予定。
	 * <p>10以上の値でなければならない。*/
	int priority = 32;
	
	/**
	 * 新・永久フラグ．
	 * <p>すべてのルールが適応し終わった状態で，このフラグが真の場合，すべての膜を再度活性化させる．
	 */
	// by nakano
//	static private boolean perpetual = false;
	
	/** 親膜を持たない新しいルート膜および対応するタスク（マスタタスク）を作成する
	 * @param runtime 作成したタスクを実行するランタイム（つねにEnv.getRuntime()を渡す）*/
	Task(LMNtalRuntime runtime) {
		this.runtime = runtime;
		root = new Membrane(this);
		memStack.push(root);
	}

	/** 指定した親膜を持つ新しいロックされたルート膜および対応するタスク（スレーブタスク）を作成する
	 * @param runtime 作成したタスクを実行するランタイム（つねにEnv.getRuntime()を渡す）
	 * @param parent 親膜 */
	Task(LMNtalRuntime runtime, Membrane parent) {
		this(runtime);
		root = new Membrane(this);
		root.lockThread = Thread.currentThread();
//		root.remote = parent.remote;
		root.activate(); 		// 仮の実行膜スタックに積む
		parent.addMem(root);	// タスクは膜の作成時に設定した
		thread.start();
	}
	/** 親膜の無い膜を作成し、このタスクが管理する膜にする。 */
	public Membrane createFreeMembrane() {
		return new Membrane(this);
	}
	
	////////////////////////////////////////////////////////////////
	/** このタスクのルールスレッドを実行する。
	 * 実行が終了するまで戻らない。
	 * <p>マスタタスクのルールスレッドを実行するために使用される。*/
	public void execAsMasterTask() {
		switch (Env.ndMode) {
		case Env.ND_MODE_D:
			thread.start();
			try {
				thread.join();
			}
			catch (InterruptedException e) {}
			break;
		case Env.ND_MODE_ND_ALL:
			nondeterministicExec();
			break;
		case Env.ND_MODE_ND_ANSCESTOR:
			nondeterministicExec2();
			break;
		case Env.ND_MODE_ND_NOTHING:
			nondeterministicExec3();
			break;
		}
		
	}
	
	private static int count = 1; // 行番号表示@トレースモード okabe
	// 出力と count の排他制御のため、static synchronized
	synchronized public static void trace(String arrow, String rulesetName, String ruleName) {
		boolean dumpEnable = Env.getExtendedOption("hide").equals("") || !ruleName.matches(Env.getExtendedOption("hide"));
		if(dumpEnable) {
			Membrane memToDump = Env.theRuntime.getGlobalRoot();
			Env.p( Dumper.dump( memToDump ) );
			if(Env.getExtendedOption("dump").equals("1")) {
				Util.println(" ----- " + rulesetName + "/" + ruleName + " ---------------------------------------");
			} else {
				Util.println(" " + arrow + "  #" + (count++) + ": " + rulesetName + "/" + ruleName);
			}
		}
	}
	
	//2006.3.16 by inui
	public static void initTrace() {
		count = 1;
	}
	
	private boolean guiTrace() {
		if (!Env.guiTrace()) return false;
//		/**nakano graphic用*/
//		if (!Env.graphicTrace()){
//			/*暫定版*/
//			Membrane memToDump = ((MasterLMNtalRuntime)getMachine()).getGlobalRoot();
//			Env.p( Dumper.dump( memToDump ) );
//			System.exit(0);
////			return false;
//		}
		return true;
	}
	
	/* アトム主導テストの合計実行時間 */
	long atomtime = 0;
	/* 膜主導テストの合計実行時間 */
	long memtime = 0;
	/* 実行時間取得のためのパラメータ */
	long start,stop;
	
	/** このタスクの本膜のルールを実行する */
	void exec(Membrane mem) {
		for(int i=0; i < MAX_LOOP && mem == memStack.peek() && lockRequestCount == 0 ; i++){
			// 本膜が変わらない間 & ループ回数を越えない間
			if (!exec(mem, false)) break;
		}
	}
	boolean exec(Membrane mem, boolean nondeterministic) {
		Atom a = mem.popReadyAtom();
		Iterator<Ruleset> it = mem.rulesetIterator();
		boolean flag = false;
		
		if(!nondeterministic && Env.shuffle < Env.SHUFFLE_DONTUSEATOMSTACKS && a != null && !Env.memtestonly){ // 実行アトムスタックが空でないとき
			if(Env.profile == Env.PROFILE_BYDRIVEN){
		        start = Util.getTime();
			}
			while(it.hasNext()){ // 本膜のもつルールをaに適用
				if (it.next().react(mem, a)) {
					flag = true;
					//if (memStack.peek() != mem) break;
					break; // ルールセットが変わっているかもしれないため
					//ルールセットが追加されている可能性はあるが、削除される事はないので
					//そのまま処理を続けてもいいと思う。 2005/12/08 mizuno
				}
			}

//			if(Env.fUNYO){
//				unyo.Mediator.sync(root);
//			}
			if(flag){
				if (Env.debugOption) {//by inui
					if (Debug.isBreakPoint()) Debug.inputCommand();
				} else {
					if(Env.fUNYO){
						//unyo.Mediator.sync(root);
						if(!unyo.Mediator.sync(root)){ return false; }
					}
					if (!guiTrace()) return false;
				}
			} else {
				if(!mem.isRoot()) {mem.getParent().enqueueAtom(a);} 
				// TODO システムコールアトムなら、本膜がルート膜でも親膜につみ、親膜を活性化
			}
			if(Env.profile == Env.PROFILE_BYDRIVEN){
		        stop = Util.getTime();
		        atomtime+=(stop>start)?(stop-start):0;
			}
		}else{ // 実行アトムスタックが空の時
			if(Env.profile == Env.PROFILE_BYDRIVEN){
		        start = Util.getTime();
			}
			// 今のところ、システムルールセットは膜主導テストでしか実行されない。
			// 理想では、組み込みの + はインライン展開されるべきである。
			flag = SystemRulesets.react(mem, nondeterministic);
			if (!flag) {				
				while(it.hasNext()){ // 膜主導テストを行う
					if(((Ruleset)it.next()).react(mem, nondeterministic)) {
						flag = true;
						/*
						if(Env.fUNYO){
							//unyo.Mediator.sync(root);
							if(!unyo.Mediator.sync(root)){ return false; }
						}
						*/
						//if (memStack.peek() != mem) break;
						break; // ルールセットが変わっているかもしれないため
					}
				}
			}
			if(flag){
				if (Env.debugOption) {//by inui
					if (Debug.isBreakPoint()) Debug.inputCommand();
				} else {
					if(Env.fUNYO){
						//unyo.Mediator.sync(root);
						if(!unyo.Mediator.sync(root)){ return false; }
					}
					if (!guiTrace()) return false;
				}
			} else if (!nondeterministic){
				memStack.pop(); // 本膜をpop
				if (!mem.isNondeterministic() && !mem.perpetual) {
					// 子膜が全てstableなら、この膜をstableにする。
					Iterator<Membrane> it_m = mem.memIterator();
					flag = false;
					while(it_m.hasNext()){
						if(!it_m.next().isStable()) {
							flag = true;
							break;
						}
					}
					if(!flag) {
						mem.toStable();
					}
				}
			}

			if(Env.profile == Env.PROFILE_BYDRIVEN){
		        stop = Util.getTime();
		        memtime+=(stop>start)?(stop-start):0;
			}
		}
		
		return true;
	}
	
	/** このタスク固有のルールスレッドが実行する処理 */
	public void run() {
		Membrane root = null; // ルートタスクのときのみルート膜が入る。それ以外はnull
		if (runtime instanceof LMNtalRuntime) {
			root = runtime.getGlobalRoot();
			if (root.getTask() != this) root = null;
		}
		
		if (root != null && Env.fTrace) {
			//Env.p( Dumper.dump(root) );
		}
		
		LMNtalRuntime r = runtime;
//		if(Env.fUNYO){
//			unyo.Mediator.sync(root);
//		}
		while (!r.isTerminated()) {
			Membrane mem;
			//本膜をロック
			synchronized(this) {
				//ほかのタスクからロックがリクエストされているか、
				//実行膜スタックが空っぽか
				//膜のロックに失敗すれば
				while (lockRequestCount > 0 || (mem = (Membrane)memStack.peek()) == null || !mem.lock()) {
					if (r.isTerminated()) return;
					try {
						wait();
					} catch (InterruptedException e) {}
				}
				running = true;
			}
//			mem.remote = null;
			//非ルールスレッドが変更した内容を出力する。
			if (root != null && Env.fTrace && asyncFlag) {
				asyncFlag = false;
				Env.p( " ==>* \n" + Dumper.dump(root) );
			}
			//実行
			exec(mem);
	        mem.unlock(true);
	        
			//このタスクの停止を待っているスレッドを全て起こす。
			synchronized(this) {
				running = false;
				notifyAll();
			}
			
			//releaseが呼び出されていたらUNYO終了 (ayano)
			if(Env.fUNYO){
				if(unyo.Mediator.releasing) break;
			}
			
			//グローバルルート膜であり、本膜のルール適用を終了していたら、実行終了
			if (root != null && root.isStable()) break;
//			if (root != null && root.isStable() && !perpetual) break;
//			if (root != null && root.isStable() && perpetual){
//				activateAllMem(root);
//				try {
//					this.thread.sleep(10);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}

			// Perpetualな膜が存在するとき、100ms待って、perptualな膜の実行検査をやり直す(活性化する)。
			// TODO perpetual じゃないもうひとつのフラグをつくってその膜を活性化させる hara
			if(root!=null && memStack.isEmpty()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				activatePerpetualMem(root);
			}

			// 本膜のルール適用を終了しており、本膜がroot膜かつ親膜を持つなら、親膜を活性化する。
			// これは本膜ロック解放後に行う必要がある。
			if(memStack.isEmpty() && mem.isRoot()) {
				Membrane memToActivate = mem.getParent();
				// 親膜がすでに無効になっていた場合、活性化要求は単純に無視すればよい。
				if (memToActivate != null) {
					doAsyncLock(memToActivate);
				}
			}
		}
	}

	/**
	 * Perpetual な膜を活性化する。
	 * hara
	 * @param mem
	 */
	static public void activatePerpetualMem(Membrane mem) {
		if(mem.perpetual) doAsyncLock(mem);
		Iterator<Membrane> it = mem.memIterator();
		while(it.hasNext()) {
			final Membrane m = it.next();
			if(m.perpetual) doAsyncLock(m);
			activatePerpetualMem(m);
		}
	}
	
//	/**
//	 * 新・永久フラグのセット
//	 * @param f
//	 */
//	static public void setPerpetual(boolean f){
//		perpetual = f;
//	}
//	
//	/**
//	 * 自膜を含めたすべての子膜を活性化させる．
//	 * @param mem
//	 */
//	private void activateAllMem(Membrane mem){
//		Iterator it = mem.memIterator();
//		while(it.hasNext()) {
//			final Membrane m = (Membrane)it.next();
//			doAsyncLock(m);
//			//m.activate();
//			activateAllMem(m);
//		}
//	}
	
	// 060401 okabe
	// Membrane -> AbstractMembrane
	// 他も順次変更していく予定
	static public void doAsyncLock(Membrane mem) {
		final Membrane m = mem;
		new Thread() {
			public void run() {
				if (m.asyncLock()) {
					m.asyncUnlock(false);
				}
			}
		}.start();
	}

	public void outTime(){
		if(Env.majorVersion==1 && Env.minorVersion>4)
			Util.println("threadID="+thread.getId()+" atomtime=" + atomtime/1000000 + "msec memtime=" + memtime/1000000 +"msec totaltime="+(atomtime+memtime)/1000000+"msec");
		else
			Util.println("threadID="+thread.hashCode()+" atomtime=" + atomtime + "msec memtime=" + memtime +"msec totaltime=" + (atomtime+memtime) + "msec");
	}

	////////////////////////////////////////////////////////////////

	// タスクのルールスレッドに対する停止要求

	/**
	 *  このタスクのルールスレッドに対して停止要求を発行しているスレッドの個数。
	 *  複数のスレッドで操作するため、Task に関する synchronized 節内で操作する必要がある。
	 */
	private int lockRequestCount = 0;
	private boolean running = false;
	
	public void suspend() {
		synchronized(this) {
			lockRequestCount++;
			while (running) {
				try {
					wait();
				} catch (InterruptedException e) {}
			}
		}
	}
	public void resume() {
		synchronized(this) {
			lockRequestCount--;
			if (lockRequestCount == 0) {
				//タスクを起こす。
				//notifyAll を使ってはいるが、wait しているスレッドはルールスレッドのみのはずである。
				notifyAll();
			}
		}
	}
	
	////////////////////////////////////
	// non deterministic LMNtal

	public static HashSet<Object[]> states = new HashSet<Object[]>();
	private static final Functor FROM = new SymbolFunctor("from", 1);
	private static final Functor TO = new SymbolFunctor("to", 1);
	private static final Functor FUNCTOR_REDUCE = new SymbolFunctor("reduce", 3);
	/** 
	 * 指定された膜に関するリダクショングラフを生成する。
	 * 結果は、指定された膜の親膜の親膜に生成される。
	 * @param memExec2 非同期実行する膜
	 */
	public void nondeterministicExec(Membrane memExec2) {
		AtomSet atoms = memExec2.getAtoms();
		atoms.freeze();
		ArrayList<Membrane> l = new ArrayList<Membrane>();
		l.add(memExec2);
		HashMap<AtomSet,Membrane> memMap = new HashMap<AtomSet,Membrane>();
		memMap.put(atoms, memExec2.getParent());
		while (l.size() > 0) {
			Membrane mem2 = l.remove(l.size() - 1);
			nondeterministicExec(mem2, memMap, l);
		}
	}
	/**
	 * 指定された膜を、非決定的に 1 段階実行する。
	 * @param memExec2 実行する膜
	 * @param memMap すでに生成された膜が入ったマップ。実行した結果が同一の場合に、同一の膜を利用するためのもの。
	 * @param newMems 新しく生成した膜を追加するリスト。実行した結果が memMap 内になかった場合に、ここに追加される。
	 *         nullが指定された場合は何もしない。
	 */
	public void nondeterministicExec(Membrane memExec2, HashMap<AtomSet,Membrane> memMap, ArrayList<Membrane> newMems) {
		Membrane memExec = memExec2.getParent();
		Membrane memGraph = memExec.getParent(); // リダクショングラフが生成される膜
		states.clear();
		exec(memExec2, true);
		//それぞれ適用した結果を作成
		Iterator<Object[]> it = states.iterator();
		while (it.hasNext()) {
			//複製
			Membrane memResult = memGraph.newMem();
			Membrane memResult2 = memResult.newMem(Membrane.KIND_ND);
			Map<Atom, Atom> atomMap = memResult2.copyCellsFrom(memExec2); // 帰り値はコピー元のアトム->コピー先のアトムというMap
			memResult2.copyRulesFrom(memExec2);
			//適用
			// memResult2: 複製で作成された膜
			// memExec2:   複製元
			String name = react(memResult2, it.next(), memExec2, atomMap);
			AtomSet atoms = memResult2.getAtoms();
			atoms.freeze();
			
			//同一の膜を調べる
			Membrane memOut = memMap.get(atoms);
			boolean flg = memOut == null;
			if (flg) {
				memMap.put(atoms, memResult);
				memOut = memResult;
				if (newMems != null) newMems.add(memResult2);
			} else {
				memGraph.removeMem(memResult);
				memResult.drop();
				if (memOut.lockThread != Thread.currentThread()) 
					memOut.blockingLock();
			}
			//リンク生成
			Atom f = memExec.newAtom(FROM);
			Atom fi = memExec.newAtom(Functor.INSIDE_PROXY);
			Atom fo = memGraph.newAtom(Functor.OUTSIDE_PROXY);
			Atom r = memGraph.newAtom(FUNCTOR_REDUCE);
			Atom n = memGraph.newAtom(new StringFunctor(name));
			Atom to = memGraph.newAtom(Functor.OUTSIDE_PROXY);
			Atom ti = memOut.newAtom(Functor.INSIDE_PROXY);
			Atom t = memOut.newAtom(TO);
			memExec.newLink(f, 0, fi, 1);
			memGraph.newLink(fi, 0, fo, 0);
			memGraph.newLink(fo, 1, r, 0);
			memGraph.newLink(r, 1, to, 1);
			memGraph.newLink(r, 2, n, 0);
			memGraph.newLink(to, 0, ti, 0);
			memOut.newLink(ti, 1, t, 0);
			if (!flg) {
				memOut.unlock();
			}
		}
	}
	/**
	 * ルート膜を非同期実行し、リダクショングラフを標準出力に出力する。
	 */
	void nondeterministicExec() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		HashMap<AtomSet, Integer> idMap = new HashMap<AtomSet, Integer>();
		int nextId = 0;
		ArrayList<Membrane> st = new ArrayList<Membrane>();
		st.add(getRoot());
		AtomSet atoms = getRoot().getAtoms();
		atoms.freeze();
		idMap.put(atoms, new Integer(nextId++));
//		int i = 0, max_w = 0,t = 0;

		try {
			while (st.size() > 0) {
				Membrane mem = st.remove(st.size() - 1);
				//ルール適用の全可能性を検査
				if (mem != getRoot())
					memStack.push(mem);
				root = mem;
				states.clear();
				exec(mem, true);
				memStack.pop();
				//適用した結果を作成
				if (!Env.fInteractive)
					Util.println(idMap.get(mem.getAtoms()) + " : " + Dumper.dump(mem));
				if (states.size() > 0) {
					Iterator it = states.iterator();
		//			int w = 0;
		//			t++;
					while (it.hasNext()) {
		//				i++;
		//				w++;
						//複製
						Membrane mem2 = new Membrane(this);
						Map<Atom, Atom> map = mem2.copyCellsFrom(mem);
						//mem2.memToCopyMap = null;
						mem2.copyRulesFrom(mem);
						//適用
						String ruleName = react(mem2, (Object[])it.next(), mem, map);
						atoms = mem2.getAtoms();
						atoms.freeze();
						
						Integer id;
						if (idMap.containsKey(atoms)) {
							id = (Integer)idMap.get(atoms);
							mem2.drop();
							mem2.free();
						} else {
							id = new Integer(nextId++);
							st.add(mem2);
							idMap.put(atoms, id);
						}
						if (!Env.fInteractive)
							Util.print(" " + id + "(" + ruleName + ")");
					}
				} else if (Env.fInteractive) {
					Util.print(Dumper.dump(mem) + " ? ");
					String str = reader.readLine();
					if (str == null || str.equals("") || str.equals("y")) {
						Env.p("yes");
						idMap.remove(mem.getAtoms());
						mem.drop();
						mem.free();
						return;
					}
				}
	//			if (w > max_w) max_w = w;
				if (!Env.fInteractive)
					System.out.println();
	//			System.out.println(idMap.size() + "/" + st.size() + "/" + max_w);
			}
			if (Env.fInteractive)
				Env.p("no");
	//		System.out.println("state count is " + i + ", unique count is " + nextId);
	//		System.out.println("average width is " + (i / t) + ", max width is " + max_w);
		} catch (IOException e) {
			Env.e("I/O Error");
			Env.d(e);
		}
	}

	private int nextId;
	/**
	 * ルート膜を非同期実行し、リダクショングラフを標準出力に出力する。
	 * 重複除去の対象は、先祖とその兄弟のみ
	 */
	void nondeterministicExec2() {
		HashMap<AtomSet, Integer> idMap = new HashMap<AtomSet, Integer>();
		nextId = 0;

		Membrane mem = (Membrane)getRoot();
		AtomSet atoms = mem.getAtoms();
		atoms.freeze();
		idMap.put(atoms, new Integer(nextId++));

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			boolean ret = nondeterministicExec2(idMap, mem, reader);
			if (Env.fInteractive)
				Env.p(ret ? "yes" : "no");
		} catch (IOException e) {
			Env.e("I/O Error");
			Env.d(e);
		}
	}
	boolean nondeterministicExec2(HashMap<AtomSet, Integer> idMap, Membrane mem, BufferedReader reader) throws IOException {
		//ルール適用の全可能性を検査
		if (mem != getRoot())
			memStack.push(mem);
		root = mem;
		states.clear();
		exec(mem, true);
		memStack.pop();
		if (!Env.fInteractive)
			Util.println(idMap.get(mem.getAtoms()) + " : " + Dumper.dump(mem));
		//適用した結果を作成
		List<Membrane> children = new ArrayList<Membrane>();
		if (Env.fInteractive && states.size() == 0) {
			Util.print(Dumper.dump(mem) + " ? ");
			String str = reader.readLine();
			if (str == null || str.equals("") || str.equals("y")) {
				idMap.remove(mem.getAtoms());
				mem.drop();
				mem.free();
				return true;
			}
		}
		Iterator it = states.iterator();
		while (it.hasNext()) {
			//複製
			Membrane mem2 = new Membrane(this);
			Map<Atom, Atom> map = mem2.copyCellsFrom(mem);
			//mem2.memToCopyMap = null;
			mem2.copyRulesFrom(mem);
			//適用
			String ruleName = react(mem2, (Object[])it.next(), mem, map);

			AtomSet atoms2 = mem2.getAtoms();
			atoms2.freeze();
			Integer id;
			if (idMap.containsKey(atoms2)) {
				id = (Integer)idMap.get(atoms2);
				mem2.drop();
				mem2.free();
			} else {
				id = new Integer(nextId++);
				children.add(mem2);
				idMap.put(atoms2, id);
			}
			if (!Env.fInteractive)
				Util.print(" " + id + "(" + ruleName + ")");
		}
		if (!Env.fInteractive)
			Util.println("");
		
		for (int i = 0; i < children.size(); i++) {
			if (nondeterministicExec2(idMap, children.get(i), reader))
				return true;
		}
		idMap.remove(mem.getAtoms());
		mem.drop();
		mem.free();
		return false;
	}
	/**
	 * ルート膜を非同期実行し、リダクショングラフを標準出力に出力する。
	 */
	void nondeterministicExec3() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int nextId = 1, nowId = 0;
//long t = 0;
		LinkedList<Membrane> queue = new LinkedList<Membrane>();
		queue.addLast(getRoot());
		try {
			while (queue.size() > 0) {
				Membrane mem = queue.removeFirst();
				//ルール適用の全可能性を検査
				if (mem != getRoot())
					memStack.push(mem);
				root = mem;
				states.clear();
				exec(mem, true);
				memStack.pop();
				if (!Env.fInteractive)
					Util.println(nowId++ + " : " + Dumper.dump(mem));
				//適用した結果を作成
				if (states.size() > 0) {
					Iterator it = states.iterator();
					while (it.hasNext()) {
						//複製
						Membrane mem2 = new Membrane(this);
						Map<Atom, Atom> map = mem2.copyCellsFrom(mem);
						//mem2.memToCopyMap = null;
						mem2.copyRulesFrom(mem);
						//適用
						String ruleName = react(mem2, (Object[])it.next(), mem, map);
						
						queue.addLast(mem2);
						if (!Env.fInteractive)
							Util.print(" " + nextId++ + "(" + ruleName + ")");
					}
				} else 	if (Env.fInteractive) {
					Util.print(Dumper.dump(mem) + " ? ");
					String str = reader.readLine();
					if (str == null || str.equals("") || str.equals("y")) {
						Env.p("yes");
						mem.drop();
						mem.free();
						return;
					}
				}
	//long s = System.nanoTime();
				mem.drop();
				mem.free();
	//t += System.nanoTime() - s;
				if (!Env.fInteractive)
					System.out.println();
			}
			Env.p("no");
		} catch (IOException e) {
			Env.e("I/O Error");
			Env.d(e);
		}
//System.err.println(t / 1000000);
	}

	/**
	 * exec(origMem, true) によって得られた情報を元に、実際に 1 段階のルール適用を行う。
	 * @param mem 実行する（書き換える）対象の膜
	 * @param state exec(origMem, true) の実行時に states に生成した適用情報 
	 * @param origMem exec に渡した（マッチング検査の対象となった）膜。state 内の origMem は、mem に書き換えられる。
	 * @param atomMap origMem 内のアトムから mem 内のアトムへのマップ。state 内のアトムはこのマップにしたがって書き換えられる。
	 * @return 適用したルールの名前
	 */
	static String react(Membrane mem, Object[] state, Membrane origMem, Map<Atom, Atom> atomMap) {
		Ruleset rs = (Ruleset)state[0];
		String name = (String)state[1];
		String label = (String)state[2];
		Object[] st = (Object[])state[3];
		
		Object[] argsTemp = new Object[st.length];
		if (atomMap != null){
			for(int i = 0; i < st.length; i++){
				// atomMap に含まれている場合
				argsTemp[i] = atomMap.get(st[i]);
				if (origMem == st[i]){
					argsTemp[i] = mem;
				}
				// atomMap に含まれていない場合（ガードで追加されたプロセス）
				if(argsTemp[i]==null) {
					if(st[i] instanceof Atom) {
						argsTemp[i] = new Atom(((Atom)st[i]).getMem(),((Atom)st[i]).getFunctor());
					} else {
						// 多分ありえないケースだと思う．
						Util.println("Error: st["+i+"] is not an instance of Atom.");
						System.exit(1);
					}
				}
			}
		}
//		if (origMem == st){
//			argsTemp[i] = mem;
//		}
		
		Class[] parameterTypes = new Class[2];
		parameterTypes[0] = Object[].class;
		parameterTypes[1] = boolean.class;
		Object[] args = new Object[2];
		args[0] = argsTemp;
		args[1] = Boolean.FALSE;
		
		try {
			Method m = rs.getClass().getMethod("exec" + label, parameterTypes);
			m.invoke(rs, args);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		return name;
	}
//	static String react(Membrane mem, Object[] state, Membrane origMem, Map atomMap) {
//		Ruleset rs = (Ruleset)state[0];
//		String name = (String)state[1];
//		String label = (String)state[2];
//		Class[] parameterTypes = new Class[state.length - 3 + 1];
//		Object[] args = new Object[state.length - 3 + 1];
//		int i;
//		for (i = 0; i < state.length - 3; i++) {
////			parameterTypes[i] = Object.class;
//			args[i] = state[i+3];
//			if (args[i] instanceof Atom && atomMap != null && atomMap.containsKey(args[i]))
//				args[i] = atomMap.get(args[i]);
//			if (origMem == args[i])
//				args[i] = mem;
//		}
//		parameterTypes[0] = Object[].class;
//		parameterTypes[1] = boolean.class;
//		args[i] = Boolean.FALSE;
//		try {
//			Method m = rs.getClass().getMethod("exec" + label, parameterTypes);
//			System.out.println(m);
//			m.invoke(rs, args);
//		} catch (NoSuchMethodException e) {
//			throw new RuntimeException(e);
//		} catch (InvocationTargetException e) {
//			throw new RuntimeException(e);
//		} catch (IllegalAccessException e) {
//			throw new RuntimeException(e);
//		}
//		return name;
//	}
}