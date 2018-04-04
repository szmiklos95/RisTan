package gameLogic;

import config.Config;

public class OccupyEnemyTownL2 extends OccupyEnemyTownAction {
	private static final long serialVersionUID = 1L;
	
	public OccupyEnemyTownL2(int playerID,Point point){
		super(playerID,
				Config.Action.OccupyEnemyTownL2.time,
				point,
				Config.Action.OccupyEnemyTownL2.cost,
				Config.Action.OccupyEnemyTownL2.probability);
	}
}
