package graphics;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import config.Config;
import gameLogic.Resource;
import gameLogic.Tile;

public class HexaTile {
	private Hexagon hexagon;
	private Resource resource;
	private Tile tile = null;
	
	private boolean selected = false;
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
		
		String text=tile.getOwner()!=null?tile.getOwner().getName():"free";
        //String text = resource.name();
        
        FontMetrics metrics = g.getFontMetrics();
        
        int w = metrics.stringWidth(text);
        int h = metrics.getHeight();
        
        setOuterColor();
        setInnerColor();
        
		int x = graphicsPoint.getX();
		int y = graphicsPoint.getY();
        
        hexagon.draw(g, x, y, Config.Hexagon.innerLineThickness, innerColor, true);
        hexagon.draw(g, x, y, Config.Hexagon.outerLineThickness, outerColor, false);

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
}
