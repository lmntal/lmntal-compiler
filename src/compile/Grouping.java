/*
 * 作成日: 2004/11/10
 */
package compile;

import java.util.*;
import runtime.Instruction;
import runtime.InstructionList;
/**
 * @author sakurai
 *
 * ヘッド命令列をアトムグループごとに分ける
 * group[ findatom
 *        deref
 *        func
 *        insint ]
 * group [･･･]
 * のような形になる
 * 今のところガード命令がアトムグループの内外にうまく移動しているか確認するくらいしか利用価値なし
 */
public class Grouping {
	
	public static void atomGroup(List head){
		//おなじみのspec,jump検査
		//ガード最適化時に終わっているので要らない?
		Instruction headspec = (Instruction)head.get(0);
		if (headspec.getKind() != Instruction.SPEC) return;
		
		int headsize = head.size();
		Instruction headjump = (Instruction)head.get(headsize-1);
		if (headjump.getKind() != Instruction.JUMP) return;
				
		//FINDATOMの検索
		int firstgrouppoint = 0;
		for(int hid=1; hid<headsize; hid++){
			Instruction insth = (Instruction)head.get(hid);
			int subinstssize = 0;
			if(insth.getKind() == Instruction.FINDATOM){
				//アトムグループ外のガード命令列
				List outinsts = new ArrayList();
				//最初のグループのある位置。これより上にはallocatomなどがある
				if(firstgrouppoint == 0) firstgrouppoint = hid;
				//アトムグループ内のガード命令列
				InstructionList subinsts = new InstructionList();
				subinstssize += 1;
				
				//GROUPの終わりを検索
				for(int hid2=hid+1; hid2<headsize; hid2++){
					Instruction insth2 = (Instruction)head.get(hid2);
					boolean expflag = false;
					switch(insth2.getKind()){
						//FINDATOMかJUMPを見つけたらGROUP終わり
						case Instruction.FINDATOM:
						case Instruction.JUMP:
							hid2 = headsize; //無理矢理forループから脱出
						break; //ちなみにこのbreakはswitch文用。
						
						//2引数以上のガード命令の場合グループを跨るかチェックする必要がある
						//まずは四則演算や比較演算系
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
						case Instruction.SAMEFUNC:
							//例：FADD[resultvar,atom1,atom2]
							//    FLT[atom1,atom2]
							int atom1 = 0;
							int atom2 = 0;
							if(expflag){
								atom1 = insth2.getIntArg2();
								atom2 = insth2.getIntArg3();
							} else {
								atom1 = insth2.getIntArg1();
								atom2 = insth2.getIntArg2();
							}
							//GROUP内にatom1,atom2があるかどうかを確認する
							//2つともある、もしくはどちらか一方でも定数のアトムならアトムグループを跨ることはない
							boolean flag1 = false;
							boolean flag2 = false;

							//ALLOCATOMはSPEC命令の直後に固まっている (GROUPの外)
							//またFADDなどの定数を生むアトムもこの付近にある可能性もある
							//(ガードに1+1<3とか書いた場合など。書くなと言いたいところだが)
							//ここでの検索ではatom1,atom2のどちらかがグループとは無関係の定数アトムであることを確認する
							//もしそうならinsth2がアトムグループを跨る命令であることはない
							//atom1,atom2が両方これに当てはまるならinsth2は最初のGROUP命令より前にあるはず
							for(int hid2b=1; hid2b<firstgrouppoint; hid2b++){
								Instruction insth2b = (Instruction)head.get(hid2b);
								switch(insth2b.getKind()){
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
										int atomid = insth2b.getIntArg1();
										if(atomid == atom1 || atomid == atom2){
											if(!flag1) {
												flag1 = true;
												hid2b = firstgrouppoint;
												break;
											}
										} 
										break;
									
									default: break;
								}
							}

							//GROUP内の検索
							for(int hid2b=hid2-1; hid2b>=hid; hid2b--){
								Instruction insth2b = (Instruction)head.get(hid2b);
								switch(insth2b.getKind()){
									case Instruction.ALLOCATOMINDIRECT:
									case Instruction.ISINT:
									case Instruction.ISFLOAT:
									case Instruction.FADD:
									case Instruction.FSUB:
									case Instruction.FMUL:
									case Instruction.FDIV:
									case Instruction.IADD:
									case Instruction.ISUB:
									case Instruction.IMUL:
									case Instruction.IDIV:
									case Instruction.IMOD:	
										int atomid = insth2b.getIntArg1();
										if(atomid == atom1 && atomid == atom2){
											subinstssize += 1;
											hid2b = hid;	
											break;	
										}
										else if(atomid == atom1 || atomid == atom2){
											if(!flag1) flag1 = true;
											else {
												flag2 = true;
												subinstssize += 1;
												hid2b = hid;	
												break;							
											}
										}
										break;
										
										default: break;
								}
							}
							//グループ外に移す命令があった場合
							if(!flag2) {
								outinsts.add(insth2);
								head.remove(hid2);
								headsize -= 1;
								hid2 -= 1;
							}
							break;
							//====四則演算･比較演算系はここまで====//
							
							//GETFUNC命令の場合同じグループ内に
							//GETFUNC命令の第2引数を第1引数に持つ命令(IADD等)があればいい
							case Instruction.GETFUNC:
								flag2 = false;
								for(int hid2b=hid2-1; hid2b>=hid; hid2b--){
									Instruction insth2b = (Instruction)head.get(hid2b);
									switch(insth2b.getKind()){
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
											if(insth2b.getIntArg1() == insth2.getIntArg2()){
												subinstssize += 1;
												hid2b = hid;
												flag2 = true;	
												break;											
											}
										default: break;
									}
								}
								if(!flag2){
									outinsts.add(insth2);
									head.remove(hid2);
									headsize -= 1;
									hid2 -= 1;
								}
								break;
							//====GETFUNC命令はここまで====//
							
							//ALLOCATOMINDIRECTはGETFUNCとセットで動かしてもいいかもしれない
							case Instruction.ALLOCATOMINDIRECT:
								flag2 = false;
								for(int hid2b=hid2-1; hid2b<hid; hid2b--){
									Instruction insth2b = (Instruction)head.get(hid2b);
									if(insth2b.getKind() == Instruction.GETFUNC
									   && insth2b.getIntArg1() == insth2.getIntArg2()){
									   		subinstssize += 1;
									   		flag2 = true;
									   		break;
									   }
								}
								if(!flag2){
									outinsts.add(insth2);
									head.remove(hid2);
									headsize -= 1;
									hid2 -= 1;
								}
								break;
							//====ALLOCATOMINDIRECTはここまで====//
								
							//GROUP内の命令の追加
							default:
								subinstssize += 1;
								break;
					}									
					
				}
				
				//FINDATOMのあった位置にGROUP命令を生成する
				for(int s=0; s<subinstssize; s++){
					subinsts.add((Instruction)head.get(hid));
					head.remove(hid);
					headsize -= 1;
				}
				head.add(hid, new Instruction(Instruction.GROUP, subinsts));
				headsize += 1;
				//グループ外にガード命令を移す場合
				if(outinsts != null){
					int outinstssize = outinsts.size();
					for(int i=0; i<outinstssize; i++){
						head.add(hid+1+i, outinsts.get(i));
						headsize += 1;
					}
				}
				
			}
			//ここまでで1GROUP生成の処理終了
			
		}
		
	}

}
