package graphics;

import java.awt.CardLayout;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import config.Config;

public class GameMenubar {
	
	public GameMenubar() {
		// Set menubar for frame
		// TODO: Move to a new method
		// TODO: Confirmation if game started and player wants to return to main menu
		JMenuBar menubar = new JMenuBar();
		
		JMenu file = new JMenu("File");
		addMenuItem(Config.GUI.ButtonTexts.mainMenu, Config.GUI.CardIDs.mainMenu, file, switchCardAction);
		addMenuItem(Config.GUI.ButtonTexts.exit, null, file, exitAction);
		menubar.add(file);	
		
		// Conventional menubar ends here
		menubar.add(Box.createHorizontalGlue());
		// System messages
		JMenu textItem = new JMenu();
		textItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT); 
		
		menubar.add(textItem);
		
		CardSync.frame.setJMenuBar(menubar);
	}
	
	
	/**
	 * Adds a JMenuItem to a JMenu
	 * 
	 * @param text
	 * @param actionCommand
	 *            A string stored for firing an action with the action listener.
	 *            Leave null if not using switchCardAction.
	 * @param container
	 * @param f
	 *            Action listener function
	 */
	private void addMenuItem(String text, String actionCommand, Container container, Function f) {
		JMenuItem menuItem = new JMenuItem(text);
		menuItem.setActionCommand(actionCommand);

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.doAction(e);
			}
		});
		container.add(menuItem);
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
}
