package compile;

import java.util.*;
import runtime.*;

/*
 * 作成日: 2003/10/24
 *
 */

/**
 * <pre>
 * コンパイル時データ構造をルールオブジェクトに変換する。
 * ルールオブジェクトは命令列を持つ。
 * 
 * ので、機能はデータ構造（インスタンスの木） -> 命令列
 * 
 * 外部からは、( :- WORLD ) の形式で呼ばれることになる。
 * WORLD にはルールが含まれる場合もあるので再帰的に
 * ルールをコンパイルすることになる。
 * 
 * </pre>
 * 
 * @author hara(working)
 */
public class RuleCompiler {
}
