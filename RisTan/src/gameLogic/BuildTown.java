package gameLogic;

import config.Config;

public class BuildTown extends TileAction {
	private static final long serialVersionUID = 1L;
	
	BuildTown(int playerID,Point point){
		super(playerID,Config.Action.BuildTown.time,point,Config.Action.BuildTown.cost);
	}
	
	@Override
	void check(GameState gameState)throws GameLogicException{
		super.check(gameState);
		Tile tile=gameState.getBoard().getTileAt(getPoint());
		if(tile.getOwner()!=gameState.getActivePlayer()) {
			throw new TileIsNotOwnException();
		}
		if(tile.getBuildingLevel()!=BuildingLevel.Village) {
			throw new TileIsNotVillageException();
		}
	}
	
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		gameState.getBoard().getTileAt(getPoint()).setBuildingLevel(BuildingLevel.Town);
	}
}
