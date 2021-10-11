/**
	This is LMNtal Lex file.
*/

/* --------------------------Usercode Section------------------------ */
package compile.parser;
import java_cup.runtime.Symbol;

%%

/* -----------------Options and Declarations Section----------------- */
%class Lexer

%{
	private final boolean _DEBUG = true;
	StringBuffer string = new StringBuffer();
	StringBuffer token = new StringBuffer();
	int startline, startcol;

    private Symbol symbol(int type) {
    	return symbol(type, null, yytext(), yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value) {
    	return symbol(type, value, yytext(), yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value, String token) {
    	return symbol(type, value, token, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value, String token, int line, int col) {
        return new MySymbol(token, type, line+1, col+1, value);
    }
%}

%line
%column
%unicode
%cup

LineTerminator = \r|\n|\r\n|\f
InputCharacter = ([^\r\n\f]|Character)
WhiteSpace     = {LineTerminator} | [ \t]

LinkName       = [A-Z_][A-Za-z_0-9]*

////////////////////////////////////////////////////////////////
//
// アトム名関係

AtomName = [a-z][A-Za-z_0-9]* | [0-9]+

// AtomNameに加えて0引数でアトム名となる文字列その１（AtomNameと排他的でなければならない）

NumberName = [0-9]*\.[0-9]+ | [0-9]*\.?[0-9]+ [Ee][+-]?[0-9]+ | 0x[0-9a-fA-F]+

CharCodeLiteral = "#\"" . "\""

// AtomNameに加えて0引数でアトム名となる文字列その２、仮。
// （:の構文解析が決定するまでの間、:もここで取り込む。:は直ちに.に置換される→決定したので:取り込みは廃止）
// （注意）モジュール名の先頭文字は a-z に限定してあります

PathedAtomName = [a-z][A-Za-z_0-9]* [\.] [a-z0-9][A-Za-z_0-9]*

// 仮
SymbolName = "'" [^'\r\n]+ "'" | "'" [^'\r\n]* ("''" [^'\r\n]*)+ "'"

// ↓Stringは<STRING>に移管したため廃止
// String = "\"" ("\\\"" | [^\"\r\n]* | "\\"[\r]?"\n" )* "\""
// ↓Inlineは<QUOTEd>に移管したため廃止
// Inline = "[[" [^*] ~"]]"

%state QUOTED STRING COMMENT COMMENT_INNER

////////////////////////////////////////////////////////////////

RelativeOp = "=" | "\\==" | "==" | "!=" | "\\=" | "::" | "?" | {IntegerRelativeOp} | {FloatingRelativeOp}//seiji
IntegerRelativeOp  = "<" | ">" | ">=" | "=<" | "=:=" | "=\\="
FloatingRelativeOp = "<."| ">."| ">=."| "=<."| "=:=."| "=\\=."

//Comment = {TraditionalComment} | {EndOfLineComment}
Comment = {EndOfLineComment}

//TraditionalComment = "/*" [^*] ~"*/"
EndOfLineComment = ("//"|"%"|"#") {InputCharacter}* {LineTerminator}?

HyperLinkOp = "><" | ">*<" | ">+<" |">>" | "<<" 

%% 

/* ------------------------Lexical Rules Section---------------------- */

<YYINITIAL> {
	"/*"                { yybegin(COMMENT); }
	","					{ return symbol(sym.COMMA); }
	"("					{ return symbol(sym.LPAREN); }
	")"					{ return symbol(sym.RPAREN); }
	"{"					{ return symbol(sym.LBRACE); }
	"}"					{ return symbol(sym.RBRACE); }
	"}/"				{ return symbol(sym.RBRACE_SLASH); }
	"}@"				{ return symbol(sym.RBRACE_AT); }
	"}/@"				{ return symbol(sym.RBRACE_SLASH_AT); }
	"}_"				{ return symbol(sym.RBRACE_UNDERBAR); }
	"}_/"				{ return symbol(sym.RBRACE_UNDERBAR_SLASH); }
	"}_@"				{ return symbol(sym.RBRACE_UNDERBAR_AT); }
	"}_/@"				{ return symbol(sym.RBRACE_UNDERBAR_SLASH_AT); }
	"}*"				{ return symbol(sym.RBRACE_ASTERISK); }
	":"					{ return symbol(sym.COLON); }
	":-"				{ return symbol(sym.RULE, new Integer(yyline+1)); }
	"\\"				{ return symbol(sym.BACKSLASH); }
	"."					{ return symbol(sym.PERIOD); }
	"|"					{ return symbol(sym.GUARD); }
	{RelativeOp}		{ return symbol(sym.RELOP, yytext()); }
	"*."				{ return symbol(sym.ASTERISK_DOT); }
	"/."				{ return symbol(sym.SLASH_DOT); }
	"-."				{ return symbol(sym.MINUS_DOT); }
	"+."				{ return symbol(sym.PLUS_DOT); }
	"^"					{ return symbol(sym.HAT); }
	"~"					{ return symbol(sym.TILDE); }
	"**"				{ return symbol(sym.ASTERISK_ASTERISK); }
	"$"					{ return symbol(sym.PROCVAR); }
	"@"					{ return symbol(sym.RULEVAR); }
	"*"					{ return symbol(sym.ASTERISK); }
	"/"					{ return symbol(sym.SLASH); }
	"+"					{ return symbol(sym.PLUS); }
	"-"					{ return symbol(sym.MINUS); }
	"!"					{ return symbol(sym.EXCLA); }//seiji
	"["					{ return symbol(sym.LBRACKET); }
	"]"					{ return symbol(sym.RBRACKET); }
	"mod" 				{ return symbol(sym.MOD); }
	"logand"			{ return symbol(sym.LOGAND); }//ueda
	"logior"			{ return symbol(sym.LOGIOR); }//ueda
	"logxor"			{ return symbol(sym.LOGXOR); }//ueda
//	"lognot"			{ return symbol(sym.LOGNOT); }//ueda
//	"ash"				{ return symbol(sym.ASH); }//ueda
	"typedef"			{ return symbol(sym.TYPEDEF, new Integer(yyline+1)); }//taguchi
	"\\+"				{ return symbol(sym.NEGATIVE); }
	"@@" 				{ return symbol(sym.RULENAMESEP); }
	{HyperLinkOp}		{ return symbol(sym.HL, yytext()); }//seiji
	"[:" 				{ string.setLength(0); token.setLength(0); token.append(yytext()); startline = yyline; startcol = yycolumn; yybegin(QUOTED); }
	"\""				{ string.setLength(0); token.setLength(0); token.append(yytext()); startline = yyline; startcol = yycolumn; yybegin(STRING); }
	{LinkName}			{ return symbol(sym.LINK_NAME,			yytext(), yytext(), yyline, yycolumn); }
	{NumberName}		{ return symbol(sym.NUMBER_NAME,		yytext()); }
	{CharCodeLiteral}	{ return symbol(sym.CHAR_CODE_LITERAL,	yytext()); }
	{SymbolName}		{ return symbol(sym.SYMBOL_NAME,		yytext()); }
	{PathedAtomName}	{ return symbol(sym.PATHED_ATOM_NAME,	yytext()); }
	{AtomName}			{ return symbol(sym.ATOM_NAME,			yytext()); }
	{WhiteSpace}		{ /* just skip */ }
	{Comment}			{ /* just skip */ }
}

<QUOTED> {
	":]"                { token.append(yytext()); yybegin(YYINITIAL); return symbol(sym.SYMBOL_NAME, string.toString(), token.toString(), startline, startcol); }
	<<EOF>>             { throw new Error("EOF in quoted name"); }
	.|{LineTerminator}  { token.append(yytext()); string.append( yytext() ); }
}

<STRING> {
	"\""                { token.append(yytext()); yybegin(YYINITIAL); return symbol(sym.STRING, string.toString(), token.toString(), startline, startcol); }
	"\\\\"				{ token.append(yytext()); string.append("\\"); }
	"\\"[\r]?"\n"		{ /* just skip */ }			// 行末の \ および引き続く改行は無視する
	"\\\""				{ token.append(yytext()); string.append("\""); }
	"\\r"				{ token.append(yytext()); string.append("\r"); }
	"\\n"				{ token.append(yytext()); string.append("\n"); }
	"\\f"				{ token.append(yytext()); string.append("\f"); }
	"\\t"				{ token.append(yytext()); string.append("\t"); }
	{LineTerminator}	{ /* just skip */ }			// 改行は無視する。仮仕様なので必要ならば変更してよい
	<<EOF>>             { throw new Error("EOF in string"); }
	.					{ token.append(yytext()); string.append( yytext() ); }
}												
												
<COMMENT> {
	[^*\n]*             {}
	"\n"                {}
	"*"          { yybegin(COMMENT_INNER); }
	<<EOF>>      { throw new Error("EOF in comment"); }
}

<COMMENT_INNER> {
	"*"                 {}
	[^*/\n]      { yybegin(COMMENT); }
	"\n"         { yybegin(COMMENT); }
	"/"          { yybegin(YYINITIAL); }
	<<EOF>>      { throw new Error("EOF in comment"); }
}

/* No token was found for the input so through an error.  Print out an
   Illegal character message with the illegal character that was found. */
[^]                    { throw new Error("Illegal character <"+yytext()+"> at line "+(yyline+1)); }
