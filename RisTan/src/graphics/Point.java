package graphics;

/**
 * Graphics point class that uses the real coordinates of the window.
 * 
 * @author Miklós
 *
 */
public class Point {

	/**
	 * The X coordinate.
	 */
	private int x;

	/**
	 * The Y coordinate.
	 */
	private int y;

	/**
	 * Constructor.
	 * 
	 * @param p
	 *            a point with x and y coordinates
	 */
	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	/**
	 * Constructor.
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * @return the x coordinate of this point
	 */
	public int getX() {
		return x;
	}

	/**
	 * Set the x coordinate of this point.
	 * 
	 * @param x
	 *            the x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * 
	 * @return the y coordinate of this point
	 */
	public int getY() {
		return y;
	}

	/**
	 * Set the y coordinate of this point.
	 * 
	 * @param y
	 *            the y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Checks if this points is equal to another object (like another point).
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (other == this) {
			return true;
		}
		if (!(other instanceof Point)) {
			return false;
		}
		Point that = (Point) other;
		return (that.x == x) && (that.y == y);
	}

	/**
	 * Prints out the x and y coordinates of this point. Used for debugging.
	 */
	public void printPoint() {
		System.out.printf("(%d:%d)\n", x, y);
	}

}
