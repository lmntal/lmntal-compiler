package compile.parser;

import java.util.LinkedList;

class SrcList extends SrcElement {
  LinkedList<SrcElement> list;

  public SrcList() {
    this(new LinkedList<>());
  }

  public SrcList(LinkedList<SrcElement> list) {
    this.list = list;
  }
}
