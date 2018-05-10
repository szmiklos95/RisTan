package gameLogic;

import config.Config;

/**
 * An exception which arises at referencing to a nonexistent trade offer.
 * @author Andras
 *
 */
public class NoSuchTradeOfferException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	NoSuchTradeOfferException() {
		super(Config.Exception.NoSuchTradeOffer.errorMessage);
	}
}
