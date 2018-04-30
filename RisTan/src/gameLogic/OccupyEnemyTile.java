package gameLogic;

import config.Config;

public class OccupyEnemyTile extends OccupyEnemyEmptyTileAction {
	private static final long serialVersionUID = 1L;
	
	public OccupyEnemyTile(int playerID,Point point){
		super(playerID,
				Config.Action.OccupyEnemyTile.time,
				point,
				Config.Action.OccupyEnemyTile.cost,
				Config.Action.OccupyEnemyTile.probability);
	}
	
	@Override
	public String toString() {
		return Config.Action.OccupyEnemyTile.name;
	}
}
