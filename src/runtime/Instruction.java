/*
 * 作成日: 2003/10/21
 *
 */
package runtime;

import java.util.*;
import java.lang.reflect.Modifier;
import java.lang.reflect.Field;

/*
 * TODO memof は廃止する方向で検討する。
 * TODO 右辺の全ての膜を生成してから、活性化を行うようにする。そのためにactivatemem命令を呼ぶ。
 */

/**
 * 1 つの命令を保持する。通常は、InstructionのArrayListとして保持する？
 * 
 * デバッグ用表示メソッドを備える。
 *
 * @author hara, nakajima, n-kato
 */
public class Instruction {
	
    /**
     * どの命令なのかを保持する
     */
    private int id;

    /** 未定義の命令 */	
    public static final int UNDEF = 0;
	
    // 出力する基本ガード命令 (1--9)
    
    /** deref [-dstatom, srcatom, srcpos, dstpos]
     * <br><strong><font color="#ff0000">ガード命令</font></strong><br>
     * アトムsrcatomの第srcpos引数のリンク先が第dstpos引数に接続していることを確認したら、
     * リンク先のアトムへの参照をdstatomに代入する。
     */
    public static final int DEREF = 1;

    /** lockmem [-dstmem, srcfreelinkatom]
     * <br>ロック取得するガード命令<br>
     * 自由リンク出力管理アトムfreelinkatomが所属する膜に対して、
     * ノンブロッキングでのロックを取得を試みる。
     * そしてロック取得に成功したこの膜への参照をdstmemに代入する。
     * 取得したロックは、後続の命令列がその膜に対して失敗したときに解放される。
     * <p>
     * ロック取得に成功すれば、この膜はまだ参照を（＝ロックを）取得していなかった膜である
     * （この検査はRuby版ではnmemeq命令で行っていた）。
     * <p>膜の外からのリンクで初めて特定された膜への参照を取得するために使用される。
     * @see testmem
     * @see getmem */
    public static final int LOCKMEM = 2;

    /** anymem [-dstmem, srcmem] 
     * <br>反復するロック取得するガード命令<br>
     * 膜srcmemの子膜のうちまだロックを取得していない膜に対して次々に、
     * ノンブロッキングでのロック取得を試みる。
     * そして、ロック取得に成功した各子膜への参照をdstmemに代入する。
     * 取得したロックは、後続の命令列がその膜に対して失敗したときに解放される。
     * <p><b>注意</b>　ロック取得に失敗した場合と、その膜が存在していなかった場合とは区別できない。*/
    public static final int ANYMEM = 3;

    /** findatom [-dstatom, srcmem, func]
     * <br>反復するガード命令<br>
     * 膜srcmemにあってファンクタfuncを持つアトムへの参照を次々にdstatomに代入する。*/
    public static final int FINDATOM = 4;

    // 出力しない基本ガード命令 (10--24)

    /** testmem [dstmem, srcfreelinkatom]
     * <br>ガード命令<br>
     * 自由リンク出力管理アトムfreelinkatomが（ロックされた）膜dstmemに所属することを確認する。
     * <p><b>注意</b>　Ruby版ではgetmemで参照を取得した後でeqmemを行っていた。
     * @see lockmem */
    public static final int TESTMEM = 10;

    /** func [srcatom, func]
     * <br>ガード命令<br>
     * アトムsrcatomがファンクタfuncを持つことを確認する。*/
    public static final int FUNC = 11;

    /** norules [srcmem] 
     * <br>ガード命令<br>
     * 膜srcmemにルールが存在しないことを確認する。*/
    public static final int NORULES = 12;

    /** natoms [srcmem, count]
     * <br>ガード命令<br>
     * 膜srcmemの自由リンク管理アトム以外のアトム数がcountであることを確認する。*/
    public static final int NATOMS = 13;

    /** nfreelinks [srcmem, count]
     * <br>ガード命令<br>
     * 膜srcmemの自由リンク数がcountであることを確認する。*/
    public static final int NFREELINKS = 14;

    /** nmems [srcmem, count]
     * <br>ガード命令<br>
     * 膜srcmemの子膜の数がcountであることを確認する。*/
    public static final int NMEMS = 15;

    /** eqatom [atom1, atom2]
     * <br>ガード命令<br>
     * atom1とatom2が同一のアトムを参照していることを確認する。
     * <p><b>注意</b> Ruby版のeqから分離 */
    public static final int EQATOM = 16;

    /** eqmem [mem1, mem2]
     * <br>ガード命令<br>
     * mem1とmem2が同一の膜を参照していることを確認する。
     * <p><b>注意</b> Ruby版のeqから分離 */
    public static final int EQMEM = 17;

    /** neqatom [atom1, atom2]
     * <br>ガード命令<br>
     * atom1とatom2が異なるアトムを参照していることを確認する。
     * <p><b>注意</b> Ruby版のneqから分離 */
    public static final int NEQATOM = 18;
	
    /** neqmem [mem1, mem2]
     * <br>ガード命令<br>
     * mem1とmem2が異なる膜を参照していることを確認する。
     * <p><b>注意</b> Ruby版のneqから分離
     * <P>TODO この命令は不要かも知れない */
    public static final int NEQMEM = 19;

    //    /** lock [srcmem]
    //     * <br>（廃止された）ガード命令<br>
    //     * 膜srcmemに対するノンブロッキングでのロック取得を試みる。
    //     * ロック取得に成功すれば、この膜はまだ参照を（＝ロックを）取得していなかった膜である
    //     * <p>srcmemにmemof記法が廃止されたため、ロックはlockmemで行う。したがってlockは廃止された。*/
    //    public static final int LOCK = 20;

    // 出力する基本ボディ命令 (25--29)

    /** getmem [-dstmem, srcatom]
     * <br>ボディ命令<br>
     * アトムsrcatomの所属膜への参照をdstmemに代入する。
     * <p><b>注意</b>　ガード命令としては廃止された。
     * @see lockmem */
    public static final int GETMEM = 25;

    /** getparent [-dstmem, srcmem]
     * <br>ボディ命令<br>
     * 膜srcmemの親膜への参照をdstmemに代入する。
     * <p><b>注意</b>　ガード命令としては廃止された。*/
    public static final int GETPARENT = 26;

    // 出力しない基本ボディ命令 (30--44)    

    /** removeatom [srcatom]
     * <br>ボディ命令<br>
     * アトムsrcatomを現在の膜から取り出す。
     * <p><strike>実行スタックに入っていれば実行スタックから除去される。</strike>*/
    public static final int REMOVEATOM = 30;

    /** removemem [srcmem]
     * <br>ボディ命令<br>
     * 膜srcmemを現在の膜から取り出す。*/
    public static final int REMOVEMEM = 31;

    /** newatom [-dstatom, srcmem, func]
     * <br>ボディ命令<br>
     * 膜srcmemにファンクタfuncを持つ新しいアトム作成し、参照をdstatomに代入する。
     * <p>アクティブアトムならば膜srcmemの実行スタックに積む。*/
    public static final int NEWATOM = 32;

    /** newmem [-dstmem, srcmem]
     * <br>ボディ命令<br>
     * 膜srcmemに新しい子膜を作成し、dstmemに代入する。*/
    public static final int NEWMEM = 33;

    /** newfreelink [-dstfreelinkatom, srcmem]
     * <br>（予約された）ボディ命令<br>
     * 膜srcmemに新しい自由リンク出力管理アトムを作成し、参照をdstfreelinkatomに代入する。
     * <p>この命令は廃案になった。*/
    public static final int NEWFREELINK = 34;

    /** newroot [-dstmem, srcmem, node]
     * <br>（予約された）ボディ命令<br>
     * 膜srcmemの子膜に計算ノードnodeで実行される新しいルート膜を作成し、参照をdstmemに代入する。*/
    public static final int NEWROOT = 35;

    /** enqueueatom [srcatom]
     * <br>ボディ命令<br>
     * アトムsrcatomを所属膜の実行スタックに入れる。*/
    public static final int ENQUEUEATOM = 36;

    /** dequeueatom [srcatom]
     * <br>（予約された）ボディ命令<br>
     * アトムsrcatomを実行スタックから取り出す。*/
    public static final int DEQUEUEATOM = 36;

    /** dequeuemem [srcmem]
     * <br>（予約された）ボディ命令<br>
     * 膜srcmemを再帰的に実行膜スタックから取り出す。*/
    public static final int DEQUEUEMEM = 37;

    /** activatemem [srcmem]
     * <br>ボディ命令<br>
     * 膜srcmemを管理するマシンの実行膜スタックに膜srcmemを積む。
     * <p>実行後、srcmemへの参照は廃棄しなければならない。
     * @see activatemem */
    public static final int ACTIVATEMEM = 38;

    // ルールを操作するボディ命令 (45--49)
	
    /** loadruleset [dstmem, ruleset]
     * <br>ボディ命令<br>
     * rulesetが参照するルールセットを膜dstmemにコピーする。
     * TODO 本来であればこの膜のアトムをエンキューし直さなければならないのを何とかする。*/
    public static final int LOADRULESET = 45;

    /** copyrules [dstmem, srcmem]
     * <br>ボディ命令<br>
     * 膜srcmemにある全てのルールを膜dstmemにコピーする。
     * <p><b>注意</b>　Ruby版のinheritrulesから名称変更しました。*/
    public static final int COPYRULES = 46;

    /** clearrules [dstmem]
     * <br>ボディ命令<br>
     * 膜dstmemにある全てのルールを消去する。*/
    public static final int CLEARRULES = 47;

    // リンクを操作するボディ命令 (50--55)
	
    /** newlink [atom1, pos1, atom2, pos2]
     * <br>ボディ命令<br>
     * アトムatom1の第pos1引数と、アトムatom2の第pos2引数の間に両方向リンクを張る。
     * <p>典型的には、atom1とatom2はいずれもルールボディに存在する。
     * <p><b>注意</b>　Ruby版の片方向から仕様変更された*/
    public static final int NEWLINK = 50;

    /** relink [atom1, pos1, atom2, pos2]
     * <br>ボディ命令<br>
     * アトムatom1の第pos1引数と、アトムatom2の第pos2引数のリンク先を接続する。
     * <p>典型的には、atom1はルールボディに、atom2はルールヘッドに存在する。
     * <p>実行後、atom2[pos2]の内容は無効になる。*/
    public static final int RELINK = 51;

    /** unify [atom1, pos1, atom2, pos2]
     * <br>ボディ命令<br>
     * アトムatom1の第pos1引数のリンク先の引数と、アトムatom2の第pos2引数のリンク先の引数を接続する。
     * <p>典型的には、atom1とatom2はいずれもルールヘッドに存在する。*/
    public static final int UNIFY = 52;

    /** getlink [-link2, atom2, pos2]
     * <br>出力する最適化用ボディ命令<br>
     * アトムatom2の第pos2引数に格納されたリンクオブジェクトへの参照をlink2に代入する。
     * <p>典型的には、atom2はルールヘッドに存在する。
     * inheritlinkと組み合わせて使用してrelinkの代用にする。*/
    public static final int GETLINK = 53;

    /** inheritlink [atom1, pos1, link2]
     * <br>最適化用ボディ命令<br>
     * atom1の第pos1引数と、リンクlink2のリンク先を接続する。
     * アトムatom1の第pos1引数と、リンクlink2のリンク先を接続する。
     * <p>典型的には、atom1はルールボディに存在し、link2はルールヘッドに存在する。
     * <p>link2は再利用されるため、実行後はlink2を使用してはならない。
     * @see getlink */
    public static final int INHERITLINK = 54;

    // 予約 (55-59)
	
    // 膜の移動および自由リンク管理アトムを自動処理するためのボディ命令 (60--69)
	
    /** removeproxies [srcmem]
     * <br>ボディ命令<br>
     * 何もしない。
     * TODO removememから処理「srcmemを通る無関係な自由リンク管理アトムを自動削除する」を分離する。*/
    public static final int REMOVEPROXIES = 60;

    /** removetoplevelproxies [srcmem]
     * <br>ボディ命令<br>
     * 膜srcmem（本膜）を通過している無関係な自由リンク管理アトムを除去する。*/
    public static final int REMOVETOPLEVELPROXIES = 61;

    /** insertproxies [parentmem,childmem]
     * <br>ボディ命令<br>
     * 指定された膜間に自由リンク管理アトムを自動挿入する。*/
    public static final int INSERTPROXIES = 62;
	
    /** removetemporaryproxies [srcmem]
     * <br>ボディ命令<br>
     * 膜srcmem（本膜）に残された"star"アトムを除去する。*/
    public static final int REMOVETEMPORARYPROXIES = 63;

    /** movecells [dstmem, srcmem]
     * <br>ボディ命令<br>
     * 膜srcmemにある全てのアトムと膜を膜dstmemに移動する。
     * <p>実行後、膜srcmemはこのまま廃棄されなければならない。
     * <p>膜srcmemにあったアトムは実行スタックにエンキューされる。
     * <p><b>注意</b>　Ruby版のpourから名称変更 */
    public static final int MOVECELLS = 64;

    /** movemem [dstmem, srcmem]
     * <br>ボディ命令<br>
     * 膜srcmemを膜dstmemに移動する。*/
    public static final int MOVEMEM = 65;

    /** unlockmem [srcmem]
     * <br>ボディ命令<br>
     * 膜srcmemのロックを解放する。
     * <p>この膜を管理するマシンの実行膜スタックに積まれる。
     * <p>実行後、srcmemへの参照は廃棄しなければならない。
     * TODO activatemem に併合する？ */
    public static final int UNLOCKMEM = 66;

    // プロセス文脈をコピーまたは廃棄するための命令 (70--74)
    
    /** recursivelock [srcmem]
     * <br>（予約された）失敗しない（？）ガード命令<br>
     * 膜srcmemの全ての子膜に対して再帰的にロックを取得する。ブロッキングで行う。*/
    public static final int RECURSIVELOCK = 70;

    /** recursiveunlock [srcmem]
     * <br>（予約された）ボディ命令<br>
     * 膜srcmemの全ての子膜に対して再帰的にロックを解放する。*/
    public static final int RECURSIVEUNLOCK = 71;

    /** copymem [dstmem, srcmem]
     * <br>（予約された）ボディ命令<br>
     * 再帰的にロックされた膜srcmemの内容のコピーを作成し、膜dstmemに入れる。
     * ただし自由リンク管理アトムの第1引数の状態は定義されない。*/
    public static final int COPYMEM = 72;

    /** dropmem [srcmem]
     * <br>（予約された）ボディ命令<br>
     * 再帰的にロックされた膜srcmemを破棄する。
     * この膜や子孫の膜をルート膜とするマシンは強制終了する。*/
    public static final int DROPMEM = 73;

    // 予約 (75--79)
	
    // 制御命令 (80--99)
	
    /** react [ruleid, [atomargs...], [memargs...]]
     * <br>失敗しないガード命令<br>
     * ruleidが参照するルールに対するマッチが成功したことを表す。*/
    public static final int REACT = 80;

    /** not [[instructions...]]
     * <br>（予約された）ガード命令<br>
     * 否定条件を指定する。*/
    public static final int NOT = 81;

    /** stop 
     * <br>（予約された）失敗しないガード命令<br>
     * 否定条件にマッチしたことを表す。
     */
    public static final int STOP = 82;

    /** inline [[args...] text]
     * <br>（予約された）ボディ命令
     * 文字列textで指定されたJavaコードをエミットする。
     * コード中の%0は本膜への参照で置換される。
     * コード中の%1から%nは詳細が未定の引数argsの各要素が表すリンクへの参照で置換される。
     * <p>Javaソースを出力しない処理系環境では例外を発生する。*/
    public static final int INLINE = 83;

    /** builtin [...]
     * <br>（予約された）ボディ命令
     * 指定された引数でJavaのクラスメソッドを呼び出す。詳細は未定。*/
    public static final int BUILTIN = 84;
	
    /** spec [formals, locals]
     * <br>無視される<br>
     * 仮引数と一時変数の個数を宣言する。*/
    public static final int SPEC = 85;

    /** loop [[instructions...]]
     * <br>構造化命令<br>
     * 引数の命令列を実行することを表す。
     * 引数列を最後まで実行した場合、このloop命令の実行を繰り返す。*/
    public static final int LOOP = 91;

    /** branch [[instructions...]]
     * <br>構造化命令<br>
     * 引数の命令列を実行することを表す。
     * 引数実行中に失敗した場合、引数実行中に取得したロックを解放し、
     * 典型的にはこのbranchの次の命令に進む。
     * 引数列を最後まで実行した場合、ここで終了する（TODO またはhalt命令を作る）*/
    public static final int BRANCH = 92;

    /** 命令の数。新命令はこれより小さな数字にすること。*/
    private static final int END_OF_INSTRUCTION = 100;
	
    ////////////////////////////////////////////////////////////////

    /**
     * 引数を追加するマクロ。
     * @param o オブジェクト型の引数
     */
    private final void add(Object o) { data.add(o); }
	
    /**
     * 引数を追加するマクロ。
     * @param n int 型の引数
     */
    private final void add(int n) { data.add(new Integer(n)); }
	
    /**
     * ダミー命令を生成する。
     * さしあたって生成メソッドがまだできてない命令はこれを使う
     * @param s 説明用の文字列
     */
    public static Instruction dummy(String s) {
	Instruction i = new Instruction(-1);
	i.add(s);
	return i;
    }
	
    /**
     * react 命令を生成する。
     * 
     * @param r 反応できるルールオブジェクト
     * @param actual 引数
     * @return
     */
    public static Instruction react(Rule r, List actual) {
	Instruction i = new Instruction(REACT);
	i.add(r);
	i.add(actual);
	return i;
    }
	
    /** findatom 命令を生成する */
    public static Instruction findatom(int dstatom, List srcmem, Functor func) {
	Instruction i = new Instruction(FINDATOM);
	i.add(dstatom);
	i.add(srcmem);
	i.add(func);
	return i;
    }
	
    /** anymem 命令を生成する */
    public static Instruction anymem(int dstmem, int srcmem) {
	Instruction i = new Instruction(ANYMEM);
	i.add(dstmem);
	i.add(srcmem);
	return i;
    }
	
	
    /**
     * newatom 命令を生成する。
     * 
     * @param dstatom
     * @param srcmem
     * @param func
     * @return
     */
    public static Instruction newatom(int dstatom, int srcmem, Functor func) {
	Instruction i = new Instruction(NEWATOM);
	i.add(dstatom);
	i.add(srcmem);
	i.add(func);
	return i;
    }
	
    /** spec 命令を生成する */
    public static Instruction spec(int formals, int locals) {
	Instruction i = new Instruction(SPEC);
	i.add(formals);
	i.add(locals);
	return i;
    }
	
    /** newmem 命令を生成する */
    public static Instruction newmem(int ret, int srcmem) {
	return new Instruction(NEWMEM,ret,srcmem);
    }
    /** newlink 命令を生成する */
    public static Instruction newlink(int atom1, int pos1, int atom2, int pos2) {
	return new Instruction(NEWLINK,atom1,pos1,atom2,pos2);
    }
    /** loadruleset 命令を生成する */
    public static Instruction loadruleset(int mem, Ruleset rs) {
	Instruction i = new Instruction(LOADRULESET);
	i.add(mem);
	i.add(rs);
	return i;
    }
	
    /**
     * getmem 命令を生成する。
     * @param ret
     * @param atom
     * @return
     */
    public static Instruction getmem(int ret, int atom) {
	Instruction i = new Instruction(GETMEM);
	i.add(ret);
	i.add(atom);
	return i;
    }
	
    /**
     * removeatom 命令を生成する。
     * @param atom
     * @param func
     * @return
     */
    public static Instruction removeatom(int atom, Functor func) {
	Instruction i = new Instruction(REMOVEATOM);
	i.add(atom);
	i.add(func);
	return i;
    }
	
	
	
    /**
     * 命令の引数を保持する。
     * 命令によって引数の型が決まっている。
     */
    public List data = new ArrayList();
	
    /**
     * 無名命令を作る。
     *
     */
    public Instruction() {
    }
	
    /**
     * 指定された命令をつくる
     * @param id
     */
    public Instruction(int id) {
    	this.id = id;

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
	/*
	  ArrayList sl = new ArrayList();
	  sl.add(new Integer(1));
	  sl.add(new Integer(2));
	  sl.add(new Integer(5));
	  data.add(new Integer(0)); // 0->deref命令
	  data.add(sl);
	  System.out.println(data);
	*/
    }
    private Instruction(int id, int arg1) {
	this.id = id;
	add(arg1);
    }
    private Instruction(int id, int arg1, int arg2) {
	this.id = id;
	add(arg1);
	add(arg2);
    }
    private Instruction(int id, int arg1, int arg2, int arg3) {
	this.id = id;
	add(arg1);
	add(arg2);
	add(arg2);
    }
    private Instruction(int id, int arg1, int arg2, int arg3, int arg4) {
	this.id = id;
	add(arg1);
	add(arg2);
	add(arg3);
	add(arg4);
    }


    /**
     * デバッグ用表示メソッド。
     * 命令の文字列(String)を与えると、該当する命令のintを返してくれる
     *
     * @author NAKAJIMA Motomu <nakajima@ueda.info.waseda.ac.jp>
     * @return int
     * 
     */
//     public static int getInstructionInteger(String instructionString){
// 	int answer = -1;
// 	Object tmp;

// 	try {
// 	    answer = ((Integer)table.get(instructionString.toUpperCase())).intValue();
// 	} catch (NullPointerException e){
// 	    System.out.println(e);
// 	    System.exit(1);
// 	}
// 	return answer;
//     }

    static String[] hoge = new String[END_OF_INSTRUCTION]; // 命令の種類の数	
		
    {
	hoge[DEREF] = "deref";
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

	Hashtable table = new Hashtable();

	try {
	    Instruction hoge = new Instruction();
	    Field[] fields = Class.forName("Instruction").getDeclaredFields();
	    for (int i = 0; i < fields.length; i++) {
		Field f = fields[i];
		if (f.getType().getName().equals("int") && Modifier.isStatic(f.getModifiers())) {
		    table.put(new Integer(f.getInt(hoge)), f.getName().toLowerCase());
		}
	    }			
	}
	catch(java.lang.SecurityException e)
	    {
		
	    }

	answer = (String)table.get(new Integer(instrcutionNum));

	return answer;

	/* 
	   try {
	   answer = hoge[instrcutionNum];
	   } catch (ArrayIndexOutOfBoundsException e){
	   //	     answer = "　 ∧＿∧ \n　（　´∀｀）＜　ぬるぽ \n\n";
	   answer = "\n1 名前：仕様書無しさん 03/09/21 00:23\n　 ∧＿∧ \n　（　´∀｀）＜　ぬるぽ \n\n2 名前：仕様書無しさん ：03/09/21 00:24\n　　Λ＿Λ　　＼＼ \n　 （　・∀・）　　　|　|　ｶﾞｯ\n　と　　　　）　 　 |　| \n　　 Ｙ　/ノ　　　 人 \n　　　 /　）　 　 < 　>__Λ∩ \n　 ＿/し'　／／. Ｖ｀Д´）/\n　（＿フ彡　　　　　 　　/ \n\n";
	   answer = "dummy";
	   } catch (Exception e){
	   //本当にヤヴァイ場合
	   System.out.println(e);
	   System.exit(1);
	   }
	   return answer;
	*/
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
    public String toString() {
	return getInstructionString(id)+" "+data.toString();

	//	StringBuffer buffer = new StringBuffer("");
	//
	//	if(data.isEmpty()){
	//	    buffer.append(" No Instructions! ");
	//	} else {
	//	    for (int i = 0; i < data.size()-1; i+=2){
	//		buffer.append("[");
	//
	//		buffer.append("Command: ");
	//		buffer.append( getInstructionString(((Integer)data.get(i)).intValue()));
	//		buffer.append(" Arguments: ");
	//		buffer.append(data.get(i+1));
	//		
	//		buffer.append("]");
	//	    }
	//	}
	//
	//	return buffer.toString();
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
