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

$begin_line = 1;
$end_line = 50000;

for(@ARGV){
	if($_ =~ /b([0-9]+)/ ){
		$begin_line = $1;
	}elsif($_ =~ /e([0-9]+)/ ){
		$end_line = $1;
	}elsif($_ =~ /t/ ){
		$flg_translate = true;
	}
};

$lmntal_t_compiler = $pwd . "/../../bin/lmnc";
$lmntal_t_runtime = $pwd . "/../../bin/lmnr";
$lmntal_runtime = $pwd . "/../../bin/lmntal";

open(INPUTFILE, $pwd . "/testdata.lmn");  # input file
@file = <INPUTFILE>;

for($i=$begin_line-1;$i<=$#file && $i<=$end_line-1;$i++){
	$file[$i] =~ s/\n//;                                # delete return code
	@data = split /%/, $file[$i];
	$program = $data[0];                                # input data
	$goal = $data[1];                                   # right answer
	$flg = $data[2];                                    # ok / ng
	unless($program && $goal){next;}                    # skip comment
	unless($flg){$flg = "ok"}

	if($flg_translate){
		open(TMPOUTPUTFILE, "> " . $pwd . "/tmp.lmn"); #tmp output
		print(TMPOUTPUTFILE $program);
		close(TMPOUTPUTFILE);
		$cmp = $lmntal_t_compiler . " " . $pwd . "/tmp.lmn";
		$dust = `$cmp`;
		$run = $lmntal_t_runtime . " " . $pwd . "/tmp.jar";		
	}else{
#		$program =~ s/\$/\\\$/g;
		$run = $lmntal_runtime . " -e \"" . $program . "\"";
	}

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
