package gameLogic;

import config.Config;

//a player tries an action on a not own tile which requires an own tile
public class TileIsNotOwnException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	TileIsNotOwnException(){
		super(Config.Exception.TileIsNotOwn.errorMessage);
	}
}
