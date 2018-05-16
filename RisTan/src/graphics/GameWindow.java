package graphics;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import config.Config;

/**
 * A wrapper class for the game board. One of the cards in the CardLayuot.
 * 
 * @author Miklós
 *
 */
public class GameWindow extends JPanel {

	/**
	 * Default UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates this card. Sets up the game board JPanel. The board itself will be
	 * drawn later.
	 */
	public void Create() {
		this.removeAll(); // In case this function gets called multiple times
		this.setBackground(Config.GameWindow.background);

		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = Config.GUI.GridSettings.startingGridX;
		gbc.gridy = Config.GUI.GridSettings.startingGridY;
		gbc.insets = Config.GUI.GridSettings.defaultInsets;
		gbc.fill = GridBagConstraints.CENTER;

		// Chat
		// gbc.anchor = GridBagConstraints.FIRST_LINE_START;

		// JPanel chatPanel = new Chat(CardSync.client,
		// CardSync.settings.getPlayerName());
		// this.add(chatPanel, gbc);
		
		// Information panel
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		CardSync.infoWindow = new GameInformationWindow();
		CardSync.infoWindow.Create();
		this.add(CardSync.infoWindow.getPanel(),gbc);

		// Game Board
		gbc.anchor = GridBagConstraints.CENTER;
		this.add(CardSync.gameBoard, gbc);

		CardSync.card_GameWindow = this;
	}
}
