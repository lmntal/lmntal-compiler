@echo off

set LMNTAL_HOME=%~dp0..

set CLASSPATH=%LMNTAL_HOME%\bin\lmntal.jar
pushd %~dp0..
for %%f in (lib\*.jar) do call :append %%f
popd

goto :EOF

:append
set CLASSPATH=%CLASSPATH%;%~dp0..\%*
