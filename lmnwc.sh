#!/bin/bash
#
# 処理系の行数を表示するスクリプト by inui

# 各パッケージの行数を表示（test パッケージは除く）
for i in `find . -maxdepth 1 -type d | grep -v 'test\|CVS\|\.$'`
do
	wc `find $i -name *.java` -l |
	# 最後の行（合計情報）だけとる
	tail -1 |
	# 行数だけ取ってきれいに表示する
	perl -e "\$_=<>;s/^\s+//;split(/\s+/);printf\"%-16s %7d\n\","${i#./}",\$_[0];" |
   	# パッケージ名に置換
	sed -e 's/\//\./g'
done

echo "------------------------"

# 合計数を表示
wc `find . -name *.java | grep -v test` -l |
tail -1 |
perl -e "split(/\s+/, <>);printf\"%-16s %7d\n\",\"TOTAL\",\$_[1];"

# compile パッケージの合計
#wc `find compile -name *.java` |
#tail -1 |
#perl -e "split(/\s+/, <>);printf\"%-30s %7d\n\",\"(compile)\",\$_[1];"
