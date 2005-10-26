@echo off

set CLASSPATH=bin
REM set CLASSPATH=dist\lmntal.jar
for %%f in (lmntal_lib\*.jar) do call :append %%f

goto :EOF

:append
set CLASSPATH=%CLASSPATH%;%1
