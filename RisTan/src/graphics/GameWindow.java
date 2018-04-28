package graphics;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import config.Config;

//TODO comment
public class GameWindow extends JPanel{
	
	
	private static final long serialVersionUID = 1L;

	public GameWindow(){
			
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

		// Chat
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		
		JPanel chatPanel = new Chat(CardSync.client, CardSync.settings.getPlayerName());
		this.add(chatPanel, gbc);
		
		// Game Board
		gbc.anchor = GridBagConstraints.CENTER;
		this.add(CardSync.gameBoard,gbc);
		
		
		CardSync.card_GameWindow = this;
	}
}
