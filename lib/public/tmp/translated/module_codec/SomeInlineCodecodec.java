package translated.module_codec;
import runtime.*;
import java.util.*;
/*inline_define*/
import java.io.*;
import compile.*;
import compile.parser.LMNParser;
import compile.parser.ParseException;
import compile.parser.intermediate.RulesetParser;

public class SomeInlineCodecodec {
	public static void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		case 4: {
			/*inline*/
    try {
      String filename = me.nth(0);
      FileInputStream fis = new FileInputStream(filename);
      InputStreamReader isr = new InputStreamReader(fis);
      BufferedReader br = new BufferedReader(isr);
      LMNParser lp;
      compile.structure.Membrane m;
      Ruleset rs;
      lp = new LMNParser(br);
      m = lp.parse();
      rs = RulesetCompiler.compileMembrane(m);
      rs.react((Membrane)mem);
      br.close();
    } catch(IOException e) {
      e.printStackTrace();
    } catch(ParseException e) {
      e.printStackTrace();
    }
    me.nthAtom(0).remove();
    me.remove();
  
			break; }
		case 0: {
			/*inline*/
    me.remove();
    StringFunctor sFunc =
      new StringFunctor(((Membrane)mem).encode());
    Atom sAtom = mem.newAtom(sFunc);
    mem.relinkAtomArgs(sAtom,0,me,0);
  
			break; }
		case 2: {
			/*inline*/
    me.remove();
    StringFunctor sFunc =
      new StringFunctor(((Membrane)mem).encodeProcess());
    Atom sAtom = mem.newAtom(sFunc);
    mem.relinkAtomArgs(sAtom,0,me,0);
  
			break; }
		case 1: {
			/*inline*/
    me.remove();
    StringFunctor sFunc =
      new StringFunctor(((Membrane)mem).encodeRulesets());
    Atom sAtom = mem.newAtom(sFunc);
    mem.relinkAtomArgs(sAtom,0,me,0);
  
			break; }
		case 3: {
			/*inline*/
    String str = me.nth(0);
    LMNParser lp;
    compile.structure.Membrane m;
    Ruleset rs;
    try {
      lp = new LMNParser(new StringReader(str));
      m = lp.parse();
      rs = RulesetCompiler.compileMembrane(m);
      rs.react((Membrane)mem);
    } catch(ParseException e) {
      e.printStackTrace();
    }
    me.nthAtom(0).remove();
    me.remove();
  
			break; }
		}
	}
}
