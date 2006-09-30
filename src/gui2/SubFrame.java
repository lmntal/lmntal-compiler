package gui2;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
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
	public int WINDOW_WIDTH = 200;
	
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

	static private JList memList = new JList();
	static private DefaultListModel model = new DefaultListModel();
	
	private JButton bt2 = new JButton("OK!?");
	
	private JSlider js1 = new JSlider(JSlider.VERTICAL, SLIDER_MIN, SLIDER_MAX, SLIDER_DEF);
	
	private LMNtalFrame mainFrame;
	
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
		memList.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		memList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		memList.addListSelectionListener(new MemListSelectionListener());
		
        JScrollPane sp = new JScrollPane();
        sp.getViewport().setView(memList);
		sp.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        
		bt2.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		bt2.setActionCommand("window");
		
		goBt.addActionListener(new ActionAdapter(this));

		js1.addChangeListener(new SliderChanged());
		js1.setPaintTicks(true);      //目盛りを表示
		js1.setMinorTickSpacing(2);   //小目盛りの間隔を設定
		js1.setMajorTickSpacing(10);  //大目盛りの間隔を設定
		js1.setLabelTable(js1.createStandardLabels(10)); //目盛りﾗﾍﾞﾙを10間隔で表示
		js1.setPaintLabels(true);    //目盛りﾗﾍﾞﾙを表示

		setTitle(TITLE);
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(goBt, BorderLayout.PAGE_START);
		getContentPane().add(js1, BorderLayout.LINE_START);
		getContentPane().add(buttonPanel, BorderLayout.CENTER);
		
		buttonPanel.add(sp);
		buttonPanel.add(bt2);
		//bt1.addActionListener(new ActionAdapter(this));
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
	
    class MemCellRenderer extends JCheckBox implements ListCellRenderer{
    	
        public MemCellRenderer() {
        }

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
            return this;
        }
    }
    
    class MemListSelectionListener implements ListSelectionListener {
    	private Set<GraphMembrane> selectedItem = new HashSet<GraphMembrane>();
    	
    	public MemListSelectionListener(){
    		super();
    	}

		public void valueChanged(ListSelectionEvent e) {
        	int selectedIndex = memList.getSelectedIndex(); 
        	if((selectedIndex < 0) || (selectedIndex >= model.getSize())){ return; }
        	GraphMembrane mem = (GraphMembrane)model.getElementAt(selectedIndex);
            if(selectedItem.contains(mem)){
            	mem.setViewInside(true);
            	selectedItem.remove(mem);
            } else {
            	mem.setViewInside(false);
            	selectedItem.add(mem);
            }
            memList.clearSelection();
		}
    	
    }
}
