package gameLogic;

import config.Config;

/**
 * An exception when the player does not have enough time to perform an action.
 * @author Andras
 *
 */
public class NotEnoughTimeException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	NotEnoughTimeException() {
		super(Config.Exception.NotEnoughTime.errorMessage);
	}
}
