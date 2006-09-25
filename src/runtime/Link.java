package runtime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;

//import util.QueuedEntity;
//import java.lang.Integer;
//import util.Stack;

/**
 * リンクの接続先を、アトムと引数番号の組として表す。LMNtalのリンクには方向が無いので、
 * １つのリンクに対してこのクラスのインスタンスを２つ使用する。
 */
public final class Link implements Cloneable, Serializable {
	/** リンク先のアトム */
	private Atom atom;
	/** リンク先が第何引数か */
	private int pos;

	private static int lastId = 0;
	private int id;
	
	static void gc() {
		lastId = 0;
	}
	
	///////////////////////////////
	// コンストラクタ
	
	public Link(Atom atom, int pos) {
		set(atom, pos);
		id = lastId++;
	}

	public Object clone() {
		return new Link(atom, pos);
	}

	///////////////////////////////
	// 情報の取得

	/** 対になる２つのリンクのidのうち、若い方をリンクの番号として使用する。 */
	public String toString() {
		int i;
		if (this.id < atom.args[pos].id) {
			i = this.id;
		} else {
			i = atom.args[pos].id;
		}
		if (Env.verbose > Env.VERBOSE_SIMPLELINK)
			return "_" + i;
		else
			return "L" + i;
	}
				
	/** リンク先のアトムを取得する */
	public Atom getAtom() {
		return atom;
	}
	/** リンク先の引数番号を取得する */
	public int getPos() {
		return pos;
	}
	/** このリンクと対をなす逆向きのリンクを取得する */
	public Link getBuddy() {
		return atom.args[pos];
	}
	/** リンク先が最終リンクの場合にtrueを返す */
	boolean isFuncRef() {
		return atom.getArity() - 1 == pos;
	}
	
	/** 
	 * 基底項プロセスかどうかを検査する．(Stackを使うように修正 2005/07/26)
	 * (それに伴い引数に受け取っていたSetを廃止)
	 * 基底項プロセスを構成するアトムの数を返す．
	 * 引数には，(左辺出現アトム等)基底項プロセスに含まれてはいけないアトムのSetを指定する．
	 * ただし、自由リンク管理アトムに出会った場合は、-1を返す．
	 * @param avoSet 基底項プロセスに出てきてはいけないアトムのSet
	 * @return 基底項プロセスを構成するアトム数
	 */
//	public int isGround(Set avoSet){
//		Set srcSet = new HashSet();
//		Stack s = new Stack(); //リンクを積むスタック
//		s.push(this);
//		int c=0;
//		while(!s.isEmpty()){
//			Link l = (Link)s.pop();
//			Atom a = l.getAtom();
//			if(srcSet.contains(a))continue; //既に辿ったアトム
//			if(avoSet.contains(a))return -1; //出現してはいけないアトム
//			if(a.getFunctor().equals(Functor.INSIDE_PROXY)||
//				a.getFunctor().isOutsideProxy()) //プロキシに至ってはいけない
//				return -1;
//			c++;
//			srcSet.add(a);
//			for(int i=0;i<a.getArity();i++){
//				if(i==l.getPos())continue;
//				s.push(a.getArg(i));
//			}
//		}
//		return c;
//	}
	public int isGround(Set avoSet){
		List srclinks = new ArrayList();
		srclinks.add(this);
		return Membrane.isGround(srclinks,avoSet);
	}

	/**
	 * 同じ構造を持った基底項プロセスかどうか検査する(Stackを使うように修正 2005/07/27)
	 * ( それに伴い引数に受け取っていたMapを廃止)
	 * どちらか片方についてgroundかどうかの検査は済んでいるものとする
	 * @param srcLink 比較対象のリンク
	 * @return
	 */
//	public boolean eqGround(Link srcLink){//,Map srcMap){
//		Map map = new HashMap(); //比較元アトムから比較先アトムへのマップ
//		Stack s1 = new Stack();  //比較元リンクを入れるスタック
//		Stack s2 = new Stack();  //比較先リンクを入れるスタック
//		s1.push(this);
//		s2.push(srcLink);
//		while(!s1.isEmpty()){
//			Link l1 = (Link)s1.pop();
//			Link l2 = (Link)s2.pop();
//			if(l1.getPos() != l2.getPos())return false; //引数位置の一致を検査
//			if(!l1.getAtom().getFunctor().equals(l2.getAtom().getFunctor()))return false; //ファンクタの一致を検査
//			if(!map.containsKey(l1.getAtom()))map.put(l1.getAtom(),l2.getAtom()); //未出
//			else if(map.get(l1.getAtom()) != l2.getAtom())return false;         //既出なれど不一致
//			else continue;
//			for(int i=0;i<l1.getAtom().getArity();i++){
//				if(i==l1.getPos())continue;
//				s1.push(l1.getAtom().getArg(i));
//				s2.push(l2.getAtom().getArg(i));
//			}
//		}
//		return true;
//	}
	public boolean eqround(Link srcLink){
		List srclinks = new ArrayList();
		List dstlinks = new ArrayList();
		srclinks.add(srcLink);
		dstlinks.add(this);
		return Membrane.eqGround(srclinks,dstlinks);
	}
	/**
	 * ground構造に対して一意な文字列を返す。
	 * ground チェック済みでなければならない。
	 * hara
	 * @return
	 */
	public String groundString(){
		Set srcSet = new HashSet();
		Stack s = new Stack(); //リンクを積むスタック
		HashMap linkStr = new HashMap();
		StringBuffer sb = new StringBuffer();
		int linkNo=0;
		s.push(this);
		while(!s.isEmpty()){
			Link l = (Link)s.pop();
			Atom a = l.getAtom();
			if(srcSet.contains(a))continue; //既に辿ったアトム
			sb.append(a.getFunctor().getName());
			sb.append("(");
			srcSet.add(a);
			for(int i=0;i<a.getArity();i++){
				Link l0 = a.args[i];
				Link l1 = l0.getBuddy();
				if(!linkStr.containsKey(l0)) {
					String ss="L"+(linkNo++);
					linkStr.put(l0, ss);
					linkStr.put(l1, ss);
				}
				sb.append(linkStr.get(l0));
				if(i==l.getPos())continue;
				s.push(a.getArg(i));
			}
			sb.append(")");
		}
		return sb.toString();
	}

	///////////////////////////////
	// 操作
	/**
	 * 接続先を設定する。
	 * 膜クラスのリンク操作用メソッド内でのみ呼び出される。
	 */
	void set(Atom atom, int pos) {
		this.atom = atom;
		this.pos = pos;
	}
	/**
	 * このリンクの接続先を、与えられたリンクの接続先と同じにする。
	 * 膜クラスのリンク操作用メソッド内でのみ呼び出される。
	 */
	void set(Link link) {
		this.atom = link.atom;
		this.pos = link.pos;
	}

	/**
	 * 直列化復元時に呼ばれる。
	 * @param in 読み込むストリーム
	 * @throws IOException 入出力エラーが発生した場合
	 * @throws ClassNotFoundException 直列化されたオブジェクトのクラスが見つからなかった場合
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		id = lastId++;
	}
}
