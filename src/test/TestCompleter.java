/**************************************************************************
/* TestCompleter.java -- Sample custom completer
/*
/* Java Wrapper Copyright (c) 1998-2001 by Bernhard Bablok (mail@bablokb.de)
/*
/* This sample program is placed into the public domain and can be
/* used or modified without any restriction.
/*
/* This program is distributed in the hope that it will be useful, but
/* WITHOUT ANY WARRANTY; without even the implied warranty of
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
/**************************************************************************/


package test;
import org.gnu.readline.*;

/**
 * This class is a sample custom completer. If you press the TAB-key at
 * the readline prompt, you will see the two possible completions ("Linux"
 * and "Tux"). Once you have entered a "L" or a "T", you will only see
 * the respective single possible completion. In any other case, null is
 * returned to signal that no (more) completions are available.
 *
 * @author $Author: LMNtal $
 * @version $Revision: 1.1 $
 */


public class TestCompleter implements ReadlineCompleter {


  /**
     Default constructor.
  */

  public TestCompleter () {
  }
  

  /**
     Return possible completion. Implements org.gnu.readline.ReadlineCompleter.
  */

  public String completer (String t, int s) {
    if (s == 0) {
      if (t.equals("") || t.equals("L"))
	return "Linux";
      if (t.equals("T"))
	return "Tux";
    } else if (s == 1 && t.equals(""))
	return "Tux";
    return null;
  }
}
