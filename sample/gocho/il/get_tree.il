Compiled Ruleset @602 
Compiled Rule 
	--atommatch:
		spec           [2, 2]
	--memmatch:
		spec           [1, 1]
		jump           [L135, [0], [], []]
	--guard:L135:
		spec           [1, 1]
		jump           [L128, [0], [], []]
	--body:L128:
		spec           [1, 63]
		commit         [null, 0]
		newmem      [1, 0, 0]
		loadruleset    [0, @605]
		newatom     [2, 1, 'imnot'_0]
		newatom     [3, 1, 1_1]
		newatom     [4, 1, 'a'_1]
		newatom     [5, 0, 'b'_1]
		newatom     [6, 0, 'c'_1]
		newatom     [7, 0, 'knight'_2]
		newatom     [8, 0, 'c'_1]
		newatom     [9, 0, 'knight'_2]
		newatom    [10, 0, 'test'_1]
		newatom    [11, 0, 1_1]
		newatom    [12, 0, 'z'_1]
		newatom    [13, 0, 'knight'_2]
		newatom    [14, 0, 'ss'_1]
		newatom    [15, 0, 'ss'_2]
		newatom    [16, 0, 'knight'_2]
		newatom    [17, 0, 'i'_1]
		newatom    [18, 0, 's'_2]
		newatom    [19, 0, 's'_1]
		newatom    [20, 0, 's'_1]
		newatom    [21, 0, 's'_1]
		newatom    [22, 0, 's'_3]
		newatom    [23, 0, 's'_3]
		newatom    [24, 0, 's'_1]
		newatom    [25, 0, 'knight'_2]
		newatom    [26, 0, 'k'_0]
		newatom    [27, 0, 'l'_0]
		newatom    [28, 0, 'k'_0]
		alloclink  [29, 3, 0]
		alloclink  [30, 4, 0]
		alloclink  [31, 5, 0]
		alloclink  [32, 6, 0]
		alloclink  [33, 7, 0]
		alloclink  [34, 7, 1]
		alloclink  [35, 8, 0]
		alloclink  [36, 9, 0]
		alloclink  [37, 9, 1]
		alloclink  [38, 10, 0]
		alloclink  [39, 11, 0]
		alloclink  [40, 12, 0]
		alloclink  [41, 13, 0]
		alloclink  [42, 13, 1]
		alloclink  [43, 14, 0]
		alloclink  [44, 15, 0]
		alloclink  [45, 15, 1]
		alloclink  [46, 16, 0]
		alloclink  [47, 16, 1]
		alloclink  [48, 17, 0]
		alloclink  [49, 18, 0]
		alloclink  [50, 18, 1]
		alloclink  [51, 19, 0]
		alloclink  [52, 20, 0]
		alloclink  [53, 21, 0]
		alloclink  [54, 22, 0]
		alloclink  [55, 22, 1]
		alloclink  [56, 22, 2]
		alloclink  [57, 23, 0]
		alloclink  [58, 23, 1]
		alloclink  [59, 23, 2]
		alloclink  [60, 24, 0]
		alloclink  [61, 25, 0]
		alloclink  [62, 25, 1]
		unifylinks     [29, 30, 1]
		unifylinks     [31, 33, 0]
		unifylinks     [32, 34, 0]
		unifylinks     [35, 36, 0]
		unifylinks     [37, 38, 0]
		unifylinks     [39, 41, 0]
		unifylinks     [40, 42, 0]
		unifylinks     [43, 44, 0]
		unifylinks     [45, 46, 0]
		unifylinks     [47, 49, 0]
		unifylinks     [48, 50, 0]
		unifylinks     [51, 57, 0]
		unifylinks     [52, 54, 0]
		unifylinks     [53, 55, 0]
		unifylinks     [56, 58, 0]
		unifylinks     [59, 61, 0]
		unifylinks     [60, 62, 0]
		enqueueatom    [28]
		enqueueatom    [27]
		enqueueatom    [26]
		enqueueatom    [25]
		enqueueatom    [24]
		enqueueatom    [23]
		enqueueatom    [22]
		enqueueatom    [21]
		enqueueatom    [20]
		enqueueatom    [19]
		enqueueatom    [18]
		enqueueatom    [17]
		enqueueatom    [16]
		enqueueatom    [15]
		enqueueatom    [14]
		enqueueatom    [13]
		enqueueatom    [12]
		enqueueatom    [10]
		enqueueatom    [9]
		enqueueatom    [8]
		enqueueatom    [7]
		enqueueatom    [6]
		enqueueatom    [5]
		enqueueatom    [4]
		enqueueatom    [2]
		proceed        []


Compiled Ruleset @601 
Compiled Rule 
	--atommatch:
		spec       	   [2, 4]
	--memmatch:
		spec           [1, 40]
		
		anymem        [1, 0, 0, "^^"]
		not[[
			natoms				[0, 0]
			proceed     	[]
		]]
		branch[[
			findatom       [3, 0, 'knight'_2]
			newstack       [2]
			derefatom      [4, 3, 0]
			derefatom      [5, 3, 1]
			push           [2, 5]
			push           [2, 4]
			loop[[
				pop            [6, 2]
				branch[[
				print["des"]
					narity 				[6, 3]
				print["dd?"]
					derefatom     [4, 6, 0]
					derefatom     [5, 6, 1]
					push          [2, 5]
					push          [2, 4]
					proceed				[]
				]]
				branch[[
					narity        [6, 1]
					proceed				[]
				]]
				//else break;（arityが1or3でないとき）proceed命令を打たないので、loopを抜ける。
			]]
			narity         [6, 1] //ループを抜けるのは、popできない場合と、arityが1or3でない場合があるため
print["matching ok"]
			commit      ["get tree", 0]
			
			//    ground1              ground
			//     |0       ->copy->     |
			//    ③1--ground2          16-ground
			getlink			[ 4,  3,  0]//for removeground1
			getlink			[ 6,  3,  0]//for insertconnectorsinnull1
			getlink			[ 7,  3,  1]//for removeground2
			getlink			[ 9,  3,  1]//for insertconnectorsinnull2
			newlist			[10]     //for removeground1
			newlist     [12]     //for removeground2
			addtolist   [10,  4]
			addtolist   [12,  7]
			removeatom  [ 3,  0]
	print["1"]
			removeground[10,  0]
			removeground[12,  0]
	print["2"]
			removeproxies[1]
			insertconnectorsinnull[14, [6]]
			insertconnectorsinnull[15, [9]]
			getlink			[ 5,  3,  0]//for   copyground1
			getlink			[ 8,  3,  1]//for   copyground2
			newlist     [11]     //for   copyground1
			newlist     [13]     //for   copyground2
			addtolist   [11,  5]
			addtolist   [13,  8]
			enqueuemem  [ 1]
  		insertproxies[0, 1]
			copyatom    [16,  1, 3]
			copyground  [18, 11, 1]
			copyground  [19, 13, 1]
			getfromlist [20, 18, 0]
			getfromlist	[21, 20, 0]//for unify1
			getfromlist	[22, 18, 1]//for deleteconnectors1
			getfromlist	[23, 19, 0]
			getfromlist	[24, 23, 0]//for unify2
			getfromlist	[25, 19, 1]//for deleteconnectors2
			alloclink   [26, 16, 0]
			alloclink   [27, 16, 1]
			unifylinks  [21, 26, 1]
			unifylinks  [24, 27, 1]
			deleteconnectors[14, 22]
			deleteconnectors[15, 25]
			freeground  [11]
			freeground  [13]


			freeatom    [3]
			unlockmem   [1]
			proceed     []
			
		]]

//最後に、アトムセットの消去
		branch[[
			commit        ["clear!atomset", 0]
			clearatomset 	[0]
			unlockmem     [1]
			proceed  	   	[]
		]]
		unlockmem     [1]
		proceed 			[]

//atomがないときは、終了処理
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
