never { /* !(!(<>[]p)) */
T0_init:
	if
	:: (1) -> goto T0_init
	:: (p) -> goto accept_S2
	fi;
accept_S2:
	if
	:: (p) -> goto accept_S2
	fi;
}

