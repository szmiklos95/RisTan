package gameLogic;

//describes an action in the game
/**
 * Describes an action in the game. These actions have a player who tries to execute them and a time required to execute them.
 * @author Andras
 *
 */
public abstract class InGameAction extends Action{
	private static final long serialVersionUID = 1L;
	
	/**
	 * The ID of the action executing player.
	 */
	private final int playerID;
	/**
	 * The required time to execute the action.
	 */
	private final int time;
	
	/**
	 * Constructor, creates an in game action which require no time to complete. 
	 * @param playerID the ID of the action executing player.
	 */
	public InGameAction(int playerID){
		this.playerID=playerID;
		time=0;
	}
	
	/**
	 * Constructor.
	 * @param playerID the ID of the action executing player.
	 * @param time the required time to execute the action.
	 */
	public InGameAction(int playerID,int time){
		this.playerID=playerID;
		this.time=time;
	}
	
	/**
	 * Checks whether the action can be executed at a game state.
	 * It does not change the game state.
	 * If the action cannot be executed, throws an according GameLogicException.<br>
	 * If the game is over, throws a GameOverException.<br>
	 * If the executing player is not the active, throws a PlayerOutOfTurnException.<br>
	 * If the executing player does not have enough time, throws a NotEnoughTimeException.
	 */
	@Override
	void check(GameState gameState)throws GameLogicException{
		if(gameState.isOver()) {
			throw new GameOverException();
		}
		if(gameState.getActivePlayer().getID()!=playerID) {
			throw new PlayerOutOfTurnException();
		}
		if(gameState.getTurn().getRemainingTime()<time) {
			throw new NotEnoughTimeException();
		}
	}
	
	/**
	 * Tries to execute the action on a game state.
	 * If the execution is invalid, the game state remains unchanged and throws a GameLogicException of the cause.<br>
	 * If the game is over, throws a GameOverException.<br>
	 * If the executing player is not the active, throws a PlayerOutOfTurnException.<br>
	 * If the executing player does not have enough time, throws a NotEnoughTimeException.
	 */
	@Override
	void execute(GameState gameState)throws GameLogicException{
		super.execute(gameState);
		gameState.getTurn().takeTime(time);
	}
	
	/**
	 * Return the time required for the action
	 * @author Miklós
	 * @return the time required for the action
	 */
	public int getRequiredTime() {
		return time;
	}
}
