@echo off

set CLASSPATH=%~dp0bin
REM set CLASSPATH=%~dp0dist\lmntal.jar

pushd %~dp0
for %%f in (lmntal_lib\*.jar) do call :append %%f
popd

goto :EOF

:append
set CLASSPATH=%CLASSPATH%;%~dp0%*
