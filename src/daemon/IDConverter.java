package daemon;

import java.net.InetAddress;
import java.util.HashMap;

import runtime.AbstractMembrane;

/*
 * グローバルID -> ローカルID, object という変換をするクラス
 * 
 * @author nakajima
 *
 */
public class IDConverter{
	//グローバル膜ID -> 膜オブジェクト
	HashMap memTable = new HashMap();
	//ローカルID -> 
	
	
	/*
	 * グローバル膜ID -> 膜オブジェクト
	 * @return 登録されていればAbstractMembrane, されてなければnull
	 */
	AbstractMembrane getMem(String globalMemID){
		return (AbstractMembrane)memTable.get(globalMemID);
	}
	
	/*
	 * グローバル膜ID -> ローカル膜ID を登録する
	 */
	boolean registerMemID(String globalMemID, String localMemID){
		if(memTable.get(globalMemID)!= null){
			//登録済みならtrue
			return true;
		} else {
			//登録されていなければ登録する
			memTable.put(globalMemID, localMemID);
			return true;			
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