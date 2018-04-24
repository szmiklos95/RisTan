package gameLogic;

import config.Config;

//a player tries an action on a not town tile which requires a town tile
public class TileIsNotTownException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	TileIsNotTownException(){
		super(Config.Exception.TileIsNotTown.errorMessage);
	}
}
