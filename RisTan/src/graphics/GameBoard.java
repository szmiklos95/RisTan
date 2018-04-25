package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import config.Config;

/**
 *  The panel where the game is played
 * @author Mikl�s
 *
 */
public class GameBoard extends JPanel{
 
	 private static final long serialVersionUID = 1L;
	 
	    private gameLogic.GameState gameState;

	    private ArrayList<HexaTile> hexaTiles;
	    private Point origin; 
	    private boolean tilesInitialized = false; //Necessary because the board (thus the tiles) are generated later

	    public GameBoard() {
	    	setPreferredSize(new Dimension(Config.GameBoard.width, Config.GameBoard.height));
	    }
	    
	    
	    /**
	     * Constructor that sets the game state and the panel size
	     * @param gameState
	     */
	    public GameBoard(gameLogic.GameState gameState) {
	        setPreferredSize(new Dimension(Config.GameBoard.width, Config.GameBoard.height));
	        
	        //The center point
	       origin = new Point(Config.GameBoard.width / 2, Config.GameBoard.height / 2);
	        
	        hexaTiles = new ArrayList<HexaTile>();
	        
	        this.gameState = gameState;
	        
		    addMouseListener(new MouseAdapter() {
	            @Override
	            public void mousePressed(MouseEvent me) {
	                super.mousePressed(me);
	                for (HexaTile t : hexaTiles) {

	                    if (t.getHexagon().contains(me.getPoint())) {//check if mouse is clicked within shape

	                        System.out.println("Clicked a "+t.getClass().getName()+" at coordinates: ("+t.getHexagon().getCenter().getX()+":"+t.getHexagon().getCenter().getY()+")");
	                        t.toggleSelected();
	                        repaint();

	                    }
	                }
	            }
		    });
	        
	    }
	    
	    
	    /**
	     * Gets the Board from the gameState, necessary because the board is only created at the start of the game which is later than the GameBoard instantiation.
	     * @return Board from gameState
	     */
	    private gameLogic.Board getBoard(){
	    	return gameState.getBoard();
	    }   
	    
	    /*
	     * (non-Javadoc)
	     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	     * 
	     * The main drawing function.
	     */
	    @Override
	    public void paintComponent(Graphics g) {
	        Graphics2D g2d = (Graphics2D) g;
	        
	        g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
	        g2d.setFont(Config.GameBoard.font);

	        drawCircle(g2d, origin, Config.Circle.radius, true, true, Config.Circle.color, Config.Circle.lineThickness);
	        
	        //drawTiles(g2d, origin);
	        //drawHexGridFromPoints(g2d, origin, Config.Hexagon.radius, Config.Hexagon.padding, points);
	        
	        gameLogic.Board board=getBoard();
	        if(board!=null) {
	        	if(!tilesInitialized) {
			        ArrayList<gameLogic.Point> points = new ArrayList<gameLogic.Point>(board.getTiles().keySet());
			        initTiles(origin, Config.Hexagon.radius, Config.Hexagon.padding, points);
			        tilesInitialized = true;
	        	}
	        	drawTiles(g2d, origin);
	        }
	    }
	    

	    /**
	     * 
	     * @param origin
	     * @param radius
	     * @param padding
	     * @param points
	     */
	    private void initTiles(Point origin, int radius, int padding, List<gameLogic.Point> points) {
	        int pointNum = points.size();
	        
	        for(int i=0; i<pointNum; ++i) {
	        	gameLogic.Point pointi = points.get(i);
		        double xOff = pointi.getDescartesX()*(radius+padding)*2;
		        double yOff = pointi.getDescartesY()*(radius+padding)*2;
                int x = (int) (origin.getX() + xOff);
                int y = (int) (origin.getY() + yOff);
                
                hexaTiles.add(new HexaTile(x,y,radius, getBoard().getResourceAt(pointi)));
	        }
	    }
	    
	    /**
	     * 
	     * @param g
	     * @param origin
	     */
	    private void drawTiles(Graphics g, Point origin) {
	    	Graphics2D g2d = (Graphics2D) g;
	    	for (HexaTile t : hexaTiles) {
	    		t.draw(g2d);
	    	}
	    }
	  

	    /**
	     * Draws a circle with the given parameters.
	     * @param g Graphics object
	     * @param origin The center of the circle
	     * @param radius The radius of the circle
	     * @param centered Should be true
	     * @param filled 
	     * @param colorValue
	     * @param lineThickness
	     */
	    private void drawCircle(Graphics2D g, Point origin, int radius,
	            boolean centered, boolean filled, int colorValue, int lineThickness) {
	        // Store before changing.
	        Stroke tmpS = g.getStroke();
	        Color tmpC = g.getColor();

	        g.setColor(new Color(colorValue));
	        g.setStroke(new BasicStroke(lineThickness, BasicStroke.CAP_ROUND,
	                BasicStroke.JOIN_ROUND));

	        int diameter = radius * 2;
	        int x2 = centered ? origin.getX() - radius : origin.getX();
	        int y2 = centered ? origin.getY() - radius : origin.getY();

	        if (filled)
	            g.fillOval(x2, y2, diameter, diameter);
	        else
	            g.drawOval(x2, y2, diameter, diameter);

	        // Set values to previous when done.
	        g.setColor(tmpC);
	        g.setStroke(tmpS);
	    }
	    
	
}
	
