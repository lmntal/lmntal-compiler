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
	public void run(Atom a);
}

//class MyInlineCode implements InlineCode {
//	public void run(Atom a) {
//		Env.p("Exec inline : "+a.getName());
//		
//		switch(a.getName().hashCode()) {
//		//case eval "/*inline*/;".hashCode():
//		case 0:
//			;
//			break;
//		}
//	}
//}
