never { /* original */
T0_init:
	if
	:: (1) -> goto T0_init
	:: (p)  -> goto T0_S2
	fi;
T0_S2:
	if
	:: (1) -> goto T0_S2
	:: (q) -> goto accept_all
	fi;
accept_all:
	skip
}

