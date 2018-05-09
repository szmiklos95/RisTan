package gameLogic;

import config.Config;

//the game is over and a player wants to do an in game action
public class GameOverException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	GameOverException() {
		super(Config.Exception.GameOver.errorMessage);
	}
}
