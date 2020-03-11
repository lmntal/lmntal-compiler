
LMNtal Compiler
===============================

LMNtalのコンパイラです。
バグ報告は lmntal@ueda.info.waseda.ac.jp までお願いします。

---
## Getting started
### Prerequisites
- JDK 8 or higher version
- Ant
### Installation
```
$ git clone git@github.com:lmntal/lmntal-compiler.git
$ cd lmntal-compiler
$ ant
```
### Using LMNtal Compiler
binディレクトリ以下のシェルスクリプトを実行することで使えます。詳しくは`--help`オプションで確認してください。
```
bin/lmntal --help
```

## Development

### build.xml

1. flex_* / bison_*  
    lexer / parser の生成
2. help  
    Help.java （--help オプション指定時の出力を行うクラス）の生成
3. mkdir  
    classesディレクトリの生成
4. compile  
    mkdirとhelpを実行してソースのコンパイルをする。classesディレクトリに置く。
5. jar (default)  
    compileを実行してbinディレクトリにlmntal.jarを置く。
6. archive  
    公開用形式でアーカイブ (lmntal.tgz)。アーカイブするものは以下の通り。（矢印の右はアーカイブ内のパス）
    bin/* (set_cp_jar.bat → set_cp.bat, set_cp_jar.sh → set_cp.sh)
    sample/public/*.lmn → sample/*.lmn
7. zip  
    archiveのzipバージョン。lmntal.zipを生成する。
8. dist  
    help, jar, archiveを実行する。
9. clean  
    8で生成されるファイルの削除。
10. doc  
    javadocをdocsディレクトリに置く。

### 公開に際して
必要に応じて Env.LMNTAL_VERSION の値を修正すること。
