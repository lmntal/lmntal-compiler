never { /* !([](c1->n1 &&  c2->n2 && c3->n3)) */
T0_init:
	if
	:: (1) -> goto T0_init
	:: (!n3 && c3 && !c2) || (!n3 && c3 && n2) || (!n3 && c3 && !n1 && c1) -> goto accept_all
	fi;
accept_all:
	skip
}

