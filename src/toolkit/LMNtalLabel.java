package toolkit;

import java.awt.Component;

import javax.swing.JLabel;

import runtime.Membrane;
import util.Util;

/**
 * LMNtalWindowに設置するラベル
 * オブジェクト生成時に，膜を検索し，ラベル生成を行う．
 */
public class LMNtalLabel extends LMNComponent {

	private JLabel label;
	private String text;
	
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	/**
	 * @param lmnWindow ボタンを設置するウィンドウ
	 * @param mem 検索対象の膜
	 */
	public LMNtalLabel(LMNtalWindow lmnWindow, Membrane mem){
		super(lmnWindow, mem);
	}
	/////////////////////////////////////////////////////////////////

	/**
	 * 膜からボタン生成に必要な情報を取得する．
	 * @param mem 検索対象の膜
	 */
	
	public Component initComponent(){
		label = new JLabel(text);
		return label;
	}
	
	public void setMembrane(Membrane mem){
		text = getText(mem);
		if(label != null && getTextUpdate()){
			setTextUpdate(false);
			label.setText(text);
		}
	}
		
}
