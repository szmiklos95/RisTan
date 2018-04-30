package gameLogic;

import config.Config;

/**
 * Describes an action of occupying an enemy town, with enhanced probability and cost.
 * @author Andras
 *
 */
public class OccupyEnemyTownL2 extends OccupyEnemyTownAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * @param playerID the ID of the action executing player.
	 * @param point the coordinates of the target tile (in board coordinate system).
	 */
	public OccupyEnemyTownL2(int playerID,Point point){
		super(playerID,
				Config.Action.OccupyEnemyTownL2.time,
				point,
				Config.Action.OccupyEnemyTownL2.cost,
				Config.Action.OccupyEnemyTownL2.probability);
	}
	
	@Override
	public String toString() {
		return Config.Action.OccupyEnemyTownL2.name;
	}
}
