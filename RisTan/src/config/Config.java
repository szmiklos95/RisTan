package config;

import java.util.Map;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gameLogic.GetResourceTurn;
import gameLogic.NormalTurn;
import gameLogic.OccupyFreeTileTurn;
import gameLogic.Resource;
import gameLogic.StartTileChoiceTurn;
import gameLogic.Turn;

//Configuration options for the game
//Collection of magic constants
public abstract class Config {
	//Actions config
	public static class Action{
		public static class AcceptTradeAction{
			public static final int time=1;
		}
		public static class BuildTown{
			public static final int time=3;
			public static final Map<Resource,Integer> cost=createCost();
			private static Map<Resource,Integer> createCost(){
				HashMap<Resource,Integer> ret=new HashMap<Resource,Integer>();
				ret.put(Resource.Stone,2);
				ret.put(Resource.Wheat,1);
				ret.put(Resource.Wood,1);
				return ret;
			}
		}
		public static class BuildVillage{
			public static final int time=2;
			public static final Map<Resource,Integer> cost=createCost();
			private static Map<Resource,Integer> createCost(){
				HashMap<Resource,Integer> ret=new HashMap<Resource,Integer>();
				ret.put(Resource.Stone,1);
				ret.put(Resource.Wheat,1);
				ret.put(Resource.Wood,1);
				return ret;
			}
		}
		public static class OccupyEnemyTile{
			public static final int time=2;
			public static final Map<Resource,Integer> cost=createCost();
			private static Map<Resource,Integer> createCost(){
				HashMap<Resource,Integer> ret=new HashMap<Resource,Integer>();
				ret.put(Resource.Wheat,1);
				return ret;
			}
			public static final double probability=0.5;
		}
		public static class OccupyEnemyTileL2{
			public static final int time=2;
			public static final Map<Resource,Integer> cost=createCost();
			private static Map<Resource,Integer> createCost(){
				HashMap<Resource,Integer> ret=new HashMap<Resource,Integer>();
				ret.put(Resource.Wheat,2);
				ret.put(Resource.Wood,1);
				return ret;
			}
			public static final double probability=0.75;
		}
		public static class OccupyEnemyTown{
			public static final int time=4;
			public static final Map<Resource,Integer> cost=createCost();
			private static Map<Resource,Integer> createCost(){
				HashMap<Resource,Integer> ret=new HashMap<Resource,Integer>();
				ret.put(Resource.Stone,1);
				ret.put(Resource.Wheat,3);
				ret.put(Resource.Wood,3);
				return ret;
			}
			public static final double probability=0.5;
		}
		public static class OccupyEnemyTownL2{
			public static final int time=4;
			public static final Map<Resource,Integer> cost=createCost();
			private static Map<Resource,Integer> createCost(){
				HashMap<Resource,Integer> ret=new HashMap<Resource,Integer>();
				ret.put(Resource.Stone,1);
				ret.put(Resource.Wheat,4);
				ret.put(Resource.Wood,4);
				return ret;
			}
			public static final double probability=0.75;
		}
		public static class OccupyEnemyVillage{
			public static final int time=3;
			public static final Map<Resource,Integer> cost=createCost();
			private static Map<Resource,Integer> createCost(){
				HashMap<Resource,Integer> ret=new HashMap<Resource,Integer>();
				ret.put(Resource.Wheat,2);
				ret.put(Resource.Wood,2);
				return ret;
			}
			public static final double probability=0.5;
		}
		public static class OccupyEnemyVillageL2{
			public static final int time=3;
			public static final Map<Resource,Integer> cost=createCost();
			private static Map<Resource,Integer> createCost(){
				HashMap<Resource,Integer> ret=new HashMap<Resource,Integer>();
				ret.put(Resource.Wheat,3);
				ret.put(Resource.Wood,3);
				return ret;
			}
			public static final double probability=0.75;
		}
		public static class OccupyFreeTile{
			public static final int time=2;
			public static final Map<Resource,Integer> cost=new HashMap<Resource,Integer>();
		}
		public static class OfferTradeAction{
			public static final int time=1;
		}
		public static class TradeWithGameAction{
			public static final int time=1;
			//change ratio: for every given ratio_D, the player gets ratio_N
			public static final int ratio_N=1;
			public static final int ratio_D=3;
		}
	}
	
	//Board config
	public static class Board{
		//The number of tile layers around the central tile
		public static final int size=3;
		//resource probabilities
		public static final Map<Resource,Double> res_prob=createRes_prob();
		private static Map<Resource,Double> createRes_prob(){
			HashMap<Resource,Double> ret=new HashMap<Resource,Double>();
			ret.put(Resource.Stone,((double)1)/3);
			ret.put(Resource.Wheat,((double)1)/3);
			ret.put(Resource.Wood,((double)1)/3);
			return ret;
		}
	}
	
	//BuildingLevel config
	public static class BuildingLevel{
		public static class None{
			public static final int resourceNum=1;
			public static final int score=1;
		}
		
		public static class Village{
			public static final int resourceNum=2;
			public static final int score=3;
		}
		
		public static class Town{
			public static final int resourceNum=3;
			public static final int score=6;
		}
	}
	
	//GameLogicExceptions config
	public static class Exception{
		public static class GameLogic{
			public static final String errorMessage="Az akció nem hajtható végre, mert érvénytelen.";
		}
		public static class GameOver{
			public static final String errorMessage="Az akció nem hajtható végre, mert vége a játéknak, vagy még nem kezdõdött el.";
		}
		public static class InsufficientResource{
			public static final String errorMessage="Az akció nem hajtható végre, mert nong hozzá elég nyersanyagod.";
		}
		public static class InvalidTileAction{
			public static final String errorMessage="Ez az akció nem hajtható végre ezen a mezõn.";
		}
		public static class NotEnoughTime{
			public static final String errorMessage="Az akció nem hajtható végre, mert nincs hozzá elég idõd.";
		}
		public static class NoTileAtPoint{
			public static final String errorMessage="Az akció nem hajtható végre, mert ezen a ponton nincs mezõ.";
		}
		public static class PlayerOutOfTurn{
			public static final String errorMessage="Az akció nem hajtható végre, mert nem te következel.";
		}
		public static class TileIsNotEmpty{
			public static final String errorMessage="Az akció nem hajtható végre ezen a mezõn, mert a mezõ nem üres.";
		}
		public static class TileIsNotEnemy{
			public static final String errorMessage="Az akció nem hajtható végre ezen a mezõn, mert a mezõt nem birtokolja ellenséges játékos.";
		}
		public static class TileIsNotFree{
			public static final String errorMessage="Az akció nem hajtható végre ezen a mezõn, mert a mezõ nem szabad.";
		}
		public static class TileIsNotOwn{
			public static final String errorMessage="Az akció nem hajtható végre ezen a mezõn, mert a mezõ nem saját.";
		}
		public static class TileIsNotTown{
			public static final String errorMessage="Az akció nem hajtható végre ezen a mezõn, mivel a mezõn nem áll város.";
		}
		public static class TileIsNotVillage{
			public static final String errorMessage="Az akció nem hajtható végre ezen a mezõn, mivel a mezõn nem áll falu.";
		}
		public static class TradeIsNotAllowed{
			public static final String errorMessage="Az akció nem hajtható végre, mert a kereskedelem nem lehetésges.";
		}
	}
	
	//TurnOrder config
	public static class TurnOrder{
		public static final List<Turn> turns=createTurns();
		private static List<Turn> createTurns(){
			List<Turn> ret=new ArrayList<Turn>();
			ret.add(new StartTileChoiceTurn());
			for(int i=0;i<5;++i) {
				ret.add(new OccupyFreeTileTurn(1));
			}
			ret.add(new GetResourceTurn(createStartingResources()));
			for(int i=0;i<10;++i) {
				ret.add(new NormalTurn(7));
			}
			return ret;
		}
		private static Map<Resource,Integer> createStartingResources(){
			Map<Resource,Integer> ret=new HashMap<Resource,Integer>();
			ret.put(Resource.Stone,2);
			ret.put(Resource.Wheat,2);
			ret.put(Resource.Wood,2);
			return ret;
		}
	}
	
	/* *****************************************************************************
	 *  @@@@@@@@@@@@@@@@@@@@@@@@@@ Graphic configurations @@@@@@@@@@@@@@@@@@@@@@@@@@
	 * *****************************************************************************/
	
	/**
	 * Main menu
	 * @author Miklós
	 */
	public static class GUI{
		public static final int frameMinimumWidth = 300;
		public static final int frameMinimumHeight = 300;
		
		//The frame title of the main menu
		public static final String title = "Welcome to RisTan!";
		//The title after someone types his/her name
		public static final String nameTitle = "Ristan Beta - ";
		
		public static final String playerCount = "Number of players: ";
		
		// Spinner settings
		public static final int default_playerCount = 3;
		public static final int min_playerCount = 2;
		public static final int max_playerCount = 4;
		
		// Settings for the GridBagLayout
		public static class GridSettings {
			public static final int startingGridX = 0;
			public static final int startingGridY = 0;
			public static final Insets defaultInsets = new Insets(10,10,10,10);
			public static final int defaultGridWidth = 1;
			public static final int defaultGridHeight = 2;
		}
		
		// The text displayed on the buttons
		public static class ButtonTexts{
			public static final String mainMenu = "Main Menu";
			public static final String newGame = "New Game";
			public static final String exit = "Exit";
			public static final String ok= "OK";
			public static final String join = "Join";
		}
		
		// The IDs for the cards, and also the actionCommand for buttons which are responsible for card switches
		public static class CardIDs{
			public static final String mainMenu = "MainMenu";
			public static final String settings = "Settings";
			public static final String gameBoard = "Board";
			public static final String joinWindow = "JoinWindow";
		}
		
	}
	
	
	/**
	 * Hexagon config
	 * @author Miklós
	 */
	public static class Hexagon{
		//The radius of a hexagon
		public static final int radius = 50;
		//The distance between hexa tiles
		public static final int padding = 0;
		// The thickness of the inner hexagon line
		public static final int innerLineThickness = 0;
		// The thickness of the outer hexagon line
		public static final int outerLineThickness = 4;
		// The color of the inner "main" hexagon
		public static final int innerColor = 0x008844;
		// The color of the outer "frame" hexagon
		public static final int outerColor = 0xFFDD88;
		// Hexagon text color
		public static final int textColor = 0xFFFFFF;
		// The color of the inner hexagon when it is selected
		public static final int selectedOuterColor = 0xDA6F25;
		
	}
	
	/**
	 * GameBoard config
	 * @author Miklós
	 */
	public static class GameBoard{
		public static final int width = 1200;
		public static final int height = 800;
		public static final Font font = new Font("Arial", Font.BOLD, 18);
	}
	
	/**
	 * The circle behind the hexagonal map
	 * @author Miklós
	 *
	 */
	public static class Circle{
		public static final int radius = 380;
		public static final int color = 0x4488FF;
		public static final int lineThickness = 0;
	}
	
	/**
	 * The embedded chat window
	 * @author Miklós
	 *
	 */
	public static class Chat{
		public static final int textAreaColoumns = 25;
		public static final int textAreaRows = 10;
	}
	
	public static class Timer{
		public static final int gameStartTimerDelay = 100; //ms
	}
	
	public static class SystemMessage{
		public static final int timerDelay = 100; //ms
		public static final int col = 20;
		public static final int row = 3;
	}
	
}
