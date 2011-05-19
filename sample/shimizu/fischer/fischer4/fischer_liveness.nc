never { /* !([]( b -> <>cs )) */
T0_init:
	if
	:: (1) -> goto T0_init
	:: (!cs && b) -> goto accept_S2
	fi;
accept_S2:
	if
	:: (!cs) -> goto accept_S2
	fi;
}

