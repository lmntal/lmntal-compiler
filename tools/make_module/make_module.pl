#!/usr/bin/perl
#
###############################################################
#
# javap -public �ν��Ϥ��� LMNtal �⥸�塼���ư�������ޤ�
#
# ������
# $ javap -public java.lang.Math | ./make_module.pl > math.lmn
#
# History
#
# 2006/06/29(Thu)
# 	��������С��ǻϤޤ�᥽�å�̾���б�����
# 	$ ���ޤޤ�륯�饹̾���б�����
# 	�㳰��ȯ��������� Exception ���֥������Ȥ��֤��褦�ˤ���
# 2006/07/05(Wed)
#   ���饹̾����ʸ���ˤ���
###############################################################

# java �η� => Functor
%functors = (
	"int"				=> "IntegerFunctor",
	"long"				=> "IntegerFunctor",
	"float"				=> "FloatingFunctor",
	"double"			=> "FloatingFunctor",
	"boolean"			=> "Functor",
	"java.lang.String"	=> "StringFunctor",
);

# java �ѿ��η� => �����ѿ����������ե��󥯥��Υ᥽�å�
%getmethods = (
	"int"				=> "intValue()",
	"long"				=> "intValue()",
	"float"				=> "floatValue()",
	"double"			=> "floatValue()",
	"boolean"			=> "getName().equals(\"false\")?false:true",
	"java.lang.String"	=> "stringValue()",
);

# java �η� => ����������
%guards = (
	"int"				=> "int",
	"long"				=> "int",
	"float"				=> "float",
	"double"			=> "float",
#	"boolean"			=> "boolean",
	"java.lang.String"	=> "string",
);

# java ������ͷ� => ��̤��֤����ȥ��Ѥ� Functor
%result_functors = (
	"int"				=> "IntegerFunctor(r)",
	"long"				=> "IntegerFunctor((int)r)",
	"float"				=> "FloatingFunctor(r)",
	"double"			=> "FloatingFunctor(r)",
	"boolean"			=> "Functor(r?\"true\":\"false\", 1, null)",
	"void"				=> "Functor(\"done\", 1, null)",
	"java.lang.String"	=> "StringFunctor(r)",
);

# �ᥤ��
$time = localtime(time);# ���ä����ѿ��ˤȤ�ʤ��Ȥ��ޤ�ɽ������ʤ�
print "//----------------------------------------------------\n";
print "// The following code was generated by make_module.pl\n";
print "// ", $time, "\n";
print "//----------------------------------------------------\n";
while (<>) {
	# ���󥹥ȥ饯����static �᥽�åɡ����󥹥��󥹥᥽�åɤ� 3 ���ब����

	if (/(abstract )?class ([\w.]+)\.(\w+)/) {
		$abstract = $1; # ��ݥ��饹�ξ�� undef �ʳ����ͤ�����
		$class = $3;
		$absolute_class = "$2.$3";
		$module = $absolute_class; # �ѥå�����̾��Ȥꤿ���Ȥ��� $class �ˤ���
		$module =~ tr/./_/;
		print "{module($module).\n";
	} elsif (/compareTo\(java\.lang\.Object\)/) {
		# Comparable ���󥿥ե������Υ᥽�åɤ�̵��
	} elsif (/char|byte|short|float/) {
		#�Ȥꤢ����̵��
	} elsif (/public [\w.\$]+\((\S*)\)/ && $abstract eq "") {# ��ݥ��饹�ϥ��󥹥ȥ饯���ʤ�
		dump_constructor(split_args($1));
	} elsif (/public static (?:synchronized )?(\S+) (\S+)\((.*)\)/) {
		# ����ͤ�long�ΤȤ��Ͻ���
		if (!($3 =~ /\[\]/) && $1 ne "long") { #TODO Java�������LMNtal�Υꥹ�Ȥǽ���
			dump_static_method(trim_class($1), $2, split_args($3));
		}
	} elsif (/public (?:synchronized )?(\S+) (\S+)\((.*)\)/) {
		# ����ͤ�long�ΤȤ��Ͻ���
		if (!($3 =~ /\[\]/) && $1 ne "long") { #TODO Java�������LMNtal�Υꥹ�Ȥǽ���
			dump_method(trim_class($1), $2, split_args($3));
		}
	}
}
print "}.\n";

# �����������Ÿ������
sub split_args {
	my ($args) = @_;
	my @args = split(/\s*,\s*/, trim_class($args));
	return @args;
}

# ���饹̾����������������
sub trim_class {
	my ($class) = @_;
	$class =~ s/\$/\./g;
	return $class;
}

# �����ɤ���Ϥ���
sub dump_guards {
	@args = @_;
	my $guards = "";
	for ($i = 0; $i <= $#args; $i++) {
		$arg = $args[$i];
		if ($arg eq "boolean") {
			next;
		}
		if (exists($guards{$arg})) {
			$guards .= "$guards{$arg}(Arg$i),";
		} else {
			$guards .= "class(Arg$i, \"$arg\"),";
		}
	}
	chop($guards); #�Ǹ�Υ���ޤ����
	return $guards;
}

# �إå���ʬ����Ϥ���
sub dump_head {
	my ($args, $method, @args) = @_;
	print "H=$module.$method($args) :- ";
	my $guards = dump_guards(@args);
	if ($guards ne "") {
		print "$guards | ";
	}
	print "H=[:/*inline*/\n";
}

# ���󥹥ȥ饯������Ϥ���
sub dump_constructor {
	@args = @_;
	$argc = $#args+1;

	$ARGS=make_lmntal_args($argc);
	dump_head($ARGS, "new", @args);

	$args = dump_args(0, @args);
	print "\ttry {\n";
	print "\t\tAtom o = mem.newAtom(new ObjectFunctor(new $absolute_class($args)));\n";
	print "\t\tmem.relink(o, 0, me, $argc);\n";
	print "\t} catch (Exception e) { System.err.println(e); }\n";

	for ($i = 0; $i < $argc; $i++) {
		print "\tme.nthAtom($i).remove();\n";
	}
	print "\tme.remove();\n";
	print "\t:]($ARGS).\n";
	print "\n";
}

# java �Υ᥽�å�̾���� LMNtal �ѤΥ᥽�å�̾����������
sub lmnmethod {
	my ($method) = @_;
	$method =~ s/^_//; # ��Ƭ�� _ ����
	return lcfirst($method); # ��Ƭ��ʸ���ˤ����֤�
}

# �᥽�åɤ���Ϥ���
sub dump_method {
	my ($type, $method, @args) = @_;
	$argc = $#args+1;

	$ARGS = make_lmntal_args($argc+1);

	dump_head("${ARGS}", lmnmethod($method), ($absolute_class,@args));

#	print "class($class, \"$absolute_class\") | H=[:/*inline*/\n";
	print "\t$absolute_class o = ($absolute_class) ((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\n";
	
	$args = dump_args(1, @args);

	printf "\tmem.relink(me.nthAtom(0), 0, me, %d);\n", $argc+1;
	printf "\tAtom result = null;\n";
	printf "\ttry {\n";
	if ($type eq "void") {
		printf "\t\to.$method($args);\n";
	} else {
		printf "\t\t$type r = o.$method($args);\n";
	}
	printf "\t\t%s", dump_result_atom($type);
	printf "\t} catch (Exception e) {\n";
	printf "\t\tresult = mem.newAtom(new ObjectFunctor(e));\n";
	printf "\t} finally {\n";
	printf "\t\tmem.relink(result, 0, me, %d);\n", $argc+1;
	printf "\t}\n";

	dump_tail($argc+1, "${ARGS}");
}

# static �᥽�åɤ���Ϥ���
sub dump_static_method {
	($type, $method, @args) = @_;
	$argc = $#args+1;

	$ARGS = make_lmntal_args($argc);

	dump_head(${ARGS}, lmnmethod($method), @args);

	$args = dump_args(0, @args);
	
	print "\tAtom result = null;\n";
	print "\ttry {\n";
	if ($type eq "void") {
		print "\t\t$absolute_class.$method($args);\n";
	} else {
		print "\t\t$type r = $absolute_class.$method($args);\n";
	}
	printf "\t\t%s", dump_result_atom($type);
	print "\t} catch (Exception e) {\n";
	print "\t\tresult = mem.newAtom(new ObjectFunctor(e));\n";
	print "\t} finally {\n";
	print "\t\tmem.relink(result, 0, me, $argc);\n";
	print "\t}\n";

	dump_tail($argc, $ARGS);
}

# �Ǹ�ν���
sub dump_tail {
	my ($argc, $args) = @_;
	for ($i = $argc-1; $i >= 0; $i--) {
		print "\tme.nthAtom($i).remove();\n";
	}
	print "\tme.remove();\n";
	print "\t:]($args).\n";
	print "\n";
}

# LMNtal �ΰ�����ʸ�������������
sub make_lmntal_args {
	my ($argc) = @_;
	my $args = "";
	for ($i = 0; $i < $argc; $i++) {
		$args .= "Arg$i,";
	}
	chop($args); #�Ǹ�Υ���ޤ����
	return $args;
}

# �����������������ν���
sub dump_args {
	my ($start, @args) = @_;
	my $argc = $#args;
	my $args = "";
	for ($i = 0; $i <= $argc; $i++) {
		my $type = $args[$i];
		$type =~ s/\s//;
		$args .= "v$i,";
		if (exists($functors{$type})) {
			$functor = $functors{$type};
			$cast = "";
			$getmethod = $getmethods{$type};
		} else {
			$functor = "ObjectFunctor";
			$cast = "($type) ";
			$getmethod = "getValue()";
		}
		printf "\t$type v$i = $cast(($functor)me.nthAtom(%d).getFunctor()).$getmethod;\n", $i+$start;
	}
	chop($args); #�Ǹ�Υ���ޤ����
	return $args;
}

# ��̤��֤����ȥ��������������ν���
sub dump_result_atom {
	my ($type) = @_;

	if (exists($result_functors{$type})) {
		$functor = $result_functors{$type};
	} else {
		$functor = "ObjectFunctor(r)";
	}
	return "result = mem.newAtom(new $functor);\n";
}