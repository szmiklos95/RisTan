package gameLogic;

import config.Config;

/**
 * An exception of trying to do something with a nonexistent tile.
 * @author Andras
 *
 */
public class NoTileAtPointException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	NoTileAtPointException() {
		super(Config.Exception.GameOver.errorMessage);
	}
}
