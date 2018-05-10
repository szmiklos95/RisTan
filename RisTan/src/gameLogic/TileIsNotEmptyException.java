package gameLogic;

import config.Config;

/**
 * An exception when a player tries an action on a not empty tile which requires an empty tile.
 * @author Andras
 *
 */
public class TileIsNotEmptyException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	TileIsNotEmptyException() {
		super(Config.Exception.TileIsNotEmpty.errorMessage);
	}
}
