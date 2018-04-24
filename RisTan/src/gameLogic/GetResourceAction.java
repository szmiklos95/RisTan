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
	
	public GetResourceAction(int playerID,Resource resource,int amount){
		super(playerID);
		this.resource=resource;
		this.amount=amount;
	}
	
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		gameState.getActivePlayer().giveResource(resource,amount);
	}
}
