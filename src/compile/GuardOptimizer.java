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
                if(insth.getKind() == Instruction.NOT)
					continue;
				else 
					for(int hid2=hid-1; hid2>0; hid2--){
						Instruction insth2 = (Instruction)head.get(hid2);
						ArrayList list2 = insth2.getVarArgs();
						for(int i=0; i<list.size(); i++){
							if((insth2.getOutputType() != -1
							   && list.get(i).equals(insth2.getArg1()))
							   || list2.contains(list.get(i))){
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
}