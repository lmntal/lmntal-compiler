/**************************************************************************
/* ReadlineTest.java -- Test program for the Java wrapper of GNU readline
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

import java.io.*;
import org.gnu.readline.*;

/**
 * ReadlineTest.java
 * 
 * This class shows the usage of the readline wrapper. It will read lines 
 * from standard input using the GNU-Readline library. You can use the
 * standard line editing keys. You can also define application specific 
 * keys. Put this into your ~/.inputrc (or into whatever file $INPUTRC
 * points to) and see what happens if you press function keys F1 to F3:
 * <pre>
 *$if ReadlineTest
 *"\e[11~":	"linux is great"
 *"\e[12~":	"jikes is cool"
 *"\e[13~":	"javac is slow"
 *$endif
 *</pre>
 *
 * If one argument is given to ReadlineTest, a private initialization file
 * is read. If a second argument is given, the appropriate library is
 * loaded.
 *
 * @author $Author: LMNtal $
 * @version $Revision: 1.1 $
 */

public class ReadlineTest {
  
  public ReadlineTest() {
    
  }

  /**
     Main entry point. The first argument can be a filename with an
     application initialization file.
  */

  public static void main(String[] args) {
    String line;
    
    // Readline.setThrowExceptionOnUnsupportedMethod(true);
    
    if (args.length > 1)
      Readline.load(ReadlineLibrary.byName(args[1]));
    else
      Readline.load(ReadlineLibrary.GnuReadline);
		    
    System.out.println("initializing Readline...");
    Readline.initReadline("ReadLineTest"); // init, set app name, read inputrc
    System.out.println("... done");

    try {
      if (args.length > 0)
	Readline.readInitFile(args[0]);    // read private inputrc
    } catch (IOException e) {              // this deletes any initialization
      System.out.println(e.toString());    // from /etc/inputrc and ~/.inputrc
	System.exit(0);
    }

    // read history file, if available

    File history = new File(".rltest_history");
    try {
      if (history.exists())
	Readline.readHistoryFile(history.getName());
    } catch (Exception e) {
      System.err.println("Error reading history file!");
    }
      
    // define some additional function keys

    Readline.parseAndBind("\"\\e[18~\":	\"Function key F7\"");
    Readline.parseAndBind("\"\\e[19~\":	\"Function key F8\"");

    // Set word break characters
    try {
        Readline.setWordBreakCharacters(" \t;");
    }
    catch (UnsupportedEncodingException enc) {
        System.err.println("Could not set word break characters");
        System.exit(0);
    }

    // set test completer

    Readline.setCompleter(new TestCompleter());

    // main input loop

    while (true) {
      try {
	line = Readline.readline("linux> ");
	if (line == null)
	  System.out.println("no input");
	else
	  System.out.println("line = >" + line + "<");
      } catch (UnsupportedEncodingException enc) {
	System.err.println("caught UnsupportedEncodingException");
	break;
      } catch (IOException eof) {
	break;
      }
    }
    try {
      Readline.writeHistoryFile(history.getName());
    } catch (Exception e) {
      System.err.println("Error writing history file!");
    }
    System.out.println();
    Readline.cleanup();
    System.exit(0);
  }

}
