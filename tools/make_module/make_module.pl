#!/usr/bin/perl
# javap -public Hoge の出力から LMNtal モジュールを自動生成します

# java の型 => Functor
%functors = (
	"int"				=> "IntegerFunctor",
	"long"				=> "IntegerFunctor",
	"float"				=> "FloatingFunctor",
	"double"			=> "FloatingFunctor",
	"boolean"			=> "Functor",
	"java.lang.String"	=> "StringFunctor",
);

# java の型 => それを取得するファンクタのメソッド
%getmethods = (
	"int"				=> "intValue()",
	"long"				=> "intValue()",
	"float"				=> "floatValue()",
	"double"			=> "floatValue()",
	"boolean"			=> "getName().equals(\"false\")?false:true",
	"java.lang.String"	=> "stringValue()",
);

# java の型 => ガード制約
%guards = (
	"int"				=> "int",
	"long"				=> "int",
	"float"				=> "float",
	"double"			=> "float",
#	"boolean"			=> "boolean",
	"java.lang.String"	=> "string",
);

# java の戻り値型 => 結果を返すアトム用の Functor
%result_functors = (
	"int"				=> "IntegerFunctor(r)",
	"long"				=> "IntegerFunctor((int)r)",
	"float"				=> "FloatingFunctor(r)",
	"double"			=> "FloatingFunctor(r)",
	"boolean"			=> "Functor(r?\"true\":\"false\", 1, null)",
	"void"				=> "Functor(\"done\", 1, null)",
	"java.lang.String"	=> "StringFunctor(r)",
);

# メイン
$time = localtime(time);
print "//", $time, "\n\n";
while (<>) {
	# コンストラクタ，static メソッド，インスタンスメソッドの 3 種類がある

	if (/Compiled from "(\w+)\.java"/) {
		$class = $1;
#		$module = lc($class);
#		print "{module($module).\n";
	} elsif (/(abstract )?class (\S+)/) {
		$abstract = $1;
		$absolute_class = $2;
		$module = lc($absolute_class);
		$module =~ tr/./_/;
		print "{module($module).\n";
	} elsif (/compareTo\(java\.lang\.Object\)/) {
		# Comparable インタフェースのメソッドは無視
	} elsif (/public java\.\S+\((\S*)\)/ && $abstract eq "") {# 抽象クラスはコンストラクタなし
		@args = split(/\s*,\s*/, $1);
		dump_constructor(@args);
	} elsif (/(byte)|(short)|(float)/) {
		#とりあえず無視
	} elsif (/public static (?:synchronized )?(\S+) (\S+)\((.*)\)/) {
		# 戻り値がlongのときは除外
		if (!($3 =~ /\[\]/) && $1 ne "long") { #TODO Javaの配列をLMNtalのリストで処理
			@args = split(/\s*,\s*/, $3);
			dump_static_method($1, $2, @args);
		}
	} elsif (/public (?:synchronized )?(\S+) (\S+)\((.*)\)/) {
		# 戻り値がlongのときは除外
		if (!($3 =~ /\[\]/) && $1 ne "long") { #TODO Javaの配列をLMNtalのリストで処理
			@args = split(/\s*,\s*/, $3);
			dump_method($1, $2, @args);
		}
	}
}
print "}.\n";

# ガードを出力する
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
	chop($guards); #最後のカンマを除去
	return $guards;
}

# ヘッド部分を出力する
sub dump_head {
	my ($args, $method, @args) = @_;
	print "H=$module.$method($args) :- ";
	my $guards = dump_guards(@args);
	if ($guards ne "") {
		print "$guards | ";
	}
	print "H=[:/*inline*/\n";
}

# コンストラクタを出力する
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

# メソッドを出力する
sub dump_method {
	my ($type, $method, @args) = @_;
	$argc = $#args+1;

	$ARGS = make_lmntal_args($argc+1);

	dump_head("${ARGS}", lcfirst($method), ($absolute_class,@args));

#	print "class($class, \"$absolute_class\") | H=[:/*inline*/\n";
	print "\t$absolute_class o = ($absolute_class) ((ObjectFunctor)me.nthAtom(0).getFunctor()).getObject();\n";
	
	$args = dump_args(1, @args);

	printf "\ttry {\n";
	if ($type eq "void") {
		printf "\t\to.$method($args);\n";
	} else {
		printf "\t\t$type r = o.$method($args);\n";
	}
	printf "\t\tmem.relink(me.nthAtom(0), 0, me, %d);\n", $argc+1;
	printf "\t\t%s", dump_result_atom($type);
	printf "\t\tmem.relink(result, 0, me, %d);\n", $argc+1;
	printf "\t} catch (Exception e) { System.err.println(e); }\n";

	dump_tail($argc+1, "${ARGS}");
}

# static メソッドを出力する
sub dump_static_method {
	($type, $method, @args) = @_;
	$argc = $#args+1;

	$ARGS = make_lmntal_args($argc);

	dump_head(${ARGS}, lcfirst($method), @args);

	$args = dump_args(0, @args);

	print "\ttry {\n";
	if ($type eq "void") {
		print "\t\t$absolute_class.$method($args);\n";
	} else {
		print "\t\t$type r = $absolute_class.$method($args);\n";
	}
	print "\t\tmem.relink(me.nthAtom(0), 0, me, $argc);\n";
	print "\t\t", dump_result_atom($type);
	print "\t\tmem.relink(result, 0, me, $argc);\n";
	print "\t} catch (Exception e) { System.err.println(e); }\n";

	dump_tail($argc, $ARGS);
}

# 最後の出力
sub dump_tail {
	my ($argc, $args) = @_;
	for ($i = $argc-1; $i >= 0; $i--) {
		print "\tme.nthAtom($i).remove();\n";
	}
	print "\tme.remove();\n";
	print "\t:]($args).\n";
	print "\n";
}

# LMNtal の引数の文字列を生成する
sub make_lmntal_args {
	my ($argc) = @_;
	my $args = "";
	for ($i = 0; $i < $argc; $i++) {
		$args .= "Arg$i,";
	}
	chop($args); #最後のカンマを除去
	return $args;
}

# 引数を取得する処理の出力
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
	chop($args); #最後のカンマを除去
	return $args;
}

# 結果を返すアトムを生成する処理の出力
sub dump_result_atom {
	my ($type) = @_;

	if (exists($result_functors{$type})) {
		$functor = $result_functors{$type};
	} else {
		$functor = "ObjectFunctor(r)";
	}
	return "Atom result = mem.newAtom(new $functor);\n";
}
