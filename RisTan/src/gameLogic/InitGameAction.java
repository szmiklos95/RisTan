package gameLogic;

/**
 * This action needs to be executed on the server for the game initialization.
 * @author Andras
 * 
 */
public class InitGameAction extends ManagementAction {
	private static final long serialVersionUID = 1L;
	
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		gameState.initGame();
	}
}
