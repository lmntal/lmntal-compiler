never { /* !(!<>comp) */
T0_init:
	if
	:: (1) -> goto T0_init
	:: (comp) -> goto accept_all
	fi;
accept_all:
	skip
}

