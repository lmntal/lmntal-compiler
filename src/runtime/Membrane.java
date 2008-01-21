package runtime;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import util.QueuedEntity;
import util.RandomIterator;
import util.Stack;
import util.Util;

/**
 * ローカル膜クラス。実行時の、自計算ノード内にある膜を表す。
 * todo （効率改善） 最適化用に、子孫にルート膜を持つことができない（実行時エラーを出す）「データ膜」クラスを作る。
 * <p><b>排他制御</b></p>
 * <p>lockThread フィールドへの代入（すなわちロックの取得・解放）と、ロック解放の待ち合わせのために
 * このクラスのインスタンスに関する synchronized 節を利用する。
 * フィールドへの代入をする時は、特に記述がない限りこの膜のロックを取得している必要がある。
 * @author Mizuno, n-kato
 */
public final class Membrane extends QueuedEntity {
	/** この膜を管理するタスク。修正するときは、親膜のロックを取得している必要がある。 */
	protected Task task;
	/** 親膜。リモートにあるならばRemoteMembraneオブジェクトを参照する。GlobalRootならばnull。
	 * 修正するときは、親膜のロックを取得している必要がある。
	 * null を代入する（=この膜を除去する）時は、この膜と親膜の両方のロックを取得している必要がある。 */
	protected Membrane parent;
	/** アトムの集合 */
	protected AtomSet atoms = new AtomSet();
	/** 子膜の集合 */
	protected Set<Membrane> mems = null;
//	/** このセルの自由リンクの数 */
//	protected int freeLinkCount = 0;
	/** ルールセットの集合。 */
	protected List<Ruleset> rulesets = new ArrayList<Ruleset>();
	/** 膜のタイプ */
	protected int kind = 0;
	public static final int KIND_ND = 2;
	/** trueならばこの膜以下に適用できるルールが無い */
	protected boolean stable = false;
	/** 永続フラグ（trueならばルール適用できなくてもstableにならない）*/
	public boolean perpetual = false;
	/** この膜をロックしているスレッド。ロックされていないときはnullが入っている。*/
	protected Thread lockThread = null;

	private static int nextId = 0;
	private int id;
	
	/** 子膜->(コピー元のアトムin子膜->コピー先のアトムinコピーされた子膜)
	 * TODO 膜のメンバ変数でいいのかどうか */
	HashMap memToCopyMap = null; 
	
	/** この膜の名前（internされた文字列またはnull） */
	String name = null;
	public boolean equalName(String s){
		if(name == null && s == null)return true;
		else if(name != null && name != null)
			return name.equals(s);
		else return false;
	}
	public String getName() { return name; }
	public void setName(String name) { this.name = name; } // 仕様が固まったらコンストラクタで渡すようにすべきかも
	
	/** 実行アトムスタック。
	 * 操作する際にこの膜のロックを取得している必要はない。
	 * 排他制御には、Stack インスタンスに関する synchronized 節を利用している。 */
	private Stack ready = null;
	
//	/** リモートホストとの通信でこの膜のアトムを同定するときに使用されるatomidの表。
//	 * <p>atomid (String) -> Atom
//	 * <p>この膜のキャッシュ送信後、この膜の連続するロック期間中のみ有効。
//	 * キャッシュ送信時に初期化され、引き続くリモートホストからの要求を解釈するために使用される。
//	 * リモートホストからの要求で新しくアトムが作成されると、受信したNEW_をキーとするエントリが追加される。
//	 * $inside_proxyアトムの場合、命令ブロックの返答のタイミングでローカルIDで上書きされる。
//	 * $inside_proxy以外のアトムの場合、ロック解除までNEW_のまま放置される。
//	 * @see Atom.remoteid */
//	protected HashMap atomTable = null;

	///////////////////////////////
	// コンストラクタ

	/** 指定されたタスクに所属する膜を作成する。newMem/newRoot から呼ばれる。*/
	private Membrane(Task task, Membrane parent) {
		if (Env.shuffle >= Env.SHUFFLE_MEMS)
			mems = new RandomSet();
		else
			mems = new HashSet<Membrane>();
		
		this.task = task;
		this.parent = parent;
		id = nextId++;
	}
	/** 親膜を持たない膜を作成する。Task.createFreeMembrane から呼ばれる。*/
	protected Membrane(Task task) {
		this(task, null);
	}
	public Membrane() {
		this(null, null);
	}

	/** この膜のグローバルIDを取得する */
	public String getMemID() { return Integer.toString(id); }
//	/** この膜が所属する計算ノードにおける、この膜の指定されたアトムのIDを取得する */
//	public String getAtomID(Atom atom) { return atom.getLocalID(); }

	///////////////////////////////
	// 情報の取得

	/**
	 * デフォルトの実装だと処理系の内部状態が変わると変わってしまうので、
	 * インスタンスごとにユニークなidを用意してハッシュコードとして利用する。
	 */
	public int hashCode() {
		return id;
	}
	// 061028 okabe ランタイムは唯一つなのでGlobal/Local の区別は必要ない
//	/** この膜のローカルIDを取得する */
//	public String getLocalID() {  //publicなのはLMNtalDaemonから呼んでいるから→呼ばなくなったのでprotectedでよい
//		return Integer.toString(id);
//	}
	
	/** この膜を管理するタスクの取得 */
	public Task getTask() {
		return task;
	}
	/** 親膜の取得 */
	public Membrane getParent() {
		return parent;
	}
	
	/** AtomSetを配列形式で取得 */
	// 使ってる場所がないのでコメントアウト
//	public Atom[] getAtomSet() {
//		return (Atom[])atoms.toArray();
//	}
	/** 060727 */
	/** ルールセット数を取得 */
	public int getRulesetCount() {
		return rulesets.size();
	}
	public int getMemCount() {
		return mems.size();
	}
	/** proxy以外のアトムの数を取得
	 * todo この名前でいいのかどうか */
	public int getAtomCount() {
		return atoms.getNormalAtomCount();
	}
	/** 指定されたファンクタをもつアトムの数を取得*/
	public int getAtomCountOfFunctor(Functor functor) {
		return atoms.getAtomCountOfFunctor(functor);
	}
	/** このセルの自由リンクの数を取得 */
	public int getFreeLinkCount() {
		return atoms.getAtomCountOfFunctor(Functor.INSIDE_PROXY);
	}
	/** 膜のタイプを取得 */
	public int getKind() {
		return kind;
	}
	/** 膜のタイプを変更 */
	public void changeKind(int k) {
		kind = k;
		if (kind == KIND_ND) {
			//非決定的実行膜は、普通の方法では実行しない
			dequeue();
			toStable();
		}
	}
	/** この膜とその子孫に適用できるルールがない場合にtrue */
	public boolean isStable() {
		return stable;
	}
	/** stableフラグをONにする 10/26矢島 Task#exec()内で使う*/
	void toStable(){
		stable = true;
	}
	/** 永続フラグをONにする */
	public void makePerpetual(boolean f) {
		perpetual = f;
	}
//	/** 永続フラグをOFFにする */
//	public void makeNotPerpetual() {
//		AbstractLMNtalRuntime machine = getTask().getMachine();
//		synchronized(machine) {
//			perpetual = false;
//			machine.notify();
//		}
//	}
	/** この膜にルールがあればtrue */
	public boolean hasRules() {
		return !rulesets.isEmpty();
	}
	public boolean isRoot() {
		return task.getRoot() == this;
	}
	public boolean isNondeterministic() {
		return kind == KIND_ND;
	}
	
	// 反復子
	
	public Object[] getAtomArray() {
		return atoms.toArray();
	}
	public Object[] getMemArray() {
		return mems.toArray();
	}
	/** 06/07/27 */
	/** ルールセットのコピーを取得 */
	public ArrayList<Ruleset> getRuleset() {
		ArrayList<Ruleset> al = new ArrayList<Ruleset>(rulesets);
		return al;
	}
	/** 子膜のコピーを取得 */
	public HashSet getMemCopy() {
		return new HashSet(mems);
		//RandomSet s = new RandomSet();
		//s.addAll(mems);
		//return s;
	}
	/** この膜にあるアトムの反復子を取得する */
	public Iterator<Atom> atomIterator() {
		return atoms.iterator();
	}
	/** この膜にある子膜の反復子を取得する */
	public Iterator<Membrane> memIterator() {
		return mems.iterator();
	}
	/** 名前funcを持つアトムの反復子を取得する */
	public Iterator atomIteratorOfFunctor(Functor functor) {
		return atoms.iteratorOfFunctor(functor);
	}
	/** この膜にあるルールセットの反復子を返す */
	public Iterator rulesetIterator() {
		if (Env.shuffle >= Env.SHUFFLE_RULES) {
			return new RandomIterator(rulesets);
		} else {
			return rulesets.iterator();
		}
	}

	///////////////////////////////
	// ボディ操作（RemoteMembraneではオーバーライドされる）

	// ボディ操作1 - ルールの操作
	
	/** ルールを全て消去する */
	public void clearRules() {
		if(Env.profile == Env.PROFILE_ALL)
			Env.p(Dumper.dump(this));
		rulesets.clear();
	}
	/** srcMemにあるルールをこの膜にコピーする。 */
	public void copyRulesFrom(Membrane srcMem) {
		rulesets.addAll(srcMem.rulesets);
	}
	/** ルールセットを追加 */
	public void loadRuleset(Ruleset srcRuleset) {
		if(rulesets.contains(srcRuleset)) return;
		rulesets.add(srcRuleset);
	}

	// ボディ操作2 - アトムの操作

	/** 新しいアトムを作成し、この膜に追加する。*/
	public Atom newAtom(Functor functor) {
		Atom atom = new Atom(this, functor);
		onAddAtom(atom);
		if(Env.fUNYO){
			unyo.Mediator.addAddedAtom(atom);
		}
		return atom;
	}
	/** （所属膜を持たない）アトムをこの膜に追加する。*/
	public void addAtom(Atom atom) {
		atom.mem = this;
		onAddAtom(atom);
	}
	/** 指定されたアトムの名前を変える */
	public void alterAtomFunctor(Atom atom, Functor func) {
		
		atoms.remove(atom);
		atom.setFunctor(func);
		atoms.add(atom);
	}

	/** 指定されたアトムをこの膜から除去する。
	 * <strike>実行アトムスタックに入っている場合、スタックから取り除く。</strike>*/
	public void removeAtom(Atom atom) {
		if(Env.fUNYO){
			unyo.Mediator.addRemovedAtom(atom, getMemID());
		}
		atoms.remove(atom);
		atom.mem = null;
	}

	/** 
	 * 基底項プロセスかどうかを検査する．(Stackを使うように修正 2005/07/26)
	 * ( 2006/09/12 Membraneに移動・2引数以上のgroundを扱えるように拡張 )
	 * (それに伴い引数に受け取っていたSetを廃止)
	 * 基底項プロセスを構成するアトムの数を返す．
	 * // 引数には，(左辺出現アトム等)基底項プロセスに含まれてはいけないアトムのSetを指定する．
	 * 基底項プロセスに含まれてはいけないアトム及びその引数( つまりリンク )のSetを指定する．
	 * ただし、自由リンク管理アトムに出会った場合は、-1を返す．
	 * 
	 * 基底項プロセスは連結である必要がある為，走査開始は第1引数からのみでよい．
	 * 走査中に第1引数以外の引数に到達したらそこから先は走査せず，
	 * 走査済みの印をつけておく．
	 * 走査完了後，未到達の引数があれば-1を返す．
	 * 
	 * @param avoList 基底項プロセスに出てきてはいけないリンクのList
	 * @return 基底項プロセスを構成するアトム数
	 */
	public static int isGround(List<Link> links, Set<Atom> avoSet){
		Set<Atom> srcSet = new HashSet<Atom>(); // 走査済みアトム
		java.util.Stack<Link> s = new java.util.Stack<Link>(); //リンクを積むスタック
		s.push(links.get(0)); // 第1引数から走査する
		int c=0;
		boolean[] exists = new boolean[links.size()]; // 引数について到達したかどうか
		exists[0] = true; // 第1引数から辿る
		while(!s.isEmpty()){
			Link l = (Link)s.pop();
			Atom a = l.getAtom();
			if(srcSet.contains(a))continue; //既に辿ったアトム
			if(avoSet.contains(l.getBuddy()))return -1; //出現してはいけないリンク
			int argi = links.indexOf(l.getBuddy());
			if(argi >= 0){ // 基底項プロセスの引数に到達
				exists[argi] = true;
				continue; // それ以上走査しない
			}
			if(a.getFunctor().equals(Functor.INSIDE_PROXY)||
				a.getFunctor().isOutsideProxy()) // 自由リンク管理アトムに到達
				return -1; // 失敗
			c++;
			srcSet.add(a);
			for(int i=0;i<a.getArity();i++){
				if(i==l.getPos())continue;
				s.push(a.getArg(i));
			}
		}
		for(int i=0;i<links.size();i++)
			if(exists[i])continue;
			else return -1; // 未到達の根があれば失敗
		return c;
	}
	
	/**
	 * 同じ構造を持った基底項プロセスかどうか検査する
	 * ( Stackを使うように修正 2005/07/27 )
	 * ( それに伴い引数に受け取っていたMapを廃止)
	 * (膜に移動・ 2引数以上に対応 2006/09/13 )
	 * どちらか片方についてgroundかどうかの検査は済んでいるものとする
	 * 
	 * プロセス文脈としての引数位置も一致しなければならない
	 * 
	 * @param srcLink 比較対象のリンク
	 * @return
	 */
	public static boolean eqGround(List<Link> srclinks, List<Link> dstlinks){
		Map<Atom,Atom> map = new HashMap<Atom,Atom>(); //比較元アトムから比較先アトムへのマップ
		java.util.Stack<Link> s1 = new java.util.Stack<Link>();  //比較元リンクを入れるスタック
		java.util.Stack<Link> s2 = new java.util.Stack<Link>();  //比較先リンクを入れるスタック
		s1.push(srclinks.get(0));
		s2.push(dstlinks.get(0));
		while(!s1.isEmpty()){
			Link l1 = s1.pop();
			Link l2 = s2.pop();
			int srci = srclinks.indexOf(l1.getBuddy());
			int dsti = dstlinks.indexOf(l2.getBuddy());
			if(srci != dsti)return false; // プロセス文脈の引数だった場合(>=)の位置の一致，そうでない場合(-1)の確認
			if(srci >= 0) continue; // プロセス文脈の引数だったらそれ以上走査しない
			if(l1.getPos() != l2.getPos()) return false; //引数位置の一致を検査
			if(!l1.getAtom().getFunctor().equals(l2.getAtom().getFunctor()))return false; //ファンクタの一致を検査
			if(!map.containsKey(l1.getAtom()))map.put(l1.getAtom(),l2.getAtom()); //未出
			else if(map.get(l1.getAtom()) != l2.getAtom())return false;         //既出なれど不一致
			else continue;
			for(int i=0;i<l1.getAtom().getArity();i++){
				if(i==l1.getPos())continue;
				s1.push(l1.getAtom().getArg(i));
				s2.push(l2.getAtom().getArg(i));
			}
		}
		return true;
	}

	/**
	 * 基底項プロセスを複製する(検査は済んでいる)
	 * ( java.util.Stackを使うように変更し，それに伴い引数のMapを廃止 2005/07/28)
	 * ( 2引数以上に対応 2006/09/13 )
	 * 
	 * なお，2引数の場合には構成アトム数が0個(つまりリンク)の場合があるが，
	 * その場合は事前にinsertconnectors命令によって=/2が挿入されているものとする．
	 * つまり，このメソッドでは考慮する必要はない．
	 * 
	 * @param srcGround コピー元の基底項プロセス の根のリスト
	 * @return 2要素のリスト ( 第一要素 : コピー先の基底項プロセス の根のリスト, 第二要素 : コピー元のアトムからコピー先のアトムへのマップ)
	 */
	public List<Link> copyGroundFrom(List<Link> srclinks){
		java.util.Stack<Link> s = new java.util.Stack<Link>();
		Map<Atom,Atom> map = new HashMap<Atom,Atom>();
		Link first = (Link)srclinks.get(0);
		// 最初のアトムだけまず複製してしまう ( なぜこういう手順なのか？　下のループに持っていける気がする)
		Atom cpAtom = newAtom(first.getAtom().getFunctor());
		map.put(first.getAtom(),cpAtom);
		// 最初のアトムの引数を全てスタックに積む
		for(int i=0;i<cpAtom.getArity();i++){
			if(first.getPos()==i)continue;
			s.push(first.getAtom().getArg(i));
		}
		while(!s.isEmpty()){
			Link l = s.pop();
			int srci = srclinks.indexOf(l.getBuddy());
			if(srci >= 0)continue; // プロセス文脈の引数に到達したらそれ以上辿らない
			if(!map.containsKey(l.getAtom())){
				cpAtom = newAtom(l.getAtom().getFunctor());
				map.put(l.getAtom(),cpAtom);
				Atom a = ((Atom)map.get(l.getAtom().getArg(l.getPos()).getAtom())); //リンクの根
				a.args[l.getAtom().getArg(l.getPos()).getPos()]=new Link(cpAtom,l.getPos());
				for(int i=0;i<cpAtom.getArity();i++){
					s.push(l.getAtom().getArg(i));
				}
			}
			else{
				cpAtom = map.get(l.getAtom());
				Atom a = map.get(l.getAtom().getArg(l.getPos()).getAtom());
				a.args[l.getAtom().getArg(l.getPos()).getPos()]=new Link(cpAtom,l.getPos());
			}
		}
		List<Link> dstlinks = new ArrayList<Link>(srclinks.size());
		for(int i=0;i<srclinks.size();i++){
			Link srclink = srclinks.get(i);
			dstlinks.add( new Link(map.get(srclink.getAtom()),srclink.getPos()));
		}
		List ret_list = new ArrayList();
		ret_list.add(dstlinks);
		ret_list.add(map);
		return ret_list;//		return new Link(((Atom)map.get(srcGround.getAtom())),srcGround.getPos());
	}
	
	/** 1引数の基底項プロセスを複製する */
	public Link copyGroundFrom(Link srcGround){
		List<Link> srclinks = new ArrayList<Link>();
		srclinks.add(srcGround);
		List dstlinks = copyGroundFrom(srclinks);
		return (Link)dstlinks.get(0);
	}
	
	/** 指定された基底項プロセスをこの膜から除去する。 by kudo
	 * ( java.util.Stackを使うように修正し，伴って引数を修正 2005/08/01 )
	 * ( 2引数以上に対応 2006/09/13 )
	 * groundであることの検査は済んでいるものとする
	 * 
	 * @param srcGround
	 * @return
	 */
	public void removeGround(List<Link> srclinks){
		java.util.Stack<Link> s = new java.util.Stack<Link>();
		Link first = srclinks.get(0);
		s.push(first);
		Set<Atom> srcSet = new HashSet<Atom>();
		while(!s.isEmpty()){
			Link l = s.pop();
			int srci = srclinks.indexOf(l.getBuddy());
			if( srci >= 0 ) continue; // プロセス文脈の引数に到達したらそれ以上辿らない
			if(srcSet.contains(l.getAtom()))continue;
			srcSet.add(l.getAtom());
			for(int i=0;i<l.getAtom().getArity();i++){
				if(i==l.getPos())continue;
				s.push(l.getAtom().getArg(i));
			}

			atoms.remove(l.getAtom());
			l.getAtom().mem = null;
			l.getAtom().dequeue();
		}
	}
	
	/** 1引数の基底項プロセスをこの膜から除去する */
	public void removeGround(Link srcGround){
		List<Link> srclinks = new ArrayList<Link>();
		srclinks.add(srcGround);
		removeGround(srclinks);
	}
	
	
	/** [final] 1引数のnewAtomを呼び出すマクロ */
	final Atom newAtom(String name, int arity) {
		return newAtom(new SymbolFunctor(name, arity));
	}	
	/** [final] この膜にアトムを追加するための内部命令 */
	protected final void onAddAtom(Atom atom) {
		atoms.add(atom);
//		if (atom.getFunctor().isActive()) {
//			enqueueAtom(atom);
//		}
	}
	/** [final] removeAtomを呼び出すマクロ */
	final void removeAtoms(List atomlist) {
		// atoms.removeAll(atomlist);
		Iterator it = atomlist.iterator();
		while (it.hasNext()) {
			removeAtom((Atom)it.next());
		}
	}

//	/** 
//	 * この膜にあるアトムatomがこの計算ノードが実行するタスクにある膜の実行アトムスタック内にあれば、除去する。
//	 * 他の計算ノードが実行するタスクにある膜の実行アトムスタック内のとき（システムコール）は、この膜は
//	 * ロックされていないので何もしないでよいが、その場合は実行アトムスタック内にないので既に対応できている。*/
//	public final void dequeueAtom(Atom atom) {
//		if (atom.isQueued()) {
//			atom.dequeue();
//		}
//	}

	// ボディ操作3 - 子膜の操作
	/** [final] 指定された（親膜の無い）膜をこの膜の子膜として追加する。
	 * 実行膜スタックは操作しない。子膜のタスクについては何もしない。*/
	public final void addMem(Membrane mem) {
		mems.add(mem);
		mem.parent = this;
		
		if(Env.fUNYO){
			unyo.Mediator.addAddedMembrane(mem);
		}
	}
	/** 指定された子膜をこの膜から除去する。
	 * <strike>実行膜スタックは操作しない。</strike>
	 * 実行膜スタックに積まれていれば取り除く。 */
	public void removeMem(Membrane mem) {
		if(Env.LMNgraphic != null && !mem.isRoot())
			Env.LMNgraphic.removeGraphicMem(mem);
		if(Env.LMNtool != null && !mem.isRoot())
			Env.LMNtool.addRemovedMem(mem);		
		mems.remove(mem);
		mem.dequeue();
		mem.parent = null;
		
	}
	/** 指定されたノードで実行されるロックされたkind==kのルート膜を作成し、この膜の子膜にし、活性化する。
	 * @param nodedesc ノード名を表す文字列
	 * @return 作成されたルート膜 */
	public Membrane newRoot(String nodedesc, int k) {
		if(Env.debug > 0)Util.println("AbstractMembrane.newRoot(" + nodedesc + ")");
		if (nodedesc.equals("")) {
			Membrane mem = newMem();
			mem.changeKind(k);
			mem.lock();
			return mem;
		}
		//(nakajima 2004-10-25) 分散。とりあえずコンストラクタで登録する時にしたのでコメントアウト。
		//daemon.IDConverter.registerGlobalMembrane(this.getGlobalMemID(),this);
		
		// ↓todo (効率改善【除去可能であることを確かめる】)connectRuntimeはガードですでに呼ばれているので冗長かもしれない
//		AbstractLMNtalRuntime machine = LMNtalRuntimeManager.connectRuntime(nodedesc);
//		AbstractMembrane mem = machine.newTask(this).getRoot();
//		mem.changeKind(k);
		LMNtalRuntime machine = Env.theRuntime;
		Membrane mem = machine.newTask(this).getRoot();
		mem.changeKind(k);
		return mem;
	}
	/** 指定されたノードで実行されるロックされたkind==0のルート膜を作成し、この膜の子膜にし、活性化する。
	 * @param nodedesc ノード名を表す文字列
	 * @return 作成されたルート膜 */
	public Membrane newRoot(String nodedesc) {
		return newRoot(nodedesc, 0);
	}
	
	// ボディ操作4 - リンクの操作
	
	/**
	 * atom1の第pos1引数と、atom2の第pos2引数を接続する。
	 * 接続するアトムは、
	 * <ol><li>この膜のアトム同士
	 *     <li>この膜のoutside_proxy（atom1）と子膜のinside_proxy（atom2）
	 * </ol>
	 * の2通りに限られる。
	 */
	public void newLink(Atom atom1, int pos1, Atom atom2, int pos2) {

		atom1.args[pos1] = new Link(atom2, pos2);
		atom2.args[pos2] = new Link(atom1, pos1);

		if(Env.fUNYO){
			unyo.Mediator.addModifiedAtom(atom1);
			unyo.Mediator.addModifiedAtom(atom2);
		}
	}
	/** atom1の第pos1引数と、atom2の第pos2引数のリンク先を接続する。
	 * 実行後、atom2の第pos2引数は廃棄しなければならない。
	 * <p><font color=red><b>
	 * cloneは使わないくてよいはず。ただし当面はデバッグを容易にするためこのままでよい。
	 * </b></font>
	 */
	public void relinkAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1] = (Link)atom2.args[pos2].clone();
		atom2.args[pos2].getBuddy().set(atom1, pos1);

		if(Env.fUNYO){
			unyo.Mediator.addModifiedAtom(atom1);
			unyo.Mediator.addModifiedAtom(atom2);
		}
	}
	/** atom1の第pos1引数のリンク先と、atom2の第pos2引数のリンク先を接続する。*/
	public void unifyAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1].getBuddy().set(atom2.args[pos2]);
		atom2.args[pos2].getBuddy().set(atom1.args[pos1]);
	}
	/** atom1の第pos1引数と、atom2の第pos2引数を交換する。--ueda */
	public void swapAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		Link tmp = atom1.args[pos1];
		atom1.args[pos1] = atom2.args[pos2];
		atom2.args[pos2] = tmp;											
		atom1.args[pos1].getBuddy().set(atom1,pos1);
		atom2.args[pos2].getBuddy().set(atom2,pos2);
	}
	
	// 拡張
	
	/** deprecated */
	public void relink(Atom atom1, int pos1, Atom atom2, int pos2) {

		relinkAtomArgs(atom1, pos1, atom2, pos2);
	}
	/** link1の指すアトム引数とlink2の指すアトム引数の間に、双方向のリンクを張る。
	 * <p>実行後link1およびlink2自身は無効なリンクオブジェクトになるため、参照を使用してはならない。*/
	public void unifyLinkBuddies(Link link1, Link link2) {
		//link1.getBuddy().set(link2);
		//link2.getBuddy().set(link1);
		link1.getAtom().args[link1.getPos()] = link2;
		link2.getAtom().args[link2.getPos()] = link1;
	}
	/** atom1の第pos1引数と、リンクlink2のリンク先を接続する。
	 * <p>link2は再利用されるため、実行後link2の参照を使用してはならない。*/
	public void inheritLink(Atom atom1, int pos1, Link link2) {
		link2.getBuddy().set(atom1, pos1);
		atom1.args[pos1] = link2;
	}

	// 以下は AbstractMembrane の final メソッド
	
	/** [final] atom2の第pos2引数に格納されたリンクオブジェクトへの参照を取得する。*/
	public final Link getAtomArg(Atom atom2, int pos2) {
		return atom2.args[pos2];
	}	
	/** [final] relinkAtomArgsと同じ内部命令。ただしローカルのデータ構造のみ更新する。*/
	protected final void relinkLocalAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1] = (Link)atom2.args[pos2].clone();
		atom2.args[pos2].getBuddy().set(atom1, pos1);
	}
	/** [final] unifyAtomArgsと同じ内部命令。ただしローカルのデータ構造のみ更新する。*/
	protected final void unifyLocalAtomArgs(Atom atom1, int pos1, Atom atom2, int pos2) {
		atom1.args[pos1].getBuddy().set(atom2.args[pos2]);
		atom2.args[pos2].getBuddy().set(atom1.args[pos1]);
	}

	// ボディ操作5 - 膜自身や移動に関する操作
	
//	/** この膜を親膜から除去する
//	 * @deprecated */
//	public void remove() {
//		parent.removeMem(this);
//	}

	/** （親膜を持たない）膜srcMemにある全てのアトムと子膜（ロックを取得していない）をこの膜に移動する。
	 * 子膜はルート膜の直前の膜まで再帰的に移動される。ホスト間移動した膜は活性化される。
	 * このメソッド実行後、srcMemはこのまま廃棄されなければならない。
	 */
	public void moveCellsFrom(Membrane srcMem) {
		if (this == srcMem) return;
		// アトムの移動
		Iterator it = srcMem.atomIterator();
		while (it.hasNext()) {
			addAtom((Atom)it.next());
		}
		
		// 子膜の移動
		if (srcMem.task.getMachine() instanceof LMNtalRuntime) {
			// ローカル膜からの移動
			mems.addAll(srcMem.mems);
		}
		else {
			// リモート膜から（ローカル膜へ）の移動
			it = srcMem.memIterator();
			while (it.hasNext()) {
				Membrane subSrcMem = (Membrane)it.next();
				if (subSrcMem.isRoot()) {
					subSrcMem.moveTo(this);
				}
				else {
					Membrane subMem = newMem();
					if (!subSrcMem.blockingLock()) {
						throw new RuntimeException("Membrane.moveCellsFrom: blockingLock failure");
					}
					subMem.setName(subSrcMem.getName());
					removeMem(subMem);
					subMem.moveCellsFrom(subSrcMem);
					subSrcMem.unlock();
				}
			}
		}
		it = srcMem.memIterator();
		while (it.hasNext()) {
			Membrane subSrcMem = (Membrane)it.next();
			subSrcMem.parent = this;
			if (subSrcMem.task != task) subSrcMem.setTask(task);
		}
	}

	/** ロックされた（親膜の無い）この膜を（活性化された）膜dstMemに移動する。
	 * 子膜のロックは取得していないものとする。
	 * 子膜はルート膜の直前の膜まで再帰的に移動される。ホスト間移動した膜は活性化される。
	 * <p>メソッド終了後、thisは無効な膜を指している可能性がある。
	 * @return この膜への参照（リモート・ローカル間で移動した場合、変更している可能性がある）
	 */
	public Membrane moveTo(Membrane dstMem) {
		if (parent != null) {
			Util.errPrintln("Warning: membrane with parent was moved");
			parent.removeMem(this);
		} 
		if (dstMem instanceof Membrane) {
			// ローカル膜のローカル膜への移動
			dstMem.addMem(this);
			if (dstMem.task != task) {
				setTask(dstMem.task);
			}
			return this;
		}
		else {
			// ローカル膜のリモート膜への移動
			Membrane mem = dstMem.newMem();
			mem.moveCellsFrom(this);
			// thisは廃棄すべきである。下の TO-DO (A) の解決法はこのような新膜作成とmoveCellsFromらしい
			return mem;
		}
//		activate();
		//enqueueAllAtoms();
	}
	
	/** この膜とその子孫を管理するタスクを更新するために呼ばれる内部命令。
	 * ただしルート膜以下のタスクは変更しない。つまりルート膜に対して呼ばれた場合は何もしない。*/
	protected void setTask(Task newTask) {
		if (isRoot()) return;
		boolean locked = false;
		if (lockThread != Thread.currentThread()) {
			blockingLock();
			locked = true;
		}
		boolean queued = false;
		if (isQueued()) {
			//ロックしているので、この間でdequeueされている事はない。
			dequeue();
			queued = true;
		}
		task = newTask;
		if (queued)
			activate();
		Iterator it = memIterator();
		while (it.hasNext()) {
			((Membrane)it.next()).setTask(newTask);
		}
		if (locked)
			unlock();
		// TODO (A) ホスト間移動時にGlobalMembraneIDは変更しなくて大丈夫か調べる
	}
//	/** この膜（ルート膜）の親膜を変更する。LocalLMNtalRuntime（計算ノード）のみが呼ぶことができる。
//	 * <p>いずれ、
//	 * AbstractMembrane#newRootおよびAbstractMachine#newTaskの引数に親膜を渡すようにし、
//	 * AbstractMembrane#moveToを使って親膜を変更することにより、
//	 * todo この問題のあるメソッドは廃止しなければならない */
//	void setParent(AbstractMembrane mem) {
//		if (!isRoot()) {
//			throw new RuntimeException("setParent requires this be a root membrane");
//		}
//		parent = mem;
//	}

	//////////////////////////////////////////////////////////////
	// kudo
	
	/** この膜の複製を生成する <strike>明示的でない自由リンクが無いものと仮定</strile>
	 * */
//	public HashMap copyFrom(AbstractMembrane srcMem) {
//		int atomsize = srcMem.atoms.size(); //アトムの数
//		int memsize = srcMem.mems.size(); //子膜の数
//		List linkatom[] = new LinkedList[atomsize]; //リンク先のアトムのリスト
//		List linkpos[] = new LinkedList[atomsize]; //リンク先のポジションのリスト
//		//初期化
//		for (int i = 0; i < linkatom.length; i++) {
//			linkatom[i] = new LinkedList();
//			linkpos[i] = new LinkedList();
//		}
//
//		//子膜に繋がるリンクに関して（$outのみか→Yes (n-kato 2004-10-24)）。下で振った番号で使えるようにしておく。
//		//どの子膜の、どのアトムに繋がっているのかを示す。id番号。
//		int  glmemid[] = new int[atomsize];
//		int glatomid[] = new int[atomsize];
//		
//		//アトムにとりあえず番号を振る。Atom.idとは別。名前がよくないな。
//		Map atomId = new HashMap(); //Atom -> int
//		Atom idAtom[] = new Atom[atomsize]; //int -> Atom
//		int varcount = 0;
//		
//		//子膜にも番号を振る
//		Map memId = new HashMap(); // Mem -> int
//		AbstractMembrane idMem[] = new AbstractMembrane[memsize]; //int -> Mem
//		int memvarcount = 0;
//		
//		//リンク情報を取得
//		Iterator it = srcMem.atomIterator();
//		while (it.hasNext()) {
//			//リンク元アトム
//			Atom atomo = (Atom) it.next();//リンク元
//			if (!atomId.containsKey(atomo)) {
//				atomId.put(atomo, new Integer(varcount));
//				idAtom[varcount++] = atomo;
//			}
//			int o = ((Integer) atomId.get(atomo)).intValue();
//			//リンクを辿る
//			for (int i = 0; i < atomo.args.length; i++) {
//				Atom atoml = atomo.nthAtom(i);
//				if (atoml.mem == srcMem) { //局所リンク
//					if (!atomId.containsKey(atoml)) {
//						atomId.put(atoml, new Integer(varcount));
//						idAtom[varcount++] = atoml;
//					}
//					linkatom[o].add(i, atomId.get(atoml));
//					linkpos[o].add(i, new Integer(atomo.getArg(i).getPos()));
//				}else if(atoml.mem != null && atoml.mem.parent == srcMem){//子膜へのリンク
//					if(!memId.containsKey(atoml.mem)){
//						memId.put(atoml.mem,new Integer(memvarcount));
//						idMem[memvarcount++] = atoml.mem;
//					}
//					glmemid[o] = ((Integer)memId.get(atoml.mem)).intValue();
//					glatomid[o] = atoml.id; 
//					linkatom[o].add(i,null);
//					linkpos[o].add(i,new Integer(atomo.getArg(i).getPos()));
//				}else{//親膜ないしどこにも繋がっていない
//					linkatom[o].add(i,null);
//					linkpos[o].add(i,null);
//				}
//			}
//		}
//
//		//子膜のmap情報を得る
//		it = srcMem.memIterator();
//		while(it.hasNext()){
//			AbstractMembrane itm = (AbstractMembrane)it.next();
//			if(!memId.containsKey(itm)){
//				memId.put(itm,new Integer(memvarcount));
//				idMem[memvarcount++] = itm;
//			}
//		}
//
//
//		//子膜を再帰的にコピー(上と同時進行にすると同膜間コピーができない)
//		Map[] oldIdToNewAtom = new Map[memsize];
//		for(int i=0;i<memvarcount;i++){
//			oldIdToNewAtom[i] = newMem().copyFrom(idMem[i]);
//		}
//
//		HashMap retHashMap = new HashMap();//コピー元の$inのid -> コピー先の$inアトム
//
//		//アトムのコピーを作成
//		Atom[] idAtomCopied = new Atom[varcount];
//		for (int i = 0; i < varcount; i++) {
//			idAtomCopied[i] = newAtom(idAtom[i].getFunctor());
//		}
//
//		//リンクの貼りなおし
//		for (int i = 0; i < varcount; i++) {
//			for (int j = 0; j < linkatom[i].size();j++) {
//				if(linkatom[i].get(j) != null){
//					int l = ((Integer) linkatom[i].get(j)).intValue();
//					int lp = ((Integer) linkpos[i].get(j)).intValue();
//					newLink(idAtomCopied[i], j, idAtomCopied[l], lp);
//				}else{//リンク先が同じ膜に無い場合
//					if(idAtom[i].nthAtom(j).mem != null && idAtom[i].nthAtom(j).mem.parent == srcMem){//子膜に繋がっていた場合
//						Atom na = (Atom)oldIdToNewAtom[glmemid[i]].get(new Integer(glatomid[i]));
//						int lp = ((Integer) linkpos[i].get(j)).intValue();
//						newLink(idAtomCopied[i],j,na,lp);
//					}else{//親膜ないしどこにも繋がっていない、mapに追加
//						retHashMap.put(new Integer(idAtom[i].id),idAtomCopied[i]);
//					}
//				}
//			}
//		}
//		return retHashMap;
//	}
	
	
	/**
	 * 指定された膜より,その子膜及びアトムをこの膜にコピーする.
	 * 子膜のコピー時の戻り値のマップは,memToCopyMapに登録され
	 * 子膜のコピー先の膜は,memToCopiedMemに登録される.
	 * いずれもキーはコピー元の膜.
	 * アトムのコピーには,copyAtomsメソッドを再帰的に呼ぶ.
	 * 戻り値は,コピー元のアトムよりコピー先のアトムへのMap.
	 * 従来のcopyFromメソッドではキーはAtom.idのIntegerだったが,
	 * キーはAtomオブジェクトに変更になった.
	 * @param srcmem コピー元の膜
	 * @return コピー元のアトム->コピー先のアトムというMap
	 */
	public Map copyCellsFrom(Membrane srcmem){
		memToCopyMap = new HashMap();
		Iterator it = srcmem.memIterator();
		while(it.hasNext()){
			Membrane omem = (Membrane)it.next();
			Membrane nmem = newMem();
			nmem.setName(omem.getName());
			memToCopyMap.put(omem,nmem.copyCellsFrom(omem));
			nmem.copyRulesFrom(omem);
		}
		it = srcmem.atomIterator();
		Map oldAtomToNewAtom = new HashMap();
		while(it.hasNext()){
			Atom oatom = (Atom)it.next();
			if(oldAtomToNewAtom.containsKey(oatom))continue;
			oldAtomToNewAtom.put(oatom,newAtom(oatom.getFunctor()));
			//0引数アトムならば引数走査なし　(2006/05/26 kudo)
			if(oatom.getArity()>0)oldAtomToNewAtom = copyAtoms(oatom,oldAtomToNewAtom);
		}
		return oldAtomToNewAtom;
	}
	
	public Map copyCellsFrom2(Membrane srcmem){
		memToCopyMap = new HashMap();
		Iterator it = srcmem.memIterator();
		while(it.hasNext()){
			Membrane omem = (Membrane)it.next();
			Membrane nmem = newMem();
			nmem.setName(omem.getName());
			memToCopyMap.put(omem,nmem.copyCellsFrom(omem));
			nmem.copyRulesFrom(omem);
		}
		it = srcmem.atomIterator();
		Map oldAtomToNewAtom = new HashMap();
		while(it.hasNext()){
			Atom oatom = (Atom)it.next();
			if(oldAtomToNewAtom.containsKey(oatom))continue;
			if(oatom.getFunctor().getName()!="+" && !oatom.getFunctor().isInsideProxy()){
			    oldAtomToNewAtom.put(oatom,newAtom(oatom.getFunctor()));
				//0引数アトムならば引数走査なし　(2006/05/26 kudo)
				if(oatom.getArity()>0)
					oldAtomToNewAtom = copyAtoms(oatom,oldAtomToNewAtom);
			}
		}
		return oldAtomToNewAtom;
	}
	
	/**
	 * 指定されたアトムより,辿れるアトムを全てこの膜にコピーする.
	 * リンク構造も再現する.
	 * atomは,既にコピーされていることになっている.
	 * また,このatomの所属する膜の子膜は,既にコピーされておる.
	 * リンクが子膜に繋がっている場合は,memToCopyMapとmemToCopiedMemを駆使してリンクを繋ぐ.
	 * 親膜に繋がっている場合やどの膜にも所属していないアトムに繋がっている場合,無視する.
	 * mapは,コピー元のアトムからコピーされたアトムへの参照が登録されており,
	 * コピー済みのアトムかどうかの検査にも使用.
	 * @param atom
	 * @param map
	 * @return 更新された（かもしれない）Map
	 */
	public Map copyAtoms(Atom atom,Map map){
		for(int i=0;i<atom.args.length;i++){
			if(map.containsKey(atom.args[i].getAtom())){
				newLink(((Atom)map.get(atom)),i,
				((Atom)map.get(atom.args[i].getAtom())),atom.args[i].getPos());
			}
			else{
				if(atom.args[i].getAtom().mem != atom.mem){//リンク先がこの膜でない
					if(atom.args[i].getAtom().mem != null &&
					atom.args[i].getAtom().mem.parent == atom.mem){//子膜へ繋がっている
						//AbstractMembrane copiedmem = (AbstractMembrane)memToCopiedMem.get(atom.args[i].getAtom().mem);
						Map copymap = (Map)memToCopyMap.get(atom.args[i].getAtom().mem);
						Atom copiedatom = (Atom)copymap.get(atom.args[i].getAtom());
						newLink(((Atom)map.get(atom)),i,
						copiedatom,atom.args[i].getPos());
					}
					else continue;
				}
				else{
					Atom natom = newAtom(atom.args[i].getAtom().getFunctor());
					map.put(atom.args[i].getAtom(),natom);
					newLink(((Atom)map.get(atom)),i,
					natom,atom.args[i].getPos());
					map = copyAtoms(atom.args[i].getAtom(),map);
				}
			}
		}
		return map;
	}
	
	public void drop(){
		if (isRoot()) {
			// TODO kill this task
		}
		Iterator it = atomIterator();
		while(it.hasNext()){
			Atom atom = (Atom)it.next();
			atom.dequeue();
			// atom.free();
			// it.remove();
		}
		atoms.clear();
		it = memIterator();
		while(it.hasNext()){
			Membrane mem = (Membrane)it.next();
			mem.drop();
			mem.free();
		}
	}

//	/**
//	 * by kudo
//	 * 基底項プロセスを破棄する(検査は済んでいる)
//	 * @param srcGround 破棄する基底項プロセス
//	 */
//	public void dropGround(Link srcGround, Set srcSet){
//		if(srcSet.contains(srcGround.getAtom()))return;
//		srcSet.add(srcGround.getAtom());
//		for(int i=0;i<srcGround.getAtom().getArity();i++){
//			if(i==srcGround.getPos())continue;
//			dropGround(srcGround.getAtom().getArg(i),srcSet);
//		}
//		srcGround.getAtom().dequeue();
//	}
	
	////////////////////////////////////////////////////////////////
	// ロックに関する操作 - ガード命令は管理するtaskに直接転送される
	
	/**
	 * 現在この膜をロックしているスレッドを取得する。
	 */
	public Thread getLockThread() {
		return lockThread;
	}
	
	// - ガード命令
	
	// - ボディ命令


	///////////////////////////////
	// 自由リンク管理アトムの張り替えをするための操作
	// todo （効率改善）starをキューで管理することにより、alterAtomFunctorの回数を減らすとよいかも知れない。
	// キューはLinkedListオブジェクトとし、react内を生存期間とし、star関連のメソッドの引数に渡される。
	// $pを含む全ての膜の本膜からの相対関係がルール適用で不変な場合、
	// $pの先祖の全ての膜をうまく再利用することによって、star関連の処理を全く呼ぶ必要がなくなる。
	
	// todo （効率改善）LinkedListオブジェクトに対してcontainsを呼んでいるのを何とかする
	
	/** この膜がremoveされた直後に呼ばれる。
	 * なおremoveProxiesは、ルール左辺に書かれたアトムを除去した後、
	 * ルール左辺に書かれた膜のうち$pを持つものに対して内側の膜から呼ばれる。
	 * <p>この膜に対して
	 * <ol>
	 * <li>この膜の自由/局所リンクでないにもかかわらずこの膜内を通過しているリンクを除去し（例:V=A）
	 * <li>この膜の自由リンクが出現するアトム（ただし$in,$outのいずれか）の名前をstarに変える。
	 * </ol>
	 * <p>すべてのremoveProxiesの呼び出しが終了すると
	 * <ul>
	 * <li>$pにマッチしたプロセスの自由リンクは$pが書かれた膜のstarアトムに出現するようになり、
	 * <li>starアトムのリンク先は、starアトムまたは本膜のoutside_proxyの第1引数になっている。
	 * </ul>
	 * <pre>
	 * ( {{$p},$q},{$r} :- ... )
	 *     {{a(i(A),i(X))},  b(i(B),o(X)),i(V)=o(A)}, {d(i(W))}, c(o(V)),o(B)=o(W)
	 * --> *{a(s(A),s(X))}; {b(i(B),o(X)),i(V)=o(A)}, {d(i(W))}, c(o(V)),o(B)=o(W)
	 * -->  {a(s(A),s(X))};*{b(s(B),s(X))          }; {d(i(W))}, c(o(A)),o(B)=o(W)
	 * -->  {a(s(A),s(X))}; {b(s(B),s(X))          };*{d(s(W))}; c(o(A)),o(B)=o(W)
	 * 
	 * ( {$p} :- ... )
	 *      {a(i(X))}, b(o(X))
	 * --> *{a(s(X))}; b(o(X))
	 * 
	 * 
	 * ( {$p[i(A)|*V]},{$q[i(B)|*W]},E=o(A),F=o(B) :- ... )
	 *                                      {a(i(A)),b(i(B))}, {c(i(A')),d(i(B'))}, o(A)=o(A'),o(B)=o(B')
	 * --> AA=i(A),BB=i(B'),E=o(A),F=o(B'); {a( AA ),b(i(B))}, {c(i(A')),d( BB  )}, E=o(A'),F=o(B)
	 * -->>AA=i(A),BB=i(B'),E=o(A),F=o(B');*{a( AA ),b(s(B))};*{c(s(A')),d( BB  )}; E=o(A'),F=o(B)
	 * </pre>
	 */
	public void removeProxies() {
		// NOTE atomsへの操作が必要になるので、Setのクローンを取得してその反復子を使った方が
		//      読みやすい＆効率が良いかもしれない ←リモートの場合に適用するのは難しいかも知れない
		LinkedList changeList = new LinkedList();	// star化するアトムのリスト
		LinkedList removeList = new LinkedList();
		Iterator it = atoms.iteratorOfOUTSIDE_PROXY();
		while (it.hasNext()) {
			Atom outside = (Atom)it.next();
			Atom a0 = outside.args[0].getAtom();
			// outsideのリンク先が子膜でない場合【この検査のためにremoveで parent=null; が必要】			
			if (a0.getMem().getParent() != this) {
				Atom a1 = outside.args[1].getAtom();
				// この膜を通過して親膜に出ていくリンクを除去
				if (a1.getFunctor().equals(Functor.INSIDE_PROXY)) {
					unifyLocalAtomArgs(outside, 0, a1, 0);
					removeList.add(outside);
					removeList.add(a1);
				}
				else {
					// この膜を通過して無関係な膜に入っていくリンクを除去
					if (a1.getFunctor().isOutsideProxy()
					 && a1.args[0].getAtom().getMem().getParent() != this) {
						if (!removeList.contains(outside)) {
							unifyLocalAtomArgs(outside, 0, a1, 0);
							removeList.add(outside);
							removeList.add(a1);
						}
					}
					// それ以外のリンクは、この膜の自由リンクなので名前をstarに変える
					else {
						changeList.add(outside);
					}
				}
			}
		}
		removeAtoms(removeList);
		// この膜のinside_proxyアトムの名前をstarに変える
		it = atoms.iteratorOfFunctor(Functor.INSIDE_PROXY);
		while (it.hasNext()) {
			changeList.add(it.next());
		}
		// star化を実行する
		it = changeList.iterator();
		while (it.hasNext()) {
			alterAtomFunctor((Atom)it.next(), Functor.STAR);
		}
	}
	/** 左辺に$pが2個以上あるルールで、左辺の全てのremoveProxiesが呼ばれた後に
	 * 本膜に対して呼ぶことができる（呼ばなくてもよい）。
	 * <p>この膜を（2つのoutside経由で）通過して無関係な膜に入っていくリンクを除去する。
	 * <pre>
	 *      {a(s(A),s(X))}; {b(s(B),s(X))          }; {d(s(W))}; c(o(A)),o(B)=o(W)
	 * -->  {a(s(A),s(X))}; {b(s(B),s(X))          }; {d(s(B))}; c(o(A))
	 * 
	 *      {a(s(X))}; b(o(X))
	 * -->  {a(s(X))}; b(o(X))
	 * 
	 *      AA=i(A),BB=i(B'),E=o(A),F=o(B'); {a( AA ),b(s(B))}; {c(s(A')),d( BB  )}; E=o(A'),F=o(B)
	 * -->  変化なし
	 * </pre>
	 */
	public void removeToplevelProxies() {
		// (*) は ( {$p[i(A)]},{$q[|*V]},E=o(A) :- ... ) でEが*Vに含まれる場合などへの対策（安全側に近似）
		ArrayList removeList = new ArrayList();
		Iterator it = atoms.iteratorOfOUTSIDE_PROXY();
		while (it.hasNext()) {
			Atom outside = (Atom)it.next();
			// outsideの第1引数のリンク先が子膜でない場合			
			if (outside.args[0].getAtom().getMem() != null // 追加 n-kato 2004-10-30 (*)
			 && outside.args[0].getAtom().getMem().getParent() != this) {
				// outsideの第2引数のリンク先がoutsideの場合
				Atom a1 = outside.args[1].getAtom();
				if (a1.getFunctor().isOutsideProxy()) {
					// 2つめのoutsideの第1引数のリンク先が子膜でない場合
					if (a1.args[0].getAtom().getMem() != null // 追加 n-kato 2004-10-30 (*)
					 && a1.args[0].getAtom().getMem().getParent() != this) {
						if (!removeList.contains(outside)) {
							unifyLocalAtomArgs(outside, 0, a1, 0);
							removeList.add(outside);
							removeList.add(a1);
						}
					}
				}
			}
		}
		removeAtoms(removeList);
	}
	/** 右辺の（引数に指定した子膜セルの）膜構造および$pの内容を配置した後で、
	 * ルール右辺に書かれた膜と本膜に対して内側の膜から呼ばれる。
	 * <p>子膜childMemWithStarにあるstarアトム（子膜の自由リンクがつながっている）に対して
	 * <ol>
	 * <li>名前をinside_proxyに変え
	 * <li>自由リンクの反対側のアトムの出現する膜の位置にしたがって次のように場合分けをする：
	 *   <ul>
	 *   <li>この膜のstarアトムならば、
	 *       後者の名前をoutside_proxyに変える（最初のstarアトムと対応させる）。
	 *       また、分散実行のために、このリンクを張りなおす。（例:X,Y）
	 *   <li>この膜（本膜）に残ったoutside_proxyアトムならば、何もしない。
	 *       これはリンクがルールの左辺にマッチしたプロセスの自由リンクのときに起こる。（例:V）
	 *   <li>同じ子膜にある（star）アトムならば、2つのstarを削除する。（例:B,C）（追加 2004/1/18）
	 *   <li>それ以外の膜にあるアトムならば、
	 *       自由リンクがこの膜を通過するようにする。（例:A->V,E->W）
	 *       このとき、この膜に作成するoutside_proxyでない方のアトムの名前はstarにする。
	 *   </ul>
	 * </ol>
	 * @param childMemWithStar （自由リンクを持つ）子膜
	 * <pre>
	 * ( ... :- {{$p},$q,$r} )
	 *      {a(s(A),s(X))}; {b(s(B),s(X)),         }; {d(s(B))}; c(o(A))
	 * -->  {a(s(A),s(X))}; {b(s(B),s(X)),             d(s(B))}, c(o(A))
	 * -->{*{a(i(A),i(X))},  b(s(B),o(X)),s(V)=o(A),   d(s(B))}, c(o(V))
	 * -->*{{a(i(A),i(X))},  b(  B ,o(X)),i(V)=o(A),   d(  B )}, c(o(V))
	 * 
	 * ( ... :- {{$p},$q},{$r} )
	 *      {a(s(A),s(X))}; {b(s(Y),s(X)),         }; {d(s(E))}; c(o(A))
	 * -->  {a(s(A),s(X))}; {b(s(Y),s(X)),         },*{d(i(W))}, c(o(A)),s(E)=o(W)
	 * -->{*{a(i(A),i(X))},  b(s(Y),o(X)),s(V)=o(A)}, {d(i(W))}, c(o(V)),s(E)=o(W)
	 * -->*{{a(i(A),i(X))},  b(i(Y),o(X)),i(V)=o(A)}, {d(i(W))}, c(o(V)),o(Y)=o(W)
	 * 
	 * ( ... :- {$p,$q,$r} )
	 *      {a(s(V),s(B))}; {b(s(C),s(B)),         }; {d(s(C))}; c(o(V))
	 * -->  {a(s(V),s(B)),   b(s(C),s(B)),             d(s(C))}, c(o(V))
	 * -->  {a(s(V),  B ),   b(  C ,  B ),             d(  C )}, c(o(V))
	 *
	 * ( ... :- {$p} )
	 *      {a(s(V))}; b(o(V))
	 * -->  {a(i(V))}, b(o(V))
	 * 
	 * ( ... :- {$p[i(A)|*V],$q[i(B)|*W]},E=o(A),F=o(B) )
	 *      AA=i(A),BB=i(B'),E=o(A),F=o(B'); {a( AA ),b(s(B))}; {c(s(A')),d( BB  )}; E=o(A'),F=o(B)
	 * -->  AA=i(A),BB=i(B'),E=o(A),F=o(B');*{a( AA ),b(i(B))},*{c(i(A')),d( BB  )}, E=o(A'),F=o(B)
	 * </pre>
	 */
	public void insertProxies(Membrane childMemWithStar) {
		LinkedList changeList = new LinkedList();	// inside_proxy化するアトムのリスト
		LinkedList removeList = new LinkedList();
		Iterator it = childMemWithStar.atomIteratorOfFunctor(Functor.STAR);
		while (it.hasNext()) {
			Atom star = (Atom)it.next(); // n
			Atom oldstar = star.args[0].getAtom();
			if (oldstar.getMem() == childMemWithStar) { // 膜内の新しい局所リンクの場合
				if (!removeList.contains(star)) {
					childMemWithStar.unifyAtomArgs(star,1,oldstar,1);
					removeList.add(star);
					removeList.add(oldstar);
				}
			} else {
				changeList.add(star);
				// 自由リンクの反対側の出現がこの膜のアトムならば、後者の名前をoutside_proxyに変える。
				// このときstarが消えるかもしれないので、starをキューで実装するときはバグに注意。
				if (oldstar.getMem() == this) {
					//changeList.add(star);
					alterAtomFunctor(oldstar, new SpecialFunctor("$out",2, childMemWithStar.kind));
					newLink(oldstar, 0, star, 0);
				} else {
					Atom outside = newAtom(new SpecialFunctor("$out",2, childMemWithStar.kind)); // o
					Atom newstar = newAtom(Functor.STAR); // m
					newLink(newstar, 1, outside, 1);
					relinkAtomArgs(newstar, 0, star, 0); // これによりstar[0]が無効になる
					newLink(star, 0, outside, 0);
				}
			}
		}
		it = changeList.iterator();
		while (it.hasNext()) {
			childMemWithStar.alterAtomFunctor((Atom)it.next(), Functor.INSIDE_PROXY);
		}
		childMemWithStar.removeAtoms(removeList);		
	}
	/** 右辺のトップレベルに$pがあるルールの実行時、最後に本膜に残ったstarを処理するために呼ばれる。
	 * <p>この膜にあるstarに対して、
	 * 反対側の出現であるoutside_proxyまたはstarとともに除去し第2引数同士を直結する。
	 * このうち前者は、リンクがルールの左辺にマッチしたプロセスの自由リンクのときに起こる。（例:V）
	 * <pre>
	 * ( ... :-  $p,$q,$r  )
	 *      {a(s(V),s(B))}; {b(s(C),s(B)),         }; {d(s(C))}; c(o(V))
	 * -->   a(s(V),s(B)),   b(s(C),s(B)),             d(s(C)),  c(o(V))
	 * -->   a(  V ,  B ),   b(  C ,  B ),             d(  C ),  c(  V )
	 * 
	 * ( ... :- $p )
	 *      {a(s(V))}; b(o(V))
	 * -->   a(s(V)),  b(o(V))
	 * -->   a(  V ),  b(  V )
	 * </pre>
	 */
	public void removeTemporaryProxies() {
		LinkedList removeList = new LinkedList();
		Iterator it = atomIteratorOfFunctor(Functor.STAR);
		while (it.hasNext()) {
			Atom star = (Atom)it.next();
			Atom outside = star.args[0].getAtom();
			if (!removeList.contains(star)) {
				unifyLocalAtomArgs(star,1,outside,1);
				removeList.add(star);
				removeList.add(outside);
			}
		}
		removeAtoms(removeList);
	}
	
	/**
	 * {} なしで出力する。
	 * 
	 * // ルールの出力の際、{} アリだと
	 * // (a:-b) が ({a}:-{b}) になっちゃうから。
	 * 
	 * @return String 
	 * @deprecated
	 */
	public String toStringWithoutBrace() {
		return Dumper.dump(this);		
	}
	
	public String toString() {
		return "{ " + toStringWithoutBrace() + " }";
	}

	/**
	 * 膜をエンコードする．
	 * @return String 膜のコンパイル可能な文字列表現
	 */
	public String encode() {
		return "{" + Dumper.encode(this, true, 0) + "}";
	}
	/**
	 * 膜をエンコードする．ただしルールセットのみ．
	 * @return String 膜のコンパイル可能な文字列表現
	 */
	public String encodeRulesets() {
		return "{" + Dumper.encode(this, true, 1) + "}";
	}
	/**
	 * 膜をエンコードする．ただしルールセットは除く．
	 * @return String 膜のコンパイル可能な文字列表現
	 */
	public String encodeProcess() {
		return "{" + Dumper.encode(this, true, 2) + "}";
	}
		
	////////////////////////////////////////
	// non deterministic LMNtal
	AtomSet getAtoms() {
		return atoms;
	}
	///////////////////////////////
	// ボディ操作

	// ボディ操作1 - ルールの操作
	
//	/** ルールを全て消去する */
//	public void clearRules() {
//		if (remote == null) super.clearRules();
//		else remote.send("CLEARRULES",this);
//	}
//	/** srcMemにあるルールをこの膜にコピーする。 */
//	public void copyRulesFrom(AbstractMembrane srcMem) {
//		if (remote == null) super.copyRulesFrom(srcMem);
//		else remote.send("COPYRULESFROM",this,srcMem.getGlobalMemID());
//		// todo RemoteMembraneのやり方（最初の段階でLOADRULESETに展開する方式）に合わせる
//	}
//	/** ルールセットを追加 */
//	public void loadRuleset(Ruleset srcRuleset) {
//		if (remote == null) super.loadRuleset(srcRuleset);
//		else remote.send("LOADRULESET",this,srcRuleset.getGlobalRulesetID());
//	}

	// ボディ操作2 - アトムの操作

//	/** 新しいアトムを作成し、この膜に追加する。*/
//	public Atom newAtom(Functor functor) {
//		if (remote == null) return super.newAtom(functor);
//		else remote.send("NEWATOM",this,functor.toString());
//		return null;	// todo なんとかする（local-remote-local 問題）
//	}
//	/** （所属膜を持たない）アトムをこの膜に追加する。*/
//	public void addAtom(Atom atom) {
//		if (remote == null) super.addAtom(atom);
//		else remote.send("ADDATOM", this);
//	}
//	/** 指定されたアトムの名前を変える */
//	public void alterAtomFunctor(Atom atom, Functor func) {
//		if (remote == null) super.alterAtomFunctor(atom,func);
//		else remote.send("ALTERATOMFUNCTOR", this, atom + " " + func.serialize());
//	}

	/** 
	 * 指定されたアトムをこの膜の実行アトムスタックに追加する。
	 * すでにスタックに積まれている場合の動作は未定義とする。
	 * @param atom 実行アトムスタックに追加するアトム
	 */
	public void enqueueAtom(Atom atom) {
		if(null == ready){ ready = new Stack(); }
		ready.push(atom);
	}
	/** この膜が移動された後、アクティブアトムを実行アトムスタックに入れるために呼び出される。
	 * <p>Ruby版ではmovedTo(task,dstMem)を再帰呼び出ししていたが、
	 * キューし直すべきかどうかの判断の手間が掛かりすぎるため子孫の膜に対する処理は廃止された。 
	 * <p>
	 * 現在このメソッドを使っている場所はない。(2005/11/30 mizuno)
	 * <p>
	 * 移動された後、この膜のアクティブアトムを実行アトムスタックに入れるために呼び出される。
	 * <p><b>注意</b>　Ruby版のmovedtoと異なり、子孫の膜にあるアトムに対しては何もしない。*/
	public void enqueueAllAtoms() {
		Iterator i = atoms.activeFunctorIterator();
		while (i.hasNext()) {
			Functor f = (Functor)i.next();
			if (f.isActive()) {
				Iterator i2 = atoms.iteratorOfFunctor(f);
				while (i2.hasNext()) {
					if(null == ready){ ready = new Stack(); }
					Atom a = (Atom)i2.next();
					a.dequeue();
					ready.push(a);
				}
			}
		}
	}
//
//	/** 指定されたアトムをこの膜から除去する。
//	 * <strike>実行アトムスタックに入っている場合、スタックから取り除く。</strike>*/
//	public void removeAtom(Atom atom) {
//		if(Env.fGUI) {
//			Env.gui.lmnPanel.getGraphLayout().removedAtomPos.add(atom.getPosition());
//		}
//		atoms.remove(atom);
//		atom.mem = null;
//	}

	// ボディ操作3 - 子膜の操作

	/** 新しいタイプがkの子膜を作成し、活性化する */
	public Membrane newMem(int k){
	//		if (remote != null) {
	//		remote.send("NEWMEM",this);
	//		return null; // todo
	//	}
		Membrane m = new Membrane(task, this);
		m.changeKind(k);
		mems.add(m);
		// 親膜と同じ実行膜スタックに積む
		if (k != KIND_ND)
			stack.push(m);

		if(Env.fUNYO){
			unyo.Mediator.addAddedMembrane(m);
		}
		return m;
	}
	/** 新しいデフォルトタイプの子膜を作成し、活性化する */
	public Membrane newMem() {
		return newMem(0);
	}
	
//	/** 指定された子膜をこの膜から除去する。
//	 * <strike>実行膜スタックは操作しない。</strike>
//	 * 実行膜スタックに積まれていれば取り除く。 */
//	public void removeMem(AbstractMembrane mem) {
//		if (remote == null) super.removeMem(mem);
//		else remote.send("REMOVEMEM", this, mem.getGlobalMemID());
//	}
//	/** 指定されたノードで実行されるロックされたルート膜を作成し、この膜の子膜にし、活性化する。
//	 * @param node ノード名を表す文字列
//	 * @return 作成されたルート膜
//	 */
//	public AbstractMembrane newRoot(String node) {
//		if (remote == null) return super.newRoot(node);
//		else remote.send("NEWROOT", this, node);
//		return null;	// todo なんとかする（local-remote-local 問題）
//	}
	
	// ボディ操作5 - 膜自身や移動に関する操作

	/** 活性化する。
	 * <p>
	 * <dl>
	 * <dt><b>ルート膜の場合</b>:<dd>
	 * このタスクの仮の実行膜スタックの唯一の要素として積む。
	 * <dt><b>ルート膜でない場合</b>:<dd>
	 * 親膜を活性化した後、親膜と同じスタックに入れる。
	 * <strike>すなわち、仮の実行膜スタックが空でなければ仮の実行膜スタックに積み、
	 * 空ならば実行膜スタックに積む。</strike>
	 * ただし、親膜が実行膜スタックに積まれていた場合、すでに
	 * （実行膜スタックに）積まれていたら何もしない。
	 * </dl>
	 */
	public void activate() {
		if (isNondeterministic()) return;
		stable = false;
		Task t = (Task)task;
		if (isRoot()) {
			dequeue();
			t.bufferedStack.push(this);
		} else {
			Stack s = ((Membrane)parent).activate2();
			if (s == t.bufferedStack) {
				dequeue();
				s.push(this);
			} else {
				//方法7では、この膜をロックしていれば非同期にpushされている事はない。
				//方法8では、下2行をsynchr`onized(memStack)内に入れる。
				if (!isQueued())
					s.push(this);
			}
		}
	}
	/** 
	 * activate 内で親膜を活性化するときに利用する。
	 * すでにスタックに積まれているときは何しない。
	 * @return この要素が積まれているスタック。task.memStack か task.bufferedStack の一方。
	 */
	private Stack activate2() {
		Task t = (Task)task;
		//方法7では、この膜をロックしていれば非同期にpushされている事はない。
		//方法8では、この下を全てsynchronized(memStack)内に入れる。
		if (isQueued())
			return stack;
		if (isRoot()) {
			// ASSERT(t.bufferedStack.isEmpty());
			t.bufferedStack.push(this);
			return t.bufferedStack;
		} else {
			Stack s = ((Membrane)parent).activate2();
			s.push(this);
			return s;
		}
	}	
	
	// ロックに関する操作 - ガード命令は管理するtaskに直接転送される
	
	// - ガード命令
	
	/**
	 * この膜のロック取得を試みる。
	 * <p>ルールスレッドまたはdumperがこの膜のロックを取得するときに使用する。
	 * <p>ルールスレッドは、ロック解放にはunlock()を使用すること。
	 * <p>dumperは、ロック解放にはquietUnlock()を使用すること。
	 * <p>成功したら親膜のリモートを継承する。
	 * したがってルールスレッドは、本膜をロックした場合ただちにリモートをnullに設定すること。
	 * @return ロックの取得に成功したかどうか */
	synchronized public boolean lock() {
		if (lockThread != null) {
			return false;
		} else {
			lockThread = Thread.currentThread();
			//除去された膜はロックされているので、parent==null になるのはグローバルルートのみ
//			if (parent != null) remote = parent.remote;
			return true;
		}
	}
	/**
	 * この膜のロック取得を試みる。
	 * 失敗した場合、この膜を管理するタスクのルールスレッドに停止要求を送る。その後、
	 * このタスクがシグナルを発行するのを待ってから、再びロック取得を試みることを繰り返す。
	 * <p>ルールスレッド以外のスレッドがこの膜のロックを2つ目以降のロックとして取得するときに使用する。
	 * <p>ロック解放にはunlock()を使用すること。
	 * 親膜のロックはすでに取得している必要がある。
	 * @return 常にtrue */
	public boolean blockingLock() {
		//親膜のロックを取得しているので、タスクが変化したりこの膜が除去されたりする事はない。
		Task t = (Task)task;
		boolean stopped = false;
		while (true) {
			if (lockThread == t.thread) {
				//管理タスクのルールスレッドがロックしているかもしれない時は停止要求を送る
				//suspend を呼んだときはすでに解放済みかもしれないが、問題はない。
				//タスクに関する synchronized 節を利用するので、下の synchronized 節に入れる事はできない。
				t.suspend();
				stopped = true;
			}
			synchronized(this) {
				if (lock()) {
					break;
				} else {
					//非ルールスレッドや親タスクなら解放するのを待つ
					try {
						wait();
					} catch (InterruptedException e) {}
				}
			}
		}
		if (stopped)
			t.resume();
		return true;
	}
	/**
	 * この膜からこの膜を管理するタスクのルート膜までの全ての膜のロックをブロッキングで取得し、実行膜スタックから除去する。
     * ルート膜ならばblockingLock()と同じになる
	 * <p>ルールスレッド以外のスレッドがこの膜のロックを最初のロックとして取得するときに使用する。
	 * <p>成功したらリモートをnullに設定する。
	 * <p>ロック解放にはasyncUnlock()を使用すること。
	 * @return 成功したら true。失敗するのは、この膜がすでにremoveされている場合のみ。 */
	public boolean asyncLock() {
		Task t = (Task)task;
		Membrane root = t.getRoot();;
		//blockingLock と違い、停止するまで待つ必要がある。
		t.suspend();
		synchronized(this) {
			while (true) {
				boolean ret = root.lock();
				if (parent == null || t != task) {
					//状況が変化していた場合のキャンセル処理
					if (ret) {
						root.activate();
						root.unlock();
					}
					t.resume();
	
					if (parent == null) {
						//この膜が除去された
						return false;
					} else {
						//所属タスクが変化していた
						t = (Task)task;
						root = t.getRoot();
						t.suspend();
					}
				} else if (ret) {
					//ルート膜のロックに成功。この膜からルート膜までの間を全てロックする。
					for (Membrane mem = this; mem != root; mem = mem.parent) {
						ret = mem.lock();
						if (!ret)
							throw new RuntimeException("SYSTEM ERROR : failed to asyncLock" + mem.lockThread + mem.task);
					}
					t.resume();
					return true;
				} else {
					//ルート膜のロックが解放されるのを待つ。
					//所属タスクが変化していないかを時々検査するために、タイムアウトを設定している。
					try {
						wait(1);
					} catch (InterruptedException e) {}
				}
			}
		}
	}

	/** このロックした膜の全ての子孫の膜のロックを再帰的にブロッキングで取得する。
	 * <p>プロセス文脈のコピーや破棄をするときに使われる。
	 * <p>ロック解放にはrecursiveUnlock()を使用すること。
	 * @return ロックの取得に成功したかどうか */
	public boolean recursiveLock() {
		Iterator it = memIterator();
		LinkedList lockedmems = new LinkedList();
		boolean result = true;
		while (it.hasNext()) {
			Membrane mem = (Membrane)it.next();
			if (!mem.blockingLock()) {
				result = false;
				break;
			}
			if (!mem.recursiveLock()) {
				mem.unlock();
				result = false;
				break;
			}
			lockedmems.add(mem);
		}
		if (result) return true;
		it = lockedmems.iterator();
		while (it.hasNext()) {
			Membrane mem = (Membrane)it.next();
			mem.recursiveUnlock();
			mem.unlock();
		}
		return false;
	}

	// - ボディ命令

	/**
	 * 取得したこの膜のロックを解放する。
	 * 実行膜スタックは操作しない。
	 * ルート膜の場合、この膜を管理するタスクに対してシグナル（notifyメソッド）を発行する。
	 */
	public void quietUnlock() {
		Task task = (Task)getTask();
		synchronized(this) {
			lockThread = null;
			//blockingLock でブロックしているスレッドを起こす。
			//一つ起こせば十分。
			notify();
		}
		if (isRoot()) {
			// このタスクのルールスレッドを再開する。
			// このタスク以外のスレッドは必ずルート膜からロックしているので、ルート膜のロック解放時に起こせば十分。
			// ロックを解放してからここに来るまでの間に所属タスクが変わって場合があるが、特に問題はない。
			synchronized(task) {
				// タスク以外にも、suspend メソッド中で wait しているスレッドがあるかもしれないので、全て起こす。
				task.notifyAll();
			}
		}
	}
	
	/**
	 * 取得したこの膜のロックを解放する。ルート膜の場合、
	 * 仮の実行膜スタックの内容を実行膜スタックの底に転送し、
	 * この膜を管理するタスクに対してシグナル（notifyメソッド）を発行する。
	 * <p>lockおよびblockingLockの呼び出しに対応する。asyncLockにはasyncUnlockが対応する。
	 * <p><strike>todo unlock は weakUnlock に名称変更する</strike>
	 */
	public void unlock() {
		unlock(false);
	}
	
	public void unlock(boolean changed) {
		Task task = (Task)getTask();
		if (isRoot()) {
			task.memStack.moveFrom(task.bufferedStack);
		}
		quietUnlock();
		if(changed & Env.LMNgraphic != null)
			Env.LMNgraphic.setMem(this);
		if(changed & Env.LMNtool != null)
			Env.LMNtool.setMem(this);
	}

	/** この膜のロックを強制的に解放する。
	 * unlock()と同じ。
	 * TODO unlockに統廃合する
	 */
	public void forceUnlock() {
		unlock();
	}
	/** この膜からこの膜を管理するタスクのルート膜までの全ての膜の取得したロックを解放し、この膜を活性化する。
	 * 仮の実行膜スタックの内容を実行膜スタックに転送する。ルート膜の場合はunlock()と同じになる。
	 * <p>ルールスレッド以外のスレッドが最初に取得した膜のロックを解放するときに使用する。*/
	public void asyncUnlock() {
		asyncUnlock(true);
	}
	public void asyncUnlock(boolean asyncFlag) {
		activate();
		Membrane mem = this;
		while (!mem.isRoot()) {
			mem.lockThread = null;
			mem = mem.parent;
		}
		task.asyncFlag = asyncFlag;
		mem.unlock();
	}
	/** 取得したこの膜の全ての子孫の膜のロックを再帰的に解放する。*/
	public void recursiveUnlock() {
		Iterator it = memIterator();
		while (it.hasNext()) {
			Membrane mem = (Membrane)it.next();
			mem.recursiveUnlock();
			mem.unlock();
		}
	}

	///////////////////////////////	
	// Membrane で定義されるメソッド
	
//	// 本膜かどうか
//	boolean isCurrent() { return getTask().memStack.peek() == this; }
	
	/** デバッグ用 */
	String getReadyStackStatus() { return ready.toString(); }

	/** 実行アトムスタックの先頭のアトムを取得し、実行アトムスタックから除去 */
	Atom popReadyAtom() {
		if(null == ready){ return null; }
		Atom atom = (Atom)ready.pop();
		if(ready.isEmpty()){ ready = null; }
		return atom;
	}

//	/** 膜の活性化。ただしこの膜はルート膜ではなく、スタックに積まれておらず、
//	 * しかも親膜は仮でない実行膜スタックに積まれている。
//	 * → newMem / newLocalMembrane に移動しました */
//	public void activateThis() {
//		((Task)task).memStack.push(this);
//	}

//	/** この膜のキャッシュを表すバイト列を取得する。
//	 * @see RemoteMembrane#updateCache(byte[]) */
//	public byte[] cache() {
//		if(null == atomTable){ atomTable = new HashMap(); }
//		
//		// atomTableを更新する // 子膜の自由リンクについては要検討
//		atomTable.clear();
//		Iterator it = atomIterator();
//		while (it.hasNext()) {
//			Atom atom = (Atom)it.next();
//			atomTable.put(atom.getLocalID(), atom);
//		}
//		if(atomTable.isEmpty()){atomTable = null; }
//
//		try {
//			ByteArrayOutputStream bout = new ByteArrayOutputStream();
//			ObjectOutputStream out = new ObjectOutputStream(bout);
//
//			//子膜
//			out.writeInt(mems.size());
//			it = memIterator();
//			while (it.hasNext()) {
//				Membrane m = (Membrane)it.next();
//				out.writeObject(m.getTask().getMachine().hostname);
//				out.writeObject(m.getLocalID());
//				out.writeObject(new Boolean(m.isRoot()));
//			}
//			//アトム
//			out.writeInt(atoms.size());
//			it = atomIterator();
//			while (it.hasNext()) {
//				Atom a = (Atom)it.next();
//				out.writeObject(a);
//			}
//			//ルールセット
//			out.writeInt(rulesets.size());
//			it = rulesetIterator();
//			while (it.hasNext()) {
//				Ruleset r = (Ruleset)it.next();
//				out.writeObject(r.getGlobalRulesetID());
//			}
//			//todo nameは不要？
//			//out.writeObject(name);
//			out.writeObject(new Boolean(stable));
//
//			out.close();
//			return bout.toByteArray();
//		} catch (IOException e) {
//			//ByteArrayOutputStreamなので、発生するはずがない
//			throw new RuntimeException("Unexpected Exception", e);
//		}
//	}

//	/** アトムIDに対応するアトムを取得する */
//	public Atom lookupAtom(String atomid) {
//		return (Atom)atomTable.get(atomid);
//	}
//	/** アトムIDに対応するアトムを登録する */
//	public void registerAtom(String atomid, Atom atom) {
//		if(null == atomTable){ atomTable = new HashMap(); }
//		atomTable.put(atomid, atom);
//	}
	
	/** この膜を削除する(子膜が無い時にのみ呼んで良い) 
	 * デーモンを除去したため実体はない
	 */
	public void free() {
//		IDConverter.unregisterGlobalMembrane(getGlobalMemID());
	}
	
	
	/** インライン用マクロ Any=_old/1 :- Any=_new/1 */
	public void replace1by1(Atom _old, Atom _new) {
		relink(_old, 0, _new, 0);
		removeAtom(_old);
	}
	
	// XML <-> Mem ここから
	
	// Membrane -> XML
	private static int lastLinkId;

	private static int lastFreeLinkId;

	private static HashMap<Link, Integer> links = null;

	private static ArrayList<Link> freeLinks = null;

	/**
	 * xmlSerialize() 膜のXML表現と自由リンクの配列を返す．
	 * 
	 * @return Object[] { String xml, ArrayList<Link> freeLinks }
	 */
	public Object[] xmlSerialize() {
		lastLinkId = 0;
		lastFreeLinkId = 0;
		links = new HashMap<Link, Integer>();
		freeLinks = new ArrayList<Link>();

		String xml = this.xmlSerialize(new StringBuffer(""));
		return new Object[] { xml, freeLinks };
	}

	private String xmlSerialize(StringBuffer sb) {
		sb.append("<mem>");
		// アトム
		Iterator<Atom> atomIt = this.atomIterator();
		while (atomIt.hasNext()) {
			Atom nowAtom = atomIt.next();
			sb.append("<atom class=\"" + nowAtom.getFunctor().getClass().getSimpleName()
					+ "\" name=\"" + nowAtom.getName() + "\" arity=\""
					+ nowAtom.getArity() + "\">");
			// リンク
			for (int i = 0; i < nowAtom.getArity(); i++) {
				Link nowLink = nowAtom.getArg(i);
				if (links.containsKey(nowLink)) {
					sb.append("<link id=\"" + links.remove(nowLink).toString()
							+ "\" pos=\"" + i + "\"/>");
				} else {
					if (nowAtom.getFunctor().equals(Functor.INSIDE_PROXY)
							&& i == 0) {
						// 自由リンクの処理
						// 相方が存在しないようなInsideProxyに対して行う
						sb.append("<free_link id=\"" + lastFreeLinkId++
								+ "\"/>");
						freeLinks.add(nowLink.getAtom().getArg(1).getBuddy());
						links.remove(nowLink);
					} else {
						links.put(nowLink.getBuddy(), lastLinkId);
						sb.append("<link id=\"" + lastLinkId++ + "\" pos=\""
								+ i + "\"/>");
					}
				}
			}
			sb.append("</atom>");
		}

		// 子膜
		Iterator<Membrane> memIt = this.memIterator();
		while (memIt.hasNext()) {
			sb.append(memIt.next().xmlSerialize(new StringBuffer("")));
		}

		sb.append("</mem>");
		return sb.toString();
	}
	
	
	// XML -> Membrane
	public void xmlDeserialize(String src) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			parser.parse(new InputSource(new StringReader(src)), new LMNtalSax(
					this));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class LMNtalSax extends DefaultHandler {
		Membrane rootMem = null;

		Membrane tmpMem = null;

		Atom tmpAtom = null;

		HashMap<Integer, Object[]> linkMap = null; // <id, (atom, pos)>

		ArrayList<Atom> insides = null;

		LMNtalSax(Membrane mem) {
			this.rootMem = this.tmpMem = mem;
			this.linkMap = new HashMap<Integer, Object[]>();
			this.insides = new ArrayList<Atom>();
		}

		public void startDocument() {
		}

		public void startElement(String uri, String localName, String qName,
				Attributes attributes) {
			if (qName.equals("mem")) {
				Membrane newMem = tmpMem.newMem();
				this.tmpMem = newMem;
			} else if (qName.equals("atom")) {
				String funcClass = attributes.getValue(0).intern();
				String funcName = attributes.getValue(1).intern();

				if (funcClass == "SpecialFunctor") {
					if (funcName == "$in") {
						this.tmpAtom = tmpMem.newAtom(Functor.INSIDE_PROXY);
					} else if (funcName == "$out") {
						this.tmpAtom = tmpMem.newAtom(Functor.OUTSIDE_PROXY);
					}
				} else if (funcClass == "IntegerFunctor") {
					this.tmpAtom = tmpMem.newAtom(new IntegerFunctor(Integer
							.parseInt(funcName)));
				} else if (funcClass == "FloatingFunctor") {
					this.tmpAtom = tmpMem.newAtom(new FloatingFunctor(Double
							.parseDouble(funcName)));
				} else { // SymbolFunctor
					this.tmpAtom = tmpMem.newAtom(new SymbolFunctor(funcName,
							Integer.parseInt(attributes.getValue(2))));
				}
			} else if (qName.equals("link")) {
				int linkId = Integer.parseInt(attributes.getValue(0));
				int linkPos = Integer.parseInt(attributes.getValue(1));
				if (linkMap.containsKey(linkId)) {
					Object[] dst = linkMap.remove(linkId);
					Atom dstAtom = (Atom) dst[0];
					int dstPos = (Integer) dst[1];
					tmpMem.newLink(this.tmpAtom, linkPos, dstAtom, dstPos);
				} else {
					linkMap.put(linkId, new Object[] { this.tmpAtom, linkPos });
				}
			} else { // 自由リンク
				insides.add(tmpAtom);
			}
		}

		public void characters(char[] ch, int offset, int length) {
		}

		public void endElement(String uri, String localName, String qName) {
			if (qName.equals("mem")) {
				this.tmpMem = this.tmpMem.getParent();
			}
		}

		public void endDocument() {
			// 自由リンクのリストを作成する
			if (!insides.isEmpty()) {
				boolean first = true;
				Atom parent = tmpMem.newAtom(new SymbolFunctor("fls", 1));
				for (Atom in : insides) {
					Atom c = tmpMem.newAtom(new SymbolFunctor(".", 3));
					Atom out = tmpMem.newAtom(Functor.OUTSIDE_PROXY);
					tmpMem.newLink(c, 0, out, 1);
					in.getMem().newLink(in, 0, out, 0);
					if (first) {
						tmpMem.newLink(c, 2, parent, 0);
					} else {
						tmpMem.newLink(c, 2, parent, 1);
					}
					parent = c;
					first = false;
				}
				Atom nil = tmpMem.newAtom(new SymbolFunctor("[]", 1));
				if (first) {
					tmpMem.newLink(nil, 0, parent, 0);
				} else {
					tmpMem.newLink(nil, 0, parent, 1);
				}
			}
		}
	}
	
	// XML <-> Mem ここまで
	
	// hash ここから
	
	/* 膜のハッシュコードを返す */
	static int calculate(Membrane m) {
		return calculate(m, new HashMap<Membrane, Integer>());
	}
	
	/*
	 * 膜のハッシュコードを返す
	 * @param m ハッシュコード算出対象の膜
	 * @param m2hc mの子膜からハッシュコードへのマップ。子膜のハッシュコード算出を繰り返さないために使う。
	 */
	private static int calculate(Membrane m, Map<Membrane, Integer> m2hc) {
		//System.out.println("membrane:" + m);
		final long MAX_VALUE = Integer.MAX_VALUE;
		/*
		 * add: m内の分子のハッシュコードが加算されていく変数
		 * mult: m内の分子のハッシュコードが乗算されていく変数
		 */
		long add = 3412;        // 3412は適当な初期値
		long mult = 3412;
		
		/* 一時変数 */
		Atom a = null;
		Membrane mm = null;
		QueuedEntity q = null;
		
		/*
		 * contents:この膜内のアトムと子膜全体の集合
		 * toCalculate:現在計算中の分子内の未処理アトムまたは子膜の集合
		 * calculated:現在計算中の分子内の処理済アトムまたは子膜の集合
		 */
		Set<QueuedEntity> contents = new HashSet<QueuedEntity>(), 
							toCalculate = new HashSet<QueuedEntity>(), 
							calculated = new HashSet<QueuedEntity>();
		
		for (Iterator i = m.atomIterator(); i.hasNext(); ) {
			a = (Atom) i.next();
			if (a.getFunctor().isOutsideProxy() || a.getFunctor().isInsideProxy()) {
				continue;
			}
			contents.add(a);
		}
		
		for (Iterator i = m.memIterator(); i.hasNext(); ) {
			mm = (Membrane) i.next();
			contents.add(mm);
			m2hc.put(mm, calculate(mm, m2hc));
		}
		
		while (!contents.isEmpty()) {
			//System.out.println("uncalculated:" + contents);
			q = contents.iterator().next();
			contents.remove(q);
			
			/*
			 * mol: この分子のハッシュコードを保持する
			 * mol_add: 基本計算単位のハッシュコードが加算されていく変数
			 * mol_mult: 基本計算単位のハッシュコードが乗算されていく変数
			 * temp: 基本計算単位のハッシュコードを保持する
			 */
			 
			long mol = -1, mol_add = 0, mol_mult = 41, temp = 0;
			
			toCalculate.clear();
			calculated.clear();
			toCalculate.add(q);
				
			// 分子のハッシュコードの計算
			while (!toCalculate.isEmpty()) {
				q = toCalculate.iterator().next();
				calculated.add(q);
				toCalculate.remove(q);
				
				if (q instanceof Atom) {
					a = (Atom) q;
					temp = a.getFunctor().hashCode();
								
					// このアトムのリンクを処理する
					int arity = a.getFunctor().getArity();
					for (int k = 0; k < arity; k++) {
						temp *= 31;
						Link link = a.getArg(k);
						if (link.getAtom().getFunctor().isInsideProxy()) {
							Atom inside = link.getAtom();
							int pos = link.getPos() + 1;
							temp += (inside.getFunctor().hashCode() * pos);	
						} else if (link.getAtom().getFunctor().isOutsideProxy()) { // リンク先が子膜の場合
							/*
							 * リンク先アトムに至るまで貫いた子膜のハッシュコードと
							 * 最終的なリンク先アトムのハッシュコードと
							 * そのアトムの引数番号の組を
							 * このアトムから子膜へのリンクを表現する項とする。
							 */
							int t = 0;
							mm = link.getAtom().nthAtom(0).getMem();
							if (!calculated.contains(mm)) {
								toCalculate.add(mm);
							}
							while (link.getAtom().getFunctor().isOutsideProxy()) {
								link = link.getAtom().nthAtom(0).getArg(1);
								mm = link.getAtom().getMem();
								t += m2hc.get(mm);
								t *= 13;
							}
							
							t *= link.getAtom().getFunctor().hashCode();
							t *= link.getPos() + 1;
							temp += t;
						} else { // リンク先がプロキシ以外のアトムの場合
							Atom linked = link.getAtom();
							if (!calculated.contains(linked)) {
								toCalculate.add(linked);
							}
							int pos = link.getPos() + 1;
							// 接続先の引数番号をハッシュコードに関与させる
							temp += (linked.getFunctor().hashCode() * pos);
						}
					}
				} else {
					Membrane mt = (Membrane) q;
					final int thisMembsHC = m2hc.get(mt);
					temp = thisMembsHC;
					
					// この膜から膜の外部へのリンクを処理する
					Link link = null;
					for (Iterator i = mt.atomIteratorOfFunctor(Functor.INSIDE_PROXY); i.hasNext(); ) {
						Atom inside = (Atom) i.next();
						// この膜外部の（プロキシでない）リンク先アトムまでトレース
						int s = 0;
						link = inside.nthAtom(0).getArg(1);
						
						if (link.getAtom().getFunctor().isOutsideProxy()) { // この膜のリンク先が膜のとき
							mm = link.getAtom().nthAtom(0).getMem();
							if (!calculated.contains(mm)) {
								toCalculate.add(mm);
							}
						} else { // この膜のリンク先がアトムの場合
							a = link.getAtom();
							if (!calculated.contains(a)) {
								toCalculate.add(a);
							}
						}
							
						while (link.getAtom().getFunctor().isOutsideProxy()) {
							link = link.getAtom().nthAtom(0).getArg(1);
							s += m2hc.get(link.getAtom().getMem());
							s *= 13;
						}
						s += link.getAtom().getFunctor().hashCode();
						s *= link.getPos() + 1;
							
						// この膜内部の（プロキシでない）リンク元アトムまでトレース
						int t = 0;
						link = inside.getArg(1);
						while (link.getAtom().getFunctor().isOutsideProxy()) {
							link = link.getAtom().nthAtom(0).getArg(1);
							t += m2hc.get(link.getAtom().getMem());
							t *= 13;
						}
						t *= link.getAtom().getFunctor().hashCode();
						t *= link.getPos() + 1;
						temp += thisMembsHC^t * s;
					}
				}
				
				mol_add += temp;
				mol_add %= MAX_VALUE;
				mol_mult *= temp;
				mol_mult %= MAX_VALUE;
			}
			mol = mol_add^mol_mult;
			//System.out.println("molecule: " + calculated + " = " + mol);
			/* ハッシュコードを算出した分子を計算対象から取り除く */
			contents.removeAll(calculated);
			
			add += mol;
			add %= MAX_VALUE;
			mult *= mol;
			mult %= MAX_VALUE;
		}
		
		//System.out.println("membrane:" + m + " = " + (mult^add) + " (mult=" + mult + ", add=" + add + ")");
		return (int) (mult^add);
	}
}
