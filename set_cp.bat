@echo off
setlocal

set CLASSPATH=.;%LMNTAL_HOME%\bin
for %%f in (%LMNTAL_HOME%\lmntal_lib\*.jar) do call :append %%f

goto :EOF

:append
set CLASSPATH=%CLASSPATH%;%1
