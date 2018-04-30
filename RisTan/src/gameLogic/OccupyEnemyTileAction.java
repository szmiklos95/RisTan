package gameLogic;

import java.util.Map;

import config.Config;

public abstract class OccupyEnemyTileAction extends ProbabilisticTileAction{
	private static final long serialVersionUID = 1L;
	
	public OccupyEnemyTileAction(int playerID,int time,Point point,Map<Resource,Integer> cost,double probability){
		super(playerID,time,point,cost,probability);
	}
	
	@Override
	void check(GameState gameState)throws GameLogicException{
		super.check(gameState);
		Tile tile=gameState.getBoard().getTileAt(getPoint());
		if((tile.getOwner()==null)||(tile.getOwner()==gameState.getActivePlayer())) {
			throw new TileIsNotEnemyException();
		}
	}
	
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		if(isSuccessful()) {
			gameState.getBoard().getTileAt(getPoint()).occupy(gameState.getActivePlayer());
		}
	}
	
	@Override
	public String toString() {
		return Config.Action.OccupyEnemyTile.name;
	}
}
