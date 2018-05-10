package gameLogic;

import config.Config;

/**
 * An exception whena player tries an action on a not enemy tile which requires an enemy tile. 
 * @author Andras
 *
 */
public class TileIsNotEnemyException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	TileIsNotEnemyException() {
		super(Config.Exception.TileIsNotEnemy.errorMessage);
	}
}
