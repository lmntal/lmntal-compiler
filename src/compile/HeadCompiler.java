/*
 * 作成日: 2003/10/28
 *
 */
package compile;

import java.util.*;

/**
 * @author pa
 *
 */
public class HeadCompiler {
	public Membrane m;
	public List freemems = new ArrayList();

	HeadCompiler(Membrane m) {
		this.m = m;
	}

	/**
	 * head の仮引数リストをつくっている。何番目がどのアトム。
	 */
	public void enumformals() {
		/*
		mem.each_atom do | atom |
			# ハッシュ。アトム番号をいれる。
			@atomids[atom] = @atoms.length
			@atoms.push atom
		end
		mem.each_mem do | submem |
			enumformals submem
			@freemems.push submem if submem.natoms + submem.nmems == 0
		end
		*/
	}
}
