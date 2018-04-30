package gameLogic;

import config.Config;

/**
 * Describes an action of occupying an enemy empty tile, with basic probability and cost.
 * @author Andras
 *
 */
public class OccupyEnemyTile extends OccupyEnemyEmptyTileAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * @param playerID the ID of the action executing player.
	 * @param point the coordinates of the target tile (in board coordinate system).
	 */
	public OccupyEnemyTile(int playerID,Point point){
		super(playerID,
				Config.Action.OccupyEnemyTile.time,
				point,
				Config.Action.OccupyEnemyTile.cost,
				Config.Action.OccupyEnemyTile.probability);
	}
	
	@Override
	public String toString() {
		return Config.Action.OccupyEnemyTile.name;
	}
}
