package runtime;

import java.util.*;

public final class Rule {
	// Instruction のリスト
	
	/** アトム主導ルール適用の命令列。先頭の命令はspec[2,*]でなければならない。*/
	public List atomMatch;
	/** 膜主導ルール適用の命令列。先頭の命令はspec[1,*]でなければならない。*/
	public List memMatch;
	
	/** ガード命令列（guardLabel.insts）またはnull */
	public List guard;
	/** ボディ命令列（bodyLabel.insts）またはnull */
	public List body;
	/** ラベル付きガード命令列またはnull */
	public InstructionList guardLabel;
	/** ラベル付きボディ命令列またはnull */
	public InstructionList bodyLabel;
	/** このルールの表示用文字列 */
	private String text;
	
	//
	
	/**
	 * ふつうのコンストラクタ。
	 *
	 */
	public Rule() {
		atomMatch = new ArrayList();
		memMatch  = new ArrayList();
	}
	/**
	 * ルール文字列つきコンストラクタ
	 * @param text ルールの文字列表現
	 */
	public Rule(String text) {
		this.text = text;
	}
	
	/**
	 * 命令列の詳細を出力する
	 *
	 */
	public void showDetail() {
		Iterator l;
		Env.d("Compiled Rule " + this);
		
		/*
		l = atomMatch.listIterator();
		Env.d("--atommatches:", 1);
		while(l.hasNext()) {
			Iterator ll = ((List)l.next()).iterator();
			while(ll.hasNext()) Env.d(indent+(Instruction)ll.next());
		}
		*/
		
	
		l = atomMatch.listIterator();
		Env.d("--atommatch:", 1);
		while(l.hasNext()) Env.d((Instruction)l.next(), 2);

		l = memMatch.listIterator();
		Env.d("--memmatch:", 1);
		while(l.hasNext()) Env.d((Instruction)l.next(), 2);
		
		if (guard != null) {
			l = guard.listIterator();
			Env.d("--guard:" + guardLabel + ":", 1);
			while(l.hasNext()) Env.d((Instruction)l.next(), 2);
		}
		
		if (body != null) {
			l = body.listIterator();
			Env.d("--body:" + bodyLabel + ":", 1);
			while(l.hasNext()) Env.d((Instruction)l.next(), 2);
		}
			
		Env.d("");
	}
	
	public String toString() {
		return text;
	}
}
