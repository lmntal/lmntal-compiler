package type.quantity;

public class OrCount extends Count{

	private Count c1;
	private Count c2;
	
	public OrCount(Count c1, Count c2){
		this.c1 = c1;
		this.c2 = c2;
	}
	public OrCount(Count c){
		this.c1 = c;
		this.c2 = new NumCount(0);
	}
	
	public String toString(){
		return c1 + " or " + c2;
	}
}
