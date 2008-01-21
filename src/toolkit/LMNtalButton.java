package toolkit;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import runtime.Atom;
import runtime.Functor;
import runtime.Membrane;
import runtime.SymbolFunctor;
import util.Util;

/**
 * LMNtalWindowに設置するボタン
 * オブジェクト生成時に，膜を検索し，ボタン生成を行う．
 */
public class LMNtalButton extends LMNComponent implements ActionListener {

	private JButton button;
	private String text;
	
	private int clickedCounter = 0;
	
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	/**
	 * @param lmnWindow ボタンを設置するウィンドウ
	 * @param mem 検索対象の膜
	 */
	public LMNtalButton(LMNtalWindow lmnWindow, Membrane mem){
		super(lmnWindow, mem);
		Util.println("button");		
	}
	/////////////////////////////////////////////////////////////////

	/**
	 * 膜からボタン生成に必要な情報を取得する．
	 * @param mem 検索対象の膜
	 */
	
	public Component initComponent(){
		button = new JButton(text);
		button.addActionListener(this); //buttonのActionListener呼び出し
		return button;
	}
	
	public void setMembrane(Membrane mem){
		text = getText(mem);
		if(button != null && getTextUpdate()){
			setTextUpdate(false);
			button.setText(text);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		Util.println("clicked : " + this); ////////////// デバッグ用
		LMNtalTFrame.addUpdateComponent(this);
		clickedCounter++;
	}
	
	public void addAtom(){
		for(;clickedCounter > 0; clickedCounter--){
			Membrane mem = getMymem(); //膜を受け取る
			Functor func = new SymbolFunctor("clicked", 0); //clicked.を作る
			mem.addAtom(new Atom(mem, func)); //funcを元にして作ったアトムをmemに追加する
			//↑のaddAtomはMembraneのaddAtom。toolkit内のaddAtomとは別物。
		}
		clickedCounter = 0;
	}
}
