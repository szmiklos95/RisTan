package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import config.Config;
import gameLogic.GameLogicException;


public class DrawingPanel extends JPanel {
 
	 private static final long serialVersionUID = 1L;

	    FontMetrics metrics;
	    gameLogic.GameState gameState;
	    gameLogic.Board board;
	    
	    /*
	     * Constructor: sets the panel size
	     */
	    public DrawingPanel() {
	        setPreferredSize(new Dimension(Config.DrawingPanel.width, Config.DrawingPanel.height));
	        
	        // New game state
	        gameState = new gameLogic.GameState();
	        // Init game action
	        gameLogic.InitGameAction action = new gameLogic.InitGameAction();
	        try { //Execute the action
				gameState.ExecuteAction(action);
			} catch (GameLogicException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        board = gameState.getBoard();
	        
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
	        Point origin = new Point(Config.DrawingPanel.width / 2, Config.DrawingPanel.height / 2);

	        g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
	        g2d.setFont(Config.DrawingPanel.font);
	        metrics = g.getFontMetrics();

	        drawCircle(g2d, origin, Config.Circle.radius, true, true, Config.Circle.color, Config.Circle.lineThickness);
	        
	        ArrayList<gameLogic.Point> points = new ArrayList<gameLogic.Point>(board.getTiles().keySet());
	        drawHexGridFromPoints(g2d, origin, Config.Board.size, Config.Hexagon.radius, Config.Hexagon.padding, points);
	        
	    }
	    
	    /**
	     * Draws the hexagonal map around the given origin point.
	     * @param g Graphics object
	     * @param origin The center hexa tile
	     * @param size Number of hexa layers around the center one
	     * @param radius The radius of each hexagon
	     * @param padding The distance between hexagons
	     */
	    private void drawHexGridLoop(Graphics g, Point origin, int size, int radius, int padding) {
	    	// size: the total number of rows and columns 
	    	size = size*2 + 1; // Correction: Config.Board.size returns the number of layers around the center tile
	        double ang30 = Math.toRadians(30);
	        double xOff = Math.cos(ang30) * (radius + padding);
	        double yOff = Math.sin(ang30) * (radius + padding);
	        int half = size / 2;

	        for (int row = 0; row < size; row++) {
	            int cols = size - java.lang.Math.abs(row - half);

	            for (int col = 0; col < cols; col++) {
	                int xLbl = row < half ? col - row : col - half;
	                int yLbl = row - half;
	                int x = (int) (origin.getX() + xOff * (col * 2 + 1 - cols));
	                int y = (int) (origin.getY() + yOff * (row - half) * 3);

	                drawHex(g, xLbl, yLbl, x, y, radius);
	            }
	        }
	    }
	    
	    
	    
	    private void drawHexGridFromPoints(Graphics g, Point origin, int layerNum, int radius, int padding, List<gameLogic.Point> points) {
	    	int size = layerNum*2 + 1; // size: the total number of rows and columns 
	        
	        int pointNum = points.size();
	        
	        for(int i=0; i<pointNum; ++i) {
	        	gameLogic.Point pointi = points.get(i);
		        double xOff = pointi.getDescartesX()*(radius+padding)*2;
		        double yOff = pointi.getDescartesY()*(radius+padding)*2;
	        	int xLbl = pointi.getJ();
                int yLbl = pointi.getI();
                int x = (int) (origin.getX() + xOff);
                int y = (int) (origin.getY() + yOff);
                
                drawHex(g, xLbl, yLbl, x, y, radius);
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
	     */
	    private void drawHex(Graphics g, int posX, int posY, int x, int y, int r) {
	        Graphics2D g2d = (Graphics2D) g;

	        Hexagon hex = new Hexagon(x, y, r);
	        String text = String.format("%s : %s", coord(posX), coord(posY));
	        int w = metrics.stringWidth(text);
	        int h = metrics.getHeight();

	        hex.draw(g2d, x, y, Config.Hexagon.outerLineThickness, Config.Hexagon.outerColor, true);
	        hex.draw(g2d, x, y, Config.Hexagon.innerLineThickness, Config.Hexagon.innerColor, false);

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
	
