package org.blottn;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;

public class Rcay {
	public static void main(String[] args) throws Exception {

		final Color floor_col = Color.GRAY;
		final Color roof_col = Color.BLACK;
		final Color fog = Color.WHITE;
		final int width = 1000;
		final int height = 500;
		final double fov = 90;

		String title = "Rcay";

		final World world = new World();
		world.setPos(0,0);

		JFrame frame = new JFrame(title) {
			@Override
			public void paint(Graphics g) {
				super.paint(g);

				//do floor

				g.setColor(floor_col);
				g.fillRect(0,height / 2, width, height / 2);
				//do ceiling
				g.setColor(roof_col);
				g.fillRect(0,0,width,height / 2);

				// calculate degrees per pixel
				double degPP = fov / width;
				for (double i = 0 ; i < width ; i ++) {
					double deg = (i - (width / 2)) * degPP;
					RayIntercept result = world.cast(Math.tan(Math.toRadians(deg)),Math.tan(Math.toRadians(4*degPP / 2)));
					g.setColor(result.res);
				
					//height proportional to dist
					double fog_dist = 70.0;
					// d = 0, h = height;
					// d = 70, h = min_h;
					
					double min = 50;
					double delta = (height - min) / 2;
					double h = delta * (result.dist/ fog_dist);		// amount to remove from top
					g.fillRect((int)i,(int)h,1,(int) ((height) - (2*h)));
				}
			};
		};
		frame.setSize(width,height);
		frame.show();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}


	public static class World {
		
		public Color fog = Color.WHITE;

		int xPos, yPos;

		double bX;
	   	double bY;

		public World() {
			bX = 50;
			bY = 1;
		}


		public void setPos(int x, int y) {
			this.xPos = x;
			this.yPos = y;
		}

		public RayIntercept cast(double relang, double er) {
			double fog_dist = 70;
			if (bY / bX + er > relang && bY / bX - er < relang) {
				return new RayIntercept(Color.RED, 40);
			}
			else {
				return new RayIntercept(fog, 70);
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


}
