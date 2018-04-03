package graphics;


import javax.swing.JFrame;


public class GBoard extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private DrawingPanel drawingPanel;
	
	public GBoard() {
        super("RisTan board");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	
	 

	public void drawHexagon(){

	    drawingPanel = new DrawingPanel();
	    add(drawingPanel);

	    pack();
	    setLocationByPlatform(true);
	    setVisible(true);
	}
	
}
