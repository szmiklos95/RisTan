package gameLogic;

import config.Config;

/**
 * Describes an action of occupying a free tile normally.
 * @author Andras
 *
 */
public class OccupyFreeTile extends OccupyFreeTileAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * @param playerID the ID of the action executing player.
	 * @param point the coordinates of the target tile (in board coordinate system).
	 */
	public OccupyFreeTile(int playerID,Point point){
		super(playerID,
				Config.Action.OccupyFreeTile.time,
				point,
				Config.Action.OccupyFreeTile.cost);
	}
}
