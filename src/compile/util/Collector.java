package compile.util;

import java.util.HashSet;
import java.util.Set;

import runtime.functor.Functor;

import compile.structure.Atom;
import compile.structure.Atomic;
import compile.structure.Membrane;
import compile.structure.ProcessContext;

/**
 * 膜中のアトムを再帰的に収集するためのクラス。
 * @author Yuuki Shinobu
 */
public final class Collector
{
	public static interface ICondition<T extends Atomic>
	{
		public boolean accept(T x);
	}

	public static class Not<T extends Atomic> implements ICondition<T>
	{
		private ICondition<T> cond;

		public Not(ICondition<T> cond)
		{
			this.cond = cond;
		}

		public boolean accept(T x)
		{
			return !cond.accept(x);
		}
	}

	public static final ICondition<Atomic> ALL = new ICondition<Atomic>()
	{
		public boolean accept(Atomic anyAtomic)
		{
			return true;
		}
	};

	public static final ICondition<Atom> IS_UNIFY = new ICondition<Atom>()
	{
		public boolean accept(Atom atom)
		{
			return atom.functor.equals(Functor.UNIFY);
		}
	};

	public static final ICondition<Atom> IS_NOT_UNIFY = new Not<>(IS_UNIFY);

	public static final ICondition<ProcessContext> IS_TYPED = new ICondition<ProcessContext>()
	{
		public boolean accept(ProcessContext pc)
		{
			return pc.def.isTyped();
		}
	};

	private Collector() { }

	public static Set<ProcessContext> collectTypedProcessContexts(Membrane mem)
	{
		Set<ProcessContext> pcs = new HashSet<>();
		collectTypedProcessContexts(pcs, mem);
		return pcs;
	}

	public static Set<Atomic> collectAllAtomsAndTypedPCs(Membrane mem)
	{
		Set<Atomic> atomics = new HashSet<>();
		collectAtoms(atomics, mem, ALL);
		collectTypedProcessContexts(atomics, mem);
		return atomics;
	}

	public static Set<Atom> collectAtomsExceptUnify(Membrane mem)
	{
		return collectAtoms(mem, IS_NOT_UNIFY);
	}

	public static Set<Atom> collectUnifyAtoms(Membrane mem)
	{
		return collectAtoms(mem, IS_UNIFY);
	}

	public static Set<Atom> collectAllAtoms(Membrane mem)
	{
		return collectAtoms(mem, ALL);
	}

	/**
	 * 条件 {@code cond} を満たすアトムを再帰的に探索する。
	 * @param mem 対象膜
	 * @param cond 条件
	 * @return 条件を満たすアトムの集合
	 */
	public static Set<Atom> collectAtoms(Membrane mem, ICondition<? super Atom> cond)
	{
		Set<Atom> atoms = new HashSet<>();
		collectAtoms(atoms, mem, cond);
		return atoms;
	}

	private static void collectAtoms(Set<? super Atom> destAtoms, Membrane mem, ICondition<? super Atom> cond)
	{
		for (Membrane submem : mem.mems)
		{
			collectAtoms(destAtoms, submem, cond);
		}
		for (Atom a : mem.atoms)
		{
			if (cond.accept(a))
			{
				destAtoms.add(a);
			}
		}
	}

	private static void collectTypedProcessContexts(Set<? super ProcessContext> pcs, Membrane mem)
	{
		for (Membrane submem : mem.mems)
		{
			collectTypedProcessContexts(pcs, submem);
		}
		pcs.addAll(mem.typedProcessContexts);
	}
}
