
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
