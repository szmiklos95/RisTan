package graphics;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import config.Config;

/**
 * The first window to appear when the game starts
 * @author Miklós
 *
 */
public class GUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private GameBoard GameBoard;
	private gameLogic.GameState gameState;
	private JFrame frame;
	
	JPanel cards; //a panel that uses CardLayout
	
	
	
	public GUI() {
        //Create and set up the window.
        frame = new JFrame(Config.GUI.title); 
	}
	
	
	
	/**
	 * Interfaces and Actions for button press
	 */
	private interface Function{
		void doAction(ActionEvent e);
	}
	
	/**
	 * Default button handler
	 */
	private final Function defaultAction = new Function() {
		public void doAction(ActionEvent e) {
			System.out.print("Please assign an action for this button!\n");
		}
	};
	
	/**
	 * Do this when the start button is pressed
	 */
	private final Function startAction = new Function() {
		public void doAction(ActionEvent e) {
			System.out.print("Starting the game.\n");
			/*
			 * start the game
			 */
			drawPlayField();
		}
	};
	
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
		}
	};
	
	
    
	/**
	 * This function implements the card layout.
	 * @param pane The container for the objects, cards
	 */
    public void addComponentsToPane(Container pane) {
    	
        //Create the "cards".
    	
        JPanel card_MainMenu = createCard_MainMenu();
        JPanel card_GameBoard = createCard_GameBoard();
        JPanel card_GameSettings = createCard_GameSettings();
        
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(card_MainMenu, Config.GUI.mainmenu);
        cards.add(card_GameBoard, Config.GUI.newgame);
        cards.add(card_GameSettings, Config.GUI.newgame_settings);
        
        pane.add(cards, BorderLayout.CENTER);
    }

    /**
     * Creates the game board card (JPanel) and returns it
     * @return
     */
    private JPanel createCard_GameBoard() {
        JPanel card = new GameBoard(gameState);
        card.setLayout(new FlowLayout(FlowLayout.LEFT));
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        addMenuItem("Main Menu", file, switchCardAction);
        addMenuItem("Exit", file, exitAction);
        menubar.add(file);
        card.add(menubar);
        
        return card;
    }
    
    /**
     * Creates the main menu card and returns it
     * @return
     */
    private JPanel createCard_MainMenu() {
        JPanel card = new JPanel();
        setButton(Config.GUI.newgame_settings, card, switchCardAction);
        setButton(Config.GUI.exit, card, exitAction);
        return card;
    }
    
    /**
     * Creates the settings menu before starting a new game
     * @return
     */
    private JPanel createCard_GameSettings() {
        JPanel card = new JPanel();

        setInputFields(card);
        setButton(Config.GUI.newgame, card, switchCardAction);
        return card;
    }

    /**
     * Creates a new button within the given container.
     * @param text 		The display name of the button
     * @param container The container
     * @param f 		The method that will be called upon clicking on the button
     */
    private void setButton(String text, Container container, Function f) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getMinimumSize().height));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(new ActionListener() { 
       	 public void actionPerformed(ActionEvent e) { 
       	   f.doAction(e);
       	 } 
        } );
        container.add(button);
    }
    
    /**
     * Creates an input field with the given title
     * @param container
     */
    private void setInputFields(Container container) {
    	
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();

      	
    	c.gridx = 0;
    	c.gridy = 0;
    	panel.add(new JLabel(Config.GUI.playerCount), c);
      	c.gridx = 1;
      	c.gridy = 0;
      	panel.add(new JSpinner(new SpinnerNumberModel(Config.GUI.default_playerCount,Config.GUI.min_playerCount,Config.GUI.max_playerCount,1)), c);
      	
    	c.gridx = 0;
    	c.gridy = 1;
    	panel.add(new JLabel("Some other random setting:"), c);
    	c.gridx = 1;
    	c.gridy = 1;
      	panel.add(new JTextField("Set this", Config.GUI.default_textfield_columnCount), c);
      	
    	container.add(panel);
        
    }
    
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
    
	/**
	 * Creates the panel where the game is played.
	 */
	public void drawPlayField(){

        GameBoard = new GameBoard(gameState);

        setContentPane(GameBoard);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
	}
	

}
