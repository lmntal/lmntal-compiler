#!/usr/bin/perl
#----------------------------------------------------------------------
# LMNtal Documentation Generator
#                                              Atsuyuki Inui
#                                              2006/01/21(土) 15:46:29
# 2006.01.22 概要を追加
#----------------------------------------------------------------------
# -使い方
#  $ ./lmndoc.pl hoge.lmn
#
# -オプション
# -- --summary 概要だけ出力する
#
# -説明
# --/** */で囲まれた部分がlmndocとして認識される
# --最初の行が見出しとなる
# --先頭の空白や*は無視される
# 
# -lmndocタグ
# --@author '''name-text'''
# --@deprecated
# --@example '''example-text'''
# --@param '''parameter-name''' '''description'''
# --@since '''since-text'''
# --@version '''version-text'''
#
# '''example-text''', '''description'''は複数行に渡って書くことが可能
use Getopt::Long;

GetOptions("summary");

$title = $ARGV[0];

$state = 0;
while (<>) {
    chop($_);
    s/^\s+//; #先頭の空白を除去
    s/^\*+//; #先頭のアスタリスクを除去
    s/^\s+//; #先頭の空白を除去

    if ($state == 0) {
	if (/\/\*\*/) { # スペシャルコメントの開始
	    $state = 1;
	    
	    $tag = ""; #現在認識中の複数行に渡るタグ

	    $comment = "";
	    $param = "";
	    @examples = "";
	    $example = 0;
	    $author = "";
	    $version = "";
	    $since = "";
	    $deprecated = 0;
	}
    } elsif ($state == 1) {
	$aname = $_;
	$aname =~ s/[\W ]+/\-/g; #アンカー名に使えない文字はハイフンに変更
	if ($opt_summary) {
	    $summary .= "-$_\n";
	} else {
	    $detail .= "***$_ &aname($aname);\n";
	    $summary .= "-[[$_>#$aname]] \n";
	}
	$state = 2;
    } elsif ($state == 2) {
	if (/\//) { # スペシャルコメントが終了(*は除去している)
	    $state = 3;
	} else {
	    if ($_ eq "") {
		next;
	    }
	    
	    # タグをチェックする
	    if (/\@example(.*)/) {
		$tag = "example";
		$example++;
		if ($1 ne "") {
		    $examples[$example] .= ">>$1\n";
		}
	    } elsif (/\@param[\s]+([\w\+\-]+)[\s]*(.*)/) {
		$tag = "param";
		$param .= ">>$1 - $2~\n";
	    } elsif (/\@author(.*)/) {
		if ($author eq "") {
		    $author = ">>$1";
		} else {
		    $author .= ", $1";
		}
	    } elsif (/\@version(.*)/) {
		$version .= ">>$1\n";
	    } elsif (/\@since(.*)/) {
		$since .= ">>$1\n";
	    } elsif (/\@deprecated(.*)/) {
		$deprecated = 1;
	    } else {
		if ($tag eq "example") {
		    $examples[$example] .= ">>$_~\n";
		} elsif ($tag eq "param") {
		    $param .= ">>$_~\n";
		} else {
		    $tag = "";
		    if ($comment eq "") { # 概要は最初の行のみ
			$summary .= "$_~\n";
		    }
		    $comment .= ">$_~\n";
		}
	    }
	}
    } elsif ($state == 3) {
	if ($deprecated) {
	    $comment = ">'''推奨されません。'''\n" . $comment;
	}

	$detail .= "$comment\n";

	# パラメータを出力
	if ($param ne "") {
	    $detail .= ">''パラメータ''\n";
	    $detail .= $param;
	}

	# 導入されたバージョンを出力
	if ($since ne "") {
	    $detail .= ">''導入されたバージョン''\n";
	    $detail .= $since;
	}
	
	# 例を出力
	if ($example == 1) {
	    $detail .= ">''例''\n";
	    $detail .= "$examples[1]";
	} elsif ($example > 1) {
	    for ($i = 1; $i <= $example; $i++) {
		$detail .= ">''例$i''\n";
		$detail .= "$examples[$i]";
	    }
	}

	# 作成者を出力
	if ($author ne "") {
	    $detail .= ">''作成者''\n";
	    $detail .= "$author\n"; #最後に改行が必要
	}
	
	# バージョンを出力
	if ($version ne "") {
	    $detail .= ">''バージョン''\n";
	    $detail .= $version;
	}

	$state = 0;
    }
}

if ($opt_summary) {
    print "$summary\n";
} else {
    print "*$title\n\n";
    print "**概要\n";
    print "$summary\n";
    print "**詳細\n";
    print $detail;
}
