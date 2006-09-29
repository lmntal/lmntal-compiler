#!/usr/bin/perl

# このファイルのあるディレクトリで実行しないと上手くいかない
use Cwd;
$pwd = Cwd::getcwd();

require 'check.pl';

&check($pwd);
