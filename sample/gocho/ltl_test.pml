int A = 0;

active proctype inclement() {
	do
	:: A++
	od
}


/* <>p */
#define p A==10

never { /* (<>p) */
T0_init:
        if
        :: (1) -> goto T0_init
        :: (p) -> goto accept_all
        fi;
accept_all:
        skip
}
