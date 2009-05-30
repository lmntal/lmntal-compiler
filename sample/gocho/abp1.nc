never { /* !(!([](p-><>q))) */
accept_init:
	if
	:: (!p) || (q) -> goto accept_init
	:: (1) -> goto T0_S2
	fi;
T0_S2:
	if
	:: (1) -> goto T0_S2
	:: (q) -> goto accept_init
	fi;
}

