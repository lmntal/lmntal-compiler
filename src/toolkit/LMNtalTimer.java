package toolkit;

import javax.swing.Timer;
import java.awt.event.*;
import java.util.Iterator;

import runtime.Atom;
import runtime.Functor;
import runtime.Membrane;
import runtime.SymbolFunctor;


/**
 * LMNtalWindowに設置するボタン
 * オブジェクト生成時に，膜を検索し，ボタン生成を行う．
 */
//public class LMNtalTimer extends LMNComponent  implements ActionListener {
public class LMNtalTimer implements ActionListener {

	final static
	private Functor TIME_FUNCTOR = new SymbolFunctor("time",1);
	
	private Timer timer;
	private Membrane mymem; //もらってきた膜を保存する。
	
	int timeCount = 0;
	
	private int time;
	
	
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	/**
	 * @param lmnWindow ボタンを設置するウィンドウ
	 * @param mem 検索対象の膜
	 */
	public LMNtalTimer(LMNtalWindow lmnWindow, Membrane mem){
//		super(lmnWindow, mem);
		mymem = mem;
		init();
//		System.out.println("timer");
	}
	/////////////////////////////////////////////////////////////////


	/**
	 * 膜からボタン生成に必要な情報を取得する．
	 * @param mem 検索対象の膜
	 */
//	public Component initComponent(){
		
	public void init(){
		time = getTime(mymem);
//		System.out.println("time is :" + time);
		timer = new Timer(time, this);
//		timer.setRepeats(false); //イベントが起こるのは１度だけ
		timer.start();
	}


//	public void setMembrane(Membrane mem){
//		time = getTime(mem);
//		if(timer != null && getTextUpdate()){
//			setTextUpdate(false);
//			button.setText(text);
//		}
//	}
	

	public int getTime(Membrane mem){
		int time = 0;
		Iterator timeAtomIte = mem.atomIteratorOfFunctor(TIME_FUNCTOR);
		if(timeAtomIte.hasNext()){
			Atom atom = (Atom)timeAtomIte.next();
			time = Integer.parseInt(atom.nth(0));	
		}
		return time;
	}	
	
	public void actionPerformed(ActionEvent e) {
//		LMNtalTFrame.addUpdateComponent(this);
		timeCount++;
		addAtom();
	}

	public void addAtom(){ //timer動いた数だけtimeover作成。
		for(;timeCount > 0; timeCount--){
			Membrane mem = mymem; //膜を受け取る
			Functor func = new SymbolFunctor("timeover", 0); //timeover.を作る
			mem.addAtom(new Atom(mem, func)); //funcを元にして作ったアトムをmemに追加する
			//↑のaddAtomはMembraneのaddAtom。toolkit内のaddAtomとは別物。
		}
		timeCount = 0;
	}


}
