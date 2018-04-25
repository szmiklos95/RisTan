package gameLogic;

import config.Config;

public class InvalidTileActionException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	InvalidTileActionException() {
		super(Config.Exception.InvalidTileAction.errorMessage);
	}
}
