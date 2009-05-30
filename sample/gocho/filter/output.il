Compiled Ruleset @700
Compiled Rule 
	--atommatch:
		spec          [2, 3]
	--memmatch:
		spec          [1, 2]
		anymem      	[1, 0, 0, "*"]
		not[[
			eqmemname     [1, "^^"]
			proceed       []
		]]
		norules     	[1]
		commit        ["rulecopy", 0]
		removeproxies [1]
		enqueuemem    [1]
		insertproxies [0, 1]
		copyrules     [1, 0]
		unlockmem     [1]
		proceed       []

Compiled Rule
	--atommatch:
		spec          [2, 3]
	--memmatch:
		spec          [1, 2]
		not[[
			anymem       	[1, 0, 0, "^^"]
			proceed			 	[]
		]]
		commit        ["mem make", 0]
		newmem 				[1, 0, 0]
		setmemname    [1, "^^"]
		enqueuemem    [1]
		insertproxies [0, 1]
		clearrules    [0]
		loadruleset   [0, @650]
		proceed       []


Compiled Ruleset @650
Compiled Rule 
	--atommatch:
		spec       	   [2, 4]
	--memmatch:
		spec           [1, 100]
		anymem         [1, 0, 0, "^^"]
		not[[
			natoms		 		 [0, 0]
			proceed     	 []
		]]
    branch[[
eqmemname[0, "schej"]

      removemem     [1, 0]
      proceed       []
    ]]
    
		branch[[
findatom[3, 0, 'p'_2]

			newstack       [2]
			derefatom      [4, 3, 0]
			derefatom      [5, 3, 1]
			push           [2, 5]
			push           [2, 4]
			loop[[
				pop            [6, 2]
				branch[[
					narity 				 [6, 3]
					derefatom      [4, 6, 0]
					derefatom      [5, 6, 1]
					push           [2, 5]
					push           [2, 4]
					proceed				 []
				]]
				branch[[
					narity         [6, 1]
					proceed			   []
				]]
			]]
			narity           [6, 1]
			
			commit           ["tree", 0]
			newstack         [20]
			derefatom        [4, 3, 0]
			derefatom        [5, 3, 1]
			push             [2, 5]
			push             [2, 4]
			removeatom       [3, 0]
			copyatom         [6, 1, 3]
			alloclink        [7, 6, 0]
			alloclink        [8, 6, 1]
			push             [20, 8]
			push             [20, 7]
			freeatom         [3]
			loop[[
				pop              [9, 2]
				branch[[
					narity           [9, 3]
					derefatom        [10, 9, 0]
					derefatom        [11, 9, 1]
					push             [2, 11]
					push             [2, 10]
					removeatom       [9, 0]
					copyatom         [12, 1, 9]
					alloclink        [13, 12, 0]
					alloclink        [14, 12, 1]
					alloclink        [15, 12, 2]
					pop              [19, 20]
					unifylinks       [15, 19, 1]
					push             [20, 14]
					push             [20, 13]
					freeatom         [9]
					proceed				   []
				]]
				branch[[
					narity           [9, 1]
					removeatom       [9, 0]
					copyatom         [16, 1, 9]
					alloclink        [17, 16, 0]
					pop              [18, 20]
					unifylinks       [17, 18, 1]
					freeatom         [9]
					proceed				   []
				]]
			]]
			freestack        [2]
			freestack        [20]
			unlockmem        [1]
			proceed          []
			
		]]
		

		branch[[
			commit           ["clear_atomset", 0]
			clearatomset 	   [0]
			unlockmem        [1]
			proceed  	   	   []
		]]
		unlockmem        [1]
		proceed 			   []

Compiled Rule
	--atommatch:
		spec           [2, 3]
	--memmatch:
		spec           [1, 10]
		anymem         [1, 0, 0, "^^"]
		natoms				 [0, 0]
		
		commit         ["fin", 0]
		removemem      [1, 0]
		removeproxies  [1]
		movecells      [0, 1]
		removetemporaryproxies[0]
		freemem        [1]
		clearrules     [1]
		proceed        []

Inline
