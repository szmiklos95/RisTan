package gameLogic;

import java.util.HashMap;

public class BuildVillageFree extends BuildVillageAction {
private static final long serialVersionUID = 1L;
	
	public BuildVillageFree(int playerID,Point point){
		super(playerID,0,point,new HashMap<Resource,Integer>());
	}
}
