package gameLogic;

import config.Config;

public class OccupyFreeTile extends TileAction {
	private static final long serialVersionUID = 1L;
	
	OccupyFreeTile(int playerID,Point point){
		super(playerID,Config.Action.OccupyFreeTile.time,point,Config.Action.OccupyFreeTile.cost);
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
