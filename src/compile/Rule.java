package compile;

import runtime.HeadInstruction;
import runtime.BodyInstruction;

public final class Rule {
	public HeadInstruction[] memMatch;
	public HeadInstruction[][] atomMatches; //?
	public BodyInstruction[] body;
}
