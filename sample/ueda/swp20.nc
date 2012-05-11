never { /* !([](send -> (<>ack))) */
T0_init:
	if
	:: (1) -> goto T0_init
	:: (!ack && send) -> goto accept_S2
	fi;
accept_S2:
	if
	:: (!ack) -> goto accept_S2
	fi;
}

