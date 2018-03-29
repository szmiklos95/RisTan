package gameLogic;

import java.util.Map;

public abstract class OccupyFreeTileAction extends TileAction {
	private static final long serialVersionUID = 1L;
	
	OccupyFreeTileAction(int playerID,int time,Point point,Map<Resource,Integer> cost){
		super(playerID,time,point,cost);
	}
	
	@Override
	void check(GameState gameState)throws GameLogicException{
		super.check(gameState);
		if(gameState.getBoard().getTileAt(getPoint()).getOwner()!=null) {
			throw new TileIsNotFreeException();
		}
	}
	
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		gameState.getBoard().getTileAt(getPoint()).occupy(gameState.getActivePlayer());
	}
}
