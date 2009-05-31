never { /* !(([]<>p) -> ([]<>q)) */
T0_init:
	if
	:: (1) -> goto T0_init
	:: (!q) -> goto T1_S4
	fi;
T1_S4:
	if
	:: (!q) -> goto T1_S4
	:: (!q && p) -> goto accept_S4
	fi;
accept_S4:
	if
	:: (!q) -> goto T1_S4
	:: (!q && p) -> goto accept_S4
	fi;
}

