package graphics;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import config.Config;
import network.Message;
import network.SerialServer;
import network.UdpServer;
import network.Message.eMsgType;

//TODO comment
public class SettingsWindow extends JPanel{
	
	private static final long serialVersionUID = 1L;

	public SettingsWindow() {
		
	}
	
	public void Create(){
		
		this.removeAll(); //In case this function gets called multiple times
		
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = Config.GUI.GridSettings.startingGridX;
		gbc.gridy = Config.GUI.GridSettings.startingGridY;
		gbc.insets = Config.GUI.GridSettings.defaultInsets;
		gbc.fill = GridBagConstraints.CENTER;

		// Settings label
		gbc.gridwidth = 2;

		Label settings_label = new Label("Hello " + CardSync.settings.getPlayerName() + "!");
		settings_label.setFont(new Font("Arial Bold", 0, 20));
		gbl.setConstraints(settings_label, gbc);
		this.add(settings_label);

		// Player count label
		gbc.gridwidth = 1;
		gbc.gridy += 5;

		JLabel label = new JLabel(Config.GUI.playerCount);
		gbl.setConstraints(label, gbc);

		this.add(label);

		// Spinner
		gbc.gridx++;

		JSpinner spinner_PCount = new JSpinner(new SpinnerNumberModel(Config.GUI.default_playerCount,
				Config.GUI.min_playerCount, Config.GUI.max_playerCount, 1));
		gbl.setConstraints(spinner_PCount, gbc);
		this.add(spinner_PCount);

		// OK button
		gbc.gridx = 0;
		gbc.gridy += 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 2;

		JButton button = new JButton(Config.GUI.ButtonTexts.ok);
		button.setActionCommand(Config.GUI.CardIDs.gameBoard);
		gbl.setConstraints(button, gbc);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int value_PCount = getSpinnerValue(spinner_PCount);
				System.out.print("Player count set to " + value_PCount + ".\n");
				CardSync.settings.setPlayerCount(value_PCount);

				SetupNewGame();
				DrawBoard();

				CardLayout cl = (CardLayout) (CardSync.cards.getLayout());
				cl.show(CardSync.cards, e.getActionCommand());
				System.out.print("Switching to " + e.getActionCommand() + " panel.\n");
			}
		});

		this.add(button);

		// Back to menu button
		gbc.gridy += 2;
		setButtonGrid(Config.GUI.ButtonTexts.mainMenu, Config.GUI.CardIDs.mainMenu, this, switchCardAction, gbc);

		// Exit button
		gbc.gridy += 4;
		setButtonGrid(Config.GUI.ButtonTexts.exit, null, this, exitAction, gbc);
		
		CardSync.card_GameSettings = this;

	}

	
	
	/**
	 * Returns the spinner value. Has protection against invalid user input.
	 * 
	 * @param spinner
	 * @return
	 */
	private int getSpinnerValue(JSpinner spinner) {
		try {
			spinner.commitEdit();
		} catch (java.text.ParseException pe) {
			System.out.print(pe);
		}
		return (Integer) spinner.getValue();
	}
	
	/**
	 * Starts a new server and joins to it. This function runs at the host player.
	 */
	private void SetupNewGame() {
		network.SerialServer server = new SerialServer();
		server.Connect(CardSync.settings.getPlayerCount());
		
		UdpServer udpServer = new UdpServer();
		udpServer.connect();

		// This is localhost IP address, connects the local client to the server
		ConnectTo(udpServer.getServerAddress().getHostAddress());  //TODO This should be editable or...?
		System.out.println("Setting up the server.");

	}

	private void ConnectTo(String IPstring) {
		// network.SerialClient client=new SerialClient();
		CardSync.client.Connect(IPstring);
		
		// Update game window card because we now know the player's name for the chat window
		CardSync.cards.remove(CardSync.card_GameWindow);
		CardSync.card_GameWindow.Create();
		CardSync.cards.add(CardSync.card_GameWindow, Config.GUI.CardIDs.gameBoard);
		
		CardSync.client.Send(new Message(eMsgType.Name, CardSync.settings.getPlayerName()));

		// gameLogic.ClientController controller=client.getController();
		CardSync.gameState = CardSync.controller.getGameState();
	}

	private void DrawBoard() {
		CardSync.gameState = CardSync.controller.getGameState();

		// Update the settings, and draw a new game window.
		CardSync.gameBoard = new GameBoard(CardSync.gameState);
		CardSync.cards.remove(CardSync.card_GameWindow);
		CardSync.card_GameWindow.Create();
		CardSync.cards.add(CardSync.card_GameWindow, Config.GUI.CardIDs.gameBoard);
		CardSync.frame.pack(); //Resizes the window to fit the board
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
	
	/**
	 * Creates a new button within the given container with GridBagLayout.
	 * 
	 * @param text
	 *            The display name of the button
	 * @param buttonAction
	 *            A string stored for firing an action with the action listener.
	 *            Leave null if not using switchCardAction.
	 * @param container
	 *            The container
	 * @param f
	 *            The method that will be called upon clicking on the button
	 * @param g
	 *            The constraints for the grid layout
	 */
	private void setButtonGrid(String text, String buttonAction, Container container, Function f,
			GridBagConstraints gbc) {
		JButton button = new JButton(text);
		button.setActionCommand(buttonAction);
		button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getMinimumSize().height));
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.doAction(e);
			}
		});
		container.add(button, gbc);
	}
	
}
