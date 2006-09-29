######################################
#                                    #
# Java LMNtal system checker on Perl #
#                                    #
######################################
# AUTHOR : kudo
# LAST UPDATE : 2006/05/22

# This is LMNtal system checker.

sub tmpout{
	$prog = $_[0];
	$pwd = $_[1];
	open(TMPOUTPUTFILE, "> " . $pwd . "/tmp.lmn"); #tmp output
	print(TMPOUTPUTFILE $prog);
	close(TMPOUTPUTFILE);
};

sub check{
	
	$pwd = $_[0];

	$begin_line = 1;
	$end_line = 50000;
	
	$lmntal_options = "";
	
	for(@ARGV){
		if($flg_lmntal_option){
			$lmntal_options .= " " . $_;
		}else{
			if($_ =~ /b([0-9]+)/ ){
				$begin_line = $1;
			}elsif($_ =~ /e([0-9]+)/ ){
				$end_line = $1;
			}elsif($_ eq "t" ){
				$flg_translate = true;
			}elsif($_ eq "-" ){
				$flg_lmntal_option = true;
			}else{
				print ("invalid option (of check.pl) : " . $_ . "\n");
			}
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
			&tmpout($program, $pwd);
			$cmp = $lmntal_t_compiler . " " . $pwd . "/tmp.lmn" . $lmntal_options;
			$dust = `$cmp`;
			$run = $lmntal_t_runtime . " " . $pwd . "/tmp.jar" . $lmntal_options;
		}else{
#			$program =~ s/\$/\\\$/g;
			&tmpout($program, $pwd);
#			$run = $lmntal_runtime . " -e \"" . $program . "\"" . $lmntal_options;
			$run = $lmntal_runtime . " " . $pwd . "/tmp.lmn" . $lmntal_options;
		}
	
		$output = `$run`;
	
		$output =~ s/\n//;
		$output =~ s/@[0-9]+/()/g;
		print (($i+1) . " : ");
		$check_program = "{" . $output . "}, ({" . $goal . "}/:-ok)";
		&tmpout($check_program, $pwd);
		$check_run = $lmntal_runtime . " " . $pwd . "/tmp.lmn";
		$checked = `$check_run`;
	
		$result = index ($checked, "ok");
		if($flg eq "ok" && $result == 0){
			print "ok";
		}elsif($flg eq "ng" && $result != 0){
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
};

1;
