package type.argument;

/**
 * @author kudo
 *
 */
public interface Path {
	int hashCode();
	boolean equals(Object o);
	
	String toStringWithOutAnonMem();
	
}
