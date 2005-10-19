@echo off
setlocal

call %~dp0\set_cp
set CLASSPATH=%1;%CLASSPATH%
shift
java Main %*
