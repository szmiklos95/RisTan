package gameLogic;

/**
 * Describes an action when the active player voluntarily ends his/her turn, despite there were actions which he/she could execute.
 * @author Andras
 *
 */
public class SwitchToNextPlayerAction extends InGameAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * @param playerID the ID of the action executing player.
	 */
	public SwitchToNextPlayerAction(int playerID){
		super(playerID);
	}
	
	/**
	 * Checks whether the action can be executed at a game state.
	 * It does not change the game state.
	 * If the action cannot be executed, throws an according GameLogicException.<br>
	 * If the game is over, throws a GameOverException.<br>
	 * If the executing player is not the active, throws a PlayerOutOfTurnException.<br>
	 * If the player cannot switch to the next, throws a CantSwitchToNextPlayerAction.
	 */
	@Override
	void check(GameState gameState) throws GameLogicException{
		super.check(gameState);
		if(!gameState.canSwitchToNextPlayer()) {
			throw new CantSwitchToNextPlayerException();
		}
	}
	
	/**
	 * Tries to execute the action on a game state.
	 * If the execution is invalid, the game state remains unchanged and throws a GameLogicException of the cause.<br>
	 * If the game is over, throws a GameOverException.<br>
	 * If the executing player is not the active, throws a PlayerOutOfTurnException. <br>
	 * If the player cannot switch to the next, throws a CantSwitchToNextPlayerAction.
	 */
	@Override
	void execute(GameState gameState) throws GameLogicException {
		super.execute(gameState);
		gameState.activePlayerEnd();
	}
}
