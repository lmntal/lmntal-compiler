/**
 * 構文解析でのエラー
 */

package compile.parser;

public class ParseException extends Exception {

	public ParseException(String s) {
		super(s);	
	}
}
