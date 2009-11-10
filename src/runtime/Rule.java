package runtime;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class Rule implements Serializable {
	// Instruction のリスト
	
	/** アトム主導ルール適用の命令列（atomMatchLabel.insts）
	 * 先頭の命令はspec[2,*]でなければならない。*/
	public List<Instruction> atomMatch;
	/** 膜主導ルール適用の命令列（memMatchLabel.insts）
	 * 先頭の命令はspec[1,*]でなければならない。*/
	public List<Instruction> memMatch;
	/** コンパイル時専用膜主導ルール適用の命令列 */
	public List<Instruction> tempMatch;
	
	/** ガード命令列（guardLabel.insts）またはnull。
	 * 先頭の命令はspec[*,*]でなければならない。*/
	public List<Instruction> guard;
	/** ボディ命令列（bodyLabel.insts）またはnull。
	 * 先頭の命令はspec[*,*]でなければならない。*/
	public List<Instruction> body;
	
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
	
	/** スレッドごとのベンチマーク結果 **/
	public HashMap<Thread, Benchmark> bench;
	
	/** ルール名 */
	public String name;
	
	/** 行番号 by inui */
	public int lineno;
	
	/** 履歴 */
	public Uniq uniq;
	
	/** uniq制約を持つかどうか */
	public boolean hasUniq = false;
	
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
		bench = new HashMap<Thread, Benchmark>();
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
		
		Iterator<Instruction> l;
		if (!hasUniq) Env.p("Compiled Rule " + this);
		else Env.p("Compiled Uniq Rule " + this);
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
	
	///////////////////////////////////////////////////////////////////

	/* アトム手動テストの試行回数 */
	public long atomapply = 0;
	/* アトム手動テストの成功回数 */
	public long atomsucceed = 0;
	/* アトム手動テストの合計時間 */
	public long atomtime = 0;
	/* 膜手動テストの試行回数 */
	public long memapply = 0;
	/* 膜手動テストの成功回数 */
	public long memsucceed = 0;
	/* 膜手動テストの合計時間 */
	public long memtime = 0;
	/* ルール適用のバックトラック回数 */
	public long backtracks = 0;
	/* ルール適用時の膜ロック失敗の回数 */
	public long lockfailure = 0;
	
	public void incAtomApply(Thread thread){
		if(bench.containsKey(thread))
			bench.get(thread).atomapply ++;
		else {
			Benchmark benchmark = new Benchmark(thread);
			benchmark.atomapply ++;
			bench.put(thread, benchmark);
		}
	}
	public void incAtomSucceed(Thread thread){
		if(bench.containsKey(thread))
			bench.get(thread).atomsucceed ++;
		else {
			Benchmark benchmark = new Benchmark(thread);
			benchmark.atomsucceed ++;
			bench.put(thread, benchmark);
		}
	}
	public void setAtomTime(long value, Thread thread){
		if(bench.containsKey(thread))
			bench.get(thread).atomtime += value;
		else {
			Benchmark benchmark = new Benchmark(thread);
			benchmark.atomtime += value;
			bench.put(thread, benchmark);
		}
	}
	public void incMemApply(Thread thread){
		if(bench.containsKey(thread))
			bench.get(thread).memapply ++;
		else {
			Benchmark benchmark = new Benchmark(thread);
			benchmark.memapply ++;
			bench.put(thread, benchmark);
		}
	}
	public void incMemSucceed(Thread thread){
		if(bench.containsKey(thread))
			bench.get(thread).memsucceed ++;
		else {
			Benchmark benchmark = new Benchmark(thread);
			benchmark.memsucceed ++;
			bench.put(thread, benchmark);
		}
	}
	public void setMemTime(long value, Thread thread){
		if(bench.containsKey(thread))
			bench.get(thread).memtime += value;
		else {
			Benchmark benchmark = new Benchmark(thread);
			benchmark.memtime += value;
			bench.put(thread, benchmark);
		}
	}
	public void setBackTracks(long value, Thread thread){
		if(bench.containsKey(thread))
			bench.get(thread).backtracks += value;
		else {
			Benchmark benchmark = new Benchmark(thread);
			benchmark.backtracks += value;
			bench.put(thread, benchmark);
		}
	}
	public void setLockFailure(long value, Thread thread){
		if(bench.containsKey(thread))
			bench.get(thread).lockfailure += value;
		else {
			Benchmark benchmark = new Benchmark(thread);
			benchmark.lockfailure += value;
			bench.put(thread, benchmark);
		}
	}
	
	public long allAtomApplys() {
		Iterator<Benchmark> its = bench.values().iterator();
		long apply = 0;
		while(its.hasNext()) {
			Benchmark bench = its.next();
			apply += bench.atomapply;
		}
		return apply;
	}
	public long allMemApplys() {
		Iterator<Benchmark> its = bench.values().iterator();
		long apply = 0;
		while(its.hasNext()) {
			Benchmark bench = its.next();
			apply += bench.memapply;
		}
		return apply;
	}
	
	public long allAtomSucceeds() {
		Iterator<Benchmark> its = bench.values().iterator();
		long succeed = 0;
		while(its.hasNext()) {
			Benchmark bench = its.next();
			succeed += bench.atomsucceed;
		}
		return succeed;
	}
	
	public long allMemSucceeds() {
		Iterator<Benchmark> its = bench.values().iterator();
		long succeed = 0;
		while(its.hasNext()) {
			Benchmark bench = its.next();
			succeed += bench.memsucceed;
		}
		return succeed;
	}
	
	public long allAtomTimes() {
		Iterator<Benchmark> its = bench.values().iterator();
		long time = 0;
		while(its.hasNext()) {
			Benchmark bench = its.next();
			time += bench.atomtime;
		}
		return time;
	}

	public long allMemTimes() {
		Iterator<Benchmark> its = bench.values().iterator();
		long time = 0;
		while(its.hasNext()) {
			Benchmark bench = its.next();
			time += bench.memtime;
		}
		return time;
	}
	
	public long allApplys() {
		return allAtomApplys() + allMemApplys();
	}
	public long allSucceeds() {
		return allAtomSucceeds() + allMemSucceeds();
	}
	public long allTimes() {
		return allAtomTimes() + allMemTimes();
	}
	
	public long allBackTracks() {
		Iterator<Benchmark> its = bench.values().iterator();
		long backtracks = 0;
		while(its.hasNext()) {
			Benchmark bench = its.next();
			backtracks += bench.backtracks;
		}
		return backtracks;
	}
	public long allLockFailures() {
		Iterator<Benchmark> its = bench.values().iterator();
		long lockfailure = 0;
		while(its.hasNext()) {
			Benchmark bench = its.next();
			lockfailure += bench.lockfailure;
		}
		return lockfailure;
	}
}

class Benchmark {
	
	/* スレッドのID */
	public long threadid;
	/* アトム手動テストの試行回数 */
	public long atomapply = 0;
	/* アトム手動テストの成功回数 */
	public long atomsucceed = 0;
	/* アトム手動テストの合計時間 */
	public long atomtime = 0;
	/* 膜手動テストの試行回数 */
	public long memapply = 0;
	/* 膜手動テストの成功回数 */
	public long memsucceed = 0;
	/* 膜手動テストの合計時間 */
	public long memtime = 0;
	/* ルール適用のバックトラック回数 */
	public long backtracks = 0;
	/* ルール適用時の膜ロック失敗の回数 */
	public long lockfailure = 0;

	Benchmark(Thread thread) {
		this.threadid = (Env.majorVersion == 1 && Env.minorVersion > 4) 
						? thread.getId() : thread.hashCode();
	}
}