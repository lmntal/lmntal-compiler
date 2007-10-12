#!/bin/sh

slimpath=~/workspace/lmntal/SLIM/src

sed --quiet 's/ *case \(.*\):/\1/p' $slimpath/task.c >memo.task
sed --quiet 's/ *\(.*\),/\1/p' $slimpath/instruction.h >memo.instruction
cat memo.task memo.instruction | sort | uniq --unique
rm memo.task memo.instruction
