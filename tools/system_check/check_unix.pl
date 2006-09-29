#!/usr/bin/perl

open(PWD, "dirname $0 |");
$pwd = <PWD>;
$pwd =~ s/\n//;

require 'check.pl';

&check($pwd);
