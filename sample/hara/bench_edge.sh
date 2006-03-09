#a="10 15 20 25 30 35 40 45 50 55 60 65 70 75 80 85 90 95 100 105 110 115 120"
#a="10 15 20 25 30"
a="10 15 20 25 30 35 40 45 50"
#file="out35-50"

for file in out10-50_00 out10-50_01 out10-50_02

do echo > $file

for n in $a; do bin/lmnr_cyg edge_benchmark.jar -v0 --args $n link >> $file; done

echo >> $file

#for n in $a; do bin/lmnr_cyg edge_benchmark.jar -v0 --args $n int >> $file; done

echo >> $file

for n in $a; do sicstus -l sample/hara/prolog/edge_sic.pl --goal "goL($n),halt."  >> $file; done

echo >> $file

#for n in $a; do sicstus -l sample/hara/prolog/edge_sic.pl --goal "go($n),halt." >> $file; done


done
