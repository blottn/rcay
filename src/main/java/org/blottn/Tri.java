package org.blottn;

public class Tri {
	
	public Point3D a,b,c;		// edges are [ab] [bc] [ca]

	public Tri() {
		a = new Point3D();
		b = new Point3D();
		c = new Point3D();
	}

	public boolean overlaps(double relang, Point3D origin) {

		// check a is on opposite side of b of relang
		double relA_x, relA_z;
		relA_z = a.getZ() - origin.getZ();
		relA_x = a.getX() - origin.getX();

		double relB_x, relB_z;
		relB_z = b.getZ() - origin.getZ();
		relB_x = b.getX() - origin.getX();
		
		if (relB_x == 0 || relA_x == 0) {
			return true;	//avoid div by 0
		}

		double bAng = (relB_z / relB_x);
		double aAng = (relA_z / relA_x);
		
		if (aAng >= relang && bAng <= relang) {
			return true;
		}
		if (aAng <= relang && bAng >= relang) {
			return true;
		}

		return false;
	}

}
