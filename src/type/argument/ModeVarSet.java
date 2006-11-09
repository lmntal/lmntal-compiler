package type.argument;

import java.util.HashSet;
import java.util.Set;

import type.TypeException;

/**
 * モード変数を管理する
 * @author kudo
 *
 */
public class ModeVarSet {
	
	private Set<ModeVar> modes;
	
	public ModeVarSet(){
		modes = new HashSet<ModeVar>();
	}
	
	/**
	 * 与えられたパスのモード変数を返す
	 * @param path
	 * @return
	 */
	private ModeVar getModeVarOfPath(Path path){
		for(ModeVar ms : modes)
			if(ms.contains(path))return ms;
		return null;
	}
	
	/**
	 * 与えられたモード変数２つが同じモードを表すことを指す。ms2はms1に取り込まれる。
	 * @param ms1
	 * @param ms2
	 */
	public void merge(ModeVar ms1, ModeVar ms2){
		ms1.addAll(ms2);
		ms1.buddy.addAll(ms2.buddy);
		modes.remove(ms2);
		modes.remove(ms2.buddy);
	}
	
	/**
	 * ２つのパスについてのモード関係を登録する
	 * @param sign
	 * @param p1
	 * @param p2
	 * @throws TypeException
	 */
	public void add(int sign, Path p1, Path p2)throws TypeException{
		// 同一パスに対しモードが逆に設定されている
		if((sign == -1) && p1.equals(p2))
			throw new TypeException("mode error (same path with in/out) : " + p1 + " <=> " + p2);
		ModeVar ms1 = getModeVarOfPath(p1);
		ModeVar ms2 = getModeVarOfPath(p2);
		// 両方に既にモード変数が振られている
		if(ms1!=null && ms2!=null){
			// 符号が等しい時
			if(sign==1){
				// 振られたモード変数が同じなら何もしない
				if(ms1==ms2)return;
				// 振られたモード変数が相方なら衝突
				else if(ms1==ms2.buddy)
					throw new TypeException("mode error :" + p1 + " <=> " + p2);
				else merge(ms1, ms2);
			}
			// 符号が逆なら
			else if(sign==-1){
				// 振られたモード変数が相方なら何もしない
				if(ms1==ms2.buddy)return;
				// 振られたモード変数が同じなら衝突
				else if(ms1==ms2)
					throw new TypeException("mode error :" + p1 + " <=> " + p2);
				else merge(ms1, ms2.buddy);
			}
		}
		//片方しか振られていない場合、もう片方は相方を振る
		else if(ms1!=null && ms2==null){
			if(sign==1)ms1.add(p2);
			else ms1.buddy.add(p2);
		}
		else if(ms2!=null && ms1==null){
			if(sign==1)ms2.add(p1);
			else ms2.buddy.add(p1);
		}
		//両方振られていない場合、新しいものを作る
		else{
			ModeVar nms = newModeVar();
			nms.add(p1);
			if(sign==1)nms.add(p2);
			else nms.buddy.add(p2);
		}
	}
	
	public ModeVar getModeVar(Path path)throws TypeException{
		ModeVar ms = getModeVarOfPath(path);
		if(ms==null){
			ms = newModeVar();
			ms.add(path);
		}
		return ms;
	}
	
	private static int modeid = 0;
	private String newModeVarName(){
		return /*"'" +*/ "m" + (modeid++);
	}
	
	/**
	 * 新規にモード変数の組を作成し、その片方を返す
	 * @return
	 */
	public ModeVar newModeVar(){
		String nm = newModeVarName();
		ModeVar ms1 = new ModeVar(nm,1);
		ModeVar ms2 = new ModeVar(nm,-1);
		ms1.buddy = ms2;
		ms2.buddy = ms1;
		modes.add(ms1);
		modes.add(ms2);
		return ms1;
	}
	
}
