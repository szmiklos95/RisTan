package gameLogic;

import java.util.Map;

public abstract class OccupyEnemyVillageAction extends OccupyEnemyTileAction{
	private static final long serialVersionUID = 1L;
	
	public OccupyEnemyVillageAction(int playerID,int time,Point point,Map<Resource,Integer> cost,double probability){
		super(playerID,time,point,cost,probability);
	}
	
	@Override
	void check(GameState gameState)throws GameLogicException{
		super.check(gameState);
		Tile tile=gameState.getBoard().getTileAt(getPoint());
		if(tile.getBuildingLevel()!=BuildingLevel.Village) {
			throw new TileIsNotVillageException();
		}
	}
}
