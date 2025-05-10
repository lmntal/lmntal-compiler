package compile.parser;

public class SrcQuantifiedProcess {
    protected SrcQuantifier quantifier;
    protected Object process;

    public SrcQuantifiedProcess(SrcQuantifier q, Object p) {
        this.quantifier = q;
        this.process = p;
    }

    public SrcQuantifier getQuantifier() {
        return quantifier;
    }

    public Object getProcess() {
        return process;
    }
}
