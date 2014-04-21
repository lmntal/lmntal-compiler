
lmntal-compiler - LMNtal Compiler
===============================

LMNtalのコンパイラです。
バグ報告は lmntal@ueda.info.waseda.ac.jp までお願いします。

---


## Getting started

### ant 

Eclipse上で実行する

```
1. Eclipseにプロジェクトをインポートする
2. build.xml を右クリックして「Run As」-「Ant Build...」を選択する。
3. 実行したいターゲット（デフォルトは dist）を選択して「Run」をクリック。
```

or

コマンドラインで実行する

```
1. ant の bin ディレクトリにパスを通す。
    $ export PATH=$PATH:<ECLIPSE_HOME>/plugins/org.apache.ant_<version>/bin
2. jnuit.jar にクラスパスを通す。
    $ export CLASSPATH=<ECLIPSE_HOME>/plugins/org.junit_3.8.1/junit.jar
3. 以下のコマンドを実行する
    $ ant [target]
```

## Develop

### build.xml

できる事一覧

1. flex_* / bison_*  
    lexer / parser の生成
2. help  
    Help.java （--help オプション指定時の出力を行うクラス）の生成
3. system_ruleset  
    GlobalSystemRuleset.java の生成
4. compile, jar  
    処理系のコンパイル
5. lib  
    LMNtal ライブラリ (*.jar) の生成
6. archive  
    公開用形式でアーカイブ (lmnta.tgz)。アーカイブするものは以下の通り。（矢印の右はアーカイブ内のパス）
    bin/* (set_cp_jar.bat → set_cp.bat, set_cp_jar.sh → set_cp.sh)
    lib/std_lib.jar
    lib/public/*.lmn → lib/src/*.lmn
    sample/public/*.lmn → sample/*.lmn
7. dist (default)  
    help, compile, jar, lib, archive を実行
8. lib_clean  
    4. で生成されるファイルの削除
9. clean  
    4. ～ 6. で生成されるファイルの削除（昔とは違うので注意）

### 公開の手順

1. 必要に応じて Env.LMNTAL_VERSION の値を修正
2. ant dist の実行
3. lmntal.jar を、banon:/var/www/htdocs/lmntal/lmntal.tgz (または lmntal-beta.tgz) に置く。  
    今は、リンクは lmntal.jar に張られているので、lmntal.tgz においたら公開用 wiki を書き直す必要がある。



