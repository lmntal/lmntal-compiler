/* 全ての階層のintに繋がってる環状リスト以外を抹消する */

Compiled Ruleset @600
Compiled Rule 
	--atommatch:
		spec           [2, 2]
	--memmatch:
		spec           [1, 1]
		jump           [L127, [0], [], []]
	--guard:L127:
		spec           [1, 1]
		jump           [L120, [0], [], []]
	--body:L120:
		spec           [1, 42]
		commit         [null, 0]
		newmem      [1, 0, 0]
		newmem      [2, 0, 0]
		loadruleset    [0, @605]
		newatom     [3, 1, '+'_1]
		newatom     [4, 1, 'd'_0]
		newatom     [5, 1, '+'_1]
		newatom     [6, 1, $in_2]
		newatom     [7, 1, $in_2]
		newatom     [8, 2, 'a'_1]
		newatom     [9, 2, 's'_2]
		newatom    [10, 2, 's'_1]
		newatom    [11, 2, $in_2]
		newatom    [12, 2, $in_2]
		newatom    [13, 0, 'a'_2]
		newatom    [14, 0, $out_2]
		newatom    [15, 0, $out_2]
		newatom    [16, 0, $out_2]
		newatom    [17, 0, $out_2]
		alloclink  [18, 3, 0]
		alloclink  [19, 5, 0]
		alloclink  [20, 6, 0]
		alloclink  [21, 6, 1]
		alloclink  [22, 7, 0]
		alloclink  [23, 7, 1]
		alloclink  [24, 8, 0]
		alloclink  [25, 9, 0]
		alloclink  [26, 9, 1]
		alloclink  [27, 10, 0]
		alloclink  [28, 11, 0]
		alloclink  [29, 11, 1]
		alloclink  [30, 12, 0]
		alloclink  [31, 12, 1]
		alloclink  [32, 13, 0]
		alloclink  [33, 13, 1]
		alloclink  [34, 14, 0]
		alloclink  [35, 14, 1]
		alloclink  [36, 15, 0]
		alloclink  [37, 15, 1]
		alloclink  [38, 16, 0]
		alloclink  [39, 16, 1]
		alloclink  [40, 17, 0]
		alloclink  [41, 17, 1]
		unifylinks     [18, 23, 1]
		unifylinks     [19, 21, 1]
		unifylinks     [24, 29, 2]
		unifylinks     [25, 31, 2]
		unifylinks     [26, 27, 2]
		unifylinks     [32, 41, 0]
		unifylinks     [33, 37, 0]
		unifylinks     [34, 20, 0]
		unifylinks     [35, 39, 0]
		unifylinks     [36, 22, 0]
		unifylinks     [38, 28, 0]
		unifylinks     [40, 30, 0]
		enqueueatom    [13]
		enqueueatom    [10]
		enqueueatom    [9]
		enqueueatom    [8]
		enqueueatom    [5]
		enqueueatom    [4]
		enqueueatom    [3]
		proceed        []


/* マッチしたアトムの移動＆マッチ以外のアトムの抹消 */
Compiled Ruleset @605
Compiled Rule 
	--atommatch:
		spec          [2, 3]
	--memmatch:
		spec          [1, 25]

//長さ1以上の環状リスト
	    //       ③
  	  //       |     ｜ 
  	  // ･･･--2②1--2④-･･･
  	  anymem      [2, 0, 0, "*"]
  	  not[[
  	  	nfreelinks [2, 1]
  	  	proceed    []
  	  ]]
  	  print["test"]
  	  loop[[
  	  print["head"]
	  	  findatom    [3, 2, $in_2]
	  	print["ta"]
  	  	deref       [4, 0, 3, 0]
  	  print["deref done"]
  	  	lockmem     [5, 4, null] //lockmemは、膜名を指定させるくせに膜名をチェックしない
  	  print["d"]
 				findatom 		[6, 5, $out_2]
 				neqatom  		[4, 6]
  	  	derefatom 	[7, 0, 6, 0]
  	  	lockmem     [3, 7, "*"]
  	  	neqmem      [2, 3]
  	  print["tail"]
    	]]
    	eqmem       [2, 3]
			commit["ring_mem", 0]
			
			clearrules    [0]
			proceed []


/* 全ての階層にルールを投げる */
Compiled Rule 
	--atommatch:
		spec          [2, 3]
	--memmatch:
		spec          [1, 2]
		anymem      	[1, 0, 0, "*"]
		norules     	[1]
		commit        ["rulecopy", 0]
		removeproxies [1]
		enqueuemem    [1]
		insertproxies [0, 1]
		copyrules     [1, 0]
		unlockmem     [1]
		proceed       []

Inline
