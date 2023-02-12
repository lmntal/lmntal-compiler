# LMNtal Compiler

This is an compiler of the
[LMNtal (pronounced "elemental") ](https://www.ueda.info.waseda.ac.jp/lmntal) language.

LMNtal is a programming and modeling language
based on hierarchical graph rewriting.
It was designed to be a substrate language of diverse computational models,
especially those addressing concurrency,
mobility and multiset rewriting.

## Getting started

### Prerequisites

- JDK 8 or higher version
- Ant

### Installation

```
git clone git@github.com:lmntal/lmntal-compiler.git
cd lmntal-compiler
ant
```

### Usage

You can use the compiler by running the shell script `bin/lmntal`.
Please run it with `--help` option to get more detailed information.

```
bin/lmntal --help
```

## Development

Contributions are what make the open source community
such an amazing place to learn, inspire, and create.
Any contributions you make are **greatly appreciated**.

<a href="https://github.com/lmntal/lmntal-compiler/graphs/contributors">
  <img src = "https://contrib.rocks/image?repo=lmntal/lmntal-compiler"/>
</a>

### Formatting

We are currently using google-java-format.

```sh
wget https://github.com/google/google-java-format/releases/download/v1.15.0/google-java-format-1.15.0-all-deps.jar
java -jar /path/to/google-java-format-1.15.0-all-deps.jar --replace --skip-javadoc-formatting [files...]
```

See <https://github.com/google/google-java-format> for more detailed information.

### Directory structure

```bash
- src/
  - compiler/
    - parser/
    - structure/
    - util/
    - Compactor.java
    - CompileException.java
    - Grouping.java
    - GuardCompiler.java
    - HeadCompiler.java
    - LHSCompiler.java
    - Module.java
    - Optimizer.java
    - README.md
    - RuleCompiler.java
    - RulesetCompiler.java
  - util/ # Some utility functions.
  - type/ # A type checker of the polarity of links (please refer Kudo's thesis).
  - runtime/ # The runtime in the past century.
```

### build.xml

1. `flex_*` / `bison_*`  
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
   `bin/*` (set_cp_jar.bat → set_cp.bat, set_cp_jar.sh → set_cp.sh)
   `sample/public/*.lmn` → `sample/*.lmn`
7. zip  
   archive の zip バージョン。lmntal.zip を生成する。
8. dist
   help, jar, archive を実行する。
9. clean  
   8 で生成されるファイルの削除。
10. doc  
    javadoc を docs ディレクトリに置く。

### Testing

Please test the compiler with the runtime [SLIM](https://github.com/lmntal/slim).

```bash
export LMNTAL_HOME=/path/to/the/compiler
# e.g. export LMNTAL_HOME=/home/username/lmntal

git clone https://github.com/lmntal/slim
cd slim
./autogen.sh
./configure
make
make install

make check
```

### 公開に際して

必要に応じて Env.LMNTAL_VERSION の値を修正すること。

## Contact

バグ報告は <lmntal@ueda.info.waseda.ac.jp> までお願いします。
