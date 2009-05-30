never { /* !([](p-><>q)) */
T0_init:
	if
	:: (1) -> goto T0_init
	:: (!q && p) -> goto accept_S2
	fi;
accept_S2:
	if
	:: (!q) -> goto accept_S2
	fi;
}

