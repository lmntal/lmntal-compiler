#!/bin/bash
#
# 処理系の行数を表示するスクリプト by inui

# 各パッケージの行数を表示（test パッケージは除く）
for i in `find . -type d | grep -v test`
do
	if [ -d $i ]; then
		# *.java ファイルがあるならそれを全部 wc に渡す
		ls $i/*.java >& /dev/null && wc `ls $i/*.java`  -l |
		# 最後の行（合計情報）だけとる
		tail -1 |
		# 行数だけ取ってきれいに表示する
		perl -e "split(/\s+/, <>);printf\"%-30s %7d\n\",\"${i#./}\",\$_[1];" |
	   	# パッケージ名に置換
		sed -e 's/\//\./g'
	fi
done

echo "--------------------------------------"

# 合計数を表示
wc `find . -name *.java | grep -v test` -l |
tail -1 |
perl -e "split(/\s+/, <>);printf\"%-30s %7d\n\",\"TOTAL\",\$_[1];"

# compile パッケージの合計
pushd compile > /dev/null
wc *.java parser/*.java parser/intermediate/*.java structure/*.java | tail -1 |
perl -e "split(/\s+/, <>);printf\"%-30s %7d\n\",\"(compile)\",\$_[1];"
popd > /dev/null
