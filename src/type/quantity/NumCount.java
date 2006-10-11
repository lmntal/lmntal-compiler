package type.quantity;

public class NumCount extends Count{
	public int value;
	public NumCount(int value){
		this.value = value;
	}
	public String toString(){
		return Integer.toString(value);
	}
	public Count add(Count c){
		if(c instanceof NumCount)
			return new NumCount(((NumCount)c).value + value);
		else return super.add(c);
	}
}
