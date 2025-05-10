package compile.parser;

public class QuantifiedProcess {
    public Quantifier quantifier;
    public Object process;

    public QuantifiedProcess(Quantifier q, Object p) {
        this.quantifier = q;
        this.process = p;
    }
}
