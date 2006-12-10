package gui2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * ³ÈÂç½Ì¾®¤ò´ÉÍý¤¹¤ëListener
 * @author nakano
 *
 */
public class CommonListener implements MouseWheelListener , KeyListener{
	static
	private SubFrame subFrame_;
	
	static
	private LogFrame logFrame_;
	
	static
	private GraphPanel panel_;
	
	///////////////////////////////////////////////////////////////////////////
	public CommonListener() { }
	
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
	
	public void keyPressed(KeyEvent e) {
		if(null == panel_){ return; }
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			panel_.loadPrevState();
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			panel_.loadNextState();
		}
	}
	
	public void keyReleased(KeyEvent e) {
	}
	
	public void keyTyped(KeyEvent e) {
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	public void addTime(){
		if(null == logFrame_){ return; }
		logFrame_.addTime();
	}
	
	public String getLog(){
		if(null == logFrame_){ return ""; }
		return logFrame_.getLog();
	}
	
	public void loadNextState(){
		if(null == panel_){ return; }
		panel_.loadNextState();
	}
	
	public void loadPrevState(){
		if(null == panel_){ return; }
		panel_.loadPrevState();
	}

	public void revokeTime(){
		if(null == logFrame_){ return; }
		logFrame_.revokeTime();
	}
	
	public void setHistory(boolean flag){
		if(null == panel_){ return; }
		panel_.setHistory(flag);
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
}
