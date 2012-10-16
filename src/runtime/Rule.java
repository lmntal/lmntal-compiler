package runtime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Rule
{
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
	public String fullText = "";

	/** スレッドごとのベンチマーク結果 **/
	public Map<Thread, Benchmark> bench;

	/** ルール名 */
	public String name;

	/** 行番号 by inui */
	public int lineno;

	/** uniq制約を持つかどうか */
	public boolean hasUniq = false;

	// todo いずれ4つともInstructionListで保持するようにし、Listは廃止する。

	/**
	 * ふつうのコンストラクタ。
	 */
	public Rule()
	{
		this("");
	}

	/**
	 * ルール文字列つきコンストラクタ
	 * @param text ルールの文字列表現
	 */
	public Rule(String text)
	{
		this(text, "");
	}

	/**
	 * ルール文字列（省略なし）つきコンストラクタ
	 * @param text ルールの文字列表現
	 * @param fullText ルールの文字列表現（省略なし）
	 */
	public Rule(String text, String fullText)
	{
		this.text = text;
		this.fullText = fullText;
		atomMatchLabel = new InstructionList();
		memMatchLabel = new InstructionList();
		atomMatch = atomMatchLabel.insts;
		memMatch = memMatchLabel.insts;
		bench = new HashMap<Thread, Benchmark>();
	}

	/**
	 * パーザーで利用するコンストラクタ
	 */
	public Rule(InstructionList atomMatchLabel, InstructionList memMatchLabel, InstructionList guardLabel, InstructionList bodyLabel)
	{
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
	 */
	public void showDetail()
	{
		if (Env.debug == 0 && !Env.compileonly) return;

		if (hasUniq && Env.slimcode)
		{
			Env.p("Compiled Uniq Rule " + this);
		}
		else
		{
			Env.p("Compiled Rule " + this);
		}

		Env.p("--atommatch:", 1);
		printInstructions(atomMatch);

		Env.p("--memmatch:", 1);
		printInstructions(memMatch);

		if (guard != null)
		{
			Env.p("--guard:" + guardLabel + ":", 1);
			printInstructions(guard);
		}

		if (body != null)
		{
			Env.p("--body:" + bodyLabel + ":", 1);
			printInstructions(body);
		}

		Env.p("");
	}

	private static void printInstructions(Iterable<Instruction> insts)
	{
		for (Instruction inst : insts)
		{
			Env.p(inst, 2);
		}
	}

	public String toString()
	{
		if (Env.compileonly) return "";
		return name != null && !name.equals("") ? name : text;
	}

	/**
	 * @return fullText ルールのコンパイル可能な文字列表現
	 */
	public String getFullText()
	{
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

	public void incAtomApply(Thread thread)
	{
		if (bench.containsKey(thread))
		{
			bench.get(thread).atomapply++;
		}
		else
		{
			Benchmark benchmark = new Benchmark(thread);
			benchmark.atomapply++;
			bench.put(thread, benchmark);
		}
	}

	public void incAtomSucceed(Thread thread)
	{
		if (bench.containsKey(thread))
		{
			bench.get(thread).atomsucceed++;
		}
		else
		{
			Benchmark benchmark = new Benchmark(thread);
			benchmark.atomsucceed++;
			bench.put(thread, benchmark);
		}
	}

	public void setAtomTime(long value, Thread thread)
	{
		if (bench.containsKey(thread))
		{
			bench.get(thread).atomtime += value;
		}
		else
		{
			Benchmark benchmark = new Benchmark(thread);
			benchmark.atomtime += value;
			bench.put(thread, benchmark);
		}
	}

	public void incMemApply(Thread thread)
	{
		if (bench.containsKey(thread))
		{
			bench.get(thread).memapply++;
		}
		else
		{
			Benchmark benchmark = new Benchmark(thread);
			benchmark.memapply++;
			bench.put(thread, benchmark);
		}
	}

	public void incMemSucceed(Thread thread)
	{
		if (bench.containsKey(thread))
		{
			bench.get(thread).memsucceed++;
		}
		else
		{
			Benchmark benchmark = new Benchmark(thread);
			benchmark.memsucceed++;
			bench.put(thread, benchmark);
		}
	}

	public void setMemTime(long value, Thread thread)
	{
		if (bench.containsKey(thread))
		{
			bench.get(thread).memtime += value;
		}
		else
		{
			Benchmark benchmark = new Benchmark(thread);
			benchmark.memtime += value;
			bench.put(thread, benchmark);
		}
	}

	public void setBackTracks(long value, Thread thread)
	{
		if (bench.containsKey(thread))
		{
			bench.get(thread).backtracks += value;
		}
		else
		{
			Benchmark benchmark = new Benchmark(thread);
			benchmark.backtracks += value;
			bench.put(thread, benchmark);
		}
	}

	public void setLockFailure(long value, Thread thread)
	{
		if (bench.containsKey(thread))
		{
			bench.get(thread).lockfailure += value;
		}
		else
		{
			Benchmark benchmark = new Benchmark(thread);
			benchmark.lockfailure += value;
			bench.put(thread, benchmark);
		}
	}

	public long allAtomApplys()
	{
		long apply = 0;
		for (Benchmark b : bench.values())
		{
			apply += b.atomapply;
		}
		return apply;
	}

	public long allMemApplys()
	{
		long apply = 0;
		for (Benchmark b : bench.values())
		{
			apply += b.memapply;
		}
		return apply;
	}

	public long allAtomSucceeds()
	{
		long succeed = 0;
		for (Benchmark b : bench.values())
		{
			succeed += b.atomsucceed;
		}
		return succeed;
	}

	public long allMemSucceeds()
	{
		long succeed = 0;
		for (Benchmark b : bench.values())
		{
			succeed += b.memsucceed;
		}
		return succeed;
	}

	public long allAtomTimes()
	{
		long time = 0;
		for (Benchmark b : bench.values())
		{
			time += b.atomtime;
		}
		return time;
	}

	public long allMemTimes()
	{
		long time = 0;
		for (Benchmark b : bench.values())
		{
			time += b.memtime;
		}
		return time;
	}

	public long allApplys()
	{
		return allAtomApplys() + allMemApplys();
	}

	public long allSucceeds()
	{
		return allAtomSucceeds() + allMemSucceeds();
	}

	public long allTimes()
	{
		return allAtomTimes() + allMemTimes();
	}

	public long allBackTracks()
	{
		long backtracks = 0;
		for (Benchmark b : bench.values())
		{
			backtracks += b.backtracks;
		}
		return backtracks;
	}

	public long allLockFailures()
	{
		long lockfailure = 0;
		for (Benchmark b : bench.values())
		{
			lockfailure += b.lockfailure;
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
