/**
 * グラフのノードを表す
 * 現在位置などを保持
 */

package test.graph;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class GraphNode {
	
	/**
	 * 現在位置
	 */
	protected Point pos = null;
	
	/**
	 * ノードのラベル
	 */
	protected String label = null;
	
	/**
	 * 移動方向
	 */
	protected int dx = 0;
	protected int dy = 0;
	
	protected Rectangle area = null;
	
	/**
	 * 現在位置、ラベル、移動範囲を指定して初期化します
	 * @param label 表示ラベル
	 * @param pos 初期位置
	 * @param area 移動可能範囲
	 */
	public GraphNode(String label, Point pos, Rectangle area) {
		this.label = label;
		this.area = area;
		this.pos = pos;
	}
	
	public void setMoveDelta(double dx, double dy) {
		this.dx += dx;
		this.dy += dy;
	}
	
	public boolean isFixed() {
		return false;
	}
	
	public Point getPosition() {
		return this.pos;
	}
	
	public void move() {
		if (isFixed()) return;
		this.pos.x += Math.max(-5, Math.min(5, this.dx));
		this.pos.y += Math.max(-5, Math.min(5, this.dy));
		
		if (this.pos.x < area.getMinX()) {
			this.pos.x = (int)area.getMinX();
		} else if (this.pos.x > area.getMaxX()) {
			this.pos.x = (int)area.getMaxX();
		}
		
		if (this.pos.y < area.getMinY()) {
			this.pos.y = (int)area.getMinY();
		} else if (this.pos.y > area.getMaxY()) {
			this.pos.y = (int)area.getMaxY();
		}
		this.dx /= 2;
		this.dy /= 2;
	}
	
	public void paint(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		int w = fm.stringWidth(label) + 10;
		int h = fm.getHeight() + 4;
		g.setColor(new Color(128,200,255));
		g.fillRect(pos.x - w/2, pos.y - h / 2, w, h);
		g.setColor(Color.black);
		g.drawRect(pos.x - w/2, pos.y - h / 2, w-1, h-1);
		g.drawString(this.label, pos.x - (w-10)/2, (pos.y - (h-4)/2) + fm.getAscent());
	}
}