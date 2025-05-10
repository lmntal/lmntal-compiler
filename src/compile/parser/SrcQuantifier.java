package compile.parser;

public class SrcQuantifier {
    public static final int INF = Integer.MAX_VALUE;

    protected int min, max;
    protected SrcQuantifierKind kind;

    public SrcQuantifier(int min, int max) {
        this.min = min;
        this.max = max;
        this.kind = SrcQuantifierKind.CARD;
    }

    public SrcQuantifier(SrcQuantifierKind kind) {
        this.kind = kind;
    }

    public static SrcQuantifier parse(String token) {
        // <^>, <*>, <+>, <?>, <n1>, <n1,>, <n1,n2>
        if (token.equals("<^>"))
            return new SrcQuantifier(SrcQuantifierKind.NOT);
        if (token.equals("<*>"))
            return new SrcQuantifier(SrcQuantifierKind.ZERO_OR_MORE);
        if (token.equals("<+>"))
            return new SrcQuantifier(SrcQuantifierKind.ONE_OR_MORE);
        if (token.equals("<?>"))
            return new SrcQuantifier(0, INF);
        if (token.matches("<[0-9]+>")) {
            int n = Integer.parseInt(token.substring(1, token.length() - 1));
            return new SrcQuantifier(n, n);
        }
        if (token.matches("<[0-9]+,>")) {
            int n = Integer.parseInt(token.substring(1, token.length() - 2));
            return new SrcQuantifier(n, INF);
        }
        if (token.matches("<[0-9]+,[0-9]+>")) {
            String[] parts = token.substring(1, token.length() - 1).split(",");
            int n1 = Integer.parseInt(parts[0]);
            int n2 = Integer.parseInt(parts[1]);
            return new SrcQuantifier(n1, n2);
        }
        throw new IllegalArgumentException("Unknown quantifier: " + token);
    }
    public SrcQuantifierKind getKind() {
        return kind;
    }
}

enum SrcQuantifierKind {
    NOT,
    ONE_OR_MORE,
    ZERO_OR_MORE,
    CARD
}
