package daemon;

import java.net.InetAddress;
import java.util.HashMap;

import runtime.AbstractMembrane;

/**
 * グローバルID -> ローカルのobject という変換をするクラス
 * 
 * @author nakajima, n-kato
 *
 */
public class IDConverter{
	//グローバル膜ID (String) -> 膜オブジェクト (AbstractMembrane)
	HashMap memTable = new HashMap();

	
	
	/*
	 * グローバル膜ID -> 膜オブジェクト
	 * @return 登録されていればMembrane, されてなければnull
	 */
	AbstractMembrane getMem(String globalMemID){
		return (AbstractMembrane)memTable.get(globalMemID);
	}


	
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

	/*
	 * グローバル膜ID -> 膜オブジェクト を登録する
	 */
	boolean registerMem(String globalMemID, AbstractMembrane memobj){
		if(memTable.get(globalMemID)==null){
			memTable.put(globalMemID, memobj);
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 膜ID表を初期化
	 */
	void clearMemIDTable(){
		memTable.clear();
	}

	/*
	 * グローバルな膜IDを生成する。
	 *
	 * @return グローバルな膜ID。中身はInetAddress.getLocalHost() + ":" + AbstractMembrane.getID()。getLocalHost()に失敗したらnullが帰る。
	 * @param mem グローバルなIDを振りたい膜
	 */
	public static String getGlobalMembraneID(AbstractMembrane mem){
		 String newid;
		 try {
			 //IDを生成する
			 newid = InetAddress.getLocalHost().toString() + ":" + mem.getLocalID();
			 return newid;
		 } catch (Exception e){
			 //ID生成失敗
			 e.printStackTrace();
		 }
		
		 return null;
	}

	/*
	 * グローバルな膜IDを作成して、同時に表に登録する。
	 * 
	 * @return グローバルな膜ID。中身はInetAddress.getLocalHost() + ":" + AbstractMembrane.getID()。getLocalHost()に失敗したらnullが帰る。
	 * @param mem グローバルなIDを振りたい膜
	 */	
//		public static String getGlobalMembraneID(AbstractMembrane mem){
//			//もう登録済みなら登録されているIDを返す
//			if(localMemTable.get(mem) != null){
//				return (String)(localMemTable.get(mem)); 
//			}
//		
//			String newid;
//			try {
//				//IDを生成する
//				newid = InetAddress.getLocalHost().toString() + ":" + mem.getLocalID();
//				//ID登録
//				localMemTable.put(mem,newid);
//				return newid;
//			} catch (Exception e){
//				//ID生成失敗
//				e.printStackTrace();
//			}
//		
//			return null;
//		}

}