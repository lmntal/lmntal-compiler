package runtime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Uniq {
	/**
	 * 型付きプロセス文脈に束縛された構造へのリンクを保存する配列。
	 * 要素は Link[] 型
	 */
	public ArrayList<Link[]> history = new ArrayList<Link[]>();
	public HashSet<String> historyH = new HashSet<String>();
	
	/**
	 * 型付きプロセス文脈に束縛された構造の実体を保存する膜。タスク==null
	 */
	public Membrane mem = new Membrane();
	
	/**
	 * el が history に含まれるかどうか検査する。
	 * 含まれるなら false を返す
	 * 含まれなければ新たに追加して true を返す
	 * @param el
	 * @return
	 */
	public boolean checkOld(Link[] el) {
		// しょぼい実装 O(N)
		for(int i=0;i<history.size();i++) {
			int NG=0;
			for(int j=0;j<el.length;j++) {
				Link[] aH = history.get(i);
				if(aH[j].eqGround(el[j])) NG++;
			}
			if(NG==el.length) return false;
		}
		// コピーして履歴を保存
		for(int i=0;i<el.length;i++) {
			el[i] = mem.copyGroundFrom(el[i]);
			// この３行はなくても動くが、履歴が入った膜を dump しようとすると必要になる。
			Atom dummy = mem.newAtom(new SymbolFunctor("hist_"+history.size()+"_"+i, 1));
			dummy.args[0] = new Link(dummy, 0);
			mem.unifyLinkBuddies(dummy.getArg(0), el[i]);
		}
		history.add(el);
//		Env.p("MEM>> "+Dumper.dump(mem, false));
		return true;
	}
	/**
	 * el が history に含まれるかどうか検査する。
	 * 含まれるなら false を返す
	 * 含まれなければ新たに追加して true を返す
	 * 
	 * (2006/09/29 kudo)
	 * 2引数以上に対応。2引数以上なので、elはリンクの配列ではなく、リンクリストの配列にした。
	 * 
	 * @param el
	 * @return
	 */
//	public boolean check(Link[] el) {
//		// O(1) のはず
//		StringBuffer cur=new StringBuffer();
//		for(int j=0;j<el.length;j++) {
//			cur.append(el[j].groundString());
//			cur.append(":");
//		}
//		String curI = cur.toString();
////		System.out.println(historyH);
//		if(historyH.contains(curI)) return false;
//		historyH.add(curI);
//		return true;
//	}
	public boolean check(List<Link>[] el) {
		// O(1) のはず
		StringBuffer cur=new StringBuffer();
		for(int j=0;j<el.length;j++) {
			cur.append(Link.groundString(el[j]));
			cur.append(":");
		}
		String curI = cur.toString();
//		System.out.println(historyH);
		if(historyH.contains(curI)) return false;
		historyH.add(curI);
		return true;
	}

}
