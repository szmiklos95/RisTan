package gameLogic;

// after executing the initGameAction  on the server,
// get an instance of this on the server, then execute it on the clients to start the game
public class StartGameAction extends ManagementAction {
	private static final long serialVersionUID = 1L;
	
	private String boardGenerator;
	private String playerShuffleOrder;
	private String turnOrderGenerator;
	
	public StartGameAction(String boardGenerator,String playerShuffleOrder,String turnOrderGenerator) {
		this.boardGenerator=boardGenerator;
		this.playerShuffleOrder=playerShuffleOrder;
		this.turnOrderGenerator=turnOrderGenerator;
	}
	
	void execute(GameState gameState)throws GameLogicException{
		gameState.startGame(boardGenerator,playerShuffleOrder,turnOrderGenerator);
	}
}