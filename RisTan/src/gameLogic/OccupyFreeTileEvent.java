package gameLogic;

/**
 * An event of occupying any of the free tiles.
 * @author Andras
 *
 */
class OccupyFreeTileEvent extends Event {
	/**
	 * Checks whether the action is an OccupyFreeTileAction.
	 * @return true if and only if the action is an OccupyFreeTileAction.
	 */
	@Override
	boolean satisfies(Action action) {
		return action instanceof OccupyFreeTileAction;
	}
}
