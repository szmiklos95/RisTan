package gameLogic;

/**
 * After executing the InitGameAction on the server, get an instance of this action and execute it on the clients for starting the game. <br>
 * This action is responsible for the synchronized board, player order and turn order generation.
 * @author Andras
 *
 */
public class StartGameAction extends ManagementAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The string which describes the board resources.
	 */
	private String boardGenerator;
	/**
	 * The string which describes the order of the players.
	 */
	private String playerShuffleOrder;
	/**
	 * The string which describes the order of the turns.
	 */
	private String turnOrderGenerator;
	
	/**
	 * Constructor.
	 * @param boardGenerator the board generator string.
	 * @param playerShuffleOrder the player order string.
	 * @param turnOrderGenerator the turn order string.
	 */
	public StartGameAction(String boardGenerator,String playerShuffleOrder,String turnOrderGenerator) {
		this.boardGenerator=boardGenerator;
		this.playerShuffleOrder=playerShuffleOrder;
		this.turnOrderGenerator=turnOrderGenerator;
	}
	
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		gameState.startGame(boardGenerator,playerShuffleOrder,turnOrderGenerator);
	}
}