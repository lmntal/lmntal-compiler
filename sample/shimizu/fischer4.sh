#!/bin/sh
lmntal --slimcode -O3 fischer4.lmn >fischer4.il
#最後のInlineを削除
sed -e 's/Inline//' fischer4.il > fischer4_temp.il
cat tick.il >> fischer4_temp.il
mv -f fischer4_temp.il fischer4.il
echo '>slim --nd -p fischer4.il'
slim --nd -p fischer4.il
echo '>slim -p --ltl psym fischer2.psym --nc fischer2.nc fishcer4.il'
slim -p --ltl --psym fischer2.psym --nc fischer2.nc fischer4.il
echo '>slim --ltl psym fischer1.psym --nc fischer1.nc fishce4.il'
slim --ltl --psym fischer1.psym --nc fischer1.nc fischer4.il
