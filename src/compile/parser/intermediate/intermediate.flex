/* --------------------------Usercode Section------------------------ */
package compile.parser.intermediate;
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
	StringBuffer string = new StringBuffer();

	static boolean debug = false;
	public static void main(String[] args) throws Exception {
		Lexer lexer = new Lexer(System.in);
		lexer.debug = true;
		while (lexer.next_token().sym != sym.EOF);
	}
	
    private Symbol symbol(int type) {
    	if (debug) System.out.println(type);
        return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value) {
    	if (debug) System.out.println(type + ":" + value);
        return new Symbol(type, yyline, yycolumn, value);
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
	"\""				{string.setLength(0); yybegin(DQUOTE); }
	"'"					{string.setLength(0); yybegin(SQUOTE); }
	[a-z]+				{return symbol(sym.INST_NAME, yytext()); }
	L[0-9]+				{return symbol(sym.LABEL, Integer.valueOf(yytext().substring(1))); }
	[0-9]+				{return symbol(sym.NUMBER, Integer.valueOf(yytext())); }
	[0-9]+\.[0-9]+		{return symbol(sym.FLOAT, Double.valueOf(yytext())); }
	{LineTerminator}	{}
}
<BEGIN_RULESET> {
	@[0-9]+				{yybegin(SKIP_LINE); return symbol(sym.RULESET_ID, Integer.valueOf(yytext().substring(1))); }
	{WhiteSpace}		{}
}
<SKIP_LINE> {
	{LineTerminator}	{yybegin(YYINITIAL); }
	.					{}
}
<DQUOTE> {
	"\""				{yybegin(YYINITIAL); return symbol(sym.DQUOTED_STRING, string.toString()); }
	"\\\\"				{string.append("\\"); }
	"\\\""				{string.append("\""); }
	.					{ string.append( yytext() ); }
}
<SQUOTE> {
	"'"					{yybegin(YYINITIAL); return symbol(sym.DQUOTED_STRING, string.toString()); }
	"\\\\"				{string.append("\\"); }
	"\\\'"				{string.append("'"); }
	.					{ string.append( yytext() ); }
}
