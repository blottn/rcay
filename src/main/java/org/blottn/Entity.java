package org.blottn;

import java.awt.Color;
import java.util.*;

public class Entity {

	public double posX, posY;

	public Collection<Tri> shape;

	public Entity(double x, double y) {
		shape = new ArrayList<Tri>();
		posX = x;
		posY = y;
		
		DEBUG();
	}

	private void DEBUG() {
		Tri debugFace = new Tri();
		
		debugFace.a.set(15,0,2);
		debugFace.b.set(15,0,-2);
		debugFace.c.set(15,2,0);

		addFace(debugFace);
	}

	public void addFace(Tri face) {
		shape.add(face);
	}

	
	public boolean overlaps(double relang, Point3D origin) {
		for (Tri tri : shape) {
			if (tri.overlaps(relang, origin)) {
				return true;
			}
		}
		return false;
	}

	public RayIntercept fancyCheck(Ray ray) {
		return new RayIntercept(Color.WHITE, 100);
	}


	public RayIntercept check(double relang, double er, Point origin) {
		double fog_dist = 70;

		double relx = posX - origin.getX();
		double rely = posY - origin.getY();

		if (rely / relx + er > relang && rely / relx - er < relang) {
			//calculate distance
			double absRelX = Math.abs(relx);
			double absRelY = Math.abs(rely);
			double dist = Math.sqrt((absRelX*absRelX) + (absRelY*absRelY));
			return new RayIntercept(Color.RED, dist);
		}
		else {
			return new RayIntercept(Color.WHITE, 100);
		}
	}

}
