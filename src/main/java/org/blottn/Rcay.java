package org.blottn;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;

public class Rcay {
	public static void main(String[] args) throws Exception {
		int width = 1000;
		int height = 500;
		String title = "Rcay";

		JFrame frame = new JFrame(title) {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(Color.BLUE);
				g.fillRect(40,40,100,100);
			};
		};
		frame.setSize(width,height);
		frame.show();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
