package graphics;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import config.Config;
import gameLogic.GameLogicException;
import network.Chat;
import network.SerialServer;


/**
 * The main GUI frame with card layout
 * @author Miklós
 *
 */
public class GUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private GameBoard gameBoard;
	private gameLogic.GameState gameState;
	private JFrame frame;
	private NewGameSettings settings;
	
	private JPanel cards; //a panel that uses CardLayout
	private JPanel card_GameBoard; //need to store a reference because this will get updated later
	private JPanel card_GameSettings;
	
	
	public GUI() {
        frame = new JFrame(Config.GUI.title); 
        settings = new NewGameSettings();
        gameBoard = new GameBoard();
	}
	
	
	public GUI(gameLogic.GameState gameState) {
        //Create and set up the window.
        frame = new JFrame(Config.GUI.title); 
        frame.setSize(Config.GameBoard.width, Config.GameBoard.height);
        
        settings = new NewGameSettings();
        this.gameState = gameState;
        gameBoard = new GameBoard(this.gameState);
	}
	
    
	/**
	 * This function implements the card layout.
	 * @param pane The container for the objects, cards
	 */
    public void addComponentsToPane(Container pane) {
    	
        //Create the "cards".
    	
        JPanel card_MainMenu = createCard_MainMenu();
        card_GameBoard = gameBoard; //Add an empty board to the cards for the JFrame to be set to optimal size
        card_GameSettings = createCard_GameSettings();
        
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        
        //The param is the card itself, the second is the command with which we can call this card
        cards.add(card_MainMenu, Config.GUI.mainmenu);
        cards.add(card_GameBoard, Config.GUI.ok);
        cards.add(card_GameSettings, Config.GUI.newgame);
        
        pane.add(cards, BorderLayout.CENTER);
    }


    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public void createAndShowGUI() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * Refreshes the game state.
     * @param gameState The current game state
     */
    public void refreshGameState(gameLogic.GameState gameState) {
    	this.gameState = gameState;
    }
    
	
	
	/* *****************************************************************************
	 *  @@@@@@@@@@@@@@@@@@@@@@@@@@ Helper (private) methods @@@@@@@@@@@@@@@@@@@@@@@@@@
	 * *****************************************************************************/

	
	
	
	
	
	//*********** JPanel items ***********//
	
    /**
     * Adds a JMenuItem to a JMenu
     * @param text
     * @param container
     * @param f Action listener function
     */
    private void addMenuItem(String text, Container container, Function f) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(new ActionListener() { 
       	 public void actionPerformed(ActionEvent e) { 
       	   f.doAction(e);
       	 } 
        } );
        container.add(menuItem);
    }
    
    
    /**
     * Creates a new button within the given container with GridBagLayout.
     * @param text 		The display name of the button
     * @param container The container
     * @param f 		The method that will be called upon clicking on the button
     * @param g			The constraints for the grid layout
     */
    private void setButtonGrid(String text, Container container, Function f, GridBagConstraints gbc) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getMinimumSize().height));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(new ActionListener() { 
       	 public void actionPerformed(ActionEvent e) { 
       	   f.doAction(e);
       	 } 
        } );
        container.add(button, gbc);
    }
    
    
    
    
    
    
    
    
  //*********** Cards ***********//
    /**
     * Creates the game board card (JPanel) and returns it
     * @return
     */
    private JPanel createCard_GameBoard() {
    	
        JPanel card = new JPanel();

        gameBoard.setLayout(new FlowLayout(FlowLayout.LEFT));
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        addMenuItem("Main Menu", file, switchCardAction);
        addMenuItem("Exit", file, exitAction);
        menubar.add(file);
        gameBoard.add(menubar);
        card.add(gameBoard);
        return card;
    }
    
    
    
    /**
     * Creates the main menu card and returns it
     * @return
     */
    private JPanel createCard_MainMenu() {
        JPanel card = new JPanel();
        
        
        GridBagLayout gbl = new GridBagLayout();
		card.setLayout(gbl);;
		//Main menu
		GridBagConstraints GBC_mainMenu = new GridBagConstraints();
		GBC_mainMenu.gridx = 3;
		GBC_mainMenu.gridy = 0;
		GBC_mainMenu.fill = GridBagConstraints.CENTER;
		GBC_mainMenu.insets = new Insets(10,10,10,10);
		
		Label mainMenu = new Label("Main menu");
		mainMenu.setFont(new Font("Arial Bold",0,20));
		gbl.setConstraints(mainMenu, GBC_mainMenu);
		card.add(mainMenu);
		
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
		card.add(name);
		
		//New game
		GridBagConstraints GBC_create = new GridBagConstraints();
		GBC_create.gridx = 3;
		GBC_create.gridy = 3;
		GBC_create.gridwidth = 1;
		GBC_create.gridheight = 2;
		GBC_create.fill = GridBagConstraints.HORIZONTAL;
		GBC_create.insets = new Insets(10,10,10,10);

		JButton create = new JButton(Config.GUI.newgame);
        create.setMaximumSize(new Dimension(Integer.MAX_VALUE, create.getMinimumSize().height));
        create.setAlignmentX(Component.CENTER_ALIGNMENT);
		gbl.setConstraints(create, GBC_create);
		create.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				settings.setServerPlayerName(new String(name.getText()));
				
				// Need to update card
				cards.remove(card_GameSettings);
				card_GameSettings = createCard_GameSettings();
				cards.add(card_GameSettings, Config.GUI.newgame);
				
		        CardLayout cl = (CardLayout)(cards.getLayout());
		        cl.show(cards, e.getActionCommand());
		        System.out.print("Switching to "+e.getActionCommand()+" panel.\n");
			}
		});
		card.add(create);
		
		//Join
		GridBagConstraints GBC_join = new GridBagConstraints();
		GBC_join.gridx = 3;
		GBC_join.gridy = 5;
		GBC_join.gridwidth = 1;
		GBC_join.gridheight = 2;
		GBC_join.fill = GridBagConstraints.CENTER;
		GBC_join.insets = new Insets(10,10,10,10);

		JButton join = new JButton("Join");
        create.setMaximumSize(new Dimension(Integer.MAX_VALUE, create.getMinimumSize().height));
        create.setAlignmentX(Component.CENTER_ALIGNMENT);
		gbl.setConstraints(join, GBC_join);
		join.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				new Chat(new String(name.getText()));
				
		        // Get gameStatus from server and draw board
			}
		});
		card.add(join);
		
		//Exit
		
		GridBagConstraints GBC_exit = new GridBagConstraints();
		GBC_exit.gridx = 3;
		GBC_exit.gridy = 12;
		GBC_exit.gridwidth = 2;
		GBC_exit.gridheight = 10;
		GBC_exit.fill = GridBagConstraints.CENTER;
		//GBC_exit.insets = new Insets(10,10,10,10);

        setButtonGrid(Config.GUI.exit, card, exitAction, GBC_exit);
        return card;
    }
    
    
    
    /**
     * Creates the settings menu before starting a new game
     * @return
     */
    private JPanel createCard_GameSettings() {
        JPanel card = new JPanel();
        
        GridBagLayout gbl = new GridBagLayout();
		card.setLayout(gbl);;
		
		//Settings label
		GridBagConstraints GBC_settings = new GridBagConstraints();
		GBC_settings.gridx = 0;
		GBC_settings.gridy = 0;
		GBC_settings.gridwidth = 2;
		GBC_settings.fill = GridBagConstraints.CENTER;
		GBC_settings.insets = new Insets(10,10,10,10);
		
		Label settings_label = new Label("Hello "+settings.getServerPlayerName()+"!");
		settings_label.setFont(new Font("Arial Bold",0,20));
		gbl.setConstraints(settings_label, GBC_settings);
		card.add(settings_label);
		
		
		// Player count label
		GridBagConstraints GBC_label = new GridBagConstraints();
		GBC_label.gridx = 0;
		GBC_label.gridy = 5;
		GBC_label.fill = GridBagConstraints.CENTER;
		GBC_label.insets = new Insets(10,10,10,10);
        
		JLabel label = new JLabel(Config.GUI.playerCount);
		gbl.setConstraints(label, GBC_label);
      	
    	card.add(label);
    	
    	// Spinner
		GridBagConstraints GBC_spinner = new GridBagConstraints();
		GBC_spinner.gridx = 1;
		GBC_spinner.gridy = 5;
		GBC_spinner.fill = GridBagConstraints.CENTER;
		GBC_spinner.insets = new Insets(10,10,10,10);
      	JSpinner spinner_PCount = new JSpinner(new SpinnerNumberModel(Config.GUI.default_playerCount,Config.GUI.min_playerCount,Config.GUI.max_playerCount,1));
      	gbl.setConstraints(spinner_PCount, GBC_spinner);
      	card.add(spinner_PCount);
      	
      	// OK button
		GridBagConstraints GBC_ok = new GridBagConstraints();
		GBC_ok.gridx = 0;
		GBC_ok.gridy = 7;
		GBC_ok.gridwidth = 2;
		GBC_ok.gridheight = 2;
		GBC_ok.fill = GridBagConstraints.HORIZONTAL;
		GBC_ok.insets = new Insets(10,10,10,10);
        JButton button = new JButton(Config.GUI.ok);
        
        gbl.setConstraints(button, GBC_ok);
        
        button.addActionListener(new ActionListener() { 
       	 public void actionPerformed(ActionEvent e) { 
       		 
       		int value_PCount = getSpinnerValue(spinner_PCount);
       		System.out.print("Player count set to "+value_PCount+".\n");
       		settings.setPlayerCount(value_PCount);
       		
       		SetupNewGame();
       		
 	        CardLayout cl = (CardLayout)(cards.getLayout());
 	        cl.show(cards, e.getActionCommand());
 	        System.out.print("Starting the game.\n");
       	 } 
        } );
        
        card.add(button);
        
        
        // Back to menu button
		GridBagConstraints GBC_backToMenu = new GridBagConstraints();
		GBC_backToMenu.gridx = 0;
		GBC_backToMenu.gridy = 9;
		GBC_backToMenu.gridwidth = 2;
		GBC_backToMenu.gridheight = 2;
		GBC_backToMenu.fill = GridBagConstraints.HORIZONTAL;
		GBC_backToMenu.insets = new Insets(10,10,10,10);
    	setButtonGrid(Config.GUI.mainmenu, card, switchCardAction, GBC_backToMenu);
        
        
        // Exit button
		GridBagConstraints GBC_exit = new GridBagConstraints();
		GBC_exit.gridx = 0;
		GBC_exit.gridy = 11;
		GBC_exit.gridwidth = 2;
		GBC_exit.gridheight = 2;
		GBC_exit.fill = GridBagConstraints.HORIZONTAL;
		GBC_exit.insets = new Insets(10,10,10,10);
        setButtonGrid(Config.GUI.exit, card, exitAction, GBC_exit);
 
        
        return card;
    }
    
    /**
     * Returns the spinner value.
     * Has protection against invalid user input.
     * @param spinner
     * @return
     */
    int getSpinnerValue(JSpinner spinner) {
   		try {
   		    spinner.commitEdit();
   		} 
   		catch ( java.text.ParseException pe ){
   			System.out.print(pe);
   		}
   		return (Integer) spinner.getValue();
    }
    
    /**
     * Creates a new GameBoard JPanel and adds it to the main JPanel (cards).
     * This is a server side function
     */
    private void SetupNewGame() {
    	
        // New game state
        gameState = new gameLogic.GameState();
        
        // New player
        gameLogic.Player player = new gameLogic.Player(settings.getServerPlayerName());
        try {
			gameState.executeAction(new gameLogic.AddPlayerAction(player));
		} catch (GameLogicException e1) {
			e1.printStackTrace();
		}
        
        // Init game action
        gameLogic.InitGameAction action = new gameLogic.InitGameAction();
        try { //Execute the action
			gameState.executeAction(action);
		} catch (GameLogicException e) {
			e.printStackTrace();
		}
        
        network.SerialServer server = new SerialServer();
		server.Connect();

		new network.Chat(settings.getServerPlayerName());
    	
   		// Update the settings, and draw a new board.
   		gameBoard = new GameBoard(gameState);
   		cards.remove(card_GameBoard);
   		card_GameBoard = createCard_GameBoard();
   		cards.add(card_GameBoard, Config.GUI.ok);
    }
    
    
    
    
    
    
    
    
    
  //*********** Interface functions ***********//
	/**
	 * Interfaces and Actions for button press
	 */
	private interface Function{
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
	        CardLayout cl = (CardLayout)(cards.getLayout());
	        cl.show(cards, e.getActionCommand());
	        System.out.print("Switching to "+e.getActionCommand()+" panel.\n");
		}
	};
	
}
