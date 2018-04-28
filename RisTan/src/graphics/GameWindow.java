package graphics;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

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

		JPanel chatPanel = new Chat(CardSync.client, CardSync.settings.getPlayerName());
		this.add(chatPanel, gbc);
		
		gbc.gridwidth = Config.Chat.textAreaColoums;
		gbc.gridheight = Config.Chat.textAreaRows;
		
		this.add(CardSync.gameBoard,gbc);
		
		CardSync.card_GameWindow = this;
	}
}
