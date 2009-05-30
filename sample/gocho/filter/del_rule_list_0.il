Compiled Rule 
	--atommatch:
		spec           [2, 3]
	--memmatch:
		spec           [1, 5]
		findatom       [2, 0, '[]'_1]
		derefatom      [3, 2, 0]
		isunary        [3]
		
		commit         ["0_list", 0]
		removeatom     [2, 0]
		removeatom     [3, 0]
		freeatom       [2]
		freeatom       [3]
		proceed        []
		