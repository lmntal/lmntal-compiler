% author: Or Sharir <tnidtnid@GMAIL.COM>

:-use_module(library(chr)).
:-set_prolog_flag(chr_toplevel_show_store,false).
:-constraints guess/0,cell/3.

/*
cell(Row,Colm,num(Number) or cell(Row,Colm,pos(List_of_possible_numbers))
*/

cell(_,_,pos([]))==>fail.
cell(Row,_,num(Num))\cell(Row,Col,pos(Pos))<=>select(Num,Pos,NewPos)|cell(Row,Col,pos(NewPos)).
cell(_,Col,num(Num))\cell(Row,Col,pos(Pos))<=>select(Num,Pos,NewPos)|cell(Row,Col,pos(NewPos)).
cell(_Row,_Col,num(Num))\cell(Row,Col,pos(Pos))<=>
	square_number(_Row,_Col,Square),square_number(Row,Col,Square),
	select(Num,Pos,NewPos)|cell(Row,Col,pos(NewPos)).
cell(Row,Col,pos([Num]))<=>cell(Row,Col,num(Num)).

%guess a number
guess,cell(Row,Col,pos(Pos))<=>member(N,Pos),cell(Row,Col,num(N)).

solve:-
	board(B),
	create_cells(B,0),
	loop,
	print_board.

loop:- find_chr_constraint(cell(_,_,pos(_))),guess,loop.
loop:- \+find_chr_constraint(cell(_,_,pos(_))),!.

%create the cells from the board...
create_cells([],_):-!.
create_cells([Num|Rest],P):-
	NewP is P+1,
	create_cells(Rest,NewP),
	Row is P//9 + 1,
	Col is P mod 9 + 1,
	(number(Num)->cell(Row,Col,num(Num)) ; 
	cell(Row,Col,pos([1,2,3,4,5,6,7,8,9]))).

%retrieve the number of the square:
%1|2|3
%4|5|6
%7|8|9
square_number(Row,Col,SquareNumber):-
	SquareNumber is  (Col-1) // 3 + ((Row-1)//3)*3 + 1.

%prints the board
print_board:-print_board_(0).
print_board_(81).
print_board_(P):-
	Row is P//9 + 1,
	Col is P mod 9 + 1,
	find_chr_constraint(cell(Row,Col,num(N))),
	write(N),write(' '),
	(Col == 9 -> nl;true),
	NewP is P+1,
	print_board_(NewP).

%example board:
board([6,_,_,7,_,_,5,_,_,
       _,2,8,_,_,_,_,_,_,
       _,_,_,6,4,_,3,_,_,
       7,4,_,_,_,_,_,2,_,
       _,_,1,_,_,_,8,_,_,
       _,5,_,_,_,_,_,3,7,
       _,_,3,_,7,6,_,_,_,
       _,_,_,_,_,_,1,9,_,
       _,_,4,_,_,5,_,_,8]).


