package type.quantity;

public class MinCount extends Count {

	private Count body;
	
	public MinCount(Count body){
		this.body = body;
	}
	public String toString(){
		return "(-" + body + ")";
	}
	public Count self(){
		if(body instanceof MinCount)
			return ((MinCount)body).body.self();
		else return this;
	}
}
