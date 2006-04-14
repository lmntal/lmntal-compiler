/*
 * 作成日: 2006/04/04
 */
package debug;

import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class HelpFrame extends JFrame {
	public HelpFrame() {
		super("LMNtal Debugger Help");
		
		JTextPane jt = new JTextPane();
		jt.setEditable(false);
		jt.setContentType("text/html");
//		try {
//			FileReader fr = new FileReader("help.html");
//			BufferedReader br = new BufferedReader(fr);
//			StringBuffer buf = new StringBuffer();
//			String s = null;
//			while ((s = br.readLine()) != null) {
//				buf.append(s);
//			}
//			jt.setText(buf.toString());
			//TODO: ファイルから読み込むようにする
			String s =
				"<h1>LMNtalデバッガ ヘルプ</h1>"+
				"<h2>ブレークポイント</h2>"+
				"<ul>"+
				"<li>ブレークポイントを設定したいルールをクリックする"+
				"<li>もう一度クリックすると解除される"+
				"</ul>"+
				""+
				"<h2>実行</h2>"+
				"<li>Nextボタンでルールを1回適用して停止する（-gオプションのGo aheadと同じ動作）"+
				"<li>Continueボタンで，ブレークポイントに設定されたルールが適用されるまでルールを適用する"+
				"<li>Restartボタンで同じプログラムを最初から実行する"+
				"</ul>"+
				""+
				"<h2>その他</h2>"+
				"<ul>"+
				"<li>LineNumber<br>"+
				"行番号の表示を切替える"+
				"<li>Demo<br>"+
				"文字やアトムを大きくする"+
				"<li>ShowProfile<br>"+
				"--profileオプションで得られる情報を出力<br>"+
				"成功回数 / 適用回数 (実行時間 ms)"+
				"<li>GUI<br>"+
				"グラフィックの表示を切り替える"+
				"</ul>";
			jt.setText(s);
//		} catch (IOException e) {
//			System.err.println(e);
//		}

		JScrollPane scroll = new JScrollPane(jt);
		getContentPane().add("Center", scroll);
		pack();
		setVisible(true);
	}
}
