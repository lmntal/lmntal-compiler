/**************************************************************************
/* Readline.java -- Java wrapper of GNU readline
/*
/* Java Wrapper Copyright (c) 1998-2001 by Bernhard Bablok (mail@bablokb.de)
/*
/* This program is free software; you can redistribute it and/or modify
/* it under the terms of the GNU Library General Public License as published
/* by  the Free Software Foundation; either version 2 of the License or
/* (at your option) any later version.
/*
/* This program is distributed in the hope that it will be useful, but
/* WITHOUT ANY WARRANTY; without even the implied warranty of
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
/* GNU Library General Public License for more details.
/*
/* You should have received a copy of the GNU Library General Public License
/* along with this program; see the file COPYING.LIB.  If not, write to
/* the Free Software Foundation Inc., 59 Temple Place - Suite 330,
/* Boston, MA  02111-1307 USA
/**************************************************************************/

package org.gnu.readline;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

/**
 This class implements basic functionality of the GNU-readline interface. It
 uses native method calls if available, otherwise it defaults to normal
 I/O using a BufferedReader. 

 <p>A typical implementation could look like:
<pre>
 try {
     Readline.load(ReadlineLibrary.GnuReadline);
 }
 catch (UnsatisfiedLinkError ignore_me) {
     System.err.println("couldn't load readline lib. Using simple stdin.");
 }

 Readline.initReadline("myapp");

 Runtime.getRuntime()                       // if your version supports
   .addShutdownHook(new Thread() {          // addShutdownHook (since 1.3)
      public void run() {
        Readline.cleanup();
      }
    });

 while (true) {
     try {
         line = Readline.readline("myprompt> ");
         if (line == null)
             System.out.println("no input");
         else
             processLine();
     } 
     catch (EOFException e) {
         break;
     } 
     catch (Exception e) {
         doSomething();
     }
 }
 Readline.cleanup();  // see note above about addShutdownHook

</pre>

 @version $Revision: 1.1 $
 @author  $Author: LMNtal $
*/

public class Readline {
  
  /**
     The currently defined ReadlineCompleter.
  */

  private static ReadlineCompleter iCompleter = null;


  /**
     The currently implementing backing library.
  */

  private static ReadlineLibrary iLib = ReadlineLibrary.PureJava;

  /**
     The BufferedReader for the fallback solution.
  */

  private static BufferedReader iReader = null;

  /**
      The current encoding of the BufferedReader. The default is the
      value of the property <em>readline.encoding</em>. If this
      property is unset, the default is null, so the BufferedReader
      will use the default encoding.
   */

  private static String iEncoding =
    System.getProperty("readline.encoding", null);

   /**
     Configuration flag: throw an UnsupportedOperationException, if true.
     This value defaults to false.
  */

  private static boolean iThrowException = false;

  /////////////////////////////////////////////////////////////////////////////

  /**
     Load an implementing backing library. This method might throw an
     UnsatisfiedLinkError in case the native libary is not found in the
     library path. If you want to have portable program, just catch and 
     ignore that error. JavaReadline will then just use the pure Java fallback
     solution.

     @param lib An object (constant) of type ReadlineLibrary
     @throws UnsatisfiedLinkError if the shared library could not be
             found. Add it to your LD_LIBRARY_PATH.
     @see org.gnu.readline.ReadlineLibrary
  */

  public static final void load(ReadlineLibrary lib) throws UnsatisfiedLinkError {
    if (lib == iLib)     // ok, since objects are immutable
      return;
    if (lib == ReadlineLibrary.PureJava) {
      iLib = lib;
      return;
    }
    System.loadLibrary(lib.getName()); // might throw UnsatisfiedLinkError
    iLib = lib;
  }

  /////////////////////////////////////////////////////////////////////////////

  /**
     Initialize the GNU-Readline library. This will also set the
     application name, which can be used in the initialization files of
     Readline (usually /etc/inputrc and ~/.inputrc) to define application
     specific keys. See the file ReadlineTest.java int the test subdir of
     the distribution for an example.

     <p>Supporting implementations:
        <ul>
	  <li>GNU-Readline</li>
	  <li>Editline</li>
	  <li>Getline</li>
        </ul>
     </p>

     @param applicationName Name of application in initialization file
  */

  public static void initReadline(String applicationName) {
    if (iLib == ReadlineLibrary.GnuReadline || 
	iLib == ReadlineLibrary.Editline ||
	iLib == ReadlineLibrary.Getline)
      initReadlineImpl(applicationName);
  }

  /////////////////////////////////////////////////////////////////////////////

  /**
     Display a prompt on standard output and read a string from standard
     input. This method returns 'null', if there has been an empty input
     (user pressed just [RETURN]) and throws an EOFException on end-of-file
     (C-d). This versino of <tt>readline()</tt> automatically adds the line
     to the in-memory history; use the other version of <tt>readline()</tt>
     if you want explicit control over that feature.

     <p>Supporting implementations:
        <ul>
	  <li>GNU-Readline</li>
	  <li>Editline</li>
	  <li>Getline</li>
	  <li>PureJava</li>
        </ul>
     </p>

     @param prompt Prompt to display
     @return The string the user entered or 'null' if there was no input.
     @throws EOFException on end-of-file, i.e. CTRL-d input.
     @see #readline(String,boolean)
     @see #addHistory()
  */
  public static String readline(String prompt) throws EOFException,
                                    IOException, UnsupportedEncodingException {
      return readline(prompt,true);
  }

  /**
     Display a prompt on standard output and read a string from
     standard input. This method returns 'null', if there has
     been an empty input (user pressed just [RETURN]) and throws
     an EOFException on end-of-file (C-d).

     @param prompt    Prompt to display
     @param addToHist <tt>true</tt> to add the line to the history
                      automatically; <tt>false</tt> to refrain from
                      adding the line to the history. (You can manually
                      add the line to the history by calling
                      <tt>addHistory()</tt>.)
     @return The string the user entered or 'null' if there was no input.
     @throws EOFException on end-of-file, i.e. CTRL-d input.
     @see #readline(String)
     @see #addHistory()
  */

  public static String readline(String prompt, boolean addToHist)
               throws EOFException, IOException, UnsupportedEncodingException {
    if (iLib != ReadlineLibrary.PureJava) {
      String line = readlineImpl(prompt);
      if ((line != null) && (addToHist))
          addToHistory(line);
      return line;
    } else {
      System.out.print(prompt);
      if (iReader == null) {
	if (iEncoding == null)
	  iReader = new BufferedReader(new InputStreamReader(System.in));
	else
	  iReader = new BufferedReader(
			         new InputStreamReader(System.in, iEncoding));
      }
      String line = iReader.readLine();
      if (line == null)
        throw new EOFException("EOF");
      if (line.length() == 0)
          line = null;
      return line;
    }
  }

  /////////////////////////////////////////////////////////////////////////////

  /**
     Add a line to the in-memory history.

     <p>Supporting implementations:
        <ul>
	  <li>GNU-Readline</li>
	  <li>Editline</li>
	  <li>Getline</li>
        </ul>
     </p>

     @param line  The line to add to the history
     @throws UnsupportOperationException if underlying library doesn't support
                                         a history
  */

  public static void addToHistory(String line) {
      if (iLib == ReadlineLibrary.GnuReadline || 
	  iLib == ReadlineLibrary.Editline ||
	  iLib == ReadlineLibrary.Getline)
        addToHistoryImpl(line);
      else if (iThrowException)
      throw new UnsupportedOperationException();
  }

  /////////////////////////////////////////////////////////////////////////////

  /**
     Get the history buffer in a supplied <tt>Collection</tt>.

     <p>Supporting implementations:
        <ul>
	  <li>GNU-Readline</li>
	  <li>Editline</li>
        </ul>
     </p>

     @param collection  where to store the history
     @throws UnsupportOperationException if underlying library doesn't support
                                         a history
  */

  public static void getHistory(Collection collection) {
      if (iLib == ReadlineLibrary.GnuReadline ||
	  iLib == ReadlineLibrary.Editline)
        getHistoryImpl(collection);
      else if (iThrowException)
        throw new UnsupportedOperationException();
  }

  /////////////////////////////////////////////////////////////////////////////

  /**
     Get the size, in elements (lines), of the history buffer.

     <p>Supporting implementations:
        <ul>
	  <li>GNU-Readline</li>
	  <li>Editline</li>
        </ul>
     </p>

     @return the number of lines in the history buffer
  */
  public static int getHistorySize() {
      int result = 0;
      if (iLib == ReadlineLibrary.GnuReadline ||
	  iLib == ReadlineLibrary.Editline)
        result = getHistorySizeImpl();
      else if (iThrowException)
        throw new UnsupportedOperationException();
      return result;
  }

  /////////////////////////////////////////////////////////////////////////////

  /**
     Clear the history buffer.

     <p>Supporting implementations:
        <ul>
	  <li>GNU-Readline</li>
	  <li>Editline</li>
        </ul>
     </p>
  */
  public static void clearHistory() {
      if (iLib == ReadlineLibrary.GnuReadline ||
	  iLib == ReadlineLibrary.Editline)
        clearHistoryImpl();
      else if (iThrowException)
        throw new UnsupportedOperationException();
  }      

  /////////////////////////////////////////////////////////////////////////////

  /**
     Get the specified entry from the history buffer. History buffer entries
     are numbered from 0 through (<tt>getHistorySize() - 1</tt>), with the
     oldest entry at index 0.

     <p>Supporting implementations:
        <ul>
	  <li>GNU-Readline</li>
	  <li>Editline</li>
        </ul>
     </p>

     @param i  the index of the entry to return
     @return the line at the specified index in the history buffer
     @throws ArrayIndexOutOfBoundsException index out of range
  */
  public static String getHistoryLine(int i) {
    String s = null;
    if (iLib == ReadlineLibrary.GnuReadline ||
	iLib == ReadlineLibrary.Editline) {
      if ((i < 0) || i >= getHistorySize())
        throw new ArrayIndexOutOfBoundsException(i);
      s = getHistoryLineImpl(i);
    }
    else if (iThrowException)
      throw new UnsupportedOperationException();
    return s;
  }

  /////////////////////////////////////////////////////////////////////////////

  /**
     Read keybindings and variable assignments from a file. This method is a
     wrapper to rl_read_init_file(char *filename). Throws IOException if 
     something goes wrong.

     <p>Supporting implementations:
        <ul>
	  <li>GNU-Readline</li>
        </ul>
     </p>

     @param filename Name of file to read bindings from
     @return void
  */

  public static void readInitFile(String filename) throws IOException {
    if (iLib == ReadlineLibrary.GnuReadline)
      readInitFileImpl(filename);
    else if (iThrowException)
      throw new UnsupportedOperationException();
  }

  /////////////////////////////////////////////////////////////////////////////

  /**
     Parse argument string as if it had been read from `inputrc' file
     and perform key bindings and variable assignments found.

     <p>Supporting implementations:
        <ul>
	  <li>GNU-Readline</li>
        </ul>
     </p>

     @param line Simulated line from inputrc file
     @return boolean False in case of error
  */

  public static boolean parseAndBind(String line) {
    if (iLib == ReadlineLibrary.GnuReadline)
      return parseAndBindImpl(line);
    else if (iThrowException)
      throw new UnsupportedOperationException();
    else
      return true;
  }

  /////////////////////////////////////////////////////////////////////////////

  /**
     Reads a history file into memory

     <p>Supporting implementations:
        <ul>
	  <li>GNU-Readline</li>
	  <li>Editline</li>
        </ul>
     </p>

     @param filename Name of history file to read
     */

  public static void readHistoryFile(String filename)
                              throws EOFException, UnsupportedEncodingException {
    if (iLib == ReadlineLibrary.GnuReadline || iLib == ReadlineLibrary.Editline)
      readHistoryFileImpl(filename);
    else if (iThrowException)
      throw new UnsupportedOperationException();
  }


  /////////////////////////////////////////////////////////////////////////////

  /**
     Writes a history file to disc

     <p>Supporting implementations:
        <ul>
	  <li>GNU-Readline</li>
	  <li>Editline</li>
        </ul>
     </p>

     @param filename Name of history file to write
  */

  public static void writeHistoryFile(String filename)
                              throws EOFException, UnsupportedEncodingException {
    if (iLib == ReadlineLibrary.GnuReadline || iLib == ReadlineLibrary.Editline)
      writeHistoryFileImpl(filename);
    else if (iThrowException)
      throw new UnsupportedOperationException();
  }


  /////////////////////////////////////////////////////////////////////////////

  /**
     Set your completer implementation. Setting this to <code>null</code>
     will result in the default behaviour of readline which is filename
     completion.

     <p>Supporting implementations:
        <ul>
	  <li>GNU-Readline</li>
	  <li>Editline</li>
        </ul>
     </p>

     @param rlc An object implementing the ReadlineCompleter interface
  */
    
  public static void setCompleter(ReadlineCompleter rlc) {
    iCompleter = rlc;
    if (iLib == ReadlineLibrary.GnuReadline ||
                                             iLib == ReadlineLibrary.Editline) {
      setCompleterImpl(iCompleter);
    } else if (iThrowException)
      throw new UnsupportedOperationException();
  }

  /////////////////////////////////////////////////////////////////////////////

  /**
     Query current completer function.

     @return Current ReadlineCompleter object
  */
    
  public static ReadlineCompleter getCompleter() {
    return iCompleter;
  }

  /////////////////////////////////////////////////////////////////////////////

  /**
     Reset the readline library and with it, the terminal.

     <p>Supporting implementations:
        <ul>
	  <li>GNU-Readline</li>
	  <li>Editline</li>
        </ul>
     </p>

  */
    
  public static void cleanup() {
    if (iLib == ReadlineLibrary.GnuReadline ||
                                             iLib == ReadlineLibrary.Editline) {
      cleanupReadlineImpl();
    }      
  }

  /////////////////////////////////////////////////////////////////////////////

  /**
     Return if we have a terminal. This requires, that any of the native
     libraries have been loaded yet
     (so call Readline.{@link #load(ReadlineLibrary)}())
     first, otherwise this will always return true.

     <p>Supporting implementations:
        <ul>
	  <li>GNU-Readline</li>
	  <li>Editline</li>
        </ul>
     </p>

   */

   public static boolean hasTerminal() {
    if (iLib == ReadlineLibrary.GnuReadline ||
                                             iLib == ReadlineLibrary.Editline) {
       return hasTerminalImpl();
     }
     return true;
   }

  /////////////////////////////////////////////////////////////////////////////

  /**
     Query word break characters.

     <p>Supporting implementations:
        <ul>
	  <li>GNU-Readline</li>
	  <li>Editline</li>
        </ul>
     </p>

  */
    
  public static String getWordBreakCharacters() {
    if (iLib == ReadlineLibrary.GnuReadline || iLib == ReadlineLibrary.Editline)
      return getWordBreakCharactersImpl();
    else if (iThrowException)
      throw new UnsupportedOperationException();
    else
      return null;
  }

  /////////////////////////////////////////////////////////////////////////////

  /**
     Query the current line buffer. This returns the current content of
     the internal line buffer. You might need this in a 
     {@link ReadlineCompleter} implementation to access the full text
     given so far.

     <p>Supporting implementations:
        <ul>
	  <li>GNU-Readline</li>
	  <li>Editline</li>
        </ul>
     </p>
  */
    
  public static String getLineBuffer() {
    if (iLib == ReadlineLibrary.GnuReadline || iLib == ReadlineLibrary.Editline)
      return getLineBufferImpl();
    else if (iThrowException)
      throw new UnsupportedOperationException();
    else
      return null;
  }


  /////////////////////////////////////////////////////////////////////////////

  /**
     Set word break characters.

     <p>Supporting implementations:
        <ul>
	  <li>GNU-Readline</li>
	  <li>Editline</li>
        </ul>
     </p>

     @param wordBreakCharacters A string of word break characters
  */
    
  public static void 
    setWordBreakCharacters(String wordBreakCharacters)
                              throws UnsupportedEncodingException {
    if (iLib == ReadlineLibrary.GnuReadline || iLib == ReadlineLibrary.Editline)
      setWordBreakCharactersImpl(wordBreakCharacters);
    else if (iThrowException)
      throw new UnsupportedOperationException();
  }

  /////////////////////////////////////////////////////////////////////////////

  /**
     Configure behavior in case an unsupported method is called. If argument
     is true, unsupported methods throw an UnsupportedOperationException.

     @param flag configuration flag
  */

  public static void setThrowExceptionOnUnsupportedMethod(boolean flag) {
    iThrowException = flag;
  }

  /////////////////////////////////////////////////////////////////////////////

  /**
     Query behavior in case an unsupported method is called.

     @return configuration flag
  */

  public static boolean getThrowExceptionOnUnsupportedMethod() {
    return iThrowException;
  }

  /////////////////////////////////////////////////////////////////////////////

  /**
    Set current encoding of fallback BufferedReader.

    @param encoding encoding to use
  */
    
  public static void setEncoding(String encoding) {
      iEncoding = encoding;
  }

  /////////////////////////////////////////////////////////////////////////////
  
  /**
     Query current encoding of fallback BufferedReader.

     @return current encoding
  */

  public static String getEncoding() {
   return iEncoding;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  /**
     Native implementation of initReadline()

     @see org.gnu.readline.Readline#initReadline(String applicationName)
  */

  private native static void initReadlineImpl(String applicationName);

  /////////////////////////////////////////////////////////////////////////////

  /**
     cleanup readline; reset terminal.
  */

  private native static void cleanupReadlineImpl();

  /////////////////////////////////////////////////////////////////////////////

  /**
     native implementation of isatty();
  */

  private native static boolean hasTerminalImpl();

  /////////////////////////////////////////////////////////////////////////////

  /**
     Native implementation of readline()

     @see org.gnu.readline.Readline#readline(String prompt)
  */

  private native static String readlineImpl(String prompt)
                              throws EOFException, UnsupportedEncodingException;

  /////////////////////////////////////////////////////////////////////////////

  /**
     Native implementation of addToHistory

     @see org.gnu.readline.Readline#addToHistory(String line)
  */

  private native static void addToHistoryImpl(String line);

  /////////////////////////////////////////////////////////////////////////////

  /**
     Native implementation of getHistory

     @see org.gnu.readline.Readline#getHistory()
  */

  private native static void getHistoryImpl(Collection collection);

  /////////////////////////////////////////////////////////////////////////////

  /**
     Native implementation of getHistorySize

     @see org.gnu.readline.Readline#getHistorySize()
  */

  private native static int getHistorySizeImpl();

  /////////////////////////////////////////////////////////////////////////////

  /**
     Native implementation of getHistoryLine

     @see org.gnu.readline.Readline#getHistoryLine()
  */

  private native static String getHistoryLineImpl(int i);

  /////////////////////////////////////////////////////////////////////////////

  /**
     Native implementation of clearHistory

     @see org.gnu.readline.Readline#clearHistory()
  */

  private native static void clearHistoryImpl();

  /////////////////////////////////////////////////////////////////////////////

  /**
     Native implementation of readInitFile(String filename)

     @see org.gnu.readline.Readline#readInitFile(String filename)
  */

  private native static void readInitFileImpl(String filename)
                                                            throws IOException;


  /////////////////////////////////////////////////////////////////////////////

  /**
     Native implementation of getLineBuffer()

     @see org.gnu.readline.Readline#getLineBuffer()
  */

  private native static String getLineBufferImpl();

  /////////////////////////////////////////////////////////////////////////////

  /**
     Native implementation of parseAndBind(String line)

     @see org.gnu.readline.Readline#parseAndBind(String line)
  */

  private native static boolean parseAndBindImpl(String line);

  /////////////////////////////////////////////////////////////////////////////

  /**
     Native implementation of readHistoryFile(String filename)

     @see org.gnu.readline.Readline#readHistoryFile(String filename)
  */

  private native static void readHistoryFileImpl(String filename)
                              throws EOFException, UnsupportedEncodingException;

  /////////////////////////////////////////////////////////////////////////////

  /**
     Native implementation of writeHistoryFile(String filename)

     @see org.gnu.readline.Readline#writeHistoryFile(String filename)
  */

  private native static void writeHistoryFileImpl(String filename)
                              throws EOFException, UnsupportedEncodingException;

  /////////////////////////////////////////////////////////////////////////////

  /**
     Native implementation of setCompleter(ReadlineCompleter rlc)

     @see org.gnu.readline.Readline#setCompleter(ReadlineCompleter rlc)
  */
    
  private native static void setCompleterImpl(ReadlineCompleter rlc);
    
  /////////////////////////////////////////////////////////////////////////////

  /**
     Native implementation of getWordBreakCharacters()

     @see org.gnu.readline.Readline#getWordBreakCharacters()
  */

  private native static String getWordBreakCharactersImpl();

  /////////////////////////////////////////////////////////////////////////////

  /**
     Native implementation of setWordBreakCharacters()

     @see 
   org.gnu.readline.Readline#setWordBreakCharacters(String wordBreakCharacters)
  */

  private native static void 
    setWordBreakCharactersImpl(String wordBreakCharacters)
                              throws UnsupportedEncodingException;
}
