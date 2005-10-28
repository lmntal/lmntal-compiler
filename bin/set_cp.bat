@echo off

set CLASSPATH=%~dp0..\classes
REM set CLASSPATH=%~dp0dist\lmntal.jar

pushd %~dp0..
for %%f in (lmntal_lib\*.jar) do call :append %%f
popd

echo %CLASSPATH%
goto :EOF

:append
set CLASSPATH=%CLASSPATH%;%~dp0..\%*
