/*
 * ºîÀ®Æü: 2003/10/21
 *
 */
package test;

import junit.framework.TestCase;
import runtime.Instruction;
import util.Util;

/**
 * @author hara
 *
 */
public class InstructionTest extends TestCase {
	Instruction inst;
	/**
	 * Constructor for BodyInstructionTest.
	 * @param arg0
	 */
	public InstructionTest(String arg0) {
		super(arg0);
		inst = new Instruction();
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(InstructionTest.class);
	}

	public void testHoge() {
		Util.println("test "+inst.data.toString());
		assertTrue(inst.data.toString().equals("[react, [1, 2, 5]]"));
	}
}
