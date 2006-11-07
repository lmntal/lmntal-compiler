#!/bin/sh

# 出力先のディレクトリ
lib=~/workspace/lmntal/lib/java_lib;

# オプションを解析する
while getopts d:h option
do
	case "$option" in
	h)	echo "Usage: ./make_all_module.sh [OPTION] CLASS_LIST_FILE"
		echo "  -h show this help"
		echo "  -C change target creation directory"
		exit
		;;
	d)	lib=$OPTARG
        ;;
    esac
done
shift $(($OPTIND - 1))

# allclasses.txt? を1行ずつ読み込む
for class in `cat $1`; do
#	module=`echo $class | tr . / | tr A-Z a-z`	
#	dir=`dirname $module`
	module=`echo $class | tr . _`
#	mkdir -p $lib/$dir #ディレクトリがなかったときのために備えて毎回作る
	./class2module.pl $class > $lib/$module.lmn
	echo $lib/$module.lmn
done
