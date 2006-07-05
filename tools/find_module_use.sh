#!/bin/bash
#
# 指定されたモジュールが使われているプログラムを sample から探します
# by inui
#
# 使用例
# $ find_module.sh integer
# 
# マッチする
#   integer.use.
#   a=integer.set(1,10).
# マッチしない
#   biginteger.use.   <-- モジュール名が違う
#   integer(3).       <-- メソッド呼び出しではない

# 環境に合わせて設定してください
LMNTAL_HOME=~/workspace/lmntal

grep "\\b$1\\." `find $LMNTAL_HOME/sample -name *.lmn` | cut -d: -f1 | uniq
