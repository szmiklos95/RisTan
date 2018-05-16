package graphics;


import javax.swing.JFrame;
import javax.swing.JPanel;

import gameLogic.ClientController;
import gameLogic.GameState;
import network.SerialClient;
import network.SerialServer;

/**
 * Synchronisation class that contains all the necessary variables for
 * the JPanel cards to interact between each other
 * Package level visible
 * @author Miklós
 *
 */
class CardSync {
	/**
	 * GUI game board where the game is played.
	 */
	static GameBoard gameBoard;
	
	/**
	 * Holds every important status of the current game.
	 */
	private static GameState gameState;
	
	/**
	 * The frame of the window. Parent object for every JPanel.
	 */
	static JFrame frame;
	
	/**
	 * Used on the server side to set player numbers, etc.
	 */
	static NewGameSettings settings;
	
	/**
	 * A panel that uses CardLayout. Holds the JPanels that functions as cards.
	 */
	static JPanel cards;
	
	/**
	 * JPanel that functions as a card. The main menu, the first window to appear.
	 */
	static MainMenu card_MainMenu;
	
	/**
	 * JPanel that functions as a card. The game window that contains the game board.
	 */
	static GameWindow card_GameWindow;
	
	/**
	 * JPanel that functions as a card. The settings window when starting a new game (with new server).
	 */
	static SettingsWindow card_GameSettings;
	
	/**
	 * JPanel that functions as a card. The join window for clients.
	 */
	static JoinWindow card_JoinWindow;

	/**
	 * Reference to the server.
	 */
	static SerialServer server = null;
	
	/**
	 * Reference to the client.
	 */
	static SerialClient client = null;
	
	/**
	 * Reference to the client controller.
	 */
	static ClientController controller = null;
	
	/**
	 * Call this to instantiate the fields of this synchronisation class.
	 */
	static void Instantiate(){
		gameBoard = new GameBoard();
		frame = new JFrame();
		settings = new NewGameSettings();
		cards = new JPanel();
		card_MainMenu = new MainMenu();
		card_GameWindow = new GameWindow();
		card_GameSettings = new SettingsWindow();
		card_JoinWindow = new JoinWindow();
		client = new SerialClient();
		controller = new ClientController(client);
		
	}

	/**
	 * Gets the current game state from the client controller.
	 * @return The current game state
	 */
	public static GameState getGameState() {
		//Always update when getting a game state
		setGameState(controller.getGameState());
		return gameState;
	}

	/**
	 * Sets the game state only locally in this class. The game state cannot be modified from here.
	 * @param gameState The new game state
	 */
	public static void setGameState(GameState gameState) {
		CardSync.gameState = gameState;
	}
}
