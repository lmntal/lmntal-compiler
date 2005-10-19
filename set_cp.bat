@echo off

set LMNTAL_HOME=%~dp0
set CLASSPATH=%LMNTAL_HOME%\bin
REM set CLASSPATH=%LMNTAL_HOME%\dist\lmntal.jar
for %%f in (%LMNTAL_HOME%\lmntal_lib\*.jar) do call :append %%f

goto :EOF

:append
set CLASSPATH=%CLASSPATH%;%1
