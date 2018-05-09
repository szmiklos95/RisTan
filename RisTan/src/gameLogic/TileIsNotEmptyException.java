package gameLogic;

import config.Config;

//a player tries an action on a not empty tile which requires an empty tile
public class TileIsNotEmptyException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	TileIsNotEmptyException() {
		super(Config.Exception.TileIsNotEmpty.errorMessage);
	}
}
