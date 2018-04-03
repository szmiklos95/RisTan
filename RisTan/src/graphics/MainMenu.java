package graphics;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import config.Config;

public class MainMenu {
	
	/**
	 * Interfaces and Actions for button press
	 */
	private interface Function{
		void doAction();
	}
	
	/**
	 * Default button handler
	 */
	private static final Function defaultAction = new Function() {
		public void doAction() {
			System.out.print("Please assign an action for this button!\n");
		}
	};
	
	/**
	 * Do this when the start button is pressed
	 */
	private static final Function startAction = new Function() {
		public void doAction() {
			System.out.print("Starting the game.\n");
			/*
			 * start the game
			 */
			System.out.print("...Just kidding, it is not implemented yet.");
		}
	};
	
	/**
	 * Do this when the exit button is pressed
	 */
	private static final Function exitAction = new Function() {
		public void doAction() {
			System.out.print("Closing the game.\n");
			System.exit(0);
		}
	};
	
	/**
	 * Sets the layout, size and buttons.
	 * @param pane The container where the items will be added.
	 */
    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        
        pane.setPreferredSize(new Dimension(Config.MainMenu.width,Config.MainMenu.height));
        
        setButton("Start", pane, startAction);
        setButton("Button 2", pane, defaultAction);
        setButton("Button 3", pane, defaultAction);
        setButton("Long-Named Button 4", pane, defaultAction);
        setButton("Exit", pane, exitAction);
      
    }
    

    /**
     * Creates a new button within the given container.
     * @param text 		The display name of the button
     * @param container The container
     * @param f 		The method that will be called upon clicking on the button
     */
    private static void setButton(String text, Container container, Function f) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getMinimumSize().height));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(new ActionListener() { 
       	 public void actionPerformed(ActionEvent e) { 
       	   f.doAction();
       	 } 
        } );
        container.add(button);
    }
    

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Welcome to RisTan!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }


}
