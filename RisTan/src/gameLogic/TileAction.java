package gameLogic;

import java.util.Map;

/**
 * Describes an action which is bound to a tile. These actions usually require some time and some resources to perform.
 * @author Andras
 *
 */
public abstract class TileAction extends InGameAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The coordinates of the target tile.
	 */
	private Point point;
	/**
	 * The resource cost of the action.
	 */
	private Map<Resource,Integer> cost;
	
	/**
	 * Constructor.
	 * @param playerID the ID of the action executing player.
	 * @param time the time cost of the action.
	 * @param point the coordinates of the target tile (in board coordinate system).
	 * @param cost the resource cost of the action.
	 */
	public TileAction(int playerID,int time,Point point,Map<Resource,Integer> cost){
		super(playerID,time);
		this.point=point;
		this.cost=cost;
	}
	
	/**
	 * Gets the coordinates of the target tile (in board coordinate system).
	 * @return the coordinates of the target tile (in board coordinate system).
	 */
	Point getPoint() {
		return point;
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
	 * If this tile action is invalid in the actual turn, throws an InvaligTileActionException.
	 */
	@Override
	void check(GameState gameState)throws GameLogicException{
		super.check(gameState);
		if(gameState.getBoard().getTileAt(point)==null) {
			throw new NoTileAtPointException();
		}
		if(!gameState.getActivePlayer().hasResource(cost)) {
			throw new InsufficientResourceException();
		}
		if(!gameState.getTurn().isTileActionPossible(gameState,this)) {
			throw new InvalidTileActionException();
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
	 * If this tile action is invalid in the actual turn, throws an InvaligTileActionException.
	 */
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		gameState.getActivePlayer().takeResource(cost);
	}
}
