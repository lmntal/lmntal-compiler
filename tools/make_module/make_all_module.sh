#!/bin/sh

# 出力先のディレクトリ
dir=~/workspace/lmntal/lib/java_lib;

# オプションを解析する
while getopts C:h option
do
	case "$option" in
	h)	echo "Usage: ./make_all_module.sh [OPTION] CLASS_LIST_FILE"
		echo "  -h show this help"
		echo "  -C change target creation directory"
		exit
		;;
	C)	dir=$OPTARG
        ;;
    esac
done
shift $(($OPTIND - 1))

# allclasses.txt を1行ずつ読み込む
for class in `cat $1`; do
	module=`echo $class | tr . _`
	javap -public $class | ./make_module.pl > $dir/$module.lmn
	echo $dir/$module.lmn
done
