never { /* (((gc || gw) -> gf ) U gcwf) */
T0_init:
	if
	:: (!gc && !gw) || (gf) -> goto T0_init
	:: (gcwf) -> goto accept_all
	fi;
accept_all:
	skip
}

