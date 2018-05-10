package gameLogic;

import config.Config;

/**
 * An exception when a player wants to do something out of his/her turn.
 * @author Andras
 *
 */
public class PlayerOutOfTurnException extends GameLogicException {
	private static final long serialVersionUID=1L;
	
	/**
	 * Constructor.
	 */
	PlayerOutOfTurnException() {
		super(Config.Exception.PlayerOutOfTurn.errorMessage);
	}
}
