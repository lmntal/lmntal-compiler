#!/usr/bin/perl
# allclasses-frame.html からクラスまたはインタフェースを抜き出す
#
# 使用例(1) クラスを抽出
# ./extract_all.pl allclasses-frame.html
#
# 使用例(2) インタフェースを抽出
# ./extract_all.pl -i allclasses-frame.html

$type = "class";

# オプションを解析する
use Getopt::Std;
my $opt = {};
getopts('i', $opt);
$i = $opt->{'i'};
if ($i ne "") {
	$type = "interface";
}
 
while (<>) {
	if (/title="$type in/ && /HREF="(.+)\/(\w+).html"/) {
		$class = "$1/$2";
		$class =~ tr/\//./;
		print "$class\n";
	}
}
