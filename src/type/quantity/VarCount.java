package type.quantity;

public class VarCount extends Count {

	private static int current_id = 0;
	
	private int id;
	public VarCount(){
		id = current_id++;
	}
	public String toString(){
		return "RV" + id;
	}
}
