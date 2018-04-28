package graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gameLogic.ClientController;
import gameLogic.GameState;
import network.SerialClient;

/**
 * Synchronisation class that contains all the necessary variables for
 * the JPanel cards the interact between each other
 * Package level visible
 * @author Miklós
 *
 */
class CardSync {
	static GameBoard gameBoard;
	static GameState gameState;
	static JFrame frame;
	static NewGameSettings settings;

	static JPanel cards; // a panel that uses CardLayout
	
	static MainMenu card_MainMenu;
	static GameWindow card_GameWindow; // need to store a reference because this will get updated later
	static SettingsWindow card_GameSettings;
	static JoinWindow card_JoinWindow;

	static SerialClient client = null;
	static ClientController controller = null;
	
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
}
