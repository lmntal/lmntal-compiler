Compiled Ruleset @602 
Compiled Rule 
	--atommatch:
	--memmatch:
		spec           [1, 1]
		jump           [L106, [0], [], []]
	--guard:L106:
		spec           [1, 1]
		jump           [L107, [0], [], []]
	--body:L107:
		spec           [1, 6]
		commit         ["_init", 0]
		loadruleset    [0, @601]
		newatom      [1, 0, 'str'_1]
		newatom      [2, 0, "a"_1]
		newatom      [3, 0, 'match'_0]
		alloclink    [4, 1, 0]
		alloclink    [5, 2, 0]
		unifylinks     [4, 5, 0]
		proceed        []


Compiled Ruleset @601 
Compiled Rule 
	--atommatch:
	--memmatch:
		spec           [1, 4]
		findatom     [1, 0, 'str'_1]
		deref        [2, 1, 0, 0]
		func           [2, "a"_1]
		findatom     [3, 0, 'match'_0]
		jump           [L101, [0], [1, 3, 2], []]
	--guard:L101:
		spec           [4, 4]
		jump           [L102, [0], [1, 2, 3], []]
	--body:L102:
		spec           [4, 7]
		commit         ["_stra", 0]
		removeatom     [1, 0]
		removeatom     [2, 0]
		removeatom     [3, 0]
		newatom      [4, 0, 'done'_0]
		newatom      [5, 0, 'str'_1]
		newatom      [6, 0, "a"_1]
		newlink        [5, 0, 6, 0, 0]
		freeatom       [1]
		freeatom       [2]
		freeatom       [3]
		proceed        []


