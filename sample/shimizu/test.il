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
		spec           [1, 10]
		commit         ["_init", 0]
		newmem      [1, 0, 0]
		loadruleset    [1, @601]
		newatom     [2, 1, 'atomic_ruleset'_1]
		newatom     [3, 1, 1_1]
		newatom     [4, 1, '$callback'_2]
		newatom     [5, 0, 'a'_0]
		alloclink   [6, 2, 0]
		alloclink   [7, 3, 0]
		alloclink   [8, 4, 0]
		alloclink   [9, 4, 1]
		unifylinks     [6, 8, 1]
		unifylinks     [7, 9, 1]
		enqueueatom    [5]
		enqueueatom    [4]
		enqueueatom    [2]
		callback       [1, 4]
		proceed        []


Compiled Ruleset @601  
Compiled Rule 
	--atommatch:
		spec           [2, 2]
	--memmatch:
		spec           [1, 3]
		findatom    [1, 0, 'a'_0]
		commit         ["_ab", 0]
		dequeueatom    [1]
		removeatom     [1, 0, 'a'_0]
		newatom     [2, 0, 'b'_0]
		enqueueatom    [2]
		freeatom       [1]
		proceed        []

Compiled Rule 
	--atommatch:
		spec           [2, 2]
	--memmatch:
		spec           [1, 3]
		findatom    [1, 0, 'b'_0]
		commit         ["_bc", 0]
		dequeueatom    [1]
		removeatom     [1, 0, 'b'_0]
		newatom     [2, 0, 'c'_0]
		enqueueatom    [2]
		freeatom       [1]
		proceed        []


Inline
