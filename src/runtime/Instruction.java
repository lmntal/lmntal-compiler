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
     * つまり、[命令, [引数], 命令, [引数], …]
     *
     */
    public String toString(){
	//手抜きな方法
	//	return "not implemented\n";

	//手抜きな方法その2
	/*	Object hoge;
	 *	hoge = (Object)data;
	*/	return hoge.toString();

	//まともな方法
	//[命令, [引数], 命令, [引数], …]を全てStringに変換
	Object[] hoge; 
	Object[] fuga;
	StringBuffer buffer = new StringBuffer();

	try {
	    hoge = data.toArray();

	    buffer.append("[ ");

	    //命令のためのループ
	    for (int i = 0; i < hoge.length-1; i+=2) {
		buffer.appnend(hoge[i]);
		buffer.append(", ");
		
		buffer.append("[");

		//引数のためのループ
		fuga = hoge[i+1].toArray();
		for (int j = 0; j < fuga.length; j++) {
		    buffer.append(fuga[j]);
		}
		buffer.append("]");
	    }

	    buffer.append(" ]");

	} catch (Exception e){
	    //想定される場合：
	    //ArrayList dataが空→命令が入ってない
	    //ArrayList data[i]が空→引数無し
	    //未知のバグ→とりあえずexceptionをprint

	    //それ以外→なんかある？

	    System.out.println(e);

	    return "General Protection Fault\n\n";
	}

	return (buffer.toString());
    }


    /**
     * デバッグ用表示メソッド。与えられたListをObject[]に変換し、それぞれの要素に対してtoString()を呼んでstdoutに垂れ流す。
     *
     * @author NAKAJIMA Motomu <nakajima@ueda.info.waseda.ac.jp>
     * @return void
     * @param List
     *
     */
    public static void Dump(List listToBeDumped){
	Object[] hoge = listToBeDumped.toArray();
	Object[] fuga;
	
	for (int i = 0; i < hoge.length-1 ; i+=2){
	    System.out.print("Command: ");
	    System.out.print(hoge[i].toString());

	    System.out.print("\t");
	    System.out.print("Arguments: ");

	    fuga = hoge[i+1].toArray();
	    for (int j = 0; j < fuga.length; j++){
		System.out.print(fuga[j].toString());
		System.out.print(" ");
	    }
	    System.out.println();
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
