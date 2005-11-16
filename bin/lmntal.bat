@echo off
setlocal

call "%~dp0set_cp"
java runtime.FrontEnd %*
