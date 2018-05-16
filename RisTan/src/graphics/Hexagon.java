package graphics;

import java.awt.*;

/**
 * A hexagon graphics object class.
 * 
 * @author Miklós
 *
 */
public class Hexagon extends Polygon {

	/**
	 * Default UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Final. The number of sides (points).
	 */
	public static final int SIDES = 6;

	/**
	 * A point for each side.
	 */
	private Point[] points = new Point[SIDES];

	/**
	 * The center point of the hexagon.
	 */
	private Point center = new Point(0, 0);

	/**
	 * The radius of the hexagon.
	 */
	private int radius;

	/**
	 * The rotation (orientation) of the hexagon.
	 */
	private int rotation = 90;

	/**
	 * Constructor. Sets up the center, radius and the points of the hexagon.
	 * 
	 * @param center
	 *            the new center of the hexagon
	 * @param radius
	 *            the new radius of the hexagon
	 */
	public Hexagon(Point center, int radius) {
		npoints = SIDES;
		xpoints = new int[SIDES];
		ypoints = new int[SIDES];

		this.center = center;
		this.radius = radius;

		updatePoints();
	}

	/**
	 * Constructor. The center point can be given using x and y coordinates.
	 * 
	 * @param x
	 *            the x coordinate of the center point
	 * @param y
	 *            the y coordinate of the center point
	 * @param radius
	 *            the radius of the hexagon
	 */
	public Hexagon(int x, int y, int radius) {
		this(new Point(x, y), radius);
	}

	/**
	 * 
	 * @return radius of the hexagon
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Sets the radius.
	 * 
	 * @param radius
	 *            radius of the hexagon
	 */
	public void setRadius(int radius) {
		this.radius = radius;

		updatePoints();
	}

	/**
	 * 
	 * @return rotation of the hexagon
	 */
	public int getRotation() {
		return rotation;
	}

	/**
	 * Sets the rotation of the hexagon.
	 * 
	 * @param rotation
	 *            rotation of the hexagon
	 */
	public void setRotation(int rotation) {
		this.rotation = rotation;

		updatePoints();
	}

	/**
	 * Sets the center of the hexagon.
	 * 
	 * @param center
	 *            the center point of the hexagon
	 */
	public void setCenter(Point center) {
		this.center = center;

		updatePoints();
	}

	/**
	 * Sets the center of the hexagon with Descartes coordinates.
	 * 
	 * @param x
	 *            the x coordinate of the center point
	 * @param y
	 *            the y coordinate of the center point
	 */
	public void setCenter(int x, int y) {
		setCenter(new Point(x, y));
	}

	/**
	 * 
	 * @return the center point of the hexagon
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Calculates the angle based on the current point of a side.
	 * 
	 * @param fraction
	 *            the current point divided by the number of sides
	 * @return angle
	 */
	private double findAngle(double fraction) {
		return fraction * Math.PI * 2 + Math.toRadians((rotation + 180) % 360);
	}

	/**
	 * Returns a hexagon point on the given angle.
	 * 
	 * @param angle
	 *            the angle of the point
	 * @return a point of the hexagon
	 */
	private Point findPoint(double angle) {
		int x = (int) (center.getX() + Math.cos(angle) * radius);
		int y = (int) (center.getY() + Math.sin(angle) * radius);

		return new Point(x, y);
	}

	/**
	 * Updates the points. Call this if any of the hexagon's parameters have
	 * changed.
	 */
	protected void updatePoints() {
		for (int p = 0; p < SIDES; p++) {
			double angle = findAngle((double) p / SIDES);
			Point point = findPoint(angle);
			xpoints[p] = point.getX();
			ypoints[p] = point.getY();
			points[p] = point;
		}
	}

	/**
	 * Draws the hexagon with the given parameters on the given graphics object.
	 * 
	 * @param g
	 *            the graphics object where the hexagon is realised
	 * @param lineThickness
	 *            the thickness of the hexagon line
	 * @param colorValue
	 *            the color of the hexagon
	 * @param filled
	 *            set true if the hexaon should be filled
	 */
	public void draw(Graphics2D g, int lineThickness, int colorValue, boolean filled) {
		// Store before changing.
		Stroke tmpS = g.getStroke();
		Color tmpC = g.getColor();

		g.setColor(new Color(colorValue));
		g.setStroke(new BasicStroke(lineThickness, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));

		if (filled)
			g.fillPolygon(xpoints, ypoints, npoints);
		else
			g.drawPolygon(xpoints, ypoints, npoints);

		// Set values to previous when done.
		g.setColor(tmpC);
		g.setStroke(tmpS);
	}

}