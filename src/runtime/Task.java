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

class Task extends AbstractTask implements Runnable {
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
		thread.start();
		try {
			thread.join();
		}
		catch (InterruptedException e) {}
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
		if (!Env.graphicTrace()) return false;
		
		return true;
	}
	/** このタスクの本膜のルールを実行する */
	void exec(Membrane mem) {
		// 実行
		for(int i=0; i < maxLoop && mem == memStack.peek() && lockRequestCount == 0; i++){
			// 本膜が変わらない間 & ループ回数を越えない間
			Atom a = mem.popReadyAtom();
			Iterator it = mem.rulesetIterator();
			boolean flag = false;
			if(Env.shuffle < Env.SHUFFLE_DONTUSEATOMSTACKS && a != null){ // 実行アトムスタックが空でないとき
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
					if (!trace("-->")) break;
				} else {
					if(!mem.isRoot()) {mem.getParent().enqueueAtom(a);} 
					// TODO システムコールアトムなら、本膜がルート膜でも親膜につみ、親膜を活性化
				}
			}else{ // 実行アトムスタックが空の時
				// 今のところ、システムルールセットは膜主導テストでしか実行されない。
				// 理想では、組み込みの + はインライン展開されるべきである。
				flag = SystemRulesets.react(mem);

				if (!flag) {				
					while(it.hasNext()){ // 膜主導テストを行う
						if(((Ruleset)it.next()).react(mem)) {
							flag = true;
							//if (memStack.peek() != mem) break;
							break; // ルールセットが変わっているかもしれないため
						}
					}
				}
				
				if(flag){
					if (!trace("==>")) break;
				} else {
					memStack.pop(); // 本膜をpop
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
				}
			}
		}

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
			if (root != null && Env.fTrace && asyncFlag) {
				//非ルールスレッドが変更した内容を出力する。
				asyncFlag = false;
				Env.p( " ==>* \n" + Dumper.dump(root) );
			}
			exec(mem);
			mem.unlock(true);

			synchronized(this) {
				running = false;
				//このタスクの停止を待っているスレッドを全て起こす。
				notifyAll();
			}
			if (root != null && root.isStable()) break;

			// 本膜のルール適用を終了しており、本膜がroot膜かつ親膜を持つなら、親膜を活性化
			if(memStack.isEmpty() && mem.isRoot()) {
				final AbstractMembrane memToActivate = mem.getParent();
				// この膜のロック解放前に親膜を活性化しても、親膜のルールがこの膜に適用できずに
				// 親膜が再び安定状態に入ることがあるため、この膜のロック解放後に親膜を活性化する。
				// そのとき、親膜がすでに無効になっていた場合、活性化要求は単純に無視すればよい。
				// todo stable フラグの処理は大丈夫か？ → asyncLock 内でタスクを止めているので、大丈夫。
				if (memToActivate != null) {
					// 親膜のロックを取得するまでブロックするのは
					// （特にマルチプロセッサ環境で）もったいないので別スレッドで実行する。
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

	////////////////////////////////////////////////////////////////

	// タスクのルールスレッドに対する停止要求

	/** このタスクのルールスレッドに対して停止要求を発行しているスレッドの個数
	 * todo LinkedListを使ってスレッドをキューで管理することにより飢餓を無くす。
	 * これには Membrane#blockingLock() を書き直すことが含まれる。*/
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
}
