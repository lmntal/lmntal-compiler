/*
 * 作成日: 2003/10/21
 *
 */
package runtime;
import java.util.*;

/**
 * 1 つの命令を保持する。
 * 
 * 複数でも構わないとおもう。
 * 
 * @author pa
 *
 */
public class Instruction {
	public static final int NULL      = 0;
	public static final int MEM       = 1;
	public static final int VAR       = 2;
	public static final int NAME      = 3;
	public static final int AT        = 4;
	public static final int METAVAR   = 5;
	
	public List data = new ArrayList();
	
	public Instruction() {
		// たとえば [react, [1, 2, 5]]
		ArrayList sl = new ArrayList();
		sl.add(new Integer(1));
		sl.add(new Integer(2));
		sl.add(new Integer(5));
		data.add("react");
		data.add(sl);
		System.out.println(data);
	}
}
