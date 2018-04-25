package gameLogic;

public abstract class ManagementAction extends Action {
	private static final long serialVersionUID = 1L;
	
	@Override
	void check(GameState gameState)throws GameLogicException{}
	
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
	}
}
