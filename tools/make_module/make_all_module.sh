#!/bin/sh

# 使用法
# $ ./make_all_module.sh allclasses.txt

# 出力先のディレクトリ
dir="~/workspace/lmntal/lib/java";

# allclasses.txt を1行ずつ読み込む
for class in `cat $1`; do
	module=`echo $class | tr A-Z a-z | tr . _`
	javap -public $class | ./make_module.pl > $dir/$module.lmn
	echo $class
done