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
	private Functor POSITION_ATOM = new SymbolFunctor("position",2);
	
	final static
	private Functor WEIGHT_ATOM = new SymbolFunctor("weight",2);
	
	final static
	private Functor LABEL_ATOM = new SymbolFunctor("label",1);
	
	private GridBagConstraints gbc = new GridBagConstraints(); //位置

	private boolean isLabelUpdate = false;
	
	
	public LMNComponent(LMNtalWindow lmnWindow, Membrane mem){
		mymem = mem;
		setPosition(mem);
		setWeight(mem);
		setMembrane(mem);
		gbc.fill = GridBagConstraints.BOTH;
		component = initComponent();
		GridBagLayout layout = lmnWindow.getGridBagLayout();
		layout.setConstraints(component, gbc);
		lmnWindow.add(component);
		lmnWindow.validate(); // ウィンドウ の更新
	}
	
	public void setMembrane(Membrane mem){
		//LMNtalButton -- Labelの追加
		//LMNtalTextArea -- Textの追加
	}
	
	abstract public Component initComponent();
		
	/** position(X,Y)のアトムがあったとき、gridxとgridyを取得する(単位はGridBag)。 */
	public void setPosition (Membrane mem) {
		int positionX = 0;
		int positionY = 0;
		Iterator positionAtomIte = mem.atomIteratorOfFunctor(POSITION_ATOM);
		if(positionAtomIte.hasNext()){
			Atom atom = (Atom)positionAtomIte.next();
			positionX = Integer.parseInt(atom.nth(0));
			positionY = Integer.parseInt(atom.nth(1));
		}
		gbc.gridx = positionX;
		gbc.gridy = positionY;			
	}

	/** weight(X,Y)のアトムがあったとき、weightxとweightyを取得する(単位はGridBag)。*/
	public void setWeight (Membrane mem) {
		double weightX = 0;
		double weightY = 0;
		Iterator weightAtomIte = mem.atomIteratorOfFunctor(WEIGHT_ATOM);
		if(weightAtomIte.hasNext()){
			Atom atom = (Atom)weightAtomIte.next();
			weightX = Double.parseDouble(atom.nth(0));
			weightY = Double.parseDouble(atom.nth(1));
		}
		System.out.println(weightX);
		gbc.weightx = weightX;
		gbc.weighty = weightY;			
	}
	
	/** label("")のアトムがあったとき、labelに貼る内容を取得する */
	public String getLabel(Membrane mem){
		String label = "object";
		Iterator labelAtomIte = mem.atomIteratorOfFunctor(LABEL_ATOM);
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
		
	public void resetMembrane (Membrane mem){
		mymem = mem;
		setMembrane(mem);
	}

	public boolean getLabelUpdate(){
		return isLabelUpdate;
	}
	
	public void setLabelUpdate(boolean update){
		isLabelUpdate = update;
	}
	
	public void addAtom(){}
	
	public Membrane getMymem(){
		return mymem;
	}

}
