package gameLogic;

import config.Config;

//The player has not enough resources for something
public class InsufficientResourceException extends GameLogicException {
	private static final long serialVersionUID=1L;
	
	@Override
	public String getErrorMessage() {
		return Config.Exception.InsufficientResource.errorMessage;
	}
}