=whatis

----------------------------------------------------------------------
LMNtal Trace Viewer
                                              Koji Hara
                                              2005/10/19(水) 18:25:29
----------------------------------------------------------------------
◆使い方：

この算譜は、-t と -x dump 1 オプションをつけた LMNtal 処理系の出力を標準入力から受け取り、HTML 化して標準出力に出力するものである。

◆オプション

$ perl tohtml.pl [表示しないルール名の正規表現]... +[表示するルール名の正規表現]...

表示するルール名が優先される。並びは順不同。

◆コマンドライン例

$ java -cp bin runtime/FrontEnd -t -x dump 1 --interpret sample/hara/chr.lmn | perl tohtml.pl .* +introduce +solve +prop +simp > result.html

意味：
ルール名が introduce, solve, prop, simp にマッチしたもの（の前後）は表示する。それ以外(.*)は表示しない

----------------------------------------------------------------------



=cut
use Data::Dumper;

map{ push @n, $_; } grep { /^[^+]/ } @ARGV;
map{ s/^\+//; push @y, $_; } grep { /^\+/ } @ARGV;

$all = join "", <STDIN>;
@steps = split /\-\-\-\-+/, $all;
shift @steps;

#print Dumper({@y}, {@n});
$total = ($#steps+1)/2;




print <<END;
<html>
<head>
<title>LMNtal Trace Viewer - @{[scalar localtime]}</title>
<script>
function sw(n) {
	set(n, o(n).style.display=='none');
}

function set(n, v) {
	o(n).style.display = v ? 'block' : 'none';
}
function o(n) {return document.getElementById(n);}

</script>
</head>
<body style="font-family: monospace">
END




$co = 0;
for($i=0;$i<$#steps+1;$i+=2,$co++) {
	$rule = $steps[$i];
	$rule =~ s/^\s*@(.*?)[\s\r\n]*$/$1/;
	$dump = $steps[$i+1];
	
	$dump =~ s/^[\r\n]+//;
	$dump =~ s/([\r\n]+)/<br>$1/g;
	$dump =~ s/ /&nbsp;/g;
=memo
67,:-
49,[$@][a-zA-Z0-9]+(\[[^\]]*\])?
67,|
177,\[[^\]]*\]
241,unary|ground|int|float|string|class|module|use|inline|inline_define
=cut
	$dump =~ s|(:\-)|<b><font color=red>$1</font></b>|g;
	$dump =~ s|([\|])|<b><font color=red>$1</font></b>|g;
	$dump =~ s|(\[[^\]]*\])|<b><font color="blue">$1</font></b>|g;
	$dump =~ s|([\$@][a-zA-Z0-9]+(\[[^\]]*\])?)|<font color="#ce420d">$1</font>|g;
	$dump =~ s*\b(unary|ground|int|float|string|class|module|[a-zA-Z0-9]+\.use|inline|inline_define)\b*<b><font color="#990099">$1</font></b>*g;
	$dump =~ s|(_\d+)|<font color="#26CAC1">$1</font>|g;
	
	
	$show = display($rule);
	
	print <<END;
<div style="background-color: #aaaaff; cursor: pointer;" onClick="sw('$co');">
$co : <b>@ $rule</b>
</div>
<div id="$co" style="display:$show">
$dump
</div>
END
}






print <<END;
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

<hr>
<i>
LMNtal trace viewr by Koji Hara<br>
@{[scalar localtime]}
</i>

<script>
for(var i=1;i<$total;i++) {
	if(o(i).style.display!='none') set(i-1, 1);
}
set(0, 1);
set($total-1, 1);
</script>
</body>
</html>
END





sub display {
	my $r=shift;
	my $a=1;
	for(@n) {
		if($r =~ /$_/) {
			$a=0; last;
		}
	}
	for(@y) {
		if($r =~ /$_/) {
			$a=1; last;
		}
	}
	$a ? "block" : "none";
}
