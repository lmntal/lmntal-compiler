#!/usr/bin/perl
#
# 使用法
# まず古い LMNtal モジュールがあるディレクトリに移動する
# $ ./copy_new_module.pl *.lmn | bash

# 新しいモジュールファイルがあるディレクトリ
$DIR = "/tmp/lib";

foreach $f (@ARGV) {
	open(DIFF, "diff $f $DIR/$f |");
	# 最初の8行は無条件に日付情報が異なるため無視
	for ($i = 0; $i < 8; $i++) {
		<DIFF>;
	}
	
	if (<DIFF>) {
		print "cp $DIR/$f .\n";
	}
	close(DIFF);
}
