Compiled Ruleset @600 
Compiled Rule 
	--atommatch:
		spec           [2, 2]
	--memmatch:
		spec           [1, 1]
		jump           [L159, [0], [], []]
	--guard:L159:
		spec           [1, 1]
		jump           [L152, [0], [], []]
	--body:L152:
		spec           [1, 73]
		commit         [null, 0]
		newmem      [1, 0, 0]
		setmemname     [1, "mem1"]
		newmem      [2, 0, 0]
		setmemname     [2, "mem2"]
		newmem      [3, 0, 0]
		setmemname     [3, "mem3"]
		loadruleset    [0, @605]
		newatom     [4, 1, '+'_1]
		newatom     [5, 1, $in_2]
		newatom     [6, 2, '+'_1]
		newatom     [7, 2, $in_2]
		newatom     [8, 3, 'world'_1]
		newatom     [9, 3, 'hello'_2]
		newatom    [10, 3, 'test'_0]
		newatom    [11, 3, 'mutex'_1]
		newatom    [12, 3, $in_2]
		newatom    [13, 3, $in_2]
		newatom    [14, 0, 'list'_1]
		newatom    [15, 0, 'a'_2]
		newatom    [16, 0, 1_1]
		newatom    [17, 0, 'b'_3]
		newatom    [18, 0, 'c'_2]
		newatom    [19, 0, '[]'_1]
		newatom    [20, 0, '.'_3]
		newatom    [21, 0, '.'_3]
		newatom    [22, 0, '.'_3]
		newatom    [23, 0, 'mutex'_2]
		newatom    [24, 0, 'task'_1]
		newatom    [25, 0, $out_2]
		newatom    [26, 0, $out_2]
		newatom    [27, 0, $out_2]
		newatom    [28, 0, $out_2]
		alloclink  [29, 4, 0]
		alloclink  [30, 5, 0]
		alloclink  [31, 5, 1]
		alloclink  [32, 6, 0]
		alloclink  [33, 7, 0]
		alloclink  [34, 7, 1]
		alloclink  [35, 8, 0]
		alloclink  [36, 9, 0]
		alloclink  [37, 9, 1]
		alloclink  [38, 11, 0]
		alloclink  [39, 12, 0]
		alloclink  [40, 12, 1]
		alloclink  [41, 13, 0]
		alloclink  [42, 13, 1]
		alloclink  [43, 14, 0]
		alloclink  [44, 15, 0]
		alloclink  [45, 15, 1]
		alloclink  [46, 16, 0]
		alloclink  [47, 17, 0]
		alloclink  [48, 17, 1]
		alloclink  [49, 17, 2]
		alloclink  [50, 18, 0]
		alloclink  [51, 18, 1]
		alloclink  [52, 19, 0]
		alloclink  [53, 20, 0]
		alloclink  [54, 20, 1]
		alloclink  [55, 20, 2]
		alloclink  [56, 21, 0]
		alloclink  [57, 21, 1]
		alloclink  [58, 21, 2]
		alloclink  [59, 22, 0]
		alloclink  [60, 22, 1]
		alloclink  [61, 22, 2]
		alloclink  [62, 23, 0]
		alloclink  [63, 23, 1]
		alloclink  [64, 24, 0]
		alloclink  [65, 25, 0]
		alloclink  [66, 25, 1]
		alloclink  [67, 26, 0]
		alloclink  [68, 26, 1]
		alloclink  [69, 27, 0]
		alloclink  [70, 27, 1]
		alloclink  [71, 28, 0]
		alloclink  [72, 28, 1]
		unifylinks     [29, 31, 1]
		unifylinks     [32, 34, 2]
		unifylinks     [35, 37, 3]
		unifylinks     [36, 40, 3]
		unifylinks     [38, 42, 3]
		unifylinks     [43, 61, 0]
		unifylinks     [44, 66, 0]
		unifylinks     [45, 59, 0]
		unifylinks     [46, 47, 0]
		unifylinks     [48, 68, 0]
		unifylinks     [49, 56, 0]
		unifylinks     [50, 70, 0]
		unifylinks     [51, 53, 0]
		unifylinks     [52, 54, 0]
		unifylinks     [55, 57, 0]
		unifylinks     [58, 60, 0]
		unifylinks     [62, 72, 0]
		unifylinks     [63, 64, 0]
		unifylinks     [65, 30, 0]
		unifylinks     [67, 33, 0]
		unifylinks     [69, 39, 0]
		unifylinks     [71, 41, 0]
		enqueueatom    [24]
		enqueueatom    [23]
		enqueueatom    [18]
		enqueueatom    [17]
		enqueueatom    [15]
		enqueueatom    [14]
		enqueueatom    [11]
		enqueueatom    [10]
		enqueueatom    [9]
		enqueueatom    [8]
		enqueueatom    [6]
		enqueueatom    [4]
		proceed        []


/* マッチしたアトムの移動＆マッチ以外のアトムの抹消 */
Compiled Ruleset @601
Compiled Rule 
	--atommatch:
		spec          [2, 3]
	--memmatch:
		spec          [1, 25]
		anymem        [1, 0, 0, "^^"]
		not[[
			natoms				[0, 0]
			proceed     	[]
		]]
//接続先に自由リンクアトムが含まれる場合
		branch[[
			findatom    [3, 0, '$out'_2]
			lockmem     [2, 3, null]
			findatom    [4, 0, '.'_3]
			getlink     [5, 3, 1]
			getlink     [6, 4, 0]
			newlist     [7]
			newlist     [8]
//outの接続先膜をlockmemで取得する. lockmemに膜名を指定しても、slimは読み込まないので適当でok.
			
			unlockmem   [1]
			proceed			[]
		]]
//長さ1以上のリスト用
		branch[[
	    /*   (ground)⑤
  	               ｜    
    	       ④0--2②1--③･･･  */
			//このbranch命令中の失敗はここまでバックトラック。ここが失敗のときのみ、次のbranch命令へ移る。
			findatom    [2, 0, '.'_3]
			derefatom   [4, 2, 2]
			isunary     [4]
			getlink     [5, 2, 0]
			getlink     [6, 2, 1]
			getlink     [7, 2, 2]
  		newlist     [8] //for linklist
  		newlist     [9] //for avolist
   		addtolist   [8, 5]
  		addtolist   [9, 6]
  		addtolist   [9, 7]
  		isground    [10, 8, 9]
  		addtolist   [9, 8]
			derefatom   [3, 2, 1]
			//以降のループで、9をavolistとして扱う。
			loop[[
    	//              (list)11
    	//         |          ｜    
    	//     ---2②1-------2③1----
  			neqatom     [3, 2]
				func        [3, '.'_3] //③がtailに到達したら価数は1なのでbreakする
				getlink     [11, 3, 0] 
				newlist     [12] //for linklist
				addtolist   [12, 11]
				isground    [13, 12, 9]
				derefatom   [3, 3, 1]//一つ右のアトムへ移動
				addtolist   [9, 12]
				proceed     []
			]]
			isunary     [3]
			commit      ["list_size1以上", 0]

			derefatom   [3, 2, 2]//head
			getlink     [4, 2, 0]//for romoveground
			getlink     [5, 2, 0]//for insertconnectorsinnull
			newlist     [6]
			addtolist   [6, 4]
			removeatom  [2, 0]
			removeatom  [3, 0]
			removeground[6, 0]
			removeproxies[1]
			insertconnectorsinnull[7, [5]] //for copyground
			getlink     [8, 2, 0]
			newlist     [9]
			addtolist   [9, 8]
			enqueuemem  [1]
			insertproxies[0, 1]
			copyatom    [10, 1, 2]
			copyatom    [11, 1, 3]
			copyground  [12, 9, 1]
			getfromlist [13, 12, 0]
			getfromlist [14, 13, 0]//for unify
			getfromlist [15, 12, 1]//for deleteconnectors
			alloclink   [16, 10, 0]
			unifylinks  [16, 14, 1]
			deleteconnectors[7, 15]
			freeground  [9]
			newlink     [10, 2, 11, 0, 1]
			derefatom   [17, 2, 1]
			
			loop[[
				func   	    [17, '.'_3]
				getlink 	  [18, 17, 0]
				getlink     [19, 17, 0]
				newlist     [20]
				addtolist   [20, 18]
				removeatom	[17, 0]
				removeground[20, 0]
				removeproxies[1]
				insertconnectorsinnull[21, [19]]
				getlink     [22, 17, 0]
				newlist     [23]
				addtolist   [23, 22]
				enqueuemem  [1]
				insertproxies[0, 1]
				copyatom 	 	[24, 1, 16]
				copyground  [25, 23, 1]
				getfromlist [26, 25, 0]
				getfromlist [27, 26, 0]//for unify
				getfromlist [28, 25, 1]//for deleteconnectors
				deleteconnectors[21, 28]
				freeground  [23]
				alloclink   [29, 24, 0]
				unifylinks  [29, 27, 1]
				newlink     [10, 1, 24, 2, 1]
				derefatom		[10, 10, 1]
				derefatom 	[17, 17, 1]
				proceed     []
			]]
			removeatom  [17, 0]
			copyatom    [30, 1, 17]
			newlink     [10, 1, 30, 0, 1]
			freeatom    [2]
			unlockmem		[1]
			proceed 		[]
		]]
		

//長さ0のリスト用
		branch[[
			findatom    	[2, 0, '[]'_1]
			derefatom   	[3, 2, 0]
			isunary       [3]
			commit        ["list_size0", 0]
			copyatom      [4, 1, 2]
			copyatom			[5, 1, 3]
			newlink       [4, 0, 5, 0, 1]
			enqueueatom   [4]
			enqueueatom   [5]
			removeatom    [2, 0]
			removeatom    [3, 0]
			freeatom      [2]
			freeatom      [3]
			unlockmem     [1]
			proceed       []
		]]

//リストが見つからないときは、アトムセットの消去
		branch[[
			commit        ["clear!atomset", 0]
			clearatomset 	[0]
			unlockmem     [1]
			proceed  	   	[]
		]]
		unlockmem     [1]
		proceed 			[]

Compiled Rule
	--atommatch:
		spec          [2, 3]
	--memmatch:
		spec          [1, 10]
		anymem        [1, 0, 0, "^^"]
		natoms				[0, 0]
		commit        ["fin", 0]
		removemem     [1, 0]
		removeproxies [1]
		movecells     [0, 1]
		removetemporaryproxies[0]
		freemem       [1]
		clearrules    [1]
		proceed       []

/* 全ての階層にルールを投げる */
Compiled Ruleset @605
Compiled Rule 
	--atommatch:
		spec          [2, 3]
	--memmatch:
		spec          [1, 2]
		anymem      	[1, 0, 0, "*"]
		not[[
			eqmemname     [1, "^^"]
			proceed       []
		]]
		norules     	[1]
		commit        ["rulecopy", 0]
		removeproxies [1]
		enqueuemem    [1]
		insertproxies [0, 1]
		copyrules     [1, 0]
		unlockmem     [1]
		proceed       []

Compiled Rule
	--atommatch:
		spec          [2, 3]
	--memmatch:
		spec          [1, 2]
		not[[
			anymem       	[1, 0, 0, "^^"]
			proceed			 	[]
		]]
		commit        ["mem make", 0]
		newmem 				[1, 0, 0]
		setmemname    [1, "^^"]
		enqueuemem    [1]
		insertproxies [0, 1]
		clearrules    [0]
		loadruleset   [0, @601]
		proceed       []

Inline