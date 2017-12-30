package org.blottn;

// a ray is a line through 3 dimensional space
// given by an offset value from the origin perpendicular to the direction
// and a vector to describe direction

public class Ray {

	double offset;
	
	Vector3D direction;


	public Ray() {
		offset = 0;
		direction = new Vector3D();
	}
}
