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
	 * @param point the coordinates of the target tile.
	 * @param cost the resource cost of the action.
	 */
	public TileAction(int playerID,int time,Point point,Map<Resource,Integer> cost){
		super(playerID,time);
		this.point=point;
		this.cost=cost;
	}
	
	Point getPoint() {
		return point;
	}
	
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
	
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		gameState.getActivePlayer().takeResource(cost);
	}
}
