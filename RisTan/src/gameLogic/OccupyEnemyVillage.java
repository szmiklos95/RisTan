package gameLogic;

import config.Config;

/**
 * Describes an action of occupying an enemy village, with basic probability and cost.
 * @author Andras
 *
 */
public class OccupyEnemyVillage extends OccupyEnemyVillageAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * @param playerID the ID of the action executing player.
	 * @param point the coordinates of the target tile (in board coordinate system).
	 */
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
