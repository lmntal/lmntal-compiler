@echo off

set LMNTAL_HOME=%~dp0..

set CLASSPATH=%LMNTAL_HOME%\classes
pushd %~dp0..
for %%f in (lib\*.jar) do call :append %%f
popd

goto :EOF

:append
set CLASSPATH=%CLASSPATH%;%~dp0..\%*
