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
    private Point point;
    private int radius;
    
    private int innerColor = Config.Hexagon.innerColor_default;
    private int outerColor = Config.Hexagon.outerColor_default;
	
	public HexaTile(Point point, int r, Tile tile) {
		this.point = point;
		this.radius = r;
		this.setTile(tile);
		this.resource = tile.getResource();
		hexagon = new Hexagon(point, radius);
	}
	
	public HexaTile(int x, int y, int r, Tile tile) {
		this.point = new Point(x,y);
		this.radius = r;
		this.setTile(tile);
		this.resource = tile.getResource();
		hexagon = new Hexagon(x,y,r);
	}
	
	public void draw(Graphics2D g) {
		
        String text = resource.name();
        
        FontMetrics metrics = g.getFontMetrics();
        
        int w = metrics.stringWidth(text);
        int h = metrics.getHeight();
        
        setOuterColor();
        setInnerColor();
        
		int x = point.getX();
		int y = point.getY();
        
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
}
