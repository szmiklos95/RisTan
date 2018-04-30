package gameLogic;

import config.Config;

public class OccupyEnemyVillageL2 extends OccupyEnemyVillageAction {
	private static final long serialVersionUID = 1L;
	
	public OccupyEnemyVillageL2(int playerID,Point point){
		super(playerID,
				Config.Action.OccupyEnemyVillageL2.time,
				point,
				Config.Action.OccupyEnemyVillageL2.cost,
				Config.Action.OccupyEnemyVillageL2.probability);
	}
	
	@Override
	public String toString() {
		return Config.Action.OccupyEnemyVillageL2.name;
	}
}
