package compile.parser;

public class Quantifier {
    public static final int INF = Integer.MAX_VALUE;
    public static final int NOT = -1;
    public static final int ONE_OR_MORE = -2;
    public static final int ALL = -3;

    public int min, max, kind;

    public Quantifier(int min, int max) {
        this.min = min;
        this.max = max;
        this.kind = 0;
    }

    public Quantifier(int kind) {
        this.kind = kind;
    }

    public static Quantifier parse(String token) {
        // <^>, <*>, <+>, <?>, <n1>, <n1,>, <n1,n2>
        if (token.equals("<^>"))
            return new Quantifier(NOT);
        if (token.equals("<*>"))
            return new Quantifier(ALL);
        if (token.equals("<+>"))
            return new Quantifier(ONE_OR_MORE);
        if (token.equals("<?>"))
            return new Quantifier(0, INF);
        if (token.matches("<[0-9]+>")) {
            int n = Integer.parseInt(token.substring(1, token.length() - 1));
            return new Quantifier(n, n);
        }
        if (token.matches("<[0-9]+,>")) {
            int n = Integer.parseInt(token.substring(1, token.length() - 2));
            return new Quantifier(n, INF);
        }
        if (token.matches("<[0-9]+,[0-9]+>")) {
            String[] parts = token.substring(1, token.length() - 1).split(",");
            int n1 = Integer.parseInt(parts[0]);
            int n2 = Integer.parseInt(parts[1]);
            return new Quantifier(n1, n2);
        }
        throw new IllegalArgumentException("Unknown quantifier: " + token);
    }
}
