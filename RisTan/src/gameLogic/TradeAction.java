package gameLogic;

/**
 * Describes an action of trading. These actions require some time and resources depending on the action to perform.
 * @author Andras
 *
 */
public abstract class TradeAction extends InGameAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * @param playerID the ID of the action executing player.
	 * @param cost the resource cost of the action.
	 */
	public TradeAction(int playerID,int time){
		super(playerID,time);
	}
	
	/**
	 * Checks whether the action can be executed at a game state.
	 * It does not change the game state.
	 * If the action cannot be executed, throws an according GameLogicException.<br>
	 * If the game is over, throws a GameOverException.<br>
	 * If the executing player is not the active, throws a PlayerOutOfTurnException.<br>
	 * If the executing player does not have enough time, throws a NotEnoughTimeException. <br>
	 * If the trade is not allowed, throws a TradeIsNotAllowedException.
	 */
	@Override
	void check(GameState gameState)throws GameLogicException{
		super.check(gameState);
		if(!gameState.getTurn().isTradeEnabled()) {
			throw new TradeIsNotAllowedException();
		}
	}
	
	/**
	 * Tries to execute the action on a game state.
	 * If the execution is invalid, the game state remains unchanged and throws a GameLogicException of the cause.<br>
	 * If the game is over, throws a GameOverException.<br>
	 * If the executing player is not the active, throws a PlayerOutOfTurnException.<br>
	 * If the executing player does not have enough time, throws a NotEnoughTimeException. <br>
	 * If the trade is not allowed, throws a TradeIsNotAllowedException.
	 */
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
	}
}
