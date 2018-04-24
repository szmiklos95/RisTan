package main;

import gameLogic.GameLogicException;
import graphics.GUI;


public class Main {

	/**
	 * @param args
	 *the command line arguments
	 */
	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
    	        // New game state
    	        gameLogic.GameState gameState = new gameLogic.GameState();
    	        // Init game action
    	        gameLogic.InitGameAction action = new gameLogic.InitGameAction();
    	        try { //Execute the action
    				gameState.executeAction(action);
    			} catch (GameLogicException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    	        
            	GUI gui = new GUI(gameState);
                gui.createAndShowGUI();
            }
        });
	}
}
