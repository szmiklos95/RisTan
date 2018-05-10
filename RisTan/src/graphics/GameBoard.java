package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
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

/**
 * The panel where the game is played
 * 
 * @author Mikl�s
 *
 */
public class GameBoard extends JPanel {

	private static final long serialVersionUID = 1L;

	private GameState gameState;
	private boolean boardDrawn = false;

	private ArrayList<HexaTile> hexaTiles;
	private Point origin;
	private boolean tilesInitialized = false; // Necessary because the board (thus the tiles) are generated later

	private boolean aTileIsSelected = false;
	
	private boolean marketIsOpen = false;

	public GameBoard() {
		setPreferredSize(new Dimension(Config.GameBoard.width, Config.GameBoard.height));
		SystemMessage.setSystemMessage(Config.SystemMessages.defaultMsg);
	}

	/**
	 * Constructor that sets the game state and the panel size
	 * 
	 * @param gameState
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
	 * A wrapper function for mouse events
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
	 * Wrapper for handling mouse actions that are valid
	 * 
	 * @param clickedHexaTile
	 */
	private void handleValidMouseAction(HexaTile clickedHexaTile) {

		final JPopupMenu menu = new JPopupMenu("Menu");

		// If we haven't selected any tile yet
		if (!aTileIsSelected) {
			clickedHexaTile.toggleSelected();
			aTileIsSelected = true;
			drawPopupMenuWithActions(menu,clickedHexaTile);

		} else { //We already have a selected tile
			if (clickedHexaTile.isSelected()) { //If the clicked is the selected, remove selection
				// If the clicked tile is already selected
				clickedHexaTile.toggleSelected();
				aTileIsSelected = false;

				// clear the popup menu
				menu.removeAll();
				menu.setVisible(false);
			}
			else { //Find the already selected, and remove it's selection
				for(HexaTile ht : hexaTiles) {
					if(ht.isSelected()) ht.clearSelected();
					
					// clear the popup menu
					menu.removeAll();
					menu.setVisible(false);
				}
				clickedHexaTile.toggleSelected();
				drawPopupMenuWithActions(menu,clickedHexaTile);
			}
		}

	}

	/**
	 * Sets and draws the given popup menu
	 * @param menu
	 * @param clickedHexaTile
	 */
	private void drawPopupMenuWithActions(JPopupMenu menu, HexaTile clickedHexaTile) {
		// Get all the possible tile actions
		List<TileAction> possibleTileActions = gameState.getPossibleTileActions();
		
		// Iterate through all the tile actions
		for (TileAction tileAction : possibleTileActions) {
			if (tileAction.getPoint().equals(clickedHexaTile.getPoint())) {

				JMenuItem menuItem = new JMenuItem(tileAction.toString());

				menuItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						executeAction(tileAction.getClass().getCanonicalName(), clickedHexaTile);
					}
				});

				menu.add(menuItem);
				int popUpX = (int) clickedHexaTile.getGraphicsPoint().getX() + Config.Hexagon.radius;
				int popUpY = (int) clickedHexaTile.getGraphicsPoint().getY();
				menu.show(CardSync.card_GameWindow, popUpX, popUpY);
				
			}
		}
	}
	
	
	/**
	 * 
	 * @param actionString
	 * @param clickedHexaTile
	 */
	private void executeAction(String actionString, HexaTile clickedHexaTile) {
		
		if(actionString.equals(OccupyFreeTileFree.class.getCanonicalName())) {
			CardSync.controller.sendAction(new OccupyFreeTileFree(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}
		
		if(actionString.equals(BuildVillageFree.class.getCanonicalName())) {
			CardSync.controller.sendAction(new BuildVillageFree(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}
		
		if(actionString.equals(BuildVillage.class.getCanonicalName())) {
			CardSync.controller.sendAction(new BuildVillage(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}
		
		if(actionString.equals(BuildTown.class.getCanonicalName())) {
			CardSync.controller.sendAction(new BuildTown(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}
		
		if(actionString.equals(OccupyEnemyTile.class.getCanonicalName())) {
			CardSync.controller.sendAction(new OccupyEnemyTile(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}
		
		if(actionString.equals(OccupyEnemyTileL2.class.getCanonicalName())) {
			CardSync.controller.sendAction(new OccupyEnemyTileL2(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}
		
		if(actionString.equals(OccupyEnemyTown.class.getCanonicalName())) {
			CardSync.controller.sendAction(new OccupyEnemyTown(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}
		
		if(actionString.equals(OccupyEnemyTownL2.class.getCanonicalName())) {
			CardSync.controller.sendAction(new OccupyEnemyTownL2(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}
		
		if(actionString.equals(OccupyEnemyVillage.class.getCanonicalName())) {
			CardSync.controller.sendAction(new OccupyEnemyVillage(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}
		
		if(actionString.equals(OccupyEnemyVillageL2.class.getCanonicalName())) {
			CardSync.controller.sendAction(new OccupyEnemyVillageL2(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}
		
		if(actionString.equals(OccupyFreeTile.class.getCanonicalName())) {
			CardSync.controller.sendAction(new OccupyFreeTile(gameState.getActivePlayer().getID(), clickedHexaTile.getPoint()));
		}
		
		clickedHexaTile.clearSelected();
		aTileIsSelected = false;
	}

	/**
	 * Gets the Board from the gameState, necessary because the board is only
	 * created at the start of the game which is later than the GameBoard
	 * instantiation.
	 * 
	 * @return Board from gameState
	 */
	private gameLogic.Board getBoard() {
		return gameState.getBoard();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * 
	 * The main drawing function.
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
			drawTiles(g2d, origin);
		}
	}

	/**
	 * 
	 * @param origin
	 * @param radius
	 * @param padding
	 * @param points
	 */
	private void initTiles(Point origin, List<gameLogic.Point> points) {
		int pointNum = points.size();

		for (int i = 0; i < pointNum; ++i) {
			gameLogic.Point pointi = points.get(i);
			hexaTiles.add(new HexaTile(origin, pointi, getBoard().getTileAt(pointi)));
		}
	}

	/**
	 * 
	 * @param g
	 * @param origin
	 */
	private void drawTiles(Graphics g, Point origin) {
		Graphics2D g2d = (Graphics2D) g;
		for (HexaTile t : hexaTiles) {
			t.draw(g2d);
		}
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

	/**
	 * A timer that is only active before the game starts. When every player joined
	 * draw the board if it hasn't been drawn yet. (Otherwise only the client that
	 * joined last gets a board.)
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
					
					new GameMenubar(); //To open the market
					
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

	private void periodicUpdate() {
		int delay = Config.Timer.periodicUpdateInterval; // milliseconds
		final Timer timer = new Timer(delay, null);
		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (CardSync.controller.isActivePlayer()) {
					SystemMessage.setSystemMessage(Config.SystemMessages.YourTurn.sysMsg);
					//Turn name
					SystemMessage.addSubMessage(gameState.getTurn().toString());
					//Remaining time
					int remainingTime = gameState.getTurn().getRemainingTime();
					SystemMessage.addSubMessage(Config.SystemMessages.YourTurn.remainingTime + remainingTime);
					//Score
					int score = gameState.getActivePlayer().getScore();
					SystemMessage.addSubMessage(Config.SystemMessages.YourTurn.score + score);
					//Resources
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
		//CardSync.frame.remove(CardSync.frame.getJMenuBar());
		//new GameMenubar();
		//SystemMessage.write();
		//CardSync.frame.getJMenuBar().revalidate();
		//CardSync.frame.getJMenuBar().repaint();
	}
	
	/**
	 * Open/Close market depending on a lot of factors.
	 */
	private void toggleMarket() {
		if(CardSync.getGameState().isOver()) return;
		
		if(!marketIsOpen && CardSync.getGameState().getTurn().toString().equals(Config.TurnNames.normal) && CardSync.controller.isActivePlayer()) {
			CardSync.frame.remove(CardSync.frame.getJMenuBar());
			new GameMenubar();
			SystemMessage.write();
			marketIsOpen = true;
		}
		else if(!CardSync.controller.isActivePlayer()) marketIsOpen = false;
		
	}

	/**
	 * Call this everytime something has changed to redraw the game board
	 */
	private void rePaint() {
		fixMenuBarBug();
		revalidate();
		repaint();
	}

	
	/**
	 * 
	 */
	private void highlightAvailableTiles() {
		
		for(HexaTile t : hexaTiles) {
			// Get all the possible tile actions
			List<TileAction> possibleTileActions = gameState.getPossibleTileActions();
			
			if(CardSync.controller.isActivePlayer()) {
				
				//Make the highlight disappear (in case it was highlighted before)
				t.setAvailableForAction(false);
				
				// Iterate through all the tile actions
				for (TileAction tileAction : possibleTileActions) {
					if (tileAction.getPoint().equals(t.getPoint())) {
						t.setAvailableForAction(true);
						break; //Exit this loop as we only need to have 1 action to mark the tile available
					}
				}

			}
			else {
				t.setAvailableForAction(false);
			}

			
		}
	}
	
}