package type.quantity;

public class VarCount extends Count {

	private static int current_id = 0;
	
	private FixedCount bound;
	
	private final int id;
	public VarCount(){
		id = current_id++;
	}
	public String toString(){
		return "RV" + id;
	}
	
	public Count add(Count c){
		if(c instanceof FixedCount)return ((FixedCount)c).add(this);
		else return new SumCount(c,this);
	}
	
	public Count reflesh(){
		return this;
	}
	
	public Count inverse(){
		return new MinCount(this);
	}
	
	public void bind(FixedCount vc){
		bound = vc;
	}
	
	public FixedCount evaluate(){
		if(bound == null)System.err.println("fatal error. this var isn't bind : RV" + id);
		return bound;
	}
}
