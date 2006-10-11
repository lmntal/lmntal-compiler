package type.quantity;

public class OperatorCount extends Count {

	public static final String ADD = "+";
//	public static final String SUB = "-";
	public static final String MUL = "*";
//	public static final String DIV = "/";
	
	private final String op;
	
	private Count c1;
	private Count c2;
	
	public OperatorCount(Count c1, Count c2, String op){
		this.c1 = c1;
		this.c2 = c2;
		this.op = op;
	}
	
	public String toString(){
		return c1 + op + c2;
	}
	
}
