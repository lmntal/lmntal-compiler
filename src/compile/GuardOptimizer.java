/*
 * 作成日: 2004/11/04
 */
package compile;

import java.util.*;
import runtime.Instruction;
import runtime.InstructionList;
//import runtime.Functor;
//import runtime.Env;

//import runtime.Rule;

/**
 * @author sakurai
 *
 * ガード関係の最適化を行うメソッドを持つクラス。 
 *
 * 現在最適化の対象となる命令
 * ALLOCATOM,DEREFATOM
 * ISINT,ISFLOAT,ISSTRING,ISUNARY
 * FADD,FSUB,FMUL,FDIV,IADD,ISUB,IMUL,IDIV,IMOD
 * FLT,FLE,FGT,FGE,ILT,ILE,IGT,IGE,FEQ,FNQ,IEQ,INQ
 */
public class GuardOptimizer {
	/** アトムグループをまたがないガード命令を、
     *  ガード命令に関係するアトムグループ内に移動させる
     *  またがる場合はその2つのアトムグループの内、後で現れたアトムグループ下に移動
     */
	public static void guardMove(List head, List guard){
		if(guard == null) return;
		
		// 先頭がSPECかつ最後尾がJUMPでない場合失敗
		Instruction headspec = (Instruction)head.get(0);
		if (headspec.getKind() != Instruction.SPEC) return;
		int headformal = headspec.getIntArg1();
		int headvarcount = headspec.getIntArg2();
		
		int headsize = head.size();
		Instruction headjump = (Instruction)head.get(headsize-1);
		if (headjump.getKind() != Instruction.JUMP) return;
		
		Instruction guardspec = (Instruction)guard.get(0);
		if (guardspec.getKind() != Instruction.SPEC) return;
		int guardformal = guardspec.getIntArg1();
		int guardvarcount = guardspec.getIntArg2();
		
		int guardsize = guard.size();
		Instruction guardjump = (Instruction)guard.get(guardsize-1);
		if (guardjump.getKind() != Instruction.JUMP) return;
		
		//ヘッド命令列のjump命令の引数
		//最後に更新する
		//ガードのjumpも更新が必要?
		List headmemargs = (List)headjump.getArg2();
		List headatomargs =(List)headjump.getArg3();
		
		//ガードにX<Y,X+Y<Zのような式を書くとisintやisfloatが重複するので余分な命令を削除
		//無い場合かなり無駄になるのでなんとかならないものか･･･
		for(int gid=0; gid<guardsize; gid++){
			Instruction instg = (Instruction)guard.get(gid);
			int instid = instg.getKind();
			if(instid == Instruction.ISINT 
			 || instid == Instruction.ISFLOAT
			 || instid == Instruction.ISUNARY 
			 || instid == Instruction.ISSTRING){
			 	for(int gid2=gid+1; gid2<guardsize; gid2++){
			 		Instruction instg2 = (Instruction)guard.get(gid2);
					if(instid == instg2.getKind() 
					  && instg.getIntArg1() == instg2.getIntArg1()){
					  	guard.remove(gid2);
						gid2 -= 1;
						guardsize -= 1; 
					  }
			 	}
			 }
		}
		
		for(int gid=0; gid<guardsize; gid++){
			Instruction instg = (Instruction)guard.get(gid);
			boolean expflag = false;
			switch(instg.getKind()){
				case Instruction.ALLOCATOM:
				//ALLOCATOMは実装上都合がいいのでheadのSPECの直後に移動させておく
				//ルールが適用できない場合若干無駄が生じるが大して問題なさそう?
					head.add(1,instg);
					headsize += 1;
					headvarcount += 1;
					headatomargs.add(new Integer(instg.getIntArg1()));
					guard.remove(gid);
					guardsize -= 1;
					gid -= 1;
					break;
                //====ALLOCATOMはここまで====//
                
                //DEREFATOMを移動させる場所を決める
                //DEREFATOMは必ずヘッド側に移動させる
                //TODO ここは未完成。膜が絡んだ場合は検査していない。自分でも意味不明になってきた･･･
                case Instruction.DEREFATOM:
                	//DEREFATOMの第1引数。この値をキーとするアトムを新たにヘッド命令列で扱う
                	int atomvar = instg.getIntArg1();
                	//DEREFATOMの第2引数。このDEREFATOMの接続先のアトムを示す。
                	//このアトムの付近にDEREFATOMを移動させたい
           	        int srcatom = instg.getIntArg2();
           	        //見つけたFINDATOMの第1引数。1つ前のFINDATOM命令も参照したいので2つ用意
           	        int finddstatom = 0;
           	        int finddstatom2 = 0;
           	        //FINDATOM命令の出現位置。この値+1の位置にDEREFATOMを移動させる
           	        int findpoint = 0;
           	        int findpoint2 = 0;
                    //FINDATOMの第1引数とDEREFATOMの第2引数を比べることでDEREFATOMを移動させるのだが、
                    //この対応関係はイマイチ分からない。
                    //とりあえずsameatomlink,sameatomgrouplinkで調整しつつマッチングを図る
                    //sameatomlink,sameatomgrouplinkはDEREF命令を見つけたとき値を増やす
                    int sameatomlink = 0; 
           	        int sameatomgrouplink = 0;
                    for(int hid=1; hid<headsize; hid++){
                		Instruction insth = (Instruction)head.get(hid);
                		switch(insth.getKind()){
                			case Instruction.FINDATOM:
                			    /*
                			     * ここが最も怪しい。というか途中です。
                			     * findpoint   FINDATOM!) [finddstatom,,,,]
                			     * 	  ･        この間にあるDEREF命令をを見て、sameatomlink,sameatomgrouplink
                			     *    ･ 	   をカウントする。
                			     * 	  ･		    finddstatom=srcatom-sameatomgrouplinkならfindpoint
                			     * 	　･			finddstatom=srcatom || findstatom=srcatom+sameatomlinならfindpoint2に
                			     * 	　･			DEREFATOMを移動させる。
                			     * finpoint2   FINDATOM!) [finddstatom2,,,,]
                			     * 正当性は全く保証できないが膜が無ければそこそこうまく行くように思える。
                			     */
                				findpoint2 = hid;
                				finddstatom2 = insth.getIntArg1();
                				//1つ前に見つけたFINDATOMとのマッチング
                			    if(finddstatom == srcatom - sameatomgrouplink){
									head.add(findpoint+1, instg);
									headvarcount += 1;
									headatomargs.add(new Integer(atomvar));
									headsize += 1;
									guard.remove(gid);
									guardsize -= 1;
									gid -= 1;
									hid = headsize;
									break;                				
                				} 
                				//今回見つけたFINDATOMとのマッチング
                				else if(finddstatom2 == srcatom
									|| finddstatom2 == srcatom + sameatomlink){
							 		  head.add(findpoint2+1, instg);
									  headvarcount += 1;
									  headatomargs.add(new Integer(atomvar));
									  headsize += 1;
									  guard.remove(gid);
									  guardsize -= 1;
									  gid -= 1;
									  hid = headsize;
									  break;                				
									}
								 findpoint = hid;
								 finddstatom = insth.getIntArg1();
                				break;
                			
                			case Instruction.DEREF:
                			 	//DEREFの第2引数に着目
                				//定数アトムへのリンクの場合 a(X,10,20),b(Y,30)におけるの10,20,30へのリンク
                				//つまり同じアトム内へのリンクの場合
                				if(finddstatom == insth.getIntArg2()) sameatomlink += 1;
                				//a(X,A),b(A,Y,10)におけるAへのリンク
                				//つまり同じアトムグループ内の他のアトムへのリンクの場合
                				else sameatomgrouplink += 1;
                				break;
                				
                			
                			//上の方のFINDATOM付近に移動できないままヘッド命令の最後まで来てしまったら
                			//最後に見つけたFINDATOM付近に移動させる
                			//DEREFATOMは何が何でもヘッド側に移動してもらわないと困る
                			case Instruction.JUMP:
                				head.add(findpoint+1, instg);
                				headvarcount += 1;
                				headatomargs.add(new Integer(atomvar));
                				headsize += 1;
                				guard.remove(gid);
                				guardsize -= 1;
                				gid -= 1;
                				hid = headsize;
                				break;
                				
                			default: break;
                		}
                	}
                	break;
                //====DEREFATOMはここまで====//
                
				case Instruction.ISINT:
				case Instruction.ISFLOAT:
				case Instruction.ISSTRING:
				case Instruction.ISUNARY:
					//命令の対象となるアトムの変数番号
					atomvar = instg.getIntArg1();
					//atomvarを第1引数にとるDEREFATOMの下に移動させる
					//この時点で対となるDEREFATOMはヘッド側に移動しているはず
					for(int hid=0; hid<headsize; hid++){
						Instruction insth = (Instruction)head.get(hid);
						if(insth.getKind() == Instruction.DEREFATOM
						   && insth.getIntArg1() == atomvar){
						   	head.add(hid+1, instg);
						   	headsize += 1;
						   	guard.remove(gid);
						   	guardsize -= 1;
						   	gid -= 1;
						   }
					}
					break;
				//====ISINT,ISFLOAT,ISSTRING,ISUNARYはここまで====//
				
				//演算関係の命令と比較関係の命令はほぼ同じ処理で良さそう
				//演算の場合は計算結果のアトムの分だけ変数を増やす必要があるのでexpflagで区別
				case Instruction.FADD: expflag = true;
				case Instruction.FSUB: expflag = true;
				case Instruction.FMUL: expflag = true;
				case Instruction.FDIV: expflag = true;
				case Instruction.IADD: expflag = true;
				case Instruction.ISUB: expflag = true;
				case Instruction.IMUL: expflag = true;
				case Instruction.IDIV: expflag = true;
				case Instruction.IMOD: expflag = true;	
								
				case Instruction.FLT:
				case Instruction.FLE:
				case Instruction.FGT:
				case Instruction.FGE:
				case Instruction.ILT:
				case Instruction.ILE:
				case Instruction.IGT:
				case Instruction.IGE:
				case Instruction.IEQ:
				case Instruction.INE:
				case Instruction.FEQ:
				case Instruction.FNE:	
				
     				//命令の第1引数と第2引数を取得
               	    //例:FLT [atomvar1, atomvar2]
               	    //   FADD [resultvar, atomvar1, atomvar2]
					int atomvar1 = 0;
					int atomvar2 = 0;
					int resultvar = 0;		
					if(expflag){
						resultvar = instg.getIntArg1();
						atomvar1 = instg.getIntArg2();
						atomvar2 = instg.getIntArg3();
					} else {
						atomvar1 = instg.getIntArg1();
						atomvar2 = instg.getIntArg2();
					}
					//atomvar1,atomvar2のアトムの内1つを見つけたらtrueにする
					boolean flag = false;
                	//DEREFATOM,ALLOCATOM,四則演算命令の検索
					//すでにヘッド側に移動しているとみていいはず
					//DEREFATOMを見つけた場合その前にあるFINDATOMまたはDEREFを検索することになる
					//その都合上後ろから検索した方が良さそう
					for(int hid=0; hid<headsize-1; hid++){
						Instruction insth = (Instruction)head.get(hid);
						int instid = insth.getKind();
						//四則演算命令は結局定数のアトムを生むのでここでのALLOCATOMと扱いは同じ
						//これらは第1引数をatomvar1,atomvar2と比べるという処理において違いはない
						if(instid == Instruction.ALLOCATOM
						   || instid == Instruction.FADD
						   || instid == Instruction.FSUB
						   || instid == Instruction.FMUL
					   	   || instid == Instruction.FDIV
					   	   || instid == Instruction.IADD
					   	   || instid == Instruction.ISUB
					   	   || instid == Instruction.IMUL
					       || instid == Instruction.IDIV
					       || instid == Instruction.IMOD
					       || instid == Instruction.ISINT
					       || instid == Instruction.ISFLOAT
					       || instid == Instruction.ISUNARY
					       || instid == Instruction.ISSTRING){
							int dstatom = insth.getIntArg1();
							//検索対象の命令の第1引数はatomvar1かatomuvar2
							if(dstatom == atomvar1 || dstatom == atomvar2){
                        	    if(!flag) {
                        	    	flag = true;
                        	    	continue;
                        	    } 
							//検索対象のアトムの内1つを見つけている場合後で見つかった方の次に命令を移動
								else {
									head.add(hid+1, instg);
									headsize += 1;
									if(expflag){
										headvarcount += 1;
										headatomargs.add(new Integer(resultvar));
									}
									guard.remove(gid);
									guardsize -= 1;
									gid -= 1;
									break;
								}
							}
						}
					}
					break;		
				//====FLT,FLE,FGT,FGE,ILT,ILE,IGT,IGE,IEQ,INE,FEQ,FNE====//
				//====FADD,FSUB,FMUL,FDIV,IADD,ISUB,IMUL,IDIV,IMODはここまで====//
				
				default: break;
			}

		}
		
		//spec命令の更新
		headspec.updateSpec(headformal, headvarcount);
		//jump命令の更新
		headjump.setArg2(headmemargs); //警告が気になる･･･
		headjump.setArg3(headatomargs);
        //ガード命令列がSPECとJUMPだけになった場合
        //jump先をボディ命令列にする
		if(guardsize == 2) {
			InstructionList insts = (InstructionList)guardjump.getArg1();
			headjump.setArg1(insts);
			//guard.remove(0);
			//guard.remove(0);
			//SPEC,JUMPは消すべき?
		}
		head.set(0, headspec);
		head.set(headsize-1, headjump);
	}
	
}