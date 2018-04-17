package gameLogic;

import java.util.Map;

public abstract class BuildVillageAction extends TileAction {
	private static final long serialVersionUID = 1L;
	
	public BuildVillageAction(int playerID,int time,Point point,Map<Resource,Integer> cost){
		super(playerID,time,point,cost);
	}
	
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
	
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		gameState.getBoard().getTileAt(getPoint()).setBuildingLevel(BuildingLevel.Village);
	}
}
