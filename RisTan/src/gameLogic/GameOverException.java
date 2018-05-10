package gameLogic;

import config.Config;

/**
 * An exception when the game is over and a player wants to do an in game action.
 * @author Andras
 *
 */
public class GameOverException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	GameOverException() {
		super(Config.Exception.GameOver.errorMessage);
	}
}
