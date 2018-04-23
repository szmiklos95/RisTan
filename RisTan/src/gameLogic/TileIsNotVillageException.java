package gameLogic;

import config.Config;

//a player tries an action on a not village tile which requires a village tile
public class TileIsNotVillageException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getErrorMessage() {
		return Config.Exception.TileIsNotVillage.errorMessage;
	}
}
