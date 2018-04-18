package gameLogic;

import config.Config;

//a player wants to do something out of his/her turn
public class PlayerOutOfTurnException extends GameLogicException {
	private static final long serialVersionUID=1L;
	
	@Override
	public String getErrorMessage() {
		return Config.Exception.PlayerOutOfTurn.errorMessage;
	}
}
