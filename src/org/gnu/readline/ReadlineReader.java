package org.gnu.readline;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * A <code>Reader</code> wrapper for the Readline classes.  This seems
 * to work fine in conjunction with such classes as BufferedReader,
 * but it hasn't been tested well enough to see if this will work well
 * in all cases.
 *
 * This was implemented to make it easier to supplant Readline's
 * functionality [shrug] anywhere and everywhere, but specifically in
 * <a href="http://www.beanshell.org">BeanShell</a>.
 *
 * @version $Revision: 1.1 $
 * @author Shane Celis <shane@terrapsring.com>
 **/

public class ReadlineReader extends Reader {

  public static final String DEFAULT_PROMPT = "";
  private StringBuffer iBuff;	
  private String       iLineSeparator;
  private String iPrompt;
  private File iHistoryFile;
  
  /**
   * Constructs a ReadlineReader object with the given prompt.
   **/
  
  public ReadlineReader(String prompt,ReadlineLibrary lib) { 
    iBuff = new StringBuffer();
    setPrompt(prompt);
    Readline.load(lib);
    Readline.initReadline("ReadlineReader");
    iLineSeparator = System.getProperty("line.separator", "\n");
  }

  /**
   * Constructs a ReadlineReader object with the default prompt.
   **/

  public ReadlineReader(ReadlineLibrary lib) {
    this(DEFAULT_PROMPT,lib);
  }

  /**
   * Constructs a ReadlineReader object with an associated history
   * file.
   **/

  public ReadlineReader(File history,ReadlineLibrary lib) throws IOException {
    this(DEFAULT_PROMPT,lib);
    Readline.readHistoryFile(history.getAbsolutePath());
    iHistoryFile = history; // only set this if we can read the file
  }

  /**
   * Constructs a ReadlineReader object with an associated history
   * file and prompt.
   **/

  public ReadlineReader(String prompt, File history,ReadlineLibrary lib)
                                                         throws IOException {
    this(history,lib);
    setPrompt(prompt);
  }

  /**
   * Returns the current prompt.
   **/

  public String getPrompt() {
    return iPrompt;
  }
    
  /**
   * Sets the prompt to the given value.
   **/

  public void setPrompt(String prompt) {
    iPrompt = prompt;
  }

  /**
   * Reads what's given from <code>readline()</code> into a buffer.
   * When that buffer is emptied, <code>readline()</code> is called
   * again to replenish that buffer.  This seems to work fine in
   * conjunction with such classes as BufferedReader, but it hasn't
   * been tested well enough to see if this will work well in all
   * cases.
   **/

  public int read(char[] cbuf, int off, int len) 
    throws IOException {
    try {
      if (iBuff.length() == 0) {
	String line = Readline.readline(iPrompt);
	iBuff.append((line == null ? "" : line) + iLineSeparator);
      }
      if (len > iBuff.length())
	len = iBuff.length();
      if (len == 0) 
	return 0;
      char[] sbuf = iBuff.substring(0, len).toCharArray();
      System.arraycopy(sbuf, 0, cbuf, off, len);
      iBuff.delete(0, len);
      return len;
    } catch (EOFException eof) {
      throw eof;
    } catch (UnsupportedEncodingException uee) {
      throw uee;
    }
  }

  /**
   * Nullifies all buffers and writes history file if one was given
   * at construction time.
   **/

  public void close()
    throws IOException {
    iBuff = null;
    iPrompt = null;
    if (iHistoryFile != null) {
      Readline.writeHistoryFile(iHistoryFile.getAbsolutePath());
      iHistoryFile = null;
    }
  }

  public static void main(String[] args) throws Exception {
    java.io.BufferedReader rd = 
      new java.io.BufferedReader(new 
	  ReadlineReader("hmm ", new File("test"),ReadlineLibrary.GnuReadline));
    String line;
    try {
      while ((line = rd.readLine()) != null) {
	System.out.println("got: " + line);
      }
    } finally {
      rd.close();
    }
  }
}

