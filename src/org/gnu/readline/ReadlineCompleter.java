// File:          ReadlineCompleter.java
// Created:       2001-02-22 15:50:22, erik
// By:            <erik@skiinfo.fr>
// Time-stamp:    <2001-02-23 11:19:11, erik>
// 
// $Id: ReadlineCompleter.java,v 1.1 2003/10/22 16:50:50 LMNtal Exp $
// 
// Description:       


package org.gnu.readline;

/**
 * Callback interface that implements completion. You've to implement this
 * interface in order to provide completion in your application. The default
 * completion mode of the Readline library would otherwise be simple
 * filename completion.
 */
public interface ReadlineCompleter {

  /**
   * A generator function for filename completion in the general case.
   * Note that completion in Bash is a little different because of all
   * the pathnames that must be followed when looking up completions
   * for a command.  The Bash source is a useful reference for writing
   * custom completion functions.
   *
   * <p>The completer method is called with the current text to be
   * expanded (that is: the string of characters after the last
   * {@link Readline#getWordBreakCharacters() word break character}) and
   * an integer. The integer is zero to indicate, that the user just requested
   * completion (usually by pressing the TAB-key). The completeter method now
   * can return one possible choice or null to indicate that there is no
   * choice. If the completer returned a non-null value it is called back by
   * the readline library with increasing <code>state</code> variable until
   * it returns null.
   *
   * <p>Depending on the state and the return value, the readline library
   * reacts differently.
   * <ul>
   *  <li>if the completer returns <code>null</code> for state=0, then
   *      the readline library will beep to indicate that there is no
   *      known completion.</li>
   *  <li>if the completer returns a value for state=0 and
   *      <code>null</code> for state=1, then there was exactly <em>one</em> 
   *      possible completion, that is immediately expanded on the command line.</li>
   *  <li>if the completer returns choices for states &gt;= 1, then these
   *      choices are displayed to the user to choose from.</li>
   * </ul>
   *
   * <p><b>Example</b><br>
   * Consider you have a sorted set (like a TreeSet) of commands:
   * <hr><pre>
   *  SortedSet  commandSet; // SortedSet&lt;String&gt;
   * ...
   *  commandSet = new TreeSet();
   *  commandSet.add("copy");
   *  commandSet.add("copyme");
   *  commandSet.add("load");
   *  commandSet.add("list");
   * ...
   * </pre><hr>
   * now, you could write a completion method that provides completion for these
   * commands:
   * <hr><pre>
   *  private Iterator possibleValues;  // iterator for subsequent calls.
   *
   *  public String completer(String text, int state) {
   *      if (state == 0) {
   *           // first call to completer(): initialize our choices-iterator
   *           possibleValues = commandSet.tailSet(text).iterator();
   *      }
   *      if (possibleValues.hasNext()) {
   *           String nextKey = (String) possibleValues.next();
   *           if (nextKey.startsWith(text))
   *               return nextKey;
   *      }
   *      return null; // we reached the last choice.
   *  }
   * </pre><hr>
   *
   * @param text start of completion text. This is the text since the last
   *             word break character.
   * @param  state 0 or positive int. This state is zero on the first call
   *               for a completion request and increments for each subsequent
   *               call until the end of choices is reached.
   *
   * @return String with a completion choice or <code>null</code>, if there
   *         are no more choices.
   *
   * @see    Readline#getWordBreakCharacters()
   * @see    test.TestCompleter
   */
  public String completer(String text, int state);
}

/*
 * Local variables:
 * c-basic-offset: 2
 * End:
 */
