package runtime;

import java.util.Iterator;
import util.Stack;

/** 抽象タスク */
abstract public class AbstractTask {
	/** 物理マシン */
	protected AbstractMachine runtime;
	/** ルート膜 */
	protected AbstractMembrane root;
	/** コンストラクタ
	 * @param runtime 実行される物理マシン */
	AbstractTask(AbstractMachine runtime) {
		this.runtime = runtime;
	}
	/** 物理マシンの取得 */
	public AbstractMachine getMachine() {
		return runtime;
	}
	/** ルート膜の取得 */
	public AbstractMembrane getRoot() {
		return root;
	}
}

/** タスク
 * TODO タスク間の上下関係の実装をする？
 * <p>
 * <b>方法6</b><br>
 * 膜のロックを取得しようとするスレッドは、どの膜のロックも取得していないか、
 * またはロックを取得しようとする膜の親膜のロックを取得していなければならない。
 * <p>
 * ルール適用を行うためのスレッドをルールスレッドと呼ぶ。
 * 現在ルールスレッドは物理マシンごとに1つとなっているが、理想的にはタスクごとに1つとすべきである。
 * <p>
 * ルールスレッドは、ルール適用時にタスクのロックを取得しようとする。
 * ルールスレッドは、膜のロック取得をノンブロッキングで行う。すでにロックされていた場合は放置する。
 * <p>
 * ルールスレッドでないスレッドは、膜のロック取得をブロッキングで行う。
 * 膜のロック取得をブロッキングで行う場合、そのタスクのロックも取得する。
 * タスクのロック取得に失敗した場合、そのタスクにロック要求を行う。
 * タスクが同じ物理マシンで実行されている場合、シグナルだけで処理されるためIDは不要。
 * タスクが他の物理マシンで実行されている場合、要求元スレッドのIDを適宜決定して送信する。
 * この場合、ロック解放時に発生するシグナルを受けてロックの再取得を試み続け、成功したらIDと共に返信する。
 * <p>
 * ルールスレッドでないスレッドは、短時間でロックを解放すべきである。
 * ルールスレッドは、そのタスクに対してロック要求があった場合には直ちに実行を停止すべきである。
 * ルールスレッドは、本膜を管理するタスク以外のタスクのロックは短時間で解放すべきである。
 */
final class Task extends AbstractTask {
	/** 実行膜スタック */
	Stack memStack = new Stack();
	Stack bufferedStack = new Stack();
	boolean idle = false;
	static final int maxLoop = 100;
	/** 親膜を持たない新しいルート膜および対応するタスクを作成する
	 * @param runtime 作成したタスクを実行する物理マシン */
	Task(AbstractMachine runtime) {
		super(runtime);
		root = new Membrane(this);
		memStack.push(root);
	}
	/** 指定した親膜を持つ新しいルート膜および対応するタスクを作成する
	 * @param runtime 作成したタスクを実行する物理マシン
	 * @param parent 親膜 */
	Task(AbstractMachine runtime, AbstractMembrane parent) {
		super(runtime);
		root = new Membrane(this);
		root.lock();
		root.activate(); 		// 仮の実行膜スタックに積む
		parent.addMem(root);	// タスクは膜の作成時に設定した
	}
	/** 親膜の無い膜を作成し、このタスクが管理する膜にする。 */
	Membrane createFreeMembrane() {
		return new Membrane(this);
	}
	
	boolean isIdle(){
		return idle;
	}
	/** このタスクを実行する */
	void exec() {
		// タスクのロックを取得する
		if (lockRequested || !nonblockingLock()) {
			// 本膜のロック以前に、タスクのロックを取得できないとき
			idle = true;
			return;
		}
		// 本膜のロックを取得する
		Membrane mem = (Membrane)memStack.peek();
		if(mem == null || !mem.lock()) {
			// 本膜が無いかまたは本膜のロックを取得できないとき
			idle = true;
			unlock();
			return;
		}
		// 実行
		for(int i=0; i < maxLoop && mem == memStack.peek() && !lockRequested; i++){
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
						Env.p( " ==> " );
						Env.p( Dumper.dump(getRoot()) );
					}
				}// システムコールアトムなら親膜につみ、親膜を活性化
			}else{ // 実行膜スタックが空の時
				flag = false;
				// アトム主導が無い現在、足し算を先に行うために、順番を変えてみた。
				// 本来はアトム主導により、+ がdequeueされた直後に、再帰呼び出しがdequeueされる。
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
						mem.getParent().activate();
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
				}					
			}
		}
		// 本膜が変わったor指定回数繰り返したら、ロックを解放して終了
		mem.unlock();
		unlock();
	}
	
	// ロック
	
	/** このタスクをロックしたスレッドまたはnull */
	private Thread lockingThread = null;
	/** ロックカウント */
	private int lockCount = 0;
	/** ルールスレッド以外のスレッドがこのタスクのロック取得を要求しているかどうか */
	private boolean lockRequested = false;
	/** このタスクのロックをノンブロッキングで取得する */
	synchronized public boolean nonblockingLock() {
		if (lockingThread == null) {
			lockingThread = Thread.currentThread();
			lockCount = 1;
			lockRequested = false;
			return true;
		}
		if (lockingThread == Thread.currentThread()) {
			lockCount++;
			return true;
		}
		return false;
	}
	/** このタスクのロックをブロッキングで取得し、ロックカウントを1増やす。*/
	synchronized public void lock() {
		while (true) {
			if (nonblockingLock()) return;
			lockRequested = true;
			try {
				wait();
			}
			catch (InterruptedException e) {}
		}
	}
	/** このタスクのロックカウントが正ならば1減らす。
	 * ロックカウントが0になった場合、ロックが解放されたことを意味するので、
	 * 待っているほかのスレッドがロックの再取得を試みることができるように自分自身にシグナルを発行する。
	 * @return ロックが解放されたかどうか */
	public boolean unlock() {
		if (lockCount == 0) return false;
		synchronized(this) {
			if (--lockCount == 0) {
				lockingThread = null;
				notify();
				return true;
			}
			return false;
		}
	}
}
