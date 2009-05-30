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
