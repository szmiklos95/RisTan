package gameLogic;

import config.Config;

/**
 * An exception when a player tries an action on a not town tile which requires a town tile.
 * @author Andras
 *
 */
public class TileIsNotTownException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	TileIsNotTownException(){
		super(Config.Exception.TileIsNotTown.errorMessage);
	}
}
