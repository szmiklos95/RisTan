package gameLogic;

import config.Config;

/**
 * An exception whena player tries an action on a not free tile which requires a free tile.
 * @author Andras
 *
 */
public class TileIsNotFreeException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	TileIsNotFreeException() {
		super(Config.Exception.TileIsNotFree.errorMessage);
	}
}
