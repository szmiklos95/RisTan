/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import graphics.GControl;
import graphics.MainMenu;

/**
 *
 * @author Predi
 */
public class Main {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainMenu.createAndShowGUI();
            }
        });
	}
}
