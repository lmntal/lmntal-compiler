package runtime;

//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;

import java.awt.*;
import test.GUI.Node;
import util.QueuedEntity;
//import util.Stack;

/**
 * アトムクラス。ローカル・リモートに関わらずこのクラスのインスタンスを使用する。
 * @author Mizuno
 */
public final class Atom extends QueuedEntity implements test.GUI.Node {
	/** 所属膜。AbstractMembraneとそのサブクラスが変更してよい。
	 * ただし値を変更するときはindexも同時に更新すること。(null,-1)は所属膜なしを表す。*/
	AbstractMembrane mem;
	/** 所属膜のAtomSet内でのインデックス */
	int index = -1;

	/** ファンクタ（名前とリンク数） */
	private Functor functor;
	/** リンク */
	Link[] args;

	private static int lastId = 0;
	/** このアトムのローカルID */
	private int id;
	
	static void gc() {
		lastId = 0;
	}
	
	///////////////////////////////
	// コンストラクタ

	/**
	 * 指定された名前とリンク数を持つアトムを作成する。
	 * AbstractMembraneのnewAtomメソッド内で呼ばれる。
	 * @param mem 所属膜
	 */
	Atom(AbstractMembrane mem, Functor functor) {
		this.mem = mem;
		this.functor = functor;
		args = new Link[functor.getArity()];
		id = lastId++;
		
		if (Env.gui != null) {
			pos = new Point((int)(Math.random()*Env.gui.getSize().width), (int)(Math.random()*Env.gui.getSize().height));
		}
	}

	///////////////////////////////
	// 操作
	public void setFunctor(Functor newFunctor) {
		if (newFunctor.getArity() > args.length) {
			// TODO SystemError用の例外クラスを投げる
			throw new RuntimeException("SYSTEM ERROR: insufficient link vector length");
		}
		functor = newFunctor;
	}
	/** ファンクタ名を設定する。 */
	public void setName(String name) {
		set(name, getFunctor().getArity());
	}
	/** ファンクタを設定する。
	 * 所属膜がリモートの場合もあり、しかもAtomSetは必ず更新しなければならないので、
	 * 膜のalterAtomFunctorメソッドを呼ぶ。*/
	public void set(String name, int arity) {
		mem.alterAtomFunctor(this, new Functor(name, arity));
	}
	/** けす TODO リンクもけす（その場合、メソッド名を変えて下さい）
	 * 抽象膜クラスにメソッドを作って呼ぶようにするか、または、
	 * このメソッドから抽象膜クラスのメソッドを呼ぶ。とりあえず現状通りの後者でよい。*/
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
	/** 適切に省略された名前を取得 */
	public String getAbbrName() {
		return functor.getAbbrName();
	}
	/** リンク数を取得 */
	int getArity() {
		return functor.getArity();
	}
	/** 最終引数を取得 */
	Link getLastArg() {
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
	
	Point pos;
	double vx, vy;
	public void initNode() {
		pos = new Point();
	}
	public Point getPosition() {
		return pos;
	}
	public void setPosition(Point p) {
		pos = p;
	}
	public Node getNthNode(int index) {
		return args[index].getAtom();
	}
	public int getEdgeCount() {
		return functor.getArity();
	}
	public void setMoveDelta(double dx, double dy) {
		vx += dx;
		vy += dy;
	}
	public void move(Rectangle area) {
		//if (n.isFixed()) return;
		pos.x += Math.max(-5, Math.min(5, vx));
		pos.y += Math.max(-5, Math.min(5, vy));
		
		if (pos.x < area.getMinX())		pos.x = (int)area.getMinX();
		else if (pos.x > area.getMaxX())	pos.x = (int)area.getMaxX();
		
		if (pos.y < area.getMinY())		pos.y = (int)area.getMinY();
		else if (pos.y > area.getMaxY())	pos.y = (int)area.getMaxY();
		
//		dx=dy=0;
		vx /= 2;
		vy /= 2;
	}
	public void paintEdge(Graphics g) {
		g.setColor(Color.BLACK);
		for(int i=0;i<getEdgeCount();i++) {
			Node n2 = getNthNode(i);
			if(this.hashCode() < n2.hashCode()) continue;
			g.drawLine(this.getPosition().x, this.getPosition().y, n2.getPosition().x, n2.getPosition().y);
		}
	}
	
	public void paintNode(Graphics g) {
		String label = getName();
		FontMetrics fm = g.getFontMetrics();
		int w = fm.stringWidth(label);
		int h = fm.getHeight();
		
		Dimension size = new Dimension(16, 16);
//		g.setColor(new Color(64,128,255));
		// 適当に色分けする！
		g.setColor(test.GUI.GraphLayout.colors[ Math.abs(label.hashCode()) % test.GUI.GraphLayout.colors.length ]);
		
		g.fillOval(pos.x - size.width/2, pos.y - size.height/ 2, size.width, size.height);
		
		g.setColor(Color.BLACK);
		g.drawOval(pos.x - size.width/2, pos.y - size.height/ 2, size.width, size.height);
		g.drawString(label, pos.x - (w-10)/2, (pos.y - (h-4)/2) + fm.getAscent()+size.height);
	}
}
