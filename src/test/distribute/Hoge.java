package test.distribute;



import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.HashMap;

class Hoge {
	public static void main(String args[]){
		//TestStringTokenizer();
//		TestStringSplit();
//		TestStringSplit2();
//		DumpHashMap();
		DumpHashMapNeat();			
	}
	
	static void DumpHashMapNeat(){
		HashMap map = new HashMap();

		for(int i = 0; i < 15; i++){
			map.put(new String("key" + i), new String("value" + i));
		}

		Set set = map.entrySet();		
		Iterator it = set.iterator();
		
		while(it.hasNext()){
			System.out.println(it.next());	
		}
	}
	
	static void DumpHashMap(){
		HashMap map = new HashMap();

		for(int i = 0; i < 15; i++){
			map.put(new String("key" + i), new String("value" + i));
		}

		Set set = map.entrySet();		
		System.out.println(set);
	}

	
	static void TestStringSplit2(){
		String testStr = "REGISTERLOCAL 20";

		String result[] = new String[3];

		try{
			result = testStr.split(" ", 3);

			for(int i = 0 ; i < result.length; i++){
				if(result[i] == null){
					System.out.println("null");
				} else {
					System.out.println("result[" + i + "] is: " + result[i]);
				}
			}
		} catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	
	static void TestStringSplit(){
		String testStr = "10 \"banon\" 9 connect";
		
		String result[] = new String[3];
		
		result = testStr.split(" ", 3);
		
		for(int i = 0 ; i < result.length; i++){
			System.out.println(result[i]);
		}
		
	}
	
	static void TestStringTokenizer(){
		String testStr = new String("msgid \"banon\" rgid connect");
		
		StringTokenizer tokens = new StringTokenizer(testStr, " ");
		int msgid;
		
		while(true){
			if(tokens.hasMoreTokens()){
				System.out.println("token: " + tokens.nextToken());
			} else {
				break;
			}
		}
		
	}
	
}