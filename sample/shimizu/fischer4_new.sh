#!/bin/sh
lmntal --slimcode -O3 fischer4_new.lmn >fischer4_new.il
#最後のInlineを削除
sed -e 's/Inline//' fischer4_new.il > fischer4_new_temp.il
cat tick_new.il >> fischer4_new_temp.il
mv -f fischer4_new_temp.il fischer4_new.il
echo '>slim --nd -p fischer4_new.il'
slim --nd -p fischer4_new.il
