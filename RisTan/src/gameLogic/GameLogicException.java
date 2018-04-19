package gameLogic;

import config.Config;

//An exception from the internal game logic
public class GameLogicException extends Exception {
	private static final long serialVersionUID=1L;
	
	GameLogicException() {
		super(Config.Exception.GameLogic.errorMessage);
	}
	
	GameLogicException(String message) {
		super(message);
	}
}
