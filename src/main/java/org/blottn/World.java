package org.blottn;

import java.awt.Color;
import java.util.*;

public class World {
	
	public Color fog - Color.GREEN;

	public double xPos, yPos;	//render from here

	double bX;
	double bY;

	List<Entity> ents;

	public World(List<Entity> ents) {
		this.ents = ents;
		bX = 50;
		bY = 1;
	}

	public void setPos(int x, int y) {
		this.xPos = x;
		this.yPos = y;
	}

	public RayIntercept trace(double relang, double er) {
		RayIntercept result = new RayIntercept(Color.WHITE, 100); 	//return fog in worst case
		for (Entity ent: ents) {
			RayIntercept current = this.check(relang, er, ent);
			if (current.dist < result.dist) {
				result = current;
			}
		}
		return result;
	}

	private RayIntercept check(double relang, double er, Entity er) {
		double fog_dist = 70;
		
		double relx = e.posX - xPos;
		double rely = e.posY - yPos;
		
		if (rely / relx + er > relang && rely / relx - er < relang) {
			// calculate distance
			double absRelX = Math.abs(relx);
			double absRely = Math.abs(rely);
			double dist = Math.sqrt((relX*relX) + (relY*relY));
			return new RayIntercept(Color.RED, dist);
		}
		else {
			return new RayIntercept(Color.WHITE, 100);
		}
	}
}
