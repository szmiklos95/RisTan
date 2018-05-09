package gameLogic;

import config.Config;

public class NotEnoughTimeException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	NotEnoughTimeException() {
		super(Config.Exception.NotEnoughTime.errorMessage);
	}
}
