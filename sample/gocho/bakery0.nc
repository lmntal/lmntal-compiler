never { /* !([]!(p&&q)) */
T0_init:
	if
	:: (1) -> goto T0_init
	:: (p && q) -> goto accept_all
	fi;
accept_all:
	skip
}

