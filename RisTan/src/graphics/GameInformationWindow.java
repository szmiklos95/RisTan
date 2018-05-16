package graphics;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import config.Config;
import gameLogic.Player;
import gameLogic.Resource;
import gameLogic.TurnOrder;

/**
 * A window that displays important informations during a game.
 * 
 * @author Miklós
 *
 */
public class GameInformationWindow extends JPanel {

	/**
	 * Default UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The panel that contains the information
	 */
	JPanel infoPanel = new JPanel();

	/**
	 * Creates the information panel components
	 */
	public void Create() {
		GridBagLayout gbl = new GridBagLayout();
		infoPanel.setLayout(gbl);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = Config.GUI.GridSettings.startingGridX;
		gbc.gridy = Config.GUI.GridSettings.startingGridY;
		gbc.insets = Config.GUI.GridSettings.defaultInsets;
		gbc.fill = GridBagConstraints.CENTER;

		JLabel label;

		// Information panel message
		label = new JLabel("----- Information panel -----");
		gbl.setConstraints(label, gbc);
		infoPanel.add(label, gbc);
		
		// Information panel message
		gbc.gridy++;
		TurnOrder turnOrder = CardSync.getGameState().getTurnOrder();
		int remainingTurn = 0;
		if(turnOrder!=null) {
			remainingTurn = turnOrder.getRemainingTurn();
		}
		label = new JLabel("Remaining turns: "+remainingTurn);
		gbl.setConstraints(label, gbc);
		infoPanel.add(label, gbc);


		// Information panel message
		gbc.gridy++;
		label = new JLabel("Scores");
		gbl.setConstraints(label, gbc);
		infoPanel.add(label, gbc);

		// Scores
		List<Player> players = CardSync.controller.getPlayers();

		for (Player p : players) {
			gbc.gridy++;
			label = new JLabel(p.getName());
			gbl.setConstraints(label, gbc);
			infoPanel.add(label, gbc);

			gbc.gridx++;
			label = new JLabel("" + p.getScore());
			gbl.setConstraints(label, gbc);
			infoPanel.add(label, gbc);
			gbc.gridx = 0;
		}

		// Resources
		int localPlayerID = CardSync.controller.getLocalPlayerID();
		Player localPlayer = null;
		if(!players.isEmpty()) localPlayer = players.get(localPlayerID);
		
		int wood = 0;
		int stone = 0;
		int wheat = 0;
		
		if(localPlayer != null) {
			wood = localPlayer.getResourceAmount(Resource.Wood);
			stone = localPlayer.getResourceAmount(Resource.Stone);
			wheat = localPlayer.getResourceAmount(Resource.Wheat);
		}
		
		gbc.gridy++;
		label = new JLabel(Config.SystemMessages.YourTurn.wood + wood);
		gbl.setConstraints(label, gbc);
		infoPanel.add(label, gbc);

		gbc.gridy++;
		label = new JLabel(Config.SystemMessages.YourTurn.stone + stone);
		gbl.setConstraints(label, gbc);
		infoPanel.add(label, gbc);

		gbc.gridy++;
		label = new JLabel(Config.SystemMessages.YourTurn.wheat + wheat);
		gbl.setConstraints(label, gbc);
		infoPanel.add(label, gbc);
		
		// Remaining time
		if(CardSync.controller.isActivePlayer()) {
			int remainingTime = CardSync.getGameState().getTurn().getRemainingTime();
			gbc.gridy++;
			label = new JLabel(Config.SystemMessages.YourTurn.remainingTime + remainingTime);
			gbl.setConstraints(label, gbc);
			infoPanel.add(label, gbc);
		}
	}

	/**
	 * Returns the information panel
	 * 
	 * @return the information panel
	 */
	public JPanel getPanel() {

		return infoPanel;
	}

	/**
	 * Updates the info panel by removing recreating everything.
	 */
	public void updateInfoPanel() {
		infoPanel.removeAll();
		Create();
	}
}
