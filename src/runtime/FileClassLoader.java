/*
 * 作成日: 2003/12/22
 *
 */
package runtime;

import java.io.*;

/**
 * 簡易版クラスローダ。ファイルから読み込む。なんだかなぁ
 * 
 * @author hara
 */
public class FileClassLoader extends ClassLoader {
	String path;
	
	/**
	 * クラスを読み込んで返す
	 */
	public Class findClass(String uname) {
		try {
			File filename = InlineUnit.classFile(uname);
			FileInputStream fi = new FileInputStream( filename );
			byte[] buf = new byte[fi.available()];
			int len = fi.read(buf);
			//Env.p("FileClassLoader : "+buf);
			return defineClass(InlineUnit.className(uname), buf, 0, len);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
