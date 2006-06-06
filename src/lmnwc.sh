#!/bin/bash

echo -n "total (except test)"
wc `find $1 | grep "\.java$" | grep -v test` -l | tail -1

echo -n "  compile"
wc compile/*.java compile/parser/*.java compile/structure/*.java  -l | tail -1

echo -n "  runtime"
wc runtime/*.java -l | tail -1 | sed 's/\[ \]\+/ /g'
