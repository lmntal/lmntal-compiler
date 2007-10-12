# /bin/sh

LMNHOME=~/workspace/lmntal
LINE=`expr $1 + 5`

sed --quiet -e "${LINE}p" $LMNHOME/SLIM/src/instruction.h
