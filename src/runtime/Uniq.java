package runtime;

import java.util.ArrayList;

import runtime.AbstractMembrane;
import runtime.Membrane;

public class Uniq {
	/**
	 * 型付きプロセス文脈に束縛された構造へのリンクを保存する配列。
	 * 要素は Link[] 型
	 */
	public ArrayList history = new ArrayList();
	
	/**
	 * 型付きプロセス文脈に束縛された構造の実態を保存する膜。タスク==null
	 */
	public AbstractMembrane mem = new Membrane();
	
	/**
	 * el が history に含まれるかどうか検査する。
	 * 含まれるなら false を返す
	 * 含まれなければ新たに追加して true を返す
	 * @param el
	 * @return
	 */
	public boolean check(Link[] el) {
		for(int i=0;i<history.size();i++) {
			int NG=0;
			for(int j=0;j<el.length;j++) {
				Link[] aH = (Link[])history.get(i);
				if(aH[j].eqGround(el[j])) NG++;
			}
			if(NG==el.length) return false;
		}
		// コピーして履歴を保存
		for(int i=0;i<el.length;i++) {
			el[i] = mem.copyGroundFrom(el[i]);
			// この３行はなくても動くが、履歴が入った膜を dump しようとすると必要になる。
			Atom dummy = mem.newAtom(new Functor("hist_"+history.size()+"_"+i, 1));
			dummy.args[0] = new Link(dummy, 0);
			mem.unifyLinkBuddies(dummy.getArg(0), el[i]);
		}
		history.add(el);
//		Env.p("MEM>> "+Dumper.dump(mem, false));
		return true;
	}
}
