package chorus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Setting {
	private HashMap settingMap = new HashMap();
	
	final static 
	private String FILE_NAME = "../../Chorus.ini";
	
	public Setting(){ 
		try {
			getSetting(new FileReader(FILE_NAME));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getValue(String key){
		return (String)settingMap.get(key);
	}

	/**
	 * file2からみたfile1の相対アドレスを返す．
	 * 引数のディレクトリ区切りは"\"．
	 * 結果のディレクトリの区切りは"/"．
	 * @param file1
	 * @param file2
	 * @return
	 */
	public static String getRelativeAddress(String file1, String file2){
		File f1 = new File(file1);
		File f2 = new File(file2);
		StringBuffer s1 = new StringBuffer(f1.getAbsolutePath());
		StringBuffer s2 = new StringBuffer(f2.getAbsolutePath());
		StringBuffer result = new StringBuffer();
		
		int end = 0; // 共通部分の終わり
		for(int i = 0; i < s2.length(); i++){
			if(end == 0 && s1.charAt(i) == s2.charAt(i)){ continue; }
			if(end == 0){ end = i; }
			if(s2.charAt(i) == '\\'){
				result.append("../");
			}
		}
		for(int i = end; i < s1.length(); i++){
			if(s1.charAt(i) != '\\'){
				result.append(s1.charAt(i));
			}else{
				result.append("/");
			}
		}
		return result.toString();
		
	}
	
	private void getSetting(FileReader file){
		int i;
		StringBuffer s = new StringBuffer();
		String key = null;
		String value = null;
		boolean isKey = true;
		try {
			while((i = file.read()) != -1){
				if((char)i == '='){
					if(!isKey){ System.err.println("設定ファイルが不正です"); }
					key = s.toString();
					s = new StringBuffer();
					isKey = false;
					continue;
				}
				else if((char)i == '\n' || (char)i == '\r'){
					if(isKey || key == null){
						key = value = null;
						continue;
					}
					value = s.toString();
					s = new StringBuffer();
					isKey = true;
					settingMap.put(key, value);
					key = value = null;
					continue;
				}
				s.append((char)i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}