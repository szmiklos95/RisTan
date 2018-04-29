package gameLogic;

import config.Config;

/**
 * Describes an action of building a village normally.
 * @author Andras
 *
 */
public class BuildVillage extends BuildVillageAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * @param playerID the ID of the action executing player.
	 * @param point the coordinates of the target tile (in board coordinate system).
	 */
	public BuildVillage(int playerID,Point point){
		super(playerID,
				Config.Action.BuildVillage.time,
				point,
				Config.Action.BuildVillage.cost);
	}
}
