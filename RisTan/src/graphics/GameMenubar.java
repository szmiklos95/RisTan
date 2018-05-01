package graphics;

import java.awt.CardLayout;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import config.Config;


public class GameMenubar {
	
	JMenuBar bar = null;
	
	// Set menubar for frame
	// TODO: Move to a new method
	// TODO: Confirmation if game started and player wants to return to main menu
	public GameMenubar() {
		
		//If we already had a menubar disable it in case this function gets called multiple times
		if(CardSync.frame.getJMenuBar()!=null) CardSync.frame.getJMenuBar().setEnabled(false);
				
		bar = new JMenuBar();
		
		// File
		JMenu file = new JMenu("File");
		addMenuItem(Config.GUI.ButtonTexts.mainMenu, Config.GUI.CardIDs.mainMenu, file, switchCardAction);
		addMenuItem(Config.GUI.ButtonTexts.exit, null, file, exitAction);
		bar.add(file);	
		
		// Market
		JMenu market = new JMenu("Market");
		
		//TODO check for  || !CardSync.getGameState().getTurn().isTradeEnabled()
		if(CardSync.getGameState().isOver()) {
			JLabel over = new JLabel("The market is closed :( ");
			market.add(over);
		}
		else {
			JMenuItem offer = new JMenuItem("Make new offer");
			offer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MarketPopups.makeNewOffer(market);
				}
			});
			market.add(offer);
			
			JMenuItem accept = new JMenuItem("View offers");
			accept.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MarketPopups.viewOffers(market);
				}
			});
			market.add(accept);
			
			JMenuItem gameTrade = new JMenuItem("Trade with game");
			gameTrade.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MarketPopups.tradeWithGame(market);
				}
			});
			market.add(gameTrade);
		}
		
		bar.add(market);
		
		// Conventional menubar ends here
		bar.add(Box.createHorizontalGlue());
		// System messages
		JMenu textItem = new JMenu();
		textItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT); 
		
		bar.add(textItem);
		
		CardSync.frame.setJMenuBar(bar);
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
