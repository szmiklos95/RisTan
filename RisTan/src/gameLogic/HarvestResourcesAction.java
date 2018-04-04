package gameLogic;

public class HarvestResourcesAction extends InGameAction {
	private static final long serialVersionUID = 1L;
	
	public HarvestResourcesAction(int playerID) {
		super(playerID);
	}
	
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		gameState.getBoard().harvest(gameState.getActivePlayer());
	}
}
