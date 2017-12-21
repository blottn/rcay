package org.blottn;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;

import java.util.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Rcay {

		static final Color floor_col = Color.GRAY;
		static final Color roof_col = Color.BLACK;
		static final Color fog_col = Color.GREEN;
		static final int width = 1000;
		static final int height = 500;
		static final double fog_h = 70;
		static final double fov = 90;

	public static void main(String[] args) throws Exception {

		double MOVEMENT_SPEED = 0.2;

		String title = "Rcay";

		ArrayList<Entity> ents = new ArrayList<Entity>();
		ents.add(new Entity(95,10));
		ents.add(new Entity(50,0));
		ents.add(new Entity(10,-30));
		ents.add(new Entity(20,-50));
		ents.add(new Entity(50,-10));
		ents.add(new Entity(70,-25));
		ents.add(new Entity(10,0));
		ents.add(new Entity(22,-20));


		final World world = new World(ents);

		Listener listener = new Listener(world);

		world.setPos(0,0);

		JFrame frame = new JFrame(title);
		frame.setSize(width,height);
		frame.show();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(listener);
		double[] movement;
		while (true) {
			try {
				Thread.sleep(5);
			} catch(Exception e) {}
			movement = new double[]{0,0};
			for (KeyEvent e : listener.inputs.keySet()) {
				if (e.getID() == KeyEvent.KEY_PRESSED) {
					if (e.getKeyChar() == 'w') {
						movement[0] += MOVEMENT_SPEED;
					}
					else if (e.getKeyChar() == 's') {
						movement[0] -= MOVEMENT_SPEED;
					}
					else if (e.getKeyChar() == 'a') {
						movement[1] -= MOVEMENT_SPEED;
					}
					else if (e.getKeyChar() == 'd') {
						movement[1] += MOVEMENT_SPEED;
					}
				}
				else if (e.getID() == KeyEvent.KEY_RELEASED) {
					if (e.getKeyChar() == 'w') {
						movement[0] -= MOVEMENT_SPEED;
					}
					else if (e.getKeyChar() == 's') {
						movement[0] += MOVEMENT_SPEED;
					}
					else if (e.getKeyChar() == 'a') {
						movement[1] += MOVEMENT_SPEED;
					}
					else if (e.getKeyChar() == 'd') {
						movement[1] -= MOVEMENT_SPEED;
					}
				}
			}
			
			world.xPos += movement[0];
			world.yPos += movement[1];

			listener.inputs.clear();
			render(frame.getGraphics(),world);
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
	/*	graphics.setColor(fog_col);*/
		int fog_delta = (int) height - (int)fog_h;
		fog_delta /= 2;

		graphics.fillRect(0,fog_delta,width,height - 2*fog_delta);

		// calculate degrees per pixel
		double degPP = fov / width;
		for (double i = 0 ; i < width ; i ++) {
			double deg = (i - (width / 2)) * degPP;
			RayIntercept ray = world.trace(Math.tan(Math.toRadians(deg)),Math.tan(Math.toRadians(4*degPP / 2)));
			graphics.setColor(ray.res);
			//height proportional to dist
			double fog_dist = 110.0;
			double min = 90;
			double delta = (height - min) / 2;
			double h = delta * (ray.dist/ fog_dist);		// amount to remove from top
			graphics.fillRect((int)i, (int)h, 1, (int) ((height) - (2*h)));
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

		public RayIntercept trace(double relang, double er) {
			RayIntercept result = new RayIntercept(Color.WHITE,100);	//worst case returns fog
			for (Entity ent: ents) {
				RayIntercept current = this.check(relang,er, ent);
				if (current.dist < result.dist) {
					result = current;
				}
			}
			return result;
		}

		private RayIntercept check(double relang, double er, Entity e) {
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
				return new RayIntercept(Color.WHITE, 100);
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

	public static class Listener implements KeyListener {
		World world;

		public HashMap<KeyEvent,Integer> inputs;

		public Listener(World world) {
			inputs = new HashMap<KeyEvent, Integer>();
			this.world = world;
		}

		@Override
		public void keyPressed(KeyEvent e) {
			inputs.put(e,null);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			inputs.put(e,null);
		}

		@Override
		public void keyTyped(KeyEvent e) {}	//unused

	}
}
