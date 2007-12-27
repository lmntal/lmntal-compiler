package toolkit;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import runtime.Atom;
import runtime.Membrane;
import runtime.StringFunctor;
import util.Util;

/**
 * LMNtalWindowに設置するテキストエリア
 * オブジェクト生成時に，膜を検索し，テキストエリア生成を行う．
 */
public class LMNtalTextArea extends LMNComponent implements KeyListener {

	private JTextArea textarea;
	private JScrollPane scroll;
	private String text;
//	private Atom textatom;

//	final static
//	private Functor TEXT_ATOM = new SymbolFunctor("text",1);
	
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	/**
	 * @param lmnWindow テキストエリアを追加するウィンドウ
	 * @param mem 検索対象の膜
	 */
	public LMNtalTextArea(LMNtalWindow lmnWindow, Membrane mem){
		super(lmnWindow, mem);
		Util.println("textarea");
	}
	/////////////////////////////////////////////////////////////////

	/**
	 * 膜からボタン生成に必要な情報を取得する．
	 * @param mem 検索対象の膜
	 */
	
	public Component initComponent(){
		textarea = new JTextArea(text);
		textarea.addKeyListener(this); // textareaのKeyListener呼び出し
		textarea.setLineWrap(true);    // 折り返しの設定ON
		scroll = new JScrollPane(textarea);
		return scroll;
	}
	
	public void setMembrane(Membrane mem){
		text = getText(mem);
		if(textarea != null){
			textarea.setText(text);
		}
	}
		
	public void addAtom(){
		if (textatom == null) return;
		text = textarea.getText();
		Atom reatom = textatom.nthAtom(0); // 削除するアトム
		// 今の膜の内容にtextを追加する。StringFunctorには必ずリンクひとつ。
		Atom newatom = getMymem().newAtom(new StringFunctor(text));
        // newatomの0番目と、reatomの0番目の先につながってるものとつなげる。
		getMymem().relink(newatom, 0, reatom, 0);
		reatom.remove(); // リンクの切れたreatomを削除する。
	}

	public void keyTyped(KeyEvent arg0) {
		LMNtalTFrame.addUpdateComponent(this);
	}
	
/*	** text("")のアトムがあったとき、textの内容を取得する *
	public String getText(Membrane mem){
		String text = "";
		Iterator textAtomIte = mem.atomIteratorOfFunctor(TEXT_ATOM);
		if(textAtomIte.hasNext()){
			textatom = (Atom)textAtomIte.next();
			text = textatom.nth(0);
		}
		return text;
	}
*/
	
	public void keyPressed(KeyEvent arg0) {}
	public void keyReleased(KeyEvent arg0) {}
	
}