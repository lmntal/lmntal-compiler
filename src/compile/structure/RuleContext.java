package compile.structure;

/** ソースコード中のルール文脈の構造を表すクラス */
public final class RuleContext extends Context{
	public RuleContext(String name) {
		super(name);
	}
	
	public String toString() {
		return "@" + getName();
	}
}
