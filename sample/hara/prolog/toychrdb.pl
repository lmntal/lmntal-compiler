/* toychrdb 3.1 -- simple CHR interpreter/debugger based on the refined
                   operational semantics of CHRs
   Copright (C) 2004 Gregory J. Duck

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
*/

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% TOYCHRDB INTERPRETER/DEBUGGER
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% DESCRIPTION:
%       This is a simple CHR interpreter which is, unlike Chameleon, based on 
%       the refined operational semantics of CHRs.  It also supports a 
%       primative trace debugger.
%
%       Unlike the original toychr, the interpreter implements all of the
%       refined operational semantics, including waking up constraints on 
%       Solve.
%
% NOTES:
%       (1) Cannot use toychr and toychrdb at the same time.
%       (2) toychrdb, unlike toychr, only works with SWIProlog.
%       (3) If your program uses tell/1, then debugging won't work.
%
% USAGE:
%       chr_compile(+File),
%       -Result chr_is +Goal.
% 
% Where `Goal' is your query, `File' is the source file containing the CHR 
% program and `Result' is the result of the query.
%
% DEBUGGING:
%       chr_trace
%               Like Prolog's trace/0.
%
%       chr_notrace
%               Like Prolog's notrace/0.
%
%       chr_spy(C)
%               Sets a spy point on a constraint of form C being activated.
%
%       chr_nospy
%               Disables all spy points.
%
%       chr_no_spy(C)
%               TODO: not implemented!
%
% OPTIONS:
%       chr_option(optimization_level,+N)
%               Sets optimization level to integer N.  Higher numbers should
%               be faster (note "should").  (default: all optimizations on)
%
%               N = 0: -) all optimizations off.
%
%               N = 1: -) No history if rule is not a propagation rule.
%                      -) Wakeup only on builtin constraints that bind
%                         variables.
%
%               N = 2: -) Delete entries from history where an id is dead.
%                      -) Wakeup only the constraints affected by a binding.
%                      -) Drop will fire if active constraint is not in the
%                      store.
%
%       chr_option(show_(stack|store|history|id),(yes|no|toggle))
%               When tracing, show the execution stack, store, history or
%               current ID. (default: show store only)
%
%       chr_option(allow_deep_guards,(yes|no|toggle))
%               Allow CHR constraints in rule guards or if-conditions.
%               Operationally, a sub-derivation is spawned with the guard as
%               the goal.  The guard is deemed to succeed if the
%               sub-derivation terminates without failure. (default: off)
%
% TODO:
%       -) Fix the problems listed in `NOTES'
%       -) Implement matching better.
%
% DISCLAIMER:
%       Do not use toychrdb to judge my Prolog programming skills!
%       Thankyou!
%---------------------------------------------------------------------------%

:- op(1150,xfy,chr_is).
:- dynamic current_prog/1.
chr_is(Result,Goal) :-
        ( current_prog(P) ->
            parse_bodies(Goal,Goal0),
            run_goal(Goal0,P,R),
            state_to_result(R,Result)
        ;   format("Warning: no CHR program currently loaded.\n",[]),
            Result = Goal).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% COMPILER
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% This is a modified version of toyCHR's compiler.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

:- op(1104,fx,handler).
:- op(1104,fx,constraints).
:- op(1103,xfx,@).
:- op(1102,xfx,[<=>,==>]).
:- op(1101,xfx,\).

:- dynamic(chr_loaded/2).

chr_compile(File) :-
        retractall(current_prog(_)),
        parse_file(File,Prog),
        assert(current_prog(Prog)).

chr_retract([]).
chr_retract([Name/Arity|Preds]) :-
        functor(Term,Name,Arity),
        retractall(Term),
        chr_retract(Preds).

parse_file(File,Prog) :-
        open(File,read,Stream),
        parse_rules(Stream,0,_,[],Prog),
        close(Stream).

parse_rules(Stream,N,NN,Prog0,Prog2) :-
        read_term(Stream,Term,[]),
        ( Term = end_of_file ->
            Prog2 = Prog0,
            NN = N
        ; parse_rule(Term,N,N0,Prog0,Prog1) ->
            parse_rules(Stream,N0,NN,Prog1,Prog2)
        ;   parse_rules(Stream,N,NN,Prog0,Prog2)).

parse_rule(Term,N,NN,Prog0,Prog1) :-
        ( Term = '@'(Name,Rule) ->
            parse_rule2(Rule,Name,N,NN,Prog0,Prog1)
        ;   int_to_atom(N,Name),
            M is N + 1,
            parse_rule2(Term,Name,M,NN,Prog0,Prog1)).

parse_rule2('<=>'(Head,GuardBody),Name,N,NN,Prog0,Prog1) :-
        parse_guard_body(GuardBody,Guard,Body),
        parse_heads(Head,simp,Name,Guard,Body,N,NN,Prog0,Prog1).
parse_rule2('==>'(Head,GuardBody),Name,N,NN,Prog0,Prog1) :-
        parse_guard_body(GuardBody,Guard,Body),
        parse_heads(Head,prop,Name,Guard,Body,N,NN,Prog0,Prog1).

parse_heads(Heads,Type,Name,Guard,Body,N,NN,Prog0,Prog2) :-
        ( Heads = (HeadRemain \ HeadKill) ->
            Type = simp,
            parse_terms(HeadRemain,Remain),
            parse_terms(HeadKill,Kill),
            heads2prog(Kill,[],kill,Remain,Name,Guard,Body,N,N0,Prog0,Prog1),
            heads2prog(Remain,[],remain,Kill,Name,Guard,Body,N0,NN,Prog1,Prog2)
        ;   parse_terms(Heads,Head),
            ( Type = simp ->
                heads2prog(Head,[],kill,[],Name,Guard,Body,N,NN,Prog0,Prog2)
            ;   heads2prog(Head,[],remain,[],Name,Guard,Body,N,NN,Prog0,Prog2))).

parse_guard_body(GuardBody,Guard,Body) :-
        ( GuardBody = '|'(Guard0,Body0) ->
            Guard = Guard0,
            parse_bodies(Body0,Body)
        ; GuardBody = ';'(Guard0,Body0), Guard0 \= '->'(_,_) ->      % For Sicstus
            Guard = Guard0,
            parse_bodies(Body0,Body)
        ;   Guard = true,
            parse_bodies(GuardBody,Body)).

parse_terms(Term,Terms) :-
        ( Term = ','(Term0,Term1) ->
            parse_terms(Term0,Terms0),
            parse_terms(Term1,Terms1),
            my_append(Terms0,Terms1,Terms)
        ;   Terms = [Term]).

parse_bodies(Term,Terms) :-
        ( Term = ','(Term0,Term1) ->
            parse_bodies(Term0,Terms0),
            parse_bodies(Term1,Terms1),
            my_append(Terms0,Terms1,Terms)
        ; Term = (Term0 -> Term1 ; Term2) ->
            parse_bodies(Term1,Terms1),
            parse_bodies(Term2,Terms2),
            Terms = [if_then_else(Term0,Terms1,Terms2)]
        ;   Terms = [Term]).

heads2prog([],_,_,_,_,_,_,N,N,Prog,Prog).
heads2prog([Head|Heads],Seen,Type,Other,Name,Guard,Body,N,NN,Prog0,Prog2) :-
        ( Type == kill ->
            reverse([active|Seen],SeenRev),
            append(SeenRev,Heads,Kill),
                   Occ = kill(Name,Head,Other,Kill,Guard,Body)
        ;   reverse([active|Seen],SeenRev),
            append(SeenRev,Heads,Remain),
            Occ = remain(Name,Head,Remain,Other,Guard,Body)),
        functor(Head,F,A),
        prog_insert(F/A,Occ,Prog0,Prog1),
        M is N + 1,
        heads2prog(Heads,[Head|Seen],Type,Other,Name,Guard,Body,M,NN,Prog1,Prog2).

prog_insert(CId,Occ,[],[occs(CId,[Occ])]).
prog_insert(CId0,Occ,[Occs0|Prog0],[Occs1|Prog1]) :-
        Occs0 = occs(CId1,COccs0),
        ( CId0 = CId1 ->
            my_append(COccs0,[Occ],COccs1),
            Occs1 = occs(CId1,COccs1),
            Prog1 = Prog0
        ;   Occs1 = Occs0,
            prog_insert(CId0,Occ,Prog0,Prog1)).

my_append([],Bs,Bs).
my_append([A|As],Bs,[A|Cs]) :-
        my_append(As,Bs,Cs).

my_atom_chars(Atom,AtomChrs) :-
        ( ground(Atom) ->
            ( atom_chars(a,[a]) ->
                atom_codes(Atom,AtomChrs)
            ;   atom_chars(Atom,AtomChrs))
        ;   atom_chars(Atom,AtomChrs)).

my_number_chars(Num,NumChrs) :-
        ( ground(Num) ->
            ( number_chars(1,['1']) ->
                number_codes(Num,NumChrs)
            ;   number_chars(Num,NumChrs))
        ;   number_chars(Num,NumChrs)).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% INTERPRETER
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

run_goal(G,P,R) :-
        save_chr_options(Opts),
        state(G,S0),
        ( derivation(S0,P,R) ->
            clear_next,
            restore_chr_options(Opts)
        ;   clear_next,
            restore_chr_options(Opts),
            fail).

derivation(S0,P,R) :-
        ( final_state(S0) ->
            R = S0
        ;   transition(P,S0,S1),
            derivation(S1,P,R)).

state(G,state(G,[],[],1)).

final_state(state([],_,_,_)).

%Solve
transition(P,St0,St1) :-
        St0 = state([C|A],S,T,N),
        ( C = if_then_else(Cd,Th,El) ->
             !,
             check_next([C|A]),
             print_trace("SOLVE      ",no,St0),
             ( test_guard(Cd,P) ->
                 append(Th,A,A1)
             ;   append(El,A,A1))
        ;    builtin(C),
             !,
             check_next([C|A]),
             print_trace("SOLVE      ",no,St0),
             get_wakeups(C,S,S1),
             call(C),
             append(S1,A,A1)),
        St1 = state(A1,S,T,N).
%Activate 
transition(_,St0,St1) :- 
        St0 = state([C|A],S,T,N0),
        chr_constraint(C),
        !,
        check_for_spy_point(C),
        print_trace("ACTIVATE   ",yes,St0),
        N1 is N0 + 1,
        St1 = state([act(C,N0,1)|A],[num(C,N0)|S],T,N1).
%Reactivate
transition(_,St0,St1) :- 
        St0 = state([num(C,I)|A],S,T,N),
        !,
        check_for_spy_point(C),
        print_trace("REACTIVATE ",yes,St0),
        St1 = state([act(C,I,1)|A],S,T,N).
%Drop
transition(P,St0,St1) :- 
        St0 = state(A0,S,T,N),
        A0 = [act(C,I,J)|A],
        check_drop(P,C,J,I,S),
        !,
        check_next(A0),
        print_trace("DROP       ",no,St0),
        St1 = state(A,S,T,N).
%Simplify
transition(P,St0,St1) :- 
        St0 = state(A0,S,T,N),
        A0 = [act(C,I,J)|A],
        occurrence(C,J,P,Occ),
        Occ = kill(_,Active,Remain,Kill,_,_),
        term_match(Active,C),
        delete(I,S,S1,_),
        match(Kill,S1,MKill,S2,I,IdsK),
        match(Remain,S2,MRemain,_,I,IdsR),
        term_match(triple(Active,Remain,Kill),triple(C,MRemain,MKill)),
        copy_term(Occ,ROcc),
        ROcc = kill(RId,RActive,RRemain,RKill,RGuard,RBody),
        C = RActive,
        MKill = RKill,
        MRemain = RRemain,
        test_guard(RGuard,P),
        history_check(RId,IdsR,IdsK,Kill,T,T1),
        !,
        check_next(A0),
        print_trace("SIMPLIFY   ",no,St0),
        clean_history(T1,I,T2),
        append(RBody,A,A1),
        St1 = state(A1,S2,T2,N).
%Propagate
transition(P,St0,St1) :- 
        St0 = state(A0,S,T,N),
        A0 = [act(C,I,J)|_],
        occurrence(C,J,P,Occ),
        Occ = remain(_,Active,Remain,Kill,_,_),
        term_match(Active,C),
        delete(I,S,S1,NC),
        match(Kill,S1,MKill,S2,I,IdsK),
        match(Remain,S2,MRemain,_,I,IdsR),
        term_match(triple(Active,Remain,Kill),triple(C,MRemain,MKill)),
        copy_term(Occ,ROcc),
        ROcc = remain(RId,RActive,RRemain,RKill,RGuard,RBody),
        C = RActive,
        MKill = RKill,
        MRemain = RRemain,
        test_guard(RGuard,P),
        history_check(RId,IdsR,IdsK,Kill,T,T1),
        !,
        print_trace("PROPAGATE  ",yes,St0),
        append(RBody,A0,A1),
        St1 = state(A1,[NC|S2],T1,N).
%Default
transition(_,St0,St1) :-
        St0 = state([act(C,I,J)|A],S,T,N),
        !,
        print_trace("DEFAULT    ",yes,St0),
        K is J + 1,
        St1 = state([act(C,I,K)|A],S,T,N).

get_nonground([],[]).
get_nonground([C|S],S1) :-
        ( ground(C) ->
            get_nonground(S,S1)
        ;   S1 = [C|S0],
            get_nonground(S,S0)).

builtin(_ = _).
builtin(_ \= _).
builtin(_ < _).
builtin(_ > _).
builtin(_ =< _).
builtin(_ >= _).
builtin(_ == _).
builtin(_ =\= _).
builtin(\+ _).
builtin(abort).
builtin(arg(_,_,_)).
builtin(assert(_)).
builtin(atom(_)).
builtin(atomic(_)).
builtin(call(_)).
builtin(close(_)).
builtin(close(_,_)).
builtin(compare(_,_,_)).
builtin(copy_term(_,_)).
builtin(fail).
builtin(float(_)).
builtin(functor(_,_,_)).
builtin(ground(_)).
builtin(get_byte(_)).
builtin(get_byte(_,_)).
builtin(get_char(_)).
builtin(get_char(_,_)).
builtin(get_code(_)).
builtin(get_code(_,_)).
builtin(ground(_)).
builtin(halt).
builtin(halt(_)).
builtin(integer(_)).
builtin(_ is _).
builtin(length(_,_)).
builtin(name(_,_)).
builtin(nonvar(_)).
builtin(number(_)).
builtin(once(_)).
builtin(op(_,_,_)).
builtin(open(_,_,_)).
builtin(open(_,_,_,_)).
builtin(peek_byte(_)).
builtin(peek_byte(_,_)).
builtin(peek_char(_)).
builtin(peek_char(_,_)).
builtin(peek_code(_)).
builtin(peek_code(_,_)).
builtin(put(_)).
builtin(put_byte(_)).
builtin(put_byte(_,_)).
builtin(put_char(_)).
builtin(put_char(_,_)).
builtin(put_code(_)).
builtin(put_code(_,_)).
builtin(read(_)).
builtin(read(_,_)).
builtin(read_term(_,_)).
builtin(read_term(_,_,_)).
builtin(repeat).
builtin(retract(_)).
builtin(retract(_,_)).
builtin(retractall(_)).
builtin(see(_)).
builtin(see(_,_)).
builtin(seeing(_)).
builtin(seeing(_,_)).
builtin(seen).
builtin(set_prolog_flag(_,_)).
builtin(set_stream_position(_,_)).
builtin(statistics).
builtin(statistics(_,_)).
builtin(stream_property(_,_)).
builtin(sub_atom(_,_,_,_,_)).
builtin(sub_string(_,_,_,_,_)).
builtin(tell(_)).
builtin(tell(_,_)).
builtin(telling(_)).
builtin(telling(_,_)).
builtin(told).
builtin(true).
builtin(var(_)).
builtin(write(_)).
builtin(write(_,_)).
builtin(write_term(_,_)).
builtin(write_term(_,_,_)).
% Extras.
builtin(append(_,_,_)).
builtin(member(_,_)).
builtin(print(_)).
builtin(nl).
builtin(format(_,_)).
builtin(trace).
builtin(chr_trace).
builtin(chr_notrace).
builtin(chr_option(_,_)).
builtin(chr_spy(_)).
builtin(chr_nospy).

need_wakeup(_ = _).
need_wakeup(_ is _).
need_wakeup(arg(_,_,_)).
need_wakeup(call(_)).
need_wakeup(compare(_,_,_)).
need_wakeup(functor(_,_,_)).
need_wakeup(get_byte(_)).
need_wakeup(get_byte(_,_)).
need_wakeup(get_char(_)).
need_wakeup(get_char(_,_)).
need_wakeup(get_code(_)).
need_wakeup(get_code(_,_)).
need_wakeup(length(_,_)).
need_wakeup(name(_,_)).
need_wakeup(peek_byte(_)).
need_wakeup(peek_byte(_,_)).
need_wakeup(peek_char(_)).
need_wakeup(peek_char(_,_)).
need_wakeup(peek_code(_)).
need_wakeup(peek_code(_,_)).
need_wakeup(read(_)).
need_wakeup(read(_,_)).
need_wakeup(read_term(_,_)).
need_wakeup(read_term(_,_,_)).
need_wakeup(statistics(_,_)).
need_wakeup(sub_atom(_,_,_,_,_)).
need_wakeup(append(_,_,_)).
need_wakeup(member(_,_)).

get_wakeups(C,S,S1) :-
        ( check_optimization_level(2) ->
            free_variables(C,Vs),
            ( Vs = [] ->
                S1 = []
            ;   get_with_vars(Vs,S,S1))
        ; check_optimization_level(1) ->
            ( need_wakeup(C) ->
                get_nonground(S,S1)
            ;   S1 = [])
        ;   get_nonground(S,S1)).

get_with_vars(_,[],[]).
get_with_vars(Vs,[C|Cs],Cs1) :-
        free_variables(C,Vs1),
        ( sharing(Vs,Vs1) ->
            Cs1 = [C|Cs2],
            get_with_vars(Vs,Cs,Cs2)
        ;   get_with_vars(Vs,Cs,Cs1)).

sharing([V|Vs1],Vs2) :-
        ( var_member(V,Vs2) ->
            true
        ;   sharing(Vs1,Vs2)).

var_member(V,[V1|Vs]) :-
        ( V == V1 ->
            true
        ;   var_member(V,Vs)).

chr_constraint(C) :-
        C \= num(_,_),
        C \= act(_,_,_).

check_drop(P,C,J,I,S) :-
        ( pred_symb(C,CP),
          \+ get_occ(CP,P,J,_) ->
            true
        ;   check_optimization_level(2),
            \+ delete(I,S,_,_)).

occurrence(C,J,P,Occ) :-
        pred_symb(C,CP),
        get_occ(CP,P,J,Occ).

pred_symb(C,CP) :-
        functor(C,F,A),
        CP = F/A.

get_occ(CP,[occs(CP1,Occs)|P],J,Occ) :-
        ( CP = CP1 ->
            get_occ(J,Occs,Occ)
        ;   get_occ(CP,P,J,Occ)).

get_occ(J,[Occ0|Occs],Occ) :-
        ( J = 1 ->
            Occ = Occ0
        ;   K is J - 1,
            get_occ(K,Occs,Occ)).

match([],S,[],S,_,[]).
match([C|Cs],S,[MC|MCs],SR,AI,[I|Ids]) :-
        ( C == active ->
            I = AI,
            MC = C,
            match(Cs,S,MCs,SR,AI,Ids)
        ;   match1(C,S,MC,SR0,I),
            match(Cs,SR0,MCs,SR,AI,Ids)).

match1(C,[C1|S],MC,SR,I) :-
        C1 = num(C2,I0),
        ( term_match(C,C2) ->
            (    MC = C2,
                 I = I0,
                 SR = S
            ;    SR = [C1|SR0],
                 match1(C,S,MC,SR0,I))
        ;   SR = [C1|SR0],
            match1(C,S,MC,SR0,I)).

term_match(HT,T) :-
        \+ \+ (
            free_variables(T,TVars),
            unify_with_occurs_check(HT,T),
            free_variables(TVars,TVars1),
            TVars == TVars1
        ).

delete(I,[C|S1],S2,NC) :-
        C = num(_,J),
        ( I = J ->
            S2 = S1,
            NC = C
        ;   S2 = [C|S3],
            delete(I,S1,S3,NC)).

history_check(RId,IdsR,IdsK,Kill,T0,T1) :-
       ( Kill = [] ->
           E = [RId|IdsR],
           \+ member(E,T0),
           T1 = [E|T0]
       ; check_optimization_level(1) ->
           T1 = T0
       ;   append([RId|IdsR],IdsK,E),
           \+ member(E,T0),
           T1 = [E|T0]).

clean_history(T0,I,T1) :-
       ( check_optimization_level(2) ->
           do_clean_history(T0,I,T1)
       ;   T1 = T0).

do_clean_history([],_,[]).
do_clean_history([E|T],I,T1) :-
        ( member(I,E) ->
            do_clean_history(T,I,T1)
        ;   T1 = [E|T2],
            do_clean_history(T,I,T2)).

test_guard(G,P) :-
        ( chr_option_allow_deep_guards ->
            parse_bodies(G,Gs),
            state(Gs,State),
            ( chr_next_state(L) ->
                clear_next,
                ( derivation(State,P,_) ->
                    assert(chr_next_state(L))
                ;   assert(chr_next_state(L)),
                    fail)
            ;   derivation(State,P,_))
        ;   call(G)).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% DEBUGGER
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

:- dynamic chr_option_print_trace.
:- dynamic chr_option_trace_interactive.
:- dynamic chr_option_optimization_level/1.
:- dynamic chr_option_show_stack.
:- dynamic chr_option_show_store.
:- dynamic chr_option_show_history.
:- dynamic chr_option_show_id.
:- dynamic chr_option_allow_deep_guards.

chr_option_show_store.

chr_option(trace,N) :-
        do_option(chr_option_print_trace,N).
chr_option(interactive,N) :-
        do_option(chr_option_trace_interactive,N).
chr_option(show_stack,N) :-
        do_option(chr_option_show_stack,N).
chr_option(show_store,N) :-
        do_option(chr_option_show_store,N).
chr_option(show_history,N) :-
        do_option(chr_option_show_history,N).
chr_option(show_id,N) :-
        do_option(chr_option_show_id,N).
chr_option(optimization_level,N) :-
        ( integer(N) ->
            retractall(chr_option_optimization_level(_)),
            assert(chr_option_optimization_level(N))
        ;   true).
chr_option(allow_deep_guards,N) :-
        do_option(chr_option_allow_deep_guards,N).

do_option(Pred,YN) :-
        ( YN == toggle ->
            ( call(Pred) ->
                retractall(Pred)
            ;   assert(Pred))
        ; YN == yes ->
            ( call(Pred) ->
                true
            ;   assert(Pred))
        ; YN == no ->
            retractall(Pred)
        ;   true).

check_optimization_level(N) :-
        ( chr_option_optimization_level(M) ->
            N =< M
        ;   true).

save_chr_options(opts(Tr,Int,SA,SS,ST,SN)) :-
        ( chr_option_print_trace ->
            Tr = yes
        ;   Tr = no),
        ( chr_option_trace_interactive ->
            Int = yes
        ;   Int = no),
        ( chr_option_show_stack ->
            SA = yes
        ;   SA = no),
        ( chr_option_show_store ->
            SS = yes
        ;   SS = no),
        ( chr_option_show_history ->
            ST = yes
        ;   ST = no),
        ( chr_option_show_id ->
            SN = yes
        ;   SN = no).

restore_chr_options(opts(Tr,Int,SA,SS,ST,SN)) :-
        chr_option(trace,Tr),
        chr_option(interactive,Int),
        chr_option(show_stack,SA),
        chr_option(show_store,SS),
        chr_option(show_history,ST),
        chr_option(show_id,SN).

:- dynamic chr_next_state/1.

do_next(state(A,_,_,_)) :-
        retractall(chr_next_state(_)),
        length(A,L),
        assert(chr_next_state(L)),
        chr_option(trace,no).

check_next(A) :-
        ( chr_next_state(L0) ->
            length(A,L),
            ( L == L0 ->
                retractall(chr_next_state(_)),
                chr_option(trace,yes),
                chr_option(interactive,yes)
            ;   true)
        ;   true).

clear_next :-
        retractall(chr_next_state(_)).

print_trace(Trans,Next,State) :-
        ( chr_option_print_trace ->
            format("\n~s: ",[Trans]),
            print_state(State),
            ( chr_option_trace_interactive ->
                format("(Action : ? for help) "),
                get_single_char(C),
                string_to_atom([C],Comm),
                ( Comm == '\r' ->
                    format("step\n")
                ; Comm == 'n' ->
                    format("next\n"),
                    ( Next = yes ->
                        do_next(State)
                    ;   print_trace(Trans,Next,State))
                ; Comm == 'f' ->
                    format("fail\n"),
                    fail
                ; Comm == 'a' ->
                    format("abort\n"),
                    abort
                ; Comm == 'l' ->
                    format("leap\n"),
                    chr_option(trace,no)
                ; Comm == 'c' ->
                    format("continue\n"),
                    chr_option(interactive,no)
                ; Comm == 'r' ->
                    format("rule\n"),
                    print_rule(State),
                    print_trace(Trans,Next,State)
                ; Comm == 'h' ->
                    format("history\n{"),
                    State = state(_,_,T,_),
                    print_history(T),
                    format("}\n"),
                    print_trace(Trans,Next,State)
                ; Comm == 'H' ->
                    format("History\n"),
                    chr_option(show_history,toggle),
                    print_trace(Trans,Next,State)
                ; Comm == 'e' ->
                    format("execution stack\n["),
                    State = state(A,_,_,_),
                    print_stack(A),
                    format("]\n"),
                    print_trace(Trans,Next,State)
                ; Comm == 'E' ->
                    format("Execution stack\n"),
                    chr_option(show_stack,toggle),
                    print_trace(Trans,Next,State)
                ; Comm == 's' ->
                    format("store\n{"),
                    State = state(_,S,_,_),
                    print_store(S),
                    format("}\n"),
                    print_trace(Trans,Next,State)
                ; Comm == 'S' ->
                    format("Store\n"),
                    chr_option(show_store,toggle),
                    print_trace(Trans,Next,State)
                ; Comm == 'i' ->
                    State = state(_,_,_,N),
                    format("id\n~d\n",[N]),
                    print_trace(Trans,Next,State)
                ; Comm == 'I' ->
                    format("Id\n"),
                    chr_option(show_id,toggle),
                    print_trace(Trans,Next,State)
                ; Comm == 'y' ->
                    format("spy\n"),
                    format("Set spy point on CHR constraint: "),
                    read(Pattern),
                    chr_spy(Pattern),
                    print_trace(Trans,Next,State)
                ; Comm == '?' ->
                    format("\n(enter)\tstep (apply transition)\n"),
                    format("n\tnext (finish executing current constraint)\n"),
                    format("f\tfail (cause derivation to fail)\n"),
                    format("a\tabort (aborts derivation)\n"),
                    format("l\tleap (silently continue derivation)\n"),
                    format("c\tcontinue (continue dervation)\n"),
                    format("y\tspy (set spy point)\n"),
                    format("r\trule (print current rule)\n"),
                    format("e\texecution stack (print the execution stack)\n"),
                    format("E\tExecution stack (toggles show stack mode)\n"),
                    format("s\tstore (print the CHR store)\n"),
                    format("S\tStore (toggles show store mode)\n"),
                    format("h\thistory (print the propagation history)\n"),
                    format("H\tHistory (toggles show history mode)\n"),
                    format("i\tid (prints next free ID number)\n"),
                    format("I\tId (toggles show ID mode)\n"),
                    format("?\thelp (print this message)\n"),
                    print_trace(Trans,Next,State) 
                ;   format("undefined command \"~p\", try ? for help.\n",Comm),
                    print_trace(Trans,Next,State)) 
            ;   nl) 
        ;   true).

print_state(state([C|A],S,T,N)) :-
        format("("),
        print_cons(C),
        format(")\n"),
        ( chr_option_show_stack ->
            format("stack = ["),
            print_stack([C|A]),
            format("]\n")
        ;   true),
        ( chr_option_show_store ->
            format("store = {"),
            print_store(S),
            format("}\n")
        ;   true),
        ( chr_option_show_history ->
            format("history = {"),
            print_history(T),
            format("}\n")
        ;   true),
        ( chr_option_show_id ->
            format("id = ~d\n",[N])
        ;   true).

print_store([]).
print_store([num(C,I)|S]) :-
        format("~p#~d",[C,I]),
        ( S = [] ->
            true
        ;   format(",",[]),
            print_store(S)).

print_stack([]).
print_stack([C|A]) :-
        print_cons(C),
        ( A = [] ->
            true
        ;   format(","),
            print_stack(A)).

print_history([]).
print_history([E|T]) :-
        format("("),
        print_stack(E),
        format(")"),
        ( T = [] ->
            true
        ;   format(","),
            print_history(T)).

print_cons(C) :-
        ( C = act(C0,I,J) ->
            format("~p#~d:~d",[C0,I,J])
        ; C = num(C0,I) ->
            format("~p#~d",[C0,I])
        ; C = if_then_else(C,T,E) ->
            format("(~p->",[C]),
            print_stack(T),
            format(";"),
            print_stack(E),
            format(")")
        ;   format("~p",[C])).

print_rule(St) :-
        ( St = state([act(C,_,J)|_],_,_,_),
          current_prog(P),
          occurrence(C,J,P,Occ) ->
            ( Occ = kill(Name,Active,Remain,Kill,Guard,Body) ->
                print_rule(Name,Active,Remain,Kill,Guard,Body)
            ;   Occ = remain(Name,Active,Remain,Kill,Guard,Body),
                print_rule(Name,Active,Remain,Kill,Guard,Body))
        ;   format("No rule associated with current state.\n")).

print_rule(N,A,R,K,G,B) :-
        format("~p @ ",[N]),
        ( R = [] ->
            print_heads(K,A),
            format(" <=> ")
        ;   print_heads(R,A),
            ( K = [] ->
                format(" ==> ")
            ;   format(" \\ "),
                print_heads(K,A),
                format(" <=> "))),
        format("~p | ",[G]),
        print_stack(B),
        format(".\n").

print_heads([],_).
print_heads([H|Hs],A) :-
        ( H = active ->
            format("(active) "),
            print_cons(A)
        ;   print_cons(H)),
        ( Hs = [] ->
            true
        ;   format(", "),
            print_heads(Hs,A)).

state_to_result(state(_,S,_,_),R) :-
        store_to_result(S,R).

store_to_result([],true).
store_to_result([num(C,_)|S],R) :-
        ( S = [] ->
            R = C
        ;   R = (C , R0),
            store_to_result(S,R0)).

:- dynamic chr_spy_point/1.

chr_nospy :-
        retractall(chr_spy_point(_)).

chr_spy(Pattern) :-
        asserta(chr_spy_point(P0) :- (P0 =@= Pattern)).

check_for_spy_point(C) :-
        ( chr_spy_point(C) ->
            clear_next,
            chr_option(trace,yes),
            chr_option(interactive,yes)
        ;   true).

chr_trace :-
        chr_option(trace,yes),
        chr_option(interactive,yes).

chr_notrace :-
        chr_option(trace,no),
        chr_option(interactive,no).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% PRINT BANNER
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

:- format("_|_ _    _|_ ._   _||_\n").
:- format(" |_(_)\\/(_| ||   (_||_)  Version 3.1\n").
:- format("      /\n").
:- format("toychrdb version 1.0, Copyright (C) 2004 Gregory J. Duck\n").
:- format("toychrdb comes with ABSOLUTELY NO WARRANTY; for details see `COPYING\'\n").
:- format("This is free software, and you are welcome to redistribute it\n").
:- format("under certain conditions; see `COPYING\' for details.\n\n").

