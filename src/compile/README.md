# lmntal-compiler/src/compile

lmntal のコンパイラ

## 概要

- エントリーポイントはRulesetCompiler.javaのprocessMembrane(/src/runtime/Frontend.javaより)

## 構成

```sh
├── Compactor.java # 命令列長最適化（未完成）
├── CompileException.java # ルールコンパイルエラー用クラス
├── Grouping.java # 命令列のグループ化？
├── GuardCompiler.java # ルールのガード部のコンパイラ
├── HeadCompiler.java # ルール左辺のコンパイラ
├── LHSCompiler.java # GuardCompiler/HeadCompilerの親クラス
├── Module.java # モジュールシステムの実装
├── Optimizer.java # 命令列最適化用クラス
├── README.md
├── RuleCompiler.java # ルールのコンパイラ
├── RulesetCompiler.java # ルールコンパイラのエントリーポイント
├── parser
│   ├── LMNParser.java # パーサのエントリーポイント
│   ├── LMNtal_BNF.txt
│   ├── Lexer.java # from lmntal.flex
│   ├── MySymbol.java
│   ├── ParseException.java # パースエラー用クラス
│   ├── SrcAtom.java # アトム用データ構造
│   ├── SrcContext.java # 文脈用データ構造
│   ├── SrcDumper.java # データ構造ダンパー
│   ├── SrcHyperLink.java # ハイパーリンク用データ構造
│   ├── SrcLink.java # リンク用データ構造
│   ├── SrcLinkBundle.java # リンク束用データ構造
│   ├── SrcMembrane.java # 膜用データ構造
│   ├── SrcName.java # アトム名トークン用データ構造
│   ├── SrcProcessContext.java # プロセス文脈用データ構造
│   ├── SrcRule.java # ルール用データ構造
│   ├── SrcRuleContext.java # ルール文脈用データ構造
│   ├── lmntal.cup # lmntalの構文定義
│   ├── lmntal.flex # lmntalプログラムの字句解析
│   ├── parser.java # from lmntal.cup
│   └── sym.java # from lmntal.cup
├── structure
│   ├── Atom.java # ???
│   ├── Atomic.java # ???
│   ├── Context.java # ???
│   ├── ContextDef.java # ???
│   ├── LinkOccurrence.java # ???
│   ├── Membrane.java # ???
│   ├── ProcessContext.java # ???
│   ├── ProcessContextEquation.java # ???
│   ├── RuleContext.java # ???
│   └── RuleStructure.java # ???
└── util
    └── Collector.java # 膜中アトム収集用
```

## 各ファイルについて（主要なもの）

## LHSCompiler.java
HeadCompilerクラス、GuardCompilerクラスの親クラス。
本クラス内フィールド「match」に対して、Instructionクラス要素をaddすることで、中間命令列を生成する。

### HeadCompiler.java
ルール左辺のコンパイルを行うクラス。

### GuardCompiler.java
ガード制約部分の中間命令列生成を担うクラス。

- putLibrary関数を用いて、ガード制約の追加が出来る。
- 随所に現れるmatch.add命令によって、中間命令列の追加を行っている。
- fixTypedProcesses関数内でガード制約の識別を行い、種類に応じた中間命令列の生成を行う。

### RuleCompiler.java
ルール全体のコンパイルを担うクラス

- compile関数
初期化時に指定されたルール(「rs」フィールド)をコンパイルする。
左辺、ガード、右辺と順々にコンパイルを進める。
- compile_h関数
ルール左辺のコンパイルを行う。
HeadCompilerインスタンスを用いて処理する。
- compile_g関数
ガード部のコンパイルを行う。
GuardCompilerインスタンスを生成し、主にGuardCompilerクラスのメソッドを用いて処理する。
- compile_r関数
ルール右辺のコンパイルを行う。
swaplinkを利用する場合は、この関数の代わりにcompile_r_swaplink関数が使用される。

## RuleSetCompiler.java
### processMembrane
- 役割
compilerのエントリーポイント
与えられた膜(Membraneオブジェクト)の階層下にある全ての RuleStructure について, 対応する Rule を生成して与えられた膜のルールセット(rulesetsフィールド)に追加する

- 以下は名前が似ているけど全くの別物
RuleStructureオブジェクトはもとのLMNtalルールの抽象構文木
Ruleオブジェクトはコンパイルが終わった後のilファイルの中間命令列

- 担当する仕事
1. 再帰
2. RuleStructure->Rule変換(RuleCompilerオブジェクトのcompileメソッドを使うだけ)
3. 結果の出力(入力であるMembraneオブジェクトが結果を受け取るrulesetsフィールド(Rulesetオブジェクトのリスト)をもっているのでaddするだけ)
4. 必要ならシステムルールセット(全膜内で適用されるルール)に登録

- 再帰
与えられた膜の全ての子膜について自身を再帰的に適用
与えられた膜に(追加の膜を隔てずに)含まれる全てのルールについて, そのHead, Bodyを格納する膜に自身を再帰的に適用
すなわち, 全てのルール内ルールについてRuleStructure->Rule変換を適用する

- 変換の流れ(RuleStructure, Rule, Ruleset, InterpretedRuleset)
変換の過程である(入力Membraneオブジェクトのrules->ローカル変数rules->ローカル変数ruleset->出力Membraneオブジェクトのruleset)の型はそれぞれ,
(RuleStructureのリスト->(変換済み)Ruleのリスト->InterpretedRuleset(Rulesetオブジェクトを継承)->Rulesetオブジェクトのリスト)

- 歴史的経緯
/src/util/GlobalSystemRulesetGeneratorからの呼び出しはただの残骸
初期プロセスの生成のためcompileMembrane->processMembraneの二段階にわけて呼び出しをしていたが今はprocessMembraneに統合された

- processMembraneを呼び出すメソッド(祖先)
/src/runtime/Frontendクラスのrunメソッド
自分自身(processMembraneは再帰関数であり, 引数として与えられた膜の各子膜について自身を適用する)

- processMembraneが呼び出すメソッド(子孫)
RuleCompilerクラスのcompileメソッド
InterpretedRulesetクラスのコンストラクタ
