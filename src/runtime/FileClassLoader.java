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
	 * 探すパスを設定する。１個だけ。
	 * @param path
	 */
	public void setClassPath(String path) {
		this.path = path;
	}
	
	/**
	 * 現在のサーチパスでクラス cname を読み込むとしたらどんなファイル名になるかを返す
	 * @param cname
	 * @return
	 */
	public File filename_of_class(String cname) {
		return new File(path + "/" + cname + ".class");
	}
	
	/**
	 * クラスを読み込んで返す
	 */
	public Class findClass(String cname) {
		try {
			File filename = filename_of_class(cname);
			FileInputStream fi = new FileInputStream( filename );
			byte[] buf = new byte[fi.available()];
			int len = fi.read(buf);
			//Env.p("FileClassLoader : "+buf);
			return defineClass(cname, buf, 0, len);
		} catch (Exception e) {
		}
		return null;
	}
}
