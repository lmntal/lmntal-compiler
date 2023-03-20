# compiler/parser

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

### addGuardNegatives

ガード否定条件に対する構造を生成する

### SyntaxExpander(クラス)

略記法の展開など、構文の調整を行うメソッドをまとめたクラス\
拡張記法まわりについてはこのクラスを見るとよさそう？

## ParseException.java

LMNParser.java内でスローされるエラーの定義。\
基本は覗く必要なし。

## Src**.java

LMNtalプログラムを構成する構文要素に対するソースファイル内のデータ構造定義

- SrcAtom.java\
アトムを表すデータ構造
- SrcContext.java\
リンク、リンク束、プロセス文脈、ルール文脈を表すデータ構造の親クラス。
本クラスを継承して、SrcLink, SrcLinkBundle, SrcProcessContext, SrcRuleContextクラスが定義される。
- SrcHyperLink.java\
ハイパーリンクを表すデータ構造
- SrcLink.java\
リンクを表すデータ構造。SrcContextクラスを継承
- SrcLinkBundle.java\
リンク束を表すデータ構造。SrcContextクラスを継承
- SrcMembrane.java\
膜を表すデータ構造
- SrcName.java\
アトム名トークンの構成定義。\
アトム名トークン＝アトム名文字列＋種類(クオートの有無)
- SrcProcessContext.java\
プロセス文脈を表すデータ構造。SrcContextクラスを継承
- SrcRule.java\
ルールを表すデータ構造。恐らくSrc**.javaの中で一番重要。\
ルールは「head」「Guard」「Body」に分けて、それぞれLinkedListで保持される。
  - addHyperLinkConstraint\
  head,bodyに!X:Aが出現した場合，Guardにhlink(X,A)もしくはnew(X,A)を自動で追加する。
  a(!H:1,!H:2,!H:3)と書いても!H:1に固定されてしまう問題の元凶。
  - addTypeConstraint\
  リンク名に'_I'から始まる表記を使うと、ガードに自動でint制約を加える。
  - unSimpagationize\
  simpagation ruleを自動で通常のルールの形に変換する。
  - copySrcs\
  ソースオブジェクトのリストをコピーする。
- SrcRuleContext.java
ルール文脈を表すデータ構造。SrcContextクラスを継承
- SrcDumper.java\
Src\*\*クラスの内容出力用クラス。\
SrcDumper.dump(Src\*\*クラスインスタンス)で内容を出力できる。

## Lexer.java

JFlex によって自動生成された字句解析器

- lmntal.flex を参照して、字句解析器を生成している模様

## parser.java

CUP によって自動生成された構文解析器

- lmntal.cup を参照して、生成されている模様
