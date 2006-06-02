package chorus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * 設定ファイルから各種設定を読み込む
 * @author nakano
 *
 */
public class Setting {
	private HashMap settingMap = new HashMap();
	
	final static 
	private String FILE_NAME = "../../chorus.conf";
	
	public Setting(){ 
		try {
			getSetting(new FileReader(FILE_NAME));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 項目の値を取得する
	 * @param key 項目名
	 * @return 項目に対応する値
	 */
	public String getValue(String key){
		if(!settingMap.containsKey(key)){
			System.err.println("項目\"" + key + "\"が存在しません．");
			System.exit(0);
		}
		return (String)settingMap.get(key);
	}
	
	public String tryGetValue(String key){
		if(!settingMap.containsKey(key)){
			return null;
		}
		return (String)settingMap.get(key);
	}
	
	/**
	 * 項目の値を取得する
	 * 取得する際に値をパスと解釈して，存在するパスかどうかをチェックする．
	 * @param key　項目名
	 * @return　項目に対応する値
	 */
	public String getFilePass(String key){
		if(!settingMap.containsKey(key)){
			System.err.println("項目\"" + key + "\"が存在しません．");
			System.exit(0);
		}
		if(!(new File(((String)settingMap.get(key)).replaceAll("\\\\",""))).exists()){
			System.err.println(((String)settingMap.get(key)).replaceAll("\\\\","")+"　が存在しません．");
			System.exit(0);
		}
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
		File f2 = new File(file2 + ".java");
		if(!f1.exists()){
			System.err.println(file1+"　が存在しません．");
			System.exit(0);
		}
		if(!f2.exists()){
			System.err.println(file2+"　が存在しません．");
			System.exit(0);
		}
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
		boolean isComment = false;
		try {
			while((i = file.read()) != -1){
				//　コメント処理
				if((char)i == '#'){
					isComment = true;
				}
				if(((char)i != '\n' && (char)i != '\r') && isComment){
					continue;
				}
				else if(((char)i == '\n' || (char)i == '\r') && isComment){
					isComment = false;
					continue;
				}
				
				// key読み込み
				if((char)i == '='){
					if(!isKey){ System.err.println("設定ファイルが不正です"); }
					key = s.toString();
					s = new StringBuffer();
					// key読み込みフラグ終了
					isKey = false;
					continue;
				}
				// value読み込み
				else if((char)i == '\n' || (char)i == '\r'){
					// keyが取得できていなければ破棄
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
				// 文字取得
				s.append((char)i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}