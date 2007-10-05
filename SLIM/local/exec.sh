#! /bin/sh

LMNHOME=~/workspace/lmntal

for i in $*
do
  echo ">>> $i" >&2

  [ $i -nt ${i%.*}.jil ] && $LMNHOME/bin/lmntal --compileonly --slimcode $i >${i%.*}.jil
  [ $i -nt ${i%.*}.sil ] && $LMNHOME/SLIM/src/conv_il --v1 <${i%.*}.jil >${i%.*}.sil

  [ $i -nt ${i%.*}.jout ] && $LMNHOME/bin/lmntal $i >${i%.*}.jout
  ($LMNHOME/SLIM/src/slim --hideruleset ${i%.*}.sil >${i%.*}.sout) >&${i%.*}.serr

  if [ "`$LMNHOME/SLIM/src/slimtest.sh $i ${i%.*}.sout`" = "success" ];
  then
      echo "$i is success"
  else
      echo "$i is failure"
  fi

  echo '<<<'" $i" >&2
done
