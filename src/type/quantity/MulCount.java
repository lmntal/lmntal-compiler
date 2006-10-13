package type.quantity;


public class MulCount extends Count {

//	private List<Count> counts;
	
	private final int multiple;
	private final Count body;
	
	public MulCount(int multiple, Count body){
		this.multiple = multiple;
		this.body = body;
	}
//	public MulCount(Count first, Count second){
//		this.counts = new LinkedList<Count>();
//		counts.add(first);
//		counts.add(second);
//	}
	public String toString(){
//		if(counts.size() == 0)return "()";
//		else if(counts.size() == 1)return "(" + counts.get(0) + ")";
//		else{
//			String ret = counts.get(0).toString();
//			for(int i=1;i<counts.size();i++)
//				ret += "*" + counts.get(i);
//			return "(" + ret + ")";
//		}
		return "(" + multiple + "*" + body + ")";
	}
	
	public Count reflesh(){
		System.out.println("no support for reflesh in MulCount");
		return new MulCount(multiple, body.reflesh());
	}
	
	public Count add(Count c){
		if(c instanceof FixedCount)return ((FixedCount)c).add(this);
		else return new SumCount(c,this);
	}
	
	public Count inverse(){
		System.out.println("no support for inverse in MulCount");
		return new MulCount(-multiple, body);
	}
	
	public FixedCount evaluate(){
		return body.evaluate().mul(multiple);
	}
}
