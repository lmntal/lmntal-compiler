set PATH=%PATH%;..
for %%f in (*.lmn) do ..\lmnc --library %%f
