package org.blottn;

public class Point3D extends Point {

	double z;

	public Point3D() {
		super();
		z = 0;
	}
	
	public double getZ() {
		return z;
	}

	public void setZ(double newZ) {
		this.z = newZ;
	}

	public void set(double newX, double newY, double newZ) {
		this.setX(newX);
		this.setY(newY);
		this.setZ(newZ);
	}
	
	@Override
	public String toString() {
		return "[x:" + x + ", y:" + y + ", z:" + z + "]";
	}
}
