package gameLogic;

import config.Config;

public class OccupyEnemyTileL2 extends OccupyEnemyEmptyTileAction {
	private static final long serialVersionUID = 1L;
	
	public OccupyEnemyTileL2(int playerID,Point point){
		super(playerID,
				Config.Action.OccupyEnemyTileL2.time,
				point,
				Config.Action.OccupyEnemyTileL2.cost,
				Config.Action.OccupyEnemyTileL2.probability);
	}
	
	@Override
	public String toString() {
		return Config.Action.OccupyEnemyTileL2.name;
	}
}
