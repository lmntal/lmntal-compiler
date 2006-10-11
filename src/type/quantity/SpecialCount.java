package type.quantity;

public class SpecialCount extends Count {
	private String name;
	public SpecialCount(String name){
		this.name = name;
	}
	public String toString(){
		return name;
	}
}
