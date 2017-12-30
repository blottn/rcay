package org.blottn;

import java.awt.Color;
import java.util.*;

public class World {
	
	public Color fog = Color.GREEN;

	public Point3D cameraPos;	//render from here

	List<Entity> ents;

	public World(List<Entity> ents) {
		this.ents = ents;
		cameraPos = new Point3D();
	}

	public void setPos(double x, double y, double z) {
		cameraPos.setX(x);
		cameraPos.setY(y);
		cameraPos.setZ(z);
	}

	public void moveBy(double x, double y) {
		cameraPos.setX(cameraPos.getX() + x);
		cameraPos.setY(cameraPos.getY() + y);

	}

//	public RayIntercept trace(double relang, double er) {
	public RayIntercept trace(Ray ray) {	//fog is 100 units away
		RayIntercept result = new RayIntercept(Color.WHITE, 100); 	//return fog in worst case
		for (Entity ent: ents) {
			//RayIntercept current = ent.check(relang, er, cameraPos);
			RayIntercept current = ent.fancyCheck(ray);
			
			if (current.dist < result.dist) {
				result = current;
			}
		}
		return result;
	}
}
