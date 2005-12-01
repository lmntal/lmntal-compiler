@echo off
setlocal

call "%~dp0set_cp"
java -DLMNTAL_HOME=%LMNATL_HOME% runtime.FrontEnd %*
