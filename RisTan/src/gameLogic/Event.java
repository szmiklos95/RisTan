package gameLogic;

/**
 * An event is a "pattern" of action. For example, building a village on any own tile.
 * @author Andras
 *
 */
abstract class Event {
	/**
	 * Checks whether an action matches the pattern.
	 * @param action the action to be checked.
	 * @return true if and only if the action matches the pattern.
	 */
	abstract boolean satisfies(Action action);
}
