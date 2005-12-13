@echo off
setlocal

call "%~dp0set_cp"
java -DLMNTAL_HOME=%LMNTAL_HOME% runtime.FrontEnd %*
