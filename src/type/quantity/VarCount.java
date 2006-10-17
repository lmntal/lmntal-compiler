package type.quantity;

public class VarCount{

	private static int current_id = 0;
	
	private FixedCount bound;
	
	private final int id;
	public VarCount(){
		id = current_id++;
	}
	public String toString(){
		return "RV" + id;
	}
	
	public void bind(FixedCount vc){
		bound = vc;
	}
	
	public boolean isBound(){
		return bound == null;
	}
	
	public FixedCount evaluate(){
		if(bound == null)
			System.err.println("fatal error. this var isn't bind : RV" + id);
		return bound;
	}
}
