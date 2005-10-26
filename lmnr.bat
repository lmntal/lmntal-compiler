@echo off
setlocal

call %~dp0set_cp
set CLASSPATH=%1;%CLASSPATH%
java Main %*
