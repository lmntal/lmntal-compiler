package runtime;

//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import test.GUI.DoublePoint;
import test.GUI.Node;
import test.GUI.NodeParameter;
import util.QueuedEntity;
import daemon.IDConverter;
//import util.Stack;

/**
 * アトムクラス。ローカル・リモートに関わらずこのクラスのインスタンスを使用する。
 * @author Mizuno
 */
public final class Atom extends QueuedEntity implements test.GUI.Node, Serializable {
	
	/** 所属膜。AbstractMembraneとそのサブクラスが変更してよい。
	 * ただし値を変更するときはindexも同時に更新すること。(null, -1)は所属膜なしを表す。
	 */
	AbstractMembrane mem;
	
	/** 所属膜のAtomSet内でのインデックス */
	public int index = -1;

	private NodeParameter  nodeParam = null;
	
	public int getid(){
		return id;
	}
	
	/** ファンクタ（名前とリンク数） */
	private Functor functor;
	
	/** リンク */
	Link[] args;
	
	private static int lastId = 0;
	
	/** このアトムのローカルID */
	int id;
	
	/** リモートホストとの通信で使用されるこのアトムのID。リモート膜に所属するときのみ使用される。
	 * <p>所属膜のキャッシュ受信後、所属膜の連続するロック期間中のみ有効。
	 * キャッシュ受信時に初期化され、引き続くリモートホストへの要求を構築するために使用される。
	 * リモートホストへの要求で新しくアトムが作成されると、ローカルでNEW_が代入される。
	 * $inside_proxyアトムの場合、命令ブロックの返答を受けてリモート側のローカルIDで上書きされる。
	 * $inside_proxy以外のアトムの場合、ロック解除までNEW_のまま放置される。
	 * @see Membrane.atomTable */
	protected String remoteid;

	///////////////////////////////
	// コンストラクタ

	/**
	 * 指定された名前とリンク数を持つアトムを作成する。
	 * AbstractMembraneのnewAtomメソッド内で呼ばれる。
	 * @param mem 所属膜
	 */
	public Atom(AbstractMembrane mem, Functor functor) {
		this.mem = mem;
		this.functor = functor;
		if(functor.getArity() > 0)
			args = new Link[functor.getArity()];
		else
			args = null;
		id = lastId++;
		if(Env.fGUI) {
			nodeParam = new NodeParameter();
			Rectangle r = Env.gui.lmnPanel.getGraphLayout().getAtomsBound();
			nodeParam.pos = new DoublePoint(Math.random()*r.width + r.x, Math.random()*r.height + r.y);
		}
	}

	///////////////////////////////
	// 操作
	public void setFunctor(Functor newFunctor) {
		if (newFunctor.getArity() > args.length) {
			// todo SystemError用の例外クラスを投げる
			throw new RuntimeException("SYSTEM ERROR: insufficient link vector length");
		}
		functor = newFunctor;
	}
	/** ファンクタ名を設定する。 */
	public void setName(String name) {
		setFunctor(name, getFunctor().getArity());
	}
	/** ファンクタを設定する。
	 * 所属膜がリモートの場合もあり、しかもAtomSetは必ず更新しなければならないので、
	 * 膜のalterAtomFunctorメソッドを呼ぶ。*/
	public void setFunctor(String name, int arity) {
		mem.alterAtomFunctor(this, new Functor(name, arity));
	}
	/**@deprecated*/
	public void set(String name, int arity) {
		setFunctor(name,arity);
	}
	/** アトムを所属膜から取り除く（リンク先のアトムは除去しない）*/
	public void remove() {
		mem.removeAtom(this);
	}
	///////////////////////////////
	// 情報の取得

	public String toString() {
		return functor.getName();
	}
	/**
	 * デフォルトの実装だと処理系の内部状態が変わると変わってしまうので、
	 * インスタンスごとにユニークなidを用意してハッシュコードとして利用する。
	 */
	public int hashCode() {
		return id;
	}
	/** このアトムのローカルIDを取得する */
	String getLocalID() {
		return Integer.toString(id);
	}

	/** 所属膜の取得 */
	public AbstractMembrane getMem() {
		return mem;
	}
	/** ファンクタを取得 */
	public Functor getFunctor(){
		return functor;
	}
	/** 名前を取得 */
	public String getName() {
		return functor.getName();
	}
	/** リンク数を取得 */
	int getArity() {
		return functor.getArity();
	}
	/** 最終引数を取得 */
	public Link getLastArg() {
		return args[getArity() - 1];
	}
	/** 第pos引数に格納されたリンクオブジェクトを取得 */
	public Link getArg(int pos) {
		return args[pos];
	}
	/** 第 n 引数につながってるアトムの名前を取得する */
	public String nth(int n) {
		return nthAtom(n).getFunctor().getName();
	}
	/** 第 n 引数につながってるアトムを取得する */
	public Atom nthAtom(int n) {
		return args[n].getAtom();
	}
//	/** 所属膜を設定する。AbstractMembraneとそのサブクラスのみ呼び出してよい。*/
//	void setMem(AbstractMembrane mem) {
//		this.mem = mem;
//	}
//	/**@deprecated*/	
//	void remove() {
//		mem.removeAtom(this);
//		mem = null;
//	}
//	/** スタックに入っていれば除去する */
//	public void dequeue() {
//		super.dequeue();
//	}
	
	///////////////////////////////////////////////////////////////
	
	/* *** *** *** *** *** BEGIN GUI *** *** *** *** *** */
//
//	DoublePoint pos;
//	Double3DPoint pos3d;
//	LMNTransformGroup objTrans;
//	double vx, vy, vz;
	
	public void initNode() {
		nodeParam.pos = new DoublePoint();
	}
	public boolean isVisible() {
		return !(functor instanceof SpecialFunctor);
	}
	public DoublePoint getPosition() {
		return nodeParam.pos;
	}

	public void setPosition(DoublePoint p) {
		nodeParam.pos = p;
	}

	public Node getNthNode(int index) {
		Atom a = nthAtom(index);
		while(a.getFunctor().equals(Functor.INSIDE_PROXY) || a.getFunctor().isOUTSIDE_PROXY()) {
//			System.out.println(a.nthAtom(0).nthAtom(0));
//			System.out.println(a.nthAtom(0).nthAtom(1));
			a = a.nthAtom(0).nthAtom(1);
		}
//		System.out.println(this+" 's "+index+"th atom is "+a);
		return a;
	}
	public int getEdgeCount() {
		return functor.getArity();
	}
	public void setMoveDelta(double dx, double dy) {
		nodeParam.vx += dx;
		nodeParam.vy += dy;
	}
	public void setMoveDelta3d(double dx, double dy, double dz) {
		nodeParam.vx += dx;
		nodeParam.vy += dy;
		nodeParam.vz += dz;
	}
	public void initMoveDelta() {
		nodeParam.vx = nodeParam.vy = 0;
	}
	public void initMoveDelta3d() {
		nodeParam.vx = nodeParam.vy = nodeParam.vz = 0;
	}
	public DoublePoint getMoveDelta(){
		return new DoublePoint(nodeParam.vx, nodeParam.vy);
	}
	public void move(Rectangle area) {
		//if (n.isFixed()) return;
		final int M = 100;
		nodeParam.pos.x += Math.max(-M, Math.min(M, nodeParam.vx));
		nodeParam.pos.y += Math.max(-M, Math.min(M, nodeParam.vy));
		
		if (nodeParam.pos.x < area.getMinX())		nodeParam.pos.x = (int)area.getMinX();
		else if (nodeParam.pos.x > area.getMaxX())	nodeParam.pos.x = (int)area.getMaxX();
		
		if (nodeParam.pos.y < area.getMinY())		nodeParam.pos.y = (int)area.getMinY();
		else if (nodeParam.pos.y > area.getMaxY())	nodeParam.pos.y = (int)area.getMaxY();
		
//		vx=vy=0;
		nodeParam.vx /= 2;
		nodeParam.vy /= 2;
	}
	public void paintEdge(Graphics g) {
		g.setColor(Color.BLACK);
		for(int i=0;i<getEdgeCount();i++) {
			Node n2 = getNthNode(i);
			if(this.hashCode() < n2.hashCode()) continue;
			g.drawLine((int)getPosition().x, (int)getPosition().y, (int)n2.getPosition().x, (int)n2.getPosition().y);
		}
	}
	
	public void paintNode(Graphics g) {
		String label = getName();
		FontMetrics fm = g.getFontMetrics();
		if(Env.fDEMO) {
			g.setFont(new Font(null, Font.PLAIN, 30));
		} else {//2006.3.8 by inui
			g.setFont(new Font(null, Font.PLAIN, 12));
		}
		int w = fm.stringWidth(label);
		int h = fm.getHeight();
		
		int wh = Env.fDEMO ? 40 : 16;
		Dimension size = new Dimension(wh, wh);
//		g.setColor(new Color(64,128,255));
		// 適当に色分けする！
		
		// [0, 7] -> [128, 255] for eash R G B
		int ir = 0x7F - ((label.hashCode() & 0xF00) >> 8) * 0x08 + 0x7F;
		int ig = 0x7F - ((label.hashCode() & 0x0F0) >> 4) * 0x08 + 0x7F;
		int ib = 0x7F - ((label.hashCode() & 0x00F) >> 0) * 0x08 + 0x7F;
		
//		System.out.println(label + "  " + ir + "  " + ig + "  " + ib);
		g.setColor(new Color(ir, ig, ib));
		
		g.fillOval((int)(nodeParam.pos.x - size.width/2), (int)(nodeParam.pos.y - size.height/ 2), size.width, size.height);
		
		g.setColor(Color.BLACK);
		g.drawOval((int)(nodeParam.pos.x - size.width/2), (int)(nodeParam.pos.y - size.height/ 2), size.width, size.height);
		g.drawString(label, (int)(nodeParam.pos.x - (w-10)/2), (int)(nodeParam.pos.y - (h-4)/2) + fm.getAscent()+size.height);
	}
	
	/* *** *** *** *** *** END GUI *** *** *** *** *** */
	
	/**
	 * このアトムを直列化してストリームに書き出します。
	 * キャッシュ更新や、プロセス文脈の移送の際に利用します。
	 * @param out 出力ストリーム
	 * @throws IOException 入出力エラーが発生した場合。
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeInt(id);
		out.writeObject(functor);
		if (functor.equals(Functor.INSIDE_PROXY)) {
			//親膜へのリンクは送信しない
			out.writeObject(args[1]);
		} else if (functor.isOUTSIDE_PROXY()) {
			//子膜へのリンクは、接続先atomID/memIDのみ送信。接続先はINSIDE_PROXYの第１引数なので、アトムのIDのみで十分。
			Atom a = args[0].getAtom();
			AbstractMembrane mem = a.mem;
			//ルート膜以外ではグローバルIDが管理されていないので、とりあえず自分で作っている。
			//todo もっとよい方法を考える
			out.writeObject(mem.getTask().getMachine().hostname);
			out.writeObject(mem.getLocalID());
			out.writeInt(a.id);
			out.writeObject(args[1]);
		} else {
			out.writeObject(args);
		}
	}
	
	/**
	 * このアトムの内容をストリームから復元します。
	 * キャッシュ更新や、プロセス文脈の移送の際に利用します。
	 * @param out 入力ストリーム
	 * @throws IOException 入出力エラーが発生した場合。
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		id = lastId++;
		remoteid = Integer.toString(in.readInt());
		functor = (Functor)in.readObject();
		args = new Link[functor.getArity()];
		if (functor.equals(Functor.INSIDE_PROXY)) {
			//とりあえず復元しておく。後でRemoteMembrane内でつなぎ直され、このアトムは使われなくなる。
			args[1] = (Link)in.readObject();
		} else if (functor.isOUTSIDE_PROXY()) {
			//子膜内のINSIDE_PROXYは送信されてこないので、ここで生成する。
			String hostname = (String)in.readObject();
			String localid = (String)in.readObject();
			String globalid = hostname + ":" + localid;
			AbstractMembrane mem = IDConverter.lookupGlobalMembrane(globalid);
			//IDConverterには、RemoteMembrane.updateCache()で登録済みのはず。
			Atom inside = new Atom(mem, Functor.INSIDE_PROXY);
			mem.atoms.add(inside);
			
			inside.remoteid = Integer.toString(in.readInt());
			args[0] = new Link(inside, 0);
			inside.args[0] = new Link(this, 0);
			//insideの第2引数の接続先は、架空のアトムで終端したほうがよいかも。
			args[1] = (Link)in.readObject();
		} else {
			args = (Link[])in.readObject();
		}
	}
}
