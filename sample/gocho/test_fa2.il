Compiled Ruleset @602 
Compiled Rule 
	--atommatch:
		spec           [2, 2]
	--memmatch:
		spec           [1, 1]
		jump           [L144, [0], [], []]
	--guard:L144:
		spec           [1, 1]
		jump           [L137, [0], [], []]
	--body:L137:
		spec           [1, 13]
		commit         [null, 0]
		loadruleset    [0, @601]
		newatom     [1, 0, 1_1]
		newatom     [2, 0, 'a'_1]
		newatom     [3, 0, 2_1]
		newatom     [4, 0, 'a'_1]
		newatom     [5, 0, 3_1]
		newatom     [6, 0, 'a'_1]
		alloclink   [7, 1, 0]
		alloclink   [8, 2, 0]
		alloclink   [9, 3, 0]
		alloclink  [10, 4, 0]
		alloclink  [11, 5, 0]
		alloclink  [12, 6, 0]
		unifylinks     [7, 8, 0]
		unifylinks     [9, 10, 0]
		unifylinks     [11, 12, 0]
		enqueueatom    [6]
		enqueueatom    [4]
		enqueueatom    [2]
		proceed        []


Compiled Ruleset @601 
Compiled Rule 
	--atommatch:
		spec           [2, 2]
	--memmatch:
		spec           [1, 3]

                    findatom2   [1, 0, 0, 'a'_1]
	
		commit         [null, 0]
		dequeueatom    [1]
		removeatom     [1, 0, 'a'_1]
		newatom     [2, 0, 'b'_1]
		relink         [2, 0, 1, 0, 0]
		enqueueatom    [2]
		freeatom       [1]
		proceed        []


Inline
