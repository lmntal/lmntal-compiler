/* toychr 3.1 -- very simple CHR implementation for Prolog 
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
% TOY CHR COMPILER/SYSTEM
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% DESCRIPTION:
%       A very simple CHR compiler.  Should be very slow compared with most 
%       real systems (but still probably kicks Chameleon's arse).
% LIMITATIONS:
%       - Only works on ground data.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%---------------------------------------------------------------------------%
% USAGE:
%       chr_compile(+File),
%       -Result chr_is +Goal.
% 
% Where `Goal' is your query (list of constraints), `File' is the source file 
% containing the CHR program and `Result' is the result of the query.
%---------------------------------------------------------------------------%
:- op(1150,xfy,chr_is).

chr_is(Result,Goal) :-
        compile_body(Goal,NGoal,[],Store),
        call(NGoal),
        chr_store2result(Store,Result).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% RUNTIME SYSTEM
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

:- op(675,xfx,#).

chr_lookup(C,Store,Iterator) :-
        chr_constraint2chr_id(C,CId),
        chr_lookup_iterator(Store,CId,Iterator).

chr_lookup_iterator([],_,[]).
chr_lookup_iterator([store(CId1,List)|Store],CId0,Iterator) :-
        ( CId0 = CId1 ->
            Iterator = List
        ;   chr_lookup_iterator(Store,CId0,Iterator)).

chr_alive(Id) :-
        var(Id).

chr_kill(Id) :-
        Id = dead.

chr_different(Id0,Id1) :-
        \+ Id0 == Id1.

chr_constraint2chr_id(C,CId) :-
        functor(C,F,A),
        CId = chr_id(A,F).

chr_store_insert(C,Id,Store0,Store1) :-
        chr_constraint2chr_id(C,CId),
        chr_store_insert(Store0,CId,C,Id,Store1).

chr_store_insert([],CId,C,Id,[store(CId,[C # Id])]).
chr_store_insert([CStore0|Store0],CId0,C,Id,[CStore1|Store1]) :-
        CStore0 = store(CId1,Cs),
        ( CId0 = CId1 ->
            CStore1 = store(CId1,[C # Id|Cs]),
            Store1  = Store0
        ;   CStore1 = CStore0,
            chr_store_insert(Store0,CId0,C,Id,Store1)).

chr_store_delete(C,Id,Store0,Store1) :-
        chr_constraint2chr_id(C,CId),
        chr_store_delete2(Store0,CId,Id,Store1).

chr_store_delete2([CStore0|Store0],CId0,Id,[CStore1|Store1]) :-
        CStore0 = store(CId1,Cs0),
        ( CId0 = CId1 ->
            chr_store_delete_id(Cs0,Id,Cs1),
            CStore1 = store(CId1,Cs1),
            Store1  = Store0
        ;   CStore1 = CStore0,
            chr_store_delete2(Store0,CId0,Id,Store1)).

chr_store_delete_id([C0|Cs0],Id1,Cs1) :-
        C0 = _ # Id2,
        ( Id1 == Id2 ->
            Cs1 = Cs0
        ;   Cs1 = [C0|NCs],
            chr_store_delete_id(Cs0,Id1,NCs)).

chr_store2result([],true).
chr_store2result([store(_,Cs)|Store],Result) :-
        ( Cs = [] ->
            chr_store2result(Store,Result)
        ;   chr_storelist2result(Cs,Result0),
            chr_store2result(Store,Result1),
            ( Result1 = true ->
                Result = Result0
            ;   Result = ','(Result1,Result0))).
       
chr_storelist2result([],true).
chr_storelist2result([C # _|Cs],Result) :-
        ( Cs = [] ->
            Result = C
        ;   Result = ','(C,Result0),
            chr_storelist2result(Cs,Result0)).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% COMPILER
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

:- op(1104,fx,handler).
:- op(1104,fx,constraints).
:- op(1103,xfx,@).
:- op(1102,xfx,[<=>,==>]).
:- op(1101,xfx,\).

:- dynamic(chr_loaded/2).

chr_compile(File) :-
        ( chr_loaded(File,Preds0) ->
            chr_retract(Preds0),
            retractall(chr_loaded(File,_))
        ;   true),
        parse_file(File,Prog,Preds1),
        chr_compile_prog(Prog,Preds1,Preds2),
        assert(chr_loaded(File,Preds2)).

chr_retract([]).
chr_retract([Name/Arity|Preds]) :-
        functor(Term,Name,Arity),
        retractall(Term),
        chr_retract(Preds).

parse_file(File,Prog,Preds) :-
        open(File,read,Stream),
        parse_rules(Stream,0,_,[],Prog,[],Preds),
        close(Stream).

parse_rules(Stream,N,NN,Prog0,Prog2,Preds0,Preds2) :-
        read_term(Stream,Term,[]),
        ( Term = end_of_file ->
            Prog2 = Prog0,
            Preds2 = Preds0,
            NN = N
        ; parse_rule(Term,N,N0,Prog0,Prog1,Preds0,Preds1) ->
            parse_rules(Stream,N0,NN,Prog1,Prog2,Preds1,Preds2)
        ;   parse_rules(Stream,N,NN,Prog0,Prog2,Preds0,Preds2)).

parse_rule(Term,N,NN,Prog0,Prog1,Preds0,Preds1) :-
        ( Term = '@'(_,Rule) ->
            parse_rule2(Rule,N,NN,Prog0,Prog1,Preds0,Preds1)
        ;   parse_rule2(Term,N,NN,Prog0,Prog1,Preds0,Preds1)).

parse_rule2('<=>'(Head,GuardBody),N,NN,Prog0,Prog1,Preds0,Preds1) :-
        parse_guard_body(GuardBody,Guard,Body),
        parse_heads(Head,simp,Guard,Body,N,NN,Prog0,Prog1,Preds0,Preds1).
parse_rule2('==>'(Head,GuardBody),N,NN,Prog0,Prog1,Preds0,Preds1) :-
        parse_guard_body(GuardBody,Guard,Body),
        parse_heads(Head,prop,Guard,Body,N,NN,Prog0,Prog1,Preds0,Preds1).

parse_heads(Heads,Type,Guard,Body,N,NN,Prog0,Prog2,Preds0,Preds2) :-
        ( Heads = (HeadRemain \ HeadKill) ->
            Type = simp,
            parse_terms(HeadRemain,Remain),
            parse_terms(HeadKill,Kill),
            heads2prog(Kill,[],kill,Remain,Guard,Body,N,N0,Prog0,Prog1,Preds0,Preds1),
            heads2prog(Remain,[],remain,Kill,Guard,Body,N0,NN,Prog1,Prog2,Preds1,Preds2)
        ;   parse_terms(Heads,Head),
            ( Type = simp ->
                heads2prog(Head,[],kill,[],Guard,Body,N,NN,Prog0,Prog2,Preds0,Preds2)
            ;   heads2prog(Head,[],remain,[],Guard,Body,N,NN,Prog0,Prog2,Preds0,Preds2))).

parse_guard_body(GuardBody,Guard,Body) :-
        ( GuardBody = '|'(Guard0,Body0) ->
            Guard = Guard0,
            Body = Body0
        ; GuardBody = ';'(Guard0,Body0), Guard0 \= '->'(_,_) ->      % For Sicstus
            Guard = Guard0,
            Body = Body0
        ;   Guard = true,
            Body = GuardBody).

parse_terms(Term,Terms) :-
        ( Term = ','(Term0,Term1) ->
            parse_terms(Term0,Terms0),
            parse_terms(Term1,Terms1),
            my_append(Terms0,Terms1,Terms)
        ;   Terms = [Term]).

heads2prog([],_,_,_,_,_,N,N,Prog,Prog,Preds,Preds).
heads2prog([Head|Heads],Seen,Type,Other,Guard,Body,N,NN,Prog0,Prog2,Preds0,Preds2) :-
        my_append(Heads,Seen,Heads1),
        ( Type = kill ->
            heads2partners(Heads1,Other,Partners),
            compile_occurence(Head,kill,N,Partners,Guard,Body,GoalName,Preds0,Preds1)
        ;   heads2partners(Other,Heads1,Partners),
            compile_occurence(Head,remain,N,Partners,Guard,Body,GoalName,Preds0,Preds1)),
        chr_constraint2chr_id(Head,CId),
        prog_insert(CId,GoalName,Prog0,Prog1),
        M is N + 1,
        heads2prog(Heads,[Head|Seen],Type,Other,Guard,Body,M,NN,Prog1,Prog2,Preds1,Preds2).

heads2partners([],[],[]).
heads2partners([],[Head|Heads],[Partner|Partners]) :-
        Partner = partner(Head,remain,_HeadVar),
        heads2partners([],Heads,Partners).
heads2partners([Head|Heads],Remains,[Partner|Partners]) :-
        Partner = partner(Head,kill,_HeadVar),
        heads2partners(Heads,Remains,Partners).

prog_insert(CId,Occ,[],[occs(CId,[Occ])]).
prog_insert(CId0,Occ,[Occs0|Prog0],[Occs1|Prog1]) :-
        Occs0 = occs(CId1,COccs0),
        ( CId0 = CId1 ->
            my_append(COccs0,[Occ],COccs1),
            Occs1 = occs(CId1,COccs1),
            Prog1 = Prog0
        ;   Occs1 = Occs0,
            prog_insert(CId0,Occ,Prog0,Prog1)).

compile_body(Body0,Body1,S0,S2) :-
        ( Body0 = ','(Body01,Body02) ->
            compile_body(Body01,Body11,S0,S1),
            compile_body(Body02,Body12,S1,S2),
            Body1 = ','(Body11,Body12)
        ; Body0 = ';'(Body01,Body02) ->
            compile_body(Body01,Body11,S0,S2),
            compile_body(Body02,Body12,S0,S2),
            Body1 = ';'(Body11,Body12)
        ; Body0 = '->'(Body01,Body02) ->
            compile_body(Body01,Body11,S0,S1),
            compile_body(Body02,Body12,S1,S2),
            Body1 = '->'(Body11,Body12)
        ;   builtin(Body0) ->
            Body1 = ','(Body0,(S2 = S0))
        ;   Body0 =.. [F|Args],
            Body1 =.. [F,S0,S2|Args]).

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

compile_occurence(Active,Type,Num,Partners,Guard,Body,GoalName,Preds0,Preds2) :-
        chr_constraint2chr_id(Active,CId),
        make_occurence_name(CId,Num,GoalName),
        functor(Head,GoalName,4),
        Preds1 = [GoalName/4|Preds0],
        Head =.. [_,C,Id,Store0,Store1],
        Body0 = (C = Active),
        Body1 = Body0,
        compile_deconstr(Partners,DeConstr0),
        DeConstr1 = ','(Body0,DeConstr0),
        compile_partners(Partners,C,Id,Store0,Store1,[],[],[],Num,
            Type,DeConstr1,Guard,Body,Body2,Preds1,Preds2),
        Body3 = (Body1 -> Body2 ; (Store1 = Store0)),
        Clause = ':-'(Head,Body3),
        assert(Clause).

compile_deconstr([],true).
compile_deconstr([partner(C,_,CVar)|Partners],DeConstr) :-
        DeConstr0 = (CVar = C),
        compile_deconstr(Partners,DeConstr1),
        DeConstr = ','(DeConstr0,DeConstr1).

compile_partners([],C,Id,Store0,Store1,Kills,_,_,_,Type,DeConstr,Guard,
            Body,Body8,Preds,Preds) :-
        compile_body(Body,Body0,Store3,Store1),
        ( Type = kill ->
            Body1 = chr_store_delete(C,Id,Store2,Store3),
            Body2 = chr_kill(Id),
            Body3 = ','(Body1,Body2),
            Body4 = ','(Body3,Body0)
        ;   Body4 = Body0,
            Store3 = Store2),
        compile_kills(Kills,Store0,Store2,Body5),
        Body6 = ','(Body5,Body4),
        Body7 = Body6,
        compile_body(Guard,NGuard,[],_),
        Body8 = (DeConstr, NGuard -> Body7 ; (Store1 = Store0)).
compile_partners([partner(C0,Type0,CVar)|Partners],C,Id,Store0,Store2,
            Kills,Args,Ids,N,Type,DeConstr,Guard,Body,Body1,Preds0,Preds2) :-
        Body0 = chr_lookup(C0,Store0,List),
        chr_constraint2chr_id(C0,C0Id),
        make_join_loop_name(C0Id,N,JoinName),
        my_append(Args,Ids,JoinArgs0),
        JoinArgs = [JoinName,List,C,Id,Store0,Store2|JoinArgs0],
        Head =.. JoinArgs,
        Body1 = ','(Body0,Head),
        Body2 = (List = [CVar # Id0|List0]),
        compile_alives([Id|Ids],Body3),
        Body4 = ','(Body3,Body2),
        ( Type0 = kill ->
            NKills = [kill(C0,Id0)|Kills]
        ;   NKills = Kills),
        compile_partners(Partners,C,Id,Store0,Store1,NKills,[CVar|Args],
            [Id0|Ids],N,Type,DeConstr,Guard,Body,Body5,Preds0,Preds1),
        Body6_a = (CVar = C0),
        compile_differents([Id|Ids],Id0,Diffs),
        Body6 = ','(Body6_a,Diffs),
        NJoinArgs = [JoinName,List0,C,Id,Store1,Store2|JoinArgs0],
        Body7 =.. NJoinArgs,
        functor(Body7,_,JoinArity),
        Preds2 = [JoinName/JoinArity|Preds1],
        Body8 = (Body6 -> Body5 ; (Store1 = Store0)),
        Body9 = ','(Body8,Body7),
        Body10 = (Body4 -> Body9 ; (Store2 = Store0)),
        Clause = ':-'(Head,Body10),
        assert(Clause).

compile_differents([],_,true).
compile_differents([Id|Ids],Id0,Body) :-
        Body0 = chr_different(Id0,Id),
        compile_differents(Ids,Id0,Body1),
        Body = ','(Body0,Body1).

compile_alives([],true).
compile_alives([Id|Ids],Body) :-
        Body0 = chr_alive(Id),
        compile_alives(Ids,Body1),
        Body = ','(Body0,Body1).

compile_kills([],Store,Store,true).
compile_kills([kill(C,Id)|Kills],Store0,Store2,Body) :-
        Body1 = chr_store_delete(C,Id,Store0,Store1),
        Body2 = chr_kill(Id),
        compile_kills(Kills,Store1,Store2,Body3),
        Body4 = ','(Body1,Body2),
        Body = ','(Body4,Body3).

chr_compile_prog([],Preds,Preds).
chr_compile_prog([Occs|Prog],Preds0,Preds2) :-
        Occs = occs(chr_id(Arity,Name),Occs0),
        Arity2 is Arity + 2,
        Preds1 = [Name/Arity2|Preds0],
        functor(Head,Name,Arity2),
        Head =.. [_,S,NS|Args],
        CTerm =.. [Name|Args],
        Body0 = (C = CTerm),
        Body1 = chr_store_insert(C,Id,S,S0),
        compile_occurences(Occs0,S0,NS,Id,C,Body2),
        Body3 = ','(Body0,Body1),
        Body = ','(Body3,Body2),
        Clause = ':-'(Head,Body),
        assert(Clause),
        chr_compile_prog(Prog,Preds1,Preds2).

compile_occurences([],S,NS,_,_,(S = NS)).
compile_occurences([Occ|Occs],S,NS,Id,C,Code) :-
        Body0 =.. [Occ,C,Id,S,S0],
        compile_occurences(Occs,S0,NS,Id,C,Body1),
        Body2 = chr_alive(Id),
        Body3 = (S0 = NS),
        Body4 = (Body2 -> Body1 ; Body3),
        Code = ','(Body0,Body4).

make_occurence_name(CId,Num,Name) :-
        make_chr_name(CId,Num,CHRName),
        my_append("chr_occurence",CHRName,NameStr),
        my_atom_chars(Name,NameStr).

make_join_loop_name(CId,Num,Name) :-
        make_chr_name(CId,Num,CHRName),
        my_append("chr_join_loop",CHRName,NameStr),
        my_atom_chars(Name,NameStr).

make_chr_name(CId,Num,CHRName) :-
        CId = chr_id(Arity,Name),
        [UnderScore] = "_",
        my_number_chars(Num,NumStr0),
        NumStr = [UnderScore|NumStr0],
        my_number_chars(Arity,ArityStr0),
        ArityStr = [UnderScore|ArityStr0],
        my_append(ArityStr,NumStr,CHRName0),
        my_atom_chars(Name,NameStr),
        my_append(NameStr,CHRName0,CHRName1),
        CHRName = [UnderScore|CHRName1].

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
% PRINT BANNER
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

:- nl.
:- format("_|_ _    _|_ ._\n",[]).
:- format(" |_(_)\\/(_| ||   Version 3.1\n",[]).
:- format("      /\n",[]).
:- format("toychr version 3.1, Copyright (C) 2004 Gregory J. Duck\n",[]).
:- format("toychr comes with ABSOLUTELY NO WARRANTY; for details see `COPYING\'\n",[]).
:- format("This is free software, and you are welcome to redistribute it\n",[]).
:- format("under certain conditions; see `COPYING\' for details.\n\n",[]).

