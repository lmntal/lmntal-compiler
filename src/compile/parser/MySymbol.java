package compile.parser;

import java_cup.runtime.Symbol;

public class MySymbol extends Symbol {
	/** トークンの、ソース中の表現 */
	public String token;
	public MySymbol(String token, int id, int line, int column, Object o) {
		super(id, line, column, o);
		this.token = token;
	}
	public MySymbol(String token, int id, int line, int column) {
		super(id, line, column);
		this.token = token;
	}
}
