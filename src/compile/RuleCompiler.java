package compile;

//import java.util.*;
import runtime.Env;

/*
 * 作成日: 2003/10/24
 *
 */

/**
 * <pre>
 * コンパイル時データ構造をルールオブジェクトに変換する。
 * ルールオブジェクトは命令列を持つ。
 * 
 * ので、機能はデータ構造（インスタンスの木） -> 命令列
 * 
 * 外部からは、( :- WORLD ) の形式で呼ばれることになる。
 * WORLD にはルールが含まれる場合もあるので再帰的に
 * ルールをコンパイルすることになる。
 * 
 * </pre>
 * 
 * @author hara(working)
 */
public class RuleCompiler {
	public RuleStructure rs;
	
	/**
	 * rs 用のルールコンパイラをつくる
	 * 
	 * @param rs ルール
	 */
	RuleCompiler(RuleStructure rs) {
		Env.n("RuleCompiler");
		Env.p(rs);
		this.rs = rs;
	}
	
	/**
	 * コンパイルする。
	 * 何を返すかは未定
	 * 
	 * @return Rule
	 */
	public Rule compile() {
		Env.c("compile");
		Rule r = new Rule();
		//r.text = "( "+l.toString()+" :- "+r.toString()+" )";
		//@ruleid = rule.ruleid
		
		HeadCompiler hc = new HeadCompiler(rs.leftMem);
		hc.enumformals();
		if(false /* @lhs.natoms + @lhs.nmems == 0 */) {
			hc.freemems.add(rs.leftMem);
		}
		compile_l();
		compile_r();
		
		//optimize if $optlevel > 0
		optimize();
		
		//rule.register(@atommatches,@memmatch,@body)
		return r;
	}
	
	private void compile_l() {
		Env.c("compile_l");
		/*
		@atommatches = []
		for firstid in 0..(@lhscmp.atoms.length)
			@lhscmp.prepare
			if firstid < @lhscmp.atoms.length
				@atommatches.push @lhscmp.match
				@lhscmp.atomidpath[firstid] = @lhscmp.varcount = 1
				mem = @lhscmp.atoms[firstid].mem
				@lhscmp.match.push [:execlevel, mem.memlevel]
				@lhscmp.match.push [:func,1,@lhscmp.atoms[firstid].func]
				@lhscmp.mempaths[mem] = [:memof,1]
				while mem.mem != nil
					@lhscmp.mempaths[mem.mem] = @lhscmp.mempaths[mem].dup.unshift :memof
					mem = mem.mem
				end
				@lhscmp.compile_group firstid
			else
				@memmatch = @lhscmp.match
				@lhscmp.mempaths[@lhs] = [@lhscmp.varcount = 0]
			end
			
			# 
			@lhscmp.compile_mem  @lhs
			@lhscmp.compile_negs @negs
			# 反応しろという命令
			@lhscmp.match.push [:react, @ruleid, @lhscmp.getactuals]
		end
		 */
	}
	
	private void compile_r() {
		Env.c("compile_r");
		/*
		@lhsatoms 	 = @lhscmp.atoms
		@lhsfreemems = @lhscmp.freemems
		@lhsatomids  = @lhscmp.atomids
		@varcount = @lhsatoms.length + @lhsfreemems.length
		@body = [[:spec,@varcount]]
		genlhsmempaths
		@rhsatoms 	 = []
		@rhsatompath = {}
		@rhsmempaths = {}
		@rhsmempaths[@rhs] = @lhsmempaths[@lhs]
		
		remove_lhsatoms
		remove_lhsmem 			@lhs
		
		build_rhsmem				@rhs
		inherit_rhsrules		@rhs
		inherit_builtins		@rhs
		
		build_rhsatoms			@rhs
		free_lhsmem 				@lhs
		
		@body.first.push @varcount
		update_links
		 */
	}
	
	private void optimize() {
		Env.c("optimize");
	}
}

