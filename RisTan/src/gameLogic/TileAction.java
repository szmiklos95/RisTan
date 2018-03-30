package gameLogic;

import java.util.Map;

//describes an action on a tile
public abstract class TileAction extends InGameAction {
	private static final long serialVersionUID = 1L;
	
	private Point point;
	private Map<Resource,Integer> cost;
	
	TileAction(int playerID,int time,Point point,Map<Resource,Integer> cost){
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
		if(!gameState.getTurn().isTileActionPossible(this)) {
			throw new InvalidTileActionException();
		}
	}
	
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		gameState.getActivePlayer().takeResource(cost);
	}
}
