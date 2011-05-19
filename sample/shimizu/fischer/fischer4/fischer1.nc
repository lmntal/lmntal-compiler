never { /* !(<>(a||b||c) && !<>[](c && !(cs || a)) && !<>[](a && !b) && !<>[](d && !ncs) -> <>cs) */
T0_init:
	if
	:: (!cs) -> goto T0_init
	:: (a && !cs) || (c && !cs) -> goto T1_S16
	:: (b && !cs) -> goto T2_S16
	fi;
T1_S16:
	if
	:: (!cs) -> goto T1_S16
	:: (!a && !cs) || (b && !cs) -> goto T2_S16
	:: (!a && !cs && !d) || (!a && !cs && ncs) || (b && !cs && !d) || (b && !cs && ncs) -> goto T3_S16
	:: (!a && !c && !cs && !d) || (!a && !c && !cs && ncs) || (b && !c && !cs && !d) || (a && b && !cs && !d) || (b && !c && !cs && ncs) || (a && b && !cs && ncs) -> goto accept_S16
	fi;
T3_S16:
	if
	:: (!cs) -> goto T3_S16
	:: (!c && !cs) || (a && !cs) -> goto accept_S16
	fi;
accept_S16:
	if
	:: (!cs) -> goto T1_S16
	:: (!a && !cs) || (b && !cs) -> goto T2_S16
	:: (!a && !cs && !d) || (!a && !cs && ncs) || (b && !cs && !d) || (b && !cs && ncs) -> goto T3_S16
	:: (!a && !c && !cs && !d) || (!a && !c && !cs && ncs) || (b && !c && !cs && !d) || (a && b && !cs && !d) || (b && !c && !cs && ncs) || (a && b && !cs && ncs) -> goto accept_S16
	fi;
T2_S16:
	if
	:: (!cs) -> goto T2_S16
	:: (!cs && !d) || (!cs && ncs) -> goto T3_S16
	:: (!c && !cs && !d) || (a && !cs && !d) || (!c && !cs && ncs) || (a && !cs && ncs) -> goto accept_S16
	fi;
}

