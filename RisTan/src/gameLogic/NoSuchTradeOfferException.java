package gameLogic;

import config.Config;

public class NoSuchTradeOfferException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	NoSuchTradeOfferException() {
		super(Config.Exception.NoSuchTradeOffer.errorMessage);
	}
}
