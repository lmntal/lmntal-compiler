package toolkit;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Iterator;

import runtime.Atom;
import runtime.Functor;
import runtime.Membrane;
import runtime.SymbolFunctor;

abstract
public class LMNComponent {
	
	private Component component;
	private Membrane mymem; //もらってきた膜を保存する。
	
	final static
	private Functor POSITION_FUNCTOR = new SymbolFunctor("position", 2);
	
	final static
	private Functor WEIGHT_FUNCTOR = new SymbolFunctor("weight", 2);
	
	final static //windowSizeではなくてセルの幅の方
	private Functor SIZE_FUNCTOR = new SymbolFunctor("size", 2);
	
	final static
	private Functor TEXT_FUNCTOR = new SymbolFunctor("text",1);

	
	private GridBagConstraints gbc = new GridBagConstraints(); //位置

	private boolean isTextUpdate = false;
	
	public Atom textatom; // textatomはTextAreaでも使うので…
	
	public LMNComponent(LMNtalWindow lmnWindow, Membrane mem){
		mymem = mem;
		setPosition(mem);
		setSize(mem);
		setWeight(mem);
		setMembrane(mem);
		gbc.fill = GridBagConstraints.BOTH; //デフォルトでひきのばし
		component = initComponent();
		GridBagLayout layout = lmnWindow.getGridBagLayout();
		layout.setConstraints(component, gbc);
		lmnWindow.add(component);
		lmnWindow.validate(); // ウィンドウ の更新
	}
	
	public void setMembrane(Membrane mem){
		//LMNtalButton -- Textの追加
		//LMNtalTextArea -- Textの追加
	}
	
	abstract public Component initComponent();
		
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
		double weightX = 0;
		double weightY = 0;
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
	
/*	** label("")のアトムがあったとき、labelに貼る内容を取得する *
	public String getLabel(Membrane mem){
		String label = "object";
		Iterator labelAtomIte = mem.atomIteratorOfFunctor(LABEL_FUNCTOR);
		if(labelAtomIte.hasNext()){
			Atom atom = (Atom)labelAtomIte.next();
			if(label != atom.nth(0))
			{
				label = atom.nth(0);
				isLabelUpdate = true;
			}
		}
		return label;
	}
*/
		
	
	/** text("")のアトムがあったとき、textの内容を取得する */
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

}
