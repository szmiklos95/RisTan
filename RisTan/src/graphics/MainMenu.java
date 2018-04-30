package graphics;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import config.Config;
import network.UdpClient;

//TODO comment
public class MainMenu extends JPanel{
	

	private static final long serialVersionUID = 1L;
	
	
	public MainMenu() {
		
	}
	
	public void Create() {
		this.removeAll(); //In case this function gets called multiple times

		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = Config.GUI.GridSettings.startingGridX;
		gbc.gridy = Config.GUI.GridSettings.startingGridY;
		gbc.insets = Config.GUI.GridSettings.defaultInsets;
		gbc.fill = GridBagConstraints.CENTER;

		// Main menu
		Label mainMenu = new Label("Main menu");
		mainMenu.setFont(new Font("Arial Bold", 0, 20));
		gbl.setConstraints(mainMenu, gbc);
		this.add(mainMenu);

		// Enter name
		gbc.gridy++;

		TextField name = new TextField("Enter your name");
		name.setEditable(true);
		gbl.setConstraints(name, gbc);
		name.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				name.setText("");
			}
		});

		this.add(name);

		// New game
		gbc.gridy += 2;
		gbc.gridwidth = Config.GUI.GridSettings.defaultGridWidth;
		gbc.gridheight = Config.GUI.GridSettings.defaultGridHeight;

		JButton create = new JButton(Config.GUI.ButtonTexts.newGame);
		create.setActionCommand(Config.GUI.CardIDs.settings);
		gbl.setConstraints(create, gbc);
		create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				CardSync.settings.setPlayerName(new String(name.getText()));
				
				CardSync.frame.setTitle(Config.GUI.nameTitle + name.getText());

				// Need to update card
				CardSync.cards.remove(CardSync.card_GameSettings);
				CardSync.card_GameSettings.Create();
				CardSync.cards.add(CardSync.card_GameSettings, Config.GUI.CardIDs.settings);

				CardLayout cl = (CardLayout) (CardSync.cards.getLayout());
				cl.show(CardSync.cards, e.getActionCommand());
				System.out.print("Switching to " + e.getActionCommand() + " panel.\n");
			}
		});
		this.add(create);

		// Join
		gbc.gridy += 2;

		JButton join = new JButton(Config.GUI.ButtonTexts.join);
		join.setActionCommand(Config.GUI.CardIDs.joinWindow);

		gbl.setConstraints(join, gbc);
		join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				CardSync.settings.setPlayerName(new String(name.getText()));
				
				CardSync.frame.setTitle(Config.GUI.nameTitle + name.getText());
				
				UdpClient udpclient = new UdpClient();
				udpclient.connect();

				// Need to update card
				CardSync.cards.remove(CardSync.card_JoinWindow);
				CardSync.card_JoinWindow.setUdpClient(udpclient);
				CardSync.card_JoinWindow.Create();
				CardSync.cards.add(CardSync.card_JoinWindow, Config.GUI.CardIDs.joinWindow);

				CardLayout cl = (CardLayout) (CardSync.cards.getLayout());
				cl.show(CardSync.cards, e.getActionCommand());
				System.out.print("Switching to " + e.getActionCommand() + " panel.\n");
			}
		});
		this.add(join);

		// Exit
		gbc.gridy += 10;
		gbc.gridheight += 10;

		setButtonGrid(Config.GUI.ButtonTexts.exit, null, this, exitAction, gbc);
		
		CardSync.card_MainMenu = this;

	}
	
	
	
	
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

}
