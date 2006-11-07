#!/bin/sh

# 出力先のディレクトリ
lib=~/workspace/lmntal/lib/java_lib;

for class in `cat $1`; do
	module=`echo $class | tr . _`
	javap -public $class | ./eventlistener2module.pl > $lib/$module.lmn
	echo $lib/$module.lmn
done
