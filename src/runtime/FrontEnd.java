/*
 * ì¬“ú: 2003/10/22
 *
 */
package runtime;

import org.gnu.readline.*;
import java.io.*;

/**
 * @author pa
 *
 */
public class FrontEnd {
	public void runREPL() {
		String line;
		try {
			Readline.load(ReadlineLibrary.Getline);
			//Readline.load(ReadlineLibrary.GnuReadline);
			//Readline.load(ReadlineLibrary.PureJava);
		}
		catch (UnsatisfiedLinkError ignore_me) {
			System.err.println("couldn't load readline lib. Using simple stdin.");
		}

		Readline.initReadline("LMNtal");

		Runtime.getRuntime()                       // if your version supports
		  .addShutdownHook(new Thread() {          // addShutdownHook (since 1.3)
			 public void run() {
			   Readline.cleanup();
			 }
			});

		while (true) {
			try {
				line = Readline.readline("LMNtal> ");
				if (line == null) {
					//System.out.println("no input");
				} else if(line.equals("q")) {
					break;
				} else {
					processLine(line);
				}
			} 
			catch (EOFException e) {
				break;
			} 
			catch (Exception e) {
				//doSomething();
			}
		}
		Readline.cleanup();  // see note above about addShutdownHook
	}
	
	/**
	 * Process a line
	 * 
	 * @param line     LMNtal statement (one liner)
	 */
	public void processLine(String line) {
		System.out.println(line+"  =>  {a, b, {c}}, ({b, $p}:-{c, $p})");
	}
	
	public static void main(String[] args) {
		FrontEnd fe = new FrontEnd();
		fe.runREPL();
	}
}
