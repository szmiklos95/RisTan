package gameLogic;

public abstract class ManagementAction extends Action {
	private static final long serialVersionUID = 1L;
	
	void check(GameState gameState)throws GameLogicException{}
}
