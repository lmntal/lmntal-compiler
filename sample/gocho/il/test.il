Compiled Ruleset @602 
Compiled Rule 
	--atommatch:
		spec           [2, 2]
	--memmatch:
		spec           [1, 1]
		jump           [L160, [0], [], []]
	--guard:L160:
		spec           [1, 1]
		jump           [L153, [0], [], []]
	--body:L153:
		spec           [1, 20]
		commit         [null, 0]
		loadruleset    [0, @601]
		newatom     [1, 0, 'b'_1]
		newatom     [2, 0, 'c'_1]
		newatom     [3, 0, 1_1]
		newatom     [4, 0, 'a'_1]
		newatom     [5, 0, 'b'_1]
		newatom     [6, 0, 'b'_1]
		newatom     [13, 0, 'a'_2]
		newatom     [14, 0, 1_1]
		newatom     [15, 0, 2_1]
		alloclink   [16, 13, 0]
		alloclink   [17, 13, 1]
		alloclink   [18, 14, 0]
		alloclink   [19, 15, 0]
		unifylinks  [16, 18, 0]
		unifylinks  [17, 19, 0]
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
		enqueueatom    [5]
		enqueueatom    [4]
		enqueueatom    [2]
		enqueueatom    [1]
		proceed        []


Compiled Ruleset @601  
Compiled Rule 
	--atommatch:
		spec           [2, 5]
	--memmatch:
		spec           [1, 5]
		anyatom        [1, 0, 0, 1]
		commit         ["anyatom", 0]
		newstack       [2]
		pushanyatom    [2, 1]
		loop[[
		  pop            [3, 2]
		  removeatom     [3, 0]
		  proceed        []
		]]
		removeatom     [1, 0]
		freeatom       [1]
		freestack      [2]
		proceed        []

Inline