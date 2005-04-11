/*
 * 作成日: 2004/11/04
 */
package compile;

import java.util.*;
import runtime.Instruction;
//import runtime.InstructionList;


/**
 * @author sakurai
 *
 * ガード関係の最適化を行うメソッドを持つクラス。 
 *
 */
public class GuardOptimizer {
	/** 
	 * ガード命令を可能な限り前に移動させる.
	 * 元々ガード命令列にあった命令のみ移動させる
     */
	public static void guardMove(List head){
		int guardinstsstart = 0;
		for(int hid=1; hid<head.size()-1; hid++){
			Instruction insth = (Instruction) head.get(hid);
			switch(insth.getKind()){
				//ガード命令列の先頭にありそうな命令
				case Instruction.ALLOCATOM:
				case Instruction.DEREFATOM:
				case Instruction.GETLINK:
				case Instruction.NATOMS:
				case Instruction.NMEMS:
				case Instruction.NORULES:
					guardinstsstart = hid;
					hid = head.size();
					break;
				default: break;
			}
		}
		if(guardinstsstart != 0) //ガード命令列が空の場合は実行しない.
			for(int hid=guardinstsstart; hid<head.size()-1; hid++){
				Instruction insth = (Instruction)head.get(hid);
				ArrayList list = insth.getVarArgs();
				boolean moveOk = true;
				//ALLOCATOM命令は先頭のSPECの直後に移動
				if(insth.getKind() == Instruction.ALLOCATOM){
					head.remove(hid);
					head.add(1, insth);
				}
				else if(insth.getKind() == Instruction.NOT)
					continue;
				else 
					for(int hid2=hid-1; hid2>0; hid2--){
						Instruction insth2 = (Instruction)head.get(hid2);
						ArrayList list2 = insth2.getVarArgs();
						for(int i=0; i<list.size(); i++){
							if((insth2.getOutputType() != -1
							   && list.get(i).equals(insth2.getArg1()))
							   || !(orderConstraintCheck(insth, insth2, list.get(i)))){
							   	moveOk = false;
							   	break;
							   }
						}
						if(moveOk){
							head.remove(hid2+1); //移動対象の命令insthの位置はhid2+1
							head.add(hid2, insth); //hid2に移動
						}
						else break;
					}
			}
		}
	
	//inst1の1つ上の命令inst2が, inst1と同じ変数番号varを使用している場合,
	//inst1がinst2より前に出られるかをチェックする.
	//出られる場合true, 出られない場合falseを返す.
	//ISINT, ISFLOATは同じ変数番号を使う命令の中では優先順位が高い.
	//(IGTなどはISINTより後になる)
	private static boolean orderConstraintCheck(Instruction inst1, Instruction inst2, Object var){
		ArrayList list = inst2.getVarArgs();
		if(list.contains(var)){
			switch(inst2.getKind()){
				case Instruction.ISINT:
				case Instruction.ISFLOAT:
					return false;
				default: return true;
			}
		}
		else return true;
	}
	
	/*
	public static void guardMove(List head){
		//先頭がSPECかつ最後尾がJUMPでない場合失敗
		Instruction headspec = (Instruction)head.get(0);
		Instruction headjump = (Instruction)head.get(head.size()-1);
		if(headspec.getKind() != Instruction.SPEC) return;
		if(headjump.getKind() != Instruction.JUMP && 
		   headjump.getKind() != Instruction.PROCEED) return;
		
		for(int hid=1; hid<head.size()-1; hid++){
			Instruction insth = (Instruction)head.get(hid);
			boolean expflag = false;
			switch(insth.getKind()){
				//ALLOCATOMはSPECの直後まで押し上げていい
				case Instruction.ALLOCATOM:
					head.add(1, insth);
					head.remove(hid+1);
					break;
				//====ALLOCATOMはここまで====//
					
				//DEREFATOM
				case Instruction.DEREFATOM:
					for(int hid2=hid-1; hid2>0; hid2--){
						Instruction insth2 = (Instruction)head.get(hid2);
						switch(insth2.getKind()){
							case Instruction.FINDATOM:
							case Instruction.DEREF:
								if(insth.getIntArg2() == insth2.getIntArg1()){
									head.add(hid2+1, insth);
									head.remove(hid+1);
									hid2 = 0;
								}
								break;
							default: break;
						}
					}
					break;
				//====DEREFATOMはここまで====//
				
				//ISINT,ISFLOAT,ISSTRING,ISUNARY
				//これらは対応するDEREFATOMの下まで移動可能
				case Instruction.ISINT:
				case Instruction.ISFLOAT:
				case Instruction.ISSTRING:
				case Instruction.ISUNARY:
					for(int hid2=hid-1; hid2>0; hid2--){
						Instruction insth2 = (Instruction)head.get(hid2);
						if(insth2.getKind() == Instruction.DEREFATOM && insth.getIntArg1() == insth2.getIntArg1()){
							head.add(hid2+1, insth);
							head.remove(hid+1);
							break;
						}
					}
					break;
				//====ISINT,ISFLOAT,ISSTRING,ISUNARYはここまで====//
					
				//算術演算関係の命令
				case Instruction.FADD:	expflag = true;
				case Instruction.FSUB:	expflag = true;
				case Instruction.FMUL:	expflag = true;
				case Instruction.FDIV:	expflag = true;
				case Instruction.IADD:	expflag = true;
				case Instruction.ISUB:	expflag = true;
				case Instruction.IMUL:	expflag = true;
				case Instruction.IDIV:	expflag = true;
				case Instruction.IMOD:	expflag = true;
				
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
					int atomvar1 = 0;
					int atomvar2 = 0;
					if(expflag){
						atomvar1 = insth.getIntArg2();
						atomvar2 = insth.getIntArg3();
					} else {
						atomvar1 = insth.getIntArg1();
						atomvar2 = insth.getIntArg2();
					}
					//atomvar1,atomvar2のアトムの内1つ見つけたらtrueにする
					boolean flag = false;
					for(int hid2=1; hid2<hid; hid2++){
						Instruction insth2 = (Instruction)head.get(hid2);
						switch(insth2.getKind()){
							case Instruction.ALLOCATOM:
							case Instruction.ALLOCATOMINDIRECT:
							case Instruction.FADD:
							case Instruction.FSUB:
							case Instruction.FMUL:
							case Instruction.FDIV:
							case Instruction.IADD:
							case Instruction.ISUB:
							case Instruction.IMUL:
							case Instruction.IDIV:
							case Instruction.IMOD:
							case Instruction.ISINT:
							case Instruction.ISFLOAT:
								int dstatom = insth2.getIntArg1();
								//X*X<Yのような命令があった場合atomvar1=atomvar2となる
								if(dstatom == atomvar1 && dstatom == atomvar2){
									head.add(hid2+1, insth);
									head.remove(hid+1);
									hid2 = hid;
									break;
								}
								else if(dstatom == atomvar1 || dstatom == atomvar2){
									if(!flag){
										flag = true;
										break;
									} else {
										head.add(hid2+1, insth);
										head.remove(hid+1);
										hid2 = hid;
										break;
									}
								}
								break;
							default: break;
						}
					}
					break;
					//====算術演算関係の命令はここまで====//
				
				//GETFUNCはガードの中で一時的な変数を扱う時に使用される
				//例：a(X),a(Y) :- A=X+Y,A<10 | ok.
				//GETFUNCとALLOCATOMINDIRECTはセットのように思えるがとりあえず分けて移動させる
				case Instruction.GETFUNC:
					//探すのはALLOCATOM 例：a(X) :- A=4,X<A | ok.(意味無さそうだが)
					//ALLOCATOMINDIRECT a(X) :- A=X,B=A,B<3 | ok.
					//DEREFATOM a(X) :- A=X,A<4 | ok.
					//算術演算 a(X) :- A=X+5,A<10 | ok.
					//どれにしろ参照するのは第1引数
					for(int hid2=1; hid2<hid; hid2++){
						Instruction insth2 = (Instruction)head.get(hid2);
						switch(insth2.getKind()){
							case Instruction.ALLOCATOM:
							case Instruction.ALLOCATOMINDIRECT:
							case Instruction.DEREFATOM:
							case Instruction.FADD:
							case Instruction.FSUB:
							case Instruction.FMUL:
							case Instruction.FDIV:
							case Instruction.IADD:
							case Instruction.ISUB:
							case Instruction.IMUL:
							case Instruction.IDIV:
							case Instruction.IMOD:
								int dstatom = insth.getIntArg1();
								if(insth.getIntArg2() == insth2.getIntArg1()){
									head.add(hid2+1, insth);
									head.remove(hid+1);
									hid2 = hid;
								}
								break;
							default: break;
						}
					}
					
					case Instruction.ALLOCATOMINDIRECT:
						for(int hid2=1; hid2<hid; hid2++){
							Instruction insth2 = (Instruction)head.get(hid2);
							if(insth2.getKind() == Instruction.GETFUNC
							  && insth.getIntArg2() == insth2.getIntArg1()){
							  	head.add(hid2+1, insth);
							  	head.remove(hid+1);
							  	break;
							  }
						}
						break;
					//====GETFUNC,ALLOCATOMINDIRECT====//
					
					default: break;
					
			}
			
		}
	}*/
	
}