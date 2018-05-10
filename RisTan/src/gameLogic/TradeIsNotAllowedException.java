package gameLogic;

import config.Config;

/**
 * An exception when a player tries to execute a trade action when the trade is not allowed.
 * @author Andras
 *
 */
public class TradeIsNotAllowedException extends GameLogicException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 */
	TradeIsNotAllowedException(){
		super(Config.Exception.TradeIsNotAllowed.errorMessage);
	}
}
