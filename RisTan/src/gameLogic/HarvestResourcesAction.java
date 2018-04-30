package gameLogic;

/**
 * Describes an action where the player harvests the resources from his/her tiles. This action requires no time.
 * @author Andras
 *
 */
public class HarvestResourcesAction extends InGameAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * @param playerID the action executing player.
	 */
	public HarvestResourcesAction(int playerID) {
		super(playerID);
	}
	
	/**
	 * Tries to execute the action on a game state.
	 * If the execution is invalid, the game state remains unchanged and throws a GameLogicException of the cause.<br>
	 * If the game is over, throws a GameOverException.<br>
	 * If the executing player is not the active, throws a PlayerOutOfTurnException.
	 */
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		gameState.getBoard().harvest(gameState.getActivePlayer());
	}
}
