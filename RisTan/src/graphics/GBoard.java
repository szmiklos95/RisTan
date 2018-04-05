package graphics;


import javax.swing.JFrame;

/**
 * The board itself.
 * @author Miklós
 *
 */
public class GBoard extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private DrawingPanel drawingPanel;
	
	public GBoard() {
        super("RisTan board");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Only close this window
	}
	
	
	 
	/**
	 * Creates the panel where the game is played.
	 */
	public void drawPlayField(){

        drawingPanel = new DrawingPanel();

        setContentPane(drawingPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
	}
	
}
