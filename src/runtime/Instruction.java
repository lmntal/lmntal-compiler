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
    //マッチングの実行

    //基本命令
    /** deref [dstatom ,srcatom, srcpos, dstpos] 
     * ＠return int
     * アトムsrcatomの第srcpos引数のリンク先が第dstpos引数に接続していることを確認したら、リンク先のアトムをdstatomに代入する。
     */
    public static final int DEREF = 0;

    /** getmem [dstmem, srcatom] */
    public static final int GETMEM = 1;

    /** getparent [dstmem, srcmem] */
    public static final int GETPARENT = 2;

    /** anymem [dstmem, srcmem] 
     * 膜srcmemの子膜に対して次々に、ノンブロッキングでのロック取得を試みる。（ロック取得に成功すれば、この膜はまだ参照を（＝ロックを）取得していなかった膜である） （この検査は方法２ではneq命令で行っていた）。そして、各子膜をdstmemに代入する。取得したロックは、後続の命令列がその膜に対して失敗したときに解放される。
     */
    public static final int ANYMEM = 3;

    /**    findatom [dstatom, srcmem, func]
     * 膜srcmemにあって名前funcを持つアトムを次々にdstatomに代入する。
     */
    public static final int FINDATOM = 4;

    /** func [srcatom, func]
     */
    public static final int FUNC = 5;

    /** norules [srcmem] 
     */
    public static final int NORULES = 6;

    /** natoms [?]
     */
    public static final int NATOMS = 8;

    /** nfreelinks [?]
     */
    public static final int NFREELINKS = 9;

    /** nmems [?]
     */
    public static final int NMEMS = 10;

    /** eq [mem1, mem2]
     */
    public static final int EQ = 11;

    /** neq [mem1, mem2]
     */
    public static final int NEQ = 12;


    /** LOCK [srcmem]
     * 膜srcmemに対するノンブロッキングでのロック取得を試みる。（ロック取得に成功すれば、この膜はまだ参照を（＝ロックを）取得していなかった膜である） （この検査は方法２ではneq命令で行っていた）。取得したロックは、後続の命令列がその膜に対して失敗したときに解放される。  */
    public static final int LOCK = 13;

    /** unlock [?]
     */
    public static final int UNLOCK = 14;

    // ボディの実行
    /** removeatom [srcatom]
     * アトムsrcatomを現在の膜から取り出す。
     */
    public static final int REMOVEATOM = 15;

    /** removemem [srcmem]
     * 膜srcmemを現在の膜から取り出す。
     */
    public static final int REMOVEMEM = 16;

    /** addproxy [?]
     */
    public static final int ADDPROXY = 17;

    /** removeproxy [?]
     */
    public static final int REMOVEPROXY = 18;

    /** hoge_star [?]
     */
    //public static final int HOGE_STAR = 19;

    /** newatom [dstatom, srcmem, func]
     * 膜srcmemに名前funcを持つ新しいアトム作成し、 dstatomに代入する。
     */
    public static final int NEWATOM = 20;

    /** newmem [dstmem, srcmem]
     * 膜srcmemに新しい子膜を作成し、 dstmemに代入する。
     */
    public static final int NEWMEM = 21;

    /** newlink [atom1, pos1, atom2, pos2]
     * アトムatom1の第pos1引数と、アトムatom2の第pos2引数を接続する。
     */
    public static final int NEWLINK = 22;

    /** relink [atom1, pos1, atom2, pos2]
     * アトムatom1の第pos1引数のリンク先の引数と、アトムatom2の第pos2引数を接続する。
     */
    public static final int RELINK = 23;

    /** unify [atom1, pos1, atom2, pos2]
     * アトムatom1の第pos1引数のリンク先の引数と、アトムatom2の第pos2引数のリンク先の引数を接続する。
     */
    public static final int UNIFY = 24;

    /** dequeueatom [srcatom]
     * アトムsrcatomを実行スタックから取り出す。
     */
    public static final int DEQUEUEATOM = 25;

    /* dequeuemem [srcmem]
     * 膜srcmemを再帰的に実行膜スタックから取り出す。
     */
    public static final int DEQUEUEMEM = 26;

    /** movemem [dstmem, srcmem]
     * 膜srcmemを膜dstmemに移動する。膜dstmemのchildatomsを更新する。
     */
    public static final int MOVEMEM = 27;
	      
    //拡張命令
    /** recursivelock [srcmem]
     * 膜srcmemの全ての子膜に対して再帰的にロックを取得する。ブロッキングで行う。
     */
    public static final int RECURSIVELOCK = 28;

    /** recursiveunlock [?]
     */
    public static final int RECURSIVEUNLOCK = 29;

    /** copy [dstmem, srcmem]
     * 再帰的にロックした膜srcmemの内容のコピーを作成し、膜dstmemに入れる。ただし自由リンク管理アトムの第1引数の状態は定義されない。
     */
    public static final int COPY = 30;

    /** not [instructions...]
     */
    public static final int NOT = 31;

    /** stop 
     */
    public static final int STOP = 32;

    /** react [ruleid, args...]
     * アトムsrcatomの名前がfuncであることを確認する。 
     */
    public static final int REACT = 33;

    /* control instructions */
    /** [inline, text]*/
    //    public static final int INLINE = 34;


    //引数無しだと初期容量は10(by api仕様書)
    public List data = new ArrayList();
	
    public Instruction() {

	//deprecated by NAKAJIMA: 古いデータ形式
	//by HARA
	// たとえば [react, [1, 2, 5]]
	// 		ArrayList sl = new ArrayList();
	// 		sl.add(new Integer(1));
	// 		sl.add(new Integer(2));
	// 		sl.add(new Integer(5));
	// 		data.add("react");
	// 		data.add(sl);
	// 		System.out.println(data);

	//新しいデータ形式
	ArrayList sl = new ArrayList();
	sl.add(new Integer(1));
	sl.add(new Integer(2));
	sl.add(new Integer(5));
	data.add(new Integer(0)); // 0->deref命令
	data.add(sl);
	System.out.println(data);
    }


    /**
     * デバッグ用表示メソッド。
     * 命令の文字列(String)を与えると、該当する命令のintを返してくれる
     *
     * @author NAKAJIMA Motomu <nakajima@ueda.info.waseda.ac.jp>
     * @return int
     * 
     */
    public static int getInstructionInteger(String instructionString){
	Hashtable table = new Hashtable();
	int answer = -1;
	Object tmp;

	table.put("DEREF", new Integer(DEREF));
	table.put("GETMEM", new Integer(GETMEM));
	table.put("GETPARENT", new Integer(GETPARENT));
	table.put("ANYMEM", new Integer(ANYMEM));
	table.put("FINDATOM", new Integer(FINDATOM));
	table.put("FUNC", new Integer(FUNC));
	table.put("NORULES", new Integer(NORULES));
	table.put("NATOMS", new Integer(NATOMS));
	table.put("NFREELINKS", new Integer(NFREELINKS));
	table.put("EQ", new Integer(EQ));
	table.put("NEQ", new Integer(NEQ));
	table.put("LOCK", new Integer(LOCK));
	table.put("UNLOCK", new Integer(UNLOCK));
	table.put("REMOVEATOM", new Integer(REMOVEATOM));
	table.put("REMOVEMEM", new Integer(REMOVEMEM));
	table.put("ADDPROXY", new Integer(ADDPROXY));
	table.put("REMOVEPROXY", new Integer(REMOVEPROXY));
	table.put("NEWATOM", new Integer(NEWATOM));
	table.put("NEWMEM", new Integer(NEWMEM));
	table.put("NEWLINK", new Integer(NEWLINK));
	table.put("RELINK", new Integer(RELINK));
	table.put("UNIFY", new Integer(UNIFY));
	table.put("DEQUEUEATOM", new Integer(DEQUEUEATOM));
	table.put("DEQUEUEMEM", new Integer(DEQUEUEMEM));
	table.put("MOVEMEM", new Integer(MOVEMEM));
	table.put("RECURSIVELOCK", new Integer(RECURSIVELOCK));
	table.put("COPY", new Integer(COPY));
	table.put("NOT", new Integer(NOT));
	table.put("STOP", new Integer(STOP));
	table.put("REACT", new Integer(REACT));

	try {
	    answer = ((Integer)table.get(instructionString.toUpperCase())).intValue();
	} catch (NullPointerException e){
	    System.out.println(e);
	    System.exit(1);
	}

	return answer;
    }


    /**
     * デバッグ用表示メソッド。
     * 命令の数字(int)を与えると、該当する命令のStringを返してくれる。
     *
     * @author NAKAJIMA Motomu <nakajima@ueda.info.waseda.ac.jp>
     * @return String
     * 
     */
    public static String getInstructionString(int instrcutionNum){
	String answer = "";

	int maxNum = REACT+1; //命令の種類の数
	String[] hoge = new String[maxNum];
	hoge[DEREF] = new String("DEREF");
	hoge[GETMEM] = new String("GETMEM");
	hoge[GETPARENT] = new String("GETPARENT");
	hoge[ANYMEM] = new String("ANYMEM");
	hoge[FINDATOM] = new String("FINDATOM");
	hoge[FUNC] = new String("FUNC");
	hoge[NORULES] = new String("NORULES");
	hoge[NATOMS] = new String("NATOMS");
	hoge[NFREELINKS] = new String("NFREELINKS");
	hoge[EQ] = new String("EQ");
	hoge[NEQ] = new String("NEQ");
	hoge[LOCK] = new String("LOCK");
	hoge[UNLOCK] = new String("UNLOCK");
	hoge[REMOVEATOM] = new String("REMOVEATOM");
	hoge[REMOVEMEM] = new String("REMOVEMEM");
	hoge[ADDPROXY] = new String("ADDPROXY");
	hoge[REMOVEPROXY] = new String("REMOVEPROXY");
	hoge[NEWATOM] = new String("NEWATOM");
	hoge[NEWMEM] = new String("NEWMEM");
	hoge[NEWLINK] = new String("NEWLINK");
	hoge[RELINK] = new String("RELINK");
	hoge[UNIFY] = new String("UNIFY");
	hoge[DEQUEUEATOM] = new String("DEQUEUEATOM");
	hoge[DEQUEUEMEM] = new String("DEQUEUEMEM");
	hoge[MOVEMEM] = new String("MOVEMEM");
	hoge[RECURSIVELOCK] = new String("RECURSIVELOCK");
	hoge[COPY] = new String("COPY");
	hoge[NOT] = new String("NOT");
	hoge[STOP] = new String("STOP");
	hoge[REACT] = new String("REACT");

	try {
	    answer = hoge[instrcutionNum];
	} catch (ArrayIndexOutOfBoundsException e){
	    //	     answer = "　 ∧＿∧ \n　（　´∀｀）＜　ぬるぽ \n\n";

	    answer = "\n1 名前：仕様書無しさん 03/09/21 00:23\n　 ∧＿∧ \n　（　´∀｀）＜　ぬるぽ \n\n2 名前：仕様書無しさん ：03/09/21 00:24\n　　Λ＿Λ　　＼＼ \n　 （　・∀・）　　　|　|　ｶﾞｯ\n　と　　　　）　 　 |　| \n　　 Ｙ　/ノ　　　 人 \n　　　 /　）　 　 < 　>__Λ∩ \n　 ＿/し'　／／. Ｖ｀Д´）/\n　（＿フ彡　　　　　 　　/ \n\n";
	} catch (Exception e){
	    //本当にヤヴァイ場合
	    System.out.println(e);
	    System.exit(1);
	}

	return answer;
    }

    /**
     * デバッグ用表示メソッド。
     *
     * @author NAKAJIMA Motomu <nakajima@ueda.info.waseda.ac.jp>
     * @return String
     *
     * メモ：Instructionの中は、Listの中にArrayListが入れ子になって入っている。
     * つまり、【int型のname】命令 [引数1, 引数2, … , 引数n] 
     * ※一つのInstructionインスタンスには、1つしか命令がない
     * ただし命令はInteger型、引数はObject。
     *
     * メモ：出力の時、モードが−の変数をインデントする。
     *
     */
    public String toString(){


	StringBuffer buffer = new StringBuffer("");

	if(data.isEmpty()){
	    buffer.append(" No Instructions! ");
	} else {
	    for (int i = 0; i < data.size()-1; i+=2){
		buffer.append("[");

		buffer.append("Command: ");
		buffer.append(this.getInstructionString(((Integer)data.get(i)).intValue()));
		buffer.append(" Arguments: ");
		buffer.append(data.get(i+1));
		
		buffer.append("]");
	    }
	}

	return buffer.toString();
    }

    /**
     *
     * Deprecated: 2003-10-28 データ型が変更になったため
     *
     * デバッグ用表示メソッド。
     *
     * @author NAKAJIMA Motomu <nakajima@ueda.info.waseda.ac.jp>
     * @return String
     *
     * メモ：Instructionの中は、Listの中にArrayListが入れ子になって入っている。
     * つまり、[命令, [引数], 命令, [引数], …]
     *
     */
    //     public String toString(){
    // 	//手抜きな方法
    // 	//	return "not implemented\n";

    // 	//手抜きな方法その2
    // 	/*	Object hoge;
    // 	 *	hoge = (Object)data;
    // 	 *	return hoge.toString();     
    // 	*/

    // 	//まともな方法
    // 	//[命令, [引数], 命令, [引数], …]を全てStringに変換
    // 	Object[] hoge; 
    // 	Object[] fuga;
    // 	StringBuffer buffer = new StringBuffer();

    // 	try {
    // 	    hoge = data.toArray();

    // 	    buffer.append("[ ");

    // 	    //命令のためのループ
    // 	    for (int i = 0; i < hoge.length-1; i+=2) {
    // 		buffer.append(hoge[i]);
    // 		buffer.append(", ");
		
    // 		buffer.append("[");

    // 		//引数のためのループ
    // 		fuga = hoge[i+1].toArray();
    // 		for (int j = 0; j < fuga.length; j++) {
    // 		    buffer.append(fuga[j]);
    // 		}
    // 		buffer.append("]");
    // 	    }

    // 	    buffer.append(" ]");

    // 	} catch (Exception e){
    // 	    //想定される場合：
    // 	    //ArrayList dataが空→命令が入ってない
    // 	    //ArrayList data[i]が空→引数無し
    // 	    //未知のバグ→とりあえずexceptionをprint

    // 	    //それ以外→なんかある？

    // 	    //例：ArrayStoreException - a の実行時の型がリスト内の
    // 	    //    各要素の実行時の型のスーパーセットでない場合
    // 	    //    (by API仕様書のArrayListクラスtoArrayメソッドの解説)

    // 	    System.out.println(e);

    // 	    return "General Protection Fault\n\n";
    // 	}

    // 	return (buffer.toString());
    //     }




    /**
     * デバッグ用表示メソッド。与えられたListをObject[]に変換し、それぞれの要素に対してtoString()を呼んでstdoutに垂れ流す。
     *
     * @author NAKAJIMA Motomu <nakajima@ueda.info.waseda.ac.jp>
     * @return void
     * @param List
     *
     * NAKJAIMA:2003-10-26:いらないっかなー
     *
     */
    //     public static void Dump(List listToBeDumped){
    // 	Object[] hoge = listToBeDumped.toArray();
    // 	Object[] fuga;
	
    // 	for (int i = 0; i < hoge.length-1 ; i+=2){
    // 	    System.out.print("Command: ");
    // 	    System.out.print(hoge[i].toString());

    // 	    System.out.print("\t");
    // 	    System.out.print("Arguments: ");

    // 	    fuga = hoge[i+1].toArray();
    // 	    for (int j = 0; j < fuga.length; j++){
    // 		System.out.print(fuga[j].toString());
    // 		System.out.print(" ");
    // 	    }
    // 	    System.out.println();
    // 	}
    // 	System.out.println();
    //     }

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
     * [命令, [引数], 命令, [引数], …]を全てprintする。
     * 
     * Listの先頭はcommandと見なす。
     * commandの次は、引数のArrayListと見なす。
     * その次はまたcommandと見なす。
     *
     * @author NAKAJIMA Motomu <nakajima@ueda.info.waseda.ac.jp>
     * @return void
     */
    //     public void Dump(){
    // 	//[命令, [引数], 命令, [引数], …]を全てprintする
    // 	Object[] hoge; 
    // 	Object[] fuga;

    // 	try {
    // 	    hoge = data.toArray();

    // 	    System.out.print("[ ");

    // 	    //命令のためのループ
    // 	    for (int i = 0; i < hoge.length-1; i+=2) {
    // 		System.out.print("Command: ");
    // 		System.out.print(hoge[i]);

    // 		System.out.print("\t");
    // 		System.out.print("Arguments: ");

    // 		//引数のためのループ
    // 		fuga = hoge[i+1].toArray();
    // 		for (int j = 0; j < fuga.length; j++) {
    // 		    System.out.print(fuga[j]);
    // 		    System.out.print(" ");
    // 		}
    // 	    }

    // 	    System.out.println(" ]");

    // 	} catch (Exception e){
    // 	    //想定される場合：
    // 	    //ArrayList dataが空→命令が入ってない
    // 	    //ArrayList data[i]が空→引数無し
    // 	    //未知のバグ→とりあえずexceptionをprint

    // 	    //それ以外→なんかある？

    // 	    //例：ArrayStoreException - a の実行時の型がリスト内の
    // 	    //    各要素の実行時の型のスーパーセットでない場合
    // 	    //    (by API仕様書のArrayListクラスtoArrayメソッドの解説)

    // 	    System.out.println(e);
    // 	}
    //     }
}
