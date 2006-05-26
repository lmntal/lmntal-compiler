package chorus;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Setting {
	private FileReader file;
	private HashMap settingMap = new HashMap();
	
	final static 
	private String FILE_NAME = "../../Chorus.ini";
	
	public Setting(){ 
		try {
			file = new FileReader(FILE_NAME);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getSetting(){
		int i;
		StringBuffer s = new StringBuffer();
		try {
			while((i = file.read()) != -1){
				if((char)i == '='){
					
				}
				s.append((char)i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}