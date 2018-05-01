package gameLogic;

import config.Config;

/**
 * An exception from the internal game logic.
 * @author Andras
 *
 */
public class GameLogicException extends Exception {
	private static final long serialVersionUID=1L;
	
	/**
	 * Constructor.
	 */
	GameLogicException() {
		super(Config.Exception.GameLogic.errorMessage);
	}
	
	/**
	 * Constructor with message.
	 * @param message the message of the exception.
	 */
	GameLogicException(String message) {
		super(message);
	}
}
