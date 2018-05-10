package gameLogic;

import config.Config;

/**
 * Describes an action of trading with the game for a fixed ratio. This action require some time and resources depending on the offer to perform.
 * @author Andras
 *
 */
public class TradeWithGameAction extends TradeAction{
	private static final long serialVersionUID = 1L;
	
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
	 * Constructor.
	 * @param give the given resource.
	 * @param give_amount the amount of the given resource.
	 * @param take the wanted resource.
	 */
	public TradeWithGameAction(int playerID,Resource give,int give_amount,Resource take) {
		super(playerID,Config.Action.TradeWithGameAction.time);
		this.give=give;
		this.give_amount=give_amount;
		this.take=take;
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
		int take_amount=give_amount*Config.Action.TradeWithGameAction.ratio_N/Config.Action.TradeWithGameAction.ratio_D;
		player.giveResource(take,take_amount);
	}
	
	@Override
	public String toString() {
		return Config.Action.TradeWithGameAction.name;
	}
}
