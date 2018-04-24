package gameLogic;

import java.io.Serializable;

//describes an action in or before the game
/**
 * Describes an Action in or before the game. Everything which can modify a GameState is an Action.
 * @author András
 * 
 */
public abstract class Action implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Checks whether the action can be executed on a GameState. Id does not change the GameState. If the action cannot be executed, throws an according GameLogicException.
	 * @param gameState: the GameState for checking the Action.
	 * @throws GameLogicException: if the Action cannot be executed, an according GameLogicException is thrown.
	 */
	abstract void check(final GameState gameState)throws GameLogicException;
	
	/**
	 * Tries to execute the Action on a GameState. If the execution is unsuccessful, the GameState remains unchanged and throws a GameLogicException of the cause.
	 * @param gameState: the GameState for executing the action.
	 * @throws GameLogicException if the Action cannot be executed, an according GameLogicException is thrown.
	 */
	void execute(final GameState gameState)throws GameLogicException{
		check(gameState);
	}
}
