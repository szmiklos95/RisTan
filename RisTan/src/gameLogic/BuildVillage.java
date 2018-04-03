package gameLogic;

import config.Config;

public class BuildVillage extends BuildVillageAction {
	private static final long serialVersionUID = 1L;
	
	public BuildVillage(int playerID,Point point){
		super(playerID,
				Config.Action.BuildVillage.time,
				point,
				Config.Action.BuildVillage.cost);
	}
}
