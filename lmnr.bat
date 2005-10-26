@echo off
setlocal

%~d0
cd %~p0
call set_cp
set CLASSPATH=%1;%CLASSPATH%
java Main %*

