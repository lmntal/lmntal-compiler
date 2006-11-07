#!/usr/bin/perl

while (<>) {
	if (/interface ([\w.]+)\.(\w+)/) {
		$class = $2;
		$absolute_class = $module = "$1.$2";
		$module =~ tr/./_/;
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

print "}\n";
print ":].\n";
print "\n";
print "H=$module.new :- H=[:/*inline*/\n";
print "	Atom o = mem.newAtom(new ObjectFunctor(new LMNtal$class(mem)));\n";
print "	mem.relink(o, 0, me, 0);\n";
print "	me.remove();\n";
print "	:].\n";
print "}.";

sub dump_method {
	my ($method, $arg) = @_;
	
	print "\tpublic void $method($arg e) {\n";
	print "\t\tAtom a = mem.newAtom(new SymbolFunctor(\"$method\", 1));\n";
	print "\t\tAtom b = mem.newAtom(new ObjectFunctor(e));\n";
	print "\t\tmem.newLink(a, 0, b, 0);\n";
	print "\t}\n";
}
