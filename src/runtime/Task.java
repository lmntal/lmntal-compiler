package runtime;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import util.Stack;

/** タスク
 * todo タスクに優先度を設定する。これはルールスレッドによるロックがブロッキングになるかどうかの決定に影響させる。
 * 非同期実行はルールスレッドよりも高い優先度を与える。
 * todo ルート膜が先祖タスクまたは自タスクのルールスレッドによってロックされたかどうかを記憶する。
 * これにより、先祖タスクのルールスレッドを停止して動作する非同期実行が可能となる。
 * <p>
 * <font size=+1><b>方法6</b></font><br>
 * 
 * <p>
 * <b>不変条件</b>
 * <p>
 * 膜のロックを取得しようとするスレッドは、どの膜のロックも取得していないか、
 * またはロックを取得しようとする膜の親膜のロックを取得していなければならない。
 * <p>
 * ルールスレッド以外のスレッドは、最初にルート膜のロックを取得しなければならない。
 * 
 * <p>
 * <b>ルールスレッド</b>
 * <p>
 * ルール適用を行うためのスレッドをルールスレッドと呼ぶ。
 * タスクはちょうど1つのルールスレッドによって実行される。
 * <strike>現状ではルールスレッドは物理マシンごとに1つ存在し、タスクによって共有されている。</strike>
 * 現在、各タスクが固有のルールスレッドを持つようになっている。
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

public class Task extends AbstractTask implements Runnable {
	/** このタスクのルールスレッド */
	protected Thread thread = new Thread(this, "Task");
	/** 実行膜スタック*/
	Stack memStack = new Stack();
	/** 仮の実行膜スタック */
	Stack bufferedStack = new Stack();
	static final int maxLoop = 100;
	/** タスクの優先度（正確には、このタスクのルールスレッドの優先度）
	 * <p>ロックの制御に使用する予定。将来的にはタスクのスケジューリングにも使用される予定。
	 * <p>10以上の値でなければならない。*/
	int priority = 32;
	
	/** 親膜を持たない新しいルート膜および対応するタスク（マスタタスク）を作成する
	 * @param runtime 作成したタスクを実行するランタイム（つねにEnv.getRuntime()を渡す）*/
	Task(AbstractLMNtalRuntime runtime) {
		super(runtime);
		root = new Membrane(this);
		memStack.push(root);
	}

	/** 指定した親膜を持つ新しいロックされたルート膜および対応するタスク（スレーブタスク）を作成する
	 * @param runtime 作成したタスクを実行するランタイム（つねにEnv.getRuntime()を渡す）
	 * @param parent 親膜 */
	Task(AbstractLMNtalRuntime runtime, AbstractMembrane parent) {
		super(runtime);
		root = new Membrane(this);
		root.lockThread = Thread.currentThread();
		root.remote = parent.remote;
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
		if (Env.fNondeterministic) {
			nondeterministicExec();
		} else {
			thread.start();
			try {
				thread.join();
			}
			catch (InterruptedException e) {}
		}
	}
	
	private int count = 1; // 行番号表示@トレースモード okabe
	private boolean trace(String arrow) {
		if (Env.fTrace) {
			if (getMachine() instanceof MasterLMNtalRuntime) {
				Membrane memToDump = ((MasterLMNtalRuntime)getMachine()).getGlobalRoot();
				// ルール適用の連番
				if(Env.dumpEnable) {
					if(Env.getExtendedOption("dump").equals("1")) {
						Env.p( Dumper.dump( memToDump ) );
					} else {
						System.out.print(" #" + (count++));
						Env.p( arrow + " \n" + Dumper.dump( memToDump ) );
					}
				}
			}
		}
		if (!Env.guiTrace()) return false;
		/**nakano graphic用*/
		if (!Env.graphicTrace()){
			/*暫定版*/
			Membrane memToDump = ((MasterLMNtalRuntime)getMachine()).getGlobalRoot();
			Env.p( Dumper.dump( memToDump ) );
			System.exit(0);
//			return false;
		}
		
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
		for(int i=0; i < maxLoop && mem == memStack.peek() && lockRequestCount == 0; i++){
			// 本膜が変わらない間 & ループ回数を越えない間
			if (!exec(mem, false)) break;
		}
	}
	boolean exec(Membrane mem, boolean nondeterministic) {
		Atom a = mem.popReadyAtom();
		Iterator it = mem.rulesetIterator();
		boolean flag = false;
		if(!nondeterministic && Env.shuffle < Env.SHUFFLE_DONTUSEATOMSTACKS && a != null){ // 実行アトムスタックが空でないとき
			if(Env.profile){
				if(Env.majorVersion==1 && Env.minorVersion>4){
			        start = System.nanoTime();
				}else{
			        start = System.currentTimeMillis();
				}				
			}
			while(it.hasNext()){ // 本膜のもつルールをaに適用
				if (((Ruleset)it.next()).react(mem, a)) {
					flag = true;
					//if (memStack.peek() != mem) break;
					break; // ルールセットが変わっているかもしれないため
					//ルールセットが追加されている可能性はあるが、削除される事はないので
					//そのまま処理を続けてもいいと思う。 2005/12/08 mizuno
				}
			}
			
			if(flag){
				if (!trace("-->")) return false;
			} else {
				if(!mem.isRoot()) {mem.getParent().enqueueAtom(a);} 
				// TODO システムコールアトムなら、本膜がルート膜でも親膜につみ、親膜を活性化
			}
			if(Env.profile){
				if(Env.majorVersion==1 && Env.minorVersion>4){
			        stop = System.nanoTime();
				}else{
			        stop = System.currentTimeMillis();
				}				
		        atomtime+=(stop>start)?(stop-start):0;
			}
		}else{ // 実行アトムスタックが空の時
			if(Env.profile){
				if(Env.majorVersion==1 && Env.minorVersion>4){
			        start = System.nanoTime();
				}else{
			        start = System.currentTimeMillis();
				}				
			}
			// 今のところ、システムルールセットは膜主導テストでしか実行されない。
			// 理想では、組み込みの + はインライン展開されるべきである。
			flag = SystemRulesets.react(mem, nondeterministic);
			if (!flag) {				
				while(it.hasNext()){ // 膜主導テストを行う
					if(((Ruleset)it.next()).react(mem, nondeterministic)) {
						flag = true;
						//if (memStack.peek() != mem) break;
						break; // ルールセットが変わっているかもしれないため
					}
				}
			}
			
			if(flag){
				if (!trace("==>")) return false;
			} else if (!nondeterministic){
				memStack.pop(); // 本膜をpop
				if (!mem.isNondeterministic() && !mem.perpetual) {
					// 子膜が全てstableなら、この膜をstableにする。
					it = mem.memIterator();
					flag = false;
					while(it.hasNext()){
						if(((AbstractMembrane)it.next()).isStable() == false)
							flag = true;
					}
					if(flag == false) {
						mem.toStable();
					} else {
					}
				}
			}
			if(Env.profile){
				if(Env.majorVersion==1 && Env.minorVersion>4){
			        stop = System.nanoTime();
				}else{
			        stop = System.currentTimeMillis();
				}				
		        memtime+=(stop>start)?(stop-start):0;
			}
		}
		return true;
	}
	
	/** このタスク固有のルールスレッドが実行する処理 */
	public void run() {
		Membrane root = null; // ルートタスクのときのみルート膜が入る。それ以外はnull
		if (runtime instanceof MasterLMNtalRuntime) {
			root = ((MasterLMNtalRuntime)runtime).getGlobalRoot();
			if (root.getTask() != this) root = null;
		}
		
		if (root != null && Env.fTrace) {
			Env.p( Dumper.dump(root) );
		}
		
		LocalLMNtalRuntime r = (LocalLMNtalRuntime)runtime;
		while (true) {
			Membrane mem;
			//本膜をロック
			synchronized(this) {
				while (lockRequestCount > 0 || (mem = (Membrane)memStack.peek()) == null || !mem.lock()) {
					if (r.isTerminated()) return;
					try {
						wait();
					} catch (InterruptedException e) {}
				}
				running = true;
			}
			mem.remote = null;
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
			if (root != null && root.isStable())
				break;

			// 本膜のルール適用を終了しており、本膜がroot膜かつ親膜を持つなら、親膜を活性化。本膜ロック解放後に行う必要がある。
			if(memStack.isEmpty() && mem.isRoot()) {
				final AbstractMembrane memToActivate = mem.getParent();
				// 親膜がすでに無効になっていた場合、活性化要求は単純に無視すればよい。
				if (memToActivate != null) {
					new Thread() {
						public void run() {
							if (memToActivate.asyncLock()){
								memToActivate.asyncUnlock();
							}
						}
					}.start();
				}
			}
		}
	}

	public void outTime(){
		if(Env.majorVersion==1 && Env.minorVersion>4)
			System.out.println("threadID="+thread.getId()+" atomtime=" + atomtime/1000000 + "msec memtime=" + memtime/1000000 +"msec");
		else
			System.out.println("threadID="+thread.hashCode()+" atomtime=" + atomtime + "msec memtime=" + memtime +"msec");
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

	public static HashSet states = new HashSet();
	private static final Functor FUNC_FROM = new Functor("from", 1, "nd");
	private static final Functor FUNC_TO = new Functor("to", 1, "nd");
	/** 
	 * 指定された膜に関するリダクショングラフを生成する。
	 * 結果は、指定された膜の親膜の親膜に生成される。
	 * @param memExec2 非同期実行する膜
	 */
	public void nondeterministicExec(Membrane memExec2) {
		ArrayList l = new ArrayList();
		l.add(memExec2);
		HashMap memMap = new HashMap();
		memMap.put(memExec2, memExec2.getParent());
		while (l.size() > 0) {
			Membrane mem2 = (Membrane)l.remove(l.size() - 1);
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
	public void nondeterministicExec(Membrane memExec2, HashMap memMap, ArrayList newMems) {
		Membrane memExec = (Membrane)memExec2.getParent();
		Membrane memGraph = (Membrane)memExec.getParent();
		states.clear();
		exec(memExec2, true);
		//それぞれ適用した結果を作成
		Iterator it = states.iterator();
		while (it.hasNext()) {
			//複製
			Membrane memResult = (Membrane)memGraph.newMem();
			Membrane memResult2 = (Membrane)memResult.newMem();
			memResult2.setNondeterministic(true);
			Map atomMap = memResult2.copyCellsFrom(memExec2);
			memResult2.copyRulesFrom(memExec2);
			//適用
			react(memResult2, (Object[])it.next(), memExec2, atomMap);
			
			//同一の膜を調べる
			Membrane memOut = (Membrane)memMap.get(memResult2);
			boolean flg = memOut == null;
			if (flg) {
				memMap.put(memResult2, memResult);
				memOut = memResult;
				if (newMems != null) newMems.add(memResult2);
			} else {
				memGraph.removeMem(memResult);
				memResult.drop();
				if (memOut.lockThread != Thread.currentThread()) 
					memOut.blockingLock();
			}
			//リンク生成
			Atom f = memExec.newAtom(FUNC_FROM);
			Atom fi = memExec.newAtom(Functor.INSIDE_PROXY);
			Atom fo = memGraph.newAtom(Functor.OUTSIDE_PROXY);
			Atom to = memGraph.newAtom(Functor.OUTSIDE_PROXY);
			Atom ti = memOut.newAtom(Functor.INSIDE_PROXY);
			Atom t = memOut.newAtom(FUNC_TO);
			memExec.newLink(f, 0, fi, 1);
			memGraph.newLink(fi, 0, fo, 0);
			memGraph.newLink(fo, 1, to, 1);
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
		Env.fMemory = false;
		HashMap idMap = new HashMap();
		int id = 0;
		ArrayList st = new ArrayList();
		st.add(getRoot());
		idMap.put(getRoot(), new Integer(id++));

		while (st.size() > 0) {
			Membrane mem = (Membrane)st.remove(st.size() - 1);
			//ルール適用の全可能性を検査
			if (mem != getRoot())
				memStack.push(mem);
			root = mem;
			states.clear();
			exec(mem, true);
			//それぞれ適用した結果を作成
			System.out.print(idMap.get(mem) + ":");
			Iterator it = states.iterator();
			while (it.hasNext()) {
				//複製
				Membrane mem2 = new Membrane();
				mem2.task = this;
				Map map = mem2.copyCellsFrom(mem);
				mem2.copyRulesFrom(mem);
				//適用
				react(mem2, (Object[])it.next(), mem, map);
				
				if (idMap.containsKey(mem2)) {
					System.out.print(" " + idMap.get(mem2));
				} else {
					System.out.print(" " + id);
					st.add(mem2);
					idMap.put(mem2, new Integer(id++));
				}
			}
			System.out.println();
		}
		Iterator it = idMap.keySet().iterator();
		while (it.hasNext()) {
			Membrane mem = (Membrane)it.next();
			System.out.println(idMap.get(mem) + " = " + mem);
		}
	}
	/**
	 * exec(origMem, true) によって得られた情報を元に、実際に 1 段階のルール適用を行う。
	 * @param mem 実行する対象の膜
	 * @param state exec(origMem, true) の実行時に states に生成した適用情報 
	 * @param origMem exec に渡した膜。state 内の origMem は、mem に書き換えられる。
	 * @param atomMap origMem 内のアトムから mem 内のアトムへのマップ。state 内のアトムはこのマップにしたがって書き換えられる。
	 */
	static void react(Membrane mem, Object[] state, Membrane origMem, Map atomMap) {
		Ruleset rs = (Ruleset)state[0];
		Class[] parameterTypes = new Class[state.length - 2 + 1];
		Object[] args = new Object[state.length - 2 + 1];
		int i;
		for (i = 0; i < state.length - 2; i++) {
			parameterTypes[i] = Object.class;
			args[i] = state[i+2];
			if (args[i] instanceof Atom && atomMap != null && atomMap.containsKey(args[i]))
				args[i] = atomMap.get(args[i]);
			if (origMem == args[i])
				args[i] = mem;
		}
		parameterTypes[i] = boolean.class;
		args[i] = Boolean.FALSE;
		try {
			Method m = rs.getClass().getMethod("exec" + state[1], parameterTypes);
			m.invoke(rs, args);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
