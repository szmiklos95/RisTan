package gameLogic;

import config.Config;

public class InvalidTileActionException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getErrorMessage() {
		return Config.Exception.InvalidTileAction.errorMessage;
	}
}
