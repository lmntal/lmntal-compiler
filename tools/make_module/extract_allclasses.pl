#!/usr/bin/perl
# allclasses-frame.html から クラス名を抜き出す

while (<>) {
	if (/title="class in/ && /HREF="(.+)\/(\w+).html"/) {
		$class = "$1/$2";
		$class =~ tr/\//./;
		print "$class\n";
	}
}
