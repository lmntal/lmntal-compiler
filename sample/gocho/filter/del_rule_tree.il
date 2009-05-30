
Compiled Rule 
	--atommatch:
		spec       	   [2, 4]
	--memmatch:
		spec           [1, 15]

		newstack       [1]
		derefatom      [3, 2, 0]
		derefatom      [4, 2, 1]
		push           [1, 4]
		push           [1, 3]
		loop[[
			pop            [5, 1]
			branch[[
				narity 				 [5, 3]
				derefatom      [3, 5, 0]
				derefatom      [4, 5, 1]
				push           [1, 4]
				push           [1, 3]
				proceed        []
			]]
			branch[[
				narity         [5, 1]
				proceed        []
			]]
		]]
		narity         [5, 1]
		
		commit         ["tree", 0]
		derefatom      [3, 2, 0]
		derefatom      [4, 2, 1]
		push           [1, 4]
		push           [1, 3]
		removeatom     [2, 0]
		freeatom       [2]
		loop[[
			pop            [5, 1]
			branch[[
				narity 		  	 [5, 3]
				derefatom      [3, 5, 0]
				derefatom      [4, 5, 1]
				push           [1, 4]
				push           [1, 3]
				removeatom     [5, 0]
				freeatom       [5]
				proceed        []
			]]
			branch[[
				narity         [5, 1]
				removeatom     [5, 0]
				freeatom       [5]
				proceed[]
			]]
		]]
		proceed        []


