
Compiled Rule 
	--atommatch:
		spec           [2, 3]
	--memmatch:
		spec           [1, 5]
		findatom       [2, 0, '[]'_1]
		derefatom      [3, 2, 0]
		func           [3, '.'_3]
		derefatom      [4, 3, 0]
		derefatom      [5, 3, 2]
		eqatom         [4, 5]
		
		commit         ["ring0", 0]
		removeatom     [2, 0]
		removeatom     [3, 0]
		freeatom       [2]
		freeatom       [3]
		proceed        []
