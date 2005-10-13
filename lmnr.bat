@echo off
setlocal

call set_cp
set CLASSPATH=%1;%CLASSPATH%
shift
java Main %*
