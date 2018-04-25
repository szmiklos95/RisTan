package gameLogic;

import config.Config;

//a player tries an action on a not free tile which requires a free tile
public class TileIsNotFreeException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	TileIsNotFreeException() {
		super(Config.Exception.TileIsNotFree.errorMessage);
	}
}
