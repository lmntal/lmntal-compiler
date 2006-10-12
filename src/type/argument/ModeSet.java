package type.argument;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import type.TypeConstraintException;

public class ModeSet {
	public ModeSet buddy;

	private Set<Path> pathes;

	public String name;

	public int sign;

	public int value = 0;

	public ModeSet() {
		pathes = new HashSet<Path>();
	}

	public void bindSign(int s) throws TypeConstraintException {
		if (value == 0) {
			value = s;
			buddy.value = -s;
		} else if (value == s)
			return;
		else
			throw new TypeConstraintException("mode conflict." + value
					+ " <=> " + s);
	}

	public void add(Path path) {
		pathes.add(path);
	}

	public void addAll(ModeSet ms) {
		Iterator it = ms.iterator();
		while (it.hasNext()) {
			add((Path) it.next());
		}
	}

	public Iterator iterator() {
		return pathes.iterator();
	}

	public boolean contains(Path path) {
		return pathes.contains(path);
	}

	public String toString() {
		return ("["
				+ (value == 0 ? ("?" + (sign == 1 ? "+" : "-") + "(" + name + ")")
						: value == 1 ? "+" : "-") + "]");
	}

	public boolean equals(Object o) {
		if (o instanceof ModeSet) {
			ModeSet ms = (ModeSet) o;
			return name.equals(ms.name) && sign == ms.sign;
		} else
			return false;
	}

	public int hashCode() {
		return name.hashCode() + sign;
	}

}
