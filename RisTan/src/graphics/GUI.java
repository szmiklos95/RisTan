package graphics;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import config.Config;
import network.SerialClient;

/**
 * The main GUI frame with card layout.
 * 
 * @author Mikl�s
 *
 */
public class GUI extends JFrame {

	/**
	 * Default UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor. Sets the fields in CardSync and creates a menu bar.
	 */
	public GUI() {
		CardSync.Instantiate();

		CardSync.frame = new JFrame(Config.GUI.title);
		CardSync.frame.setMinimumSize(new Dimension(Config.GUI.frameMinimumWidth, Config.GUI.frameMinimumHeight));
		CardSync.settings = new NewGameSettings();
		CardSync.gameBoard = new GameBoard();
		CardSync.client = new SerialClient();
		CardSync.controller = CardSync.client.getController();

		new GameMenubar(); // Constructor call automatically creates a menu bar
	}

	/**
	 * This function implements the card layout.
	 * 
	 * @param pane
	 *            the container for the objects, cards
	 */
	public void addComponentsToPane(Container pane) {

		// Create the panel that contains the "cards".
		CardSync.cards = new JPanel(new CardLayout());

		// Create the "cards".

		CardSync.card_MainMenu.Create();
		CardSync.card_GameWindow = new GameWindow(); // Add an empty panel, otherwise the frame will be resized at the
														// beginning to fit the board
		CardSync.card_GameSettings.Create();
		CardSync.card_JoinWindow.Create();

		// The param is the card itself, the second is the command with which we can
		// call this card
		CardSync.cards.add(CardSync.card_MainMenu, Config.GUI.CardIDs.mainMenu);
		CardSync.cards.add(CardSync.card_GameWindow, Config.GUI.CardIDs.gameBoard);
		CardSync.cards.add(CardSync.card_GameSettings, Config.GUI.CardIDs.settings);
		CardSync.cards.add(CardSync.card_JoinWindow, Config.GUI.CardIDs.joinWindow);

		pane.add(CardSync.cards, BorderLayout.CENTER);
	}

	/**
	 * Create the GUI and shows it. For thread safety, this method should be invoked
	 * from the event-dispatching thread.
	 */
	public void createAndShowGUI() {

		CardSync.frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	
            	System.out.println("Closing the window.");

                    if(CardSync.server != null) CardSync.server.disconnect();
                    if(CardSync.client != null) CardSync.client.disconnect();
                    
                    try{
                    	System.exit(0);
                    }
                    catch(Exception err) {
                    	System.out.println(err);
                    }
            }
        });

		// Set up the content pane.
		addComponentsToPane(CardSync.frame.getContentPane());

		// Display the window.
		CardSync.frame.pack();
		CardSync.frame.setVisible(true);
	}

	/**
	 * Refreshes the game state.
	 * 
	 * @param gameState
	 *            the current game state
	 */
	public void refreshGameState(gameLogic.GameState gameState) {
		CardSync.setGameState(gameState);
	}

}
