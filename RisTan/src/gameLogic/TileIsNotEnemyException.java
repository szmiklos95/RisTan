package gameLogic;

import config.Config;

//a player tries an action on a not enemy tile which requires an enemy tile
public class TileIsNotEnemyException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getErrorMessage() {
		return Config.Exception.TileIsNotEnemy.errorMessage;
	}
}
