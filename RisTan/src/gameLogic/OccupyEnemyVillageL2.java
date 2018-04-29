package gameLogic;

import config.Config;

/**
 * Describes an action of occupying an enemy village, with enhanced probability and cost.
 * @author Andras
 *
 */
public class OccupyEnemyVillageL2 extends OccupyEnemyVillageAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * @param playerID the ID of the action executing player.
	 * @param point the coordinates of the target tile (in board coordinate system).
	 */
	public OccupyEnemyVillageL2(int playerID,Point point){
		super(playerID,
				Config.Action.OccupyEnemyVillageL2.time,
				point,
				Config.Action.OccupyEnemyVillageL2.cost,
				Config.Action.OccupyEnemyVillageL2.probability);
	}
}
