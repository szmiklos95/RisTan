package gameLogic;

public class SwitchToNextPlayerAction extends InGameAction {
	private static final long serialVersionUID = 1L;
	
	SwitchToNextPlayerAction(int playerID){
		super(playerID);
	}
	
	@Override
	void execute(GameState gameState) throws GameLogicException {
		gameState.activePlayerEnd();
	}
}
