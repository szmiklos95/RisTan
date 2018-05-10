package gameLogic;

import config.Config;

/**
 * An exception when the player has not enough resources to perform an action.
 * @author Andras
 *
 */
public class InsufficientResourceException extends GameLogicException {
	private static final long serialVersionUID=1L;
	
	/**
	 * Constructor.
	 */
	InsufficientResourceException() {
		super(Config.Exception.InsufficientResource.errorMessage);
	}
}