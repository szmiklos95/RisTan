package gameLogic;

import config.Config;

/**
 * An exception when a player tries an action on a not village tile which requires a village tile.
 * @author Andras
 *
 */
public class TileIsNotVillageException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	TileIsNotVillageException(){
		super(Config.Exception.TileIsNotVillage.errorMessage);
	}
}
