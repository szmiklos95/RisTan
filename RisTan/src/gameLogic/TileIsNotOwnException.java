package gameLogic;

import config.Config;

/**
 * An exception whena player tries an action on a not own tile which requires an own tile. 
 * @author Andras
 *
 */
public class TileIsNotOwnException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	TileIsNotOwnException(){
		super(Config.Exception.TileIsNotOwn.errorMessage);
	}
}
