package compile;

import runtime.BodyInstruction;
import runtime.HeadInstruction;

public final class Rule {
	public HeadInstruction[] memMatch;
	public HeadInstruction[][] atomMatches; //?
	public BodyInstruction[] body;
}
