package gameLogic;

import config.Config;

public class OccupyEnemyVillage extends OccupyEnemyVillageAction {
	private static final long serialVersionUID = 1L;
	
	public OccupyEnemyVillage(int playerID,Point point){
		super(playerID,
				Config.Action.OccupyEnemyVillage.time,
				point,
				Config.Action.OccupyEnemyVillage.cost,
				Config.Action.OccupyEnemyVillage.probability);
	}
	
	@Override
	public String toString() {
		return Config.Action.OccupyEnemyVillage.name;
	}
}
