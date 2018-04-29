package gameLogic;

import config.Config;

/**
 * Describes an action of offering a trade offer. This action require some time and resources depending on the offer to perform.
 * @author Andras
 *
 */
public class OfferTradeAction extends TradeAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The ID of the trade offer.
	 */
	private int offerID;
	/**
	 * The resource which the action executing player offers.
	 */
	private Resource give;
	/**
	 * The amount of the offered resource.
	 */
	private int give_amount;
	/**
	 * The resource which the action executing player wants.
	 */
	private Resource take;
	/**
	 * The amount of the offered resource.
	 */
	private int take_amount;

	/**
	 * Constructor.
	 * @param tradeOffer the trade offer from which the action gets the data.
	 */
	public OfferTradeAction(TradeOffer tradeOffer) {
		super(tradeOffer.getOffererID(),Config.Action.OfferTradeAction.time);
		offerID=tradeOffer.getID();
		give=tradeOffer.getGive();
		give_amount=tradeOffer.getGive_amount();
		take=tradeOffer.getTake();
		take_amount=tradeOffer.getTake_amount();
	}
	
	/**
	 * Checks whether the action can be executed at a game state.
	 * It does not change the game state.
	 * If the action cannot be executed, throws an according GameLogicException.<br>
	 * If the game is over, throws a GameOverException.<br>
	 * If the executing player is not the active, throws a PlayerOutOfTurnException.<br>
	 * If the executing player does not have enough time, throws a NotEnoughTimeException. <br>
	 * If the trade is not allowed, throws a TradeIsNotAllowedException. <br>
	 * If the player has insufficient resources to complete the offer, throws an InsufficientResourceExcpetion.
	 */
	@Override
	void check(GameState gameState)throws GameLogicException{
		super.check(gameState);
		if(!gameState.getActivePlayer().hasResource(give,give_amount)) {
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
	 * If the player has insufficient resources to complete the offer, throws an InsufficientResourceExcpetion.
	 */
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		Player player=gameState.getActivePlayer();
		player.takeResource(give,give_amount);
		TradeOffer tradeOffer=new TradeOffer(offerID,player.getID(),give,give_amount,take,take_amount);
		gameState.getMarket().putOffer(tradeOffer);
	}
}
