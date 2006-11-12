#!/usr/bin/perl

#モジュールを生成するディレクトリ
$dir=".";

#オプション解析
use Getopt::Std;
my $opt = {}; 
getopts('d:h',$opt);
if ($opt->{'d'}) {
	$dir=$opt->{'d'};
}
#ディレクトリが存在するかどうかチェック
if (!-d $dir) {
	die "$0: Directory '$dir' is not found.\n";
}
#クラスが指定されないか、-hオプションのときは使い方を表示する
if ($opt->{'h'} || $#ARGV == -1) {
	print STDERR "Usage: $0 [options] <classes>\n";
	print STDERR "\n";
	print STDERR "\t-d <dir>\tSet output directory\n";
	exit(0);
}

for ($i = 0; $i <= $#ARGV; $i++) {
	$class = $ARGV[$i];
	open(CLASS, "javap -public $class |");
	while (<CLASS>) {
		if (/interface ([\w.]+)\.(\w+)/) {
			$class = $2;
			$absolute_class = $module = "$1.$2";
			$module =~ tr/./_/;
			open(STDOUT, ">$dir/$module.lmn");
			print "{module($module).\n";
			print "[:/*inline_define*/\n";
			print "class LMNtal$class implements $absolute_class {\n";
			print "\tprivate Membrane mem;\n";
			print "\tpublic LMNtal$class(Membrane mem) {\n";
			print "\t\tthis.mem = mem;\n";
			print "\t}\n";
		} elsif (/public abstract void (\S+)\((.*)\)/) {
			dump_method($1, $2);
		}
	}
	close(CLASS);
	print "}\n";
	print ":].\n";
	print "\n";
	print "H=$module.new :- H=[:/*inline*/\n";
	print "	Atom o = mem.newAtom(new ObjectFunctor(new LMNtal$class(mem)));\n";
	print "	mem.relink(o, 0, me, 0);\n";
	print "	me.remove();\n";
	print "	:].\n";
	print "}.\n";
	close(STDOUT);
}

sub dump_method {
	my ($method, $arg) = @_;
	
	print "\tpublic void $method($arg e) {\n";
	print "\t\tAtom a = mem.newAtom(new SymbolFunctor(\"$method\", 1));\n";
	print "\t\tAtom b = mem.newAtom(new ObjectFunctor(e));\n";
	print "\t\tmem.newLink(a, 0, b, 0);\n";
	print "\t}\n";
}
