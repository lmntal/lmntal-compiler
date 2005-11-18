/* --------------------------Usercode Section------------------------ */
package compile.parser.intermediate;
import compile.parser.MySymbol;
import java_cup.runtime.Symbol;

%%
/* -----------------Options and Declarations Section----------------- */
%class Lexer
%line
%column
%unicode
%cup
//%standalone

%{
	StringBuffer token = new StringBuffer(), value = new StringBuffer();
	int startpos;

	static boolean debug = false;
	public static void main(String[] args) throws Exception {
		Lexer lexer = new Lexer(System.in);
		lexer.debug = true;
		while (lexer.next_token().sym != sym.EOF);
	}
	
    private Symbol symbol(int type) {
    	return symbol(type, null, yytext(), yycolumn);
    }
    private Symbol symbol(int type, Object value) {
    	return symbol(type, value, yytext(), yycolumn);
    }
    private Symbol symbol(int type, Object value, String token) {
    	return symbol(type, value, token, yycolumn);
    }
    private Symbol symbol(int type, Object value, String token, int pos) {
    	if (debug) System.out.println("(" + (yyline+1) + "," + (pos+1) + ") " + type + ":" + value);
        return new MySymbol(token, type, yyline+1, pos+1, value);
    }
%}

LineTerminator	= \r|\n|\r\n|\f
WhiteSpace		= {LineTerminator} | [ \t]

%state BEGIN_RULESET SKIP_LINE DQUOTE SQUOTE

%%
/* ------------------------Lexical Rules Section---------------------- */

<YYINITIAL> {
	{WhiteSpace}		{}
	"Compiled Ruleset"	{yybegin(BEGIN_RULESET);	return symbol(sym.KW_COMPILED_RULESET); }
	"Compiled Rule"		{yybegin(SKIP_LINE);		return symbol(sym.KW_COMPILED_RULE); }
	"--atommatch:"		{return symbol(sym.KW_ATOM_MATCH); }
	"--memmatch:"		{return symbol(sym.KW_MEM_MATCH); }
	"--guard:"			{return symbol(sym.KW_GUARD); }
	"--body:"			{return symbol(sym.KW_BODY); }
	"$in_2"				{return symbol(sym.INSIDE_PROXY); }
	"$out_2"			{return symbol(sym.OUTSIDE_PROXY); }
	"["					{return symbol(sym.LBRACKET); }
	"]"					{return symbol(sym.RBRACKET); }
	"_"					{return symbol(sym.UNDERBAR); }
	","					{return symbol(sym.COMMA); }
	":"					{return symbol(sym.COLON); }
	@[0-9]+				{return symbol(sym.RULESET_ID, Integer.valueOf(yytext().substring(1))); }
	"\""				{token.setLength(0); value.setLength(0); startpos = yycolumn; token.append(yytext()); yybegin(DQUOTE); }
	"'"					{token.setLength(0); value.setLength(0); startpos = yycolumn; token.append(yytext()); yybegin(SQUOTE); }
	[a-zA-Z]+			{return symbol(sym.INST_NAME, yytext()); }
	L[0-9]+				{return symbol(sym.LABEL, Integer.valueOf(yytext().substring(1))); }
	[0-9]+				{return symbol(sym.NUMBER, Integer.valueOf(yytext())); }
	[0-9]+\.[0-9]+		{return symbol(sym.FLOAT, Double.valueOf(yytext())); }
	{LineTerminator}	{}
}
<BEGIN_RULESET> {
	@[0-9]+				{yybegin(SKIP_LINE); return symbol(sym.RULESET_ID, Integer.valueOf(yytext().substring(1))); }
	{WhiteSpace}		{}
	.					{return symbol(sym.error); }
}
<SKIP_LINE> {
	{LineTerminator}	{yybegin(YYINITIAL); }
	.					{}
}
<DQUOTE> {
	"\""				{token.append(yytext()); yybegin(YYINITIAL); return symbol(sym.DQUOTED_STRING, value.toString(), token.toString(), startpos); }
	"\\\""				{token.append(yytext()); value.append("\""); }
	"\\\\"				{token.append(yytext()); value.append("\\"); }
	"\\r"				{token.append(yytext()); value.append("\r"); }
	"\\n"				{token.append(yytext()); value.append("\n"); }
	"\\f"				{token.append(yytext()); value.append("\f"); }
	"\\t"				{token.append(yytext()); value.append("\t"); }
	{LineTerminator}	{throw new RuntimeException("end of line in quoted string at line " + (yyline+1)); }
	.					{token.append(yytext()); value.append( yytext() ); }
}
<SQUOTE> {
	"'"					{token.append(yytext()); yybegin(YYINITIAL); return symbol(sym.DQUOTED_STRING, value.toString(), token.toString(), startpos); }
	"\\\'"				{token.append(yytext()); value.append("'"); }
	"\\\\"				{token.append(yytext()); value.append("\\"); }
	"\\r"				{token.append(yytext()); value.append("\r"); }
	"\\n"				{token.append(yytext()); value.append("\n"); }
	"\\f"				{token.append(yytext()); value.append("\f"); }
	"\\t"				{token.append(yytext()); value.append("\t"); }
	{LineTerminator}	{throw new RuntimeException("end of line in quoted string at line " + (yyline+1)); }
	.					{token.append(yytext()); value.append( yytext() ); }
}

/* No token was found for the input so through an error.  Print out an
   Illegal character message with the illegal character that was found. */
[^]                    { throw new RuntimeException("Illegal character <"+yytext()+"> at line "+(yyline+1)); }
