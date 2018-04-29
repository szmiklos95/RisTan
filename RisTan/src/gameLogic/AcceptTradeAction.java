package gameLogic;

import config.Config;

/**
 * Describes an action of accepting a trade offer. This action require some time and resources depending on the offer to perform.
 * @author Andras
 *
 */
public class AcceptTradeAction extends TradeAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The ID of the accepted offer.
	 */
	private final int offerID;

	/**
	 * Constructor.
	 * @param playerID: the ID of the accepting player
	 * @param offerID: the ID of the accepted offer
	 */
	public AcceptTradeAction(final int playerID,final int offerID) {
		super(playerID,Config.Action.OfferTradeAction.time);
		this.offerID=offerID;
	}
	
	/**
	 * Checks whether the action can be executed at a game state.
	 * It does not change the game state.
	 * If the action cannot be executed, throws an according GameLogicException.<br>
	 * If the game is over, throws a GameOverException.<br>
	 * If the executing player is not the active, throws a PlayerOutOfTurnException.<br>
	 * If the executing player does not have enough time, throws a NotEnoughTimeException. <br>
	 * If the trade is not allowed, throws a TradeIsNotAllowedException. <br>
	 * If the trade offer does not exist, throws a NoSuchTradeOfferException. <br>
	 * If the player has insufficient resources to complete the offer, throws an InsufficientResourceExcpetion.
	 */
	@Override
	void check(final GameState gameState)throws GameLogicException{
		super.check(gameState);
		TradeOffer tradeOffer=gameState.getMarket().getOffer(offerID);
		if(tradeOffer==null) {
			throw new NoSuchTradeOfferException();
		}
		Resource take=tradeOffer.getTake();
		int take_amount=tradeOffer.getTake_amount();
		if(!gameState.getActivePlayer().hasResource(take,take_amount)) {
			throw new InsufficientResourceException();
		}
	}

	/**
	 * Tries to execute the action on a game state.
	 * If the execution is invalid, the game state remains unchanged and throws a GameLogicException of the cause.<br>
	 * If the game is over, throws a GameOverException.<br>
	 * If the executing player is not the active, throws a PlayerOutOfTurnException.<br>
	 * If the executing player does not have enough time, throws a NotEnoughTimeException. <br>
	 * If the trade is not allowed, throws a TradeIsNotAllowedException. <br>
	 * If the trade offer does not exist, throws a NoSuchTradeOfferException. <br>
	 * If the player has insufficient resources to complete the offer, throws an InsufficientResourceExcpetion.
	 */
	@Override
	void execute(final GameState gameState)throws GameLogicException{
		super.execute(gameState);
		Player player=gameState.getActivePlayer();
		TradeOffer tradeOffer=gameState.getMarket().getOffer(offerID);
		Player offerer=gameState.getPlayerByID(tradeOffer.getOffererID());
		Resource give=tradeOffer.getGive();
		int give_amount=tradeOffer.getGive_amount();
		player.giveResource(give,give_amount);
		Resource take=tradeOffer.getTake();
		int take_amount=tradeOffer.getTake_amount();
		player.takeResource(take,take_amount);
		offerer.giveResource(take,take_amount);
	}
}
