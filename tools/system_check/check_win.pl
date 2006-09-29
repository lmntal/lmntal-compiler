#!/usr/bin/perl

# このファイルのあるディレクトリで実行しないと上手くいかない(求む 修正)
use Cwd;
$pwd = Cwd::getcwd();

require 'check.pl';

check($pwd);
