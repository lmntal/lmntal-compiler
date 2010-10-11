package type.argument;

import java.util.HashSet;
import java.util.Set;

import type.TypeException;

public class ModeVar {

	/** 相方(逆符号のモード変数) */
	public ModeVar buddy;

	/** このモード変数を持つパス */
	private final Set<Path> pathes;
	
	public Set<Path> getPathes(){
		return pathes;
	}

	/** モード変数名 */
	public final String name;

	/** 符号 */
	public final int sign;

	/** 実際のモード値
	 * 1: 入力
	 * -1 : 出力
	 * 0 : 不明
	 */
	public int value = 0;

	public ModeVar(String name, int sign) {
		pathes = new HashSet<Path>();
		this.name = name;
		this.sign = sign;
	}	
	
	/**
	 * モード変数の値を束縛する。既に束縛されていた場合、異なる符号に束縛しようとすると例外を投げる。
	 * @param s
	 * @throws TypeException
	 */
	public void bindSign(int s) throws TypeException {
		// 不明なら束縛する
		if (value == 0) {
			value = s;
			buddy.value = -s;
		} else if (value == s)
			return;
		else
			throw new TypeException("mode error " + value
					+ " <=> " + s);
	}

	public void add(Path path) {
		pathes.add(path);
	}

	/**
	 * 対象のモード変数を持つパスについて、このモード変数を持たせる
	 * @param ms
	 */
	public void addAll(ModeVar ms) {
		for(Path path : ms.getPathes()){
			add(path);
		}
	}

	public boolean contains(Path path) {
		return pathes.contains(path);
	}

	public String toString() {
		return ("["
				+ (value == 0 ? ("?" + (sign == 1 ? "+" : "-") + "(" + name + ")")
						: value == 1 ? "+" : "-") + "]");
	}
	
	public String shortString(){
		return (value == 0? "<" + (sign == 1 ? "" : "-") + name + ">" : (value == 1 ? "+" : "-"));
	}
	
	public String shortStringLMNSyntax() {
		return (value == 0? "(" + (sign == 1 ? "" : "-") + name + ")" : (value == 1 ? "+" : "-"));
	}

	public boolean equals(Object o) {
		if (o instanceof ModeVar) {
			ModeVar mv = (ModeVar) o;
			return name.equals(mv.name) && sign == mv.sign;
		} else
			return false;
	}

	public int hashCode() {
		return name.hashCode() + sign;
	}

}
