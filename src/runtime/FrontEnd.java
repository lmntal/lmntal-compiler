/*
 * 作成日: 2003/10/22
 */
package runtime;

import java.io.FileInputStream;
import java.io.SequenceInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.FileNotFoundException;
import java.lang.SecurityException;
import java.util.*;

import compile.*;
import compile.parser.*;

/**
 * LMNtal のメイソ
 * 
 * 
 * 作成日: 2003/10/22
 */
public class FrontEnd {
	/**
	 * 全ての始まり。
	 * 
	 * <pre>
	 * コマンドライン引数
	 *   なし                       → REPL 起動
	 *   ファイル名                 → ファイルの中身を実行して終わる
	 *   -e [LMNtal oneliner]       → [LMNtal oneliner] （１行文）を実行して終わる
	 *   -d                         → デバッグモード
	 *   --help                     → ヘルプを表示
	 * </pre>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		FileInputStream fis = null;
		InputStream is = null;
		Reader src = null;
		/**
		 * コマンドライン引数があったらファイルの中身を解釈実行
		 */
		for(int i = 0; i < args.length;i++){
			// 必ずlength>0, '-'ならオプション
			if(args[i].charAt(0) == '-'){
				if(args[i].length() < 2){ // '-'のみの時
					System.out.println("不明なオプション:" + args[i]);
					System.exit(-1);					
				}else{ // オプション解釈部
					switch(args[i].charAt(1)){
					case 'd':
						System.out.println("debug mode");
						Env.debug = 1;
						break;
					case 't':
						Env.fTrace = true;
						break;
					case 's':
						Env.fRandom = true;
						break;
					case 'e':
						// lmntal -e 'a,(a:-b)'
						// 形式で実行できるようにする。like perl
						// この書き方は汚い気がするけど...。by Hara
						REPL.processLine(args[i+1]);
						System.exit(-1);
						break;
					case '-': // 文字列オプション
						if(args[i].equals("--help")){
							System.out.println("usage: FrontEnd [-d] filename");
						}else{
							System.out.println("不明なオプション:" + args[i]);
							System.exit(-1);
						}
						break;
					default:
						System.out.println("不明なオプション:" + args[i]);
						System.exit(-1);						
					}							
				}
			}else{ // '-'以外で始まるものはファイルとみなす
				try{
					fis = new FileInputStream(args[i]);
				}catch(FileNotFoundException e){
					System.out.println("ファイルが見つかりません:" + args[i]);
					System.exit(-1);
				}catch(SecurityException e){
					System.out.println("ファイルが開けません:" + args[i]);
					System.exit(-1);
				}
				if(is == null) is = fis;
				else is = new SequenceInputStream(is, fis); // ソースファイルを連結
			}
		}
		
		// ソースなしならREPL, ありならソースを解釈実行。
		if(is == null){
			REPL.run();
		}else{			
			run( new BufferedReader(new InputStreamReader(is)) );
		}
	}
	
	/**
	 * 与えられたソースについて、一連の実行を行う。
	 * @param src Reader 型で表されたソース
	 */
	public static void run(Reader src) {
		try{
			LMNParser lp = new LMNParser(src);
				
			compile.structure.Membrane m = lp.parse();
			Env.d("");
			Env.d( "After parse   : "+m );
			
			compile.structure.Membrane root = RulesetCompiler.runStartWithNull(m);
			InterpretedRuleset ir = (InterpretedRuleset)root.rulesets.get(0);
			Env.d( "After compile : "+ir );
			root.showAllRule();
			
			// 実行
			LMNtalRuntime rt = new LMNtalRuntime();
			ir.react(rt.getGlobalRoot());
			if (Env.fTrace) {
				Env.d( "Before execute : " );
				Env.p( Dumper.dump(rt.getGlobalRoot()) );
			}
			rt.exec();
			if (!Env.fTrace) {
				Env.d( "After execute : " );
				Env.p( Dumper.dump(rt.getGlobalRoot()) );
			}
		} catch (Exception e) {
			Env.e("!! catch !! "+e.getMessage()+"\n"+Env.parray(Arrays.asList(e.getStackTrace()), "\n"));
		}
	}
}
