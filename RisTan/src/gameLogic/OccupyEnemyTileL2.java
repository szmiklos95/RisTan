package gameLogic;

import config.Config;

/**
 * Describes an action of occupying an enemy empty tile, with enhanced probability and cost.
 * @author Andras
 *
 */
public class OccupyEnemyTileL2 extends OccupyEnemyEmptyTileAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * @param playerID the ID of the action executing player.
	 * @param point the coordinates of the target tile (in board coordinate system).
	 */
	public OccupyEnemyTileL2(int playerID,Point point){
		super(playerID,
				Config.Action.OccupyEnemyTileL2.time,
				point,
				Config.Action.OccupyEnemyTileL2.cost,
				Config.Action.OccupyEnemyTileL2.probability);
	}
	
	@Override
	public String toString() {
		return Config.Action.OccupyEnemyTileL2.name;
	}
}
