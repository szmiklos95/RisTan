package config;

import java.util.Map;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gameLogic.BuildVillageTurn;
import gameLogic.GetResourceTurn;
import gameLogic.NormalTurn;
import gameLogic.OccupyFreeTileTurn;
import gameLogic.Resource;
import gameLogic.StartTileChoiceTurn;
import gameLogic.Turn;

/**
 * Configuration options for the game. Collection of magic constants.
 * 
 * @author Andras
 *
 */
public abstract class Config {
	/**
	 * Actions config.
	 * 
	 * @author Andras
	 *
	 */
	public static class Action {
		/**
		 * Display name of an Action.
		 */
		public static final String name = "Default Action String";

		/**
		 * AcceptTradeAction config.
		 * 
		 * @author Andras
		 *
		 */
		public static class AcceptTradeAction {
			/**
			 * Time requirement of an AcceptTradeAction.
			 */
			public static final int time = 1;
			/**
			 * Display name of an AcceptTradeAction.
			 */
			public static final String name = "Accept trade";
		}

		/**
		 * BuildTown config.
		 * 
		 * @author Andras
		 *
		 */
		public static class BuildTown {
			/**
			 * Time requirement of a BuildTown.
			 */
			public static final int time = 3;
			/**
			 * Resource requirement of a BuildTown.
			 */
			public static final Map<Resource, Integer> cost = createCost();

			/**
			 * Creates the resource requirement.
			 * 
			 * @return the resource requirement.
			 */
			private static Map<Resource, Integer> createCost() {
				HashMap<Resource, Integer> ret = new HashMap<Resource, Integer>();
				ret.put(Resource.Stone, 2);
				ret.put(Resource.Wheat, 1);
				ret.put(Resource.Wood, 1);
				return ret;
			}

			/**
			 * Display name of a BuildTown.
			 */
			public static final String name = "Build town";
		}

		/**
		 * BuildVillage config.
		 * 
		 * @author Andras
		 *
		 */
		public static class BuildVillage {
			/**
			 * Time requirement of a BuildVillage.
			 */
			public static final int time = 2;
			/**
			 * Resource requirement of a BuildVillage.
			 */
			public static final Map<Resource, Integer> cost = createCost();

			/**
			 * Creates the resource requirement.
			 * 
			 * @return the resource requirement.
			 */
			private static Map<Resource, Integer> createCost() {
				HashMap<Resource, Integer> ret = new HashMap<Resource, Integer>();
				ret.put(Resource.Stone, 1);
				ret.put(Resource.Wheat, 1);
				ret.put(Resource.Wood, 1);
				return ret;
			}

			/**
			 * Display name of a BuildVillage.
			 */
			public static final String name = "Build village";
		}

		/**
		 * OccupyEnemyTile config.
		 * 
		 * @author Andras
		 *
		 */
		public static class OccupyEnemyTile {
			/**
			 * Time requirement of an OccupyEnemyTile.
			 */
			public static final int time = 2;
			/**
			 * Resource requirement of an OccupyEnemyTile.
			 */
			public static final Map<Resource, Integer> cost = createCost();

			/**
			 * Creates the resource requirement.
			 * 
			 * @return the resource requirement.
			 */
			private static Map<Resource, Integer> createCost() {
				HashMap<Resource, Integer> ret = new HashMap<Resource, Integer>();
				ret.put(Resource.Wheat, 1);
				return ret;
			}

			/**
			 * Success probability of an OccupyEnemyTile.
			 */
			public static final double probability = 0.5;
			/**
			 * Display name of an OccupyEnemyTile.
			 */
			public static final String name = "Occupy enemy tile";
		}

		/**
		 * OccupyEnemyTileL2 config.
		 * 
		 * @author Andras
		 *
		 */
		public static class OccupyEnemyTileL2 {
			/**
			 * Time requirement of an OccupyEnemyTileL2.
			 */
			public static final int time = 2;
			/**
			 * Resource requirement of an OccupyEnemyTileL2.
			 */
			public static final Map<Resource, Integer> cost = createCost();

			/**
			 * Creates the resource requirement.
			 * 
			 * @return the resource requirement.
			 */
			private static Map<Resource, Integer> createCost() {
				HashMap<Resource, Integer> ret = new HashMap<Resource, Integer>();
				ret.put(Resource.Wheat, 2);
				ret.put(Resource.Wood, 1);
				return ret;
			}

			/**
			 * Success probability of an OccupyEnemyTileL2.
			 */
			public static final double probability = 0.75;
			/**
			 * Display name of an OccupyEnemyTileL2.
			 */
			public static final String name = "Occupy enemy tile L2";
		}

		/**
		 * OccupyEnemyTown config.
		 * 
		 * @author Andras
		 *
		 */
		public static class OccupyEnemyTown {
			/**
			 * Time requirement of an OccupyEnemyTown.
			 */
			public static final int time = 4;
			/**
			 * Resource requirement of an OccupyEnemyTown.
			 */
			public static final Map<Resource, Integer> cost = createCost();

			/**
			 * Creates the resource requirement.
			 * 
			 * @return the resource requirement.
			 */
			private static Map<Resource, Integer> createCost() {
				HashMap<Resource, Integer> ret = new HashMap<Resource, Integer>();
				ret.put(Resource.Stone, 1);
				ret.put(Resource.Wheat, 3);
				ret.put(Resource.Wood, 3);
				return ret;
			}

			/**
			 * Success probability of an OccupyEnemyTown.
			 */
			public static final double probability = 0.5;
			/**
			 * Display name of an OccupyEnemyTown.
			 */
			public static final String name = "Occupy enemy town";
		}

		/**
		 * OccupyEnemyTownL2 config.
		 * 
		 * @author Andras
		 *
		 */
		public static class OccupyEnemyTownL2 {
			/**
			 * Time requirement of an OccupyEnemyTownL2.
			 */
			public static final int time = 4;
			/**
			 * Resource requirement of an OccupyEnemyTownL2.
			 */
			public static final Map<Resource, Integer> cost = createCost();

			/**
			 * Creates the resource requirement.
			 * 
			 * @return the resource requirement.
			 */
			private static Map<Resource, Integer> createCost() {
				HashMap<Resource, Integer> ret = new HashMap<Resource, Integer>();
				ret.put(Resource.Stone, 1);
				ret.put(Resource.Wheat, 4);
				ret.put(Resource.Wood, 4);
				return ret;
			}

			/**
			 * Success probability of an OccupyEnemyTownL2.
			 */
			public static final double probability = 0.75;
			/**
			 * Display name of an OccupyEnemyTownL2.
			 */
			public static final String name = "Occupy enemy town L2";
		}

		/**
		 * OccupyEnemyVillage config.
		 * 
		 * @author Andras
		 *
		 */
		public static class OccupyEnemyVillage {
			/**
			 * Time requirement of an OccupyEnemyVillage.
			 */
			public static final int time = 3;
			/**
			 * Resource requirement of an OccupyEnemyVillage.
			 */
			public static final Map<Resource, Integer> cost = createCost();

			/**
			 * Creates the resource requirement.
			 * 
			 * @return the resource requirement.
			 */
			private static Map<Resource, Integer> createCost() {
				HashMap<Resource, Integer> ret = new HashMap<Resource, Integer>();
				ret.put(Resource.Wheat, 2);
				ret.put(Resource.Wood, 2);
				return ret;
			}

			/**
			 * Success probability of an OccupyEnemyVillage.
			 */
			public static final double probability = 0.5;
			/**
			 * Display name of an OccupyEnemyVillage.
			 */
			public static final String name = "Occupy enemy village";
		}

		/**
		 * OccupyEnemyVillageL2 config.
		 * 
		 * @author Andras
		 *
		 */
		public static class OccupyEnemyVillageL2 {
			/**
			 * Time requirement of an OccupyEnemyVillageL2.
			 */
			public static final int time = 3;
			/**
			 * Resource requirement of an OccupyEnemyVillageL2.
			 */
			public static final Map<Resource, Integer> cost = createCost();

			/**
			 * Creates the resource requirement.
			 * 
			 * @return the resource requirement.
			 */
			private static Map<Resource, Integer> createCost() {
				HashMap<Resource, Integer> ret = new HashMap<Resource, Integer>();
				ret.put(Resource.Wheat, 3);
				ret.put(Resource.Wood, 3);
				return ret;
			}

			/**
			 * Success probability of an OccupyEnemyVillageL2.
			 */
			public static final double probability = 0.75;
			/**
			 * Display name of an OccupyEnemyVillageL2.
			 */
			public static final String name = "Occupy enemy village L2";
		}

		/**
		 * OccupyFreeTile config.
		 * 
		 * @author Andras
		 *
		 */
		public static class OccupyFreeTile {
			/**
			 * Time requirement of an OccupyFreeTile.
			 */
			public static final int time = 2;
			/**
			 * Resource requirement of an OccupyFreeTile.
			 */
			public static final Map<Resource, Integer> cost = new HashMap<Resource, Integer>();
			/**
			 * Display name of an OccupyFreeTile.
			 */
			public static final String name = "Occupy free tile";
		}

		/**
		 * OfferTradeAction config.
		 * 
		 * @author Andras
		 *
		 */
		public static class OfferTradeAction {
			/**
			 * Time requirement of an OfferTradeAction.
			 */
			public static final int time = 1;
			/**
			 * Display name of an OfferTradeAction.
			 */
			public static final String name = "Offer trade";
		}

		/**
		 * TradeWithGameAction config.
		 * 
		 * @author Andras
		 *
		 */
		public static class TradeWithGameAction {
			/**
			 * Time requirement of a TradeWithGameAction.
			 */
			public static final int time = 1;
			/**
			 * Change ratio: for every given ratio_D, the player gets ratio_N.
			 */
			public static final int ratio_N = 1;
			/**
			 * Change ratio: for every given ratio_D, the player gets ratio_N.
			 */
			public static final int ratio_D = 3;
			/**
			 * Display name of a TradeWithGameAction.
			 */
			public static final String name = "Trade with game";
		}
	}

	/**
	 * Board config.
	 * 
	 * @author Andras
	 *
	 */
	public static class Board {
		/**
		 * The number of tile layers around the central tile.
		 */
		public static final int size = 3;
		/**
		 * Resource probabilities.
		 */
		public static final Map<Resource, Double> res_prob = createRes_prob();

		/**
		 * Creates the resource probabilities.
		 * 
		 * @return the resource probabilities.
		 */
		private static Map<Resource, Double> createRes_prob() {
			HashMap<Resource, Double> ret = new HashMap<Resource, Double>();
			ret.put(Resource.Stone, ((double) 1) / 3);
			ret.put(Resource.Wheat, ((double) 1) / 3);
			ret.put(Resource.Wood, ((double) 1) / 3);
			return ret;
		}
	}

	/**
	 * BuildingLevel config.
	 * 
	 * @author Andras
	 *
	 */
	public static class BuildingLevel {
		/**
		 * Default building level config.
		 * 
		 * @author Andras
		 *
		 */
		public static class None {
			/**
			 * The amount of resources got from a default building level tile.
			 */
			public static final int resourceNum = 1;
			/**
			 * The amount of score got from a default building level tile.
			 */
			public static final int score = 1;
		}

		/**
		 * Village building level config.
		 * 
		 * @author Andras
		 *
		 */
		public static class Village {
			/**
			 * The amount of resources got from a village building level tile.
			 */
			public static final int resourceNum = 2;
			/**
			 * The amount of score got from a village building level tile.
			 */
			public static final int score = 3;
		}

		/**
		 * Town building level config.
		 * 
		 * @author Andras
		 *
		 */
		public static class Town {
			/**
			 * The amount of resources got from a town building level tile.
			 */
			public static final int resourceNum = 3;
			/**
			 * The amount of score got from a town building level tile.
			 */
			public static final int score = 6;
		}
	}

	/**
	 * GameLogicExceptions config.
	 * 
	 * @author Andras
	 *
	 */
	public static class Exception {
		/**
		 * CantSwitchToNextPlayerException config.
		 * 
		 * @author Andras
		 *
		 */
		public static class CantSwitchToNextPlayer {
			/**
			 * The error message of a CantSwitchToNextPlayerException.
			 */
			public static final String errorMessage = "Can't switch to next player because there are automatic actions or obligatory events which haven't happened yet.\r\n";
		}

		/**
		 * GameLogicException config.
		 * 
		 * @author Andras
		 *
		 */
		public static class GameLogic {
			/**
			 * The error message of a GameLogicException.
			 */
			public static final String errorMessage = "Cannot execute the action because it is invalid.\r\n";
		}

		/**
		 * GameOverException config.
		 * 
		 * @author Andras
		 *
		 */
		public static class GameOver {
			/**
			 * The error message of a GameOverException.
			 */
			public static final String errorMessage = "Cannot execute the action mert vége a játéknak, vagy még nem kezdõdött el.\r\n";
		}

		/**
		 * InvalidTileActionException config.
		 * 
		 * @author Andras
		 *
		 */
		public static class InsufficientResource {
			/**
			 * The error message of a InvalidTileActionException.
			 */
			public static final String errorMessage = "Cannot execute the action because you have insufficient resources.\r\n";
		}

		/**
		 * InvalidTileActionException config.
		 * 
		 * @author Andras
		 *
		 */
		public static class InvalidTileAction {
			/**
			 * The error message of a InvalidTileActionException.
			 */
			public static final String errorMessage = "Cannot execute this action on this tile in this turn.\r\n";
		}

		/**
		 * NoSuchTradeOfferException config.
		 * 
		 * @author Andras
		 *
		 */
		public static class NoSuchTradeOffer {
			/**
			 * The error message of a NoSuchTradeOfferException.
			 */
			public static final String errorMessage = "Cannot execute the action because no such trade offer exists.\r\n";
		}

		/**
		 * NotEnoughTimeException config.
		 * 
		 * @author Andras
		 *
		 */
		public static class NotEnoughTime {
			/**
			 * The error message of a NotEnoughTimeException.
			 */
			public static final String errorMessage = "Cannot execute the action because you do not have enough time.\r\n";
		}

		/**
		 * NoTileAtPointException config.
		 * 
		 * @author Andras
		 *
		 */
		public static class NoTileAtPoint {
			/**
			 * The error message of a NoTileAtPointException.
			 */
			public static final String errorMessage = "Cannot execute the action there is no tile at this point.\r\n";
		}

		/**
		 * PlayerOutOfTurnException config.
		 * 
		 * @author Andras
		 *
		 */
		public static class PlayerOutOfTurn {
			/**
			 * The error message of a PlayerOutOfTurnException.
			 */
			public static final String errorMessage = "Cannot execute the action because an other player is active.\r\n";
		}

		/**
		 * TileIsNotEmptyException config.
		 * 
		 * @author Andras
		 *
		 */
		public static class TileIsNotEmpty {
			/**
			 * The error message of a TileIsNotEmptyException.
			 */
			public static final String errorMessage = "Cannot execute the action on this tile because the tile is empty.\r\n";
		}

		/**
		 * TileIsNotEnemyException config.
		 * 
		 * @author Andras
		 *
		 */
		public static class TileIsNotEnemy {
			/**
			 * The error message of a TileIsNotEnemyException.
			 */
			public static final String errorMessage = "Cannot execute the action on this tile becaouse the tile is not occupied by an other player.\r\n";
		}

		/**
		 * TileIsNotFreeException config.
		 * 
		 * @author Andras
		 *
		 */
		public static class TileIsNotFree {
			/**
			 * The error message of a TileIsNotFreeException.
			 */
			public static final String errorMessage = "Cannot execute the action on this tile because the tile is not free.\r\n";
		}

		/**
		 * TileIsNotOwnException config.
		 * 
		 * @author Andras
		 *
		 */
		public static class TileIsNotOwn {
			/**
			 * The error message of a TileIsNotOwnException.
			 */
			public static final String errorMessage = "Cannot execute the action on this tile because the tile is not own.\r\n";
		}

		/**
		 * TileIsNotTownException config.
		 * 
		 * @author Andras
		 *
		 */
		public static class TileIsNotTown {
			/**
			 * The error message of a TileIsNotTownException.
			 */
			public static final String errorMessage = "Cannot execute the action on this tile because the tile is not a town.\r\n";
		}

		/**
		 * TileIsNotVillageException config.
		 * 
		 * @author Andras
		 *
		 */
		public static class TileIsNotVillage {
			/**
			 * The error message of a TileIsNotVillageException.
			 */
			public static final String errorMessage = "Cannot execute the action on this tile because the tile is not a village.\r\n";
		}

		/**
		 * TradeIsNotAllowedException config.
		 * 
		 * @author Andras
		 *
		 */
		public static class TradeIsNotAllowed {
			/**
			 * The error message of a TradeIsNotAllowedException.
			 */
			public static final String errorMessage = "Cannot execute the action because the trading is disabled.\r\n";
		}
	}

	/**
	 * TurnOrder config.
	 * 
	 * @author Andras
	 *
	 */
	public static class TurnOrder {
		/**
		 * The list of turns in the game.
		 */
		public static final List<Turn> turns = createTurns();

		/**
		 * Creates the list of the turns.
		 * 
		 * @return the list of the turns.
		 */
		private static List<Turn> createTurns() {
			List<Turn> ret = new ArrayList<Turn>();
			ret.add(new StartTileChoiceTurn());
			for (int i = 0; i < 4; ++i) {
				ret.add(new OccupyFreeTileTurn(1));
			}
			ret.add(new BuildVillageTurn());
			ret.add(new GetResourceTurn(createStartingResources()));
			for (int i = 0; i < 10; ++i) {
				ret.add(new NormalTurn(7));
			}
			return ret;
		}

		/**
		 * Creates the list of starting resources.
		 * 
		 * @return the list of starting resources.
		 */
		private static Map<Resource, Integer> createStartingResources() {
			Map<Resource, Integer> ret = new HashMap<Resource, Integer>();
			ret.put(Resource.Stone, 2);
			ret.put(Resource.Wheat, 2);
			ret.put(Resource.Wood, 2);
			return ret;
		}
	}

	/**
	 * Names of the turns.
	 * 
	 * @author Andras
	 *
	 */
	public static class TurnNames {
		/**
		 * Default name of turn.
		 */
		public static final String defaultName = "Default Turn String";
		/**
		 * Name of a village building turn.
		 */
		public static final String buildVillage = "Build village turn";
		/**
		 * Name of a resource getting turn.
		 */
		public static final String getResource = "Get resource turn";
		/**
		 * Name of a normal turn.
		 */
		public static final String normal = "Normal turn";
		/**
		 * Name of a free tile occupation turn.
		 */
		public static final String occupyFreeTile = "Occupy free tile turn";
		/**
		 * Name of the start tile choice turn.
		 */
		public static final String startTileChoice = "Start tile choice turn";
	}

	/**
	 * Names of the resources.
	 * 
	 * @author Andras
	 *
	 */
	public static class ResourceNames {
		/**
		 * The array containing the resource names.
		 */
		public static final String[] resourceStrings = { "Stone", "Wheat", "Wood" };
	}

	/*
	 * *****************************************************************************
	 * 
	 * @@@@@@@@@@@@@@@@@@@@@@@@@@ Graphic configurations @@@@@@@@@@@@@@@@@@@@@@@@@@
	 *****************************************************************************/

	/**
	 * The configurations for the GUI
	 * 
	 * @author Miklós
	 */
	public static class GUI {

		/**
		 * The minimum width of the starting window.
		 */
		public static final int frameMinimumWidth = 300;

		/**
		 * The minimum height of the starting window.
		 */
		public static final int frameMinimumHeight = 300;

		/**
		 * The frame title of the main menu
		 */
		public static final String title = "Welcome to RisTan!";

		/**
		 * The title after someone types his/her name
		 */
		public static final String nameTitle = "Ristan Beta - ";

		/**
		 * Number of players string
		 */
		public static final String playerCount = "Number of players: ";

		// Spinner settings
		/**
		 * The default spinner setting.
		 */
		public static final int default_playerCount = 2;

		/**
		 * The minimum player number to be set.
		 */
		public static final int min_playerCount = 2;

		/**
		 * The maximum player number to be set.
		 */
		public static final int max_playerCount = 4;

		/**
		 * Default settings for the GridBagLayout
		 * 
		 * @author Miklós
		 *
		 */
		public static class GridSettings {
			/**
			 * The starting x grid
			 */
			public static final int startingGridX = 0;

			/**
			 * The starting y grid
			 */
			public static final int startingGridY = 0;

			/**
			 * Default insets
			 */
			public static final Insets defaultInsets = new Insets(10, 10, 10, 10);

			/**
			 * The default grid width
			 */
			public static final int defaultGridWidth = 1;

			/**
			 * The default grid height
			 */
			public static final int defaultGridHeight = 2;
		}

		/**
		 * The text displayed on the buttons
		 * 
		 * @author Miklós
		 *
		 */
		public static class ButtonTexts {
			/**
			 * Main menu button text.
			 */
			public static final String mainMenu = "Main Menu";

			/**
			 * New game button text.
			 */
			public static final String newGame = "New Game";

			/**
			 * Exit button text.
			 */
			public static final String exit = "Exit";

			/**
			 * OK button text.
			 */
			public static final String ok = "OK";

			/**
			 * Join button text.
			 */
			public static final String join = "Join";
		}

		/**
		 * The IDs for the cards. These also function as the actionCommand string for
		 * the buttons responsible for card switches.
		 * 
		 * @author Miklós
		 *
		 */
		public static class CardIDs {
			/**
			 * Main Menu card ID
			 */
			public static final String mainMenu = "MainMenu";

			/**
			 * Settings card ID
			 */
			public static final String settings = "Settings";

			/**
			 * Game Board card ID
			 */
			public static final String gameBoard = "Board";

			/**
			 * Join Window card ID
			 */
			public static final String joinWindow = "JoinWindow";
		}

	}

	/**
	 * Hexagon configurations
	 * 
	 * @author Miklós
	 */
	public static class Hexagon {
		/**
		 * The radius of a hexagon
		 */
		public static final int radius = 50;

		/**
		 * The distance between hexa tiles
		 */
		public static final int padding = 0;

		/**
		 * The thickness of the inner hexagon line
		 */
		public static final int innerLineThickness = 0;

		/**
		 * The thickness of the outer hexagon line
		 */
		public static final int outerLineThickness = 4;

		/**
		 * The color of the inner "main" hexagon
		 */
		public static final int innerColor_default = 0x008844;

		/**
		 * The color of the outer "frame" hexagon
		 */
		public static final int outerColor_default = 0x000000;

		/**
		 * Hexagon text color
		 */
		public static final int textColor = 0xFFFFFF;

		/**
		 * The color of the inner hexagon when it is selected
		 */
		public static final int outerColor_selected = 0xDA6F25;

		/**
		 * The color of the outer
		 */
		public static final int outerColor_availableForAction = 0x06FFFF;

		/**
		 * The color of the hexagon when it contains stone resource
		 */
		public static final int innerColor_stone = 0xb8b09b;

		/**
		 * The color of the hexagon when it contains wood resource
		 */
		public static final int innerColor_wood = 0x913D02;

		/**
		 * The color of the hexagon when it contains wheat resource
		 */
		public static final int innerColor_wheat = 0xE6b800;

		/**
		 * The string displayed on the hexagon when it is not occupied
		 */
		public static final String freeTileString = "Free";
	}

	/**
	 * General Game Window settings
	 * 
	 * @author Miklós
	 *
	 */
	public static class GameWindow {
		/**
		 * The background color of the game window
		 */
		public static final Color background = Color.darkGray;
	}

	/**
	 * GameBoard configurations
	 * 
	 * @author Miklós
	 */
	public static class GameBoard {
		/**
		 * Game board default width
		 */
		public static final int width = 1200;

		/**
		 * Game board default height
		 */
		public static final int height = 800;

		/**
		 * Game board default font
		 */
		public static final Font font = new Font("Arial", Font.BOLD, 18);
	}

	/**
	 * The circle behind the hexagonal map
	 * 
	 * @author Miklós
	 *
	 */
	public static class Circle {

		/**
		 * Circle radius
		 */
		public static final int radius = 380;

		/**
		 * Circle color
		 */
		public static final int color = 0x006600;

		/**
		 * Circle line thickness
		 */
		public static final int lineThickness = 0;
	}

	/**
	 * Each player has a small colored circle representing him/her.
	 * 
	 * @author Miklós
	 *
	 */
	public static class PlayerCircle {
		/**
		 * Radius of the player circle
		 */
		public static final int radius = Hexagon.radius / 3;

		/**
		 * Default player circle color
		 */
		public static final int defaultColor = Circle.color;

		/**
		 * Default player circle line thickness
		 */
		public static final int lineThickness = 0;
	}

	/**
	 * 
	 * @author Miklós
	 *
	 */
	public static class PlayerColor {

		/**
		 * default player color
		 */
		public static final int color_default = 0x000000;

		/**
		 * Color of player 0 D007EC pink
		 */
		public static final int color_player0 = 0xD007EC;

		/**
		 * Color of player 1 F80000 red
		 */
		public static final int color_player1 = 0xF80000;

		/**
		 * Color of player 2 01FB17 green
		 */
		public static final int color_player2 = 0x01FB17;

		/**
		 * Color of player 2 0017FF blue
		 */
		public static final int color_player3 = 0x0017FF;
	}

	/**
	 * The embedded chat window configurations
	 * 
	 * @author Miklós
	 *
	 */
	public static class Chat {

		/**
		 * The text display area number of columns
		 */
		public static final int textAreaColoumns = 25;

		/**
		 * The text display area number of rows
		 */
		public static final int textAreaRows = 10;

		/**
		 * Chat thread sleep timer in milliseconds
		 */
		public static final int sleepTime = 100;
	}

	/**
	 * Timer used in game board.
	 * 
	 * @author Miklós
	 *
	 */
	public static class Timer {

		/**
		 * The timer before all players join. milliseconds
		 */
		public static final int gameStartTimerDelay = 100;

		/**
		 * The periodic update interval in milliseconds.
		 */
		public static final int periodicUpdateInterval = 100;
	}

	/**
	 * System messages configurations
	 * 
	 * @author Miklós
	 *
	 */
	public static class SystemMessages {

		/**
		 * The default system message.
		 */
		public static final String defaultMsg = "System Messages";

		/**
		 * The system message displayed when waiting for players to join.
		 */
		public static final String waitingForPlayers = "Waiting for other players to join";

		/**
		 * The message when the board is drawn.
		 */
		public static final String boardDrawn = "The board has been drawn.";

		/**
		 * The default sub message added to the system message when there are sub
		 * messages.
		 */
		public static final String defaultSubMsgAddition = " (Click here for details)";

		/**
		 * The system message window when it is your turn (the active player = the
		 * player with this client).
		 * 
		 * @author Miklós
		 *
		 */
		public static class YourTurn {

			/**
			 * The default system message
			 */
			public static final String sysMsg = "It is your turn!" + defaultSubMsgAddition;
			// public static final String startingTile = "Choose starting tile!";

			/**
			 * Remaining time message
			 */
			public static final String remainingTime = "Remaining time: ";

			/**
			 * Score message
			 */
			public static final String score = "Your score: ";

			/**
			 * Wood text
			 */
			public static final String wood = "Wood = ";

			/**
			 * Stone text
			 */
			public static final String stone = "Stone = ";

			/**
			 * Wheat text
			 */
			public static final String wheat = "Wheat = ";
		}

		/**
		 * Not your turn message
		 */
		public static final String notYourTurn = "It is not your turn!";

		/**
		 * String too long error message
		 */
		public static final String error_tooLong = "The string is too long.";

		/**
		 * Default error message addition
		 */
		public static final String error = "ERROR: ";

		/**
		 * Default time for which the error message stays on screen
		 */
		public static final int defaultErrorTimeSeconds = 2;

		/**
		 * Max number of dots to display during dot animation
		 */
		public static final int dotAnimationMaxDots = 4;

		/**
		 * The maximum lenght of the system messages
		 */
		public static final int maxSysMsgLength = 50;
	}

	/**
	 * Rectangle configurations
	 * 
	 * @author Miklós
	 *
	 */
	public static class Rectangle {

		/**
		 * rectangle width
		 */
		public static final int width = 10;

		/**
		 * rectangle height
		 */
		public static final int height = 30;

		/**
		 * rectangle line thickness
		 */
		public static final int lineThickness = 0;

		/**
		 * rectangle x offset from the center
		 */
		public static final int xOff = Hexagon.radius / 2;

		/**
		 * rectangle y offset from the center
		 */
		public static final int yOff = -(Hexagon.radius / 2 - height / 3);

		/**
		 * Square configurations
		 * 
		 * @author Miklós
		 *
		 */
		public static class Square {

			/**
			 * Square width
			 */
			public static final int width = Rectangle.width;

			/**
			 * Square height
			 */
			public static final int height = Rectangle.width;

			/**
			 * Square line thickness
			 */
			public static final int lineThickness = Rectangle.lineThickness;

			/**
			 * Square x offset from origin
			 */
			public static final int xOff = Rectangle.xOff;

			/**
			 * Square y offset from origin
			 */
			public static final int yOff = -Rectangle.yOff - Rectangle.height;
		}
	}

	/**
	 * Game over pop up configurations
	 * 
	 * @author Miklós
	 *
	 */
	public static class GameOverPopup {
		/**
		 * Default width
		 */
		public static final int width = 200;

		/**
		 * Default height
		 */
		public static final int height = 200;

	}

}
