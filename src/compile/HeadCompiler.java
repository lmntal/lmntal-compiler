/*
 * 作成日: 2003/10/28
 *
 */
package compile;

import java.util.*;
import runtime.Env;
import runtime.Instruction;
import compile.structure.*;

/**
 * @author pa
 *
 */
public class HeadCompiler {
	public Membrane m;
	public List atoms = new ArrayList();
	
	/**
	 * 空の膜を保持する。実引数リストは通常アトムから成るが、空膜にマッチさせる時は空膜自身が実引数となる。
	 * 実引数リスト内で、任意の空膜は任意のアトムより後ろに来る。
	 */
	public List freemems = new ArrayList();
	
	public boolean visited[];
	public Map  mempaths      = new HashMap();
	public List atomidpath    = new ArrayList();
	public List match         = new ArrayList();
	public Map  atomids       = new HashMap();
	
	public int varcount;
	
	HeadCompiler(Membrane m) {
		Env.n("HeadCompiler");
		this.m = m;
	}
	
	/**
	 * head の仮引数リストをつくっている。何番目がどのアトム。
	 */
	public void enumformals(Membrane mem) {
		Env.c("enumformals");
		for(int i=0;i<mem.atoms.size();i++) {
			Atom atom = (Atom)(mem.atoms.get(i));
			
			// アトム番号をいれる。
			atomids.put(atom, new Integer(atoms.size()));
			atoms.add(atom);
		}
		for(int i=0;i<mem.mems.size();i++) {
			Membrane submem = (Membrane)(mem.mems.get(i));
			
			enumformals(submem);
		}
		// 子膜それぞれにやってたのを、自分に関して調べるように変更。
		// これで大元の膜に対してこれを書く必要がなくなった（とおもう）
		if(mem.atoms.size() + mem.mems.size() == 0) {
			freemems.add(mem);
		}
		Env.p("atomids = "+atomids);
		Env.p("atoms = "+atoms);
		Env.p("freemems = "+freemems);
	}
	
	public void prepare() {
		Env.c("prepare");
		varcount = 0;
		mempaths.clear();
		visited = new boolean[atoms.size()];
		atomidpath = new ArrayList();
		for(int i=0;i<atoms.size();i++) atomidpath.add(null);
		match.clear();
	}
	
	public void compile_group(int targetid) {
		Env.c("compile_group");
		
		LinkedList targetidstack = new LinkedList();
		Atom atom, buddyatom;
		LinkOccurrence buddylink;
		int buddyid, buddyatompath;
		Membrane targetmem, buddymem;
		
		targetidstack.add(new Integer(targetid));
		while( ! targetidstack.isEmpty() ) {
			targetid = ((Integer)targetidstack.removeFirst()).intValue();
			
			if( visited[targetid] ) continue;
			visited[targetid] = true;
			
			atom = (Atom)(atoms.get(targetid));
			for(int pos=1; pos <= atom.functor.getArity(); pos++) {
				buddylink = atom.args[pos];
				if(buddylink.buddy.equals(buddylink)) continue;
				buddyatom = buddylink.atom;
				buddyid = ((Integer)(atomids.get(buddyatom))).intValue();
				if(buddyid==0) continue;
				
				if( ((Integer)(atomidpath.get(buddyid))).intValue() != 0 ) {
					if( buddyatom.mem.mems.get(0).equals(atom.mem.mems.get(0)) ) {
						int b = ((Integer)(atomidpath.get(buddyid))).intValue();
						int t = ((Integer)(atomidpath.get(targetid))).intValue();
						if(b<t) continue;
						if(b==t && atom.args[pos].pos < pos) continue;
					}
				}
				buddyatompath = varcount += 1;
				match.add( Instruction.dummy("[:deref, buddyatompath, @atomidpath[targetid],  pos, atom.args[pos].pos]") );
				if( ((Integer)(atomidpath.get(buddyid))).intValue() != 0 ) {
					// lhs(<)->lhs(>), neg(<)->neg(>), neg->lhs
					match.add( Instruction.dummy("[:eq, buddyatompath, @atomidpath[buddyid]]") );
					continue;
				}
				atomidpath.set(buddyid, new Integer(buddyatompath));
				targetidstack.addLast( new Integer(buddyid) );
				targetmem = atom.mem;
				buddymem  = buddyatom.mem;
				
//				if( buddymem.mems.get(0).equals(targetmem.mems.get(0)) ) {
//					// lhs->lhs, neg->sameneg
//					while( targetmem.memlevel > buddymem.memlevel ) {
//						targetmem = targetmem.mem;
//					}
//				}
			}
		}
		/*
#
				if buddymem.getmem(0) == targetmem.getmem(0) # lhs->lhs, neg->sameneg
					while targetmem.memlevel > buddymem.memlevel
						targetmem = targetmem.mem
					end
					targetmemlevel = targetmem.memlevel
				else # neg->anotherneg
					targetmem = nil
					targetmemlevel = 0
				end
				@match.push [:func, buddyatompath, buddyatom.func]
				buddypath = [:memof,buddyatompath]
				while buddymem.memlevel > targetmemlevel
					@mempaths[buddymem] = buddypath.dup
					buddypath.unshift :memof
					buddymem = buddymem.mem
				end
				if targetmem
					until buddymem == targetmem
						targetmem = targetmem.mem
						if @mempaths[buddymem]
							break
						else
							buddymem.mem.each_mem do | othermem |
								if othermem != buddymem and @mempaths[othermem]
									@match.push [:neq, buddypath, @mempaths[othermem]]
								end
							end
						end
						@mempaths[buddymem] = buddypath
						buddypath = buddypath.dup
						buddypath.unshift :memof
						buddymem = buddymem.mem
					end
				end
				targetpath = @mempaths[buddymem]
				@match.push [:eq, targetpath, buddypath]
			end
		end
	end
		 */
	}
	public void compile_mem(Membrane mem) {
		Env.c("compile_mem");
		
		Iterator l;
		l = mem.atoms.iterator();
		while(l.hasNext()) {
			Atom atom = (Atom)(l.next());
			
			// 仮引数番号
			int targetid = ((Integer)(atomids.get(atom))).intValue();
			if(atomidpath.get(targetid) == null) {
				atomidpath.set(targetid, new Integer(++varcount));
				match.add( Instruction.findatom(varcount, (List)(mempaths.get(mem)), atom.functor) );
				
				Iterator l2=mem.atoms.iterator();
				while(l2.hasNext()) {
					Atom otheratom = (Atom)(l2.next());
					
					int z = ((Integer)( atomidpath.get( ((Integer)(atomids.get(otheratom))).intValue() ) )).intValue();
					if(z==0) continue;
					if(! otheratom.functor.equals(atom.functor)) continue;
					if(otheratom.equals(atom)) continue;
					match.add( Instruction.dummy("[:neq"+varcount+", "+atomidpath+", "+z) );
				}
			}
			compile_group(targetid);
		}
		
		l = mem.mems.iterator();
		while(l.hasNext()) {
			Membrane submem=(Membrane)(l.next());
			
			if(! mempaths.containsValue(submem) ) {
				mempaths.put(submem, new Integer(++varcount));
				match.add( Instruction.anymem(varcount, ((Integer)mempaths.get(submem.mem)).intValue()) );
				
				Iterator l2=mem.mems.iterator();
				while(l2.hasNext()) {
					Membrane othermem=(Membrane)(l2.next());
					
					if(null == mempaths.get(othermem)) continue;
					if(othermem.equals(submem)) continue;
					match.add( Instruction.dummy("[:neq, ["+varcount+"], "+mempaths.get(othermem.mem)) );
				}
			}
			// プロセス文脈がないときは、数が一致する必要がある。
			if(0==submem.processContexts.size()) {
				match.add( Instruction.dummy("[:ieq, "+submem.atoms.size()+", [:natoms, "+mempaths.get(submem)) );
				match.add( Instruction.dummy("[:ieq, "+submem.mems.size()+", [:nmems, "+mempaths.get(submem)) );
			}
			if(0==submem.ruleContexts.size()) {
				match.add( Instruction.dummy("[:norules, "+mempaths.get(submem)) );
			}
			compile_mem(submem);
		}
	}
	public List getactuals() {
		Env.c("HeadCompiler::getactuals");
		
		List args = new ArrayList();
		
		for(int i=0;i<atoms.size();i++) {
			args.add( atomidpath.get(i) );
		}
		for(int i=0;i<freemems.size();i++) {
			args.add( mempaths.get(freemems.get(i)) );
		}
		return args;
	}
}
