package type.quantity;

import util.Util;

public class VarCount {

  private static int current_id = 0;

  public IntervalCount bound;

  private final int id;

  public VarCount() {
    id = current_id++;
  }

  public String toString() {
    return "RV" + id;
  }

  public void bind(IntervalCount vc) {
    bound = vc;
  }

  public boolean isBound() {
    return bound != null;
  }

  public IntervalCount evaluate() {
    if (bound == null) Util.errPrintln("fatal error. this var isn't bind : RV" + id);
    return bound;
  }
}
