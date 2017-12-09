package org.blottn;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;

import java.util.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Rcay {

		static final Color floor_col = Color.GRAY;
		static final Color roof_col = Color.BLACK;
		static final Color fog_col = Color.GREEN;
		static final int width = 1000;
		static final int height = 500;
		static final double fog_h = 70;
		static final double fov = 90;

	public static void main(String[] args) throws Exception {
		String title = "Rcay";

		ArrayList<Entity> ents = new ArrayList<Entity>();
		ents.add(new Entity(50,10));
		ents.add(new Entity(50,-10));
		ents.add(new Entity(5,0));


		final World world = new World(ents);
		world.setPos(0,0);

		JFrame frame = new JFrame(title);
		frame.setSize(width,height);
		frame.show();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		while(true) {
			try {
				Thread.sleep(5);
			}
			catch (Exception e) {
				System.exit(1);
			}
			render(frame.getGraphics(),world);
			world.yPos = world.yPos + 0.01;
		}
	}

	public static void render(Graphics g, World world) {

		BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D graphics = frame.createGraphics();

		//do floor
		graphics.setColor(floor_col);
		graphics.fillRect(0,height / 2, width, height / 2);

		//do ceiling
		graphics.setColor(roof_col);
		graphics.fillRect(0,0,width,height / 2);

		//do fog
		graphics.setColor(fog_col);
		int fog_delta = (int) height - (int)fog_h;
		fog_delta /= 2;

		graphics.fillRect(0,fog_delta,width,height - 2*fog_delta);

		// calculate degrees per pixel
		double degPP = fov / width;
		for (double i = 0 ; i < width ; i ++) {
			double deg = (i - (width / 2)) * degPP;
			for (Entity ent : world.ents) {
				RayIntercept result = world.cast(Math.tan(Math.toRadians(deg)),Math.tan(Math.toRadians(4*degPP / 2)),ent);
				if (result != null) {	//hit something
					graphics.setColor(result.res);
					//height proportional to dist
					double fog_dist = 70.0;
					double min = 50;
					double delta = (height - min) / 2;
					double h = delta * (result.dist/ fog_dist);		// amount to remove from top
					graphics.fillRect((int)i, (int)h, 1, (int) ((height) - (2*h)));
				}
			}
		}
		g.drawImage(frame,0,0,null);
	}

	public static class World {
		
		public Color fog = Color.GREEN;

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

		public RayIntercept cast(double relang, double er, Entity e) {
			double fog_dist = 70;
			//relx and y
			double relx = e.posX - xPos;
			double rely = e.posY - yPos;
			if (rely / relx + er > relang && rely / relx - er < relang) {
				// calculate distance
				double relX,relY;
				relX = Math.abs(e.posX - xPos);
				relY = Math.abs(e.posY - yPos);
				double dist = Math.sqrt((relX*relX) + (relY*relY));
				return new RayIntercept(Color.RED, dist);
			}
			else {
				return null;
			}
		}

	}

	public static class RayIntercept {
		Color res;
		double dist;

		public RayIntercept(Color col, double d) {
			this.res = col;
			this.dist = d;
		}
	}
	public static class Entity {
		public double posX, posY;

		public Entity(double x, double y) {
			posX = x;
			posY = y;
		}
	}
}
