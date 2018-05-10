package main;

import graphics.GUI;

public class Main {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				GUI gui = new GUI();
				gui.createAndShowGUI();
			}
		});
	}
}
