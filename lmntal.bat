@echo off
setlocal

set LMNTAL_HOME=D:\Documents\LMNtal

set CLASSPATH=.;%LMNTAL_HOME%\bin
for %%f in (%LMNTAL_HOME%\lmntal_lib\*.jar) do call d:\tmp\append.bat %%f

if "%1"=="-c" (
	java runtime.FrontEnd --translate %2 %3 %4 %5 %6 %7 %8 %9
) else if "%1"=="-r" (
	java -cp %CLASSPATH%;%2 %3 %4 %5 %6 %7 %8 %9 Main
) else (
	echo Invalid option
)
