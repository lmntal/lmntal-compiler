package compile;

import java.util.*;
import runtime.Env;
import runtime.InterpretedRuleset;
import runtime.Instruction;

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
	public List atommatches = new ArrayList();
	public List memmatch = new ArrayList();
	public int varcount;
	public List body;
	
	public List lhsfreemems;
	public Map lhsatomids;
	
	public List rhsatoms;
	public Map rhsatompath;
	public Map rhsmempaths;
	
	public List lhsatoms;
	public Map lhsatompath;
	public Map lhsatomidpath;
	public Map lhsmempaths;
	
	HeadCompiler hc;
	
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
	 * 
	 * @return InterpretedRuleset
	 */
	public InterpretedRuleset compile() {
		Env.c("compile");
		List rules = new ArrayList();
		
		//r.text = "( "+l.toString()+" :- "+r.toString()+" )";
		//@ruleid = rule.ruleid
		
		hc = new HeadCompiler(rs.leftMem);
		hc.enumformals();
		if(false /* @lhs.natoms + @lhs.nmems == 0 */) {
			hc.freemems.add(rs.leftMem);
		}
		compile_l();
		compile_r();
		
		//optimize if $optlevel > 0
		optimize();
		
		//rule.register(@atommatches,@memmatch,@body)
		Rule[] rr = new Rule[rules.size()];
		for(int i=0;i<rules.size();i++) {
			rr[i] = (Rule)rules.get(i);
		}
		return new InterpretedRuleset( rr );
	}
	
	private void compile_l() {
		Env.c("compile_l");
		for(int firstid=0; firstid<=hc.atoms.size(); firstid++) {
			hc.prepare();
			if(firstid < hc.atoms.size()) {
				atommatches.addAll(hc.match);
				hc.atomidpath.set(firstid, new Integer(1));
				hc.varcount = 1;
				Membrane mem = ((Atom)(hc.atoms.get(firstid))).mem;
				hc.match.add( new Instruction(/* [:execlevel, mem.memlevel] */) );
				hc.match.add( new Instruction(/* [:func,1,@lhscmp.atoms[firstid].func */) );
				
				hc.mempaths.put(mem, "[:memof,1]");
				// 親膜をたどる
				while(mem.mem != null) {
					//@lhscmp.mempaths[mem.mem] = @lhscmp.mempaths[mem].dup.unshift :memof
					List l = ((List)(hc.mempaths.get(mem)));
					List ll = new ArrayList();
					for(ListIterator li=l.listIterator();li.hasNext();) {
						ll.add(li.next());
					}
					ll.add(0, ":memof");
					hc.mempaths.put(mem.mem, ll);
					mem = mem.mem;
				}
				hc.compile_group(firstid);
			} else {
				memmatch = hc.match;
				hc.varcount = 0;
				List tmp = new ArrayList();
				tmp.add(new Integer(0));
				hc.mempaths.put(rs.leftMem, tmp);
			}
			hc.compile_mem(rs.leftMem);
			// 反応しろという命令
			hc.match.add( new Instruction( /*[:react, @ruleid, @lhscmp.getactuals]*/ ) );
		}
		/*
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
		lhsatoms = hc.atoms;
		lhsfreemems = hc.freemems;
		lhsatomids = hc.atomids;
		varcount = lhsatoms.size() + lhsfreemems.size();
		rhsatompath = new HashMap();
		rhsmempaths = new HashMap();
		rhsmempaths.put(rs.rightMem, lhsmempaths.get(rs.leftMem));
		genlhsmempaths();
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
	
	public void simplify() {
		Env.c("RuleCompiler::simplify");
		static_unify(rs.leftMem);
		static_unify(rs.rightMem);
	}
	
	public void static_unify(Membrane mem) {
		Env.c("RuleCompiler::static_unify");
		/*
		mem.each_mem do | submem | static_unify submem end
		removedatoms = []
		mem.each_atomoffunc($FUNC_UNIFY) do | atom |
			link1 = atom.args[1]
			link2 = atom.args[2]
			if link1.atom.mem.getmem(0) != mem.getmem(0) and \
				 link2.atom.mem.getmem(0) != mem.getmem(0)
				if mem.getmem(0) == @lhs
					$nerrors += 1
					print "Compile error: head contains body unification\n"
				end
				next
			end
			link1.relink link2
			link2.relink link1
			removedatoms.push atom
		end
		removedatoms.each do | atom | atom.remove end
		 */
	}
	
	private void genlhsmempaths() {
		Env.c("RuleCompiler::genlhsmempaths");
		
		Membrane mem;
		int atomid;
		
		List rootmems = new ArrayList();
		lhsmempaths = new HashMap();
		lhsatomidpath = new HashMap();
		
		for(int i=0;i<lhsatoms.size();i++) {
			Atom atom = (Atom)(lhsatoms.get(i));
			atomid = ((Integer)(lhsatomids.get( (Object)atom) )).intValue();
			lhsatomidpath.put(new Integer(atomid), new Integer(atomid + 1));
			if(lhsmempaths.get(atom.mem)!=null) {
				varcount++;
				lhsmempaths.put((Object)(atom.mem), new Integer(varcount));
				body.add("[:getmem, @varcount, atomid + 1]");
				rootmems.add(atom.mem);
			}
		}
		for(int i=0;i<lhsfreemems.size();i++) {
			mem = (Membrane)(lhsfreemems.get(i));
			lhsmempaths.put(mem, new Integer(i + lhsatoms.size() + 1));
			rootmems.add(mem);
		}
		while( mem.mem != null && ! lhsmempaths.containsValue(mem.mem) ) {
			varcount++;
			lhsmempaths.put((Object)(mem.mem), new Integer(varcount));
			body.add( "[:getparent, @varcount, @lhsmempaths[mem]]" );
			mem = mem.mem;
		}
		/*
		rootmems = []
		@lhsmempaths	 = {} # elements are integers such as 4
		@lhsatomidpath = {}
		@lhsatoms.each do | atom |
			atomid = @lhsatomids[atom]
			@lhsatomidpath[atomid] = atomid + 1
			
			unless @lhsmempaths[atom.mem]
				@lhsmempaths[atom.mem] = (@varcount += 1)
				@body.push [:getmem, @varcount, atomid + 1]
				rootmems.push atom.mem
			end
		end
		@lhsfreemems.length.times do | index |
			mem = @lhsfreemems[index]
			@lhsmempaths[mem] = (index + @lhsatoms.length + 1)
			rootmems.push mem
		end
		rootmems.each do | mem |
# 		 while mem.mem and (not @lhsmempaths.include? mem.mem or
# 				 @lhsmempaths[mem].length + 1 < @lhsmempaths[mem.mem].length)
# 			 @lhsmempaths[mem.mem] = @lhsmempaths[mem].dup.unshift :memof
# 			 mem = mem.mem
# 		 end
			while mem.mem and not @lhsmempaths.include? mem.mem
				@lhsmempaths[mem.mem] = (@varcount += 1)
				@body.push [:getparent, @varcount, @lhsmempaths[mem]]
				mem = mem.mem
			end
		end
		 */
	}
	
	private void optimize() {
		Env.c("optimize");
	}
}

