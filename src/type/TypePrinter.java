package type;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import runtime.Env;
import runtime.Functor;
import type.argument.ActiveAtomPath;
import type.argument.ArgumentInferer;
import type.argument.ConstraintSet;
import type.argument.Path;
import type.argument.TypeVarConstraint;
import type.quantity.FixedCount;
import type.quantity.FixedCounts;
import type.quantity.QuantityInferer;

/**
 * 型解析・推論結果を出力する
 * @author kudo
 *
 */
public class TypePrinter {

	private final Map<String,Map<Functor, TypeVarConstraint[]>> memnameToFunctorTypes;
	private final TreeSet<String> sortedMemNames;
	/** 念のため、ファンクタを名前順->引数数順でソートして管理する(意味ないかも) */
	private final TreeSet<Functor> sortedFunctors;

	/** 膜名 -> アトム／子膜の子数のセット */
	private final Map<String, FixedCounts> memnameToCounts;
	
	public TypePrinter(ArgumentInferer ai, QuantityInferer qi){
		
		//まず引数の型情報を集約する
		ConstraintSet cs = ai.getConstraints();
		Set<TypeVarConstraint> tvcs = cs.getTypeVarConstraints();
		memnameToFunctorTypes = new HashMap<String,Map<Functor,TypeVarConstraint[]>>();
		sortedMemNames = new TreeSet<String>();
		sortedFunctors = new TreeSet<Functor>(new FunctorComparator());
		for(TypeVarConstraint tvc : tvcs){
			Path p = tvc.getPath();
			// TODO TracingPathについてはとりあえず無視
			if(!(p instanceof ActiveAtomPath))continue;
			ActiveAtomPath aap = (ActiveAtomPath)p;
			String memname = aap.getMemName();
			sortedMemNames.add(memname);
			if(!memnameToFunctorTypes.containsKey(memname))
				memnameToFunctorTypes.put(memname, new HashMap<Functor,TypeVarConstraint[]>());
			Map<Functor, TypeVarConstraint[]> functorToArgumentTypes = memnameToFunctorTypes.get(memname);
			Functor f = aap.getFunctor();
			sortedFunctors.add(f);
			if(!functorToArgumentTypes.containsKey(f))
				functorToArgumentTypes.put(f, new TypeVarConstraint[f.getArity()]);
			TypeVarConstraint[] argtypes = functorToArgumentTypes.get(f);
			argtypes[aap.getPos()] = tvc;
		}
		
		//次に個数情報を得、ファンクタ名を集約する
		memnameToCounts = qi.getMemNameToFixedCountsSet();
		for(String memname : memnameToCounts.keySet()){
			sortedMemNames.add(memname);
			FixedCounts fcs = memnameToCounts.get(memname);
			for(Functor f : fcs.functorToCount.keySet()){
				sortedFunctors.add(f);
			}
		}
	}
	
	public void printAll(){
		Env.p("Type Information : ");
		for(String memname : sortedMemNames){
			FixedCounts fcs = memnameToCounts.get(memname);
			Env.p(memname + "{");
			// アクティブアトムの情報を出力
			for(Functor f : sortedFunctors){
				
				// データアトム、コネクタは無視する
				if(TypeEnv.outOfPassiveFunctor(f) != TypeEnv.ACTIVE)continue;
				
				Map<Functor, TypeVarConstraint[]> functorToArgumentTypes = memnameToFunctorTypes.get(memname);
				
				if(fcs.functorToCount.containsKey(f)){

					StringBuffer texp = new StringBuffer("");
					texp.append("\t" + f.getQuotedAtomName() + "(");

					// 引数の型を表示
					TypeVarConstraint[] argtypes = functorToArgumentTypes.get(f);
					for(int i=0;i<argtypes.length;i++){
						if(i!=0)texp.append(", ");
						texp.append(argtypes[i].shortString());
					}
					texp.append(")");
					
					FixedCount fc = fcs.functorToCount.get(f);
					texp.append(" : " + fc);
					
					Env.p(texp);
				}
			}
			
			// 子膜の情報を出力
			for(String childname : sortedMemNames){
				
				if(fcs.memnameToCount.containsKey(childname)){
					FixedCount fc = fcs.memnameToCount.get(childname);
					Env.p("\t" + childname + "{} : " + fc);
				}
			}
			
			Env.p("}");
		}
	}

	/**
	 * ファンクタを名前順(辞書式)->引数数順でソートする
	 */
	class FunctorComparator implements Comparator<Functor>{
		public int compare(Functor f1, Functor f2){
			int nc = f1.getName().compareTo(f2.getName());
			if(nc != 0)return nc;
			else{
				return f1.getArity() - f2.getArity();
			}
		}
	}
}
