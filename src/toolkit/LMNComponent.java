package toolkit;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Iterator;

import javax.swing.JLabel;

import runtime.Atom;
import runtime.Functor;
import runtime.Membrane;
import runtime.SymbolFunctor;

abstract
public class LMNComponent {
	
	private Component component;
	private Membrane mymem; //もらってきた膜を保存する。
	
	final static // 描画物の位置
	private Functor POSITION_FUNCTOR = new SymbolFunctor("position", 2);
		
	final static // 描画物の比重(デフォルト１)
	private Functor SIZE_FUNCTOR = new SymbolFunctor("size", 2);

	final static // 描画物の余分なスペースの分配比率(デフォルト０)
	private Functor WEIGHT_FUNCTOR = new SymbolFunctor("weight", 2);

	final static // 描画物の内側の周囲の大きさ(デフォルト０)
	private Functor IPAD_FUNCTOR = new SymbolFunctor("ipad", 2);

	final static // 描画物の格子内の配置位置
	private Functor ANCHOR_FUNCTOR = new SymbolFunctor("anchor", 1);
	
	final static
	private Functor TEXT_FUNCTOR = new SymbolFunctor("text", 1);
	
	final static // 描画するかどうか
	private Functor VISIBLE_FUNCTOR = new SymbolFunctor("visible",1);
		
	private GridBagConstraints gbc = new GridBagConstraints(); //位置

	private boolean isTextUpdate = false;
	private boolean visible = true;
	
	public Atom textatom; // textatomはTextAreaでも使うので共有してみる
	
	public LMNComponent(LMNtalWindow lmnWindow, Membrane mem){
		mymem = mem;
		setPosition(mem);
		setSize(mem);
		setWeight(mem);
		setIpad(mem);
		setMembrane(mem);
		setAnchor(mem);
		checkVisible(mem);
		if (visible==false) component = noComponent();
		  else component = initComponent();
		GridBagLayout layout = lmnWindow.getGridBagLayout();
		layout.setConstraints(component, gbc);
		lmnWindow.add(component);
		lmnWindow.validate(); // ウィンドウ の更新
	}
	
	public void setMembrane(Membrane mem){
		// ここで各種コンポーネントの設定
		//LMNtalButton -- Textの追加
		//LMNtalTextArea -- Textの追加
	}
	
	public Component getComponent(){
		return component;
	}
	
	abstract public Component initComponent();
	
	public Component noComponent(){
		JLabel label = new JLabel();
		return label;
	}
		
	/** position(X,Y)のアトムがあったとき、gridxとgridyを取得する(単位はGridBag)。 */
	public void setPosition (Membrane mem) {
		int positionX = 0;
		int positionY = 0;
		Iterator positionAtomIte = mem.atomIteratorOfFunctor(POSITION_FUNCTOR);
		if(positionAtomIte.hasNext()){
			Atom atom = (Atom)positionAtomIte.next();
			positionX = Integer.parseInt(atom.nth(0));
			positionY = Integer.parseInt(atom.nth(1));
		}
		gbc.gridx = positionX;
		gbc.gridy = positionY;			
	}

	/** size(X,Y)のアトムがあったとき、gridwidthとgridheightを取得する(単位はGridBag)。 */
	public void setSize (Membrane mem) {
		int sizeX = 1; //デフォルトでは１の幅
		int sizeY = 1; //デフォルトでは１の高さ
		Iterator sizeAtomIte = mem.atomIteratorOfFunctor(SIZE_FUNCTOR);
		if(sizeAtomIte.hasNext()){
			Atom atom = (Atom)sizeAtomIte.next();
			sizeX = Integer.parseInt(atom.nth(0));
			sizeY = Integer.parseInt(atom.nth(1));
		}
		gbc.gridwidth = sizeX;
		gbc.gridheight = sizeY;			
	}
		
	/** weight(X,Y)のアトムがあったとき、weightxとweightyを取得する(単位はGridBag)。*/
	public void setWeight (Membrane mem) {
		double weightX = 1.0;
		double weightY = 1.0;
		Iterator weightAtomIte = mem.atomIteratorOfFunctor(WEIGHT_FUNCTOR);
		if(weightAtomIte.hasNext()){
			Atom atom = (Atom)weightAtomIte.next();
			weightX = Double.parseDouble(atom.nth(0));
			weightY = Double.parseDouble(atom.nth(1));
		}
//		System.out.println(weightX);
		gbc.weightx = weightX;
		gbc.weighty = weightY;			
	}

	/** ipad(X,Y)のアトムがあったとき、ipadxとipadyを取得する(単位はGridBag)。*/
	public void setIpad (Membrane mem) {
		int ipadX = 0;
		int ipadY = 0;
		Iterator weightAtomIte = mem.atomIteratorOfFunctor(IPAD_FUNCTOR);
		if(weightAtomIte.hasNext()){
			Atom atom = (Atom)weightAtomIte.next();
			ipadX = Integer.parseInt(atom.nth(0));
			ipadY = Integer.parseInt(atom.nth(1));
		}
		gbc.ipadx = ipadX;
		gbc.ipady = ipadY;			
	}

	public void setAnchor(Membrane mem){
		String anc;
		Iterator anchorAtomIte = mem.atomIteratorOfFunctor(ANCHOR_FUNCTOR);
		gbc.fill = GridBagConstraints.NONE;
		if(anchorAtomIte.hasNext()){
			Atom atom = (Atom)anchorAtomIte.next();	
			anc = atom.nth(0);
			anc = anc.toUpperCase(); // 全て大文字に変換
			gbc.anchor = GridBagConstraints.CENTER; // デフォルトCENTER
			if (anc.equals("NORTHWEST"))
				gbc.anchor = GridBagConstraints.NORTHWEST;
			if (anc.equals("NORTH"))
				gbc.anchor = GridBagConstraints.NORTH;
			if (anc.equals("NORTHEAST"))
				gbc.anchor = GridBagConstraints.NORTHEAST;
			if (anc.equals("WEST"))
				gbc.anchor = GridBagConstraints.WEST;
			if (anc.equals("EAST"))
				gbc.anchor = GridBagConstraints.EAST;
			if (anc.equals("SOUTHWEST"))
				gbc.anchor = GridBagConstraints.SOUTHWEST;
			if (anc.equals("SOUTH"))
				gbc.anchor = GridBagConstraints.SOUTH;	
			if (anc.equals("SOUTHEAST"))
				gbc.anchor = GridBagConstraints.SOUTHEAST;
		} else 
			gbc.fill = GridBagConstraints.BOTH;
	}
	
	/** text("")のアトムがあったとき、textの内容を取得する。
	 *  textは割と共通なのでここで取得して使いまわす)
	 */
	public String getText(Membrane mem){
		String text = "";
		Iterator textAtomIte = mem.atomIteratorOfFunctor(TEXT_FUNCTOR);
		if(textAtomIte.hasNext()){
			textatom = (Atom)textAtomIte.next();
			if(text != textatom.nth(0))
			{
				text = textatom.nth(0);
				isTextUpdate = true;
			}
		}
		return text;
	}
	
	public void resetMembrane (Membrane mem){
		mymem = mem;
		setMembrane(mem);
	}

	public boolean getTextUpdate(){
		return isTextUpdate;
	}
	
	public void setTextUpdate(boolean update){
		isTextUpdate = update;
	}
	
	public void addAtom(){}
	
	public Membrane getMymem(){
		return mymem;
	}
	
	// visible(false)があればfalse
	public boolean checkVisible(Membrane mem){
		Iterator visibleAtomIte = mem.atomIteratorOfFunctor(VISIBLE_FUNCTOR);
		if(visibleAtomIte.hasNext()){
			Atom atom = (Atom)visibleAtomIte.next();
			if(atom.nth(0) == "false" ) visible = false;
			else visible = true;
		}
		return visible;		
	}

	
}
