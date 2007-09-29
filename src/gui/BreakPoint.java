package gui;

import java.util.HashSet;

public class BreakPoint {
	
	static
	private BreakPointFrame breakPointFrame_;
	
	static
	public void setBreakPoint(){
		if(!breakPointFlag_){
			if(breakPointFrame_ != null){
				breakPointFrame_.setVisible(false);
			}
			return;
		}
		if(breakPointFrame_ == null){
			breakPointFrame_ = new BreakPointFrame();
		} else {
			breakPointFrame_.setVisible(true);
		}
	}
	
	static 
	public void setBreakPointFlag(boolean breakPointFlag) {
		breakPointFlag_ = breakPointFlag;
		setBreakPoint();
	}


	static
	public void setCurrentRuleName(String name){
		currentRuleName_ = name;
	}
	
	static
	public void addBreakPoint(String rule){
		breakPointRule_.add(rule);
	}
	
	static
	public void removeBreakPoint(String rule){
		breakPointRule_.remove(rule);
	}
	
	static
	public boolean isBreakPoint(){
		return breakPointRule_.contains(currentRuleName_);
	}

	
	static
	private String currentRuleName_ = "";
	
	static
	private HashSet<String> breakPointRule_ = new HashSet<String>();
	
	static 
	public boolean breakPointFlag_ = false;

}
