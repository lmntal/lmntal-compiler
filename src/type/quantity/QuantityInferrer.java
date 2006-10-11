package type.quantity;

import type.ConstraintSet;

import compile.structure.Membrane;
import compile.structure.RuleStructure;

/**
 * 量的解析(?)
 * @author kudo
 *
 */
public class QuantityInferrer {
	
	private ConstraintSet constraints;
	
	private final Quantities quantities;
	
	public QuantityInferrer(ConstraintSet constraints){
		this.constraints = constraints;
		this.quantities = new Quantities();
	}

	public void infer(Membrane root){
		inferGeneratedMembrane(root);
	}
	
	/**
	 * 
	 * @param rule
	 */
	private void inferRule(RuleStructure rule){
		inferInheritedMembranes(rule.leftMem, rule.rightMem);
	}
	/**
	 * 左辺から右辺に受け継がれた「同じ膜」である、として解析する。
	 * @param lhs
	 * @param rhs
	 */
	private void inferInheritedMembranes(Membrane lhs, Membrane rhs){
		
	}
	/**
	 * 単独で生成された膜として解析する。
	 * @param mem
	 */
	private void inferGeneratedMembrane(Membrane mem){
		
	}
}
