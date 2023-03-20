package compile.parser;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import util.Util;

/**
 * ソースファイル中の名前トークン（アトムの名前の記述に使用される文字列）を表すクラス
 * <p>このクラスは現在、プロセス文脈名およびルール文脈名では使用されない。
 * <p>
 * '-1'(X)と書きたいことがあるため、処理系の方針として、
 * 12と'12'は区別せず、1引数ならばどちらも整数とみなすように仮決定した。
 * <p>
 * 12 という名前の1引数のシンボルは [[12]] と記述する。
 * 12]]33 という名前のシンボルは '12]]33' と記述する。
 * <p>
 * runtime.Functorから参照するためにpublicクラスに変更してみた。
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE)
public class SrcName {

  /** 名前トークンが表す文字列 */
  protected String name;

  /** 名前トークンの種類 */
  protected int type;

  // typeの値
  public static final int PLAIN = 0; // aaa 12 -12 3.14 -3.14e-1 ＜クオータなしの名前トークン＞
  public static final int SYMBOL = 1; // 'aaa' 'AAA' '12' '-12' '3.14' '-3.14e-1'
  public static final int STRING = 2; // "aaa" "AAA" "12" "-12" "3.14" "-3.14e-1"
  public static final int QUOTED = 3; // [:aaa:] [:AAA:] [:12:] [:-12:] [:3.14:] [:-3.14e-1:]
  public static final int PATHED = 4; // module.p module:p

  public SrcName() {}

  /**
   * 標準の名前トークンの表現を生成する。
   * @param name 名前
   */
  public SrcName(String name) {
    this.name = name;
    this.type = PLAIN;
  }

  /** 指定された種類の名前トークンの表現を生成する。*/
  public SrcName(String name, int type) {
    this.name = name;
    this.type = type;
  }

  /** この名前トークンが表す文字列を取得する */
  public String getName() {
    return name;
  }

  /** トークンの種類を返す */
  public int getType() {
    return type;
  }

  /** ソースコード中の表現を取得する。*/
  public String getSourceName() {
    switch (type) {
      case SYMBOL:
        return Util.quoteString(name, '\'');
      case STRING:
        return Util.quoteString(name, '"');
      case QUOTED:
        return "[:" + name + ":]";
      default:
        // 中置記法のアトムも通常の記法になってしまうので、特殊名はクォートしておく。
        String path, n;
        int pos = name.indexOf('.');
        if (pos > 0) {
          path = name.substring(0, pos);
          n = name.substring(pos + 1);
        } else {
          path = null;
          n = name;
        }
        if (!n.matches("^([a-z0-9][A-Za-z0-9_]*)$")) n = Util.quoteString(name, '\'');
        return (path == null ? "" : path + ".") + n;
    }
  }

  public SrcName clone() {
    return new SrcName(name, type);
  }
}
