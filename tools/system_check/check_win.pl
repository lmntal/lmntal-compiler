#!/usr/bin/perl

######################################
#                                    #
# Java LMNtal system checker on Perl #
#                                    #
######################################
# AUTHOR : kudo
# LAST UPDATE : 2006/05/25

# This is LMNtal system checker.

#open(PWD, "dirname $0 |");
#$pwd = <PWD>;
#$pwd =~ s/\n//;

# このファイルのあるディレクトリで実行しないと上手くいかない(要修正)
use Cwd;
$pwd = Cwd::getcwd();

$lmntal_runtime = $pwd . "/../../bin/lmntal";

open(INPUTFILE, $pwd . "/testdata.lmn");  # input file
@file = <INPUTFILE>;

for($i=0;$i<=$#file;$i++){
	$file[$i] =~ s/\n//;                                # delete return code
	@data = split /%/, $file[$i];
	$program = $data[0];                                # input data
	$goal = $data[1];                                   # right answer
	$flg = $data[2];                                    # ok / ng
	unless($program && $goal){next;}                    # skip comment
	unless($flg){$flg = "ok"}

#	$program =~ s/\$/\\\$/g;
	$run = $lmntal_runtime . " -e \"" . $program . "\"";
	$output = `$run`;

	$output =~ s/\n//;
	$output =~ s/@[0-9]+/()/g;
	print (($i+1) . " : ");
	$check_program = "{" . $output . "}, ({" . $goal . "}/:-ok)";
	$check_run = $lmntal_runtime . " -e \"" . $check_program . "\"";
	$checked = `$check_run`;

	$result = index ($checked, "ok");
	if($flg == "ok" && $result == 0){
		print "ok";
	}elsif($flg == "ng" && $result != 0){
		print "ok";
	}else{
		print "ng : ";
		print $program;
		print " ==> ";
		print $output;
		print " <=> " . $goal;
	}
	print "\n";
}
close(IN);
