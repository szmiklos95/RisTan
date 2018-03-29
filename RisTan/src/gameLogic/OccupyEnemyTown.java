package gameLogic;

import config.Config;

public class OccupyEnemyTown extends OccupyEnemyTownAction {
	private static final long serialVersionUID = 1L;
	
	OccupyEnemyTown(int playerID,Point point){
		super(playerID,
				Config.Action.OccupyEnemyTown.time,
				point,
				Config.Action.OccupyEnemyTown.cost,
				Config.Action.OccupyEnemyTown.probability);
	}
}
