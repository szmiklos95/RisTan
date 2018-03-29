package gameLogic;

import config.Config;

public class OfferTradeAction extends InGameAction {
	private int offerID;
	private Resource give;
	private int give_amount;
	private Resource take;
	private int take_amount;

	public OfferTradeAction(TradeOffer tradeOffer) {
		super(tradeOffer.getOffererID(),Config.Action.OfferTradeAction.time);
		offerID=tradeOffer.getID();
		give=tradeOffer.getGive();
		give_amount=tradeOffer.getGive_amount();
		take=tradeOffer.getTake();
		take_amount=tradeOffer.getTake_amount();
	}
	
	@Override
	void check(GameState gameState)throws GameLogicException{
		super.check(gameState);
		if(!gameState.getActivePlayer().hasResource(give,give_amount)) {
			throw new InsufficientResourceException();
		}
	}
	
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		Player player=gameState.getActivePlayer();
		player.takeResource(give,give_amount);
		TradeOffer tradeOffer=new TradeOffer(offerID,player.getID(),give,give_amount,take,take_amount);
		gameState.getMarket().putOffer(tradeOffer);
	}
}
