package graphics;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import config.Config;
import network.SerialClient;

/**
 * The main GUI frame with card layout
 * 
 * @author Miklós
 *
 */
public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	public GUI() {
		CardSync.Instantiate();
		
		CardSync.frame = new JFrame(Config.GUI.title);
		CardSync.frame.setMinimumSize(new Dimension(Config.GUI.frameMinimumWidth,Config.GUI.frameMinimumHeight));
		CardSync.settings = new NewGameSettings();
		CardSync.gameBoard = new GameBoard();
		CardSync.client = new SerialClient();
		CardSync.controller = CardSync.client.getController();
		
		
		// Set menubar for frame
		// TODO: Move to a new method
		// TODO: Confirmation if game started and player wants to return to main menu
		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		addMenuItem(Config.GUI.ButtonTexts.mainMenu, Config.GUI.CardIDs.mainMenu, file, switchCardAction);
		addMenuItem(Config.GUI.ButtonTexts.exit, null, file, exitAction);
		menubar.add(file);
		
		CardSync.frame.setJMenuBar(menubar);
	}

	/**
	 * This function implements the card layout.
	 * 
	 * @param pane
	 *            The container for the objects, cards
	 */
	public void addComponentsToPane(Container pane) {
		
		// Create the panel that contains the "cards".
		CardSync.cards = new JPanel(new CardLayout());

		// Create the "cards".

		CardSync.card_MainMenu.Create();
		CardSync.card_GameWindow = new GameWindow(); // Add an empty panel, otherwise the frame will be resized at the beginning to fit the board
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
	 * Create the GUI and show it. For thread safety, this method should be invoked
	 * from the event-dispatching thread.
	 */
	public void createAndShowGUI() {

		CardSync.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
	 *            The current game state
	 */
	public void refreshGameState(gameLogic.GameState gameState) {
		CardSync.gameState = gameState;
	}

	/*
	 * *****************************************************************************
	 * 
	 * @@@@@@@@@@@@@@@@@@@@@@@@@@ Helper (private)
	 * methods @@@@@@@@@@@@@@@@@@@@@@@@@@
	 *****************************************************************************/

	// *********** JPanel items ***********//

	/**
	 * Adds a JMenuItem to a JMenu
	 * 
	 * @param text
	 * @param actionCommand
	 *            A string stored for firing an action with the action listener.
	 *            Leave null if not using switchCardAction.
	 * @param container
	 * @param f
	 *            Action listener function
	 */
	private void addMenuItem(String text, String actionCommand, Container container, Function f) {
		JMenuItem menuItem = new JMenuItem(text);
		menuItem.setActionCommand(actionCommand);

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.doAction(e);
			}
		});
		container.add(menuItem);
	}

	// *********** Interface functions ***********//
	/**
	 * Interfaces and Actions for button press
	 */
	private interface Function {
		void doAction(ActionEvent e);
	}

	/**
	 * Do this when the exit button is pressed
	 */
	private final Function exitAction = new Function() {
		public void doAction(ActionEvent e) {
			System.out.print("Closing the game.\n");
			System.exit(0);
		}
	};

	/**
	 * Used for switching between cards (JPanels) on the JFrame
	 */
	private final Function switchCardAction = new Function() {
		public void doAction(ActionEvent e) {
			CardLayout cl = (CardLayout) (CardSync.cards.getLayout());
			cl.show(CardSync.cards, e.getActionCommand());
			System.out.print("Switching to " + e.getActionCommand() + " panel.\n");
		}
	};

}
