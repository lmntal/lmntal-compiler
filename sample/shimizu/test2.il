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
		spec           [1, 2]
		commit         ["_init", 0]
		loadruleset    [0, @601]
		newatom     [1, 0, 'a'_0]
		enqueueatom    [1]
		proceed        []


Compiled Ruleset @601 
Compiled Rule 
	--atommatch:
		spec           [2, 2]
	--memmatch:
		spec           [1, 2]
		findatom    [1, 0, 'a'_0]
		jump           [L114, [0], [1], []]
	--guard:L114:
		spec           [2, 2]
		jump           [L103, [0], [1], []]
	--body:L103:
		spec           [2, 3]
		commit         ["_ab", 0]
		dequeueatom    [1]
		removeatom     [1, 0, 'a'_0]
		newatom     [2, 0, 'b'_0]
		enqueueatom    [2]
		freeatom       [1]
		proceed        []


Inline
