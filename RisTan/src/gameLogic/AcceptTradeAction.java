package gameLogic;

import config.Config;

public class AcceptTradeAction extends InGameAction {
	private static final long serialVersionUID = 1L;
	
	private int offerID;

	public AcceptTradeAction(int playerID, int offerID) {
		super(playerID,Config.Action.OfferTradeAction.time);
		this.offerID=offerID;
	}
	
	@Override
	void check(GameState gameState)throws GameLogicException{
		super.check(gameState);
		TradeOffer tradeOffer=gameState.getMarket().getOffer(offerID);
		Resource take=tradeOffer.getTake();
		int take_amount=tradeOffer.getTake_amount();
		if(!gameState.getActivePlayer().hasResource(take,take_amount)) {
			throw new InsufficientResourceException();
		}
	}

	@Override
	void execute(GameState gameState)throws GameLogicException{
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
