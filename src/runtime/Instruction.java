/*
 * 作成日: 2003/10/21
 *
 */
package runtime;

import java.util.*;
import java.lang.reflect.Modifier;
import java.lang.reflect.Field;
import java.io.*;

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
 * <li>活性化する膜がリモート膜や他のタスクの膜の場合、一時的な「仮の」実行膜スタックを作り、
 * そこに親膜側から積んでいく。
 * リモートのルート膜のロックが解放されると、実行膜スタックの先頭に丸ごと移動される。
 * これによって実行膜スタックに全ての膜がアトミックに積まれることになる。
 * </ul>
 * コンパイラは次のコードを出力する：addmemやnewrootした膜は、ルール実行終了時に（子膜側から順番に）unlockmemを実行する。
 */
 
/**
 * 1 つの命令を保持する。通常は、InstructionのArrayListとして保持する。
 * 
 * デバッグ用表示メソッドを備える。
 *
 * @author hara, nakajima, n-kato
 */
public class Instruction implements Cloneable, Serializable {
	/** 命令毎の引数情報を入れるテーブル */
	private static HashMap argTypeTable = new HashMap();
	/**アトム*/
	public static final int ARG_ATOM = 0;
	/**膜*/
	public static final int ARG_MEM = 1;
	/**アトム・膜以外の変数*/
	public static final int ARG_VAR = 2;
	/**整数*/
	public static final int ARG_INT = 3;
	/**命令列*/
	public static final int ARG_INSTS = 4;
	/**ラベル付き命令列*/
	public static final int ARG_LABEL = 5;
	/**変数番号のList*/
	public static final int ARG_VARS = 6;
	/**その他オブジェクト(ルールなど)への参照*/
	public static final int ARG_OBJ = 7;
	
	
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
    /** 型付きアトムに対する命令がアトムではなく、ファンクタを対象にしていることを表す修飾子 */
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
	//  -----  dereflink [-dstatom, srclink, dstpos]
	//  -----  findatom  [-dstatom, srcmem, funcref]

    /** deref [-dstatom, srcatom, srcpos, dstpos]
     * <br><strong><font color="#ff0000">出力するガード命令</font></strong><br>
     * アトム$srcatomの第srcpos引数のリンク先が第dstpos引数に接続していることを確認したら、
     * リンク先のアトムへの参照を$dstatomに代入する。*/
	public static final int DEREF = 1;
	// LOCALDEREFは不要
	static {setArgType(DEREF, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_INT, ARG_INT));}

	/** derefatom [-dstatom, srcatom, srcpos]
	 * <br>出力する失敗しない最適化用＋型付き拡張用ガード命令<br>
	 * アトム$srcatomの第srcpos引数のリンク先のアトムへの参照を$dstatomに代入する。
	 * <p>引き続き$dstatomが、単項アトム（整数なども含む）や自由リンク管理アトムと
	 * マッチするかどうか検査する場合に使用することができる。*/
	public static final int DEREFATOM = 2;
	// LOCALDEREFATOMは不要
	static {setArgType(DEREFATOM, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_INT));}

    /** dereflink [-dstatom, srclink, dstpos]
     * <br>出力する最適化用ガード命令<br>
     * リンク$srclinkが第dstpos引数に接続していることを確認したら、
     * リンク先のアトムへの参照を$dstatomに代入する。*/
	public static final int DEREFLINK = 3; // by mizuno
	// LOCALDEREFLINKは不要
	static {setArgType(DEREFLINK, new ArgType(true, ARG_ATOM, ARG_VAR, ARG_INT));}

	/** findatom [-dstatom, srcmem, funcref]
	 * <br>反復するガード命令<br>
	 * 膜$srcmemにあってファンクタfuncrefを持つアトムへの参照を次々に$dstatomに代入する。*/
	public static final int FINDATOM = 4;
	// LOCALFINDATOMは不要
	static {setArgType(FINDATOM, new ArgType(true, ARG_ATOM, ARG_MEM, ARG_OBJ));}

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
	static {setArgType(LOCKMEM, new ArgType(true, ARG_MEM, ARG_ATOM));}
    
    /** locallockmem [-dstmem, freelinkatom]
     * <br>ロック取得する最適化用ガード命令<br>
     * lockmemと同じ。ただし$freelinkatomはこの計算ノードに存在する。*/
	public static final int LOCALLOCKMEM = LOCAL + LOCKMEM;
	static {setArgType(LOCALLOCKMEM, new ArgType(true, ARG_MEM, ARG_ATOM));}

    /** anymem [-dstmem, srcmem] 
     * <br>反復するロック取得するガード命令<br>
     * 膜$srcmemの子膜のうちまだロックを取得していない膜に対して次々に、
     * ノンブロッキングでのロック取得を試みる。
     * そして、ロック取得に成功した各子膜への参照を$dstmemに代入する。
     * 取得したロックは、後続の命令列がその膜に対して失敗したときに解放される。
     * <p><b>注意</b>　ロック取得に失敗した場合と、その膜が存在していなかった場合とは区別できない。*/
	public static final int ANYMEM = 6;
	static {setArgType(ANYMEM, new ArgType(true, ARG_MEM, ARG_MEM));}
	
	/** localanymem [-dstmem, srcmem]
     * <br>反復するロック取得する最適化用ガード命令<br>
	 * anymemと同じ。ただし$srcmemはこの計算ノードに存在する。$dstmemについては何も仮定されない。*/
	public static final int LOCALANYMEM = LOCAL + ANYMEM;
	static {setArgType(LOCALANYMEM, new ArgType(true, ARG_MEM, ARG_ATOM));}

	/** lock [srcmem]
	 * <br>ロック取得するガード命令<br>
	 * 膜$srcmemに対して、ノンブロッキングでのロックを取得を試みる。
	 * 取得したロックは、後続の命令列が失敗したときに解放される。
	 * <p>アトム主導テストで、主導するアトムによって特定された膜のロックを取得するために使用される。
	 * @see lockmem
	 * @see getmem */
	public static final int LOCK = 7;
	static {setArgType(LOCK, new ArgType(false, ARG_MEM));}
	
	/** locallock [srcmem]
	 * <br>ロック取得する最適化用ガード命令<br>
	 * lockと同じ。ただし$srcmemはこの計算ノードに存在する。*/
	public static final int LOCALLOCK = LOCAL + LOCK;
	static {setArgType(LOCALLOCK, new ArgType(false, ARG_MEM));}

	/** getmem [-dstmem, srcatom]
	 * <br>失敗しないガード命令<br>
	 * アトム$srcatomの所属膜への参照をロックせずに$dstmemに代入する。
	 * <p>アトム主導テストで使用される。
	 * @see lock */
	public static final int GETMEM = 8;
	// LOCALGETMEMは不要
	static {setArgType(GETMEM, new ArgType(true, ARG_MEM, ARG_ATOM));}
	
	/** getparent [-dstmem, srcmem]
	 * <br>ガード命令<br>
	 * （ロックしていない）膜$srcmemに対して、その親膜への参照をロックせずに$dstmemに代入する。
	 * 親膜が無い場合は失敗する。
	 * <p>アトム主導テストで使用される。*/
	public static final int GETPARENT = 9;
	// LOCALGETPARENTは不要
	static {setArgType(GETPARENT, new ArgType(true, ARG_MEM, ARG_MEM));}

    // 膜に関係する出力しない基本ガード命令 (10--19)
	//  ----- testmem    [dstmem, srcatom]
	//  ----- norules    [srcmem] 
	//  ----- nfreelinks [srcmem, count]
	//  ----- natoms     [srcmem, count]
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
	static {setArgType(TESTMEM, new ArgType(false, ARG_MEM, ARG_ATOM));}

    /** norules [srcmem] 
     * <br>ガード命令<br>
     * 膜$srcmemにルールが存在しないことを確認する。*/
    public static final int NORULES = 11;
    // LOCALNORULESは不要
	static {setArgType(NORULES, new ArgType(false, ARG_MEM));}

    /** nfreelinks [srcmem, count]
     * <br>ガード命令<br>
     * 膜$srcmemの自由リンク数がcountであることを確認する。*/
    public static final int NFREELINKS = 12;
	// LOCALNFREELINKSは不要
	static {setArgType(NFREELINKS, new ArgType(false, ARG_MEM, ARG_INT));}

	/** natoms [srcmem, count]
	 * <br>ガード命令<br>
	 * 膜$srcmemの自由リンク管理アトム以外のアトム数がcountであることを確認する。*/
	public static final int NATOMS = 13;
	// LOCALNATOMSは不要
	static {setArgType(NATOMS, new ArgType(false, ARG_MEM, ARG_INT));}
	
	/** natomsindirect [srcmem, countfunc]
	 * <br>ガード命令<br>
	 * 膜$srcmemの自由リンク管理アトム以外のアトム数が$countfuncの値であることを確認する。
	 */
	public static final int NATOMSINDIRECT = 14;
	static {setArgType(NATOMSINDIRECT, new ArgType(false, ARG_MEM, ARG_VAR));}

    /** nmems [srcmem, count]
     * <br>ガード命令<br>
     * 膜$srcmemの子膜の数がcountであることを確認する。*/
    public static final int NMEMS = 15;
	// LOCALNMEMSは不要
	static {setArgType(NMEMS, new ArgType(false, ARG_MEM, ARG_INT));}

	// 16は予約 see isground

    /** eqmem [mem1, mem2]
     * <br>ガード命令<br>
     * $mem1と$mem2が同一の膜を参照していることを確認する。
     * <p><b>注意</b> Ruby版のeqから分離 */
    public static final int EQMEM = 17;
	// LOCALEQMEMは不要
	static {setArgType(EQMEM, new ArgType(false, ARG_MEM, ARG_MEM));}
	
    /** neqmem [mem1, mem2]
     * <br>ガード命令<br>
     * $mem1と$mem2が異なる膜を参照していることを確認する。
     * <p><b>注意</b> Ruby版のneqから分離
     * <p><font color=red><b>この命令は不要かも知れない</b></font> */
    public static final int NEQMEM = 18;
	// LOCALNEQMEMは不要
	static {setArgType(NEQMEM, new ArgType(false, ARG_MEM, ARG_MEM));}

	/** stable [srcmem]
	 * <br>ガード命令<br>
	 * 膜$srcmemとその子孫の全ての膜の実行が停止していることを確認する。*/
	public static final int STABLE = 19;
	// LOCALSTABLEは不要
	static {setArgType(STABLE, new ArgType(false, ARG_MEM));}
    
	// アトムに関係する出力しない基本ガード命令 (20-24)
	//  -----  func     [srcatom, funcref]
	//  -----  notfunc  [srcatom, funcref]
	//  -----  eqatom   [atom1, atom2]
	//  -----  neqatom  [atom1, atom2]
	//  -----  samefunc [atom1, atom2]

	/** func [srcatom, funcref]
	 * <br>ガード命令<br>
	 * アトム$srcatomがファンクタfuncrefを持つことを確認する。
	 * <p>getfunc[tmp,srcatom];loadfunc[func,funcref];eqfunc[tmp,func] と同じ。*/
	public static final int FUNC = 20;
	// LOCALFUNCは不要
	static {setArgType(FUNC, new ArgType(false, ARG_ATOM, ARG_OBJ));}

	/** notfunc [srcatom, funcref]
	 * <br>ガード命令<br>
	 * アトム$srcatomがファンクタfuncrefを持たないことを確認する。
	 * <p>典型的には、プロセス文脈の明示的な自由リンクの出現アトムが$inside_proxyでないことを確認するために使われる。
	 * <p>getfunc[tmp,srcatom];loadfunc[func,funcref];neqfunc[tmp,func] と同じ。*/
	public static final int NOTFUNC = 21;
	// LOCALNOTFUNCは不要
	static {setArgType(NOTFUNC, new ArgType(false, ARG_ATOM, ARG_OBJ));}

	/** eqatom [atom1, atom2]
	 * <br>ガード命令<br>
	 * $atom1と$atom2が同一のアトムを参照していることを確認する。
	 * <p><b>注意</b> Ruby版のeqから分離 */
	public static final int EQATOM = 22;
	// LOCALEQATOMは不要
	static {setArgType(EQATOM, new ArgType(false, ARG_ATOM, ARG_ATOM));}

	/** neqatom [atom1, atom2]
	 * <br>ガード命令<br>
	 * $atom1と$atom2が異なるアトムを参照していることを確認する。
	 * <p><b>注意</b> Ruby版のneqから分離 */
	public static final int NEQATOM = 23;
	// LOCALNEQATOMは不要
	static {setArgType(NEQATOM, new ArgType(false, ARG_ATOM, ARG_ATOM));}

	/** samefunc [atom1, atom2]
	 * <br>ガード命令<br>
	 * $atom1と$atom2が同じファンクタを持つことを確認する。
	 * <p>getfunc[func1,atom1];getfunc[func2,atom2];eqfunc[func1,func2]と同じ。*/
	public static final int SAMEFUNC = 24;
	// LOCALSAMEFUNCは不要
	static {setArgType(SAMEFUNC, new ArgType(false, ARG_ATOM, ARG_ATOM));}

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
	static {setArgType(DEREFFUNC, new ArgType(true, ARG_VAR, ARG_ATOM, ARG_INT));}

	/** getfunc [-func, atom]
	 * <br>出力する失敗しない拡張ガード命令<br>
	 * アトム$atomのファンクタへの参照を$funcに代入する。*/
	public static final int GETFUNC = 26;
	// LOCALGETFUNCは不要
	static {setArgType(GETFUNC, new ArgType(true, ARG_VAR, ARG_ATOM));}

	/** loadfunc [-func, funcref]
	 * <br>出力する失敗しない拡張ガード命令<br>
	 * ファンクタfuncrefへの参照を$funcに代入する。*/
	public static final int LOADFUNC = 27;
	// LOCALLOADFUNCは不要
	static {setArgType(LOADFUNC, new ArgType(true, ARG_VAR, ARG_OBJ));}
	// func/funcrefはVAR? OBJ? -> (n-kato) func=VAR, funcref=OBJ
	
	/** eqfunc [func1, func2]
	 * <br>型付き拡張用ガード命令<br>
	 * ファンクタ$func1と$func2が等しいことを確認する。*/
	public static final int EQFUNC = 28;
	// LOCALEQFUNCは不要
	static {setArgType(EQFUNC, new ArgType(false, ARG_VAR, ARG_VAR));}

	/** neqfunc [func1, func2]
	 * <br>型付き拡張用ガード命令<br>
	 * ファンクタ$func1と$func2が異なることを確認する。*/
	public static final int NEQFUNC = 29;
	// LOCALEQFUNCは不要
	static {setArgType(NEQFUNC, new ArgType(false, ARG_VAR, ARG_VAR));}

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
     * 実行アトムスタックは操作しない。
     * @see dequeueatom */
	public static final int REMOVEATOM = 30;
	static {setArgType(REMOVEATOM, new ArgType(false, ARG_ATOM, ARG_MEM, ARG_OBJ));}
	
	/** localremoveatom [srcatom, srcmem, funcref]
     * <br>最適化用ボディ命令<br>
     * removeatomと同じ。ただし$srcatomはこの計算ノードに存在する。*/
	public static final int LOCALREMOVEATOM = LOCAL + REMOVEATOM;
	static {setArgType(LOCALREMOVEATOM, new ArgType(false, ARG_ATOM, ARG_MEM, ARG_OBJ));}

    /** newatom [-dstatom, srcmem, funcref]
     * <br>ボディ命令<br>
     * 膜$srcmemにファンクタfuncrefを持つ新しいアトム作成し、参照を$dstatomに代入する。
     * アトムはまだ実行アトムスタックには積まれない。
     * @see enqueueatom */
    public static final int NEWATOM = 31;
	static {setArgType(NEWATOM, new ArgType(true, ARG_ATOM, ARG_MEM, ARG_OBJ));}
    
	/** localnewatom [-dstatom, srcmem, funcref]
	 * <br>最適化用ボディ命令<br>
	 * newatomと同じ。ただし$srcmemはこの計算ノードに存在する。*/
	public static final int LOCALNEWATOM = LOCAL + NEWATOM;
	static {setArgType(LOCALNEWATOM, new ArgType(true, ARG_ATOM, ARG_MEM, ARG_OBJ));}

	/** newatomindirect [-dstatom, srcmem, func]
	 * <br>型付き拡張用ボディ命令<br>
	 * 膜$srcmemにファンクタ$funcを持つ新しいアトム作成し、参照を$dstatomに代入する。
	 * アトムはまだ実行アトムスタックには積まれない。
	 * @see newatom */
	public static final int NEWATOMINDIRECT = 32;
	static {setArgType(NEWATOMINDIRECT, new ArgType(true, ARG_ATOM, ARG_MEM, ARG_VAR));}
	
	/** localnewatomindirect [-dstatom, srcmem, func]
	 * <br>型付き拡張用最適化用ボディ命令<br>
	 * newatomindirectと同じ。ただし$srcmemはこの計算ノードに存在する。*/
	public static final int LOCALNEWATOMINDIRECT = LOCAL + NEWATOMINDIRECT;
	static {setArgType(LOCALNEWATOMINDIRECT, new ArgType(true, ARG_ATOM, ARG_MEM, ARG_VAR));}

	/** enqueueatom [srcatom]
     * <br>ボディ命令<br>
     * アトム$srcatomを所属膜の実行アトムスタックに積む。
     * <p>すでに実行アトムスタックに積まれていた場合の動作は未定義とする。
     * <p>アトム$srcatomがシンボルファンクタを持たない場合の動作も未定義とする。
     * <p>アクティブかどうかによって命令の動作は変わらない。
     * むしろこの命令で積まれるアトムがアクティブである。*/
    public static final int ENQUEUEATOM = 33;
	static {setArgType(ENQUEUEATOM, new ArgType(false, ARG_ATOM));}
    
	/** localenqueueatom [srcatom]
	 * <br>最適化用ボディ命令<br>
	 * enqueueatomと同じ。ただし$srcatomは<B>本膜と同じタスクが管理する膜に存在する</B>。*/
	public static final int LOCALENQUEUEATOM = LOCAL + ENQUEUEATOM;
	static {setArgType(LOCALENQUEUEATOM, new ArgType(false, ARG_ATOM));}

    /** dequeueatom [srcatom]
     * <br>最適化用ボディ命令<br>
     * アトム$srcatomがこの計算ノードにある実行アトムスタックに入っていれば、スタックから取り出す。
     * <p><b>注意</b>　この命令は、メモリ使用量のオーダを削減するために任意に使用することができる。
     * アトムを再利用するときは、因果関係に注意すること。
     * <p>なお、他の計算ノードにある実行アトムスタックの内容を取得/変更する命令は存在しない。
     * <p>この命令は、Runtime.Atom.dequeueを呼び出す。*/
    public static final int DEQUEUEATOM = 34;
	// LOCALDEQUEUEATOMは最適化の効果が無いため却下
	static {setArgType(DEQUEUEATOM, new ArgType(false, ARG_ATOM));}

	/** freeatom [srcatom]
	 * <br>最適化用ボディ命令<br>
	 * 何もしない。
	 * <p>$srcatomがどの膜にも属さず、かつこの計算ノード内の実行アトムスタックに積まれていないことを表す。
	 * アトムを他の計算ノードで積んでいる場合、輸出表の整合性は大丈夫か調べる。
	 * → 輸出表は作らないことにしたので大丈夫。*/
	public static final int FREEATOM = 35;
	// LOCALFREEATOMは不要
	static {setArgType(FREEATOM, new ArgType(false, ARG_ATOM));}

	/** alterfunc [atom, funcref]
	 * <br>最適化用ボディ命令<br>
	 * （所属膜を持つ）アトム$atomのファンクタをfuncrefにする。
	 * 引数の個数が異なる場合の動作は未定義とする。*/
	public static final int ALTERFUNC = 36;
	static {setArgType(ALTERFUNC, new ArgType(false, ARG_ATOM, ARG_OBJ));}

	/** localalterfunc [atom, funcref]
	 * <br>最適化用ボディ命令<br>
	 * alterfuncと同じ。ただし$atomはこの計算ノードに存在する。*/
	public static final int LOCALALTERFUNC = LOCAL + ALTERFUNC;
	static {setArgType(LOCALALTERFUNC, new ArgType(false, ARG_ATOM, ARG_OBJ));}

	/** alterfuncindirect [atom, func]
	 * <br>最適化用ボディ命令<br>
	 * alterfuncと同じ。ただしファンクタは$funcにする。*/
	public static final int ALTERFUNCINDIRECT = 37;
	static {setArgType(ALTERFUNCINDIRECT, new ArgType(false, ARG_ATOM, ARG_VAR));}
	
	/** localalterfuncindirect [atom, func]
	 * <br>最適化用ボディ命令<br>
	 * alterfuncindirectと同じ。ただし$atomはこの計算ノードに存在する。*/
	public static final int LOCALALTERFUNCINDIRECT = LOCAL + ALTERFUNCINDIRECT;
	static {setArgType(LOCALALTERFUNCINDIRECT, new ArgType(false, ARG_ATOM, ARG_VAR));}

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
	static {setArgType(ALLOCATOM, new ArgType(true, ARG_ATOM, ARG_OBJ));}

	/** allocatomindirect [-dstatom, func]
	 * <br>型付き拡張用最適化用命令<br>
	 * ファンクタ$funcを持つ所属膜を持たない新しいアトムを作成し、参照を$dstatomに代入する。
	 * <p>ガード検査で使われる定数アトムを生成するために使用される。*/
	public static final int ALLOCATOMINDIRECT = 41;
	// LOCALALLOCATOMINDIRECTは不要
	static {setArgType(ALLOCATOMINDIRECT, new ArgType(true, ARG_ATOM, ARG_VAR));}

	/** copyatom [-dstatom, mem, srcatom]
	 * <br>型付き拡張用ボディ命令
	 * アトム$srcatomと同じ名前のアトムを膜$memに生成し、$dstatomに代入して返す。
	 * 実行アトムスタックは操作しない。
	 * <p>マッチングで得た型付きアトムをコピーするために使用する。
	 * <p>getfunc[func,srcatom];newatomindirect[dstatom,mem,func]と同じ。よって廃止？
	 * copygroundtermに移行すべきかもしれない。*/
	public static final int COPYATOM = 42;
	static {setArgType(COPYATOM, new ArgType(true, ARG_ATOM, ARG_MEM, ARG_ATOM));}

	/** localcopyatom [-dstatom, mem, srcatom]
	 * <br>最適化用ボディ命令<br>
	 * copyatomと同じ。ただし$memはこの計算ノードに存在する。*/
	public static final int LOCALCOPYATOM = LOCAL + COPYATOM;
	static {setArgType(LOCALCOPYATOM, new ArgType(true, ARG_ATOM, ARG_MEM, ARG_ATOM));}

	/** localaddatom [dstmem, atom]
	 * <br>最適化用ボディ命令<br>
	 * （所属膜を持たない）アトム$atomを膜$dstmemに所属させる。
	 * ただし$dstmemはこの計算ノードに存在する。*/
	public static final int LOCALADDATOM = LOCAL + 43;
	// 一般の ADDATOM は存在しない。
	static {setArgType(LOCALADDATOM, new ArgType(false, ARG_MEM, ARG_ATOM));}
	
	// 膜を操作する基本ボディ命令 (50--59)    
	// [local]removemem                [srcmem, parentmem]
	// [local]newmem          [-dstmem, srcmem]
	//  ----- allocmem        [-dstmem]
	//  ----- newroot         [-dstmem, srcmem, node]
	//  ----- movecells                [dstmem, srcmem]
	//  ----- enqueueallatoms          [srcmem]
	//  ----- freemem                  [srcmem]
	// [local]addmem                   [dstmem, srcmem]
	// [local]enququmem                [srcmem]
	// [local]unlockmem                [srcmem]
	// [local]setmemname               [dstmem, name]

	/** removemem [srcmem, parentmem]
	 * <br>ボディ命令<br>
	 * 膜$srcmemを親膜（$parentmem）から取り出す。
	 * <strike>膜$srcmemはロック時に実行膜スタックから除去されているため、実行膜スタックは操作しない。</strike>
	 * 実行膜スタックに積まれている場合は除去する。
	 * @see removeproxies */
	public static final int REMOVEMEM = 50;
	static {setArgType(REMOVEMEM, new ArgType(false, ARG_MEM, ARG_MEM));}

	/** localremovemem [srcmem, parentmem]
	 * <br>最適化用ボディ命令<br>
	 * removememと同じ。ただし$srcmemの親膜（$parentmem）はこの計算ノードに存在する。*/
	public static final int LOCALREMOVEMEM = LOCAL + REMOVEMEM;
	static {setArgType(LOCALREMOVEMEM, new ArgType(false, ARG_MEM, ARG_MEM));}

	/** newmem [-dstmem, srcmem]
	 * <br>ボディ命令<br>
	 * （活性化された）膜$srcmemに新しい（ルート膜でない）子膜を作成し、$dstmemに代入し、活性化する。
	 * この場合の活性化は、$srcmemと同じ実行膜スタックに積むことを意味する。
	 * @see newroot
	 * @see addmem */
	public static final int NEWMEM = 51;
	static {setArgType(NEWMEM, new ArgType(true, ARG_MEM, ARG_MEM));}

	/** localnewmem [-dstmem, srcmem]
	* <br>最適化用ボディ命令<br>
	* newmemと同じ。ただし$srcmemは<B>本膜と同じタスクによって管理される</B>。*/
	public static final int LOCALNEWMEM = LOCAL + NEWMEM;
	static {setArgType(LOCALNEWMEM, new ArgType(true, ARG_MEM, ARG_MEM));}

	/** allocmem [-dstmem]
	 * <br>最適化用ボディ命令<br>
	 * 親膜を持たない新しい膜を作成し、参照を$dstmemに代入する。*/
	public static final int ALLOCMEM = 52;
	// LOCALALLOCMEMは不要
	static {setArgType(ALLOCMEM, new ArgType(true, ARG_MEM));}

	/** newroot [-dstmem, srcmem, nodeatom]
	 * <br>ボディ命令<br>
	 * 膜$srcmemの子膜にアトム$nodeatomの名前で指定された計算ノードで実行される新しいロックされた
	 * ルート膜を作成し、参照を$dstmemに代入し、（ロックしたまま）活性化する。
	 * この場合の活性化は、仮の実行膜スタックに積むことを意味する。
	 * <p>ただし上記の仕様は計算ノード指定が空文字列でないときのみ。
	 * 空文字列の場合は、newmemと同じだがロックされた状態で作られる。
	 * <p>newmemと違い、このルート膜のロックは明示的に解放しなければならない。
	 * @see unlockmem */
	public static final int NEWROOT = 53;
	// LOCALNEWROOTは最適化の効果が無いため却下
	static {setArgType(NEWROOT, new ArgType(true, ARG_MEM, ARG_MEM, ARG_ATOM));}
	
	/** movecells [dstmem, srcmem]
	 * <br>ボディ命令<br>
	 * （親膜を持たない）膜$srcmemにある全てのアトムと子膜（ロックを取得していない）を膜$dstmemに移動する。
	 * 子膜はルート膜の直前の膜まで再帰的に移動される。ホスト間移動した膜は活性化される。
	 * <p>実行後、膜$srcmemはこのまま廃棄されなければならない
	 * <strike>（内容物のルールセットに限り参照してよい？）</strike>
	 * <p>実行後、膜$dstmemの全てのアクティブアトムをエンキューし直すべきである。
	 * <p><b>注意</b>　Ruby版のpourから名称変更
	 * <p>moveCellsFromメソッドを呼び出す。
	 * @see enqueueallatoms */
	public static final int MOVECELLS = 54;
	// LOCALMOVECELLSは最適化の効果が無いため却下？あるいはさらに特化した仕様にする。
	static {setArgType(MOVECELLS, new ArgType(false, ARG_MEM, ARG_MEM));}

	/** enqueueallatoms [srcmem]
	 * <br>（予約された）ボディ命令<br>
	 * 何もしない。または、膜$srcmemにある全てのアクティブアトムをこの膜の実行アトムスタックに積む。
	 * <p>アトムがアクティブかどうかを判断するには、
	 * ファンクタを動的検査する方法と、2つのグループのアトムがあるとして所属膜が管理する方法がある。
	 * @see enqueueatom */
	public static final int ENQUEUEALLATOMS = 55;
	// LOCALENQUEUEALLATOMSは最適化の効果が無いため却下
	static {setArgType(ENQUEUEALLATOMS, new ArgType(false, ARG_MEM));}

	/** freemem [srcmem]
	 * <br>ボディ命令<br>
	 * 膜$srcmemを廃棄する。
	 * <p>$srcmemがどの膜にも属さず、かつスタックに積まれていないことを表す。
	 * @see freeatom */
	public static final int FREEMEM = 56;
	// LOCALFREEMEMは不要
	static {setArgType(FREEMEM, new ArgType(false, ARG_MEM));}

	/** addmem [dstmem, srcmem]
	 * <br>ボディ命令<br>
	 * ロックされた（親膜の無い）膜$srcmemを（活性化された）膜$dstmemに移動する。
	 * 子膜のロックは取得していないものとする。
	 * 子膜はルート膜の直前の膜まで再帰的に移動される。ホスト間移動した膜は活性化される。
	 * <p>膜$srcmemを再利用するために使用される。
	 * <p>newmemと違い、$srcmemのロックは明示的に解放しなければならない。
	 * <p>moveToメソッドを呼び出す。
	 * @see unlockmem, enqueuemem
	 */
	public static final int ADDMEM = 57;
	static {setArgType(ADDMEM, new ArgType(false, ARG_MEM, ARG_MEM));}

	/** enqueuemem [srcmem]
	 * ロックされた膜$srcmemをロックしたまま活性化する。
	 * この場合の活性化は、$srcmemがルート膜の場合、仮の実行膜スタックに積むことを意味し、
	 * ルート膜でない場合、親膜と同じ実行膜スタックに積むことを意味する。
	 * すでに実行膜スタックまたは仮の実行膜スタックに積まれている場合は、何もしない。
	 */	
	public static final int ENQUEUEMEM = 58;
	static {setArgType(ENQUEUEMEM, new ArgType(false, ARG_MEM));}

	/** localaddmem [dstmem, srcmem]
	 * <br>最適化用ボディ命令<br>
	 * addmemと同じ。ただし$srcmemはこの計算ノードに存在する。$dstmemについては何も仮定しない。*/
	public static final int LOCALADDMEM = LOCAL + ADDMEM;
	static {setArgType(LOCALADDMEM, new ArgType(false, ARG_MEM, ARG_MEM));}

	/** unlockmem [srcmem]
	 * <br>ボディ命令<br>
	 * （活性化した）膜$srcmemのロックを解放する。
	 * $srcmemがルート膜の場合、仮の実行膜スタックの内容を実行膜スタックの底に転送する。
	 * <p>addmemによって再利用された膜、およびnewrootによってルールで新しく生成された
	 * ルート膜に対して、（子孫から順番に）必ず呼ばれる。
	 * <p>実行後、$srcmemへの参照は廃棄しなければならない。*/
	public static final int UNLOCKMEM = 59;
	static {setArgType(UNLOCKMEM, new ArgType(false, ARG_MEM));}

	/** localunlockmem [srcmem]
	 * <br>最適化用ボディ命令<br>
	 * unlockmemと同じ。ただし$srcmemはこの計算ノードに存在する。*/
	public static final int LOCALUNLOCKMEM = LOCAL + UNLOCKMEM;
	static {setArgType(LOCALUNLOCKMEM, new ArgType(false, ARG_MEM));}

	/** setmemname [dstmem, name]
	 * <br>ボディ命令<br>
	 * 膜$dstmemの名前を文字列（またはnull）nameに設定する。
	 * <p>現在、膜の名前の使用目的は表示用のみ。いずれ、膜名に対するマッチングができるようになるはず。*/
	public static final int SETMEMNAME = 60;
	static {setArgType(SETMEMNAME, new ArgType(false, ARG_MEM, ARG_OBJ));}

	/** localsetmemname [dstmem, name]
	 * <br>最適化用ボディ命令<br>
	 * setmemnameと同じ。ただし$dstmemはこの計算ノードに存在する。*/
	public static final int LOCALSETMEMNAME = LOCAL + SETMEMNAME;
	static {setArgType(LOCALSETMEMNAME, new ArgType(false, ARG_MEM, ARG_OBJ));}

	// 予約 (61--62)

	// リンクに関係する出力するガード命令 (63--64)
	
	//	-----  getlink   [-link,atom,pos]
	//	-----  alloclink [-link,atom,pos]

	/** getlink [-link, atom, pos]
	 * <br>出力する失敗しない拡張ガード命令、最適化用ボディ命令<br>
	 * アトム$atomの第pos引数に格納されたリンクオブジェクトへの参照を$linkに代入する。
	 * <p>典型的には、$atomはルールヘッドに存在する。*/
	public static final int GETLINK = 63;
	// LOCALGETLINKは不要
	static {setArgType(GETLINK, new ArgType(true, ARG_VAR, ARG_ATOM, ARG_INT));}

	/** alloclink [-link, atom, pos]
	 * <br>出力する失敗しない拡張ガード命令、最適化用ボディ命令<br>
	 * アトム$atomの第pos引数を指すリンクオブジェクトを生成し、参照を$linkに代入する。
	 * <p>典型的には、$atomはルールボディに存在する。*/
	public static final int ALLOCLINK = 64;
	// LOCALGETLINKは不要
	static {setArgType(ALLOCLINK, new ArgType(true, ARG_VAR, ARG_ATOM, ARG_INT));}

	// リンクを操作するボディ命令 (65--69)
	// [local]newlink     [atom1, pos1, atom2, pos2, mem1]
	// [local]relink      [atom1, pos1, atom2, pos2, mem]
	// [local]unify       [atom1, pos1, atom2, pos2, mem]
	// [local]inheritlink [atom1, pos1, link2, mem]
	// [local]unifylinks  [link1, link2, mem]

	/** newlink [atom1, pos1, atom2, pos2, mem1]
	 * <br>ボディ命令<br>
	 * アトム$atom1（膜$mem1にある）の第pos1引数と、
	 * アトム$atom2の第pos2引数の間に両方向リンクを張る。
	 * <p>典型的には、$atom1と$atom2はいずれもルールボディに存在する。
	 * <p><b>注意</b>　Ruby版の片方向から仕様変更された。
	 * <p>alloclink[link1,atom1,pos1];alloclink[link2,atom2,pos2];unifylinks[link1,link2,mem1]と同じ。*/
	public static final int NEWLINK = 65;
	static {setArgType(NEWLINK, new ArgType(false, ARG_ATOM, ARG_INT, ARG_ATOM, ARG_INT, ARG_MEM));}

	/** localnewlink [atom1, pos1, atom2, pos2 (,mem1)]
	 * <br>最適化用ボディ命令<br>
	 * newlinkと同じ。ただし膜$mem1はこの計算ノードに存在する。*/
	public static final int LOCALNEWLINK = LOCAL + NEWLINK;
	static {setArgType(LOCALNEWLINK, new ArgType(false, ARG_ATOM, ARG_INT, ARG_ATOM, ARG_INT, ARG_MEM));}

	/** relink [atom1, pos1, atom2, pos2, mem]
	 * <br>ボディ命令<br>
	 * アトム$atom1（膜$memにある）の第pos1引数と、
	 * アトム$atom2の第pos2引数のリンク先（膜$memにある）の引数を接続する。
	 * <p>典型的には、$atom1はルールボディに、$atom2はルールヘッドに存在する。
	 * <p>型付きプロセス文脈が無いルールでは、つねに$memが本膜なのでlocalrelinkが使用できる。
	 * <p>実行後、$atom2[pos2]の内容は無効になる。
	 * <p>getlink[link2,atom2,pos2];inheritlink[atom1,pos1,link2,mem]と同じ。
	 * <p>alloclink[link1,atom1,pos1];getlink[link2,atom2,pos2];unifylinks[link1,link2,mem]と同じ。*/
	public static final int RELINK = 66;
	static {setArgType(RELINK, new ArgType(false, ARG_ATOM, ARG_INT, ARG_ATOM, ARG_INT, ARG_MEM));}

	/** localrelink [atom1, pos1, atom2, pos2 (,mem)]
	 * <br>最適化用ボディ命令<br>
	 * relinkと同じ。ただし膜$memはこの計算ノードに存在する。*/
	public static final int LOCALRELINK = LOCAL + RELINK;
	static {setArgType(LOCALRELINK, new ArgType(false, ARG_ATOM, ARG_INT, ARG_ATOM, ARG_INT, ARG_MEM));}

	/** unify [atom1, pos1, atom2, pos2, mem]
	 * <br>ボディ命令<br>
	 * アトム$atom1の第pos1引数のリンク先<strike>（膜$memにある）</strike>の引数と、
	 * アトム$atom2の第pos2引数のリンク先<strike>（膜$memにある）</strike>の引数を接続する。
	 * <strike>ただし$atom1および$atom2のリンク先がどちらも所属膜を持たない場合も許されており、
	 * この場合、何もしないで終わってもよいことになっている。これは f(A,A),(f(X,Y):-X=Y) の書き換えなどで起こる。</strike>
	 * $atom1 と $atom2 の両方もしくは一方が所属膜を持たない場合もある。
	 * これは a(A),f(A,B),(a(X),f(Y,Z):-Y=Z,b(X)) の書き換えなどで起こる。
	 * <p>典型的には、$atom1と$atom2はいずれもルールヘッドに存在する。
	 * <p>型付きプロセス文脈が無いルールでは、つねに$memが本膜なのでlocalunifyが使用できる。
	 * <p>getlink[link1,atom1,pos1];getlink[link2,atom2,pos2];unifylinks[link1,link2,mem]と同じ。*/
	public static final int UNIFY = 67;
	static {setArgType(UNIFY, new ArgType(false, ARG_ATOM, ARG_INT, ARG_ATOM, ARG_INT, ARG_MEM));}

	/** localunify [atom1, pos1, atom2, pos2 (,mem)]
	 * <br>最適化用ボディ命令<br>
	 * unifyと同じ。ただし膜$memはこの計算ノードに存在する。*/
	public static final int LOCALUNIFY = LOCAL + UNIFY;
	static {setArgType(LOCALUNIFY, new ArgType(false, ARG_ATOM, ARG_INT, ARG_ATOM, ARG_INT, ARG_MEM));}

	/** inheritlink [atom1, pos1, link2, mem]
	 * <br>最適化用ボディ命令<br>
	 * アトム$atom1（膜$memにある）の第pos1引数と、
	 * リンク$link2のリンク先（膜$memにある）を接続する。
	 * <p>典型的には、$atom1はルールボディに存在し、$link2はルールヘッドに存在する。relinkの代用。
	 * <p>型付きプロセス文脈が無いルールでは、つねに$memが本膜なのでlocalinheritrelinkが使用できる。
	 * <p>$link2は再利用されるため、実行後は$link2は廃棄しなければならない。
	 * <p>alloclink[link1,atom1,pos1];unifylinks[link1,link2,mem]と同じ。
	 * @see getlink */
	public static final int INHERITLINK = 68;
	static {setArgType(INHERITLINK, new ArgType(false, ARG_ATOM, ARG_INT, ARG_VAR, ARG_MEM));}

	/** localinheritlink [atom1, pos1, link2 (,mem)]
	 * <br>最適化用ボディ命令<br>
	 * inheritlinkと同じ。ただし膜$memはこの計算ノードに存在する。*/
	public static final int LOCALINHERITLINK = LOCAL + INHERITLINK;
	static {setArgType(LOCALINHERITLINK, new ArgType(false, ARG_ATOM, ARG_INT, ARG_VAR, ARG_MEM));}

	/** unifylinks [link1, link2, mem]
	 * <br>ボディ命令<br>
	 * リンク$link1の指すアトム引数とリンク$link2の指すアトム引数との間に双方向のリンクを張る。
	 * ただし$link1は膜$memのアトムを指しているか、または所属膜の無いアトムを指している。
	 * 後者の場合、何もしないで終わってもよいことになっている。
	 * <p>todo 命令の解釈時にmem引数が使われることはないので、引数に含めないようにした方がよい。
	 * <p>実行後$link1および$link2は無効なリンクオブジェクトとなるため、参照を使用してはならない。
	 * <p>基底項データ型のコンパイルで使用される。*/
	public static final int UNIFYLINKS = 69;
	static {setArgType(UNIFYLINKS, new ArgType(false, ARG_VAR, ARG_VAR, ARG_MEM));}

	/** localunifylinks [link1, link2 (,mem)]
	 * <br>最適化用ボディ命令<br>
	 * unifylinksと同じ。ただし膜$memはこの計算ノードに存在する。*/
	public static final int LOCALUNIFYLINKS = LOCAL + UNIFYLINKS;
	static {setArgType(LOCALUNIFYLINKS, new ArgType(false, ARG_VAR, ARG_VAR, ARG_MEM));}

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
	static {setArgType(REMOVEPROXIES, new ArgType(false, ARG_MEM));}

    /** removetoplevelproxies [srcmem]
     * <br>ボディ命令<br>
     * 膜$srcmem（本膜）を通過している無関係な自由リンク管理アトムを除去する。
	 * <p>removeproxiesが全て終わった後で呼ばれる。*/
    public static final int REMOVETOPLEVELPROXIES = 71;
	// LOCALREMOVETOPLEVELPROXIESは最適化の効果が無いため却下
	static {setArgType(REMOVETOPLEVELPROXIES, new ArgType(false, ARG_MEM));}

    /** insertproxies [parentmem,childmem]
     * <br>ボディ命令<br>
     * 指定された膜間に自由リンク管理アトムを自動挿入する。
     * <p>addmemが全て終わった後で呼ばれる。*/
    public static final int INSERTPROXIES = 72;
	// LOCALINSERTPROXIESは最適化の効果が無いため却下
	static {setArgType(INSERTPROXIES, new ArgType(false, ARG_MEM, ARG_MEM));}
	
    /** removetemporaryproxies [srcmem]
     * <br>ボディ命令<br>
     * 膜$srcmem（本膜）に残された"star"アトムを除去する。
     * <p>insertproxiesが全て終わった後で呼ばれる。*/
    public static final int REMOVETEMPORARYPROXIES = 73;
	// LOCALREMOVETEMPORARYPROXIESは最適化の効果が無いため却下
	static {setArgType(REMOVETEMPORARYPROXIES, new ArgType(false, ARG_MEM));}

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
	static {setArgType(LOADRULESET, new ArgType(false, ARG_MEM, ARG_OBJ));}
	
	/** localloadruleset [dstmem, ruleset]
	 * <br>最適化用ボディ命令<br>
	 * loadrulesetと同じ。ただし$dstmemはこの計算ノードに存在する。*/
	public static final int LOCALLOADRULESET = LOCAL + LOADRULESET;
	static {setArgType(LOCALLOADRULESET, new ArgType(false, ARG_MEM, ARG_OBJ));}

	/** copyrules [dstmem, srcmem]
	 * <br>ボディ命令<br>
	 * 膜$srcmemにある全てのルールを膜$dstmemにコピーする。
	 * <p><b>注意</b>　Ruby版のinheritrulesから名称変更 */
	public static final int COPYRULES = 76;
	static {setArgType(COPYRULES, new ArgType(false, ARG_MEM, ARG_MEM));}

	/** localcopyrules [dstmem, srcmem]
	 * <br>最適化用ボディ命令<br>
	 * copyrulesと同じ。ただし$dstmemはこの計算ノードに存在する。$srcmemについては何も仮定しない。*/
	public static final int LOCALCOPYRULES = LOCAL + COPYRULES;
	static {setArgType(LOCALCOPYRULES, new ArgType(false, ARG_MEM, ARG_MEM));}

	/** clearrules [dstmem]
	 * <br>ボディ命令<br>
	 * 膜$dstmemにある全てのルールを消去する。*/
	public static final int CLEARRULES = 77;
	static {setArgType(CLEARRULES, new ArgType(false, ARG_MEM));}
	
	/** localclearrules [dstmem]
	 * <br>最適化用ボディ命令<br>
	 * clearrulesと同じ。ただし$dstmemはこの計算ノードに存在する。*/
	public static final int LOCALCLEARRULES = LOCAL + CLEARRULES;
	static {setArgType(LOCALCLEARRULES, new ArgType(false, ARG_MEM));}

	/** loadmodule [dstmem, ruleset]
	 * <br>ボディ命令<br>
	 * ルールセットrulesetを膜$dstmemにコピーする。
	 */
	public static final int LOADMODULE = 78;
	static {setArgType(LOADMODULE, new ArgType(false, ARG_MEM, ARG_OBJ));}

    // 型付きでないプロセス文脈をコピーまたは廃棄するための命令 (80--89)
	//  ----- recursivelock            [srcmem]
	//  ----- recursiveunlock          [srcmem]
	//  ----- copymem         [-dstmap, dstmem, srcmem]
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
	static {setArgType(RECURSIVELOCK, new ArgType(false, ARG_MEM));}

    /** recursiveunlock [srcmem]
     * <br>（予約された）ボディ命令<br>
     * 膜$srcmemの全ての子膜に対して再帰的にロックを解放する。
     * 膜はそれを管理するタスクの実行膜スタックに再帰的に積まれる。
     * <p>再帰的に積む方法は、今後考える。
     * @see unlockmem */
    public static final int RECURSIVEUNLOCK = 81;
	// LOCALRECURSIVEUNLOCKは最適化の効果が無いため却下
	static {setArgType(RECURSIVEUNLOCK, new ArgType(false, ARG_MEM));}

    /** copycells [-dstmap, dstmem, srcmem]
     * <br>（予約された）ボディ命令<br>
     * 再帰的にロックされた膜$srcmemの内容のコピーを作成し,膜$dstmemに入れる.
     * その際、リンク先がこの膜の(子膜を含めて)中に無いアトムの情報を
     * コピーされるアトムオブジェクト -> コピーされたアトムオブジェクト
     * (2005/01/13 従来のAtom.idからの参照を変更)
     * というMapオブジェクトとして,dstmapに入れる.
     **/
    public static final int COPYCELLS = 82;
	// LOCALCOPYMEMは最適化の効果が無いため却下
	static {setArgType(COPYCELLS, new ArgType(true, ARG_VAR, ARG_MEM, ARG_MEM));}

	/** dropmem [srcmem]
	 * <br>（予約された）ボディ命令<br>
	 * 再帰的にロックされた膜$srcmemを破棄する。
	 * この膜や子孫の膜をルート膜とするタスクは強制終了する。*/
	public static final int DROPMEM = 83;
	// LOCALDROPMEMは最適化の効果が無いため却下
	static {setArgType(DROPMEM, new ArgType(false, ARG_MEM));}

	/** lookuplink [-dstlink, srcmap, srclink]
	 * srclinkのリンク先のアトムのコピーを$srcmapより得て、
	 * そのアトムをリンク先とする-dstlinkを作って返す。
	 */
	public static final int LOOKUPLINK = 84;
	static {setArgType(LOOKUPLINK, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));}

	/** insertconnectors[-dstset,linklist,mem]
	 * $linklistリストの各変数番号にはリンクオブジェクトが格納されている。
	 * それらのリンクオブジェクトのリンク先は$mem内のアトムである。
	 * それらのリンクオブジェクトの全ての組み合わせに対し、buddyの関係にあるかどうかを検査し、
	 * その場合には'='アトムを挿入する。
	 * 挿入したアトムを$dstsetに追加する。
	 */
	public static final int INSERTCONNECTORS = 85;
	static {setArgType(INSERTCONNECTORS, new ArgType(true, ARG_VAR, ARG_VARS, ARG_MEM));}
	
	/** deleteconnectors[srcset,srcmap,srcmem]
	 * $srcsetに含まれる'='アトムをコピーしたアトムを$srcmapから得て、
	 * 削除し、リンクをつなぎなおす。$srcmemはコピー先の膜。
	 * 
	 * (単に$srcmemに含まれる'='を削除するようにすればよいか？)
	 */
	public static final int DELETECONNECTORS = 86;
	static {setArgType(DELETECONNECTORS, new ArgType(false, ARG_VAR,ARG_VAR,ARG_MEM));}

//	/** * [srcatom,pos]
//	 * <br>（予約された）ボディ命令<br>
//	 * アトムsrcatomの第1引数のリンク先のリンク先のアトムの第pos引数と、
//	 * srcatomの第1引数の間に双方向リンクを貼る。自由リンク管理アトムをどうするか。
//	 * <p>プロセス文脈のコピー時に使用される。*/
//	public static final int * = err;

	// アトム集団のコンパイルには、findatomとrunを使うといいかもしれないが、できないかもしれない。

	// コピーした後でrelinkする。

	// 予約 (90--99)

	//////////////////////////////////////////////////////////////////
	
	// 200番以降の命令にはLOCAL修飾版は存在しない
	
	// 制御命令 (200--209)
	//  -----  react       [ruleref,         [memargs...], [atomargs...], [varargs...]]
	//  -----  jump        [instructionlist, [memargs...], [atomargs...], [varargs...]]
	//  -----  commit      [ruleref]
	//  -----  resetvars   [[memargs...], [atomargs...], [varargs...]]
	//  -----  changevars  [[memargs...], [atomargs...], [varargs...]]
	//  -----  spec        [formals,locals]
	//  -----  proceed
	//  -----  stop 
	//  -----  branch      [instructionlist]
	//  -----  loop        [[instructions...]]
	//  -----  run         [[instructions...]]
	//  -----  not         [instructionslist]

	/** react [ruleref, [memargs...], [atomargs...], [varargs...]]
	 * <br>失敗しないガード命令<br>
	 * ルールrulerefに対するマッチングが成功したことを表す。
	 * 処理系はこのルールのボディを呼び出さなければならない。
	 * <p>spec        [formals, locals];
	 *    resetvars   [[memargs...], [atomargs...], [varargs...]];
	 *    branch      [body] と同じ。
	 * ただしbodyはrulerefのボディ命令列で、先頭の命令はspec[formals,locals]
	 * <p>（未使用命令）*/
	public static final int REACT = 1200;
	static {setArgType(REACT, new ArgType(false, ARG_OBJ, ARG_VARS, ARG_VARS, ARG_VARS));}

	/** jump [instructionlist, [memargs...], [atomargs...], [varargs...]]
     * <br>制御命令<br>
     * 指定の引数列でラベル付き命令列instructionlistを呼び出す。
     * <p>
     * 指定した命令列の実行に失敗すると、この命令が失敗する。
     * 指定した命令列の実行に成功すると、ここで終了する。
     * <p>spec        [formals, locals];
     *    resetvars   [[memargs...], [atomargs...], [varargs...]];
     *    branch      [body] と同じ。
     * ただしbodyはinstructionlistの命令列で、先頭の命令はspec[formals,locals]*/
    public static final int JUMP = 200;
	static {setArgType(JUMP, new ArgType(false, ARG_LABEL, ARG_VARS, ARG_VARS, ARG_VARS));}

	/** commit [ruleref]
	 * <br>無視される最適化用およびトレース用命令<br>
	 * 現在の実引数ベクタでルールrulerefに対するマッチングが成功したことを表す。
	 * 処理系は、この命令に到達するまでに行った全ての分岐履歴を忘却してよい。*/
	public static final int COMMIT = 201;
	static {setArgType(COMMIT, new ArgType(false, ARG_OBJ));}

	/** resetvars [[memargs...], [atomargs...], [varargs...]]
	 * <br>失敗しないガード命令および最適化用ボディ命令<br>
	 * 変数ベクタの内容を再定義する。新しい変数番号は、膜、アトム、その他の順番で0から振り直される。
	 * <b>注意</b>　memargs[0]は本膜が予約しているため変更してはならない。
	 */
	public static final int RESETVARS = 202;
	static {setArgType(RESETVARS, new ArgType(false, ARG_VARS, ARG_VARS, ARG_VARS));}

	/** changevars [[memargs...], [atomargs...], [varargs...]]
	 * <br>失敗しないガード命令および最適化用ボディ命令<br>
	 * 変数ベクタの内容および長さを再定義する。
	 * 新しい変数番号は、膜、アトム、その他のいずれも0から振り直される。
	 * ただしnullが入っている要素は無視される。
	 * 同じ番号に異なる種類のオブジェクトが振られないように注意すること。
	 * <b>注意</b>　memargs[0]は本膜が予約しているため変更してはならない。
	 * <p>（未使用命令）
	 */
	public static final int CHANGEVARS = 1202;
	static {setArgType(CHANGEVARS, new ArgType(false, ARG_VARS, ARG_VARS, ARG_VARS));}

    /** spec [formals, locals]
     * <br>制御命令<br>
     * 仮引数と局所変数の個数を宣言する。
     * 局所変数の個数が不足している場合、変数ベクタを拡張する。*/
    public static final int SPEC = 203;
	static {setArgType(SPEC, new ArgType(false, ARG_INT, ARG_INT));}

	/** proceed
	 * <br>ボディ命令<br>
	 * このproceed命令が所属する命令列の実行が成功したことを表す。
	 * <p>トップレベル命令列で使用された場合、ルールの適用が終わり、
	 * 再利用された全ての膜のロックを解放し、生成した全ての膜を活性化したことを表す。
	 * <p><b>注意</b>　proceedなしで命令列の終端まで進んだ場合、
	 * その命令列の実行は失敗したものとする仕様が採用された。*/
	public static final int PROCEED = 204;
	static {setArgType(PROCEED, new ArgType(false));}

//	/** stop 
//	 * <br>（予約された）失敗しないガード命令<br>
//	 * proceedと同じ。マッチングとボディの命令が統合されたことに伴って廃止される予定。
//	 * <p>典型的には、否定条件にマッチしたことを表すために使用される。
//	 */
//	public static final int STOP = 205;
//	static {setArgType(STOP, new ArgType(false));}

    /** branch [instructionlist]
     * <br>構造化命令<br>
     * 引数の命令列を実行することを表す。
     * 引数実行中に失敗した場合、引数実行中に取得したロックを解放し、branchの次の命令に進む。
     * 引数実行中にproceed命令を実行した場合、ここで終了する。*/
    public static final int BRANCH = 206;
	static {setArgType(BRANCH, new ArgType(false, ARG_LABEL));}

	/** loop [[instructions...]]
	 * <br>構造化命令<br>
	 * 引数の命令列を実行することを表す。
     * 引数実行中に失敗した場合、引数実行中に取得したロックを解放し、loopの次の命令に進む。
     * 引数実行中にproceed命令を実行した場合、このloop命令の実行を繰り返す。*/
	public static final int LOOP = 207;
	static {setArgType(LOOP, new ArgType(false, ARG_INSTS));}

	/** run [[instructions...]]
	 * <br>（予約された）構造化命令<br>
	 * 引数の命令列を実行することを表す。引数列はロックを取得してはならない。
	 * 引数実行中に失敗した場合、runの次の命令に進む。
	 * 引数実行中にproceed命令を実行した場合、次の命令に進む。
	 * <p>将来、明示的な引数付きのプロセス文脈のコンパイルに使用するために予約。*/
	public static final int RUN = 208;
	static {setArgType(RUN, new ArgType(false, ARG_INSTS));}

	/** not [instructionlist]
	 * <br>（予約された）構造化命令<br>
	 * 引数の命令列を実行することを表す。引数列はロックを取得してはならない。
	 * 引数実行中に失敗した場合、notの次の命令に進む。
	 * 引数実行中にproceed命令を実行した場合、この命令が失敗する。
	 * <p>将来、否定条件のコンパイルに使用するために予約。*/
	public static final int NOT = 209;
	static {setArgType(NOT, new ArgType(false, ARG_LABEL));}

	// 組み込み機能に関する命令（仮） (210--215)
	//  -----  inline  [atom, inlineref]
	//  -----  builtin [class, method, [links...]]

	/** inline [atom, string, inlineref]
	 * <br>ガード命令、ボディ命令<br>
	 * アトム$atomに対して、inlinerefが指定するインライン命令を適用し、成功することを確認する。
	 * <p>inlinerefには現在、インライン番号を渡すことになっているが、
	 * <p>ボディで呼ばれる場合、典型的には、全てのリンクを張り直した直後に呼ばれる。*/
	public static final int INLINE = 210;
	static {setArgType(INLINE, new ArgType(false, ARG_ATOM, ARG_OBJ, ARG_INT));}

	/** builtin [class, method, [links...]]
	 * <br>（予約された）ボディ命令
	 * リンク参照$(links[i])の配列を唯一の引数としてJavaのクラスメソッドを呼び出す。
	 * <p>インタプリタ動作するときに組み込み機能を提供するために使用する。
	 * <p>通常は、$builtin(class,method):(X1,…,Xn)に対応し、引数の種類によって次のものが渡される。
	 * 型付きプロセス文脈は、膜から除去したヘッド出現（またはガード生成したもの）が渡される。
	 * ヘッドとボディに1回ずつ出現するリンクは、ヘッドでのリンク出現が渡される。
	 * ボディに2回出現するリンクは、X=Xで初期化された後、各出現をヘッドでの出現と見なして渡される。*/
	public static final int BUILTIN = 211;
	static {setArgType(BUILTIN, new ArgType(false, ARG_OBJ, ARG_OBJ, ARG_OBJ));} // あってる。

	///////////////////////////////////////////////////////////////////////

	// 型付きプロセス文脈を扱うための追加命令 (216--219)	

	/** eqground [link1,link2]
	 * <br>（予約された）拡張ガード命令<br>
	 * （どちらかが基底項プロセスを指すとわかっている）
	 * 2つのリンクlink1とlink2に対して、
	 * それらが同じ形状の基底項プロセスであることを確認する。
	 * @see isground */
	public static final int EQGROUND = 216;
	static {setArgType(EQGROUND, new ArgType(false, ARG_VAR, ARG_VAR));}

	/** copyground [-dstlink, srclink, dstmem]
	 * （基底項プロセスを指す）リンク$srclinkを$dstmemに複製し、$dstlinkに格納する。
	 * @see isground */
	public static final int COPYGROUND = 217;
	static {setArgType(COPYGROUND, new ArgType(true, ARG_VAR, ARG_VAR, ARG_MEM));}
		
	/** removeground [srclink,srcmem]
	 * $srcmemに属する（基底項プロセスを指す）リンク$srclinkを現在の膜から取り出す。
	 * 実行アトムスタックは操作しない。
	 */
	public static final int REMOVEGROUND = 218;
	static {setArgType(REMOVEGROUND, new ArgType(false, ARG_VAR, ARG_MEM));}
	
	/** freeground [srclink]
	 * 基底項プロセス$srclinkがどの膜にも属さず、かつスタックに積まれていないことを表す。
	 */
	public static final int FREEGROUND = 219;
	static {setArgType(FREEGROUND, new ArgType(false, ARG_VAR));}
	
	// 型検査のためのガード命令 (220--229)	

	/** isground [-natomsfunc, link, srcset]
	 * <br>（予約された）ロック取得する拡張ガード命令<br>
	 * リンク$linkの指す先が基底項プロセスであることを確認する。
	 * すなわち、リンク先から（戻らずに）到達可能なアトムが全てこの膜に存在していることを確認する。
	 * ただし、$srcsetに登録されたアトムに到達したら失敗する。
	 * 見つかった基底項プロセスを構成するこの膜のアトムの個数（をラップしたInteger）を$natomsに格納する。
     * 
     * <p>natomsとnmemsと統合した命令を作り、$natomsの総和を引数に渡すようにする。
     * 子膜の個数の照合は、本膜がロックしていない子膜の個数が0個かどうか調べればよい。
     * しかし本膜がロックしたかどうかを調べるメカニズムが今は備わっていないため、保留。
     * 
     * groundには膜は含まれないことになったので、上記は不要
     * */
	public static final int ISGROUND = 220;
	static {setArgType(ISGROUND, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));}
	
	/** isunary [atom]
	 * <br>ガード命令<br>
	 * アトム$atomが1引数のアトムであることを確認する。*/
	public static final int ISUNARY     = 221;
	public static final int ISUNARYFUNC = ISUNARY + OPT;
	static {setArgType(ISUNARY, new ArgType(false, ARG_ATOM));}
	static {setArgType(ISUNARYFUNC, new ArgType(false, ARG_ATOM));}

	/** isint [atom]
	 * <br>ガード命令<br>
	 * アトム$atomが整数アトムであることを確認する。*/
	public static final int ISINT    = 225;
	public static final int ISFLOAT  = 226;
	public static final int ISSTRING = 227;
	static {setArgType(ISINT, new ArgType(false, ARG_ATOM));}
	static {setArgType(ISFLOAT, new ArgType(false, ARG_ATOM));}
	static {setArgType(ISSTRING, new ArgType(false, ARG_ATOM));}

	/** isintfunc [func]
	 * <br>最適化用ガード命令<br>
	 * ファンクタ$funcが整数ファンクタであることを確認する。*/
	public static final int ISINTFUNC    = ISINT    + OPT;
	public static final int ISFLOATFUNC  = ISFLOAT  + OPT;
	public static final int ISSTRINGFUNC = ISSTRING + OPT;
	static {setArgType(ISINTFUNC, new ArgType(false, ARG_VAR));}
	static {setArgType(ISFLOATFUNC, new ArgType(false, ARG_VAR));}
	static {setArgType(ISSTRINGFUNC, new ArgType(false, ARG_VAR));}

	/** getclass [-stringatom, atom]
	 * <br>出力するガード命令<br>
	 * アトム$atomがObjectFunctorまたはそのサブクラスのファンクタを持つことを確認し、
	 * 格納されたオブジェクトのクラスの完全修飾名文字列を表すファンクタを持つアトムを生成し、
	 * $stringatomに代入する。
	 * ただし、Translator を利用した場合、同一ソースのInlineコードで定義されたクラスに関しては単純名を取得する。(2005/10/17 Mizuno )*/
	public static final int GETCLASS = 228;
	static {setArgType(GETCLASS, new ArgType(true, ARG_ATOM, ARG_ATOM));}
	/** getclassfunc [-stringfunc, func]
	 * <br>出力するガード命令<br>
	 * ファンクタ$funcがObjectFunctorまたはそのサブクラスであることを確認し、
	 * 格納されたオブジェクトのクラスの完全限定（修飾）名文字列を表すファンクタを生成し、
	 * $stringfuncに代入する。
	 * ただし、Translator を利用した場合、同一ソースのInlineコードで定義されたクラスに関しては単純名を取得する。(2005/10/17 Mizuno )*/
	public static final int GETCLASSFUNC = 228 + OPT;
	static {setArgType(GETCLASSFUNC, new ArgType(true, ARG_VAR, ARG_VAR));}

	///////////////////////////////////////////////////////////////////////

	// 分散拡張用の命令 (230--239)
	/** getruntime [-dstatom, srcmem]
	 * <br>失敗しない分散拡張用ガード命令<br>
	 * 膜$srcmem（を管理するタスク）が所属するノードを表す文字列ファンクタを持つ
	 * 所属膜を持たない文字列アトムを生成し、$dstatomに代入する。
	 * ただし上記の仕様はルート膜のときのみ。ルート膜でない膜に対しては空文字列が得られる。
	 * <p>ルールの左辺に{..}@Hがあるときに使用される。文字列を使うのは仮仕様だがおそらく変えない。*/
	public static final int GETRUNTIME = 230;
	static {setArgType(GETRUNTIME, new ArgType(true, ARG_ATOM, ARG_MEM));}
	
	/** connectruntime [srcatom]
	 * 	 	 * <br>分散拡張用ガード命令<br>
	 * アトム$srcatomが文字列ファンクタを持つことを確認し、
	 * その文字列が表すノードに接続できることを確認する。
	 * <p>文字列が空のときはつねに成功する。
	 * <p>ルールの右辺に{..}@Hがあるときに使用される。文字列を使うのは仮仕様だがおそらく変えない。
	 *
	 * 追記(nakajima: 2004-01-12) 
	 * 方法7（分散版）では、指定ホストにVMが無かったら作る。あったら生存確認。
	 * 新方式1では、生存確認のみ。新規作成はボディ命令に移動。
	 * */
	public static final int CONNECTRUNTIME = 231;
	static {setArgType(CONNECTRUNTIME, new ArgType(false, ARG_ATOM));}

	/////////////////////////////////////////////////////////////////////////
	
	// アトムセットを操作するための命令 ( 240--249)
	
	/** newset [-dstset]
	 * 新しいアトムセットを作る
	 */
	public static final int NEWSET = 240;
	static {setArgType(NEWSET, new ArgType(true,ARG_VAR));}
	
	/** addatomtoset [srcset, atom]
	 * $atomをアトムセット$srcsetに追加する
	 */
	public static final int ADDATOMTOSET = 241;
	static {setArgType(ADDATOMTOSET, new ArgType(false, ARG_VAR, ARG_ATOM));}


	///////////////////////////////////////////////////////////////////////

	// 整数用の組み込みボディ命令 (400--419+OPT)
	/** iadd [-dstintatom, intatom1, intatom2]
	 * <br>整数用の組み込み命令<br>
	 * 整数アトムの加算結果を表す所属膜を持たない整数アトムを生成し、$dstintatomに代入する。
	 * <p>idivおよびimodに限り失敗する。*/
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
	static {setArgType(IADD, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));}
	static {setArgType(ISUB, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));}
	static {setArgType(IMUL, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));}
	static {setArgType(IDIV, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));}
	static {setArgType(INEG, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));}
	static {setArgType(IMOD, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));}
	static {setArgType(INOT, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));}
	static {setArgType(IAND, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));}
	static {setArgType(IOR, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));}
	static {setArgType(IXOR, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));}
	
	/** isal [-dstintatom, intatom1, intatom2]
	 * <br>整数用の組み込み命令<br>
	 * $intatom1を$intatom2ビット分符号つき(算術)左シフトした結果を表す所属膜を持たない整数アトムを生成し、$dstintatomに代入する。*/
	public static final int ISAL = 414;
	static {setArgType(ISAL, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));}
	/** isar [-dstintatom, intatom1, intatom2]
	 * <br>整数用の組み込み命令<br>
	 * $intatom1を$intatom2ビット分符号つき(算術)右シフトした結果を表す所属膜を持たない整数アトムを生成し、$dstintatomに代入する。*/
	public static final int ISAR = 415;
	static {setArgType(ISAR, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));}
	/** ishr [-dstintatom, intatom1, intatom2]
	 * <br>整数用の組み込み命令<br>
	 * $intatom1を$intatom2ビット分論理右シフトした結果を表す所属膜を持たない整数アトムを生成し、$dstintatomに代入する。*/
	public static final int ISHR = 416;
	static {setArgType(ISHR, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));}

	/** iaddfunc [-dstintfunc, intfunc1, intfunc2]
	 * <br>整数用の最適化用組み込み命令<br>
	 * 整数ファンクタの加算結果を表す整数ファンクタを生成し、$dstintfuncに代入する。
	 * <p>idivfuncおよびimodfuncに限り失敗する。*/
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
	static {setArgType(IADDFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));}
	static {setArgType(ISUBFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));}
	static {setArgType(IMULFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));}
	static {setArgType(IDIVFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));}
	static {setArgType(INEGFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));}
	static {setArgType(IMODFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));}
	static {setArgType(INOTFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));}
	static {setArgType(IANDFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));}
	static {setArgType(IORFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));}
	static {setArgType(IXORFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));}
	static {setArgType(ISALFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));}
	static {setArgType(ISARFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));}
	static {setArgType(ISHRFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));}

	// 整数用の組み込みガード命令 (420--429+OPT)

	/** ilt [intatom1, intatom2]
	 * <br>整数用の組み込みガード命令<br>
	 * 整数アトムの値の大小比較が成り立つことを確認する。*/
	public static final int ILT = 420;
	public static final int ILE = 421;
	public static final int IGT = 422;
	public static final int IGE = 423;	
	public static final int IEQ = 424;	
	public static final int INE = 425;	
	static {setArgType(ILT, new ArgType(false, ARG_ATOM, ARG_ATOM));}
	static {setArgType(ILE, new ArgType(false, ARG_ATOM, ARG_ATOM));}
	static {setArgType(IGT, new ArgType(false, ARG_ATOM, ARG_ATOM));}
	static {setArgType(IGE, new ArgType(false, ARG_ATOM, ARG_ATOM));}
	static {setArgType(IEQ, new ArgType(false, ARG_ATOM, ARG_ATOM));}
	static {setArgType(INE, new ArgType(false, ARG_ATOM, ARG_ATOM));}

	/** iltfunc [intfunc1, intfunc2]
	 * <br>整数用の最適化用組み込みガード命令<br>
	 * 整数ファンクタの値の大小比較が成り立つことを確認する。*/
	public static final int ILTFUNC = ILT + OPT;
	public static final int ILEFUNC = ILE + OPT;
	public static final int IGTFUNC = IGT + OPT;
	public static final int IGEFUNC = IGE + OPT;
	static {setArgType(ILTFUNC, new ArgType(false, ARG_VAR, ARG_VAR));}
	static {setArgType(ILEFUNC, new ArgType(false, ARG_VAR, ARG_VAR));}
	static {setArgType(IGTFUNC, new ArgType(false, ARG_VAR, ARG_VAR));}
	static {setArgType(IGEFUNC, new ArgType(false, ARG_VAR, ARG_VAR));}

	// 浮動小数点数用の組み込みボディ命令 (600--619+OPT)
	public static final int FADD = 600;
	public static final int FSUB = 601;
	public static final int FMUL = 602;
	public static final int FDIV = 603;
	public static final int FNEG = 604;
	static {setArgType(FADD, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));}
	static {setArgType(FSUB, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));}
	static {setArgType(FMUL, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));}
	static {setArgType(FDIV, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));}
	static {setArgType(FNEG, new ArgType(true, ARG_ATOM, ARG_ATOM, ARG_ATOM));}
	
	public static final int FADDFUNC = FADD + OPT;
	public static final int FSUBFUNC = FSUB + OPT;
	public static final int FMULFUNC = FMUL + OPT;
	public static final int FDIVFUNC = FDIV + OPT;
	public static final int FNEGFUNC = FNEG + OPT;
	static {setArgType(FADDFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));}
	static {setArgType(FSUBFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));}
	static {setArgType(FMULFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));}
	static {setArgType(FDIVFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));}
	static {setArgType(FNEGFUNC, new ArgType(true, ARG_VAR, ARG_VAR, ARG_VAR));}
	
	// 浮動小数点数用の組み込みガード命令 (620--629+OPT)
	public static final int FLT = 620;
	public static final int FLE = 621;
	public static final int FGT = 622;
	public static final int FGE = 623;	
	public static final int FEQ = 624;	
	public static final int FNE = 625;	
	static {setArgType(FLT, new ArgType(false, ARG_ATOM, ARG_ATOM));}
	static {setArgType(FLE, new ArgType(false, ARG_ATOM, ARG_ATOM));}
	static {setArgType(FGT, new ArgType(false, ARG_ATOM, ARG_ATOM));}
	static {setArgType(FGE, new ArgType(false, ARG_ATOM, ARG_ATOM));}
	static {setArgType(FEQ, new ArgType(false, ARG_ATOM, ARG_ATOM));}
	static {setArgType(FNE, new ArgType(false, ARG_ATOM, ARG_ATOM));}

	public static final int FLTFUNC = FLT + OPT;
	public static final int FLEFUNC = FLE + OPT;
	public static final int FGTFUNC = FGT + OPT;
	public static final int FGEFUNC = FGE + OPT;
	static {setArgType(FLTFUNC, new ArgType(false, ARG_VAR, ARG_VAR));}
	static {setArgType(FLEFUNC, new ArgType(false, ARG_VAR, ARG_VAR));}
	static {setArgType(FGTFUNC, new ArgType(false, ARG_VAR, ARG_VAR));}
	static {setArgType(FGEFUNC, new ArgType(false, ARG_VAR, ARG_VAR));}

	// ここもBUILTIN 命令にすべきである。
	public static final int FLOAT2INT = 630;
	public static final int INT2FLOAT = 631;
	static {setArgType(FLOAT2INT, new ArgType(true, ARG_ATOM, ARG_ATOM));}
	static {setArgType(INT2FLOAT, new ArgType(true, ARG_ATOM, ARG_ATOM));}
	public static final int FLOAT2INTFUNC = FLOAT2INT + OPT;
	public static final int INT2FLOATFUNC = INT2FLOAT + OPT;
	static {setArgType(FLOAT2INTFUNC, new ArgType(true, ARG_VAR, ARG_VAR));}
	static {setArgType(INT2FLOATFUNC, new ArgType(true, ARG_VAR, ARG_VAR));}
	

	// TODO （拡張性向上）BUILTIN 命令を使う方がよいと思われる
//	public static final int FSIN = 640;
//	public static final int FCOS = 641;
//	public static final int FTAN = 642;
    
    //グループ化に関する命令
    /** group [subinsts]
     * subinsts 内部の命令列
     * sakurai
     */
    public static final int GROUP = 2000;
	static {setArgType(GROUP, new ArgType(false, ARG_INSTS));}
    
   
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
	public int getIntArg6(){
		return ((Integer)data.get(5)).intValue();
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
	public Object getArg6() {
		return data.get(5);
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
     * @param actual 実引数列
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
	 * @deprecated
	 */
	public static Instruction react(Rule r, List memactuals, List atomactuals) {
		Instruction i = new Instruction(REACT);
		i.add(r);
		i.add(memactuals);
		i.add(atomactuals);
		i.add(new ArrayList());
		return i;
	}
	/**
	 * react 命令を生成する。
	 * @param r 反応できるルールオブジェクト
	 * @param memactuals 膜実引数のリスト。膜の変数番号からなる。
	 * @param atomactuals アトム実引数のリスト。アトムの変数番号からなる。
	 * @param varactuals その他の実引数のリスト。その他の変数番号からなる。
	 */
	public static Instruction react(Rule r, List memactuals, List atomactuals, List varactuals) {
		Instruction i = new Instruction(REACT);
		i.add(r);
		i.add(memactuals);
		i.add(atomactuals);
		i.add(varactuals);
		return i;
	}
	/**
	 * jump 命令を生成する。
	 * @param insts ジャンプ先のラベル付き命令列
	 * @param memactuals 膜実引数のリスト。膜の変数番号からなる。
	 * @param atomactuals アトム実引数のリスト。アトムの変数番号からなる。
	 * @param varactuals その他の実引数のリスト。その他の変数番号からなる。
	 */
	public static Instruction jump(InstructionList insts, List memactuals, List atomactuals, List varactuals) {
		Instruction i = new Instruction(JUMP);
		i.add(insts);
		i.add(memactuals);
		i.add(atomactuals);
		i.add(varactuals);
		return i;
	}
	/**
	 * commit 命令を生成する。
	 * @param r 反応するルールオブジェクト
	 */
	public static Instruction commit(Rule r) {
		Instruction i = new Instruction(COMMIT);
		i.add(r);
		return i;
	}
    /** resetvars 命令を生成する */
    public static Instruction resetvars(List memargs, List atomargs, List varargs) {
    	Instruction i = new Instruction(RESETVARS);
    	i.add(memargs);
    	i.add(atomargs);
    	i.add(varargs);
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
//	/** newlink 命令を生成する
//	 * @deprecated */
//	public static Instruction newlink(int atom1, int pos1, int atom2, int pos2) {
//		return new Instruction(NEWLINK,atom1,pos1,atom2,pos2);
//	}
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
    
    /** fail擬似命令を生成する */
	public static Instruction fail() {
		InstructionList label = new InstructionList();
		label.add(new Instruction(PROCEED));
		return new Instruction(Instruction.NOT, label);
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
	public Instruction(int kind, int arg1, Object arg2, int arg3) {
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
	
	/** パーザー用コンストラクタ */
	public Instruction(String name, List data) {
		try {
	    	Field f = Instruction.class.getField(name.toUpperCase());
	    	this.kind = f.getInt(null);
	    	this.data = data;
	    	return;
		} catch (NoSuchFieldException e) {
		} catch (IllegalAccessException e) {
		}
		//例外発生時
		throw new RuntimeException("invalid instruction name : " + name);
    }

    public Object clone() {
		Instruction c = new Instruction();
		c.kind = this.kind;
		Iterator it = this.data.iterator();
		while (it.hasNext()) {
			Object o = it.next();
			if (o instanceof ArrayList) {
				c.data.add(((ArrayList)o).clone());
			} else if (kind != Instruction.JUMP && o instanceof InstructionList) {
				c.data.add(((InstructionList)o).clone());
			} else {
				c.data.add(o);
			}
		}
		return c;
	}
	
	//////////////////////////////////
	// 最適化器が使う、命令列書き換えのためのクラスメソッド
	// @author Mizuno
	
	// todo argtype は signature に名称変更するとよい
	
	private static void setArgType(int kind, ArgType argtype) {	
		if (argTypeTable.containsKey(new Integer(kind))) {
			throw new RuntimeException("setArgType for '" + kind + "' was called more than once");
		}
		argTypeTable.put(new Integer(kind), argtype);
	}
	private static class ArgType {
		
		boolean output;
		int[] type;
		ArgType(boolean output) {
			this.output = output;
			type = new int[0];
		}
		ArgType(boolean output, int arg1) {
			this.output = output;
			type = new int[] {arg1};
		}
		ArgType(boolean output, int arg1, int arg2) {
			this.output = output;
			type = new int[] {arg1, arg2};
		}
		ArgType(boolean output, int arg1, int arg2, int arg3) {
			this.output = output;
			type = new int[] {arg1, arg2, arg3};
		}
		ArgType(boolean output, int arg1, int arg2, int arg3, int arg4) {
			this.output = output;
			type = new int[] {arg1, arg2, arg3, arg4};
		}
		ArgType(boolean output, int arg1, int arg2, int arg3, int arg4, int arg5) {
			this.output = output;
			type = new int[] {arg1, arg2, arg3, arg4, arg5};
		}
		ArgType(boolean output, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6){
			this.output = output;
			type = new int[] {arg1, arg2, arg3, arg4, arg5, arg6};
		}
	}

	public ArrayList getVarArgs() {
		ArrayList ret = new ArrayList();
		ArgType argtype = (ArgType)argTypeTable.get(new Integer(getKind()));
		int i = 0;
		if (getOutputType() != -1) {
			i = 1;
		}
		for (; i < data.size(); i++) {
			switch (argtype.type[i]) {
				case ARG_ATOM:
				case ARG_MEM:
				case ARG_VAR:
					ret.add(getArg(i));
			}
		}
		return ret;
	}
	/**
	 * 与えられた対応表によって、ボディ命令列中のアトム変数を書き換える。<br>
	 * 命令列中の変数が、対応表のキーに出現する場合、対応する値に書き換えます。
	 * 
	 * @param list 書き換える命令列
	 * @param map 変数の対応表。
	 */
	public static void changeAtomVar(List list, Map map) {
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Instruction inst = (Instruction)it.next();
			ArgType argtype = (ArgType)argTypeTable.get(new Integer(inst.getKind()));
			for (int i = 0; i < inst.data.size(); i++) {
				switch (argtype.type[i]) {
					case ARG_ATOM:
						changeArg(inst, i+1, map);
						break;
				}
			}
		}
	}
	/**
	 * 与えられた対応表によって、ボディ命令列中の膜変数を書き換える。<br>
	 * 命令列中の変数が、対応表のキーに出現する場合、対応する値に書き換えます。
	 * 
	 * @param list 書き換える命令列
	 * @param map 変数の対応表。
	 */
	public static void changeMemVar(List list, Map map) {
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Instruction inst = (Instruction)it.next();
			ArgType argtype = (ArgType)argTypeTable.get(new Integer(inst.getKind()));
			for (int i = 0; i < inst.data.size(); i++) {
				switch (argtype.type[i]) {
					case ARG_MEM:
						changeArg(inst, i+1, map);
						break;
				}
			}
		}
	}
	/**
	 * 与えられた対応表によって、ボディ命令列中のアトム・膜以外の変数を書き換える。<br>
	 * 命令列中の変数が、対応表のキーに出現する場合、対応する値に書き換えます。
	 * 
	 * @param list 書き換える命令列
	 * @param map 変数の対応表。
	 */
	public static void changeOtherVar(List list, Map map) {
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Instruction inst = (Instruction)it.next();
			ArgType argtype = (ArgType)argTypeTable.get(new Integer(inst.getKind()));
			for (int i = 0; i < inst.data.size(); i++) {
				switch (argtype.type[i]) {
					case ARG_VAR:
						changeArg(inst, i+1, map);
						break;
				}
			}
		}
	}
	/**
	 * 与えられた対応表によって、ボディ命令列中のすべての変数を書き換える。<br>
	 * 命令列中の変数が、対応表のキーに出現する場合、対応する値に書き換えます。
	 * 
	 * @param list 書き換える命令列
	 * @param map 変数の対応表。
	 */
	public static void applyVarRewriteMap(List list, Map map) {
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Instruction inst = (Instruction)it.next();
			ArgType argtype = (ArgType)argTypeTable.get(new Integer(inst.getKind()));
			for (int i = 0; i < inst.data.size(); i++) {
				switch (argtype.type[i]) {
					case ARG_MEM:
					case ARG_ATOM:
					case ARG_VAR:
						changeArg(inst, i+1, map);
						break;
					case ARG_LABEL:
						if (inst.getKind() == JUMP) break; // JUMP命令のLABEL引数はただのラベルなので除外
						applyVarRewriteMap( ((InstructionList)inst.data.get(i)).insts, map);
						break;
					case ARG_INSTS:
						applyVarRewriteMap( (List)inst.data.get(i), map);
						break;
					case ARG_VARS:
						ListIterator li = ((List)inst.data.get(i)).listIterator();
						while (li.hasNext()) {
							Object varnum = li.next();
							if (map.containsKey(varnum)) li.set(map.get(varnum));
						}
						break;
				}
			}
			if (inst.getKind() == RESETVARS || inst.getKind() == CHANGEVARS) break;
		}
	}
	/** 命令列後半部分に対して変数番号を付け替える */
	public static void applyVarRewriteMapFrom(List list, Map map, int start) {
		applyVarRewriteMap( list.subList(start, list.size()), map );
	}

	////////////////////////////////////////////////////////////////
	
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

	/** 指定された命令列中で変数が参照される回数を返す（代入は含まない）
	 * @param list   命令列
	 * @param varnum 変数番号
	 * @author n-kato */
	public static int getVarUseCount(List list, Integer varnum) {
		int count = 0;
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Instruction inst = (Instruction)it.next();
			ArgType argtype = (ArgType)argTypeTable.get(new Integer(inst.getKind()));
			int i = 0;
			if (argtype.output) i++;
			for (; i < inst.data.size(); i++) {
				switch (argtype.type[i]) {
					case ARG_MEM:
					case ARG_ATOM:
					case ARG_VAR:
						if (inst.data.get(i).equals(varnum)) count++;
						break;
					case ARG_LABEL:
						if (inst.getKind() == JUMP) break; // JUMP命令のLABEL引数はただのラベルなので除外
						count += getVarUseCount( ((InstructionList)inst.data.get(i)).insts, varnum);
						break;
					case ARG_INSTS:
						count += getVarUseCount( (List)inst.data.get(i), varnum);
						break;
					case ARG_VARS:
						Iterator it2 = ((List)inst.data.get(i)).iterator();
						while (it2.hasNext()) {
							if (it2.next().equals(varnum)) count++;
						}
						break;
				}
			}
		}
		return count;
	}
	/** 指定された命令列後半部分で変数が参照される回数を返す（代入は含まない）
	 * @param list   命令列
	 * @param varnum 変数番号
	 * @param start  開始位置
	 * @see getVarUseCount */
	public static int getVarUseCountFrom(List list, Integer varnum, int start) {
		return getVarUseCount( list.subList(start, list.size()), varnum );
	}
	
	////////////////////////////////////////////////////////////////
	/**
	 * この命令が出力命令の場合、出力の種類を返す。
	 * そうでない場合、-1を返す。
	 */
	public int getOutputType() {
		ArgType argtype = (ArgType)argTypeTable.get(new Integer(kind));
		if (argtype.output) {
			return argtype.type[0];
		} else {
			return -1;
		}
	}
	/** この命令が副作用を持つ可能性があるかどうかを返す。不明な場合trueを返さなければならない。
	 * ただし膜のロック取得は副作用とは見なさない。
	 * <p>どうやら、従来「ガード命令」と呼んでいたものに相当するらしい。*/
	public boolean hasSideEffect() {
		// todo 水野君方式のかっこいい管理にして正しく実装する予定
		switch (getKind()) {
			case Instruction.DEREF:
			case Instruction.DEREFATOM:
			case Instruction.DEREFLINK:
			case Instruction.DEREFFUNC:
			case Instruction.GETFUNC:
			case Instruction.FUNC:
			case Instruction.EQATOM:
			case Instruction.SAMEFUNC:
			case Instruction.GETMEM:
			case Instruction.GETPARENT:
			case Instruction.LOADFUNC:
			case Instruction.GETLINK:
			case Instruction.ALLOCLINK:
			case Instruction.ALLOCMEM:
			case Instruction.LOOKUPLINK:
			case Instruction.LOCK:
			case Instruction.GETRUNTIME:
			case Instruction.ISINT: case Instruction.ISUNARY:
			case Instruction.IADD: case Instruction.IADDFUNC:
			case Instruction.IEQ: case Instruction.ILT: case Instruction.ILE:
			case Instruction.IGT: case Instruction.IGE: case Instruction.INE:
			case Instruction.FEQ: case Instruction.FLT: case Instruction.FLE:
			case Instruction.FGT: case Instruction.FGE: case Instruction.FNE:
				return false;
		}
		return true;
	}
	/** この命令が制御動作をする可能性があるかどうかを返す。
	 * 制御動作とは失敗や反復などを表す。不明な場合trueを返さなければならない。*/
	public boolean hasControlEffect() {
		// todo 水野君方式のかっこいい管理にして正しく実装する予定
		switch (getKind()) {
			case Instruction.DEREFATOM:
			case Instruction.DEREFFUNC:
			case Instruction.GETFUNC:
			case Instruction.GETMEM:
			case Instruction.LOADFUNC:
			case Instruction.GETLINK:
			case Instruction.ALLOCLINK:
			case Instruction.ALLOCMEM:
			case Instruction.LOOKUPLINK:
			case Instruction.IADD: case Instruction.IADDFUNC:
				return false;
			case Instruction.DEREF:
			case Instruction.DEREFLINK:
			case Instruction.IDIV: case Instruction.IDIVFUNC:
			case Instruction.LOCK:
			case Instruction.GETPARENT:
				return true;
		}
		return true;
	}
	
	//////////////////////////////////
	//
	// デバッグ用表示メソッド
	//


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
				if (f.getName().startsWith("ARG_")) continue; //added by mizuno
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
	 * 命令のkind (int)を与えると、該当する命令の名前 (String) を返してくれる。
	 *
	 * @author NAKAJIMA Motomu <nakajima@ueda.info.waseda.ac.jp>
	 * @return String
	 * 
	 */
	public static String getInstructionString(int kind){
		String answer = "";
		answer = (String)instructionTable.get(new Integer(kind));
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
    public String toString() {
		//nakajima版2004-01-21
		StringBuffer buffer = new StringBuffer("               ");
		String tmp = getInstructionString(kind);
		ArgType argtype = (ArgType)argTypeTable.get(new Integer(kind));
		int indent = 14;
		if (argtype.output) {
			indent -= data.get(0).toString().length() + 2;
			buffer.delete(indent,14);
		}
		if( tmp.length() > indent ) {
			buffer.replace(0, indent, tmp.substring(0, indent - 2) + "..");
		} else {
			buffer.replace(0, tmp.length(), tmp);
		}
		if (data.size() == 1 && data.get(0) instanceof ArrayList) {
			ArrayList arg1 = (ArrayList)data.get(0);
			if (arg1.size() == 1 && arg1.get(0) instanceof ArrayList) {
				ArrayList insts = (ArrayList)arg1.get(0);
				if(insts.size() == 0) {
					buffer.append("[[]]");
				} else {
					buffer.append("[[\n");
					int i;
					for(i = 0; i < insts.size()-1; i++){
						buffer.append("                  ");
						buffer.append(insts.get(i));
						buffer.append(", \n");
					}
					buffer.append("                  ");
					buffer.append(insts.get(i));
					buffer.append(" ]]");
					return buffer.toString();
				}
			}
		}
		
		if (kind != Instruction.JUMP && data.size() >= 1 && data.get(0) instanceof InstructionList) {
			List insts = ((InstructionList)data.get(0)).insts;
			if(insts.size() == 0) {
				buffer.append("[]");
			} else {
				if(Env.compileonly) buffer.append("[[\n");
				else buffer.append("[\n");
				int i;
				for(i = 0; i < insts.size()-1; i++){
					//アトム主導テストの命令列を見やすく(?)する sakurai
//					if(((Instruction)insts.get(i)).getKind() == Instruction.GROUP
//						|| ((Instruction)insts.get(i)).getKind() == Instruction.COMMIT){
//						buffer.append("\n");
//					}
					buffer.append("                  ");
					buffer.append(insts.get(i));
					//TODO 出力引数だったらインデントを下げる.
					buffer.append(", \n");
				}
				buffer.append("                  ");
				buffer.append(insts.get(i));
				for(int j = 1; j < data.size(); j++){
					buffer.append("                  ");
					buffer.append("     ");
					buffer.append(", " + data.get(j));
				}
				buffer.append(" ]]");
				return buffer.toString();
			}
		}

		buffer.append(data.toString());

		return buffer.toString();
    }

    
    /** spec命令の引数値を新しい値に更新する（暫定的措置）*/
    public void updateSpec(int formals, int locals) {
    	if (getKind() == SPEC) {
			data.set(0,new Integer(formals));
			data.set(1,new Integer(locals));
    	}
    }
    
	private void writeObject(java.io.ObjectOutputStream out) throws IOException{
		out.writeInt(kind);
		out.writeInt(data.size());
		Iterator it = data.iterator();
		while (it.hasNext()) {
			Object o = it.next();
			if (o instanceof Ruleset) {
				out.writeObject("Ruleset");
				((Ruleset)o).serialize(out);
			} else {
				out.writeObject("Other");
				out.writeObject(o);
			}
		}
	}
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		kind = in.readInt();
		data = new ArrayList();
		int size = in.readInt();
		for (int i = 0; i < size; i++) {
			String argtype = (String)in.readObject();
			if (argtype.equals("Ruleset")) {
				data.add(Ruleset.deserialize(in));
			} else {
				data.add(in.readObject());
			}
		}
	}
}
