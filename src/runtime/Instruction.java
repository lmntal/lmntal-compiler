/*
 * 作成日: 2003/10/21
 *
 */
package runtime;
import java.util.*;

/**
 * 1 つの命令を保持する。
 * 
 * 複数でも構わないとおもう。
 * 
 * デバッグ用表示メソッドを備える。
 *
 * @author pa
 *
 */
public class Instruction {
	public static final int NULL      = 0;
	public static final int MEM       = 1;
	public static final int VAR       = 2;
	public static final int NAME      = 3;
	public static final int AT        = 4;
	public static final int METAVAR   = 5;

    //引数無しだと初期容量は10(by api仕様書)
	public List data = new ArrayList();
	
	public Instruction() {
		// たとえば [react, [1, 2, 5]]
		ArrayList sl = new ArrayList();
		sl.add(new Integer(1));
		sl.add(new Integer(2));
		sl.add(new Integer(5));
		data.add("react");
		data.add(sl);
		System.out.println(data);
	}

    /**
     * デバッグ用表示メソッド。
     *
     * @author NAKAJIMA Motomu <nakajima@ueda.info.waseda.ac.jp>
     * @return String
     *
     * メモ：Instructionの中は、Listの中にArrayListが入れ子になって入っている。
     *
     */
    public String toString(){
	//手抜きな方法
	//	return "not implemented\n";

	//手抜きな方法その2
	Object hoge;
	hoge = (Object)data;
	return hoge.toString();
    }


    /**
     * デバッグ用表示メソッド。与えられたListをObject[]に変換し、それぞれの要素に対してtoString()を呼ぶ。
     *
     * @author NAKAJIMA Motomu <nakajima@ueda.info.waseda.ac.jp>
     * @return void
     * @param List
     *
     */
    public static void Dump(List listToBeDumped){
	Object[] tmp = listToBeDumped.toArray();

	System.out.print("Command: ");
	System.out.print(tmp[0].toString());

	System.out.print("\t");
	System.out.print("Arguments: ");

	for (int i = 1; i < tmp.length; i++){
	    System.out.print(tmp[i].toString());
	    System.out.print(", ");
	}
	System.out.println();
    }

    /**
     * デバッグ用表示メソッド。Instruction
     * オブジェクト内のListをObject[]に変換し、
     * それぞれの要素に対してtoString()を呼ぶ。
     * 
     * deprecated: by NAKAJIMA Motomu on 2003-10-25
     *
     * @author NAKAJIMA Motomu <nakajima@ueda.info.waseda.ac.jp>
     * @return void
     */
//     public void Dump(){
// 	Object[] tmp = data.toArray();

// 	for (int i = 0; i < tmp.length; i++){
// 	    System.out.print(tmp[i].toString());
// 	    System.out.print(" ");
// 	    System.out.println();
// 	}
//     }

    /**
     * デバッグ用表示メソッド。
     * 
     * Listの先頭はcommandと見なす。
     * commandの次は、引数のArrayListと見なす。
     * その次はまたcommandと見なす。
     *
     * @author NAKAJIMA Motomu <nakajima@ueda.info.waseda.ac.jp>
     * @return void
     */
    public void Dump(){
	Object[] hoge = data.toArray();
	Object[] fuga;
	
	for (int i = 0; i < hoge.length-1 ; i+=2){
	    System.out.print("Command: ");
	    System.out.print(hoge[0].toString());

	    System.out.print("\t");
	    System.out.print("Arguments: ");

	    fuga = hoge[i].toArray();
	    for (int j = 1; j < fuga.length; j++){
		System.out.print(fuga[j].toString());
		System.out.print(" ");
	    }
	    System.out.println();
	}
	System.out.println();
    }
}
