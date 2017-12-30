package org.blottn;

import java.awt.Color;

public class Entity {

	public double posX, posY;

	public Entity(double x, double y) {
		posX = x;
		posY = y;
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
