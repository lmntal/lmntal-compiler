@echo off
setlocal

call set_cp
set jar=%1
shift
java -cp %CLASSPATH%;%jar% Main %*
