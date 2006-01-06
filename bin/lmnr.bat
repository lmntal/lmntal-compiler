@echo off
setlocal

call "%~dp0set_cp"
set CLASSPATH=%1;%CLASSPATH%
java -DLMNTAL_HOME=%LMNTAL_HOME% Main %*
