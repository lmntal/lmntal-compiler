/*
 * 作成日: 2005/12/30
 * */

package util;

import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * ストリームからクラスをロードする。それだけ。
 * 
 * TODO キャッシュとかその他いろいろ
 * 
 * @author okabe
 * 
 */
public class StreamClassLoader extends ClassLoader {
	/**
	 * @param name バイナリ名(Java API 参照)
	 * @param in InputStream
	 * @return Class
	 * @throws ClassNotFoundException
	 */
	public Class findClass(String name, InputStream in)
			throws ClassNotFoundException {
		byte[] classData = getClassData(name, in);
		return defineClass("translated." + name, classData, 0, classData.length);
	}

	/**
	 * @param name バイナリ名
	 * @param in InputStream
	 * @return classData
	 */
	private byte[] getClassData(String name, InputStream in) {
		byte[] temp = new byte[65536];
		ByteArrayOutputStream baos = new ByteArrayOutputStream(65536);
		int len = 0;
		try {
			while ((len = in.read(temp)) > 0) {
				baos.write(temp, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] classData = baos.toByteArray();
		return classData;
	}
}