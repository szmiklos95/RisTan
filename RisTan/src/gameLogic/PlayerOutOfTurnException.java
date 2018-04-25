package gameLogic;

import config.Config;

//a player wants to do something out of his/her turn
public class PlayerOutOfTurnException extends GameLogicException {
	private static final long serialVersionUID=1L;
	
	PlayerOutOfTurnException() {
		super(Config.Exception.PlayerOutOfTurn.errorMessage);
	}
}
