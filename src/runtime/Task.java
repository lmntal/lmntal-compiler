package runtime;

import java.util.Iterator;

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
 * 非同期で膜のロックを取得することにより実行膜スタックを更新することにより実現する。実装済み。未テスト。
 * 
 * <p>
 * <b>システムコール</b>
 * <p>
 * 非同期で膜のロックを取得することにより実現可能。
 * ただし子膜の取得がブロッキングになるようにタスクに優先度を設ける必要があるはずである。
 * システムコールは現在はまだ実装されていない。
 */

class Task extends AbstractTask implements Runnable {
	/** このタスクのルールスレッド */
	protected Thread thread = new Thread(this, "Task");
	/** 実行膜スタック */
	Stack memStack = new Stack();
	Stack bufferedStack = new Stack();
	boolean idle = false;
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

	/** 指定した親膜を持つ新しいルート膜および対応するタスク（スレーブタスク）を作成する
	 * @param runtime 作成したタスクを実行するランタイム（つねにEnv.getRuntime()を渡す）
	 * @param parent 親膜 */
	Task(AbstractLMNtalRuntime runtime, AbstractMembrane parent) {
		super(runtime);
		root = new Membrane(this);
		root.lock();
		root.activate(); 		// 仮の実行膜スタックに積む
		parent.addMem(root);	// タスクは膜の作成時に設定した
		thread.run();
	}
	/** 親膜の無い膜を作成し、このタスクが管理する膜にする。 */
	public Membrane createFreeMembrane() {
		return new Membrane(this);
	}
	
	boolean isIdle(){
		return idle;
	}
	
	////////////////////////////////////////////////////////////////
	
	/** このタスクのルールスレッドを実行する。
	 * 実行が終了するまで戻らない。
	 * <p>マスタタスクのルールスレッドを実行するために使用される。*/
	public void execAsMasterTask() {
		thread.start();
		try {
			thread.join();
		}
		catch (InterruptedException e) {}
	}
	/** このタスクの本膜のルールを実行する */
	void exec() {
		Membrane mem; // 本膜
		synchronized(this) {
			// このタスクを実行するルールスレッド（現在のスレッド）に停止要求があるときは何もしない
			if (lockRequestCount > 0) {
				idle = true;
				return;
			}
			// 本膜のロックを取得する
			mem = (Membrane)memStack.peek();
			if(mem == null || !mem.lock()) {
				// 本膜が無いかまたは本膜のロックを取得できないとき
				idle = true;
				return;
			}
		}
		// 実行
		for(int i=0; i < maxLoop && mem == memStack.peek() && lockRequestCount == 0; i++){
			// 本膜が変わらない間 & ループ回数を越えない間
//			System.out.println("mems  = " + memStack);
//			System.out.println("atoms = " + mem.getReadyStackStatus());
			Atom a = mem.popReadyAtom();
			Iterator it = mem.rulesetIterator();
			boolean flag;
//			mem.atoms.print();
			if(a != null){ // 実行アトムスタックが空でないとき
				flag = false;
				while(it.hasNext()){ // 本膜のもつルールをaに適用
					if (((Ruleset)it.next()).react(mem, a)) {
						flag = true;
						//if (memStack.peek() != mem) break;
						break; // ルールセットが変わっているかもしれないため
					}
				}
				if(flag == false){ // ルールが適用できなかった時
					if(!mem.isRoot()) {mem.getParent().enqueueAtom(a);} 
				}
				else {
					if (Env.fTrace) {
						Env.p( " --> " );
						Env.p( Dumper.dump(getRoot()) );
					}
					Env.guiTrace();
				}// システムコールアトムなら親膜につみ、親膜を活性化
			}else{ // 実行アトムスタックが空の時
				flag = false;
				// アトム主導テストしないときに足し算を先に行うために、順番を変えてみた。
				// アトム主導テストするときは、+ を先に実行した後、再帰呼び出しが実行される。
				// 理想では、組み込みの + はインライン展開されるべきである。
				{
					int debugvalue = Env.debug; // todo spy機能を実装する
					if (Env.debug < Env.DEBUG_SYSTEMRULESET) Env.debug = 0;
					flag = SystemRuleset.getInstance().react(mem);
					Env.debug = debugvalue;
				}
				if (flag == false) {				
					while(it.hasNext()){ // 膜主導テストを行う
						if(((Ruleset)it.next()).react(mem)) {
							flag = true;
							//if (memStack.peek() != mem) break;
							break; // ルールセットが変わっているかもしれないため
						}
					}
				}
				if(flag == false){ // ルールが適用できなかった時
					memStack.pop(); // 本膜をpop
					// 本膜がroot膜かつ親膜を持つなら、親膜を活性化
					if(mem.isRoot() && mem.getParent() != null) {
						new Thread() {
							AbstractMembrane mem;
							public void run() {
								if (mem.getParent().asyncLock())
									mem.getParent().asyncUnlock();
							}
							public void activate(AbstractMembrane mem) {
								this.mem = mem;
								run();
							}
						}.activate(mem);
					}
					if (!mem.perpetual) {
						// 子膜が全てstableなら、この膜をstableにする。
						it = mem.memIterator();
						flag = false;
						while(it.hasNext()){
							if(((AbstractMembrane)it.next()).isStable() == false)
								flag = true;
						}
						if(flag == false) mem.toStable();
					}
				} else {
					if (Env.fTrace) {
						Env.p( " ==> " );
						Env.p( Dumper.dump(getRoot()) );
					}
					Env.guiTrace();
				}
			}
		}
		// 本膜が変わったor指定回数繰り返したら、ロックを解放して終了
		synchronized(this) {
			mem.unlock();
			if (lockRequestCount > 0) {
				signal();
			}
		}
	}
	/** このタスク固有のルールスレッドが実行する処理 */
	public void run() {
		Membrane root = null;
		if (runtime instanceof MasterLMNtalRuntime) root = ((MasterLMNtalRuntime)runtime).getGlobalRoot();
		while (true) {
			if (root != null) { 	
				if (Env.fTrace) {
					Env.p( Dumper.dump(root) );
				}
			}
			while (true) {
				while (!isIdle()) {
					exec();
				}
				if (((LocalLMNtalRuntime)runtime).isTerminated()) return;
				if (root != null && root.isStable()) return;
				synchronized(this) {
					if (awakened) {
						awakened = false;
						continue;
					}
					try {
						wait();
					}
					catch (InterruptedException e) {}
					awakened = false;
				}
				break;
			}
		}	
	}

	////////////////////////////////////////////////////////////////

	// タスクのルールスレッドに対する停止要求

	/** このタスクのルールスレッドに対して停止要求を発行しているスレッドの個数
	 * todo LinkedListを使ってスレッドをキューで管理することにより飢餓を無くす。
	 * これには Membrane#blockingLock() を書き直すことが含まれる。*/
	private int lockRequestCount = 0;
	/** このタスクのルールスレッドに対して停止要求を発行する。
	 * <p>キューで管理していないことによる実装の都合により、
	 * 呼び出しは物理マシンに関するsynchronizedブロック内でなければならない（と思う）*/
	synchronized public void requestLock() {
		lockRequestCount++;
	}
	/** このタスクのルールスレッドに対して発行した停止要求を解除する。*/
	synchronized public void retractLock() {
		lockRequestCount--;
	}
	
	////////////////////////////////////////////////////////////////
	
	/** この抽象タスクのルールスレッドの再実行が要求されたかどうか */
	protected boolean awakened = false;

	/** このタスクに対してシグナルを発行する。
	 * すなわち、このタスクのルート膜のロックの取得をするためにブロックしているスレッドが存在するならば
	 * そのスレッドを再開してロックの取得を試みることを要求し、
	 * 存在しないならばこのタスクのルールスレッドの再実行を要求する。*/
	synchronized public final void signal() {
		awakened = true;
		notify();
	}

}
