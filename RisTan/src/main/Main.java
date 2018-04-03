package main;

import graphics.MainMenu;

/*
 * ToDo:
 * - Hide MainMenu when the game starts (DrawingPanel)
 * - Bring back MainMenu when someone closes the DrawingPanel
 * - Modify the copied DrawingPanel and Hexagon classes to accommodate our own methods
 */

public class Main {

	/**
	 * @param args
	 *the command line arguments
	 */
	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	MainMenu mainmenu = new MainMenu();
                mainmenu.createAndShowGUI();
            }
        });
	}
}
