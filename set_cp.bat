@echo off

set CLASSPATH=%~dp0bin
REM set CLASSPATH=%~dp0dist\lmntal.jar
for %%f in (%~dp0lmntal_lib\*.jar) do call :append %%f

goto :EOF

:append
set CLASSPATH=%CLASSPATH%;%1
