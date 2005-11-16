@echo off

set CLASSPATH=%~dp0..\classes
REM set CLASSPATH=%~dp0lmntal.jar

pushd %~dp0..
for %%f in (lib\*.jar) do call :append %%f
popd

goto :EOF

:append
set CLASSPATH=%CLASSPATH%;%~dp0..\%*
