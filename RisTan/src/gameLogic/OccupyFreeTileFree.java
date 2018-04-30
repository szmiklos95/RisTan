package gameLogic;

import java.util.HashMap;

/**
 * Describes an action of occupying a free tile for free (at the game start).
 * @author Andras
 *
 */
public class OccupyFreeTileFree extends OccupyFreeTileAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * @param playerID the ID of the action executing player.
	 * @param point the coordinates of the target tile (in board coordinate system).
	 */
	public OccupyFreeTileFree(int playerID,Point point){
		super(playerID,0,point,new HashMap<Resource,Integer>());
	}
}
