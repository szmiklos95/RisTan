package graphics;

import java.awt.Polygon;

import config.Config;

public class Hexagon {
    private final int radius;
    private final Point center;
    private final Polygon hexagon;

    public Hexagon(Point coordinates) {
    	radius = Config.Hexagon.radius;
    	this.center = convertToCenter(coordinates);
        this.hexagon = createHexagon();
    }

    private Polygon createHexagon() {
        Polygon polygon = new Polygon();

        for (int i = 0; i < 6; i++) {
            int xval = (int) (center.getX() + radius
                    * Math.cos(i * 2 * Math.PI / 6D));
            int yval = (int) (center.getY() + radius
                    * Math.sin(i * 2 * Math.PI / 6D));
            polygon.addPoint(xval, yval);
        }

        return polygon;
    }

    public int getRadius() {
        return radius;
    }

    public Point getCenter() {
        return center;
    }

    public Polygon getHexagon() {
        return hexagon;
    }
    
    private Point convertToCenter(Point coordinates) {
    	coordinates.printPoint();
    	int boardCenterX = Config.DrawingPanel.width/2;
    	int boardCenterY = Config.DrawingPanel.height/2;
    	int newX = boardCenterX + coordinates.getX()*radius*2;
    	int newY = boardCenterY + coordinates.getY()*radius*2;
    	Point center = new Point(newX,newY);
    	return center;
    }

}

