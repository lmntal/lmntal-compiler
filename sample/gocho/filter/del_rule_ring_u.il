
Compiled Rule
	--atommatch:
		spec           [2, 10]
	--memmatch:
		spec           [1, 8]
		findatom       [2, 0, '.'_3]
		derefatom      [3, 2, 1]
		derefatom      [4, 2, 0]
		isunary        [4]
		loop[[
   		neqatom        [3, 2]
			func           [3, '.'_3]
			derefatom      [5, 3, 0]
			isunary        [5]
			derefatom      [3, 3, 1]
			proceed        []
		]]
    eqatom         [3, 2]

		commit         ["ringN", 0]
		derefatom      [3, 3, 1]
		loop[[
  		neqatom        [3, 2]
			derefatom      [4, 3, 0]
			removeatom     [3, 0]
			removeatom     [4, 0]
			derefatom      [3, 3, 1]
			proceed        []
		]]
    derefatom      [4, 3, 0]
		removeatom     [3, 0]
		removeatom     [4, 0]
		freeatom       [2]
		freeatom       [3]
		freeatom       [4]
		unlockmem      [1]
		proceed        []


