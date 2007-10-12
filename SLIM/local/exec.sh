#! /bin/sh

LMNHOME=~/workspace/lmntal
LMNTAL=$LMNHOME/bin/lmntal
CONV_IL=$LMNHOME/SLIM/src/conv_il
SLIM=$LMNHOME/SLIM/src/slim

slimonly_flag=0
clean_flag=0
prof_flag=0

while getopts "SCP" opt
do
  case $opt in
      "S" ) slimonly_flag=1 ;;
      "C" ) clean_flag=1 ;;
      "P" ) prof_flag=1 ;;
  esac
done
shift `expr $OPTIND - 1`

if [ $clean_flag -eq 1 ]
then
    for i in $*
    do
      rm -f ${i%.*}.prof
      rm -f ${i%.*}.jil
      rm -f ${i%.*}.sil
      rm -f ${i%.*}.jout
      rm -f ${i%.*}.sout
      rm -f ${i%.*}.serr
      rm -f ${i%.*}.check
    done

    exit 0
fi

if [ $prof_flag -eq 1 ]; then CONV_IL_FLAG=""; else CONV_IL_FLAG="--v1"; fi

for i in $*
do
  echo ">>> $i" >&2

  if [ $i -nt ${i%.*}.jil ]; then
      $LMNTAL --compileonly --slimcode $i >${i%.*}.jil
  fi
  
  if [ ${i%.*}.jil -nt ${i%.*}.sil ] || [ $CONV_IL -nt ${i%.*}.sil ]; then
      $CONV_IL $CONV_IL_FLAG <${i%.*}.jil >${i%.*}.sil
  fi

  if [ ${i%.*}.sil -nt ${i%.*}.sout ] || [ $SLIM -nt ${i%.*}.sout ]; then
      ($LMNHOME/SLIM/src/slim --hideruleset ${i%.*}.sil >${i%.*}.sout) >&${i%.*}.serr
  fi

  if [ $prof_flag -eq 1 ]
  then
      gprof $LMNHOME/SLIM/src/slim gmon.out >${i%.*}.prof
  elif [ $slimonly_flag -eq 0 ]
  then
      if [ $i -nt ${i%.*}.jout ]; then
	  $LMNTAL --hideruleset --hiderule $i >${i%.*}.jout
      fi
  
      if [ ${i%.*}.jout -nt ${i%.*}.check ] || [ ${i%.*}.sout -nt ${i%.*}.check ]; then
	  $LMNHOME/SLIM/local/slimtest.sh ${i%.*}.jout ${i%.*}.sout >${i%.*}.check
      fi

      if [ "`cat ${i%.*}.check`" = "success" ];
      then
	  echo "$i is success"
      else
	  echo "$i is failure"
      fi
  fi
  
  rm -f gmon.out
  
  echo '<<<'" $i" >&2
done
