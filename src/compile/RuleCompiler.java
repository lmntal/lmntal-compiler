package compile;

import java.util.*;
import runtime.Env;
import runtime.Rule;
import runtime.InterpretedRuleset;
import runtime.Instruction;
import runtime.Functor;
import compile.structure.*;

/*
 * 作成日: 2003/10/24
 *
 */

/**
 * <pre>
 * コンパイル時データ構造（膜）にルールセットを付加する。
 * 
 * 具体的には、Membrane -> RuleSet が付加された Membrane
 * 
 * 外部からは、{ ( :- WORLD ) } の形式で呼ばれることになる。
 * WORLD にはルールが含まれる場合もあるので、
 * 見つかったルールからルールオブジェクトを生成してその膜のルールセットに追加する
 * という作業を再帰的にやることになる。
 * 
 * </pre>
 * 
 * @author hara(working)
 */
public class RuleCompiler {
	/**
	 * 繋がってるとみなすファンクタ。=/2
	 */
	public static final Functor FUNC_UNIFY = new Functor("=", 2);
	
	/**
	 * コンパイルされるルール
	 */
	public RuleStructure rs;
	
	/**
	 * コンパイルされるルールに対応するルールオブジェクト。
	 */
	public Rule theRule;
	
	public List atommatches;
	public List memmatch = new ArrayList();
	public List body;
	public int varcount;
	
	public List lhsfreemems;
	
	public List rhsatoms;
	public Map  rhsatompath;
	public Map  rhsmempaths;
	
	public List lhsatoms;
	public Map  lhsatompath;
	public Map  lhsmempaths;
	
	public Map lhsatomids;
	public Map lhsatomidpath;
	
	HeadCompiler hc;
	
	/**
	 * 指定された RuleStructure 用のルールコンパイラをつくる
	 * 
	 * @param rs ルール
	 */
	RuleCompiler(RuleStructure rs) {
		Env.n("RuleCompiler");
		Env.p(rs);
		this.rs = rs;
	}
	
	/**
	 * 初期化時に指定されたルール構造をルールオブジェクトにコンパイルし、
	 * 所属膜のルールセットに追加する。
	 */
	public void compile() {
		Env.c("compile");
		
		simplify();
		
		theRule = new Rule(rs.toString());
		
		//@ruleid = rule.ruleid
		
		hc = new HeadCompiler(rs.leftMem);
		hc.enumformals(rs.leftMem);
		
		compile_l();
		compile_r();
		
		//optimize if $optlevel > 0
		optimize();
		
		//showInstructions();
		
		//rule.register(@atommatches,@memmatch,@body)
		theRule.memMatch     = memmatch;
		theRule.atomMatches  = atommatches;
		theRule.body         = body;
		
		theRule.showDetail();
		((InterpretedRuleset)rs.parent.ruleset).rules.add(theRule);
		
		// ルールの右辺膜以下にルールがあるかもしれないので再帰的に走査
		RuleSetGenerator.processMembrane(rs.leftMem); // 一応左辺も
		RuleSetGenerator.processMembrane(rs.rightMem);
	}
	
	/**
	 * 左辺膜をコンパイルする。
	 *
	 */
	private void compile_l() {
		Env.c("compile_l");
		
		atommatches = new ArrayList();
		for(int firstid=0; firstid<=hc.atoms.size(); firstid++) {
			// 初期化。
			hc.prepare();
			
			if(firstid < hc.atoms.size()) {
				// アトム主導
				/*
				atommatches.add(hc.match);
				hc.atomidpath.set(firstid, new Integer(1));
				hc.varcount = 1;
				Membrane mem = ((Atom)(hc.atoms.get(firstid))).mem;
				hc.match.add( Instruction.dummy("[:execlevel, mem.memlevel]") );
				hc.match.add( Instruction.dummy("[:func,1,@lhscmp.atoms[firstid].func") );
				
				{
					List l = new LinkedList();
					l.add("memof");
					l.add(new Integer(1));
					hc.mempaths.put(mem, l);
				}
				// 親膜をたどる
				while(mem.mem != null) {
					List l = ((List)(hc.mempaths.get(mem)));
					List ll = new LinkedList();
					
					Iterator li=l.iterator();
					while(li.hasNext()) ll.add(li.next());
					
					ll.add(0, ":memof");
					hc.mempaths.put(mem.mem, ll);
					mem = mem.mem;
				}
				*/
				hc.compile_group(firstid);
			} else {
				// 膜主導
				memmatch = hc.match;
				hc.varcount = 0;
				List tmp = new ArrayList();
				tmp.add(new Integer(0));
				hc.mempaths.put(rs.leftMem, tmp);
			}
			hc.compile_mem(rs.leftMem);
			// 反応しろという命令
			hc.match.add( Instruction.react(theRule, hc.getactuals()) );
		}
	}
	
	/**
	 * 右辺膜をコンパイルする
	 *
	 */
	private void compile_r() {
		Env.c("compile_r");
		
		lhsatoms = hc.atoms;
		lhsfreemems = hc.freemems;
		lhsatomids = hc.atomids;
		varcount = lhsatoms.size() + lhsfreemems.size();
		body = new ArrayList();
		int formals = varcount;
		
		genlhsmempaths();
		rhsatoms = new ArrayList();
		rhsatompath = new HashMap();
		rhsmempaths = new HashMap();
		rhsmempaths.put(rs.rightMem, lhsmempaths.get(rs.leftMem));
		//Env.p("rs.leftMem -> "+rs.leftMem);
		//Env.p("lhsmempaths.get(rs.leftMem) -> "+lhsmempaths.get(rs.leftMem));
		//Env.p("rhsmempaths -> "+rhsmempaths);
		
		remove_lhsatoms();
		remove_lhsmem(rs.leftMem);
		build_rhsmem(rs.rightMem);
		inherit_rhsrules(rs.rightMem);
		inherit_builtins(rs.rightMem);
		
		build_rhsatoms(rs.rightMem);
		free_lhsmem(rs.leftMem);
		
		body.add(0, Instruction.spec(formals, varcount));
		update_links();
	}
	
	/**
	 * ルールの左辺・右辺について = を繋げる処理をする。
	 *
	 */
	public void simplify() {
		Env.c("RuleCompiler::simplify");
		static_unify(rs.leftMem);
		static_unify(rs.rightMem);
	}
	
	/**
	 * 指定膜について = を繋げる処理をする。
	 * @param mem
	 */
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
		
		Membrane mem = null;
		int atomid;
		Iterator l;
		
		List rootmems = new ArrayList();
		lhsmempaths = new HashMap();
		lhsatomidpath = new HashMap();
		
		l = lhsatoms.iterator();
		while(l.hasNext()) {
			Atom atom = (Atom)(l.next());
			
			atomid = ((Integer)(lhsatomids.get( (Object)atom) )).intValue();
			lhsatomidpath.put(new Integer(atomid), new Integer(atomid + 1));
			if(lhsmempaths.get(atom.mem)==null) {
				varcount++;
				lhsmempaths.put((Object)(atom.mem), new Integer(varcount));
				body.add( Instruction.getmem(varcount, atomid + 1) );
				rootmems.add(atom.mem);
			}
		}
		int i=0;
		l = lhsfreemems.iterator();
		while(l.hasNext()) {
			mem = (Membrane)(l.next());
			lhsmempaths.put(mem, new Integer(i++ + lhsatoms.size() + 1));
			rootmems.add(mem);
		}
		if(mem != null)
		while( mem.mem != null && ! lhsmempaths.containsValue(mem.mem) ) {
			varcount++;
			lhsmempaths.put((Object)(mem.mem), new Integer(varcount));
			body.add( Instruction.dummy("[:getparent, @varcount, @lhsmempaths[mem]]") );
			mem = mem.mem;
		}
		//Env.p("lhsmempaths"+lhsmempaths);
	}
	
	private void optimize() {
		Env.c("optimize");
	}
	
	private void remove_lhsatoms() {
		Env.c("RuleCompiler::remove_lhsatoms");
		Iterator l = lhsatoms.iterator();
		int i=0;
		while(l.hasNext()) {
			Atom atom = (Atom)(l.next());
			body.add( Instruction.removeatom(i+1, atom.functor) );
			//廃止？
			//body.add( Instruction.dummy("[:freeatom, "+(i+1)+", "+atom.functor) );
			i++;
		}
	}
	
	private void remove_lhsmem(Membrane mem) {
		Env.c("RuleCompiler::remove_lhsmem");
		Iterator l = mem.mems.iterator();
		while(l.hasNext()) {
			Membrane m = (Membrane)(l.next());
			
			remove_lhsmem(m);
			body.add( Instruction.dummy("[:removemem"+lhsmempaths.get(m)) );
		}
	}
	
	private void build_rhsmem(Membrane mem) {
		Env.p("RuleCompiler::build_rhsmem"+mem);
		Iterator l;
		
		l=mem.mems.iterator();
		while(l.hasNext()) {
			Membrane m = (Membrane)(l.next());
			
			rhsmempaths.put(m, new Integer(++varcount));
			body.add( Instruction.newmem(varcount, ((Integer)rhsmempaths.get(mem)).intValue()) );
			build_rhsmem(m); //inside must be enqueued first
		}
		l=mem.processContexts.iterator();
		while(l.hasNext()) {
			ProcessContext p = (ProcessContext)(l.next());
			
			if(rhsmempaths.get(mem).equals(lhsmempaths.get(p.lhsMem))) continue;
			body.add( Instruction.dummy("[:pour"+rhsmempaths.get(mem)+", "+lhsmempaths.get(p.lhsMem)) );
		}
		//Env.p("rhsmempaths -> "+rhsmempaths);
	}
	
	private void inherit_rhsrules(Membrane mem) {
		Env.c("RuleCompiler::inherit_rhsrules");
		Iterator l;
		
		l=mem.mems.iterator();
		while(l.hasNext()) {
			Membrane m = (Membrane)(l.next());
			inherit_rhsrules(m);
		}
		l=mem.ruleContexts.iterator();
		while(l.hasNext()) {
			RuleContext r = (RuleContext)(l.next());
			
			if(rhsmempaths.get(mem).equals(lhsmempaths.get(r.lhsMem))) continue;
			body.add( Instruction.dummy("[:inheritrules"+rhsmempaths.get(mem)+", "+lhsmempaths.get(r.lhsMem)) );
		}
	}
	
	private void inherit_builtins(Membrane mem) {
		Env.c("RuleCompiler::inherit_builtins");
		Iterator l;
		l=mem.mems.iterator();
		while(l.hasNext()) {
			Membrane m = (Membrane)(l.next());
			inherit_builtins(m);
		}
		body.add( Instruction.loadruleset(((Integer)rhsmempaths.get(mem)).intValue(), mem.ruleset) );
	}
	
	private void build_rhsatoms(Membrane mem) {
		Env.c("RuleCompiler::build_rhsatoms");
		Iterator l;
		
		l=mem.mems.iterator();
		while(l.hasNext()) {
			Membrane m = (Membrane)(l.next());
			build_rhsatoms(m);
		}
		l=mem.atoms.iterator();
		while(l.hasNext()) {
			Atom atom = (Atom)(l.next());
			
			if(atom.functor.equals(FUNC_UNIFY)) {
				LinkOccurrence link1 = atom.args[0];
				LinkOccurrence link2 = atom.args[1];
				body.add( Instruction.dummy("[:unify, "
					+lhsatomidpath.get(lhsatomids.get(link1.atom))+", "+link1.pos
					+lhsatomidpath.get(lhsatomids.get(link2.atom))+", "+link2.pos
				) );
			} else {
				rhsatompath.put(atom, new Integer(++varcount));
				rhsatoms.add(atom);
				body.add( Instruction.newatom(varcount, ((Integer)rhsmempaths.get(mem)).intValue(), atom.functor) );
			}
		}
	}
	
	private void free_lhsmem(Membrane mem) {
		Env.c("RuleCompiler::free_lhsmem");
		Iterator l = mem.mems.iterator();
		while(l.hasNext()) {
			Membrane m = (Membrane)(l.next());
			free_lhsmem(m);
			body.add( Instruction.dummy("[:freemem, "+lhsmempaths.get(m)) );
		}
	}
	
	private void update_links() {
		Env.c("RuleCompiler::update_links");
		Iterator l=rhsatoms.iterator();
		while(l.hasNext()) {
			Atom atom = (Atom)(l.next());
			
			for(int pos=0; pos < atom.functor.getArity(); pos++) {
				LinkOccurrence link = atom.args[pos];
				Env.p(atom+"("+pos+")"+" buddy -> "+link.buddy.atom+" link.atom="+link.atom);
				if(link.atom.mem.equals(rs.leftMem)) {
					body.add( Instruction.dummy("[:relink, "+rhsatompath.get(atom)+", "+pos+", "
						+lhsatomidpath.get(lhsatomids.get(link.atom))+", "+link.pos) );
				} else {
					body.add( Instruction.newlink(
						((Integer)rhsatompath.get(atom)).intValue(), pos,
						((Integer)rhsatompath.get(link.buddy.atom)).intValue(), link.buddy.pos) );
				}
			}
		}
	}
	
	/**
	 * デバッグ用表示
	 */
	private void showInstructions() {
		Iterator l;
		l = atommatches.listIterator();
		Env.p("--atommatches :");
		while(l.hasNext()) Env.p((Instruction)l.next());
		
		l = memmatch.listIterator();
		Env.p("--memmatch :");
		while(l.hasNext()) Env.p((Instruction)l.next());
		
		l = body.listIterator();
		Env.p("--body :");
		while(l.hasNext()) Env.p((Instruction)l.next());
	}
}

