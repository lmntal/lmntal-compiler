/*
 * 作成日: 2006/03/18
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
package debug;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import runtime.Env;
import runtime.Rule;

class SourceTextComponent extends JTextPane {
	private boolean showProfile;
	
	public SourceTextComponent() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int offset = getCaretPosition();
				try {
					String text = getText(0, offset);
					int lineno = 0;
					for (int i = 0; i < text.length(); i++) {
						if (text.charAt(i) == '\n')
							lineno++;
					}
					Debug.toggleBreakPointAt(lineno);
					//System.out.println("lineno"+lineno);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
				repaint();
			}
		});
		setContentType("text/html");
		setEditable(false);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		final int SIZE = (Env.fDEMO  ? 25 : 18);
		
		// ブレークポイントの表示
		g.setColor(Color.red);
		Iterator iter = Debug.breakPointIterator();
		while (iter.hasNext()) {
			//g.fillRect(0, SIZE*(((Rule)iter.next()).lineno-1)+9, SIZE-8, SIZE-2);
			final int x = 0;
			final int y = SIZE*(((Integer)iter.next()).intValue()-1)+9;
			final int w = SIZE-(Env.fDEMO ? 5 : 2);
			final int h = SIZE-(Env.fDEMO ? 5 : 2);
			g.fillOval(x, y, w, h);
		}
		
		// 現在停止中のルールの表示
		g.setColor(Color.blue);
		int lineno = Debug.getCurrentRuleLineno();
		if (lineno > 0) {
			g.setColor(Color.blue);
			g.setXORMode(Color.black);
			g.fillRect(SIZE-4, SIZE*(lineno-1)+9, getWidth(), SIZE-2);
			g.setPaintMode();
		}
		
		if (Env.profile && showProfile) {
			g.setColor(Color.blue);
			//g.setFont(new Font("Monospace", Font.PLAIN, SIZE));
			Font font = new Font(getFont().getName(), Font.PLAIN, Env.fDEMO ? 16 : 12);
			g.setFont(font);
			Iterator rules = Debug.ruleIterator();
			if (rules != null) {
				while (rules.hasNext()) {
					Rule r = (Rule)rules.next();
					double time = (Env.majorVersion == 1 && Env.minorVersion > 4) ? r.time / 1000000 : r.time;
					String s = r.succeed + "/" + r.apply+ "(" + time + "ms)";
					g.drawString(s, getWidth()-(Env.fDEMO ? 110 : 70), SIZE*r.lineno+(Env.fDEMO ? 2 : 4));
				}
			}
		}
	}
	
	public void showProfile(boolean b) {
		showProfile = b;
	}
}