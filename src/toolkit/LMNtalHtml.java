package toolkit;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import runtime.Atom;
import runtime.Membrane;
import runtime.StringFunctor;
import util.Util;

/**
 * LMNtalWindowに設置するテキストエリア
 * オブジェクト生成時に，膜を検索し，テキストエリア生成を行う．
 */
public class LMNtalHtml extends LMNComponent implements KeyListener {

	private JEditorPane html;
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
	public LMNtalHtml(LMNtalWindow lmnWindow, Membrane mem){
		super(lmnWindow, mem);
		Util.println("html");
	}
	/////////////////////////////////////////////////////////////////

	/**
	 * 膜からボタン生成に必要な情報を取得する．
	 * @param mem 検索対象の膜
	 */
	
	public Component initComponent(){
		html = new JEditorPane("text/html",text);
		html.setEditable(false);
//		html.setContentType("text/html");
//		html.setText(text);
		html.addKeyListener(this); // textareaのKeyListener呼び出し
		scroll = new JScrollPane(html);
		return scroll;
	}
	
	public void setMembrane(Membrane mem){
		text = getText(mem);
		if(html != null){
			html.setText(text);
		}
	}
		
	public void addAtom(){
		if (textatom == null) return;
		text = html.getText();
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