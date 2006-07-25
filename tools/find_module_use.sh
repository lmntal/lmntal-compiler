#!/bin/bash
#
# 指定されたモジュールが使われているプログラムを
# sample, lib/src, lib/public から探します
#
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

# そういうモジュールがあるかどうか調べる
if [ -e $LMNTAL_HOME/lib/public/$1.lmn -o -e $LMNTAL_HOME/lib/src/$1.lmn ]; then
	grep "\\b$1\\." `find $LMNTAL_HOME/sample -name *.lmn` | cut -d: -f1 | uniq
	grep "\\b$1\\." `find $LMNTAL_HOME/lib/public -name *.lmn` | cut -d: -f1 | uniq
	grep "\\b$1\\." `find $LMNTAL_HOME/lib/src -name *.lmn` | cut -d: -f1 | uniq
else
	echo $1: module not found.
	exit
fi
