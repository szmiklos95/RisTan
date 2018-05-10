package gameLogic;

import config.Config;

/**
 * An exception when the player tries to do a tile action which is invalid in the current turn.
 * @author Andras
 *
 */
public class InvalidTileActionException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Construnctor.
	 */
	InvalidTileActionException() {
		super(Config.Exception.InvalidTileAction.errorMessage);
	}
}
