package gameLogic;

import config.Config;

public class TradeIsNotAllowedException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getErrorMessage() {
		return Config.Exception.TradeIsNotAllowed.errorMessage;
	}
}
