package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
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
 * @author Miklós
 *
 */
public class GameBoard extends JPanel{
 
	 private static final long serialVersionUID = 1L;
	 

	    private FontMetrics metrics;
	    private gameLogic.GameState gameState;
	    private gameLogic.Board board;
	    private ArrayList<Hexagon> hexagons;
	    private NewGameSettings initialSettings;
	    
	    
	    public GameBoard() {
	    	setPreferredSize(new Dimension(Config.GameBoard.width, Config.GameBoard.height));
	    }
	    
	    
	    /**
	     * Constructor that sets the game state and the panel size
	     * @param gameState
	     */
	    public GameBoard(gameLogic.GameState gameState, NewGameSettings initialSettings) {
	        setPreferredSize(new Dimension(Config.GameBoard.width, Config.GameBoard.height));
	        
	        this.initialSettings = initialSettings;
	        
	        hexagons = new ArrayList<Hexagon>();
	        this.gameState = gameState;
	        board = this.gameState.getBoard();
	        
	        
		    addMouseListener(new MouseAdapter() {
	            @Override
	            public void mousePressed(MouseEvent me) {
	                super.mousePressed(me);
	                for (Hexagon h : hexagons) {

	                    if (h.contains(me.getPoint())) {//check if mouse is clicked within shape

	                        System.out.println("Clicked a "+h.getClass().getName()+" at coordinates: ("+h.getCenter().getX()+":"+h.getCenter().getY()+")");
	                        h.toggleSelected();
	                        repaint();

	                    }
	                }
	            }
		    });
	        
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
	        
	        //The center point
	        Point origin = new Point(Config.GameBoard.width / 2, Config.GameBoard.height / 2);

	        g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
	        g2d.setFont(Config.GameBoard.font);
	        metrics = g.getFontMetrics();

	        drawCircle(g2d, origin, Config.Circle.radius, true, true, Config.Circle.color, Config.Circle.lineThickness);
	        
	        ArrayList<gameLogic.Point> points = new ArrayList<gameLogic.Point>(board.getTiles().keySet());
	        drawHexGridFromPoints(g2d, origin, Config.Hexagon.radius, Config.Hexagon.padding, points);
	        
	    }
	    

	    
	    
	    /**
	     * Draws the hexagonal map around the given origin point from points
	     * @param g Graphics object
	     * @param origin The center hexa tile
	     * @param radius The radius of each hexagon
	     * @param padding The distance between hexagons
	     */
	    private void drawHexGridFromPoints(Graphics g, Point origin, int radius, int padding, List<gameLogic.Point> points) {
	        
	        int pointNum = points.size();
	        
	        for(int i=0; i<pointNum; ++i) {
	        	gameLogic.Point pointi = points.get(i);
		        double xOff = pointi.getDescartesX()*(radius+padding)*2;
		        double yOff = pointi.getDescartesY()*(radius+padding)*2;
	        	int xLbl = pointi.getJ();
                int yLbl = pointi.getI();
                int x = (int) (origin.getX() + xOff);
                int y = (int) (origin.getY() + yOff);
                
                drawHex(g, xLbl, yLbl, x, y, radius, i);
	        }

	    }
	    
	    
	    
	    /**
	     * Draws a hexagon to the given position with the given radius
	     * @param g Graphics object
	     * @param posX The x coordinate of the text
	     * @param posY The y coordinate of the text
	     * @param x The x coordinate of the hexagon
	     * @param y The y coordinate of the hexagon
	     * @param r The radius of the hexagon
	     * @param i The i-th element in the hexagon list
	     */
	    private void drawHex(Graphics g, int posX, int posY, int x, int y, int r, int i) {
	        Graphics2D g2d = (Graphics2D) g;

	        hexagons.add(new Hexagon(x, y, r));
	        String text = String.format("%s : %s", coord(posX), coord(posY));
	        int w = metrics.stringWidth(text);
	        int h = metrics.getHeight();
	        
	        Hexagon hexagon = hexagons.get(i);
	        
	        int outerColor = Config.Hexagon.outerColor;
	        if(hexagon.isSelected()) outerColor = Config.Hexagon.selectedOuterColor;
	        
	        hexagon.draw(g2d, x, y, Config.Hexagon.innerLineThickness, Config.Hexagon.innerColor, true);
	        hexagon.draw(g2d, x, y, Config.Hexagon.outerLineThickness, outerColor, false);

	        g.setColor(new Color(Config.Hexagon.textColor));
	        g.drawString(text, x - w/2, y + h/2);
	    }
	    
	    /**
	     * Returns the coordinates in string format
	     * @param value
	     * @return
	     */
	    private String coord(int value) {
	        return (value > 0 ? "+" : "") + Integer.toString(value);
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
	    public void drawCircle(Graphics2D g, Point origin, int radius,
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
	
