package util;

import junit.framework.TestCase;

public class StackTest extends TestCase {

	public StackTest(String arg0) {
		super(arg0);
	}
	public static void main(String[] args) {
		junit.textui.TestRunner.run(StackTest.class);
	}
	public void testStack() {
		Stack s = new Stack();
		QueuedEntity[] data = {
			new QueuedEntity(), new QueuedEntity(), new QueuedEntity(), new QueuedEntity(),
		};
		for (int i = 1; i < 4; i++) {
			s.push(data[i]);
		}
		s.pushToBottom(data[0]);
		assertEquals(s.pop(), data[3]);
		assertEquals(s.peek(), data[2]);
		assertEquals(s.pop(), data[2]);
		s.pushToBottom(data[3]);
		assertEquals(s.pop(), data[1]);
		assertEquals(s.pop(), data[0]);
		assertEquals(s.pop(), data[3]);
		s.pushToBottom(data[0]);
		s.push(data[1]);
		assertEquals(s.pop(), data[1]);
		assertEquals(s.pop(), data[0]);
	}
}
