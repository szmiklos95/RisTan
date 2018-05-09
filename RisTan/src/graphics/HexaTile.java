package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import config.Config;
import gameLogic.Resource;
import gameLogic.Tile;

public class HexaTile {
	private Hexagon hexagon;
	private Resource resource;
	private Tile tile = null;
	
	private boolean selected = false;
	private boolean availableForAction = false;
	
	private gameLogic.Point point;
	private graphics.Point graphicsPoint;
    
    private int innerColor = Config.Hexagon.innerColor_default;
    private int outerColor = Config.Hexagon.outerColor_default;
	
	public HexaTile(graphics.Point origin, gameLogic.Point point, Tile tile) {
		this.setPoint(point);
		this.setTile(tile);
		this.resource = tile.getResource();
		
		int radius = Config.Hexagon.radius;
		int padding = Config.Hexagon.padding;
		
        double xOff = point.getDescartesX()*(radius+padding)*2;
        double yOff = point.getDescartesY()*(radius+padding)*2;
        int x = (int) (origin.getX() + xOff);
        int y = (int) (origin.getY() + yOff);
		
        graphicsPoint = new graphics.Point(x,y);
        
		hexagon = new Hexagon(x,y,radius);
	}
	
	
	public void draw(Graphics2D g) {
		
		String text=tile.getOwner()!=null?tile.getOwner().getName():Config.Hexagon.freeTileString;
        //String text = resource.name();
        
        FontMetrics metrics = g.getFontMetrics();
        
        int w = metrics.stringWidth(text);
        int h = metrics.getHeight();
        
        setOuterColor();
        setInnerColor();
        
		int x = graphicsPoint.getX();
		int y = graphicsPoint.getY();
        
		//Draw inner hexagon
        hexagon.draw(g, x, y, Config.Hexagon.innerLineThickness, innerColor, true);
        //Draw outer hexagon
        hexagon.draw(g, x, y, Config.Hexagon.outerLineThickness, outerColor, false);
        
        //Draw player circle
        if(tile.getOwner()!=null) {
        	int playerID = tile.getOwner().getID();
        	int color = Config.PlayerCircle.defaultColor;
        	switch(playerID) {
        		case 0: color = Config.PlayerCircle.color_player0; break;
        		case 1: color = Config.PlayerCircle.color_player1; break;
        		case 2: color = Config.PlayerCircle.color_player2; break;
        		case 3: color = Config.PlayerCircle.color_player3; break;
        		default: color = Config.PlayerCircle.defaultColor; break;
        	}
        	drawCircle(g, graphicsPoint, Config.PlayerCircle.radius, true, true, color, Config.PlayerCircle.lineThickness);
        }
        
        //Draw text
        g.setColor(new Color(Config.Hexagon.textColor));
        g.drawString(text, x - w/2, y + h/2);
	}
	
	public Hexagon getHexagon() {
		return hexagon;
	}
	
    public void toggleSelected() {
    	selected = !selected;
    }
    
    public void setSelected() {
    	selected = true;
    }
    
    public void clearSelected() {
    	selected = false;
    }
    
    public boolean isSelected() {
    	return selected;
    }
	
    private void setOuterColor() {
        outerColor = Config.Hexagon.outerColor_default;
        
        if(availableForAction) outerColor = Config.Hexagon.outerColor_availableForAction;
        
        //Change outer color of the tile is selected
        if(isSelected()) outerColor = Config.Hexagon.outerColor_selected;
    }
    
    private void setInnerColor() {
    	innerColor = Config.Hexagon.innerColor_default;
    	switch(resource) {
    		case Stone: innerColor = Config.Hexagon.innerColor_stone; break;
    		case Wood: innerColor = Config.Hexagon.innerColor_wood; break;
    		case Wheat: innerColor = Config.Hexagon.innerColor_wheat; break;
    		default: innerColor = Config.Hexagon.innerColor_default; break;
    	}
    }

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}


	public gameLogic.Point getPoint() {
		return point;
	}


	public void setPoint(gameLogic.Point point) {
		this.point = point;
	}
	
	public graphics.Point getGraphicsPoint(){
		return graphicsPoint;
	}
	
	public void setAvailableForAction(boolean available) {
		availableForAction = available;
	}
	
	public boolean getAvailableForAction(){
		return availableForAction;
	}
	
	/**
	 * Draws a circle with the given parameters.
	 * 
	 * @param g
	 *            Graphics object
	 * @param origin
	 *            The center of the circle
	 * @param radius
	 *            The radius of the circle
	 * @param centered
	 *            Should be true
	 * @param filled
	 * @param colorValue
	 * @param lineThickness
	 */
	private void drawCircle(Graphics2D g, Point origin, int radius, boolean centered, boolean filled, int colorValue,
			int lineThickness) {
		// Store before changing.
		Stroke tmpS = g.getStroke();
		Color tmpC = g.getColor();

		g.setColor(new Color(colorValue));
		g.setStroke(new BasicStroke(lineThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

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
