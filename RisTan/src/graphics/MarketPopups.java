package graphics;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import config.Config;
import gameLogic.OfferTradeAction;
import gameLogic.Resource;
import gameLogic.TradeOffer;

public class MarketPopups {

	MarketPopups(){
		
	}
	
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
		popupPanel.add(label,gbc);
		
		// Given resource type
		gbc.gridx++;
		
		String[] resourceStrings = { "Stone", "Wood", "Wheat" };

		JComboBox given_resource = new JComboBox(resourceStrings);
		given_resource.setSelectedIndex(0);
		popupPanel.add(given_resource,gbc);

		
		// Given resource amount label
		gbc.gridx=0;
		gbc.gridy++;
		
		label = new JLabel("Offered resource amount: ");
		gbl.setConstraints(label, gbc);
		popupPanel.add(label,gbc);
		
		// Given resource amount spinner
		gbc.gridx++;
		JSpinner given_resource_amount = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
		popupPanel.add(given_resource_amount, gbc);
		
		// Desired resource type label
		gbc.gridx=0;
		gbc.gridy++;
		
		label = new JLabel("Desired resource type: ");
		gbl.setConstraints(label, gbc);
		popupPanel.add(label,gbc);
		
		// Desired resource type
		gbc.gridx++;

		JComboBox desired_resource = new JComboBox(resourceStrings);
		desired_resource.setSelectedIndex(0);
		popupPanel.add(desired_resource,gbc);
		
		// Desired resource amount label
		gbc.gridx=0;
		gbc.gridy++;
		
		label = new JLabel("Desired resource amount: ");
		gbl.setConstraints(label, gbc);
		popupPanel.add(label,gbc);
		
		// Desired resource amount spinner
		gbc.gridx++;
		JSpinner desired_resource_amount = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
		popupPanel.add(desired_resource_amount, gbc);
		
		// Ok button
		gbc.gridx = 0;
		gbc.gridy++;
		
		JButton ok = new JButton("Ok");
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
	 * Returns the spinner value. Has protection against invalid user input.
	 * 
	 * @param spinner
	 * @return
	 */
	private static int getSpinnerValue(JSpinner spinner) {
		try {
			spinner.commitEdit();
		} catch (java.text.ParseException pe) {
			System.out.print(pe);
		}
		return (Integer) spinner.getValue();
	}
	
	private static Resource getResource(String string) {
		switch(string) {
		case "Wood": return Resource.Wood;
		case "Stone": return Resource.Stone;
		case "Wheat": return Resource.Wheat;
		default: return null;
		}
	}
}
