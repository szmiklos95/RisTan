package graphics;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import config.Config;
import gameLogic.Player;

/**
 * The popup that appears when the game is finished. Draws the high scores.
 * 
 * @author Miklós
 *
 */
public class GameOverPopup {

	/**
	 * Constructor. Displays the high scores of the players. Highlights the winner.
	 * 
	 * @param container the parent container of this popup
	 */
	GameOverPopup(Container container) {

		JPopupMenu popup = new JPopupMenu("Popup menu");

		JPanel popupPanel = new JPanel();

		GridBagLayout gbl = new GridBagLayout();
		popupPanel.setLayout(gbl);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = Config.GUI.GridSettings.startingGridX;
		gbc.gridy = Config.GUI.GridSettings.startingGridY;
		gbc.insets = Config.GUI.GridSettings.defaultInsets;
		gbc.fill = GridBagConstraints.CENTER;

		JLabel label;

		// Game Over message
		label = new JLabel("Game Over!");
		gbl.setConstraints(label, gbc);
		popupPanel.add(label, gbc);

		// High scores
		gbc.gridy += 2;
		label = new JLabel("High scores:");
		gbl.setConstraints(label, gbc);
		popupPanel.add(label, gbc);

		// Player names
		List<Player> players = CardSync.controller.getPlayers();
		String winnerName = "No winner";
		int winnerScore = 0;

		for (Player p : players) {
			gbc.gridy++;
			label = new JLabel(p.getName());
			gbl.setConstraints(label, gbc);
			popupPanel.add(label, gbc);

			gbc.gridx++;
			label = new JLabel("" + p.getScore());
			gbl.setConstraints(label, gbc);
			popupPanel.add(label, gbc);
			gbc.gridx = 0;

			if (winnerScore < p.getScore()) {
				winnerName = p.getName();
				winnerScore = p.getScore();
			}
		}

		// Winner
		gbc.gridy += 2;
		gbc.gridx = 0;
		label = new JLabel("THE WINNER IS ***" + winnerName + "*** WITH A SCORE OF " + winnerScore + " !");
		gbl.setConstraints(label, gbc);
		popupPanel.add(label, gbc);

		// Cancel button
		gbc.gridx = 0;
		gbc.gridy++;

		JButton cancel = new JButton("Exit Game");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.print("Game Over! Closing the game.\n");
				System.exit(0);
			}
		});
		popupPanel.add(cancel, gbc);

		popup.add(popupPanel);

		popup.show(container, Config.GameBoard.width / 2 - Config.GameOverPopup.width / 2,
				Config.GameBoard.height / 2 - Config.GameOverPopup.height / 2);
	}

}
