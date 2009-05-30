Compiled Rule
	--atommatch:
		spec        [2, 3]
	--memmatch:
		spec        [1, 5]

		not[[
		  natoms     [1, 0]
		  proceed     []
		]]
		commit      ["mem_match", 0]
		clearatomset[1]
		unlockmem   [1]
		proceed     []