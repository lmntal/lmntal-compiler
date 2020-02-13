package compile.parser;

import java.util.LinkedList;

class SrcProcessList extends SrcAbstract
{
  LinkedList<SrcAbstract> list;

  SrcProcessList(LinkedList<SrcAbstract> list) {
    this.list = list;
  }
}
