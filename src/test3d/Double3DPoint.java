package test3d;

import javax.vecmath.Vector3d;
import javax.vecmath.Point3d;


public class Double3DPoint {
	public double x, y, z;
	public Double3DPoint(double nx, double ny, double nz) {
		x = nx;
		y = ny;
		z = nz;
	}
	public Double3DPoint(Point3d p) {
		x = p.x;
		y = p.y;
		z = p.z;
	}
	public Double3DPoint(){
		this(0.0, 0.0, 0.0);
	}
	public Double3DPoint(Vector3d p) {
		this(p.x, p.y, p.z);
	}
	public Vector3d toPoint() {
		return new Vector3d((double)x, (double)y, (double)z);
	}
}