package gameLogic;

import java.util.HashMap;

public class OccupyFreeTileFree extends OccupyFreeTileAction {
	private static final long serialVersionUID = 1L;
	
	public OccupyFreeTileFree(int playerID,Point point){
		super(playerID,0,point,new HashMap<Resource,Integer>());
	}
}
