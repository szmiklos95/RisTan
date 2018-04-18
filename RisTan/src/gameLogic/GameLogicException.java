package gameLogic;

import config.Config;

//An exception from the internal game logic
public class GameLogicException extends Exception {
	private static final long serialVersionUID=1L;
	
	public String getErrorMessage() {
		return Config.Exception.GameLogic.errorMessage;
	}
}
