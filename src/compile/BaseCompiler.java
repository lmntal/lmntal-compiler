package compile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import runtime.Instruction;
import runtime.InstructionList;

import compile.structure.Atom;
import compile.structure.Atomic;
import compile.structure.Membrane;

/**
 * HeadCompiler, GuardCompilerの基底クラス
 */
abstract class BaseCompiler
{
	/** マッチング命令列（のラベル）*/
	InstructionList matchLabel;
	/** matchLabel.insts */
	List<Instruction> match;

	List<Membrane> mems = new ArrayList<>(); // 出現する膜のリスト。[0]がm
	List<Atomic> atoms = new ArrayList<>(); // 出現するアトムのリスト
	HashMap<Membrane, Integer> memPaths = new HashMap<>(); // Membrane -> 変数番号
	HashMap<Atomic, Integer> atomPaths = new HashMap<>(); // Atomic -> 変数番号
	Map<Integer, int[]> linkPaths = new HashMap<>(); // Atomの変数番号 -> リンクの変数番号の配列

	protected Map<Atom, Integer> atomIds = new HashMap<>(); // Atom -> atoms内のindex（廃止の方向で検討する）
	protected HashSet<Atom> visited = new HashSet<>(); // Atom -> boolean, マッチング命令を生成したかどうか

	int varCount; // いずれアトムと膜で分けるべきだと思う

	final boolean isAtomLoaded(Atomic atom) { return atomPaths.containsKey(atom); }
	final boolean isMemLoaded(Membrane mem) { return memPaths.containsKey(mem); }

	protected final int atomToPath(Atomic atom) { 
		if (!isAtomLoaded(atom)) return UNBOUND;
		return atomPaths.get(atom);
	}
	protected final int memToPath(Membrane mem) {
		if (!isMemLoaded(mem)) return UNBOUND;
		return memPaths.get(mem);
	}

	protected static final int UNBOUND = -1;

	BaseCompiler()
	{
	}

	/**
	 * ガード否定条件およびボディのコンパイルで使うために、
	 * thisを指定されたhcに対する正規化されたHeadCompilerとする。
	 * 正規化とは、左辺の全てのアトムおよび膜に対して、ガード/ボディ用の仮引数番号を
	 * 変数番号として左辺のマッチングを取り終わった内部状態を持つようにすることを意味する。
	 */
	protected final void initNormalizedCompiler(HeadCompiler hc) {
		matchLabel = new InstructionList();
		match = matchLabel.insts;
		mems.addAll(hc.mems);
		atoms.addAll(hc.atoms);
		varCount = 0;
		for (Membrane mem : mems)
		{
			memPaths.put(mem, varCount++);
		}
		for (Atomic a : atoms)
		{
			atomPaths.put(a, varCount);
			atomIds.put((Atom)a, varCount++);
			visited.add((Atom)a);
		}
	}

	/**
	 * 指定されたアトムに対してgetlinkを行い、変数番号をlinkpathsに登録する。
	 * RISC化に伴い追加(mizuno)
	 */
	final void getLinks(int atompath, int arity, List<Instruction> insts) {
		int[] paths = new int[arity];
		for (int i = 0; i < arity; i++) {
			paths[i] = varCount;
			insts.add(new Instruction(Instruction.GETLINK, varCount, atompath, i));
			varCount++;
		}
		final int[] put = linkPaths.put(atompath, paths);
	}

	/**
	 * 次の命令列（ヘッド命令列→ガード命令列→ボディ命令列）への膜引数列を返す。
	 * 具体的にはmemsに対応する変数番号のリストを格納したArrayListを返す。
	 */
	List<Integer> getMemActuals() {
		List<Integer> args = new ArrayList<>();
		for (int i = 0; i < mems.size(); i++) {
			if(memPaths.get(mems.get(i)) != null)args.add( memPaths.get(mems.get(i)) );
		}
		return args;
	}

	/**
	 * 次の命令列（ヘッド命令列→ガード命令列→ボディ命令列）への膜やアトム以外の引数列を返す。
	 * 具体的にはHeadCompilerは空のArrayListを返す。
	 */
	List<Object> getVarActuals() {
		return new ArrayList<>();
	}
}