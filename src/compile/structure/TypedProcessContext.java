package compile.structure;

final public class TypedProcessContext extends Context {

	// TODO buddy の buddy で相手を参照する
	public LinkOccurrence freeLink;
	
	/**
	 * TODO 仕様を考える、クラス構造
	 * @param name
	 */
	public TypedProcessContext(String name, LinkOccurrence freeLink) {
		super(name);
		this.freeLink = freeLink;
	}
		
}
