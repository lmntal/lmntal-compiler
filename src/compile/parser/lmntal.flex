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
	private final boolean _DEBUG = false;

    /* To create a new java_cup.runtime.Symbol with information about
       the current token, the token will have no value in this
       case. */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    /* Also creates a new java_cup.runtime.Symbol with information
       about the current token, but this object has a value. */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

%line
%column
%cup

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

NameChars = [A-Za-z_0-9]
LinkName = [A-Z][A-Za-z_0-9]*
AtomName = [a-z0-9][A-Za-z_0-9]*

Comment = {TraditionalComment} | {EndOfLineComment}

TraditionalComment = "/*" [^*] ~"*/"
EndOfLineComment = ["//""%"] {InputCharacter}* {LineTerminator}?

%% 

/* ------------------------Lexical Rules Section---------------------- */
<YYINITIAL> {
	"nil"			{ return symbol(sym.NULL); }
	","				{ return symbol(sym.COMMA); }
	"("				{ return symbol(sym.LPAREN); }
	")"				{ return symbol(sym.RPAREN); }
	"{"				{ return symbol(sym.LBREATH); }
	"}"				{ return symbol(sym.RBREATH); }
	":-"			{ return symbol(sym.RULE); }
	"="				{ return symbol(sym.UNIFY); }
	"."				{ return symbol(sym.PERIOD); }
	"|"				{ return symbol(sym.GUARD); }
	"<"             { return symbol(sym.LT); }
	">"             { return symbol(sym.GT); }
	"=="            { return symbol(sym.EQ); }
	"!="            { return symbol(sym.NE); }
	"<="            { return symbol(sym.LE); }
	">="            { return symbol(sym.GE); }
	"$"				{ return symbol(sym.PROCVAR); }
	"@"				{ return symbol(sym.RULEVAR); }
	"*"				{ return symbol(sym.WILDCARD); }
	"["				{ return symbol(sym.LBRACET); }
	"]"				{ return symbol(sym.RBRACET); }
	"\+"			{ return symbol(sym.NEGATIVE); }
	{LinkName}		{ return symbol(sym.LINK_NAME, yytext()); }
	{AtomName}		{ return symbol(sym.ATOM_NAME, yytext()); }
	{WhiteSpace}	{ /* just skip */ }
	{Comment}		{ /* just skip */ }
}

/* No token was found for the input so through an error.  Print out an
   Illegal character message with the illegal character that was found. */
[^]                    { throw new Error("Illegal character <"+yytext()+"> at line:"+yyline); }
