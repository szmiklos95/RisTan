package gameLogic;

import config.Config;

/**
 * An exception when a player tries to switch to the next player but the automatic actions or the obligatory events have not happened yet.
 * @author Andras
 *
 */
public class CantSwitchToNextPlayerException extends GameLogicException {
	private static final long serialVersionUID=1L;
	
	/**
	 * Constructor.
	 */
	CantSwitchToNextPlayerException() {
		super(Config.Exception.CantSwitchToNextPlayer.errorMessage);
	}
}
