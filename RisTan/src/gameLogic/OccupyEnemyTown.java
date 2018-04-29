package gameLogic;

import config.Config;

/**
 * Describes an action of occupying an enemy town, with basic probability and cost.
 * @author Andras
 *
 */
public class OccupyEnemyTown extends OccupyEnemyTownAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * @param playerID the ID of the action executing player.
	 * @param point the coordinates of the target tile (in board coordinate system).
	 */
	public OccupyEnemyTown(int playerID,Point point){
		super(playerID,
				Config.Action.OccupyEnemyTown.time,
				point,
				Config.Action.OccupyEnemyTown.cost,
				Config.Action.OccupyEnemyTown.probability);
	}
}
