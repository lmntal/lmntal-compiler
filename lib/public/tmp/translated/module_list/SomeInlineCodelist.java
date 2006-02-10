package translated.module_list;
import runtime.*;
import java.util.*;
/*inline_define*/
import util.Util;
class CustomGuardImpl implements CustomGuard {
	public boolean run(String guardID, Membrane mem, Object obj) {
//		System.out.println("guardID "+guardID);
		ArrayList ary = (ArrayList)obj;
//		for(int i=0;i<ary.size();i++) {
//			System.out.println(ary.get(i).getClass());
//		}
//		System.out.println("CustomGuardImpl "+ary);
		
		if(guardID.equals("is_list")) {
			return Util.isList((Link)ary.get(0));
		}
		else if(guardID.equals("list_max")) {
			Atom a = new Atom(null, new IntegerFunctor(0));
			boolean b = Util.listMax((Link)ary.get(0), a);
			ary.set(1, a);
			return b;
		}
		else if(guardID.equals("list_min")) {
			Atom a = new Atom(null, new IntegerFunctor(0));
			boolean b = Util.listMin((Link)ary.get(0), a);
			ary.set(1, a);
			return b;
		}
		else if(guardID.equals("member")) {
			return Util.listMember((Atom)ary.get(0), (Link)ary.get(1));
		}
		else if(guardID.equals("not_member")) {
			return !Util.listMember((Atom)ary.get(0), (Link)ary.get(1));
		}
		else if(guardID.equals("test")) {
//			boolean b = Util.listMin((Link)ary.get(0), a);
			Atom aa = mem.newAtom(new IntegerFunctor(777));
			Link link = (Link)ary.get(0);
//			link.getAtom().remove();
			mem.inheritLink(aa, 0, link);
			return true;
		}
		return false;
	}
}

public class SomeInlineCodelist {
	public static void run(Atom me, int codeID) {
		AbstractMembrane mem = me.getMem();
		switch(codeID) {
		}
	}
}
