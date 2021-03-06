package graphics;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import config.Config;
import gameLogic.AcceptTradeAction;
import gameLogic.Market;
import gameLogic.OfferTradeAction;
import gameLogic.Resource;
import gameLogic.TradeOffer;
import gameLogic.TradeWithGameAction;

/**
 * The class that handles every pop up window and message related to using the
 * market.
 * 
 * @author Mikl�s
 *
 */
public class MarketPopups {

	/**
	 * The received resource amount spinner for tradeWithGame. Needs to be editable
	 * within a stateChanged listener.
	 */
	static private JSpinner received_resource_amount = null;

	/**
	 * Draws the pop up window for making new offers.
	 * 
	 * @param container
	 *            the parent container of this pop up window
	 */
	static void makeNewOffer(Container container) {
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

		// Given resource type label
		label = new JLabel("Offered resource type: ");
		gbl.setConstraints(label, gbc);
		popupPanel.add(label, gbc);

		// Given resource type
		gbc.gridx++;

		String[] resourceStrings = Config.ResourceNames.resourceStrings;

		JComboBox<String> given_resource = new JComboBox<String>(resourceStrings);
		given_resource.setSelectedIndex(0);
		popupPanel.add(given_resource, gbc);

		// Given resource amount label
		gbc.gridx = 0;
		gbc.gridy++;

		label = new JLabel("Offered resource amount: ");
		gbl.setConstraints(label, gbc);
		popupPanel.add(label, gbc);

		// Given resource amount spinner
		gbc.gridx++;
		JSpinner given_resource_amount = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
		popupPanel.add(given_resource_amount, gbc);

		// Desired resource type label
		gbc.gridx = 0;
		gbc.gridy++;

		label = new JLabel("Desired resource type: ");
		gbl.setConstraints(label, gbc);
		popupPanel.add(label, gbc);

		// Desired resource type
		gbc.gridx++;

		JComboBox<String> desired_resource = new JComboBox<String>(resourceStrings);
		desired_resource.setSelectedIndex(0);
		popupPanel.add(desired_resource, gbc);

		// Desired resource amount label
		gbc.gridx = 0;
		gbc.gridy++;

		label = new JLabel("Desired resource amount: ");
		gbl.setConstraints(label, gbc);
		popupPanel.add(label, gbc);

		// Desired resource amount spinner
		gbc.gridx++;
		JSpinner desired_resource_amount = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
		popupPanel.add(desired_resource_amount, gbc);

		// Ok button
		gbc.gridx = 0;
		gbc.gridy++;

		JButton ok = new JButton("Ok"+" (Time: "+Config.Action.OfferTradeAction.time+")");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int offererID = CardSync.getGameState().getActivePlayer().getID();
				Resource give = getResource((String) given_resource.getSelectedItem());
				int give_amount = getSpinnerValue(given_resource_amount);
				Resource take = getResource((String) desired_resource.getSelectedItem());
				int take_amount = getSpinnerValue(desired_resource_amount);
				TradeOffer offer = new TradeOffer(offererID, give, give_amount, take, take_amount);
				CardSync.controller.sendAction(new OfferTradeAction(offer));

				popup.setVisible(false);
			}
		});
		popupPanel.add(ok, gbc);

		// Cancel button
		gbc.gridx = 0;
		gbc.gridy++;

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				popup.setVisible(false);
			}
		});
		popupPanel.add(cancel, gbc);

		popup.add(popupPanel);
		popup.show(container, 20, 20);
	}

	/**
	 * Draws the pop up window for viewing offers.
	 * 
	 * @param container
	 *            the parent container of this pop up window
	 */
	static void viewOffers(Container container) {
		JPopupMenu popup = new JPopupMenu("Popup menu");

		JPanel popupPanel = new JPanel();

		GridBagLayout gbl = new GridBagLayout();
		popupPanel.setLayout(gbl);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = Config.GUI.GridSettings.startingGridX;
		gbc.gridy = Config.GUI.GridSettings.startingGridY;
		gbc.insets = Config.GUI.GridSettings.defaultInsets;
		gbc.fill = GridBagConstraints.CENTER;

		Market market = CardSync.getGameState().getMarket();
		List<TradeOffer> tradeOffers = market.getOffers();

		JLabel label;

		// Label

		label = new JLabel("Available trade offers");
		gbl.setConstraints(label, gbc);
		popupPanel.add(label, gbc);

		// Iterate through all the trade offers
		for (TradeOffer tradeOffer : tradeOffers) {
			gbc.gridy++;
			gbc.gridx = 0;

			String text = new String();

			int offererID = tradeOffer.getOffererID();
			String offererName = CardSync.getGameState().getPlayers().get(offererID).getName();

			Resource give = tradeOffer.getGive();
			String giveString = give.toString();

			int give_amount = tradeOffer.getGive_amount();
			String give_amountString = Integer.toString(give_amount);

			Resource take = tradeOffer.getTake();
			String takeString = take.toString();

			int take_amount = tradeOffer.getTake_amount();
			String take_amountString = Integer.toString(take_amount);

			text = offererName + " | Give: " + giveString + " (" + give_amountString + ") | Take: " + takeString + " ("
					+ take_amountString + ")";

			JMenuItem menuItem = new JMenuItem(text);
			popupPanel.add(menuItem, gbc);

			gbc.gridx++;
			JButton accept = new JButton("Accept"+" (Time: "+Config.Action.AcceptTradeAction.time+")");
			accept.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CardSync.controller.sendAction(new AcceptTradeAction(
							CardSync.getGameState().getActivePlayer().getID(), tradeOffer.getID()));

					popup.setVisible(false);
				}
			});
			popupPanel.add(accept, gbc);
		}

		// Close button
		gbc.gridx = 0;
		gbc.gridy++;

		JButton cancel = new JButton("Close");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				popup.setVisible(false);
			}
		});
		popupPanel.add(cancel, gbc);

		popup.add(popupPanel);
		popup.show(container, 20, 20);
	}

	/**
	 * Draws the pop up window for trading with the game.
	 * 
	 * @param container
	 *            the parent container of this pop up window
	 */
	static void tradeWithGame(Container container) {
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

		// Given resource type label
		label = new JLabel("Offered resource type: ");
		gbl.setConstraints(label, gbc);
		popupPanel.add(label, gbc);

		// Given resource type
		gbc.gridx = 1;

		String[] resourceStrings = Config.ResourceNames.resourceStrings;

		JComboBox<String> given_resource = new JComboBox<String>(resourceStrings);
		given_resource.setSelectedIndex(0);
		popupPanel.add(given_resource, gbc);

		// Given resource amount label
		gbc.gridx = 0;
		gbc.gridy = 1;

		label = new JLabel("Offered resource amount: ");
		gbl.setConstraints(label, gbc);
		popupPanel.add(label, gbc);

		// Given resource amount spinner

		gbc.gridx = 1;
		JSpinner given_resource_amount = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
		given_resource_amount.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {

				if (received_resource_amount != null)
					popupPanel.remove(received_resource_amount);

				int receivedSpinnerValue = getSpinnerValue(given_resource_amount)
						/ Config.Action.TradeWithGameAction.ratio_D;

				// Received resource amount spinner
				gbc.gridx = 1;
				gbc.gridy = 3;
				received_resource_amount = new JSpinner(new SpinnerNumberModel(receivedSpinnerValue, 0, 99, 1));
				received_resource_amount.setEnabled(false); // not editable
				popupPanel.add(received_resource_amount, gbc);

				// popupPanel.revalidate();
				// popupPanel.repaint();
			}
		});
		popupPanel.add(given_resource_amount, gbc);

		// Desired resource type label
		gbc.gridx = 0;
		gbc.gridy = 2;

		label = new JLabel("Desired resource type: ");
		gbl.setConstraints(label, gbc);
		popupPanel.add(label, gbc);

		// Desired resource type
		gbc.gridx = 1;

		JComboBox<String> desired_resource = new JComboBox<String>(resourceStrings);
		desired_resource.setSelectedIndex(0);
		popupPanel.add(desired_resource, gbc);

		// Received resource amount label
		gbc.gridx = 0;
		gbc.gridy = 3;

		label = new JLabel("Received resource amount: ");
		gbl.setConstraints(label, gbc);
		popupPanel.add(label, gbc);

		int receivedSpinnerValue = getSpinnerValue(given_resource_amount) / Config.Action.TradeWithGameAction.ratio_D;

		// Received resource amount spinner
		gbc.gridx = 1;
		gbc.gridy = 3;
		received_resource_amount = new JSpinner(new SpinnerNumberModel(receivedSpinnerValue, 0, 99, 1));
		received_resource_amount.setEnabled(false); // not editable
		popupPanel.add(received_resource_amount, gbc);

		// Ok button
		gbc.gridx = 0;
		gbc.gridy = 4;

		JButton ok = new JButton("Ok"+" (Time: "+Config.Action.TradeWithGameAction.time+")");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int offererID = CardSync.getGameState().getActivePlayer().getID();
				Resource give = getResource((String) given_resource.getSelectedItem());
				int give_amount = getSpinnerValue(given_resource_amount);
				Resource take = getResource((String) desired_resource.getSelectedItem());

				CardSync.controller.sendAction(new TradeWithGameAction(offererID, give, give_amount, take));

				popup.setVisible(false);
			}
		});
		popupPanel.add(ok, gbc);

		// Cancel button
		gbc.gridx = 0;
		gbc.gridy++;

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				popup.setVisible(false);
			}
		});
		popupPanel.add(cancel, gbc);

		popup.add(popupPanel);
		popup.show(container, 20, 20);
	}

	/**
	 * Returns the spinner value. Has protection against invalid user input.
	 * 
	 * @param spinner
	 *            the spinner from which we get the value
	 * @return the value of the spinner
	 */
	private static int getSpinnerValue(JSpinner spinner) {
		try {
			spinner.commitEdit();
		} catch (java.text.ParseException pe) {
			System.out.print(pe);
		}
		return (Integer) spinner.getValue();
	}

	/**
	 * Returns the corresponding resource based on a string
	 * 
	 * @param string
	 *            the resource string
	 * @return the corresponding resource
	 */
	private static Resource getResource(String string) {
		switch (string) {
		case "Wood":
			return Resource.Wood;
		case "Stone":
			return Resource.Stone;
		case "Wheat":
			return Resource.Wheat;
		default:
			return null;
		}
	}
}
