#!/bin/bash
#
# 指定されたモジュールの translate されたファイルを削除します
# by inui
#
# 使用例
# $ ./clean_lib.sh io math

for i
do
	rm -rf public/translated/module_$i
	rm -f public/translated/Module_$i.java
	rm -f public/translated/Module_$i.class
	rm -f $i.jar
done
