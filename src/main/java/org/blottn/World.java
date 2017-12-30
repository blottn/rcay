package org.blottn;

import java.awt.Color;
import java.util.*;

public class World {
	
	public Color fog = Color.GREEN;

	public double xPos, yPos;	//render from here
	public Point cameraPos;

	List<Entity> ents;

	public World(List<Entity> ents) {
		this.ents = ents;
		cameraPos = new Point();
	}

	public void setPos(double x, double y) {
		cameraPos.setX(x);
		cameraPos.setY(y);
	}

	public void moveBy(double x, double y) {
		cameraPos.setX(cameraPos.getX() + x);
		cameraPos.setY(cameraPos.getY() + y);

	}

	public RayIntercept trace(double relang, double er) {
		RayIntercept result = new RayIntercept(Color.WHITE, 100); 	//return fog in worst case
		for (Entity ent: ents) {
			RayIntercept current = ent.check(relang, er, cameraPos);
			if (current.dist < result.dist) {
				result = current;
			}
		}
		return result;
	}
}
