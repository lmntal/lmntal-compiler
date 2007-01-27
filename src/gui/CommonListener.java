package gui;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * ³ÈÂç½Ì¾®¤ò´ÉÍý¤¹¤ëListener
 * @author nakano
 *
 */
public class CommonListener implements MouseWheelListener {

	static
	private AdvanceFrame advance_;
	
	static
	private LogFrame logFrame_;
	
	static
	private GraphPanel panel_;
	
	static
	private SubFrame subFrame_;
	///////////////////////////////////////////////////////////////////////////
	public CommonListener() { }
	
	public CommonListener(AdvanceFrame advance) {
		if(advance == null){ return; }
		advance_ = advance;
	}
	
	public CommonListener(GraphPanel panel) {
		if(panel == null){ return; }
		panel_ = panel;
	}
	
	public CommonListener(LogFrame f) {
		if(f == null){ return; }
		logFrame_ = f;
	}
	
	public CommonListener(SubFrame f) {
		if(f == null){ return; }
		subFrame_ = f;
	}
	
	///////////////////////////////////////////////////////////////////////////

	public void mouseWheelMoved(MouseWheelEvent e) {
		if(subFrame_ == null){ return; }
		
		subFrame_.setSliderValue(subFrame_.getSliderValue() - e.getWheelRotation());
	}
	
	///////////////////////////////////////////////////////////////////////////
	
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

	public void revokeTime(){
		if(null == logFrame_){ return; }
		logFrame_.revokeTime();
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

	public void setState(int value){
		if(null == panel_){ return; }
		panel_.loadState(value);
	}
	
	public void setLog(String log){
		if(null == logFrame_){ return; }
		logFrame_.setLog(log);
	}
	
	public void setSelectedNode(Node node){
		if(null == advance_){ return; }
		advance_.setSelectedNode(node);
	}
	
	public void setMagnificationSliderValue(int value){
		if(null == subFrame_){ return; }
		subFrame_.setSliderValue(value);
	}
}
