# LMNtal Compiler

LMNtal のコンパイラです。
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

bin ディレクトリ以下のシェルスクリプトを実行することで使えます。詳しくは`--help`オプションで確認してください。

```
bin/lmntal --help
```

## Development

### Formatting

We are currently using prettier-java.

```sh
npm install # install prettier
npx prettier --write "**/*.java"
```

See <https://github.com/jhipster/prettier-java> for more detailed information.

### build.xml

1. flex*\* / bison*\*  
   lexer / parser の生成
2. help  
   Help.java （--help オプション指定時の出力を行うクラス）の生成
3. mkdir  
   classes ディレクトリの生成
4. compile  
   mkdir と help を実行してソースのコンパイルをする。classes ディレクトリに置く。
5. jar (default)  
   compile を実行して bin ディレクトリに lmntal.jar を置く。
6. archive  
   公開用形式でアーカイブ (lmntal.tgz)。アーカイブするものは以下の通り。（矢印の右はアーカイブ内のパス）
   bin/_ (set_cp_jar.bat → set_cp.bat, set_cp_jar.sh → set_cp.sh)
   sample/public/_.lmn → sample/\*.lmn
7. zip  
   archive の zip バージョン。lmntal.zip を生成する。
8. dist  
   help, jar, archive を実行する。
9. clean  
   8 で生成されるファイルの削除。
10. doc  
    javadoc を docs ディレクトリに置く。

### 公開に際して

必要に応じて Env.LMNTAL_VERSION の値を修正すること。
