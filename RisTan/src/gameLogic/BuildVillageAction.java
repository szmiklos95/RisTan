package gameLogic;

import java.util.Map;

/**
 * Describes an action of building a village. This action has some variants in time and resource cost.
 * @author Andras
 *
 */
public abstract class BuildVillageAction extends TileAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * @param playerID the ID of the action executing player.
	 * @param time the time cost of the action.
	 * @param point the coordinates of the target tile (in board coordinate system).
	 * @param cost the resource cost of the action.
	 */
	public BuildVillageAction(int playerID,int time,Point point,Map<Resource,Integer> cost){
		super(playerID,time,point,cost);
	}
	
	/**
	 * Checks whether the action can be executed at a game state.
	 * It does not change the game state.
	 * If the action cannot be executed, throws an according GameLogicException.<br>
	 * If the game is over, throws a GameOverException.<br>
	 * If the executing player is not the active, throws a PlayerOutOfTurnException.<br>
	 * If the executing player does not have enough time, throws a NotEnoughTimeException. <br>
	 * If no tile exists at the target coordinates, throws a NoTileAtPointException. <br>
	 * If the executing player does not have enough resources, throws an InsufficientResourceException. <br>
	 * If this tile action is invalid in the actual turn, throws an InvaligTileActionException. <br>
	 * If the target tile is not owned by the action executing player, throws a TileIsNotOwnException. <br>
	 * If the target tile is not empty, throws a TileIsNotEmptyException.
	 */
	@Override
	void check(GameState gameState)throws GameLogicException{
		super.check(gameState);
		Tile tile=gameState.getBoard().getTileAt(getPoint());
		if(tile.getOwner()!=gameState.getActivePlayer()) {
			throw new TileIsNotOwnException();
		}
		if(tile.getBuildingLevel()!=BuildingLevel.None) {
			throw new TileIsNotEmptyException();
		}
	}
	
	/**
	 * Tries to execute the action on a game state.
	 * If the execution is invalid, the game state remains unchanged and throws a GameLogicException of the cause.<br>
	 * If the game is over, throws a GameOverException.<br>
	 * If the executing player is not the active, throws a PlayerOutOfTurnException.<br>
	 * If the executing player does not have enough time, throws a NotEnoughTimeException. <br>
	 * If no tile exists at the target coordinates, throws a NoTileAtPointException. <br>
	 * If the executing player does not have enough resources, throws an InsufficientResourceException. <br>
	 * If this tile action is invalid in the actual turn, throws an InvaligTileActionException. <br>
	 * If the target tile is not owned by the action executing player, throws a TileIsNotOwnException. <br>
	 * If the target tile is not empty, throws a TileIsNotEmptyException.
	 */
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		gameState.getBoard().getTileAt(getPoint()).setBuildingLevel(BuildingLevel.Village);
	}
}
