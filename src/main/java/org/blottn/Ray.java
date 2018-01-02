package org.blottn;

// a ray is a line through 3 dimensional space
// given by an offset value from the origin perpendicular to the direction
// and a vector to describe direction

public class Ray {

	public Point origin;
	
	public Vector direction;

	public Ray(Point origin) {
		this.origin = origin;
		direction = new Vector();
	}


}
