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
 *
 */
public class FileClassLoader extends ClassLoader {
	public Class findClass(String name) {
		try {
			FileInputStream fi = new FileInputStream( name + ".class" );
			byte[] buf = new byte[fi.available()];
			int len = fi.read(buf);
			//Env.p("FileClassLoader : "+buf);
			return defineClass(name, buf, 0, len);
		} catch (Exception e) {
		}
		return null;
	}
}
