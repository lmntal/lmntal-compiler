package gui;

import java.awt.Point;

/** double型の位置(x,y)の設定 */
public class DoublePoint {
	public double x, y;
	public DoublePoint(double nx, double ny) {
		x = nx;
		y = ny;
	}
	public DoublePoint() {
		this(0.0, 0.0);
	}
	public DoublePoint(Point p) {
		this(p.x, p.y);
	}
	public Point toPoint() {
		return new Point((int)x, (int)y);
	}
}
