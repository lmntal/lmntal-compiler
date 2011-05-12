#!/bin/sh
lmntal --slimcode -O3 coffee_machine.lmn >coffee_machine.il
#最後のInlineを削除
sed -e 's/Inline//' coffee_machine.il > coffee_machine_temp.il
cat cm_tick.il >> coffee_machine_temp.il
mv -f coffee_machine_temp.il coffee_machine.il
echo '>slim --nd coffee_machine.il'
slim --nd coffee_machine.il
