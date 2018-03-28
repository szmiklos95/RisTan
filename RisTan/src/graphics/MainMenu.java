package graphics;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class MainMenu {
    public static void addComponentsToPane(Container pane) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        
        pane.setPreferredSize(new Dimension(1024,768));
        addAButton("Start", pane);
        addAButton("Button 2", pane);
        addAButton("Button 3", pane);
        addAButton("Long-Named Button 4", pane);
        addAButton("Exit", pane);
      
    }

    private static void addAButton(String text, Container container) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getMinimumSize().height));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
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
