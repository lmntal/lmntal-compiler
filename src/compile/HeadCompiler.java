/*
 * 作成日: 2003/10/28
 *
 */
package compile;

import java.util.*;

/**
 * @author pa
 *
 */
public class HeadCompiler {
	public Membrane m;
	public List atoms = new ArrayList();
	public List freemems = new ArrayList();
	public Map mempaths = new HashMap();
	public List visited = new ArrayList();
	public List atomidpath = new ArrayList();
	public List match = new ArrayList();
	public Map atomids = new HashMap();
	
	public int varcount;
	
	HeadCompiler(Membrane m) {
		this.m = m;
	}
	
	/**
	 * head の仮引数リストをつくっている。何番目がどのアトム。
	 */
	public void enumformals() {
		/*
		mem.each_atom do | atom |
			# ハッシュ。アトム番号をいれる。
			@atomids[atom] = @atoms.length
			@atoms.push atom
		end
		mem.each_mem do | submem |
			enumformals submem
			@freemems.push submem if submem.natoms + submem.nmems == 0
		end
		*/
	}
	
	public void prepare() {
		varcount = 0;
		mempaths.clear();
		visited.clear();
		atomidpath.clear();
		match.clear();
		/*
		@varcount 	= 0
		@mempaths 	= {} # elements are lists such as [:memof, :memof, 4]
		@visited		= [] # Array.new(@atoms.length, nil)
		@atomidpath = [] # Array.new(@atoms.length, nil)
		@match			= []
		 */
	}
	
	public void compile_group(int targetid) {
		/*
	def compile_group(targetid)
		targetidstack = [targetid]
		while targetid = targetidstack.shift
			next if @visited[targetid]
			@visited[targetid] = true
			atom = @atoms[targetid]
			for pos in 1..(atom.arity)
				buddylink = atom.args[pos]
				next if buddylink.buddy == buddylink # guard anonymous variable
				buddyatom = buddylink.atom
				buddyid = @atomids[buddyatom]
				next unless buddyid 						# lhs->rhs
				if @atomidpath[buddyid]
					if buddyatom.mem.getmem(0) == atom.mem.getmem(0)
						# next if: lhs(>)->lhs(<), neg(>)->sameneg(<)
						next if @atomidpath[buddyid] <	@atomidpath[targetid]
						next if @atomidpath[buddyid] == @atomidpath[targetid] and
								 atom.args[pos].pos < pos
					end
				end
				buddyatompath = @varcount += 1
				@match.push [:deref, buddyatompath, @atomidpath[targetid], \
										 pos, atom.args[pos].pos]
				if @atomidpath[buddyid] # lhs(<)->lhs(>), neg(<)->neg(>), neg->lhs
					@match.push [:eq, buddyatompath, @atomidpath[buddyid]]
					next
				end
				@atomidpath[buddyid] = buddyatompath
				targetidstack.push buddyid
#
				targetmem = atom.mem
				buddymem	= buddyatom.mem
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
	public void compile_mem(Membrane m) {
		/*
	def compile_mem(mem)
		mem.each_atom do | atom |
		
			# targetid : 仮引数番号
			targetid = @atomids[atom]
			if @atomidpath[targetid] == nil
				@atomidpath[targetid] = @varcount += 1
				@match.push [:findatom, @varcount, @mempaths[mem], atom.func]
				mem.each_atom do | otheratom |
					next unless @atomidpath[@atomids[otheratom]]
					next if otheratom.func != atom.func
					next if otheratom == atom
					@match.push [:neq, @varcount, @atomidpath[@atomids[otheratom]]]
				end
			end
			compile_group targetid
		end
		mem.each_mem do | submem |
			if not @mempaths.include? submem
				@mempaths[submem] = [@varcount += 1]
				@match.push [:anymem, @varcount, @mempaths[submem.mem]]
				mem.each_mem do | othermem |
					next unless @mempaths[othermem]
					next if othermem == submem
					@match.push [:neq, [@varcount], @mempaths[othermem]]
				end
			end
			if submem.procvars.empty?
				@match.push [:ieq, submem.natoms, [:natoms, @mempaths[submem]]]
				@match.push [:ieq, submem.nmems,	[:nmems,	@mempaths[submem]]]
			end
			if submem.rulevars.empty?
				@match.push [:norules, @mempaths[submem]]
			end
			compile_mem submem
		end
	end
		 */
	}
}
