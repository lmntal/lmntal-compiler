print "\tSystem.out.println(\"\"\n";
while(<>) {
	next unless m|/// ([^\n\r]*)|;
	$v = $1; #chomp $v;
	print "\t\t".'+"';
	print "                " unless $v =~ /^\-/;
	print "    ".$v .'\n"'."\n";
}
print "\t\t);\n";
