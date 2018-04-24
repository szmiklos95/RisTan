package gameLogic;

import config.Config;

public class TradeIsNotAllowedException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	TradeIsNotAllowedException(){
		super(Config.Exception.TradeIsNotAllowed.errorMessage);
	}
}
