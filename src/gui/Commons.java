package gui;

import gui.model.Node;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import runtime.Membrane;

/**
 * @author nakano
 *
 */
public class Commons implements MouseWheelListener {

	static
	private EditFrame editFrame_;
	
	static
	private LogFrame logFrame_;
	
	static
	private GraphPanel panel_;
	
	static
	private SubFrame subFrame_;
	///////////////////////////////////////////////////////////////////////////
	public Commons() { }
	
	public Commons(EditFrame advance) {
		if(advance == null){ return; }
		editFrame_ = advance;
	}
	
	public Commons(GraphPanel panel) {
		if(panel == null){ return; }
		panel_ = panel;
	}
	
	public Commons(LogFrame f) {
		if(f == null){ return; }
		logFrame_ = f;
	}
	
	public Commons(SubFrame f) {
		if(f == null){ return; }
		subFrame_ = f;
	}
	
	///////////////////////////////////////////////////////////////////////////

	public void mouseWheelMoved(MouseWheelEvent e) {
		if(subFrame_ == null){ return; }
		
		subFrame_.setSliderValue(subFrame_.getSliderValue() - e.getWheelRotation());
	}
	
	///////////////////////////////////////////////////////////////////////////


	public void addAtom(String name, Membrane targetMem){
		if(null == panel_){ return; }
		panel_.addAtom(name, targetMem);
	}
	
	public void addRenameAtom(String name, Node node){
		if(null == panel_){ return; }
		panel_.addRenameAtom(name, node);
	}
	
	public void addRenameMembrane(String name, Node node){
		if(null == panel_){ return; }
		panel_.addRenameMembrane(name, node);
	}
	
	public void addMembrane(String name, Membrane targetMem){
		if(null == panel_){ return; }
		panel_.addMembrane(name, targetMem);
	}
	
	public void addTime(){
		if(null == logFrame_){ return; }
		logFrame_.addTime();
	}
	
	public void autoFocus(){
		if(null == panel_){ return; }
		panel_.autoFocus();
	}
	
	public String getLog(){
		if(null == logFrame_){ return ""; }
		return logFrame_.getLog();
	}
	
	public void moveRotate(double angle){
		if(null == panel_){ return; }
		panel_.moveRotate(angle);
	}

	public void revokeTime(){
		LogFrame.revokeTime();
	}
	
	public void setEditLinkMode(boolean flag){
		if(null == panel_){ return; }
		panel_.setEditLinkMode(flag);
	}
	
	public void setHistory(boolean flag){
		if(null == panel_){ return; }
		panel_.setHistory(flag);
	}
	
	public void setLocalHeatingMode(boolean flag){
		if(null == panel_){ return; }
		panel_.setLocalHeatingMode(flag);
	}
	
	public void setShowRules(boolean flag){
		if(null == panel_){ return; }
		panel_.setShowRules(flag);
	}

	public void loadState(int value){
		if(null == panel_){ return; }
		panel_.loadState(value);
	}
	
	public void setLog(String log){
		if(null == logFrame_){ return; }
		logFrame_.setLog(log);
	}
	
	public void setSelectedNode(Node node){
		if(null == editFrame_){ return; }
		editFrame_.setSelectedNode(node);
	}
	
	public void setMagnificationSliderValue(int value){
		if(null == subFrame_){ return; }
		subFrame_.setSliderValue(value);
	}
	
	public void setUncalc(boolean flag){
		if(null == panel_){ return; }
		panel_.setUncalc(flag);
	}
}
