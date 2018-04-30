package gameLogic;

/**
 * Describes an action when the player gets an amount of a resource. This action requires no time.
 * @author Andras
 *
 */
public class GetResourceAction extends InGameAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The got resource.
	 */
	private final Resource resource;
	/**
	 * The amount of the got resource.
	 */
	private final int amount;
	
	/**
	 * Constructor.
	 * @param playerID the ID of the player which gets the resource.
	 * @param resource the got resource.
	 * @param amount the amount of the got resource.
	 */
	public GetResourceAction(int playerID,Resource resource,int amount){
		super(playerID);
		this.resource=resource;
		this.amount=amount;
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
		gameState.getActivePlayer().giveResource(resource,amount);
	}
}
