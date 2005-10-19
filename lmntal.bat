@echo off
setlocal

call %~dp0\set_cp
java runtime.FrontEnd %*
