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
import java.io.IOException;
import java.lang.SecurityException;

/**
 * LMNtal のメイン
 * 
 * <pre>
 * TODO 名前は FrontEnd でいいんだろうか。
 *       案：FrontEnd
 *           素直に Main
 * </pre>
 * 
 * 作成日: 2003/10/22
 */
public class FrontEnd {
	/**
	 * 全ての始まり
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		FileInputStream fis = null;
		InputStream is = null;
		Reader src = null;
		
		/**
		 * TODO コマンドライン引数があったらファイルの中身を解釈実行
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
		
		if(is == null){
			/* こうできるといいなぁと妄想
			src = new REPL();
			 */
			//file指定がなければこれを呼ぶ
			REPL repl = new REPL();
			repl.run();
		}else{
			src = new BufferedReader(new InputStreamReader(is));
		}
		
		// srcを構文解析に渡す。
		koubun_kaiseki(src); // ダミー
		try{
			src.close();
		}catch(IOException e){
			System.out.println("ファイルがクローズできません");
			System.exit(-1);
		}
		// 計算ノードに、得られた初期化ルールを渡して呼び出す
	}
	
	static void koubun_kaiseki(Reader src){
		// ソースをダンプするだけ
		int i;
		while(true){
			try{
				i = src.read();
				if(i == -1) break;
				System.out.write(i);
			}catch(IOException e){
				System.out.println("file dump error");
				System.exit(-1);
			}
		}
	}
}
