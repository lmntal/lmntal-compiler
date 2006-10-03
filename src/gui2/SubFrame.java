package gui2;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SubFrame extends JFrame {

	/////////////////////////////////////////////////////////////////
	// 定数
	
	final static
	public int WINDOW_WIDTH = 250;
	
	final static
	public int WINDOW_HIEGHT = 600;
	
	final static
	public String TITLE = "Control Panel";
	
	final static
	private int SLIDER_MIN = 0;
	
	final static
	private int SLIDER_MAX = 100;
	
	final static
	private int SLIDER_DEF = 30;
	
	/////////////////////////////////////////////////////////////////
	
	// 倍率
	static public int magnification;
	
	private JPanel buttonPanel;
	
	private JButton goBt = new JButton("Go ahead");
	
	private JButton hideBt = new JButton("Hide All");

	private JButton showBt = new JButton("Show All");

	static private JList memList = new JList();
	static private DefaultListModel model = new DefaultListModel();
	
	private JSlider js1 = new JSlider(JSlider.VERTICAL, SLIDER_MIN, SLIDER_MAX, SLIDER_DEF);
	
	private LMNtalFrame mainFrame;
	
	private MemListSelectionListener memListener;
	
	/////////////////////////////////////////////////////////////////
	// コンストラクタ
	public SubFrame(LMNtalFrame f) {
		
		mainFrame = f;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_WIDTH, WINDOW_HIEGHT);
		setLocation(LMNtalFrame.WINDOW_WIDTH, 0);
		
		initComponents();
		
		LMNtalFrame.setMagnification((double)js1.getValue() / (double)js1.getMaximum());
		setVisible(true);
	}
	/////////////////////////////////////////////////////////////////
	
	private void initComponents() {
		
		MemCellRenderer renderer = new MemCellRenderer();
		memList.setCellRenderer(renderer);
		memList.setModel(model);
//		memList.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		memList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		memListener = new MemListSelectionListener();
		memList.addListSelectionListener(memListener);
		memList.addMouseListener(memListener);
		memList.addMouseWheelListener(memListener);
		
        JScrollPane sp = new JScrollPane();
        sp.getViewport().setView(memList);
//		sp.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
//        
//		showBt.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.SIZE));
//		hideBt.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.SIZE));

		goBt.addActionListener(new ActionAdapter(this));
		showBt.addActionListener(new ShowAllAdapter(this));
		hideBt.addActionListener(new HideAllAdapter(this));

		js1.addChangeListener(new SliderChanged());
		js1.setPaintTicks(true);      //目盛りを表示
		js1.setMinorTickSpacing(2);   //小目盛りの間隔を設定
		js1.setMajorTickSpacing(10);  //大目盛りの間隔を設定
		js1.setLabelTable(js1.createStandardLabels(10)); //目盛りﾗﾍﾞﾙを10間隔で表示
		js1.setPaintLabels(true);    //目盛りﾗﾍﾞﾙを表示

		setTitle(TITLE);
//		buttonPanel = new JPanel();
//		buttonPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
//		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
		getContentPane().add(goBt, gbc);

		gbc.gridy = 1;
		gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
		getContentPane().add(js1, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
		getContentPane().add(showBt, gbc);

		gbc.gridx = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
		getContentPane().add(hideBt, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
		getContentPane().add(sp, gbc);
		
//		buttonPanel.add(showBt);
//		buttonPanel.add(hideBt);
//		buttonPanel.add(sp);
	}

	static
	public void resetList(Map memMap){
		if(memList == null){ return; }
		
		// 未登録の要素を追加
		Collection<GraphMembrane> memSet = memMap.values();
		Iterator<GraphMembrane> mems = memSet.iterator();
		while(mems.hasNext()){
			GraphMembrane mem = mems.next();
			if(!model.contains(mem)){ 
				model.addElement(mem);
			}			
		}

	}
	
	static
	public void initModel(){
		model.removeAllElements();
	}
	
	public class SliderChanged  implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			LMNtalFrame.setMagnification((double)source.getValue() / (double)source.getMaximum());
		}
	}

	private class ActionAdapter implements ActionListener {
		SubFrame frame;
		ActionAdapter(SubFrame f) {
			frame = f;
		}
		public void actionPerformed(ActionEvent e) {
			frame.mainFrame.stopCalc = false;
			initModel();
		}
	}
	
	private class ShowAllAdapter implements ActionListener {
		SubFrame frame;
		ShowAllAdapter(SubFrame f) {
			frame = f;
		}
		public void actionPerformed(ActionEvent e) {
			frame.mainFrame.showAll();
			initModel();
		}
	}
	
	private class HideAllAdapter implements ActionListener {
		SubFrame frame;
		HideAllAdapter(SubFrame f) {
			frame = f;
		}
		public void actionPerformed(ActionEvent e) {
			frame.mainFrame.hideAll();
			initModel();
		}
	}
	
    class MemCellRenderer extends JCheckBox implements ListCellRenderer{
    	
        public MemCellRenderer() { }

        public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus)
        {

        	GraphMembrane mem = (GraphMembrane)value;
            setText(mem.toString());
            this.setSelected(mem.getViewInside());
            if(mem.getViewInside() && memListener != null){
            	memListener.addSelectedItem(mem);
            } else if(memListener != null) {
            	memListener.removeSelectedItem(mem);
            }
            return this;
        }
    }
    
    class MemListSelectionListener 
    implements ListSelectionListener,
    MouseListener,
    MouseWheelListener 
    {
    	private Set<GraphMembrane> selectedItem = new HashSet<GraphMembrane>();
    	private boolean clicked = false;
    	
    	public MemListSelectionListener(){
    		super();
    	}

    	public void addSelectedItem(GraphMembrane mem){
    		selectedItem.add(mem);
    	}
    	
    	public void removeSelectedItem(GraphMembrane mem){
    		selectedItem.remove(mem);
    	}

		public void valueChanged(ListSelectionEvent e) {
			if(clicked){
	            memList.clearSelection();
	            return;
			}
        	int selectedIndex = memList.getSelectedIndex(); 
        	if((selectedIndex < 0) || (selectedIndex >= model.getSize())){ return; }
        	GraphMembrane mem = (GraphMembrane)model.getElementAt(selectedIndex);
            if(selectedItem.contains(mem)){
            	mem.setViewInside(false);
            	selectedItem.remove(mem);
            } else {
            	mem.setViewInside(true);
            	selectedItem.add(mem);
            }
            memList.clearSelection();
            clicked = true;
		}

		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			clicked = false;
			
		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseWheelMoved(MouseWheelEvent e) {
			// TODO Auto-generated method stub
			if(e.getUnitsToScroll() < 0){
				int delta = e.getScrollAmount();
				for(; delta > 0; delta--){
					if(js1.getValue() >= js1.getMaximum()){
						break;
					}
					js1.setValue(js1.getValue() + 1);
					break;
				}
			} 
			else if(e.getUnitsToScroll() > 0){
				int delta = e.getScrollAmount();
				for(; delta > 0; delta--){
					if(js1.getValue() <= js1.getMinimum()){
						break;
					}
					js1.setValue(js1.getValue() - 1);
					break;
				}
			}
		}
    	
    }
}
