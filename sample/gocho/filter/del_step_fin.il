Compiled Rule 
	--atommatch:
		spec           [2, 3]
	--memmatch:
		spec           [1, 5]
		anymem         [1, 0, 0, "*"]
		norules        [1]
		
		commit         ["copy_r", 0]
		removeproxies  [1]
		enqueuemem     [1]
		insertproxies  [0, 1]
		copyrules      [1, 0]
		unlockmem      [1]
		proceed        []

Inline
