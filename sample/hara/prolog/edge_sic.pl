% simple constraint solver for inequalities between variables
% thom fruehwirth ECRC 950519, LMU 980207, 980311

:- use_module(library(chr)).
%:- use_module(library(random)).

handler leq.

constraints e/2, loop/5.

%e(E00, E10), e(E11, E20), e(E21, E01) ==> E00=E01, E10=E11, E20=E21 | loop(E00, E10, E20).
%e(E0, E1), e(E1, E2), e(E2, E0) ==> loop(E0, E1, E2).
e(E0, E1), e(E1, E2), e(E2, E3), e(E3, E4), e(E4, E0) ==> 
E0\==E1, E0\==E2, E0\==E3, E0\==E4,
E1\==E2, E1\==E3, E1\==E4,
E2\==E3, E2\==E4,
E3\==E4
 | loop(E0, E1, E2, E3, E4).
%e(E0, E1), e(E1, E2), e(E2, E3), e(E3, E0) ==> loop(E0, E1, E2, E3).

% this generates a circular leq-relation chain with N variables

go(N):-
%	write(user_error, N),
%	nl(user_error),
	cputime(X),
	gen(N, N*10),
	cputime( Now),
	Time is Now-X,
	write(Time), nl.

goL(N):-
%	write(user_error, N),
%	nl(user_error),
	cputime(X),
	length(L, N),
	genL(L, N, N*10),
	cputime( Now),
	Time is Now-X,
	write(Time), nl.

gen(_, 0).
gen(N, Co) :- rndInt(N, X0), rndInt(N, X1), ee(X0, X1), N1 is Co-1, gen(N, N1).

genL(_, _, 0).
genL(L, N, Co) :- 
	rndInt(N, X0), rndInt(N, X1), 
	nth(X0, L, Z0), nth(X1, L, Z1), 
	tryL(N, Co, L, X0, X1, Z0, Z1), 
	Ne is Co-1, genL(L, N, Ne).

tryL(_, _, _, X, X, _, _) :- 
	ground(X).
	%write('RETRY'), write(X), nl, genL(L, N, Co).

tryL(_, _, _, X0, X1, Z0, Z1) :- 
	X0=\=X1, 
%	write('OK'), write(X0), write(X1), nl, 
	e(Z0, Z1).


ee(V0, V0).
ee(V0, V1) :- e(V0, V1).

nth(0, [X|_], X).
nth(N, [_|L], X) :- N > 0, N1 is N-1, nth(N1, L, X).


% SICStus
%rndInt(N, Res) :- random(X), Res is integer(X*N).
% SWI
rndInt(N, Res) :- Res is random(N).

cputime(Ts) :- 
	statistics( runtime, [Tm,_]),
	Ts is Tm/1000.

%run(N) :- run2(5, N), run1(5, N).
%run1(N, N).
%run1(Co, N) :- go(Co), N1 is Co+5, run1(N1, N).
%run2(N, N).
%run2(Co, N) :- goL(Co), N1 is Co+5, run2(N1, N).


% eof handler leq -----------------------------------------------
