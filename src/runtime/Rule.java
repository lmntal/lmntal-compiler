package runtime;

import java.util.*;
import java.io.*;

public final class Rule implements Serializable {
	// Instruction のリスト
	
	/** アトム主導ルール適用の命令列（atomMatchLabel.insts）
	 * 先頭の命令はspec[2,*]でなければならない。*/
	public List atomMatch;
	/** 膜主導ルール適用の命令列（memMatchLabel.insts）
	 * 先頭の命令はspec[1,*]でなければならない。*/
	public List memMatch;
	
	/** ガード命令列（guardLabel.insts）またはnull。
	 * 先頭の命令はspec[*,*]でなければならない。*/
	public List guard;
	/** ボディ命令列（bodyLabel.insts）またはnull。
	 * 先頭の命令はspec[*,*]でなければならない。*/
	public List body;
	
	/** ラベル付きアトム主導ルール適用命令列 */
	public InstructionList atomMatchLabel;
	/** ラベル付き膜主導ルール適用命令列 */
	public InstructionList memMatchLabel;	
	/** ラベル付きガード命令列またはnull */
	public InstructionList guardLabel;
	/** ラベル付きボディ命令列またはnull */
	public InstructionList bodyLabel;
	/** このルールの表示用文字列 */
	public String text = "";
	/** このルールの表示用文字列（省略なし） */
	public String fullText ="";
	
	/** ルール名 */
	public String name;
	
	/** 行番号 by inui */
	public int lineno;
	
	/* ルール適用回数 */
	public int apply = 0;
	/* ルール適用の成功回数 */
	public int succeed = 0;
	/* ルール適用の合計時間 */
	public long time = 0;
	
	/** 履歴 */
	public Uniq uniq;
	
	// todo いずれ4つともInstructionListで保持するようにし、Listは廃止する。
	
	/**
	 * ふつうのコンストラクタ。
	 *
	 */
	public Rule() {
//		atomMatch = new ArrayList();
//		memMatch  = new ArrayList();
		atomMatchLabel = new InstructionList();
		memMatchLabel = new InstructionList();
		atomMatch = atomMatchLabel.insts;
		memMatch = memMatchLabel.insts;
	}
	/**
	 * ルール文字列つきコンストラクタ
	 * @param text ルールの文字列表現
	 */
	public Rule(String text) {
		this();
		this.text = text;
	}
	/**
	 * ルール文字列（省略なし）つきコンストラクタ
	 * @param text ルールの文字列表現
	 * @param fullText ルールの文字列表現（省略なし）
	 */
	public Rule(String text, String fullText) {
		this(text);
		this.fullText = fullText;
	}
	/** パーザーで利用するコンストラクタ */
	public Rule(InstructionList atomMatchLabel, InstructionList memMatchLabel, InstructionList guardLabel, InstructionList bodyLabel) {
		this.atomMatchLabel = atomMatchLabel;
		this.memMatchLabel = memMatchLabel;
		this.guardLabel = guardLabel;
		this.bodyLabel = bodyLabel;
		atomMatch = atomMatchLabel.insts;
		memMatch = memMatchLabel.insts;
		if (guardLabel != null)
			guard = guardLabel.insts;
		if (bodyLabel != null)
			body = bodyLabel.insts;
	}
	
	/**
	 * 命令列の詳細を出力する
	 *
	 */
	public void showDetail() {
		if (Env.debug == 0 && !Env.compileonly) return;
		
		Iterator l;
		Env.p("Compiled Rule " + this);
		
		l = atomMatch.listIterator();
		Env.p("--atommatch:", 1);
		while(l.hasNext()) Env.p((Instruction)l.next(), 2);

		l = memMatch.listIterator();
		Env.p("--memmatch:", 1);
		while(l.hasNext()) Env.p((Instruction)l.next(), 2);
		
		if (guard != null) {
			l = guard.listIterator();
			Env.p("--guard:" + guardLabel + ":", 1);
			while(l.hasNext()) Env.p((Instruction)l.next(), 2);
		}
		
		if (body != null) {
			l = body.listIterator();
			Env.p("--body:" + bodyLabel + ":", 1);
			while(l.hasNext()) Env.p((Instruction)l.next(), 2);
		}
			
		Env.p("");
	}
	
	public String toString() {
//		return text;
		if (Env.compileonly) return "";
//		if (Env.compileonly) return (name!=null) ? name : "";
		return name!=null && !name.equals("") ? name : text;
//		return name;
	}
	
	/**
	 * @return fullText ルールのコンパイル可能な文字列表現
	 */
	public String getFullText() {
		return fullText;
	}
}
