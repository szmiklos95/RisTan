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
	
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		gameState.getBoard().harvest(gameState.getActivePlayer());
	}
}
