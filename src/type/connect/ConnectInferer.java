package type.connect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import compile.structure.Atom;
import compile.structure.Atomic;
import compile.structure.LinkOccurrence;
import compile.structure.Membrane;
import compile.structure.ProcessContext;
import compile.structure.RuleStructure;

public class ConnectInferer {
	private Membrane root;

	/**
	 * Functor の引数に繋がる可能性のある Functor の引数への set
	 */
	private Multimap<FunctorAndArgument, FunctorAndArgument> functorConnect;

	/**
	 * a(X) :- b(X). のようなルールにおいて
	 * (a_0, 0) -> (b_0, 0) のようなものが集まった multimap 
	 */
	private Multimap<FunctorAndArgument, FunctorAndArgument> functorTrans;


	@SuppressWarnings("unused")
	private ConnectInferer() {}

	public ConnectInferer(Membrane root) {
		this.root = root;
		functorConnect = new Multimap<FunctorAndArgument, FunctorAndArgument>();
		functorTrans = new Multimap<FunctorAndArgument, FunctorAndArgument>();

	}

	public void infer(){
		/**
		 * 初期アトムとルールに明示的に繋がっているアトム同士について
		 * functorConnect と functorTrans　を求める 
		 */
		makeFunctorConnect(root);

		/**
		 * functorTrans を用いて推移閉方を計算し、functorConnect を求める
		 */
		solveFunctorConnect();
	}

	private void solveFunctorConnect() {
		boolean flag = true;
		while (flag) {
			flag = false;
			for (Map.Entry<FunctorAndArgument, Set<FunctorAndArgument>> mapEntry : 
				functorTrans.entrySet()) {
				FunctorAndArgument leftFaa = mapEntry.getKey();
				for (FunctorAndArgument rightFaa : mapEntry.getValue()) {
					int preCount = functorConnect.getSetSize(rightFaa);
					functorConnect.addAll(rightFaa, functorConnect.getSet(leftFaa));
					int postCount = functorConnect.getSetSize(rightFaa);
					flag = preCount != postCount ? true : flag;
				}
			}
		}
	}

	private void makeFunctorConnect(Membrane mem) {
		for (Atomic atomic : mem.atoms) {
			if (atomic instanceof ProcessContext) {
				continue;
			}
			for(LinkOccurrence otherSide : atomic.args){
				Atomic a = otherSide.buddy.atom;
				if(a instanceof Atom) {
					functorConnect.add(
							new FunctorAndArgument(((Atom) atomic).functor, otherSide.pos),
							new FunctorAndArgument(((Atom) a).functor, otherSide.buddy.pos)
					);
				}
			}
		}

		for (Membrane subMem: mem.mems) {
			makeFunctorConnect(subMem);
		}

		for (RuleStructure rule: mem.rules) {
			makeFunctorConnectRule(rule);
		}
	}

	private void makeFunctorConnectRule(RuleStructure rule) {
		/* functorConnect の作成 */		
		makeFunctorConnectRuleRightMem(rule.rightMem, rule);

		/* functorTrans の作成 */
		makeFunctorConnectRuleLeftMem(rule.leftMem, rule);	
	}

	private void makeFunctorConnectRuleLeftMem(Membrane leftMem, RuleStructure rule) {
		for (Atomic atomic : leftMem.atoms) {
			if (atomic instanceof ProcessContext) {
				continue;
			}
			Atom atom = (Atom) atomic;
			for(LinkOccurrence otherSide : atomic.args) {
				if (!isFreeLink(otherSide, rule) && !(otherSide.buddy.atom instanceof Atom)) {
					continue;
				}
				Atom a = (Atom)otherSide.buddy.atom;
				functorTrans.add(
						new FunctorAndArgument(atom.functor, otherSide.pos),
						new FunctorAndArgument(a.functor, otherSide.buddy.pos)
				);
			}
		}

		for (Membrane subMem : leftMem.mems) {
			makeFunctorConnectRuleLeftMem(subMem, rule);
		}

	}

	private void makeFunctorConnectRuleRightMem(Membrane rightMem, RuleStructure rule) {
		for (Atomic atomic : rightMem.atoms) {
			if (atomic instanceof ProcessContext) {
				continue;
			}
			Atom atom = (Atom) atomic;
			for (LinkOccurrence otherSide : atomic.args){
				if (isFreeLink(otherSide, rule)) {
					continue;
				}
				Atomic a = otherSide.buddy.atom;
				if (!(a instanceof Atom)) {
					continue;
				}
				functorConnect.add(
						new FunctorAndArgument(atom.functor, otherSide.pos),
						new FunctorAndArgument(((Atom) a).functor, otherSide.buddy.pos)
				);
			}
		}

		for (Membrane subMem : rightMem.mems) {
			makeFunctorConnectRuleRightMem(subMem, rule);
		}

		for (RuleStructure r : rightMem.rules) {
			makeFunctorConnectRule(r);
		}

	}

	private boolean isFreeLink(LinkOccurrence lo, RuleStructure rs){
		return rs.leftMem.freeLinks.containsValue(lo) 
		|| rs.rightMem.freeLinks.containsValue(lo) ;
	}

}
