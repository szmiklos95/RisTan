package gameLogic;

/**
 * An event of building a village on any of the own tiles.
 * @author Andras
 *
 */
class BuildVillageEvent extends Event {
	/**
	 * Checks whether the action is a BuildVillageAction.
	 * @return true if and only if the action is a BuildVillageAction.
	 */
	@Override
	boolean satisfies(Action action) {
		return action instanceof BuildVillageAction;
	}
}
