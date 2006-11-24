#!/bin/sh
./check_unix.pl $*
./check_unix.pl $* - -O1
./check_unix.pl $* - -O3
./check_unix.pl $* - -s1
./check_unix.pl $* - -s2
./check_unix.pl $* - -s3
./check_unix.pl $* - --optimize-merging
./check_unix.pl $* t
./check_unix.pl $* t - -O1
./check_unix.pl $* t - -O3
./check_unix.pl $* t - -s1
./check_unix.pl $* t - -s2
./check_unix.pl $* t - -s3
./check_unix.pl $* t - --optimize-merging
