package network;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

public class GameMenu extends JFrame{

	/**
	 * @Goal:
	 * - Creating the game menu what is appeared when you start the application.
	 * - Including the following buttons:
	 * 		- Create new game
	 * 		- Join
	 * 		- Exit
	 * - and an input field for your nickname
	 * 
	 * Detailed description:
	 * 	Create new game:
	 * 	- Start the Server (init + connect)
	 * 	- Start a Client (init + connect to server)
	 *	- Client sends id message to server (identification)
	 *	- Switch the GUI 
	 *  Join:
	 * 	- Start a Client (init + connect to server)
	 *	- Client sends id message to server (identification)
	 *	- Switch the GUI
	 *  Exit:
	 *  - just exit the window
	 *
	 */
	private static final long serialVersionUID = 1L;
	private SerialServer server = null;
	
	// Constructor
	GameMenu(){
		super("RisTan Beta");
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);;
		//Main menu
		GridBagConstraints GBC_mainMenu = new GridBagConstraints();
		GBC_mainMenu.gridx = 3;
		GBC_mainMenu.gridy = 0;
		GBC_mainMenu.fill = GridBagConstraints.CENTER;
		GBC_mainMenu.insets = new Insets(10,10,10,10);
		
		Label mainMenu = new Label("Main menu");
		mainMenu.setFont(new Font("Arial Bold",0,20));
		gbl.setConstraints(mainMenu, GBC_mainMenu);
		this.add(mainMenu);
		
		//Enter name
		GridBagConstraints GBC_name = new GridBagConstraints();
		GBC_name.gridx = 3;
		GBC_name.gridy = 1;
		GBC_name.fill = GridBagConstraints.CENTER;
		GBC_name.insets = new Insets(10,10,10,10);
		
		TextField name = new TextField("Enter your name");
		name.setEditable(true);
		gbl.setConstraints(name, GBC_name);
		name.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				name.setText("");
			}
		});;
		this.add(name);
		
		//Create the game
		GridBagConstraints GBC_create = new GridBagConstraints();
		GBC_create.gridx = 3;
		GBC_create.gridy = 3;
		GBC_create.gridwidth = 1;
		GBC_create.gridheight = 2;
		GBC_create.fill = GridBagConstraints.HORIZONTAL;
		GBC_create.insets = new Insets(10,10,10,10);

		Button create = new Button("Create new game");
		create.setFont(new Font("Arial Bold",0,20));
		create.setBackground(new Color(100,100,100));
		gbl.setConstraints(create, GBC_create);
		create.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			    server = new SerialServer();
				server.Connect();

				// Close the window
				dispose();
				new Chat(new String(name.getText()));
			}
		});
		this.add(create);
		
		//Join
		
		GridBagConstraints GBC_join = new GridBagConstraints();
		GBC_join.gridx = 3;
		GBC_join.gridy = 5;
		GBC_join.gridwidth = 1;
		GBC_join.gridheight = 2;
		GBC_join.fill = GridBagConstraints.CENTER;
		GBC_join.insets = new Insets(10,10,10,10);

		Button join = new Button("Join");
		join.setFont(new Font("Arial Bold",0,20));
		join.setBackground(new Color(100,100,100));
		gbl.setConstraints(join, GBC_join);
		join.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Close the window
				dispose();
				new Chat(new String(name.getText()));
			}
		});
		this.add(join);
		
		//Exit
		
		GridBagConstraints GBC_exit = new GridBagConstraints();
		GBC_exit.gridx = 3;
		GBC_exit.gridy = 12;
		GBC_exit.gridwidth = 2;
		GBC_exit.gridheight = 10;
		GBC_exit.fill = GridBagConstraints.CENTER;
		//GBC_exit.insets = new Insets(10,10,10,10);

		Button exit = new Button("exit");
		exit.setFont(new Font("Arial Bold",0,20));
		exit.setBackground(new Color(100,100,100));
		gbl.setConstraints(exit, GBC_exit);
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		this.add(exit);
		
		setVisible(true);
	}	
	
}
