/*
 * 作成日: 2003/10/21
 *
 */
package runtime;

import java.util.*;
import java.lang.reflect.Modifier;
import java.lang.reflect.Field;

/*
 * <p><b>注意</b>　方法4の文書に対して、「ロックしたまま実行膜スタックに積む」という操作
 * およびその結果の「ロックされた、かつ実行膜スタックにも積まれた」状態が追加された。
 * 
 * <p>ボディ実行の流れは次の通り。生成と活性化を親膜側から行った後、ロックを子膜側から解放する。
 * <ul>
 * <li>活性化する膜が本膜と同じタスクの膜の場合、親膜側から実際に実行膜スタックに積む。
 * 本膜が実行中なので、新しい膜が実行されることはない。
 * このルールの適用終了前にロックは解放するが、何も起こらない。
 * このルールの適用が終了すると、子膜の実行が開始される。
 * <li>活性化する膜がリモート膜や他のタスクの膜の場合、一時的な実行スタックを作り、
 * そこに親膜側から積んでいく。
 * リモートのルート膜のロックが解放されると、実行膜スタックの先頭に丸ごと移動される。
 * これによって実行膜スタックに全ての膜がアトミックに積まれることになる。
 * </ul>
 * TODO コンパイラは次のコードを出力する：addmemやnewrootした膜は、ルール実行終了時に（子膜側から順番に）unlockmemを実行する。
 */
 
/**
 * 1 つの命令を保持する。通常は、InstructionのArrayListとして保持する。
 * 
 * デバッグ用表示メソッドを備える。
 *
 * @author hara, nakajima, n-kato
 */
public class Instruction implements Cloneable {
	
    /** 命令の種類を保持する。*/	
    private int kind;

	/**
	 * 命令の引数を保持する。
	 * 命令の種類によって引数の型が決まっている。
	 */
	public List data = new ArrayList();
	
    //////////
    // 定数

    /** 対象の膜がローカルの計算ノードに存在することを保証する修飾子（一部他の用途で使用） */
    public static final int LOCAL = 100;
    /** 型付きアトムに対する命令がリンクではなく、ファンクタを対象にしていることを表す修飾子 */
    public static final int OPT = 100;
    /** ダミーの命令 */	
    public static final int DUMMY = -1;
    /** 未定義の命令 */	
    public static final int UNDEF = 0;
//	/** 命令の最大種類数。命令の種類を表す値はこれより小さな数にすること。*/
//	private static final int END_OF_INSTRUCTION = 1024;

    // アトムに関係する出力する基本ガード命令 (1--5)
	//  -----  deref     [-dstatom, srcatom, srcpos, dstpos]
	//  -----  derefatom [-dstatom, srcatom, srcpos]
	//  -----  findatom  [-dstatom, srcmem, funcref]
	//	-----  getlink   [-link,    atom, pos]

    /** deref [-dstatom, srcatom, srcpos, dstpos]
     * <br><strong><font color="#ff0000">出力するガード命令</font></strong><br>
     * アトム$srcatomの第srcpos引数のリンク先が第dstpos引数に接続していることを確認したら、
     * リンク先のアトムへの参照を$dstatomに代入する。*/
	public static final int DEREF = 1;
	// LOCALDEREFは不要

	/** derefatom [-dstatom, srcatom, srcpos]
	 * <br>出力する失敗しない最適化用＋型付き拡張用ガード命令<br>
	 * アトム$srcatomの第srcpos引数のリンク先のアトムへの参照を$dstatomに代入する。
	 * <p>引き続き$dstatomが、単項アトム（整数なども含む）や自由リンク管理アトムと
	 * マッチするかどうか検査する場合に使用することができる。*/
	public static final int DEREFATOM = 2;
	// LOCALDEREFATOMは不要

	/** findatom [-dstatom, srcmem, funcref]
	 * <br>反復するガード命令<br>
	 * 膜$srcmemにあってファンクタfuncrefを持つアトムへの参照を次々に$dstatomに代入する。*/
	public static final int FINDATOM = 3;
	// LOCALFINDATOMは不要

	/** getlink [-link, atom, pos]
	 * <br>出力する失敗しない拡張ガード命令、最適化用ボディ命令<br>
	 * アトム$atomの第pos引数に格納されたリンクオブジェクトへの参照を$linkに代入する。
	 * <p>典型的には、$atomはルールヘッドに存在する。*/
	public static final int GETLINK = 4;
	// LOCALGETLINKは不要

//	/** dereflink [atom, link]
//	 * <br>出力する失敗しない拡張ガード命令、最適化用ボディ命令<br>
//	 * リンク$linkが指すアトムへの参照を$atomに代入する。*/
//	public static final int DEREFLINK = err;
//	// LOCALDEREFLINKは不要

	// 膜に関係する出力する基本ガード命令 (5--9)
	// [local]lockmem    [-dstmem, freelinkatom]
	// [local]anymem     [-dstmem, srcmem] 
	// [local]lock       [srcmem]
	//  ----- getmem     [-dstmem, srcatom]
	//  ----- getparent  [-dstmem, srcmem]

    /** lockmem [-dstmem, freelinkatom]
     * <br>ロック取得するガード命令<br>
     * 自由リンク出力管理アトム$freelinkatomが所属する膜に対して、
     * ノンブロッキングでのロックを取得を試みる。
     * そしてロック取得に成功したこの膜への参照を$dstmemに代入する。
     * 取得したロックは、後続の命令列がその膜に対して失敗したときに解放される。
     * <p>
     * ロック取得に成功すれば、この膜はまだ参照を（＝ロックを）取得していなかった膜である
     * （この検査はRuby版ではneqmem命令で行っていた）。
     * <p>膜の外からのリンクで初めて特定された膜への参照を取得するために使用される。
     * @see testmem
     * @see getmem */
    public static final int LOCKMEM = 5;
    
    /** locallockmem [-dstmem, freelinkatom]
     * <br>ロック取得する最適化用ガード命令<br>
     * lockmemと同じ。ただし$freelinkatomはこの計算ノードに存在する。*/
	public static final int LOCALLOCKMEM = LOCAL + LOCKMEM;

    /** anymem [-dstmem, srcmem] 
     * <br>反復するロック取得するガード命令<br>
     * 膜$srcmemの子膜のうちまだロックを取得していない膜に対して次々に、
     * ノンブロッキングでのロック取得を試みる。
     * そして、ロック取得に成功した各子膜への参照を$dstmemに代入する。
     * 取得したロックは、後続の命令列がその膜に対して失敗したときに解放される。
     * <p><b>注意</b>　ロック取得に失敗した場合と、その膜が存在していなかった場合とは区別できない。*/
	public static final int ANYMEM = 6;
	
	/** localanymem [-dstmem, srcmem]
     * <br>反復するロック取得する最適化用ガード命令<br>
	 * anymemと同じ。ただし$srcmemはこの計算ノードに存在する。$dstmemについては何も仮定されない。*/
	public static final int LOCALANYMEM = LOCAL + ANYMEM;

	/** lock [srcmem]
	 * <br>ロック取得するガード命令<br>
	 * 膜$srcmemに対して、ノンブロッキングでのロックを取得を試みる。
	 * 取得したロックは、後続の命令列が失敗したときに解放される。
	 * <p>アトム主導テストで、主導するアトムによって特定された膜のロックを取得するために使用される。
	 * @see lockmem
	 * @see getmem */
	public static final int LOCK = 7;
	
	/** locallock [srcmem]
	 * <br>ロック取得する最適化用ガード命令<br>
	 * lockと同じ。ただし$srcmemはこの計算ノードに存在する。*/
	public static final int LOCALLOCK = LOCAL + LOCK;

	/** getmem [-dstmem, srcatom]
	 * <br>ガード命令<br>
	 * アトム$srcatomの所属膜への参照をロックせずに$dstmemに代入する。
	 * <p>アトム主導テストで使用される。
	 * @see lock */
	public static final int GETMEM = 8;
	// LOCALGETMEMは不要
	
	/** getparent [-dstmem, srcmem]
	 * <br>ガード命令<br>
	 * （ロックしていない）膜$srcmemの親膜への参照をロックせずに$dstmemに代入する。
	 * <p>アトム主導テストで使用される。*/
	public static final int GETPARENT = 9;
	// LOCALGETPARENTは不要

    // 膜に関係する出力しない基本ガード命令 (10--19)
	//  ----- testmem    [dstmem, srcatom]
	//  ----- norules    [srcmem] 
	//  ----- natoms     [srcmem, count]
	//  ----- nfreelinks [srcmem, count]
	//  ----- nmems      [srcmem, count]
	//  ----- eqmem      [mem1, mem2]
	//  ----- neqmem     [mem1, mem2]
	//  ----- stable     [srcmem]

    /** testmem [dstmem, srcatom]
     * <br>ガード命令<br>
     * アトム$srcatomが（ロックされた）膜$dstmemに所属することを確認する。
     * <p><b>注意</b>　Ruby版ではgetmemで参照を取得した後でeqmemを行っていた。
     * @see lockmem */
	public static final int TESTMEM = 10;
	// LOCALTESTMEMは不要

    /** norules [srcmem] 
     * <br>ガード命令<br>
     * 膜$srcmemにルールが存在しないことを確認する。*/
    public static final int NORULES = 11;
    // LOCALNORULESは不要

    /** natoms [srcmem, count]
     * <br>ガード命令<br>
     * 膜$srcmemの自由リンク管理アトム以外のアトム数がcountであることを確認する。*/
    public static final int NATOMS = 12;
	// LOCALNATOMSは不要

    /** nfreelinks [srcmem, count]
     * <br>ガード命令<br>
     * 膜$srcmemの自由リンク数がcountであることを確認する。*/
    public static final int NFREELINKS = 13;
	// LOCALNFREELINKSは不要

    /** nmems [srcmem, count]
     * <br>ガード命令<br>
     * 膜$srcmemの子膜の数がcountであることを確認する。*/
    public static final int NMEMS = 14;
	// LOCALNMEMSは不要

    /** eqmem [mem1, mem2]
     * <br>ガード命令<br>
     * $mem1と$mem2が同一の膜を参照していることを確認する。
     * <p><b>注意</b> Ruby版のeqから分離 */
    public static final int EQMEM = 15;
	// LOCALEQMEMは不要
	
    /** neqmem [mem1, mem2]
     * <br>ガード命令<br>
     * $mem1と$mem2が異なる膜を参照していることを確認する。
     * <p><b>注意</b> Ruby版のneqから分離
     * <p><font color=red><b>この命令は不要かも知れない</b></font> */
    public static final int NEQMEM = 16;
	// LOCALNEQMEMは不要

	/** stable [srcmem]
	 * <br>ガード命令<br>
	 * 膜$srcmemとその子孫の全ての膜の実行が停止していることを確認する。*/
	public static final int STABLE = 17;
	// LOCALSTABLEは不要
    
	// アトムに関係する出力しない基本ガード命令 (20-24)
	//  -----  func    [srcatom, funcref]
	//  -----  eqatom  [atom1, atom2]
	//  -----  neqatom [atom1, atom2]

	/** func [srcatom, funcref]
	 * <br>ガード命令<br>
	 * アトム$srcatomがファンクタfuncrefを持つことを確認する。
	 * <p>getfunc[tmp,srcatom];loadfunc[func,funcref];eqfunc[tmp,func] と同じ。*/
	public static final int FUNC = 20;
	// LOCALFUNCは不要

	/** eqatom [atom1, atom2]
	 * <br>ガード命令<br>
	 * $atom1と$atom2が同一のアトムを参照していることを確認する。
	 * <p><b>注意</b> Ruby版のeqから分離 */
	public static final int EQATOM = 21;
	// LOCALEQATOMは不要

	/** neqatom [atom1, atom2]
	 * <br>ガード命令<br>
	 * $atom1と$atom2が異なるアトムを参照していることを確認する。
	 * <p><b>注意</b> Ruby版のneqから分離 */
	public static final int NEQATOM = 22;
	// LOCALNEQATOMは不要

	// ファンクタに関係する命令 (25--29)	
	//  -----  dereffunc [-dstfunc, srcatom, srcpos]
	//  -----  getfunc   [-func,    atom]
	//  -----  loadfunc  [-func,    funcref]
	//  -----  eqfunc              [func1, func2]
	//  -----  neqfunc             [func1, func2]

	/** dereffunc [-dstfunc, srcatom, srcpos]
	 * <br>出力する失敗しない拡張ガード命令
	 * アトム$srcatomの第srcpos引数のリンク先のアトムのファンクタを取得し、$dstfuncに代入する。
	 * <p>引き続き、型付き単項アトムのマッチングを行うために使用される。
	 * <p>単項アトムでない型付きプロセス文脈は、リンクオブジェクトを使って操作する。
	 * <p>derefatom[dstatom,srcatom,srcpos];getfunc[dstfunc,dstatom]と同じなので廃止？*/
	public static final int DEREFFUNC = 25;
	// LOCALDEREFFUNCは不要

	/** getfunc [-func, atom]
	 * <br>出力する失敗しない拡張ガード命令<br>
	 * アトム$atomのファンクタへの参照を$funcに代入する。*/
	public static final int GETFUNC = 26;
	// LOCALGETFUNCは不要

	/** loadfunc [-func, funcref]
	 * <br>出力する失敗しない拡張ガード命令<br>
	 * ファンクタfuncrefへの参照を$funcに代入する。*/
	public static final int LOADFUNC = 27;
	// LOCALLOADFUNCは不要

	/** eqfunc [func1, func2]
	 * <br>型付き拡張用ガード命令<br>
	 * ファンクタ$func1と$func2が等しいことを確認する。*/
	public static final int EQFUNC = 28;
	// LOCALEQFUNCは不要

	/** neqfunc [func1, func2]
	 * <br>型付き拡張用ガード命令<br>
	 * ファンクタ$func1と$func2が異なることを確認する。*/
	public static final int NEQFUNC = 29;
	// LOCALEQFUNCは不要

    // アトムを操作する基本ボディ命令 (30--39)    
	// [local]removeatom                  [srcatom, srcmem, funcref]
	// [local]newatom           [-dstatom, srcmem, funcref]
	// [local]newatomindirect   [-dstatom, srcmem, func]
    // [local]enqueueatom                 [srcatom]
	//  ----- dequeueatom                 [srcatom]
	//  ----- freeatom                    [srcatom]
	// [local]alterfunc                   [atom, funcref]
	// [local]alterfuncindirect           [atom, func]

    /** removeatom [srcatom, srcmem, funcref]
     * <br>ボディ命令<br>
     * （膜$srcmemにあってファンクタ$funcを持つ）アトム$srcatomを現在の膜から取り出す。
     * 実行スタックは操作しない。
     * @see dequeueatom */
	public static final int REMOVEATOM = 30;
	
	/** localremoveatom [srcatom, srcmem, funcref]
     * <br>最適化用ボディ命令<br>
     * removeatomと同じ。ただし$srcatomはこの計算ノードに存在する。*/
	public static final int LOCALREMOVEATOM = LOCAL + REMOVEATOM;

    /** newatom [-dstatom, srcmem, funcref]
     * <br>ボディ命令<br>
     * 膜$srcmemにファンクタfuncrefを持つ新しいアトム作成し、参照を$dstatomに代入する。
     * アトムはまだ実行スタックには積まれない。
     * @see enqueueatom */
    public static final int NEWATOM = 31;

	/** newatomindirect [-dstatom, srcmem, func]
	 * <br>型付き拡張用ボディ命令<br>
	 * 膜$srcmemにファンクタ$funcを持つ新しいアトム作成し、参照を$dstatomに代入する。
	 * アトムはまだ実行スタックには積まれない。
	 * @see newatom */
	public static final int NEWATOMINDIRECT = 32;    
    
    /** localnewatom [-dstatom, srcmem, funcref]
     * <br>最適化用ボディ命令<br>
     * newatomと同じ。ただし$srcmemはこの計算ノードに存在する。*/
	public static final int LOCALNEWATOM = LOCAL + NEWATOM;
	
	/** localnewatomindirect [-dstatom, srcmem, func]
	 * <br>型付き拡張用最適化用ボディ命令<br>
	 * newatomindirectと同じ。ただし$srcmemはこの計算ノードに存在する。*/
	public static final int LOCALNEWATOMINDIRECT = LOCAL + NEWATOMINDIRECT;

	/** enqueueatom [srcatom]
     * <br>ボディ命令<br>
     * アトム$srcatomを所属膜の実行スタックに積む。
     * <p>すでに実行スタックに積まれていた場合の動作は未定義とする。
     * <p>アクティブかどうかは関係ない。むしろこの命令で積まれるアトムがアクティブである。*/
    public static final int ENQUEUEATOM = 33;
    
	/** localenqueueatom [srcatom]
	 * <br>最適化用ボディ命令<br>
	 * enqueueatomと同じ。ただし$srcatomは<B>本膜と同じタスクが管理する膜に存在する</B>。*/
	public static final int LOCALENQUEUEATOM = LOCAL + ENQUEUEATOM;

    /** dequeueatom [srcatom]
     * <br>最適化用ボディ命令<br>
     * アトム$srcatomがこの計算ノードにある実行スタックに入っていれば、実行スタックから取り出す。
     * <p><b>注意</b>　この命令は、メモリ使用量のオーダを削減するために任意に使用することができる。
     * アトムを再利用するときは、因果関係に注意すること。
     * <p>なお、他の計算ノードにある実行スタックの内容を取得/変更する命令は存在しない。
     * <p>この命令は、Runtime.Atom.dequeueを呼び出す。*/
    public static final int DEQUEUEATOM = 34;
	// LOCALDEQUEUEATOMは最適化の効果が無いため却下

	/** freeatom [srcatom]
	 * <br>最適化用ボディ命令<br>
	 * 何もしない。
	 * <p>$srcatomがどの膜にも属さず、かつこの計算ノード内の実行スタックに積まれていないことを表す。
	 * TODO アトムを他の計算ノードで積んでいる場合、輸出表の整合性は大丈夫か調べる。*/
	public static final int FREEATOM = 35;
	// LOCALFREEATOMは不要

	/** alterfunc [atom, funcref]
	 * <br>最適化用ボディ命令<br>
	 * （所属膜を持つ）アトム$atomのファンクタをfuncrefにする。
	 * 引数の個数が異なる場合の動作は未定義とする。*/
	public static final int ALTERFUNC = 36;

	/** localalterfunc [atom, funcref]
	 * <br>最適化用ボディ命令<br>
	 * alterfuncと同じ。ただし$atomはこの計算ノードに存在する。*/
	public static final int LOCALALTERFUNC = LOCAL + ALTERFUNC;

	/** alterfuncindirect [atom, func]
	 * <br>最適化用ボディ命令<br>
	 * alterfuncと同じ。ただしファンクタは$funcにする。*/
	public static final int ALTERFUNCINDIRECT = 37;
	
	/** localalterfuncindirect [atom, func]
	 * <br>最適化用ボディ命令<br>
	 * alterfuncindirectと同じ。ただし$atomはこの計算ノードに存在する。*/
	public static final int LOCALALTERFUNCINDIRECT = LOCAL + ALTERFUNCINDIRECT;

	// アトムを操作する型付き拡張用命令 (40--49)
	//  ----- allocatom         [-dstatom, funcref]
	//  ----- allocatomindirect [-dstatom, func]
	// [local]copyatom          [-dstatom, mem, srcatom]
	//  local addatom                     [dstmem, atom]

	/** allocatom [-dstatom, funcref]
	 * <br>型付き拡張用命令<br>
	 * ファンクタfuncrefを持つ所属膜を持たない新しいアトム作成し、参照を$dstatomに代入する。
	 * <p>ガード検査で使われる定数アトムを生成するために使用される。*/
	public static final int ALLOCATOM = 40;
	// LOCALALLOCATOMは不要

	/** allocatomindirect [-dstatom, func]
	 * <br>型付き拡張用最適化用命令<br>
	 * ファンクタ$funcを持つ所属膜を持たない新しいアトムを作成し、参照を$dstatomに代入する。
	 * <p>ガード検査で使われる定数アトムを生成するために使用される。*/
	public static final int ALLOCATOMINDIRECT = 41;
	// LOCALALLOCATOMINDIRECTは不要

	/** copyatom [-dstatom, mem, srcatom]
	 * <br>型付き拡張用ボディ命令
	 * アトム$srcatomと同じ名前のアトムを膜$memに生成し、$dstatomに代入して返す。
	 * 実行スタックは操作しない。
	 * <p>マッチングで得た型付きアトムをコピーするために使用する。
	 * <p>getfunc[func,srcatom];newatomindirect[dstatom,mem,func]と同じ。よって廃止？
	 * copygroundtermに移行すべきかもしれない。*/
	public static final int COPYATOM = 42;

	/** localcopyatom [-dstatom, mem, srcatom]
	 * <br>最適化用ボディ命令<br>
	 * copyatomと同じ。ただし$memはこの計算ノードに存在する。*/
	public static final int LOCALCOPYATOM = LOCAL + COPYATOM;

	/** localaddatom [dstmem, atom]
	 * <br>最適化用ボディ命令<br>
	 * （所属膜を持たない）アトム$atomを膜$dstmemに所属させる。
	 * ただし$dstmemはこの計算ノードに存在する。*/
	public static final int LOCALADDATOM = LOCAL + 43;
	// 一般の ADDATOM は存在しない。
	
	// 膜を操作する基本ボディ命令 (50--59)    
	// [local]removemem                [srcmem, parentmem]
	// [local]newmem          [-dstmem, srcmem]
	//  ----- newroot         [-dstmem, srcmem, node]
	//  ----- movecells                [dstmem, srcmem]
	//  ----- enqueueallatoms          [srcmem]
	//  ----- freemem                  [srcmem]
	// [local]addmem                   [dstmem, srcmem]
	// [local]unlockmem                [srcmem]

	/** removemem [srcmem, parentmem]
	 * <br>ボディ命令<br>
	 * 膜$srcmemを親膜（$parentmem）から取り出す。
	 * 膜$srcmemはロック時に実行膜スタックから除去されているため、実行膜スタックは操作しない。
	 * @see removeproxies */
	public static final int REMOVEMEM = 50;

	/** localremovemem [srcmem, parentmem]
	 * <br>最適化用ボディ命令<br>
	 * removememと同じ。ただし$srcmemの親膜（$parentmem）はこの計算ノードに存在する。*/
	public static final int LOCALREMOVEMEM = LOCAL + REMOVEMEM;

	/** newmem [-dstmem, srcmem]
	 * <br>ボディ命令<br>
	 * （活性化された）膜$srcmemに新しい（ルート膜でない）子膜を作成し、$dstmemに代入し、活性化する。
	 * この場合の活性化は、$srcmemと同じ実行膜スタックに積むことを意味する。
	 * @see newroot
	 * @see addmem */
	public static final int NEWMEM = 51;

	/** localnewmem [-dstmem, srcmem]
	* <br>最適化用ボディ命令<br>
	* newmemと同じ。ただし$srcmemは<B>本膜と同じタスクによって管理される</B>。*/
	public static final int LOCALNEWMEM = LOCAL + NEWMEM;

	/** newroot [-dstmem, srcmem, node]
	 * <br>（予約された）ボディ命令<br>
	 * 膜$srcmemの子膜に文字列nodeで指定された計算ノードで実行される新しいロックされたルート膜を作成し、
	 * 参照を$dstmemに代入し、（ロックしたまま）活性化する。
	 * この場合の活性化は、仮の実行膜スタックに積むことを意味する。
	 * <p>newmemと違い、このルート膜のロックは明示的に解放しなければならない。
	 * @see unlockmem */
	public static final int NEWROOT = 52;
	// LOCALNEWROOTは最適化の効果が無いため却下
	
	/** movecells [dstmem, srcmem]
	 * <br>ボディ命令<br>
	 * 膜$srcmemにある全てのアトムと子膜（ロックを取得していない）を膜$dstmemに移動する。
	 * 実行膜スタックおよび実行スタックは操作しない。
	 * <p>実行後、膜$srcmemはこのまま廃棄されなければならない。
	 * <p>実行後、膜$dstmemの全てのアクティブアトムをエンキューし直すべきである。
	 * <p><b>注意</b>　Ruby版のpourから名称変更
	 * @see enqueueallatoms */
	public static final int MOVECELLS = 53;
	// LOCALMOVECELLSは最適化の効果が無いため却下？あるいはさらに特化した仕様にする。

	/** enqueueallatoms [srcmem]
	 * <br>（予約された）ボディ命令<br>
	 * 何もしない。または、膜$srcmemにある全てのアクティブアトムをこの膜の実行スタックに積む。
	 * <p>アトムがアクティブかどうかを判断するには、
	 * ファンクタを動的検査する方法と、2つのグループのアトムがあるとして所属膜が管理する方法がある。
	 * @see enqueueatom */
	public static final int ENQUEUEALLATOMS = 54;
	// LOCALENQUEUEALLATOMSは最適化の効果が無いため却下

	/** freemem [srcmem]
	 * <br>最適化用ボディ命令<br>
	 * 何もしない。
	 * <p>$srcmemがどの膜にも属さず、かつスタックに積まれていないことを表す。
	 * @see freeatom */
	public static final int FREEMEM = 55;
	// LOCALFREEMEMは不要

	/** addmem [dstmem, srcmem]
	 * <br>ボディ命令<br>
	 * ロックされた（親膜の無い）膜$srcmemを（活性化された）膜$dstmemに移動し、ロックしたまま活性化する。
	 * この場合の活性化は、$srcmemがルート膜の場合、仮の実行膜スタックに積むことを意味し、
	 * ルート膜でない場合、$dstmemと同じ実行膜スタックに積むことを意味する。
	 * <p>膜$srcmemを再利用するために使用される。
	 * <p>newmemと違い、$srcmemのロックは明示的に解放しなければならない。
	 * @see unlockmem */
	public static final int ADDMEM = 56;

	/** localaddmem [dstmem, srcmem]
	 * <br>最適化用ボディ命令<br>
	 * addmemと同じ。ただし$srcmemはこの計算ノードに存在する。$dstmemについては何も仮定しない。*/
	public static final int LOCALADDMEM = LOCAL + ADDMEM;

	/** unlockmem [srcmem]
	 * <br>ボディ命令<br>
	 * （活性化した）膜$srcmemのロックを解放する。
	 * $srcmemがルート膜の場合、仮の実行膜スタックの内容を実行膜スタックの底に転送する。
	 * <p>addmemによって再利用された膜、およびnewrootによってルールで新しく生成された
	 * ルート膜に対して、（子孫から順番に）必ず呼ばれる。
	 * <p>実行後、$srcmemへの参照は廃棄しなければならない。*/
	public static final int UNLOCKMEM = 57;

	/** localunlockmem [srcmem]
	 * <br>最適化用ボディ命令<br>
	 * unlockmemと同じ。ただし$srcmemはこの計算ノードに存在する。*/
	public static final int LOCALUNLOCKMEM = LOCAL + UNLOCKMEM;

	// 予約 (60--64)
	
	// リンクを操作するボディ命令 (65--69)
	// [local]newlink     [atom1, pos1, atom2, pos2, mem1]
	// [local]relink      [atom1, pos1, atom2, pos2, mem]
	// [local]unify       [atom1, pos1, atom2, pos2]
	// [local]inheritlink [atom1, pos1, link2, mem]

	/** newlink [atom1, pos1, atom2, pos2, mem1]
	 * <br>ボディ命令<br>
	 * アトム$atom1（膜$mem1にある）の第pos1引数と、
	 * アトム$atom2の第pos2引数の間に両方向リンクを張る。
	 * <p>典型的には、$atom1と$atom2はいずれもルールボディに存在する。
	 * <p><b>注意</b>　Ruby版の片方向から仕様変更された */
	public static final int NEWLINK = 65;

	/** localnewlink [atom1, pos1, atom2, pos2 (,mem1)]
	 * <br>最適化用ボディ命令<br>
	 * newlinkと同じ。ただし膜$mem1はこの計算ノードに存在する。*/
	public static final int LOCALNEWLINK = LOCAL + NEWLINK;

	/** relink [atom1, pos1, atom2, pos2, mem]
	 * <br>ボディ命令<br>
	 * アトム$atom1（膜$memにある）の第pos1引数と、
	 * アトム$atom2の第pos2引数のリンク先（膜$memにある）の引数を接続する。
	 * <p>典型的には、$atom1はルールボディに、$atom2はルールヘッドに存在する。
	 * <p>型付きプロセス文脈が無いルールでは、つねに$memが本膜なのでlocalrelinkが使用できる。
	 * <p>実行後、$atom2[pos2]の内容は無効になる。*/
	public static final int RELINK = 66;

	/** localrelink [atom1, pos1, atom2, pos2 (,mem)]
	 * <br>最適化用ボディ命令<br>
	 * relinkと同じ。ただし膜$memはこの計算ノードに存在する。*/
	public static final int LOCALRELINK = LOCAL + RELINK;

	/** unify [atom1, pos1, atom2, pos2, mem]
	 * <br>ボディ命令<br>
	 * アトム$atom1の第pos1引数のリンク先（膜$memにある）の引数と、
	 * アトム$atom2の第pos2引数のリンク先（膜$memにある）の引数を接続する。
	 * <p>典型的には、$atom1と$atom2はいずれもルールヘッドに存在する。
	 * <p>型付きプロセス文脈が無いルールでは、つねに$memが本膜なのでlocalunifyが使用できる。*/
	public static final int UNIFY = 67;

	/** localunify [atom1, pos1, atom2, pos2 (,mem)]
	 * <br>最適化用ボディ命令<br>
	 * unifyと同じ。ただし膜$memはこの計算ノードに存在する。*/
	public static final int LOCALUNIFY = LOCAL + UNIFY;

	/** inheritlink [atom1, pos1, link2, mem]
	 * <br>最適化用ボディ命令<br>
	 * アトム$atom1（膜$memにある）の第pos1引数と、
	 * リンク$link2のリンク先（膜$memにある）を接続する。
	 * <p>典型的には、$atom1はルールボディに存在し、$link2はルールヘッドに存在する。relinkの代用。
	 * <p>型付きプロセス文脈が無いルールでは、つねに$memが本膜なのでinheritrelinkが使用できる。
	 * <p>$link2は再利用されるため、実行後は$link2は廃棄しなければならない。
	 * @see getlink */
	public static final int INHERITLINK = 68;

	/** localinheritlink [atom1, pos1, link2 (,mem)]
	 * <br>最適化用ボディ命令<br>
	 * inheritlinkと同じ。ただし膜$memはこの計算ノードに存在する。*/
	public static final int LOCALINHERITLINK = LOCAL + INHERITLINK;

    // 自由リンク管理アトム自動処理のためのボディ命令 (70--74)
	//  -----  removeproxies          [srcmem]
	//  -----  removetoplevelproxies  [srcmem]
	//  -----  insertproxies          [parentmem,childmem]
	//  -----  removetemporaryproxies [srcmem]

    /** removeproxies [srcmem]
     * <br>ボディ命令<br>
     * $srcmemを通る無関係な自由リンク管理アトムを自動削除する。
     * <p>removememの直後に同じ膜に対して呼ばれる。*/
    public static final int REMOVEPROXIES = 70;
    // LOCALREMOVEPROXIESは最適化の効果が無いため却下

    /** removetoplevelproxies [srcmem]
     * <br>ボディ命令<br>
     * 膜$srcmem（本膜）を通過している無関係な自由リンク管理アトムを除去する。
	 * <p>removeproxiesが全て終わった後で呼ばれる。*/
    public static final int REMOVETOPLEVELPROXIES = 71;
	// LOCALREMOVETOPLEVELPROXIESは最適化の効果が無いため却下

    /** insertproxies [parentmem,childmem]
     * <br>ボディ命令<br>
     * 指定された膜間に自由リンク管理アトムを自動挿入する。
     * <p>addmemが全て終わった後で呼ばれる。*/
    public static final int INSERTPROXIES = 72;
	// LOCALINSERTPROXIESは最適化の効果が無いため却下
	
    /** removetemporaryproxies [srcmem]
     * <br>ボディ命令<br>
     * 膜$srcmem（本膜）に残された"star"アトムを除去する。
     * <p>insertproxiesが全て終わった後で呼ばれる。*/
    public static final int REMOVETEMPORARYPROXIES = 73;
	// LOCALREMOVETEMPORARYPROXIESは最適化の効果が無いため却下

	// ルールを操作するボディ命令 (75--79)
	// [local]loadruleset [dstmem, ruleset]
	// [local]copyrules   [dstmem, srcmem]
	// [local]clearrules  [dstmem]

	/** loadruleset [dstmem, ruleset]
	 * <br>ボディ命令<br>
	 * ルールセットrulesetを膜$dstmemにコピーする。
	 * <p>この膜のアクティブアトムは再エンキューすべきである。
	 * @see enqueueallatoms */
	public static final int LOADRULESET = 75;

	/** localloadruleset [dstmem, ruleset]
	 * <br>最適化用ボディ命令<br>
	 * loadrulesetと同じ。ただし$dstmemはこの計算ノードに存在する。*/
	public static final int LOCALLOADRULESET = LOCAL + LOADRULESET;

	/** copyrules [dstmem, srcmem]
	 * <br>ボディ命令<br>
	 * 膜$srcmemにある全てのルールを膜$dstmemにコピーする。
	 * <p><b>注意</b>　Ruby版のinheritrulesから名称変更 */
	public static final int COPYRULES = 76;

	/** localcopyrules [dstmem, srcmem]
	 * <br>最適化用ボディ命令<br>
	 * copyrulesと同じ。ただし$dstmemはこの計算ノードに存在する。$srcmemについては何も仮定しない。*/
	public static final int LOCALCOPYRULES = LOCAL + COPYRULES;

	/** clearrules [dstmem]
	 * <br>ボディ命令<br>
	 * 膜$dstmemにある全てのルールを消去する。*/
	public static final int CLEARRULES = 77;
	
	/** localclearrules [dstmem]
	 * <br>最適化用ボディ命令<br>
	 * clearrulesと同じ。ただし$dstmemはこの計算ノードに存在する。*/
	public static final int LOCALCLEARRULES = LOCAL + CLEARRULES;

	/** loadmodule [dstmem, ruleset]
	 * <br>ボディ命令<br>
	 * ルールセットrulesetを膜$dstmemにコピーする。
	 */
	public static final int LOADMODULE = 78;

    // 型付きでないプロセス文脈をコピーまたは廃棄するための命令 (80--89)
	//  ----- recursivelock            [srcmem]
	//  ----- recursiveunlock          [srcmem]
	//  ----- copymem         [-dstmem, srcmem]
	//  ----- dropmem                  [srcmem]
	
    /** recursivelock [srcmem]
     * <br>（予約された）ガード命令<br>
     * 膜$srcmemの全ての子膜に対して再帰的にロックを取得する。
     * <p>右辺での出現が1回でないプロセス文脈が書かれた左辺の膜に対して使用される。
     * <p><font color=red><b>
     * デッドロックが起こらないことを保証できれば、この命令はブロッキングで行うべきである。
     * </b></font>*/
    public static final int RECURSIVELOCK = 80;
	// LOCALRECURSIVELOCKは最適化の効果が無いため却下

    /** recursiveunlock [srcmem]
     * <br>（予約された）ボディ命令<br>
     * 膜$srcmemの全ての子膜に対して再帰的にロックを解放する。
     * 膜はそれを管理するタスクの実行膜スタックに再帰的に積まれる。
     * <p>再帰的に積む方法は、今後考える。
     * @see unlockmem */
    public static final int RECURSIVEUNLOCK = 81;
	// LOCALRECURSIVEUNLOCKは最適化の効果が無いため却下

    /** copymem [-dstmem, srcmem]
     * <br>（予約された）ボディ命令<br>
     * 再帰的にロックされた膜$srcmemの内容のコピーを作成し、膜$dstmemに入れる。
     * $dstmemの自由リンク管理アトムの第1引数は、対応する$srcmemの第1引数を指すリンクで初期化するが、
     * 他の計算ノードには初期化を通知しない。*/
    public static final int COPYMEM = 82;
	// LOCALCOPYMEMは最適化の効果が無いため却下

	/** dropmem [srcmem]
	 * <br>（予約された）ボディ命令<br>
	 * 再帰的にロックされた膜$srcmemを破棄する。
	 * この膜や子孫の膜をルート膜とするタスクは強制終了する。*/
	public static final int DROPMEM = 83;
	// LOCALDROPMEMは最適化の効果が無いため却下

//	/** * [srcatom,pos]
//	 * <br>（予約された）ボディ命令<br>
//	 * アトムsrcatomの第1引数のリンク先のリンク先のアトムの第pos引数と、
//	 * srcatomの第1引数の間に双方向リンクを貼る。自由リンク管理アトムをどうするか。
//	 * <p>プロセス文脈のコピー時に使用される。*/
//	public static final int * = err;

	// アトム集団のコンパイルには、findatomとrunを使うといいかもしれないが、できないかもしれない。

	// TODO プロセス文脈の明示的な引数列は、膜に一時的なアトムを作って管理する。
	// コピーした後でrelinkする。

	// 予約 (90--99)

	//////////////////////////////////////////////////////////////////
	
	// 200番以降の命令にはLOCAL修飾版は存在しない
	
	// 制御命令 (200--209)
	//  -----  react       [ruleref, [memargs...], [atomargs...]]
	//  -----  inlinereact [ruleref, [memargs...], [atomargs...]]
	//  -----  resetvars   [[memargs...], [atomargs...], [varargs...]]
	//  -----  spec        [formals,locals]
	//  -----  proceed
	//  -----  stop 
	//  -----  branch      [[instructions...]]
	//  -----  loop        [[instructions...]]
	//  -----  run         [[instructions...]]
	//  -----  not         [[instructions...]]

    /** react [ruleref, [memargs...], [atomargs...]]
     * <br>失敗しないガード命令<br>
     * ルールrulerefに対するマッチングが成功したことを表す。
     * 処理系はこのルールのボディを呼び出さなければならない。*/
    public static final int REACT = 200;

	/** inlinereact [ruleref, [memargs...], [atomargs...]]
	 * <br>無視される<br>
     * ルールrulerefに対するマッチングが成功したことを表す。
     * <p>トレース用。*/
	public static final int INLINEREACT = 201;

	/** reloadvars [[memargs...], [atomargs...], [varargs...]]
	 * <br>失敗しないガード命令および最適化用ボディ命令<br>
	 * 変数ベクタの内容を再定義する。新しい変数番号は、膜、アトム、その他の順番で0から振り直される。
	 * <b>注意</b>　memargs[0]は本膜が予約しているため変更してはならない。
	 * <p>（仕様検討中の未使用命令）
	 */
	public static final int RELOADVARS = 202;

    /** spec [formals, locals]
     * <br><strike>
     * spec [memformals,atomformals,memlocals,atomlocals,linklocals,funclocals]</strike>
     * <br>無視される<br>
     * 仮引数と局所変数の個数を宣言する。*/
    public static final int SPEC = 203;

	/** proceed
	 * <br>ボディ命令<br>
	 * このproceed命令が所属する命令列の実行が成功したことを表す。
	 * <p>トップレベル命令列で使用された場合、ルールの適用が終わり、
	 * 再利用された全ての膜のロックを解放し、生成した全ての膜を活性化したことを表す。
	 * <p><b>注意</b>　proceedなしで命令列の終端まで進んだ場合、
	 * その命令列の実行は失敗したものとする仕様が採用された。*/
	public static final int PROCEED = 204;

	/** stop 
	 * <br>（予約された）失敗しないガード命令<br>
	 * proceedと同じ。マッチングとボディの命令が統合されたことに伴って廃止される予定。
	 * <p>典型的には、否定条件にマッチしたことを表すために使用される。
	 */
	public static final int STOP = 205;

    /** branch [[instructions...]]
     * <br>構造化命令<br>
     * 引数の命令列を実行することを表す。
     * 引数実行中に失敗した場合、引数実行中に取得したロックを解放し、次の命令に進む。
     * 引数実行中にproceed命令を実行した場合、ここで終了する。*/
    public static final int BRANCH = 206;

	/** loop [[instructions...]]
	 * <br>構造化命令<br>
	 * 引数の命令列を実行することを表す。
     * 引数実行中に失敗した場合、引数実行中に取得したロックを解放し、次の命令に進む。
     * 引数実行中にproceed命令を実行した場合、このloop命令の実行を繰り返す。*/
	public static final int LOOP = 207;

	/** run [[instructions...]]
	 * <br>（予約された）構造化命令<br>
	 * 引数の命令列を実行することを表す。引数列はロックを取得してはならない。
	 * 引数実行中に失敗した場合、次の命令に進む。
	 * 引数実行中にproceed命令を実行した場合、次の命令に進む。
	 * <p>将来、明示的な引数付きのプロセス文脈のコンパイルに使用するために予約。*/
	public static final int RUN = 208;

	/** not [[instructions...]]
	 * <br>（予約された）構造化命令<br>
	 * 引数の命令列を実行することを表す。引数列はロックを取得してはならない。
	 * 引数実行中に失敗した場合、次の命令に進む。
	 * 引数実行中にproceed命令を実行した場合、この命令が失敗する。
	 * <p>将来、否定条件のコンパイルに使用するために予約。*/
	public static final int NOT = 209;

	// 組み込み機能に関する命令（仮） (210--219)
	//  -----  inline  [atom, inlineref]
	//  -----  builtin [class, method, [links...]]

	/** inline [atom, inlineref]
	 * <br>ガード命令、ボディ命令<br>
	 * アトム$atomに対して、inlinerefが指定するインライン命令を適用し、成功することを確認する。
	 * <p>inlinerefには現在、インライン番号を渡すことになっているが、
	 * 処理系はinlinerefを無視して$atomのファンクタからインライン命令を決定してよい。
	 * 仕様は将来変更されるかもしれない。
	 * <p>ボディで呼ばれる場合典型的には、全てのリンクを張りなおした直後に呼ばれる。*/
	public static final int INLINE = 210;

	/** builtin [class, method, [links...]]
	 * <br>（予約された）ボディ命令
	 * リンク参照$(links[i])の配列を唯一の引数としてJavaのクラスメソッドを呼び出す。
	 * <p>インタプリタ動作するときに組み込み機能を提供するために使用する。
	 * <p>通常は、$builtin(class,method):(X1,…,Xn)に対応し、引数の種類によって次のものが渡される。
	 * 型付きプロセス文脈は、膜から除去したヘッド出現（またはガード生成したもの）が渡される。
	 * ヘッドとボディに1回ずつ出現するリンクは、ヘッドでのリンク出現が渡される。
	 * ボディに2回出現するリンクは、X=Xで初期化された後、各出現をヘッドでの出現と見なして渡される。*/
	public static final int BUILTIN = 211;

	///////////////////////////////////////////////////////////////////////

	// 型付きプロセス文脈を扱うための追加命令 (216--219)	

	/** eqground [groundlink1,groundlink2]
	 * <br>（予約された）型付き拡張用ガード命令<br>
	 * 基底項プロセスを指す2つのリンクlink1とlink2に対して、
	 * それらが同じ形状の基底項プロセスであることを確認する。
	 * @see isground */
	public static final int EQGROUND = 216;
    	
	// 型検査のためのガード命令 (220--229)	

	/** isground [link]
	 * <br>（予約された）型付き拡張用ガード命令<br>
	 * リンク$linkの指す先が基底項プロセスであることを確認する。
	 * すなわち、リンク先から（戻らずに）到達可能なアトムが全てこの膜に存在していることを確認する。
	 * @see getlink */
	public static final int ISGROUND = 220;
	
	/** isunary [atom]
	 * <br>型付き拡張用ガード命令<br>
	 * アトム$atomが1引数のアトムであることを確認する。*/
	public static final int ISUNARY     = 221;
	public static final int ISUNARYFUNC = ISUNARY + OPT;

	/** isint [atom]
	 * <br>型付き拡張用ガード命令<br>
	 * アトム$atomが整数アトムであることを確認する。*/
	public static final int ISINT    = 225;
	public static final int ISFLOAT  = 226;
	public static final int ISSTRING = 227;

	/** isintfunc [func]
	 * <br>型付き拡張用最適化用ガード命令<br>
	 * ファンクタ$funcが整数ファンクタであることを確認する。*/
	public static final int ISINTFUNC    = ISINT    + OPT;
	public static final int ISFLOATFUNC  = ISFLOAT  + OPT;
	public static final int ISSTRINGFUNC = ISSTRING + OPT;

	// 整数用の組み込みボディ命令 (400--419+OPT)
	/** iadd [-dstintatom, intatom1, intatom2]
	 * <br>整数用の組み込み命令<br>
	 * 整数アトムの加算結果を表す所属膜を持たない整数アトムを生成し、$dstintatomに代入する。*/
	public static final int IADD = 400;
	public static final int ISUB = 401;
	public static final int IMUL = 402;
	public static final int IDIV = 403;
	public static final int INEG = 404;
	public static final int IMOD = 405;
	public static final int INOT = 410;
	public static final int IAND = 411;
	public static final int IOR  = 412;
	public static final int IXOR = 413;
	/** iadd [-dstintatom, intatom1, intatom2]
	 * <br>整数用の組み込み命令<br>
	 * intatom1をintatom2ビット分符号つき(算術)左シフトした結果を表す所属膜を持たない整数アトムを生成し、$dstintatomに代入する。*/
	public static final int ISAL = 414;
	/** iadd [-dstintatom, intatom1, intatom2]
	 * <br>整数用の組み込み命令<br>
	 * intatom1をintatom2ビット分符号つき(算術)右シフトした結果を表す所属膜を持たない整数アトムを生成し、$dstintatomに代入する。*/
	public static final int ISAR = 415;
	/** iadd [-dstintatom, intatom1, intatom2]
	 * <br>整数用の組み込み命令<br>
	 * intatom1をintatom2ビット分論理右シフトした結果を表す所属膜を持たない整数アトムを生成し、$dstintatomに代入する。*/
	public static final int ISHR = 416;

	/** iaddfunc [-dstintfunc, intfunc1, intfunc2]
	 * <br>整数用の最適化用組み込み命令<br>
	 * 整数ファンクタの加算結果を表す整数ファンクタを生成し、$dstintfuncに代入する。*/
	public static final int IADDFUNC = IADD + OPT;
	public static final int ISUBFUNC = ISUB + OPT;
	public static final int IMULFUNC = IMUL + OPT;
	public static final int IDIVFUNC = IDIV + OPT;
	public static final int INEGFUNC = INEG + OPT;
	public static final int IMODFUNC = IMOD + OPT;
	public static final int INOTFUNC = INOT + OPT;
	public static final int IANDFUNC = IAND + OPT;
	public static final int IORFUNC  = IOR  + OPT;
	public static final int IXORFUNC = IXOR + OPT;
	public static final int ISALFUNC = ISAL + OPT;
	public static final int ISARFUNC = ISAR + OPT;
	public static final int ISHRFUNC = ISHR + OPT;

	// 整数用の組み込みガード命令 (420--429+OPT)

	/** ilt [intatom1, intatom2]
	 * <br>整数用の組み込みガード命令<br>
	 * 整数アトムの値の大小比較が成り立つことを確認する。*/
	public static final int ILT = 420;
	public static final int ILE = 421;
	public static final int IGT = 422;
	public static final int IGE = 423;	

	/** iltfunc [intfunc1, intfunc2]
	 * <br>整数用の最適化用組み込みガード命令<br>
	 * 整数ファンクタの値の大小比較が成り立つことを確認する。*/
	public static final int ILTFUNC = ILT + OPT;
	public static final int ILEFUNC = ILE + OPT;
	public static final int IGTFUNC = IGT + OPT;
	public static final int IGEFUNC = IGE + OPT;


	// 浮動小数点数用の組み込みボディ命令 (600--619+OPT)
	public static final int FADD = 600;
	public static final int FSUB = 601;
	public static final int FMUL = 602;
	public static final int FDIV = 603;
	public static final int FNEG = 604;
	public static final int FMOD = 605;
	public static final int FNOT = 610;
	public static final int FAND = 611;
	public static final int FOR  = 612;
	public static final int FXOR = 613;
	public static final int FSAL = 614;
	public static final int FSAR = 615;
	public static final int FSHR = 616;
	
	public static final int FADDFUNC = FADD + OPT;
	public static final int FSUBFUNC = FSUB + OPT;
	public static final int FMULFUNC = FMUL + OPT;
	public static final int FDIVFUNC = FDIV + OPT;
	public static final int FNEGFUNC = FNEG + OPT;
	public static final int FMODFUNC = FMOD + OPT;
	public static final int FNOTFUNC = FNOT + OPT;
	public static final int FANDFUNC = FAND + OPT;
	public static final int FORFUNC  = FOR  + OPT;
	public static final int FXORFUNC = FXOR + OPT;
	public static final int FSALFUNC = FSAL + OPT;
	public static final int FSARFUNC = FSAR + OPT;
	public static final int FSHRFUNC = FSHR + OPT;
	
	// 浮動小数点数用の組み込みガード命令 (620--629+OPT)
	public static final int FLT = 620;
	public static final int FLE = 621;
	public static final int FGT = 622;
	public static final int FGE = 623;	

	public static final int FLTFUNC = FLT + OPT;
	public static final int FLEFUNC = FLE + OPT;
	public static final int FGTFUNC = FGT + OPT;
	public static final int FGEFUNC = FGE + OPT;

	////////////////////////////////////////////////////////////////

    /** 命令の種類を取得する。*/
	public int getKind() {
		return kind;
	}
	/**@deprecated*/
	public int getID() {
		return getKind();
	}
	public int getIntArg(int pos) {
		return ((Integer)data.get(pos)).intValue();
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
	public int getIntArg5() {
		return ((Integer)data.get(4)).intValue();
	}
	public Object getArg(int pos) {
		return data.get(pos);
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
	public Object getArg5() {
		return data.get(4);
	}
	/**@deprecated*/
	public void setArg(int pos, Object arg) {
		data.set(pos,arg);
	}
	/**@deprecated*/
	public void setArg1(Object arg) {
		data.set(0,arg);
	}
	/**@deprecated*/
	public void setArg2(Object arg) {
		data.set(1,arg);
	}
	/**@deprecated*/
	public void setArg3(Object arg) {
		data.set(2,arg);
	}
	/**@deprecated*/
	public void setArg4(Object arg) {
		data.set(3,arg);
	}
	/**@deprecated*/
	public void setArg5(Object arg) {
		data.set(4,arg);
	}

    ////////////////////////////////////////////////////////////////

    /**
     * 引数を追加するマクロ。
     * @param o オブジェクト型の引数
     */
	public final void add(Object o) { data.add(o); }
	
    /**
     * 引数を追加するマクロ。
     * @param n int 型の引数
     */
    public final void add(int n) { data.add(new Integer(n)); }
	
	////////////////////////////////////////////////////////////////

    /**
     * ダミー命令を生成する。
     * さしあたって生成メソッドがまだできてない命令はこれを使う
     * @param s 説明用の文字列
     * @deprecated
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
     * @deprecated
     */
    public static Instruction react(Rule r, List actual) {
		Instruction i = new Instruction(REACT);
		i.add(r);
		i.add(actual);
		return i;
    }
    /**
     * react 命令を生成する。
     * @param r 反応できるルールオブジェクト
     * @param memactuals 膜実引数のリスト。膜の変数番号からなる。
     * @param atomactuals アトム実引数のリスト。アトムの変数番号からなる。
     */
    public static Instruction react(Rule r, List memactuals, List atomactuals) {
		Instruction i = new Instruction(REACT);
		i.add(r);
		i.add(memactuals);
		i.add(atomactuals);
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
	/** newlink 命令を生成する
	 * @deprecated */
	public static Instruction newlink(int atom1, int pos1, int atom2, int pos2) {
		return new Instruction(NEWLINK,atom1,pos1,atom2,pos2);
	}
	/** newlink 命令を生成する */
	public static Instruction newlink(int atom1, int pos1, int atom2, int pos2, int mem1) {
		return new Instruction(NEWLINK,atom1,pos1,atom2,pos2,mem1);
	}
    /** loadruleset 命令を生成する */
    public static Instruction loadruleset(int mem, Ruleset rs) {
		return new Instruction(LOADRULESET,mem,rs);
    }
    /** getmem 命令を生成する */
    public static Instruction getmem(int ret, int atom) {
		return new Instruction(GETMEM,ret,atom);
    }	
    /** removeatom 命令を生成する @deprecated*/
	public static Instruction removeatom(int atom) {
		return new Instruction(REMOVEATOM,atom);
	}
	/** removeatom 命令を生成する*/
	public static Instruction removeatom(int atom, int mem, Functor func) {
		return new Instruction(REMOVEATOM,atom,mem,func);
	}
	/** dequeueatom 命令を生成する*/
	public static Instruction dequeueatom(int atom) {
		return new Instruction(DEQUEUEATOM,atom);
	}
    
	// コンストラクタ
	
    /** 無名命令を作る。*/
    public Instruction() {
    }
	
    /**
     * 指定された命令をつくる。いずれprivateにするといいのかも知れない。
     * @param kind
     */
    public Instruction(int kind) {
    	this.kind = kind;
    }
	public Instruction(int kind, int arg1) {
		this.kind = kind;
		add(arg1);
	}
	public Instruction(int kind, Object arg1) {
		this.kind = kind;
		add(arg1);
	}
	public Instruction(int kind, int arg1, int arg2) {
		this.kind = kind;
		add(arg1);
		add(arg2);
	}
	public Instruction(int kind, int arg1, Object arg2) {
		this.kind = kind;
		add(arg1);
		add(arg2);
	}
	public Instruction(int kind, Object arg1, int arg2) {
		this.kind = kind;
		add(arg1);
		add(arg2);
	}
	public Instruction(int kind, int arg1, int arg2, int arg3) {
		this.kind = kind;
		add(arg1);
		add(arg2);
		add(arg3);
	}
	public Instruction(int kind, int arg1, int arg2, Object arg3) {
		this.kind = kind;
		add(arg1);
		add(arg2);
		add(arg3);
	}
	public Instruction(int kind, int arg1, int arg2, int arg3, int arg4) {
		this.kind = kind;
		add(arg1);
		add(arg2);
		add(arg3);
		add(arg4);
	}
	public Instruction(int kind, int arg1, int arg2, int arg3, int arg4, int arg5) {
		this.kind = kind;
		add(arg1);
		add(arg2);
		add(arg3);
		add(arg4);
		add(arg5);
	}

	public Object clone() {
		Instruction c = new Instruction();
		c.kind = this.kind;
		Iterator it = this.data.iterator();
		while (it.hasNext()) {
			c.data.add(it.next());
		}
		return c;
	}
	
	//////////////////////////////////
	// 最適化器が使う、命令列書き換えのためのクラスメソッド
	// @author Mizuno
	

	/**
	 * 与えられた対応表によって、ボディ命令列中の変数を書き換える。<br>
	 * 命令列中の変数が、対応表のキーに出現する場合、対応する値に書き換えます。
	 * 
	 * @param list 書き換える命令列
	 * @param map 変数の対応表。
	 */
	public static void changeVar(List list, Map map) {
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Instruction inst = (Instruction)it.next();
			switch (inst.getKind()) { // TODO ガード命令や、出力変数も書き換える
				case Instruction.DEREF:
					changeArg(inst, 1, map);
					changeArg(inst, 2, map);
					break;
				case Instruction.FUNC:
					changeArg(inst, 1, map);
					break;
				case Instruction.NEWATOM:
				case Instruction.LOCALNEWATOM:
				case Instruction.NEWATOMINDIRECT:
				case Instruction.LOCALNEWATOMINDIRECT:
					changeArg(inst, 1, map);
					changeArg(inst, 2, map);
					break;
				case Instruction.NEWLINK:
				case Instruction.LOCALNEWLINK:
					changeArg(inst, 1, map);
					changeArg(inst, 3, map);
//					changeArg(inst, 5, map);
					break;
				case Instruction.GETLINK:
					changeArg(inst, 1, map);
					changeArg(inst, 2, map);
					break;
				case Instruction.INHERITLINK:
				case Instruction.LOCALINHERITLINK:
					changeArg(inst, 1, map);
					changeArg(inst, 3, map);
					changeArg(inst, 4, map);
					break;
				case Instruction.ENQUEUEATOM:
					changeArg(inst, 1, map);
					break;
				case Instruction.DEQUEUEATOM:
					changeArg(inst, 1, map);
					break;
				case Instruction.REMOVEATOM:
				case Instruction.LOCALREMOVEATOM:
					changeArg(inst, 2, map);
					break;
				case Instruction.COPYATOM:
				case Instruction.LOCALCOPYATOM:
					changeArg(inst, 2, map);
					break;
				case Instruction.LOCALADDATOM:
					changeArg(inst, 1, map);
					break;
				case Instruction.REMOVEMEM:
					changeArg(inst, 1, map);
					changeArg(inst, 2, map);
					break;
				case Instruction.NEWMEM:
					changeArg(inst, 2, map);
					break;
				case Instruction.NEWROOT:
					changeArg(inst, 2, map);
					break;
				case Instruction.MOVECELLS:
					changeArg(inst, 1, map);
					changeArg(inst, 2, map);
					break;
				case Instruction.ENQUEUEALLATOMS:
					changeArg(inst, 1, map);
					break;
				case Instruction.FREEMEM:
					changeArg(inst, 1, map);
					break;
				case Instruction.ADDMEM:
				case Instruction.LOCALADDMEM:
					changeArg(inst, 1, map);
					changeArg(inst, 2, map);
					break;
				case Instruction.UNLOCKMEM:
				case Instruction.LOCALUNLOCKMEM:
					changeArg(inst, 1, map);
					break;
				case Instruction.REMOVEPROXIES:
				case Instruction.REMOVETOPLEVELPROXIES:
				case Instruction.REMOVETEMPORARYPROXIES:
					changeArg(inst, 1, map);
					break;
				case Instruction.INSERTPROXIES:
					changeArg(inst, 1, map);
					changeArg(inst, 2, map);
					break;
				case Instruction.LOADRULESET:
				case Instruction.LOCALLOADRULESET:
					changeArg(inst, 1, map);
					break;
				case Instruction.COPYRULES:
				case Instruction.LOCALCOPYRULES:
					changeArg(inst, 1, map);
					changeArg(inst, 2, map);
					break;
				case Instruction.CLEARRULES:
				case Instruction.LOCALCLEARRULES:
					changeArg(inst, 1, map);
					break;
				case Instruction.RECURSIVELOCK:
					changeArg(inst, 1, map);
					break;
				case Instruction.RECURSIVEUNLOCK:
					changeArg(inst, 1, map);
					break;
				case Instruction.COPYMEM:
					changeArg(inst, 1, map);
					changeArg(inst, 2, map);
					break;
				case Instruction.DROPMEM:
					changeArg(inst, 1, map);
					break;
			//
				case RELINK:
				case LOCALRELINK:
					changeArg(inst, 5, map);
					break;
			}
		}
	}
	/**
	 * 対応表によって引数を書き換える。
	 * @param inst 書き換える命令
	 * @param pos 書き換える引数番号
	 * @param map 書き換えマップ
	 */
	private static void changeArg(Instruction inst, int pos, Map map) {
		Integer id = (Integer)inst.data.get(pos - 1);
		if (map.containsKey(id)) {
			inst.data.set(pos - 1, map.get(id));
		}
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
	//ExceptionInInitializerError がおきてたので修正 by hara
    static {
		try {
			Instruction inst = new Instruction();
			Field[] fields = inst.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				// 追加。hara
				if(! f.getType().isPrimitive()) continue;
				int kind = f.getInt(inst);
				if (kind != LOCAL
				 && f.getType().getName().equals("int") && Modifier.isStatic(f.getModifiers())) {
					Integer idobj = new Integer(kind);
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
		//nakajima版2004-01-21
		StringBuffer buffer = new StringBuffer("               ");
		String tmp = getInstructionString(kind);
		
		if( tmp.length() > 14 ) {
			buffer.replace(0, 14, tmp.substring(0,14));
		} else {
			buffer.replace(0, tmp.length(), tmp);
		}

		if(tmp.equals("loop") && data.size() == 1 && data.get(0) instanceof ArrayList && ((ArrayList)data.get(0)).size() == 1) {
			ArrayList list = (ArrayList)((ArrayList)data.get(0)).get(0);
			if(list.size() == 0) {
				buffer.append("[[[]]]\n");
			} else {
				buffer.append("[[[\n");
				int i;
				for(i = 0; i < list.size()-1; i++){
					buffer.append("  ");
					buffer.append(list.get(i));
					buffer.append(", \n");
				}
				buffer.append("  ");
				buffer.append(list.get(i));
				buffer.append("]]]\n");
			}
		} else {
			buffer.append(data.toString());
		}

		//各要素を分解
		//instanceOf
		//ArrayListであって、空でなくて、かつcastして先頭要素を手に入れたのがinstanceOfでIntegerでないとき、
		//引数列は命令列なのでインデント+2ぐらいで再帰的に。
//		for (int i =0; i < data.size(); i++){
//			ArrayList hoge = (ArrayList)data.get(i);
//
//			if(!hoge.isEmpty()){
//				if(!(hoge.get(0) instanceof Integer)){
//					buffer.append("\n  ");
//					buffer.append((String)hoge.get(0));
//				} else {
//					buffer.append(hoge.toString());
//					continue;
//				}
//			} else {
//				buffer.append(hoge.toString());			
//			}
//		}
		
		return buffer.toString();

	//n-kato版2004-01-21まで使ってました
	//return getInstructionString(kind)+"\t"+data.toString();

	//古い（2003年のいつか）nakajima版のコード
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

//以下、廃止された命令の墓場

//* lock [srcmem]
//* <br>（廃止された）ガード命令<br>
//* 膜$srcmemに対するノンブロッキングでのロック取得を試みる。
//* ロック取得に成功すれば、この膜はまだ参照を（＝ロックを）取得していなかった膜である
//* <p>srcmemにmemof記法が廃止されたため、ロックはlockmemで行う。したがってlockは廃止された。*/
//public static final int LOCK = err;
