#!/bin/sh
lmntal --slimcode -O3 fischer.lmn >fischer.il
#最後のInlineを削除
sed -e 's/Inline//' fischer.il > fischer_temp.il
cat tick.il >> fischer_temp.il
mv -f fischer_temp.il fischer.il
echo '>slim --nd fischer.il'
slim --nd fischer.il
echo '>slim --ltl psym fischer2.psym --nc fischer2.nc fishcer.il'
slim --ltl --psym fischer2.psym --nc fischer2.nc fischer.il
echo '>slim --ltl psym fischer1.psym --nc fischer1.nc fishcer.il'
slim --ltl --psym fischer1.psym --nc fischer1.nc fischer.il
