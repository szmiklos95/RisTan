package gameLogic;

public class HarvestResourcesAction extends InGameAction {
	public HarvestResourcesAction(int playerID) {
		super(playerID);
	}
	
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		gameState.getBoard().harvest(gameState.getActivePlayer());
	}
}
