/*
 * 作成日: 2003/12/22
 *
 */
package runtime;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 簡易版クラスローダ。ファイルから読み込む。なんだかなぁ
 * 
 * @author hara
 */
public class FileClassLoader extends ClassLoader {
	// 読み込むパス : File -> null   重複をなくしたいのでこうした。
	static Map<File, Object> path = new HashMap<File, Object>();
	static {
		path.put(new File("."), null);
	}
	
	/**
	 * クラスパスを追加する
	 * @param thePath
	 */
	public static void addPath(File thePath) {
		path.put(thePath, null);
	}
	
	/**
	 * クラスを読み込んで返す
	 * @param className クラス名
	 */
	public Class<?> findClass(String className) {
//		System.out.println("TRY   to load " + className);
		for(Iterator<File> i=path.keySet().iterator();i.hasNext();) {
			File path = i.next();
			try {
				File filename = new File(path + "/" + className + ".class");
//				System.out.println("path "+path);
//				System.out.println("FILE "+filename);
				FileInputStream fi = new FileInputStream( filename );
				byte[] buf = new byte[fi.available()];
				int len = fi.read(buf);
				fi.close();
				//Env.p("FileClassLoader : "+buf);
				return defineClass(InlineUnit.className(className), buf, 0, len);
			} catch (Exception e) {
//				e.printStackTrace();
			}
		}
//		System.out.println("FAILED to load " + className);
		return null;
	}
}
