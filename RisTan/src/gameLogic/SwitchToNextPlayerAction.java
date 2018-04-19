package gameLogic;

public class SwitchToNextPlayerAction extends InGameAction {
	private static final long serialVersionUID = 1L;
	
	public SwitchToNextPlayerAction(int playerID){
		super(playerID);
	}
	
	@Override
	void execute(GameState gameState) throws GameLogicException {
		super.execute(gameState);
		gameState.activePlayerEnd();
	}
}
