package runtime;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

import java.io.FileInputStream;

import de.uka.ilkd.pp.*;

final class GlaphLayouter extends Layouter<NoExceptions>{
	
	GlaphLayouter(StringBackend back, int indentation){
		super(back, indentation);		
	}
	private String last = " ";
	
	public Layouter<NoExceptions> print(String text) {
		if (!last.matches(".*['\"\\[\\]|,(){} ]")
				&& Env.profile != Env.PROFILE_ALL
				&& !text.matches("['\"\\[\\]|,(){} ].*")
				&& last.matches(".*[0-9A-Za-z_]") == text
						.matches("[0-9A-Za-z_].*")) {
			// if (! (last.matches(".*=") && text.matches("\\$.*")) )
			return super.brk(1,0);
		}
		last = text;
		return super.print(text);		
	}
	
	public Layouter<NoExceptions> indMem(){
		return this.ind(0, Dumper2.getIndentation()-1);
	}
	
	public void  beginMem(){
		switch(Dumper2.getMemFormat()){
		case INCONSISTENT:
			this.beginI();
			break;
		case CONSISTENT:
			this.beginC();
			break;
		case OFF:
			break;
		}
	}

	public void endMem(){
		switch(Dumper2.getMemFormat()){
		case INCONSISTENT:
		case CONSISTENT:
			this.end();
			break;
		case OFF:
			break;
			}
	}
		
	public void beginAtomGroup(){
		switch(Dumper2.getAtomFormat()){
		case INCONSISTENT:
			this.beginI();
			break;
		case CONSISTENT:
			this.beginC();
			break;
		case OFF:
			break;
		}
	}
	
	public void endAtomGroup(){
		switch(Dumper2.getAtomFormat()){
		case INCONSISTENT:
		case CONSISTENT:
			this.end();
			break;
		case OFF:
			break;
		}
	}

	
}

class IntAtomComparator implements Comparator{
	  public int compare(Object a1, Object a2){
		  if(((Atom)a1).getName().equals(((Atom)a2).getName())){
			    return ((Integer)((Atom)a1).getLastArg().getAtom().getFunctor().getValue()).compareTo(
			    		(Integer)((Atom)a2).getLastArg().getAtom().getFunctor().getValue());
		  }else{
			    return ((Atom)a1).getName().compareTo(((Atom)a2).getName());
		  }
	  }
	}

class AtomComparator implements Comparator{
	  public int compare(Object a1, Object a2){
	    return ((Atom)a1).getName().compareTo(((Atom)a2).getName());
	  }
	}

class plusLinkComparator implements Comparator{
	  public int compare(Object a1, Object a2){
			Link l1, l2, tmp_l1,tmp_l2;
			tmp_l1 = l1 = ((Atom)a1).args[0];
			tmp_l2 = l2 = ((Atom)a2).args[0];
			Atom tmp_a1 = l1.getAtom();
			Atom tmp_a2 = l2.getAtom();			
			while (!tmp_a1.isVisible()) {
				tmp_l1 = tmp_a1.args[tmp_l1.getPos() == 0 ? 1 : 0];
				tmp_a1 = tmp_l1.getAtom();
			}
			while (!tmp_a2.isVisible()) {
				tmp_l2 = tmp_a2.args[tmp_l2.getPos() == 0 ? 1 : 0];
				tmp_a2 = tmp_l2.getAtom();
			}
			return ((Integer)( ((Integer)l1.getID()).compareTo(tmp_l1.getBuddy().getID()) >= 0 ? l1
					.getID() : tmp_l1.getBuddy().getID()	))
					.compareTo
					(((Integer)l2.getID()).compareTo(tmp_l2.getBuddy().getID()) >= 0 ? l2
							.getID() : tmp_l2.getBuddy().getID()	);

	  }
	}

class plusAtomComparator implements Comparator{
	  public int compare(Object a1, Object a2){
	    return ((Atom)a1).getLastArg().getAtom().getName().compareTo(((Atom)a2).getLastArg().getAtom().getName());
	  }
	}


class AtomGroupComparator implements Comparator{
	  public int compare(Object a1, Object a2){
	    return ((AtomGroup)a1).getTopLevelName().compareTo(((AtomGroup)a2).getTopLevelName());
	  }
	}

class MembraneComparator implements Comparator{
	  public int compare(Object m1, Object m2){
	    return ((Membrane)m1).name.compareTo(((Membrane)m2).name);
	  }
	}


public class Dumper2{

	private static int fLineWidth = 40;

	private static int fIndentation = 2;
	
	private static int fSpace = 1;

	private static boolean isUseSource = false;
	
	private static boolean specifiedTopAtom;
	
	private static HashSet<String> toplevelAtomNameSet = new HashSet<String>();
		
	private static PrintFormat memFormat;
	
	private static PrintFormat atomFormat;
	
	private static boolean fSort;
	
	private static boolean abbrAtom;

	private static boolean abbrMem;
	
	private static boolean listNotation;
	
	private static boolean infixNotation; 
	
	private static boolean memSort; 
	
	private static String propertiesPath;

	static String PROFILE_TABS = "RuleName,\tThreadID,\tAtomDrivenTime,\tMembraneDrivenTime,\t"
		+ "AtomDrivenSucceed,\tMembraneDrivenSucceed,\t"
		+ "AtomDrivenApply,\tMembraneDrivenApply,\t"
		+ "BackTracks,\tLockFailures\n";
	
	/** 膜の中身を出力する. グラウンド膜*/
	public static String dump(Membrane mem) {
		getProperties();
		StringBackend back = new StringBackend(fLineWidth );
		GlaphLayouter out = new GlaphLayouter(back,0);
		out.beginMem();
		new DumpMembrane(mem ,out,0).dump(mem, true);
		out.endMem();
		out.close();
		return back.getString();
	}
	
	/** 膜の中身を出力する.子膜 */
	static void dump(Membrane mem, GlaphLayouter out) {
        out.beginMem();
		new DumpMembrane(mem , out, 0).dump(mem, true);
		out.endMem();
	}
	
	public static void setPropertiesPath(String path){
		propertiesPath = path;
	}
	
	public static String getPropertiesPath(){
		return propertiesPath;
	}
	
	public static void setIsUseSource(boolean b){
		isUseSource = b;
	}

	public static boolean getIsUseSource(){
		return isUseSource;
	}
	
	public static boolean isSpecifiedTopAtom(){
		return specifiedTopAtom;
	}
	
	public static HashSet<String> getToplevelAtomNameSet(){
		return toplevelAtomNameSet;
	}
	
	public static int getLineWidth(){
		return fLineWidth;
	}
	
	public static int getIndentation(){
		return fIndentation;
	}
	
	public static int get_fSpace(){
		return fSpace;
	}
		
	public static PrintFormat getMemFormat(){
		return memFormat;
	}
		
	public static PrintFormat getAtomFormat(){
		return atomFormat;
	}
	
	public static boolean isSort(){
		return fSort;
	}
	
	public static boolean isAbbrAtom(){
		return abbrAtom;
	}

	public static boolean isAbbrMem(){
		return abbrMem;
	}
	
	public static boolean isListNotation(){
		return listNotation;
	}

	public static boolean isInfixNotation(){
		return infixNotation;
	}
	
	public static boolean isMemSort(){
		return memSort;
	}
	
	public static void getProperties(){
		try {
		      Properties prop = new Properties();

		      // プロパティファイルからキーと値のリストを読み込む
		      prop.load(new FileInputStream(propertiesPath));
		      String str = prop.getProperty("useSourceParse", "false");
		      if(str.equals("true")){
		    	  isUseSource = true;
		      }else{
		    	  isUseSource = false;
		      }
		      if(isUseSource){
		    	  str = prop.getProperty("toplevelNameFromSource");
			      String[] AtomNames  = str.split(",");
			      for(int i=0; i<AtomNames.length;i++){
				      toplevelAtomNameSet.add(AtomNames[i]);			    	  
			      }		    	  
		      }
		      str = prop.getProperty("toplevelNameFromUser");
		      if(str !=null){
		    	  // TODO エラー処理？
			      String[] AtomNames  = str.split(",");
			      for(int i=0; i<AtomNames.length;i++){
				      toplevelAtomNameSet.add(AtomNames[i]);			    	  
			      }
			      specifiedTopAtom = true;
		      }else{
		    	  specifiedTopAtom = false;
		      }
		      
		      str = prop.getProperty("lineWidth", "70");
			  fLineWidth = Integer.parseInt(str);
		      if(fLineWidth < 0){
		    	  fLineWidth = 70;
		      }
		      
		      str = prop.getProperty("indentation", "2");
		      fIndentation = Integer.parseInt(str);
		      if(fIndentation < 0){
		    	  fIndentation = 2;
		      }
		      str = prop.getProperty("space", "1");
		      fSpace = Integer.parseInt(str);
		      if(fSpace < 0){
		    	  fSpace = 1;
		      }		      
		      str = prop.getProperty("memFormat", "inconsistent");
		      if(str.equals("inconsistent")){
		    	  memFormat = PrintFormat.INCONSISTENT;
		      }else if(str.equals("consistent")){
		    	  memFormat = PrintFormat.CONSISTENT;
		      }else if(str.equals("off")){
		    	  memFormat = PrintFormat.OFF;
		      }else{
		    	  memFormat = PrintFormat.INCONSISTENT;
		      }
		      str = prop.getProperty("atomFormat", "inconsistent");
		      if(str.equals("inconsistent")){
		    	  atomFormat = PrintFormat.INCONSISTENT;
		      }else if(str.equals("consistent")){
		    	  atomFormat = PrintFormat.CONSISTENT;
		      }else if(str.equals("off")){
		    	  atomFormat = PrintFormat.OFF;
		      }else{
		    	  atomFormat = PrintFormat.INCONSISTENT;
		      }
		      str = prop.getProperty("sort", "true");
		      if(str.equals("true")){
		    	  fSort = true;
		      }else{
		    	  fSort = false;
		      }
		      
		      str = prop.getProperty("abbrAtom", "true");
		      if(str.equals("true")){
		    	  abbrAtom = true;
		      }else{
		    	  abbrAtom = false;
		      }		      

		      str = prop.getProperty("abbrMem", "true");
		      if(str.equals("true")){
		    	  abbrMem = true;
		      }else{
		    	  abbrMem = false;
		      }		      

		      str = prop.getProperty("listNotation", "true");
		      if(str.equals("true")){
		    	  listNotation = true;
		      }else{
		    	  listNotation = false;
		      }		      
		      
		      str = prop.getProperty("infixNotation", "true");
		      if(str.equals("true")){
		    	  infixNotation = true;
		      }else{
		    	  infixNotation = false;
		      }
		      		      
		      str = prop.getProperty("memSort", "true");
		      if(str.equals("true")){
		    	  memSort = true;
		    	  
		      }else{
		    	  memSort = false;
		      }		      

		}catch (NumberFormatException e) {
			Env.e("Dumper2:Value must be a positive integer.");
			}catch (Exception e) {
		      e.printStackTrace();
		    }
	}
	
}

enum PrintFormat{
	CONSISTENT,
	INCONSISTENT,
	OFF
}

class DumpMembrane {
	static HashMap<String, int[]> binops = new HashMap<String, int[]>();

	private static final int xfy = 0;

	private static final int yfx = 1;

	private static final int xfx = 2;
	
	private Set<Atom> atoms;  //膜下のアトム
	
	private Set<Membrane> mems; //子膜
		
	private List<Atom> ruleObjectName = new LinkedList();  
	
	private GlaphLayouter out;
	
	private int indentation;
	
	/** 膜内の要素を仕分けして出力する*/
	DumpMembrane(Membrane mem , GlaphLayouter o ,int indent){
		indentation = indent;
		out = o;
		atoms = new HashSet<Atom>(mem.atoms.size()); // 今はproxyを表示しているため。いずれ上に戻す
		Iterator<Atom> it_a = mem.atomIterator();
		while (it_a.hasNext()) {
			Atom a = it_a.next();
			if (Env.hideProxy && !a.isVisible()) {
				// PROXYを表示させない 2005/02/03 T.Nagata
				continue;
			}
			atoms.add(a);
		}
		
		mems = new HashSet<Membrane>(mem.mems); 
		
	}
	
	/** 膜内の要素を仕分けして出力する. アトム引数内で膜を出力する場合*/
	DumpMembrane(Membrane mem , GlaphLayouter o ,int indent, Atom removeAtom){
		indentation = indent;
		out = o;
		atoms = new HashSet<Atom>(mem.atoms.size()); // 今はproxyを表示しているため。いずれ上に戻す
		Iterator<Atom> it_a = mem.atomIterator();
		while (it_a.hasNext()) {
			Atom a = it_a.next();
			if (Env.hideProxy && !a.isVisible()) {
				// PROXYを表示させない 2005/02/03 T.Nagata
				continue;
			}
			atoms.add(a);
		}
		atoms.remove(removeAtom);
		
		mems = new HashSet<Membrane>(mem.mems); 
	}

	
	static {
		binops.put("^", new int[] { xfy, 200 });
		binops.put("**", new int[] { xfy, 300 });
		binops.put("mod", new int[] { xfx, 300 });
		binops.put("*", new int[] { yfx, 400 });
		binops.put("/", new int[] { yfx, 400 });
		binops.put("*.", new int[] { yfx, 400 });
		binops.put("/.", new int[] { yfx, 400 });
		binops.put("+", new int[] { yfx, 500 });
		binops.put("-", new int[] { yfx, 500 });
		binops.put("+.", new int[] { yfx, 500 });
		binops.put("-.", new int[] { yfx, 500 });
		binops.put("=", new int[] { xfx, 700 });
		binops.put("==", new int[] { xfx, 700 });
		binops.put("=:=", new int[] { xfx, 700 });
		binops.put("=\\=", new int[] { xfx, 700 });
		binops.put(">", new int[] { xfx, 700 });
		binops.put(">=", new int[] { xfx, 700 });
		binops.put("<", new int[] { xfx, 700 });
		binops.put("=<", new int[] { xfx, 700 });
		binops.put("=:=.", new int[] { xfx, 700 });
		binops.put("=\\=.", new int[] { xfx, 700 });
		binops.put(">.", new int[] { xfx, 700 });
		binops.put(">=.", new int[] { xfx, 700 });
		binops.put("<.", new int[] { xfx, 700 });
		binops.put("=<.", new int[] { xfx, 700 });
		binops.put(":", new int[] { xfy, 800 }); // ただし : の左辺が
		// [a-z][A-Za-z0-9_]* のときのみ
	}
	
	void resetAtomSet(Set<Atom> newAtoms){
		atoms = newAtoms;
	}
	
	void resetMemSet(Set<Membrane> newMems){
		mems = newMems;
	}
	
	static int getyfx(){
		return yfx;
	}

	static int getxfy(){
		return xfy;
	}
//	static String PROFILE_TABS = "RuleName,\tThreadID,\tAtomDrivenTime,\tMembraneDrivenTime,\t"
//			+ "AtomDrivenSucceed,\tMembraneDrivenSucceed,\t"
//			+ "AtomDrivenApply,\tMembraneDrivenApply,\t"
//			+ "BackTracks,\tLockFailures\n";
	
	private boolean addComma(boolean commaFlag){
		if (commaFlag){
			out.print(",");
			if(Dumper2.getMemFormat() != PrintFormat.OFF){
				out.brk(Dumper2.get_fSpace());
			}
		}else{
			commaFlag = true;
		}
		return commaFlag;
	}


	void dump(Membrane mem, boolean doLock) {
		boolean locked = false;
		if (doLock) {
			if (mem.getLockThread() != Thread.currentThread()) {
				if (!mem.lock()) {
					out.print("...");
					return;
				}
				locked = true;
			}
		}

		boolean commaFlg = false;

		// #1 - アトムの出力

		Iterator<Atom> it_a = mem.atomIterator();
        if(!Dumper2.isAbbrAtom()){
			while (it_a.hasNext()) {
					Atom a = it_a.next();
					if(a.getName().matches("^[@].*")){
						ruleObjectName.add(a);
						atoms.remove(a);
					}
					if (!atoms.contains(a))
						continue;
					commaFlg = addComma(commaFlg);
					AtomGroup ag = new AtomGroup(a, atoms, mems, this , Dumper2.getLineWidth(), indentation, false);
					ag.dumpAtomGroup(a, atoms, mems);
					out.pre(ag.getBackend().getString());		
				}
		} else {

			List predAtoms[] = { new LinkedList(), new LinkedList(),  new LinkedList(),new LinkedList(),
					new LinkedList(), new LinkedList(), new LinkedList(),new LinkedList(),new LinkedList() };
			
			List<AtomGroup> listToDumpAtomGroup = new LinkedList<AtomGroup>(); 
			List<AtomGroup> listToSortAtomGroup = new LinkedList<AtomGroup>(); 

			// 起点にするアトムとその優先順位:
			// 0. 引数なしのアトム、(および最終引数がこの膜以外へのリンクであるアトム)
			// 1. 結合度が = 以下の2引数演算子のアトム（臨時：およびinlineアトム）
			//通常のシンボル名でリンク先が最終引数の1引数アトムのうち
			// 2. リンク先が整数であるアトム
			// 3. 2,4,5以外
			// 4. シンボル名が+でリンクが膜をつらぬかないアトム
			// 5. シンボル名が+でリンクが膜をつらぬくアトム
			//toplevel指定機能ONのとき
			// 6. toplevelに指定されたアトム名で最終引数のリンク先が最終引数の2引数以上のアトム
			// 7. それ以外
			//toplevel指定機能OFFのとき
			// 6. 通常のシンボル名で最終引数のリンク先が最終引数の2引数以上のアトム
			// 7. シンボル名がtrue or false
			// 8. 第3引数のリンク先が最終引数のconsアトム

			// 通常でないアトム名（起点にしないアトムの名前）:
			// - $in,$out,[],整数,実数,およびA-Zで始まるアトム,true/false

			it_a = mem.atomIterator();
			while (it_a.hasNext()) {
				Atom a = it_a.next();
				if (a.getArity() == 0
						|| a.getLastArg().getAtom().getMem() != mem) {
					if(a.getName().matches("^[@].*")){
						ruleObjectName.add(a);
						atoms.remove(a);
					}else{
					predAtoms[0].add(a);
					}
				} else if (a.getArity() == 2 
						&& isInfixOperator(a.getName())
						&& getBinopPrio(a.getName()) >= 700
						|| a.getName().startsWith("/*inline*/")) {
					predAtoms[1].add(a);
				} else if (a.getLastArg().isFuncRef()) {
					// todo コードが気持ち悪いのでなんとかする (1)
					if (!a.getFunctor().isSymbol())
						continue; // 通常のファンクタを起点にしたい
					if (a.getName().matches("^[A-Z].*"))
						continue; // 補完された自由リンクは引数に置きたい
					if (a.getFunctor().equals(Functor.INSIDE_PROXY))
						continue;
					if (a.getFunctor().isOutsideProxy())
						continue;
					if (a.getFunctor().equals(Functor.NIL))
						continue; // []は整数と同じ表示的な扱い
					if (a.getName().matches("[tT][rR][uU][eE]")){
						predAtoms[7].add(a);
						continue;
					}
					if(a.getName().matches("[fF][aA][lL][sS][eE]")){
						predAtoms[7].add(a);
						continue;
					}
					if (a.getArity() == 1) {
						if(a.getLastArg().getAtom().getFunctor().isInteger()){
							predAtoms[2].add(a); //n(0)
						}else if(a.getName().matches("[-\\+]")){
							if(!( a.args[0].getAtom().getFunctor() instanceof SpecialFunctor) ){
								predAtoms[4].add(a); //+aa
							}else{
								predAtoms[5].add(a); //+L0
							}
						}else{
							if(a.getLastArg().getAtom().getName().matches("[-\\+]"))
								continue;
							if(Dumper2.getIsUseSource() || Dumper2.isSpecifiedTopAtom()){
								//toplevel指定機能onのときは,toplevelに指定されていないアトムは後ろにまわす
								if(!Dumper2.getToplevelAtomNameSet().contains(a.getName())){
									predAtoms[7].add(a);
									continue;
								}
							}
							predAtoms[3].add(a);
						}
					}else if(Dumper2.getIsUseSource() || Dumper2.isSpecifiedTopAtom()){
						//toplevel指定機能onのときは,toplevelに指定されていないアトムは後ろにまわす
						if(!Dumper2.getToplevelAtomNameSet().contains(a.getName())){
							predAtoms[7].add(a);
							continue;
						}
						else{
							//TODO ここらの仕分け見直したい
							predAtoms[6].add(a); 
						}
					}else if(!a.getFunctor().equals(Functor.CONS)) {
							predAtoms[6].add(a);
					}else{ // consはできるだけデータとして扱う
						predAtoms[8].add(a);
					}
				}
			}
			//0,1価のアトムにソートをかける，それ以外はcyclicの可能性を想定してあとからソート
			if(Dumper2.isSort()){
				if(!predAtoms[0].isEmpty()){
					Collections.sort(predAtoms[0], new AtomComparator());				
				}
				if(!predAtoms[2].isEmpty()){
					Collections.sort(predAtoms[2], new IntAtomComparator());				
				}
				if(!predAtoms[3].isEmpty()){
					Collections.sort(predAtoms[3], new AtomComparator());				
				}
				if(!predAtoms[4].isEmpty()){
					Collections.sort(predAtoms[4], new plusAtomComparator());				
				}
				if(!predAtoms[5].isEmpty()){
					Collections.sort(predAtoms[5], new plusLinkComparator());				
				}
			}

			// predAtoms内のアトムを起点に出力
			for (int phase = 0; phase < predAtoms.length; phase++) {
				it_a = predAtoms[phase].iterator();
				while (it_a.hasNext()) {
					Atom a = it_a.next();
					if (atoms.contains(a)) { // まだ出力されていない場合
						// 3引数演算子の強制 s=t 表示は、演算子展開表示しないときのみ行う
						if (Dumper2.isInfixNotation()) {
							// consは演算子と同じ表示的な扱い
							if (a.getFunctor().equals(Functor.CONS)
									|| (a.getArity() == 3 && isInfixOperator(a
											.getName()))) {
								AtomGroup ag = new AtomGroup(a, atoms, mems, this, Dumper2.getLineWidth(), indentation,false);
								ag.dumpMathExpression(a, atoms, mems, 700);
								listToSortAtomGroup.add(ag);
										continue;
							}
						}
						if( phase < 6){
							AtomGroup ag = new AtomGroup(a, atoms, mems, this , Dumper2.getLineWidth(), indentation, false);
							ag.dumpAtomGroup(a, atoms, mems);
							listToDumpAtomGroup.add(ag);
							
						}else{
							AtomGroup ag = new AtomGroup(a, atoms, mems, this , Dumper2.getLineWidth(), indentation, false);
							ag.dumpAtomGroup(a, atoms, mems);
							listToSortAtomGroup.add(ag); //ソート用のリストに入れる
						}
					}
				}
			}

			// todo このchangedループもpredAtomsに統合する

			// 閉路がある場合にはまだ残っているので、適当な所から出力。
			// 閉路の部分を探した方がいいが、とりあえずこのまま。
			boolean changed;
			do {
				changed = false;
				it_a = atoms.iterator();
				while (it_a.hasNext()) {
					Atom a = it_a.next();
					// 演算子表示できるときは、データができるだけ引数に来るようにする
					if (Dumper2.isInfixNotation()) {
						// todo コードが気持ち悪いのでなんとかする (2)
						if (!a.getFunctor().isSymbol())
							continue;
						if (a.getName().matches("^[A-Z].*"))
							continue;
						if (a.getFunctor().equals(Functor.INSIDE_PROXY))
							continue;
						if (a.getFunctor().isOutsideProxy())
							continue;
						if (a.getFunctor().equals(Functor.NIL))
							continue;
					}
					// プロキシを省略できるときは、プロキシができるだけ引数に来るようにする
					if (Env.hideProxy/* Env.verbose < Env.VERBOSE_EXPANDPROXIES */) {
						if (a.getFunctor().isInsideProxy())
							continue;
						if (a.getFunctor().isOutsideProxy())
							continue;
					}
					// ここまで残った1引数のアトムはデータの可能性が高いので、できるだけ引数に来るようにする
					if (a.getArity() == 1)
						continue;
					//
					AtomGroup ag = new AtomGroup(a, atoms, mems, this , Dumper2.getLineWidth(), indentation, false);
					ag.dumpAtomGroup(a, atoms, mems);
					listToSortAtomGroup.add(ag);
					changed = true;
					break;
				}
			} while (changed);

			// 残った1引数のアトム（データだと思って保留していた整数など）を起点にして出力する。
			// ただしリンク先が自由リンク管理アトムのときに限る
			//1(L3), {+L3}など
			do {
				changed = false;
				it_a = atoms.iterator();
				while (it_a.hasNext()) {
					Atom a = it_a.next();
					if (a.getArity() == 1) {
						if (a.getLastArg().getAtom().getFunctor() == Functor.INSIDE_PROXY
								|| a.getLastArg().getAtom().getFunctor()
										.isOutsideProxy()) {
							AtomGroup ag = new AtomGroup(a, atoms, mems, this , Dumper2.getLineWidth(), indentation, false);
							ag.dumpAtomGroup(a, atoms, mems);
							listToSortAtomGroup.add(ag);
							changed = true;
							break;
						}
					}
				}
			} while (changed);

			// 残ったアトムの出力
			//元々は s=t の形式で出力していたけど，とりあえず保留
			while (!atoms.isEmpty()) {
				it_a = atoms.iterator();
				Atom a = it_a.next();

				AtomGroup ag = new AtomGroup(a, atoms, mems, this, Dumper2.getLineWidth(), indentation, false);
				ag.dumpAtomGroup(a, atoms, mems);
				listToSortAtomGroup.add(ag);
			}
			
			if(listToDumpAtomGroup.size() != 0){
				Iterator<AtomGroup>it = listToDumpAtomGroup.iterator();
				while (it.hasNext()) {
					AtomGroup ag = it.next();
					commaFlg = addComma(commaFlg);
					out.pre( ag.getBackend().getString() );
				}
			}
			if(Dumper2.isSort() && listToSortAtomGroup.size() != 0){
				//未ソート分をトップレベル名でソートして出力
				Collections.sort(listToSortAtomGroup, new AtomGroupComparator());
			}
			if(listToSortAtomGroup.size() != 0){
				Iterator<AtomGroup>it = listToSortAtomGroup.iterator();
				while (it.hasNext()) {
					AtomGroup ag = it.next();
					commaFlg = addComma(commaFlg);
					out.pre( ag.getBackend().getString() );
				}
			}
		}

		// #2 - 子膜の出力
        Iterator<Membrane> it_m;
        if(Dumper2.isMemSort() && !mem.mems.isEmpty()){
        	//膜名でソート
    		List<Membrane> sortedMems = new LinkedList<Membrane>();
    		List<Membrane> noNameMems = new LinkedList<Membrane>();
    		it_m = mem.memIterator();
    		while(it_m.hasNext()){
    			Membrane m = it_m.next();
    			if(m.name != null){
    				sortedMems.add(m);
    			}else{
    				noNameMems.add(m);
    			}
    		}
    		if(!sortedMems.isEmpty()){
    			Collections.sort(sortedMems, new MembraneComparator());
    			if(!noNameMems.isEmpty()){
    				sortedMems.addAll(noNameMems);
    			}
    			it_m = sortedMems.listIterator();
    		}else{
    			it_m = mem.memIterator();
    		}
        }else{
    		it_m = mem.memIterator();
        }
		while (it_m.hasNext()) {
			Membrane m = it_m.next();
			if(mems.contains(m)){
				mems.remove(m);
				commaFlg = addComma(commaFlg);
				if (m.name != null)
					out.print(m.name);
				if(Dumper2.getMemFormat() != PrintFormat.OFF){
					out.indMem();
				}
				out.print("{");
				Dumper2.dump(m,out);
				out.print("}");
					if (m.kind == 1)
					out.print("_");
				else if (m.kind == Membrane.KIND_ND)
					out.print("*");
			}
		}

		// #3 - ルールの出力  
//		Iterator<Atom> it_r = ruleObjectName.iterator();
//		while(it_r.hasNext()){
//			commaFlg = addComma(commaFlg);
//			out.print(((Atom)it_r.next()).getFunctor().getName());
//		}
		
		Iterator<Ruleset> it_r;
		// #3 - ルールの出力
		if(Env.showruleset){
			it_r = mem.rulesetIterator();
			while (it_r.hasNext()) {
				commaFlg = addComma(commaFlg);
				out.print(it_r.next().toString());
				}
		}
		if(Env.showrule){
			it_r = mem.rulesetIterator();
			while (it_r.hasNext()) {
				Ruleset rs = it_r.next();
				List<Rule> rules;
				if(rs instanceof InterpretedRuleset)
					rules = ((InterpretedRuleset)rs).rules;
				else
					rules = rs.compiledRules;
				if(rules != null){
					Iterator<Rule> it2 = rules.iterator();
					while (it2.hasNext()) {
						Rule r = it2.next();
						if (r.name != null) {
							commaFlg = addComma(commaFlg);
							out.print("@" + r.toString() + "@");
							if (Env.profile == Env.PROFILE_BYRULE) {
								long times = (Env.majorVersion == 1 && Env.minorVersion > 4) ? (r.atomtime+r.memtime)/1000000 : r.atomtime+r.memtime;
								out.print("_" + (r.atomsucceed+r.memsucceed) + "/" + (r.atomapply+r.memapply) + "(" + times + "msec)");
							} else if (Env.profile == Env.PROFILE_BYRULEDETAIL) {
								long times = (Env.majorVersion == 1 && Env.minorVersion > 4) ? (r.atomtime+r.memtime)/1000000 : r.atomtime+r.memtime;
								out.print("_" + (r.atomsucceed+r.memsucceed) + "/" + (r.atomapply+r.memapply) +
										"(" + r.backtracks + "," + r.lockfailure + ")" + "(" + times + "msec)");
							}//Env.PROFILE_ALLはとりあえず保留 
						}
					}					
				}
			}
		}

		
		if (locked) {
			mem.quietUnlock();
		}
		
		return;

	}
	static boolean isInfixOperator(String name) {
		return binops.containsKey(name);
	}

	static int getBinopType(String name) {
		return ((int[]) binops.get(name))[0];
	}

	static int getBinopPrio(String name) {
		return ((int[]) binops.get(name))[1];
	}
	
}

class AtomGroup{
	private String topLevelName;
	private boolean cyclicList = false ;
	private Set<Atom> atomSet; //保存用
	private Set<Membrane> memSet; //保存用
	private GlaphLayouter appGlaph;
	private boolean appendflg = true;
	private DumpMembrane dumpMem; //所属している膜
	private StringBackend fBackend;
	private int lineWidth;
	private int fIndentation;
	private Link startPosition; //環状リスト用
	private int fOuterprio; //環状リスト用
	private boolean fully; //trueならファンクタ名やアトム名を省略しない

	
	AtomGroup(Atom a, Set atoms, Set mems, DumpMembrane dm, int lineWidth ,int ind, boolean fl){
		this.lineWidth = lineWidth;
		fIndentation = ind;
		topLevelName = a.getName();
		atomSet = new HashSet<Atom>(atoms);
		memSet = new HashSet<Membrane>(mems);
		dumpMem = dm;
		fBackend = new StringBackend(lineWidth );
		appGlaph = new GlaphLayouter(fBackend, fIndentation);
		fully = fl;
	}
	
	StringBackend getBackend(){
		return fBackend;
	}
	
	String getTopLevelName(){
		return topLevelName;
	}		
	
	private void setAppendflg(boolean flg){
		appendflg = flg;
	}
	
	private void setCyclicList(boolean flg){
		this.cyclicList = flg;
	}
	void dumpMathExpression(Atom a, Set atoms, Set mems,int outerprio){
		dumpLink(a.getLastArg(), atoms, mems, outerprio);
		appGlaph.print("=");
		dumpAtomGroupWithoutLastArg(a, atoms, mems, outerprio);
		appGlaph.close();
		appGlaph.flush();
		if(cyclicList){
			fBackend = new StringBackend(lineWidth );
			appGlaph = new GlaphLayouter(fBackend, fIndentation); 
			dumpCyclicList(startPosition, fOuterprio);
			appGlaph.close();
		}
		return;
	}

	void dumpAtomGroup(Atom a, Set atoms, Set mems) {
		dumpAtomGroup(a, atoms, mems, 0, 999);
		appGlaph.close();
		appGlaph.flush();
		if(cyclicList){
			fBackend = new StringBackend(lineWidth );
			appGlaph = new GlaphLayouter(fBackend, fIndentation); 
			dumpCyclicList(startPosition, fOuterprio);
			appGlaph.close();
		}
		return;
	}

	private void dumpAtomGroupWithoutLastArg(Atom a, Set atoms, Set mems,
			int outerprio) {
		dumpAtomGroup(a, atoms, mems, 1, outerprio);

	}
	
	/**
		 * アトムの引数を展開しながら文字列に変換する。 ただし、アトムaの最後のreducedArgCount個の引数は出力しない。
		 * <p>
		 * 出力したアトムはatomsから除去される。 出力するアトムはatomsの要素でなければならない。
		 * 
		 * @param a
		 *            出力するアトム
		 * @param atoms
		 *            まだ出力していないアトムの集合 [in,out]
		 * @param reducedArgCount
		 *            aのうち出力しない最後の引数の長さ
		 */
		public void dumpAtomGroup(Atom a, Set atoms, Set mems, int reducedArgCount,
				int outerprio) {
			atoms.remove(a);
			Functor func = a.getFunctor();
			int arity = func.getArity() - reducedArgCount;
			if (arity == 0) {
				if (!fully){
					appGlaph.print( func.getQuotedAtomName() );
					return;
				}
				appGlaph.print(func.getQuotedFullyAtomName());
				return;
			}
			if (Env.hideProxy // Env.verbose < Env.VERBOSE_EXPANDPROXIES
					&& arity == 1
					&& (func.isInsideProxy() || func.isOutsideProxy())) {
				dumpLink(a.args[0], atoms, mems, outerprio);
				return;
			}
			if (Dumper2.isInfixNotation()) {
				if (arity == 2 && DumpMembrane.isInfixOperator(func.getName())) {
					if (func.getName().equals(":")
							&& (a.args[0].getAtom().getArity() != 1 || !a.args[0]
									.getAtom().getName().matches(
											"[a-z][A-Za-z0-9_]*"))) {
					} else {
						int type = DumpMembrane.getBinopType(func.getName());
						int prio = DumpMembrane.getBinopPrio(func.getName());
						int innerleftprio = prio + (type == DumpMembrane.getyfx() ? 1 : 0);
						int innerrightprio = prio + (type == DumpMembrane.getxfy() ? 1 : 0);
						boolean needpar = (outerprio < innerleftprio || outerprio < innerrightprio);
						if (needpar){
							appGlaph.print("(");
						}
						appGlaph.beginAtomGroup(); //ずれるかも
						dumpLink(a.args[0], atoms, mems, innerleftprio);
						appGlaph.print(func.getName());
						dumpLink(a.args[1], atoms, mems, innerrightprio);						
						if (needpar){
							appGlaph.print(")");
						}
						appGlaph.endAtomGroup();  //ずれるかも
						return;
					}
				}
				if (arity == 2 && func.getName().equals(".")) {
					appGlaph.print("[");
					if(Dumper2.getAtomFormat() != PrintFormat.OFF){
						appGlaph.beginI();
					}
					dumpLink(a.args[0], atoms, mems, outerprio);
					dumpListCdr(a.args[1], atoms, mems, outerprio);
					if(Dumper2.getAtomFormat() != PrintFormat.OFF){
						appGlaph.end();
					}
					if(appendflg){
						appGlaph.print("]");
					}
					return;
				}
			}
			if (!fully)
				appGlaph.print(func.getQuotedFunctorName());
			    //buf.append(dumpCyclicList(l , atoms, mems, outerprio));
			else
				appGlaph.print(func.getQuotedFullyFunctorName());
			boolean listflg = false;
			boolean blockStart = false;
//			if (Env.verbose > Env.VERBOSE_SIMPLELINK
            if(
					(!func.getName().matches("[-\\+]") || !Dumper2.isAbbrAtom() || !Dumper2.isInfixNotation()) 
					|| !(arity == 1)
//					|| !(a.args[0].getAtom().getFunctor() instanceof SpecialFunctor) 
			){
				if(     Dumper2.isListNotation()
						&& reducedArgCount == 0 
						&&  a.args[0].getAtom().getArity() == 3 
						&& a.args[0].getAtom().getFunctor().getName().equals(".")
						&& a.getArity() == 1){ //ap2([4,5],'L1').こういう構造に対処
					//リスト用
					listflg =true;
					appGlaph.print("=");
				}else{
					appGlaph.print("("); //+()
					appGlaph.beginAtomGroup();
					if(Dumper2.getAtomFormat() != PrintFormat.OFF){
						blockStart = true;
					}
				}
			}
			dumpLink(a.args[0], atoms, mems);
			for (int i = 1; i < arity; i++) {
				if(appendflg){
					appGlaph.print(",");
					if(Dumper2.getAtomFormat() != PrintFormat.OFF){
						appGlaph.brk(Dumper2.get_fSpace());
					}
					dumpLink(a.args[i], atoms, mems);
				}
			}
			if(blockStart){
				appGlaph.end();
			}
//			if (Env.verbose > Env.VERBOSE_SIMPLELINK
            if(
			        (!func.getName().matches("[-\\+]") || !Dumper2.isAbbrAtom()|| !Dumper2.isInfixNotation())
					|| !(arity == 1)
//					|| !(a.args[0].getAtom().getFunctor() instanceof SpecialFunctor))
				)if(!listflg && appendflg){
					appGlaph.print(")");
				}
			 	return;
		}

	private void dumpListCdr(Link l, Set atoms, Set mems, int outerprio) {
		boolean terminateCycList=false; //環状リスト出力で2回目この関数に入ったとき用のフラグ
		while (true) {
			if(!atoms.contains(l.getAtom())){
				//環状リスト
				if(cyclicList){
					terminateCycList = true;
				}else{
				 setCyclicList(true);
				}
				break;
			}
			if (!l.isFuncRef())
				break;
			Atom a = l.getAtom();
			if (!a.getFunctor().equals(Functor.CONS))
				break;
			atoms.remove(a);
			appGlaph.print(",");
			if(Dumper2.getAtomFormat() != PrintFormat.OFF){
				appGlaph.brk(Dumper2.get_fSpace());
			}
			dumpLink(a.args[0], atoms, mems);
			l = a.args[1];
		}
        if(cyclicList && terminateCycList){
			appGlaph.print("|");
			appGlaph.print( l.toString().compareTo(l.getBuddy().toString()) >= 0 ? l
					.toString() : l.getBuddy().toString() );
			return;			
		}else if(cyclicList && !terminateCycList){
			startPosition = l;   //リンクのスタート場所を保存
			fOuterprio = outerprio;
			return;
		}else if (l.getAtom().getFunctor().equals(Functor.NIL)) { 
			atoms.remove(l.getAtom());
		}else {
			appGlaph.print("|");
			dumpLink(l, atoms, mems);
		}
		return;
	}

	private void dumpCyclicList(Link l,int outerprio){
		Link startl = l;
		Set<Atom> atoms = this.atomSet;		
		Set<Membrane> mems = this.memSet;		
		appGlaph.print( ( topLevelName = l.toString().compareTo(l.getBuddy().toString()) >= 0 ? l
					.toString() : l.getBuddy().toString() ) );
		appGlaph.print("=");
		dumpLink(l, atoms, mems, outerprio);		
		dumpMem.resetAtomSet(atoms);
		dumpMem.resetMemSet(mems);
		setAppendflg(false);
		return;
	}

	private  void dumpLink(Link l, Set atoms, Set mems) {
		dumpLink(l, atoms, mems, 999);
	}

	private  void dumpLink(Link l, Set atoms, Set mems, int outerprio) {
		// PROXYを表示しない 2005/02/03 T.Nagata
		if (Env.hideProxy && !l.getAtom().isVisible()) {
			Atom tmp_a = l.getAtom();
			Link tmp_l = l;
			Atom tmp_a2 = tmp_a.args[tmp_l.getPos() == 0 ? 0 : 1].getAtom(); //-{}対策
			if(Dumper2.isAbbrMem() && tmp_a.getFunctor().isOutsideProxy()){
			    /* outプロキシの接続先である膜の自由リンクが一つで、一引数の'+'アトムに
			       接続している場合、膜をその場に出力する */
					tmp_l = tmp_a.args[tmp_l.getPos() == 0 ? 1 : 0];
					tmp_a = tmp_l.getAtom(); //inのはず
					Atom  plus = tmp_a.args[tmp_l.getPos() == 0 ? 1 : 0].getAtom(); //plusのはず
				if(!tmp_a.isVisible() && plus.getName().equals("+")){
					Membrane m = tmp_a.getMem();
					if( m.getFreeLinkCount() == 1 ){
						mems.remove(m);
						if(m.name != null){
							if(Dumper2.getMemFormat() != PrintFormat.OFF ){
								appGlaph.indMem();
							}
							appGlaph.print(m.name+"{");
							dump(tmp_a.getMem(), fIndentation , plus ,appGlaph);
							appGlaph.print("}");
						  return;
						}else{
							if(Dumper2.getMemFormat() != PrintFormat.OFF && !tmp_a2.getName().matches("[-\\+]")/*-{}対策*/){
								appGlaph.indMem();
							}
							appGlaph.print("{");
							dump(tmp_a.getMem(), fIndentation , plus, appGlaph);
							appGlaph.print("}");
						return;
						}
					}
				}
			}
			while (!tmp_a.isVisible()) {
				tmp_l = tmp_a.args[tmp_l.getPos() == 0 ? 1 : 0];
				tmp_a = tmp_l.getAtom();
			}
			appGlaph.print( l.toString().compareTo(tmp_l.getBuddy().toString()) >= 0 ? l
					.toString() : tmp_l.getBuddy().toString() );
			return;
		}
		if (Env.verbose < Env.VERBOSE_EXPANDATOMS && l.isFuncRef()
				&& atoms.contains(l.getAtom())) {
			if(Dumper2.isAbbrAtom()){
				dumpAtomGroupWithoutLastArg(l.getAtom(), atoms, mems, outerprio);
				return;
			}else{
				appGlaph.print(l.toString() );
			return;
			}
		} else {
			appGlaph.print(l.toString() );
			return;
		}
	}
	
	/** 膜の中身を引数中に出力する．({}) */
	private void dump(Membrane mem, int indent, Atom removeAtom, GlaphLayouter out) {
		out.beginMem();
		new DumpMembrane(mem , out, 0, removeAtom).dump(mem, true);
		out.endMem();
	}

}
