package config;

import java.util.Map;
import java.awt.Font;
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
			public static final String errorMessage="Az akci� nem hajthat� v�gre, mert �rv�nytelen.";
		}
		public static class GameOver{
			public static final String errorMessage="Az akci� nem hajthat� v�gre, mert v�ge a j�t�knak, vagy m�g nem kezd�d�tt el.";
		}
		public static class InsufficientResource{
			public static final String errorMessage="Az akci� nem hajthat� v�gre, mert nong hozz� el�g nyersanyagod.";
		}
		public static class InvalidTileAction{
			public static final String errorMessage="Ez az akci� nem hajthat� v�gre ezen a mez�n.";
		}
		public static class NotEnoughTime{
			public static final String errorMessage="Az akci� nem hajthat� v�gre, mert nincs hozz� el�g id�d.";
		}
		public static class NoTileAtPoint{
			public static final String errorMessage="Az akci� nem hajthat� v�gre, mert ezen a ponton nincs mez�.";
		}
		public static class PlayerOutOfTurn{
			public static final String errorMessage="Az akci� nem hajthat� v�gre, mert nem te k�vetkezel.";
		}
		public static class TileIsNotEmpty{
			public static final String errorMessage="Az akci� nem hajthat� v�gre ezen a mez�n, mert a mez� nem �res.";
		}
		public static class TileIsNotEnemy{
			public static final String errorMessage="Az akci� nem hajthat� v�gre ezen a mez�n, mert a mez�t nem birtokolja ellens�ges j�t�kos.";
		}
		public static class TileIsNotFree{
			public static final String errorMessage="Az akci� nem hajthat� v�gre ezen a mez�n, mert a mez� nem szabad.";
		}
		public static class TileIsNotOwn{
			public static final String errorMessage="Az akci� nem hajthat� v�gre ezen a mez�n, mert a mez� nem saj�t.";
		}
		public static class TileIsNotTown{
			public static final String errorMessage="Az akci� nem hajthat� v�gre ezen a mez�n, mivel a mez�n nem �ll v�ros.";
		}
		public static class TileIsNotVillage{
			public static final String errorMessage="Az akci� nem hajthat� v�gre ezen a mez�n, mivel a mez�n nem �ll falu.";
		}
		public static class TradeIsNotAllowed{
			public static final String errorMessage="Az akci� nem hajthat� v�gre, mert a kereskedelem nem lehet�sges.";
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
			ret.add(new GetResourceTurn(startingResources));
			for(int i=0;i<10;++i) {
				ret.add(new NormalTurn());
			}
			return ret;
		}
		private static final Map<Resource,Integer> startingResources=createStartingResources();
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
	 * @author Mikl�s
	 */
	public static class GUI{
		public static final String title = "Welcome to RisTan!";
		public static final String mainmenu = "Main Menu";
		public static final String newgame = "New Game";
		public static final String exit = "Exit";
		public static final String newgame_settings = "New Game Settings";
		
		public static final int default_textfield_columnCount = 5;
		public static final String playerCount = "Number of players: ";
		
		public static final int default_playerCount = 3;
		public static final int min_playerCount = 2;
		public static final int max_playerCount = 4;
	}
	
	
	/**
	 * Hexagon config
	 * @author Mikl�s
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
	 * @author Mikl�s
	 */
	public static class GameBoard{
		public static final int width = 1200;
		public static final int height = 800;
		public static final Font font = new Font("Arial", Font.BOLD, 18);
	}
	
	/**
	 * The circle behind the hexagonal map
	 * @author Mikl�s
	 *
	 */
	public static class Circle{
		public static final int radius = 380;
		public static final int color = 0x4488FF;
		public static final int lineThickness = 0;
	}
	
}
