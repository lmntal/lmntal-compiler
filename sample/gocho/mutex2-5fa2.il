Compiled Ruleset @602 
Compiled Rule 
	--atommatch:
		spec           [2, 2]
	--memmatch:
		spec           [1, 1]
		jump           [L229, [0], [], []]
	--guard:L229:
		spec           [1, 1]
		jump           [L222, [0], [], []]
	--body:L222:
		spec           [1, 37]
		commit         [null, 0]
		loadruleset    [0, @601]
		newatom     [1, 0, 'wait'_1]
		newatom     [2, 0, 1_1]
		newatom     [3, 0, 'p'_2]
		newatom     [4, 0, 'wait'_1]
		newatom     [5, 0, 2_1]
		newatom     [6, 0, 'p'_2]
		newatom     [7, 0, 'wait'_1]
		newatom     [8, 0, 3_1]
		newatom     [9, 0, 'p'_2]
		newatom    [10, 0, 'wait'_1]
		newatom    [11, 0, 4_1]
		newatom    [12, 0, 'p'_2]
		newatom    [13, 0, 'wait'_1]
		newatom    [14, 0, 5_1]
		newatom    [15, 0, 'p'_2]
		newatom    [16, 0, 'mutex'_0]
		alloclink  [17, 1, 0]
		alloclink  [18, 2, 0]
		alloclink  [19, 3, 0]
		alloclink  [20, 3, 1]
		alloclink  [21, 4, 0]
		alloclink  [22, 5, 0]
		alloclink  [23, 6, 0]
		alloclink  [24, 6, 1]
		alloclink  [25, 7, 0]
		alloclink  [26, 8, 0]
		alloclink  [27, 9, 0]
		alloclink  [28, 9, 1]
		alloclink  [29, 10, 0]
		alloclink  [30, 11, 0]
		alloclink  [31, 12, 0]
		alloclink  [32, 12, 1]
		alloclink  [33, 13, 0]
		alloclink  [34, 14, 0]
		alloclink  [35, 15, 0]
		alloclink  [36, 15, 1]
		unifylinks     [17, 19, 0]
		unifylinks     [18, 20, 0]
		unifylinks     [21, 23, 0]
		unifylinks     [22, 24, 0]
		unifylinks     [25, 27, 0]
		unifylinks     [26, 28, 0]
		unifylinks     [29, 31, 0]
		unifylinks     [30, 32, 0]
		unifylinks     [33, 35, 0]
		unifylinks     [34, 36, 0]
		enqueueatom    [16]
		enqueueatom    [15]
		enqueueatom    [13]
		enqueueatom    [12]
		enqueueatom    [10]
		enqueueatom    [9]
		enqueueatom    [7]
		enqueueatom    [6]
		enqueueatom    [4]
		enqueueatom    [3]
		enqueueatom    [1]
		proceed        []


Compiled Ruleset @601   
Compiled Rule 
	--atommatch:
		spec           [2, 2]
	--memmatch:
		spec           [1, 3]
		group          [[
                  branch         [[
                    findatom2   [1, 0, 0, 'wait'_1]
                    resetvars      [[0], [1], []]
                    proceed        [] ]]
                  stop           [] ]]
		jump           [L118, [0], [1], []]
	--guard:L118:
		spec           [2, 2]
		jump           [L103, [0], [1], []]
	--body:L103:
		spec           [2, 3]
		commit         ["request", 0]
		dequeueatom    [1]
		removeatom     [1, 0, 'wait'_1]
		newatom     [2, 0, 'rqst'_1]
		relink         [2, 0, 1, 0, 0]
		enqueueatom    [2]
		freeatom       [1]
		proceed        []

Compiled Rule 
	--atommatch:
		spec           [2, 3]
	--memmatch:
		spec           [1, 3]
		group          [[
                  branch         [[
                    findatom2   [1, 0, 1, 'rqst'_1]
                    resetvars      [[0], [1], []]
                    proceed        [] ]]
                  stop           [] ]]
		group          [[
                  branch         [[
                    findatom2   [2, 0, 2, 'mutex'_0]
                    resetvars      [[0], [1, 2], []]
                    proceed        [] ]]
                  stop           [] ]]
		jump           [L160, [0], [1, 2], []]
	--guard:L160:
		spec           [3, 3]
		jump           [L137, [0], [1, 2], []]
	--body:L137:
		spec           [3, 4]
		commit         ["get_mtx", 0]
		dequeueatom    [1]
		dequeueatom    [2]
		removeatom     [1, 0, 'rqst'_1]
		removeatom     [2, 0, 'mutex'_0]
		newatom     [3, 0, 'crit'_1]
		relink         [3, 0, 1, 0, 0]
		enqueueatom    [3]
		freeatom       [1]
		freeatom       [2]
		proceed        []

Compiled Rule 
	--atommatch:
		spec           [2, 2]
	--memmatch:
		spec           [1, 3]
		group          [[
                  branch         [[
                    findatom2   [1, 0, 3, 'crit'_1]
                    resetvars      [[0], [1], []]
                    proceed        [] ]]
                  stop           [] ]]
		jump           [L203, [0], [1], []]
	--guard:L203:
		spec           [2, 2]
		jump           [L188, [0], [1], []]
	--body:L188:
		spec           [2, 4]
		commit         ["release", 0]
		dequeueatom    [1]
		removeatom     [1, 0, 'crit'_1]
		newatom     [2, 0, 'wait'_1]
		newatom     [3, 0, 'mutex'_0]
		relink         [2, 0, 1, 0, 0]
		enqueueatom    [3]
		enqueueatom    [2]
		freeatom       [1]
		proceed        []


Inline
