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

		static final double horiz_fov = 90;
		static final double vert_fov = 50;

	public static void main(String[] args) throws Exception {

		double MOVEMENT_SPEED = 0.2;

		String title = "Rcay";

		ArrayList<Entity> ents = new ArrayList<Entity>();
		ents.add(new Entity(95,10));
/*		ents.add(new Entity(50,0));
		ents.add(new Entity(10,-30));
		ents.add(new Entity(20,-50));
		ents.add(new Entity(50,-10));
		ents.add(new Entity(70,-25));
		ents.add(new Entity(10,0));
		ents.add(new Entity(22,-20));*/


		final World world = new World(ents);

		Listener listener = new Listener(world);

		world.setPos(-20,5,0);

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
			world.moveBy(movement[0], movement[1]);		

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
		double horiz_degPP = horiz_fov / width;
		double vert_degPP = vert_fov / height;

		for (double i = 0; i < width ; i++) {
			double horiz_deg = (i - (width / 2)) * horiz_degPP;
			List<Entity> selected = world.select(Math.tan(Math.toRadians(horiz_deg)));
			if (!selected.isEmpty()) {

				graphics.setColor(Color.BLUE);
				for (double j = 0 ; j < height ; j++) {
					
					graphics.fillRect((int) i,(int) j , 1, 1);
				}

			}
		}

		/*
		double degPP = horiz_fov / width;
		for (double i = 0 ; i < width ; i ++) {
			for (double j = 
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
		*/
		g.drawImage(frame,0,0,null);
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
