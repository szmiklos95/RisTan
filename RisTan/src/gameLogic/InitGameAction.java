package gameLogic;

//execute on server to init game
public class InitGameAction extends ManagementAction {
	private static final long serialVersionUID = 1L;
	
	void execute(GameState gameState)throws GameLogicException{
		gameState.initGame();
	}
}
