/*
 * ºîÀ®Æü: 2003/12/16
 *
 */
package runtime;

/**
 * @author hara
 *
 */
public interface InlineCode {
	public void run(Atom a, int codeID);
}

class MyInlineCode implements InlineCode {
	public void run(Atom me, int id) {
		Env.p("Exec inline : "+me.getName());
		
		me.getFunctor().getName()
		switch(id) {
		//case eval "/*inline*/;".hashCode():
		case 0:
			;
			break;
		}
	}
}
