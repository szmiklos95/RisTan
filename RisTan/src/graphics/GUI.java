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
import gameLogic.ClientController;
import gameLogic.InitGameAction;
import network.Message;
import network.SerialClient;
import network.SerialServer;
import network.Message.eMsgType;


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
	
    private SerialClient client = null;
    private ClientController controller = null;
	
	public GUI() {
        frame = new JFrame(Config.GUI.title); 
        settings = new NewGameSettings();
        gameBoard = new GameBoard();
        client = new SerialClient();
        controller = client.getController();
	}
	
	
	public GUI(gameLogic.GameState gameState) {
        //Create and set up the window.
        frame = new JFrame(Config.GUI.title); 
        frame.setSize(Config.GameBoard.width, Config.GameBoard.height);
        
        settings = new NewGameSettings();
        this.gameState = gameState;
        gameBoard = new GameBoard(this.gameState);
        
        client = new SerialClient();
        controller = client.getController();
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
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = Config.GUI.MainMenu.startingGridX;
		gbc.gridy = Config.GUI.MainMenu.startingGridY;
		gbc.insets = Config.GUI.MainMenu.defaultInsets;
		gbc.fill = GridBagConstraints.CENTER;
		
		//Main menu
		Label mainMenu = new Label("Main menu");
		mainMenu.setFont(new Font("Arial Bold",0,20));
		gbl.setConstraints(mainMenu, gbc);
		card.add(mainMenu);
		
		//Enter name
		gbc.gridy++;
		
		TextField name = new TextField("Enter your name");
		name.setEditable(true);
		gbl.setConstraints(name, gbc);
		name.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				name.setText("");
			}
		});;
		card.add(name);
		
		//New game
		gbc.gridy+=2;
		gbc.gridwidth = Config.GUI.MainMenu.defaultGridWidth;
		gbc.gridheight = Config.GUI.MainMenu.defaultGridHeight;

		JButton create = new JButton(Config.GUI.newgame);
		gbl.setConstraints(create, gbc);
		create.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				settings.setPlayerName(new String(name.getText()));
				
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
		//TODO new Join window
		gbc.gridy+=2;

		JButton join = new JButton("Join");

		gbl.setConstraints(join, gbc);
		join.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				settings.setPlayerName(new String(name.getText()));
				ConnectTo("127.0.0.1");//TODO: connect to selected address, move this to connection window, open here connection window
				
				DrawBoard();
				
				CardLayout cl = (CardLayout)(cards.getLayout());
	 	        cl.show(cards, Config.GUI.ok); //Change to board
	 	        System.out.print("Player joining the game.\n");
			}
		});
		card.add(join);
		
		//Exit
		gbc.gridy+=10;
		gbc.gridheight+=10;

        setButtonGrid(Config.GUI.exit, card, exitAction, gbc);
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
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = Config.GUI.MainMenu.startingGridX;
		gbc.gridy = Config.GUI.MainMenu.startingGridY;
		gbc.insets = Config.GUI.MainMenu.defaultInsets;
		gbc.fill = GridBagConstraints.CENTER;
		
		//Settings label
		gbc.gridwidth = 2;
		
		Label settings_label = new Label("Hello "+settings.getPlayerName()+"!");
		settings_label.setFont(new Font("Arial Bold",0,20));
		gbl.setConstraints(settings_label, gbc);
		card.add(settings_label);
		
		
		// Player count label
		gbc.gridwidth = 1;
		gbc.gridy+=5;
        
		JLabel label = new JLabel(Config.GUI.playerCount);
		gbl.setConstraints(label, gbc);
      	
    	card.add(label);
    	
    	// Spinner
    	gbc.gridx++;
    	
      	JSpinner spinner_PCount = new JSpinner(new SpinnerNumberModel(Config.GUI.default_playerCount,Config.GUI.min_playerCount,Config.GUI.max_playerCount,1));
      	gbl.setConstraints(spinner_PCount, gbc);
      	card.add(spinner_PCount);
      	
      	// OK button
      	gbc.gridx=0;
      	gbc.gridy+=2;
      	gbc.gridwidth = 2;
      	gbc.gridheight = 2;
      	
        JButton button = new JButton(Config.GUI.ok);
        gbl.setConstraints(button, gbc);
        
        button.addActionListener(new ActionListener() { 
       	 public void actionPerformed(ActionEvent e) { 
       		 
       		int value_PCount = getSpinnerValue(spinner_PCount);
       		System.out.print("Player count set to "+value_PCount+".\n");
       		settings.setPlayerCount(value_PCount);
       		
       		SetupNewGame();
       		DrawBoard();
       		
 	        CardLayout cl = (CardLayout)(cards.getLayout());
 	        cl.show(cards, e.getActionCommand());
 	        System.out.print("Starting the game.\n");
       	 } 
        } );
        
        card.add(button);
        
        
        // Back to menu button
        gbc.gridy+=2;  
    	setButtonGrid(Config.GUI.mainmenu, card, switchCardAction, gbc);
        
        
        // Exit button
    	gbc.gridy+=4;
        setButtonGrid(Config.GUI.exit, card, exitAction, gbc);
 
        return card;
    }
    
    /**
     * Returns the spinner value.
     * Has protection against invalid user input.
     * @param spinner
     * @return
     */
    private int getSpinnerValue(JSpinner spinner) {
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
     * This function runs at the host player.
     */
    private void SetupNewGame() {
    	network.SerialServer server = new SerialServer();
		server.Connect(settings.getPlayerCount());
    	
		//This is localhost IP address, connects the local client to the server
		ConnectTo("127.0.0.1");
		
    	controller.executeAction(controller.getLocalPlayerID(), new InitGameAction());


    }
    
    private void ConnectTo(String IPstring) {
    	//network.SerialClient client=new SerialClient();
		client.Connect(IPstring);
		
		new network.Chat(client,settings.getPlayerName());
		client.Send(new Message(eMsgType.Name,settings.getPlayerName()));
		
		//gameLogic.ClientController controller=client.getController();
		gameState=controller.getGameState();
    }
    
    private void DrawBoard() {
    	gameState = controller.getGameState();
    	
    	// Update the settings, and draw a new board.
		gameBoard = new GameBoard(gameState); //TODO: a GUI-nak továbbítani a controllert, hogy tudjon action-t feladni
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
