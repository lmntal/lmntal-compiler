never { /* !(<>(a||b||c) -> <>cs) */
T0_init:
	if
	:: (!cs) -> goto T0_init
	:: (a && !cs) || (c && !cs) || (b && !cs) -> goto accept_S2
	fi;
accept_S2:
	if
	:: (!cs) -> goto accept_S2
	fi;
}

