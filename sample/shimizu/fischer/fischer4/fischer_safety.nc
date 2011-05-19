never { /* !([]!assert) */
T0_init:
	if
	:: (1) -> goto T0_init
	:: (assert) -> goto accept_all
	fi;
accept_all:
	skip
}

