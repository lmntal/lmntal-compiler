package type.argument;


/**
 * @author kudo
 * 
 */
public class PolarizedPath {
	private int pol;

	private Path path;

	public PolarizedPath(int pol, Path path) {
		this.pol = pol;
		this.path = path;
	}

	public PolarizedPath inverse() {
		pol *= -1;
		return this;
	}

	public Path getPath() {
		return path;
	}

	public int getSign() {
		return pol;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public int hashCode(){
		return pol + path.hashCode();
	}
	
	public boolean equals(Object o) {
		if (o instanceof PolarizedPath) {
			PolarizedPath pp = (PolarizedPath) o;
			return (pol == pp.pol && path.equals(pp.path));
		} else
			return false;
	}

	public String toString() {
		return (pol == -1 ? "-" : "") + path;
	}
}
