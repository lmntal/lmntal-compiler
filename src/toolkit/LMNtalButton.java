package toolkit;

import java.awt.BorderLayout;

import javax.swing.JButton;

import runtime.Membrane;

/**
 * LMNtalWindowに設置するボタン
 * オブジェクト生成時に，膜を検索し，ボタン生成を行う．
 */
public class LMNtalButton {

	private JButton button;
	
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	/**
	 * @param lmnWindow ボタンを設置するウィンドウ
	 * @param mem 検索対象の膜
	 */
	public LMNtalButton(LMNtalWindow lmnWindow, Membrane mem){
		System.out.println("button");
		resetMembrane(mem);
		lmnWindow.add(button, BorderLayout.NORTH);
		// ウィンドウ の更新
		lmnWindow.validate();
	}
	/////////////////////////////////////////////////////////////////

	/**
	 * 膜からボタン生成に必要な情報を取得する．
	 * @param mem 検索対象の膜
	 */
	private void resetMembrane(Membrane mem){
		button = new JButton("button");	
	}
}
