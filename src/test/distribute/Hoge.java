package test.distribute;

import java.util.StringTokenizer;


class Hoge {
	public static void main(String args[]){
		//TestStringTokenizer();
		TestStringSplit();
			
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