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

	public void moveBy(double x, double z) {
		cameraPos.setX(cameraPos.getX() + x);
		cameraPos.setZ(cameraPos.getZ() + z);

	}

	public List<Entity> select(double horizDegrees) {
		List<Entity> out = new ArrayList<Entity>();
		//create ray
		for (Entity ent : ents) {
			if (ent.overlaps(horizDegrees, cameraPos)) {
				out.add(ent);
			}
		}

		return out;
	}

	public RayIntercept trace(double relang, double er) {
//	public RayIntercept trace(Ray ray) {	//fog is 100 units away		// this is what i want to use in future
		RayIntercept result = new RayIntercept(Color.WHITE, 100); 	//return fog in worst case
		for (Entity ent: ents) {
			RayIntercept current = ent.check(relang, er, cameraPos);
			//RayIntercept current = ent.fancyCheck(ray);
			
			if (current.dist < result.dist) {
				result = current;
			}
		}
		return result;
	}
}
