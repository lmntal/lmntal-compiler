/*
 * 作成日: 2003/10/21
 *
 */
package runtime;

import java.util.*;
import java.lang.reflect.Modifier;
import java.lang.reflect.Field;

/*
 * 中島君へ：Eclipseのデフォルトに合わせてタブ幅4で編集して下さい。
 * 
 * TODO memof は廃止する方向で検討する。
 * TODO ルール実行中の5つの配列はそれぞれ0から詰めて使用するのか決める。
 * TODO ルール実行中の5つの配列は3つ、あるいは1つに併合した方がよい？
 * 
 * TODO 実行膜スタックに積むタイミングを考える。
 *  - 右辺の全ての膜を生成してから、活性化を行うようにする。そのためにactivatemem命令を呼ぶ。
 *  - 底のほうに積むの実現にはStackの結合を使う。コミットのタイミングで結合する
 * ※現状では、ルート膜が除去されると、次のルート膜までの部分を移動先で実行するようにしている。
 * 	 *
	 * <p><b>注意</b>　方法4の文書に対して、「ロックしたまま実行膜スタックに積む」という操作
	 * およびその結果の「ロックしたまま実行膜スタックにも積まれた」状態が追加されました。
	 * <ul>
	 * <li>ローカルの膜の場合、親膜側から実際に実行膜スタックに積みます。
	 * 本膜がロックされているため、新しい膜が実行されることはありません。
	 * この
	 * 本膜のロックを解放すると、アトミックにロックが解放された状態になります。
	 * <li>リモートの膜の場合、一時的な実行スタックを作り、そこに親膜側から積んでいきます。
	 * リモートのルート膜のロックが解放されると、実行膜スタックの先頭に丸ごと移動されます。
	 * これによってアトミックにロックが解放された状態になります。
	 * TODO したがって、movememやnewrootの場合は、ルール実行終了時にロックを解放しなければなりません。
	 * </ul>
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
     * 命令の種類を保持する。*/	
    private int kind;

	/**
	 * 命令の引数を保持する。
	 * 命令の種類によって引数の型が決まっている。
	 */
	public List data = new ArrayList();
	
	//////////
	// 定数

	/** 対象の膜がローカルの計算ノードに存在することを保証する修飾子 */
	public static final int LOCAL = 100;
	/** ダミーの命令 */	
	public static final int DUMMY = -1;
    /** 未定義の命令 */	
    public static final int UNDEF = 0;
	/** 命令の最大種類数。全ての命令の種類を表す値はこれより小さな数字にすること。*/
	private static final int END_OF_INSTRUCTION = 256;	

    // 出力する基本ガード命令 (1--9)
    
    /** deref [-dstatom, srcatom, srcpos, dstpos]
     * <br><strong><font color="#ff0000">ガード命令</font></strong><br>
     * アトムsrcatomの第srcpos引数のリンク先が第dstpos引数に接続していることを確認したら、
     * リンク先のアトムへの参照をdstatomに代入する。
     */
	public static final int DEREF = 1;

	/** findatom [-dstatom, srcmem, func]
	 * <br>反復するガード命令<br>
	 * 膜srcmemにあってファンクタfuncを持つアトムへの参照を次々にdstatomに代入する。*/
	public static final int FINDATOM = 2;

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
    public static final int LOCKMEM = 3;
    
    /** locallockmem [-dstmem, srcfreelinkatom]
     * <br>ロック取得する最適化用ガード命令<br>
     * lockmemと同じ。ただしsrcfreelinkatomはこの計算ノードに存在する。
     * @see lockmem */
	public static final int LOCALLOCKMEM = LOCAL + LOCKMEM;

    /** anymem [-dstmem, srcmem] 
     * <br>反復するロック取得するガード命令<br>
     * 膜srcmemの子膜のうちまだロックを取得していない膜に対して次々に、
     * ノンブロッキングでのロック取得を試みる。
     * そして、ロック取得に成功した各子膜への参照をdstmemに代入する。
     * 取得したロックは、後続の命令列がその膜に対して失敗したときに解放される。
     * <p><b>注意</b>　ロック取得に失敗した場合と、その膜が存在していなかった場合とは区別できない。*/
	public static final int ANYMEM = 4;
	
	/** localanymem [-dstmem, srcmem]
     * <br>反復するロック取得する最適化用ガード命令<br>
	 * anymemと同じ。ただしsrcmemはこの計算ノードに存在する。dstmemについては何も仮定しない。
	 * @see anymem */
	public static final int LOCALANYMEM = LOCAL + ANYMEM;

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
     * <p><font color=red><b>この命令は不要かも知れない</b></font> */
    public static final int NEQMEM = 19;

//    /** lock [srcmem]
//     * <br>（廃止された）ガード命令<br>
//     * 膜srcmemに対するノンブロッキングでのロック取得を試みる。
//     * ロック取得に成功すれば、この膜はまだ参照を（＝ロックを）取得していなかった膜である
//     * <p>srcmemにmemof記法が廃止されたため、ロックはlockmemで行う。したがってlockは廃止された。*/
//    public static final int LOCK = err;

    // ヘッド命令から移管されたボディ命令 (25--29)

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

    // アトムを操作する基本ボディ命令 (30--39)    

    /** removeatom [srcatom]
     * <br>ボディ命令<br>
     * アトムsrcatomを現在の膜から取り出す。実行スタックは操作しない。
     * @see dequeueatom */
	public static final int REMOVEATOM = 30;
	
	/** localremoveatom [srcatom]
     * <br>最適化用ボディ命令<br>
     * removeatomと同じ。ただしsrcatomはこの計算ノードに存在する。*/
	public static final int LOCALREMOVEATOM = LOCAL + REMOVEATOM;

    /** newatom [-dstatom, srcmem, func]
     * <br>ボディ命令<br>
     * 膜srcmemにファンクタfuncを持つ新しいアトム作成し、参照をdstatomに代入する。
     * アトムはまだ実行スタックには積まれない。
     * @see enqueueatom */
    public static final int NEWATOM = 31;
    
    /** localnewatom [-dstatom, srcmem, func]
     * <br>最適化用ボディ命令<br>
     * newatomと同じ。ただしsrcmemはこの計算ノードに存在する。*/
	public static final int LOCALNEWATOM = LOCAL + NEWATOM;

    /** enqueueatom [srcatom]
     * <br>ボディ命令<br>
     * アトムsrcatomを所属膜の実行スタックに積む。
     * <p>すでに実行スタックに積まれていた場合の動作は未定義とする。
     * TODO ※水野君[　] srcatomがアクティブかどうかは判定しない仕様にすべきですね？ */
    public static final int ENQUEUEATOM = 32;
    
	/** localenqueueatom [srcatom]
	 * <br>最適化用ボディ命令<br>
	 * enqueueatomと同じ。ただしsrcatomはこの計算ノードに存在する。*/
	public static final int LOCALENQUEUEATOM = LOCAL + ENQUEUEATOM;

    /** dequeueatom [srcatom]
     * <br>最適化用ボディ命令<br>
     * アトムsrcatomがこの計算ノードにある実行スタックに入っていれば、実行スタックから取り出す。
     * <p><b>注意</b>　この命令は、メモリ使用量のオーダを削減するために任意に使用することができる。
     * アトムを再利用するときは、因果関係に注意すること。
     * <p>なお、他の計算ノードにある実行スタックの内容を取得/変更する命令は存在しない。*/
    public static final int DEQUEUEATOM = 33;

    /** localdequeueatom [srcatom]
     * <br>最適化用ボディ命令<br>
     * dequeueatomと同じ。ただしsrcatomはこの計算ノードに存在する。*/
	public static final int LOCALDEQUEUEATOM = LOCAL + DEQUEUEATOM;

	/** enqueueallatoms [srcmem]
	 * <br>ボディ命令<br>
	 * 膜srcmemにある全てのアクティブアトムをこの膜の実行スタックに積む。
	 * アクティブかどうかの判断には、
	 * ファンクタを動的検査する方法と、2つのグループのアトムがあるとして所属膜が管理する方法がある。*/
	public static final int ENQUEUEALLATOMS = 34;
	// LOCALENQUEUEALLATOMS は最適化の効果が少ないと思われるため、廃案。

	// 膜を操作する基本ボディ命令 (40--49)    

	/** removemem [srcmem]
	 * <br>ボディ命令<br>
	 * 膜srcmemを現在の膜から取り出す。
	 * 膜srcmemはロック時に実行膜スタックから除去されているため、実行膜スタックは操作しない。
	 * @see removeproxies */
	public static final int REMOVEMEM = 40;

	/** localremovemem [srcmem]
	 * <br>最適化用ボディ命令<br>
	 * removememと同じ。ただしsrcmemの親膜はこの計算ノードに存在する。*/
	public static final int LOCALREMOVEMEM = LOCAL + REMOVEMEM;

	/** newmem [-dstmem, srcmem]
	 * <br>ボディ命令<br>
	 * （活性化された）膜srcmemに新しい（ルート膜でない）子膜を作成し、dstmemに代入し、活性化する。
	 * この場合の活性化は、srcmemと同じ実行膜スタックに積むことを意味する。
	 * @see enqueuemem */
	public static final int NEWMEM = 41;

	/** localnewmem [-dstmem, srcmem]
	* <br>最適化用ボディ命令<br>
	* newmemと同じ。ただしsrcmemはこの計算ノードに存在する。*/
	public static final int LOCALNEWMEM = LOCAL + NEWMEM;

	/** newroot [-dstmem, srcmem, node]
	 * <br>（予約された）ボディ命令<br>
	 * 膜srcmemの子膜に文字列nodeで指定された計算ノードで実行される新しいロックされたルート膜を作成し、
	 * 参照をdstmemに代入し、（ロックしたまま）活性化する。
	 * この場合の活性化は、仮の実行膜スタックに積むことを意味する。
	 * <p>newmemと違い、このルート膜のロックは明示的に解放しなければならない。
	 * @see unlockmem */
	public static final int NEWROOT = 42;

//	/** localnewroot [-dstmem, srcmem, node]
//	 * <br>（予約された）最適化用ボディ命令<br>
//	 * newrootと同じ。ただしsrcmemはこの計算ノードに存在する。
//	 * この命令には最適化の効果がほとんど無いため、廃案。*/
//	public static final int LOCALNEWROOT = LOCAL + NEWROOT;

//    /** dequeuemem [srcmem]
//     * <br>（予約された）ボディ命令<br>
//     * 膜srcmemを再帰的に実行膜スタックから取り出す。
//     * 再帰的にロック取得するヘッド命令が取り出しているため不要。この命令は廃止する。
//     */
//    public static final int DEQUEUEMEM = err;

	/** enqueuemem [srcmem]
	 * <br>ボディ命令<br>
	 * 膜srcmemを管理するタスクの実行膜スタックに膜srcmemを積む。
	 * <p><strike>実行後、srcmemへの参照は廃棄しなければならない。</strike>
	 * <p>典型的には、ロックした膜を再利用するために移動した直後のタイミングで呼ばれる。
	 * @see newmem
	 * @see activatemem */
	public static final int ENQUEUEMEM = 43;

	/** localenqueuemem [srcmem]
	 * <br>最適化用ボディ命令<br>
	 * enqueuememと同じ。ただしsrcmemはこの計算ノードに存在する。*/
	public static final int LOCALENQUEUEMEM = LOCAL + ENQUEUEMEM;

	/** movecells [dstmem, srcmem]
	 * <br>ボディ命令<br>
	 * 膜srcmemにある全てのアトムと子膜（ロックを取得していない）を膜dstmemに移動する。
	 * 実行膜スタックおよび実行スタックは操作しない。
	 * <p>実行後、膜srcmemはこのまま廃棄されなければならない。
	 * <p><b>注意</b>　Ruby版のpourから名称変更
	 * @see enqueueallatoms */
	public static final int MOVECELLS = 44;

	/** movemem [dstmem, srcmem]
	 * <br>ボディ命令<br>
	 * ロックされた膜srcmemを（活性化された）膜dstmemに移動し、ロックしたまま活性化する。
	 * この場合の活性化は、srcmemがルート膜の場合、仮の実行膜スタックに積むことを意味し、
	 * ルート膜でない場合、dstmemと同じ実行膜スタックに積むことを意味する。
	 * <p>膜srcmemを再利用するために使用される。
	 * <p>newmemと違い、srcmemのロックは明示的に解放しなければならない。
	 * @see unlockmem */
	public static final int MOVEMEM = 45;

	/** localmovemem [dstmem, srcmem]
	 * <br>ボディ命令<br>
	 * movememと同じ。ただしsrcmemはこの計算ノードに存在する。*/
	public static final int LOCALMOVEMEM = LOCAL + MOVEMEM;

	/** unlockmem [srcmem]
	 * <br>ボディ命令<br>
	 * （活性化した）膜srcmemのロックを解放する。
	 * srcmemがルート膜の場合、仮の実行膜スタックの内容を実行膜スタックの底に転送する。
	 * <p>再利用された膜、およびルールで新しく生成されたルート膜に対して必ず呼ばれる。
	 * <p>実行後、srcmemへの参照は廃棄しなければならない。*/
	public static final int UNLOCKMEM = 46;

	/** localunlockmem [srcmem]
	 * <br>最適化用ボディ命令<br>
	 * unlockmemと同じ。ただしsrcmemはこの計算ノードに存在する。*/
	public static final int LOCALUNLOCKMEM = LOCAL + UNLOCKMEM;
	
	// 予約 (50--59)
	
    // 自由リンク管理アトム自動処理のためのボディ命令 (60--64)
	
    /** removeproxies [srcmem]
     * <br>ボディ命令<br>
     * srcmemを通る無関係な自由リンク管理アトムを自動削除する。
     * <p>removememの直後に同じ膜に対して呼ばれる。*/
    public static final int REMOVEPROXIES = 60;

    /** removetoplevelproxies [srcmem]
     * <br>ボディ命令<br>
     * 膜srcmem（本膜）を通過している無関係な自由リンク管理アトムを除去する。
	 * <p>全てのremoveproxiesの後で呼ばれる。*/
    public static final int REMOVETOPLEVELPROXIES = 61;

    /** insertproxies [parentmem,childmem]
     * <br>ボディ命令<br>
     * 指定された膜間に自由リンク管理アトムを自動挿入する。
     * <p>全てのmovememの後で呼ばれる。*/
    public static final int INSERTPROXIES = 62;
	
    /** removetemporaryproxies [srcmem]
     * <br>ボディ命令<br>
     * 膜srcmem（本膜）に残された"star"アトムを除去する。
     * <p>全てのinsertproxiesの後で呼ばれる。*/
    public static final int REMOVETEMPORARYPROXIES = 63;


	// ルールを操作するボディ命令 (65--69)
	
	/** loadruleset [dstmem, ruleset]
	 * <br>ボディ命令<br>
	 * rulesetが参照するルールセットを膜dstmemにコピーする。
	 * <p>この膜のアクティブアトムは再エンキューすべきである。
	 * @see enqueueallatoms */
	public static final int LOADRULESET = 65;

	/** localloadruleset [dstmem, ruleset]
	 * <br>最適化用ボディ命令<br>
	 * loadrulesetと同じ。ただしdstmemはこの計算ノードに存在する。*/
	public static final int LOCALLOADRULESET = LOCAL + LOADRULESET;

	/** copyrules [dstmem, srcmem]
	 * <br>ボディ命令<br>
	 * 膜srcmemにある全てのルールを膜dstmemにコピーする。
	 * <p><b>注意</b>　Ruby版のinheritrulesから名称変更 */
	public static final int COPYRULES = 66;

	/** localcopyrules [dstmem, srcmem]
	 * <br>最適化用ボディ命令<br>
	 * copyrulesと同じ。ただしdstmemはこの計算ノードに存在する。*/
	public static final int LOCALCOPYRULES = LOCAL + COPYRULES;

	/** clearrules [dstmem]
	 * <br>ボディ命令<br>
	 * 膜dstmemにある全てのルールを消去する。*/
	public static final int CLEARRULES = 67;
	
	/** localclearrules [dstmem]
	 * <br>最適化用ボディ命令<br>
	 * clearrulesと同じ。ただしdstmemはこの計算ノードに存在する。*/
	public static final int LOCALCLEARRULES = LOCAL + CLEARRULES;

    // プロセス文脈をコピーまたは廃棄するための命令 (70--79)
    
    /** recursivelock [srcmem]
     * <br>（予約された）ガード命令<br>
     * 膜srcmemの全ての子膜に対して再帰的にロックを取得する。
     * <p>右辺での出現が1回でないプロセス文脈が書かれた左辺の膜に対して使用される。
     * <p><font color=red><b>
     * デッドロックが起こらないことを保証できれば、この命令はブロッキングで行うべきである。
     * </b></font>*/
    public static final int RECURSIVELOCK = 70;

    /** recursiveunlock [srcmem]
     * <br>（予約された）ボディ命令<br>
     * 膜srcmemの全ての子膜に対して再帰的にロックを解放する。
     * 膜はそれを管理するタスクの実行膜スタックに再帰的に積まれる。
     * <p>再帰的に積む方法は、今後考える。
     * @see unlockmem */
    public static final int RECURSIVEUNLOCK = 71;
	
    /** copymem [-dstmem, srcmem]
     * <br>（予約された）ボディ命令<br>
     * 再帰的にロックされた膜srcmemの内容のコピーを作成し、膜dstmemに入れる。
     * ただし自由リンク管理アトムの第1引数の状態は定義されない。
     * <p>自由リンクリストのコピーを行う方法は、今後考える。*/
    public static final int COPYMEM = 72;

    /** dropmem [srcmem]
     * <br>（予約された）ボディ命令<br>
     * 再帰的にロックされた膜srcmemを破棄する。
     * この膜や子孫の膜をルート膜とするタスクは強制終了する。*/
    public static final int DROPMEM = 73;


	// リンクを操作するボディ命令 (80--84)
	
	/** newlink [atom1, pos1, atom2, pos2]
	 * <br>ボディ命令<br>
	 * アトムatom1の第pos1引数と、アトムatom2の第pos2引数の間に両方向リンクを張る。
	 * <p>典型的には、atom1とatom2はいずれもルールボディに存在する。
	 * <p><b>注意</b>　Ruby版の片方向から仕様変更された */
	public static final int NEWLINK = 80;

	/** localnewlink [atom1, pos1, atom2, pos2]
	 * <br>ボディ命令<br>
	 * newlinkと同じ。ただしatom1はこの計算ノードに存在する。*/
	public static final int LOCALNEWLINK = LOCAL + NEWLINK;

	/** relink [atom1, pos1, atom2, pos2]
	 * <br>ボディ命令<br>
	 * アトムatom1の第pos1引数と、アトムatom2の第pos2引数のリンク先を接続する。
	 * <p>典型的には、atom1はルールボディに、atom2はルールヘッドに存在する。
	 * <p>実行後、atom2[pos2]の内容は無効になる。*/
	public static final int RELINK = 81;

	/** localrelink [atom1, pos1, atom2, pos2]
	 * <br>ボディ命令<br>
	 * relinkと同じ。ただしatom1およびatom2はこの計算ノードに存在する。*/
	public static final int LOCALRELINK = LOCAL + RELINK;

	/** unify [atom1, pos1, atom2, pos2]
	 * <br>ボディ命令<br>
	 * アトムatom1の第pos1引数のリンク先の引数と、アトムatom2の第pos2引数のリンク先の引数を接続する。
	 * <p>典型的には、atom1とatom2はいずれもルールヘッドに存在する。*/
	public static final int UNIFY = 82;

	/** localunify [atom1, pos1, atom2, pos2]
	 * <br>ボディ命令<br>
	 * unifyと同じ。ただしatom1およびatom2はこの計算ノードに存在する。*/
	public static final int LOCALUNIFY = LOCAL + UNIFY;

	/** getlink [-link2, atom2, pos2]
	 * <br>出力する最適化用ボディ命令<br>
	 * アトムatom2の第pos2引数に格納されたリンクオブジェクトへの参照をlink2に代入する。
	 * <p>典型的には、atom2はルールヘッドに存在する。
	 * inheritlinkと組み合わせて使用してrelinkの代用にする。*/
	public static final int GETLINK = 83;

	/** localgetlink [-link2, atom2, pos2]
	 * <br>ボディ命令<br>
	 * getlinkと同じ。ただしatom2はこの計算ノードに存在する。*/
	public static final int LOCALGETLINK = LOCAL + GETLINK;

	/** inheritlink [atom1, pos1, link2]
	 * <br>最適化用ボディ命令<br>
	 * atom1の第pos1引数と、リンクlink2のリンク先を接続する。
	 * アトムatom1の第pos1引数と、リンクlink2のリンク先を接続する。
	 * <p>典型的には、atom1はルールボディに存在し、link2はルールヘッドに存在する。
	 * <p>link2は再利用されるため、実行後はlink2は廃棄しなければならない。
	 * @see getlink */
	public static final int INHERITLINK = 84;

	/** localinheritlink [atom1, pos1, link2]
	 * <br>ボディ命令<br>
	 * inheritlinkと同じ。ただしatom1はこの計算ノードに存在する。*/
	public static final int LOCALINHERITLINK = LOCAL + INHERITLINK;

	// 予約 (85-99)
	

	/** isint [atom, pos]
	 * <br>（予約された）ガード命令
	 * アトムatomの第pos引数のリンク先が整数アトムであることを確認する。*/
	public static final int ISINT = 90;

	
    // 制御命令 (200--209)
	
    /** react [ruleid, [atomargs...], [memargs...]]
     * <br>失敗しないガード命令<br>
     * ruleidが参照するルールに対するマッチが成功したことを表す。*/
    public static final int REACT = 200;

//    /** not [[instructions...]]
//     * <br>（予約された）ガード命令<br>
//     * 否定条件を指定する。
//     * branchと同じ。
//     * @deprecated
//     * @see branch */
//    public static final int NOT = 201;

    /** stop 
     * <br>（予約された）失敗しないガード命令<br>
     * 否定条件にマッチしたことを表す。
     * 命令列の最後とは異なるらしい。
     * 最後→Ruleset#reactはreturnするが、
     * stop→Ruleset#reactはreturnしない。
     */
    public static final int STOP = 202;

    /** spec [formals, locals]
     * <br>無視される<br>
     * 仮引数と一時変数の個数を宣言する。*/
    public static final int SPEC = 203;

    /** loop [[instructions...]]
     * <br>構造化命令<br>
     * 引数の命令列を実行することを表す。
     * 引数列を最後まで実行した場合、このloop命令の実行を繰り返す。*/
    public static final int LOOP = 204;

    /** branch [[instructions...]]
     * <br>構造化命令<br>
     * 引数の命令列を実行することを表す。
     * 引数実行中に失敗した場合、引数実行中に取得したロックを解放し、
     * 典型的にはこのbranchの次の命令に進む。
     * 引数列を最後まで実行した場合、ここで終了する
     * TODO またはhalt命令を作る
     * @see not */
    public static final int BRANCH = 205;

	// 組み込み機能に関する命令 (210--229)

	/** inline [[args...] text]
	 * <br>（予約された）ボディ命令
	 * 文字列textで指定されたJavaコードをエミットする。
	 * コード中の%0は本膜への参照で置換される。
	 * コード中の%1から%nは詳細が未定の引数argsの各要素が表すリンクへの参照で置換される。
	 * <p>Javaソースを出力しない処理系環境では例外を発生する。*/
	public static final int INLINE = 210;

	/** builtin [class, method, [args...]]
	 * <br>（予約された）ボディ命令
	 * 指定された引数でJavaのクラスメソッドを呼び出す。
	 * インタプリタ動作するときに組み込み機能を提供するために使用する。詳細は未定。*/
	public static final int BUILTIN = 211;
	

    ////////////////////////////////////////////////////////////////
    /**
     * 命令の種類を取得する。
     */
	public int getKind() {
		return kind;
	}
	/**@deprecated*/
	public int getID() {
		return getKind();
	}
	public int getIntArg1() {
		return ((Integer)data.get(0)).intValue();
	}
	public int getIntArg2() {
		return ((Integer)data.get(1)).intValue();
	}
	public int getIntArg3() {
		return ((Integer)data.get(2)).intValue();
	}
	public int getIntArg4() {
		return ((Integer)data.get(3)).intValue();
	}
	public Object getArg1() {
		return data.get(0);
	}
	public Object getArg2() {
		return data.get(1);
	}
	public Object getArg3() {
		return data.get(2);
	}
	public Object getArg4() {
		return data.get(3);
	}

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
	
	////////////////////////////////////////////////////////////////

    /**
     * ダミー命令を生成する。
     * さしあたって生成メソッドがまだできてない命令はこれを使う
     * @param s 説明用の文字列
     */
    public static Instruction dummy(String s) {
		Instruction i = new Instruction(DUMMY);
		i.add(s);
		return i;
    }
	
    /**
     * react 命令を生成する。
     * @param r 反応できるルールオブジェクト
     * @param actual 実引数
     * TODO reactの引数の仕様を決める
     */
    public static Instruction react(Rule r, List actual) {
		Instruction i = new Instruction(REACT);
		i.add(r);
		i.add(actual);
		return i;
    }	
    /** @deprecated */
    public static Instruction findatom(int dstatom, List srcmem, Functor func) {
		Instruction i = new Instruction(FINDATOM);
		i.add(dstatom);
		i.add(srcmem);
		i.add(func);
		return i;
    }	
    
    //
    
	/** findatom 命令を生成する */
	public static Instruction findatom(int dstatom, int srcmem, Functor func) {
		return new Instruction(FINDATOM,dstatom,srcmem,func);
	}	
    /** anymem 命令を生成する */
    public static Instruction anymem(int dstmem, int srcmem) {
		return new Instruction(ANYMEM,dstmem,srcmem);
    }
    /** newatom 命令を生成する */
    public static Instruction newatom(int dstatom, int srcmem, Functor func) {
		return new Instruction(NEWATOM,dstatom,srcmem,func);
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
		return new Instruction(LOADRULESET,mem,rs);
    }
    /** getmem 命令を生成する */
    public static Instruction getmem(int ret, int atom) {
		return new Instruction(GETMEM,ret,atom);
    }	
    /** removeatom 命令を生成する */
	public static Instruction removeatom(int atom) {
		return new Instruction(REMOVEATOM,atom);
	}	
	/** @deprecated */
	public static Instruction removeatom(int atom, Functor func) {
		return new Instruction(REMOVEATOM,atom,func);
	}	
    
	// コンストラクタ
	
    /** 無名命令を作る。*/
    public Instruction() {
    }
	
    /**
     * 指定された命令をつくる
     * @param kind
     */
    public Instruction(int kind) {
    	this.kind = kind;
    }
    private Instruction(int kind, int arg1) {
		this.kind = kind;
		add(arg1);
    }
	private Instruction(int kind, int arg1, int arg2) {
		this.kind = kind;
		add(arg1);
		add(arg2);
	}
	private Instruction(int kind, int arg1, Object arg2) {
		this.kind = kind;
		add(arg1);
		add(arg2);
	}
	private Instruction(int kind, int arg1, int arg2, int arg3) {
		this.kind = kind;
		add(arg1);
		add(arg2);
		add(arg3);
	}
	private Instruction(int kind, int arg1, int arg2, Object arg3) {
		this.kind = kind;
		add(arg1);
		add(arg2);
		add(arg3);
	}
    private Instruction(int kind, int arg1, int arg2, int arg3, int arg4) {
		this.kind = kind;
		add(arg1);
		add(arg2);
		add(arg3);
		add(arg4);
    }
    
	//////////////////////////////////
	//
	// デバッグ用表示メソッド
	//


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

	/** Integerでラップされた命令番号から命令名へのハッシュ。
	 * <p>処理系開発が収束した頃に、もっと効率のよい別の構造で置き換えてもよい。 */
	static Hashtable instructionTable = new Hashtable();
	
	//インスタンス生成時にスタックオーバーフローを起こしたので修正しました。 by Mizuno
    static {
		try {
			Instruction inst = new Instruction();
			Field[] fields = inst.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				if (f.getType().getName().equals("int") && Modifier.isStatic(f.getModifiers())) {
					Integer idobj = new Integer(f.getInt(inst));
					if (instructionTable.containsKey(idobj)) {
						System.out.println("WARNING: collision detected on instruction kind = " 
							+ idobj.intValue());
					}
					instructionTable.put(idobj, f.getName().toLowerCase());
				}
			}
		}
		catch(java.lang.SecurityException e)		{ e.printStackTrace(); }
		catch(java.lang.IllegalAccessException e)	{ e.printStackTrace(); }
    }


	/**
	 * デバッグ用表示メソッド。
	 * 命令のid (int)を与えると、該当する命令のStringを返してくれる。
	 *
	 * @author NAKAJIMA Motomu <nakajima@ueda.info.waseda.ac.jp>
	 * @return String
	 * 
	 */
	public static String getInstructionString(int kind){
		String answer = "";
		answer = (String)instructionTable.get(new Integer(kind));
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
		return getInstructionString(kind)+" "+data.toString();

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
