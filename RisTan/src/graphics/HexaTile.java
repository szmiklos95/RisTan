package graphics;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import config.Config;
import gameLogic.Resource;

public class HexaTile {
	private Hexagon hexagon;
	private Resource resource;
	private boolean selected = false;
    private Point point;
    private int radius;
	
	public HexaTile(Point point, int r, Resource resource) {
		this.point = point;
		this.radius = r;
		this.resource = resource;
		hexagon = new Hexagon(point, radius);
	}
	
	public HexaTile(int x, int y, int r, Resource resource) {
		this.point = new Point(x,y);
		this.radius = r;
		this.resource = resource;
		hexagon = new Hexagon(x,y,r);
	}
	
	public void draw(Graphics2D g) {
		
        String text = resource.name();
        
        FontMetrics metrics = g.getFontMetrics();
        
        int w = metrics.stringWidth(text);
        int h = metrics.getHeight();
        
        int outerColor = Config.Hexagon.outerColor;
        if(isSelected()) outerColor = Config.Hexagon.selectedOuterColor;
        
		int x = point.getX();
		int y = point.getY();
        
        hexagon.draw(g, x, y, Config.Hexagon.innerLineThickness, Config.Hexagon.innerColor, true);
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
	
}
