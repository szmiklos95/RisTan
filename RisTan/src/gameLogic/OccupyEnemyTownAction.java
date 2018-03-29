package gameLogic;

import java.util.Map;

public abstract class OccupyEnemyTownAction extends OccupyEnemyTileAction {
	private static final long serialVersionUID = 1L;
	
	OccupyEnemyTownAction(int playerID,int time,Point point,Map<Resource,Integer> cost,double probability){
		super(playerID,time,point,cost,probability);
	}
	
	@Override
	void check(GameState gameState)throws GameLogicException{
		super.check(gameState);
		Tile tile=gameState.getBoard().getTileAt(getPoint());
		if(tile.getBuildingLevel()!=BuildingLevel.Town) {
			throw new TileIsNotTownException();
		}
	}
}
