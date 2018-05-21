package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import config.Config;
import gameLogic.Resource;
import gameLogic.Tile;

/**
 * This class represents a whole hexagon tile that appears on the game board. It
 * fuses graphics with game logic. Each hexatile actually contains 2 hexagons,
 * an inner one and an outer one. The outer one is always bigger so it appears
 * as a padding for the inner one.
 * 
 * @author Miklós
 *
 */
public class HexaTile {

	/**
	 * The hexagon polygon object.
	 */
	private Hexagon hexagon;

	/**
	 * The resource on this tile.
	 */
	private Resource resource;

	/**
	 * A game logic tile
	 */
	private Tile tile = null;

	/**
	 * True if this hexatile is selected.
	 */
	private boolean selected = false;

	/**
	 * True if this hexatile has any available actions.
	 */
	private boolean availableForAction = false;

	/**
	 * Game logic point for using the hexa coordinate system.
	 */
	private gameLogic.Point point;

	/**
	 * Graphics point for using the real coordinate system of java.
	 */
	private graphics.Point graphicsPoint;

	/**
	 * The color of the inner hexagon.
	 */
	private int innerColor = Config.Hexagon.innerColor_default;

	/**
	 * The color of the outer hexagon.
	 */
	private int outerColor = Config.Hexagon.outerColor_default;

	/**
	 * Constructor. Initialises the fields.
	 * 
	 * @param origin
	 *            a graphics point type origin point
	 * @param point
	 *            a game logic point type point, same as the origin but in hexa
	 *            coordinate system
	 * @param tile
	 *            a game logic tile
	 */
	public HexaTile(graphics.Point origin, gameLogic.Point point, Tile tile) {
		this.setPoint(point);
		this.setTile(tile);
		if(tile!=null) this.resource = tile.getResource();

		int radius = Config.Hexagon.radius;
		int padding = Config.Hexagon.padding;

		double xOff = point.getDescartesX() * (radius + padding) * 2;
		double yOff = point.getDescartesY() * (radius + padding) * 2;
		int x = (int) (origin.getX() + xOff);
		int y = (int) (origin.getY() + yOff);

		graphicsPoint = new graphics.Point(x, y);

		hexagon = new Hexagon(x, y, radius);
	}

	/**
	 * Draws the hexatile.
	 * 
	 * @param g
	 *            graphics container object
	 */
	public void draw(Graphics2D g) {

		String text = tile.getOwner() != null ? tile.getOwner().getName() : Config.Hexagon.freeTileString;
		// String text = resource.name();

		FontMetrics metrics = g.getFontMetrics();

		int w = metrics.stringWidth(text);
		int h = metrics.getHeight();

		setOuterColor();
		setInnerColor();

		int x = graphicsPoint.getX();
		int y = graphicsPoint.getY();

		// Draw inner hexagon
		hexagon.draw(g, Config.Hexagon.innerLineThickness, innerColor, true);
		// Draw outer hexagon
		hexagon.draw(g, Config.Hexagon.outerLineThickness, outerColor, false);

		// Draw player circle
		int playerColor = getPlayerColor();

		if (tile.getOwner() != null) {
			drawCircle(g, graphicsPoint, Config.PlayerCircle.radius, true, true, playerColor,
					Config.PlayerCircle.lineThickness);
		}

		// Draw buildings
		drawBuildings(g, playerColor);

		// Draw text
		g.setColor(new Color(Config.Hexagon.textColor));
		g.drawString(text, x - w / 2, y + h / 2);

	}

	/**
	 * 
	 * @return the hexagon polygon object
	 */
	public Hexagon getHexagon() {
		return hexagon;
	}

	/**
	 * If the hexatile was selected, unselect it.. else mark this hexatile as
	 * selected.
	 */
	public void toggleSelected() {
		selected = !selected;
	}

	/**
	 * Set this hexatile as selected.
	 */
	public void setSelected() {
		selected = true;
	}

	/**
	 * Mark this hexatile as not selected.
	 */
	public void clearSelected() {
		selected = false;
	}

	/**
	 * Checks whether this hexatile is selected or not
	 * 
	 * @return true if it is selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Sets the outer hexagon's color based on different factors. Current
	 * configuration has 3 possible options: normal, selected, available for action.
	 */
	private void setOuterColor() {
		outerColor = Config.Hexagon.outerColor_default;

		if (availableForAction)
			outerColor = Config.Hexagon.outerColor_availableForAction;

		// Change outer color of the tile is selected
		if (isSelected())
			outerColor = Config.Hexagon.outerColor_selected;
	}

	/**
	 * Sets the inner hexagon's color based on it's resrouce.
	 */
	private void setInnerColor() {
		innerColor = Config.Hexagon.innerColor_default;
		switch (resource) {
		case Stone:
			innerColor = Config.Hexagon.innerColor_stone;
			break;
		case Wood:
			innerColor = Config.Hexagon.innerColor_wood;
			break;
		case Wheat:
			innerColor = Config.Hexagon.innerColor_wheat;
			break;
		default:
			innerColor = Config.Hexagon.innerColor_default;
			break;
		}
	}

	/**
	 * Returns a unique color for the player who owns this tile.
	 * 
	 * @return the tile owner's unique color
	 */
	private int getPlayerColor() {
		int color = Config.PlayerColor.color_default;
		if (tile.getOwner() != null) {
			int playerID = tile.getOwner().getID();
			switch (playerID) {
			case 0:
				color = Config.PlayerColor.color_player0;
				break;
			case 1:
				color = Config.PlayerColor.color_player1;
				break;
			case 2:
				color = Config.PlayerColor.color_player2;
				break;
			case 3:
				color = Config.PlayerColor.color_player3;
				break;
			default:
				color = Config.PlayerColor.color_default;
				break;
			}
		}
		return color;
	}

	/**
	 * 
	 * @return the tile of the hexatile
	 */
	public Tile getTile() {
		return tile;
	}

	/**
	 * Sets the tile of the hexatile
	 * 
	 * @param tile
	 *            the tile of the hexatile
	 */
	public void setTile(Tile tile) {
		this.tile = tile;
	}

	/**
	 * 
	 * @return the game logic point of this hexatile
	 */
	public gameLogic.Point getPoint() {
		return point;
	}

	/**
	 * Sets the game logic point of this hexatile
	 * 
	 * @param point
	 *            the game logic point of this hexatile
	 */
	public void setPoint(gameLogic.Point point) {
		this.point = point;
	}

	/**
	 * 
	 * @return the graphics point of this hexatile
	 */
	public graphics.Point getGraphicsPoint() {
		return graphicsPoint;
	}

	/**
	 * Sets whether this hexatile is available for action or not
	 * 
	 * @param available
	 *            set true to mark this hexatile available for action
	 */
	public void setAvailableForAction(boolean available) {
		availableForAction = available;
	}

	/**
	 * 
	 * @return true if this hexatile is available for action
	 */
	public boolean getAvailableForAction() {
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
	 *            set true if the circle should be filled
	 * @param colorValue
	 *            the color of the circle
	 * @param lineThickness
	 *            the line thickness of the circle
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

	/**
	 * Draws a rectangle with the given parameters.
	 * 
	 * @param g
	 *            Container graphics object
	 * @param origin
	 *            the origin point of the rectangle
	 * @param xOff
	 *            the x offset from the origin point
	 * @param yOff
	 *            the y offset from the origin point
	 * @param a
	 *            one of the sides of the rectangle
	 * @param b
	 *            the other side of the rectangle
	 * @param colorValue
	 *            the color of the rectangle
	 * @param lineThickness
	 *            the line thickness of the rectangle
	 */
	private void drawRectangle(Graphics2D g, Point origin, int xOff, int yOff, int a, int b, int colorValue,
			int lineThickness) {
		Stroke tmpS = g.getStroke();
		Color tmpC = g.getColor();

		g.setColor(new Color(colorValue));
		g.setStroke(new BasicStroke(lineThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		g.fillRect(origin.getX() + xOff, origin.getY() + yOff, a, b);

		// Set values to previous when done.
		g.setColor(tmpC);
		g.setStroke(tmpS);
	}

	/**
	 * Draws buildings on this hexatile based on the building's level.
	 * 
	 * @param g
	 *            Graphics container object
	 * @param color
	 *            Color of the buildings
	 */
	private void drawBuildings(Graphics2D g, int color) {

		switch (tile.getBuildingLevel()) {
		case None:
			break;
		case Village:
			drawRectangle(g, graphicsPoint, Config.Rectangle.Square.xOff, Config.Rectangle.Square.yOff,
					Config.Rectangle.Square.width, Config.Rectangle.Square.height, color,
					Config.Rectangle.Square.lineThickness);
			break;
		case Town:
			drawRectangle(g, graphicsPoint, Config.Rectangle.xOff, Config.Rectangle.yOff, Config.Rectangle.width,
					Config.Rectangle.height, color, Config.Rectangle.lineThickness);
			break;
		default:
			break;
		}

	}

}
