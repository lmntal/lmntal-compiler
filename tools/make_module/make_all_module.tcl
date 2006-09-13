#!/usr/bin/wish

pack [frame .f1] -fill both -expand 1
pack [label .f1.l -text 生成先のディレクトリ] -side left
pack [entry .f1.e1 -width 40 -textvariable dir] -side left
button .f1.dstButton -text ... -command {
	set dir [tk_chooseDirectory]
}
pack .f1.dstButton -side left

pack [frame .f2] -fill both -expand 1
pack [label .f2.l -text 生成するクラスリスト] -side left
pack [entry .f2.e2 -width 40 -textvariable allclassesfile] -side left
button .f2.b -text ... -command {
	set allclassesfile [tk_getOpenFile]
}
pack .f2.b -side left

pack [frame .tframe] -fill both -expand 1
text .tframe.t -yscroll {.tframe.y set}
scrollbar .tframe.y -command {.tframe.t yview} -orient vertical
pack .tframe.t -side left -fill both
pack .tframe.y -side right -fill y

pack [frame .f3] -fill both -expand 1

button .f3.createButton -text 生成 -command {
	# ディレクトリとファイルが指定されているかチェックする
	if {$dir ne "" && $allclassesfile ne ""} {
		if [catch {open $allclassesfile r} fd] {
			error "can't open file '$allclassesfile'"
		}
		# 1行ずつクラスを読み込んでモジュールを生成
		while {[gets $fd class] >= 0} {
			.tframe.t insert end "$i\n"
			update
			set module [exec echo $class | tr A-Z a-z | tr . _]
			exec javap -public $class | perl ./make_module.pl > $dir/$module.lmn
		}
		close $fd
	}
}
pack .f3.createButton -side left

pack [button .f3.exitButton -text 終了 -command exit] -side left

