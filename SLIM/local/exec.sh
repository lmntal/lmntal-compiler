#! /bin/sh

LMNHOME=~/workspace/lmntal

for i in $*
do
  echo ">>> $i" >&2

  [ $i -nt ${i%.*}.jil ] && $LMNHOME/bin/lmntal --compileonly --slimcode $i >${i%.*}.jil
  [ $i -nt ${i%.*}.sil ] && $LMNHOME/SLIM/src/conv_il --v1 <${i%.*}.jil >${i%.*}.sil

  [ $i -nt ${i%.*}.jout ] && $LMNHOME/bin/lmntal --hideruleset --hiderule $i >${i%.*}.jout
  ($LMNHOME/SLIM/src/slim --hideruleset ${i%.*}.sil >${i%.*}.sout) >&${i%.*}.serr

#  $LMNHOME/SLIM/src/slimtest.sh $i ${i%.*}.sout >${i%.*}.check
  $LMNHOME/SLIM/local/slimtest.sh ${i%.*}.jout ${i%.*}.sout >${i%.*}.check

  if [ "`cat ${i%.*}.check`" = "success" ];
  then
      echo "$i is success"
  else
      echo "$i is failure"
  fi

  echo '<<<'" $i" >&2
done
