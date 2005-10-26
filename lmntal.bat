@echo off
setlocal

%~d0
cd %~p0
call set_cp
java runtime.FrontEnd %*
