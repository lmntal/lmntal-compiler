package daemon;

//import java.net.InetAddress;
import java.util.HashMap;

import runtime.*;

/**
 * グローバルID -> ローカルのobject という変換をするクラス（設計中）
 * 
 * <p>NEW_->objectの変換は、別途行う。
 * 
 * @author nakajima, n-kato
 *
 */
public class IDConverter {
	/** グローバルルールセットID (String) -> Ruleset */
	static HashMap rulesetTable = new HashMap();
	/** グローバル膜ID (String) -> AbstractMembrane */
	static HashMap memTable = new HashMap();

	////////////////////////////////////////////////////////////////	

//	static void init() {
//		rulesetTable.clear();
//		memTable.clear();
//	}

	/** 指定されたルールセットを表に登録する */
	public static void registerRuleset(String globalid, Ruleset rs){
		rulesetTable.put(globalid, rs);
	}
	/** 指定されたglobalRulesetIDを持つルールセットを探す
	 * @return Ruleset（見つからなかった場合はnull）*/
	public static Ruleset lookupRuleset(String globalRulesetID){
		return (Ruleset)rulesetTable.get(globalRulesetID);
	}

	/** 指定された膜を表に登録する */
	public static void registerGlobalMembrane(String globalMemID, AbstractMembrane mem) {
		memTable.put(globalMemID, mem);
	}
	/** 指定されたglobalMemIDを持つ膜を探す
	 * @return AbstractMembrane（見つからなかった場合はnull）*/
	public static AbstractMembrane lookupGlobalMembrane(String globalMemID){
		return (AbstractMembrane)memTable.get(globalMemID);
	}

	////////////////////////////////////////////////////////////////	
	
	/** グローバル膜ID (String) -> AbstractMembrane */
	HashMap newMemTable = new HashMap();
	/** ローカルアトムIDまたはNEW_ (String) -> Atom */
	HashMap newAtomTable = new HashMap();
	
	////////////////////////////////////////////////////////////////

//	void clear() {
//		newMemTable.clear();
//		newAtomTable.clear();
//	}
	
	////////////////////////////////////////////////////////////////

	/** 指定された膜を表に登録する */
	public void registerNewMembrane(String globalMemID, AbstractMembrane mem) {
		newMemTable.put(globalMemID, mem);
	}
	/** グローバル膜IDまたはNEW_に対応する膜を探す
	 * @return Membrane（見つからなかった場合はnull）*/
	public AbstractMembrane lookupMembrane(String memid) {
		Object obj = newMemTable.get(memid);
		if (obj instanceof AbstractMembrane) return (AbstractMembrane)obj;
		return (AbstractMembrane)memTable.get(memid);
	}

	/** 指定されたアトムを表に登録する */
	public void registerNewAtom(String atomID, Atom atom) {
		newAtomTable.put(atomID, atom);
	}
	/** アトムIDに対応するアトムを探す
	 * @param mem 所属膜
	 * @return Atom（見つからなかった場合はnull）*/
	public Atom lookupAtom(AbstractMembrane mem, String atomid) {
		Object obj = newAtomTable.get(atomid);
		if (obj instanceof Atom) return (Atom)obj;
		if (mem instanceof Membrane) {
			return (Atom)((Membrane)mem).lookupAtom(atomid);
		}
		return null;
	}
	
	////////////////////////////////////////////////////////////////
	
	/*
	 * グローバル膜ID -> ローカル膜ID を登録する
	 */
//	boolean registerMemID(String globalMemID, String localMemID){
//		if(memTable.get(globalMemID)== null){
//			//登録されていなければ登録する
//			memTable.put(globalMemID, localMemID);
//			return true;
//		} else {
//			//登録済みなら現在登録されているのがlocalMemIDと同じか調べる
//			if(((String)memTable.get(globalMemID)).equalsIgnoreCase(localMemID)){
//				return true;
//			} else {
//				//違っていたらfalse
//				return false;
//			}
//		}
//	}
//
//	/*
//	 * 膜ID表を初期化
//	 */
//	void clearMemIDTable(){
//		memTable.clear();
//	}
//
	/*
	 * グローバルな膜IDを作成して、同時に表に登録する。
	 * 
	 * @return グローバルな膜ID。中身はInetAddress.getLocalHost() + ":" + AbstractMembrane.getID()。getLocalHost()に失敗したらnullが帰る。
	 * @param mem グローバルなIDを振りたい膜
	 */	
//		public static String getGlobalMembraneID(AbstractMembrane mem){
//			//もう登録済みなら登録されているIDを返す
//			if(memTable.get(mem) != null){
//				return (String)(memTable.get(mem)); 
//			}
//		
//			String newid;
//			try {
//				//IDを生成する
//				newid = InetAddress.getLocalHost().toString() + ":" + mem.getLocalID();
//				//ID登録
//				memTable.put(mem,newid);
//				return newid;
//			} catch (Exception e){
//				//ID生成失敗
//				e.printStackTrace();
//			}
//		
//			return null;
//		}

}