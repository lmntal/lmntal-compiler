package toolkit;

import java.awt.Component;

import javax.swing.JTextArea;

import runtime.Membrane;

/**
 * LMNtalWindowに設置するボタン
 * オブジェクト生成時に，膜を検索し，ボタン生成を行う．
 */
public class LMNtalTextArea extends LMNComponent{

	private JTextArea textarea;
	private String text;
	
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	/**
	 * @param lmnWindow テキストエリアを追加するウィンドウ
	 * @param mem 検索対象の膜
	 */
	public LMNtalTextArea(LMNtalWindow lmnWindow, Membrane mem){
		super(lmnWindow, mem);
		System.out.println("textarea");		
	}
	/////////////////////////////////////////////////////////////////

	/**
	 * 膜からボタン生成に必要な情報を取得する．
	 * @param mem 検索対象の膜
	 */
	
	public Component initComponent(){
		textarea = new JTextArea(text);
		return textarea;
	}
	
	public void setMembrane(Membrane mem){
		text = getText(mem);
	}
}