package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

import config.Config;


public class DrawingPanel extends JPanel {
 
	private static final long serialVersionUID = 1L;
	private Hexagon hexagon;
	private ArrayList<Point> points;
	private ArrayList<Hexagon> hexagons;
	private int tilenum;

    public DrawingPanel() {
    	tilenum = 0;
    	points = new ArrayList<Point>();
    	hexagons = new ArrayList<Hexagon>();
        setCoordinates();
        setHexagons();
        this.hexagon = hexagon;
        this.setPreferredSize(new Dimension(Config.DrawingPanel.width, Config.DrawingPanel.height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i=0; i<tilenum;++i) {
        	if(i == 0) {
        		g.setColor(Color.RED);
        	}
        	else g.setColor(Color.BLACK);
            g.drawPolygon(hexagons.get(i).getHexagon());
        }
        
    }
    
	/**
	 * Sets the coordinates for the tiles
	 */
	private void setCoordinates() {
		int size=Config.Board.size;
		for(int i=-size;i<=size;++i) {
			for(int j=-size;j<=size;++j) {
				if(Math.abs(i+j)<=size) {
					points.add(new Point(i,j));
					tilenum++;
				}
			}
		}
	}
	
	
	/**
	 * Sets the hexagon coordinates
	 */
	private void setHexagons() {
		Point point;
		for(int i = 0; i<tilenum; ++i) {
			point = points.get(i);
			hexagons.add(new Hexagon(point));
		}
	}
	
}
