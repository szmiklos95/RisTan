package gameLogic;

import config.Config;

//a player tries an action on a not empty tile which requires an empty tile
public class TileIsNotEmptyException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getErrorMessage() {
		return Config.Exception.TileIsNotEmpty.errorMessage;
	}
}
