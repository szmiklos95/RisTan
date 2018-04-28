package graphics;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import config.Config;
import network.Message;
import network.Message.eMsgType;

//TODO comment
public class JoinWindow extends JPanel {


	private static final long serialVersionUID = 1L;
	
	JoinWindow(){
		
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

		// Welcome label
		gbc.gridwidth = 2;

		Label settings_label = new Label("Hello " + CardSync.settings.getPlayerName() + "!");
		settings_label.setFont(new Font("Arial Bold", 0, 20));
		gbl.setConstraints(settings_label, gbc);
		this.add(settings_label);

		// IP address label
		gbc.gridwidth = 1;
		gbc.gridy += 5;

		JLabel label = new JLabel("IP address: ");
		gbl.setConstraints(label, gbc);

		this.add(label);

		// Input text field
		gbc.gridx++;
		
		JTextField ipField = new JTextField("127.0.0.1");
		ipField.setEditable(true);
		gbl.setConstraints(ipField, gbc);
		ipField.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				ipField.setText("");
			}
		});
		;
		this.add(ipField);


		// Connect button
		gbc.gridwidth = 1;
		gbc.gridy += 5;

		JButton join = new JButton("Connect");
		join.setActionCommand(Config.GUI.CardIDs.gameBoard);

		gbl.setConstraints(join, gbc);
		join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				ConnectTo(ipField.getText());

				DrawBoard();

				CardLayout cl = (CardLayout) (CardSync.cards.getLayout());
				cl.show(CardSync.cards, e.getActionCommand());
				System.out.print("Player " + CardSync.settings.getPlayerName() + " joining the game.\n");
			}
		});
		this.add(join);

		CardSync.card_JoinWindow = this;
	}
	
	private void ConnectTo(String IPstring) {
		// network.SerialClient client=new SerialClient();
		CardSync.client.Connect(IPstring);
		
		// Update game board card because we now know the player's name for the chat window
		CardSync.cards.remove(CardSync.card_GameWindow);
		CardSync.card_GameWindow.Create();
		CardSync.cards.add(CardSync.card_GameWindow, Config.GUI.CardIDs.gameBoard);
		
		CardSync.client.Send(new Message(eMsgType.Name, CardSync.settings.getPlayerName()));

		// gameLogic.ClientController controller=client.getController();
		CardSync.gameState = CardSync.controller.getGameState();
	}

	private void DrawBoard() {
		CardSync.gameState = CardSync.controller.getGameState();

		// Update the settings, and draw a new board.
		CardSync.gameBoard = new GameBoard(CardSync.gameState);
		CardSync.cards.remove(CardSync.card_GameWindow);
		CardSync.card_GameWindow.Create();
		CardSync.cards.add(CardSync.card_GameWindow, Config.GUI.CardIDs.gameBoard);
		CardSync.frame.pack(); //Resizes the window to fit the board
	}

}
