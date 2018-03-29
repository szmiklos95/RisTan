package gameLogic;

import config.Config;

public class OccupyFreeTile extends OccupyFreeTileAction {
	private static final long serialVersionUID = 1L;
	
	OccupyFreeTile(int playerID,Point point){
		super(playerID,Config.Action.OccupyFreeTile.time,point,Config.Action.OccupyFreeTile.cost);
	}
}
