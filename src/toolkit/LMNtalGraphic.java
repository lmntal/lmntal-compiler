package toolkit;

import java.awt.Component;

import runtime.Membrane;
import util.Util;

/**
 * LMNtalWindowに設置するラベル
 * オブジェクト生成時に，膜を検索し，ラベル生成を行う．
 */
public class LMNtalGraphic extends LMNComponent {

	private LMNtalPanel panel;
	
	
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	/**
	 * @param lmnWindow ボタンを設置するウィンドウ
	 * @param mem 検索対象の膜
	 */
	public LMNtalGraphic(LMNtalWindow lmnWindow, Membrane mem){
		super(lmnWindow, mem);
	}
	/////////////////////////////////////////////////////////////////

	/**
	 * 膜からボタン生成に必要な情報を取得する．
	 * @param mem 検索対象の膜
	 */
	
	public Component initComponent(){
		panel = new LMNtalPanel(getMymem());
		return panel;
	}
	
	public void setChildMem(Membrane mem){
		panel.setChildMem(mem);
	}
}
