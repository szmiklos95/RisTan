package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.Timer;

import config.Config;
import gameLogic.BuildTown;
import gameLogic.BuildVillage;
import gameLogic.BuildVillageFree;
import gameLogic.GameState;
import gameLogic.OccupyEnemyTile;
import gameLogic.OccupyEnemyTileL2;
import gameLogic.OccupyEnemyTown;
import gameLogic.OccupyEnemyTownL2;
import gameLogic.OccupyEnemyVillage;
import gameLogic.OccupyEnemyVillageL2;
import gameLogic.OccupyFreeTile;
import gameLogic.OccupyFreeTileFree;
import gameLogic.Resource;
import gameLogic.TileAction;
import network.Chat;

/**
 * The panel where the game is played. This JPanel is not CardLayout based,
 * rather a dynamic window that uses java graphics. Every change in game state
 * initiates a repaint on the board. There is also a periodic update with timer.
 * 
 * @author Miklós
 *
 */
public class GameBoard extends JPanel {

	/**
	 * Default UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Holds every important status of the current game.
	 */
	private GameState gameState;

	/**
	 * True if the game board has been drawn on the current client's window. False
	 * until every player joins the game.
	 */
	private boolean boardDrawn = false;

	/**
	 * A list for every hexa tile (hexagon graphics + tile logics) on the board.
	 */
	private ArrayList<HexaTile> hexaTiles;

	/**
	 * The center of the game board.
	 */
	private Point origin;

	/**
	 * True if the tiles has been initialised. Necessary because the board (thus the
	 * tiles) are generated later.
	 */
	private boolean tilesInitialized = false;

	/**
	 * True if there is atleast 1 tile selected on the board.
	 */
	private boolean aTileIsSelected = false;

	/**
	 * True if we can use market actions and the market GUI is shown. False if
	 * market has no menu items thus the user cannot use market actions.
	 */
	private boolean marketIsOpen = false;

	/**
	 * Default constructor. Sets the board size and the default system message.
	 */
	public GameBoard() {
		setPreferredSize(new Dimension(Config.GameBoard.width, Config.GameBoard.height));
		SystemMessage.setSystemMessage(Config.SystemMessages.defaultMsg);
	}

	/**
	 * Constructor that sets up all the components: - sets panel size - sets the
	 * game state - sets the chat window - handles mouse events - starts timers
	 * 
	 * @param gameState
	 *            the starting game state of the board
	 */
	public GameBoard(gameLogic.GameState gameState) {
		setPreferredSize(new Dimension(Config.GameBoard.width, Config.GameBoard.height));
		SystemMessage.setSystemMessage(Config.SystemMessages.waitingForPlayers);

		// Set chat window
		JPanel chatPanel = new Chat(CardSync.client, CardSync.settings.getPlayerName());
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(chatPanel);

		// The center point
		origin = new Point(Config.GameBoard.width / 2, Config.GameBoard.height / 2);

		hexaTiles = new ArrayList<HexaTile>();

		this.gameState = gameState;

		handleMouseEvents();

		gameStartRepaintTimer();
		periodicUpdate();

	}

	/**
	 * A wrapper function for mouse events.
	 */
	private void handleMouseEvents() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				super.mousePressed(me);
				for (HexaTile clickedHexaTile : hexaTiles) {

					if (clickedHexaTile.getHexagon().contains(me.getPoint())) {// check if mouse is clicked within shape

						if (!CardSync.controller.isActivePlayer()) {
							SystemMessage.setErrorMessage(Config.SystemMessages.notYourTurn);
						} else {
							handleValidMouseAction(clickedHexaTile);
						}

						rePaint();

					}
				}
			}
		});
	}

	/**
	 * Wrapper for handling mouse actions that are valid.
	 * 
	 * @param clickedHexaTile
	 *            the currently clicked tile
	 */
	private void handleValidMouseAction(HexaTile clickedHexaTile) {

		final JPopupMenu menu = new JPopupMenu("Menu");

		// If we haven't selected any tile yet
		if (!aTileIsSelected) {
			clickedHexaTile.toggleSelected();
			aTileIsSelected = true;
			drawPopupMenuWithActions(menu, clickedHexaTile);

		} else { // We already have a selected tile
			if (clickedHexaTile.isSelected()) { // If the clicked is the selected, remove selection
				// If the clicked tile is already selected
				clickedHexaTile.toggleSelected();
				aTileIsSelected = false;

				// clear the popup menu
				menu.removeAll();
				menu.setVisible(false);
			} else { // Find the already selected, and remove it's selection
				for (HexaTile ht : hexaTiles) {
					if (ht.isSelected())
						ht.clearSelected();

					// clear the popup menu
					menu.removeAll();
					menu.setVisible(false);
				}
				clickedHexaTile.toggleSelected();
				drawPopupMenuWithActions(menu, clickedHexaTile);
			}
		}

	}

	/**
	 * Sets and draws the given popup menu.
	 * 
	 * @param menu
	 *            the popup menu container where we will draw the buttons if there
	 *            are actions
	 * @param clickedHexaTile
	 *            the tile we currently interact with
	 */
	private void drawPopupMenuWithActions(JPopupMenu menu, HexaTile clickedHexaTile) {
		// Get all the possible tile actions
		List<TileAction> possibleTileActions = gameState.getPossibleTileActions();

		JPanel popupPanel = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		popupPanel.setLayout(gbl);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = Config.GUI.GridSettings.startingGridX;
		gbc.gridy = Config.GUI.GridSettings.startingGridY;
		gbc.insets = Config.GUI.GridSettings.defaultInsets;
		gbc.fill = GridBagConstraints.CENTER;

		// Iterate through all the tile actions
		for (TileAction tileAction : possibleTileActions) {
			if (tileAction.getPoint().equals(clickedHexaTile.getPoint())) {

				// Actions as buttons
				gbc.gridx = 0;
				gbc.gridy++;

				JButton actionButton = new JButton(tileAction.toString());
				actionButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						executeAction(tileAction.getClass().getCanonicalName(), clickedHexaTile);
						menu.setVisible(false);
					}
				});
				popupPanel.add(actionButton, gbc);

			}
		}

		menu.add(popupPanel);
		int popUpX = (int) clickedHexaTile.getGraphicsPoint().getX();
		int popUpY = (int) clickedHexaTile.getGraphicsPoint().getY();
		menu.show(CardSync.card_GameWindow, popUpX, popUpY);

	}

	/**
	 * Executes the appropriate game logic action.
	 * 
	 * @param actionString
	 *            the string name of the action used as identifier
	 * @param clickedHexaTile
	 *            the currently clicked tile
	 */
	private void executeAction(String actionString, HexaTile clickedHexaTile) {

		if (actionString.equals(OccupyFreeTileFree.class.getCanonicalName())) {
			CardSync.controller.sendAction(
					new OccupyFreeTileFree(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}

		if (actionString.equals(BuildVillageFree.class.getCanonicalName())) {
			CardSync.controller
					.sendAction(new BuildVillageFree(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}

		if (actionString.equals(BuildVillage.class.getCanonicalName())) {
			CardSync.controller
					.sendAction(new BuildVillage(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}

		if (actionString.equals(BuildTown.class.getCanonicalName())) {
			CardSync.controller
					.sendAction(new BuildTown(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}

		if (actionString.equals(OccupyEnemyTile.class.getCanonicalName())) {
			CardSync.controller
					.sendAction(new OccupyEnemyTile(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}

		if (actionString.equals(OccupyEnemyTileL2.class.getCanonicalName())) {
			CardSync.controller
					.sendAction(new OccupyEnemyTileL2(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}

		if (actionString.equals(OccupyEnemyTown.class.getCanonicalName())) {
			CardSync.controller
					.sendAction(new OccupyEnemyTown(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}

		if (actionString.equals(OccupyEnemyTownL2.class.getCanonicalName())) {
			CardSync.controller
					.sendAction(new OccupyEnemyTownL2(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}

		if (actionString.equals(OccupyEnemyVillage.class.getCanonicalName())) {
			CardSync.controller.sendAction(
					new OccupyEnemyVillage(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}

		if (actionString.equals(OccupyEnemyVillageL2.class.getCanonicalName())) {
			CardSync.controller.sendAction(
					new OccupyEnemyVillageL2(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}

		if (actionString.equals(OccupyFreeTile.class.getCanonicalName())) {
			CardSync.controller
					.sendAction(new OccupyFreeTile(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}

		if (actionString.equals(OccupyEnemyTown.class.getCanonicalName())) {
			CardSync.controller
					.sendAction(new OccupyEnemyTown(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}

		if (actionString.equals(OccupyEnemyTownL2.class.getCanonicalName())) {
			CardSync.controller
					.sendAction(new OccupyEnemyTownL2(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}

		clickedHexaTile.clearSelected();
		aTileIsSelected = false;
	}

	/**
	 * Gets the Board from the gameState, necessary because the board is only
	 * created at the start of the game which is later than the GameBoard
	 * instantiation.
	 * 
	 * @return board from gameState
	 */
	private gameLogic.Board getBoard() {
		return gameState.getBoard();
	}

	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * 
	 *      The main drawing function.
	 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
		g2d.setFont(Config.GameBoard.font);

		drawCircle(g2d, origin, Config.Circle.radius, true, true, Config.Circle.color, Config.Circle.lineThickness);

		// drawTiles(g2d, origin);
		// drawHexGridFromPoints(g2d, origin, Config.Hexagon.radius,
		// Config.Hexagon.padding, points);

		gameLogic.Board board = getBoard();
		if (board != null) {
			if (!tilesInitialized) {
				ArrayList<gameLogic.Point> points = new ArrayList<gameLogic.Point>(board.getTileCoordinates());
				initTiles(origin, points);
				tilesInitialized = true;
			}
			drawTiles(g2d);
		}
	}

	/**
	 * Initialises the hexa tiles. Sets their coordinates and their game logic tile
	 * field.
	 * 
	 * @param origin
	 *            the center of the board
	 * @param points
	 *            the list of the points that will be initialised
	 */
	private void initTiles(Point origin, List<gameLogic.Point> points) {
		int pointNum = points.size();

		for (int i = 0; i < pointNum; ++i) {
			gameLogic.Point pointi = points.get(i);
			hexaTiles.add(new HexaTile(origin, pointi, getBoard().getTileAt(pointi)));
		}
	}

	/**
	 * Draws each hexa tile.
	 * 
	 * @param g
	 *            graphics container for drawing
	 */
	private void drawTiles(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (HexaTile t : hexaTiles) {
			t.draw(g2d);
		}
	}

	/**
	 * Draws a circle with the given parameters.
	 * 
	 * @param g
	 *            graphics container object
	 * @param origin
	 *            the center of the circle
	 * @param radius
	 *            the radius of the circle
	 * @param centered
	 *            should be true
	 * @param filled
	 *            filled or empty circle
	 * @param colorValue
	 *            the color of the circle
	 * @param lineThickness
	 *            the line thickness
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
	 * A timer that is only active before the game starts. When every player joined
	 * draw the board if it hasn't been drawn yet. (Without this only the client
	 * that joined last gets a board.)
	 */
	private void gameStartRepaintTimer() {

		int delay = Config.Timer.gameStartTimerDelay; // milliseconds
		final Timer timer = new Timer(delay, null);
		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SystemMessage.dotAnimation();

				// If over = false ---> the game is running --> stop the timer because the game
				// already started
				// If the game is already running but we haven't drawn the board yet repaint it
				// (Happens at every client opened except the last one)
				if (!boardDrawn && !gameState.isOver()) {
					timer.stop();
					boardDrawn = true;

					new GameMenubar(); // To open the market

					SystemMessage.setSystemMessage(Config.SystemMessages.boardDrawn);

					rePaint();

				}

				// Stop the timer
				if (boardDrawn)
					timer.stop();

			}
		});
		timer.start();

	}

	/**
	 * Starts a timer that periodically (set in Config) does important jobs: - sets
	 * the system message - gets the current game state - highlights the available
	 * tiles - checks if whether the game is finished - repaints the board
	 */
	private void periodicUpdate() {
		int delay = Config.Timer.periodicUpdateInterval; // milliseconds
		final Timer timer = new Timer(delay, null);
		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (CardSync.controller.isActivePlayer()) {
					SystemMessage.setSystemMessage(Config.SystemMessages.YourTurn.sysMsg);
					// Turn name
					SystemMessage.addSubMessage(gameState.getTurn().toString());
					// Remaining time
					int remainingTime = gameState.getTurn().getRemainingTime();
					SystemMessage.addSubMessage(Config.SystemMessages.YourTurn.remainingTime + remainingTime);
					// Score
					int score = gameState.getActivePlayer().getScore();
					SystemMessage.addSubMessage(Config.SystemMessages.YourTurn.score + score);
					// Resources
					int wood = gameState.getActivePlayer().getResourceAmount(Resource.Wood);
					SystemMessage.addSubMessage(Config.SystemMessages.YourTurn.wood + wood);
					int stone = gameState.getActivePlayer().getResourceAmount(Resource.Stone);
					SystemMessage.addSubMessage(Config.SystemMessages.YourTurn.stone + stone);
					int wheat = gameState.getActivePlayer().getResourceAmount(Resource.Wheat);
					SystemMessage.addSubMessage(Config.SystemMessages.YourTurn.wheat + wheat);

				} else if (!gameState.isOver()) {
					SystemMessage.setSystemMessage("It is " + gameState.getActivePlayer().getName() + "'s turn.");
				}

				gameState = CardSync.controller.getGameState();
				highlightAvailableTiles();
				rePaint();
				gameOverHandler();
			}
		});
		timer.start();
	}

	/**
	 * Sometimes (for certain actions) a menu bar appears in the background.
	 * Temporary solution: Reset the menu bar every time an action would trigger
	 * this. (The SystemMessage.write does just that)
	 */
	private void fixMenuBarBug() {
		SystemMessage.write();
		toggleMarket();
	}

	/**
	 * Open/Close market depending on a multiple factors.
	 */
	private void toggleMarket() {

		if (!CardSync.getGameState().isOver()) { // If the turn isn't over
			if (!marketIsOpen && CardSync.controller.isActivePlayer()) {
				CardSync.frame.remove(CardSync.frame.getJMenuBar());
				new GameMenubar();
				SystemMessage.write();
				marketIsOpen = true;
			} else if (!CardSync.controller.isActivePlayer()) {
				CardSync.frame.remove(CardSync.frame.getJMenuBar());
				new GameMenubar();
				SystemMessage.write();
				marketIsOpen = false;
			}
		}

	}

	/**
	 * Call this every time something has changed to redraw the game board
	 */
	private void rePaint() {
		fixMenuBarBug();
		revalidate();
		repaint();
	}

	/**
	 * Iterates through every hexa tile and checks whether the current player can
	 * make any actions on it. If yes then highlight that tile.
	 */
	private void highlightAvailableTiles() {

		for (HexaTile t : hexaTiles) {
			// Get all the possible tile actions
			List<TileAction> possibleTileActions = gameState.getPossibleTileActions();

			if (CardSync.controller.isActivePlayer()) {

				// Make the highlight disappear (in case it was highlighted before)
				t.setAvailableForAction(false);

				// Iterate through all the tile actions
				for (TileAction tileAction : possibleTileActions) {
					if (tileAction.getPoint().equals(t.getPoint())) {
						t.setAvailableForAction(true);
						break; // Exit this loop as we only need to have 1 action to mark the tile available
					}
				}

			} else {
				t.setAvailableForAction(false);
			}

		}
	}

	/**
	 * Checks whether the game has finished.
	 * 
	 * @return true if the game has finished, false otherwise
	 */
	private boolean isFinished() {
		return gameState.isFinished();
	}

	/**
	 * Handles the game over event by drawing a popup window.
	 */
	private void gameOverHandler() {
		if (isFinished()) {
			new GameOverPopup(CardSync.frame);
		}
	}

}