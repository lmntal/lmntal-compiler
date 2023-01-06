# compiler/parser

[山田くんの日誌から勝手に拝借してきた，この世で一番わかりやすい lmntal-compiler の parser の説明](https://www.ueda.info.waseda.ac.jp/lmntal/local/index.php?k-yamada/%E9%9B%86%E4%B8%AD%E4%BD%9C%E6%A5%AD2022%E6%98%A5)

## src/compile/parser/LMNParser.java

LMNtal の意味解析を行うプログラム

### LMNParser(コンストラクタ)

LMNParser インスタンスを作る -初期化には、使用する字句解析器インスタンスかストリームが必要

- パッケージ外からの呼出しはストリームによるもののみ使用可能 -ストリームによる呼び出しがされた場合は、[[Lexer.java>#b1cb735e]]で定義された字句解析器を使用

### parse

メインメソッド。ソースファイルから、その全体が表すプロセス構造を生成するルールが入った(compile.structure.)Membrane 型を返す

- SrcRule 型インスタンスを[[parseSrc>#jcfd9eac]]メソッドによって生成
- setRuleText 呼び出し -[[SyntaxExpander>#mb25565c]]内で定義されたメソッド群を実行
  - incorporateSignSymbols, expandAtoms, correctWorld
  - すべての引数に、先の SrcRule 型インスタンスの body フィールドを使用
- addObjectToMem を実行し、膜にアトム、子膜、ルールなどの構文オブジェクトを追加する。
- 膜を return して終了

### parseSrc

ソースコードの構文解析結果をルール形式の解析木として返す。 -指定した字句解析器を渡して[[parser>#l47e8929]]インスタンスを作成する

- parser インスタンスによる解析結果を return して終了

### `add_**ToMem`

構文オブジェクトを入力された膜構造オブジェクトに追加するメソッド群

- addProcessToMem
  リスト内の構文オブジェクトの追加。入力された LinkedList 要素への addObjectToMem メソッドの連続適用をする。
- addObjectToMem
  アトム、子膜、ルールなどの構文オブジェクトの追加。以下五つの add### ToMem メソッドのうち、入力された Object に対応するメソッドの適用を行う。
- addSrcMemToMem
  膜構文の追加
- addSrcAtomToMem
  アトム構文の追加
- addSrcProcessContextToMem
  プロセス文脈構文の追加
- addSrcRuleContextToMem
  ルール文脈構文の追加
- addSrcRuleToMem
  ルール構文の追加

### SyntaxExpander(クラス)

略記法の展開など、構文の調整を行うメソッドをまとめたクラス -拡張記法まわりについてはこのクラスを見るとよさそう？

## Lexer.java

JFlex によって自動生成された字句解析器

- lmntal.flex を参照して、字句解析器を生成している模様

## Parser.java

CUP によって自動生成された構文解析器

- lmntal.cup を参照してい生成されている模様
