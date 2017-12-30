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
}
