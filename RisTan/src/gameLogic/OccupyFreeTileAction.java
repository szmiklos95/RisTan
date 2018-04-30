package gameLogic;

import java.util.Map;

import config.Config;

/**
 * Describes an action of occupying a free tile. This action has some variants in time and resource cost.
 * @author Andras
 *
 */
public abstract class OccupyFreeTileAction extends TileAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * @param playerID the ID of the action executing player.
	 * @param time the time cost of the action.
	 * @param point the coordinates of the target tile (in board coordinate system).
	 * @param cost the resource cost of the action.
	 */
	public OccupyFreeTileAction(int playerID,int time,Point point,Map<Resource,Integer> cost){
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
	 * If the target tile is not free, throws a TileIsNotFreeException.
	 */
	@Override
	void check(GameState gameState)throws GameLogicException{
		super.check(gameState);
		if(gameState.getBoard().getTileAt(getPoint()).getOwner()!=null) {
			throw new TileIsNotFreeException();
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
	 * If the target tile is not free, throws a TileIsNotFreeException.
	 */
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		gameState.getBoard().getTileAt(getPoint()).occupy(gameState.getActivePlayer());
	}
	
	@Override
	public String toString() {
		return Config.Action.OccupyFreeTile.name;
	}
}
