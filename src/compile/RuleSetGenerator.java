/*
 * ºîÀ®Æü: 2003/11/18
 *
 */
package compile;

import java.util.Iterator;
import runtime.Env;

/**
 * @author hara
 *
 */
public class RuleSetGenerator {
	public static void run(Membrane m) {
		Env.c("RuleSetGenerator.run");
		
		Iterator i = m.rules.listIterator();
		while(i.hasNext()) {
			RuleStructure rs = (RuleStructure)i.next();
			RuleCompiler rc = new RuleCompiler(rs);
			rc.compile();
		}
	}
}
