package gameLogic;

import config.Config;

/**
 * An action which describes the acceptance of a trade offer.
 * @author András
 * 
 */
public class AcceptTradeAction extends TradeAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * offerID: the ID of the accepted offer
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
	@Override
	void check(final GameState gameState)throws GameLogicException{
		super.check(gameState);
		TradeOffer tradeOffer=gameState.getMarket().getOffer(offerID);
		Resource take=tradeOffer.getTake();
		int take_amount=tradeOffer.getTake_amount();
		if(!gameState.getActivePlayer().hasResource(take,take_amount)) {
			throw new InsufficientResourceException();
		}
	}

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
