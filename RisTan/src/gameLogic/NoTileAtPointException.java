package gameLogic;

import config.Config;

//a player tries to do something with a tile that not exists
public class NoTileAtPointException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	NoTileAtPointException() {
		super(Config.Exception.GameOver.errorMessage);
	}
}
