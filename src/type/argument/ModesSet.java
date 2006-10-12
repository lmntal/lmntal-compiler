package type.argument;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import type.TypeConstraintException;

public class ModesSet {
	
	private Set<ModeSet> modes;
	
	public ModesSet(){
		modes = new HashSet<ModeSet>();
	}
	
	private ModeSet getContainingModeSet(Path path){
		Iterator it = modes.iterator();
		while(it.hasNext()){
			ModeSet ms = (ModeSet)it.next();
			if(ms.contains(path))return ms;
		}
		return null;
	}
	
	public void merge(ModeSet ms1, ModeSet ms2){
		ms1.addAll(ms2);
		ms1.buddy.addAll(ms2.buddy);
		modes.remove(ms2);
		modes.remove(ms2.buddy);
	}
	
	public void add(int sign, Path p1, Path p2)throws TypeConstraintException{
		if((sign == -1) && p1.equals(p2))throw new TypeConstraintException("mode conflict (same path connected) : " + p1 + " <=> " + p2);
		ModeSet ms1 = getContainingModeSet(p1);
		ModeSet ms2 = getContainingModeSet(p2);
		if(ms1!=null && ms2!=null){
			if(sign==1 && ms1==ms2)return;
			else if(sign==1 && ms1==ms2.buddy)
				throw new TypeConstraintException("mode error :" + p1 + " <=> " + p2);
			else if(sign==-1 && ms1==ms2.buddy)return;
			else if(sign==-1 && ms1==ms2)
				throw new TypeConstraintException("mode error :" + p1 + " <=> " + p2);
			else if(sign==1)
				merge(ms1, ms2);
			else if(sign==-1)
				merge(ms1, ms2.buddy);
		}
		else if(ms1!=null && ms2==null){
			if(sign==1)ms1.add(p2);
			else ms1.buddy.add(p2);
		}
		else if(ms2!=null && ms1==null){
			if(sign==1)ms2.add(p1);
			else ms2.buddy.add(p1);
		}
		else{
			ModeSet nms = newModeSet();
			nms.add(p1);
			if(sign==1)
				nms.add(p2);
			else
				nms.buddy.add(p2);
		}
	}
	
	public ModeSet getModeSet(Path path)throws TypeConstraintException{
		ModeSet ms = getContainingModeSet(path);
		if(ms==null){
			ms = newModeSet();
			ms.add(path);
		}
		return ms;
	}
	
	private static int index = 0;
	private String newNameForModeSet(){
		return "'m" + (index++);
	}
	
	public ModeSet newModeSet(){
		ModeSet ms1 = new ModeSet();
		ModeSet ms2 = new ModeSet();
		String nm = newNameForModeSet();
		ms1.name = nm;
		ms2.name = nm;
		ms1.sign = 1;
		ms2.sign = -1;
		ms1.buddy = ms2;
		ms2.buddy = ms1;
		modes.add(ms1);
		modes.add(ms2);
		return ms1;
	}
	
}
