package gameLogic;

import config.Config;

//The player has not enough resources for something
public class InsufficientResourceException extends GameLogicException {
	private static final long serialVersionUID=1L;
	
	InsufficientResourceException() {
		super(Config.Exception.InsufficientResource.errorMessage);
	}
}